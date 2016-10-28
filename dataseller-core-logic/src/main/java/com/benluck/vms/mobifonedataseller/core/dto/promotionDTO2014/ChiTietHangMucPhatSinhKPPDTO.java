package com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/29/14
 * Time: 1:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class ChiTietHangMucPhatSinhKPPDTO implements Serializable{
    private static final long serialVersionUID = 7303664264249153192L;

    private Long dealer_Id;
    private Long loaiItemId;        // hard code.
    private String dealer_Code;
    private String dealer_Name;
    private String soHoaDon;
    private Timestamp ngayHoaDon;
    private String maHangHoa;
    private String tenHangHoa;
    private Integer soLuong;
    private Double giaTri;
    private String soEZ;
    private Timestamp ngay_ps;
    private String soKhachHang;
    private String so_thue_bao;
    private Double cuoc_ps;
    private Double soDiemQuiDoi;
    private String noiDung;
    private String maGiaoDich;
    private String maGoi;
    private String tenGoi;
    private Integer chuKy;
    private String noiDungTinNhan;
    private String ketQua;
    private Timestamp ngayNhanTin;
    private Timestamp ngayKichHoat;

    public Long getLoaiItemId() {
        return loaiItemId;
    }

    public void setLoaiItemId(Long loaiItemId) {
        this.loaiItemId = loaiItemId;
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

    public String getSoHoaDon() {
        return soHoaDon;
    }

    public void setSoHoaDon(String soHoaDon) {
        this.soHoaDon = soHoaDon;
    }

    public Timestamp getNgayHoaDon() {
        return ngayHoaDon;
    }

    public void setNgayHoaDon(Timestamp ngayHoaDon) {
        this.ngayHoaDon = ngayHoaDon;
    }

    public String getMaHangHoa() {
        return maHangHoa;
    }

    public void setMaHangHoa(String maHangHoa) {
        this.maHangHoa = maHangHoa;
    }

    public String getTenHangHoa() {
        return tenHangHoa;
    }

    public void setTenHangHoa(String tenHangHoa) {
        this.tenHangHoa = tenHangHoa;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    public Double getGiaTri() {
        return giaTri;
    }

    public void setGiaTri(Double giaTri) {
        this.giaTri = giaTri;
    }

    public String getSoEZ() {
        return soEZ;
    }

    public void setSoEZ(String soEZ) {
        this.soEZ = soEZ;
    }

    public Timestamp getNgay_ps() {
        return ngay_ps;
    }

    public void setNgay_ps(Timestamp ngay_ps) {
        this.ngay_ps = ngay_ps;
    }

    public String getSoKhachHang() {
        return soKhachHang;
    }

    public void setSoKhachHang(String soKhachHang) {
        this.soKhachHang = soKhachHang;
    }

    public String getSo_thue_bao() {
        return so_thue_bao;
    }

    public void setSo_thue_bao(String so_thue_bao) {
        this.so_thue_bao = so_thue_bao;
    }

    public Double getCuoc_ps() {
        return cuoc_ps;
    }

    public void setCuoc_ps(Double cuoc_ps) {
        this.cuoc_ps = cuoc_ps;
    }

    public Double getSoDiemQuiDoi() {
        return soDiemQuiDoi;
    }

    public void setSoDiemQuiDoi(Double soDiemQuiDoi) {
        this.soDiemQuiDoi = soDiemQuiDoi;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getMaGiaoDich() {
        return maGiaoDich;
    }

    public void setMaGiaoDich(String maGiaoDich) {
        this.maGiaoDich = maGiaoDich;
    }

    public String getMaGoi() {
        return maGoi;
    }

    public void setMaGoi(String maGoi) {
        this.maGoi = maGoi;
    }

    public String getTenGoi() {
        return tenGoi;
    }

    public void setTenGoi(String tenGoi) {
        this.tenGoi = tenGoi;
    }

    public Integer getChuKy() {
        return chuKy;
    }

    public void setChuKy(Integer chuKy) {
        this.chuKy = chuKy;
    }

    public Long getDealer_Id() {
        return dealer_Id;
    }

    public void setDealer_Id(Long dealer_Id) {
        this.dealer_Id = dealer_Id;
    }

    public String getNoiDungTinNhan() {
        return noiDungTinNhan;
    }

    public void setNoiDungTinNhan(String noiDungTinNhan) {
        this.noiDungTinNhan = noiDungTinNhan;
    }

    public String getKetQua() {
        return ketQua;
    }

    public void setKetQua(String ketQua) {
        this.ketQua = ketQua;
    }

    public Timestamp getNgayNhanTin() {
        return ngayNhanTin;
    }

    public void setNgayNhanTin(Timestamp ngayNhanTin) {
        this.ngayNhanTin = ngayNhanTin;
    }

    public Timestamp getNgayKichHoat() {
        return ngayKichHoat;
    }

    public void setNgayKichHoat(Timestamp ngayKichHoat) {
        this.ngayKichHoat = ngayKichHoat;
    }
}
