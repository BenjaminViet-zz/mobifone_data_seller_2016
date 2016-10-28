package com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: HP
 * Date: 2/26/15
 * Time: 10:59 AM
 * To change this template use File | Settings | File Templates.
 */
public class BaoCaoSoEZNhanTinThamGiaChuongTrinhDTO implements Serializable {
    private static final long serialVersionUID = 1949707876487501527L;

    private String soEZDangKy;
    private String thueBaoKH;
    private PromPackageDTO goiCuoc;
    private Timestamp thoiGianDK;
    private Double mucKhuyenKhich;
    private String transStatus;
    private String transError;
    private String serial;
    private Long branchId;

    private Timestamp fromDate;
    private Timestamp toDate;

    public String getTransStatus() {
        return transStatus;
    }

    public void setTransStatus(String transStatus) {
        this.transStatus = transStatus;
    }

    public String getTransError() {
        return transError;
    }

    public void setTransError(String transError) {
        this.transError = transError;
    }

    public Timestamp getFromDate() {
        return fromDate;
    }

    public void setFromDate(Timestamp fromDate) {
        this.fromDate = fromDate;
    }

    public Timestamp getToDate() {
        return toDate;
    }

    public void setToDate(Timestamp toDate) {
        this.toDate = toDate;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getSoEZDangKy() {
        return soEZDangKy;
    }

    public void setSoEZDangKy(String soEZDangKy) {
        this.soEZDangKy = soEZDangKy;
    }

    public String getThueBaoKH() {
        return thueBaoKH;
    }

    public void setThueBaoKH(String thueBaoKH) {
        this.thueBaoKH = thueBaoKH;
    }

    public PromPackageDTO getGoiCuoc() {
        return goiCuoc;
    }

    public void setGoiCuoc(PromPackageDTO goiCuoc) {
        this.goiCuoc = goiCuoc;
    }

    public Timestamp getThoiGianDK() {
        return thoiGianDK;
    }

    public void setThoiGianDK(Timestamp thoiGianDK) {
        this.thoiGianDK = thoiGianDK;
    }

    public Double getMucKhuyenKhich() {
        return mucKhuyenKhich;
    }

    public void setMucKhuyenKhich(Double mucKhuyenKhich) {
        this.mucKhuyenKhich = mucKhuyenKhich;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }
}
