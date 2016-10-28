package com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/28/14
 * Time: 9:48 AM
 * To change this template use File | Settings | File Templates.
 */
public class BaoCaoDanhSachMaThuongDTO implements Serializable{
    private static final long serialVersionUID = -5295214304449930948L;
    private String branch_Name;
    private String district_Name;
    private String item_Name;
    private Timestamp ngay_ps;
    private String dealer_Code;
    private String dealer_Name;
    private String so_EZ;
    private String ma_So;
    private Integer trang_thai_trung_thuong;
    private String dat_giai;

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

    public String getItem_Name() {
        return item_Name;
    }

    public void setItem_Name(String item_Name) {
        this.item_Name = item_Name;
    }

    public Timestamp getNgay_ps() {
        return ngay_ps;
    }

    public void setNgay_ps(Timestamp ngay_ps) {
        this.ngay_ps = ngay_ps;
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

    public String getSo_EZ() {
        return so_EZ;
    }

    public void setSo_EZ(String so_EZ) {
        this.so_EZ = so_EZ;
    }

    public String getMa_So() {
        return ma_So;
    }

    public void setMa_So(String ma_So) {
        this.ma_So = ma_So;
    }

    public Integer getTrang_thai_trung_thuong() {
        return trang_thai_trung_thuong;
    }

    public void setTrang_thai_trung_thuong(Integer trang_thai_trung_thuong) {
        this.trang_thai_trung_thuong = trang_thai_trung_thuong;
    }

    public String getDat_giai() {
        return dat_giai;
    }

    public void setDat_giai(String dat_giai) {
        this.dat_giai = dat_giai;
    }
}
