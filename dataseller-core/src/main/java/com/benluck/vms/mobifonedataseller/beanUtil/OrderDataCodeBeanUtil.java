package com.benluck.vms.mobifonedataseller.beanUtil;

import com.benluck.vms.mobifonedataseller.core.dto.KHDNDTO;
import com.benluck.vms.mobifonedataseller.core.dto.OrderDTO;
import com.benluck.vms.mobifonedataseller.core.dto.OrderDataCodeDTO;
import com.benluck.vms.mobifonedataseller.core.dto.PackageDataDTO;
import com.benluck.vms.mobifonedataseller.domain.KHDNEntity;
import com.benluck.vms.mobifonedataseller.domain.OrderDataCodeEntity;
import com.benluck.vms.mobifonedataseller.domain.PackageDataEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/6/16
 * Time: 11:34
 * To change this template use File | Settings | File Templates.
 */
public class OrderDataCodeBeanUtil {

    private static OrderDataCodeDTO entity2DTO(OrderDataCodeEntity entity){
        if (entity != null){
            OrderDataCodeDTO dto = new OrderDataCodeDTO();
            dto.setOrderDataCodeId(entity.getOrderDataCodeId());

            KHDNEntity khdnEntity = entity.getOrder().getKhdn();
            KHDNDTO khdnDTO = new KHDNDTO(khdnEntity.getKHDNId(), khdnEntity.getName(), khdnEntity.getMst(), khdnEntity.getGpkd());

            PackageDataEntity packageDataEntity = entity.getOrder().getPackageData();
            PackageDataDTO packageDataDTO = new PackageDataDTO(packageDataEntity.getPackageDataId(), packageDataEntity.getName(), packageDataEntity.getValue(), packageDataEntity.getVolume(), packageDataEntity.getDuration(), packageDataEntity.getDurationText());

            OrderDTO orderDTO = new OrderDTO(entity.getOrder().getOrderId(), khdnDTO, packageDataDTO);

            dto.setOrder(orderDTO);
            dto.setSerial(entity.getSerial());
            dto.setDataCode(entity.getDataCode());
            dto.setExpiredDate(entity.getExpiredDate());
            return dto;
        }
        return null;
    }

    public static List<OrderDataCodeDTO> entityList2DTOList(List<OrderDataCodeEntity> entityList){
        if (entityList != null){
            List<OrderDataCodeDTO> dtoList = new ArrayList<OrderDataCodeDTO>();
            for (OrderDataCodeEntity entity : entityList){
                dtoList.add(entity2DTO(entity));
            }
            return dtoList;
        }
        return null;
    }
}
