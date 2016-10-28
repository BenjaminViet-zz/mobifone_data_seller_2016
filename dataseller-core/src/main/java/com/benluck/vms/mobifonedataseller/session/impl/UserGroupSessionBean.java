package com.benluck.vms.mobifonedataseller.session.impl;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.domain.VmsUserGroupEntity;
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
public class UserGroupSessionBean extends AbstractSessionBean<VmsUserGroupEntity, Long> implements UserGroupLocalBean {
    public UserGroupSessionBean() {
    }

    @Override
    public VmsUserGroupEntity findUserGroupByCode(String code) {
        StringBuilder sqlQueryClause = new StringBuilder("from VmsUserGroupEntity where code = :code");
        Query query = entityManager.createQuery(sqlQueryClause.toString());
        query.setParameter("code", code);
        List<VmsUserGroupEntity> entityList = (List<VmsUserGroupEntity>)query.getResultList();
        if(entityList.size() > 0){
            return entityList.get(0);
        }
        return null;
    }

    @Override
    public List<VmsUserGroupEntity> findAll4Access() {
        StringBuilder sqlQueryClause = new StringBuilder();
        List<String> codes = new ArrayList<String>();
        codes.add(Constants.USERGROUP_NV);
        codes.add(Constants.USERGROUP_TD);
        codes.add(Constants.USERGROUP_CN);
        codes.add(Constants.USERGROUP_ADMIN);
        codes.add(Constants.USERGROUP_BAOCAO);
        sqlQueryClause.append(" from VmsUserGroupEntity e where e.code in (:codes) order by e.code ");
        Query query = entityManager.createQuery(sqlQueryClause.toString());
        query.setParameter("codes", codes);
        return  (List<VmsUserGroupEntity>)query.getResultList();
    }
}
