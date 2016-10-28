package com.benluck.vms.mobifonedataseller.domain;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/18/14
 * Time: 5:34 PM
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "VMS_SHOPCODE_CHINHANH")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Entity
public class ShopCodeChiNhanhEntity {
    private Long shopCodeChiNhanhId;
    private String shopCode;
    private ChiNhanhEntity chiNhanh;
    private List<DepartmentEntity> departmentList;

    @Column(name = "SHOPCODECNID")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VMS_SHOPCODE_CHINHANH_SEQ")
    @SequenceGenerator(name="VMS_SHOPCODE_CHINHANH_SEQ", sequenceName="VMS_SHOPCODE_CHINHANH_SEQ", allocationSize=1)
    public Long getShopCodeChiNhanhId() {
        return shopCodeChiNhanhId;
    }

    public void setShopCodeChiNhanhId(Long shopCodeChiNhanhId) {
        this.shopCodeChiNhanhId = shopCodeChiNhanhId;
    }

    @Column(name = "SHOPCODE")
    @Basic
    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CHINHANHID", referencedColumnName = "CHINHANHID")
    public ChiNhanhEntity getChiNhanh() {
        return chiNhanh;
    }

    public void setChiNhanh(ChiNhanhEntity chiNhanh) {
        this.chiNhanh = chiNhanh;
    }

    @OneToMany(cascade=CascadeType.REMOVE, orphanRemoval = false)
    @JoinColumn(name="CODE", insertable = false, updatable = false)
    public List<DepartmentEntity> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(List<DepartmentEntity> departmentList) {
        this.departmentList = departmentList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShopCodeChiNhanhEntity that = (ShopCodeChiNhanhEntity) o;

        if (shopCodeChiNhanhId != null ? !shopCodeChiNhanhId.equals(that.shopCodeChiNhanhId) : that.shopCodeChiNhanhId != null) return false;
        if (shopCode != null ? !shopCode.equals(that.shopCode) : that.shopCode != null) return false;
        if (chiNhanh != null ? !chiNhanh.equals(that.chiNhanh) : that.chiNhanh != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = shopCodeChiNhanhId != null ? shopCodeChiNhanhId.hashCode() : 0;
        result = 31 * result + (shopCode != null ? shopCode.hashCode() : 0);
        result = 31 * result + (chiNhanh != null ? chiNhanh.hashCode() : 0);
        return  result;
    }
}
