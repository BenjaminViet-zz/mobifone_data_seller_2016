package com.benluck.vms.mobifonedataseller.webapp.validator.validator2015;

import com.benluck.vms.mobifonedataseller.core.business.QTeenQStudent2015ManagementLocalBean;
import com.benluck.vms.mobifonedataseller.webapp.command.command2014.ThongTinThueBaoCommand;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/16/14
 * Time: 10:24 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class ThongTinThueBaoQStudentQTeenValidator extends ApplicationObjectSupport implements Validator{
    @Autowired
    private QTeenQStudent2015ManagementLocalBean qTeenQStudent2015ManagementLocalBean;

    @Override
    public boolean supports(Class<?> aClass) {
        return ThongTinThueBaoCommand.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ThongTinThueBaoCommand command = (ThongTinThueBaoCommand)o;
        trimmingFields(command);
        validateMobifoneNumber(command, errors);
        checkIfRegitered(command, errors);
    }

    private void checkIfRegitered(ThongTinThueBaoCommand command, Errors errors) {
        if(StringUtils.isNotBlank(command.getThue_bao())){
            try{
                boolean hasRegister = this.qTeenQStudent2015ManagementLocalBean.checkRegister(command.getThue_bao());
                if(!hasRegister){
                    command.setWarning(this.getMessageSourceAccessor().getMessage("cuahanggiaodich.thongtinthuebao.thue_bao_chua_tham_gia"));
//                    command.setMessage(this.getMessageSourceAccessor().getMessage("cuahanggiaodich.thongtinthuebao.thue_bao_da_huy_tham_gia"));
                }
            }catch (Exception e){
                command.setMessage(this.getMessageSourceAccessor().getMessage("cuahanggiaodich.thongtinthuebao.thue_bao_chua_tham_gia"));
            }
        }
    }

    /**
     * Remove white spaces at the start and end of each string in model.
     * @param command
     */
    private void trimmingFields(ThongTinThueBaoCommand command){
        if(StringUtils.isNotBlank(command.getThue_bao())){
            command.setThue_bao(command.getThue_bao().trim());
        }
    }

    /**
     * Validate validity of Phone Number. Attach error message to the POJO's attribute and bind to the Form of web page.
     * @param command
     * @param errors
     */
    private void validateMobifoneNumber(ThongTinThueBaoCommand command, Errors errors){
        if(StringUtils.isNotBlank(command.getThue_bao())){
            if(!command.getThue_bao().matches("^0{1}\\d{9,10}$")){
                errors.rejectValue("thue_bao", "cuahanggiaodich.tracuusothuebao.invalid_mobifone_number");
            }
        }
    }
}
