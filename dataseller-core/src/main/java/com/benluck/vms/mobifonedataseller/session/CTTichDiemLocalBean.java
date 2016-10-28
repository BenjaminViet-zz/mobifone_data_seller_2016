package com.benluck.vms.mobifonedataseller.session;

import com.benluck.vms.mobifonedataseller.core.dto.CTTichDiemSoDiemDTO;

import javax.ejb.Local;
import javax.ejb.ObjectNotFoundException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by hieu
 * Date: 10/11/14
 * Time: 2:07 PM
 */
@Local
public interface CTTichDiemLocalBean {
    /**
     * Search list of Ma Phieu (ticket) by properties.
     * @param phoneNumber a phone number is used to register to the promotion.
     * @param offset
     * @param limit
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    public Object[] searchByThueBao(String phoneNumber, Integer da_cap_ma, Integer offset, Integer limit, String sortExpression, String sortDirection);

    /**
     * Search list of Ma Phieu (ticket) by properties.
     * @param thueBao
     * @param ngay_ps
     * @param offset
     * @param limit
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    public Object[] searchBySoThueBaoAndNgayPS(String thueBao, Timestamp ngay_ps, Integer offset, Integer limit, String sortExpression, String sortDirection);

    /**
     * Get information of Ma Phieu (ticket).
     * @param maPhieu
     * @param da_doi_qua
     * @param offset
     * @param limit
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    public Object[] searchByMaPhieu(String maPhieu, Integer da_doi_qua, Integer offset, Integer limit, String sortExpression, String sortDirection);

    /**
     * Perform insert message details for sending by SMS to the user phone number.
     * @param serverIP
     * @param phoneNumber
     * @param content
     */
    public void sendSMS(String serverIP, String phoneNumber, String content);

    /**
     * Perform exchange on Ma Phieu list.
     * @param dsMaPhieus
     * @param shopCode
     * @param shopUserName
     */
    public void shopUserGiftExchange(List<String> dsMaPhieus, String shopCode, String shopUserName);

    /**
     * Get statistic cumulative scores in date by phone number.
     * @param phoneNumber
     * @param whereClause
     * @param offset
     * @param limit
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] statisticCumulativeScoresByDate(String phoneNumber, String whereClause, Integer offset, Integer limit);

    /**
     * Verify id the phoneNumber has registered to the promotion.
     * @param phoneNumber
     * @return True if the phone number has registered, else False.
     * @throws ObjectNotFoundException
     */
    Boolean checkIfAlreadyRegistered(String phoneNumber) throws ObjectNotFoundException;

    Boolean checkIfAlreadyRegister4KPP(String phoneNumber) throws ObjectNotFoundException;

    Object[] searchDanhSachMaPhieu(String phoneNumber, Integer offset, Integer limit, String sortExpression, String sortDirection);

    Integer getCurrentPointTotal(String phoneNumber);

    Object[] statisticCumulativeScoresByMonth(String phoneNumber);

    Integer countSoPhieuChuaDoi(String phoneNumber);

    /**
     * Update the status of the Ma Phieu to Exchanging.
     * @param phoneNumber
     */
    void updateExchangeStatus4Maphieu(String phoneNumber);

    /**
     * Find the user phone number by ma phieu (ticket code)
     * @param ticketCode
     * @return Phone number of the user that is used to register to the promotion.
     */
    String findUserPhoneNumberByMaPhieu(String ticketCode);

    /**
     * Calculate total of exchanged Ma Phieu (ticket) from the promotion.
     * @return a number of total exchanged Ma Phieu.
     */
    Integer getTotalOfExchangedMaPhieu();

    /**
     * Calculate total of exchanged Ma Phieu (ticket) from the promotion by properties.
     * @param phoneNumber
     * @return a number of total exchanged Ma Phieu by phone number.
     */
    Integer getTotalOfExchangedMaPhieu(String phoneNumber);


    List messageManagementReport(String doiTuongFromKHCN, String doiTuongFromVMS, Timestamp fromDateTime, Timestamp toDateTime);

