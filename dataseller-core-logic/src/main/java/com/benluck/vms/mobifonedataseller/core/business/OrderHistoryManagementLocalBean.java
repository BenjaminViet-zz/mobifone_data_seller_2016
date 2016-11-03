package com.benluck.vms.mobifonedataseller.core.business;

import javax.ejb.Local;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/2/16
 * Time: 22:07
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface OrderHistoryManagementLocalBean {

    Object[] searchByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer offset, Integer limitItems);
}
