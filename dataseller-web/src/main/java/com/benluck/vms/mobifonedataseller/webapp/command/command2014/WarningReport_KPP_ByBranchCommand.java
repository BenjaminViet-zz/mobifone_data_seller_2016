package com.benluck.vms.mobifonedataseller.webapp.command.command2014;

import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BaoCaoCanhBao_KPP_TheoChiNhanhDTO;
import com.benluck.vms.mobifonedataseller.webapp.command.AbstractCommand;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 11/18/14
 * Time: 11:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class WarningReport_KPP_ByBranchCommand extends AbstractCommand<BaoCaoCanhBao_KPP_TheoChiNhanhDTO> {
    public WarningReport_KPP_ByBranchCommand(){
        this.pojo = new BaoCaoCanhBao_KPP_TheoChiNhanhDTO();
    }

    private Long branch_Id;
    private Date fromDate;
    private Date toDate;
    private Timestamp fromDateTime;
    private Timestamp toDateTime;

    public Long getBranch_Id() {
        return branch_Id;
    }

    public void setBranch_Id(Long branch_Id) {
        this.branch_Id = branch_Id;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Timestamp getFromDateTime() {
        return fromDateTime;
    }

    public void setFromDateTime(Timestamp fromDateTime) {
        this.fromDateTime = fromDateTime;
    }

    public Timestamp getToDateTime() {
        return toDateTime;
    }

    public void setToDateTime(Timestamp toDateTime) {
        this.toDateTime = toDateTime;
    }
}