    /**
     * Get all of Ma Phieu by subscriber phone number.
     * @param phoneNumber
     * @param offset
     * @param limit
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] searchSubscriberInfo(String phoneNumber, Integer offset, Integer limit, String sortExpression, String sortDirection);

    Object[] traCuuPhatSinhTheoNgay4KPP(Integer thangTichDiem, Long item_Id, String thue_bao, Timestamp fromDate, Timestamp toDate, Integer offfset, Integer limit, String sortExpression, String sortDirection);

    Integer getCurrentPointTotal4KPP(String thue_bao);

    /**
     * Get list of Dealer by properties.
     * @param branchId
     * @param districtId
     * @return A list of matched RetailDealerDTO.
     */
    List findRetailDealerList(Long branchId, Long districtId);

    /**
     * Get data for report MarketInfoReportByAgency.
     * @param properties
     * @param firstItem
     * @param maxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] baoCaoThongTinThiTruongTheoDaiLy(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection);

    /**
     * Get data for report MarketInfoReportByDistrict.
     * @param properties
     * @param firstItem
     * @param maxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] baoCaoThongTinThiTruongTheoQuanHuyen(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection);

    /**
     * Get data for report MarketInfoReportByAgency.
     * @param properties
     * @param firstItem
     * @param maxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] baoCaoThongTinThiTruongTheoChiNhanh(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection);

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

    Object[] TraCuuThongTinDoiDiemDBH(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection);

    /**
     * Get list of District by branchId.
     * @param branchId
     * @return A list of Objects.
     */
    List findDistrictListByBranchId(Long branchId);

    /**
     * Get all branches in the promotion.
     * @return A list of Objects.
     */
    List findAllBranches();

    Object[] findDealerInfoByDealerId(Long dealer_id) throws ObjectNotFoundException;

    Object[] findDealerAccountByDealerIdAndCycle(Long dealerId, Integer cycle) throws ObjectNotFoundException;

    void updateDealerAccountExchangeMoneyStatusByDealerIdAndCycle(Long dealer_id, Integer cycle) throws ObjectNotFoundException;

    Integer getTotalSoMaDuThuongHienTaiKPP(String phoneNumber);

    /**
     * Get information of the agency by phone number
     * @param phoneNumber
     * @return
     * @throws ObjectNotFoundException
     */
    String getAgencyInformation(String phoneNumber) throws ObjectNotFoundException;

    /**
     * Find all PromItems in DB.
     * @return A list of PromItems.
     */
    List findAllPromItems();

    List getThongTinMaTrungThuong(String thue_bao, String maDuThuong) throws ObjectNotFoundException;

    Object[] getWinningCodeList(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection);

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

    Object[] xemChiTietHangMucPhatSinh4Item1(Long dealer_Id, Long item_Id, Timestamp fromDateTime, Timestamp toDateTime, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection);

    Object[] xemChiTietHangMucPhatSinh4Item24User(Long dealer_Id, Long item_Id, Timestamp fromDateTime, Timestamp toDateTime, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection);

    Object[] xemChiTietHangMucPhatSinh4Item24Admin(Long dealer_Id, Long item_Id, Timestamp fromDateTime, Timestamp toDateTime, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection);

    Object[] xemChiTietHangMucPhatSinh4Item24AdminNew(Long dealer_Id, Long item_Id, Timestamp fromDateTime, Timestamp toDateTime, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection);

    Object[] xemChiTietHangMucPhatSinh4Item3(Long dealer_Id, Long item_Id, Timestamp fromDateTime, Timestamp toDateTime, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection);

    Object[] xemChiTietHangMucPhatSinh4Item4(Long dealer_Id, Long item_Id, Timestamp fromDateTime, Timestamp toDateTime, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection);

    Object[] xemChiTietHangMucPhatSinh4Item5(Long dealer_Id, Long item_Id, Timestamp fromDateTime, Timestamp toDateTime, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection);

    Object[] xemChiTietHangMucPhatSinh4Item6(Long dealer_Id, Long item_Id, Timestamp fromDateTime, Timestamp toDateTime, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection);

