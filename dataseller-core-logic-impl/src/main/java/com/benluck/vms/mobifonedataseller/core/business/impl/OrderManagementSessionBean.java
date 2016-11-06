package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.beanUtil.OrderBeanUtil;
import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.utils.Config;
import com.benluck.vms.mobifonedataseller.core.business.OrderManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.KHDNDTO;
import com.benluck.vms.mobifonedataseller.core.dto.OrderDTO;
import com.benluck.vms.mobifonedataseller.core.dto.PackageDataDTO;
import com.benluck.vms.mobifonedataseller.core.dto.UserDTO;
import com.benluck.vms.mobifonedataseller.domain.*;
import com.benluck.vms.mobifonedataseller.session.OrderDataCodeLocalBean;
import com.benluck.vms.mobifonedataseller.session.OrderHistoryLocalBean;
import com.benluck.vms.mobifonedataseller.session.OrderLocalBean;

import javax.ejb.*;
import java.sql.Timestamp;
import java.util.*;

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
    @EJB
    private OrderHistoryLocalBean orderHistoryService;
    @EJB
    private OrderDataCodeLocalBean orderDataCodeService;

    public OrderManagementSessionBean() {
    }

    @Override
    public Object[] searchByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer offset, Integer limitItems, String whereClause) {
        Object[] resultObject = this.orderService.searchByProperties(properties, sortExpression, sortDirection, offset, limitItems, whereClause);
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
        entity.setIssuedDate(pojo.getIssuedDate());
        entity.setShippingDate(pojo.getShippingDate());
        entity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        entity.setOrderStatus(pojo.getOrderStatus());
        entity.setActiveStatus(Constants.ORDER_ACTIVE_STATUS_ALIVE);
        entity = this.orderService.save(entity);

        createdOrderHistory(pojo, Constants.ORDER_HISTORY_OPERATOR_CREATED, entity);

        if(pojo.getOrderStatus().equals(Constants.ORDER_STATUS_FINISH)){
            saveDataCodes4Order(entity, pojo.getDataCodeHasSet2Store());
        }
    }

    @Override
    public void updateItem(OrderDTO pojo) throws ObjectNotFoundException, DuplicateKeyException {
        OrderEntity dbItem = this.orderService.findById(pojo.getOrderId());
        boolean storeDataCodes = false;

        if(dbItem.getOrderStatus().equals(Constants.ORDER_STATUS_PROCESSING)
                && pojo.getOrderStatus().equals(Constants.ORDER_STATUS_FINISH)){
            storeDataCodes = true;
        }

        if(!dbItem.getKhdn().getKHDNId().equals(pojo.getKhdn().getKHDNId())){
            KHDNEntity khdnEntity = new KHDNEntity();
            khdnEntity.setKHDNId(pojo.getKhdn().getKHDNId());
            dbItem.setKhdn(khdnEntity);
        }

        if(!dbItem.getPackageData().getPackageDataId().equals(pojo.getPackageData().getPackageDataId())){
            PackageDataEntity packageDataEntity = new PackageDataEntity();
            packageDataEntity.setPackageDataId(pojo.getPackageData().getPackageDataId());
            dbItem.setPackageData(packageDataEntity);
        }

        dbItem.setQuantity(pojo.getQuantity());
        dbItem.setUnitPrice(pojo.getUnitPrice());
        dbItem.setIssuedDate(pojo.getIssuedDate());
        dbItem.setShippingDate(pojo.getShippingDate());
        dbItem.setOrderStatus(pojo.getOrderStatus());
        dbItem.setLastModified(new Timestamp(System.currentTimeMillis()));
        this.orderService.update(dbItem);

        createdOrderHistory(pojo, Constants.ORDER_HISTORY_OPERATOR_UPDATED, null);
        if(storeDataCodes){
            saveDataCodes4Order(dbItem, pojo.getDataCodeHasSet2Store());
        }
    }

    private void saveDataCodes4Order(OrderEntity orderEntity, HashSet<String> dataCodes2Store) throws DuplicateKeyException{
        Integer totalDataCode = this.orderDataCodeService.countTotal();
        if(totalDataCode.intValue() <= Constants.ORDER_DATA_CODE_SERIAL_OFFSET){
            totalDataCode = Constants.ORDER_DATA_CODE_SERIAL_OFFSET + 1;
        }

        Calendar current = Calendar.getInstance();
        Integer expiredDays = Integer.valueOf(Config.getInstance().getProperty("order_data_code_expired_2016"));
        if(current.get(Calendar.YEAR) == 2017){
            expiredDays = Integer.valueOf(Config.getInstance().getProperty("order_data_code_expired_2017_or_later"));
        }
        Calendar expiredDate = Calendar.getInstance();
        expiredDate.add(Calendar.DAY_OF_YEAR, expiredDays);
        Timestamp expiredDate4CardCode = new Timestamp(expiredDate.getTimeInMillis());

        Iterator<String> ito = dataCodes2Store.iterator();
        while (ito.hasNext()){
            String dataCodeStr = ito.next();

            StringBuilder serial = new StringBuilder();
            serial.append(dataCodeStr.substring(0, 5));

            if(totalDataCode > Constants.ORDER_DATA_CODE_SERIAL_OFFSET && totalDataCode < 99999){
                serial.append("00");
            }else if(totalDataCode > 99999 && totalDataCode < 999999){
                serial.append("0");
            }
            serial.append(totalDataCode.toString());

            OrderDataCodeEntity entity = new OrderDataCodeEntity();
            entity.setOrder(orderEntity);
            entity.setSerial(Long.valueOf(serial.toString()));
            entity.setDataCode(Long.valueOf(dataCodeStr.toString()));
            entity.setExpiredDate(expiredDate4CardCode);
            this.orderDataCodeService.save(entity);
            totalDataCode++;
        }
    }

    private void createdOrderHistory(OrderDTO pojo, Integer operator, OrderEntity dbItem) throws DuplicateKeyException{
        OrderHistoryEntity entity = new OrderHistoryEntity();

        if(dbItem != null){
            entity.setOrder(dbItem);
        }else{
            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setOrderId(pojo.getOrderId());
            entity.setOrder(orderEntity);
        }

        KHDNEntity khdnEntity = new KHDNEntity();
        khdnEntity.setKHDNId(pojo.getKhdn().getKHDNId());
        entity.setKhdn(khdnEntity);

        PackageDataEntity packageDataEntity = new PackageDataEntity();
        packageDataEntity.setPackageDataId(pojo.getPackageData().getPackageDataId());
        entity.setPackageData(packageDataEntity);

        UserEntity createdBy = new UserEntity();
        createdBy.setUserId(pojo.getCreatedBy().getUserId());
        entity.setCreatedBy(createdBy);

        entity.setOperator(operator);
        entity.setQuantity(pojo.getQuantity());
        entity.setUnitPrice(pojo.getUnitPrice());
        entity.setIssuedDate(pojo.getIssuedDate());
        entity.setShippingDate(pojo.getShippingDate());
        entity.setOrderStatus(pojo.getOrderStatus());
        entity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        this.orderHistoryService.save(entity);
    }

    @Override
    public void deleteItem(Long orderId, Long modifiedByUserId) throws ObjectNotFoundException, DuplicateKeyException, RemoveException {
        OrderEntity dbItem = this.orderService.findById(orderId);
        dbItem.setActiveStatus(Constants.ORDER_ACTIVE_STATUS_DIE);

        OrderDTO pojo = new OrderDTO();
        pojo.setOrderId(dbItem.getOrderId());

        KHDNDTO khdndto = new KHDNDTO();
        khdndto.setKHDNId(dbItem.getKhdn().getKHDNId());
        pojo.setKhdn(khdndto);

        PackageDataDTO packageDataDTO = new PackageDataDTO();
        packageDataDTO.setPackageDataId(dbItem.getPackageData().getPackageDataId());
        pojo.setPackageData(packageDataDTO);

        UserDTO modifiedBy = new UserDTO();
        modifiedBy.setUserId(modifiedByUserId);
        pojo.setCreatedBy(modifiedBy);

        pojo.setQuantity(dbItem.getQuantity());
        pojo.setUnitPrice(dbItem.getUnitPrice());
        pojo.setIssuedDate(dbItem.getIssuedDate());
        pojo.setShippingDate(dbItem.getShippingDate());
        pojo.setOrderStatus(dbItem.getOrderStatus());

        createdOrderHistory(pojo, Constants.ORDER_HISTORY_OPERATOR_DELETED, null);
    }
}
