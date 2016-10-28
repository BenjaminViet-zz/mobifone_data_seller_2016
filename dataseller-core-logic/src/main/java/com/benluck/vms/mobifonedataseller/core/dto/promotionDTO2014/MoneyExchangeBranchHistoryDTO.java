package com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/26/14
 * Time: 3:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class MoneyExchangeBranchHistoryDTO implements Serializable {
    private static final long serialVersionUID = -8740578072952365725L;

    private Long moneyExchangeBranchHistoryId;
    private MoneyExchangeBranchDTO moneyExchangeBranch;
    private Integer cycle;
    private Double exchangeAmount;
    private Timestamp createdDate;
    private String item_name;

    private RetailDealerDTO retailDealer;
    private BranchDTO branch;
    private DistrictDTO district;

    public Long getMoneyExchangeBranchHistoryId() {
        return moneyExchangeBranchHistoryId;
    }

    public void setMoneyExchangeBranchHistoryId(Long moneyExchangeBranchHistoryId) {
        this.moneyExchangeBranchHistoryId = moneyExchangeBranchHistoryId;
    }

    public MoneyExchangeBranchDTO getMoneyExchangeBranch() {
        return moneyExchangeBranch;
    }

    public void setMoneyExchangeBranch(MoneyExchangeBranchDTO moneyExchangeBranch) {
        this.moneyExchangeBranch = moneyExchangeBranch;
    }

    public Integer getCycle() {
        return cycle;
    }

    public void setCycle(Integer cycle) {
        this.cycle = cycle;
    }

    public Double getExchangeAmount() {
        return exchangeAmount;
    }

    public void setExchangeAmount(Double exchangeAmount) {
        this.exchangeAmount = exchangeAmount;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public RetailDealerDTO getRetailDealer() {
        return retailDealer;
    }

    public void setRetailDealer(RetailDealerDTO retailDealer) {
        this.retailDealer = retailDealer;
    }

    public BranchDTO getBranch() {
        return branch;
    }

    public void setBranch(BranchDTO branch) {
        this.branch = branch;
    }

    public DistrictDTO getDistrict() {
        return district;
    }

    public void setDistrict(DistrictDTO district) {
        this.district = district;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }
}
