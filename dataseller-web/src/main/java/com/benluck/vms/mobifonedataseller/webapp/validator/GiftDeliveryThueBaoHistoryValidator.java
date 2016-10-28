package com.benluck.vms.mobifonedataseller.webapp.validator;

import com.benluck.vms.mobifonedataseller.webapp.command.command2014.GiftDeliveryThueBaoHistoryCommand;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/17/14
 * Time: 10:20 AM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class GiftDeliveryThueBaoHistoryValidator extends ApplicationObjectSupport implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return GiftDeliveryThueBaoHistoryCommand.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        GiftDeliveryThueBaoHistoryCommand command = (GiftDeliveryThueBaoHistoryCommand)o;
        trimmingFields(command);
        validateMobifoneNumber(command, errors);
    }

    /**
     * Remove white spaces at the start and end of each string in the model.
     * @param command
     */
    private void trimmingFields(GiftDeliveryThueBaoHistoryCommand command){
        if(StringUtils.isNotBlank(command.getPojo().getGiftDeliveryThueBao().getThueBao())){
            command.getPojo().getGiftDeliveryThueBao().setThueBao(command.getPojo().getGiftDeliveryThueBao().getThueBao().trim());
        }
    }

    /**
     * Validate format of the Phone Number in the model.
     * @param command
     * @param errors
     */
    private void validateMobifoneNumber(GiftDeliveryThueBaoHistoryCommand command, Errors errors){
        if(StringUtils.isNotBlank(command.getPojo().getGiftDeliveryThueBao().getThueBao())){
            if(!command.getPojo().getGiftDeliveryThueBao().getThueBao().matches("^0{1}\\d{9,10}$")){
                errors.rejectValue("pojo.giftDeliveryThueBao.thueBao", "cuahanggiaodich.tracuusothuebao.invalid_mobifone_number");
            }
        }
    }
}
