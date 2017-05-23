package com.benluck.vms.mobifonedataseller.beanUtil;

import com.benluck.vms.mobifonedataseller.core.dto.PackageDataDTO;
import com.benluck.vms.mobifonedataseller.domain.PackageDataEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 10/30/16
 * Time: 22:36
 * To change this template use File | Settings | File Templates.
 */
public class PackageDataBeanUtil {
    public static PackageDataDTO entity2DTO(PackageDataEntity entity){
        if (entity != null){
            PackageDataDTO dto = new PackageDataDTO();
            dto.setPackageDataId(entity.getPackageDataId());
            dto.setName(entity.getName());
            dto.setValue(entity.getValue());
            dto.setVolume(entity.getVolume());
            dto.setDuration(entity.getDuration());
            dto.setDurationText(entity.getDurationText());
            dto.setNumberOfExtend(entity.getNumberOfExtend());
            dto.setTk(entity.getTk());
            dto.setCustomPrefixUnitPrice(entity.getCustomPrefixUnitPrice());

            if(entity.getPackageDataCodeGenList() != null && entity.getPackageDataCodeGenList().size() > 0){
                dto.setGeneratedCardCode(true);
            }

            return dto;
        }
        return null;
    }

    public static List<PackageDataDTO> entityList2DTOList(List<PackageDataEntity> entityList){
        if (entityList != null){
            List<PackageDataDTO> dtoList = new ArrayList<PackageDataDTO>();
            for (PackageDataEntity entity : entityList){
                dtoList.add(PackageDataBeanUtil.entity2DTO(entity));
            }
            return dtoList;
        }
        return null;
    }
}
