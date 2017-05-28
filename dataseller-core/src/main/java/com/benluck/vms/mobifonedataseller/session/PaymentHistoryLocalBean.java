package com.benluck.vms.mobifonedataseller.session;

import com.benluck.vms.mobifonedataseller.domain.PaymentHistoryEntity;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 5/25/17
 * Time: 23:12
 * To change this template use File | Settings | File Templates.
 */
public interface PaymentHistoryLocalBean extends GenericSessionBean<PaymentHistoryEntity, Long>{
    void deleteByPaymentId(Long paymentId);

    Object[] searchByCustomProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer firstItem, Integer reportMaxPageItems);

    Integer countHistoryRecordLines(Long paymentHistoryId);
}
