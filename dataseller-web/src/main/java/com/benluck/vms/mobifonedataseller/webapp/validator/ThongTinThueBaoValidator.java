package com.benluck.vms.mobifonedataseller.webapp.validator;

import com.benluck.vms.mobifonedataseller.core.business.SoDiemCTTichDiemManagementlocalBean;
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
public class ThongTinThueBaoValidator extends ApplicationObjectSupport implements Validator{
    @Autowired
    private SoDiemCTTichDiemManagementlocalBean soDiemCTTichDiemManagementlocalBean;

    @Override
    public boolean supports(Class<?> aClass) {
        return ThongTinThueBaoCommand.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ThongTinThueBaoCommand command = (ThongTinThueBaoCommand)o;
        trimmingFields(command);
        validateMobifoneNumber(command, errors);
        checkIfRegistered(command, errors);
    }

    /**
     * Make sure the phoneNumber (thue bao) that has registered the promotion and still work.
     * @param command
     * @param errors
     */
    private void checkIfRegistered(ThongTinThueBaoCommand command, Errors errors) {
        if(StringUtils.isNotBlank(command.getThue_bao())){
            try{
                boolean hasRegister = this.soDiemCTTichDiemManagementlocalBean.checkIfAlreadyRegistered_tdcg(command.getThue_bao());
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
     * Remove white spaces at the start and end of each string.
     * @param command
     */
    private void trimmingFields(ThongTinThueBaoCommand command){
        if(StringUtils.isNotBlank(command.getThue_bao())){
            command.setThue_bao(command.getThue_bao().trim());
        }
    }

    /**
     * Validate format of phoneNumber in the model.
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
