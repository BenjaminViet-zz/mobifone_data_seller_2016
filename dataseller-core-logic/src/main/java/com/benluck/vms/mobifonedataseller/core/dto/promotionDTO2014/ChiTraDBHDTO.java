package com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: HP
 * Date: 2/6/15
 * Time: 6:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class ChiTraDBHDTO implements Serializable{
    public String getChiNhanh() {
        return chiNhanh;
    }

    public void setChiNhanh(String chiNhanh) {
        this.chiNhanh = chiNhanh;
    }

    public String getDiemBanHang() {
        return diemBanHang;
    }

    public void setDiemBanHang(String diemBanHang) {
        this.diemBanHang = diemBanHang;
    }

    public Timestamp getTuNgay() {
        return tuNgay;
    }

    public void setTuNgay(Timestamp tuNgay) {
        this.tuNgay = tuNgay;
    }

    public Timestamp getDenNgay() {
        return denNgay;
    }

    public void setDenNgay(Timestamp denNgay) {
        this.denNgay = denNgay;
    }

    public double getSoTien() {
        return soTien;
    }

    public void setSoTien(double soTien) {
        this.soTien = soTien;
    }

    public Integer getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Integer trangThai) {
        this.trangThai = trangThai;
    }

    private String chiNhanh;
    private String diemBanHang;
    private Timestamp tuNgay;
    private Timestamp denNgay;
    private double soTien;
    private Integer trangThai;
}
