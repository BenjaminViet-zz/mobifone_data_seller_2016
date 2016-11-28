package com.benluck.vms.mobifonedataseller.session;

import com.benluck.vms.mobifonedataseller.domain.OrderDataCodeEntity;

import java.util.HashSet;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/6/16
 * Time: 12:09
 * To change this template use File | Settings | File Templates.
 */
public interface OrderDataCodeLocalBean extends GenericSessionBean<OrderDataCodeEntity, Long>{

    Integer countTotal(Integer year, String prefixCardCode);

    List<String> findListCardCodeByOrder(Long orderId);

    HashSet<String> findCardCodeImported4OldOrder();

}
