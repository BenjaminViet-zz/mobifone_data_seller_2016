package com.benluck.vms.mobifonedataseller.session.impl;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.domain.OrderDataCodeEntity;
import com.benluck.vms.mobifonedataseller.session.OrderDataCodeLocalBean;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.List;

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
    public Integer countTotal(Integer year, String prefixCardCode) {
        StringBuilder sqlCount = new StringBuilder();
        sqlCount.append(" SELECT COUNT(orderDataCodeId) ")
                .append(" FROM MOBI_DATA_ORDER_DATA_CODE odc ")
                .append(" WHERE EXISTS (SELECT 1 FROM MOBI_DATA_ORDER o ")
                .append("              WHERE o.orderId = odc.orderId ")
                .append("                  AND EXISTS (SELECT 1 FROM MOBI_DATA_PACKAGE_DATA p ")
                .append("                                WHERE o.packageDataId = p.packageDataId ")
                .append("                                    AND ((p.custom_Prefix_Unit_Price IS NULL AND p.value = :value) ")
                .append("                                        OR ")
                .append("                                        (p.custom_Prefix_Unit_Price IS NOT NULL AND p.custom_Prefix_Unit_Price = :customPrefixUnitPrice)) ")
                .append("                                    AND EXISTS (SELECT 1 FROM MOBI_DATA_PACKAGEDATA_CODE_GEN pdcg ")
                .append("                                                WHERE pdcg.packageDataId = p.packageDataId ")
                .append("                                                    AND pdcg.year = :year))) ");
        Query query = entityManager.createNativeQuery(sqlCount.toString());
        query.setParameter("year", year);
        query.setParameter("value", Double.valueOf(prefixCardCode) * 1000);
        query.setParameter("customPrefixUnitPrice", prefixCardCode);
        Object maxRecords = query.getSingleResult();
        if(maxRecords != null){
            return Integer.valueOf(maxRecords.toString());
        }
        return Constants.ORDER_DATA_CODE_SERIAL_OFFSET;
    }

    @Override
    public List<String> findListCardCodeByOrder(Long orderId) {
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append(" SELECT dataCode FROM MOBI_DATA_ORDER_DATA_CODE WHERE orderId = :orderId ORDER BY serial ASC ");
        Query query = entityManager.createNativeQuery(sqlQuery.toString()).setParameter("orderId", orderId);
        return query.getResultList();
    }
}
