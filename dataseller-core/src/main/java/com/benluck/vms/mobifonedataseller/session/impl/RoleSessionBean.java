package com.benluck.vms.mobifonedataseller.session.impl;

import com.benluck.vms.mobifonedataseller.domain.RoleEntity;
import com.benluck.vms.mobifonedataseller.session.RoleLocalBean;

import javax.ejb.Stateless;

/**
 * Created with IntelliJ IDEA.
 * User: benluck
 * Date: 10/4/14
 * Time: 3:30 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "RoleSessionEJB")
public class RoleSessionBean extends AbstractSessionBean<RoleEntity, Long> implements RoleLocalBean {
    public RoleSessionBean() {
    }
}
