package com.benluck.vms.mobifonedataseller.session;

import com.benluck.vms.mobifonedataseller.domain.MBDCostEntity;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/9/16
 * Time: 22:02
 * To change this template use File | Settings | File Templates.
 */
public interface MBDCostLocalBean extends GenericSessionBean<MBDCostEntity, Long>{

    Object[] search4GeneralExpenseReport(Map<String, Object> properties, String sortExpression, String sortDirection, Integer firstItem, Integer maxPageItems);

    Object[] search4DetailExpenseReport(Map<String, Object> properties, String sortExpression, String sortDirection, Integer firstItem, Integer maxPageItems);

    Object[] searchPaymentListByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer firstItem, Integer reportMaxPageItems);
}
