package com.benluck.vms.mobifonedataseller.session.impl;

import com.benluck.vms.mobifonedataseller.domain.GiftDeliveryThueBaoEntity;
import com.benluck.vms.mobifonedataseller.session.GiftDeliveryThueBaoLocalBean;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/17/14
 * Time: 12:54 AM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "GiftDeliveryThueBaoSessionEJB")
public class GiftDeliveryThueBaoSessionBean extends AbstractSessionBean<GiftDeliveryThueBaoEntity, Long> implements GiftDeliveryThueBaoLocalBean{
    public GiftDeliveryThueBaoSessionBean() {
    }

    @Override
    public List<GiftDeliveryThueBaoEntity> findByDelivererId(Long userId) {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append("from GiftDeliveryThueBaoEntity e where e.nvGiao.userId = :userId");
        Query query = entityManager.createQuery(sqlQueryClause.toString());
        query.setParameter("userId", userId);
        return (List<GiftDeliveryThueBaoEntity>)query.getResultList();
    }
}
