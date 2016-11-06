package com.benluck.vms.mobifonedataseller.domain;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/6/16
 * Time: 11:28
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "MOBI_DATA_ORDER_DATA_CODE")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Entity
public class OrderDataCodeEntity {
    private Long orderDataCodeId;
    private OrderEntity order;
    private Long serial;
    private Long dataCode;
    private Timestamp expiredDate;

    @Column(name = "ORDERDATACODEID")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MOBI_DATA_ORDER_DATA_CODE_SEQ")
    @SequenceGenerator(name="MOBI_DATA_ORDER_DATA_CODE_SEQ", sequenceName="MOBI_DATA_ORDER_DATA_CODE_SEQ", allocationSize=1)
    public Long getOrderDataCodeId() {
        return orderDataCodeId;
    }

    public void setOrderDataCodeId(Long orderDataCodeId) {
        this.orderDataCodeId = orderDataCodeId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDERID", referencedColumnName = "ORDERID")
    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }

    @Column(name = "SERIAL")
    @Basic
    public Long getSerial() {
        return serial;
    }

    public void setSerial(Long serial) {
        this.serial = serial;
    }

    @Column(name = "DATACODE")
    @Basic
    public Long getDataCode() {
        return dataCode;
    }

    public void setDataCode(Long dataCode) {
        this.dataCode = dataCode;
    }

    @Column(name = "EXPIREDDATE")
    @Basic
    public Timestamp getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Timestamp expiredDate) {
        this.expiredDate = expiredDate;
    }

    @Override
    public int hashCode() {
        int result = orderDataCodeId != null ? orderDataCodeId.hashCode() : 0;
        result = 31 * result + (serial != null ? serial.hashCode() : 0);
        result = 31 * result + (dataCode != null ? dataCode.hashCode() : 0);
        result = 31 * result + (expiredDate != null ? expiredDate.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderDataCodeEntity that = (OrderDataCodeEntity) o;

        if (orderDataCodeId != null ? !orderDataCodeId.equals(that.orderDataCodeId) : that.orderDataCodeId != null) return false;
        if (serial != null ? !serial.equals(that.serial) : that.serial != null) return false;
        if (dataCode != null ? !dataCode.equals(that.dataCode) : that.dataCode != null) return false;
        if (expiredDate != null ? !expiredDate.equals(that.expiredDate) : that.expiredDate != null) return false;

        return true;
    }
}
