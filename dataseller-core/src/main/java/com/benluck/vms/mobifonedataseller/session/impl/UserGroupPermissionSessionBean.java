package com.benluck.vms.mobifonedataseller.session.impl;

import com.benluck.vms.mobifonedataseller.domain.UserGroupPermissionEntity;
import com.benluck.vms.mobifonedataseller.session.UserGroupPermissionLocalBean;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: benluck
 * Date: 10/4/14
 * Time: 3:29 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "UserGroupPermissionSessionEJB")
public class UserGroupPermissionSessionBean extends AbstractSessionBean<UserGroupPermissionEntity, Long> implements UserGroupPermissionLocalBean {
    public UserGroupPermissionSessionBean() {
    }

    @Override
    public void deleteByUserGroupId(Long userGroupId) {
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append(" DELETE FROM UserGroupPermissionEntity WHERE userGroup.userGroupId = :userGroupId ");
        entityManager.createQuery(sqlQuery.toString()).setParameter("userGroupId", userGroupId).executeUpdate();
    }

    @Override
    public List<Long> findPermissionIsListById(Long userGroupId) {
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append(" SELECT ugp.permissionId FROM MOBI_DATA_USERGROUPPERMISSION ugp WHERE userGroupId = :userGroupId ");
        Query query = entityManager.createNativeQuery(sqlQuery.toString()).setParameter("userGroupId", userGroupId);
        List resultSet = query.getResultList();

        if (resultSet.size() > 0){
            List<Long> permissionIds = new ArrayList<Long>();
            for (int i = 0; i < resultSet.size(); i++){
                permissionIds.add(Long.valueOf(resultSet.get(i).toString()));
            }
            return permissionIds;
        }else{
            return new ArrayList<Long>();
        }
    }

    @Override
    public void deleteOutUpdatePermissionIds(Long userGroupId, List<Long> permissionIds) {
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append(" DELETE FROM UserGroupPermissionEntity WHERE userGroup.userGroupId = :userGroupId AND permission.permissionId NOT IN :permissionIds ");
        entityManager.createQuery(sqlQuery.toString())
                    .setParameter("userGroupId", userGroupId)
                    .setParameter("permissionIds", permissionIds)
                    .executeUpdate();
    }
}
