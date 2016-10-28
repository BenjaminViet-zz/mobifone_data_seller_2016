package com.benluck.vms.mobifonedataseller.core.business;

import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BranchDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.DistrictDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.RetailDealerDTO;

import javax.ejb.DuplicateKeyException;
import javax.ejb.ObjectNotFoundException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/25/14
 * Time: 10:24 PM
 * To change this template use File | Settings | File Templates.
 */
public interface BranchManagementLocalBean {
    Object[] search(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection);

    List<DistrictDTO> findByBranchId(Long branchId);

    /**
     * Fetch for CT Tich Diem Cuoc Goi
     * @return
     */
    List<BranchDTO> findAll_tdcg();

    /**
     * Fetch for CT Thue Bao Phat Trien Moi
     * @return
     */
    List<BranchDTO> findAll_tbptm();

    /**
     * Find branch list by ChuongTrinhId
     * @param chuongTrinhId
     * @return
     * @throws ObjectNotFoundException
     */
    List<BranchDTO> findByChuongTrinhId(Long chuongTrinhId) throws ObjectNotFoundException;

    /**
     * Perform money exchange at the MobiFone shop.
     * @param shopUserId
     * @param checkList
     * @throws ObjectNotFoundException
     * @throws DuplicateKeyException
     */
    void moneyExchangeAgency(Long shopUserId, String[] checkList) throws ObjectNotFoundException, DuplicateKeyException;

    /**
     * Perform payment at the MobiFone Shop.
     * @param exchangeUserId
     * @param checkList
     * @param paymentDate
     * @throws ObjectNotFoundException
     * @throws DuplicateKeyException
     */
    void paymentAgency(Long exchangeUserId, String[] checkList, Timestamp paymentDate) throws ObjectNotFoundException, DuplicateKeyException;

    /**
     * Get history of payment at Agency.
     * @param properties
     * @param firstItem
     * @param maxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] searchPaymentHistoryAtAgency(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection);

    /**
     * Fetch list of Dealer which belong to the branchId.
     * @param branchId
     * @return A list of RetailDealerDTO.
     */
    List<RetailDealerDTO> findRetailDealerListByBranchId(Long branchId);

    /**
     * Fetch list of Dealer which belong to the districtId.
     * @param district_Id
     * @return A list of RetailDealerDTO.
     */
    List<RetailDealerDTO> findRetailDealerListByDistrictId(Long district_Id);

    /**
     * Fetch for CT Tich Diem Cuoc Goi
     * @param branchId
     * @return
     * @throws ObjectNotFoundException
     */
    BranchDTO findBranchById_tdcg(Long branchId) throws ObjectNotFoundException;

    /**
     * Search agencies that joined to payment.
     * @param properties
     * @param firstItem
     * @param maxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] searchPaymentAgency(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection);

    /**
     * Find branch list by ChiNhanhId and Chuong Trinh Code.
     * @param ctCode
     * @return
     */
    List<BranchDTO> findByCTCode(String ctCode) throws ObjectNotFoundException;

    /**
     * Find Branch by ChiNhanhId and CTCode.
     * @param chiNhanhId
     * @param ctCode
     * @return
     * @throws ObjectNotFoundException
     */
    BranchDTO findByChiNhanhIdAndCTCode(Long chiNhanhId, String ctCode)throws ObjectNotFoundException;

    /**
     * Check if the dealer has paid by properties or not. If paid, return True, else False.
     * @param dealer_Id
     * @param sumDateList
     * @param soEZ
     * @return boolean
     */
    boolean checkPaymentStatusByDealerIdAndSumDate(Long dealer_Id, List<Date> sumDateList, String soEZ);

    /**
     * Get total of transaction that satisfied to pay.
     * @param dealer_Id
     * @param sumDateList
     * @param soEZ
     * @return Total of satisfied transactions.
     */
    Integer getTotalTrans_ThoaDKCT(Long dealer_Id, List<Date> sumDateList, String soEZ);

    /**
     * Retrieve branch by properties.
     * @param departmentId
     * @param code
     * @return BranchDTO
     * @throws ObjectNotFoundException
     */
    BranchDTO findByDepartmentIdAndCTCode(Long departmentId, String code) throws ObjectNotFoundException;

}
