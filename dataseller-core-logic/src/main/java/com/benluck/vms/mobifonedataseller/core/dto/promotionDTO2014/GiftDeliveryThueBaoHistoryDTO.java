package com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/17/14
 * Time: 1:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class GiftDeliveryThueBaoHistoryDTO implements Serializable{
    private static final long serialVersionUID = -4022035157091751435L;

    private Long giftDeliveryThueBaoHisId;
    private GiftDeliveryThueBaoDTO giftDeliveryThueBao;
    private String ma_phieu;
    private GiftDTO gift;
    private Integer quantity;
    private String tenCuaHangGiaoQua;

    private Timestamp fromDate;
    private Timestamp toDate;

    public Long getGiftDeliveryThueBaoHisId() {
        return giftDeliveryThueBaoHisId;
    }

    public void setGiftDeliveryThueBaoHisId(Long giftDeliveryThueBaoHisId) {
        this.giftDeliveryThueBaoHisId = giftDeliveryThueBaoHisId;
    }

    public GiftDeliveryThueBaoDTO getGiftDeliveryThueBao() {
        return giftDeliveryThueBao;
    }

    public void setGiftDeliveryThueBao(GiftDeliveryThueBaoDTO giftDeliveryThueBao) {
        this.giftDeliveryThueBao = giftDeliveryThueBao;
    }

    public String getMa_phieu() {
        return ma_phieu;
    }

    public void setMa_phieu(String ma_phieu) {
        this.ma_phieu = ma_phieu;
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

    public String getTenCuaHangGiaoQua() {
        return tenCuaHangGiaoQua;
    }

    public void setTenCuaHangGiaoQua(String tenCuaHangGiaoQua) {
        this.tenCuaHangGiaoQua = tenCuaHangGiaoQua;
    }
}
