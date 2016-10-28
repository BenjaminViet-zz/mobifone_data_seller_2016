package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.utils.Config;
import com.benluck.vms.mobifonedataseller.core.business.KPPManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.*;
import com.benluck.vms.mobifonedataseller.session.CTTichDiemLocalBean;
import com.benluck.vms.mobifonedataseller.session.ThueBaoPhatTrienMoiLocalBean;
import com.benluck.vms.mobifonedataseller.session.UserGroupLocalBean;
import com.benluck.vms.mobifonedataseller.session.UserLocalBean;

import javax.ejb.EJB;
import javax.ejb.ObjectNotFoundException;
import javax.ejb.Stateless;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/21/14
 * Time: 10:05 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "KPPManagementSessionEJB")
public class KPPManagementSessionBean implements KPPManagementLocalBean{
    @EJB
    private CTTichDiemLocalBean ctTichDiemLocalBean;
    @EJB
    private UserLocalBean userLocalBean;
    @EJB
    private UserGroupLocalBean userGroupLocalBean;
    @EJB
    private ThueBaoPhatTrienMoiLocalBean thueBaoPhatTrienMoiLocalBean;

    public KPPManagementSessionBean() {
    }

    @Override
    public Boolean checkIfAlreadyRegistered(String phoneNumber) throws ObjectNotFoundException{
        String chuong_trinh = Config.getInstance().getProperty("promotion_type_configure");
        if(chuong_trinh.equalsIgnoreCase(Constants.CT_THUE_BAO_PTM)){
            return this.thueBaoPhatTrienMoiLocalBean.checkIfAlreadyRegister4KPP(phoneNumber);
        }else{
            return this.ctTichDiemLocalBean.checkIfAlreadyRegister4KPP(phoneNumber);
        }
    }

