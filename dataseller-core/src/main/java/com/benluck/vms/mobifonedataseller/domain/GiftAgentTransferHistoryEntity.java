package com.benluck.vms.mobifonedataseller.domain;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 11/5/14
 * Time: 5:30 PM
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "VMS_GIFT_AGENT_TRANSFER_HIS")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Entity
public class GiftAgentTransferHistoryEntity {
    private Long giftAgentTransferHistoryId;
    private StockAgentEntity stockAgent;
    private Integer quantity;
    private UserEntity nguoiChuyen;
    private Integer inventoryTotal;
    private Timestamp createdDate;

    @Column(name = "GIFTAGENTTRANSFERHISID")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VMS_GIFT_AGENT_TRANS_HIS_SEQ")
    @SequenceGenerator(name="VMS_GIFT_AGENT_TRANS_HIS_SEQ", sequenceName="VMS_GIFT_AGENT_TRANS_HIS_SEQ", allocationSize=1)
    public Long getGiftAgentTransferHistoryId() {
        return giftAgentTransferHistoryId;
    }

    public void setGiftAgentTransferHistoryId(Long giftAgentTransferHistoryId) {
        this.giftAgentTransferHistoryId = giftAgentTransferHistoryId;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STOCKAGENTID", referencedColumnName = "STOCKAGENTID")
    public StockAgentEntity getStockAgent() {
        return stockAgent;
    }

    public void setStockAgent(StockAgentEntity stockAgent) {
        this.stockAgent = stockAgent;
    }

    @Column(name = "QUANTITY")
    @Basic
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NGUOICHUYENID", referencedColumnName = "USERID")
    public UserEntity getNguoiChuyen() {
        return nguoiChuyen;
    }

    public void setNguoiChuyen(UserEntity nguoiChuyen) {
        this.nguoiChuyen = nguoiChuyen;
    }

    @Column(name = "INVENTORYTOTAL")
    @Basic
    public Integer getInventoryTotal() {
        return inventoryTotal;
    }

    public void setInventoryTotal(Integer inventoryTotal) {
        this.inventoryTotal = inventoryTotal;
    }

    @Column(name = "CREATEDDATE")
    @Basic
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GiftAgentTransferHistoryEntity that = (GiftAgentTransferHistoryEntity) o;

        if (giftAgentTransferHistoryId != null ? !giftAgentTransferHistoryId.equals(that.giftAgentTransferHistoryId) : that.giftAgentTransferHistoryId != null) return false;
        if (quantity != null ? !quantity.equals(that.quantity) : that.quantity != null) return false;
        if (inventoryTotal != null ? !inventoryTotal.equals(that.inventoryTotal) : that.inventoryTotal != null) return false;
        if (createdDate != null ? !createdDate.equals(that.createdDate) : that.createdDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = giftAgentTransferHistoryId != null ? giftAgentTransferHistoryId.hashCode() : 0;
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        result = 31 * result + (inventoryTotal != null ? inventoryTotal.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        return result;
    }
}
