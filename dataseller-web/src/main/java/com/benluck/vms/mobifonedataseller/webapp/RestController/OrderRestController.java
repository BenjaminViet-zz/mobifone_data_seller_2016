package com.benluck.vms.mobifonedataseller.webapp.RestController;

import com.benluck.vms.mobifonedataseller.core.business.OrderDataCodeManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.OrderManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.OrderDTO;
import com.benluck.vms.mobifonedataseller.core.dto.OrderDataCodeDTO;
import com.benluck.vms.mobifonedataseller.webapp.RestModel.OrderRestModel;
import com.benluck.vms.mobifonedataseller.webapp.RestModel.OrderRestModelBeanUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 12/4/16
 * Time: 19:15
 * To change this template use File | Settings | File Templates.
 */
@RestController
public class OrderRestController{
    private Logger logger = Logger.getLogger(OrderRestController.class);

    @Autowired
    private OrderManagementLocalBean orderService;
    @Autowired
    private OrderDataCodeManagementLocalBean orderDataCodeService;

    @RequestMapping(value = "/order/{orderId}", method = RequestMethod.GET, produces = "application/json")
    public OrderRestModel getOrderInfoJSON(@PathVariable("orderId") Long orderId){
        try{
            OrderDTO orderDTO = this.orderService.findById(orderId);
            List<OrderDataCodeDTO> orderDataCodeDTOList = this.orderDataCodeService.findByOrderId(orderId);
            return OrderRestModelBeanUtil.dto2RestModel(orderDTO, orderDataCodeDTOList);
        }catch (Exception e){
            logger.error(e.getMessage());
            return new OrderRestModel();
        }
    }

}
