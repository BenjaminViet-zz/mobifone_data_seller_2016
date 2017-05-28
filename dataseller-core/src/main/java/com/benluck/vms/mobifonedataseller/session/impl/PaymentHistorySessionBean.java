package com.benluck.vms.mobifonedataseller.session.impl;

import com.benluck.vms.mobifonedataseller.domain.PaymentHistoryEntity;
import com.benluck.vms.mobifonedataseller.session.PaymentHistoryLocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 5/25/17
 * Time: 23:12
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "PaymentHistorySessionEJB")
public class PaymentHistorySessionBean extends AbstractSessionBean<PaymentHistoryEntity, Long> implements PaymentHistoryLocalBean{
    public PaymentHistorySessionBean() {
    }

    @Override
    public void deleteByPaymentId(Long paymentId) {
        entityManager.createQuery("DELETE FROM PaymentHistoryEntity WHERE payment.paymentId = :paymentId").setParameter("paymentId", paymentId).executeUpdate();
    }

    @Override
    public Object[] searchByCustomProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer firstItem, Integer reportMaxPageItems) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append(" FROM PaymentHistoryEntity WHERE 1 = 1 ");

        if (properties.get("paymentId") != null){
            mainQuery.append(" AND payment.paymentId = :paymentId ");
        }

        if (properties.get("khdnId") != null){
            mainQuery.append(" AND payment.order.khdn.KHDNId = :khdnId ");
        }

        if (properties.get("orderId") != null){
            mainQuery.append(" AND payment.order.orderId = :orderId");
        }

        mainQuery.append(" ORDER BY payment.order.orderId, createdDate ASC ");

        StringBuilder sqlQuery = new StringBuilder(mainQuery.toString());
        Query query = entityManager.createQuery(sqlQuery.toString());

        if (properties.get("paymentId") != null){
            query.setParameter("paymentId", Long.valueOf(properties.get("paymentId").toString()));
        }

        if (properties.get("khdnId") != null){
            query.setParameter("khdnId", Long.valueOf(properties.get("khdnId").toString()));
        }

        if (properties.get("orderId") != null){
            query.setParameter("orderId", Long.valueOf(properties.get("orderId").toString()));
        }

        List<PaymentHistoryEntity> entityList = (List<PaymentHistoryEntity>)query.getResultList();

        StringBuilder sqlCountQuery = new StringBuilder("SELECT COUNT(paymentHistoryId) ").append(mainQuery.toString());
        Query countQuery = entityManager.createQuery(sqlCountQuery.toString());

        if (properties.get("paymentId") != null){
            countQuery.setParameter("paymentId", Long.valueOf(properties.get("paymentId").toString()));
        }

        if (properties.get("khdnId") != null){
            countQuery.setParameter("khdnId", Long.valueOf(properties.get("khdnId").toString()));
        }

        if (properties.get("orderId") != null){
            countQuery.setParameter("orderId", Long.valueOf(properties.get("orderId").toString()));
        }

        Integer count = Integer.valueOf(countQuery.getSingleResult().toString());

        return new Object[]{count, entityList};
    }

    @Override
    public Integer countHistoryRecordLines(Long paymentHistoryId) {
        return Integer.valueOf(entityManager.createQuery("SELECT COUNT(ph.paymentHistoryId) FROM PaymentHistoryEntity ph WHERE ph.payment.paymentId = (SELECT ph1.payment.paymentId FROM PaymentHistoryEntity ph1 WHERE ph1.paymentHistoryId = :paymentHistoryId)").setParameter("paymentHistoryId", paymentHistoryId).getSingleResult().toString());
    }
}
