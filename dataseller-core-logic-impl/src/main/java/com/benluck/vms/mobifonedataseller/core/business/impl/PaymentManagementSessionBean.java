package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.beanUtil.PaymentBeanUtil;
import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.core.business.PaymentManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.PaymentDTO;
import com.benluck.vms.mobifonedataseller.domain.*;
import com.benluck.vms.mobifonedataseller.session.PaymentHistoryLocalBean;
import com.benluck.vms.mobifonedataseller.session.PaymentLocalBean;

import javax.ejb.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 5/25/17
 * Time: 23:27
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "PaymentManagementSessionEJB")
public class PaymentManagementSessionBean implements PaymentManagementLocalBean{

    @EJB
    private PaymentLocalBean paymentService;
    @EJB
    private PaymentHistoryLocalBean paymentHistoryService;

    public PaymentManagementSessionBean() {
    }

    @Override
    public Object[] searchByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer firstItem, Integer maxPageItems) {
        Object[] resultSet = this.paymentService.searchByProperties(properties, sortExpression, sortDirection, firstItem, maxPageItems);
        List<PaymentEntity> entityList = (List<PaymentEntity>)resultSet[1];

        if (entityList != null && entityList.size() > 0){
            resultSet[1] = PaymentBeanUtil.entityList2DTOList(entityList);
        }

        return resultSet;
    }

    @Override
    public void deleteItem(Long paymentId) throws RemoveException {
        this.paymentHistoryService.deleteByPaymentId(paymentId);
        this.paymentService.delete(paymentId);
    }

    @Override
    public PaymentDTO findById(Long paymentId) throws ObjectNotFoundException {
        return PaymentBeanUtil.entity2DTO(this.paymentService.findById(paymentId));
    }

    @Override
    public void addItem(PaymentDTO pojo) throws DuplicateKeyException {
        KHDNEntity khdnEntity = new KHDNEntity();
        khdnEntity.setKHDNId(pojo.getKhdn().getKHDNId());

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderId(pojo.getOrder().getOrderId());

        UserEntity createdBy = new UserEntity();
        createdBy.setUserId(pojo.getCreatedBy().getUserId());

        PaymentEntity entity = new PaymentEntity();
        entity.setKhdn(khdnEntity);
        entity.setOrder(orderEntity);
        entity.setCreatedBy(createdBy);
        entity.setStatus(pojo.getStatus());
        entity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        entity = this.paymentService.save(entity);

        createHistory(entity, pojo);

        if (checkPaidFull(entity)){
            entity.setStatus(Constants.PAYMENT_STATUS_PAID);
            this.paymentService.update(entity);
        }
    }

    private void createHistory(PaymentEntity paymentEntity, PaymentDTO pojo) throws DuplicateKeyException{
        UserEntity createdBy = new UserEntity();
        createdBy.setUserId(pojo.getCreatedBy().getUserId());

        PaymentHistoryEntity entity = new PaymentHistoryEntity();
        entity.setPayment(paymentEntity);
        entity.setAmount(pojo.getAmount());
        entity.setCreatedBy(createdBy);

        if (pojo.getAmount() != null && pojo.getAmount().compareTo(0D) > 0){
            entity.setStatus(Constants.PAYMENT_HIS_UPDATE_PAYMENT);
        }else{
            entity.setStatus(Constants.PAYMENT_HIS_UPDATE_INFO);
        }
        entity.setCreatedDate(new Timestamp(System.currentTimeMillis()));

        this.paymentHistoryService.save(entity);
    }

    private boolean checkPaidFull(PaymentEntity paymentEntity){
        List<PaymentHistoryEntity> paymentHistoryEntityList = paymentEntity.getPaymentHistoryList();
        if (paymentHistoryEntityList != null && paymentHistoryEntityList.size() > 0){
            Double orderTotal = paymentEntity.getOrder().getQuantity().intValue() * paymentEntity.getOrder().getUnitPrice().doubleValue();
            Double totalPaidAmount = 0D;

            for (PaymentHistoryEntity paymentHistoryEntity : paymentHistoryEntityList){
                if (paymentHistoryEntity.getAmount() != null && paymentHistoryEntity.getAmount().compareTo(0D) > 0){
                    totalPaidAmount = totalPaidAmount.doubleValue() + paymentHistoryEntity.getAmount().doubleValue();
                }
            }

            if (orderTotal.compareTo(totalPaidAmount) == 0){
                return true;
            }
        }
        return false;
    }

    @Override
    public void updateItem(PaymentDTO pojo) throws ObjectNotFoundException, DuplicateKeyException {
        PaymentEntity paymentEntity = this.paymentService.findById(pojo.getPaymentId());
        createHistory(paymentEntity, pojo);

        if (checkPaidFull(paymentEntity)){
            paymentEntity.setStatus(Constants.PAYMENT_STATUS_PAID);
            this.paymentService.update(paymentEntity);
        }
    }
}
