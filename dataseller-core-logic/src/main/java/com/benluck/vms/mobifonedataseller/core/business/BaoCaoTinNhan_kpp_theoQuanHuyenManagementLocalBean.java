package com.benluck.vms.mobifonedataseller.core.business;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 11/18/14
 * Time: 10:01 PM
 * To change this template use File | Settings | File Templates.
 */
public interface BaoCaoTinNhan_kpp_theoQuanHuyenManagementLocalBean {

    /**
     * Get data for report Message_KPP_byDistrict Report.
     * @param properties
     * @param firstItem
     * @param reportMaxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] search(Map<String, Object> properties, Integer firstItem, Integer reportMaxPageItems, String sortExpression, String sortDirection);
}
