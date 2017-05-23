package com.benluck.vms.mobifonedataseller.webapp.validator;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.core.business.UserGroupManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.UserGroupDTO;
import com.benluck.vms.mobifonedataseller.webapp.command.UserGroupCommand;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import javax.ejb.ObjectNotFoundException;

@Component
public class UserGroupValidator extends ApplicationObjectSupport implements Validator {
    private transient final Logger logger = Logger.getLogger(UserGroupValidator.class);
    @Autowired
    private UserGroupManagementLocalBean userGroupService;

    @Override
    public boolean supports(Class<?> aClass) {
        return UserGroupCommand.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserGroupCommand command = (UserGroupCommand)o;
        String action = command.getCrudaction();
        if(action.equals(Constants.ACTION_DELETE)){
            validateDeleteSystemUserGroup(command);
        }else{
            UserGroupDTO dbUserGroupDTO = fetchDBUserGroupById(command);

            trimmingField(command);
            checkRequiredFields(command, errors, dbUserGroupDTO);
            checkUniqueCode(command, errors);
            checkWhiteSpaceCode(command, errors);
        }
    }

    private UserGroupDTO fetchDBUserGroupById(UserGroupCommand command) {
        if (StringUtils.isBlank(command.getErrorMessage())){
            UserGroupDTO pojo = command.getPojo();
            if (pojo.getUserGroupId() != null){
                try{
                    return this.userGroupService.findById(command.getPojo().getUserGroupId());
                }catch (ObjectNotFoundException one){
                    logger.error(one.getMessage());
                    command.setErrorMessage(this.getMessageSourceAccessor().getMessage("usergroup.usergroup_does_not_exsist_in_system"));
                }
            }
        }
        return null;
    }


    private void validateDeleteSystemUserGroup(UserGroupCommand command){
        UserGroupDTO pojo = null;
        try{
            pojo = this.userGroupService.findById(command.getPojo().getUserGroupId());
            if(pojo.getCode().equals(Constants.USERGROUP_ADMIN)){
                command.setErrorMessage(this.getMessageSourceAccessor().getMessage("usergroup.not_allow_delete_system_usergroup"));
            }
        }catch (ObjectNotFoundException one){
            command.setErrorMessage(this.getMessageSourceAccessor().getMessage("usergroup.not_allow_this_usergroup_to_delete"));
        }
    }

    /**
     * Validate required fields against to CRUD actions.
     * @param command
     * @param errors
     */
    private void checkRequiredFields(UserGroupCommand command, Errors errors, UserGroupDTO dbUserGroupDTO) {
        if (dbUserGroupDTO != null && dbUserGroupDTO.getCode().equals(Constants.USERGROUP_ADMIN)){
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pojo.description", "errors.required", new Object[]{this.getMessageSourceAccessor().getMessage("pojo.description")}, "non-empty value required.");
        }else{
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pojo.code", "errors.required", new Object[]{this.getMessageSourceAccessor().getMessage("pojo.code")}, "non-empty value required.");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pojo.description", "errors.required", new Object[]{this.getMessageSourceAccessor().getMessage("pojo.description")}, "non-empty value required.");
        }

    }

    /**
     * Remove white spaces at the start and end of each string in the model.
     * @param command
     */
    private void trimmingField(UserGroupCommand command) {
        if(StringUtils.isNotBlank(command.getPojo().getCode())){
            command.getPojo().setCode(command.getPojo().getCode().trim());
        }
        if(StringUtils.isNotBlank(command.getPojo().getDescription())){
            command.getPojo().setDescription(command.getPojo().getDescription().trim());
        }
    }

    /**
     * Validate unique code before proceeding to CRUD actions.
     * @param command
     * @param errors
     */
    private void checkUniqueCode(UserGroupCommand command, Errors errors) {
        String code = command.getPojo().getCode();
        try{
            UserGroupDTO dto = userGroupService.findByCode(command.getPojo().getCode());
            if(command.getPojo().getUserGroupId() != null){
                if(!dto.getUserGroupId().equals(command.getPojo().getUserGroupId())){
                    errors.rejectValue("pojo.code", "errors.duplicated");
                }
            }else{
                if(dto != null){
                    errors.rejectValue("pojo.code", "errors.duplicated");
                }
            }
        }catch (ObjectNotFoundException one){

        }
    }

    private void checkWhiteSpaceCode(UserGroupCommand command, Errors errors){
        String code = command.getPojo().getCode();
        if(StringUtils.isNotBlank(code)){
            if(code.indexOf(" ") > -1){
                errors.rejectValue("pojo.code", "usergroup.not_allow_whitespace_in_code");
            }
        }
    }
}
