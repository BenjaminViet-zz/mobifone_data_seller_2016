package com.benluck.vms.mobifonedataseller.webapp.RestController;

import com.benluck.vms.mobifonedataseller.core.business.OrderDataCodeManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.OrderManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.PackageDataManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.UserManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.OrderDTO;
import com.benluck.vms.mobifonedataseller.core.dto.OrderDataCodeDTO;
import com.benluck.vms.mobifonedataseller.core.dto.PackageDataDTO;
import com.benluck.vms.mobifonedataseller.core.dto.UserDTO;
import com.benluck.vms.mobifonedataseller.utils.MobiFoneSecurityBase64Util;
import com.benluck.vms.mobifonedataseller.webapp.RestModel.*;
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
    private UserManagementLocalBean userService;
    @Autowired
    private PackageDataManagementLocalBean packageDataService;
    @Autowired
    private OrderDataCodeManagementLocalBean orderDataCodeService;

    @RequestMapping(value = "/order/{username}/{password}/{orderId}", method = RequestMethod.GET, produces = "application/json")
    public OrderRestModel getOrderInfoJSON(@PathVariable("username") String username,
                                           @PathVariable("password") String password,
                                           @PathVariable("orderId") Long orderId){
        try{
            String encodedPassword = MobiFoneSecurityBase64Util.encode(password);
            UserDTO userDTO = this.userService.loadUserByUserNameAndPassword(username, password);

            OrderDTO orderDTO = this.orderService.findById(orderId);
            List<OrderDataCodeDTO> orderDataCodeDTOList = this.orderDataCodeService.findByOrderId(orderId);
            return OrderRestModelBeanUtil.dto2RestModel(orderDTO, orderDataCodeDTOList);
        }catch (Exception e){
            logger.error(e.getMessage());
            return new OrderRestModel();
        }
    }

    @RequestMapping(value = "/listOfOrders/{username}/{password}/{shopcode}", method = RequestMethod.GET, produces = "application/json")
    public OrderListRestModel getOrderList4KHDN(@PathVariable("username") String username,
                                                @PathVariable("password") String password,
                                                @PathVariable("shopcode") String shopCode){
        try{
            String encodedPassword = MobiFoneSecurityBase64Util.encode(password);
            UserDTO userDTO = this.userService.loadUserByUserNameAndPassword(username, password);

            List<OrderDTO> orderDTOList = this.orderService.fetchAllOrderList4KHDNByShopCode(shopCode);
            return OrderListRestModelBeanUtil.dto2RestModel(orderDTOList);
        }catch (Exception e){
            logger.error(e.getMessage());
            return new OrderListRestModel();
        }
    }

    @RequestMapping(value = "/packages/{username}/{password}")
    public PackageListRestModel getListPackageDate(@PathVariable("username") String username,
                                                   @PathVariable("password") String password){
        try{
//            String encodedPassword = MobiFoneSecurityBase64Util.encode(password);
            UserDTO userDTO = this.userService.loadUserByUserNameAndPassword(username, password);

            List<PackageDataDTO> packageDataDTOList = this.packageDataService.findAll();
            return PackageListRestBeanUtil.dto2Model(packageDataDTOList);
        }catch (Exception e){
            logger.error(e.getMessage());
            return new PackageListRestModel();
        }
    }
}
