package com.benluck.vms.mobifonedataseller.domain;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/15/14
 * Time: 10:57 AM
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "VMS_GIF_DELIVERY_AGENT_HISTORY")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Entity
public class GiftDeliveryAgentHistoryEntity {
    private Long giftDeliveryAgentHistoryId;
    private StockAgentEntity stockAgent;
    private UserEntity user;
    private GiftEntity gift;
    private Integer quantity;
    private Integer inventoryTotal;
    private Timestamp deliveryTime;
    private Integer status;
    private String tenNguoiGiaoHang;

    @Column(name = "GIFTDELIVERYAGENTHISTORYID")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VMS_GIF_DEL_AGENT_HIS_SEQ")
    @SequenceGenerator(name="VMS_GIF_DEL_AGENT_HIS_SEQ", sequenceName="VMS_GIF_DEL_AGENT_HIS_SEQ", allocationSize=1)
    public Long getGiftDeliveryAgentHistoryId() {
        return giftDeliveryAgentHistoryId;
    }

    public void setGiftDeliveryAgentHistoryId(Long giftDeliveryAgentHistoryId) {
        this.giftDeliveryAgentHistoryId = giftDeliveryAgentHistoryId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STOCKAGENTID", referencedColumnName = "STOCKAGENTID")
    public StockAgentEntity getStockAgent() {
        return stockAgent;
    }

    public void setStockAgent(StockAgentEntity stockAgent) {
        this.stockAgent = stockAgent;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USERID", referencedColumnName = "USERID")
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GIFTID", referencedColumnName = "GIFTID")
    public GiftEntity getGift() {
        return gift;
    }

    public void setGift(GiftEntity gift) {
        this.gift = gift;
    }

    @Column(name = "QUANTITY")
    @Basic
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Column(name = "DELIVERYTIME")
    @Basic
    public Timestamp getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Timestamp deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    @Column(name = "INVENTORYTOTAL")
    @Basic
    public Integer getInventoryTotal() {
        return inventoryTotal;
    }

    public void setInventoryTotal(Integer inventoryTotal) {
        this.inventoryTotal = inventoryTotal;
    }

    @Column(name = "STATUS")
    @Basic
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "TENNGUOIGIAOHANG")
    @Basic
    public String getTenNguoiGiaoHang() {
        return tenNguoiGiaoHang;
    }

    public void setTenNguoiGiaoHang(String tenNguoiGiaoHang) {
        this.tenNguoiGiaoHang = tenNguoiGiaoHang;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GiftDeliveryAgentHistoryEntity that = (GiftDeliveryAgentHistoryEntity) o;

        if (giftDeliveryAgentHistoryId != null ? !giftDeliveryAgentHistoryId.equals(that.giftDeliveryAgentHistoryId) : that.giftDeliveryAgentHistoryId != null) return false;
        if (quantity != null ? !quantity.equals(that.quantity) : that.quantity != null) return false;
        if (inventoryTotal != null ? !inventoryTotal.equals(that.inventoryTotal) : that.inventoryTotal != null) return false;
        if (deliveryTime != null ? !deliveryTime.equals(that.deliveryTime) : that.deliveryTime != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (tenNguoiGiaoHang != null ? !status.equals(that.tenNguoiGiaoHang) : that.tenNguoiGiaoHang != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = giftDeliveryAgentHistoryId != null ? giftDeliveryAgentHistoryId.hashCode() : 0;
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        result = 31 * result + (inventoryTotal != null ? inventoryTotal.hashCode() : 0);
        result = 31 * result + (deliveryTime != null ? deliveryTime.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (tenNguoiGiaoHang != null ? tenNguoiGiaoHang.hashCode() : 0);
        return result;
    }
}
