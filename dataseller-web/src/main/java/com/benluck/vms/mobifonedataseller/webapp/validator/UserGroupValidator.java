package com.benluck.vms.mobifonedataseller.webapp.validator;

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
        trimmingField(command);
        checkRequiredFields(command, errors);
        checkUniqueCode(command, errors);
    }

    /**
     * Validate required fields against to CRUD actions.
     * @param command
     * @param errors
     */
    private void checkRequiredFields(UserGroupCommand command, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pojo.code", "errors.required", new Object[]{this.getMessageSourceAccessor().getMessage("usergroup.label.code")}, "non-empty value required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pojo.description", "errors.required", new Object[]{this.getMessageSourceAccessor().getMessage("usergroup.label.description")}, "non-empty value required.");
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
        }catch (ObjectNotFoundException one){}
    }
}
