package com.benluck.vms.mobifonedataseller.webapp.validator;

import com.benluck.vms.mobifonedataseller.webapp.command.command2014.DealerAccountCommand;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/26/14
 * Time: 4:07 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class DoiTienDBHValidator extends ApplicationObjectSupport implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return DealerAccountCommand.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        DealerAccountCommand command = (DealerAccountCommand)o;
        validateList(command, errors);
    }

    /**
     * Validate checkList in the model to make sure at least one
     * @param command
     * @param errors
     */
    private void validateList(DealerAccountCommand command, Errors errors){
        if(command.getCheckList() == null || command.getCheckList().length == 0){
            command.setMessage(this.getMessageSourceAccessor().getMessage("chinhanh.doi_tien_dbh.error.no_cycle_chosen"));
        }
    }
}
