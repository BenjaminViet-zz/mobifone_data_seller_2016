package com.benluck.vms.mobifonedataseller.session.impl;

import com.benluck.vms.mobifonedataseller.domain.UserTypeEntity;
import com.benluck.vms.mobifonedataseller.session.UserTypeLocalBean;

import javax.ejb.Stateless;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 5/22/17
 * Time: 22:08
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "UserTypeSessionEJB")
public class UserTypeSessionBean extends AbstractSessionBean<UserTypeEntity, Long> implements UserTypeLocalBean{
    public UserTypeSessionBean() {
    }
}
