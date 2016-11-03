package com.benluck.vms.mobifonedataseller.webapp.command;

import com.benluck.vms.mobifonedataseller.core.dto.OrderHistoryDTO;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/3/16
 * Time: 07:12
 * To change this template use File | Settings | File Templates.
 */
public class OrderHistoryCommand extends AbstractCommand<OrderHistoryDTO>{
    public OrderHistoryCommand(){
        this.pojo = new OrderHistoryDTO();
    }
}
