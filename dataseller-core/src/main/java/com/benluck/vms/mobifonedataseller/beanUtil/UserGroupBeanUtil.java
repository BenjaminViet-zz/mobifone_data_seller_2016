package com.benluck.vms.mobifonedataseller.beanUtil;

import com.benluck.vms.mobifonedataseller.core.dto.UserGroupDTO;
import com.benluck.vms.mobifonedataseller.domain.UserGroupEntity;

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
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        return dto;
    }
}
