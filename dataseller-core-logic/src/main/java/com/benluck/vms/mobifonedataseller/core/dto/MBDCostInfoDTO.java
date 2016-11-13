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
public class MBDCostInfoDTO implements Serializable{
    private static final long serialVersionUID = 2689383867715432434L;

    private Long costId;
    private String shopCode;
    private String shopName;
    private String empCode;
    private Long custId;
    private Long subId;
    private String isdn;
    private String name;
    private String busType;
    private String custType;
    private Timestamp staDateTime;
    private String actStatus;
    private String status;
    private Timestamp issueMonth;
    private Double payment;
    private String developmentPhase1;
    private Double developmentAmount1;
    private String developmentPhase2;
    private Double developmentAmount2;
    private String developmentPhase3;
    private Double developmentAmount3;
    private String maintainPhase1;
    private Double maintainAmount1;
    private String maintainPhase2;
    private Double maintainAmount2;
    private String maintainPhase3;
    private Double maintainAmount3;
    private Timestamp insertDateTime;
    private String paymentStatus = "0"; // 0: Not paid; 1: paid
    private Timestamp paymentDate;
    private Timestamp staDateFrom;
    private Timestamp staDateTo;

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

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public Long getSubId() {
        return subId;
    }

    public void setSubId(Long subId) {
        this.subId = subId;
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

    public Timestamp getIssueMonth() {
        return issueMonth;
    }

    public void setIssueMonth(Timestamp issueMonth) {
        this.issueMonth = issueMonth;
    }

    public Double getPayment() {
        return payment;
    }

    public void setPayment(Double payment) {
        this.payment = payment;
    }

    public String getDevelopmentPhase1() {
        return developmentPhase1;
    }

    public void setDevelopmentPhase1(String developmentPhase1) {
        this.developmentPhase1 = developmentPhase1;
    }

    public Double getDevelopmentAmount1() {
        return developmentAmount1;
    }

    public void setDevelopmentAmount1(Double developmentAmount1) {
        this.developmentAmount1 = developmentAmount1;
    }

    public String getDevelopmentPhase2() {
        return developmentPhase2;
    }

    public void setDevelopmentPhase2(String developmentPhase2) {
        this.developmentPhase2 = developmentPhase2;
    }

    public Double getDevelopmentAmount2() {
        return developmentAmount2;
    }

    public void setDevelopmentAmount2(Double developmentAmount2) {
        this.developmentAmount2 = developmentAmount2;
    }

    public String getDevelopmentPhase3() {
        return developmentPhase3;
    }

    public void setDevelopmentPhase3(String developmentPhase3) {
        this.developmentPhase3 = developmentPhase3;
    }

    public Double getDevelopmentAmount3() {
        return developmentAmount3;
    }

    public void setDevelopmentAmount3(Double developmentAmount3) {
        this.developmentAmount3 = developmentAmount3;
    }

    public String getMaintainPhase1() {
        return maintainPhase1;
    }

    public void setMaintainPhase1(String maintainPhase1) {
        this.maintainPhase1 = maintainPhase1;
    }

    public Double getMaintainAmount1() {
        return maintainAmount1;
    }

    public void setMaintainAmount1(Double maintainAmount1) {
        this.maintainAmount1 = maintainAmount1;
    }

    public String getMaintainPhase2() {
        return maintainPhase2;
    }

    public void setMaintainPhase2(String maintainPhase2) {
        this.maintainPhase2 = maintainPhase2;
    }

    public Double getMaintainAmount2() {
        return maintainAmount2;
    }

    public void setMaintainAmount2(Double maintainAmount2) {
        this.maintainAmount2 = maintainAmount2;
    }

    public String getMaintainPhase3() {
        return maintainPhase3;
    }

    public void setMaintainPhase3(String maintainPhase3) {
        this.maintainPhase3 = maintainPhase3;
    }

    public Double getMaintainAmount3() {
        return maintainAmount3;
    }

    public void setMaintainAmount3(Double maintainAmount3) {
        this.maintainAmount3 = maintainAmount3;
    }

    public Timestamp getInsertDateTime() {
        return insertDateTime;
    }

    public void setInsertDateTime(Timestamp insertDateTime) {
        this.insertDateTime = insertDateTime;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Timestamp getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Timestamp paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Timestamp getStaDateFrom() {
        return staDateFrom;
    }

    public void setStaDateFrom(Timestamp staDateFrom) {
        this.staDateFrom = staDateFrom;
    }

    public Timestamp getStaDateTo() {
        return staDateTo;
    }

    public void setStaDateTo(Timestamp staDateTo) {
        this.staDateTo = staDateTo;
    }
}
