package com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 11/18/14
 * Time: 11:22 AM
 * To change this template use File | Settings | File Templates.
 */
public class BaoCaoChiTietHangMucPSCKPP_gioi_thieu_kh_tham_gia_ctkm_TheoChiNhanhDTO implements Serializable{
    private static final long serialVersionUID = -2154602258881054697L;
    private String branch_Name;
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
