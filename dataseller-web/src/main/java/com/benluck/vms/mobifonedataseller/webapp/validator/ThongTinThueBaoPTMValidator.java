package com.benluck.vms.mobifonedataseller.webapp.validator;

import com.benluck.vms.mobifonedataseller.webapp.command.command2014.ThongTinThueBaoCommand;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created with IntelliJ IDEA.
 * User: HP
 * Date: 3/2/15
 * Time: 9:23 AM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class ThongTinThueBaoPTMValidator extends ApplicationObjectSupport implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return ThongTinThueBaoCommand.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ThongTinThueBaoCommand command = (ThongTinThueBaoCommand)o;
        trimmingFields(command);
        validatePhoneNumber(command, errors);
    }

    /**
     * Remove white spaces at the start and end of each string in the model.
     * @param command
     */
    private void trimmingFields(ThongTinThueBaoCommand command){
        if(StringUtils.isNotBlank(command.getThue_bao())){
            command.setThue_bao(command.getThue_bao().trim());
        }
    }

    /**
     * Validate format for phoneNumber (thue bao) in the model.
     * @param command
     * @param errors
     */
    private void validatePhoneNumber(ThongTinThueBaoCommand command, Errors errors){
        if(StringUtils.isNotBlank(command.getThue_bao())){
            if(!command.getThue_bao().matches("^0{1}\\d{9,10}$")){
                errors.rejectValue("thue_bao", "cuahanggiaodich.tracuusothuebao.invalid_mobifone_number");
            }
        }
    }
}
