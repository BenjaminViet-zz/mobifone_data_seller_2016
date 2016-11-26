package com.benluck.vms.mobifonedataseller.session.impl;

import com.benluck.vms.mobifonedataseller.domain.UsedCardCodeEntity;
import com.benluck.vms.mobifonedataseller.session.UsedCardCodeLocalBean;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/26/16
 * Time: 11:04
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "UsedCardCodeSessionEJB")
public class UsedCardCodeSessionBean extends AbstractSessionBean<UsedCardCodeEntity, Long> implements UsedCardCodeLocalBean{

    public UsedCardCodeSessionBean() {
    }

    @Override
    public HashSet<String> findAllListCardCode() {
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append(" SELECT ucc.cardCode FROM UsedCardCodeEntity ucc ORDER BY ucc.usedCardCodeId ASC ");
        Query query = entityManager.createQuery(sqlQuery.toString());
        return new HashSet<String>(query.getResultList());
    }

    @Override
    public Boolean checkImportUsedCardCode() {
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append(" SELECT COUNT(usedCardCodeId) FROM UsedCardCodeEntity ");
        Query query = entityManager.createQuery(sqlQuery.toString());
        Integer count = Integer.valueOf(query.getSingleResult().toString());
        if(count.intValue() > 0){
            return true;
        }
        return false;
    }

    @Override
    public void deleteAll() {
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append(" DELETE FROM UsedCardCodeEntity ");
        Query query = entityManager.createQuery(sqlQuery.toString());
        query.executeUpdate();
    }
}
