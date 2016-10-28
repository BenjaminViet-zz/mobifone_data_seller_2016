package com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/23/14
 * Time: 7:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class CTTichDiemMaDuThuongDTO implements Serializable {
    private static final long serialVersionUID = -6171118473263519009L;

    private Long dealer_Id;
    private String dealer_code;
    private String dealer_name;
    private PromItemsDTO item;
    private String ma_phieu;
    private Date ngay_ps;
    private Timestamp cycle;
    private Date insert_dateTime;
    private String sms_status;
    private String exchange_gift_status;
    private Long exchange_gift_id;
    private String exchange_gift_name;
    private String winning_status;
    private String gift_Level;

    public Long getDealer_Id() {
        return dealer_Id;
    }

    public void setDealer_Id(Long dealer_Id) {
        this.dealer_Id = dealer_Id;
    }

    public String getDealer_code() {
        return dealer_code;
    }

    public void setDealer_code(String dealer_code) {
        this.dealer_code = dealer_code;
    }

    public PromItemsDTO getItem() {
        return item;
    }

    public void setItem(PromItemsDTO item) {
        this.item = item;
    }

    public String getMa_phieu() {
        return ma_phieu;
    }

    public void setMa_phieu(String ma_phieu) {
        this.ma_phieu = ma_phieu;
    }

    public Date getNgay_ps() {
        return ngay_ps;
    }

    public void setNgay_ps(Date ngay_ps) {
        this.ngay_ps = ngay_ps;
    }

    public Date getInsert_dateTime() {
        return insert_dateTime;
    }

    public void setInsert_dateTime(Date insert_dateTime) {
        this.insert_dateTime = insert_dateTime;
    }

    public String getSms_status() {
        return sms_status;
    }

    public void setSms_status(String sms_status) {
        this.sms_status = sms_status;
    }

    public String getExchange_gift_status() {
        return exchange_gift_status;
    }

    public void setExchange_gift_status(String exchange_gift_status) {
        this.exchange_gift_status = exchange_gift_status;
    }

    public Long getExchange_gift_id() {
        return exchange_gift_id;
    }

    public void setExchange_gift_id(Long exchange_gift_id) {
        this.exchange_gift_id = exchange_gift_id;
    }

    public String getExchange_gift_name() {
        return exchange_gift_name;
    }

    public void setExchange_gift_name(String exchange_gift_name) {
        this.exchange_gift_name = exchange_gift_name;
    }

    public String getWinning_status() {
        return winning_status;
    }

    public void setWinning_status(String winning_status) {
        this.winning_status = winning_status;
    }

    public Timestamp getCycle() {
        return cycle;
    }

    public void setCycle(Timestamp cycle) {
        this.cycle = cycle;
    }

    public String getDealer_name() {
        return dealer_name;
    }

    public void setDealer_name(String dealer_name) {
        this.dealer_name = dealer_name;
    }

    public String getGift_Level() {
        return gift_Level;
    }

    public void setGift_Level(String gift_Level) {
        this.gift_Level = gift_Level;
    }
}
