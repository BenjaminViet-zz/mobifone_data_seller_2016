package com.benluck.vms.mobifonedataseller.beanUtil;

import com.benluck.vms.mobifonedataseller.core.dto.KHDNDTO;
import com.benluck.vms.mobifonedataseller.domain.KHDNEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 10/30/16
 * Time: 22:45
 * To change this template use File | Settings | File Templates.
 */
public class KHDNBeanUtil {
    public static KHDNDTO entity2DTO(KHDNEntity entity){
        KHDNDTO dto = new KHDNDTO();
        dto.setKHDNId(entity.getKHDNId());
        dto.setName(entity.getName());
        dto.setMst(entity.getMst());
        dto.setGpkd(entity.getGpkd());
        dto.setIssuedContractDate(entity.getIssuedContractDate());
        dto.setStb_vas(entity.getStb_vas());
        dto.setCustId(entity.getCustId());
        return dto;
    }

    public static List<KHDNDTO> entityList2DTOList(List<KHDNEntity> entityList){
        List<KHDNDTO> khdndtoList = new ArrayList<KHDNDTO>();
        for (KHDNEntity entity : entityList){
            khdndtoList.add(KHDNBeanUtil.entity2DTO(entity));
        }
        return khdndtoList;
    }
}
