package com.benluck.vms.mobifonedataseller.session;

import com.benluck.vms.mobifonedataseller.domain.NotificationEntity;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/12/16
 * Time: 15:59
 * To change this template use File | Settings | File Templates.
 */
public interface NotificationLocalBean extends GenericSessionBean<NotificationEntity, Long>{

    List<NotificationEntity> fetchNotificationNewestList(Long userId);

    void updateNotificationIsRead(List<Long> notificationIds);
}
