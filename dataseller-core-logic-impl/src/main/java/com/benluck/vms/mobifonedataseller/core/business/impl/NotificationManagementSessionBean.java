package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.beanUtil.NotificationBeanUtil;
import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.core.business.NotificationManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.NotificationDTO;
import com.benluck.vms.mobifonedataseller.domain.NotificationEntity;
import com.benluck.vms.mobifonedataseller.domain.UserEntity;
import com.benluck.vms.mobifonedataseller.session.NotificationLocalBean;

import javax.ejb.DuplicateKeyException;
import javax.ejb.EJB;
import javax.ejb.ObjectNotFoundException;
import javax.ejb.Stateless;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/12/16
 * Time: 15:58
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "NotificationManagementSessionEJB")
public class NotificationManagementSessionBean implements NotificationManagementLocalBean{

    @EJB
    private NotificationLocalBean notificationService;

    public NotificationManagementSessionBean() {
    }

    @Override
    public Object[] searchByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer firstItem, Integer maxPageItems) {
        Object[] resultObject = this.notificationService.searchByProperties(properties, sortExpression, sortDirection, firstItem, maxPageItems);
        List<NotificationEntity> entityList = (List<NotificationEntity>) resultObject[1];
        if(entityList.size() > 0){
            resultObject[1] = NotificationBeanUtil.entityList2DTOList(entityList);
        }
        return resultObject;
    }

    @Override
    public List<NotificationDTO> fetchNotificationNewestList(Long userId) {
        return NotificationBeanUtil.entityList2DTOList(this.notificationService.fetchNotificationNewestList(userId));
    }

    @Override
    public void addItem(NotificationDTO pojo) throws DuplicateKeyException {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(pojo.getUser().getUserId());

        NotificationEntity entity = new NotificationEntity();
        entity.setUser(userEntity);
        entity.setRead(Constants.NOTIFICATION_NOT_YET_READ);
        entity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        entity.setMessageType(pojo.getMessageType());
        entity.setMessage(pojo.getMessage());
        this.notificationService.save(entity);
    }

    @Override
    public void updateIsRead(Long notificationId) throws ObjectNotFoundException, DuplicateKeyException {
        NotificationEntity dbItem = this.notificationService.findById(notificationId);
        dbItem.setRead(Constants.NOTIFICATION_READ_ALREADY);
        this.notificationService.update(dbItem);
    }
}
