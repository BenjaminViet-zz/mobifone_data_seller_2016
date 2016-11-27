package com.benluck.vms.mobifonedataseller.session.impl;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.domain.NotificationEntity;
import com.benluck.vms.mobifonedataseller.session.NotificationLocalBean;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/12/16
 * Time: 15:59
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "NotificationSessionEJB")
public class NotificationSessionBean extends AbstractSessionBean<NotificationEntity, Long> implements NotificationLocalBean{

    public NotificationSessionBean() {
    }

    @Override
    public List<NotificationEntity> fetchNotificationNewestList(Long userId) {
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append(" FROM NotificationEntity WHERE user.userId = :userId AND read = :read ORDER BY createdDate DESC ");
        Query query = entityManager.createQuery(sqlQuery.toString()).setParameter("userId", userId).setParameter("read", Constants.NOTIFICATION_NOT_YET_READ);
        query.setFirstResult(0);
        query.setMaxResults(Constants.MAX_NOTIFICATION_MESSAGE_POPUP);
        return (List<NotificationEntity>)query.getResultList();
    }

    @Override
    public void updateNotificationIsRead(List<Long> notificationIds) {
        StringBuilder sqlUpdate = new StringBuilder();
        sqlUpdate.append(" UPDATE NotificationEntity SET isRead = :isRead WHERE notificationId in :notificationIds ");
        Query query = entityManager.createQuery(sqlUpdate.toString())
                .setParameter("notificationIds", notificationIds)
                .setParameter("isRead", Constants.NOTIFICATION_READ_ALREADY);
        query.executeUpdate();
    }
}
