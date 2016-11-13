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
    private String shopCode;
    private String isdn;
    private String name;
    private String empCode;
    private String busType;
    private String loaiTB;
    private String custType;
    private Timestamp staDateTime;
    private String actStatus;
    private String status;
    private Double cuocThucThu;
    private Double developmentAmount1;
    private Double developmentAmount2;
    private Double developmentAmount3;
    private Double maintainAmount1;
    private Double maintainAmount2;
    private Double maintainAmount3;
    private Timestamp issuedDateTime;
    private Timestamp issuedDateTimeFrom;
    private Timestamp issuedDateTimeTo;

    public Long getCostId() {
        return costId;
    }

    public void setCostId(Long costId) {
        this.costId = costId;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public Timestamp getIssuedDateTime() {
        return issuedDateTime;
    }

    public void setIssuedDateTime(Timestamp issuedDateTime) {
        this.issuedDateTime = issuedDateTime;
    }

    public Timestamp getIssuedDateTimeFrom() {
        return issuedDateTimeFrom;
    }

    public void setIssuedDateTimeFrom(Timestamp issuedDateTimeFrom) {
        this.issuedDateTimeFrom = issuedDateTimeFrom;
    }

    public Timestamp getIssuedDateTimeTo() {
        return issuedDateTimeTo;
    }

    public void setIssuedDateTimeTo(Timestamp issuedDateTimeTo) {
        this.issuedDateTimeTo = issuedDateTimeTo;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public String getLoaiTB() {
        return loaiTB;
    }

    public void setLoaiTB(String loaiTB) {
        this.loaiTB = loaiTB;
    }

    public String getCustType() {
        return custType;
    }

    public void setCustType(String custType) {
        this.custType = custType;
    }

    public Timestamp getStaDateTime() {
        return staDateTime;
    }

    public void setStaDateTime(Timestamp staDateTime) {
        this.staDateTime = staDateTime;
    }

    public String getActStatus() {
        return actStatus;
    }

    public void setActStatus(String actStatus) {
        this.actStatus = actStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getCuocThucThu() {
        return cuocThucThu;
    }

    public void setCuocThucThu(Double cuocThucThu) {
        this.cuocThucThu = cuocThucThu;
    }

    public Double getDevelopmentAmount1() {
        return developmentAmount1;
    }

    public void setDevelopmentAmount1(Double developmentAmount1) {
        this.developmentAmount1 = developmentAmount1;
    }

    public Double getDevelopmentAmount2() {
        return developmentAmount2;
    }

    public void setDevelopmentAmount2(Double developmentAmount2) {
        this.developmentAmount2 = developmentAmount2;
    }

    public Double getDevelopmentAmount3() {
        return developmentAmount3;
    }

    public void setDevelopmentAmount3(Double developmentAmount3) {
        this.developmentAmount3 = developmentAmount3;
    }

    public Double getMaintainAmount1() {
        return maintainAmount1;
    }

    public void setMaintainAmount1(Double maintainAmount1) {
        this.maintainAmount1 = maintainAmount1;
    }

    public Double getMaintainAmount2() {
        return maintainAmount2;
    }

    public void setMaintainAmount2(Double maintainAmount2) {
        this.maintainAmount2 = maintainAmount2;
    }

    public Double getMaintainAmount3() {
        return maintainAmount3;
    }

    public void setMaintainAmount3(Double maintainAmount3) {
        this.maintainAmount3 = maintainAmount3;
    }
}
