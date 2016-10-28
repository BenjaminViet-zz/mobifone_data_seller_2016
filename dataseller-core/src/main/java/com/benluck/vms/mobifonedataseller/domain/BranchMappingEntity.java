package com.benluck.vms.mobifonedataseller.domain;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 3/2/15
 * Time: 10:59 PM
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "BRANCH_MAPPING")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Entity
public class BranchMappingEntity {
    private Long branchMappingId;
    private DMChuongTrinhEntity chuongTrinh;
    private ChiNhanhEntity chiNhanh;
    private Long branchId;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private UserEntity createdBy;
    private UserEntity changedBy;

    @Column(name = "BRANCHMAPPINGID")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VMS_BM_SEQ")
    @SequenceGenerator(name="VMS_BM_SEQ", sequenceName="VMS_BM_SEQ", allocationSize=1)
    public Long getBranchMappingId() {
        return branchMappingId;
    }

    public void setBranchMappingId(Long branchMappingId) {
        this.branchMappingId = branchMappingId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHUONGTRINHID", referencedColumnName = "CHUONGTRINHID")
    public DMChuongTrinhEntity getChuongTrinh() {
        return chuongTrinh;
    }

    public void setChuongTrinh(DMChuongTrinhEntity chuongTrinh) {
        this.chuongTrinh = chuongTrinh;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHINHANHID", referencedColumnName = "CHINHANHID")
    public ChiNhanhEntity getChiNhanh() {
        return chiNhanh;
    }

    public void setChiNhanh(ChiNhanhEntity chiNhanh) {
        this.chiNhanh = chiNhanh;
    }

    @Column(name = "BRANCHID")
    @Basic
    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    @Column(name = "CREATEDDATE")
    @Basic
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "MODIFIEDDATE")
    @Basic
    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATEDBYUSERID", referencedColumnName = "USERID")
    public UserEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserEntity createdBy) {
        this.createdBy = createdBy;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHANGEBYUSERID", referencedColumnName = "USERID")
    public UserEntity getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(UserEntity changedBy) {
        this.changedBy = changedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BranchMappingEntity that = (BranchMappingEntity) o;

        if (branchMappingId != null ? !branchMappingId.equals(that.branchMappingId) : that.branchMappingId != null) return false;
        if (branchId != null ? !branchId.equals(that.branchId) : that.branchId != null) return false;
        if (createdDate != null ? !createdDate.equals(that.createdDate) : that.createdDate != null) return false;
        if (modifiedDate != null ? !modifiedDate.equals(that.modifiedDate) : that.modifiedDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = branchMappingId != null ? branchMappingId.hashCode() : 0;
        result = 31 * result + (branchId != null ? branchId.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (modifiedDate != null ? modifiedDate.hashCode() : 0);
        return result;
    }
}
