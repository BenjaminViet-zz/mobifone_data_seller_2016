package com.benluck.vms.mobifonedataseller.webapp.validator.validator2015;

import com.benluck.vms.mobifonedataseller.core.business.MaPhieuCTTichDiemManagementLocalBean;
import com.benluck.vms.mobifonedataseller.webapp.command.command2014.TicketCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created with IntelliJ IDEA.
 * User: vovanphuc0810
 * Date: 4/8/15
 * Time: 6:25 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class MaPhieuExchangeQStudent2015HuyDoiQuaValidator extends ApplicationObjectSupport implements Validator {
    @Autowired
    private MaPhieuCTTichDiemManagementLocalBean maPhieuCTTichDiemManagementLocalBean;

    @Override
    public boolean supports(Class<?> aClass) {
        return TicketCommand.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        TicketCommand command = (TicketCommand)o;
        validateMaPhieuBeforeCancelExchange(command, errors);
    }

    /**
     * Validate validity of Ma Phieu list before proceed to canceling progress.
     * If one of Ma Phieu which did not exchanged before, build a HTML content include it and put to the model.
     * @param command
     * @param errors
     */
    private void validateMaPhieuBeforeCancelExchange(TicketCommand command, Errors errors){
        if(command.getCheckList() != null && command.getCheckList().length > 0){
            String[] exchangedMaPhieuList = this.maPhieuCTTichDiemManagementLocalBean.getExchangedMaPhieuListByList_qStudent(command.getCheckList());
            StringBuilder htmlErrors = new StringBuilder();
            if(exchangedMaPhieuList == null && exchangedMaPhieuList.length == 0){
                htmlErrors.append(" Huỷ đổi quà thất bại. Một số mã phiếu được chọn chưa đổi quà. ");
                htmlErrors.append("<ul style='list-type: disc;'>");
                for(String exchangedMaPhieu : exchangedMaPhieuList){
                    htmlErrors.append("<li>").append(exchangedMaPhieu).append("</li>");
                }
                htmlErrors.append("</ul>");
                command.setMessage(htmlErrors.toString());
            }
        }
    }
}
