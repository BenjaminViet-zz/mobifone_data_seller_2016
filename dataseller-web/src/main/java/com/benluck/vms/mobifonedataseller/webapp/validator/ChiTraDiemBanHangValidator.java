package com.benluck.vms.mobifonedataseller.webapp.validator;

import com.benluck.vms.mobifonedataseller.core.business.BranchManagementLocalBean;
import com.benluck.vms.mobifonedataseller.webapp.command.command2014.RegistrationTransCommand;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: HP
 * Date: 3/18/15
 * Time: 9:02 AM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class ChiTraDiemBanHangValidator extends ApplicationObjectSupport implements Validator {
    @Autowired
    private BranchManagementLocalBean branchManagementLocalBean;

    @Override
    public boolean supports(Class<?> aClass){
        return RegistrationTransCommand.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o , Errors errors){
        RegistrationTransCommand command = (RegistrationTransCommand)o;
        trimmingFields(command);
        checkRequireFields(command, errors);
        validateMobifoneNumber(command, errors);
        validateList(command, errors);
    }

    /**
     * validate the required fields which need to be not empty in the model.
     * @param command
     * @param errors
     */
    private void checkRequireFields(RegistrationTransCommand command, Errors errors){
        if(command.getNgayChiTra() == null){
            command.setMessage(this.getMessageSourceAccessor().getMessage("chitradbh.errors.required_ngay_chi_tra"));
        }
    }

    /**
     * Validate checkList in the model to make sure all of agencies are chosen to pay correctly.
     * @param command
     * @param errors
     */
    private void validateList(RegistrationTransCommand command, Errors errors) {
        if (command.getCheckList() == null || command.getCheckList().length == 0){
            command.setMessage(this.getMessageSourceAccessor().getMessage("chitradbh.error.no_dbh_chosen"));
        }else {
            for(String complexStr : command.getCheckList()){
                try{
                    Long dealer_Id = Long.valueOf(complexStr.split("\\_")[0]);
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    String soEZ = complexStr.split("\\_")[1];
                    Integer totalTrans = Integer.valueOf(complexStr.split("\\_")[2]);
                    String sum_Date = complexStr.split("\\_")[3].toString();

                    List<Date> sumDateList = new ArrayList<>();
                    for (String s : sum_Date.split("\\#")){
                        sumDateList.add(df.parse(s));
                    }

                    if (this.branchManagementLocalBean.checkPaymentStatusByDealerIdAndSumDate(dealer_Id, sumDateList , soEZ)){
                        command.setMessage(this.getMessageSourceAccessor().getMessage("chitradbh.errors.duplicated"));
                    }
                    if (!this.branchManagementLocalBean.getTotalTrans_ThoaDKCT(dealer_Id, sumDateList, soEZ).equals(totalTrans)){
                        command.setMessage(this.getMessageSourceAccessor().getMessage("chitradbh.errors.not_match_compare_total_transaction"));
                        return;
                    }
                }catch (ParseException pe){
                    command.setMessage(this.getMessageSourceAccessor().getMessage("chitradbh.erros.invalid_sum_date"));
                    return;
                }catch (Exception e){
                    command.setMessage("Can not parse some arguments from complex string to Objects.");
                    return;
                }
            }
        }
    }

    /**
     * Remove white spaces at the start and end of each string in the model.
     * @param command
     */
    private void trimmingFields(RegistrationTransCommand command){
        if(StringUtils.isNotBlank(command.getPojo().getEz_Isdn())){
            command.getPojo().setEz_Isdn(command.getPojo().getEz_Isdn().trim());
        }
    }

    /**
     * Validate format of the Phone Number in the model.
     * @param command
     * @param errors
     */
    private void validateMobifoneNumber(RegistrationTransCommand command, Errors errors){
        if(StringUtils.isNotBlank(command.getPojo().getEz_Isdn())){
            if(!command.getPojo().getEz_Isdn().matches("^0{1}\\d{9,10}$")){
                command.setMessage(this.getMessageSourceAccessor().getMessage("pojo.ez_Isdn", "user.msg.invalid_mobifone_number"));
            }
        }
    }
}
