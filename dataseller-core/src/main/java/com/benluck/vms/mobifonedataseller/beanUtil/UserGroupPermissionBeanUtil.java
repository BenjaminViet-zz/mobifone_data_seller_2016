package com.benluck.vms.mobifonedataseller.beanUtil;

import com.benluck.vms.mobifonedataseller.core.dto.UserGroupDTO;
import com.benluck.vms.mobifonedataseller.core.dto.UserGroupPermissionDTO;
import com.benluck.vms.mobifonedataseller.domain.UserGroupPermissionEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 5/22/17
 * Time: 21:53
 * To change this template use File | Settings | File Templates.
 */
public class UserGroupPermissionBeanUtil {
    public static UserGroupPermissionDTO entity2DTO(UserGroupPermissionEntity entity){
        if (entity != null){
            UserGroupPermissionDTO dto = new UserGroupPermissionDTO();
            dto.setUserGroupPermissionId(entity.getUserGroupPermissionId());

            if (entity.getUserGroup() != null){
                UserGroupDTO userGroupDTO = new UserGroupDTO();
                userGroupDTO.setUserGroupId(entity.getUserGroup().getUserGroupId());
                userGroupDTO.setCode(entity.getUserGroup().getCode());
                userGroupDTO.setDescription(entity.getUserGroup().getDescription());
                dto.setUserGroup(userGroupDTO);
            }

            dto.setPermission(PermissionBeanUtil.entity2DTO(entity.getPermission()));
            return dto;
        }
        return null;
    }

    public static List<UserGroupPermissionDTO> entityList2DTOList(List<UserGroupPermissionEntity> entityList){
        if (entityList != null){
            List<UserGroupPermissionDTO> dtoList = new ArrayList<UserGroupPermissionDTO>();
            for (UserGroupPermissionEntity entity : entityList){
                dtoList.add(entity2DTO(entity));
            }
            return dtoList;
        }
        return null;
    }
}
