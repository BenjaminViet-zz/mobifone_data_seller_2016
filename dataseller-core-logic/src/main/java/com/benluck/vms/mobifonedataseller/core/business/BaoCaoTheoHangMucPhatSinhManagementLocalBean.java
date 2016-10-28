package com.benluck.vms.mobifonedataseller.core.business;

import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BranchDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.DistrictDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.PromItemsDTO;

import javax.ejb.ObjectNotFoundException;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/28/14
 * Time: 1:33 PM
 * To change this template use File | Settings | File Templates.
 */
public interface BaoCaoTheoHangMucPhatSinhManagementLocalBean {

    /**
     * Fetch all BranchDTO in DB.
     * @return A list of BranchDTO.
     */
    List<BranchDTO> findAllBranches();

    /**
     * Get district list by branchId.
     * @param branch_Id
     * @return A list of DistrictDTO.
     */
    List<DistrictDTO> findByBranchId(Long branch_Id);

    /**
     * Fetch all PromItems in DB.
     * @return A list of PromItemsDTO.
     */
    List<PromItemsDTO> findAllItems();

    /**
     * Get data for report ReportByPSItems.
     * @param properties
     * @param firstItem
     * @param maxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] baoCaoTheoHangMucPhatSinh(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection);

    /**
     * Retrieve Branch by branchId.
     * @param branchId
     * @return BranchDTO.
     * @throws ObjectNotFoundException
     */
    BranchDTO findBranchById(Long branchId) throws ObjectNotFoundException;
}
