package com.benluck.vms.mobifonedataseller.webapp.validator;


import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.core.business.UserManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.UserDTO;
import com.benluck.vms.mobifonedataseller.webapp.command.UserCommand;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
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
    private Logger logger = Logger.getLogger(UserValidator.class);

    @Autowired
    private UserManagementLocalBean userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return UserCommand.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserCommand command = (UserCommand)o;
        String action = command.getCrudaction();

        if(action.equals(Constants.ACTION_DELETE)){
            validateDeleteSystemUser(command);
        }else{
            trimmingField(command);
            checkRequiredFields(command, errors);
            checkUniqueCode(command, errors);
        }
    }

    private void validateDeleteSystemUser(UserCommand command){
        UserDTO pojo = null;
        try{
            pojo = this.userService.findById(command.getPojo().getUserId());
            if(pojo.getUserGroup().getCode().equals(Constants.USERGROUP_ADMIN) && pojo.getUserName().equalsIgnoreCase("admin")){
                command.setErrorMessage(this.getMessageSourceAccessor().getMessage("user.not_allow_delete_system_user"));
            }
        }catch (ObjectNotFoundException one){
            command.setErrorMessage(this.getMessageSourceAccessor().getMessage("user.not_allow_this_user_to_delete"));
        }
    }

    /**
     * Validate required fields to make sure it is enough of required properties to insert or update.
     * @param command
     * @param errors
     */
    private void checkRequiredFields(UserCommand command, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pojo.userType.userTypeId", "errors.required", new Object[]{this.getMessageSourceAccessor().getMessage("pojo.userType.userTypeId")}, "non-empty value required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pojo.userGroup.userGroupId", "errors.required", new Object[]{this.getMessageSourceAccessor().getMessage("userGroup.userGroupId")}, "non-empty value required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pojo.status", "errors.required", new Object[]{this.getMessageSourceAccessor().getMessage("userGroup.status")}, "non-empty value required.");

        if(command.getPojo().getUserId() != null){
            if(command.getPojo().getUserType().getCode().equals(Constants.USER_TYPE_KHDN)){
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pojo.userName", "errors.required", new Object[]{this.getMessageSourceAccessor().getMessage("pojo.userName")}, "non-empty value required.");
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pojo.password", "errors.required", new Object[]{this.getMessageSourceAccessor().getMessage("pojo.password")}, "non-empty value required.");

                if(command.getPojo().getUserType() != null && command.getPojo().getUserType().getCode().equals(Constants.USER_TYPE_KHDN)){
                    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pojo.isdn", "errors.required", new Object[]{this.getMessageSourceAccessor().getMessage("pojo.isdn")}, "non-empty value required.");
                }
            }
        }else{
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pojo.userName", "errors.required", new Object[]{this.getMessageSourceAccessor().getMessage("pojo.userName")}, "non-empty value required.");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pojo.password", "errors.required", new Object[]{this.getMessageSourceAccessor().getMessage("pojo.password")}, "non-empty value required.");

            if(command.getPojo().getUserType() != null && command.getPojo().getUserType().getCode().equals(Constants.USER_TYPE_KHDN)){
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pojo.isdn", "errors.required", new Object[]{this.getMessageSourceAccessor().getMessage("pojo.isdn")}, "non-empty value required.");
            }
        }
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
        if(StringUtils.isNotBlank(command.getPojo().getIsdn())){
            command.getPojo().setIsdn(command.getPojo().getIsdn().trim());
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
                UserDTO dto = userService.findByUsername(command.getPojo().getUserName());
                if(command.getPojo().getUserId() != null){
                    if(!dto.getUserId().equals(command.getPojo().getUserId())){
                        errors.rejectValue("pojo.userName", "label.user_name.errors_duplicated");
                    }
                }else{
                    if(dto != null){
                        errors.rejectValue("pojo.userName", "label.user_name.errors_duplicated");
                    }
                }
            }catch (ObjectNotFoundException one){}
        }
    }
}
