package com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 11/5/14
 * Time: 5:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class GiftAgentTransferHistoryDTO implements Serializable{
    private static final long serialVersionUID = 4141666783610988592L;

    private Long giftAgentTransferHistoryId;
    private StockAgentDTO stockAgent;
    private Integer quantity;
    private UserDTO nguoiChuyen;
    private Integer inventoryTotal;
    private Timestamp createdDate;
    private String nguoi_xuat;

    public Long getGiftAgentTransferHistoryId() {
        return giftAgentTransferHistoryId;
    }

    public void setGiftAgentTransferHistoryId(Long giftAgentTransferHistoryId) {
        this.giftAgentTransferHistoryId = giftAgentTransferHistoryId;
    }

    public StockAgentDTO getStockAgent() {
        return stockAgent;
    }

    public void setStockAgent(StockAgentDTO stockAgent) {
        this.stockAgent = stockAgent;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public UserDTO getNguoiChuyen() {
        return nguoiChuyen;
    }

    public void setNguoiChuyen(UserDTO nguoiChuyen) {
        this.nguoiChuyen = nguoiChuyen;
    }

    public Integer getInventoryTotal() {
        return inventoryTotal;
    }

    public void setInventoryTotal(Integer inventoryTotal) {
        this.inventoryTotal = inventoryTotal;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public String getNguoi_xuat() {
        return nguoi_xuat;
    }

    public void setNguoi_xuat(String nguoi_xuat) {
        this.nguoi_xuat = nguoi_xuat;
    }
}
