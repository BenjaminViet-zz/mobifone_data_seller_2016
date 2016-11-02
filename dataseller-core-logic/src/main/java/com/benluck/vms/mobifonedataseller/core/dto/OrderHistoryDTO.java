package com.benluck.vms.mobifonedataseller.core.dto;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 10/31/16
 * Time: 20:04
 * To change this template use File | Settings | File Templates.
 */
public class OrderHistoryDTO implements Serializable{
    private static final long serialVersionUID = -3854183499499172354L;

    private Long orderHistoryId;
    private OrderDTO order;
    private KHDNDTO khdn;
    private PackageDataDTO packageData;
    private Integer operator;
    private Integer quantity;
    private Double unitPrice;
    private Timestamp issuedDate;
    private Timestamp shippingDate;
    private Integer orderStatus;
    private Timestamp createdDate;
    private UserDTO createdBy;

    public Long getOrderHistoryId() {
        return orderHistoryId;
    }

    public void setOrderHistoryId(Long orderHistoryId) {
        this.orderHistoryId = orderHistoryId;
    }

    public OrderDTO getOrder() {
        return order;
    }

    public void setOrder(OrderDTO order) {
        this.order = order;
    }

    public Integer getOperator() {
        return operator;
    }

    public void setOperator(Integer operator) {
        this.operator = operator;
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

    public Timestamp getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(Timestamp issuedDate) {
        this.issuedDate = issuedDate;
    }

    public Timestamp getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(Timestamp shippingDate) {
        this.shippingDate = shippingDate;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
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
}
