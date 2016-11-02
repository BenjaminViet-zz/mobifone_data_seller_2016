package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.beanUtil.OrderBeanUtil;
import com.benluck.vms.mobifonedataseller.core.business.OrderManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.OrderDTO;
import com.benluck.vms.mobifonedataseller.domain.KHDNEntity;
import com.benluck.vms.mobifonedataseller.domain.OrderEntity;
import com.benluck.vms.mobifonedataseller.domain.PackageDataEntity;
import com.benluck.vms.mobifonedataseller.domain.UserEntity;
import com.benluck.vms.mobifonedataseller.session.OrderLocalBean;

import javax.ejb.DuplicateKeyException;
import javax.ejb.EJB;
import javax.ejb.ObjectNotFoundException;
import javax.ejb.Stateless;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/2/16
 * Time: 07:12
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "OrderManagementSessionEJB")
public class OrderManagementSessionBean implements OrderManagementLocalBean{

    @EJB
    private OrderLocalBean orderService;

    public OrderManagementSessionBean() {
    }

    @Override
    public Object[] searchByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer offset, Integer limitItems) {
        Object[] resultObject = this.orderService.searchByProperties(properties, sortExpression, sortDirection, offset, limitItems);
        List<OrderDTO> orderDTOList = OrderBeanUtil.entityList2DTOList((List<OrderEntity>)resultObject[1]);
        resultObject[1] = orderDTOList;
        return resultObject;
    }

    @Override
    public OrderDTO findById(Long orderId) throws ObjectNotFoundException {
        return OrderBeanUtil.entity2DTO(this.orderService.findById(orderId));
    }

    @Override
    public void addItem(OrderDTO pojo) throws DuplicateKeyException {
        OrderEntity entity = new OrderEntity();

        KHDNEntity khdnEntity = new KHDNEntity();
        khdnEntity.setKHDNId(pojo.getKhdn().getKHDNId());
        entity.setKhdn(khdnEntity);

        PackageDataEntity packageDataEntity = new PackageDataEntity();
        packageDataEntity.setPackageDataId(pojo.getPackageData().getPackageDataId());
        entity.setPackageData(packageDataEntity);

        UserEntity createdBy = new UserEntity();
        createdBy.setUserId(pojo.getCreatedBy().getUserId());
        entity.setCreatedBy(createdBy);

        entity.setQuantity(pojo.getQuantity());
        entity.setUnitPrice(pojo.getUnitPrice());
        entity.setIssueDate(pojo.getIssueDate());
        entity.setShippingDate(pojo.getShippingDate());
        entity.setStatus(pojo.getStatus());
        entity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        this.orderService.save(entity);
    }

    @Override
    public void updateItem(OrderDTO pojo) throws ObjectNotFoundException, DuplicateKeyException {
        OrderEntity dbItem = this.orderService.findById(pojo.getCreatedBy().getUserId());

        createdOrderHistory(dbItem);

        dbItem.setStatus(pojo.getStatus());
        dbItem.setQuantity(pojo.getQuantity());
        dbItem.setUnitPrice(pojo.getUnitPrice());
        dbItem.setIssueDate(pojo.getIssueDate());
        dbItem.setShippingDate(pojo.getShippingDate());
        this.orderService.update(dbItem);
    }

    private void createdOrderHistory(OrderEntity orderEntity){

    }
}
