package com.benluck.vms.mobifonedataseller.webapp.validator;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.core.business.PackageDataManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.PackageDataDTO;
import com.benluck.vms.mobifonedataseller.webapp.command.PackageDataCommand;
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
 * User: vietquocpham
 * Date: 11/19/16
 * Time: 21:32
 * To change this template use File | Settings | File Templates.
 */
@Component
public class PackageDataValidator extends ApplicationObjectSupport implements Validator{
    private Logger logger = Logger.getLogger(PackageDataValidator.class);

    @Autowired
    private PackageDataManagementLocalBean packageDataService;

    @Override
    public boolean supports(Class<?> aClass) {
        return PackageDataCommand.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        PackageDataCommand command = (PackageDataCommand)o;
        String crudaction = command.getCrudaction();
        if(crudaction.equals(Constants.ACTION_DELETE)){
            checkUsageBeforeDelete(command);
        }else{
            checkRequiredFields(command, errors);
            checkUnique(command, errors);
            checkCustomPrefixUnitPrice(command,errors);
        }
    }

    /**
     * Validate required fields to make sure it is enough of required properties to insert or update.
     * @param command
     * @param errors
     */
    private void checkRequiredFields(PackageDataCommand command, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pojo.name", "errors.required", new Object[]{this.getMessageSourceAccessor().getMessage("packagedata.label.tenGoiCuoc")}, "non-empty value required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pojo.value", "errors.required", new Object[]{this.getMessageSourceAccessor().getMessage("packagedata.label.giaGoiCuoc")}, "non-empty value required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pojo.volume", "errors.required", new Object[]{this.getMessageSourceAccessor().getMessage("packagedata.label.dungLuongMienPhi")}, "non-empty value required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pojo.duration", "errors.required", new Object[]{this.getMessageSourceAccessor().getMessage("packagedata.label.thoiGianSuDung_value")}, "non-empty value required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pojo.numberOfExtend", "errors.required", new Object[]{this.getMessageSourceAccessor().getMessage("packagedata.label.soLanGiaHan")}, "non-empty value required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pojo.tk", "errors.required", new Object[]{this.getMessageSourceAccessor().getMessage("packagedata.label.tk")}, "non-empty value required.");
    }

    private void checkUnique(PackageDataCommand command, Errors errors){
        PackageDataDTO pojo = command.getPojo();
        try{
            if(StringUtils.isNotBlank(pojo.getName())){
                PackageDataDTO dto = packageDataService.findEqualUnique("name", pojo.getName());
                if(pojo.getPackageDataId() != null){
                    if(!dto.getPackageDataId().equals(pojo.getPackageDataId())){
                        errors.rejectValue("pojo.name", "packagedata.duplicated_name");
                    }
                }else{
                    if(dto != null){
                        errors.rejectValue("pojo.name", "packagedata.duplicated_name");
                    }
                }
            }
        }catch (ObjectNotFoundException one){}
    }

    private void checkUsageBeforeDelete(PackageDataCommand command){
        Integer countUsage = this.packageDataService.findUsageBeforeDelete(command.getPojo().getPackageDataId());
        if(countUsage.intValue() > 0){
            command.setErrorMessage(this.getMessageSourceAccessor().getMessage("packagedata.used_to_generate_card_code"));
        }
    }

    private void checkCustomPrefixUnitPrice(PackageDataCommand command, Errors errors){
        if(StringUtils.isNotBlank(command.getPojo().getCustomPrefixUnitPrice())){
            try{
                Integer.valueOf(command.getPojo().getCustomPrefixUnitPrice());
            }catch (NumberFormatException nfe){
                errors.rejectValue("pojo.customPrefixUnitPrice", "packagedata.only_number_allowed_for_custom_prefix_unit_price");
            }
        }
    }
}
