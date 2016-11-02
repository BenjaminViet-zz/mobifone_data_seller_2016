package com.benluck.vms.mobifonedataseller.core.business;

import com.benluck.vms.mobifonedataseller.core.dto.OrderDTO;

import javax.ejb.DuplicateKeyException;
import javax.ejb.Local;
import javax.ejb.ObjectNotFoundException;
import javax.ejb.RemoveException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/2/16
 * Time: 07:10
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface OrderManagementLocalBean {

    Object[] searchByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer offset, Integer limitItems);

    OrderDTO findById(Long orderId) throws ObjectNotFoundException;

    void addItem(OrderDTO pojo) throws DuplicateKeyException;

    void updateItem(OrderDTO pojo) throws ObjectNotFoundException, DuplicateKeyException;

    void deleteItem(Long orderId) throws ObjectNotFoundException, DuplicateKeyException, RemoveException;
}
