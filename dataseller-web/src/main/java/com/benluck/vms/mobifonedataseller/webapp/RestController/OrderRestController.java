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

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
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
            if (validateUser(username, password)){
                OrderDTO orderDTO = this.orderService.findById(orderId);
                List<OrderDataCodeDTO> orderDataCodeDTOList = this.orderDataCodeService.findByOrderId(orderId);
                return OrderRestModelBeanUtil.dto2RestModel(orderDTO, orderDataCodeDTOList);
            }else{
                return new OrderRestModel();
            }
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
            if (validateUser(username, password)){
                List<OrderDTO> orderDTOList = this.orderService.fetchAllOrderList4KHDNByShopCode(shopCode);
                return OrderListRestModelBeanUtil.dto2RestModel(orderDTOList);
            }else{
                return new OrderListRestModel();
            }
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
            if (validateUser(username, password)){
                List<PackageDataDTO> packageDataDTOList = this.packageDataService.findAll();
                return PackageListRestBeanUtil.dto2Model(packageDataDTOList);
            }else{
                return new PackageListRestModel();
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            return new PackageListRestModel();
        }
    }

    private boolean validateUser(String userName, String password) throws Exception{
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        List<UserDTO> allUserList = this.userService.fetchAllUserIsNotLDAP();
        if (allUserList != null && allUserList.size() > 0){
            StringBuffer tmpEncryptedPasswordSHA256 = null;
            for (UserDTO userDTO : allUserList){
                if (userDTO.getUserName().equals(userName)){

                    tmpEncryptedPasswordSHA256 = new StringBuffer(DatatypeConverter.printHexBinary(md.digest(userDTO.getPassword().getBytes("UTF-8"))));
                    System.out.println(tmpEncryptedPasswordSHA256.toString());
                    if (password.equals(tmpEncryptedPasswordSHA256.toString())){
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
