package com.benluck.vms.mobifonedataseller.domain;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.persistence.criteria.Join;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/26/14
 * Time: 3:11 PM
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "VMS_MONEY_EXCHANGE_BRANCH")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Entity
public class MoneyExchangeBranchEntity {
    private Long moneyExchangeBranchId;
    private UserEntity exchangeUser;
    private Long dealerId;
    private String dealer_code;
    private String dealer_name;
    private Timestamp createdDate;

    @Column(name = "MONEYEXCHANGEBRANCHID")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VMS_MONEY_EXCHANGE_BRANCH_SEQ")
    @SequenceGenerator(name="VMS_MONEY_EXCHANGE_BRANCH_SEQ", sequenceName="VMS_MONEY_EXCHANGE_BRANCH_SEQ", allocationSize=1)
    public Long getMoneyExchangeBranchId() {
        return moneyExchangeBranchId;
    }

    public void setMoneyExchangeBranchId(Long moneyExchangeBranchId) {
        this.moneyExchangeBranchId = moneyExchangeBranchId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EXCHANGEUSERID", referencedColumnName = "USERID")
    public UserEntity getExchangeUser() {
        return exchangeUser;
    }

    public void setExchangeUser(UserEntity exchangeUser) {
        this.exchangeUser = exchangeUser;
    }

    @Column(name = "DEALER_ID")
    @Basic
    public Long getDealerId() {
        return dealerId;
    }

    public void setDealerId(Long dealerId) {
        this.dealerId = dealerId;
    }

    @Column(name = "DEALER_CODE")
    @Basic
    public String getDealer_code() {
        return dealer_code;
    }

    public void setDealer_code(String dealer_code) {
        this.dealer_code = dealer_code;
    }

    @Column(name = "DEALER_NAME")
    @Basic
    public String getDealer_name() {
        return dealer_name;
    }

    public void setDealer_name(String dealer_name) {
        this.dealer_name = dealer_name;
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

        MoneyExchangeBranchEntity that = (MoneyExchangeBranchEntity) o;

        if (moneyExchangeBranchId != null ? !moneyExchangeBranchId.equals(that.moneyExchangeBranchId) : that.moneyExchangeBranchId != null) return false;
        if (dealerId != null ? !dealerId.equals(that.dealerId) : that.dealerId != null) return false;
        if (dealer_code != null ? !dealer_code.equals(that.dealer_code) : that.dealer_code != null) return false;
        if (dealer_name != null ? !dealer_name.equals(that.dealer_name) : that.dealer_name != null) return false;
        if (createdDate != null ? !createdDate.equals(that.createdDate) : that.createdDate != null) return false;
        return  true;
    }

    @Override
    public int hashCode() {
        int result = moneyExchangeBranchId != null ? moneyExchangeBranchId.hashCode() : 0;
        result = 31 * result + (dealerId != null ? dealerId.hashCode() : 0);
        result = 31 * result + (dealer_code != null ? dealer_code.hashCode() : 0);
        result = 31 * result + (dealer_name != null ? dealer_name.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        return result;
    }
}
