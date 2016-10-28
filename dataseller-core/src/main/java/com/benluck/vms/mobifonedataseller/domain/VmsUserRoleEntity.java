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
@Table(name = "VMS_USER_ROLE")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Entity
public class VmsUserRoleEntity {
    private Long userRoleId;

    @Column(name = "USERROLEID")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VMS_USER_ROLE_SEQ")
    @SequenceGenerator(name="VMS_USER_ROLE_SEQ", sequenceName="VMS_USER_ROLE_SEQ", allocationSize=1)
    public Long getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Long userRoleId) {
        this.userRoleId = userRoleId;
    }

    private RoleEntity role;

    private UserEntity user;


    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="ROLEID")
    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="USERID")
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
