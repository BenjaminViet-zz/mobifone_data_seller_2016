package com.benluck.vms.mobifonedataseller.webapp.validator;

import com.benluck.vms.mobifonedataseller.core.business.DMChuongTrinhManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.PromotionDTO;
import com.benluck.vms.mobifonedataseller.webapp.command.command2014.DMChuongTrinhCommand;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.ejb.ObjectNotFoundException;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 3/2/15
 * Time: 11:33 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class DMChuongTrinhValidator extends ApplicationObjectSupport implements Validator{
    @Autowired
    private DMChuongTrinhManagementLocalBean dmChuongTrinhService;

    @Override
    public boolean supports(Class<?> aClass) {
        return DMChuongTrinhCommand.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        DMChuongTrinhCommand command = (DMChuongTrinhCommand)o;
        trimmingFields(command, errors);
        checkRequiredFields(command, errors);
        checkUniqueCode(command, errors);
    }

    /**
     * Remove white spaces at the start and end of each string in the model.
     * @param command
     * @param errors
     */
    private void trimmingFields(DMChuongTrinhCommand command, Errors errors){
        if(StringUtils.isBlank(command.getPojo().getCode())){
            command.getPojo().setCode(command.getPojo().getCode().trim());
        }
        if(StringUtils.isBlank(command.getPojo().getDescription())){
            command.getPojo().setDescription(command.getPojo().getDescription().trim());
        }
        if(StringUtils.isBlank(command.getPojo().getDbLinkName())){
            command.getPojo().setDbLinkName(command.getPojo().getDbLinkName().trim());
        }
    }

    /**
     * Verify required fields to make sure there are non-empty values on those fields.
     * @param command
     * @param errors
     */
    private void checkRequiredFields(DMChuongTrinhCommand command, Errors errors){
        if(StringUtils.isBlank(command.getPojo().getCode())){
            errors.rejectValue("pojo.code", "dmchuongtrinh.msg.required_code");
        }
        if(StringUtils.isBlank(command.getPojo().getDescription())){
            errors.rejectValue("pojo.chiNhanh", "dmchuongtrinh.msg.required_description");
        }
        if(StringUtils.isBlank(command.getPojo().getDescription())){
            errors.rejectValue("pojo.dbLinkName", "dmchuongtrinh.msg.required_dbLink_name");
        }
    }

    /**
     * Validate unique code before proceeding to the Controller for CRUD actions.
     * @param command
     * @param errors
     */
    private void checkUniqueCode(DMChuongTrinhCommand command, Errors errors){
        if(StringUtils.isNotBlank(command.getPojo().getCode())){
            PromotionDTO dto = null;
            try{
                this.dmChuongTrinhService.findByCode(command.getPojo().getCode());
            }catch (ObjectNotFoundException oe){}
            if(dto != null){
                if(command.getPojo().getChuongTrinhId() != null){
                    if(dto.getChuongTrinhId() != command.getPojo().getChuongTrinhId()){
                        errors.rejectValue("pojo.code", "dmchuongtrinh.msg.duplicated_code");
                    }
                }else{
                    errors.rejectValue("pojo.code", "dmchuongtrinh.msg.duplicated_code");
                }
            }
        }
    }
}
