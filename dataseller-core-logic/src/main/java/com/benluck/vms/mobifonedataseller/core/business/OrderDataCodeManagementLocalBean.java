package com.benluck.vms.mobifonedataseller.core.business;

import com.benluck.vms.mobifonedataseller.core.dto.OrderDataCodeDTO;

import javax.ejb.Local;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/6/16
 * Time: 12:07
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface OrderDataCodeManagementLocalBean{

    List<OrderDataCodeDTO> fetchByOrderId(Long orderId);

    List<String> findListCardCodeByOrder(Long orderId);

    HashSet<String> findCardCodeImported4OldOrder();

    Object[] findByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer firstItem, Integer reportMaxPageItems);

    List<OrderDataCodeDTO> findByOrderId(Long orderId);
}
