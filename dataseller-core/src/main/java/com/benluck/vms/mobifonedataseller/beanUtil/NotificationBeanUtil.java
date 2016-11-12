package com.benluck.vms.mobifonedataseller.beanUtil;

import com.benluck.vms.mobifonedataseller.core.dto.NotificationDTO;
import com.benluck.vms.mobifonedataseller.core.dto.UserDTO;
import com.benluck.vms.mobifonedataseller.domain.NotificationEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/12/16
 * Time: 15:53
 * To change this template use File | Settings | File Templates.
 */
public class NotificationBeanUtil {

    public static NotificationDTO entity2DTO(NotificationEntity entity){
        NotificationDTO dto = new NotificationDTO();
        dto.setNotificationId(entity.getNotificationId());
        dto.setUser(new UserDTO(entity.getUser().getUserId(), entity.getUser().getUserName(), entity.getUser().getDisplayName()));
        dto.setMessageType(entity.getMessageType());
        dto.setMessage(entity.getMessage());
        dto.setRead(entity.getRead());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public static List<NotificationDTO> entityList2DTOList(List<NotificationEntity> entityList){
        List<NotificationDTO> dtoList = new ArrayList<NotificationDTO>();
        for (NotificationEntity entity : entityList){
            dtoList.add(entity2DTO(entity));
        }
        return dtoList;
    }
}
