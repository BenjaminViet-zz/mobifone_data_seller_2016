package com.benluck.vms.mobifonedataseller.session.impl;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.domain.OrderDataCodeEntity;
import com.benluck.vms.mobifonedataseller.session.OrderDataCodeLocalBean;

import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/6/16
 * Time: 12:10
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "OrderDataCodeSessionEJB")
public class OrderDataCodeSessionBean extends AbstractSessionBean<OrderDataCodeEntity, Long> implements OrderDataCodeLocalBean{
    public OrderDataCodeSessionBean() {
    }

    @Override
    public Integer countTotal() {
        StringBuilder sqlCount = new StringBuilder();
        sqlCount.append(" SELECT MAX(orderDataCodeId) FROM MOBI_DATA_ORDER_DATA_CODE odc ");
        Query query = entityManager.createNativeQuery(sqlCount.toString());
        Object maxRecords = query.getSingleResult();
        if(maxRecords != null){
            return Integer.valueOf(maxRecords.toString());
        }
        return Constants.ORDER_DATA_CODE_SERIAL_OFFSET;
    }
}
