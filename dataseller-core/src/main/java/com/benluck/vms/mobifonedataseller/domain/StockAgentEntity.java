package com.benluck.vms.mobifonedataseller.domain;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/15/14
 * Time: 10:51 AM
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "VMS_STOCK_AGENT")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Entity
public class StockAgentEntity {
    private Long stockAgentId;
    private DepartmentEntity department;
    private Integer total;

    @Column(name = "STOCKAGENTID")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VMS_STOCK_AGENT_SEQ")
    @SequenceGenerator(name="VMS_STOCK_AGENT_SEQ", sequenceName="VMS_STOCK_AGENT_SEQ", allocationSize=1)
    public Long getStockAgentId() {
        return stockAgentId;
    }

    public void setStockAgentId(Long stockAgentId) {
        this.stockAgentId = stockAgentId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AGENTID", referencedColumnName = "DEPARTMENTID")
    public DepartmentEntity getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentEntity department) {
        this.department = department;
    }

    @Column(name = "TOTAL")
    @Basic
    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StockAgentEntity that = (StockAgentEntity) o;

        if (stockAgentId != null ? !stockAgentId.equals(that.stockAgentId) : that.stockAgentId != null) return false;
        if (total != null ? !total.equals(that.total) : that.total != null) return false;
        return  true;
    }

    @Override
    public int hashCode() {
        int result = stockAgentId != null ? stockAgentId.hashCode() : 0;
        result = 31 * result + (total != null ? total.hashCode() : 0);
        return  result;
    }
}
