package com.benluck.vms.mobifonedataseller.core.business;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: HP
 * Date: 3/6/15
 * Time: 9:17 AM
 * To change this template use File | Settings | File Templates.
 */
public interface BaoCaoChiTietGoiKhuyenMai_TheoKhachHangManagementLocalBean {

    /**
     * Get data for report PromotionalPackageDetailsByCustomer_Agency.
     * @param properties
     * @param sortExpression
     * @param sortDirection
     * @param firstItem
     * @param maxPageItems
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] baoCaoChiTietGoiKhuyenMai_TheoKhachHang_TheoDiemBanHang(Map<String, Object> properties, String sortExpression, String sortDirection, int firstItem, int maxPageItems);

    /**
     * Get data for report PromotionalPackageDetailsByCustomer_District.
     * @param properties
     * @param sortExpression
     * @param sortDirection
     * @param firstItem
     * @param maxPageItems
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] baoCaoChiTietGoiKhuyenMai_TheoKhachHang_TheoQuanHuyen(Map<String, Object> properties, String sortExpression, String sortDirection, int firstItem, int maxPageItems);

    /**
     * Get data for report PromotionalPackageDetailsByCustomer_Branch.
     * @param properties
     * @param sortExpression
     * @param sortDirection
     * @param firstItem
     * @param maxPageItems
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] baoCaoChiTietGoiKhuyenMai_TheoKhachHang_TheoChiNhanh(Map<String, Object> properties, String sortExpression, String sortDirection, int firstItem, int maxPageItems);
}
