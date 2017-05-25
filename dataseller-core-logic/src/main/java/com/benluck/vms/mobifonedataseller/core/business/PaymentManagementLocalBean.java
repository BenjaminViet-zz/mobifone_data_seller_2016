package com.benluck.vms.mobifonedataseller.core.business;

import com.benluck.vms.mobifonedataseller.core.dto.PaymentDTO;

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
public interface PaymentManagementLocalBean {
    Object[] searchByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer firstItem, Integer maxPageItems);

    void deleteItem(Long paymentId) throws RemoveException;

    PaymentDTO findById(Long paymentId) throws ObjectNotFoundException;

    void addItem(PaymentDTO pojo) throws DuplicateKeyException;

    void updateItem(PaymentDTO pojo) throws ObjectNotFoundException, DuplicateKeyException;
}
