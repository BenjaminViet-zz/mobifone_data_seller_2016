package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.core.business.BranchManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.*;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015.RegistrationTransDTO;
import com.benluck.vms.mobifonedataseller.domain.*;
import com.benluck.vms.mobifonedataseller.session.*;

import javax.ejb.DuplicateKeyException;
import javax.ejb.EJB;
import javax.ejb.ObjectNotFoundException;
import javax.ejb.Stateless;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/25/14
 * Time: 10:25 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "BranchManagementSessionEJB")
public class BranchManagementSessionBean implements BranchManagementLocalBean{
    @EJB
    private CTTichDiemLocalBean ctTichDiemLocalBean;
    @EJB
    private DMChuongTrinhLocalBean dmChuongTrinhLocalBean;
    @EJB
    private ThueBaoPhatTrienMoiLocalBean thueBaoPhatTrienMoiLocalBean;
    @EJB
    private MoneyExchangeBranchLocalBean moneyExchangeBranchLocalBean;
    @EJB
    private MoneyExchangeBranchHistoryLocalBean moneyExchangeBranchHistoryLocalBean;
    @EJB
    private BranchMappingLocalBean branchMappingLocalBean;
    @EJB
    private BranchLocalBean branchLocalBean;
    @EJB
    private ActionLogLocalBean actionLogLocalBean;
    @EJB
    private DepartmentLocalBean departmentLocalBean;

    public BranchManagementSessionBean() {
    }


