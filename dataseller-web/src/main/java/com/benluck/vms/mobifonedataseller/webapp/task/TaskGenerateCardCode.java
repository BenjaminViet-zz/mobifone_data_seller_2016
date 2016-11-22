package com.benluck.vms.mobifonedataseller.webapp.task;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.utils.Config;
import com.benluck.vms.mobifonedataseller.context.AppContext;
import com.benluck.vms.mobifonedataseller.core.business.NotificationManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.PackageDataCodeGenManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.PackageDataManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.NotificationDTO;
import com.benluck.vms.mobifonedataseller.core.dto.PackageDataDTO;
import com.benluck.vms.mobifonedataseller.core.dto.UserDTO;
import com.benluck.vms.mobifonedataseller.utils.MobiFoneSecurityBase64Util;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

import javax.ejb.DuplicateKeyException;
import javax.ejb.ObjectNotFoundException;
import java.util.HashSet;
import java.util.TimerTask;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/6/16
 * Time: 23:22
 * To change this template use File | Settings | File Templates.
 */
public class TaskGenerateCardCode extends TimerTask{
    private Logger logger = Logger.getLogger(TaskGenerateCardCode.class);

    private final String VMS_CODE_PREFIX = "2";
    private final int NUMBER_CARD_CODE_IN_EACH_BATCH = 1000000;

    private Long userId;
    private Integer year;
    private String yearCode;
    private String[] packageDataIds;
    private StringBuilder prefixYearCode;
    private StringBuilder tmpUnitPriceCode;
    private StringBuilder tmpUnitPriceCodeBatchIndex;
    private StringBuilder tmpCardCode;
    private int minCardCodeIndex;
    private int maxCardCodeIndex;

    private HashSet<String> tmpCardCodeHS;

    private ApplicationContext ctx = AppContext.getApplicationContext();
    private PackageDataCodeGenManagementLocalBean packageCodeDataGenService = ctx.getBean(PackageDataCodeGenManagementLocalBean.class);
    private PackageDataManagementLocalBean packageDataService = ctx.getBean(PackageDataManagementLocalBean.class);
    private NotificationManagementLocalBean notificationService = ctx.getBean(NotificationManagementLocalBean.class);
    private RedisTemplate<String, String> redisTemplate = (RedisTemplate<String, String>) AppContext.getApplicationContext().getBean("redisTemplate");

    public TaskGenerateCardCode(Long userId, Integer year, String[] packageDataIds) {
        this.year = year;
        this.yearCode = year.toString().substring(2, 4);
        this.packageDataIds = packageDataIds;
        this.prefixYearCode = new StringBuilder(VMS_CODE_PREFIX).append(yearCode);
        this.userId = userId;
    }

