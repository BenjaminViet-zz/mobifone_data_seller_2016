package com.benluck.vms.mobifonedataseller.core.business;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 11/7/14
 * Time: 12:51 PM
 * To change this template use File | Settings | File Templates.
 */
public interface TraCuuThongTinKPPManagementLocalBean {

    /**
     * Get information about Dealer by properties.
     * @param properties
     * @param firstItems
     * @param maxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] search(Map<String, Object> properties, Integer firstItems, Integer maxPageItems, String sortExpression, String sortDirection);
}
