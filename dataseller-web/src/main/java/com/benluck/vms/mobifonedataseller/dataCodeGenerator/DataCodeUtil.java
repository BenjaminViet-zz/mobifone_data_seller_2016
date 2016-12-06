package com.benluck.vms.mobifonedataseller.dataCodeGenerator;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.utils.CacheUtil;
import com.benluck.vms.mobifonedataseller.common.utils.Config;
import com.benluck.vms.mobifonedataseller.context.AppContext;
import com.benluck.vms.mobifonedataseller.core.business.PackageDataCodeGenManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.UsedCardCodeManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.PackageDataCodeGenDTO;
import com.benluck.vms.mobifonedataseller.redis.domain.DataCode;
import com.benluck.vms.mobifonedataseller.util.RedisUtil;
import org.apache.log4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;

import javax.ejb.DuplicateKeyException;
import javax.ejb.ObjectNotFoundException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/5/16
 * Time: 22:35
 * To change this template use File | Settings | File Templates.
 */
public class DataCodeUtil {

    private static Logger logger = Logger.getLogger(DataCode.class);

    private static RedisTemplate<String, String> redisTemplate = (RedisTemplate<String, String>) AppContext.getApplicationContext().getBean("redisTemplate");
    private static PackageDataCodeGenManagementLocalBean packageCodeDataGenService = AppContext.getApplicationContext().getBean(PackageDataCodeGenManagementLocalBean.class);
    private static UsedCardCodeManagementLocalBean usedCardCodeService = (UsedCardCodeManagementLocalBean)AppContext.getApplicationContext().getBean(UsedCardCodeManagementLocalBean.class);
    private static StringBuilder tmpCardCode;

    /**
     *
     * @param packageDataCodeGenDTO
     * @param year
     * @param yearCode
     * @param unitPriceCode
     * @param numberOfDataCodes Number of Card Code need to be taken for the Order exporting.
     * @return
     */
    public static Object[] generateDataCodes(PackageDataCodeGenDTO packageDataCodeGenDTO, Integer year, String yearCode, String unitPriceCode, Integer numberOfDataCodes) throws Exception{
        return generateDataCodeList(packageDataCodeGenDTO, year, yearCode, (unitPriceCode.length() == 1 ? "0" + unitPriceCode : unitPriceCode), numberOfDataCodes);
    }

    /**
     *  Function sinh ra danh sách Data Code theo params.
     * @param packageDataCodeGenDTO     Used to search nearest available batch index in the PackageDataCodeGen this packageDataId.
     * @param yearCode          Prefix 3 ký tự đầu của năm trong mỗi Data Code
     * @param unitPriceCode         Prefix 2 ký tự tiếp theo của mệnh giá gói trong mỗi Data Code
     * @param numberOfCardCode      Số lượng Data Code cần sinh ra
     * @return
     */
    public static Object[] generateDataCodeList(PackageDataCodeGenDTO packageDataCodeGenDTO, Integer year, String yearCode, String unitPriceCode, Integer numberOfCardCode) throws Exception{

        Map<String, HashSet<String>> mapCardCodeHSRemainingInBatches = new HashMap<String, HashSet<String>>();
        HashSet<String> tmpCardCodeHSFromCache = null;
        int cardCodeSizeCounter = 0;

        if(Config.getInstance().getProperty("redis.turn_on").equals("false")){
            tmpCardCodeHSFromCache = RedisUtil.getUsedCardCodeByKey();
            mapCardCodeHSRemainingInBatches.put("NULL", tmpCardCodeHSFromCache);
            return new Object[]{tmpCardCodeHSFromCache.size(), tmpCardCodeHSFromCache, mapCardCodeHSRemainingInBatches};
        }else{
            HashSet<String> usedCardCode21610HashSet = new HashSet<String>();
            Calendar current = Calendar.getInstance();
            if(current.get(Calendar.YEAR) == 2016 && unitPriceCode.equals(Constants.USED_UNIT_PRICE_CODE)){
                usedCardCode21610HashSet = RedisUtil.getUsedCardCodeByKey();
            }

            HashSet<String> cardCodeHashSet = new HashSet<String>();

            try{
                StringBuilder tmpUnitPriceCodeWithBatchIndex = null;
                int batchSizeRemaining = -1;

                for(int batchIndex = 1; batchIndex <= 9; batchIndex++){
                    if(batchIndex == 1){
                        batchSizeRemaining = packageDataCodeGenDTO.getBatch1_Remaining().intValue();
                    }else if(batchIndex == 2){
                        batchSizeRemaining = packageDataCodeGenDTO.getBatch2_Remaining().intValue();
                    }else if(batchIndex == 3){
                        batchSizeRemaining = packageDataCodeGenDTO.getBatch3_Remaining().intValue();
                    }else if(batchIndex == 4){
                        batchSizeRemaining = packageDataCodeGenDTO.getBatch4_Remaining().intValue();
                    }else if(batchIndex == 5){
                        batchSizeRemaining = packageDataCodeGenDTO.getBatch5_Remaining().intValue();
                    }else if(batchIndex == 6){
                        batchSizeRemaining = packageDataCodeGenDTO.getBatch6_Remaining().intValue();
                    }else if(batchIndex == 7){
                        batchSizeRemaining = packageDataCodeGenDTO.getBatch7_Remaining().intValue();
                    }else if(batchIndex == 8){
                        batchSizeRemaining = packageDataCodeGenDTO.getBatch8_Remaining().intValue();
                    }else if(batchIndex == 9){
                        batchSizeRemaining = packageDataCodeGenDTO.getBatch9_Remaining().intValue();
                    }

                    if(batchSizeRemaining > 0){
                        tmpUnitPriceCodeWithBatchIndex = new StringBuilder(unitPriceCode).append("_").append(batchIndex);
                        tmpCardCodeHSFromCache = (HashSet<String>) redisTemplate.opsForHash().get(yearCode, tmpUnitPriceCodeWithBatchIndex.toString());
                        if(tmpCardCodeHSFromCache.size() != batchSizeRemaining){
                            throw new Exception("The remaining Card Code size in batch " + batchIndex + " of packageData name : " + packageDataCodeGenDTO.getPackageData().getName() + ", year: " + year + " not same. THIS IS A FATAL ERROR. PLEASE CHECK REDIS CONSISTENCY!");
                        }
                        if(tmpCardCodeHSFromCache.size() + cardCodeSizeCounter <= numberOfCardCode.intValue()){

                            if(usedCardCode21610HashSet.size() > 0){
                                Iterator<String> ito = tmpCardCodeHSFromCache.iterator();
                                while (ito.hasNext()){
                                    tmpCardCode = new StringBuilder(ito.next());
                                    if(!usedCardCode21610HashSet.contains(tmpCardCode.toString())){
                                        cardCodeHashSet.add(tmpCardCode.toString());
                                        cardCodeSizeCounter++;
                                    }
                                }
                            }else{
                                cardCodeHashSet.addAll(tmpCardCodeHSFromCache);
                                cardCodeSizeCounter += tmpCardCodeHSFromCache.size();
                            }

                            mapCardCodeHSRemainingInBatches.put(tmpUnitPriceCodeWithBatchIndex.toString(), new HashSet<String>());

                            if(cardCodeSizeCounter == numberOfCardCode.intValue()){
                                break;
                            }
                        }else{
                            HashSet<String> remainingCardCodeInCacheHS = new HashSet<>();
                            Iterator<String> ito = tmpCardCodeHSFromCache.iterator();
                            while(ito.hasNext()){
                                if(cardCodeSizeCounter == numberOfCardCode.intValue()){
                                    remainingCardCodeInCacheHS.add(ito.next());
                                }else{
                                    cardCodeHashSet.add(ito.next());
                                    cardCodeSizeCounter++;
                                }
                            }

                            mapCardCodeHSRemainingInBatches.put(tmpUnitPriceCodeWithBatchIndex.toString(), remainingCardCodeInCacheHS);
                            break;
                        }
                    }
                }
            }catch (Exception e){
                logger.error(e.getMessage());
                throw new Exception(e.getMessage());
            }

            if(cardCodeSizeCounter != numberOfCardCode.intValue()){
                logger.error("Not enough Card Code in Redis Database to taking for this Order. Please try another one");
                throw new Exception("NOT_ENOUGH_CARD_CORD_2_TAKE");
            }

            return new Object[]{cardCodeSizeCounter, cardCodeHashSet, mapCardCodeHSRemainingInBatches};
        }
    }

