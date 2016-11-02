package com.benluck.vms.mobifonedataseller.beanUtil;

import com.benluck.vms.mobifonedataseller.core.dto.OrderDTO;
import com.benluck.vms.mobifonedataseller.core.dto.UserDTO;
import com.benluck.vms.mobifonedataseller.domain.OrderEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 10/31/16
 * Time: 19:46
 * To change this template use File | Settings | File Templates.
 */
public class OrderBeanUtil {
    public static OrderDTO entity2DTO(OrderEntity entity){
        OrderDTO dto = new OrderDTO();
        dto.setOrderId(entity.getOrderId());
        dto.setKhdn(KHDNBeanUtil.entity2DTO(entity.getKhdn()));
        dto.setPackageData(PackageDataBeanUtil.entity2DTO(entity.getPackageData()));
        dto.setQuantity(entity.getQuantity());
        dto.setUnitPrice(entity.getUnitPrice());
        dto.setIssuedDate(entity.getIssuedDate());
        dto.setShippingDate(entity.getShippingDate());
        dto.setOrderStatus(entity.getOrderStatus());
        dto.setActiveStatus(entity.getActiveStatus());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setLastModified(entity.getLastModified());
        dto.setCreatedBy(new UserDTO(entity.getCreatedBy().getUserId(), entity.getCreatedBy().getUserName(), entity.getCreatedBy().getDisplayName()));
        return dto;
    }

    public static List<OrderDTO> entityList2DTOList(List<OrderEntity> entityList){
        List<OrderDTO> dtoList = new ArrayList<OrderDTO>();
        for (OrderEntity entity : entityList){
            dtoList.add(OrderBeanUtil.entity2DTO(entity));
        }
        return dtoList;
    }
}
