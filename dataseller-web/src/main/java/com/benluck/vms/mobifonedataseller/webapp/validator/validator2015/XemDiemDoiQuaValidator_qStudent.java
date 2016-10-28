package com.benluck.vms.mobifonedataseller.webapp.validator.validator2015;

import com.benluck.vms.mobifonedataseller.common.utils.CommonUtil;
import com.benluck.vms.mobifonedataseller.core.business.SoDiemCTTichDiemManagementlocalBean;
import com.benluck.vms.mobifonedataseller.webapp.command.command2014.UserCommand;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/13/14
 * Time: 10:50 AM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class XemDiemDoiQuaValidator_qStudent extends ApplicationObjectSupport implements Validator {
    private transient final Log log = LogFactory.getLog(getClass());

    @Autowired
    private SoDiemCTTichDiemManagementlocalBean soDiemCTTichDiemManagementlocalBean;

    @Override
    public boolean supports(Class<?> aClass) {
        return UserCommand.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserCommand command = (UserCommand)o;
        trimmingFields(command);
        checkRequiredFields(command, errors);
        validateMobifoneNumber(command, errors);
        if(!errors.hasErrors()){
            checkIfAlreadyRegister(command, errors);
        }
    }

    /**
     * Remove white spaces at the start and end of each string in the model.
     * @param command
     */
    private void trimmingFields(UserCommand command){
        if(StringUtils.isNotBlank(command.getPojo().getUserName())){
            command.getPojo().setUserName(command.getPojo().getUserName().trim());
        }
    }

    /**
     * validate the required fields which need to be not empty in the model.
     * @param command
     * @param errors
     */
    private void checkRequiredFields(UserCommand command, Errors errors){
        if(StringUtils.isBlank(command.getPojo().getUserName())){
            command.setMessage(this.getMessageSourceAccessor().getMessage("user.user_name.required"));
        }
    }

    /**
     * Validate format of the phone number in the model.
     * @param command
     * @param errors
     */
    private void validateMobifoneNumber(UserCommand command, Errors errors){
        if(StringUtils.isNotBlank(command.getPojo().getUserName())){
            if(!CommonUtil.isMobifoneNumber(command.getPojo().getUserName())){
                command.setMessage(this.getMessageSourceAccessor().getMessage("user.msg.invalid_mobifone_number"));
            }
        }
    }

    /**
     * Validate before proceed to get data for Gif Exchange.
     * @param command
     * @param errors
     */
    private void checkIfAlreadyRegister(UserCommand command, Errors errors){
        if(StringUtils.isBlank(command.getMessage())){
            if(StringUtils.isNotBlank(command.getPojo().getUserName())){
                try{
                    boolean hasRegister = this.soDiemCTTichDiemManagementlocalBean.checkIfAlreadyRegistered_qStudent(command.getPojo().getUserName());
                    if(!hasRegister){
                        command.setMessage(this.getMessageSourceAccessor().getMessage("user.msg.not_yet_register"));
                    }
                }catch (Exception e){
                    command.setMessage(this.getMessageSourceAccessor().getMessage("user.msg.not_yet_register"));
                }
            }
        }
    }
}
