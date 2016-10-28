package com.benluck.vms.mobifonedataseller.session.impl;

import com.benluck.vms.mobifonedataseller.domain.GiftEntity;
import com.benluck.vms.mobifonedataseller.session.GiftLocalBean;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: benluck
 * Date: 10/4/14
 * Time: 3:29 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "GiftSessionEJB")
public class GiftSessionBean extends AbstractSessionBean<GiftEntity, Long> implements GiftLocalBean {
    public GiftSessionBean() {
    }

    @Override
    public GiftEntity findByCode(String code) {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append("from GiftEntity g where g.code = :code");
        Query query = entityManager.createQuery(sqlQueryClause.toString());
        query.setParameter("code", code);
        List<GiftEntity> entitylist = (List<GiftEntity>)query.getResultList();
        if(entitylist.size() > 0){
            return entitylist.get(0);
        }
        return null;
    }
}
