package com.benluck.vms.mobifonedataseller.session.impl;

import com.benluck.vms.mobifonedataseller.domain.MBDCostEntity;
import com.benluck.vms.mobifonedataseller.session.MBDCostLocalBean;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/9/16
 * Time: 22:03
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "MBDCostSessionEJB")
public class MBDCostSessionBean extends AbstractSessionBean<MBDCostEntity, Long> implements MBDCostLocalBean{

    public MBDCostSessionBean() {
    }

    @Override
    public Object[] search4GeneralExpenseReport(Map<String, Object> properties, String sortExpression, String sortDirection, Integer firstItem, Integer maxPageItems) {
        StringBuilder sqlSelectQuery = new StringBuilder();
        sqlSelectQuery.append(" SELECT shop_Code, shop_Name ")
                    .append("           , development_Phase1, development_Amount1 ")
                    .append("           , development_Phase2, development_Amount2 ")
                    .append("           , development_Phase3, development_Amount3 ")
                    .append(" FROM MOBI_DATA_COST WHERE Cust_ID = :custID ORDER BY shop_Code, shop_Name ASC ");

        Query query = entityManager.createNativeQuery(sqlSelectQuery.toString());
        query.setParameter("custID", Long.valueOf(properties.get("custID").toString()));
        query.setFirstResult(firstItem);
        query.setMaxResults(maxPageItems);
        List resultSet = query.getResultList();

        StringBuilder sqlCountQuery = new StringBuilder();
        sqlCountQuery.append(" SELECT COUNT(CostID) FROM MOBI_DATA_COST WHERE Cust_ID = :custID ");
        Query countQuery = entityManager.createNativeQuery(sqlCountQuery.toString()).setParameter("custID", Long.valueOf(properties.get("custID").toString()));
        Integer count = Integer.valueOf(countQuery.getSingleResult().toString());

        return new Object[]{count, resultSet};
    }
}
