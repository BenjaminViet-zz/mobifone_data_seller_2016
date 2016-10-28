package com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 11/18/14
 * Time: 11:22 AM
 * To change this template use File | Settings | File Templates.
 */
public class BaoCaoChiTietHangMucPSCKPP_MuaTheTuMobiFone_TheoDBHDTO implements Serializable{
    private static final long serialVersionUID = 6624118844356967481L;

    private String branch_Name;
    private String district_Name;
    private String dealer_Code;
    private String dealer_Name;
    private Timestamp ngay_don_hang;
    private String donHang;
    private String tuSerial;
    private String denSerial;
    private Double doanhSoTheTrenHoaDon;

    public String getBranch_Name() {
        return branch_Name;
    }

    public void setBranch_Name(String branch_Name) {
        this.branch_Name = branch_Name;
    }

    public String getDistrict_Name() {
        return district_Name;
    }

    public void setDistrict_Name(String district_Name) {
        this.district_Name = district_Name;
    }

    public String getDealer_Code() {
        return dealer_Code;
    }

    public void setDealer_Code(String dealer_Code) {
        this.dealer_Code = dealer_Code;
    }

    public String getDealer_Name() {
        return dealer_Name;
    }

    public void setDealer_Name(String dealer_Name) {
        this.dealer_Name = dealer_Name;
    }

    public Timestamp getNgay_don_hang() {
        return ngay_don_hang;
    }

    public void setNgay_don_hang(Timestamp ngay_don_hang) {
        this.ngay_don_hang = ngay_don_hang;
    }

    public String getDonHang() {
        return donHang;
    }

    public void setDonHang(String donHang) {
        this.donHang = donHang;
    }

    public String getTuSerial() {
        return tuSerial;
    }

    public void setTuSerial(String tuSerial) {
        this.tuSerial = tuSerial;
    }

    public String getDenSerial() {
        return denSerial;
    }

    public void setDenSerial(String denSerial) {
        this.denSerial = denSerial;
    }

    public Double getDoanhSoTheTrenHoaDon() {
        return doanhSoTheTrenHoaDon;
    }

    public void setDoanhSoTheTrenHoaDon(Double doanhSoTheTrenHoaDon) {
        this.doanhSoTheTrenHoaDon = doanhSoTheTrenHoaDon;
    }
}
