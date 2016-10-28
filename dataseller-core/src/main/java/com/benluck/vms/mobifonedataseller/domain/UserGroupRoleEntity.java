package com.benluck.vms.mobifonedataseller.domain;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: benluck
 * Date: 9/21/13
 * Time: 3:22 PM
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "VMS_USERGROUP_ROLE")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Entity
public class UserGroupRoleEntity {
    private Long userGroupRoleId;

    @Column(name = "USERGROUPROLEID")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VMS_USERGROUP_ROLE_SEQ")
    @SequenceGenerator(name="VMS_USERGROUP_ROLE_SEQ", sequenceName="VMS_USERGROUP_ROLE_SEQ", allocationSize=1)
    public Long getUserRoleId() {
        return userGroupRoleId;
    }

    public void setUserRoleId(Long userGroupRoleId) {
        this.userGroupRoleId = userGroupRoleId;
    }

    private VmsUserGroupEntity userGroup;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="USERGROUPID")
    public VmsUserGroupEntity getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(VmsUserGroupEntity userGroup) {
        this.userGroup = userGroup;
    }

    private RoleEntity vmsRole;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="ROLEID")
    public RoleEntity getVmsRole() {
        return vmsRole;
    }

    public void setVmsRole(RoleEntity vmsRole) {
        this.vmsRole = vmsRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserGroupRoleEntity that = (UserGroupRoleEntity) o;

        if (userGroupRoleId != null ? !userGroupRoleId.equals(that.userGroupRoleId) : that.userGroupRoleId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return userGroupRoleId != null ? userGroupRoleId.hashCode() : 0;
    }
}
