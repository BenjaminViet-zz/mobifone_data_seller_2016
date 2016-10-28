package com.benluck.vms.mobifonedataseller.session.impl;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.domain.UserPasswordEntity;
import com.benluck.vms.mobifonedataseller.session.UserPasswordLocalBean;

import javax.ejb.ObjectNotFoundException;
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
@Stateless(name = "UserPasswordSessionEJB")
public class UserPasswordSessionBean extends AbstractSessionBean<UserPasswordEntity, Long> implements UserPasswordLocalBean {
    public UserPasswordSessionBean() {
    }

    @Override
    public UserPasswordEntity getInfoByVerifyingCode(String verifyCode, String userGroupType) throws ObjectNotFoundException{
        StringBuilder sqlQueryClause = new StringBuilder("from UserPasswordEntity u where upper(u.password) = upper(:verifyCode) ");
        if(userGroupType.equals(Constants.USERGROUP_KPP)){
            sqlQueryClause.append(" and u.passwordType = 1 ");
        }else{
            sqlQueryClause.append(" and u.passwordType = 0 or u.passwordType is null ");
        }
        sqlQueryClause.append("  order by u.createdTime desc  ");
        Query query = entityManager.createQuery(sqlQueryClause.toString());
        query.setParameter("verifyCode", verifyCode);
        List<UserPasswordEntity> entityList = (List<UserPasswordEntity>)query.getResultList();
        if(entityList.size() > 0){
            return entityList.get(0);
        }else{
            throw new ObjectNotFoundException("Verifying code is not found for: " + verifyCode);
        }
    }

    @Override
    public UserPasswordEntity findLastestBySoPhoneNumber(String phoneNumber) {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append("from UserPasswordEntity u where u.user.userName = :soThueBao order by u.createdTime desc");
        Query query = entityManager.createQuery(sqlQueryClause.toString());
        query.setParameter("soThueBao", phoneNumber);
        List<UserPasswordEntity> entityList = (List<UserPasswordEntity>)query.getResultList();
        if(entityList.size() > 0){
            return entityList.get(0);
        }
        return null;
    }
}
