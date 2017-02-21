package com.benluck.vms.mobifonedataseller.webapp.RestModel;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.core.dto.OrderDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 2/21/17
 * Time: 12:46
 * To change this template use File | Settings | File Templates.
 */
public class OrderListRestModelBeanUtil {
    public static OrderListRestModel dto2RestModel(List<OrderDTO> orderDTOList){
        OrderListRestModel model = new OrderListRestModel();
        if (orderDTOList != null && orderDTOList.size() > 0){
            List<OrderNoCardCodeListRestModel> orderModelList = new ArrayList<OrderNoCardCodeListRestModel>();
            OrderNoCardCodeListRestModel orderModel = null;
            for (OrderDTO orderDTO : orderDTOList){
                orderModel = new OrderNoCardCodeListRestModel();
                orderModel.setPackageDataName(orderDTO.getPackageData().getName());
                orderModel.setQuantity(orderDTO.getQuantity());
                orderModel.setUnitPrice(orderDTO.getUnitPrice());
                orderModel.setIssuedDate(orderDTO.getIssuedDate());
                if (orderDTO.getOrderStatus().equals(Constants.ORDER_STATUS_FINISH)){
                    orderModel.setStatus("Đã hoàn thành");
                }else{
                    orderModel.setStatus("Đang xử lý");
                }
                orderModelList.add(orderModel);
            }
            model.setOrderList(orderModelList);
        }
        return model;
    }
}
