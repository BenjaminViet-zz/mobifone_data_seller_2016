package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.beanUtil.PaymentHistoryBeanUtil;
import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.core.business.PaymentHistoryManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.PaymentHistoryDTO;
import com.benluck.vms.mobifonedataseller.domain.PaymentEntity;
import com.benluck.vms.mobifonedataseller.domain.PaymentHistoryEntity;
import com.benluck.vms.mobifonedataseller.domain.UserEntity;
import com.benluck.vms.mobifonedataseller.session.PaymentHistoryLocalBean;
import com.benluck.vms.mobifonedataseller.session.PaymentLocalBean;
import com.benluck.vms.mobifonedataseller.session.UserLocalBean;

import javax.ejb.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 5/25/17
 * Time: 23:28
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "PaymentHistoryManagementSessionEJB")
public class PaymentHistoryManagementSessionBean implements PaymentHistoryManagementLocalBean{

    @EJB
    private PaymentHistoryLocalBean paymentHistoryService;
    @EJB
    private PaymentLocalBean paymentService;
    @EJB
    private UserLocalBean userService;

    public PaymentHistoryManagementSessionBean() {
    }

    @Override
    public Object[] searchByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer firstItem, Integer reportMaxPageItems) {
        Object[] resultObject = this.paymentHistoryService.searchByCustomProperties(properties, sortExpression, sortDirection, firstItem, reportMaxPageItems);
        List<PaymentHistoryEntity> entityList = (List<PaymentHistoryEntity>)resultObject[1];

        if (entityList != null && entityList.size() > 0){
            resultObject[1] = PaymentHistoryBeanUtil.entityList2DTOList(entityList);
        }

        return resultObject;
    }

    @Override
    public PaymentHistoryDTO updateItem(PaymentHistoryDTO pojo) throws ObjectNotFoundException, DuplicateKeyException {
        PaymentHistoryEntity dbItem = this.paymentHistoryService.findById(pojo.getPaymentHistoryId());
        dbItem.setAmount(pojo.getAmount());
        dbItem.setPaymentDate(pojo.getPaymentDate());
        dbItem.setLastModifiedDate(new Timestamp(System.currentTimeMillis()));

        if (pojo.getAmount() != null && pojo.getAmount().compareTo(0D) > 0){
            dbItem.setStatus(Constants.PAYMENT_HIS_UPDATE_PAYMENT);
        }else{
            dbItem.setStatus(Constants.PAYMENT_HIS_UPDATE_INFO);
        }

        dbItem.setModifiedBy(this.userService.findById(pojo.getModifiedBy().getUserId()));
        dbItem = this.paymentHistoryService.update(dbItem);

        PaymentEntity paymentEntity = dbItem.getPayment();

        if (paymentEntity.getStatus().equals(Constants.PAYMENT_STATUS_PAID)){
            if(!checkPaidFull(dbItem.getPayment())){
                paymentEntity.setStatus(Constants.PAYMENT_STATUS_CREATED);
                this.paymentService.update(paymentEntity);
            }
        }else{
            if(checkPaidFull(dbItem.getPayment())){
                paymentEntity.setStatus(Constants.PAYMENT_STATUS_PAID);
                this.paymentService.update(paymentEntity);
            }
        }

        return PaymentHistoryBeanUtil.entity2DTO(dbItem);
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
    public void deleteItem(Long paymentHistoryId) throws ObjectNotFoundException, RemoveException {
        Integer historyLineCount = this.paymentHistoryService.countHistoryRecordLines(paymentHistoryId);

        if (historyLineCount.intValue() == 1){
            PaymentHistoryEntity paymentHistoryEntity = this.paymentHistoryService.findById(paymentHistoryId);
            PaymentEntity paymentEntity = paymentHistoryEntity.getPayment();
            this.paymentHistoryService.delete(paymentHistoryId);
            this.paymentService.delete(paymentEntity);
        }else{
            this.paymentHistoryService.delete(paymentHistoryId);
        }
    }

    @Override
    public PaymentHistoryDTO findById(Long paymentHistoryId) throws ObjectNotFoundException {
        return PaymentHistoryBeanUtil.entity2DTO(this.paymentHistoryService.findById(paymentHistoryId));
    }
}
