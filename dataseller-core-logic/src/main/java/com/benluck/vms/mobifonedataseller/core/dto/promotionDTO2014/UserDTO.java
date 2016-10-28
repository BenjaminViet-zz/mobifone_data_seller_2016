package com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: benluck
 * Date: 10/4/14
 * Time: 3:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserDTO implements Serializable {
    private static final long serialVersionUID = -7304789966662538769L;

    private Long userId;

    private String userName;

    private String password;

    private String lastTemporaryPassword;

    private Timestamp lastExpiredTime;

    private String email;

    private String displayName;

    private String mobileNumber;

    private Integer status;

    private Timestamp createdDate;

    private Timestamp modifiedDate;

    private Integer currentScore;

    private Timestamp lastUpdateScore;

    private DepartmentDTO department;

    private UserGroupDTO userGroup;

    private String tenChiNhanh;

    private ChiNhanhDTO chiNhanh;   // dung khi tra cuu tren DB cua minh.

    public UserDTO(){

    }

    public UserDTO(String userName, String fullName, String password){
        this.userName = userName;
        this.displayName = fullName;
        this.password = password;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastTemporaryPassword() {
        return lastTemporaryPassword;
    }

    public void setLastTemporaryPassword(String lastTemporaryPassword) {
        this.lastTemporaryPassword = lastTemporaryPassword;
    }

    public Timestamp getLastExpiredTime() {
        return lastExpiredTime;
    }

    public void setLastExpiredTime(Timestamp lastExpiredTime) {
        this.lastExpiredTime = lastExpiredTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Integer getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(Integer currentScore) {
        this.currentScore = currentScore;
    }

    public Timestamp getLastUpdateScore() {
        return lastUpdateScore;
    }

    public void setLastUpdateScore(Timestamp lastUpdateScore) {
        this.lastUpdateScore = lastUpdateScore;
    }

    public DepartmentDTO getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentDTO department) {
        this.department = department;
    }

    public UserGroupDTO getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroupDTO userGroup) {
        this.userGroup = userGroup;
    }

    // more
    private Timestamp fromDate;
    private Timestamp toDate;
    private Long chiNhanhId;

    public Timestamp getFromDate() {
        return fromDate;
    }

    public void setFromDate(Timestamp fromDate) {
        this.fromDate = fromDate;
    }

    public Timestamp getToDate() {
        return toDate;
    }

    public void setToDate(Timestamp toDate) {
        this.toDate = toDate;
    }

    public String getTenChiNhanh() {
        return tenChiNhanh;
    }

    public void setTenChiNhanh(String tenChiNhanh) {
        this.tenChiNhanh = tenChiNhanh;
    }

    public Long getChiNhanhId() {
        return chiNhanhId;
    }

    public void setChiNhanhId(Long chiNhanhId) {
        this.chiNhanhId = chiNhanhId;
    }

    public ChiNhanhDTO getChiNhanh() {
        return chiNhanh;
    }

    public void setChiNhanh(ChiNhanhDTO chiNhanh) {
        this.chiNhanh = chiNhanh;
    }
}
