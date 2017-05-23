package com.benluck.vms.mobifonedataseller.beanUtil;

import com.benluck.vms.mobifonedataseller.core.dto.MBDReportGeneralExpenseDTO;
import com.benluck.vms.mobifonedataseller.domain.MBDCostEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/9/16
 * Time: 21:55
 * To change this template use File | Settings | File Templates.
 */
public class MBDReportGeneralExpenseDTOBeanUtil {

    public static MBDReportGeneralExpenseDTO entity2DTO(MBDCostEntity entity){
        if (entity != null){
            MBDReportGeneralExpenseDTO dto = new MBDReportGeneralExpenseDTO();
            dto.setCostId(entity.getCostId());
            dto.setShopCode(entity.getShopCode());
            dto.setShopName(entity.getShopName());
            dto.setDevelopmentAmount1(entity.getDevelopmentAmount1());
            dto.setDevelopmentAmount2(entity.getDevelopmentAmount2());
            dto.setDevelopmentAmount3(entity.getDevelopmentAmount3());
            dto.setMaintainAmount1(entity.getMaintainAmount1());
            dto.setMaintainAmount2(entity.getMaintainAmount2());
            dto.setMaintainAmount3(entity.getMaintainAmount3());
            return dto;
        }
        return null;
    }

    public static List<MBDReportGeneralExpenseDTO> entityList2DTOList(List<MBDCostEntity> entityList){
        if (entityList != null){
            List<MBDReportGeneralExpenseDTO> dtoList = new ArrayList<MBDReportGeneralExpenseDTO>();
            for (MBDCostEntity entity : entityList){
                dtoList.add(entity2DTO(entity));
            }
            return dtoList;
        }
        return null;
    }
}
