package com.benluck.vms.mobifonedataseller.core.business;

import javax.ejb.Local;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/9/16
 * Time: 22:01
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface MBDCostManagementLocalBean {

    /**
     * Search genral report data
     * @param properties
     * @param sortExpression
     * @param sortDirection
     * @param firstItem
     * @param reportMaxPageItems
     * @return
     */
    Object[] searchGeneralReportDataByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer firstItem, Integer reportMaxPageItems);

    /**
     * Search detail report data
     * @param properties
     * @param sortExpression
     * @param sortDirection
     * @param firstItem
     * @param reportMaxPageItems
     * @return
     */
    Object[] searchDetailReportDataByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer firstItem, Integer reportMaxPageItems);

}
