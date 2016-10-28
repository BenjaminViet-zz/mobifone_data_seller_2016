package com.benluck.vms.mobifonedataseller.core.business;

import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.*;

import javax.ejb.DuplicateKeyException;
import javax.ejb.ObjectNotFoundException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/21/14
 * Time: 10:04 PM
 * To change this template use File | Settings | File Templates.
 */
public interface KPPManagementLocalBean {

    /**
     * Verify thw phone number to make sure has registered in the promotion.
     * @param phoneNumber
     * @return Boolean
     * @throws ObjectNotFoundException
     */
    Boolean checkIfAlreadyRegistered(String phoneNumber)throws ObjectNotFoundException;

    /**
     * Get score by date.
     * @param month
     * @param item_Id
     * @param phoneNumber
     * @param fromDate
     * @param toDate
     * @param offset
     * @param limit
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] getScoreByDate(Integer month, Long item_Id, String phoneNumber, Timestamp fromDate, Timestamp toDate, Integer offset, Integer limit, String sortExpression, String sortDirection);

    /**
     * Get score by date.
     * @param month
     * @param item_Id
     * @param phoneNumber
     * @param fromDate
     * @param toDate
     * @param offset
     * @param limit
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] getScoreByDateNew(Integer month, Long item_Id, String phoneNumber, Timestamp fromDate, Timestamp toDate, Integer offset, Integer limit, String sortExpression, String sortDirection);

    /**
     * Get current score of the user by phone number.
     * @param phoneNumber
     * @return
     */
    Integer getCurrentScore(String phoneNumber);

    /**
     * Get current total score of winning ticket for KPP.
     * @param phoneNumber
     * @return
     */
    Integer getCurrentTotalScoreWinningTicketKPP(String phoneNumber);

    /**
     * Get list of winning tickets of the promotion.
     * @param phoneNumber
     * @param firstItem
     * @param maxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] searchWinningTicketList(String phoneNumber, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection);

    /**
     * Generate information
     * @param phoneNumber
     * @return
     * @throws ObjectNotFoundException
     */
    String generateInfo4Agency(String phoneNumber) throws ObjectNotFoundException;

    List<PromItemsDTO> findAllItems();

    CTTichDiemMaDuThuongDTO getThongTinMaTrungThuong(String thue_bao, String maDuThuong) throws ObjectNotFoundException;

    Object[] xemChiTietHangMucPhatSinh(Boolean isAdmin, Long dealer_Id, Long item_Id, Timestamp fromDateTime, Timestamp toDateTime, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection);

    Object[] traCuuDanhSachTrungThuongKPP(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection);

    List<DealerAccountActionDTO> traCuuPhatSinhTheoThangNew(String thue_bao, Integer thangTichDiem);

    RetailDealerDTO findRetailDealerByThueBao(String thue_bao) throws ObjectNotFoundException;

    List<KppGiftDTO> findAllKppGifts();

    Object[] traCuuChiTietTrangThaiKy(Long dealer_Id, Integer cycle);

    Object[] getWarningReportData(String soEZ, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection);
}
