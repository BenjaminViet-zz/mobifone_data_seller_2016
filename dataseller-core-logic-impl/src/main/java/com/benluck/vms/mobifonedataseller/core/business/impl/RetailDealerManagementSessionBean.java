package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.core.business.RetailDealerManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.DistrictDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.RetailDealerDTO;
import com.benluck.vms.mobifonedataseller.domain.DMChuongTrinhEntity;
import com.benluck.vms.mobifonedataseller.session.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.ejb.EJB;
import javax.ejb.ObjectNotFoundException;
import javax.ejb.Stateless;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 11/7/14
 * Time: 12:45 AM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "RetailDealerManagementSessionEJB")
public class RetailDealerManagementSessionBean implements RetailDealerManagementLocalBean{
    private Log log = LogFactory.getLog(RetailDealerManagementSessionBean.class);
    @EJB
    private CTTichDiemLocalBean ctTichDiemLocalBean;
    @EJB
    private ThueBaoPhatTrienMoiLocalBean thueBaoPhatTrienMoiLocalBean;
    @EJB
    private RetailDealerLocalBean retailDealerLocalBean;
    @EJB
    private DMChuongTrinhLocalBean dmChuongTrinhLocalBean;
    @EJB
    private BranchMappingLocalBean branchMappingLocalBean;

    public RetailDealerManagementSessionBean() {
    }

