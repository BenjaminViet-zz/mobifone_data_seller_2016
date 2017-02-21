package com.benluck.vms.mobifonedataseller.webapp.RestModel;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 2/21/17
 * Time: 12:43
 * To change this template use File | Settings | File Templates.
 */
public class OrderListRestModel {
    List<OrderNoCardCodeListRestModel> orderList;

    public List<OrderNoCardCodeListRestModel> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderNoCardCodeListRestModel> orderList) {
        this.orderList = orderList;
    }
}
