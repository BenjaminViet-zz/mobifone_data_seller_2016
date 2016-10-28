package com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/15/14
 * Time: 11:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class GiftDeliveryAgentHistoryDTO implements Serializable {
    private static final long serialVersionUID = 8206545698541632072L;

    private Long giftDeliveryAgentHistoryId;
    private StockAgentDTO stockAgent;
    private UserDTO user;
    private GiftDTO gift;
    private Integer quantity;
    private Integer inventoryTotal;
    private Timestamp deliveryTime;
    private Integer status;
    private String tenNguoiGiaoHang;

    private Timestamp fromDate;
    private Timestamp toDate;

    public Long getGiftDeliveryAgentHistoryId() {
        return giftDeliveryAgentHistoryId;
    }

    public void setGiftDeliveryAgentHistoryId(Long giftDeliveryAgentHistoryId) {
        this.giftDeliveryAgentHistoryId = giftDeliveryAgentHistoryId;
    }

    public StockAgentDTO getStockAgent() {
        return stockAgent;
    }

    public void setStockAgent(StockAgentDTO stockAgent) {
        this.stockAgent = stockAgent;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public GiftDTO getGift() {
        return gift;
    }

    public void setGift(GiftDTO gift) {
        this.gift = gift;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getInventoryTotal() {
        return inventoryTotal;
    }

    public void setInventoryTotal(Integer inventoryTotal) {
        this.inventoryTotal = inventoryTotal;
    }

    public Timestamp getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Timestamp deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Timestamp getFromDate() {
        return fromDate;
    }

    public void setFromDate(Timestamp fromDate) {
        this.fromDate = fromDate;
    }

    public Timestamp getToDate() {
        return toDate;
    }

    public void setToDate(Timestamp toDate) {
        this.toDate = toDate;
    }

    public String getTenNguoiGiaoHang() {
        return tenNguoiGiaoHang;
    }

    public void setTenNguoiGiaoHang(String tenNguoiGiaoHang) {
        this.tenNguoiGiaoHang = tenNguoiGiaoHang;
    }
}
