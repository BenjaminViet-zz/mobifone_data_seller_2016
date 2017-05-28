package com.benluck.vms.mobifonedataseller.core.dto;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 5/25/17
 * Time: 23:13
 * To change this template use File | Settings | File Templates.
 */
public class PaymentDTO implements Serializable{
    private static final long serialVersionUID = 3182321758436589514L;

    private Long paymentId;
    private OrderDTO order;
    private Date paymentDate;
    private Integer status;
    private Timestamp createdDate;
    private UserDTO createdBy;
    private List<PaymentHistoryDTO> paymentHistoryList;
    private Double orderTotal = 0D;
    private Double totalPaidAmount = 0D;
    private Double remainingAmount = 0D;
    private Double amount;

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public OrderDTO getOrder() {
        return order;
    }

    public void setOrder(OrderDTO order) {
        this.order = order;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public List<PaymentHistoryDTO> getPaymentHistoryList() {
        return paymentHistoryList;
    }

    public void setPaymentHistoryList(List<PaymentHistoryDTO> paymentHistoryList) {
        this.paymentHistoryList = paymentHistoryList;
    }

    public Double getTotalPaidAmount() {
        return totalPaidAmount;
    }

    public void setTotalPaidAmount(Double totalPaidAmount) {
        this.totalPaidAmount = totalPaidAmount;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(Double remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public Double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(Double orderTotal) {
        this.orderTotal = orderTotal;
    }
}
