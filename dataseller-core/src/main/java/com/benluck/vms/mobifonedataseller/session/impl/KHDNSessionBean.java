package com.benluck.vms.mobifonedataseller.session.impl;

import com.benluck.vms.mobifonedataseller.domain.KHDNEntity;
import com.benluck.vms.mobifonedataseller.session.KHDNLocalBean;

import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/2/16
 * Time: 07:38
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "KHDNSessionEJB")
public class KHDNSessionBean extends AbstractSessionBean<KHDNEntity, Long> implements KHDNLocalBean{
    public KHDNSessionBean() {
    }

    @Override
    public Boolean checkExistsBeforeDelete(Long khdnId) {
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append(" SELECT COUNT(o.orderId) FROM OrderEntity o WHERE o.khdn.KHDNId = :khdnId ");
        Query query = entityManager.createQuery(sqlQuery.toString()).setParameter("khdnId", khdnId);
        Integer counter = Integer.valueOf(query.getSingleResult().toString());
        if(counter.intValue() > 0){
            return true;
        }
        return false;
    }
}
