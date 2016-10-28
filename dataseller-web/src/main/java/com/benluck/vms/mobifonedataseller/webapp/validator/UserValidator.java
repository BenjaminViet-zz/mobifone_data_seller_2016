package com.benluck.vms.mobifonedataseller.webapp.validator;


import com.benluck.vms.mobifonedataseller.common.utils.CommonUtil;
import com.benluck.vms.mobifonedataseller.core.business.UserManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.UserDTO;
import com.benluck.vms.mobifonedataseller.webapp.command.command2014.UserCommand;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import javax.ejb.ObjectNotFoundException;

/**
 * Created with IntelliJ IDEA.
 * User: hau
 * Date: 11/2/13
 * Time: 4:02 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class UserValidator extends ApplicationObjectSupport implements Validator {
    private transient final Log log = LogFactory.getLog(getClass());
    @Autowired
    private UserManagementLocalBean managementLocalBean;

    @Override
    public boolean supports(Class<?> aClass) {
        return UserCommand.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserCommand command = (UserCommand)o;
        trimmingField(command);
        checkRequiredFields(command, errors);
        validateEmail(command, errors);
        validatePhoneNumber(command, errors);
        checkUniqueCode(command, errors);
    }

    /**
     * Validate required fields to make sure it is enough of required properties to insert or update.
     * @param command
     * @param errors
     */
    private void checkRequiredFields(UserCommand command, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pojo.userName", "errors.required", new Object[]{this.getMessageSourceAccessor().getMessage("label.user_name")}, "non-empty value required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pojo.password", "errors.required", new Object[]{this.getMessageSourceAccessor().getMessage("user.label.password")}, "non-empty value required.");
    }

    /**
     * Remove white spaces at the start and end of each string in the model.
     * @param command
     */
    private void trimmingField(UserCommand command) {
        if(StringUtils.isNotBlank(command.getPojo().getUserName())){
            command.getPojo().setUserName(command.getPojo().getUserName().trim());
        }
        if(StringUtils.isNotBlank(command.getPojo().getDisplayName())){
            command.getPojo().setDisplayName(command.getPojo().getDisplayName().trim());
        }
        if(StringUtils.isNotBlank(command.getPojo().getPassword())){
            command.getPojo().setPassword(command.getPojo().getPassword().trim());
        }
        if(StringUtils.isNotBlank(command.getPojo().getEmail())){
            command.getPojo().setEmail(command.getPojo().getEmail().trim());
        }
        if(StringUtils.isNotBlank(command.getPojo().getMobileNumber())){
            command.getPojo().setMobileNumber(command.getPojo().getMobileNumber().trim());
        }
    }

    /**
     * Validate unique code to make sure it is only one exists in DB.
     * @param command
     * @param errors
     */
    private void checkUniqueCode(UserCommand command, Errors errors) {
        if(StringUtils.isNotBlank(command.getPojo().getUserName())){
            try{
                UserDTO dto = managementLocalBean.findByCode(command.getPojo().getUserName());
                if(command.getPojo().getUserId() != null){
                    if(!dto.getUserId().equals(command.getPojo().getUserId())){
                        errors.rejectValue("pojo.userName", "label.user_name.errors_duplicated");
                    }
                }else{
                    if(dto != null){
                        errors.rejectValue("pojo.userName", "label.user_name.errors_duplicated");
                    }
                }
            }catch (ObjectNotFoundException oe){}
        }

        if(StringUtils.isNotBlank(command.getPojo().getEmail())){
            try{
                UserDTO dto1 = this.managementLocalBean.findByEmail(command.getPojo().getEmail());
                if(command.getPojo().getUserId() != null){
                    if(!command.getPojo().getEmail().equals(dto1.getEmail())){
                        errors.rejectValue("pojo.email", "label.email.errors_duplicated");
                    }
                }else{
                    errors.rejectValue("pojo.email", "label.email.errors_duplicated");
                }
            }catch (ObjectNotFoundException oe){}
        }

        if(StringUtils.isNotBlank(command.getPojo().getMobileNumber())){
            try{
                UserDTO dto = managementLocalBean.findByMobileNumber(command.getPojo().getMobileNumber());
                if(command.getPojo().getUserId() != null){
                    if(!dto.getUserId().equals(command.getPojo().getUserId())){
                        errors.rejectValue("pojo.mobileNumber", "label.phone_number.errors_duplicated");
                    }
                }else{
                    if(dto != null){
                        errors.rejectValue("pojo.mobileNumber", "label.phone_number.errors_duplicated");
                    }
                }
            }catch (ObjectNotFoundException oe){}
        }
    }

    /**
     * Validate format of the email in the model for CRUD actions.
     * @param command
     * @param errors
     */
    private void validateEmail(UserCommand command, Errors errors) {
        if(StringUtils.isNotBlank(command.getPojo().getEmail())){
            if(!CommonUtil.isValidEmail(command.getPojo().getEmail())){
                errors.rejectValue("pojo.email", "admin.edit_user.invalid_email");
            }
        }
    }

    /**
     * Validate format of phone number in the model.
     * @param command
     * @param errors
     */
    private void validatePhoneNumber(UserCommand command, Errors errors) {
        if(StringUtils.isNotBlank(command.getPojo().getMobileNumber())){
            if(!command.getPojo().getMobileNumber().matches("^0{1}\\d{9,10}$")){
                errors.rejectValue("pojo.mobileNumber", "admin.edit_user.invalid_tel");
            }
        }
    }
}
