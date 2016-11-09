package com.benluck.vms.mobifonedataseller.domain;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 10/30/16
 * Time: 22:39
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "MOBI_DATA_KHDN")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Entity
public class KHDNEntity {
    private Long KHDNId;
    private String name;
    private String mst;
    private String gpkd;
    private Timestamp issuedContractDate;
    private String stb_vas;
    private Long custId;

    @Column(name = "KHDNID")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MOBI_DATA_KHDN_SEQ")
    @SequenceGenerator(name="MOBI_DATA_KHDN_SEQ", sequenceName="MOBI_DATA_KHDN_SEQ", allocationSize=1)
    public Long getKHDNId() {
        return KHDNId;
    }

    public void setKHDNId(Long KHDNId) {
        this.KHDNId = KHDNId;
    }

    @Column(name = "NAME")
    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "MST")
    @Basic
    public String getMst() {
        return mst;
    }

    public void setMst(String mst) {
        this.mst = mst;
    }

    @Column(name = "GPKD")
    @Basic
    public String getGpkd() {
        return gpkd;
    }

    public void setGpkd(String gpkd) {
        this.gpkd = gpkd;
    }

    @Column(name = "ISSUEDCONTRACTDATE")
    @Basic
    public Timestamp getIssuedContractDate() {
        return issuedContractDate;
    }

    public void setIssuedContractDate(Timestamp issuedContractDate) {
        this.issuedContractDate = issuedContractDate;
    }

    @Column(name = "STB_VAS")
    @Basic
    public String getStb_vas() {
        return stb_vas;
    }

    public void setStb_vas(String stb_vas) {
        this.stb_vas = stb_vas;
    }

    @Column(name = "CUST_ID")
    @Basic
    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    @Override
    public int hashCode() {
        int result = KHDNId != null ? KHDNId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (mst != null ? mst.hashCode() : 0);
        result = 31 * result + (gpkd != null ? gpkd.hashCode() : 0);
        result = 31 * result + (issuedContractDate != null ? issuedContractDate.hashCode() : 0);
        result = 31 * result + (stb_vas != null ? stb_vas.hashCode() : 0);
        result = 31 * result + (custId != null ? custId.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KHDNEntity that = (KHDNEntity) o;

        if (KHDNId != null ? !KHDNId.equals(that.KHDNId) : that.KHDNId != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (mst != null ? !mst.equals(that.mst) : that.mst != null) return false;
        if (gpkd != null ? !gpkd.equals(that.gpkd) : that.gpkd != null) return false;
        if (issuedContractDate != null ? !issuedContractDate.equals(that.issuedContractDate) : that.issuedContractDate != null) return false;
        if (stb_vas != null ? !stb_vas.equals(that.stb_vas) : that.stb_vas != null) return false;
        if (custId != null ? !custId.equals(that.custId) : that.custId != null) return false;

        return true;
    }
}
