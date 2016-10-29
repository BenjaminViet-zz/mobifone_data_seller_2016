package com.benluck.vms.mobifonedataseller.core.dto;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: benluck
 * Date: 10/4/14
 * Time: 3:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserGroupPermissionDTO implements Serializable {
    private static final long serialVersionUID = -6049133268813011847L;

    private Long userGroupPermissionId;
    private UserGroupDTO userGroup;
    private PermissionDTO permission;

    public Long getUserGroupPermissionId() {
        return userGroupPermissionId;
    }

    public void setUserGroupPermissionId(Long userGroupPermissionId) {
        this.userGroupPermissionId = userGroupPermissionId;
    }

    public UserGroupDTO getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroupDTO userGroup) {
        this.userGroup = userGroup;
    }

    public PermissionDTO getPermission() {
        return permission;
    }

    public void setPermission(PermissionDTO permission) {
        this.permission = permission;
    }
}

