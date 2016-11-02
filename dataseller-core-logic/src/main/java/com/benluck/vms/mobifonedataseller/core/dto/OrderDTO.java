package com.benluck.vms.mobifonedataseller.core.dto;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 10/31/16
 * Time: 19:45
 * To change this template use File | Settings | File Templates.
 */
public class OrderDTO implements Serializable{
    private static final long serialVersionUID = -6489630744743768524L;

    private Long orderId;
    private KHDNDTO khdn;
    private PackageDataDTO packageData;
    private Integer quantity;
    private Double unitPrice;
    private Timestamp issuedDate;
    private Timestamp shippingDate;
    private Integer orderStatus;
    private Integer activeStatus;
    private Timestamp createdDate;
    private Timestamp lastModified;
    private UserDTO createdBy;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public KHDNDTO getKhdn() {
        return khdn;
    }

    public void setKhdn(KHDNDTO khdn) {
        this.khdn = khdn;
    }

    public PackageDataDTO getPackageData() {
        return packageData;
    }

    public void setPackageData(PackageDataDTO packageData) {
        this.packageData = packageData;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Timestamp getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(Timestamp shippingDate) {
        this.shippingDate = shippingDate;
    }

    public Timestamp getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(Timestamp issuedDate) {
        this.issuedDate = issuedDate;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Integer activeStatus) {
        this.activeStatus = activeStatus;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public UserDTO getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserDTO createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getLastModified() {
        return lastModified;
    }

    public void setLastModified(Timestamp lastModified) {
        this.lastModified = lastModified;
    }
}
