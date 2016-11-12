package com.benluck.vms.mobifonedataseller.webapp.task;

import com.benluck.vms.mobifonedataseller.context.AppContext;
import com.benluck.vms.mobifonedataseller.core.business.KHDNManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.ImportKHDNDTO;
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

    private ApplicationContext ctx = AppContext.getApplicationContext();
    private KHDNManagementLocalBean khdnService = ctx.getBean(KHDNManagementLocalBean.class);

    public TaskImportKHDN(List<ImportKHDNDTO> importDTOList) {
        this.importDTOList = importDTOList;
    }

    @Override
    public void run() {
        logger.info("============TASK IMPORT KHDN is starting...============");
        if(importDTOList == null || importDTOList.size() == 0){
            logger.error("Import list is empty. Import task is cancelled.");
        }else{
            try{
                khdnService.importData(importDTOList);
            }catch (Exception e){
                logger.error(e.getMessage());
            }
        }
        logger.info("============Import completed============");
    }
}
