package com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/28/14
 * Time: 9:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class BaoCaoTienThuongMaDuThuongDTO implements Serializable{
    private static final long serialVersionUID = 208955721144333430L;

    private String branch_Name;
    private String district_Name;
    private String dealer_Code;
    private String dealer_Name;
    private String address;
    private String item_Name;
    private Integer ngay;
    private Integer thang;
    private Integer nam;
    private Double soThucTe;
    private Double soDuocTinhDiem;
    private Double soDuocQuiDoi;
    private Double soDiemQuiDoi;
    private Double soTienQuiDoi;
    private Integer soMaDuThuong;
    private String trang_thai_tra_thuong;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getItem_Name() {
        return item_Name;
    }

    public void setItem_Name(String item_Name) {
        this.item_Name = item_Name;
    }

    public Integer getNgay() {
        return ngay;
    }

    public void setNgay(Integer ngay) {
        this.ngay = ngay;
    }

    public Integer getThang() {
        return thang;
    }

    public void setThang(Integer thang) {
        this.thang = thang;
    }

    public Integer getNam() {
        return nam;
    }

    public void setNam(Integer nam) {
        this.nam = nam;
    }

    public Double getSoThucTe() {
        return soThucTe;
    }

    public void setSoThucTe(Double soThucTe) {
        this.soThucTe = soThucTe;
    }

    public Double getSoDuocTinhDiem() {
        return soDuocTinhDiem;
    }

    public void setSoDuocTinhDiem(Double soDuocTinhDiem) {
        this.soDuocTinhDiem = soDuocTinhDiem;
    }

    public Double getSoDuocQuiDoi() {
        return soDuocQuiDoi;
    }

    public void setSoDuocQuiDoi(Double soDuocQuiDoi) {
        this.soDuocQuiDoi = soDuocQuiDoi;
    }

    public Double getSoDiemQuiDoi() {
        return soDiemQuiDoi;
    }

    public void setSoDiemQuiDoi(Double soDiemQuiDoi) {
        this.soDiemQuiDoi = soDiemQuiDoi;
    }

    public Double getSoTienQuiDoi() {
        return soTienQuiDoi;
    }

    public void setSoTienQuiDoi(Double soTienQuiDoi) {
        this.soTienQuiDoi = soTienQuiDoi;
    }

    public Integer getSoMaDuThuong() {
        return soMaDuThuong;
    }

    public void setSoMaDuThuong(Integer soMaDuThuong) {
        this.soMaDuThuong = soMaDuThuong;
    }

    public String getTrang_thai_tra_thuong() {
        return trang_thai_tra_thuong;
    }

    public void setTrang_thai_tra_thuong(String trang_thai_tra_thuong) {
        this.trang_thai_tra_thuong = trang_thai_tra_thuong;
    }
}
