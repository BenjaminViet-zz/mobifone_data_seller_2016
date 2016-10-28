package com.benluck.vms.mobifonedataseller.core.business;

import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.DistrictDTO;

import javax.ejb.ObjectNotFoundException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/26/14
 * Time: 10:48 PM
 * To change this template use File | Settings | File Templates.
 */
public interface DistrictManagementLocalBean {
    /**
     * Fetch for CT Tich Diem Cuoc Goi
     */
    List<DistrictDTO> findByBranchId_tdcg(Long branchId);

    /**
     * Fetch for CT Tich Diem Cuoc Goi
     * @return
     */
    List<DistrictDTO> findAll_tdcg();
    DistrictDTO findByDistrictId(Long districtId) throws ObjectNotFoundException;


    /**
     * Fetch for CT Thue Bao Phat Trien Moi
     */
    List<DistrictDTO> findByBranchId_tbptm(Long branchId);

    /**
     * Fetch for CT Thue Bao Phat Trien Moi
     * @return
     */
    List<DistrictDTO> findAll_tbptm();

    /**
     * Find all District by Chuong Trinh Code.
     * @return
     */
    List<DistrictDTO> findAllByChuongTrinhCode(String ctCode) throws ObjectNotFoundException;

    /**
     * Find District list by branchId and CTCode.
     * @param branchId
     * @param ctCode
     * @return
     * @throws ObjectNotFoundException
     */
    List<DistrictDTO> findByBranchIdAndCTCode(Long branchId, String ctCode) throws ObjectNotFoundException;

}
