package com.benluck.vms.mobifonedataseller.webapp.validator;

import com.benluck.vms.mobifonedataseller.common.utils.CommonUtil;
import com.benluck.vms.mobifonedataseller.core.business.KPPManagementLocalBean;
import com.benluck.vms.mobifonedataseller.webapp.command.command2014.UserCommand;
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
 * Date: 10/13/14
 * Time: 10:50 AM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class XemDiemDoiThuongKPPValidator extends ApplicationObjectSupport implements Validator {
    private transient final Log log = LogFactory.getLog(getClass());

    @Autowired
    private KPPManagementLocalBean kppManagementLocalBean;

    @Override
    public boolean supports(Class<?> aClass) {
        return UserCommand.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserCommand command = (UserCommand)o;
        trimmingFields(command);
        checkRequiredFields(command, errors);
        validatePhoneNumber(command, errors);
        if(!errors.hasErrors()){
            checkIfAlreadyRegistered(command, errors);
        }
    }

    /**
     * Remove white spaces at the start and end of each string in the model.
     * @param command
     */
    private void trimmingFields(UserCommand command){
        if(StringUtils.isNotBlank(command.getPojo().getUserName())){
            command.getPojo().setUserName(command.getPojo().getUserName().trim());
        }
    }

    /**
     * Validate required fields to make sure it is enough of required properties for CRUD actions.
     * @param command
     * @param errors
     */
    private void checkRequiredFields(UserCommand command, Errors errors){
        if(StringUtils.isBlank(command.getPojo().getUserName())){
            command.setMessage(this.getMessageSourceAccessor().getMessage("user.user_name.required"));
        }
    }

    /**
     * Validate format of phone number in the model.
     * @param command
     * @param errors
     */
    private void validatePhoneNumber(UserCommand command, Errors errors){
        if(StringUtils.isNotBlank(command.getPojo().getUserName())){
            if(!CommonUtil.isMobifoneNumber(command.getPojo().getUserName())){
                command.setMessage(this.getMessageSourceAccessor().getMessage("user.msg.invalid_mobifone_number"));
            }
        }
    }

    /**
     * Make sure he user that has registered the promotion from DB.
     * @param command
     * @param errors
     */
    private void checkIfAlreadyRegistered(UserCommand command, Errors errors){
        if(StringUtils.isBlank(command.getMessage())){
            if(StringUtils.isNotBlank(command.getPojo().getUserName())){
                try{
                    this.kppManagementLocalBean.checkIfAlreadyRegistered(command.getPojo().getUserName());
                }catch (Exception e){
                    command.setMessage(this.getMessageSourceAccessor().getMessage("kpp.xem_diem_doi_thuong.msg.thue_bao_chua_dk_nhan_otp"));
                }
            }
        }
    }
}
