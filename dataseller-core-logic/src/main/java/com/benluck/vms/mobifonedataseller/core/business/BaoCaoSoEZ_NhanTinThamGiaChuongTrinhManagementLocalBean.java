package com.benluck.vms.mobifonedataseller.core.business;

import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015.BaoCaoSoEZNhanTinThamGiaChuongTrinhDTO;

/**
 * Created with IntelliJ IDEA.
 * User: HP
 * Date: 3/3/15
 * Time: 2:28 PM
 * To change this template use File | Settings | File Templates.
 */
public interface BaoCaoSoEZ_NhanTinThamGiaChuongTrinhManagementLocalBean {

    /**
     * Get data for report EZMessageReport.
     * @param baoCaoSoEZNhanTinThamGiaChuongTrinhDTO
     * @param sortExpression
     * @param sortDirection
     * @param firstItems
     * @param maxPageItems
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] baoCaoSoEZ_NhanTinThamGiaChuongTrinh(BaoCaoSoEZNhanTinThamGiaChuongTrinhDTO baoCaoSoEZNhanTinThamGiaChuongTrinhDTO, String sortExpression, String sortDirection, Integer firstItems, Integer maxPageItems);
}
