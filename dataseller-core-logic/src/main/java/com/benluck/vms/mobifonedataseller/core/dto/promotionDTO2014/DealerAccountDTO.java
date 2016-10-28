package com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/25/14
 * Time: 10:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class DealerAccountDTO implements Serializable{
    private static final long serialVersionUID = -960577997083544581L;

    private RetailDealerDTO dealer;
    private PromItemsDTO item;
    private Timestamp cycle;
    private Double exchange_amount;
    private Integer total_point;
    private Integer prom_point;
    private Integer ticket_point;
    private String exchange_money_status;
    private String exchange_ticket_status;
    private String cycle_lock_status;
    private String kit_sales_condition_status;
    private String sms_sales_condition_status;
    private String sub_charge_condition_status;
    private String sub_intro_condition_status;
    private String vas_sales_conditon_status;
    private String market_info_condition_status;
    private Timestamp insert_dateTime;
    private String tenQuanHuyen;
    private String branch_Name;
    private String condition_status;
    private Integer payment_status;
    private Double payment_money;

    public Integer getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(Integer payment_status) {
        this.payment_status = payment_status;
    }

    public Double getPayment_money() {
        return payment_money;
    }

    public void setPayment_money(Double payment_money) {
        this.payment_money = payment_money;
    }

    public RetailDealerDTO getDealer() {
        return dealer;
    }

    public void setDealer(RetailDealerDTO dealer) {
        this.dealer = dealer;
    }

    public PromItemsDTO getItem() {
        return item;
    }

    public void setItem(PromItemsDTO item) {
        this.item = item;
    }

    public Timestamp getCycle() {
        return cycle;
    }

    public void setCycle(Timestamp cycle) {
        this.cycle = cycle;
    }

    public Double getExchange_amount() {
        return exchange_amount;
    }

    public void setExchange_amount(Double exchange_amount) {
        this.exchange_amount = exchange_amount;
    }

    public Integer getTotal_point() {
        return total_point;
    }

    public void setTotal_point(Integer total_point) {
        this.total_point = total_point;
    }

    public Integer getProm_point() {
        return prom_point;
    }

    public void setProm_point(Integer prom_point) {
        this.prom_point = prom_point;
    }

    public Integer getTicket_point() {
        return ticket_point;
    }

    public void setTicket_point(Integer ticket_point) {
        this.ticket_point = ticket_point;
    }

    public String getExchange_money_status() {
        return exchange_money_status;
    }

    public void setExchange_money_status(String exchange_money_status) {
        this.exchange_money_status = exchange_money_status;
    }

    public String getExchange_ticket_status() {
        return exchange_ticket_status;
    }

    public void setExchange_ticket_status(String exchange_ticket_status) {
        this.exchange_ticket_status = exchange_ticket_status;
    }

    public String getCycle_lock_status() {
        return cycle_lock_status;
    }

    public void setCycle_lock_status(String cycle_lock_status) {
        this.cycle_lock_status = cycle_lock_status;
    }

    public String getKit_sales_condition_status() {
        return kit_sales_condition_status;
    }

    public void setKit_sales_condition_status(String kit_sales_condition_status) {
        this.kit_sales_condition_status = kit_sales_condition_status;
    }

    public String getSms_sales_condition_status() {
        return sms_sales_condition_status;
    }

    public void setSms_sales_condition_status(String sms_sales_condition_status) {
        this.sms_sales_condition_status = sms_sales_condition_status;
    }

    public String getSub_charge_condition_status() {
        return sub_charge_condition_status;
    }

    public void setSub_charge_condition_status(String sub_charge_condition_status) {
        this.sub_charge_condition_status = sub_charge_condition_status;
    }

    public String getSub_intro_condition_status() {
        return sub_intro_condition_status;
    }

    public void setSub_intro_condition_status(String sub_intro_condition_status) {
        this.sub_intro_condition_status = sub_intro_condition_status;
    }

    public String getVas_sales_conditon_status() {
        return vas_sales_conditon_status;
    }

    public void setVas_sales_conditon_status(String vas_sales_conditon_status) {
        this.vas_sales_conditon_status = vas_sales_conditon_status;
    }

    public String getMarket_info_condition_status() {
        return market_info_condition_status;
    }

    public void setMarket_info_condition_status(String market_info_condition_status) {
        this.market_info_condition_status = market_info_condition_status;
    }

    public Timestamp getInsert_dateTime() {
        return insert_dateTime;
    }

    public void setInsert_dateTime(Timestamp insert_dateTime) {
        this.insert_dateTime = insert_dateTime;
    }

    public String getTenQuanHuyen() {
        return tenQuanHuyen;
    }

    public void setTenQuanHuyen(String tenQuanHuyen) {
        this.tenQuanHuyen = tenQuanHuyen;
    }

    public String getBranch_Name() {
        return branch_Name;
    }

    public void setBranch_Name(String branch_Name) {
        this.branch_Name = branch_Name;
    }

    public String getCondition_status() {
        return condition_status;
    }

    public void setCondition_status(String condition_status) {
        this.condition_status = condition_status;
    }
}
