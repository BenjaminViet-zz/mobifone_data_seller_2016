package com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 3/2/15
 * Time: 11:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class BranchMappingDTO implements Serializable{
    private static final long serialVersionUID = 7291524048710454354L;

    private Long branchMappingId;
    private PromotionDTO chuongTrinh;
    private ChiNhanhDTO chiNhanh;
    private Long branchId;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private UserDTO createdBy;
    private UserDTO changedBy;

    public Long getBranchMappingId() {
        return branchMappingId;
    }

    public void setBranchMappingId(Long branchMappingId) {
        this.branchMappingId = branchMappingId;
    }

    public PromotionDTO getChuongTrinh() {
        return chuongTrinh;
    }

    public void setChuongTrinh(PromotionDTO chuongTrinh) {
        this.chuongTrinh = chuongTrinh;
    }

    public ChiNhanhDTO getChiNhanh() {
        return chiNhanh;
    }

    public void setChiNhanh(ChiNhanhDTO chiNhanh) {
        this.chiNhanh = chiNhanh;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
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

    public UserDTO getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserDTO createdBy) {
        this.createdBy = createdBy;
    }

    public UserDTO getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(UserDTO changedBy) {
        this.changedBy = changedBy;
    }
}