    @Override
    public Object[] search(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) {
        Object[] resultObject = this.ctTichDiemLocalBean.TraCuuThongTinDoiDiemDBH(properties, firstItem, maxPageItems, sortExpression, sortDirection);
        List<DealerAccountDTO> dtoList = new ArrayList<DealerAccountDTO>();
        for (Object object : (List)resultObject[1]){
            Object[] tmpArr = (Object[])object;
            DealerAccountDTO dto = new DealerAccountDTO();
            RetailDealerDTO dealerDTO = new RetailDealerDTO();
            dealerDTO.setDealer_Id(tmpArr[0] != null ? Long.valueOf(tmpArr[0].toString()) : null);
            dealerDTO.setDealer_code(tmpArr[1] != null ? tmpArr[1].toString() : null);
            dealerDTO.setDealer_name(tmpArr[2] != null ? tmpArr[2].toString() : null);

            dto.setDealer(dealerDTO);
            dto.setCycle(tmpArr[3] != null ? Timestamp.valueOf(tmpArr[3].toString()) : null);
            dto.setProm_point(tmpArr[4] != null ? Integer.valueOf(tmpArr[4].toString()) : null);
            dto.setExchange_amount(tmpArr[5] != null ? Double.valueOf(tmpArr[5].toString()) : null);
            dto.setCycle_lock_status(tmpArr[6] != null ? tmpArr[6].toString() : null);
            dto.setTenQuanHuyen(tmpArr[7] != null ? tmpArr[7].toString() : null);

            dto.setExchange_money_status(tmpArr[8] != null ? tmpArr[8].toString() : null);
            dto.setBranch_Name(tmpArr[9] != null ? tmpArr[9].toString() : null);
            dto.setCondition_status(tmpArr[10] != null ? tmpArr[10].toString().trim() : null);
            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return resultObject;
    }

    @Override
    public Object[] searchPaymentAgency(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) {
        Object[] resultObject = this.thueBaoPhatTrienMoiLocalBean.searchResultOfExchangedUsers(properties, firstItem, maxPageItems, sortExpression, sortDirection);
        List<RegistrationTransDTO> dtoList = new ArrayList<RegistrationTransDTO>();
        for (Object object : (List)resultObject[1]){
            Object[] tmpArr = (Object[])object;
            RegistrationTransDTO dto = new RegistrationTransDTO();
            RetailDealerDTO dealerDTO = new RetailDealerDTO();
            dealerDTO.setBranch_name(tmpArr[0] != null ? tmpArr[0].toString() : null);
            dealerDTO.setDistrict_name(tmpArr[1] != null ? tmpArr[1].toString() : null);
            dealerDTO.setDealer_Id(tmpArr[2] != null ? Long.valueOf(tmpArr[2].toString()) : null);
            dealerDTO.setDealer_code(tmpArr[3] != null ? tmpArr[3].toString() : null);
            dealerDTO.setDealer_name(tmpArr[4] != null ? tmpArr[4].toString() : null);
            dealerDTO.setTax_Code(tmpArr[12] != null ? tmpArr[12].toString() : null);
            dealerDTO.setAddress(tmpArr[13] != null ? tmpArr[13].toString() : null);
            dealerDTO.setContact_name(tmpArr[14] != null ? tmpArr[14].toString() : null);
            dto.setRetailDealer(dealerDTO);

            dto.setEz_Isdn(tmpArr[5] != null ? tmpArr[5].toString() : null);
            dto.setSum_Date(tmpArr[6] != null ? Timestamp.valueOf(tmpArr[6].toString()) : null);
            dto.setPayment_Date(tmpArr[7] != null ? Timestamp.valueOf(tmpArr[7].toString()) : null);
            dto.setTongTienQuyDoi(tmpArr[8] != null ? Double.valueOf(tmpArr[8].toString()) : null);
            dto.setSoTienDuDK(tmpArr[9] != null ? Double.valueOf(tmpArr[9].toString()) : null);
            dto.setPayment_Status(tmpArr[10] != null ? Integer.valueOf(tmpArr[10].toString()) : null);
            dto.setTotalTrans_ThoaDKCT(tmpArr[11] != null ? Integer.valueOf(tmpArr[11].toString()) : null);
            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return resultObject;
    }

    @Override
    public List<BranchDTO> findByCTCode(String ctCode) throws ObjectNotFoundException{
        DMChuongTrinhEntity dmChuongTrinhEntity = this.dmChuongTrinhLocalBean.findEqualUnique("code", ctCode);
        List<BranchDTO> dtoList = new ArrayList<BranchDTO>();
        List resultSet = this.branchLocalBean.findAllBranchesByDBLink(dmChuongTrinhEntity.getDbLinkName());
        for (Object object : resultSet){
            Object[] tmpArr = (Object[])object;
            BranchDTO dto = new BranchDTO();
            dto.setBranch_Id(tmpArr[0] != null ? Long.valueOf(tmpArr[0].toString()) : null);
            dto.setBranch_code(tmpArr[1] != null ? tmpArr[1].toString() : null);
            dto.setBranch_name(tmpArr[2] != null ? tmpArr[2].toString() : null);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public BranchDTO findByChiNhanhIdAndCTCode(Long chiNhanhId, String ctCode) throws ObjectNotFoundException {
        BranchMappingEntity branchMappingEntity = branchMappingLocalBean.findByUniqueProperties(chiNhanhId, ctCode);
        Object[] resultSet = this.branchLocalBean.findByBranchIdAndDBLink(branchMappingEntity.getBranchId(), branchMappingEntity.getChuongTrinh().getDbLinkName());
        BranchDTO dto = new BranchDTO();
        dto.setBranch_Id(resultSet[0] != null ? Long.valueOf(resultSet[0].toString()) : null);
        dto.setBranch_code(resultSet[1] != null ? resultSet[1].toString() : null);
        dto.setBranch_name(resultSet[2] != null ? resultSet[2].toString() : null);
        return dto;
    }

    @Override
    public boolean checkPaymentStatusByDealerIdAndSumDate(Long dealer_Id, List<Date> sumDateList, String soEZ) {
        return this.thueBaoPhatTrienMoiLocalBean.checkPaymentStatusByDealerIdAndSumDate(dealer_Id, sumDateList, soEZ);
    }

    @Override
    public Integer getTotalTrans_ThoaDKCT(Long dealer_Id, List<Date> sumDateList, String soEZ) {
        return this.thueBaoPhatTrienMoiLocalBean.getTotalTrans_ThoaDKCT(dealer_Id, sumDateList, soEZ);
    }

    @Override
    public BranchDTO findByDepartmentIdAndCTCode(Long departmentId, String ctCode) throws ObjectNotFoundException {
        BranchMappingEntity branchMappingEntity = this.branchMappingLocalBean.findByDepartmentIdAndCtCode(departmentId, ctCode);
        Object[] resultSet = this.branchLocalBean.findByBranchIdAndDBLink(branchMappingEntity.getBranchId(), branchMappingEntity.getChuongTrinh().getDbLinkName());
        BranchDTO dto = new BranchDTO();
        dto.setBranch_Id(resultSet[0] != null ? Long.valueOf(resultSet[0].toString()) : null);
        dto.setBranch_code(resultSet[1] != null ? resultSet[1].toString() : null);
        dto.setBranch_name(resultSet[2] != null ? resultSet[2].toString() : null);
        return dto;
    }


    @Override
    public List<DistrictDTO> findByBranchId(Long branchId) {
        List resultSet = this.ctTichDiemLocalBean.findDistrictListByBranchId(branchId);
        List<DistrictDTO> dtoList = new ArrayList<DistrictDTO>();
        for (Object object : resultSet){
            Object[] tmpArr = (Object[])object;
            DistrictDTO dto = new DistrictDTO();
            dto.setDistrict_Id(tmpArr[0] != null ? Long.valueOf(tmpArr[0].toString()) : null);
            dto.setDistrict_code(tmpArr[1] != null ? tmpArr[1].toString() : null);
            dto.setDistrict_name(tmpArr[2] != null ? tmpArr[2].toString() : null);
            dto.setCity_Id(tmpArr[3] != null ? Long.valueOf(tmpArr[3].toString()) : null);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public List<BranchDTO> findAll_tdcg() {
        List<BranchDTO> dtoList = new ArrayList<BranchDTO>();
        List resultSet = this.ctTichDiemLocalBean.findAllBranches();
        for (Object object : resultSet){
            Object[] tmpArr = (Object[])object;
            BranchDTO dto = new BranchDTO();
            dto.setBranch_Id(tmpArr[0] != null ? Long.valueOf(tmpArr[0].toString()) : null);
            dto.setBranch_code(tmpArr[1] != null ? tmpArr[1].toString() : null);
            dto.setBranch_name(tmpArr[2] != null ? tmpArr[2].toString() : null);
            dtoList.add(dto);
        }

        return dtoList;
    }

    @Override
    public List<BranchDTO> findAll_tbptm() {
        List<BranchDTO> dtoList = new ArrayList<BranchDTO>();
        List resultSet = this.thueBaoPhatTrienMoiLocalBean.findAllBranches();
        for (Object object : resultSet){
            Object[] tmpArr = (Object[])object;
            BranchDTO dto = new BranchDTO();
            dto.setBranch_Id(tmpArr[0] != null ? Long.valueOf(tmpArr[0].toString()) : null);
            dto.setBranch_code(tmpArr[1] != null ? tmpArr[1].toString() : null);
            dto.setBranch_name(tmpArr[2] != null ? tmpArr[2].toString() : null);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public List<BranchDTO> findByChuongTrinhId(Long chuongTrinhId) throws ObjectNotFoundException{
        List<BranchDTO> dtoList = new ArrayList<BranchDTO>();
        String dbLinkName = dmChuongTrinhLocalBean.findById(chuongTrinhId).getDbLinkName();
        List resultSet = this.branchLocalBean.findAllBranchesByDBLink(dbLinkName);
        for (Object object : resultSet){
            Object[] tmpArr = (Object[])object;
            BranchDTO dto = new BranchDTO();
            dto.setBranch_Id(tmpArr[0] != null ? Long.valueOf(tmpArr[0].toString()) : null);
            dto.setBranch_code(tmpArr[1] != null ? tmpArr[1].toString() : null);
            dto.setBranch_name(tmpArr[2] != null ? tmpArr[2].toString() : null);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public void moneyExchangeAgency(Long shopUserId, String[] checkList) throws ObjectNotFoundException, DuplicateKeyException{
        for(String complexString : checkList){
            try{
                Long dealer_Id = Long.valueOf(complexString.split("\\$")[0].toString());
                Integer cycle = Integer.valueOf(complexString.split("\\$")[1].toString());
                if(dealer_Id != null && cycle != null){
                    MoneyExchangeBranchEntity entity = this.moneyExchangeBranchLocalBean.findByDealerId(dealer_Id);
                    if(entity == null){
                        entity = new MoneyExchangeBranchEntity();

                        Object[] dealerObjectArr = this.ctTichDiemLocalBean.findDealerInfoByDealerId(dealer_Id);
                        if(dealerObjectArr != null && dealerObjectArr.length > 0){
                            entity.setDealerId(dealer_Id);
                            entity.setDealer_code(dealerObjectArr[1] != null ? dealerObjectArr[1].toString() : null);
                            entity.setDealer_name(dealerObjectArr[2] != null ? dealerObjectArr[2].toString() : null);
                        }else{
                            throw new ObjectNotFoundException("Object not found for Dealer with Id: " + dealer_Id);
                        }

                        UserEntity exchangeUserEntity = new UserEntity();
                        exchangeUserEntity.setUserId(shopUserId);
                        entity.setExchangeUser(exchangeUserEntity);

                        entity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
                        entity = this.moneyExchangeBranchLocalBean.save(entity);
                    }
                    addMoneyExchangeBranchHistory(entity, cycle);
                    updateExchangeMoneyStatus(dealer_Id, cycle);
                }
            }catch (Exception e){
                throw new ObjectNotFoundException("Du lieu de doi tien khong hop le voi chuoi~: " + complexString);
            }
        }
    }


    @Override
    public void paymentAgency(Long exchangeUserId, String[] checkList, Timestamp paymentDate) throws ObjectNotFoundException, DuplicateKeyException {
        for(String complexStr : checkList){
            try{
                Long dealer_Id = Long.valueOf(complexStr.split("\\_")[0].toString());
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String soEZ = complexStr.split("\\_")[1];
                String sum_Date = complexStr.split("\\_")[3].toString();

                List<Date> sumDateList = new ArrayList<>();
                for (String s : sum_Date.split("\\#")){
                    sumDateList.add(df.parse(s));
                }
                updatePaymentStatus(dealer_Id, sumDateList, paymentDate, soEZ);
                addPaymentHistory(exchangeUserId, dealer_Id, paymentDate, soEZ);
            }catch (Exception e){
                throw new ObjectNotFoundException("Du lieu de doi tien khong hop le voi chuoi~: " + complexStr);
            }
        }
    }

    @Override
    public Object[] searchPaymentHistoryAtAgency(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) {
        Object[] resultObject = this.moneyExchangeBranchHistoryLocalBean.searchPaymentHistoryAtAgency(properties, firstItem, maxPageItems, sortExpression, sortDirection);
        List<MoneyExchangeBranchHistoryDTO> dtoList = new ArrayList<MoneyExchangeBranchHistoryDTO>();
        for(Object object : (List)resultObject[1]){
            Object[] tmpArr = (Object[])object;
            MoneyExchangeBranchHistoryDTO dto = new MoneyExchangeBranchHistoryDTO();
            dto.setMoneyExchangeBranchHistoryId(tmpArr[0] != null ? Long.valueOf(tmpArr[0].toString()) : null);

            MoneyExchangeBranchDTO moneyExchangeBranchDTO = new MoneyExchangeBranchDTO();
            moneyExchangeBranchDTO.setMoneyExchangeBranchId(tmpArr[1] != null ? Long.valueOf(tmpArr[1].toString()) : null);
            dto.setMoneyExchangeBranch(moneyExchangeBranchDTO);

            dto.setCycle(tmpArr[2] != null ? Integer.valueOf(tmpArr[2].toString()) : null);
            dto.setExchangeAmount(tmpArr[3] != null ? Double.valueOf(tmpArr[3].toString()) : null);
            dto.setCreatedDate(tmpArr[4] != null ? Timestamp.valueOf(tmpArr[4].toString()) : null);

            RetailDealerDTO retailDealerDTO = new RetailDealerDTO();
            retailDealerDTO.setDealer_Id(tmpArr[5] != null ? Long.valueOf(tmpArr[5].toString()) : null);
            retailDealerDTO.setDealer_code(tmpArr[6] != null ? tmpArr[6].toString() : null);
            retailDealerDTO.setDealer_name(tmpArr[7] != null ? tmpArr[7].toString() : null);
            dto.setRetailDealer(retailDealerDTO);

            BranchDTO branchDTO = new BranchDTO();
            branchDTO.setBranch_Id(tmpArr[8] != null ? Long.valueOf(tmpArr[8].toString()) : null);
            branchDTO.setBranch_code(tmpArr[9] != null ? tmpArr[9].toString() : null);
            branchDTO.setBranch_name(tmpArr[10] != null ? tmpArr[10].toString() : null);
            dto.setBranch(branchDTO);

            DistrictDTO districtDTO = new DistrictDTO();
            districtDTO.setDistrict_Id(tmpArr[11] != null ? Long.valueOf(tmpArr[11].toString()) : null);
            districtDTO.setDistrict_code(tmpArr[12] != null ? tmpArr[12].toString() : null);
            districtDTO.setDistrict_name(tmpArr[13] != null ? tmpArr[13].toString() : null);
            dto.setDistrict(districtDTO);
            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return  resultObject;
    }

    @Override
    public List<RetailDealerDTO> findRetailDealerListByBranchId(Long branchId) {
        List resultSet = this.ctTichDiemLocalBean.findRetailDealerListByBranchId(branchId);
        List<RetailDealerDTO> dtoList = new ArrayList<RetailDealerDTO>();
        for(Object object : resultSet){
            Object[] tmpArr = (Object[])object;
            RetailDealerDTO dto = new RetailDealerDTO();
            dto.setDealer_Id(tmpArr[0] != null ? Long.valueOf(tmpArr[0].toString()) : null);
            dto.setDealer_code(tmpArr[1] != null ? tmpArr[1].toString() : null);
            dto.setDealer_name(tmpArr[2] != null ? tmpArr[2].toString() : null);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public List<RetailDealerDTO> findRetailDealerListByDistrictId(Long district_Id) {
        List resultSet = this.ctTichDiemLocalBean.findRetailDealerList(null, district_Id);
        List<RetailDealerDTO> dtoList = new ArrayList<RetailDealerDTO>();
        for (Object object : resultSet){
            Object[] tmpArr = (Object[])object;
            RetailDealerDTO dealerDTO = new RetailDealerDTO();
            dealerDTO.setDealer_Id(tmpArr[0] != null ? Long.valueOf(tmpArr[0].toString()) : null);
            dealerDTO.setDealer_code(tmpArr[1] != null ? tmpArr[1].toString() : null);
            dealerDTO.setDealer_name(tmpArr[2] != null ? tmpArr[2].toString() : null);
            dtoList.add(dealerDTO);
        }
        return dtoList;
    }

    @Override
    public BranchDTO findBranchById_tdcg(Long branchId) throws ObjectNotFoundException{
        Object[] resultSet = this.ctTichDiemLocalBean.findBranchById(branchId);
        BranchDTO dto = new BranchDTO();
        dto.setBranch_Id(resultSet[0] != null ? Long.valueOf(resultSet[0].toString()) : null);
        dto.setBranch_code(resultSet[1] != null ? resultSet[1].toString() : null);
        dto.setBranch_name(resultSet[2] != null ? resultSet[2].toString() : null);
        return dto;
    }

    private void addMoneyExchangeBranchHistory(MoneyExchangeBranchEntity moneyExchangeBranchEntity, Integer cycle) throws ObjectNotFoundException, DuplicateKeyException{
        MoneyExchangeBranchHistoryEntity entity = new MoneyExchangeBranchHistoryEntity();
        entity.setMoneyExchangeBranch(moneyExchangeBranchEntity);
        entity.setCycle(cycle);
        entity.setCreatedDate(new Timestamp(System.currentTimeMillis()));

        Object[] dealerAccountObjectArr = this.ctTichDiemLocalBean.findDealerAccountByDealerIdAndCycle(moneyExchangeBranchEntity.getDealerId(), cycle);
        entity.setExchangeAmount(dealerAccountObjectArr[2] != null ? Double.valueOf(dealerAccountObjectArr[2].toString()) : 0);
        entity = this.moneyExchangeBranchHistoryLocalBean.save(entity);
    }

    private void updateExchangeMoneyStatus(Long dealer_Id, Integer cycle) throws ObjectNotFoundException, DuplicateKeyException{
        this.ctTichDiemLocalBean.updateDealerAccountExchangeMoneyStatusByDealerIdAndCycle(dealer_Id, cycle);
    }

    /**
     * Update status of the dealer.
     * @param dealer_Id
     * @param sumDateList
     * @param paymentDate
     * @param soEZ
     * @throws ObjectNotFoundException
     * @throws DuplicateKeyException
     */
    private void updatePaymentStatus(Long dealer_Id, List<Date> sumDateList, Timestamp paymentDate, String soEZ) throws ObjectNotFoundException, DuplicateKeyException{
        this.thueBaoPhatTrienMoiLocalBean.updatePaymentStatus(dealer_Id, sumDateList, paymentDate, soEZ);
    }

    /**
     * Track log for payment for Thue Bao PTM 2015
     * @param exchangeUserId
     * @param dealer_Id
     * @throws DuplicateKeyException
     */
    private void addPaymentHistory(Long exchangeUserId, Long dealer_Id, Timestamp ngayChiTra, String soEZ) throws DuplicateKeyException {
        ActionLogEntity entity = new ActionLogEntity();
        DMChuongTrinhEntity dmChuongTrinhEntity = null;
        try{
            dmChuongTrinhEntity = this.dmChuongTrinhLocalBean.findEqualUnique("code", "TB_PTM_2015");
        }catch (ObjectNotFoundException oe){}

        entity.setChuongTrinh(dmChuongTrinhEntity);

        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(exchangeUserId);
        entity.setNguoiThaoTac(userEntity);

        entity.setTableLog("REGISTRATION_TRANS");
        entity.setCreatedTime(new Timestamp(System.currentTimeMillis()));
        entity.setColumnIdLogName("DEALER_ID");
        entity.setColumnIdLogValue(dealer_Id);
        entity.setSupportData("SOEZ=" +soEZ+ ";PAYMENT_DATE=" + ngayChiTra.toString().substring(0, 10));
        entity = this.actionLogLocalBean.save(entity);
    }
}