    @Override
    public Object[] getScoreByDate(Integer month, Long item_Id, String phoneNumber, Timestamp fromDate, Timestamp toDate, Integer offset, Integer limit, String sortExpression, String sortDirection) {
        Object[] resultObject = this.ctTichDiemLocalBean.traCuuPhatSinhTheoNgay4KPP(month, item_Id, phoneNumber, fromDate, toDate, offset, limit, sortExpression, sortDirection);
        List<DealerAccountActionDTO> dtoList = new ArrayList<DealerAccountActionDTO>();

        for(Object object : (List)resultObject[1]){
            Object[] tmpArr = (Object[])object;
            DealerAccountActionDTO dto = new DealerAccountActionDTO();
            dto.setTenLoaiPS(tmpArr[0] != null ? tmpArr[0].toString() : null);
            dto.setIssue_date(tmpArr[1] != null ? Timestamp.valueOf(tmpArr[1].toString()) : null);
            dto.setProm_point(tmpArr[2] != null ? Integer.valueOf(tmpArr[2].toString()) : null);
            dto.setItem_Id(tmpArr[3] != null ? Long.valueOf(tmpArr[3].toString()) : null);
            dto.setDealer_Id(tmpArr[4] != null ? Long.valueOf(tmpArr[4].toString()) : null);
            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return resultObject;
    }

    @Override
    public Object[] getScoreByDateNew(Integer month, Long item_Id, String phoneNumber, Timestamp fromDate, Timestamp toDate, Integer offset, Integer limit, String sortExpression, String sortDirection) {
        Object[] resultObject = this.ctTichDiemLocalBean.traCuuPhatSinhTheoNgay4KPPNew(month, item_Id, phoneNumber, fromDate, toDate, offset, limit, sortExpression, sortDirection);
        List<DealerAccountActionDTO> dtoList = new ArrayList<DealerAccountActionDTO>();

        for(Object object : (List)resultObject[1]){
            Object[] tmpArr = (Object[])object;
            DealerAccountActionDTO dto = new DealerAccountActionDTO();
            dto.setItem_Id(tmpArr[0] != null ? Long.valueOf(tmpArr[0].toString()) : null);
            dto.setTenLoaiPS(tmpArr[1] != null ? tmpArr[1].toString() : null);
            dto.setProm_point(tmpArr[2] != null ? Integer.valueOf(tmpArr[2].toString()) : null);
            dto.setSoTienTuongUng(tmpArr[3] != null ? Double.valueOf(tmpArr[3].toString()) : null);
            dto.setSoMaDuThuong(tmpArr[4] != null ? Integer.valueOf(tmpArr[4].toString()) : null);
            dto.setCycle(tmpArr[5] != null ? Integer.valueOf(tmpArr[5].toString()) : null);
            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return resultObject;
    }

    @Override
    public Integer getCurrentScore(String phoneNumber) {
        return this.ctTichDiemLocalBean.getCurrentPointTotal4KPP(phoneNumber);
    }

    @Override
    public Integer getCurrentTotalScoreWinningTicketKPP(String phoneNumber) {
        return this.ctTichDiemLocalBean.getTotalSoMaDuThuongHienTaiKPP(phoneNumber);
    }

    @Override
    public Object[] searchWinningTicketList(String phoneNumber, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) {
        Object[] resultObject = this.ctTichDiemLocalBean.searchWinningTicketList(phoneNumber, firstItem, maxPageItems, sortExpression, sortDirection);
        List<CTTichDiemMaDuThuongDTO> dtoList = new ArrayList<CTTichDiemMaDuThuongDTO>();
        for (Object object : (List)resultObject[1]){
            Object[] tmpArr = (Object[])object;
            CTTichDiemMaDuThuongDTO dto = new CTTichDiemMaDuThuongDTO();
            dto.setNgay_ps(tmpArr[0] != null ? Timestamp.valueOf(tmpArr[0].toString()) : null);
            dto.setMa_phieu(tmpArr[1] != null ? tmpArr[1].toString() : null);
            dto.setExchange_gift_status(tmpArr[2] != null ? tmpArr[2].toString() : "0");
            dto.setExchange_gift_name(tmpArr[3] != null ? tmpArr[3].toString() : null);
            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return  resultObject;
    }

    @Override
    public String generateInfo4Agency(String phoneNumber) throws ObjectNotFoundException{
        return this.ctTichDiemLocalBean.getAgencyInformation(phoneNumber);
    }

    @Override
    public List<PromItemsDTO> findAllItems() {
        List resultSet = this.ctTichDiemLocalBean.findAllPromItems();
        List<PromItemsDTO> dtoList = new ArrayList<PromItemsDTO>();
        for (Object object : resultSet){
            Object[] tmpArr = (Object[])object;
            PromItemsDTO dto = new PromItemsDTO();
            dto.setItem_Id(tmpArr[0] != null ? Long.valueOf(tmpArr[0].toString()) : null);
            dto.setItem_code(tmpArr[1] != null ? tmpArr[1].toString() : null);
            dto.setItem_name(tmpArr[2] != null ? tmpArr[2].toString() : null);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public CTTichDiemMaDuThuongDTO getThongTinMaTrungThuong(String thue_bao, String maDuThuong) throws ObjectNotFoundException{
        List resultSet = this.ctTichDiemLocalBean.getThongTinMaTrungThuong(thue_bao, maDuThuong);
        CTTichDiemMaDuThuongDTO dto = null;
        if(resultSet != null && resultSet.size() > 0){
            dto = new CTTichDiemMaDuThuongDTO();
            Object[] tmpArr = (Object[])resultSet.get(0);
            dto.setDealer_Id(tmpArr[0] != null ? Long.valueOf(tmpArr[0].toString()) : null);
            dto.setDealer_code(tmpArr[1] != null ? tmpArr[1].toString() : null);

            PromItemsDTO itemDTO = new PromItemsDTO();
            itemDTO.setItem_Id(tmpArr[2] != null ? Long.valueOf(tmpArr[2].toString()) : null);
            dto.setItem(itemDTO);

            dto.setMa_phieu(tmpArr[3] != null ? tmpArr[3].toString() : null);
            dto.setCycle(tmpArr[4] != null ? Timestamp.valueOf(tmpArr[4].toString()) : null);
            dto.setInsert_dateTime(tmpArr[5] != null ? Timestamp.valueOf(tmpArr[5].toString()) : null);
            dto.setSms_status(tmpArr[6] != null ? tmpArr[6].toString().trim() : null);
            dto.setExchange_gift_status(tmpArr[7] != null ? tmpArr[7].toString().trim() : null);
            dto.setExchange_gift_id(tmpArr[8] != null ? Long.valueOf(tmpArr[8].toString()) : null);
            dto.setExchange_gift_name(tmpArr[9] != null ? tmpArr[9].toString() : null);
            dto.setWinning_status(tmpArr[10] != null ? tmpArr[10].toString().trim() : null);
        }
        return  dto;
    }

    @Override
    public Object[] xemChiTietHangMucPhatSinh(Boolean isAdmin, Long dealer_Id, Long item_Id, Timestamp fromDateTime, Timestamp toDateTime, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) {
        Object[] resultObject = null;
        List<ChiTietHangMucPhatSinhKPPDTO> dtoList = new ArrayList<ChiTietHangMucPhatSinhKPPDTO>();
        if(item_Id.equals(Constants.ITEM_ID_1)){
            resultObject = this.ctTichDiemLocalBean.xemChiTietHangMucPhatSinh4Item1(dealer_Id, item_Id, fromDateTime, toDateTime, firstItem, maxPageItems, sortExpression, sortDirection);
        }else if(item_Id.equals(Constants.ITEM_ID_2)){
            if(isAdmin != null && isAdmin.booleanValue()){
//                resultObject = this.ctTichDiemLocalBean.xemChiTietHangMucPhatSinh4Item24Admin(dealer_Id, item_Id, fromDateTime, toDateTime, firstItem, maxPageItems, sortExpression, sortDirection);
                resultObject = this.ctTichDiemLocalBean.xemChiTietHangMucPhatSinh4Item24AdminNew(dealer_Id, item_Id, fromDateTime, toDateTime, firstItem, maxPageItems, sortExpression, sortDirection);
            }else{
                resultObject = this.ctTichDiemLocalBean.xemChiTietHangMucPhatSinh4Item24User(dealer_Id, item_Id, fromDateTime, toDateTime, firstItem, maxPageItems, sortExpression, sortDirection);
            }
        }else if(item_Id.equals(Constants.ITEM_ID_3)){
            resultObject = this.ctTichDiemLocalBean.xemChiTietHangMucPhatSinh4Item3(dealer_Id, item_Id, fromDateTime, toDateTime, firstItem, maxPageItems, sortExpression, sortDirection);
        }else if(item_Id.equals(Constants.ITEM_ID_4)){
            resultObject = this.ctTichDiemLocalBean.xemChiTietHangMucPhatSinh4Item4(dealer_Id, item_Id, fromDateTime, toDateTime, firstItem, maxPageItems, sortExpression, sortDirection);
        }else if(item_Id.equals(Constants.ITEM_ID_5)){
            resultObject = this.ctTichDiemLocalBean.xemChiTietHangMucPhatSinh4Item5(dealer_Id, item_Id, fromDateTime, toDateTime, firstItem, maxPageItems, sortExpression, sortDirection);
        }else if(item_Id.equals(Constants.ITEM_ID_6)){
            resultObject = this.ctTichDiemLocalBean.xemChiTietHangMucPhatSinh4Item6(dealer_Id, item_Id, fromDateTime, toDateTime, firstItem, maxPageItems, sortExpression, sortDirection);
        }else if(item_Id.equals(Constants.ITEM_ID_7)){
            resultObject = this.ctTichDiemLocalBean.xemChiTietHangMucPhatSinh4Item7(dealer_Id, item_Id, fromDateTime, toDateTime, firstItem, maxPageItems, sortExpression, sortDirection);
        }
        dtoList = buildDataList(isAdmin, item_Id, resultObject);
        resultObject[1] = dtoList;
        return resultObject;
    }

    @Override
    public Object[] traCuuDanhSachTrungThuongKPP(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) {
        Object[] resultObject = this.ctTichDiemLocalBean.traCuuDanhSachTrungThuongKPP(properties, firstItem, maxPageItems, sortExpression, sortDirection);
        List<CTTichDiemMaDuThuongDTO> dtoList = new ArrayList<CTTichDiemMaDuThuongDTO>();
        for(Object object : (List)resultObject[1]){
            Object[] tmpArr = (Object[])object;
            CTTichDiemMaDuThuongDTO dto = new CTTichDiemMaDuThuongDTO();
            dto.setDealer_Id(tmpArr[0] != null ? Long.valueOf(tmpArr[0].toString()) : null);
            dto.setDealer_code(tmpArr[1] != null ? tmpArr[1].toString() : null);
            dto.setDealer_name(tmpArr[2] != null ? tmpArr[2].toString() : null);
            dto.setMa_phieu(tmpArr[3] != null ? tmpArr[3].toString() : null);
            dto.setNgay_ps(tmpArr[4] != null ? Timestamp.valueOf(tmpArr[4].toString()) : null);
            dto.setExchange_gift_name(tmpArr[5] != null ? tmpArr[5].toString() : null);
            dto.setExchange_gift_status(tmpArr[6] != null ? tmpArr[6].toString() : null);
            dto.setGift_Level(tmpArr[7] != null ? tmpArr[7].toString() : null);
            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return resultObject;
    }

    @Override
    public List<DealerAccountActionDTO> traCuuPhatSinhTheoThangNew(String thue_bao, Integer thangTichDiem) {
        List resultObject = this.ctTichDiemLocalBean.traCuuPhatSinhDiemTheoThangKPP(thue_bao);
        List<DealerAccountActionDTO> dtoList = new ArrayList<DealerAccountActionDTO>();
        Long dealer_Id = null;
        for (Object object : resultObject){
            Object[] tmpArr = (Object[])object;
            DealerAccountActionDTO dto = new DealerAccountActionDTO();
            dto.setCycle(tmpArr[0] != null ? Integer.valueOf(tmpArr[0].toString()) : null);
            dto.setProm_point(tmpArr[1] != null ? Integer.valueOf(tmpArr[1].toString()) : null);
            dto.setSoTienTuongUng(tmpArr[2] != null ? Double.valueOf(tmpArr[2].toString()) : null);
            dto.setSoMaDuThuong(tmpArr[3] != null ? Integer.valueOf(tmpArr[3].toString()) : null);
            dto.setCycle_lock_status(tmpArr[4] != null ? tmpArr[4].toString() : null);
            dto.setCondition_status(tmpArr[5] != null ? tmpArr[5].toString() : null);
            dto.setDealer_Id(tmpArr[6] != null ? Long.valueOf(tmpArr[6].toString()) : null);
            dtoList.add(dto);
        }

        boolean alreadyData4T10 = false;
        boolean alreadyData4T11 = false;
        boolean alreadyData4T12 = false;
        for(int cycle = 10; cycle <= thangTichDiem.intValue(); cycle++){
            for(DealerAccountActionDTO dto : dtoList){
                if(dto.getCycle().intValue() == cycle){
                    if(cycle == 10){
                        alreadyData4T10 = true;
                    }else if (cycle == 11){
                        alreadyData4T11 = true;
                    }else if (cycle == 12){
                        alreadyData4T12 = true;
                    }
                }
            }
        }

        for(int cycle = 10; cycle <= thangTichDiem.intValue(); cycle++){
            if(cycle == 10){
                if(!alreadyData4T10){
                    DealerAccountActionDTO dto1 = new DealerAccountActionDTO();
                    dto1.setCycle(10);
                    dto1.setProm_point(0);
                    dto1.setSoTienTuongUng(new Double(0));
                    dto1.setSoMaDuThuong(0);
                    dto1.setDealer_Id(dealer_Id);
                    dtoList.add(dto1);
                }
            }
            if(cycle == 11){
                if(!alreadyData4T11){
                    DealerAccountActionDTO dto1 = new DealerAccountActionDTO();
                    dto1.setCycle(11);
                    dto1.setProm_point(0);
                    dto1.setSoTienTuongUng(new Double(0));
                    dto1.setSoMaDuThuong(0);
                    dto1.setDealer_Id(dealer_Id);
                    dtoList.add(dto1);
                }
            }
            if(cycle == 12){
                if(!alreadyData4T12){
                    DealerAccountActionDTO dto1 = new DealerAccountActionDTO();
                    dto1.setCycle(12);
                    dto1.setProm_point(0);
                    dto1.setSoTienTuongUng(new Double(0));
                    dto1.setSoMaDuThuong(0);
                    dto1.setDealer_Id(dealer_Id);
                    dtoList.add(dto1);
                }
            }
        }

        Collections.sort(dtoList, new Comparator<DealerAccountActionDTO>() {
            @Override
            public int compare(DealerAccountActionDTO o1, DealerAccountActionDTO o2) {
                return o1.getCycle().compareTo(o2.getCycle());
            }
        });

        return dtoList;
    }

    @Override
    public RetailDealerDTO findRetailDealerByThueBao(String thue_bao) throws ObjectNotFoundException {
        Object[] resultSet = this.ctTichDiemLocalBean.findRetailDealerByEZ(thue_bao);
        RetailDealerDTO dto = new RetailDealerDTO();
        dto.setDealer_Id(resultSet[0] != null ? Long.valueOf(resultSet[0].toString()) : null);
        dto.setDealer_code(resultSet[1] != null ? resultSet[1].toString() : null);
        dto.setDealer_name(resultSet[2] != null ? resultSet[2].toString() : null);
        return dto;
    }

    @Override
    public List<KppGiftDTO> findAllKppGifts() {
        List resultSet = this.ctTichDiemLocalBean.findAllKppGifts();
        List<KppGiftDTO> dtoList = new ArrayList<KppGiftDTO>();
        for(Object object : resultSet){
            Object[] tmpArr = (Object[])object;
            KppGiftDTO dto = new KppGiftDTO();
            dto.setGift_Id(tmpArr[0] != null ? Long.valueOf(tmpArr[0].toString()) : null);
            dto.setGift_Name(tmpArr[1] != null ? tmpArr[1].toString() : null);
            dto.setGift_level(tmpArr[2] != null ? tmpArr[2].toString() : null);
            dto.setGift_Quantity(tmpArr[3] != null ? Integer.valueOf(tmpArr[3].toString()) : null);
            dto.setGift_Value(tmpArr[4] != null ? Double.valueOf(tmpArr[4].toString()) : null);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public Object[] traCuuChiTietTrangThaiKy(Long dealer_Id, Integer cycle) {
        Object[] resultObject = this.ctTichDiemLocalBean.traCuuChiTietTrangThaiKy(dealer_Id, cycle);
        List<DealerAccountDTO> dtoList = new ArrayList<DealerAccountDTO>();
        for(Object object : (List)resultObject[1]){
            Object[] tmpArr = (Object[])object;
            DealerAccountDTO dto = new DealerAccountDTO();
            dto.setKit_sales_condition_status(tmpArr[0] != null ? tmpArr[0].toString() : null);
            dto.setSms_sales_condition_status(tmpArr[1] != null ? tmpArr[1].toString() : null);
            dto.setSub_charge_condition_status(tmpArr[2] != null ? tmpArr[2].toString() : null);
            dto.setSub_intro_condition_status(tmpArr[3] != null ? tmpArr[3].toString() : null);
            dto.setVas_sales_conditon_status(tmpArr[4] != null ? tmpArr[4].toString() : null);
            dto.setMarket_info_condition_status(tmpArr[5] != null ? tmpArr[5].toString() : null);
            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return resultObject;
    }

    @Override
    public Object[] getWarningReportData(String soEZ, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) {
        Object[] resultObject = this.ctTichDiemLocalBean.getWarningInfo(soEZ, firstItem, maxPageItems, sortExpression, sortDirection);
        List<CanhBaoKPPDTO> dtoList = new ArrayList<CanhBaoKPPDTO>();
        for(Object object : (List)resultObject[1]){
            Object[] tmpArr = (Object[])object;
            CanhBaoKPPDTO dto = new CanhBaoKPPDTO();
            dto.setChuKy(tmpArr[0] != null ? Integer.valueOf(tmpArr[0].toString()) : null);
            dto.setItem_Name(tmpArr[1] != null ? tmpArr[1].toString() : null);
            dto.setSoPS(tmpArr[2] != null ? Double.valueOf(tmpArr[2].toString()) : null);
            dto.setSoDiemDaDat(tmpArr[3] != null ? Double.valueOf(tmpArr[3].toString()) : null);
            dto.setSoPhaiDat(tmpArr[4] != null ? Double.valueOf(tmpArr[4].toString()) : null);
            dto.setNgayTongHop(tmpArr[5] != null ? Timestamp.valueOf(tmpArr[5].toString()) : null);
            dto.setThoiGianConLai(tmpArr[6] != null ? Integer.valueOf(tmpArr[6].toString()) : null);
            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return resultObject;
    }

    private List<ChiTietHangMucPhatSinhKPPDTO> buildDataList(Boolean isAdmin, Long item_Id, Object[] resultObject){
        if(item_Id.equals(Constants.ITEM_ID_1)){
            return buildDataList4Item1(resultObject);
        }else if(item_Id.equals(Constants.ITEM_ID_2)){
            if(isAdmin != null && isAdmin.booleanValue()){
//                return buildDataList4Item24Admin(resultObject);
                return buildDataList4Item24AdminNew(resultObject);
            }else{
                return buildDataList4Item2(resultObject);
            }
        }else if(item_Id.equals(Constants.ITEM_ID_3)){
            return buildDataList4Item3(resultObject);
        }else if(item_Id.equals(Constants.ITEM_ID_4)){
            return buildDataList4Item4(resultObject);
        }else if(item_Id.equals(Constants.ITEM_ID_5)){
            return buildDataList4Item5(resultObject);
        }else if(item_Id.equals(Constants.ITEM_ID_6)){
            return buildDataList4Item6(resultObject);
        }else if(item_Id.equals(Constants.ITEM_ID_7)){
            return buildDataList4Item7(resultObject);
        }
        return null;
    }

    private List<ChiTietHangMucPhatSinhKPPDTO> buildDataList4Item1(Object[] resultObject){
        List<ChiTietHangMucPhatSinhKPPDTO> dtoList = new ArrayList<ChiTietHangMucPhatSinhKPPDTO>();
        if(resultObject != null && resultObject[1] != null){
            for(Object object : (List)resultObject[1]){
                Object[] tmpArr = (Object[])object;
                ChiTietHangMucPhatSinhKPPDTO dto = new ChiTietHangMucPhatSinhKPPDTO();
                dto.setLoaiItemId(Constants.ITEM_ID_1);
                dto.setDealer_Code(tmpArr[0] != null ? tmpArr[0].toString() : null);
                dto.setDealer_Name(tmpArr[1] != null ? tmpArr[0].toString() : null);
                dto.setSoHoaDon(tmpArr[2] != null ? tmpArr[2].toString() : null);
                dto.setNgayHoaDon(tmpArr[3] != null ? Timestamp.valueOf(tmpArr[3].toString()) : null);
                dto.setMaHangHoa(tmpArr[4] != null ? tmpArr[4].toString() : null);
                dto.setTenHangHoa(tmpArr[5] != null ? tmpArr[5].toString() : null);
                dto.setSoLuong(tmpArr[6] != null ? Integer.valueOf(tmpArr[6].toString()) : null);
                dto.setGiaTri(tmpArr[7] != null ? Double.valueOf(tmpArr[7].toString()) : null);
                dtoList.add(dto);
            }
        }
        return dtoList;
    }

    private List<ChiTietHangMucPhatSinhKPPDTO> buildDataList4Item2(Object[] resultObject){
        List<ChiTietHangMucPhatSinhKPPDTO> dtoList = new ArrayList<ChiTietHangMucPhatSinhKPPDTO>();
        if(resultObject != null && resultObject[1] != null){
            for(Object object : (List)resultObject[1]){
                Object[] tmpArr = (Object[])object;
                ChiTietHangMucPhatSinhKPPDTO dto = new ChiTietHangMucPhatSinhKPPDTO();
                dto.setLoaiItemId(Constants.ITEM_ID_2);
                dto.setDealer_Id(tmpArr[0] != null ? Long.valueOf(tmpArr[0].toString()) : null);
                dto.setDealer_Code(tmpArr[1] != null ? tmpArr[1].toString() : null);
                dto.setDealer_Name(tmpArr[2] != null ? tmpArr[2].toString() : null);
                dto.setSoEZ(tmpArr[3] != null ? tmpArr[3].toString() : null);
                dto.setNgay_ps(tmpArr[4] != null ? Timestamp.valueOf(tmpArr[4].toString()) : null);
                dto.setNoiDungTinNhan(tmpArr[5] != null ? tmpArr[5].toString() : null);
                dto.setKetQua(tmpArr[6] != null ? tmpArr[6].toString() : null);
                dtoList.add(dto);
            }
        }
        return dtoList;
    }

    private List<ChiTietHangMucPhatSinhKPPDTO> buildDataList4Item24Admin(Object[] resultObject){
        List<ChiTietHangMucPhatSinhKPPDTO> dtoList = new ArrayList<ChiTietHangMucPhatSinhKPPDTO>();
        if(resultObject != null && resultObject[1] != null){
            for(Object object : (List)resultObject[1]){
                Object[] tmpArr = (Object[])object;
                ChiTietHangMucPhatSinhKPPDTO dto = new ChiTietHangMucPhatSinhKPPDTO();
                dto.setLoaiItemId(Constants.ITEM_ID_2);
                dto.setDealer_Id(tmpArr[0] != null ? Long.valueOf(tmpArr[0].toString()) : null);
                dto.setDealer_Code(tmpArr[1] != null ? tmpArr[1].toString() : null);
                dto.setDealer_Name(tmpArr[2] != null ? tmpArr[2].toString() : null);
                dto.setSoEZ(tmpArr[3] != null ? tmpArr[3].toString() : null);
                dto.setNgay_ps(tmpArr[4] != null ? Timestamp.valueOf(tmpArr[4].toString()) : null);
                dto.setNoiDungTinNhan(tmpArr[5] != null ? tmpArr[5].toString() : null);
                dto.setKetQua(tmpArr[6] != null ? tmpArr[6].toString() : null);
                dtoList.add(dto);
            }
        }
        return dtoList;
    }

    private List<ChiTietHangMucPhatSinhKPPDTO> buildDataList4Item24AdminNew(Object[] resultObject){
        List<ChiTietHangMucPhatSinhKPPDTO> dtoList = new ArrayList<ChiTietHangMucPhatSinhKPPDTO>();
        if(resultObject != null && resultObject[1] != null){
            for(Object object : (List)resultObject[1]){
                Object[] tmpArr = (Object[])object;
                ChiTietHangMucPhatSinhKPPDTO dto = new ChiTietHangMucPhatSinhKPPDTO();
                dto.setLoaiItemId(Constants.ITEM_ID_2);
                dto.setDealer_Code(tmpArr[0] != null ? tmpArr[0].toString() : null);
                dto.setDealer_Name(tmpArr[1] != null ? tmpArr[1].toString() : null);
                dto.setSoEZ(tmpArr[2] != null ? tmpArr[2].toString() : null);
                dto.setNgay_ps(tmpArr[3] != null ? Timestamp.valueOf(tmpArr[3].toString()) : null);
                dto.setSoKhachHang(tmpArr[4] != null ? tmpArr[4].toString() : null);
                dto.setNgayKichHoat(tmpArr[5] != null ? Timestamp.valueOf(tmpArr[5].toString()) : null);
                dtoList.add(dto);
            }
        }
        return dtoList;
    }

    private List<ChiTietHangMucPhatSinhKPPDTO> buildDataList4Item3(Object[] resultObject){
        List<ChiTietHangMucPhatSinhKPPDTO> dtoList = new ArrayList<ChiTietHangMucPhatSinhKPPDTO>();
        if(resultObject != null && resultObject[1] != null){
            for(Object object : (List)resultObject[1]){
                Object[] tmpArr = (Object[])object;
                ChiTietHangMucPhatSinhKPPDTO dto = new ChiTietHangMucPhatSinhKPPDTO();
                dto.setLoaiItemId(Constants.ITEM_ID_3);
                dto.setDealer_Code(tmpArr[0] != null ? tmpArr[0].toString() : null);
                dto.setDealer_Name(tmpArr[1] != null ? tmpArr[1].toString() : null);
                dto.setSo_thue_bao(tmpArr[2] != null ? tmpArr[2].toString() : null);
                dto.setNgay_ps(tmpArr[3] != null ? Timestamp.valueOf(tmpArr[3].toString()) : null);
                dto.setCuoc_ps(tmpArr[4] != null ? Double.valueOf(tmpArr[4].toString().trim()) : null);
                dto.setSoDiemQuiDoi(tmpArr[5] != null ? Double.valueOf(tmpArr[5].toString()) : null);
                dto.setNgayNhanTin(tmpArr[6] != null ? Timestamp.valueOf(tmpArr[6].toString()) : null);
                dto.setNgayKichHoat(tmpArr[7] != null ? Timestamp.valueOf(tmpArr[7].toString()) : null);
                dtoList.add(dto);
            }
        }
        return dtoList;
    }
    private List<ChiTietHangMucPhatSinhKPPDTO> buildDataList4Item4(Object[] resultObject){
        List<ChiTietHangMucPhatSinhKPPDTO> dtoList = new ArrayList<ChiTietHangMucPhatSinhKPPDTO>();
        if(resultObject != null && resultObject[1] != null){
            for(Object object : (List)resultObject[1]){
                Object[] tmpArr = (Object[])object;
                ChiTietHangMucPhatSinhKPPDTO dto = new ChiTietHangMucPhatSinhKPPDTO();
                dto.setLoaiItemId(Constants.ITEM_ID_4);
                dto.setDealer_Code(tmpArr[0] != null ? tmpArr[0].toString() : null);
                dto.setDealer_Name(tmpArr[1] != null ? tmpArr[1].toString() : null);
                dto.setSoHoaDon(tmpArr[2] != null ? tmpArr[2].toString() : null);
                dto.setNgayHoaDon(tmpArr[3] != null ? Timestamp.valueOf(tmpArr[3].toString()) : null);
                dto.setMaHangHoa(tmpArr[4] != null ? tmpArr[4].toString() : null);
                dto.setTenHangHoa(tmpArr[5] != null ? tmpArr[5].toString() : null);
                dto.setSoLuong(tmpArr[6] != null ? Integer.valueOf(tmpArr[6].toString()) : null);
                dto.setGiaTri(tmpArr[7] != null ? Double.valueOf(tmpArr[7].toString()) : null);
                dtoList.add(dto);
            }
        }
        return dtoList;
    }

    private List<ChiTietHangMucPhatSinhKPPDTO> buildDataList4Item5(Object[] resultObject){
        List<ChiTietHangMucPhatSinhKPPDTO> dtoList = new ArrayList<ChiTietHangMucPhatSinhKPPDTO>();
        if(resultObject != null && resultObject[1] != null){
            for(Object object : (List)resultObject[1]){
                Object[] tmpArr = (Object[])object;
                ChiTietHangMucPhatSinhKPPDTO dto = new ChiTietHangMucPhatSinhKPPDTO();
                dto.setLoaiItemId(Constants.ITEM_ID_5);
                dto.setDealer_Id(tmpArr[0] != null ? Long.valueOf(tmpArr[0].toString()) : null);
                dto.setDealer_Code(tmpArr[1] != null ? tmpArr[1].toString() : null);
                dto.setDealer_Name(tmpArr[2] != null ? tmpArr[2].toString() : null);
                dto.setSoEZ(tmpArr[3] != null ? tmpArr[3].toString() : null);
                dto.setNgay_ps(tmpArr[4] != null ? Timestamp.valueOf(tmpArr[4].toString()) : null);
                dto.setNoiDungTinNhan(tmpArr[5] != null ? tmpArr[5].toString() : null);
                dto.setKetQua(tmpArr[6] != null ? tmpArr[6].toString() : null);
                dtoList.add(dto);
            }
        }
        return dtoList;
    }

    private List<ChiTietHangMucPhatSinhKPPDTO> buildDataList4Item6(Object[] resultObject){
        List<ChiTietHangMucPhatSinhKPPDTO> dtoList = new ArrayList<ChiTietHangMucPhatSinhKPPDTO>();
        if(resultObject != null && resultObject[1] != null){
            for(Object object : (List)resultObject[1]){
                Object[] tmpArr = (Object[])object;
                ChiTietHangMucPhatSinhKPPDTO dto = new ChiTietHangMucPhatSinhKPPDTO();
                dto.setLoaiItemId(Constants.ITEM_ID_6);
                dto.setDealer_Code(tmpArr[0] != null ? tmpArr[0].toString() : null);
                dto.setDealer_Name(tmpArr[1] != null ? tmpArr[1].toString() : null);
                dto.setSoEZ(tmpArr[2] != null ? tmpArr[2].toString() : null);
                dto.setMaGiaoDich(tmpArr[3] != null ? tmpArr[3].toString() : null);
                dto.setMaGoi(tmpArr[4] != null ? tmpArr[4].toString() : null);
                dto.setTenGoi(tmpArr[5] != null ? tmpArr[5].toString() : null);
                dto.setChuKy(tmpArr[6] != null ? Integer.valueOf(tmpArr[6].toString()) : null);
                dto.setNgay_ps(tmpArr[7] != null ? Timestamp.valueOf(tmpArr[7].toString()) : null);
                dtoList.add(dto);
            }
        }
        return dtoList;
    }

    private List<ChiTietHangMucPhatSinhKPPDTO> buildDataList4Item7(Object[] resultObject){
        List<ChiTietHangMucPhatSinhKPPDTO> dtoList = new ArrayList<ChiTietHangMucPhatSinhKPPDTO>();
        if(resultObject != null && resultObject[1] != null){
            for(Object object : (List)resultObject[1]){
                Object[] tmpArr = (Object[])object;
                ChiTietHangMucPhatSinhKPPDTO dto = new ChiTietHangMucPhatSinhKPPDTO();
                dto.setLoaiItemId(Constants.ITEM_ID_7);
                dto.setDealer_Id(tmpArr[0] != null ? Long.valueOf(tmpArr[0].toString()) : null);
                dto.setDealer_Code(tmpArr[1] != null ? tmpArr[1].toString() : null);
                dto.setDealer_Name(tmpArr[2] != null ? tmpArr[2].toString() : null);
                dto.setSoEZ(tmpArr[3] != null ? tmpArr[3].toString() : null);
                dto.setNgay_ps(tmpArr[4] != null ? Timestamp.valueOf(tmpArr[4].toString()) : null);
                dto.setNoiDungTinNhan(tmpArr[5] != null ? tmpArr[5].toString() : null);
                dto.setKetQua(tmpArr[6] != null ? tmpArr[6].toString() : null);
                dtoList.add(dto);
            }
        }
        return dtoList;
    }
}
