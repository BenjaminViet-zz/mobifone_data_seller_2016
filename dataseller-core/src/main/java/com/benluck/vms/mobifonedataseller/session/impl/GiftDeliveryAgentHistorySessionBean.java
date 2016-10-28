package com.benluck.vms.mobifonedataseller.session.impl;

import com.benluck.vms.mobifonedataseller.domain.GiftDeliveryAgentHistoryEntity;
import com.benluck.vms.mobifonedataseller.session.GiftDeliveryAgentHistoryLocalBean;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/15/14
 * Time: 11:14 AM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "GiftDeliveryAgentHistorySessionEJB")
public class GiftDeliveryAgentHistorySessionBean extends AbstractSessionBean<GiftDeliveryAgentHistoryEntity, Long> implements GiftDeliveryAgentHistoryLocalBean{
    public GiftDeliveryAgentHistorySessionBean() {
    }

    @Override
    public List<GiftDeliveryAgentHistoryEntity> findBySubscriberId(Long userId) {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" from GiftDeliveryAgentHistoryEntity e where e.user.userId = :userId ");
        Query query = entityManager.createQuery(sqlQueryClause.toString());
        query.setParameter("userId", userId);
        return (List<GiftDeliveryAgentHistoryEntity>)query.getResultList();
    }
}
