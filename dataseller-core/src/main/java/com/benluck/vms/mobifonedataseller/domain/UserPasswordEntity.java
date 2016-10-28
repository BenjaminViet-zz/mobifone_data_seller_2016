package com.benluck.vms.mobifonedataseller.domain;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: benluck
 * Date: 9/21/13
 * Time: 3:22 PM
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "VMS_USER_PASSWORD")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Entity
public class UserPasswordEntity {
    private Long userPasswordId;

    @Column(name = "USERPASSWORDID")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VMS_USER_PASSWORD_SEQ")
    @SequenceGenerator(name="VMS_USER_PASSWORD_SEQ", sequenceName="VMS_USER_PASSWORD_SEQ", allocationSize=1)
    public Long getUserPasswordId() {
        return userPasswordId;
    }

    public void setUserPasswordId(Long userPasswordId) {
        this.userPasswordId = userPasswordId;
    }

    private String password;

    @Column(name = "PASSWORD")
    @Basic
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private Integer status;

    @Column(name = "STATUS")
    @Basic
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    private Timestamp createdTime;

    @Column(name = "CREATEDTIME")
    @Basic
    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    private Timestamp expiredTime;

    @Column(name = "EXPIREDTIME")
    @Basic
    public Timestamp getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Timestamp expiredTime) {
        this.expiredTime = expiredTime;
    }

    private UserEntity user;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="USERID")
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    private Integer passwordType;

    @Column(name = "PASSWORD_TYPE")
    @Basic
    public Integer getPasswordType() {
        return passwordType;
    }

    public void setPasswordType(Integer passwordType) {
        this.passwordType = passwordType;
    }
}