    @Override
    public List<RetailDealerDTO> findAll_tdcg() {
        List<RetailDealerDTO> dtoList = new ArrayList<RetailDealerDTO>();
        List resultSet = this.ctTichDiemLocalBean.findAllRetailDealers();
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
    public List<RetailDealerDTO> findByBranchId_tdcg(Long branchId) {
        List<RetailDealerDTO> dtoList = new ArrayList<RetailDealerDTO>();
        List resultSet = this.ctTichDiemLocalBean.findRetailDealerListByBranchId(branchId);
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
    public List<RetailDealerDTO> findByDistrictId_tdcg(Long district_Id) {
        List<RetailDealerDTO> dtoList = new ArrayList<RetailDealerDTO>();
        List resultSet = this.ctTichDiemLocalBean.findRetailDealerListByDistrictId(district_Id);
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
    public List<RetailDealerDTO> findByDistrictIdAndByBranchId_tbptm(Long branchId, Long districtId) {
        List resultSet = this.thueBaoPhatTrienMoiLocalBean.findByDistrictIdAndByBranchId(branchId, districtId);
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
    public RetailDealerDTO findById_tdcg(Long dealer_Id) throws ObjectNotFoundException {
        Object[] resultSet = this.ctTichDiemLocalBean.findRetailDealerInfoById(dealer_Id);
        RetailDealerDTO dto = new RetailDealerDTO();
        dto.setDealer_Id(resultSet[0] != null ? Long.valueOf(resultSet[0].toString()) : null);
        dto.setDealer_code(resultSet[1] != null ? resultSet[1].toString() : null);
        dto.setDealer_name(resultSet[2] != null ? resultSet[2].toString() : null);
        return dto;
    }

    @Override
    public List<RetailDealerDTO> findAll_tbptm() {
        List<RetailDealerDTO> dtoList = new ArrayList<RetailDealerDTO>();
        List resultSet = this.thueBaoPhatTrienMoiLocalBean.findAllRetailDealers();
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
    public List<RetailDealerDTO> findByBranchId_tbptm(Long branchId) {
        List<RetailDealerDTO> dtoList = new ArrayList<RetailDealerDTO>();
        List resultSet = this.thueBaoPhatTrienMoiLocalBean.findRetailDealerListByBranchId(branchId);
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
    public List<RetailDealerDTO> findByDistrictId_tbptm(Long district_Id) {
        List<RetailDealerDTO> dtoList = new ArrayList<RetailDealerDTO>();
        List resultSet = this.thueBaoPhatTrienMoiLocalBean.findRetailDealerListByDistrictId(district_Id);
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
    public Object[] search_tbptm(RetailDealerDTO retailDealerDTO, String sortExpression, String sortDirection, Integer firstItems, Integer maxPageItems) {
        Map<String, Object> properties = new HashMap<String, Object>();

        if (retailDealerDTO.getBranch() != null && retailDealerDTO.getBranch().getBranch_Id() != null) {
            properties.put("branch_Id", retailDealerDTO.getBranch().getBranch_Id());
        }
        if (retailDealerDTO.getDistrict() != null && retailDealerDTO.getDistrict().getDistrict_Id() != null) {
            properties.put("district_Id", retailDealerDTO.getDistrict().getDistrict_Id());
        }
        if (retailDealerDTO.getDealer_Id() != null){
            properties.put("dealer_Id", retailDealerDTO.getDealer_Id());
        }
        if (StringUtils.isNotBlank(retailDealerDTO.getEz_isdn())){
            properties.put("ez", retailDealerDTO.getEz_isdn());
        }

        Object[] res = this.thueBaoPhatTrienMoiLocalBean.searchDealerList(properties, firstItems, maxPageItems, sortExpression, sortDirection);
        List<RetailDealerDTO> dtoList = new ArrayList<RetailDealerDTO>();
        List resultSet = (List)res[0];
        for (Object object : resultSet) {
            Object[] tmpArr = (Object[])object;
            RetailDealerDTO dto = new RetailDealerDTO();

            dto.setDealer_Id(tmpArr[0] != null ? Long.valueOf(tmpArr[0].toString()) : null);
            dto.setDealer_code(tmpArr[1] != null ? tmpArr[1].toString() : null);
            dto.setDealer_name(tmpArr[2] != null ? tmpArr[2].toString() : null);
            dto.setDistrict_name(tmpArr[3] != null ? tmpArr[3].toString() : null);
            dto.setBranch_name(tmpArr[4] != null ? tmpArr[4].toString() : null);
            dto.setContact_name(tmpArr[5] != null ? tmpArr[5].toString() : null);
            dto.setEmail(tmpArr[6] != null ? tmpArr[6].toString() : null);
            dto.setAddress(tmpArr[7] != null ? tmpArr[7].toString() : null);
            dto.setEz_isdn(tmpArr[8] != null ? tmpArr[8].toString() : null);
            dto.setCreatedDate(tmpArr[9] != null ? Timestamp.valueOf(tmpArr[9].toString()) : null);
            dto.setPropertiesCode(tmpArr[10] != null ? tmpArr[10].toString() : null);
            dto.setPrimary(tmpArr[11] != null ? tmpArr[11].toString() : null);
            dtoList.add(dto);
        }
        res[0] = dtoList;
        return res;
    }

    @Override
    public List<RetailDealerDTO> findAllByCTCode(String ctCode) throws ObjectNotFoundException {
        DMChuongTrinhEntity dmChuongTrinhEntity = this.dmChuongTrinhLocalBean.findEqualUnique("code", ctCode);
        List resultSet = this.retailDealerLocalBean.findAllByDBLink(dmChuongTrinhEntity.getDbLinkName());
        List<RetailDealerDTO> dtoList = new ArrayList<RetailDealerDTO>();
        for (Object object : resultSet) {
            Object[] tmpArr = (Object[])object;
            RetailDealerDTO dto = new RetailDealerDTO();
            dto.setDealer_Id(tmpArr[0] != null ? Long.valueOf(tmpArr[0].toString()) : null);
            dto.setDealer_code(tmpArr[1] != null ? tmpArr[1].toString() : null);
            dto.setDealer_name(tmpArr[2] != null ? tmpArr[2].toString() : null);
            if(tmpArr[3] != null){
                DistrictDTO districtDTO = new DistrictDTO();
                districtDTO.setDistrict_Id(Long.valueOf(tmpArr[3].toString()));
                dto.setDistrict(districtDTO);
            }
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public List<RetailDealerDTO> findByBranchIdAndCTCode(Long branchId, String ctCode) throws ObjectNotFoundException {
        DMChuongTrinhEntity dmChuongTrinhEntity = this.dmChuongTrinhLocalBean.findEqualUnique("code", ctCode);
        List resultSet = this.retailDealerLocalBean.findByBranchIdAndDBLink(branchId, dmChuongTrinhEntity.getDbLinkName());
        List<RetailDealerDTO> dtoList = new ArrayList<RetailDealerDTO>();
        for (Object object : resultSet) {
            Object[] tmpArr = (Object[])object;
            RetailDealerDTO dto = new RetailDealerDTO();
            dto.setDealer_Id(tmpArr[0] != null ? Long.valueOf(tmpArr[0].toString()) : null);
            dto.setDealer_code(tmpArr[1] != null ? tmpArr[1].toString() : null);
            dto.setDealer_name(tmpArr[2] != null ? tmpArr[2].toString() : null);
            if(tmpArr[3] != null){
                DistrictDTO districtDTO = new DistrictDTO();
                districtDTO.setDistrict_Id(Long.valueOf(tmpArr[3].toString()));
                dto.setDistrict(districtDTO);
            }
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public List<RetailDealerDTO> findByDistrictIdAndCTCode(Long district_Id, String ctCode) throws ObjectNotFoundException {
        DMChuongTrinhEntity dmChuongTrinhEntity = this.dmChuongTrinhLocalBean.findEqualUnique("code", ctCode);
        List resultSet = this.retailDealerLocalBean.findByDistrictIdAndDBLink(district_Id, dmChuongTrinhEntity.getDbLinkName());
        List<RetailDealerDTO> dtoList = new ArrayList<RetailDealerDTO>();
        for (Object object : resultSet) {
            Object[] tmpArr = (Object[])object;
            RetailDealerDTO dto = new RetailDealerDTO();
            dto.setDealer_Id(tmpArr[0] != null ? Long.valueOf(tmpArr[0].toString()) : null);
            dto.setDealer_code(tmpArr[1] != null ? tmpArr[1].toString() : null);
            dto.setDealer_name(tmpArr[2] != null ? tmpArr[2].toString() : null);
            if(tmpArr[3] != null){
                DistrictDTO districtDTO = new DistrictDTO();
                districtDTO.setDistrict_Id(Long.valueOf(tmpArr[3].toString()));
                dto.setDistrict(districtDTO);
            }
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public RetailDealerDTO findByEzAndCtCode_tbptm(RetailDealerDTO retailDealerDTO, String ctCode) throws ObjectNotFoundException{
        Map<String, Object> properties = new HashMap<String, Object>();

        if (retailDealerDTO.getDealer_code() != null){
            properties.put("dealercode", retailDealerDTO.getDealer_code());
        }
        if (retailDealerDTO.getBranch() != null && retailDealerDTO.getBranch().getBranch_code() != null){
            properties.put("branchcode", retailDealerDTO.getBranch().getBranch_code());
        }
        if (retailDealerDTO.getDistrict() != null && retailDealerDTO.getDistrict().getDistrict_code() != null){
            properties.put("districtcode", retailDealerDTO.getDistrict().getDistrict_code());
        }
        if (StringUtils.isNotBlank(retailDealerDTO.getEz_isdn())){
            properties.put("ez", retailDealerDTO.getEz_isdn());
        }

        DMChuongTrinhEntity dmChuongTrinhEntity = this.dmChuongTrinhLocalBean.findEqualUnique("code", ctCode);
        Object[] resultSet = this.retailDealerLocalBean.findByEzAndDBLink(properties, dmChuongTrinhEntity.getDbLinkName());

        RetailDealerDTO dto = new RetailDealerDTO();
        dto.setDealer_Id(resultSet[0] != null ? Long.valueOf(resultSet[0].toString()) : null);
        dto.setDealer_code(resultSet[1] != null ? resultSet[1].toString() : null);
        dto.setDealer_name(resultSet[2] != null ? resultSet[2].toString() : null);
        return dto;
    }

    @Override
    public void saveImport(List<RetailDealerDTO> listUsers) {
        for (RetailDealerDTO retailDealerDTO : listUsers){
            if(!this.thueBaoPhatTrienMoiLocalBean.checkIfHaveDocBySoEZ(retailDealerDTO.getEz_isdn(), retailDealerDTO.getDealer_code())){
                this.thueBaoPhatTrienMoiLocalBean.saveImportDealerSigned(null, retailDealerDTO.getEz_isdn(), retailDealerDTO.getDealer_code());
            }
        }
    }

    @Override
    public Boolean checkIfDealerHaveDOC(String dealer_Code, String soEz) {
       return this.thueBaoPhatTrienMoiLocalBean.checkIfHaveDocBySoEZ(soEz, dealer_Code);
    }

    @Override
    public RetailDealerDTO findBySoEZAndCTCode(String soEZ, String ctCode) {
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("ez", soEZ);

        try{
            DMChuongTrinhEntity dmChuongTrinhEntity = this.dmChuongTrinhLocalBean.findEqualUnique("code", ctCode);

            Object[] resultSet = this.retailDealerLocalBean.findBySoEZAndDBLinkName(soEZ, dmChuongTrinhEntity.getDbLinkName());
            RetailDealerDTO dto = new RetailDealerDTO();
            dto.setEz_isdn(resultSet[0] != null ? resultSet[0].toString() : null);
            dto.setDealer_code(resultSet[1] != null ? resultSet[1].toString() : null);
            dto.setDealer_name(resultSet[2] != null ? resultSet[2].toString() : null);
            dto.setCreatedDate(resultSet[3] != null ? Timestamp.valueOf(resultSet[3].toString()) : null);
            dto.setDistrict_name(resultSet[4] != null ? resultSet[4].toString() : null);
            return dto;
        }catch (ObjectNotFoundException oe){
            return null;
        }
    }
}
