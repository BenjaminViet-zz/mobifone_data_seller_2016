package com.benluck.vms.mobifonedataseller.webapp.task;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.context.AppContext;
import com.benluck.vms.mobifonedataseller.core.business.NotificationManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.OrderManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.PackageDataCodeGenManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.NotificationDTO;
import com.benluck.vms.mobifonedataseller.core.dto.OrderDTO;
import com.benluck.vms.mobifonedataseller.core.dto.PackageDataCodeGenDTO;
import com.benluck.vms.mobifonedataseller.core.dto.UserDTO;
import com.benluck.vms.mobifonedataseller.dataCodeGenerator.DataCodeUtil;
import com.benluck.vms.mobifonedataseller.util.RedisUtil;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Map;
import java.util.TimerTask;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/8/16
 * Time: 23:27
 * To change this template use File | Settings | File Templates.
 */
public class TaskTakeCardCode extends TimerTask{
    private Logger logger = Logger.getLogger(TaskTakeCardCode.class);

    private ApplicationContext ctx = AppContext.getApplicationContext();
    private OrderManagementLocalBean orderService = ctx.getBean(OrderManagementLocalBean.class);
    private PackageDataCodeGenManagementLocalBean packageDataCodeGenService = ctx.getBean(PackageDataCodeGenManagementLocalBean.class);
    private NotificationManagementLocalBean notificationService = ctx.getBean(NotificationManagementLocalBean.class);
    private RedisTemplate<String, String> redisTemplate = (RedisTemplate<String, String>) AppContext.getApplicationContext().getBean("redisTemplate");

    private Long orderId;
    private String unitPriceCode;
    private Long userId;

    public TaskTakeCardCode(Long userId, Long orderId, String unitPriceCode) {
        this.userId = userId;
        this.orderId = orderId;
        this.unitPriceCode = unitPriceCode;
    }

    @Override
    public void run() {
        logger.info("=================TAKING CARD CODE TASK - Starting...=================");
        logger.info("Getting Order info...");
        boolean hasError = false;
        OrderDTO orderDTO = null;
        Calendar current = Calendar.getInstance();
        String yearCode = String.valueOf(current.get(Calendar.YEAR)).replace("0","");

        try{
            orderDTO = this.orderService.findById(orderId);

            while(RedisUtil.getLockRedisKey(yearCode, unitPriceCode)){
                Thread.sleep(15000);
            }

            if(orderDTO.getOrderStatus().equals(Constants.ORDER_STATUS_FINISH)){
                RedisUtil.lockOrUnlockRedisKey(yearCode, unitPriceCode, true);

                PackageDataCodeGenDTO packageDataCodeGenDTO = this.packageDataCodeGenService.findByUniqueCompositeKey(orderDTO.getPackageData().getPackageDataId(), current.get(Calendar.YEAR));

                Object[] cardCodeHSGenerationObject = DataCodeUtil.generateDataCodes(packageDataCodeGenDTO, Calendar.getInstance().get(Calendar.YEAR), yearCode, unitPriceCode, orderDTO.getQuantity());

                if(!Integer.valueOf(cardCodeHSGenerationObject[0].toString()).equals(orderDTO.getQuantity())){
                    throw new Exception("Error when taking Card Code List from Cache. Details: Not matching request size and generated size. ");
                }

                orderDTO.setCardCodeHashSet2Store((HashSet<String>)cardCodeHSGenerationObject[1]);
                orderDTO.setCardCodeProcessStatus(Constants.ORDER_CARD_CODE_COMPLETED_STATUS);

                this.orderService.updateItem(orderDTO, false);

                DataCodeUtil.updateRemainingCardCodeSize(packageDataCodeGenDTO.getPackageDataCodeGenId(), yearCode, (Map<String, HashSet<String>>)cardCodeHSGenerationObject[2]);
                createNotificationMessage(true, null);

                RedisUtil.lockOrUnlockRedisKey(yearCode, unitPriceCode, false);
            }else{
                hasError = true;
                createNotificationMessage(false, null);
                logger.error("TAKING CARD CODE TASK is cancelled. The Order is only with status FINISH will be processing for Card Code list.");
            }
        }catch (Exception e){
            RedisUtil.lockOrUnlockRedisKey(yearCode, unitPriceCode, false);
            hasError = true;
            if(e.getMessage().equals("NOT_ENOUGH_CARD_CORD_2_TAKE")){
                createNotificationMessage(false, true);
            }else{
                createNotificationMessage(false, null);
            }
            logger.error("Error happen in TAKING CARD CODE for OrderId: " + orderId);
            logger.error("Details: " + e.getMessage());
        }

        if(hasError && orderDTO != null){
            try{
                orderDTO.setCardCodeProcessStatus(Constants.ORDER_CARD_CODE_FAILED_STATUS);
                this.orderService.updateItem(orderDTO, false);
            }catch (Exception e){
                logger.error(e.getMessage());
            }
        }

        logger.info("=================TAKING CARD CODE TASK - FINISHED");
    }

    private void createNotificationMessage(Boolean isSuccess, Boolean isNotEnoughCardCodeToTake){
        try{
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(this.userId);

            NotificationDTO notificationDTO = new NotificationDTO();
            notificationDTO.setUser(userDTO);

            if(isSuccess){
                notificationDTO.setMessageType(Constants.TAKE_CARD_CODE_4_ORDER_SUCCESS);
                notificationDTO.setMessage("Đơn hàng orderId " + orderId +" đã hoàn tất sinh Card Code");
            }else{
                notificationDTO.setMessageType(Constants.TAKE_CARD_CODE_4_ORDER_FAILED);
                if(isNotEnoughCardCodeToTake != null && !isNotEnoughCardCodeToTake.booleanValue()){
                    notificationDTO.setMessage("Đơn hàng orderId " + orderId + " thất bại khi sinh Card Code");
                }else{
                    notificationDTO.setMessage("Đơn hàng orderId " + orderId + " thất bại khi sinh Card Code. Không đủ Card Code để sinh");
                }
            }

            notificationService.addItem(notificationDTO);
        }catch (Exception e){
            logger.error("Could not create notification message for Task Take Card Code with status: " + (isSuccess ? "SUCCESS" : "FAILED"));
        }
    }
}
