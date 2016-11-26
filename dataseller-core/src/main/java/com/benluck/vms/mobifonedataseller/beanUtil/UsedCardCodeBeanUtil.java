package com.benluck.vms.mobifonedataseller.beanUtil;

import com.benluck.vms.mobifonedataseller.core.dto.UsedCardCodeDTO;
import com.benluck.vms.mobifonedataseller.domain.UsedCardCodeEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/26/16
 * Time: 09:09
 * To change this template use File | Settings | File Templates.
 */
public class UsedCardCodeBeanUtil {
    public static UsedCardCodeDTO entity2DTO(UsedCardCodeEntity entity){
        UsedCardCodeDTO dto = new UsedCardCodeDTO();
        dto.setUsedCardCodeId(entity.getUsedCardCodeId());
        dto.setCardCode(entity.getCardCode());
        return dto;
    }

    public static List<UsedCardCodeDTO> entityList2DTOList(List<UsedCardCodeEntity> entityList){
        List<UsedCardCodeDTO> dtoList = new ArrayList<UsedCardCodeDTO>();
        for (UsedCardCodeEntity entity : entityList){
            dtoList.add(entity2DTO(entity));
        }
        return dtoList;
    }
}
