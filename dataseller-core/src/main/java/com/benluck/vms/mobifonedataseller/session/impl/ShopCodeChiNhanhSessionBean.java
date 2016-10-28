package com.benluck.vms.mobifonedataseller.session.impl;

import com.benluck.vms.mobifonedataseller.domain.ShopCodeChiNhanhEntity;
import com.benluck.vms.mobifonedataseller.session.ShopCodeChiNhanhLocalBean;

import javax.ejb.Stateless;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/18/14
 * Time: 5:39 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "ShopCodeChiNhanhSessionEJB")
public class ShopCodeChiNhanhSessionBean extends AbstractSessionBean<ShopCodeChiNhanhEntity, Long> implements ShopCodeChiNhanhLocalBean{
    public ShopCodeChiNhanhSessionBean() {
    }
}
