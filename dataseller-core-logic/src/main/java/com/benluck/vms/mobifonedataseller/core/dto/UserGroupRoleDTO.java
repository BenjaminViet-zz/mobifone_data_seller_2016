package com.benluck.vms.mobifonedataseller.core.dto;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: benluck
 * Date: 10/4/14
 * Time: 3:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserGroupRoleDTO implements Serializable {
    private static final long serialVersionUID = -6049133268813011847L;

    private Long userGroupRoleId;
    private UserGroupDTO userGroup;
    private RoleDTO role;

    public Long getUserGroupRoleId() {
        return userGroupRoleId;
    }

    public void setUserGroupRoleId(Long userGroupRoleId) {
        this.userGroupRoleId = userGroupRoleId;
    }

    public UserGroupDTO getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroupDTO userGroup) {
        this.userGroup = userGroup;
    }

    public RoleDTO getRole() {
        return role;
    }

    public void setRole(RoleDTO role) {
        this.role = role;
    }
}

