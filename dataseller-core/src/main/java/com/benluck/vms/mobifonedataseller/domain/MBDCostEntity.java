package com.benluck.vms.mobifonedataseller.domain;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/9/16
 * Time: 21:34
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "MOBI_DATA_COST")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Entity
public class MBDCostEntity {
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
    private String paymentStatus;
    private Timestamp paymentDate;

    @Column(name = "COSTID")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MOBI_DATA_COST_SEQ")
    @SequenceGenerator(name="MOBI_DATA_COST_SEQ", sequenceName="MOBI_DATA_COST_SEQ", allocationSize=1)
    public Long getCostId() {
        return costId;
    }

    public void setCostId(Long costId) {
        this.costId = costId;
    }

    @Column(name = "SHOP_CODE")
    @Basic
    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    @Column(name = "SHOP_NAME")
    @Basic
    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    @Column(name = "EMP_CODE")
    @Basic
    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    @Column(name = "CUST_ID")
    @Basic
    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    @Column(name = "SUB_ID")
    @Basic
    public Long getSubId() {
        return subId;
    }

    public void setSubId(Long subId) {
        this.subId = subId;
    }

    @Column(name = "ISDN")
    @Basic
    public String getIsdn() {
        return isdn;
    }

    public void setIsdn(String isdn) {
        this.isdn = isdn;
    }

