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
 * Time: 2:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class BaoCaoChiTietGoiKhuyenMaiDTO implements Serializable {
    private static final long serialVersionUID = -5602268330496895579L;

    private BranchDTO branch;
    private DistrictDTO district;
    private RetailDealerDTO retailDealer;
    private String soThueBao;
    private String loaiThueBao;
    private Integer soLuongGoiT50_s;
    private Integer soLuongGoi3T50_s;
    private Integer soLuongGoiT100_s;
    private Integer soLuongGoi3T100_s;
    private Integer soLuongGoiT50_CT;
    private Integer soLuongGoi3T50_CT;
    private Integer soLuongGoiT100_CT;
    private Integer soLuongGoi3T100_CT;
    private Integer soLuongGoiT50_KCT;
    private Integer soLuongGoi3T50_KCT;
    private Integer soLuongGoiT100_KCT;
    private Integer soLuongGoi3T100_KCT;
    private String soEZ;
    private String lyDo;
    private String viTriDangKy;
    private Timestamp thoiGianDK;
    private PromPackageDTO promPackage;
    private String tinhTrang = "-1";
    private String trangThaiChiTra = "-1";
    private String promConditionStatus;
    private String promConditionError;
    private String transError;
    private Timestamp fromDate;
    private Timestamp toDate;
    private String serial;

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
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

    public String getSoThueBao() {
        return soThueBao;
    }

    public void setSoThueBao(String soThueBao) {
        this.soThueBao = soThueBao;
    }

    public String getLoaiThueBao() {
        return loaiThueBao;
    }

    public void setLoaiThueBao(String loaiThueBao) {
        this.loaiThueBao = loaiThueBao;
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

    public Integer getSoLuongGoiT50_KCT() {
        return soLuongGoiT50_KCT;
    }

    public void setSoLuongGoiT50_KCT(Integer soLuongGoiT50_KCT) {
        this.soLuongGoiT50_KCT = soLuongGoiT50_KCT;
    }

    public Integer getSoLuongGoi3T50_KCT() {
        return soLuongGoi3T50_KCT;
    }

    public void setSoLuongGoi3T50_KCT(Integer soLuongGoi3T50_KCT) {
        this.soLuongGoi3T50_KCT = soLuongGoi3T50_KCT;
    }

    public Integer getSoLuongGoiT100_KCT() {
        return soLuongGoiT100_KCT;
    }

    public void setSoLuongGoiT100_KCT(Integer soLuongGoiT100_KCT) {
        this.soLuongGoiT100_KCT = soLuongGoiT100_KCT;
    }

    public Integer getSoLuongGoi3T100_KCT() {
        return soLuongGoi3T100_KCT;
    }

    public void setSoLuongGoi3T100_KCT(Integer soLuongGoi3T100_KCT) {
        this.soLuongGoi3T100_KCT = soLuongGoi3T100_KCT;
    }

    public String getSoEZ() {
        return soEZ;
    }

    public void setSoEZ(String soEZ) {
        this.soEZ = soEZ;
    }

    public String getLyDo() {
        return lyDo;
    }

    public void setLyDo(String lyDo) {
        this.lyDo = lyDo;
    }

    public String getViTriDangKy() {
        return viTriDangKy;
    }

    public void setViTriDangKy(String viTriDangKy) {
        this.viTriDangKy = viTriDangKy;
    }

    public Timestamp getThoiGianDK() {
        return thoiGianDK;
    }

    public void setThoiGianDK(Timestamp thoiGianDK) {
        this.thoiGianDK = thoiGianDK;
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
