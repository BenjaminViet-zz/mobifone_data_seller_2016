package com.benluck.vms.mobifonedataseller.domain;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/6/16
 * Time: 20:35
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "MOBI_DATA_PACKAGEDATA_CODE_GEN")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Entity
public class PackageDataCodeGenEntity {
    private Long packageDataCodeGenId;
    private PackageDataEntity packageData;
    private Integer year;
    private Integer batch1_Remaining;
    private Integer batch2_Remaining;
    private Integer batch3_Remaining;
    private Integer batch4_Remaining;
    private Integer batch5_Remaining;
    private Integer batch6_Remaining;
    private Integer batch7_Remaining;
    private Integer batch8_Remaining;
    private Integer batch9_Remaining;
    private Timestamp createdDate;
    private Integer status;

    @Column(name = "PACKAGEDATACODEGENID")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MOBI_DATA_PDCGEN_PKD_SEQ")
    @SequenceGenerator(name="MOBI_DATA_PDCGEN_PKD_SEQ", sequenceName="MOBI_DATA_PDCGEN_PKD_SEQ", allocationSize=1)
    public Long getPackageDataCodeGenId() {
        return packageDataCodeGenId;
    }

    public void setPackageDataCodeGenId(Long packageDataCodeGenId) {
        this.packageDataCodeGenId = packageDataCodeGenId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PACKAGEDATAID", referencedColumnName = "PACKAGEDATAID")
    public PackageDataEntity getPackageData() {
        return packageData;
    }

    public void setPackageData(PackageDataEntity packageData) {
        this.packageData = packageData;
    }

    @Column(name = "YEAR")
    @Basic
    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Column(name = "CREATEDDATE")
    @Basic
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "BATCH1_REMAINING")
    @Basic
    public Integer getBatch1_Remaining() {
        return batch1_Remaining;
    }

    public void setBatch1_Remaining(Integer batch1_Remaining) {
        this.batch1_Remaining = batch1_Remaining;
    }

    @Column(name = "BATCH2_REMAINING")
    @Basic
    public Integer getBatch2_Remaining() {
        return batch2_Remaining;
    }

    public void setBatch2_Remaining(Integer batch2_Remaining) {
        this.batch2_Remaining = batch2_Remaining;
    }

    @Column(name = "BATCH3_REMAINING")
    @Basic
    public Integer getBatch3_Remaining() {
        return batch3_Remaining;
    }

    public void setBatch3_Remaining(Integer batch3_Remaining) {
        this.batch3_Remaining = batch3_Remaining;
    }

    @Column(name = "BATCH4_REMAINING")
    @Basic
    public Integer getBatch4_Remaining() {
        return batch4_Remaining;
    }

    public void setBatch4_Remaining(Integer batch4_Remaining) {
        this.batch4_Remaining = batch4_Remaining;
    }

    @Column(name = "BATCH5_REMAINING")
    @Basic
    public Integer getBatch5_Remaining() {
        return batch5_Remaining;
    }

    public void setBatch5_Remaining(Integer batch5_Remaining) {
        this.batch5_Remaining = batch5_Remaining;
    }

    @Column(name = "BATCH6_REMAINING")
    @Basic
    public Integer getBatch6_Remaining() {
        return batch6_Remaining;
    }

    public void setBatch6_Remaining(Integer batch6_Remaining) {
        this.batch6_Remaining = batch6_Remaining;
    }

    @Column(name = "BATCH7_REMAINING")
    @Basic
    public Integer getBatch7_Remaining() {
        return batch7_Remaining;
    }

    public void setBatch7_Remaining(Integer batch7_Remaining) {
        this.batch7_Remaining = batch7_Remaining;
    }

    @Column(name = "BATCH8_REMAINING")
    @Basic
    public Integer getBatch8_Remaining() {
        return batch8_Remaining;
    }

    public void setBatch8_Remaining(Integer batch8_Remaining) {
        this.batch8_Remaining = batch8_Remaining;
    }

    @Column(name = "BATCH9_REMAINING")
    @Basic
    public Integer getBatch9_Remaining() {
        return batch9_Remaining;
    }

    public void setBatch9_Remaining(Integer batch9_Remaining) {
        this.batch9_Remaining = batch9_Remaining;
    }

    @Column(name = "STATUS")
    @Basic
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int result = packageDataCodeGenId != null ? packageDataCodeGenId.hashCode() : 0;
        result = 31 * result + (year != null ? year.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (batch1_Remaining != null ? batch1_Remaining.hashCode() : 0);
        result = 31 * result + (batch2_Remaining != null ? batch2_Remaining.hashCode() : 0);
        result = 31 * result + (batch3_Remaining != null ? batch3_Remaining.hashCode() : 0);
        result = 31 * result + (batch4_Remaining != null ? batch4_Remaining.hashCode() : 0);
        result = 31 * result + (batch5_Remaining != null ? batch5_Remaining.hashCode() : 0);
        result = 31 * result + (batch6_Remaining != null ? batch6_Remaining.hashCode() : 0);
        result = 31 * result + (batch7_Remaining != null ? batch7_Remaining.hashCode() : 0);
        result = 31 * result + (batch8_Remaining != null ? batch8_Remaining.hashCode() : 0);
        result = 31 * result + (batch9_Remaining != null ? batch9_Remaining.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PackageDataCodeGenEntity that = (PackageDataCodeGenEntity) o;

        if (packageDataCodeGenId != null ? !packageDataCodeGenId.equals(that.packageDataCodeGenId) : that.packageDataCodeGenId != null) return false;
        if (year != null ? !year.equals(that.year) : that.year != null) return false;
        if (createdDate != null ? !createdDate.equals(that.createdDate) : that.createdDate != null) return false;
        if (batch1_Remaining != null ? !batch1_Remaining.equals(that.batch1_Remaining) : that.batch1_Remaining != null) return false;
        if (batch2_Remaining != null ? !batch2_Remaining.equals(that.batch2_Remaining) : that.batch2_Remaining != null) return false;
        if (batch3_Remaining != null ? !batch3_Remaining.equals(that.batch3_Remaining) : that.batch3_Remaining != null) return false;
        if (batch4_Remaining != null ? !batch4_Remaining.equals(that.batch4_Remaining) : that.batch4_Remaining != null) return false;
        if (batch5_Remaining != null ? !batch5_Remaining.equals(that.batch5_Remaining) : that.batch5_Remaining != null) return false;
        if (batch6_Remaining != null ? !batch6_Remaining.equals(that.batch6_Remaining) : that.batch6_Remaining != null) return false;
        if (batch7_Remaining != null ? !batch7_Remaining.equals(that.batch7_Remaining) : that.batch7_Remaining != null) return false;
        if (batch8_Remaining != null ? !batch8_Remaining.equals(that.batch8_Remaining) : that.batch8_Remaining != null) return false;
        if (batch9_Remaining != null ? !batch9_Remaining.equals(that.batch9_Remaining) : that.batch9_Remaining != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;

        return true;
    }
}
