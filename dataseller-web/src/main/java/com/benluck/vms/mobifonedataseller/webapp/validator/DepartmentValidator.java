package com.benluck.vms.mobifonedataseller.webapp.validator;

import com.benluck.vms.mobifonedataseller.core.business.DepartmentManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.DepartmentDTO;
import com.benluck.vms.mobifonedataseller.webapp.command.command2014.DepartmentCommand;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import javax.ejb.ObjectNotFoundException;

/**
 * Created with IntelliJ IDEA.
 * User: nkhang
 * Date: 9/28/13
 * Time: 4:00 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class DepartmentValidator extends ApplicationObjectSupport implements Validator {

    @Autowired
    private DepartmentManagementLocalBean departmentManagementLocalBean;

    @Override
    public boolean supports(Class<?> aClass) {
        return DepartmentCommand.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        DepartmentCommand command = (DepartmentCommand)o;
        trimingField(command);
        validateMobifoneNumber(command, errors);
        checkRequiredFields(command, errors);
        checkUniqueCode(command, errors);
    }

    /**
     * Validate unique code before proceeding to the Controller for CRUD actions.
     * @param command
     * @param errors
     */
    private void checkUniqueCode(DepartmentCommand command, Errors errors) {
        String code = command.getPojo().getCode();
        if(StringUtils.isNotBlank(code)){
            try{
                DepartmentDTO dto = departmentManagementLocalBean.findByCode(code);
                if(command.getPojo().getDepartmentId() != null){
                    if(!command.getPojo().getDepartmentId().equals(dto.getDepartmentId())){
                        errors.rejectValue("pojo.code", "admin.cua_hang.errors_duplicated_code");
                    }
                }else{
                    if(dto != null){
                        errors.rejectValue("pojo.code", "admin.cua_hang.errors_duplicated_code");
                    }
                }
            }catch (ObjectNotFoundException e){
                //This code is available.
            }
        }
    }

    /**
     * Validate format of phone number string in the model.
     * @param command
     * @param errors
     */
    private void validateMobifoneNumber(DepartmentCommand command, Errors errors){
        if(StringUtils.isNotBlank(command.getPojo().getTel())){
            String[] telList = command.getPojo().getTel().split(";");
            for(String tel : telList){
                if(!command.getPojo().getTel().matches("^0{1}\\d{9,10}$")){
                    errors.rejectValue("pojo.tel", "admin.cua_hang.invalid_tel");
                }
            }
        }
    }

    /**
     * Verify required fields to make sure there are non-empty values on those fields.
     * @param command
     * @param errors
     */
    private void checkRequiredFields(DepartmentCommand command, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pojo.code", "required", new Object[]{this.getMessageSourceAccessor().getMessage("label.ma_cua_hang")}, "non-empty value required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pojo.name", "required", new Object[]{this.getMessageSourceAccessor().getMessage("label.department_name")}, "non-empty value required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pojo.address", "required", new Object[]{this.getMessageSourceAccessor().getMessage("label.address")}, "non-empty value required.");

    }

    /**
     * Remove white spaces at the start and end of each string in the model.
     * @param command
     */
    private void trimingField(DepartmentCommand command) {
        if(StringUtils.isNotBlank(command.getPojo().getCode())){
            command.getPojo().setCode(command.getPojo().getCode().trim());
        }
        if(StringUtils.isNotBlank(command.getPojo().getName())){
            command.getPojo().setName(command.getPojo().getName().trim());
        }
        if(StringUtils.isNotBlank(command.getPojo().getTel())){
            command.getPojo().setName(command.getPojo().getTel().trim());
        }
        if(StringUtils.isNotBlank(command.getPojo().getAddress())){
            command.getPojo().setName(command.getPojo().getAddress().trim());
        }
        if(StringUtils.isNotBlank(command.getPojo().getContactName())){
            command.getPojo().setName(command.getPojo().getContactName().trim());
        }
    }
}
