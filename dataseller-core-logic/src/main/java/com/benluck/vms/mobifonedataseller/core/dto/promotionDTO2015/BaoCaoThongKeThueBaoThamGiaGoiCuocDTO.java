package com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015;

import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BranchDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.DistrictDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.RetailDealerDTO;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: HP
 * Date: 2/26/15
 * Time: 2:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class BaoCaoThongKeThueBaoThamGiaGoiCuocDTO implements Serializable{
    private static final long serialVersionUID = -4267943535588806543L;

    private BranchDTO branch;
    private DistrictDTO district;
    private RetailDealerDTO retailDealer;
    private String soEZ;
    private String soThueBao;
    private PromPackageDTO promPackage;
    private Timestamp thoiGianDK;
    private String tinhTrang = "-1";
    private String tinhTrangChiTra = "-1";
    private String promConditionStatus;
    private String promConditionError;
    private String transError;
    private String transStatus;
    private Timestamp fromTransDate;
    private Timestamp toTransDate;
    private Timestamp fromDate;
    private Timestamp toDate;
    private String serial;
    private RegistrationTransDTO registrationTrans;
    private Timestamp ngayTongHop;

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

    public String getSoEZ() {
        return soEZ;
    }

    public void setSoEZ(String soEZ) {
        this.soEZ = soEZ;
    }

    public String getSoThueBao() {
        return soThueBao;
    }

    public void setSoThueBao(String soThueBao) {
        this.soThueBao = soThueBao;
    }

    public PromPackageDTO getPromPackage() {
        return promPackage;
    }

    public void setPromPackage(PromPackageDTO promPackage) {
        this.promPackage = promPackage;
    }

    public Timestamp getThoiGianDK() {
        return thoiGianDK;
    }

    public void setThoiGianDK(Timestamp thoiGianDK) {
        this.thoiGianDK = thoiGianDK;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public String getTinhTrangChiTra() {
        return tinhTrangChiTra;
    }

    public void setTinhTrangChiTra(String tinhTrangChiTra) {
        this.tinhTrangChiTra = tinhTrangChiTra;
    }

    public String getPromConditionStatus() {
        return promConditionStatus;
    }

    public void setPromConditionStatus(String promConditionStatus) {
        this.promConditionStatus = promConditionStatus;
    }

    public String getPromConditionError() {
        return promConditionError;
    }

    public void setPromConditionError(String promConditionError) {
        this.promConditionError = promConditionError;
    }

    public String getTransError() {
        return transError;
    }

    public void setTransError(String transError) {
        this.transError = transError;
    }

    public String getTransStatus() {
        return transStatus;
    }

    public void setTransStatus(String transStatus) {
        this.transStatus = transStatus;
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

    public RegistrationTransDTO getRegistrationTrans() {
        return registrationTrans;
    }

    public void setRegistrationTrans(RegistrationTransDTO registrationTrans) {
        this.registrationTrans = registrationTrans;
    }

    public Timestamp getNgayTongHop() {
        return ngayTongHop;
    }

    public void setNgayTongHop(Timestamp ngayTongHop) {
        this.ngayTongHop = ngayTongHop;
    }

    public Timestamp getFromTransDate() {
        return fromTransDate;
    }

    public void setFromTransDate(Timestamp fromTransDate) {
        this.fromTransDate = fromTransDate;
    }

    public Timestamp getToTransDate() {
        return toTransDate;
    }

    public void setToTransDate(Timestamp toTransDate) {
        this.toTransDate = toTransDate;
    }
}
