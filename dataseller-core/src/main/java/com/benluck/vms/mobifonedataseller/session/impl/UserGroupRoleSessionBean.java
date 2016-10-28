package com.benluck.vms.mobifonedataseller.session.impl;

import com.benluck.vms.mobifonedataseller.domain.UserGroupRoleEntity;
import com.benluck.vms.mobifonedataseller.session.UserGroupRoleLocalBean;

import javax.ejb.Stateless;

/**
 * Created with IntelliJ IDEA.
 * User: benluck
 * Date: 10/4/14
 * Time: 3:29 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "UserGroupRoleSessionEJB")
public class UserGroupRoleSessionBean extends AbstractSessionBean<UserGroupRoleEntity, Long> implements UserGroupRoleLocalBean {
    public UserGroupRoleSessionBean() {
    }
}
