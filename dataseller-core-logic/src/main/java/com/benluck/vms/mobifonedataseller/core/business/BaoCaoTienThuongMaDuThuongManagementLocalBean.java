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
 * Time: 9:38 PM
 * To change this template use File | Settings | File Templates.
 */
public interface BaoCaoTienThuongMaDuThuongManagementLocalBean {

    /**
     * Fetch all branches in the promotion.
     * @return A list of BranchDTO.
     */
    List<BranchDTO> findAllBranches();

    /**
     * Fetch all district which belong the branchId.
     * @param branch_Id
     * @return A list of DistrictDTO.
     */
    List<DistrictDTO> findByBranchId(Long branch_Id);

    /**
     * Fetch all Dealers which belong the the branchId and districtId.
     * @param branch_Id
     * @param district_Id
     * @return A list of RetailDealerDTO.
     */
    List<RetailDealerDTO> findRetailDealerList(Long branch_Id, Long district_Id);

    /**
     * Fetch all PromItems in DB.
     * @return A list of PromItemsDTO.
     */
    List<PromItemsDTO> findAllItems();

    /**
     * Get data for report BonusAndWinningTicketByAgency.
     * @param properties
     * @param firstItem
     * @param maxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] baoCaoTienThuongMaDuThuongTheoDaiLy(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection);

    /**
     * Get data for report BonusAndWinningTicketByDistrict.
     * @param properties
     * @param firstItem
     * @param maxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] baoCaoTienThuongMaDuThuongTheoQuanHuyen(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection);

    /**
     * Get data for report BonusAndWinningTicketByBranch.
     * @param properties
     * @param firstItem
     * @param maxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] baoCaoTienThuongMaDuThuongTheoChiNhanh(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection);

    /**
     * Retrieve branch by branchId.
     * @param branchId
     * @return BranchDTO.
     * @throws ObjectNotFoundException
     */
    BranchDTO findBranchById(Long branchId)throws ObjectNotFoundException;
}
