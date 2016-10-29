package com.benluck.vms.mobifonedataseller.session.impl;

import com.benluck.vms.mobifonedataseller.domain.UserRoleEntity;
import com.benluck.vms.mobifonedataseller.session.UserRoleLocalBean;

import javax.ejb.Stateless;

/**
 * Created with IntelliJ IDEA.
 * User: benluck
 * Date: 10/4/14
 * Time: 3:28 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "UserRoleSessionEJB")
public class UserRoleSessionBean extends AbstractSessionBean<UserRoleEntity, Long> implements UserRoleLocalBean{
    public UserRoleSessionBean() {
    }
}
