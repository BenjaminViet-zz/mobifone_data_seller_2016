package com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/22/14
 * Time: 3:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class BaoCaoQuanLyTinNhanDTO implements Serializable {
    private static final long serialVersionUID = 6374552019031672059L;

    private String doiTuong;
    private Timestamp thoiGianNhanTin;
    private String noiDungTinNhan;
    private Integer soLuongTB;
    private Integer soLuongTinNhan;
    private Integer soLuongTinNhanThanhCong;
    private Integer soLuongTinNhanKhongThanhCong;

    public String getDoiTuong() {
        return doiTuong;
    }

    public void setDoiTuong(String doiTuong) {
        this.doiTuong = doiTuong;
    }

    public Timestamp getThoiGianNhanTin() {
        return thoiGianNhanTin;
    }

    public void setThoiGianNhanTin(Timestamp thoiGianNhanTin) {
        this.thoiGianNhanTin = thoiGianNhanTin;
    }

    public String getNoiDungTinNhan() {
        return noiDungTinNhan;
    }

    public void setNoiDungTinNhan(String noiDungTinNhan) {
        this.noiDungTinNhan = noiDungTinNhan;
    }

    public Integer getSoLuongTB() {
        return soLuongTB;
    }

    public void setSoLuongTB(Integer soLuongTB) {
        this.soLuongTB = soLuongTB;
    }

    public Integer getSoLuongTinNhan() {
        return soLuongTinNhan;
    }

    public void setSoLuongTinNhan(Integer soLuongTinNhan) {
        this.soLuongTinNhan = soLuongTinNhan;
    }

    public Integer getSoLuongTinNhanThanhCong() {
        return soLuongTinNhanThanhCong;
    }

    public void setSoLuongTinNhanThanhCong(Integer soLuongTinNhanThanhCong) {
        this.soLuongTinNhanThanhCong = soLuongTinNhanThanhCong;
    }

    public Integer getSoLuongTinNhanKhongThanhCong() {
        return soLuongTinNhanKhongThanhCong;
    }

    public void setSoLuongTinNhanKhongThanhCong(Integer soLuongTinNhanKhongThanhCong) {
        this.soLuongTinNhanKhongThanhCong = soLuongTinNhanKhongThanhCong;
    }
}
