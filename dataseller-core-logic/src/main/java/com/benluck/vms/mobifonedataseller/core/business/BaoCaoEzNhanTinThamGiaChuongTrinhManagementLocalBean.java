package com.benluck.vms.mobifonedataseller.core.business;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: HP
 * Date: 3/18/15
 * Time: 9:41 AM
 * To change this template use File | Settings | File Templates.
 */
public interface BaoCaoEzNhanTinThamGiaChuongTrinhManagementLocalBean {

    /**
     * Get data for report EZMessageRegister.
     * @param properties
     * @param sortExpression
     * @param sortDirection
     * @param firstItem
     * @param maxPageItems
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] baoCaoEzNhanTinThamGiaChuongTrinh(Map<String, Object> properties, String sortExpression, String sortDirection, int firstItem, int maxPageItems);
}
