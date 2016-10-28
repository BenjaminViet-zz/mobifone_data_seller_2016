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
 * Date: 10/28/14
 * Time: 9:42 AM
 * To change this template use File | Settings | File Templates.
 */
public interface BaoCaoDanhSachMaThuongManagementLocalBean {

    /**
     * Get data for report WinningCodeList.
     * @param properties
     * @param firstItem
     * @param maxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return
     */
    Object[] getWinningCodeList(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection);

    /**
     * Get all branches in the promotion.
     * @return A list of BranchDTO.
     */
    List<BranchDTO> findAllBranches();

    /**
     * Get list of District by branchId.
     * @param branch_Id
     * @return A list of DistrictDTO.
     */
    List<DistrictDTO> findByBranchId(Long branch_Id);

    /**
     * Get list of Dealer by properties.
     * @param branch_Id
     * @param district_Id
     * @return A list of matched RetailDealerDTO.
     */
    List<RetailDealerDTO> findRetailDealerList(Long branch_Id, Long district_Id);

    /**
     * Find all PromItems in DB.
     * @return A list of PromItems.
     */
    List<PromItemsDTO> findAllItems();

    /**
     * Retrieve branch by branchId.
     * @param branchId
     * @return BranchDTO
     * @throws ObjectNotFoundException
     */
    BranchDTO findBranchById(Long branchId)throws ObjectNotFoundException;
}