    Object[] xemChiTietHangMucPhatSinh4Item7(Long dealer_Id, Long item_Id, Timestamp fromDateTime, Timestamp toDateTime, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection);

    Object[] traCuuDanhSachTrungThuongKPP(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection);

    /**
     * Retrieve branch by branchId.
     * @param branchId
     * @return BranchDTO
     * @throws ObjectNotFoundException
     */
    Object[] findBranchById(Long branchId) throws ObjectNotFoundException;

    Object[] baoCaoHangHoaTheoDaiLy(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection);

    Object[] baoCaoHangHoaTheoQuanHuyen(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection);

    Object[] baoCaoHangHoaTheoChiNhanh(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection);

    /**
     * Fetch list of Dealer which belong to the branchId.
     * @param branchId
     * @return A list of RetailDealerDTO.
     */
    List findRetailDealerListByBranchId(Long branchId);

    List baoCaoDanhGiaKetQuaTHCT_tieuChi1(Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia);
    List baoCaoDanhGiaKetQuaTHCT_tieuChi2(Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia);
    List baoCaoDanhGiaKetQuaTHCT_tieuChi3And4Summary(Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia);
    List baoCaoDanhGiaKetQuaTHCT_tieuChi3And4TheoNgay(Long branchId, Long districtId, Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia);
    List baoCaoDanhGiaKetQuaTHCT_tieuChi5And6Summary(Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia, Timestamp fromDateTimeReportFilter, Timestamp toDateTimeReportFilter);
    List baoCaoDanhGiaKetQuaTHCT_tieuChi5And6TheoNgay(Long branchId, Long districtId, Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia, Timestamp fromDateTimeReportFilter, Timestamp toDateTimeReportFilter);
    List baoCaoDanhGiaKetQuaTHCT_tieuChi7(Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia);
    List baoCaoDanhGiaKetQuaTHCT_tieuChi8(Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia);
    List baoCaoDanhGiaKetQuaTHCT_tieuChi9(Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia);
    List baoCaoDanhGiaKetQuaTHCT_tieuChi10(Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia);

    Object[] traCuuPhatSinhTheoNgay4KPPNew(Integer thangTichDiem, Long item_Id, String thue_bao, Timestamp fromDate, Timestamp toDate, Integer offset, Integer limit, String sortExpression, String sortDirection);

    List traCuuPhatSinhDiemTheoThangKPP(String thue_bao);

    /**
     * Get information of the Dealer by EZ (phone number)
     * @param ez a phone number of the Dealer that is used to register the promotion.
     * @return A list of Objects.
     * @throws ObjectNotFoundException
     */
    Object[] findRetailDealerByEZ(String ez) throws ObjectNotFoundException;

    /**
     * Get all kind of gifts in the promotion "Tich Diem Cuoc Goi Nhan Voucher"
     * @return A list of Objects.
     */
    List findAllKppGifts();

    Object[] traCuuChiTietTrangThaiKy(Long dealer_Id, Integer cycle);

    /**
     * Get all Dealers in the promotion "Tich Diem Cuoc Goi Nhan Voucher".
     * @return A list of Objects.
     */
    List findAllRetailDealers();

    /**
     * Get all district that are available in the promotion Tich Diem Cuoc Goi Nhan Voucher
     * @return A list of Objects.
     */
    List findAllDistricts();

    /**
     * Search list of Dealer that are belong to the district by districtId.
     * @param district_Id
     * @return A list of Objects.
     */
    List findRetailDealerListByDistrictId(Long district_Id);

    /**
     * Get information about Dealer by properties.
     * @param properties
     * @param firstItems
     * @param maxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] searchKPPInformationByProperties(Map<String, Object> properties, Integer firstItems, Integer maxPageItems, String sortExpression, String sortDirection);

    /**
     * Get RetailDealer information by dealerId.
     * @param dealerId
     * @return An array of property-value of the Dealer.
     * @throws ObjectNotFoundException
     */
    Object[] findRetailDealerInfoById(Long dealerId) throws ObjectNotFoundException;

