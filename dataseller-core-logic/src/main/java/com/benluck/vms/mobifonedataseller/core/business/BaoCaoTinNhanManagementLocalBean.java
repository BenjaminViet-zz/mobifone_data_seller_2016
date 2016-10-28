package com.benluck.vms.mobifonedataseller.core.business;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: HP
 * Date: 3/5/15
 * Time: 11:53 PM
 * To change this template use File | Settings | File Templates.
 */
public interface BaoCaoTinNhanManagementLocalBean {

    /**
     * Get data for report MessageReportByAgency.
     * @param properties
     * @param sortExpression
     * @param sortDirection
     * @param firstItem
     * @param maxPageItems
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] baoCaoTinNhan_TheoDiemBanHang(Map<String, Object> properties, String sortExpression, String sortDirection, int firstItem, int maxPageItems);

    /**
     * Get data for report MessageReportByDistrict.
     * @param properties
     * @param sortExpression
     * @param sortDirection
     * @param firstItem
     * @param maxPageItems
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] baoCaoTinNhan_TheoQuanHuyen(Map<String, Object> properties, String sortExpression, String sortDirection, int firstItem, int maxPageItems);

    /**
     * Get data for report MessageReportByBranch.
     * @param properties
     * @param sortExpression
     * @param sortDirection
     * @param firstItem
     * @param maxPageItems
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] baoCaoTinNhan_TheoChiNhanh(Map<String, Object> properties, String sortExpression, String sortDirection, int firstItem, int maxPageItems);
}
