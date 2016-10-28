package com.benluck.vms.mobifonedataseller.webapp.validator;

import com.benluck.vms.mobifonedataseller.core.business.UserGroupManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.UserGroupDTO;
import com.benluck.vms.mobifonedataseller.webapp.command.command2014.UserGroupCommand;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserGroupValidator extends ApplicationObjectSupport implements Validator {
    private transient final Log log = LogFactory.getLog(getClass());
    @Autowired
    private UserGroupManagementLocalBean userGroupManagementLocalBean;

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
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pojo.name", "errors.required", new Object[]{this.getMessageSourceAccessor().getMessage("usergroup.label.name")}, "non-empty value required.");
    }

    /**
     * Remove white spaces at the start and end of each string in the model.
     * @param command
     */
    private void trimmingField(UserGroupCommand command) {
        if(StringUtils.isNotBlank(command.getPojo().getCode())){
            command.getPojo().setCode(command.getPojo().getCode().trim());
        }
        if(StringUtils.isNotBlank(command.getPojo().getName())){
            command.getPojo().setName(command.getPojo().getName().trim());
        }
    }

    /**
     * Validate unique code before proceeding to CRUD actions.
     * @param command
     * @param errors
     */
    private void checkUniqueCode(UserGroupCommand command, Errors errors) {
        String code = command.getPojo().getCode();
        if(StringUtils.isNotBlank(code)){
            try{
                UserGroupDTO dto = userGroupManagementLocalBean.findByCode(code);
                if(dto != null && ! dto.getUserGroupId().equals(command.getPojo().getUserGroupId())){
                    errors.rejectValue("pojo.code", "errors.duplicated");
                }
            }catch (Exception e)
            {
                log.error(e.getMessage(), e);
            }
        }
    }
}
