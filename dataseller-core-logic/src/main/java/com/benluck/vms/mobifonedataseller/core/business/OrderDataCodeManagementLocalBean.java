package com.benluck.vms.mobifonedataseller.core.business;

import com.benluck.vms.mobifonedataseller.core.dto.OrderDataCodeDTO;

import javax.ejb.Local;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/6/16
 * Time: 12:07
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface OrderDataCodeManagementLocalBean{

    List<OrderDataCodeDTO> fetchByOrderId(Long orderId);

}