    public static void updateRemainingCardCodeSize(Long packageDataCodeGenId, String redisKey, Map<String, HashSet<String>> mapCardCodeRemainingHS) throws ObjectNotFoundException, DuplicateKeyException{
        StringBuilder tmpUnitPriceCodeWithBatchIndex = null;
        Iterator<String> ito = mapCardCodeRemainingHS.keySet().iterator();
        HashSet<String> tmpCardCodeSizeRemaining = null;
        while(ito.hasNext()){
            tmpUnitPriceCodeWithBatchIndex = new StringBuilder(ito.next());
            if(!tmpUnitPriceCodeWithBatchIndex.toString().equals("NULL")){
                tmpCardCodeSizeRemaining = mapCardCodeRemainingHS.get(tmpUnitPriceCodeWithBatchIndex.toString());
                updateRemainingCardCodeSizeOnDB(packageDataCodeGenId, Integer.valueOf(tmpUnitPriceCodeWithBatchIndex.toString().substring(tmpUnitPriceCodeWithBatchIndex.toString().length() - 1, tmpUnitPriceCodeWithBatchIndex.toString().toString().length())), tmpCardCodeSizeRemaining.size());
                if(Config.getInstance().getProperty("redis.turn_on").equals("true")){
                    updateRemainingCardCodeSizeOnCache(redisKey, tmpUnitPriceCodeWithBatchIndex.toString(), tmpCardCodeSizeRemaining);
                }
            }
        }
    }

    private static void updateRemainingCardCodeSizeOnDB(Long packageDataCodeGenId, int batchIndex, int remainingCardCodeSize) throws ObjectNotFoundException, DuplicateKeyException{
        logger.info("Updating Card Code Size on DB");
        packageCodeDataGenService.updateBatchRemainingCardCodeSize(packageDataCodeGenId, batchIndex, remainingCardCodeSize);
    }

    private static void updateRemainingCardCodeSizeOnCache(String redisKey, String redisHashKey, HashSet<String> cardCodeSizeRemaining){
        logger.info("Updating Card Code Size on Redis DB");
        redisTemplate.opsForHash().put(redisKey, redisHashKey, cardCodeSizeRemaining);
    }

    /**
     * Store Data Code list to Redis with KEY (region) is Year number and Hash Key (Object Key) is Unit Price value to check later.
     * @param dataCode      Object that includes DataCode list for a specific Order.
     */
    public static void storeDataCodes(DataCode dataCode){
        RedisTemplate<String, String> redisTemplate = (RedisTemplate<String, String>) AppContext.getApplicationContext().getBean("redisTemplate");
        redisTemplate.opsForHash().put(dataCode.getKey(), dataCode.getHashKey(), dataCode);
    }
}
