package com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 11/19/14
 * Time: 9:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class CanhBaoKPPDTO implements Serializable{
    private static final long serialVersionUID = -3522055472672178014L;
    private Integer chuKy;
    private String item_Name;
    private Double soPS;
    private Double soDiemDaDat;
    private Double soPhaiDat;
    private Timestamp ngayTongHop;
    private Integer thoiGianConLai;

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

    public Double getSoPS() {
        return soPS;
    }

    public void setSoPS(Double soPS) {
        this.soPS = soPS;
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

    public Timestamp getNgayTongHop() {
        return ngayTongHop;
    }

    public void setNgayTongHop(Timestamp ngayTongHop) {
        this.ngayTongHop = ngayTongHop;
    }
}
