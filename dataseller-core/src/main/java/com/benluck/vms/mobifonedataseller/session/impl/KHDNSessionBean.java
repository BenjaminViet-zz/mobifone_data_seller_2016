package com.benluck.vms.mobifonedataseller.session.impl;

import com.benluck.vms.mobifonedataseller.domain.KHDNEntity;
import com.benluck.vms.mobifonedataseller.session.KHDNLocalBean;

import javax.ejb.Stateless;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/2/16
 * Time: 07:38
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "KHDNSessionEJB")
public class KHDNSessionBean extends AbstractSessionBean<KHDNEntity, Long> implements KHDNLocalBean{
    public KHDNSessionBean() {
    }
}
