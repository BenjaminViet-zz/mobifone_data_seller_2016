package com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015;

import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BranchDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.DistrictDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.RetailDealerDTO;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: HP
 * Date: 3/2/15
 * Time: 4:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class BaoCaoTinNhanDTO implements Serializable {
    private static final long serialVersionUID = 7311914988557195526L;

    private BranchDTO branch;
    private DistrictDTO district;
    private RetailDealerDTO retailDealer;
    private Integer soLuongThanhCong;
    private Integer soLuongDuocChiTra;
    private Integer soLuongKhongThanhCong;
    private String soEZ;
    private PromPackageDTO promPackage;
    private String tinhTrang = "-1";
    private String trangThaiChiTra = "-1";
    private Timestamp fromDate;
    private Timestamp toDate;
    private String serial;

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public BranchDTO getBranch() {
        return branch;
    }

    public void setBranch(BranchDTO branch) {
        this.branch = branch;
    }

    public DistrictDTO getDistrict() {
        return district;
    }

    public void setDistrict(DistrictDTO district) {
        this.district = district;
    }

    public RetailDealerDTO getRetailDealer() {
        return retailDealer;
    }

    public void setRetailDealer(RetailDealerDTO retailDealer) {
        this.retailDealer = retailDealer;
    }

    public Integer getSoLuongThanhCong() {
        return soLuongThanhCong;
    }

    public void setSoLuongThanhCong(Integer soLuongThanhCong) {
        this.soLuongThanhCong = soLuongThanhCong;
    }

    public Integer getSoLuongDuocChiTra() {
        return soLuongDuocChiTra;
    }

    public void setSoLuongDuocChiTra(Integer soLuongDuocChiTra) {
        this.soLuongDuocChiTra = soLuongDuocChiTra;
    }

    public Integer getSoLuongKhongThanhCong() {
        return soLuongKhongThanhCong;
    }

    public void setSoLuongKhongThanhCong(Integer soLuongKhongThanhCong) {
        this.soLuongKhongThanhCong = soLuongKhongThanhCong;
    }

    public String getSoEZ() {
        return soEZ;
    }

    public void setSoEZ(String soEZ) {
        this.soEZ = soEZ;
    }

    public PromPackageDTO getPromPackage() {
        return promPackage;
    }

    public void setPromPackage(PromPackageDTO promPackage) {
        this.promPackage = promPackage;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public String getTrangThaiChiTra() {
        return trangThaiChiTra;
    }

    public void setTrangThaiChiTra(String trangThaiChiTra) {
        this.trangThaiChiTra = trangThaiChiTra;
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
}
