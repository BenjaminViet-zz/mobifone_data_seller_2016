package com.benluck.vms.mobifonedataseller.core.dto;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 5/25/17
 * Time: 23:14
 * To change this template use File | Settings | File Templates.
 */
public class PaymentHistoryDTO implements Serializable{
    private static final long serialVersionUID = 3076686699321151089L;

    private Long paymentHistoryId;
    private PaymentDTO payment;
    private Double amount;
    private Integer status;
    private UserDTO createdBy;
    private Date paymentDate;
    private Timestamp createdDate;
    private Timestamp lastModifiedDate;
    private UserDTO modifiedBy;

    public Long getPaymentHistoryId() {
        return paymentHistoryId;
    }

    public void setPaymentHistoryId(Long paymentHistoryId) {
        this.paymentHistoryId = paymentHistoryId;
    }

    public PaymentDTO getPayment() {
        return payment;
    }

    public void setPayment(PaymentDTO payment) {
        this.payment = payment;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public UserDTO getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserDTO createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Timestamp getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Timestamp lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public UserDTO getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(UserDTO modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
}
