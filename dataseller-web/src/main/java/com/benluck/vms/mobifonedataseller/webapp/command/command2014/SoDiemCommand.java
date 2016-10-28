package com.benluck.vms.mobifonedataseller.webapp.command.command2014;


import com.benluck.vms.mobifonedataseller.core.dto.CTTichDiemSoDiemDTO;
import com.benluck.vms.mobifonedataseller.webapp.command.AbstractCommand;

import java.sql.Timestamp;

public class SoDiemCommand extends AbstractCommand<CTTichDiemSoDiemDTO> {
    public SoDiemCommand(){
        this.pojo = new CTTichDiemSoDiemDTO();
    }
    private Timestamp fromDate;
    private Timestamp toDate;

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
}
