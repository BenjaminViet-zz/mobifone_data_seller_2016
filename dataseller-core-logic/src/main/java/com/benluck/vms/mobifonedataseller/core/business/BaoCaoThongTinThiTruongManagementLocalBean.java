package com.benluck.vms.mobifonedataseller.core.business;


import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BranchDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.DistrictDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.RetailDealerDTO;

import javax.ejb.ObjectNotFoundException;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: benluck
 * Date: 10/4/14
 * Time: 3:54 PM
 * To change this template use File | Settings | File Templates.
 */
public interface BaoCaoThongTinThiTruongManagementLocalBean {

    /**
     * Fetch all branches in the promotion.
     * @return A list of BranchDTO.
     */
    List<BranchDTO> findAllBranches();

    /**
     * Get list of Dealer by properties.
     * @param branchId
     * @param districtId
     * @return A list of RetailDealerDTO.
      */
    List<RetailDealerDTO> findRetailDealerList(Long branchId, Long districtId);

    /**
     * Get list of District which belong to the branchId.
     * @param branchId
     * @return A list of DistrictDTO.
     */
    List<DistrictDTO> findByBranchId(Long branchId);

    /**
     * Get data for report MarketInfoReportByAgency.
     * @param properties
     * @param firstItem
     * @param maxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] baoCaoThongTinThiTruongTheoDaiLy(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection);

    /**
     * Get data for report MarketInfoReportByDistrict.
     * @param properties
     * @param firstItem
     * @param maxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] baoCaoThongTinThiTruongTheoQuanHuyen(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection);

    /**
     * Get data for report MarketInfoReportByBranch.
     * @param properties
     * @param firstItem
     * @param maxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] baoCaoThongTinThiTruongTheoChiNhanh(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection);

    /**
     * Retrieve branch by branchId
     * @param branchId
     * @return BranchDTO
     * @throws ObjectNotFoundException
     */
    BranchDTO findBranchById(Long branchId) throws ObjectNotFoundException;
}
