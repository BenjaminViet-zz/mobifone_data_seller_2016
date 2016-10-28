package com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015;

import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BranchDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.DistrictDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.RetailDealerDTO;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: HP
 * Date: 3/17/15
 * Time: 6:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class BaoCaoEzNhanTinThamGiaChuongTrinhDTO implements Serializable{
    private static final long serialVersionUID = 2288342334459626103L;

    private BranchDTO branch;
    private DistrictDTO district;
    private RetailDealerDTO retailDealer;
    private String soEz;
    private Timestamp ngayNhanTin;
    private Timestamp ngayKichHoat;
    private String ghiChu;

    private Timestamp fromDate;
    private Timestamp toDate;
    private Timestamp fromNgayKichHoat;
    private Timestamp toNgayKichHoat;

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

    public BranchDTO getBranch() {
        return branch;
    }

    public void setBranch(BranchDTO branch) {
        this.branch = branch;
    }

    public DistrictDTO getDistrict() {
        return district;
    }

    public void setDistrict(DistrictDTO district) {
        this.district = district;
    }

    public RetailDealerDTO getRetailDealer() {
        return retailDealer;
    }

    public void setRetailDealer(RetailDealerDTO retailDealer) {
        this.retailDealer = retailDealer;
    }

    public String getSoEz() {
        return soEz;
    }

    public void setSoEz(String soEz) {
        this.soEz = soEz;
    }

    public Timestamp getNgayNhanTin() {
        return ngayNhanTin;
    }

    public void setNgayNhanTin(Timestamp ngayNhanTin) {
        this.ngayNhanTin = ngayNhanTin;
    }

    public Timestamp getNgayKichHoat() {
        return ngayKichHoat;
    }

    public void setNgayKichHoat(Timestamp ngayKichHoat) {
        this.ngayKichHoat = ngayKichHoat;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public Timestamp getFromNgayKichHoat() {
        return fromNgayKichHoat;
    }

    public void setFromNgayKichHoat(Timestamp fromNgayKichHoat) {
        this.fromNgayKichHoat = fromNgayKichHoat;
    }

    public Timestamp getToNgayKichHoat() {
        return toNgayKichHoat;
    }

    public void setToNgayKichHoat(Timestamp toNgayKichHoat) {
        this.toNgayKichHoat = toNgayKichHoat;
    }
}
