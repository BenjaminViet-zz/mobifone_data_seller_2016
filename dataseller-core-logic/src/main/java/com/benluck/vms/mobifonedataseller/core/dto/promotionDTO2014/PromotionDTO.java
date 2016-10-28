package com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 3/2/15
 * Time: 11:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class PromotionDTO implements Serializable{
    private static final long serialVersionUID = -5378166066697066722L;
    private Long chuongTrinhId;
    private String code;
    private String description;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private String dbLinkName;

    public Long getChuongTrinhId() {
        return chuongTrinhId;
    }

    public void setChuongTrinhId(Long chuongTrinhId) {
        this.chuongTrinhId = chuongTrinhId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getDbLinkName() {
        return dbLinkName;
    }

    public void setDbLinkName(String dbLinkName) {
        this.dbLinkName = dbLinkName;
    }
}
