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

    /**
     * Only fetch Orders which has ActiveStatus still alive
     * @param properties
     * @param sortExpression
     * @param sortDirection
     * @param offset
     * @param limitItems
     * @param whereClause
     * @return
     */
    Object[] searchByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer offset, Integer limitItems, String whereClause);

    OrderDTO findById(Long orderId) throws ObjectNotFoundException;

    OrderDTO addItem(OrderDTO pojo) throws DuplicateKeyException;

    void updateItem(OrderDTO pojo) throws ObjectNotFoundException, DuplicateKeyException;

    /**
     * Not delete Order, just update it to status 'DIE' and create an delete history for this.
     * @param orderId
     * @param modifiedByUserId
     * @throws ObjectNotFoundException
     * @throws DuplicateKeyException
     * @throws RemoveException
     */
    void deleteItem(Long orderId, Long modifiedByUserId) throws ObjectNotFoundException, DuplicateKeyException, RemoveException;
}
