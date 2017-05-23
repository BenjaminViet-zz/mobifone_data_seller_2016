package com.benluck.vms.mobifonedataseller.beanUtil;

import com.benluck.vms.mobifonedataseller.core.dto.PackageDataCodeGenDTO;
import com.benluck.vms.mobifonedataseller.domain.PackageDataCodeGenEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/6/16
 * Time: 20:46
 * To change this template use File | Settings | File Templates.
 */
public class PackageDataCodeGenBeanUtil {

    public static PackageDataCodeGenDTO entity2DTO(PackageDataCodeGenEntity entity){
        if (entity != null){
            PackageDataCodeGenDTO dto = new PackageDataCodeGenDTO();
            dto.setPackageDataCodeGenId(entity.getPackageDataCodeGenId());
            dto.setPackageData(PackageDataBeanUtil.entity2DTO(entity.getPackageData()));
            dto.setYear(entity.getYear());
            dto.setBatch1_Remaining(entity.getBatch1_Remaining());
            dto.setBatch2_Remaining(entity.getBatch2_Remaining());
            dto.setBatch3_Remaining(entity.getBatch3_Remaining());
            dto.setBatch4_Remaining(entity.getBatch4_Remaining());
            dto.setBatch5_Remaining(entity.getBatch5_Remaining());
            dto.setBatch6_Remaining(entity.getBatch6_Remaining());
            dto.setBatch7_Remaining(entity.getBatch7_Remaining());
            dto.setBatch8_Remaining(entity.getBatch8_Remaining());
            dto.setBatch9_Remaining(entity.getBatch9_Remaining());
            dto.setCreatedDate(entity.getCreatedDate());
            dto.setStatus(entity.getStatus());
            return dto;
        }
        return null;
    }

    public static List<PackageDataCodeGenDTO> entityList2DTOList(List<PackageDataCodeGenEntity> entityList){
        if (entityList != null){
            List<PackageDataCodeGenDTO> dtoList = new ArrayList<PackageDataCodeGenDTO>();
            for (PackageDataCodeGenEntity entity : entityList){
                dtoList.add(entity2DTO(entity));
            }
            return dtoList;
        }
        return null;
    }
}
