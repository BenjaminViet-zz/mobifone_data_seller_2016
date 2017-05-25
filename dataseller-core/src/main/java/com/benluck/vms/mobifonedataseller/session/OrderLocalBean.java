package com.benluck.vms.mobifonedataseller.session;

import com.benluck.vms.mobifonedataseller.domain.OrderEntity;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/2/16
 * Time: 07:12
 * To change this template use File | Settings | File Templates.
 */
public interface OrderLocalBean extends GenericSessionBean<OrderEntity, Long>{
    List<OrderEntity> fetchAllOrderList4KHDNByShopCode(String shopCode);

    OrderEntity findByIdAndShopCode(Long orderId, String shopCode);

    List<OrderEntity> findListByKHDNIdInWaitingStatus(Long khdnId);

    List<OrderEntity> findAllInWaitingStatus();
}
