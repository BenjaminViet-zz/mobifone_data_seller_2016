package com.benluck.vms.mobifonedataseller.webapp.command.command2014;

import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BaoCaoTongHopKetQuaChuongTrinhKPPTheoDaiLyDTO;
import com.benluck.vms.mobifonedataseller.webapp.command.AbstractCommand;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 11/16/14
 * Time: 9:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class BaoCaoTongHopKetQuaChuongTrinhKPPTheoDaiLyCommand extends AbstractCommand<BaoCaoTongHopKetQuaChuongTrinhKPPTheoDaiLyDTO> {
    public BaoCaoTongHopKetQuaChuongTrinhKPPTheoDaiLyCommand(){
        this.pojo = new BaoCaoTongHopKetQuaChuongTrinhKPPTheoDaiLyDTO();
    }

    private Long branch_Id;
    private Long district_Id;
    private Long dealer_Id;
    private String soEZ;
    private Integer giaiDoan;
    private Long item_Id;
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

    public Long getDistrict_Id() {
        return district_Id;
    }

    public void setDistrict_Id(Long district_Id) {
        this.district_Id = district_Id;
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

    public Long getDealer_Id() {
        return dealer_Id;
    }

    public void setDealer_Id(Long dealer_Id) {
        this.dealer_Id = dealer_Id;
    }

    public String getSoEZ() {
        return soEZ;
    }

    public void setSoEZ(String soEZ) {
        this.soEZ = soEZ;
    }

    public Integer getGiaiDoan() {
        return giaiDoan;
    }

    public void setGiaiDoan(Integer giaiDoan) {
        this.giaiDoan = giaiDoan;
    }
}
