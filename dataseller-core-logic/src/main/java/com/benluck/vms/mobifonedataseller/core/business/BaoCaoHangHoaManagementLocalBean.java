package com.benluck.vms.mobifonedataseller.core.business;

import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BranchDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.DistrictDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.PromItemsDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.RetailDealerDTO;

import javax.ejb.ObjectNotFoundException;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/29/14
 * Time: 11:25 PM
 * To change this template use File | Settings | File Templates.
 */
public interface BaoCaoHangHoaManagementLocalBean {

    /**
     * Get data for report GoodsReportByAgency.
     * @param properties
     * @param firstItem
     * @param maxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] baoCaoHangHoaTheoDaiLy(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection);

    /**
     * Get data for report GoodsReportByDistrict.
     * @param properties
     * @param firstItem
     * @param maxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] baoCaoHangHoaTheoQuanHuyen(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection);

    /**
     * Get data for report GoodsReportByBranch.
     * @param properties
     * @param firstItem
     * @param maxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] baoCaoHangHoaTheoChiNhanh(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection);

    /**
     * Fetch all branches in the promotion.
     * @return A list of branches in DB.
     */
    List<BranchDTO> findAllBranches();

    /**
     * Retrieve branch by branchId.
     * @param branchId
     * @return BreanchDTO.
     * @throws ObjectNotFoundException
     */
    BranchDTO findBranchById(Long branchId) throws ObjectNotFoundException;

    /**
     * Get list all district by branchId.
     * @param branchId
     * @return A list of DistrictDTO.
     */
    List<DistrictDTO> findByBranchId(Long branchId);

    /**
     * Fetch all Dealer which belong to the branchId and districtId.
     * @param branch_Id
     * @param district_Id
     * @return A list of RetailDealerDTO
     */
    List<RetailDealerDTO> findRetailDealerList(Long branch_Id, Long district_Id);

    /**
     * Fetch all PromItems in DB.
     * @return A list of PromItemsDTO.
     */
    List<PromItemsDTO> findAllItems();
}
