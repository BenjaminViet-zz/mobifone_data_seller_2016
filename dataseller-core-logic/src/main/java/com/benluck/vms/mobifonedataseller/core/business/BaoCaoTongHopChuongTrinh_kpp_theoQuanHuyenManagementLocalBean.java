package com.benluck.vms.mobifonedataseller.core.business;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 11/16/14
 * Time: 10:17 AM
 * To change this template use File | Settings | File Templates.
 */
public interface BaoCaoTongHopChuongTrinh_kpp_theoQuanHuyenManagementLocalBean {

    /**
     * Get data for report PromotionSummary_KPP_byDistrict.
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
