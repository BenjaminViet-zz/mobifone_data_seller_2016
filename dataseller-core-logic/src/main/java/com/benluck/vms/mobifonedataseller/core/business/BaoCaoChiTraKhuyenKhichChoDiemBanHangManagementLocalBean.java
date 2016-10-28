package com.benluck.vms.mobifonedataseller.core.business;

import javax.ejb.ObjectNotFoundException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 3/5/15
 * Time: 11:35 PM
 * To change this template use File | Settings | File Templates.
 */
public interface BaoCaoChiTraKhuyenKhichChoDiemBanHangManagementLocalBean {

    /**
     * Get data for report EncouragementPaymentForAgencyByAgency.
     * @param properties
     * @param sortExpression
     * @param sortDirection
     * @param firstItem
     * @param maxPageItems
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     * @throws ObjectNotFoundException
     */
    Object[] baoCaoChiTraKhuyenKhichChoDiemBanHang_TheoDiemBanHang(Map<String, Object> properties, String sortExpression, String sortDirection, int firstItem, int maxPageItems) throws ObjectNotFoundException;

    /**
     * Get data for report EncouragementPaymentForAgencyByDistrict.
     * @param properties
     * @param sortExpression
     * @param sortDirection
     * @param firstItem
     * @param maxPageItems
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     * @throws ObjectNotFoundException
     */
    Object[] baoCaoChiTraKhuyenKhichChoDiemBanHang_TheoQuanHuyen(Map<String, Object> properties, String sortExpression, String sortDirection, int firstItem, int maxPageItems) throws ObjectNotFoundException;

    /**
     * Get data for report EncouragementPaymentForAgencyByBranch.
     * @param properties
     * @param sortExpression
     * @param sortDirection
     * @param firstItem
     * @param maxPageItems
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     * @throws ObjectNotFoundException
     */
    Object[] baoCaoChiTraKhuyenKhichChoDiemBanHang_theoChiNhanh(Map<String, Object> properties, String sortExpression, String sortDirection, int firstItem, int maxPageItems) throws ObjectNotFoundException;
}
