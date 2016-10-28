package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.core.business.ShopCodeChiNhanhManagementLocalBean;
import com.benluck.vms.mobifonedataseller.session.ShopCodeChiNhanhLocalBean;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/18/14
 * Time: 5:42 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "ShopCodeChiNhanhManagementSessionEJB")
public class ShopCodeChiNhanhManagementSessionBean implements ShopCodeChiNhanhManagementLocalBean{
    @EJB
    private ShopCodeChiNhanhLocalBean shopCodeChiNhanhLocalBean;

    public ShopCodeChiNhanhManagementSessionBean() {
    }
}
