package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.beanUtil.OrderDataCodeBeanUtil;
import com.benluck.vms.mobifonedataseller.core.business.OrderDataCodeManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.OrderDataCodeDTO;
import com.benluck.vms.mobifonedataseller.domain.OrderDataCodeEntity;
import com.benluck.vms.mobifonedataseller.session.OrderDataCodeLocalBean;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

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

    @Override
    public HashSet<String> findCardCodeImported4OldOrder() {
        return this.orderDataCodeService.findCardCodeImported4OldOrder();
    }

    @Override
    public Object[] findByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer firstItem, Integer reportMaxPageItems) {
        Object[] resultObject = this.orderDataCodeService.searchByProperties(properties, sortExpression, sortDirection, firstItem, reportMaxPageItems);
        List<OrderDataCodeEntity> entityList = (List<OrderDataCodeEntity>)resultObject[1];

        if(entityList.size() > 0 ){
            resultObject[1] = OrderDataCodeBeanUtil.entityList2DTOList(entityList);
        }
        return resultObject;
    }
}
