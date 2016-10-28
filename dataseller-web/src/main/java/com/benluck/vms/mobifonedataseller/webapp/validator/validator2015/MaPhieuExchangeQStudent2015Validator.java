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
 * Date: 4/7/15
 * Time: 5:32 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class MaPhieuExchangeQStudent2015Validator extends ApplicationObjectSupport implements Validator {
    @Autowired
    private MaPhieuCTTichDiemManagementLocalBean maPhieuCTTichDiemManagementLocalBean;

    @Override
    public boolean supports(Class<?> aClass) {
        return TicketCommand.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        TicketCommand command = (TicketCommand)o;
        validateMaPhieuIfExchanged(command, errors);
    }

    /**
     * Validate validity of Ma Phieu list.
     * If one has exchanged before, return a HTML content include it for rejecting.
     * @param command
     * @param errors
     */
    private void validateMaPhieuIfExchanged(TicketCommand command, Errors errors){
        if(command.getCheckList() != null && command.getCheckList().length > 0){
            String[] exchangedMaPhieuList = this.maPhieuCTTichDiemManagementLocalBean.getExchangedMaPhieuListByList_qStudent(command.getCheckList());
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
}
