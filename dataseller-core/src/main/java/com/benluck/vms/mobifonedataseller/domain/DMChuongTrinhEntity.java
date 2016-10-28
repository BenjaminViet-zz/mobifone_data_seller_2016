package com.benluck.vms.mobifonedataseller.domain;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: Viet Pham
 * Date: 3/2/15
 * Time: 10:50 PM
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "DM_CHUONG_TRINH")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Entity
public class DMChuongTrinhEntity {
    private Long chuongTrinhId;
    private String code;
    private String description;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private String dbLinkName;

    @Column(name = "CHUONGTRINHID")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VMS_DM_CT_SEQ")
    @SequenceGenerator(name="VMS_DM_CT_SEQ", sequenceName="VMS_DM_CT_SEQ", allocationSize=1)
    public Long getChuongTrinhId() {
        return chuongTrinhId;
    }

    public void setChuongTrinhId(Long chuongTrinhId) {
        this.chuongTrinhId = chuongTrinhId;
    }

    @Column(name = "CODE")
    @Basic
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "DESCRIPTION")
    @Basic
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Column(name = "DBLINK_NAME")
    @Basic
    public String getDbLinkName() {
        return dbLinkName;
    }

    public void setDbLinkName(String dbLinkName) {
        this.dbLinkName = dbLinkName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DMChuongTrinhEntity that = (DMChuongTrinhEntity) o;

        if (chuongTrinhId != null ? !chuongTrinhId.equals(that.chuongTrinhId) : that.chuongTrinhId != null) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (createdDate != null ? !createdDate.equals(that.createdDate) : that.createdDate != null) return false;
        if (modifiedDate != null ? !modifiedDate.equals(that.modifiedDate) : that.modifiedDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = chuongTrinhId != null ? chuongTrinhId.hashCode() : 0;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (modifiedDate != null ? modifiedDate.hashCode() : 0);
        return result;
    }
}
