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
public class BaoCaoChiTietHangMucPSCKPP_MuaBTGTuMobiFone_TheoChiNhanhDTO implements Serializable{
    private static final long serialVersionUID = 1993286350784587755L;

    private String branch_Name;
    private Timestamp ngay_don_hang;
    private String donHang;
    private String tuSerial;
    private String denSerial;
    private Double soLuongBTG;

    public String getBranch_Name() {
        return branch_Name;
    }

    public void setBranch_Name(String branch_Name) {
        this.branch_Name = branch_Name;
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

    public Double getSoLuongBTG() {
        return soLuongBTG;
    }

    public void setSoLuongBTG(Double soLuongBTG) {
        this.soLuongBTG = soLuongBTG;
    }
}
