package com.benluck.vms.mobifonedataseller.beanUtil;

import com.benluck.vms.mobifonedataseller.core.dto.MBDCodeHistoryDTO;
import com.benluck.vms.mobifonedataseller.domain.MBDCodeHistoryEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/9/16
 * Time: 21:28
 * To change this template use File | Settings | File Templates.
 */
public class MBDCodeHistoryBeanUtil {
    public static MBDCodeHistoryDTO entity2DTO(MBDCodeHistoryEntity entity){
        MBDCodeHistoryDTO dto = new MBDCodeHistoryDTO();
        dto.setTransId(entity.getTransId());
        dto.setSubId(entity.getSubId());
        dto.setIsdn(entity.getIsdn());
        dto.setRegDateTime(entity.getRegDateTime());
        dto.setCustId(entity.getCustId());
        dto.setName(entity.getName());
        dto.setStaDateTime(entity.getStaDateTime());
        dto.setTin(entity.getTin());
        dto.setInsertDateTime(entity.getInsertDateTime());
        return dto;
    }

    public static List<MBDCodeHistoryDTO> entityList2DTOList(List<MBDCodeHistoryEntity> entityList){
        List<MBDCodeHistoryDTO> dtoList = new ArrayList<MBDCodeHistoryDTO>();
        for (MBDCodeHistoryEntity entity : entityList){
            dtoList.add(entity2DTO(entity));
        }
        return dtoList;
    }
}
