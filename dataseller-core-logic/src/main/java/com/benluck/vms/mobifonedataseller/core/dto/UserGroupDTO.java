package com.benluck.vms.mobifonedataseller.core.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: benluck
 * Date: 10/4/14
 * Time: 3:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserGroupDTO implements Serializable {
    private static final long serialVersionUID = 5738387028325118615L;

    private Long userGroupId;
    private String code;
    private String description;
    private List<UserGroupPermissionDTO> userGroupPermissionList;
    private List<PermissionDTO> permissionList;

    public Long getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(Long userGroupId) {
        this.userGroupId = userGroupId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<UserGroupPermissionDTO> getUserGroupPermissionList() {
        return userGroupPermissionList;
    }

    public void setUserGroupPermissionList(List<UserGroupPermissionDTO> userGroupPermissionList) {
        this.userGroupPermissionList = userGroupPermissionList;
    }

    public List<PermissionDTO> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<PermissionDTO> permissionList) {
        this.permissionList = permissionList;
    }
}
