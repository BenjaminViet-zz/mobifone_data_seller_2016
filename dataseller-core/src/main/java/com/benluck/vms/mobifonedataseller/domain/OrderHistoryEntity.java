package com.benluck.vms.mobifonedataseller.domain;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 10/31/16
 * Time: 19:51
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "MOBI_DATA_ORDER_HISTORY")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Entity
public class OrderHistoryEntity {
    private Long orderHistoryId;
    private OrderEntity order;
    private Integer operator;
    private String originalData;
    private String newData;
    private Timestamp createdDate;
    private UserEntity createdBy;

    @Column(name = "ORDERHISTORYID")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MOBI_DATA_ORDER_HISTORY_SEQ")
    @SequenceGenerator(name="MOBI_DATA_ORDER_HISTORY_SEQ", sequenceName="MOBI_DATA_ORDER_HISTORY_SEQ", allocationSize=1)
    public Long getOrderHistoryId() {
        return orderHistoryId;
    }

    public void setOrderHistoryId(Long orderHistoryId) {
        this.orderHistoryId = orderHistoryId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDERID", referencedColumnName = "ORDERID")
    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }

    @Column(name = "OPERATOR")
    @Basic
    public Integer getOperator() {
        return operator;
    }

    public void setOperator(Integer operator) {
        this.operator = operator;
    }

    @Column(name = "ORIGINALDATA")
    @Basic
    @Lob
    public String getOriginalData() {
        return originalData;
    }

    public void setOriginalData(String originalData) {
        this.originalData = originalData;
    }

    @Column(name = "NEWDATA")
    @Basic
    @Lob
    public String getNewData() {
        return newData;
    }

    public void setNewData(String newData) {
        this.newData = newData;
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

    @Override
    public int hashCode() {
        int result = orderHistoryId != null ? orderHistoryId.hashCode() : 0;
        result = 31 * result + (operator != null ? operator.hashCode() : 0);
        result = 31 * result + (originalData != null ? originalData.hashCode() : 0);
        result = 31 * result + (newData != null ? newData.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderHistoryEntity that = (OrderHistoryEntity) o;

        if (orderHistoryId != null ? !orderHistoryId.equals(that.orderHistoryId) : that.orderHistoryId != null) return false;
        if (operator != null ? !operator.equals(that.operator) : that.operator != null) return false;
        if (originalData != null ? !originalData.equals(that.originalData) : that.originalData != null) return false;
        if (newData != null ? !newData.equals(that.newData) : that.newData != null) return false;
        if (createdDate != null ? !createdDate.equals(that.createdDate) : that.createdDate != null) return false;

        return true;
    }
}
