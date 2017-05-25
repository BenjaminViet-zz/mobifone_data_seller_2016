package com.benluck.vms.mobifonedataseller.webapp.validator;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.core.business.PaymentManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.PaymentDTO;
import com.benluck.vms.mobifonedataseller.webapp.command.PaymentCommand;
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
public class PaymentValidator extends ApplicationObjectSupport implements Validator{
    private Logger logger = Logger.getLogger(PaymentValidator.class);

    @Autowired
    private PaymentManagementLocalBean paymentService;

    @Override
    public boolean supports(Class<?> aClass) {
        return PaymentCommand.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        PaymentCommand command = (PaymentCommand)o;

        String crudaction = command.getCrudaction();

        if (StringUtils.isNotBlank(crudaction)){
            if (crudaction.equals("insert-update")){

                if (command.getPojo().getPaymentId() != null){
                    checkPaymentStatus(command);
                }

                if (StringUtils.isBlank(command.getErrorMessage())){
                    checkRequiredFields(command, errors);
                }
            }else if (crudaction.equals(Constants.ACTION_DELETE)){
                checkPaymentStatus(command);
            }
        }
    }

    private void checkPaymentStatus(PaymentCommand command) {
        if (command.getPojo().getPaymentId() == null){
            command.setErrorMessage(this.getMessageSourceAccessor().getMessage("payment.management.msg.empty_paymentId_to_delete"));
        }else{
            try{
                PaymentDTO paymentDTO = this.paymentService.findById(command.getPojo().getPaymentId());
                if (paymentDTO.getStatus().equals(Constants.PAYMENT_STATUS_PAID)){
                    command.setErrorMessage(this.getMessageSourceAccessor().getMessage("payment.management.msg.can_not_delete_finish_payment"));
                }
            }catch (ObjectNotFoundException one){
                logger.error(one.getMessage());
                command.setErrorMessage(this.getMessageSourceAccessor().getMessage("payment.management.msg.not_found_payment", new Object[]{command.getPojo().getPaymentId()}));
            }
        }
    }

    private void checkRequiredFields(PaymentCommand command, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pojo.khdn.khdnId", "errors.required", new Object[]{this.getMessageSourceAccessor().getMessage("pojo.khdn.khdnId")}, "non-empty value required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pojo.order.orderId", "errors.required", new Object[]{this.getMessageSourceAccessor().getMessage("pojo.order.orderId")}, "non-empty value required.");
    }
}
