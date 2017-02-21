package com.benluck.vms.mobifonedataseller.session.impl;

import com.benluck.vms.mobifonedataseller.domain.OrderEntity;
import com.benluck.vms.mobifonedataseller.session.OrderLocalBean;

import javax.ejb.Stateless;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/2/16
 * Time: 07:13
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "OrderSessionEJB")
public class OrderSessionBean extends AbstractSessionBean<OrderEntity, Long> implements OrderLocalBean{
    public OrderSessionBean() {
    }

    @Override
    public List<OrderEntity> fetchAllOrderList4KHDNByShopCode(String shopCode) {
        return (List<OrderEntity>)entityManager.createQuery("FROM OrderEntity WHERE khdn.shopCode = :shopCode ORDER BY issuedDate DESC").setParameter("shopCode", shopCode).getResultList();
    }

    @Override
    public OrderEntity findByIdAndShopCode(Long orderId, String shopCode) {
        return (OrderEntity)entityManager.createQuery("FROM OrderEntity WHERE orderId = :orderId AND khdn.shopCode = :shopCode")
                .setParameter("orderId", orderId)
                .setParameter("shopCode", shopCode)
                .getSingleResult();
    }
}
