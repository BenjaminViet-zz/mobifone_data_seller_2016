package com.benluck.vms.mobifonedataseller.webapp.validator;

import com.benluck.vms.mobifonedataseller.webapp.command.command2014.BaoCaoTongHopKetQuaChuongTrinhKPPTheoDaiLyCommand;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 11/16/14
 * Time: 9:02 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class BaoCaoTongHopKetQuaChuongTrinhKPPTheoDaiLyValidator extends ApplicationObjectSupport implements Validator{
    @Override
    public boolean supports(Class<?> aClass) {
        return BaoCaoTongHopKetQuaChuongTrinhKPPTheoDaiLyCommand.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        BaoCaoTongHopKetQuaChuongTrinhKPPTheoDaiLyCommand command = (BaoCaoTongHopKetQuaChuongTrinhKPPTheoDaiLyCommand)o;
        trimmingFields(command);
        validateMobifoneNumber(command, errors);
    }

    /**
     * Remove white spaces at the start and end of each string in the model.
     * @param command
     */
    private void trimmingFields(BaoCaoTongHopKetQuaChuongTrinhKPPTheoDaiLyCommand command){
        if(StringUtils.isNotBlank(command.getSoEZ())){
            command.setSoEZ(command.getSoEZ().trim());
        }
    }

    /**
     * Validate format of the Phone Number in the model.
     * @param command
     * @param errors
     */
    private void validateMobifoneNumber(BaoCaoTongHopKetQuaChuongTrinhKPPTheoDaiLyCommand command, Errors errors){
            if(StringUtils.isNotBlank(command.getSoEZ())){
            if(!command.getSoEZ().matches("^0{1}\\d{9,10}$")){
                errors.rejectValue("soEZ", "admin.baocaotonghopchuongtrinhkpp.msg.invalid_soEZ");
            }
        }
    }
}
