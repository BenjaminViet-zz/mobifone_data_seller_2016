package com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/22/14
 * Time: 4:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class ThongTinThueBaoDTO implements Serializable {
    private static final long serialVersionUID = 3609306911680103113L;

    private List<ThongTinThueBaoMaPhieuDTO> thongTinThueBaoMaPhieuList;
    private Integer soDiemHienTai;
    private Integer soLuongPhieuDaDoiQua;
    private String thue_bao;

    public List<ThongTinThueBaoMaPhieuDTO> getThongTinThueBaoMaPhieuList() {
        return thongTinThueBaoMaPhieuList;
    }

    public void setThongTinThueBaoMaPhieuList(List<ThongTinThueBaoMaPhieuDTO> thongTinThueBaoMaPhieuList) {
        this.thongTinThueBaoMaPhieuList = thongTinThueBaoMaPhieuList;
    }

    public Integer getSoDiemHienTai() {
        return soDiemHienTai;
    }

    public void setSoDiemHienTai(Integer soDiemHienTai) {
        this.soDiemHienTai = soDiemHienTai;
    }

    public String getThue_bao() {
        return thue_bao;
    }

    public void setThue_bao(String thue_bao) {
        this.thue_bao = thue_bao;
    }

    public Integer getSoLuongPhieuDaDoiQua() {
        return soLuongPhieuDaDoiQua;
    }

    public void setSoLuongPhieuDaDoiQua(Integer soLuongPhieuDaDoiQua) {
        this.soLuongPhieuDaDoiQua = soLuongPhieuDaDoiQua;
    }

    /*DTO thông tin thuê bao 2015*/
    private String soThueBao;
    private String chuSoHuu;
    private String soCMND;
    private Timestamp ngayDangKy;
    private String goiCuoc;
    private String dbh;

    public String getDbh() {
        return dbh;
    }

    public void setDbh(String dbh) {
        this.dbh = dbh;
    }

    public String getGoiCuoc() {
        return goiCuoc;
    }

    public void setGoiCuoc(String goiCuoc) {
        this.goiCuoc = goiCuoc;
    }

    public String getSoThueBao() {
        return soThueBao;
    }

    public void setSoThueBao(String soThueBao) {
        this.soThueBao = soThueBao;
    }

    public String getChuSoHuu() {
        return chuSoHuu;
    }

    public void setChuSoHuu(String chuSoHuu) {
        this.chuSoHuu = chuSoHuu;
    }

    public String getSoCMND() {
        return soCMND;
    }

    public void setSoCMND(String soCMND) {
        this.soCMND = soCMND;
    }

    public Timestamp getNgayDangKy() {
        return ngayDangKy;
    }

    public void setNgayDangKy(Timestamp ngayDangKy) {
        this.ngayDangKy = ngayDangKy;
    }
}
