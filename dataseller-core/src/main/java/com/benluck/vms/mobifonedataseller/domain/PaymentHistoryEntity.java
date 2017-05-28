package com.benluck.vms.mobifonedataseller.domain;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 5/25/17
 * Time: 23:02
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "MOBI_DATA_PAYMENT_HISTORY")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Entity
public class PaymentHistoryEntity {
    private Long paymentHistoryId;
    private PaymentEntity payment;
    private Double amount;
    private Integer status;
    private UserEntity createdBy;
    private Date paymentDate;
    private Timestamp createdDate;
    private Timestamp lastModifiedDate;
    private UserEntity modifiedBy;

    @Column(name = "PAYMENTHISTORYID")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MOBI_DATA_PAYMENTHIS_SEQ")
    @SequenceGenerator(name="MOBI_DATA_PAYMENTHIS_SEQ", sequenceName="MOBI_DATA_PAYMENTHIS_SEQ", allocationSize=1)
    public Long getPaymentHistoryId() {
        return paymentHistoryId;
    }

    public void setPaymentHistoryId(Long paymentHistoryId) {
        this.paymentHistoryId = paymentHistoryId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PAYMENTID", referencedColumnName = "PAYMENTID")
    public PaymentEntity getPayment() {
        return payment;
    }

    public void setPayment(PaymentEntity payment) {
        this.payment = payment;
    }

    @Column(name = "AMOUNT")
    @Basic
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Column(name = "STATUS")
    @Basic
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATEDBY", referencedColumnName = "USERID")
    public UserEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserEntity createdBy) {
        this.createdBy = createdBy;
    }

    @Column(name = "PAYMENTDATE")
    @Basic
    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    @Column(name = "CREATEDDATE")
    @Basic
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "LASTMODIFIEDDATE")
    @Basic
    public Timestamp getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Timestamp lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MODIFIEDBY", referencedColumnName = "USERID")
    public UserEntity getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(UserEntity modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    @Override
    public int hashCode() {
        int result = paymentHistoryId != null ? paymentHistoryId.hashCode() : 0;
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (paymentDate != null ? paymentDate.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (lastModifiedDate != null ? lastModifiedDate.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PaymentHistoryEntity that = (PaymentHistoryEntity) o;

        if (paymentHistoryId != null ? !paymentHistoryId.equals(that.paymentHistoryId) : that.paymentHistoryId != null) return false;
        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (paymentDate != null ? !paymentDate.equals(that.paymentDate) : that.paymentDate != null) return false;
        if (createdDate != null ? !createdDate.equals(that.createdDate) : that.createdDate != null) return false;
        if (lastModifiedDate != null ? !lastModifiedDate.equals(that.lastModifiedDate) : that.lastModifiedDate != null) return false;

        return true;
    }
}
