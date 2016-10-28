package com.benluck.vms.mobifonedataseller.webapp.validator;

import com.benluck.vms.mobifonedataseller.common.utils.CommonUtil;
import com.benluck.vms.mobifonedataseller.core.business.KPPManagementLocalBean;
import com.benluck.vms.mobifonedataseller.webapp.command.command2014.TraCuuThongTinKPPCommand;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 11/7/14
 * Time: 11:43 AM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class TraCuuThongTinKPPValidator extends ApplicationObjectSupport implements Validator{
    private transient final Log log = LogFactory.getLog(getClass());

    @Autowired
    private KPPManagementLocalBean kppManagementLocalBean;

    @Override
    public boolean supports(Class<?> aClass) {
        return TraCuuThongTinKPPCommand.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        TraCuuThongTinKPPCommand command = (TraCuuThongTinKPPCommand)o;
        trimmingFields(command);
        validateMobifoneNumber(command, errors);
        checkIfAlreadyRegistered(command, errors);
    }

    /**
     * Remove white spaces at the start and end of each string in the model.
     * @param command
     */
    private void trimmingFields(TraCuuThongTinKPPCommand command){
        if(StringUtils.isNotBlank(command.getSoEZ())){
            command.setSortExpression(command.getSoEZ().trim());
        }
    }

    /**
     * Validate format of the phoneNumber in the model.
     * @param command
     * @param errors
     */
    private void validateMobifoneNumber(TraCuuThongTinKPPCommand command, Errors errors){
        if(StringUtils.isNotBlank(command.getSoEZ())){
            if(!CommonUtil.isMobifoneNumber(command.getSoEZ())){
                command.setMessage(this.getMessageSourceAccessor().getMessage("user.msg.invalid_mobifone_number"));
            }else{
                if(!command.getSoEZ().matches("^0{1}\\d{9,10}$")){
                    errors.rejectValue("soEZ", "admin.tracuuthongtinkpp.msg.invalid_phone_number");
                }
            }
        }
    }

    /**
     * Make sure the phoneNumber (thus bao) that has registered the promotion and still work.
     * @param command
     * @param errors
     */
    private void checkIfAlreadyRegistered(TraCuuThongTinKPPCommand command, Errors errors){
        if(StringUtils.isBlank(command.getMessage())){
            if(StringUtils.isNotBlank(command.getSoEZ())){
                try{
                    boolean hasRegister = this.kppManagementLocalBean.checkIfAlreadyRegistered(command.getSoEZ());
                }catch (Exception e){
                    command.setMessage(this.getMessageSourceAccessor().getMessage("admin.tracuuthongtinkpp.msg.not_yet_register"));
                }
            }
        }
    }
}
