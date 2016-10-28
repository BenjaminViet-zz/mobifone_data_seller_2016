package com.benluck.vms.mobifonedataseller.core.business;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: vovanphuc0810
 * Date: 7/15/15
 * Time: 10:28 AM
 * To change this template use File | Settings | File Templates.
 */
public interface BaoCaoPhatTrienGoiManagementLocalBean {

    /**
     * Get data for report PackageReport.
     * @param properties
     * @param sortExpression
     * @param sortDirection
     * @param firstItem
     * @param reportMaxPageItems
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] baoCaoPhatTrienGoi(Map<String, Object> properties, String sortExpression, String sortDirection, Integer firstItem, Integer reportMaxPageItems);
}
