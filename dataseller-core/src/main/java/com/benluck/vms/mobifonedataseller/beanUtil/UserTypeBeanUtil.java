package com.benluck.vms.mobifonedataseller.beanUtil;

import com.benluck.vms.mobifonedataseller.core.dto.UserTypeDTO;
import com.benluck.vms.mobifonedataseller.domain.UserTypeEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 5/22/17
 * Time: 22:12
 * To change this template use File | Settings | File Templates.
 */
public class UserTypeBeanUtil {
    public static UserTypeDTO entity2DTO(UserTypeEntity entity){
        if (entity != null){
            UserTypeDTO dto = new UserTypeDTO();
            dto.setUserTypeId(entity.getUserTypeId());
            dto.setCode(entity.getCode());
            dto.setDescription(entity.getDescription());
            return dto;
        }
        return null;
    }

    public static List<UserTypeDTO> entityList2DTOList(List<UserTypeEntity> entityList){
        if (entityList != null){
            List<UserTypeDTO> dtoList = new ArrayList<UserTypeDTO>();
            for (UserTypeEntity entity : entityList){
                dtoList.add(entity2DTO(entity));
            }
            return dtoList;
        }
        return null;
    }
}
