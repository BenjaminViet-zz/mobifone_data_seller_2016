package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.beanUtil.OrderDataCodeBeanUtil;
import com.benluck.vms.mobifonedataseller.core.business.OrderDataCodeManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.OrderDataCodeDTO;
import com.benluck.vms.mobifonedataseller.session.OrderDataCodeLocalBean;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/6/16
 * Time: 12:08
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "OrderDataCodeManagementSessionEJB")
public class OrderDataCodeManagementSessionBean implements OrderDataCodeManagementLocalBean{

    @EJB
    private OrderDataCodeLocalBean orderDataCodeService;

    public OrderDataCodeManagementSessionBean() {
    }

    @Override
    public List<OrderDataCodeDTO> fetchByOrderId(Long orderId) {
        return OrderDataCodeBeanUtil.entityList2DTOList(this.orderDataCodeService.findProperty("order.orderId", orderId));
    }

    @Override
    public List<String> findListCardCodeByOrder(Long orderId) {
        return (List<String>) this.orderDataCodeService.findListCardCodeByOrder(orderId);
    }
}
