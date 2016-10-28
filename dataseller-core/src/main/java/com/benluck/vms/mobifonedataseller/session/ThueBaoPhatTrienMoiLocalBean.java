package com.benluck.vms.mobifonedataseller.session;

import javax.ejb.Local;
import javax.ejb.ObjectNotFoundException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: HP
 * Date: 2/11/15
 * Time: 5:30 PM
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface ThueBaoPhatTrienMoiLocalBean{

    /**
     * Verify thw phone number to make sure has registered in the promotion.
     * @param thue_bao
     * @return Boolean
     * @throws ObjectNotFoundException
     */
    Boolean checkIfAlreadyRegister4KPP(String thue_bao)throws ObjectNotFoundException;

    /**
     * Search data for registration details by properties.
     * @param properties
     * @param firstItem
     * @param maxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] searchKetQuaThueBaoThamGiaGoiCuoc(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection);

    /**
     * Get all PromPackage from DB
     * @return List of Objects.
     */
    List findAllPromPackage();

    /**
     * Perform updating status and date of payment.
     * @param dealer_Id
     * @param sumDateList
     * @param paymentDate
     * @param soEZ
     */
    void updatePaymentStatus(Long dealer_Id, List<Date> sumDateList, Timestamp paymentDate, String soEZ);

    /**
     * Fetch all branches in the promotion.
     * @return A list of branches in the promotion.
     */
    List findAllBranches();

    /**
     * Fetch all districts in the promotion.
     * @return A list of district
     */
    List findAllDistricts();

    /**
     * Fetch all Dealer in the promotion
     * @return A list of Dealer.
     */
    List findAllRetailDealers();

    /**
     * Find Dealer list by branchId
     * @param branchId
     * @return A list of RetailDealer that belong to the branchId.
     */
    List findRetailDealerListByBranchId(Long branchId);

    /**
     * Find Dealer list by districtId
     * @param district_Id
     * @return A list of RetailDealer that belong to the districtId.
     */
    List findRetailDealerListByDistrictId(Long district_Id);

    /**
     * Find Dealer list by branchId and districtId.
     * @param branchId
     * @param districtId
     * @return A list of RetailDealer that belong to the branchId and districtId.
     */
    List findByDistrictIdAndByBranchId(Long branchId, Long districtId);

    /**
     * Find district list by branchId
     * @param branchId
     * @return A list District that belong to the branchId.
     */
    List findDistrictListByBranchId(Long branchId);

    /**
     * Find all dealer that matched with properties.
     * @param properties
     * @param firstItem
     * @param maxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] searchDealerList(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection);

    /**
     *
     * @param properties
     * @param firstItem
     * @param maxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] searchResultOfExchangedUsers(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection);

    /**
     *
     * @param thue_bao
     * @param sortExpression
     * @param sortDirection
     * @param firstItems
     * @param maxPageItems
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] searchUserInfo(String thue_bao, String sortExpression, String sortDirection, Integer firstItems, Integer maxPageItems);

    /**
     * Get data for report EZMessageReport.
     * @param properties
     * @param firstItem
     * @param maxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] BaoCaoSoEZ_NhanTinThamGiaChuongTrinh(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection);

    /**
     * Get data for report PackageSubscriberStatisticByAgency.
     * @param properties
     * @param sortExpression
     * @param sortDirection
     * @param firstItem
     * @param maxPageItems
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] baoCaoThongKeThueBaoThamGiaGoiCuoc_TheoDiemBanHang(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection);

    /**
     * Get data for report PackageSubscriberStatisticByDistrict.
     * @param properties
     * @param sortExpression
     * @param sortDirection
     * @param firstItem
     * @param maxPageItems
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] baoCaoThongKeThueBaoThamGiaGoiCuoc_TheoQuanHuyen(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection);

    /**
     * Get data for report PackageSubscriberStatisticByBranch.
     * @param properties
     * @param sortExpression
     * @param sortDirection
     * @param firstItem
     * @param maxPageItems
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     * @return
     */
    Object[] baoCaoThongKeThueBaoThamGiaGoiCuoc_TheoChiNhanh(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection);

    /**
     * Get data for report PromotionSummaryByAgencyReport
     * @param properties
     * @param firstItem
     * @param maxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] baoCaoTongHopKetQuaChuongTrinh_TheoDiemBanHang(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection);

    /**
     * Get data for report PromotionSummaryByDistrictReport
     * @param properties
     * @param firstItem
     * @param maxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] baoCaoTongHopKetQuaChuongTrinh_TheoQuanHuyen(Map<String, Object> properties, int firstItem, int maxPageItems, String sortExpression, String sortDirection);

    /**
     * Get data for report PromotionSummaryByBranchReport
     * @param properties
     * @param firstItem
     * @param maxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] baoCaoTongHopKetQuaChuongTrinh_TheoChiNhanh(Map<String, Object> properties, int firstItem, int maxPageItems, String sortExpression, String sortDirection);

    /**
     * Get data for report EncouragementPaymentForAgencyByAgency.
     * @param properties
     * @param firstItem
     * @param maxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     * @throws ObjectNotFoundException
     */
    Object[] baoCaoChiTraKhuyenKhichChoDiemBanHang_TheoDiemBanHang(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) throws ObjectNotFoundException;

    /**
     * Get data for report EncouragementPaymentForAgencyByDistrict.
     * @param properties
     * @param firstItem
     * @param maxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     * @throws ObjectNotFoundException
     */
    Object[] baoCaoChiTraKhuyenKhichChoDiemBanHang_TheoQuanHuyen(Map<String, Object> properties, int firstItem, int maxPageItems, String sortExpression, String sortDirection) throws ObjectNotFoundException;

    /**
     * Get data for report EncouragementPaymentForAgencyByBranch.
     * @param properties
     * @param firstItem
     * @param maxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     * @throws ObjectNotFoundException
     */
    Object[] baoCaoChiTraKhuyenKhichChoDiemBanHang_TheoChiNhanh(Map<String, Object> properties, int firstItem, int maxPageItems, String sortExpression, String sortDirection) throws ObjectNotFoundException;

    /**
     * Get data for report MessageReportByAgency.
     * @param properties
     * @param firstItem
     * @param maxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] baoCaoTinNhan_TheoDiemBanHang(Map<String, Object> properties, int firstItem, int maxPageItems, String sortExpression, String sortDirection);

    /**
     * Get data for report MessageReportByDistrict.
     * @param properties
     * @param firstItem
     * @param maxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] baoCaoTinNhan_TheoQuanHuyen(Map<String, Object> properties, int firstItem, int maxPageItems, String sortExpression, String sortDirection);

    /**
     * Get data for report MessageReportByBranch.
     * @param properties
     * @param firstItem
     * @param maxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] baoCaoTinNhan_TheoChiNhanh(Map<String, Object> properties, int firstItem, int maxPageItems, String sortExpression, String sortDirection);

    /**
     * Get data for report PromotionalPackageDetailsByCustomer_Agency.
     * @param properties
     * @param firstItem
     * @param maxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] baoCaoChiTietGoiKhuyenMai_TheoKhachHang_TheoDiemBanHang(Map<String, Object> properties, int firstItem, int maxPageItems, String sortExpression, String sortDirection);

    /**
     * Get data for report PromotionalPackageDetailsByCustomer_District.
     * @param properties
     * @param firstItem
     * @param maxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] baoCaoChiTietGoiKhuyenMai_TheoKhachHang_TheoQuanHuyen(Map<String, Object> properties, int firstItem, int maxPageItems, String sortExpression, String sortDirection);

    /**
     * Get data for report PromotionalPackageDetailsByCustomer_Branch.
     * @param properties
     * @param firstItem
     * @param maxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] baoCaoChiTietGoiKhuyenMai_TheoKhachHang_TheoChiNhanh(Map<String, Object> properties, int firstItem, int maxPageItems, String sortExpression, String sortDirection);

    /**
     * Get data for report PromotionalPackageDetailsByCodeName_Agency.
     * @param properties
     * @param firstItem
     * @param maxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] baoCaoChiTietGoiKhuyenMai_TheoMaGoi_TenGoi_TheoDiemBanHang(Map<String, Object> properties, int firstItem, int maxPageItems, String sortExpression, String sortDirection);

    /**
     * Get data for report PromotionalPackageDetailsByCodeName_District.
     * @param properties
     * @param firstItem
     * @param maxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] baoCaoChiTietGoiKhuyenMai_TheoMaGoi_TenGoi_TheoQuanHuyen(Map<String, Object> properties, int firstItem, int maxPageItems, String sortExpression, String sortDirection);

    /**
     * Get data for report PromotionalPackageDetailsByCodeName_Branch.
     * @param properties
     * @param firstItem
     * @param maxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return
     */
    Object[] baoCaoChiTietGoiKhuyenMai_TheoMaGoi_TenGoi_TheoChiNhanh(Map<String, Object> properties, int firstItem, int maxPageItems, String sortExpression, String sortDirection);

    /**
     * Return true if this Dealer has been signed. Else return false.
     * @param dealerId
     * @param soEZ
     * @return
     */
    Boolean checkIfDealerHaveDOC(Long dealerId, String soEZ);

    /**
     * Save new data for Dealer which signed to conract.
     * @param dealerId
     * @param soEz
     * @param dealer_code
     */
    void saveImportDealerSigned(Long dealerId, String soEz, String dealer_code);

    /**
     * Update imported date for Dealer which signed to contract.
     * @param dealerId
     * @param soEz
     * @param dealer_code
     */
    void updateDealerSigned(Long dealerId, String soEz, String dealer_code);

    /**
     * Get data for report EZMessageRegister.
     * @param properties
     * @param sortExpression
     * @param sortDirection
     * @param firstItem
     * @param maxPageItems
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] baoCaoEzNhanTinThamGiaChuongTrinh(Map<String, Object> properties, int firstItem, int maxPageItems, String sortExpression, String sortDirection);

    /**
     * Check if the dealer has paid by properties or not. If paid, return True, else False.
     * @param dealer_Id
     * @param sumDateList
     * @param soEZ
     * @return Boolean
     */
    Boolean checkPaymentStatusByDealerIdAndSumDate(Long dealer_Id, List<Date> sumDateList, String soEZ);

    /**
     * Check if the dealer which is either imported or not by soEZ and dealer_Code.
     * @param soEZ
     * @param dealer_Code
     * @return
     */
    Boolean checkIfHaveDocBySoEZ(String soEZ, String dealer_Code);

    /**
     * Perform updating importing date of a dealer by properties.
     * @param ez_isdn
     * @param dealer_code
     */
    void updateDealerSignedBySoEZAndDealerCode(String ez_isdn, String dealer_code);

    /**
     * Get total of transaction that satisfied to pay.
     * @return Total of satisfied transactions.
     */
    Integer getTotalTrans_ThoaDKCT(Long dealer_id, List<Date> sumDateList, String soEZ);

    /**
     * Get total of score related to the phone number parameter in the promotion Q-Teen and Q-Student.
     * @param phoneNumber
     * @return
     */
    Integer getCurrentPointTotal(String phoneNumber);

    /**
     * Count number of exchanged Ma Phieu in the promotion.
     * @param phoneNumber
     * @return A number of Ma Phieu that exchanged for gift.
     */
    Integer countExchangedMaPhieus(String phoneNumber);

    /**
     * Search information of the subscriber.
     * @param phoneNumber
     * @param firstItem
     * @param maxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return A list of information of the subscriber.
     */
    Object[] searchSubscriberInfo(String phoneNumber, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection);

    Object[] statisticCumulativeScoresByMonth(String phoneNumber);

    Object[] statisticCumulativeScoresByDate(String phoneNumber, String whereClause, Integer offset, Integer limit);

    Object[] searchByMaPhieu(String maPhieu, Integer exchangedStatus, Integer offset, Integer limit, String sortExpression, String sortDirection);

    Object[] searchByThueBao(String phoneNumber, Integer exchangedStatus, Integer offset, Integer limit, String sortExpression, String sortDirection);

    /**
     * Get list of Ma Phieu by properties.
     * @param phoneNumber
     * @param offset
     * @param limit
     * @param sortExpression
     * @param sortDirection
     * @return An array of Objects with 2 elements. First one is total of records in DB and the last one is a list of POJOs.
     */
    Object[] searchMaPhieuListByProperties(String phoneNumber, Integer offset, Integer limit, String sortExpression, String sortDirection);

    /**
     * Update the status of Ma Phieu for the promotion Tich Diem Cuoc Goi Nhan Voucher.
     * @param phoneNumber
     */
    void updateMaphieuStatus_tdcg(String phoneNumber);

    /**
     * Check User is either registered or not.
     * @param mobifoneNumber
     * @return true if the User has registered already to this promotion, else for not yet.
     */
    Boolean checkIfAlreadyRegister(String mobifoneNumber)throws ObjectNotFoundException;

    /**
     * Get report data of report Message Management.
     * @param loaiTinNhanFromKHCN a type of message from KHCN
     * @param loaiTinNhanFromVMS a type of message from VMS (MobiFone)
     * @param fromDateTime
     * @param toDateTime
     * @return A list of POJOs.
     */
    List getMessageManagementReportData(String loaiTinNhanFromKHCN, String loaiTinNhanFromVMS, Timestamp fromDateTime, Timestamp toDateTime);

    /**
     * Get report data of report
     * @param fromDateTimeThamGia
     * @param toDateTimeThamGia
     * @return
     */
    Long baoCaoDanhGiaKetQuaTHCT_tieuChi1(Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia);

    Long baoCaoDanhGiaKetQuaTHCT_tieuChi2(Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia);

    List baoCaoDanhGiaKetQuaTHCT_tieuChi3And4Summary(Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia);

    List baoCaoDanhGiaKetQuaTHCT_tieuChi5And6Summary(Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia, Timestamp fromDateTimeReportFilter, Timestamp toDateTimeReportFilter);

    List baoCaoDanhGiaKetQuaTHCT_tieuChi7(Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia);

    List baoCaoDanhGiaKetQuaTHCT_tieuChi8(Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia);

    Long baoCaoDanhGiaKetQuaTHCT_tieuChi9(Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia);

    Long baoCaoDanhGiaKetQuaTHCT_tieuChi10(Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia);

    List baoCaoDanhGiaKetQuaTHCT_tieuChi3And4TheoNgay(Long branchId, Long districtId, Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia);

    List baoCaoDanhGiaKetQuaTHCT_tieuChi5And6TheoNgay(Long branchId, Long districtId, Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia, Timestamp fromDateTimeReportFilter, Timestamp toDateTimeReportFilter);

    /**
     * Get exchanged Ma Phieu list from list of Ma Phieu paramater.
     * @param maPhieuList
     * @return
     */
    String[] getExchangedMaPhieuListByList(String[] maPhieuList);

    void shopUserGiaoQua(List<String> dsMaPhieus, String shopCode, String userName);

    /**
     * Check if the phone number has registered to the promotion.
     * @param thue_bao
     * @return Boolean
     * @throws ObjectNotFoundException
     */
    Boolean checkRegisterThueBao4QTeen(String thue_bao)throws ObjectNotFoundException;

    /**
     * Update quantity in stock of Shop.
     * @param properties comprise shopId, giftId and number of this items iin stock for the Shop.
     */
    void updateShopStock_qStudent(Map<String, Object> properties);

    /**
     * This method is used to update Ma Phieu status from exchanged status to not exchanged.
     * @param maPhieus a list of Ma Phieu that are being to updated.
     */
    void updateMaPhieuStatus2NoExchange(List<String> maPhieus);

    /**
     * Get data for report ScoreSubscriberReport.
     * @param properties
     * @param firstItem
     * @param maxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] baoCaoSoDiemChoThueBao(Map<String, Object> properties, int firstItem, int maxPageItems, String sortExpression, String sortDirection);

    /**
     * Get data for report PackageReport.
     * @param properties
     * @param firstItem
     * @param reportMaxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] baoCaoPhatTrienGoi(Map<String, Object> properties, int firstItem, int reportMaxPageItems, String sortExpression, String sortDirection);
}
