package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.beanUtil.OrderBeanUtil;
import com.benluck.vms.mobifonedataseller.core.business.OrderManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.OrderDTO;
import com.benluck.vms.mobifonedataseller.domain.OrderEntity;
import com.benluck.vms.mobifonedataseller.session.OrderLocalBean;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/2/16
 * Time: 07:12
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "OrderManagementSessionEJB")
public class OrderManagementSessionBean implements OrderManagementLocalBean{

    @EJB
    private OrderLocalBean orderService;

    public OrderManagementSessionBean() {
    }

    @Override
    public Object[] searchByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer offset, Integer limitItems) {
        Object[] resultObject = this.orderService.searchByProperties(properties, sortExpression, sortDirection, offset, limitItems);
        List<OrderDTO> orderDTOList = OrderBeanUtil.entityList2DTOList((List<OrderEntity>)resultObject[1]);
        resultObject[1] = orderDTOList;
        return resultObject;
    }
}
