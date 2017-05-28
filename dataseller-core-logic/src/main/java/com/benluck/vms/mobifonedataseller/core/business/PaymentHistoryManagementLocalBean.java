package com.benluck.vms.mobifonedataseller.core.business;

import com.benluck.vms.mobifonedataseller.core.dto.PaymentHistoryDTO;

import javax.ejb.DuplicateKeyException;
import javax.ejb.Local;
import javax.ejb.ObjectNotFoundException;
import javax.ejb.RemoveException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 5/25/17
 * Time: 23:26
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface PaymentHistoryManagementLocalBean {
    Object[] searchByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer firstItem, Integer reportMaxPageItems);

    PaymentHistoryDTO updateItem(PaymentHistoryDTO pojo) throws ObjectNotFoundException, DuplicateKeyException;

    void deleteItem(Long paymentHistoryId) throws ObjectNotFoundException, RemoveException;

    PaymentHistoryDTO findById(Long paymentHistoryId) throws ObjectNotFoundException;
}
