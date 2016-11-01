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
    public List<UserGroupEntity> findAll4Access() {
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append(" FROM UserGroupEntity ug WHERE ug.code != :code ");
        Query query = entityManager.createQuery(sqlQuery.toString());
        query.setParameter("code", Constants.ADMIN_ROLE);
        return  (List<UserGroupEntity>)query.getResultList();
    }

    @Override
    public Boolean checkInUse(Long userGroupId) {
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append(" SELECT COUNT(ug.userGroupId) FROM UserGroupEntity ug WHERE ug.userGroupId = :userGroupId AND EXISTS (SELECT 1 FROM UserEntity u WHERE u.userGroup.userGroupId = ug.userGroupId) ");
        Query query = entityManager.createQuery(sqlQuery.toString()).setParameter("userGroupId", userGroupId);
        Integer count = Integer.valueOf(query.getSingleResult().toString());
        if (count.intValue() > 0){
            return true;
        }
        return false;
    }
}
