package com.benluck.vms.mobifonedataseller.session.impl;

import com.benluck.vms.mobifonedataseller.domain.UserGroupPermissionEntity;
import com.benluck.vms.mobifonedataseller.session.UserGroupPermissionLocalBean;

import javax.ejb.Stateless;

/**
 * Created with IntelliJ IDEA.
 * User: benluck
 * Date: 10/4/14
 * Time: 3:29 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "UserGroupPermissionSessionEJB")
public class UserGroupPermissionSessionBean extends AbstractSessionBean<UserGroupPermissionEntity, Long> implements UserGroupPermissionLocalBean {
    public UserGroupPermissionSessionBean() {
    }
}
