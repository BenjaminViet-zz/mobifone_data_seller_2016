package com.benluck.vms.mobifonedataseller.beanUtil;

import com.benluck.vms.mobifonedataseller.core.dto.UserGroupDTO;
import com.benluck.vms.mobifonedataseller.core.dto.UserGroupPermissionDTO;
import com.benluck.vms.mobifonedataseller.domain.UserGroupEntity;
import com.benluck.vms.mobifonedataseller.domain.UserGroupPermissionEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 10/29/16
 * Time: 10:37
 * To change this template use File | Settings | File Templates.
 */
public class UserGroupBeanUtil {
    public static UserGroupDTO entity2DTO(UserGroupEntity entity){
        if (entity != null){
            UserGroupDTO dto = new UserGroupDTO();
            dto.setUserGroupId(entity.getUserGroupId());
            dto.setCode(entity.getCode());
            dto.setDescription(entity.getDescription());
            dto.setUserGroupPermissionList(UserGroupPermissionBeanUtil.entityList2DTOList(entity.getUserGroupPermissionList()));
            return dto;
        }
        return null;
    }

    public static List<UserGroupDTO> entityList2DTOList(List<UserGroupEntity> entityList){
        if (entityList != null){
            List<UserGroupDTO> dtoList = new ArrayList<UserGroupDTO>();
            for (UserGroupEntity entity : entityList){
                dtoList.add(UserGroupBeanUtil.entity2DTO(entity));
            }
            return dtoList;
        }
        return null;
    }
}
