package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.core.business.BaoCaoTienThuongMaDuThuongManagementLocalBean;
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
 * Date: 10/28/14
 * Time: 9:39 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "BaoCaoTienThuongMaDuThuongManagementSessionEJB")
public class BaoCaoTienThuongMaDuThuongManagementSessionBean implements BaoCaoTienThuongMaDuThuongManagementLocalBean{
    @EJB
    private CTTichDiemLocalBean ctTichDiemLocalBean;

    public BaoCaoTienThuongMaDuThuongManagementSessionBean() {
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
    public List<DistrictDTO> findByBranchId(Long branch_Id) {
        List resultSet = this.ctTichDiemLocalBean.findDistrictListByBranchId(branch_Id);
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

    @Override
    public Object[] baoCaoTienThuongMaDuThuongTheoDaiLy(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) {
        Object[] resultObject = this.ctTichDiemLocalBean.baoCaoTienThuongMaDuThuongTheoDaiLy(properties, firstItem, maxPageItems, sortExpression, sortDirection);
        List<BaoCaoTienThuongMaDuThuongDTO> dtoList = new ArrayList<BaoCaoTienThuongMaDuThuongDTO>();
        for (Object object : (List)resultObject[1]){
            Object[] tmpArr = (Object[])object;
            BaoCaoTienThuongMaDuThuongDTO dto = new BaoCaoTienThuongMaDuThuongDTO();
            dto.setBranch_Name(tmpArr[0] != null ? tmpArr[0].toString() : null);
            dto.setDistrict_Name(tmpArr[1] != null ? tmpArr[1].toString() : null);
            dto.setDealer_Code(tmpArr[2] != null ? tmpArr[2].toString() : null);
            dto.setDealer_Name(tmpArr[3] != null ? tmpArr[3].toString() : null);
            dto.setAddress(tmpArr[4] != null ? tmpArr[4].toString() : null);
            dto.setItem_Name(tmpArr[5] != null ? tmpArr[5].toString() : null);
            dto.setNgay(tmpArr[6] != null ? Integer.valueOf(tmpArr[6].toString()) : null);
            dto.setThang(tmpArr[7] != null ? Integer.valueOf(tmpArr[7].toString()) : null);
            dto.setNam(tmpArr[8] != null ? Integer.valueOf(tmpArr[8].toString()) : null);
            dto.setSoThucTe(tmpArr[9] != null ? Double.valueOf(tmpArr[9].toString()) : null);
            dto.setSoDuocTinhDiem(tmpArr[10] != null ? Double.valueOf(tmpArr[10].toString()) : null);
            dto.setSoDuocQuiDoi(tmpArr[11] != null ? Double.valueOf(tmpArr[11].toString()) : null);
            dto.setSoDiemQuiDoi(tmpArr[12] != null ? Double.valueOf(tmpArr[12].toString()) : null);
            dto.setSoTienQuiDoi(tmpArr[13] != null ? Double.valueOf(tmpArr[13].toString()) : null);
            dto.setSoMaDuThuong(tmpArr[14] != null ? Integer.valueOf(tmpArr[14].toString()) : null);
            dto.setTrang_thai_tra_thuong(tmpArr[15] != null ? tmpArr[15].toString().trim() : null);
            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return resultObject;
    }

    @Override
    public Object[] baoCaoTienThuongMaDuThuongTheoQuanHuyen(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) {
        Object[] resultObject = this.ctTichDiemLocalBean.baoCaoTienThuongMaDuThuongTheoQuanHuyen(properties, firstItem, maxPageItems, sortExpression, sortDirection);
        List<BaoCaoTienThuongMaDuThuongDTO> dtoList = new ArrayList<BaoCaoTienThuongMaDuThuongDTO>();
        for (Object object : (List)resultObject[1]){
            Object[] tmpArr = (Object[])object;
            BaoCaoTienThuongMaDuThuongDTO dto = new BaoCaoTienThuongMaDuThuongDTO();
            dto.setBranch_Name(tmpArr[0] != null ? tmpArr[0].toString() : null);
            dto.setDistrict_Name(tmpArr[1] != null ? tmpArr[1].toString() : null);
            dto.setItem_Name(tmpArr[2] != null ? tmpArr[2].toString() : null);
            dto.setNgay(tmpArr[3] != null ? Integer.valueOf(tmpArr[3].toString()) : null);
            dto.setThang(tmpArr[4] != null ? Integer.valueOf(tmpArr[4].toString()) : null);
            dto.setNam(tmpArr[5] != null ? Integer.valueOf(tmpArr[5].toString()) : null);
            dto.setSoThucTe(tmpArr[6] != null ? Double.valueOf(tmpArr[6].toString()) : null);
            dto.setSoDuocTinhDiem(tmpArr[7] != null ? Double.valueOf(tmpArr[7].toString()) : null);
            dto.setSoDuocQuiDoi(tmpArr[8] != null ? Double.valueOf(tmpArr[8].toString()) : null);
            dto.setSoDiemQuiDoi(tmpArr[9] != null ? Double.valueOf(tmpArr[9].toString()) : null);
            dto.setSoTienQuiDoi(tmpArr[10] != null ? Double.valueOf(tmpArr[10].toString()) : null);
            dto.setSoMaDuThuong(tmpArr[11] != null ? Integer.valueOf(tmpArr[11].toString()) : null);
            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return resultObject;
    }

    @Override
    public Object[] baoCaoTienThuongMaDuThuongTheoChiNhanh(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) {
        Object[] resultObject = this.ctTichDiemLocalBean.baoCaoTienThuongMaDuThuongTheoChiNhanh(properties, firstItem, maxPageItems, sortExpression, sortDirection);
        List<BaoCaoTienThuongMaDuThuongDTO> dtoList = new ArrayList<BaoCaoTienThuongMaDuThuongDTO>();
        for (Object object : (List)resultObject[1]){
            Object[] tmpArr = (Object[])object;
            BaoCaoTienThuongMaDuThuongDTO dto = new BaoCaoTienThuongMaDuThuongDTO();
            dto.setBranch_Name(tmpArr[0] != null ? tmpArr[0].toString() : null);
            dto.setItem_Name(tmpArr[1] != null ? tmpArr[1].toString() : null);
            dto.setNgay(tmpArr[2] != null ? Integer.valueOf(tmpArr[2].toString()) : null);
            dto.setThang(tmpArr[3] != null ? Integer.valueOf(tmpArr[3].toString()) : null);
            dto.setNam(tmpArr[4] != null ? Integer.valueOf(tmpArr[4].toString()) : null);
            dto.setSoThucTe(tmpArr[5] != null ? Double.valueOf(tmpArr[5].toString()) : null);
            dto.setSoDuocTinhDiem(tmpArr[6] != null ? Double.valueOf(tmpArr[6].toString()) : null);
            dto.setSoDuocQuiDoi(tmpArr[7] != null ? Double.valueOf(tmpArr[7].toString()) : null);
            dto.setSoDiemQuiDoi(tmpArr[8] != null ? Double.valueOf(tmpArr[8].toString()) : null);
            dto.setSoTienQuiDoi(tmpArr[9] != null ? Double.valueOf(tmpArr[9].toString()) : null);
            dto.setSoMaDuThuong(tmpArr[10] != null ? Integer.valueOf(tmpArr[10].toString()) : null);
            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return resultObject;
    }

    @Override
    public BranchDTO findBranchById(Long branchId) throws ObjectNotFoundException {
        Object[] resultSet = this.ctTichDiemLocalBean.findBranchById(branchId);
        BranchDTO dto = new BranchDTO();
        dto.setBranch_Id(resultSet[0] != null ? Long.valueOf(resultSet[0].toString()) : null);
        dto.setBranch_code(resultSet[1] != null ? resultSet[1].toString() : null);
        dto.setBranch_name(resultSet[2] != null ? resultSet[2].toString() : null);
        return dto;
    }
}
