package com.benluck.vms.mobifonedataseller.core.business;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: HP
 * Date: 3/4/15
 * Time: 10:25 AM
 * To change this template use File | Settings | File Templates.
 */
public interface BaoCaoThongKeThueBaoThamGiaGoiCuocManagementLocalBean {

    /**
     * Get data for report PackageSubscriberStatisticByAgency.
     * @param properties
     * @param sortExpression
     * @param sortDirection
     * @param firstItems
     * @param maxPageItems
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] baoCaoThongKeThueBaoThamGiaGoiCuoc_TheoDiemBanHang(Map<String, Object> properties, String sortExpression, String sortDirection, Integer firstItems, Integer maxPageItems);

    /**
     * Get data for report PackageSubscriberStatisticByDistrict.
     * @param properties
     * @param sortExpression
     * @param sortDirection
     * @param firstItems
     * @param maxPageItems
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] baoCaoThongKeThueBaoThamGiaGoiCuoc_TheoQuanHuyen(Map<String, Object> properties, String sortExpression, String sortDirection, Integer firstItems, Integer maxPageItems);

    /**
     * Get data for report PackageSubscriberStatisticByBranch.
     * @param properties
     * @param sortExpression
     * @param sortDirection
     * @param firstItems
     * @param maxPageItems
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] baoCaoThongKeThueBaoThamGiaGoiCuoc_TheoChiNhanh(Map<String, Object> properties, String sortExpression, String sortDirection, Integer firstItems, Integer maxPageItems);
}
