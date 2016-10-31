package com.benluck.vms.mobifonedataseller.beanUtil;

import com.benluck.vms.mobifonedataseller.core.dto.UserGroupDTO;
import com.benluck.vms.mobifonedataseller.domain.UserGroupEntity;

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
        UserGroupDTO dto = new UserGroupDTO();
        dto.setUserGroupId(entity.getUserGroupId());
        dto.setCode(entity.getCode());
        dto.setDescription(entity.getDescription());
        return dto;
    }

    public static List<UserGroupDTO> entityList2DTOList(List<UserGroupEntity> entityList){
        List<UserGroupDTO> dtoList = new ArrayList<UserGroupDTO>();
        for (UserGroupEntity entity : entityList){
            dtoList.add(UserGroupBeanUtil.entity2DTO(entity));
        }
        return dtoList;
    }
}
