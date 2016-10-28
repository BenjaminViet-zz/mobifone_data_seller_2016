package com.benluck.vms.mobifonedataseller.webapp.validator;

import com.benluck.vms.mobifonedataseller.webapp.command.command2014.GiftDeliveryAgentHistoryCommand;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/15/14
 * Time: 10:07 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class GiftDeliveryAgentHistoryValidator extends ApplicationObjectSupport implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return GiftDeliveryAgentHistoryCommand.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        GiftDeliveryAgentHistoryCommand command = (GiftDeliveryAgentHistoryCommand)o;
        trimmingFields(command);
        validateQuantity(command, errors);
    }

    /**
     * Remove white spaces at the start and end of each string in the model.
     * @param command
     */
    private void trimmingFields(GiftDeliveryAgentHistoryCommand command){
        if(StringUtils.isNotBlank(command.getPojo().getTenNguoiGiaoHang())){
            command.getPojo().setTenNguoiGiaoHang(command.getPojo().getTenNguoiGiaoHang().trim());
        }
    }

    /**
     * Validate validity of warehouse quantity.
     * @param command
     * @param errors
     */
    private void validateQuantity(GiftDeliveryAgentHistoryCommand command, Errors errors){
        if(command.getPojo().getQuantity() == null || command.getPojo().getQuantity().equals(0)){
            errors.rejectValue("pojo.quantity", "stockagent.msg.quantity_not_empty");
        }
        if(StringUtils.isBlank(command.getPojo().getTenNguoiGiaoHang())){
            errors.reject("pojo.tenNguoiGiaoHang", "stockagent.msg.ten_nguoi_giao_hang_not_empty");
        }
    }
}
