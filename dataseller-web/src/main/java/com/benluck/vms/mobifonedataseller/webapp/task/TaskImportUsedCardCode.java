package com.benluck.vms.mobifonedataseller.webapp.task;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.context.AppContext;
import com.benluck.vms.mobifonedataseller.core.business.NotificationManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.UsedCardCodeManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.NotificationDTO;
import com.benluck.vms.mobifonedataseller.core.dto.UsedCardCodeDTO;
import com.benluck.vms.mobifonedataseller.core.dto.UserDTO;
import com.benluck.vms.mobifonedataseller.util.RedisUtil;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.TimerTask;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 12/19/16
 * Time: 22:52
 * To change this template use File | Settings | File Templates.
 */
public class TaskImportUsedCardCode extends TimerTask{
    private Logger logger = Logger.getLogger(TaskImportUsedCardCode.class);

    private ApplicationContext ctx = AppContext.getApplicationContext();
    private UsedCardCodeManagementLocalBean usedCardCodeService = ctx.getBean(UsedCardCodeManagementLocalBean.class);
    private NotificationManagementLocalBean notificationService = ctx.getBean(NotificationManagementLocalBean.class);

    private List<UsedCardCodeDTO> importUsedCardCodeList = null;
    private Long userId = null;

    public TaskImportUsedCardCode(List<UsedCardCodeDTO> importUsedCardCodeList, Long userId) {
        this.importUsedCardCodeList = importUsedCardCodeList;
        this.userId = userId;
    }

    @Override
    public void run() {
        logger.info("================IMPORT USED CARD CODE TASK================");
        try{
            if (!RedisUtil.pingRedisServer()){
                logger.error("Redis Server is not reached. Please verify!");
                logger.error("Could not finish task IMPORT USED CARD CODE");
            }
            logger.info("Saving Used Card Code list to Database...");
            usedCardCodeService.importCardCodeList(importUsedCardCodeList);
            createNotification(userId, true);

            logger.info("Saving Used Card Code list to Redis Database...");
            RedisUtil.updateUsedCardCodeByKey(usedCardCodeService.findAllListCardCode());

            logger.info("Saving Used Card Code completely!");
        }catch (Exception e){
            logger.error("IMPORT USED CARD CODE TASK failed!");
            logger.error(e.getMessage());
//            tmpImportUsedCardCodeService.deleteAll();
            createNotification(userId, false);
        }
        logger.info("================FINISH IMPORT USED CARD CODE TASK================");
    }

    private void createNotification(Long userId, Boolean isSuccess){
        try{
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(userId);

            NotificationDTO notificationDTO = new NotificationDTO();
            notificationDTO.setUser(userDTO);
            if(isSuccess){
                notificationDTO.setMessageType(Constants.IMPORT_USED_CARD_CODE_SUCCESS);
                notificationDTO.setMessage("Import danh sách Card Code đã sử dụng thành công.");
            }else{
                notificationDTO.setMessageType(Constants.IMPORT_USED_CARD_CODE_FAILED);
                notificationDTO.setMessage("Import danh sách Card Code đã sử dụng thất bại.");
            }

            notificationService.addItem(notificationDTO);
        }catch (Exception e){
            logger.error("Could not create notification message for Task Import Used Card Code with status: " + (isSuccess ? "SUCCESS" : "FAILED"));
        }
    }
}
