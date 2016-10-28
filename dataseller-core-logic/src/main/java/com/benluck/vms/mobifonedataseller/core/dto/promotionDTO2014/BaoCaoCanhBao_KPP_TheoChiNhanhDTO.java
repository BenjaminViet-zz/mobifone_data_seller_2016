package com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 11/18/14
 * Time: 11:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class BaoCaoCanhBao_KPP_TheoChiNhanhDTO implements Serializable{
    private static final long serialVersionUID = 60244200672035280L;
    private String branch_Name;
    private Integer chuKy;
    private String item_Name;
    private Double soPhatSinh;
    private Double soDiemDaDat;
    private Double soPhaiDat;
    private Integer thoiGianConLai;

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

    public Integer getThoiGianConLai() {
        return thoiGianConLai;
    }

    public void setThoiGianConLai(Integer thoiGianConLai) {
        this.thoiGianConLai = thoiGianConLai;
    }

    public Integer getChuKy() {
        return chuKy;
    }

    public void setChuKy(Integer chuKy) {
        this.chuKy = chuKy;
    }

    public Double getSoPhatSinh() {
        return soPhatSinh;
    }

    public void setSoPhatSinh(Double soPhatSinh) {
        this.soPhatSinh = soPhatSinh;
    }

    public Double getSoDiemDaDat() {
        return soDiemDaDat;
    }

    public void setSoDiemDaDat(Double soDiemDaDat) {
        this.soDiemDaDat = soDiemDaDat;
    }

    public Double getSoPhaiDat() {
        return soPhaiDat;
    }

    public void setSoPhaiDat(Double soPhaiDat) {
        this.soPhaiDat = soPhaiDat;
    }
}
