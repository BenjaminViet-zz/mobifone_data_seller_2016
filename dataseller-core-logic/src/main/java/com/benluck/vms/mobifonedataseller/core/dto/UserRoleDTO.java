package com.benluck.vms.mobifonedataseller.core.dto;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: benluck
 * Date: 10/4/14
 * Time: 3:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserRoleDTO implements Serializable {
    private static final long serialVersionUID = 8631278626903413160L;

    private Long userRoleId;
    private RoleDTO role;
    private UserDTO user;

    public Long getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Long userRoleId) {
        this.userRoleId = userRoleId;
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
}
