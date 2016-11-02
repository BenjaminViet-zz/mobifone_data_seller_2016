package com.benluck.vms.mobifonedataseller.domain;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 10/31/16
 * Time: 19:35
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "MOBI_DATA_ORDER")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Entity
public class OrderEntity {
    private Long orderId;
    private KHDNEntity khdn;
    private PackageDataEntity packageData;
    private Integer quantity;
    private Double unitPrice;
    private Timestamp issuedDate;
    private Timestamp shippingDate;
    private Integer orderStatus;
    private Integer activeStatus;
    private Timestamp createdDate;
    private Timestamp lastModified;
    private UserEntity CreatedBy;

    @Column(name = "ORDERID")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MOBI_DATA_ORDER_SEQ")
    @SequenceGenerator(name="MOBI_DATA_ORDER_SEQ", sequenceName="MOBI_DATA_ORDER_SEQ", allocationSize=1)
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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
    @JoinColumn(name = "PACKAGEDATAID", referencedColumnName = "PACKAGEDATAID")
    public PackageDataEntity getPackageData() {
        return packageData;
    }

    public void setPackageData(PackageDataEntity packageData) {
        this.packageData = packageData;
    }

    @Column(name = "QUANTITY")
    @Basic
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Column(name = "UNITPRICE")
    @Basic
    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Column(name = "ISSUEDDATE")
    @Basic
    public Timestamp getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(Timestamp issuedDate) {
        this.issuedDate = issuedDate;
    }

    @Column(name = "SHIPPINGDATE")
    @Basic
    public Timestamp getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(Timestamp shippingDate) {
        this.shippingDate = shippingDate;
    }

    @Column(name = "ORDERSTATUS")
    @Basic
    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
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
        return CreatedBy;
    }

    public void setCreatedBy(UserEntity createdBy) {
        CreatedBy = createdBy;
    }

    @Column(name = "LASTMODIFIED")
    @Basic
    public Timestamp getLastModified() {
        return lastModified;
    }

    public void setLastModified(Timestamp lastModified) {
        this.lastModified = lastModified;
    }

    @Column(name = "ACTIVESTATUS")
    @Basic
    public Integer getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Integer activeStatus) {
        this.activeStatus = activeStatus;
    }

    @Override
    public int hashCode() {
        int result = orderId != null ? orderId.hashCode() : 0;
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        result = 31 * result + (unitPrice != null ? unitPrice.hashCode() : 0);
        result = 31 * result + (issuedDate != null ? issuedDate.hashCode() : 0);
        result = 31 * result + (shippingDate != null ? shippingDate.hashCode() : 0);
        result = 31 * result + (orderStatus != null ? orderStatus.hashCode() : 0);
        result = 31 * result + (activeStatus != null ? activeStatus.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (lastModified != null ? lastModified.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderEntity that = (OrderEntity) o;

        if (orderId != null ? !orderId.equals(that.orderId) : that.orderId != null) return false;
        if (quantity != null ? !quantity.equals(that.quantity) : that.quantity != null) return false;
        if (unitPrice != null ? !unitPrice.equals(that.unitPrice) : that.unitPrice != null) return false;
        if (issuedDate != null ? !issuedDate.equals(that.issuedDate) : that.issuedDate != null) return false;
        if (shippingDate != null ? !shippingDate.equals(that.shippingDate) : that.shippingDate != null) return false;
        if (orderStatus != null ? !orderStatus.equals(that.orderStatus) : that.orderStatus!= null) return false;
        if (activeStatus != null ? !activeStatus.equals(that.activeStatus) : that.activeStatus!= null) return false;
        if (createdDate != null ? !createdDate.equals(that.createdDate) : that.createdDate != null) return false;
        if (lastModified != null ? !lastModified.equals(that.lastModified) : that.lastModified != null) return false;

        return true;
    }
}
