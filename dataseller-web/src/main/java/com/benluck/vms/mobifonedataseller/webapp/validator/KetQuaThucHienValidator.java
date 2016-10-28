package com.benluck.vms.mobifonedataseller.webapp.validator;

import com.benluck.vms.mobifonedataseller.common.utils.CommonUtil;
import com.benluck.vms.mobifonedataseller.core.business.KPPManagementLocalBean;
import com.benluck.vms.mobifonedataseller.webapp.command.command2014.UserCommand;
import com.benluck.vms.mobifonedataseller.webapp.command.command2015.ThueBaoPTM_KetQuaThucHien_Command;
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
public class KetQuaThucHienValidator extends ApplicationObjectSupport implements Validator {
    private transient final Log log = LogFactory.getLog(getClass());

    @Autowired
    private KPPManagementLocalBean kppManagementLocalBean;

    @Override
    public boolean supports(Class<?> aClass) {
        return UserCommand.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ThueBaoPTM_KetQuaThucHien_Command command = (ThueBaoPTM_KetQuaThucHien_Command)o;
        trimmingFields(command);
        checkRequiredFields(command, errors);
        validateMobifoneNumber(command, errors);
        if(!errors.hasErrors()){
            checkIfAlreadyRegistered(command, errors);
        }
    }

    /**
     * Remove white spaces at the start and end of string in the model.
     * @param command
     */
    private void trimmingFields(ThueBaoPTM_KetQuaThucHien_Command command){
        if(StringUtils.isNotBlank(command.getUser().getUserName())){
            command.getUser().setUserName(command.getUser().getUserName().trim());
        }
    }

    /**
     * Verify required fields to make sure there are non-empty values on those fields.
     * @param command
     * @param errors
     */
    private void checkRequiredFields(ThueBaoPTM_KetQuaThucHien_Command command, Errors errors){
        if(StringUtils.isBlank(command.getUser().getUserName())){
            command.setMessage(this.getMessageSourceAccessor().getMessage("user.user_name.required"));
        }
    }

    /**
     * Validate format of the Phone Number in the model.
     * @param command
     * @param errors
     */
    private void validateMobifoneNumber(ThueBaoPTM_KetQuaThucHien_Command command, Errors errors){
        if(StringUtils.isNotBlank(command.getUser().getUserName())){
            if(!CommonUtil.isMobifoneNumber(command.getUser().getUserName())){
                command.setMessage(this.getMessageSourceAccessor().getMessage("user.msg.invalid_mobifone_number"));
            }
        }
    }

    /**
     * Check if the phone number has registered the promotion.
     * @param command
     * @param errors
     */
    private void checkIfAlreadyRegistered(ThueBaoPTM_KetQuaThucHien_Command command, Errors errors){
        if(StringUtils.isBlank(command.getMessage())){
            if(StringUtils.isNotBlank(command.getUser().getUserName())){
                try{
                    boolean hasRegister = this.kppManagementLocalBean.checkIfAlreadyRegistered(command.getUser().getUserName());
                }catch (Exception e){
                    command.setMessage(this.getMessageSourceAccessor().getMessage("kpp.xem_diem_doi_thuong.msg.thue_bao_chua_dk_nhan_otp"));
                }
            }
        }
    }
}
