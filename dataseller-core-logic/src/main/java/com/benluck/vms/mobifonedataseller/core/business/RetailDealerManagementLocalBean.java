package com.benluck.vms.mobifonedataseller.core.business;

import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.RetailDealerDTO;

import javax.ejb.ObjectNotFoundException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 11/7/14
 * Time: 12:44 AM
 * To change this template use File | Settings | File Templates.
 */
public interface RetailDealerManagementLocalBean {
    /**
     * fetch for CT Tich Diem Cuoc Goi
     * @return
     */
    List<RetailDealerDTO> findAll_tdcg();

    /**
     * fetch for CT Tich Diem Cuoc Goi
     * @param branchId
     * @return
     */
    List<RetailDealerDTO> findByBranchId_tdcg(Long branchId);

    /**
     * fetch for CT Tich Diem Cuoc Goi
     * @param district_Id
     * @return
     */
    List<RetailDealerDTO> findByDistrictId_tdcg(Long district_Id);

    /**
     * fetch for CT Tich Diem Cuoc Goi
     * @param dealer_Id
     * @return
     * @throws ObjectNotFoundException
     */
    RetailDealerDTO findById_tdcg(Long dealer_Id) throws ObjectNotFoundException;

    /**
     *  fetch for CT Thue Bao Phat Trien Moi
     */
    List<RetailDealerDTO> findAll_tbptm();

    /**
     * fetch for CT Thue Bao Phat Trien Moi
     * @param branchId
     * @return
     */
    List<RetailDealerDTO> findByBranchId_tbptm(Long branchId);

    /**
     * fetch for CT Thue Bao Phat Trien Moi
     * @param district_Id
     * @return
     */
    List<RetailDealerDTO> findByDistrictId_tbptm(Long district_Id);

    /**
     * fetch for CT Thue Bao Phat Trien Moi
     * @param branchId
     * @param district_Id
     * @return
     */
    List<RetailDealerDTO> findByDistrictIdAndByBranchId_tbptm(Long branchId, Long district_Id);

    /**
     * fetch for CT Thue Bao Phat Trien Moi
     * @param retailDealerDTO
     * @param sortExpression
     * @param sortDirection
     * @param firstItems
     * @param maxPageItems
     * @return
     */
    Object[] search_tbptm(RetailDealerDTO retailDealerDTO, String sortExpression, String sortDirection, Integer firstItems, Integer maxPageItems);

    /**
     * Find Retail Dealer list by ChuongTrinhCode.
     * @param ctCode
     * @return
     * @throws ObjectNotFoundException
     */
    List<RetailDealerDTO> findAllByCTCode(String ctCode) throws ObjectNotFoundException;

    /**
     * Find Retail Dealer list by branchId and CTCode.
     * @param branchId
     * @param ctCode
     * @return
     * @throws ObjectNotFoundException
     */
    List<RetailDealerDTO> findByBranchIdAndCTCode(Long branchId, String ctCode) throws ObjectNotFoundException;

    /**
     * Find Retail Dealer list by districtId and CTCode.
     * @param district_Id
     * @param ctCode
     * @return A list of RetailDealerDTO.
     * @throws ObjectNotFoundException
     */
    List<RetailDealerDTO> findByDistrictIdAndCTCode(Long district_Id, String ctCode)throws ObjectNotFoundException;

    /**
     * Retrieve Dealer by properties.
     * @param retailDealerDTO
     * @param ctCode
     * @return RetailDealerDTO
     * @throws ObjectNotFoundException
     */
    RetailDealerDTO findByEzAndCtCode_tbptm(RetailDealerDTO retailDealerDTO, String ctCode) throws ObjectNotFoundException;

    /**
     * Perform inserting new Dealer into DB.
     * @param listUsers
     */
    void saveImport(List<RetailDealerDTO> listUsers);

    /**
     *
     * @param dealer_Code
     * @param soEz
     * @return
     */
    Boolean checkIfDealerHaveDOC(String dealer_Code, String soEz);

    /**
     * FInd RetailDealer info by soEZ and CT Code.
     * @param soEZ
     * @param code
     * @return
     */
    RetailDealerDTO findBySoEZAndCTCode(String soEZ, String code);
}
