package com.benluck.vms.mobifonedataseller.webapp.command.command2014;


import com.benluck.vms.mobifonedataseller.core.dto.CTTichDiemMaPhieuDTO;
import com.benluck.vms.mobifonedataseller.webapp.command.AbstractCommand;

import java.sql.Timestamp;

public class TicketCommand extends AbstractCommand<CTTichDiemMaPhieuDTO> {
    public TicketCommand(){
        this.pojo = new CTTichDiemMaPhieuDTO();
    }

    private Timestamp fromDate;
    private Timestamp toDate;
    private String message;
    private String warning;
    private String[] checkListHuy;

    public Timestamp getFromDate() {
        return fromDate;
    }

    public void setFromDate(Timestamp fromDate) {
        this.fromDate = fromDate;
    }

    public Timestamp getToDate() {
        return toDate;
    }

    public void setToDate(Timestamp toDate) {
        this.toDate = toDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    public String[] getCheckListHuy() {
        return checkListHuy;
    }

    public void setCheckListHuy(String[] checkListHuy) {
        this.checkListHuy = checkListHuy;
    }
}
