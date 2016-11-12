package com.benluck.vms.mobifonedataseller.webapp.validator;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.core.business.KHDNManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.KHDNDTO;
import com.benluck.vms.mobifonedataseller.webapp.command.KHDNCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Component;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import javax.ejb.ObjectNotFoundException;

/**
 * Created by thaihoang on 11/6/2016.
 */

@Component
public class KHDNValidator extends ApplicationObjectSupport implements Validator {

    @Autowired
    private KHDNManagementLocalBean khdnService;

    @Override
    public boolean supports(Class<?> aClass) {
        return KHDNCommand.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        KHDNCommand command = (KHDNCommand) o;
        String action = command.getCrudaction();

        if(action.equals(Constants.ACTION_DELETE)){
            checkExistBeforeDelete(command, errors);
        }else{
            checkRequiredFields(command, errors);
            checkUnique(command, errors);
        }
    }

    /**
     * Validate required fields to make sure it is enough of required properties to insert or update.
     * @param command
     * @param errors
     */
    private void checkRequiredFields(KHDNCommand command, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pojo.name", "errors.required", new Object[]{this.getMessageSourceAccessor().getMessage("label.tenDoanhNghiep")}, "non-empty value required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pojo.mst", "errors.required", new Object[]{this.getMessageSourceAccessor().getMessage("admin.khdn.label.mst")}, "non-empty value required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pojo.gpkd", "errors.required", new Object[]{this.getMessageSourceAccessor().getMessage("admin.khdn.label.gpkd")}, "non-empty value required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "issuedContractDate", "errors.required", new Object[]{this.getMessageSourceAccessor().getMessage("admin.khdn.label.issuedContractDate")}, "non-empty value required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pojo.stb_vas", "errors.required", new Object[]{this.getMessageSourceAccessor().getMessage("admin.khdn.label.stb_vas")}, "non-empty value required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pojo.shopCode", "errors.required", new Object[]{this.getMessageSourceAccessor().getMessage("admin.khdn.label.shopCode")}, "non-empty value required.");
    }

    private void checkUnique(KHDNCommand command, Errors errors){
        KHDNDTO pojo = command.getPojo();
        try{
            if(StringUtils.isNotBlank(pojo.getMst())){
                KHDNDTO dto = khdnService.findEqualUnique("mst", pojo.getMst());
                if(pojo.getKHDNId() != null){
                    if(!dto.getKHDNId().equals(pojo.getKHDNId())){
                        errors.rejectValue("pojo.mst", "label.khdn.mst.errors_duplicated");
                    }
                }else{
                    if(dto != null){
                        errors.rejectValue("pojo.mst", "label.khdn.mst.errors_duplicated");
                    }
                }
            }
        }catch (ObjectNotFoundException one){}

        try{
            if(StringUtils.isNotBlank(pojo.getGpkd())){
                KHDNDTO dto = khdnService.findEqualUnique("gpkd", pojo.getGpkd());
                if(pojo.getKHDNId() != null){
                    if(!dto.getKHDNId().equals(pojo.getKHDNId())){
                        errors.rejectValue("pojo.gpkd", "label.khdn.gpkd.errors_duplicated");
                    }
                }else{
                    if(dto != null){
                        errors.rejectValue("pojo.gpkd", "label.khdn.gpkd.errors_duplicated");
                    }
                }
            }
        }catch (ObjectNotFoundException one){}

        try{
            if(StringUtils.isNotBlank(pojo.getGpkd())){
                KHDNDTO dto = khdnService.findEqualUnique("shopCode", pojo.getGpkd());
                if(pojo.getKHDNId() != null){
                    if(!dto.getKHDNId().equals(pojo.getKHDNId())){
                        errors.rejectValue("pojo.gpkd", "label.khdn.shopCode.errors_duplicated");
                    }
                }else{
                    if(dto != null){
                        errors.rejectValue("pojo.gpkd", "label.khdn.shopCode.errors_duplicated");
                    }
                }
            }
        }catch (ObjectNotFoundException one){}
    }

    private void checkExistBeforeDelete(KHDNCommand command, Errors errors){
        Boolean isExisting = this.khdnService.checkExistsBeforeDelete(command.getPojo().getKHDNId());
        if(isExisting){
            command.setErrorMessage(this.getMessageSourceAccessor().getMessage("label.khdn.existed_in_order"));
        }
    }
}