    /**
     * Get District information by districtid.
     * @param districtId
     * @return An array of property-value of the District.
     * @throws ObjectNotFoundException
     */
    Object[] findDistrictById(Long districtId)throws ObjectNotFoundException;

    /**
     * Get data for report KPP_SummaryByAgency.
     * @param properties
     * @param hasFilterDate
     * @param firstItem
     * @param reportMaxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] baoCaoTongHopChuongTrinhKPP_theoDaiLy(Map<String, Object> properties, Boolean hasFilterDate, Integer firstItem, Integer reportMaxPageItems, String sortExpression, String sortDirection);

    /**
     * Get data for report KPP_SummaryByDistrict.
     * @param properties
     * @param hasFilterDate
     * @param firstItem
     * @param reportMaxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] baoCaoTongHopChuongTrinhKPP_theoQuanHuyen(Map<String, Object> properties, Boolean hasFilterDate, Integer firstItem, Integer reportMaxPageItems, String sortExpression, String sortDirection);

    /**
     * Get data for report KPP_SummaryByBranch.
     * @param properties
     * @param hasFilterDate
     * @param firstItem
     * @param reportMaxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] baoCaoTongHopChuongTrinhKPP_theoChiNhanh(Map<String, Object> properties, Boolean hasFilterDate, Integer firstItem, Integer reportMaxPageItems, String sortExpression, String sortDirection);

    /**
     * Get data for report PSCItemDetail_KPP_BTGFromMobiFone_ByAgency.
     * @param properties
     * @param firstItem
     * @param reportMaxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] baoCaoChiTietHangMucPSC_kpp_mua_btg_tu_mobiFone_theoDaiLy(Map<String, Object> properties, Integer firstItem, Integer reportMaxPageItems, String sortExpression, String sortDirection);

    /**
     * Get data for report PSCItemDetail_KPP_BTGFromMobiFone_ByDistrict.
     * @param properties
     * @param firstItem
     * @param reportMaxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] baoCaoChiTietHangMucPSC_kpp_mua_btg_tu_mobiFone_theoQuanHuyen(Map<String, Object> properties, Integer firstItem, Integer reportMaxPageItems, String sortExpression, String sortDirection);

    /**
     * Get data for report PSCItemDetail_KPP_BTGFromMobiFone_ByBranch.
     * @param properties
     * @param firstItem
     * @param reportMaxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] baoCaoChiTietHangMucPSC_kpp_mua_btg_tu_mobiFone_theoChiNhanh(Map<String, Object> properties, Integer firstItem, Integer reportMaxPageItems, String sortExpression, String sortDirection);

    /**
     * Get data for report PSCItemDetail_KPP_CardFromMobiFone_ByAgency.
     * @param properties
     * @param firstItem
     * @param reportMaxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] baoCaoChiTietHangMucPSC_kpp_mua_the_tu_mobiFone_theoDaiLy(Map<String, Object> properties, Integer firstItem, Integer reportMaxPageItems, String sortExpression, String sortDirection);

    /**
     * Get data for report PSC_VAS_ItemDetailReport_ByAgency.
     * @param properties
     * @param firstItem
     * @param reportMaxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] baoCaoChiTietHangMucPSC_kpp_ban_vas_theoDaiLy(Map<String, Object> properties, Integer firstItem, Integer reportMaxPageItems, String sortExpression, String sortDirection);

    /**
     * Get data for report PSC_VAS_ItemDetailReport_ByDistrict.
     * @param properties
     * @param firstItem
     * @param reportMaxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] baoCaoChiTietHangMucPSC_kpp_ban_vas_theoQuanHuyen(Map<String, Object> properties, Integer firstItem, Integer reportMaxPageItems, String sortExpression, String sortDirection);

    /**
     * Get data for report PSC_VAS_ItemDetailReport_ByBranch.
     * @param properties
     * @param firstItem
     * @param reportMaxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] baoCaoChiTietHangMucPSC_kpp_ban_vas_theoChiNhanh(Map<String, Object> properties, Integer firstItem, Integer reportMaxPageItems, String sortExpression, String sortDirection);

    Object[] baoCaoChiTietHangMucPSC_kpp_gioi_thieu_kh_tham_gia_ctkm_theoDaiLy(Map<String, Object> properties, Integer firstItem, Integer reportMaxPageItems, String sortExpression, String sortDirection);

    /**
     * Get data for report PSCItemDetail_KPP_CTKM_ByDistrict.
     * @param properties
     * @param firstItem
     * @param reportMaxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] baoCaoChiTietHangMucPSC_kpp_gioi_thieu_kh_tham_gia_ctkm_theoQuanHuyen(Map<String, Object> properties, Integer firstItem, Integer reportMaxPageItems, String sortExpression, String sortDirection);

    /**
     * Get data for report PSCItemDetail_KPP_CTKM_ByBranch.
     * @param properties
     * @param firstItem
     * @param reportMaxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] baoCaoChiTietHangMucPSC_kpp_gioi_thieu_kh_tham_gia_ctkm_theoChiNhanh(Map<String, Object> properties, Integer firstItem, Integer reportMaxPageItems, String sortExpression, String sortDirection);

    Object[] baoCaoChiTietHangMucPSC_kpp_ban_truc_tiep_btg_va_psc_theoDBH(Map<String, Object> properties, Integer firstItem, Integer reportMaxPageItems, String sortExpression, String sortDirection);

    Object[] baoCaoChiTietHangMucPSC_kpp_ban_truc_tiep_btg_va_psc_theoQuanHuyen(Map<String, Object> properties, Integer firstItem, Integer reportMaxPageItems, String sortExpression, String sortDirection);

    Object[] baoCaoChiTietHangMucPSC_kpp_ban_truc_tiep_btg_va_psc_theoChiNhanh(Map<String, Object> properties, Integer firstItem, Integer reportMaxPageItems, String sortExpression, String sortDirection);

    /**
     * Get data for report Message_KPP_byAgency Report.
     * @param properties
     * @param firstItem
     * @param reportMaxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] baoCaoTinNhan_kpp_theoDBH(Map<String, Object> properties, Integer firstItem, Integer reportMaxPageItems, String sortExpression, String sortDirection);

    /**
     * Get data for report Message_KPP_byDistrict Report.
     * @param properties
     * @param firstItem
     * @param reportMaxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] baoCaoTinNhan_kpp_theoQuanHuyen(Map<String, Object> properties, Integer firstItem, Integer reportMaxPageItems, String sortExpression, String sortDirection);

    /**
     * Get data for report Message_KPP_byBranch Report.
     * @param properties
     * @param firstItem
     * @param reportMaxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] baoCaoTinNhan_kpp_theoChiNhanh(Map<String, Object> properties, Integer firstItem, Integer reportMaxPageItems, String sortExpression, String sortDirection);

    /**
     * Get data for report Warning_KPP_ByAgency.
     * @param properties
     * @param firstItem
     * @param reportMaxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] baoCaoCanhBao_kpp_theoDBH(Map<String, Object> properties, Integer firstItem, Integer reportMaxPageItems, String sortExpression, String sortDirection);

    /**
     * Get data for report Warning_KPP_ByDistrict.
     * @param properties
     * @param firstItem
     * @param reportMaxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] baoCaoCanhBao_kpp_theoQuanHuyen(Map<String, Object> properties, Integer firstItem, Integer reportMaxPageItems, String sortExpression, String sortDirection);

    /**
     * Get data for report Warning_KPP_ByBranch.
     * @param properties
     * @param firstItem
     * @param reportMaxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] baoCaoCanhBao_kpp_theoChiNhanh(Map<String, Object> properties, Integer firstItem, Integer reportMaxPageItems, String sortExpression, String sortDirection);

    /**
     * Get warning information by properties.
     * @param soEZ
     * @param firstItem
     * @param maxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] getWarningInfo(String soEZ, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection);

    /**
     * Find all services has type is "SMS_SERVICE_TYPE"
     * @return A list of all matched services.
     */
    List findServiceTypeList();

    /**
     * Validate list of Ma Phieu that are being to be exchanged.
     * @param maPhieuList
     * @return An array of string that has exchanged before
     */
    String[] validateMaPhieuNeedToExchanged(String[] maPhieuList);
}
