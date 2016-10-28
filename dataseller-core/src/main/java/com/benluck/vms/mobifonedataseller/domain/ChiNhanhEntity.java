package com.benluck.vms.mobifonedataseller.domain;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/19/14
 * Time: 1:32 PM
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "VMS_CHINHANH")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Entity
public class ChiNhanhEntity {
    private Long chiNhanhId;
    private Integer chiNhanh;
    private String name;
    private Long branchId;

    @Column(name = "CHINHANHID")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VMS_CHINHANH_SEQ")
    @SequenceGenerator(name="VMS_CHINHANH_SEQ", sequenceName="VMS_CHINHANH_SEQ", allocationSize=1)
    public Long getChiNhanhId() {
        return chiNhanhId;
    }

    public void setChiNhanhId(Long chiNhanhId) {
        this.chiNhanhId = chiNhanhId;
    }

    @Column(name = "CHINHANH")
    @Basic
    public Integer getChiNhanh() {
        return chiNhanh;
    }

    public void setChiNhanh(Integer chiNhanh) {
        this.chiNhanh = chiNhanh;
    }

    @Column(name = "NAME")
    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "BRANCHID")
    @Basic
    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChiNhanhEntity that = (ChiNhanhEntity) o;

        if (chiNhanhId != null ? !chiNhanhId.equals(that.chiNhanhId) : that.chiNhanhId != null) return false;
        if (chiNhanh != null ? !chiNhanh.equals(that.chiNhanh) : that.chiNhanh != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (branchId != null ? !branchId.equals(that.branchId) : that.branchId != null) return false;
        return  true;
    }

    @Override
    public int hashCode() {
        int result = chiNhanhId != null ? chiNhanhId.hashCode() : 0;
        result = 31 * result + (chiNhanh != null ? chiNhanh.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (branchId != null ? branchId.hashCode() : 0);
        return result;
    }
}
