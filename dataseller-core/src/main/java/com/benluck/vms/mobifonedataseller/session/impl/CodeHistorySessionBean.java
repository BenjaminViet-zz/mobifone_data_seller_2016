package com.benluck.vms.mobifonedataseller.session.impl;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.domain.MBDCodeHistoryEntity;
import com.benluck.vms.mobifonedataseller.session.CodeHistoryLocalBean;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/11/16
 * Time: 19:46
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "CodeHistorySessionEJB")
public class CodeHistorySessionBean extends AbstractSessionBean<MBDCodeHistoryEntity, Long> implements CodeHistoryLocalBean{

    public CodeHistorySessionBean() {
    }

    @Override
    public Double calculateTotalPaidPackageValue(String isdn) {
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append(" SELECT COUNT(trans_ID) FROM MOBI_DATA_CODE_HISTORY WHERE isdn = :isdn ");
        Query query = entityManager.createNativeQuery(sqlQuery.toString()).setParameter("isdn", isdn);
        Integer count = Integer.valueOf(query.getSingleResult() != null ? query.getSingleResult().toString() : "0");
        Double totalPaidPackageValue = count * Constants.PACKAGE_KHDN_DATA_VALUE;

        StringBuilder sqlQueryTotalOrderValue = new StringBuilder();
        sqlQueryTotalOrderValue.append(" SELECT NVL(SUM(v.total), 0) as total FROM (select (o.quantity * o.unitPrice) as total FROM MOBI_DATA_ORDER o WHERE o.KHDNID = (select k.KHDNID FROM MOBI_DATA_KHDN k WHERE k.STB_VAS = :isdn)) v ");
        Query queryTotalOrderValue = entityManager.createNativeQuery(sqlQueryTotalOrderValue.toString()).setParameter("isdn", isdn);
        Double totalUsedValue = Double.valueOf(queryTotalOrderValue.getSingleResult().toString());
        return totalPaidPackageValue - totalUsedValue;
    }

    @Override
    public Object[] searchPaymentHistoryByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer firstItem, Integer reportMaxPageItems) {
        StringBuilder sqlMainQuery = new StringBuilder();
        sqlMainQuery.append(" FROM MOBI_DATA_CODE_HISTORY WHERE 1 = 1 ");
        if(properties.get("tin") != null){
            sqlMainQuery.append(" AND tin LIKE :tin ");
        }
        if(properties.get("isdn") != null){
            sqlMainQuery.append(" AND isdn LIKE :isdn ");
        }
        if(properties.get("regDateTimeFrom") != null){
            sqlMainQuery.append(" AND reg_dateTime >= to_date(substr(:regDateTimeFrom,1,10), ' ").append(Constants.DB_DATE_FORMAT).append("') ");
        }
        if(properties.get("regDateTimeTo") != null){
            sqlMainQuery.append(" AND reg_dateTime <= to_date(substr(:regDateTimeTo,1,10), ' ").append(Constants.DB_DATE_FORMAT).append("') + 1 ");
        }
        if(properties.get("staDateTimeFrom") != null){
            sqlMainQuery.append(" AND sta_dateTime >= to_date(substr(:staDateTimeFrom,1,10), ' ").append(Constants.DB_DATE_FORMAT).append("') ");
        }
        if(properties.get("staDateTimeTo") != null){
            sqlMainQuery.append(" AND sta_dateTime <= to_date(substr(:staDateTimeTo,1,10), ' ").append(Constants.DB_DATE_FORMAT).append("') + 1 ");
        }

        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append(" SELECT isdn, name as tenKhachHang, tin, reg_dateTime, sta_dateTime ")
                .append(sqlMainQuery.toString())
                .append(" ORDER BY sta_dateTime ASC ");


        Query query = entityManager.createNativeQuery(sqlQuery.toString());
        if(properties.get("tin") != null){
            query.setParameter("tin", "%" + properties.get("tin").toString() + "%");
        }
        if(properties.get("isdn") != null){
            query.setParameter("isdn", "%" + properties.get("isdn").toString() + "%");
        }
        if(properties.get("regDateTimeFrom") != null){
            query.setParameter("regDateTimeFrom", properties.get("regDateTimeFrom").toString());
        }
        if(properties.get("regDateTimeTo") != null){
            query.setParameter("regDateTimeTo", properties.get("regDateTimeTo").toString());
        }
        if(properties.get("staDateTimeFrom") != null){
            query.setParameter("staDateTimeFrom", properties.get("staDateTimeFrom").toString());
        }
        if(properties.get("staDateTimeTo") != null){
            query.setParameter("staDateTimeTo", properties.get("staDateTimeTo").toString());
        }

        query.setFirstResult(firstItem);
        query.setMaxResults(reportMaxPageItems);

        List resultSet = query.getResultList();

        StringBuilder sqlCountQuery = new StringBuilder();
        sqlCountQuery.append(" SELECT COUNT(trans_Id) ").append(sqlMainQuery.toString());
        Query countQuery = entityManager.createNativeQuery(sqlCountQuery.toString());
        if(properties.get("tin") != null){
            countQuery.setParameter("tin", "%" + properties.get("tin").toString() + "%");
        }
        if(properties.get("isdn") != null){
            countQuery.setParameter("isdn", "%" + properties.get("isdn").toString() + "%");
        }
        if(properties.get("regDateTimeFrom") != null){
            countQuery.setParameter("regDateTimeFrom", properties.get("regDateTimeFrom").toString());
        }
        if(properties.get("regDateTimeTo") != null){
            countQuery.setParameter("regDateTimeTo", properties.get("regDateTimeTo").toString());
        }
        if(properties.get("staDateTimeFrom") != null){
            countQuery.setParameter("staDateTimeFrom", properties.get("staDateTimeFrom").toString());
        }
        if(properties.get("staDateTimeTo") != null){
            countQuery.setParameter("staDateTimeTo", properties.get("staDateTimeTo").toString());
        }

        Integer count = Integer.valueOf(countQuery.getSingleResult().toString());

        return new Object[]{count, resultSet};
    }
}
