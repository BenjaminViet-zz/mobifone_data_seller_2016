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
public class MBDReportGeneralExpenseDTO implements Serializable{
    private static final long serialVersionUID = 6152971691370936337L;
    private Long costId;
    private String shopCode;
    private String empCode;
    private String shopName;
    private Double developmentAmount1;
    private Double developmentAmount2;
    private Double developmentAmount3;
    private Double maintainAmount1;
    private Double maintainAmount2;
    private Double maintainAmount3;
    private Timestamp issuedDateTime;
    private Timestamp issuedDateTimeFrom;
    private Timestamp issuedDateTimeTo;
    private String isdn;

    public Long getCostId() {
        return costId;
    }

    public void setCostId(Long costId) {
        this.costId = costId;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
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

    public Timestamp getIssuedDateTime() {
        return issuedDateTime;
    }

    public void setIssuedDateTime(Timestamp issuedDateTime) {
        this.issuedDateTime = issuedDateTime;
    }

    public String getIsdn() {
        return isdn;
    }

    public void setIsdn(String isdn) {
        this.isdn = isdn;
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
}
