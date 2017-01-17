package com.benluck.vms.mobifonedataseller.webapp.task;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.context.AppContext;
import com.benluck.vms.mobifonedataseller.core.business.KHDNManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.NotificationManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.ImportKHDNDTO;
import com.benluck.vms.mobifonedataseller.core.dto.NotificationDTO;
import com.benluck.vms.mobifonedataseller.core.dto.UserDTO;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.TimerTask;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/11/16
 * Time: 21:56
 * To change this template use File | Settings | File Templates.
 */
public class TaskImportKHDN extends TimerTask{
    private Logger logger = Logger.getLogger(TaskImportKHDN.class);

    private List<ImportKHDNDTO> importDTOList;
    private Long userId;

    private ApplicationContext ctx = AppContext.getApplicationContext();
    private KHDNManagementLocalBean khdnService = ctx.getBean(KHDNManagementLocalBean.class);
    private NotificationManagementLocalBean notificationService = ctx.getBean(NotificationManagementLocalBean.class);

    public TaskImportKHDN(Long createdById, List<ImportKHDNDTO> importDTOList) {
        this.userId = createdById;
        this.importDTOList = importDTOList;
    }

    @Override
    public void run() {
        logger.info("============TASK IMPORT KHDN is starting...============");
        if(importDTOList == null || importDTOList.size() == 0){
            logger.error("Import list is empty. Import task is cancelled.");
        }else{
            try{
                logger.info("Importing KHDN list to Database");
                khdnService.importData(importDTOList);

                logger.info("Creating notification for importing completely.");
                createNotificationMessage(true);

            }catch (Exception e){
                logger.error(e.getMessage());
                logger.info("Creating notification for importing failed.");
                createNotificationMessage(false);
            }
        }
        logger.info("============Import completed============");
    }

    private void createNotificationMessage(Boolean isSuccess){
        try{
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(this.userId);

            NotificationDTO notificationDTO = new NotificationDTO();
            notificationDTO.setUser(userDTO);

            if(isSuccess){
                notificationDTO.setMessageType(Constants.IMPORT_KHDN_SUCCESS);
                notificationDTO.setMessage("Import danh sách KHDN hoàn tất." );
            }else{
                notificationDTO.setMessageType(Constants.IMPORT_KHDN_FAILED);
                notificationDTO.setMessage("Import danh sách KHDN thất bại." );
            }

            notificationService.addItem(notificationDTO);
        }catch (Exception e){
            logger.error("Could not create notification message for Task Import KHDN with status: " + (isSuccess ? "SUCCESS" : "FAILED"));
        }
    }
}
