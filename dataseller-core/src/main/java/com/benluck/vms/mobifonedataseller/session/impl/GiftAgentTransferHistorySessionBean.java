package com.benluck.vms.mobifonedataseller.session.impl;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.domain.GiftAgentTransferHistoryEntity;
import com.benluck.vms.mobifonedataseller.session.GiftAgentTransferHistoryLocalBean;
import org.apache.commons.lang.StringUtils;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 11/5/14
 * Time: 5:40 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "GiftAgentTransferHistorySessionEJB")
public class GiftAgentTransferHistorySessionBean extends AbstractSessionBean<GiftAgentTransferHistoryEntity, Long> implements GiftAgentTransferHistoryLocalBean{
    public GiftAgentTransferHistorySessionBean() {
    }

    @Override
    public Object[] search(Long departmentId, Timestamp fromDateTime, Timestamp toDateTime, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append("  from vms_gift_agent_transfer_his h ")
                .append("   inner join vms_user u on u.userId = h.nguoiChuyenId ")
                .append("   where exists (select 1 from vms_stock_agent sa where sa.stockAgentId = h.stockAgentId and sa.agentId = :departmentId ) ");
        if(fromDateTime != null){
            mainQuery.append("      and to_timestamp(substr(h.createdDate, 1, 10)) >= to_timestamp(substr(:fromDateTime, 1, 10), 'YYYY-MM-DD') ");
        }
        if(toDateTime != null){
            mainQuery.append("      and to_timestamp(substr(h.createdDate, 1, 10)) <= to_timestamp(substr(:toDateTime, 1, 10), 'YYYY-MM-DD') ");
        }

        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select h.giftAgentTransferHisId, h.createdDate, h.quantity, u.displayName ")
                    .append(mainQuery);
        if(StringUtils.isNotBlank(sortExpression)){
            sqlQueryClause.append(" order by h.").append(sortExpression);
        }else{
            sqlQueryClause.append(" order by h.createdDate ");
        }
        if(StringUtils.isNotBlank(sortDirection)){
            sqlQueryClause.append(sortDirection.equalsIgnoreCase(Constants.SORT_ASC) ? " asc " : " desc ");
        }else{
            sqlQueryClause.append(" asc ");
        }
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("departmentId", departmentId);
        if(fromDateTime != null){
            query.setParameter("fromDateTime", fromDateTime.toString());
        }
        if(toDateTime != null){
            query.setParameter("toDateTime", toDateTime.toString());
        }
        query.setFirstResult(firstItem);
        query.setMaxResults(maxPageItems);
        List resultSet = query.getResultList();

        StringBuilder sqlCountClause = new StringBuilder(" select count(h.giftAgentTransferHisId) ").append(mainQuery);
        Query countQuery = entityManager.createNativeQuery(sqlCountClause.toString());
        countQuery.setParameter("departmentId", departmentId);
        if(fromDateTime != null){
            countQuery.setParameter("fromDateTime", fromDateTime.toString());
        }
        if(toDateTime != null){
            countQuery.setParameter("toDateTime", toDateTime.toString());
        }
        Long count = Long.valueOf(countQuery.getSingleResult().toString());
        return new Object[]{count, resultSet};
    }
}
