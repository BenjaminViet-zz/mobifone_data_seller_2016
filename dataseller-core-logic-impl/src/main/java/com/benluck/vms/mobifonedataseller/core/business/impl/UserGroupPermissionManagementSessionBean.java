package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.core.business.UserGroupPermissionManagementLocalBean;
import com.benluck.vms.mobifonedataseller.session.UserGroupPermissionLocalBean;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 5/22/17
 * Time: 22:19
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "UserGroupPermissionManagementSessionEJB")
public class UserGroupPermissionManagementSessionBean implements UserGroupPermissionManagementLocalBean{

    @EJB
    private UserGroupPermissionLocalBean userGroupPermissionService;

    public UserGroupPermissionManagementSessionBean() {
    }
}
