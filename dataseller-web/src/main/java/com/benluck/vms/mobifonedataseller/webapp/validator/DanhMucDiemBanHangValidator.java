package com.benluck.vms.mobifonedataseller.webapp.validator;

import com.benluck.vms.mobifonedataseller.webapp.command.command2015.DanhMucBanHangCommand;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created with IntelliJ IDEA.
 * User: HP
 * Date: 3/20/15
 * Time: 4:56 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class DanhMucDiemBanHangValidator extends ApplicationObjectSupport implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return DanhMucBanHangCommand.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        DanhMucBanHangCommand command = (DanhMucBanHangCommand)o;
        trimmingFields(command);
        validateMobifoneNumber(command, errors);
    }

    /**
     * Remove white spaces at the start and end of each string in the model.
     * @param command
     */
    private void trimmingFields(DanhMucBanHangCommand command){
        if(StringUtils.isNotBlank(command.getPojo().getEz_isdn())){
            command.getPojo().setEz_isdn(command.getPojo().getEz_isdn().trim());
        }
    }

    /**
     * Validate format of phone number string in the model.
     * @param command
     * @param errors
     */
    private void validateMobifoneNumber(DanhMucBanHangCommand command, Errors errors){
        if(StringUtils.isNotBlank(command.getPojo().getEz_isdn())){
            if(!command.getPojo().getEz_isdn().matches("^0{1}\\d{9,10}$")){
                errors.rejectValue("pojo.ez_isdn", "user.msg.invalid_mobifone_number");
            }
        }
    }
}
