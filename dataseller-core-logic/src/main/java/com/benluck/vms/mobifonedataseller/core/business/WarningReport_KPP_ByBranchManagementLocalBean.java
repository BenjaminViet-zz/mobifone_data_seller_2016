package com.benluck.vms.mobifonedataseller.core.business;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 11/20/14
 * Time: 5:34 PM
 * To change this template use File | Settings | File Templates.
 */
public interface WarningReport_KPP_ByBranchManagementLocalBean {

    /**
     * Query the report data base on properties and expression.
     * @param properties
     * @param firstItem
     * @param reportMaxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of objects with 2 element. The first one is total of records in Database with this filter.
     *          And the last one is a list of POJOs which comprise report data.
     */
    Object[] search(Map<String, Object> properties, Integer firstItem, Integer reportMaxPageItems, String sortExpression, String sortDirection);
}
