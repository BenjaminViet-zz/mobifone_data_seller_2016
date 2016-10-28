package com.benluck.vms.mobifonedataseller.webapp.validator;

import com.benluck.vms.mobifonedataseller.core.business.SoDiemCTTichDiemManagementlocalBean;
import com.benluck.vms.mobifonedataseller.webapp.command.command2014.TicketCommand;
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
public class MaPhieuThueBaoValidator extends ApplicationObjectSupport implements Validator{
    @Autowired
    private SoDiemCTTichDiemManagementlocalBean soDiemCTTichDiemManagementlocalBean;

    @Override
    public boolean supports(Class<?> aClass) {
        return TicketCommand.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        TicketCommand command = (TicketCommand)o;
        trimmingFields(command);
        validateMobifoneNumber(command, errors);
        checkIfRegistered(command, errors);
    }

    /**
     * Verify the phone number (thue bao) to make sure this has registered the promotion and still work.
     * @param command
     * @param errors
     */
    private void checkIfRegistered(TicketCommand command, Errors errors) {
        if(StringUtils.isNotBlank(command.getPojo().getThue_bao())){
            try{
                boolean hasRegister = this.soDiemCTTichDiemManagementlocalBean.checkIfAlreadyRegistered_tdcg(command.getPojo().getThue_bao());
                if(!hasRegister){
                    command.setWarning(this.getMessageSourceAccessor().getMessage("cuahanggiaodich.tracuutheothuebao.warning.canceled_or_not_yet_register"));
//                    command.setMessage(this.getMessageSourceAccessor().getMessage("cuahanggiaodich.thongtinthuebao.thue_bao_da_huy_tham_gia"));
                }
            }catch (Exception e){
                command.setMessage(this.getMessageSourceAccessor().getMessage("cuahanggiaodich.thongtinthuebao.thue_bao_chua_tham_gia"));
            }
        }
    }

    /**
     * Remove white spaces at the start and end of each string in the model.
     * @param command
     */
    private void trimmingFields(TicketCommand command){
        if(StringUtils.isNotBlank(command.getPojo().getThue_bao())){
            command.getPojo().setThue_bao(command.getPojo().getThue_bao().trim());
        }
    }

    /**
     * Validate format of phone number in the model.
     * @param command
     * @param errors
     */
    private void validateMobifoneNumber(TicketCommand command, Errors errors){
        if(StringUtils.isNotBlank(command.getPojo().getThue_bao())){
            if(!command.getPojo().getThue_bao().matches("^0{1}\\d{9,10}$")){
                errors.rejectValue("pojo.thue_bao", "cuahanggiaodich.tracuusothuebao.invalid_mobifone_number");
            }
        }
    }
}
