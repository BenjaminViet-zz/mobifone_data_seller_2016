package com.benluck.vms.mobifonedataseller.core.business;

import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BranchMappingDTO;

import javax.ejb.DuplicateKeyException;
import javax.ejb.Local;
import javax.ejb.ObjectNotFoundException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 3/2/15
 * Time: 11:10 PM
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface BranchMappingManagementLocalBean {
    /**
     * Save or update branch mapping for Chuong Trinh and Chi Nhanh.
     * @param updatedByUserId
     * @param branchMappingList
     */
    void anhXaChiNhanh(Long updatedByUserId, List<BranchMappingDTO> branchMappingList)throws DuplicateKeyException;

    /**
     * Get list of BranchMapping that belong to the promotion by programId.
     * @param programId
     * @return
     */
    List<BranchMappingDTO> findByChuongTrinhId(Long programId);

    /**
     * Retrieve BranchMapping by branchId and programId.
     * @param branchId
     * @param programCode
     * @return BranchMappingDTO
     * @throws ObjectNotFoundException
     */
    BranchMappingDTO findByBranchIdAndProgramCode(Long branchId, String programCode) throws ObjectNotFoundException;
}
