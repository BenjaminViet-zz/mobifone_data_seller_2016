package com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/26/14
 * Time: 3:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class MoneyExchangeBranchDTO implements Serializable{
    private static final long serialVersionUID = 1529892713072854411L;

    private Long moneyExchangeBranchId;
    private UserDTO exchangeUser;
    private Long dealerId;
    private String dealer_code;
    private String dealer_name;
    private Timestamp createdDate;

    public Long getMoneyExchangeBranchId() {
        return moneyExchangeBranchId;
    }

    public void setMoneyExchangeBranchId(Long moneyExchangeBranchId) {
        this.moneyExchangeBranchId = moneyExchangeBranchId;
    }

    public UserDTO getExchangeUser() {
        return exchangeUser;
    }

    public void setExchangeUser(UserDTO exchangeUser) {
        this.exchangeUser = exchangeUser;
    }

    public Long getDealerId() {
        return dealerId;
    }

    public void setDealerId(Long dealerId) {
        this.dealerId = dealerId;
    }

    public String getDealer_code() {
        return dealer_code;
    }

    public void setDealer_code(String dealer_code) {
        this.dealer_code = dealer_code;
    }

    public String getDealer_name() {
        return dealer_name;
    }

    public void setDealer_name(String dealer_name) {
        this.dealer_name = dealer_name;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }
}
