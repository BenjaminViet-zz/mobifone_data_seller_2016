package com.benluck.vms.mobifonedataseller.session.impl;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.core.dto.UserDTO;
import com.benluck.vms.mobifonedataseller.domain.UserEntity;
import com.benluck.vms.mobifonedataseller.session.UserLocalBean;
import org.apache.commons.lang.StringUtils;

import javax.ejb.ObjectNotFoundException;
import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: benluck
 * Date: 10/4/14
 * Time: 3:28 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "UserSessionEJB")
public class UserSessionBean extends AbstractSessionBean<UserEntity, Long> implements UserLocalBean{
    public UserSessionBean() {
    }

    @Override
    public UserEntity findByUserName(String userName) throws ObjectNotFoundException {
        StringBuilder sqlQuery = new StringBuilder("FROM UserEntity u WHERE lower(u.userName) = lower(:userName)");
        Query query = entityManager.createQuery(sqlQuery.toString());
        query.setParameter("userName", userName);
        try{
            return (UserEntity)query.getSingleResult();
        }catch (Exception e) {
            throw new ObjectNotFoundException("NOT FOUND UserName: " + userName);
        }
    }

    @Override
    public List<UserEntity> findListByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer offset, Integer limitItems) {
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append(" FROM UserEntity u ")
                .append(" WHERE 1 = 1 ");

        if(properties.get("userName") != null){
            sqlQuery.append(" u.userName LIKE :userName ");
        }
        if(properties.get("userGroupId") != null){
            sqlQuery.append(" u.userGroup.userGroupId = :userGroupId ");
        }

        Query query = entityManager.createQuery(sqlQuery.toString());
        if(properties.get("userName") != null){
            query.setParameter("userName", "%" + properties.get("userName").toString().toLowerCase() + "%");
        }
        if(properties.get("userGroupId") != null){
            query.setParameter("userGroupId", Long.valueOf(properties.get("userGroupId").toString()));
        }

        List<UserEntity> entityList = (List<UserEntity>) query.getResultList();

        StringBuilder sqlCount = new StringBuilder();
        sqlCount.append(" SELECT COUNT(u.userId) ").append(sqlQuery.toString());
        Query queryCount = entityManager.createQuery(sqlCount.toString());
        if(properties.get("userName") != null){
            queryCount.setParameter("userName", "%" + properties.get("userName").toString().toLowerCase() + "%");
        }
        if(properties.get("userGroupId") != null){
            queryCount.setParameter("userGroupId", Long.valueOf(properties.get("userGroupId").toString()));
        }

        Integer count = Integer.valueOf(queryCount.getSingleResult().toString());
        return null;
    }

    @Override
    public UserEntity loadUserByUserNameAndPassword(String username, String password) throws ObjectNotFoundException{
        List<UserEntity> entityList = (List<UserEntity>)entityManager.createQuery("FROM UserEntity WHERE userName = :userName AND password = :password AND LDAP = 0")
                .setParameter("userName", username)
                .setParameter("password", password)
                .getResultList();
        if (entityList != null && entityList.size() > 0){
            return entityList.get(0);
        }
        throw new ObjectNotFoundException("The userName: " + username + ", password: " + password + " does not match to any account in the system.");
    }
}
