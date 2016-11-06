package com.benluck.vms.mobifonedataseller.webapp.validator;

import com.benluck.vms.mobifonedataseller.webapp.command.KHDNCommand;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Component;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by thaihoang on 11/6/2016.
 */

@Component
public class KHDNValidator extends ApplicationObjectSupport implements Validator {


    @Override
    public boolean supports(Class<?> aClass) {
        return KHDNCommand.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        KHDNCommand command = (KHDNCommand) o;
        checkRequiredFields(command, errors);
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
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pojo.issuedContractDate", "errors.required", new Object[]{this.getMessageSourceAccessor().getMessage("admin.khdn.label.issuedContractDate")}, "non-empty value required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pojo.stb_vas", "errors.required", new Object[]{this.getMessageSourceAccessor().getMessage("admin.khdn.label.stb_vas")}, "non-empty value required.");

    }
}
