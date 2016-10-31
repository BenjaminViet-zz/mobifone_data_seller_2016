package com.benluck.vms.mobifonedataseller.beanUtil;

import com.benluck.vms.mobifonedataseller.core.dto.OrderHistoryDTO;
import com.benluck.vms.mobifonedataseller.core.dto.UserDTO;
import com.benluck.vms.mobifonedataseller.domain.OrderHistoryEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 10/31/16
 * Time: 20:04
 * To change this template use File | Settings | File Templates.
 */
public class OrderHistoryBeanUtil {
    public static OrderHistoryDTO entity2DTO(OrderHistoryEntity entity){
        OrderHistoryDTO dto = new OrderHistoryDTO();
        dto.setOrderHistoryId(entity.getOrderHistoryId());
        dto.setOrder(OrderBeanUtil.entity2DTO(entity.getOrder()));
        dto.setOperator(entity.getOperator());
        dto.setOriginalData(entity.getOriginalData());
        dto.setNewData(entity.getNewData());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setCreatedBy(new UserDTO(entity.getCreatedBy().getUserId(), entity.getCreatedBy().getUserName(), entity.getCreatedBy().getDisplayName()));
        return dto;
    }

    public static List<OrderHistoryDTO> entityList2DTOList(List<OrderHistoryEntity> entityList){
        List<OrderHistoryDTO> dtoList = new ArrayList<OrderHistoryDTO>();
        for (OrderHistoryEntity entity : entityList){
            dtoList.add(OrderHistoryBeanUtil.entity2DTO(entity));
        }
        return  dtoList;
    }
}
