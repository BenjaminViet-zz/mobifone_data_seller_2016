package com.benluck.vms.mobifonedataseller.webapp.command;

import com.benluck.vms.mobifonedataseller.core.dto.OrderDTO;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/2/16
 * Time: 07:14
 * To change this template use File | Settings | File Templates.
 */
public class OrderCommand extends AbstractCommand<OrderDTO>{
    public OrderCommand(){
        this.pojo = new OrderDTO();
    }
}
