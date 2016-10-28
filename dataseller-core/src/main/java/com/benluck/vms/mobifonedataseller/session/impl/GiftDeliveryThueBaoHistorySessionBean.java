package com.benluck.vms.mobifonedataseller.session.impl;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.utils.CommonUtil;
import com.benluck.vms.mobifonedataseller.domain.GiftDeliveryThueBaoHistoryEntity;
import com.benluck.vms.mobifonedataseller.session.GiftDeliveryThueBaoHistoryLocalBean;
import org.apache.commons.lang.StringUtils;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/17/14
 * Time: 12:55 AM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "GiftDeliveryThueBaoHistorySessionEJB")
public class GiftDeliveryThueBaoHistorySessionBean extends AbstractSessionBean<GiftDeliveryThueBaoHistoryEntity, Long> implements GiftDeliveryThueBaoHistoryLocalBean{
    public GiftDeliveryThueBaoHistorySessionBean() {
    }

    @Override
    public Object[] report4KHCN_tdcg(String thue_bao, Timestamp fromDate, Timestamp toDate, Integer offset, Integer limit, String sortExpression, String sortDirection) {
        if(StringUtils.isNotBlank(thue_bao)){
            thue_bao = CommonUtil.removeCountryCode(thue_bao);
        }
        StringBuilder mainQueryClause = new StringBuilder();
        mainQueryClause.append(" from GiftDeliveryThueBaoHistoryEntity gh ")
                    .append("   inner join gh.giftDeliveryThueBao g where 1 = 1 ");
        if(StringUtils.isNotBlank(thue_bao)){
            mainQueryClause.append(" and g.thueBao like :thue_bao ");
        }
        if(fromDate != null){
            mainQueryClause.append(" and to_timestamp(substr(g.deliveryTime, 1, 10)) >= to_timestamp(substr(:fromDate").append(",1,10)").append(", '").append(Constants.DB_DATE_FORMAT).append("')");
        }
        if(toDate != null){
            mainQueryClause.append(" and to_timestamp(substr(g.deliveryTime, 1, 10)) <= to_timestamp(substr(:toDate").append(",1,10)").append(", '").append(Constants.DB_DATE_FORMAT).append("')");
        }
        if(StringUtils.isNotBlank(sortExpression)){
            mainQueryClause.append(" order by gh.").append(sortExpression);
        }else{
            mainQueryClause.append(" order by gh.createdTime ");
        }
        if(StringUtils.isNotBlank(sortDirection)){
            mainQueryClause.append(sortDirection.equalsIgnoreCase(Constants.SORT_ASC) ? " asc " : " desc ");
        }else{
            mainQueryClause.append(" asc ");
        }

        StringBuilder sqlQueryClause = new StringBuilder("select gh ").append(mainQueryClause);
        Query query = entityManager.createQuery(sqlQueryClause.toString());
        if(StringUtils.isNotBlank(thue_bao)){
            query.setParameter("thue_bao", "%" + thue_bao + "%");
        }
        if(fromDate != null){
            query.setParameter("fromDate", fromDate.toString());
        }
        if(toDate != null){
            query.setParameter("toDate", toDate.toString());
        }
        if(offset != null){
            query.setFirstResult(offset);
        }
        if(limit != null){
            query.setMaxResults(limit);
        }
        List<GiftDeliveryThueBaoHistoryEntity> entityList = (List<GiftDeliveryThueBaoHistoryEntity>)query.getResultList();

        StringBuilder sqlCountQuery = new StringBuilder("select count(gh) ").append(mainQueryClause);
        Query countQuery = entityManager.createQuery(sqlCountQuery.toString());
        if(StringUtils.isNotBlank(thue_bao)){
            countQuery.setParameter("thue_bao", "%" + thue_bao + "%");
        }
        if(fromDate != null){
            countQuery.setParameter("fromDate", fromDate.toString());
        }
        if(toDate != null){
            countQuery.setParameter("toDate", toDate.toString());
        }
        Long count = Long.valueOf(countQuery.getSingleResult().toString());
        return  new Object[]{count, entityList};
    }

    @Override
    public List findGiftList() {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select g.GIFT_ID, g.GIFT_CODE, g.GIFT_NAME from QSV_2015_GIFT@MKITDW_KM_QSV_DBL g order by g.GIFT_NAME ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        return  query.getResultList();
    }
}
