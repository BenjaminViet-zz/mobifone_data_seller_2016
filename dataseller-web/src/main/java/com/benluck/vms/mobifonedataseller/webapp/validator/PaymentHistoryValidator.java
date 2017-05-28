package com.benluck.vms.mobifonedataseller.webapp.validator;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.core.business.PaymentHistoryManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.PaymentManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.PaymentDTO;
import com.benluck.vms.mobifonedataseller.core.dto.PaymentHistoryDTO;
import com.benluck.vms.mobifonedataseller.webapp.command.PaymentHistoryCommand;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import javax.ejb.ObjectNotFoundException;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 5/25/17
 * Time: 23:37
 * To change this template use File | Settings | File Templates.
 */
@Component
public class PaymentHistoryValidator extends ApplicationObjectSupport implements Validator{
    private Logger logger = Logger.getLogger(PaymentHistoryValidator.class);

    @Autowired
    private PaymentManagementLocalBean paymentService;
    @Autowired
    private PaymentHistoryManagementLocalBean paymentHistoryService;

    @Override
    public boolean supports(Class<?> aClass) {
        return PaymentHistoryCommand.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        PaymentHistoryCommand command = (PaymentHistoryCommand)o;

        String crudaction = command.getCrudaction();

        if (StringUtils.isNotBlank(crudaction)){
            if (crudaction.equals(Constants.ACTION_UPDATE)){
                checkPaymentStatus(command);
                checkRequiredFields(command, errors);
            }else if (crudaction.equals(Constants.ACTION_DELETE)){
                checkPaymentStatus(command);
            }
        }else{
            PaymentHistoryDTO pojo = command.getPojo();
            if (pojo.getPaymentHistoryId() != null){
                try{
                    PaymentHistoryDTO paymentHistoryDTO = this.paymentHistoryService.findById(pojo.getPaymentHistoryId());
                    if (paymentHistoryDTO.getPayment().getStatus().equals(Constants.PAYMENT_STATUS_PAID)){
                        command.setErrorMessage(this.getMessageSourceAccessor().getMessage("payment_history.management.edit_page.msg.can_not_update_4_paid_payment"));
                    }
                }catch (Exception e){
                    logger.error(e.getMessage());
                    command.setErrorMessage(this.getMessageSourceAccessor().getMessage("payment_history.management.msg.require_payment_historyId"));
                }
            }else{
                command.setErrorMessage(this.getMessageSourceAccessor().getMessage("payment_history.management.msg.require_payment_historyId"));
            }
        }
    }

    private void checkPaymentStatus(PaymentHistoryCommand command) {
        if (command.getPojo().getPayment() != null && command.getPojo().getPayment().getPaymentId() != null){
            try{
                PaymentDTO paymentDTO = this.paymentService.findById(command.getPojo().getPayment().getPaymentId());

                if (paymentDTO.getStatus().equals(Constants.PAYMENT_STATUS_PAID)){
                    String crudaction = command.getCrudaction();
                    if (StringUtils.isNotBlank(crudaction)){
                        if (crudaction.equals(Constants.ACTION_UPDATE)){
                            command.setErrorMessage(this.getMessageSourceAccessor().getMessage("payment_history.management.edit_page.msg.can_not_update_4_paid_payment"));
                        }else if (crudaction.equals(Constants.ACTION_DELETE)){
                            command.setErrorMessage(this.getMessageSourceAccessor().getMessage("payment_history.management.edit_page.msg.can_not_delete_4_paid_payment"));
                        }
                    }
                }
            }catch (ObjectNotFoundException one){
                logger.error(one.getMessage());
                command.setErrorMessage(this.getMessageSourceAccessor().getMessage("payment.management.msg.not_found_payment", new Object[]{command.getPojo().getPayment().getPaymentId()}));
            }
        }
    }

    private void checkRequiredFields(PaymentHistoryCommand command, Errors errors){
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pojo.amount", "errors.required", new Object[]{this.getMessageSourceAccessor().getMessage("pojo.amount")}, "non-empty value required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pojo.paymentDate", "errors.required", new Object[]{this.getMessageSourceAccessor().getMessage("pojo.paymentDate")}, "non-empty value required.");
    }
}
