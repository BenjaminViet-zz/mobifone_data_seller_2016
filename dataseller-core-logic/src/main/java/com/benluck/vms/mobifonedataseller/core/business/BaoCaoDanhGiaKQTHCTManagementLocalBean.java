package com.benluck.vms.mobifonedataseller.core.business;

import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BaoCaoDanhGiaKQTHCTChiTietTheoNgayDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BaoCaoDanhGiaKQTHCTSummaryDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015.BaoCaoDanhGiaKQTHCTChiTietTheoNgay_QStudent2015DTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015.BaoCaoDanhGiaKQTHCTSummary_QStudent2015DTO;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/30/14
 * Time: 4:41 PM
 * To change this template use File | Settings | File Templates.
 */
public interface BaoCaoDanhGiaKQTHCTManagementLocalBean {
    /**
     * Fetch for CT tich diem cuoc goi
     * @param properties
     * @param firstItem
     * @param maxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return BaoCaoDanhGiaKQTHCTSummaryDTO
     */
    BaoCaoDanhGiaKQTHCTSummaryDTO baoCaoDanhGiaKQTHCT_summary_tdcg(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection);

    /**
     * Fetch for CT Q-Student Q-Teen
     * @param properties
     * @param firstItem
     * @param maxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return BaoCaoDanhGiaKQTHCTSummary_QStudent2015DTO
     */
    BaoCaoDanhGiaKQTHCTSummary_QStudent2015DTO baoCaoDanhGiaKQTHCT_summary_qStudent(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection);

    /**
     * Fetch for CT tich diem cuoc goi
     * @param properties
     * @param firstItem
     * @param maxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return BaoCaoDanhGiaKQTHCTChiTietTheoNgayDTO
     */
    BaoCaoDanhGiaKQTHCTChiTietTheoNgayDTO baoCaoDanhGiaKQTHCT_theoNgay_tdcg(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection);

    /**
     * Fetch for CT Q-Student Q-Teen
     * @param properties
     * @param firstItem
     * @param maxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return BaoCaoDanhGiaKQTHCTChiTietTheoNgay_QStudent2015DTO
     */
    BaoCaoDanhGiaKQTHCTChiTietTheoNgay_QStudent2015DTO baoCaoDanhGiaKQTHCT_theoNgay_qStudent(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection);
}
