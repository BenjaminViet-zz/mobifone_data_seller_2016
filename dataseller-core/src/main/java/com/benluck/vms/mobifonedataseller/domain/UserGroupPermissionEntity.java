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
@Table(name = "MOBI_DATA_USERGROUPPERMISSION")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Entity
public class UserGroupPermissionEntity {
    private Long userGroupPermissionId;
    private UserGroupEntity userGroup;
    private PermissionEntity permission;

    @Column(name = "USERGROUPPERMISSIONID")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MOBI_DATA_USERGROUPPER_SEQ")
    @SequenceGenerator(name="MOBI_DATA_USERGROUPPER_SEQ", sequenceName="MOBI_DATA_USERGROUPPER_SEQ", allocationSize=1)
    public Long getUserGroupPermissionId() {
        return userGroupPermissionId;
    }

    public void setUserGroupPermissionId(Long userGroupPermissionId) {
        this.userGroupPermissionId = userGroupPermissionId;
    }

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="USERGROUPID")
    public UserGroupEntity getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroupEntity userGroup) {
        this.userGroup = userGroup;
    }

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="PERMISSIONID")
    public PermissionEntity getPermission() {
        return permission;
    }

    public void setPermission(PermissionEntity permission) {
        this.permission = permission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserGroupPermissionEntity that = (UserGroupPermissionEntity) o;

        if (userGroupPermissionId != null ? !userGroupPermissionId.equals(that.userGroupPermissionId) : that.userGroupPermissionId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return userGroupPermissionId != null ? userGroupPermissionId.hashCode() : 0;
    }
}
