package com.benluck.vms.mobifonedataseller.webapp.task;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.context.AppContext;
import com.benluck.vms.mobifonedataseller.core.business.NotificationManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.OrderManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.UsedCardCodeManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.NotificationDTO;
import com.benluck.vms.mobifonedataseller.core.dto.UsedCardCodeDTO;
import com.benluck.vms.mobifonedataseller.core.dto.UserDTO;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.TimerTask;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 12/26/16
 * Time: 13:04
 * To change this template use File | Settings | File Templates.
 */
public class TaskImportOldOrder extends TimerTask{
    private Logger logger = Logger.getLogger(TaskImportOldOrder.class);

    private ApplicationContext ctx = AppContext.getApplicationContext();
    private NotificationManagementLocalBean notificationService = ctx.getBean(NotificationManagementLocalBean.class);
    private OrderManagementLocalBean orderService = ctx.getBean(OrderManagementLocalBean.class);

    private List<UsedCardCodeDTO> importUsedCardCodeList = null;
    private Long userId = null;
    private Long orderId;

    public TaskImportOldOrder(List<UsedCardCodeDTO> importUsedCardCodeList, Long userId, Long orderId) {
        this.importUsedCardCodeList = importUsedCardCodeList;
        this.userId = userId;
        this.orderId = orderId;
    }

    @Override
    public void run() {
        logger.info("================IMPORT USED CARD CODE FOR OLD ORDER TASK================");
        try{
            logger.info("Saving Used Card Code list to Database...");
            orderService.updateOldOrder(orderId, importUsedCardCodeList, Constants.ORDER_STATUS_FINISH);

            createNotification(orderId, userId, true);
        }catch (Exception e){
            logger.error("IMPORT USED CARD CODE FOR OLD ORDER TASK failed!");
            logger.error(e.getMessage());
            createNotification(orderId, userId, false);
        }
        logger.info("================FINISH USED CARD CODE FOR OLD ORDER TASK================");
    }

    private void createNotification(Long orderId, Long userId, Boolean isSuccess){
        try{
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(userId);

            NotificationDTO notificationDTO = new NotificationDTO();
            notificationDTO.setUser(userDTO);
            if(isSuccess){
                notificationDTO.setMessageType(Constants.IMPORT_OLD_ORDER_SUCCESS);
                notificationDTO.setMessage("Nhập đơn hàng cũ thành công cho đơn hàng: " + orderId + ".");
            }else{
                notificationDTO.setMessageType(Constants.IMPORT_OLD_ORDER_FAILED);
                notificationDTO.setMessage("Nhập đơn hàng cũ thất bại cho đơn hàng: " + orderId + ".");
            }

            notificationService.addItem(notificationDTO);
        }catch (Exception e){
            logger.error("Could not create notification message for Task Import Old Order with status: " + (isSuccess ? "SUCCESS" : "FAILED"));
        }
    }
}
