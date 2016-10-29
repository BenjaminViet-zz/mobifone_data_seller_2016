package com.benluck.vms.mobifonedataseller.session.impl;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.domain.UserGroupEntity;
import com.benluck.vms.mobifonedataseller.session.UserGroupLocalBean;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: benluck
 * Date: 10/4/14
 * Time: 3:28 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "UserGroupSessionEJB")
public class UserGroupSessionBean extends AbstractSessionBean<UserGroupEntity, Long> implements UserGroupLocalBean {
    public UserGroupSessionBean() {
    }

    @Override
    public UserGroupEntity findUserGroupByCode(String code) {
        StringBuilder sqlQueryClause = new StringBuilder("FROM UserGroupEntity where code = :code");
        Query query = entityManager.createQuery(sqlQueryClause.toString());
        query.setParameter("code", code);
        List<UserGroupEntity> entityList = (List<UserGroupEntity>)query.getResultList();
        if(entityList.size() > 0){
            return entityList.get(0);
        }
        return null;
    }

    @Override
    public List<UserGroupEntity> findAll4Access() {
        StringBuilder sqlQueryClause = new StringBuilder();
        List<String> codes = new ArrayList<String>();
        codes.add(Constants.USERGROUP_ADMIN);
        codes.add(Constants.USERGROUP_KHDN);
        sqlQueryClause.append(" from UserGroupEntity e where e.code in (:codes) order by e.code ");
        Query query = entityManager.createQuery(sqlQueryClause.toString());
        query.setParameter("codes", codes);
        return  (List<UserGroupEntity>)query.getResultList();
    }
}
