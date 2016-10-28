package com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 11/18/14
 * Time: 11:22 AM
 * To change this template use File | Settings | File Templates.
 */
public class BaoCaoChiTietHangMucPSCKPP_ban_vas_TheoQuanHuyenDTO implements Serializable{
    private static final long serialVersionUID = 6765559797794678147L;
    private String branch_Name;
    private String district_Name;
    private String loaiVAS;
    private Double soLuong;
    private Double doanhThu;

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

    public String getLoaiVAS() {
        return loaiVAS;
    }

    public void setLoaiVAS(String loaiVAS) {
        this.loaiVAS = loaiVAS;
    }

    public Double getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Double soLuong) {
        this.soLuong = soLuong;
    }

    public Double getDoanhThu() {
        return doanhThu;
    }

    public void setDoanhThu(Double doanhThu) {
        this.doanhThu = doanhThu;
    }
}
