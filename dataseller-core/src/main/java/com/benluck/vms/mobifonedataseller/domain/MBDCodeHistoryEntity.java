package com.benluck.vms.mobifonedataseller.domain;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/9/16
 * Time: 21:20
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "MOBI_DATA_CODE_HISTORY")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Entity
public class MBDCodeHistoryEntity {
    private Long transId;
    private Long subId;
    private String isdn;
    private Timestamp regDateTime;
    private Long custId;
    private String name;
    private Timestamp staDateTime;
    private String tin;
    private Timestamp insertDateTime;

    @Column(name = "TRANS_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MOBI_DATA_CODE_HISTORY_SEQ")
    @SequenceGenerator(name="MOBI_DATA_CODE_HISTORY_SEQ", sequenceName="MOBI_DATA_CODE_HISTORY_SEQ", allocationSize=1)
    public Long getTransId() {
        return transId;
    }

    public void setTransId(Long transId) {
        this.transId = transId;
    }

    @Column(name = "SUB_ID")
    @Basic
    public Long getSubId() {
        return subId;
    }

    public void setSubId(Long subId) {
        this.subId = subId;
    }

    @Column(name = "ISDN")
    @Basic
    public String getIsdn() {
        return isdn;
    }

    public void setIsdn(String isdn) {
        this.isdn = isdn;
    }

    @Column(name = "REG_DATETIME")
    @Basic
    public Timestamp getRegDateTime() {
        return regDateTime;
    }

    public void setRegDateTime(Timestamp regDateTime) {
        this.regDateTime = regDateTime;
    }

    @Column(name = "CUST_ID")
    @Basic
    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    @Column(name = "NAME")
    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "STA_DATETIME")
    @Basic
    public Timestamp getStaDateTime() {
        return staDateTime;
    }

    public void setStaDateTime(Timestamp staDateTime) {
        this.staDateTime = staDateTime;
    }

    @Column(name = "TIN")
    @Basic
    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }


    @Column(name = "INSERT_DATETIME")
    @Basic
    public Timestamp getInsertDateTime() {
        return insertDateTime;
    }

    public void setInsertDateTime(Timestamp insertDateTime) {
        this.insertDateTime = insertDateTime;
    }

    @Override
    public int hashCode() {
        int result = transId != null ? transId.hashCode() : 0;
        result = 31 * result + (subId != null ? subId.hashCode() : 0);
        result = 31 * result + (isdn != null ? isdn.hashCode() : 0);
        result = 31 * result + (regDateTime != null ? regDateTime.hashCode() : 0);
        result = 31 * result + (custId != null ? custId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (staDateTime != null ? staDateTime.hashCode() : 0);
        result = 31 * result + (tin != null ? tin.hashCode() : 0);
        result = 31 * result + (insertDateTime != null ? insertDateTime.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MBDCodeHistoryEntity that = (MBDCodeHistoryEntity) o;

        if (transId != null ? !transId.equals(that.transId) : that.transId != null) return false;
        if (subId != null ? !subId.equals(that.subId) : that.subId != null) return false;
        if (isdn != null ? !isdn.equals(that.isdn) : that.isdn != null) return false;
        if (regDateTime != null ? !regDateTime.equals(that.regDateTime) : that.regDateTime != null) return false;
        if (custId != null ? !custId.equals(that.custId) : that.custId != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (staDateTime != null ? !staDateTime.equals(that.staDateTime) : that.staDateTime != null) return false;
        if (tin != null ? !tin.equals(that.tin) : that.tin != null) return false;
        if (insertDateTime != null ? !insertDateTime.equals(that.insertDateTime) : that.insertDateTime != null) return false;

        return true;
    }
}
