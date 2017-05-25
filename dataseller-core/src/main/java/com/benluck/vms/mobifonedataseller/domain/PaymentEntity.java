package com.benluck.vms.mobifonedataseller.domain;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 5/25/17
 * Time: 22:59
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "MOBI_DATA_PAYMENT")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Entity
public class PaymentEntity {
    private Long paymentId;
    private KHDNEntity khdn;
    private OrderEntity order;
    private Integer status;
    private Timestamp createdDate;
    private UserEntity createdBy;
    private List<PaymentHistoryEntity> paymentHistoryList;

    @Column(name = "PAYMENTID")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MOBI_DATA_PAYMENT_SEQ")
    @SequenceGenerator(name="MOBI_DATA_PAYMENT_SEQ", sequenceName="MOBI_DATA_PAYMENT_SEQ", allocationSize=1)
    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "KHDNID", referencedColumnName = "KHDNID")
    public KHDNEntity getKhdn() {
        return khdn;
    }

    public void setKhdn(KHDNEntity khdn) {
        this.khdn = khdn;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDERID", referencedColumnName = "ORDERID")
    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }

    @Column(name = "STATUS")
    @Basic
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "CREATEDDATE")
    @Basic
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATEDBY", referencedColumnName = "USERID")
    public UserEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserEntity createdBy) {
        this.createdBy = createdBy;
    }

    @OneToMany(cascade=CascadeType.REMOVE, orphanRemoval = false)
    @JoinColumn(name="PAYMENTID", insertable = false, updatable = false)
    @OrderBy("createdDate ASC")
    public List<PaymentHistoryEntity> getPaymentHistoryList() {
        return paymentHistoryList;
    }

    public void setPaymentHistoryList(List<PaymentHistoryEntity> paymentHistoryList) {
        this.paymentHistoryList = paymentHistoryList;
    }
}
