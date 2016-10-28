package com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014;

import java.io.Serializable;
import java.security.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: HP
 * Date: 2/6/15
 * Time: 5:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class DangKyGoiCuoc2015DTO implements Serializable{

    public String getSoThueBao() {
        return soThueBao;
    }

    public void setSoThueBao(String soThueBao) {
        this.soThueBao = soThueBao;
    }

    public String getGoiCuoc() {
        return goiCuoc;
    }

    public void setGoiCuoc(String goiCuoc) {
        this.goiCuoc = goiCuoc;
    }

    public Timestamp getNgayHieuLuc() {
        return ngayHieuLuc;
    }

    public void setNgayHieuLuc(Timestamp ngayHieuLuc) {
        this.ngayHieuLuc = ngayHieuLuc;
    }

    public Timestamp getNgayHetHan() {
        return ngayHetHan;
    }

    public void setNgayHetHan(Timestamp ngayHetHan) {
        this.ngayHetHan = ngayHetHan;
    }

    private String soThueBao;
    private String goiCuoc;
    private Timestamp ngayHieuLuc;
    private Timestamp ngayHetHan;
}
