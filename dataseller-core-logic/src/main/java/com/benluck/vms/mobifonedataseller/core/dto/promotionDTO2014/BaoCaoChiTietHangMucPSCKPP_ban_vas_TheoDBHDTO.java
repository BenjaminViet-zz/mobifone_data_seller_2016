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
public class BaoCaoChiTietHangMucPSCKPP_ban_vas_TheoDBHDTO implements Serializable{
    private static final long serialVersionUID = -7555023435922409357L;

    private String branch_Name;
    private String district_Name;
    private String dealer_Code;
    private String dealer_Name;
    private String soEZ;
    private Timestamp ngay_ban_vas;
    private String soThueBaoKH;
    private String loaiVAS;
    private Double soLuong;
    private Double doanhThu;
    private String thue_bao_kh_thuoc_ds_nhan_tin_bhtt;

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

    public Timestamp getNgay_ban_vas() {
        return ngay_ban_vas;
    }

    public void setNgay_ban_vas(Timestamp ngay_ban_vas) {
        this.ngay_ban_vas = ngay_ban_vas;
    }

    public String getSoThueBaoKH() {
        return soThueBaoKH;
    }

    public void setSoThueBaoKH(String soThueBaoKH) {
        this.soThueBaoKH = soThueBaoKH;
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

    public String getThue_bao_kh_thuoc_ds_nhan_tin_bhtt() {
        return thue_bao_kh_thuoc_ds_nhan_tin_bhtt;
    }

    public void setThue_bao_kh_thuoc_ds_nhan_tin_bhtt(String thue_bao_kh_thuoc_ds_nhan_tin_bhtt) {
        this.thue_bao_kh_thuoc_ds_nhan_tin_bhtt = thue_bao_kh_thuoc_ds_nhan_tin_bhtt;
    }
}
