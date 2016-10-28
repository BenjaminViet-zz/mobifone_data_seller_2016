package com.benluck.vms.mobifonedataseller.webapp.validator.validator2015;

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
 * User: vovanphuc0810
 * Date: 4/3/15
 * Time: 1:59 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class MaPhieuThueBaoQStudent2015Validator extends ApplicationObjectSupport implements Validator {
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
        validatePhoneNumber(command, errors);
        checkIfRegistered(command, errors);
    }

    /**
     * Validate registration for the Phone Number.
     * @param command
     * @param errors
     */
    private void checkIfRegistered(TicketCommand command, Errors errors) {
        if(StringUtils.isNotBlank(command.getPojo().getThue_bao())){
            try{
                boolean registered = this.soDiemCTTichDiemManagementlocalBean.checkIfAlreadyRegistered_qStudent(command.getPojo().getThue_bao());
                if(!registered){
                    command.setWarning(this.getMessageSourceAccessor().getMessage("cuahanggiaodich.tracuutheothuebao.warning.canceled_or_not_yet_register"));
                }
            }catch (Exception e){
                command.setMessage(this.getMessageSourceAccessor().getMessage("cuahanggiaodich.thongtinthuebao.thue_bao_chua_tham_gia"));
            }
        }
    }

    /**
     * Perform remove white spaces at the start and end of each strings in the Model.
     * @param command
     */
    private void trimmingFields(TicketCommand command){
        if(StringUtils.isNotBlank(command.getPojo().getThue_bao())){
            command.getPojo().setThue_bao(command.getPojo().getThue_bao().trim());
        }
    }

    /**
     * Validate if the Phone Number is valid to Phone format.
     * @param command
     * @param errors
     */
    private void validatePhoneNumber(TicketCommand command, Errors errors){
        if(StringUtils.isNotBlank(command.getPojo().getThue_bao())){
            if(!command.getPojo().getThue_bao().matches("^0{1}\\d{9,10}$")){
                errors.rejectValue("pojo.thue_bao", "cuahanggiaodich.tracuusothuebao.invalid_mobifone_number");
            }
        }
    }
}
