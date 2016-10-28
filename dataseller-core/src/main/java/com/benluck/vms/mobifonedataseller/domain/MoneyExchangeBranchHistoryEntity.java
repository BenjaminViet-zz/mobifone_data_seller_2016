package com.benluck.vms.mobifonedataseller.domain;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/26/14
 * Time: 3:27 PM
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "VMS_MONEY_EXCHANGE_BRANCH_HIS")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Entity
public class MoneyExchangeBranchHistoryEntity {
    private Long moneyExchangeBranchHistoryId;
    private MoneyExchangeBranchEntity moneyExchangeBranch;
    private Integer cycle;
    private Double exchangeAmount;
    private Timestamp createdDate;

    @Column(name = "MONEYEXCHANGEBRANCHHISID")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VMS_MONEY_EXCHANGE_BRA_HIS_SEQ")
    @SequenceGenerator(name="VMS_MONEY_EXCHANGE_BRA_HIS_SEQ", sequenceName="VMS_MONEY_EXCHANGE_BRA_HIS_SEQ", allocationSize=1)
    public Long getMoneyExchangeBranchHistoryId() {
        return moneyExchangeBranchHistoryId;
    }

    public void setMoneyExchangeBranchHistoryId(Long moneyExchangeBranchHistoryId) {
        this.moneyExchangeBranchHistoryId = moneyExchangeBranchHistoryId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MONEYEXCHANGEBRANCHID", referencedColumnName = "MONEYEXCHANGEBRANCHID")
    public MoneyExchangeBranchEntity getMoneyExchangeBranch() {
        return moneyExchangeBranch;
    }

    public void setMoneyExchangeBranch(MoneyExchangeBranchEntity moneyExchangeBranch) {
        this.moneyExchangeBranch = moneyExchangeBranch;
    }

    @Column(name = "CYCLE")
    @Basic
    public Integer getCycle() {
        return cycle;
    }

    public void setCycle(Integer cycle) {
        this.cycle = cycle;
    }

    @Column(name = "EXCHANGEAMOUNT")
    @Basic
    public Double getExchangeAmount() {
        return exchangeAmount;
    }

    public void setExchangeAmount(Double exchangeAmount) {
        this.exchangeAmount = exchangeAmount;
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

        MoneyExchangeBranchHistoryEntity that = (MoneyExchangeBranchHistoryEntity) o;

        if (moneyExchangeBranchHistoryId != null ? !moneyExchangeBranchHistoryId.equals(that.moneyExchangeBranchHistoryId) : that.moneyExchangeBranchHistoryId != null) return false;
        if (cycle != null ? !cycle.equals(that.cycle) : that.cycle != null) return false;
        if (exchangeAmount != null ? !exchangeAmount.equals(that.exchangeAmount) : that.exchangeAmount != null) return false;
        if (createdDate != null ? !createdDate.equals(that.createdDate) : that.createdDate != null) return false;
        return  true;
    }

    @Override
    public int hashCode() {
        int result = moneyExchangeBranchHistoryId != null ? moneyExchangeBranchHistoryId.hashCode() : 0;
        result = 31 * result + (cycle != null ? cycle.hashCode() : 0);
        result = 31 * result + (exchangeAmount != null ? exchangeAmount.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        return result;
    }
}
