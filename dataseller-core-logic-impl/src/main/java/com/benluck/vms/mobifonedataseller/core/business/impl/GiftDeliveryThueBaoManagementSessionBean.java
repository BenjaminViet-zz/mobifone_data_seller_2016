package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.core.business.GiftDeliveryThueBaoHistoryManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.GiftDeliveryThueBaoManagementLocalBean;
import com.benluck.vms.mobifonedataseller.session.GiftDeliveryThueBaoLocalBean;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/17/14
 * Time: 12:57 AM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "GiftDeliveryThueBaoManagementSessionEJB")
public class GiftDeliveryThueBaoManagementSessionBean implements GiftDeliveryThueBaoManagementLocalBean{
    @EJB
    private GiftDeliveryThueBaoLocalBean giftDeliveryThueBaoLocalBean;

    public GiftDeliveryThueBaoManagementSessionBean() {
    }
}
