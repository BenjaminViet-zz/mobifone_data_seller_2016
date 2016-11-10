package com.benluck.vms.mobifonedataseller.core.dto;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/9/16
 * Time: 21:54
 * To change this template use File | Settings | File Templates.
 */
public class MBDReportDetailExpenseDTO implements Serializable{
    private static final long serialVersionUID = 6152971691370936337L;
    private Long costId;
    private Long custId;
    private String empCode;
    private String isdn;
    private String empName;
    private String maNVPhatTrien;
    private String loaiHM;
    private String loaiTB;
    private String loaiKH;
    private Timestamp ngayDauNoi;
    private Timestamp ngayNopHoSo;
    private String trangThaiChanCat;
    private String trangThaiThueBao;
    private Double cuocThucThu;
    private String chuKy;
    private Double hoaHong;

    public Long getCostId() {
        return costId;
    }

    public void setCostId(Long costId) {
        this.costId = costId;
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getIsdn() {
        return isdn;
    }

    public void setIsdn(String isdn) {
        this.isdn = isdn;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getMaNVPhatTrien() {
        return maNVPhatTrien;
    }

    public void setMaNVPhatTrien(String maNVPhatTrien) {
        this.maNVPhatTrien = maNVPhatTrien;
    }

    public String getLoaiHM() {
        return loaiHM;
    }

    public void setLoaiHM(String loaiHM) {
        this.loaiHM = loaiHM;
    }

    public String getLoaiTB() {
        return loaiTB;
    }

    public void setLoaiTB(String loaiTB) {
        this.loaiTB = loaiTB;
    }

    public String getLoaiKH() {
        return loaiKH;
    }

    public void setLoaiKH(String loaiKH) {
        this.loaiKH = loaiKH;
    }

    public Timestamp getNgayDauNoi() {
        return ngayDauNoi;
    }

    public void setNgayDauNoi(Timestamp ngayDauNoi) {
        this.ngayDauNoi = ngayDauNoi;
    }

    public Timestamp getNgayNopHoSo() {
        return ngayNopHoSo;
    }

    public void setNgayNopHoSo(Timestamp ngayNopHoSo) {
        this.ngayNopHoSo = ngayNopHoSo;
    }

    public String getTrangThaiChanCat() {
        return trangThaiChanCat;
    }

    public void setTrangThaiChanCat(String trangThaiChanCat) {
        this.trangThaiChanCat = trangThaiChanCat;
    }

    public String getTrangThaiThueBao() {
        return trangThaiThueBao;
    }

    public void setTrangThaiThueBao(String trangThaiThueBao) {
        this.trangThaiThueBao = trangThaiThueBao;
    }

    public Double getCuocThucThu() {
        return cuocThucThu;
    }

    public void setCuocThucThu(Double cuocThucThu) {
        this.cuocThucThu = cuocThucThu;
    }

    public String getChuKy() {
        return chuKy;
    }

    public void setChuKy(String chuKy) {
        this.chuKy = chuKy;
    }

    public Double getHoaHong() {
        return hoaHong;
    }

    public void setHoaHong(Double hoaHong) {
        this.hoaHong = hoaHong;
    }
}
