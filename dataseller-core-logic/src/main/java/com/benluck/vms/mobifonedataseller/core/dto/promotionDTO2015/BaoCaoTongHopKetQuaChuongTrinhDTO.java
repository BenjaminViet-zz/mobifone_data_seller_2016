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
 * Time: 10:37 AM
 * To change this template use File | Settings | File Templates.
 */
public class BaoCaoTongHopKetQuaChuongTrinhDTO implements Serializable {
    private static final long serialVersionUID = -1553421390764893430L;

    private BranchDTO branch;
    private DistrictDTO district;
    private RetailDealerDTO retailDealer;
    private Integer sLDBHThamGia;
    private Integer soLuongBTGMoi;
    private Integer soLuongBTGTon;
    private Integer soLuongGoiT50_s;
    private Integer soLuongGoi3T50_s;
    private Integer soLuongGoiT100_s;
    private Integer soLuongGoi3T100_s;
    private Integer soLuongGoiT50_CT;
    private Integer soLuongGoi3T50_CT;
    private Integer soLuongGoiT100_CT;
    private Integer soLuongGoi3T100_CT;
    private String soEZ;
    private PromPackageDTO promPackage;
    private String tinhTrang = "-1";
    private Double doanhThuBTGMoi;
    private Double doanhThuBTGTon;
    private String trangThaiChiTra = "-1";
    private Timestamp thoiGianDK;
    private Timestamp fromDate;
    private Timestamp toDate;
    private String serial;

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public Integer getSLDBHThamGia() {
        return sLDBHThamGia;
    }

    public void setSLDBHThamGia(Integer sLDBHThamGia) {
        this.sLDBHThamGia = sLDBHThamGia;
    }

    public Timestamp getThoiGianDK() {
        return thoiGianDK;
    }

    public void setThoiGianDK(Timestamp thoiGianDK) {
        this.thoiGianDK = thoiGianDK;
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

    public Integer getSoLuongBTGMoi() {
        return soLuongBTGMoi;
    }

    public void setSoLuongBTGMoi(Integer soLuongBTGMoi) {
        this.soLuongBTGMoi = soLuongBTGMoi;
    }

    public Integer getSoLuongBTGTon() {
        return soLuongBTGTon;
    }

    public void setSoLuongBTGTon(Integer soLuongBTGTon) {
        this.soLuongBTGTon = soLuongBTGTon;
    }

    public Integer getSoLuongGoiT50_s() {
        return soLuongGoiT50_s;
    }

    public void setSoLuongGoiT50_s(Integer soLuongGoiT50_s) {
        this.soLuongGoiT50_s = soLuongGoiT50_s;
    }

    public Integer getSoLuongGoi3T50_s() {
        return soLuongGoi3T50_s;
    }

    public void setSoLuongGoi3T50_s(Integer soLuongGoi3T50_s) {
        this.soLuongGoi3T50_s = soLuongGoi3T50_s;
    }

    public Integer getSoLuongGoiT100_s() {
        return soLuongGoiT100_s;
    }

    public void setSoLuongGoiT100_s(Integer soLuongGoiT100_s) {
        this.soLuongGoiT100_s = soLuongGoiT100_s;
    }

    public Integer getSoLuongGoi3T100_s() {
        return soLuongGoi3T100_s;
    }

    public void setSoLuongGoi3T100_s(Integer soLuongGoi3T100_s) {
        this.soLuongGoi3T100_s = soLuongGoi3T100_s;
    }

    public Integer getSoLuongGoiT50_CT() {
        return soLuongGoiT50_CT;
    }

    public void setSoLuongGoiT50_CT(Integer soLuongGoiT50_CT) {
        this.soLuongGoiT50_CT = soLuongGoiT50_CT;
    }

    public Integer getSoLuongGoi3T50_CT() {
        return soLuongGoi3T50_CT;
    }

    public void setSoLuongGoi3T50_CT(Integer soLuongGoi3T50_CT) {
        this.soLuongGoi3T50_CT = soLuongGoi3T50_CT;
    }

    public Integer getSoLuongGoiT100_CT() {
        return soLuongGoiT100_CT;
    }

    public void setSoLuongGoiT100_CT(Integer soLuongGoiT100_CT) {
        this.soLuongGoiT100_CT = soLuongGoiT100_CT;
    }

    public Integer getSoLuongGoi3T100_CT() {
        return soLuongGoi3T100_CT;
    }

    public void setSoLuongGoi3T100_CT(Integer soLuongGoi3T100_CT) {
        this.soLuongGoi3T100_CT = soLuongGoi3T100_CT;
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

    public Double getDoanhThuBTGMoi() {
        return doanhThuBTGMoi;
    }

    public void setDoanhThuBTGMoi(Double doanhThuBTGMoi) {
        this.doanhThuBTGMoi = doanhThuBTGMoi;
    }

    public Double getDoanhThuBTGTon() {
        return doanhThuBTGTon;
    }

    public void setDoanhThuBTGTon(Double doanhThuBTGTon) {
        this.doanhThuBTGTon = doanhThuBTGTon;
    }

    public String getTrangThaiChiTra() {
        return trangThaiChiTra;
    }

    public void setTrangThaiChiTra(String trangThaiChiTra) {
        this.trangThaiChiTra = trangThaiChiTra;
    }
}
