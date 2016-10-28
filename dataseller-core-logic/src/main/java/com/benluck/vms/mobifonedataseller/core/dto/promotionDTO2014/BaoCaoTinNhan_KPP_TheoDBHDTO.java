package com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 11/18/14
 * Time: 11:22 AM
 * To change this template use File | Settings | File Templates.
 */
public class BaoCaoTinNhan_KPP_TheoDBHDTO implements Serializable{
    private static final long serialVersionUID = 4480971008540180078L;

    private String branch_Name;
    private String district_Name;
    private String dealer_Code;
    private String dealer_Name;
    private String item_Name;
    private String soEZ;
    private Double soLuongThanhCong;
    private Double soEZChuaDangKy;
    private Double soThueBaoKhongThuocDoiTuongThamGia;
    private Double soThueBaoDaDuocDangKy;
    private Double soThueBaoDangChoXacNhan;
    private Double soCuPhapKhongDung;

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

    public String getSoEZ() {
        return soEZ;
    }

    public void setSoEZ(String soEZ) {
        this.soEZ = soEZ;
    }

    public String getItem_Name() {
        return item_Name;
    }

    public void setItem_Name(String item_Name) {
        this.item_Name = item_Name;
    }

    public Double getSoLuongThanhCong() {
        return soLuongThanhCong;
    }

    public void setSoLuongThanhCong(Double soLuongThanhCong) {
        this.soLuongThanhCong = soLuongThanhCong;
    }

    public Double getSoEZChuaDangKy() {
        return soEZChuaDangKy;
    }

    public void setSoEZChuaDangKy(Double soEZChuaDangKy) {
        this.soEZChuaDangKy = soEZChuaDangKy;
    }

    public Double getSoThueBaoKhongThuocDoiTuongThamGia() {
        return soThueBaoKhongThuocDoiTuongThamGia;
    }

    public void setSoThueBaoKhongThuocDoiTuongThamGia(Double soThueBaoKhongThuocDoiTuongThamGia) {
        this.soThueBaoKhongThuocDoiTuongThamGia = soThueBaoKhongThuocDoiTuongThamGia;
    }

    public Double getSoThueBaoDaDuocDangKy() {
        return soThueBaoDaDuocDangKy;
    }

    public void setSoThueBaoDaDuocDangKy(Double soThueBaoDaDuocDangKy) {
        this.soThueBaoDaDuocDangKy = soThueBaoDaDuocDangKy;
    }

    public Double getSoThueBaoDangChoXacNhan() {
        return soThueBaoDangChoXacNhan;
    }

    public void setSoThueBaoDangChoXacNhan(Double soThueBaoDangChoXacNhan) {
        this.soThueBaoDangChoXacNhan = soThueBaoDangChoXacNhan;
    }

    public Double getSoCuPhapKhongDung() {
        return soCuPhapKhongDung;
    }

    public void setSoCuPhapKhongDung(Double soCuPhapKhongDung) {
        this.soCuPhapKhongDung = soCuPhapKhongDung;
    }
}
