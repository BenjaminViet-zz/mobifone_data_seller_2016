package com.benluck.vms.mobifonedataseller.session.impl;

import com.benluck.vms.mobifonedataseller.domain.MoneyExchangeBranchEntity;
import com.benluck.vms.mobifonedataseller.session.MoneyExchangeBranchLocalBean;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/26/14
 * Time: 3:42 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "MoneyExchangeBranchSessionEJB")
public class MoneyExchangeBranchSessionBean extends AbstractSessionBean<MoneyExchangeBranchEntity, Long> implements MoneyExchangeBranchLocalBean{
    public MoneyExchangeBranchSessionBean() {
    }

    @Override
    public MoneyExchangeBranchEntity findByDealerId(Long dealer_id) {
        StringBuilder sqlQueryClause = new StringBuilder(" from MoneyExchangeBranchEntity e where e.dealerId = :dealerId ");
        Query query = entityManager.createQuery(sqlQueryClause.toString());
        query.setParameter("dealerId", dealer_id);
        List<MoneyExchangeBranchEntity> entityList = (List<MoneyExchangeBranchEntity>)query.getResultList();
        if(entityList.size() > 0){
            return entityList.get(0);
        }
        return null;
    }
}
