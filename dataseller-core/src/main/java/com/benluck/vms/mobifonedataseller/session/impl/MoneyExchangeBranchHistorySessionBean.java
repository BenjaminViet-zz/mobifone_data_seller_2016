package com.benluck.vms.mobifonedataseller.session.impl;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.domain.MoneyExchangeBranchHistoryEntity;
import com.benluck.vms.mobifonedataseller.session.MoneyExchangeBranchHistoryLocalBean;
import org.apache.commons.lang.StringUtils;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/26/14
 * Time: 3:44 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "MoneyExchangeBranchHistorySessionEJB")
public class MoneyExchangeBranchHistorySessionBean extends AbstractSessionBean<MoneyExchangeBranchHistoryEntity, Long> implements MoneyExchangeBranchHistoryLocalBean{
    public MoneyExchangeBranchHistorySessionBean() {
    }

    @Override
    public Object[] searchPaymentHistoryAtAgency(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append("  from vms_money_exchange_branch_his m ")
                .append("   inner join vms_money_exchange_branch m1 on m1.moneyExchangeBranchId = m.moneyExchangeBranchId ")
                .append("   inner join retail_dealer@mkitdw_qlud2_dbl rd on rd.dealer_Id = m1.dealer_Id ")
                .append("   inner join District@mkitdw_qlud2_dbl d on d.district_Id = rd.district_Id ")
                .append("   inner join Vms_user u on u.userId = m1.exchangeUserId ")
                .append("   inner join vms_chiNhanh cn on cn.chiNhanhId = u.chiNhanhId ")
                .append("   inner join branch@mkitdw_qlud2_dbl b on b.branch_Id = rd.branch_Id ")
                .append("   where 1 = 1 ");
        if(properties.get("branchId") != null){
            mainQuery.append(" and cn.branchId = :branchId ");
        }
        if(properties.get("districtId") != null){
            mainQuery.append(" and rd.district_Id = :districtId ");
        }
        if(properties.get("dealer_code") != null){
            mainQuery.append(" and upper(rd.dealer_code) = upper(:dealer_code) ");
        }
        if(properties.get("dealer_Id") != null){
            mainQuery.append(" and rd.dealer_Id = :dealer_Id ");
        }

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select m.moneyExchangeBranchHisId, m.moneyExchangeBranchId, m.cycle, m.exchangeAmount, m.createdDate ")
                    .append("           , rd.dealer_Id, rd.dealer_code, rd.dealer_name, b.branch_Id, b.branch_code, b.branch_name ")
                    .append("           , d.district_Id, d.district_code, d.district_name ")
                    .append(mainQuery)
                    .append("   order by b.branch_name, d.district_name, rd.dealer_name, m.cycle, m.createdDate ");

        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        if(properties.get("branchId") != null){
            query.setParameter("branchId", Long.valueOf(properties.get("branchId").toString()));
        }
        if(properties.get("districtId") != null){
            query.setParameter("districtId", Long.valueOf(properties.get("districtId").toString()));
        }
        if(properties.get("dealer_code") != null){
            query.setParameter("dealer_code", properties.get("dealer_code"));
        }
        if(properties.get("dealer_Id") != null){
            query.setParameter("dealer_Id", Long.valueOf(properties.get("dealer_Id").toString()));
        }

        query.setFirstResult(firstItem);
        query.setMaxResults(maxPageItems);

        List resultSet = query.getResultList();

        StringBuilder sqlCountClause = new StringBuilder(" select count(m.moneyExchangeBranchHisId) ").append(mainQuery);
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        if(properties.get("branchId") != null){
            countQuery.setParameter("branchId", Long.valueOf(properties.get("branchId").toString()));
        }
        if(properties.get("districtId") != null){
            countQuery.setParameter("districtId", Long.valueOf(properties.get("districtId").toString()));
        }
        if(properties.get("dealer_code") != null){
            countQuery.setParameter("dealer_code", properties.get("dealer_code"));
        }
        if(properties.get("dealer_Id") != null){
            countQuery.setParameter("dealer_Id", Long.valueOf(properties.get("dealer_Id").toString()));
        }
        Long count = Long.valueOf(countQuery.getSingleResult().toString());

        return new Object[]{count, resultSet};
    }
}
