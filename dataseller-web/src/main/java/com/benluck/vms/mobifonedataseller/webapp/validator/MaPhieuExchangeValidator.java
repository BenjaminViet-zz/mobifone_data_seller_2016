package com.benluck.vms.mobifonedataseller.webapp.validator;

import com.benluck.vms.mobifonedataseller.core.business.MaPhieuCTTichDiemManagementLocalBean;
import com.benluck.vms.mobifonedataseller.webapp.command.command2014.TicketCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 12/1/14
 * Time: 11:18 AM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class MaPhieuExchangeValidator extends ApplicationObjectSupport implements Validator{
    @Autowired
    private MaPhieuCTTichDiemManagementLocalBean maPhieuCTTichDiemManagementLocalBean;

    @Override
    public boolean supports(Class<?> aClass) {
        return TicketCommand.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        TicketCommand command = (TicketCommand)o;
        trimmingFields(command);
        validateMaPhieuIfNotExchange(command, errors);
    }

    /**
     * Check the list of Ma Phieu. Make sure there is not any Ma Phieu that has not exchanged to gift before.
     * @param command
     * @param errors
     */
    private void validateMaPhieuIfNotExchange(TicketCommand command, Errors errors){
        if(command.getCheckList() != null && command.getCheckList().length > 0){
            String[] exchangedMaPhieuList = this.maPhieuCTTichDiemManagementLocalBean.validateMaPhieuNeedToExchanged_tdcg(command.getCheckList());
            StringBuilder htmlErrors = new StringBuilder();
            if(exchangedMaPhieuList != null && exchangedMaPhieuList.length > 0){
                htmlErrors.append(" Đổi quà thất bại. Các mã phiếu bên dưới đã đổi quà rồi. ");
                htmlErrors.append("<ul style='list-type: disc;'>");
                for(String exchangedMaPhieu : exchangedMaPhieuList){
                    htmlErrors.append("<li>").append(exchangedMaPhieu).append("</li>");
                }
                htmlErrors.append("</ul>");
                command.setMessage(htmlErrors.toString());
            }
        }
    }

    /**
     * Remove white spaces at the start and end of each string in the model.
     * @param command
     */
    private void trimmingFields(TicketCommand command){
        if(command.getCheckList() != null && command.getCheckList().length > 0){
            for (String maPhieu : command.getCheckList()){
                maPhieu = maPhieu.trim();
            }
        }
    }
}
