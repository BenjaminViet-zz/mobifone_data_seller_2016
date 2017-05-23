package com.benluck.vms.mobifonedataseller.beanUtil;

import com.benluck.vms.mobifonedataseller.core.dto.MBDCostInfoDTO;
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
public class MBDCostInfoBeanUtil {

    public static MBDCostInfoDTO entity2DTO(MBDCostEntity entity){
        if (entity != null){
            MBDCostInfoDTO dto = new MBDCostInfoDTO();
            dto.setCostId(entity.getCostId());
            dto.setShopCode(entity.getShopCode());
            dto.setShopName(entity.getShopName());
            dto.setEmpCode(entity.getEmpCode());
            dto.setCustId(entity.getCustId());
            dto.setSubId(entity.getSubId());
            dto.setIsdn(entity.getIsdn());
            dto.setName(entity.getName());
            dto.setBusType(entity.getBusType());
            dto.setCustType(entity.getCustType());
            dto.setStaDateTime(entity.getStaDateTime());
            dto.setActStatus(entity.getActStatus());
            dto.setStatus(entity.getStatus());
            dto.setIssueMonth(entity.getIssueMonth());
            dto.setPayment(entity.getPayment());
            dto.setDevelopmentPhase1(entity.getDevelopmentPhase1());
            dto.setDevelopmentAmount1(entity.getDevelopmentAmount1());
            dto.setDevelopmentPhase2(entity.getDevelopmentPhase2());
            dto.setDevelopmentAmount2(entity.getDevelopmentAmount2());
            dto.setDevelopmentPhase3(entity.getDevelopmentPhase3());
            dto.setDevelopmentAmount3(entity.getDevelopmentAmount3());
            dto.setMaintainPhase1(entity.getMaintainPhase1());
            dto.setMaintainAmount1(entity.getMaintainAmount1());
            dto.setMaintainPhase2(entity.getMaintainPhase2());
            dto.setMaintainAmount2(entity.getMaintainAmount2());
            dto.setMaintainPhase3(entity.getMaintainPhase3());
            dto.setMaintainAmount3(entity.getMaintainAmount3());
            dto.setInsertDateTime(entity.getInsertDateTime());
            dto.setPaymentStatus(entity.getPaymentStatus());
            dto.setPaymentDate(entity.getPaymentDate());
            return dto;
        }
        return null;
    }

    public static List<MBDCostInfoDTO> entityList2DTOList(List<MBDCostEntity> entityList){
        if (entityList != null){
            List<MBDCostInfoDTO> dtoList = new ArrayList<MBDCostInfoDTO>();
            for (MBDCostEntity entity : entityList){
                dtoList.add(entity2DTO(entity));
            }
            return dtoList;
        }
        return null;
    }
}
