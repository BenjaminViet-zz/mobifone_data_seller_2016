package com.benluck.vms.mobifonedataseller.session.impl;

import com.benluck.vms.mobifonedataseller.domain.PermissionEntity;
import com.benluck.vms.mobifonedataseller.session.PermissionLocalBean;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 10/29/16
 * Time: 21:43
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "PermissionSessionEJB")
public class PermissionSessionBean extends AbstractSessionBean<PermissionEntity, Long> implements PermissionLocalBean{
    public PermissionSessionBean() {
    }

    @Override
    public List<PermissionEntity> findPermissionByUserId(Long userId) {
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append(" FROM PermissionEntity p ")
                .append(" WHERE EXISTS (SELECT 1 FROM UserGroupPermissionEntity ugp WHERE ugp.permission.permissionId = p.permissionId AND EXISTS (SELECT u.userId FROM UserEntity u WHERE u.userId = :userId AND u.userGroup.userGroupId = ugp.userGroup.userGroupId) ) ");
        Query query = entityManager.createQuery(sqlQuery.toString());
        query.setParameter("userId", userId);
        return (List<PermissionEntity>) query.getResultList();
    }

    @Override
    public List<PermissionEntity> findAllAndSort() {
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append(" FROM PermissionEntity p ")
                .append(" ORDER BY p.permissionGroupOrder, p.permissionGroupNumber, p.orderNo ASC ");
        return (List<PermissionEntity>)entityManager.createQuery(sqlQuery.toString()).getResultList();
    }
}
