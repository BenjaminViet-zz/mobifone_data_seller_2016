package com.benluck.vms.mobifonedataseller.core.business;

import javax.ejb.Local;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/11/16
 * Time: 19:43
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface CodeHistoryManagementLocalBean {

    Double calculateTotalPaidPackageValue(String isdn, Long orderId);

    Object[] searchPaymentHistoryByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer firstItem, Integer reportMaxPageItems);
}
