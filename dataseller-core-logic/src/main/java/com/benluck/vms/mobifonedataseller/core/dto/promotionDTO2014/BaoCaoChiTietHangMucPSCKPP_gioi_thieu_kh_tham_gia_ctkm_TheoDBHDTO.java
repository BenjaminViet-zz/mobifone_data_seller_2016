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
public class BaoCaoChiTietHangMucPSCKPP_gioi_thieu_kh_tham_gia_ctkm_TheoDBHDTO implements Serializable{
    private static final long serialVersionUID = -224629187978778532L;

    private String branch_Name;
    private String district_Name;
    private String dealer_Code;
    private String dealer_Name;
    private String soEZ;
    private Timestamp ngay_nhan_tin;
    private String thue_bao_kh;
    private Integer ngay_kich;
    private Integer thang_kich;
    private Integer nam_kich;
    private Double cuocPS_thoai;
    private Double cuocPS_sms;
    private Double cuocPS_data;
    private Double cuocPS_khac;
    private Double cuocPS_total;

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

    public Timestamp getNgay_nhan_tin() {
        return ngay_nhan_tin;
    }

    public void setNgay_nhan_tin(Timestamp ngay_nhan_tin) {
        this.ngay_nhan_tin = ngay_nhan_tin;
    }

    public String getThue_bao_kh() {
        return thue_bao_kh;
    }

    public void setThue_bao_kh(String thue_bao_kh) {
        this.thue_bao_kh = thue_bao_kh;
    }

    public Integer getNgay_kich() {
        return ngay_kich;
    }

    public void setNgay_kich(Integer ngay_kich) {
        this.ngay_kich = ngay_kich;
    }

    public Integer getThang_kich() {
        return thang_kich;
    }

    public void setThang_kich(Integer thang_kich) {
        this.thang_kich = thang_kich;
    }

    public Integer getNam_kich() {
        return nam_kich;
    }

    public void setNam_kich(Integer nam_kich) {
        this.nam_kich = nam_kich;
    }

    public Double getCuocPS_thoai() {
        return cuocPS_thoai;
    }

    public void setCuocPS_thoai(Double cuocPS_thoai) {
        this.cuocPS_thoai = cuocPS_thoai;
    }

    public Double getCuocPS_sms() {
        return cuocPS_sms;
    }

    public void setCuocPS_sms(Double cuocPS_sms) {
        this.cuocPS_sms = cuocPS_sms;
    }

    public Double getCuocPS_data() {
        return cuocPS_data;
    }

    public void setCuocPS_data(Double cuocPS_data) {
        this.cuocPS_data = cuocPS_data;
    }

    public Double getCuocPS_khac() {
        return cuocPS_khac;
    }

    public void setCuocPS_khac(Double cuocPS_khac) {
        this.cuocPS_khac = cuocPS_khac;
    }

    public Double getCuocPS_total() {
        return cuocPS_total;
    }

    public void setCuocPS_total(Double cuocPS_total) {
        this.cuocPS_total = cuocPS_total;
    }
}
