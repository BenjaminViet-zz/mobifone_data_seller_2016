package com.benluck.vms.mobifonedataseller.webapp.validator;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.core.business.MBDCostManagementLocalBean;
import com.benluck.vms.mobifonedataseller.webapp.command.MBDCostCommand;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.ejb.ObjectNotFoundException;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/12/16
 * Time: 23:57
 * To change this template use File | Settings | File Templates.
 */
@Component
public class PaymentManagementValidator extends ApplicationObjectSupport implements Validator{
    private Logger logger = Logger.getLogger(PaymentManagementValidator.class);

    @Autowired
    private MBDCostManagementLocalBean costService;

    @Override
    public boolean supports(Class<?> aClass) {
        return MBDCostCommand.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        MBDCostCommand command = (MBDCostCommand)o;
        checkRequiredList(command);
        checkRequiredPaymentDate(command);
    }

    private void checkRequiredList(MBDCostCommand command){
        if(command.getCheckList() == null || command.getCheckList().length == 0){
            command.setErrorMessage(this.getMessageSourceAccessor().getMessage("code_history.management.no_items_to_update_payment"));
        }else{
            try{
                for(String paymentId : command.getCheckList()){
                    this.costService.findById(Long.valueOf(Long.valueOf(paymentId)));
                }
            }catch (ObjectNotFoundException one){
                command.setErrorMessage(this.getMessageSourceAccessor().getMessage("code_history.management.not_found_payment_item"));
            }
        }
    }

    private void checkRequiredPaymentDate(MBDCostCommand command){
        if(StringUtils.isBlank(command.getErrorMessage())){
            if(command.getPojo().getPaymentDate() == null){
                command.setErrorMessage(this.getMessageSourceAccessor().getMessage("code_history.management.no_payment_date_chosen"));
            }
        }
    }
}
