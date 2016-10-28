package com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015;

import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BranchDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.DistrictDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.RetailDealerDTO;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 3/5/15
 * Time: 11:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class BaoCaoChiTraKhuyenKhichChoDBHDTO implements Serializable{
    private static final long serialVersionUID = -4606524352333422847L;

    private BranchDTO branch;
    private DistrictDTO district;
    private RetailDealerDTO retailDealer;
    private String soEz;
    private double soTienKhuyenKhich;
    private double soTienDaChi;
    private Timestamp thoiGianPhaiChi;
    private Timestamp thoiGianThucChi;
    private Timestamp fromDate;
    private Timestamp toDate;
    private PromPackageDTO promPackage;
    private String serial;
    private Timestamp fromPaymentDate;
    private Timestamp toPaymentDate;
    private Timestamp paymentDate;
    private String trangThai = "-1";
    private Timestamp sumDate;
    private String address;
    private String contact_name;
    private String tax_Code;


    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public BranchDTO getBranch() {
        return branch;
    }

    public PromPackageDTO getPromPackage() {
        return promPackage;
    }

    public void setPromPackage(PromPackageDTO promPackage) {
        this.promPackage = promPackage;
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

    public String getSoEz() {
        return soEz;
    }

    public void setSoEz(String soEz) {
        this.soEz = soEz;
    }

    public double getSoTienKhuyenKhich() {
        return soTienKhuyenKhich;
    }

    public void setSoTienKhuyenKhich(double soTienKhuyenKhich) {
        this.soTienKhuyenKhich = soTienKhuyenKhich;
    }

    public double getSoTienDaChi() {
        return soTienDaChi;
    }

    public void setSoTienDaChi(double soTienDaChi) {
        this.soTienDaChi = soTienDaChi;
    }

    public Timestamp getThoiGianPhaiChi() {
        return thoiGianPhaiChi;
    }

    public void setThoiGianPhaiChi(Timestamp thoiGianPhaiChi) {
        this.thoiGianPhaiChi = thoiGianPhaiChi;
    }

    public Timestamp getThoiGianThucChi() {
        return thoiGianThucChi;
    }

    public void setThoiGianThucChi(Timestamp thoiGianThucChi) {
        this.thoiGianThucChi = thoiGianThucChi;
    }

    public Timestamp getFromDate() {
        return fromDate;
    }

    public Timestamp getToDate() {
        return toDate;
    }

    public void setFromDate(Timestamp fromDate) {
        this.fromDate = fromDate;
    }

    public void setToDate(Timestamp toDate) {
        this.toDate = toDate;
    }

    public Timestamp getFromPaymentDate() {
        return fromPaymentDate;
    }

    public void setFromPaymentDate(Timestamp fromPaymentDate) {
        this.fromPaymentDate = fromPaymentDate;
    }

    public Timestamp getToPaymentDate() {
        return toPaymentDate;
    }

    public void setToPaymentDate(Timestamp toPaymentDate) {
        this.toPaymentDate = toPaymentDate;
    }

    public Timestamp getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Timestamp paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public Timestamp getSumDate() {
        return sumDate;
    }

    public void setSumDate(Timestamp sumDate) {
        this.sumDate = sumDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public String getTax_Code() {
        return tax_Code;
    }

    public void setTax_Code(String tax_Code) {
        this.tax_Code = tax_Code;
    }
}
