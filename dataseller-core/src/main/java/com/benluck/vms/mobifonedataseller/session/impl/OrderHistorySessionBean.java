package com.benluck.vms.mobifonedataseller.session.impl;

import com.benluck.vms.mobifonedataseller.domain.OrderHistoryEntity;
import com.benluck.vms.mobifonedataseller.session.OrderHistoryLocalBean;

import javax.ejb.Stateless;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/2/16
 * Time: 22:09
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "OrderHistorySessionEJB")
public class OrderHistorySessionBean extends AbstractSessionBean<OrderHistoryEntity, Long> implements OrderHistoryLocalBean{
    public OrderHistorySessionBean() {
    }
}
