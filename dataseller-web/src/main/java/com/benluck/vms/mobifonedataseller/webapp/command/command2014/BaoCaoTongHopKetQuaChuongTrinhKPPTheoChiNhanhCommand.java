package com.benluck.vms.mobifonedataseller.webapp.command.command2014;

import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BaoCaoTongHopKetQuaChuongTrinhKPPTheoChiNhanhOrQuanHuyenDTO;
import com.benluck.vms.mobifonedataseller.webapp.command.AbstractCommand;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 11/16/14
 * Time: 9:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class BaoCaoTongHopKetQuaChuongTrinhKPPTheoChiNhanhCommand extends AbstractCommand<BaoCaoTongHopKetQuaChuongTrinhKPPTheoChiNhanhOrQuanHuyenDTO> {
    public BaoCaoTongHopKetQuaChuongTrinhKPPTheoChiNhanhCommand(){
        this.pojo = new BaoCaoTongHopKetQuaChuongTrinhKPPTheoChiNhanhOrQuanHuyenDTO();
    }

    private Long branch_Id;
    private Long item_Id;
    private Integer giaiDoan = Calendar.getInstance().get(Calendar.MONTH) + 1;
    private Integer trang_thai_dat = -1; // -1: all, 0: khong_dat, 1: dat
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

    public Long getItem_Id() {
        return item_Id;
    }

    public void setItem_Id(Long item_Id) {
        this.item_Id = item_Id;
    }

    public Integer getTrang_thai_dat() {
        return trang_thai_dat;
    }

    public void setTrang_thai_dat(Integer trang_thai_dat) {
        this.trang_thai_dat = trang_thai_dat;
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

    public Integer getGiaiDoan() {
        return giaiDoan;
    }

    public void setGiaiDoan(Integer giaiDoan) {
        this.giaiDoan = giaiDoan;
    }
}
