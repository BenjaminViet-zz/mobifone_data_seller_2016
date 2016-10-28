package com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: HauKute
 * Date: 10/25/14
 * Time: 3:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class BaoCaoTheoHangMucPhatSinhDTO implements Serializable {
    private static final long serialVersionUID = -9010201856967477676L;

    private String branch_Name;
    private String district_Name;
    private String item_Name;
    private Double soPS;
    private String donViTinh;
    private String dealer_Code;
    private String dealer_Name;
    private Integer cycle;
    private Double quyDiem;
    private Double soTienTuongUng;
    private Integer soMaDuThuong;
    private Integer du_dieu_kien_status;

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

    public Double getSoPS() {
        return soPS;
    }

    public void setSoPS(Double soPS) {
        this.soPS = soPS;
    }

    public String getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
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

    public Integer getCycle() {
        return cycle;
    }

    public void setCycle(Integer cycle) {
        this.cycle = cycle;
    }

    public Double getQuyDiem() {
        return quyDiem;
    }

    public void setQuyDiem(Double quyDiem) {
        this.quyDiem = quyDiem;
    }

    public Double getSoTienTuongUng() {
        return soTienTuongUng;
    }

    public void setSoTienTuongUng(Double soTienTuongUng) {
        this.soTienTuongUng = soTienTuongUng;
    }

    public Integer getSoMaDuThuong() {
        return soMaDuThuong;
    }

    public void setSoMaDuThuong(Integer soMaDuThuong) {
        this.soMaDuThuong = soMaDuThuong;
    }

    public Integer getDu_dieu_kien_status() {
        return du_dieu_kien_status;
    }

    public void setDu_dieu_kien_status(Integer du_dieu_kien_status) {
        this.du_dieu_kien_status = du_dieu_kien_status;
    }
}
