package com.benluck.vms.mobifonedataseller.session.impl;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.domain.MBDCodeHistoryEntity;
import com.benluck.vms.mobifonedataseller.session.CodeHistoryLocalBean;

import javax.ejb.Stateless;
import javax.persistence.Query;

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
}
