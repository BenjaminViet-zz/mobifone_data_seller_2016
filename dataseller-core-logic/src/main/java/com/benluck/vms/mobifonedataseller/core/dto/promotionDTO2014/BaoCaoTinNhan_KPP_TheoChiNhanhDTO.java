package com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 11/18/14
 * Time: 11:22 AM
 * To change this template use File | Settings | File Templates.
 */
public class BaoCaoTinNhan_KPP_TheoChiNhanhDTO implements Serializable{
    private static final long serialVersionUID = -6219816011195745429L;
    private String branch_Name;
    private String item_Name;
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
