package com.benluck.vms.mobifonedataseller.beanUtil;

import com.benluck.vms.mobifonedataseller.common.security.DesEncrypterUtils;
import com.benluck.vms.mobifonedataseller.core.dto.UserDTO;
import com.benluck.vms.mobifonedataseller.core.dto.UserGroupDTO;
import com.benluck.vms.mobifonedataseller.domain.UserEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 10/29/16
 * Time: 10:34
 * To change this template use File | Settings | File Templates.
 */
public class UserBeanUtil {
    public static UserDTO entity2DTO(UserEntity entity){
        if (entity != null){
            UserDTO dto = new UserDTO();
            dto.setUserId(entity.getUserId());
            dto.setUserName(entity.getUserName());
            dto.setPassword(DesEncrypterUtils.getInstance().decrypt(entity.getPassword()));
            dto.setDisplayName(entity.getDisplayName());
            dto.setLastLogin(entity.getLastLogin());
            dto.setCreatedDate(entity.getCreatedDate());
            dto.setLastModified(entity.getLastModified());
            dto.setUserType(UserTypeBeanUtil.entity2DTO(entity.getUserType()));

            if (entity.getUserGroup() != null){
                UserGroupDTO userGroupDTO = new UserGroupDTO();
                userGroupDTO.setUserGroupId(entity.getUserGroup().getUserGroupId());
                userGroupDTO.setCode(entity.getUserGroup().getCode());
                userGroupDTO.setDescription(entity.getUserGroup().getDescription());
                dto.setUserGroup(userGroupDTO);
            }

            dto.setUserGroup(UserGroupBeanUtil.entity2DTO(entity.getUserGroup()));
            dto.setStatus(entity.getStatus());
            dto.setLDAP(entity.getLDAP());
            dto.setIsdn(entity.getIsdn());
            return dto;
        }
        return null;
    }

    public static List<UserDTO> entityList2DTOList(List<UserEntity> entityList){
        if (entityList != null){
            List<UserDTO> dtoList = new ArrayList<UserDTO>();
            for (UserEntity entity : entityList){
                dtoList.add(UserBeanUtil.entity2DTO(entity));
            }
            return dtoList;
        }
        return null;
    }
}
