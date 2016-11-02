package com.benluck.vms.mobifonedataseller.webapp.validator;

import com.benluck.vms.mobifonedataseller.webapp.command.OrderCommand;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import javax.ejb.ObjectNotFoundException;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/2/16
 * Time: 07:15
 * To change this template use File | Settings | File Templates.
 */
@Component
public class OrderValidator extends ApplicationObjectSupport implements Validator{
    private Logger logger = Logger.getLogger(OrderValidator.class);

    @Override
    public boolean supports(Class<?> aClass) {
        return OrderCommand.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        OrderCommand command = (OrderCommand)o;
        checkRequiredFields(command, errors);
    }

    /**
     * Validate required fields to make sure it is enough of required properties to insert or update.
     * @param command
     * @param errors
     */
    private void checkRequiredFields(OrderCommand command, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pojo.packageData.packageDataId", "errors.required", new Object[]{this.getMessageSourceAccessor().getMessage("order.label.package_data")}, "non-empty value required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pojo.khdn.KHDNId", "errors.required", new Object[]{this.getMessageSourceAccessor().getMessage("order.label.khdn")}, "non-empty value required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pojo.quantity", "errors.required", new Object[]{this.getMessageSourceAccessor().getMessage("order.label.quantity")}, "non-empty value required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pojo.unitPrice", "errors.required", new Object[]{this.getMessageSourceAccessor().getMessage("order.label.unit_price")}, "non-empty value required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pojo.status", "errors.required", new Object[]{this.getMessageSourceAccessor().getMessage("order.label.status")}, "non-empty value required.");
    }
}
