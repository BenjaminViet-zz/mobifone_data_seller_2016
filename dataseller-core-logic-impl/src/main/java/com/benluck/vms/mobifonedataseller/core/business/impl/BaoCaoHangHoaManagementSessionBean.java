package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.core.business.BaoCaoHangHoaManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.*;
import com.benluck.vms.mobifonedataseller.session.CTTichDiemLocalBean;

import javax.ejb.EJB;
import javax.ejb.ObjectNotFoundException;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/29/14
 * Time: 11:25 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "BaoCaoHangHoaManagementSessionEJB")
public class BaoCaoHangHoaManagementSessionBean implements BaoCaoHangHoaManagementLocalBean{
    @EJB
    private CTTichDiemLocalBean ctTichDiemLocalBean;

    public BaoCaoHangHoaManagementSessionBean() {
    }

    @Override
    public Object[] baoCaoHangHoaTheoDaiLy(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) {
        Object[] resultObject = this.ctTichDiemLocalBean.baoCaoHangHoaTheoDaiLy(properties, firstItem, maxPageItems, sortExpression, sortDirection);
        List<BaoCaoHangHoaDTO> dtoList = new ArrayList<BaoCaoHangHoaDTO>();
        for (Object object : (List)resultObject[1]){
            Object[] tmpArr = (Object[])object;
            BaoCaoHangHoaDTO dto = new BaoCaoHangHoaDTO();
            dto.setBranch_Name(tmpArr[0] != null ? tmpArr[0].toString() : null);
            dto.setDistrict_Name(tmpArr[1] != null ? tmpArr[1].toString() : null);
            dto.setDealer_Id(tmpArr[2] != null ? Long.valueOf(tmpArr[2].toString()) : null);
            dto.setDealer_Code(tmpArr[3] != null ? tmpArr[3].toString() : null);
            dto.setDealer_Name(tmpArr[4] != null ? tmpArr[4].toString() : null);
            dto.setCard_quantity(tmpArr[5] != null ? Double.valueOf(tmpArr[5].toString()) : null);
            dto.setKit_quantity(tmpArr[6] != null ? Double.valueOf(tmpArr[6].toString()) : null);
            dto.setBhtt_quantity(tmpArr[7] != null ? Double.valueOf(tmpArr[7].toString()) : null);
            dto.setBhtt_psc_25(tmpArr[8] != null ? Double.valueOf(tmpArr[8].toString()) : null);
            dto.setPsc_amount(tmpArr[9] != null ? Double.valueOf(tmpArr[9].toString()) : null);
            dto.setGtkh_quantity(tmpArr[10] != null ? Double.valueOf(tmpArr[10].toString()) : null);
            dto.setGtkh_psc_25k(tmpArr[11] != null ? Double.valueOf(tmpArr[11].toString()) : null);
            dto.setVas_quantity(tmpArr[12] != null ? Double.valueOf(tmpArr[12].toString()) : null);
            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return resultObject;
    }

    @Override
    public Object[] baoCaoHangHoaTheoQuanHuyen(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) {
        Object[] resultObject = this.ctTichDiemLocalBean.baoCaoHangHoaTheoQuanHuyen(properties, firstItem, maxPageItems, sortExpression, sortDirection);
        List<BaoCaoHangHoaDTO> dtoList = new ArrayList<BaoCaoHangHoaDTO>();
        for (Object object : (List)resultObject[1]){
            Object[] tmpArr = (Object[])object;
            BaoCaoHangHoaDTO dto = new BaoCaoHangHoaDTO();
            dto.setBranch_Name(tmpArr[0] != null ? tmpArr[0].toString() : null);
            dto.setDistrict_Name(tmpArr[1] != null ? tmpArr[1].toString() : null);
            dto.setCard_quantity(tmpArr[2] != null ? Double.valueOf(tmpArr[2].toString()) : null);
            dto.setKit_quantity(tmpArr[3] != null ? Double.valueOf(tmpArr[3].toString()) : null);
            dto.setBhtt_quantity(tmpArr[4] != null ? Double.valueOf(tmpArr[4].toString()) : null);
            dto.setBhtt_psc_25(tmpArr[5] != null ? Double.valueOf(tmpArr[5].toString()) : null);
            dto.setPsc_amount(tmpArr[6] != null ? Double.valueOf(tmpArr[6].toString()) : null);
            dto.setGtkh_quantity(tmpArr[7] != null ? Double.valueOf(tmpArr[7].toString()) : null);
            dto.setGtkh_psc_25k(tmpArr[8] != null ? Double.valueOf(tmpArr[8].toString()) : null);
            dto.setVas_quantity(tmpArr[9] != null ? Double.valueOf(tmpArr[9].toString()) : null);
            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return resultObject;
    }

    @Override
    public Object[] baoCaoHangHoaTheoChiNhanh(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) {
        Object[] resultObject = this.ctTichDiemLocalBean.baoCaoHangHoaTheoChiNhanh(properties, firstItem, maxPageItems, sortExpression, sortDirection);
        List<BaoCaoHangHoaDTO> dtoList = new ArrayList<BaoCaoHangHoaDTO>();
        for (Object object : (List)resultObject[1]){
            Object[] tmpArr = (Object[])object;
            BaoCaoHangHoaDTO dto = new BaoCaoHangHoaDTO();
            dto.setBranch_Name(tmpArr[0] != null ? tmpArr[0].toString() : null);
            dto.setCard_quantity(tmpArr[1] != null ? Double.valueOf(tmpArr[1].toString()) : null);
            dto.setKit_quantity(tmpArr[2] != null ? Double.valueOf(tmpArr[2].toString()) : null);
            dto.setBhtt_quantity(tmpArr[3] != null ? Double.valueOf(tmpArr[3].toString()) : null);
            dto.setBhtt_psc_25(tmpArr[4] != null ? Double.valueOf(tmpArr[4].toString()) : null);
            dto.setPsc_amount(tmpArr[5] != null ? Double.valueOf(tmpArr[5].toString()) : null);
            dto.setGtkh_quantity(tmpArr[6] != null ? Double.valueOf(tmpArr[6].toString()) : null);
            dto.setGtkh_psc_25k(tmpArr[7] != null ? Double.valueOf(tmpArr[7].toString()) : null);
            dto.setVas_quantity(tmpArr[8] != null ? Double.valueOf(tmpArr[8].toString()) : null);
            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return resultObject;
    }

    @Override
    public List<BranchDTO> findAllBranches() {
        List resultObject = this.ctTichDiemLocalBean.findAllBranches();
        List<BranchDTO> dtoList = new ArrayList<BranchDTO>();
        for (Object object : resultObject){
            Object[] tmpArr = (Object[])object;
            BranchDTO branchDTO = new BranchDTO();
            branchDTO.setBranch_Id(tmpArr[0] != null ? Long.parseLong(tmpArr[0].toString()) : null);
            branchDTO.setBranch_code(tmpArr[1] != null ? tmpArr[1].toString() : null);
            branchDTO.setBranch_name(tmpArr[2] != null ? tmpArr[2].toString() : null);
            dtoList.add(branchDTO);
        }
        return dtoList;
    }

    @Override
    public BranchDTO findBranchById(Long branchId) throws ObjectNotFoundException{
        Object[] resultSet = this.ctTichDiemLocalBean.findBranchById(branchId);
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
            dto.setDistrict_Id(tmpArr[0] != null ? Long.parseLong(tmpArr[0].toString()) : null);
            dto.setDistrict_code(tmpArr[1] != null ? tmpArr[1].toString() : null);
            dto.setDistrict_name(tmpArr[2] != null ? tmpArr[2].toString() : null);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public List<RetailDealerDTO> findRetailDealerList(Long branch_Id, Long district_Id) {
        List resultSet = this.ctTichDiemLocalBean.findRetailDealerList(branch_Id, district_Id);
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
}