    @Column(name = "NAME")
    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "BUS_TYPE")
    @Basic
    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    @Column(name = "CUST_TYPE")
    @Basic
    public String getCustType() {
        return custType;
    }

    public void setCustType(String custType) {
        this.custType = custType;
    }

    @Column(name = "STA_DATETIME")
    @Basic
    public Timestamp getStaDateTime() {
        return staDateTime;
    }

    public void setStaDateTime(Timestamp staDateTime) {
        this.staDateTime = staDateTime;
    }

    @Column(name = "ACT_STATUS")
    @Basic
    public String getActStatus() {
        return actStatus;
    }

    public void setActStatus(String actStatus) {
        this.actStatus = actStatus;
    }

    @Column(name = "STATUS")
    @Basic
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "ISSUE_MONTH")
    @Basic
    public Timestamp getIssueMonth() {
        return issueMonth;
    }

    public void setIssueMonth(Timestamp issueMonth) {
        this.issueMonth = issueMonth;
    }

    @Column(name = "PAYMENT")
    @Basic
    public Double getPayment() {
        return payment;
    }

    public void setPayment(Double payment) {
        this.payment = payment;
    }

    @Column(name = "DEVELOPMENT_PHASE1")
    @Basic
    public String getDevelopmentPhase1() {
        return developmentPhase1;
    }

    public void setDevelopmentPhase1(String developmentPhase1) {
        this.developmentPhase1 = developmentPhase1;
    }

    @Column(name = "DEVELOPMENT_AMOUNT1")
    @Basic
    public Double getDevelopmentAmount1() {
        return developmentAmount1;
    }

    public void setDevelopmentAmount1(Double developmentAmount1) {
        this.developmentAmount1 = developmentAmount1;
    }

    @Column(name = "DEVELOPMENT_PHASE2")
    @Basic
    public String getDevelopmentPhase2() {
        return developmentPhase2;
    }

    public void setDevelopmentPhase2(String developmentPhase2) {
        this.developmentPhase2 = developmentPhase2;
    }

    @Column(name = "DEVELOPMENT_AMOUNT2")
    @Basic
    public Double getDevelopmentAmount2() {
        return developmentAmount2;
    }

    public void setDevelopmentAmount2(Double developmentAmount2) {
        this.developmentAmount2 = developmentAmount2;
    }

    @Column(name = "DEVELOPMENT_PHASE3")
    @Basic
    public String getDevelopmentPhase3() {
        return developmentPhase3;
    }

    public void setDevelopmentPhase3(String developmentPhase3) {
        this.developmentPhase3 = developmentPhase3;
    }

    @Column(name = "DEVELOPMENT_AMOUNT3")
    @Basic
    public Double getDevelopmentAmount3() {
        return developmentAmount3;
    }

    public void setDevelopmentAmount3(Double developmentAmount3) {
        this.developmentAmount3 = developmentAmount3;
    }

    @Column(name = "MAINTAIN_PHASE1")
    @Basic
    public String getMaintainPhase1() {
        return maintainPhase1;
    }

    public void setMaintainPhase1(String maintainPhase1) {
        this.maintainPhase1 = maintainPhase1;
    }

    @Column(name = "MAINTAIN_AMOUNT1")
    @Basic
    public Double getMaintainAmount1() {
        return maintainAmount1;
    }

    public void setMaintainAmount1(Double maintainAmount1) {
        this.maintainAmount1 = maintainAmount1;
    }

    @Column(name = "MAINTAIN_PHASE2")
    @Basic
    public String getMaintainPhase2() {
        return maintainPhase2;
    }

    public void setMaintainPhase2(String maintainPhase2) {
        this.maintainPhase2 = maintainPhase2;
    }

    @Column(name = "MAINTAIN_AMOUNT2")
    @Basic
    public Double getMaintainAmount2() {
        return maintainAmount2;
    }

    public void setMaintainAmount2(Double maintainAmount2) {
        this.maintainAmount2 = maintainAmount2;
    }

    @Column(name = "MAINTAIN_PHASE3")
    @Basic
    public String getMaintainPhase3() {
        return maintainPhase3;
    }

    public void setMaintainPhase3(String maintainPhase3) {
        this.maintainPhase3 = maintainPhase3;
    }

    @Column(name = "MAINTAIN_AMOUNT3")
    @Basic
    public Double getMaintainAmount3() {
        return maintainAmount3;
    }

    public void setMaintainAmount3(Double maintainAmount3) {
        this.maintainAmount3 = maintainAmount3;
    }

    @Column(name = "INSERT_DATETIME")
    @Basic
    public Timestamp getInsertDateTime() {
        return insertDateTime;
    }

    public void setInsertDateTime(Timestamp insertDateTime) {
        this.insertDateTime = insertDateTime;
    }

    @Column(name = "PAYMENT_STATUS")
    @Basic
    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @Column(name = "PAYMENT_DATE")
    @Basic
    public Timestamp getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Timestamp paymentDate) {
        this.paymentDate = paymentDate;
    }

    @Override
    public int hashCode() {
        int result = costId != null ? costId.hashCode() : 0;
        result = 31 * result + (shopCode != null ? shopCode.hashCode() : 0);
        result = 31 * result + (shopName != null ? shopName.hashCode() : 0);
        result = 31 * result + (empCode != null ? empCode.hashCode() : 0);
        result = 31 * result + (custId != null ? custId.hashCode() : 0);
        result = 31 * result + (subId != null ? subId.hashCode() : 0);
        result = 31 * result + (isdn != null ? isdn.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (busType != null ? busType.hashCode() : 0);
        result = 31 * result + (custType != null ? custType.hashCode() : 0);
        result = 31 * result + (staDateTime != null ? staDateTime.hashCode() : 0);
        result = 31 * result + (actStatus != null ? actStatus.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (issueMonth != null ? issueMonth.hashCode() : 0);
        result = 31 * result + (payment != null ? payment.hashCode() : 0);
        result = 31 * result + (developmentPhase1 != null ? developmentPhase1.hashCode() : 0);
        result = 31 * result + (developmentAmount1 != null ? developmentAmount1.hashCode() : 0);
        result = 31 * result + (developmentPhase2 != null ? developmentPhase2.hashCode() : 0);
        result = 31 * result + (developmentAmount2 != null ? developmentAmount2.hashCode() : 0);
        result = 31 * result + (developmentPhase3 != null ? developmentPhase3.hashCode() : 0);
        result = 31 * result + (developmentAmount3 != null ? developmentAmount3.hashCode() : 0);
        result = 31 * result + (maintainPhase1 != null ? maintainPhase1.hashCode() : 0);
        result = 31 * result + (maintainAmount1 != null ? maintainAmount1.hashCode() : 0);
        result = 31 * result + (maintainPhase2 != null ? maintainPhase2.hashCode() : 0);
        result = 31 * result + (maintainAmount2 != null ? maintainAmount2.hashCode() : 0);
        result = 31 * result + (maintainPhase3 != null ? maintainPhase3.hashCode() : 0);
        result = 31 * result + (maintainAmount3 != null ? maintainAmount3.hashCode() : 0);
        result = 31 * result + (insertDateTime != null ? insertDateTime.hashCode() : 0);
        result = 31 * result + (paymentStatus != null ? paymentStatus.hashCode() : 0);
        result = 31 * result + (paymentDate != null ? paymentDate.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MBDCostEntity that = (MBDCostEntity) o;

        if (costId != null ? !costId.equals(that.costId) : that.costId != null) return false;
        if (shopCode != null ? !shopCode.equals(that.shopCode) : that.shopCode != null) return false;
        if (shopName != null ? !shopName.equals(that.shopName) : that.shopName != null) return false;
        if (empCode != null ? !empCode.equals(that.empCode) : that.empCode != null) return false;
        if (custId != null ? !custId.equals(that.custId) : that.custId != null) return false;
        if (subId != null ? !subId.equals(that.subId) : that.subId != null) return false;
        if (isdn != null ? !isdn.equals(that.isdn) : that.isdn != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (busType != null ? !busType.equals(that.busType) : that.busType != null) return false;
        if (custType != null ? !custType.equals(that.custType) : that.custType != null) return false;
        if (staDateTime != null ? !staDateTime.equals(that.staDateTime) : that.staDateTime != null) return false;
        if (actStatus != null ? !actStatus.equals(that.actStatus) : that.actStatus != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (issueMonth != null ? !issueMonth.equals(that.issueMonth) : that.issueMonth != null) return false;
        if (payment != null ? !payment.equals(that.payment) : that.payment != null) return false;
        if (developmentPhase1 != null ? !developmentPhase1.equals(that.developmentPhase1) : that.developmentPhase1 != null) return false;
        if (developmentAmount1 != null ? !developmentAmount1.equals(that.developmentAmount1) : that.developmentAmount1 != null) return false;
        if (developmentAmount2 != null ? !developmentAmount2.equals(that.developmentAmount2) : that.developmentAmount2 != null) return false;
        if (developmentAmount2 != null ? !developmentAmount2.equals(that.developmentAmount2) : that.developmentAmount2 != null) return false;
        if (developmentPhase3 != null ? !developmentPhase3.equals(that.developmentPhase3) : that.developmentPhase3 != null) return false;
        if (developmentAmount3 != null ? !developmentAmount3.equals(that.developmentAmount3) : that.developmentAmount3 != null) return false;
        if (maintainPhase1 != null ? !maintainPhase1.equals(that.maintainPhase1) : that.maintainPhase1 != null) return false;
        if (maintainAmount1 != null ? !maintainAmount1.equals(that.maintainAmount1) : that.maintainAmount1 != null) return false;
        if (maintainPhase2 != null ? !maintainPhase2.equals(that.maintainPhase2) : that.maintainPhase2 != null) return false;
        if (maintainAmount2 != null ? !maintainAmount2.equals(that.maintainAmount2) : that.maintainAmount2 != null) return false;
        if (maintainPhase3 != null ? !maintainPhase3.equals(that.maintainPhase3) : that.maintainPhase3 != null) return false;
        if (maintainAmount3 != null ? !maintainAmount3.equals(that.maintainAmount3) : that.maintainAmount3 != null) return false;
        if (insertDateTime != null ? !insertDateTime.equals(that.insertDateTime) : that.insertDateTime != null) return false;
        if (paymentStatus != null ? !paymentStatus.equals(that.paymentStatus) : that.paymentStatus != null) return false;
        if (paymentDate != null ? !paymentDate.equals(that.paymentDate) : that.insertDateTime != null) return false;

        return true;
    }
}
