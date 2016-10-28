package com.benluck.vms.mobifonedataseller.webapp.command.command2014;

import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BaoCaoChiTietHangMucPSCKPP_MuaTheTuMobiFone_TheoDBHDTO;
import com.benluck.vms.mobifonedataseller.webapp.command.AbstractCommand;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 11/18/14
 * Time: 11:27 AM
 * To change this template use File | Settings | File Templates.
 */
public class BaoCaoChiTietHangMucPSC_kpp_muaTheTuMobiFone_theoDBHCommand extends AbstractCommand<BaoCaoChiTietHangMucPSCKPP_MuaTheTuMobiFone_TheoDBHDTO> {
    public BaoCaoChiTietHangMucPSC_kpp_muaTheTuMobiFone_theoDBHCommand(){
        this.pojo = new BaoCaoChiTietHangMucPSCKPP_MuaTheTuMobiFone_TheoDBHDTO();
    }

    private Long branch_Id;
    private Long district_Id;
    private Long dealer_Id;
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

    public Long getDistrict_Id() {
        return district_Id;
    }

    public void setDistrict_Id(Long district_Id) {
        this.district_Id = district_Id;
    }

    public Long getDealer_Id() {
        return dealer_Id;
    }

    public void setDealer_Id(Long dealer_Id) {
        this.dealer_Id = dealer_Id;
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
