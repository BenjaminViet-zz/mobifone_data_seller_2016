package com.benluck.vms.mobifonedataseller.webapp.validator;

import com.benluck.vms.mobifonedataseller.core.business.ChiNhanhManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.ChiNhanhDTO;
import com.benluck.vms.mobifonedataseller.webapp.command.command2014.ChiNhanhCommand;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/19/14
 * Time: 10:19 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class ChiNhanhValidator extends ApplicationObjectSupport implements Validator{
    @Autowired
    private ChiNhanhManagementLocalBean chiNhanhManagementLocalBean;

    @Override
    public boolean supports(Class<?> aClass) {
        return ChiNhanhCommand.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ChiNhanhCommand command = (ChiNhanhCommand) o;
        trimmingFields(command);
        checkRequiredFields(command, errors);
        checkUniqueCode(command, errors);
    }

    /**
     * Remove white spaces at the start and end of string in the model.
     * @param command
     */
    private void trimmingFields(ChiNhanhCommand command){
        if(StringUtils.isNotBlank(command.getPojo().getName())){
            command.getPojo().setName(command.getPojo().getName().trim());
        }
    }

    /**
     * Verify required fields to make sure there are non-empty values on those fields.
     * @param command
     * @param errors
     */
    private  void checkRequiredFields(ChiNhanhCommand command, Errors errors){
        if(command.getPojo().getChiNhanh() == null){
            errors.rejectValue("pojo.chiNhanh", "chinhanh.thong_tin_chi_nhanh.required.chi_nhanh_number");
        }else if(command.getPojo().getChiNhanh().compareTo(0) <= 0){
            errors.rejectValue("pojo.chiNhanh", "chinhanh.thong_tin_chi_nhanh.errors.invalid_chi_nhanh_number");
        }
    }

    /**
     * Verify if duplicated value on some branch that inserted before. Attached error message if one existed.
     * @param command
     * @param errors
     */
    private void checkUniqueCode(ChiNhanhCommand command,Errors errors){
        if(command.getPojo().getChiNhanh() != null){
            ChiNhanhDTO dto = this.chiNhanhManagementLocalBean.findByChiNhanhUnique(command.getPojo().getChiNhanh());
            if (command.getPojo().getChiNhanhId() != null){
                if(!command.getPojo().getChiNhanhId().equals(dto.getChiNhanhId())){
                    errors.rejectValue("pojo.chiNhanh", "chinhanh.thong_tin_chi_nhanh.errors.duplicated");
                }
            }else{
                errors.rejectValue("pojo.chiNhanh", "chinhanh.thong_tin_chi_nhanh.errors.duplicated");
            }
        }
    }
}