    @Override
    public void run() {

        if(Config.getInstance().getProperty("redis.turn_on").equals("true")){
            logger.info("================PACKAGE DATA CODE GENERATION TASK================");
            logger.info("Pinging to Redis Server...");
            String redisResponse = redisTemplate.getConnectionFactory().getConnection().ping();
            logger.info("Redis response: " + redisResponse);
            if(redisResponse.equals("PONG")){
                if(packageDataIds != null && packageDataIds.length > 0){
                    logger.info("Card Code Year Generation: " + yearCode);
                    for (String packageDataIdStr : packageDataIds){
                        try{
                            Long packageDataId = Long.valueOf(packageDataIdStr);
                            PackageDataDTO packageDataDTO = this.packageDataService.findById(packageDataId);

                            tmpUnitPriceCode = new StringBuilder(String.valueOf(packageDataDTO.getValue() / 1000).replaceAll("\\.\\d*", ""));

                            if(tmpUnitPriceCode.toString().length() > 2){
                                logger.error("The system does not support to generate Card Code for unit price larger than 100! ");
                                continue;
                            }

                            if(tmpUnitPriceCode.toString().length() < 2){
                                tmpUnitPriceCode = new StringBuilder("0").append(tmpUnitPriceCode.toString());
                            }

                            logger.info("Start generating Card Code for PackageId: " + packageDataId);

                            // reverse batch to get nice Card Code first. (avoid this kind of Card Code: 216100000000)
                            for(int batchIndex = 8; batchIndex >= 0; batchIndex--){
                                tmpUnitPriceCodeBatchIndex = new StringBuilder(tmpUnitPriceCode).append("_").append(batchIndex + 1);

                                logger.info("Generating Card Code for batch " + batchIndex);
                                tmpCardCodeHS = generateCardCode(yearCode, tmpUnitPriceCode.toString(), batchIndex);

                                logger.info("Saving batch " + batchIndex + " with " + tmpCardCodeHS.size() + " Card Codes.");
                                this.packageCodeDataGenService.insertUpdatePackageDataCodeGenAndBatch(packageDataId, year, batchIndex, tmpCardCodeHS.size(), (batchIndex == 0 ? true : false));
                                logger.info("Saved batch " + batchIndex + " into PackageDataCodeGen table for tracking");

                                logger.info("Saving batch "  + batchIndex + " to Redis Database");
                                redisTemplate.opsForHash().put(this.prefixYearCode.toString(), tmpUnitPriceCodeBatchIndex.toString(), tmpCardCodeHS);
                            }
                            createNotificationMessage(true, packageDataDTO.getName());
                            logger.info("Finish generating 10 batches of Card Code " + tmpCardCodeHS.size() + " Card Code for KEY: '" + VMS_CODE_PREFIX + yearCode + "', HASHKEY: '" + tmpUnitPriceCode.toString() + "'");
                        }catch (DuplicateKeyException dke){
                            createNotificationMessage(false, "packageId: " + packageDataIdStr);
                            logger.error("Duplicated PackageDataCodeGen for PackageDataId: " + packageDataIdStr + " in year " + year.toString());
                            logger.error("Details: " + dke.getMessage());
                        }catch (ObjectNotFoundException one){
                            createNotificationMessage(false, "packageId: " + packageDataIdStr);
                            logger.error("Can not find PackageDataEntity with packageId: " + packageDataIdStr);
                            logger.error("Details: " + one.getMessage());
                        }catch (Exception e){
                            createNotificationMessage(false, "packageId: " + packageDataIdStr);
                            logger.error(" Error happened while update PackageDateCodeGenEntity. See details as below.");
                            logger.error(e.getMessage());
                        }
                    }
                }
            }else{
                createNotificationMessage(false, null);
                logger.error("Connection to Redis Server is not reachable. Please check Redis Server and start it up!");
                logger.error("Generation Card Code is cancelled!");
            }
            logger.info("================FINISH GENERATION CARD CODE TASK================");
        }else{
            try{
                this.packageCodeDataGenService.AddOrUpdateProcessing(year, packageDataIds, Constants.PACKAGE_DATA_CODE_GEN_STATUS_FAILED);
                createNotificationMessage(false, null);
            }catch (Exception e){
                createNotificationMessage(false, null);
            }
            logger.info("================GENERATION CARD CODE TASK: Cancelled. Your setting for Redis Server is turned off. Please check again================");
        }
    }

    private void createNotificationMessage(Boolean isSuccess, String packageDataId){
        try{
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(this.userId);

            NotificationDTO notificationDTO = new NotificationDTO();
            notificationDTO.setUser(userDTO);

            if(isSuccess){
                notificationDTO.setMessageType(Constants.GENERATE_CARD_CODE_FINISH_SUCCESS);
                notificationDTO.setMessage("Sinh Card Code hoàn tất" + (packageDataId != null ? " cho Gói " + packageDataId : "." ));
            }else{
                notificationDTO.setMessageType(Constants.GENERATE_CARD_CODE_FINISH_FAILED);
                notificationDTO.setMessage("Sinh Card Code thất bại" + (packageDataId != null ? " cho Gói " + packageDataId : "." ));
            }

            notificationService.addItem(notificationDTO);
        }catch (Exception e){
            logger.error("Could not create notification message for Task Generate Card Code with status: " + (isSuccess ? "SUCCESS" : "FAILED"));
        }
    }

    private HashSet<String> generateCardCode(String yearCode, String unitPriceCode, int batchIndex){
        minCardCodeIndex = batchIndex * NUMBER_CARD_CODE_IN_EACH_BATCH;
        maxCardCodeIndex = minCardCodeIndex + NUMBER_CARD_CODE_IN_EACH_BATCH;

        StringBuilder prefixCardCode = new StringBuilder();
        prefixCardCode.append(VMS_CODE_PREFIX).append(yearCode).append(unitPriceCode);

        HashSet<String> cardCodeHashSet = new HashSet<String>();
        for(int i = minCardCodeIndex; i < maxCardCodeIndex; i++){
            tmpCardCode = new StringBuilder(prefixCardCode);
            if(i < 10){
                tmpCardCode.append("000000").append(i);
            }else if(i >= 10 && i < 100){
                tmpCardCode.append("00000").append(i);
            }else if(i >= 100 && i < 1000){
                tmpCardCode.append("0000").append(i);
            }else if (i >= 1000 && i < 10000){
                tmpCardCode.append("000").append(i);
            }else if(i >= 10000 && i < 100000){
                tmpCardCode.append("00").append(i);
            }else if(i >= 100000 && i < 1000000){
                tmpCardCode.append("0").append(i);
            }else{
                tmpCardCode.append(i);
            }
            cardCodeHashSet.add(MobiFoneSecurityBase64Util.encode(tmpCardCode.toString()));
        }
        return cardCodeHashSet;
    }
}
