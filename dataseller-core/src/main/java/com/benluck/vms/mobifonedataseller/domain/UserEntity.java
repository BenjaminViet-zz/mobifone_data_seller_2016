package com.benluck.vms.mobifonedataseller.domain;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: benluck
 * Date: 9/21/13
 * Time: 3:22 PM
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "Vms_User")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Entity
public class UserEntity {
    private Long userId;

    @Column(name = "USERID")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VMS_USER_SEQ")
    @SequenceGenerator(name="VMS_USER_SEQ", sequenceName="VMS_USER_SEQ", allocationSize=1)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    private String userName;

    private String displayName;

    private String email;

    private Integer status;

    private String mobileNumber;

    private String password;

    private String lastTemporaryPassword;

    private Timestamp lastExpiredTime;

    private DepartmentEntity department;

    private Timestamp createdDate;

    private Timestamp modifiedDate;

    @Column(name = "USERNAME")
    @Basic
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(name = "DISPLAYNAME")
    @Basic
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Column(name = "EMAIL")
    @Basic
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "STATUS")
    @Basic
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "MOBILENUMBER")
    @Basic
    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    @Column(name = "PASSWORD")
    @Basic
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "LASTTEMPORARYPASS")
    @Basic
    public String getLastTemporaryPassword() {
        return lastTemporaryPassword;
    }

    public void setLastTemporaryPassword(String lastTemporaryPassword) {
        this.lastTemporaryPassword = lastTemporaryPassword;
    }

    @Column(name = "LASTEXPIREDTIME")
    @Basic
    public Timestamp getLastExpiredTime() {
        return lastExpiredTime;
    }

    public void setLastExpiredTime(Timestamp lastExpiredTime) {
        this.lastExpiredTime = lastExpiredTime;
    }

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="DEPARTMENTID")
    public DepartmentEntity getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentEntity department) {
        this.department = department;
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

    private List<RoleEntity> roles;
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable
            (
                    name="VMS_USER_ROLE",
                    joinColumns={ @JoinColumn(name="userid", referencedColumnName="userid") },
                    inverseJoinColumns={ @JoinColumn(name="roleid", referencedColumnName="roleid", unique=true)}
            )
    public List<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleEntity> vmsRoles) {
        this.roles = vmsRoles;
    }

    private VmsUserGroupEntity userGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @javax.persistence.JoinColumn(name = "usergroupid", referencedColumnName = "usergroupid", nullable = false)
    public VmsUserGroupEntity getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(VmsUserGroupEntity userGroup) {
        this.userGroup = userGroup;
    }

    private ChiNhanhEntity chiNhanh;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHINHANHID", referencedColumnName = "CHINHANHID")
    public ChiNhanhEntity getChiNhanh() {
        return chiNhanh;
    }

    public void setChiNhanh(ChiNhanhEntity chiNhanh) {
        this.chiNhanh = chiNhanh;
    }
}
