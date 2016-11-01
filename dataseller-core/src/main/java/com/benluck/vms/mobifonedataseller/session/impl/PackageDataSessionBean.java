package com.benluck.vms.mobifonedataseller.session.impl;

import com.benluck.vms.mobifonedataseller.domain.PackageDataEntity;
import com.benluck.vms.mobifonedataseller.session.PackageDataLocalBean;

import javax.ejb.Stateless;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/1/16
 * Time: 22:17
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "PackageDataSessionEJB")
public class PackageDataSessionBean extends AbstractSessionBean<PackageDataEntity, Long> implements PackageDataLocalBean{
    public PackageDataSessionBean() {
    }
}
