package com.benluck.vms.mobifonedataseller.webapp.task;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.context.AppContext;
import com.benluck.vms.mobifonedataseller.core.business.OrderManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.PackageDataCodeGenManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.OrderDTO;
import com.benluck.vms.mobifonedataseller.core.dto.PackageDataCodeGenDTO;
import com.benluck.vms.mobifonedataseller.dataCodeGenerator.DataCodeUtil;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

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

    private Long orderId;
    private String unitPriceCode;

    public TaskTakeCardCode(Long orderId, String unitPriceCode) {
        this.orderId = orderId;
        this.unitPriceCode = unitPriceCode;
    }

    @Override
    public void run() {
        logger.info("=================TAKING CARD CODE TASK - Starting...=================");
        logger.info("Getting Order info...");
        boolean hasError = false;
        OrderDTO orderDTO = null;
        try{
            orderDTO = this.orderService.findById(orderId);
            Calendar current = Calendar.getInstance();
            String yearCode = String.valueOf(current.get(Calendar.YEAR)).replace("0","");

            if(orderDTO.getOrderStatus().equals(Constants.ORDER_STATUS_FINISH)){
                PackageDataCodeGenDTO packageDataCodeGenDTO = this.packageDataCodeGenService.findByUniqueCompositeKey(orderDTO.getPackageData().getPackageDataId(), current.get(Calendar.YEAR));

                Object[] cardCodeHSGenerationObject = DataCodeUtil.generateDataCodes(packageDataCodeGenDTO, Calendar.getInstance().get(Calendar.YEAR), yearCode, unitPriceCode, orderDTO.getQuantity());

                if(!Integer.valueOf(cardCodeHSGenerationObject[0].toString()).equals(orderDTO.getQuantity())){
                    throw new Exception("Error when taking Card Code List from Cache. Details: Not matching request size and generated size. ");
                }

                orderDTO.setCardCodeHashSet2Store((HashSet<String>)cardCodeHSGenerationObject[1]);
                orderDTO.setCardCodeProcessStatus(Constants.ORDER_CARD_CODE_COMPLETED_STATUS);

                this.orderService.updateItem(orderDTO);

                DataCodeUtil.updateRemainingCardCodeSize(packageDataCodeGenDTO.getPackageDataCodeGenId(), yearCode, (Map<String, HashSet<String>>)cardCodeHSGenerationObject[2]);
            }else{
                hasError = true;
                logger.error("TAKING CARD CODE TASK is cancelled. The Order is only with status FINISH will be processing for Card Code list.");
            }
        }catch (Exception e){
            hasError = true;
            logger.error("Error happen in TAKING CARD CODE for OrderId: " + orderId);
            logger.error("Details: " + e.getMessage());
        }

        if(hasError && orderDTO != null){
            try{
                orderDTO.setCardCodeProcessStatus(Constants.ORDER_CARD_CODE_FAILED_STATUS);
                this.orderService.updateItem(orderDTO);
            }catch (Exception e){
                logger.error(e.getMessage());
            }
        }

        logger.info("=================TAKING CARD CODE TASK - FINISHED");
    }
}
