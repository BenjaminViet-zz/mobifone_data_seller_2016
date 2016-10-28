package com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: benluck
 * Date: 10/4/14
 * Time: 3:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserRoleDTO implements Serializable {

    private Long userRoleId;
    private UserDTO user;
    private RoleDTO role;
    private Long userID;
    private String[] roleID;
    private String[] deleteRole;

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String[] getRoleID() {
        return roleID;
    }

    public void setRoleID(String[] roleID) {
        this.roleID = roleID;
    }

    public String[] getDeleteRole() {
        return deleteRole;
    }

    public void setDeleteRole(String[] deleteRole) {
        this.deleteRole = deleteRole;
    }

    public RoleDTO getRole() {
        return role;
    }

    public void setRole(RoleDTO role) {
        this.role = role;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public Long getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Long userRoleId) {
        this.userRoleId = userRoleId;
    }

}
