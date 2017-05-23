package com.benluck.vms.mobifonedataseller.beanUtil;

import com.benluck.vms.mobifonedataseller.core.dto.MBDCodeHistoryDTO;
import com.benluck.vms.mobifonedataseller.domain.MBDCodeHistoryEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/11/16
 * Time: 19:50
 * To change this template use File | Settings | File Templates.
 */
public class CodeHistoryBeanUtil {

    public static MBDCodeHistoryDTO entity2DTO(MBDCodeHistoryEntity entity){
        if (entity != null){
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
        return null;
    }

    public static List<MBDCodeHistoryDTO> entityList2DTOList(List<MBDCodeHistoryEntity> entityList){
        if (entityList != null){
            List<MBDCodeHistoryDTO> dtoList = new ArrayList<MBDCodeHistoryDTO>();
            for (MBDCodeHistoryEntity entity : entityList){
                dtoList.add(entity2DTO(entity));
            }
            return dtoList;
        }
        return null;
    }
}
