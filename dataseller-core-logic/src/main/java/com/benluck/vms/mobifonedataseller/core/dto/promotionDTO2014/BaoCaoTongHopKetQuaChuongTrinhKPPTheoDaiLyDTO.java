package com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 11/16/14
 * Time: 9:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class BaoCaoTongHopKetQuaChuongTrinhKPPTheoDaiLyDTO implements Serializable{
    private static final long serialVersionUID = 3342917914866840574L;
    private String branch_Name;
    private String district_Name;
    private Integer giaiDoan;
    private Timestamp ngay;
    private String dealer_Code;
    private String dealer_Name;
    private String trang_thai_dat;
    private Double soLuongBTG_MuaTuMobifone;
    private Double doanhThuMuaThe;
    private Double soLuongVAS;
    private Double doanhThu_VAS;
    private Double soLuongTon_BanTrucTiepBTG;
    private Double soLuongMoi_BanTrucTiepBTG;
    private Double cuocPSBoTon_BanTrucTiepBTG;
    private Double cuocPSBoMoi_BanTrucTiepBTG;
    private Double soLuong_GioiThieuKH;
    private Double soLuongPSCuoc_GioiThieuKH;
    private Double cuocPS_GioiThieuKH;

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

    public Integer getGiaiDoan() {
        return giaiDoan;
    }

    public void setGiaiDoan(Integer giaiDoan) {
        this.giaiDoan = giaiDoan;
    }

    public Timestamp getNgay() {
        return ngay;
    }

    public void setNgay(Timestamp ngay) {
        this.ngay = ngay;
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

    public String getTrang_thai_dat() {
        return trang_thai_dat;
    }

    public void setTrang_thai_dat(String trang_thai_dat) {
        this.trang_thai_dat = trang_thai_dat;
    }

    public Double getDoanhThuMuaThe() {
        return doanhThuMuaThe;
    }

    public void setDoanhThuMuaThe(Double doanhThuMuaThe) {
        this.doanhThuMuaThe = doanhThuMuaThe;
    }

    public Double getSoLuongVAS() {
        return soLuongVAS;
    }

    public void setSoLuongVAS(Double soLuongVAS) {
        this.soLuongVAS = soLuongVAS;
    }

    public Double getDoanhThu_VAS() {
        return doanhThu_VAS;
    }

    public void setDoanhThu_VAS(Double doanhThu_VAS) {
        this.doanhThu_VAS = doanhThu_VAS;
    }

    public Double getSoLuongTon_BanTrucTiepBTG() {
        return soLuongTon_BanTrucTiepBTG;
    }

    public void setSoLuongTon_BanTrucTiepBTG(Double soLuongTon_BanTrucTiepBTG) {
        this.soLuongTon_BanTrucTiepBTG = soLuongTon_BanTrucTiepBTG;
    }

    public Double getSoLuongMoi_BanTrucTiepBTG() {
        return soLuongMoi_BanTrucTiepBTG;
    }

    public void setSoLuongMoi_BanTrucTiepBTG(Double soLuongMoi_BanTrucTiepBTG) {
        this.soLuongMoi_BanTrucTiepBTG = soLuongMoi_BanTrucTiepBTG;
    }

    public Double getCuocPSBoTon_BanTrucTiepBTG() {
        return cuocPSBoTon_BanTrucTiepBTG;
    }

    public void setCuocPSBoTon_BanTrucTiepBTG(Double cuocPSBoTon_BanTrucTiepBTG) {
        this.cuocPSBoTon_BanTrucTiepBTG = cuocPSBoTon_BanTrucTiepBTG;
    }

    public Double getCuocPSBoMoi_BanTrucTiepBTG() {
        return cuocPSBoMoi_BanTrucTiepBTG;
    }

    public void setCuocPSBoMoi_BanTrucTiepBTG(Double cuocPSBoMoi_BanTrucTiepBTG) {
        this.cuocPSBoMoi_BanTrucTiepBTG = cuocPSBoMoi_BanTrucTiepBTG;
    }

    public Double getSoLuong_GioiThieuKH() {
        return soLuong_GioiThieuKH;
    }

    public void setSoLuong_GioiThieuKH(Double soLuong_GioiThieuKH) {
        this.soLuong_GioiThieuKH = soLuong_GioiThieuKH;
    }

    public Double getSoLuongPSCuoc_GioiThieuKH() {
        return soLuongPSCuoc_GioiThieuKH;
    }

    public void setSoLuongPSCuoc_GioiThieuKH(Double soLuongPSCuoc_GioiThieuKH) {
        this.soLuongPSCuoc_GioiThieuKH = soLuongPSCuoc_GioiThieuKH;
    }

    public Double getCuocPS_GioiThieuKH() {
        return cuocPS_GioiThieuKH;
    }

    public void setCuocPS_GioiThieuKH(Double cuocPS_GioiThieuKH) {
        this.cuocPS_GioiThieuKH = cuocPS_GioiThieuKH;
    }

    public Double getSoLuongBTG_MuaTuMobifone() {
        return soLuongBTG_MuaTuMobifone;
    }

    public void setSoLuongBTG_MuaTuMobifone(Double soLuongBTG_MuaTuMobifone) {
        this.soLuongBTG_MuaTuMobifone = soLuongBTG_MuaTuMobifone;
    }
}
