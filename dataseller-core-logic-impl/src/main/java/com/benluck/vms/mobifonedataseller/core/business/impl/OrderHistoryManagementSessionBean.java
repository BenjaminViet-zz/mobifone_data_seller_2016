package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.core.business.OrderHistoryManagementLocalBean;
import com.benluck.vms.mobifonedataseller.session.OrderLocalBean;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/2/16
 * Time: 22:08
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "OrderHistoryManagementSessionEJB")
public class OrderHistoryManagementSessionBean implements OrderHistoryManagementLocalBean{

    @EJB
    private OrderLocalBean orderService;

    public OrderHistoryManagementSessionBean() {
    }
}
