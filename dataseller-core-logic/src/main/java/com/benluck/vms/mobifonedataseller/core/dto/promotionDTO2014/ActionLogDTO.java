package com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014;

import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015.RegistrationTransDTO;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 3/16/15
 * Time: 11:13 AM
 * To change this template use File | Settings | File Templates.
 */
public class ActionLogDTO implements Serializable{
    private static final long serialVersionUID = -6562813859465100376L;

    private BranchDTO branch;
    private DistrictDTO district;
    private RetailDealerDTO retailDealer;
    private UserDTO user;
    private RegistrationTransDTO registrationTrans;
    private Long logId;
    private PromotionDTO chuongTrinh;
    private String tableLog;
    private String columnIdLogName;
    private UserDTO nguoiThaoTac;
    private Timestamp createdTime;
    private String description;
    private Long columnIdLogValue;

//    Getter and Setter methods

    public BranchDTO getBranch() {
        return branch;
    }

    public void setBranch(BranchDTO branch) {
        this.branch = branch;
    }

    public DistrictDTO getDistrict() {
        return district;
    }

    public void setDistrict(DistrictDTO district) {
        this.district = district;
    }

    public RetailDealerDTO getRetailDealer() {
        return retailDealer;
    }

    public void setRetailDealer(RetailDealerDTO retailDealer) {
        this.retailDealer = retailDealer;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public RegistrationTransDTO getRegistrationTrans() {
        return registrationTrans;
    }

    public void setRegistrationTrans(RegistrationTransDTO registrationTrans) {
        this.registrationTrans = registrationTrans;
    }

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public PromotionDTO getChuongTrinh() {
        return chuongTrinh;
    }

    public void setChuongTrinh(PromotionDTO chuongTrinh) {
        this.chuongTrinh = chuongTrinh;
    }

    public String getTableLog() {
        return tableLog;
    }

    public void setTableLog(String tableLog) {
        this.tableLog = tableLog;
    }

    public String getColumnIdLogName() {
        return columnIdLogName;
    }

    public void setColumnIdLogName(String columnIdLogName) {
        this.columnIdLogName = columnIdLogName;
    }

    public UserDTO getNguoiThaoTac() {
        return nguoiThaoTac;
    }

    public void setNguoiThaoTac(UserDTO nguoiThaoTac) {
        this.nguoiThaoTac = nguoiThaoTac;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getColumnIdLogValue() {
        return columnIdLogValue;
    }

    public void setColumnIdLogValue(Long columnIdLogValue) {
        this.columnIdLogValue = columnIdLogValue;
    }
}
