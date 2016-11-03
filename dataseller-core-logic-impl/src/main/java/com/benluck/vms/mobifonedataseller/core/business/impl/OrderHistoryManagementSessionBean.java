package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.beanUtil.OrderHistoryBeanUtil;
import com.benluck.vms.mobifonedataseller.core.business.OrderHistoryManagementLocalBean;
import com.benluck.vms.mobifonedataseller.domain.OrderHistoryEntity;
import com.benluck.vms.mobifonedataseller.session.OrderHistoryLocalBean;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;
import java.util.Map;

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
    private OrderHistoryLocalBean orderHistoryService;

    public OrderHistoryManagementSessionBean() {
    }

    @Override
    public Object[] searchByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer offset, Integer limitItems) {
        Object[] resultObject = this.orderHistoryService.searchByProperties(properties, sortExpression, sortDirection, offset, limitItems);
        resultObject[1] = OrderHistoryBeanUtil.entityList2DTOList((List<OrderHistoryEntity>)resultObject[1]);
        return resultObject;
    }
}
