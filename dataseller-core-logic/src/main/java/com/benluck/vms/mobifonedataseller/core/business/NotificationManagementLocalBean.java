package com.benluck.vms.mobifonedataseller.core.business;

import com.benluck.vms.mobifonedataseller.core.dto.NotificationDTO;

import javax.ejb.DuplicateKeyException;
import javax.ejb.Local;
import javax.ejb.ObjectNotFoundException;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/12/16
 * Time: 15:57
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface NotificationManagementLocalBean {

    Object[] searchByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer firstItem, Integer maxPageItems);

    List<NotificationDTO> fetchNotificationNewestList(Long userId);

    void addItem(NotificationDTO pojo) throws DuplicateKeyException;

    void updateIsRead(List<Long> notificationIds);
}
