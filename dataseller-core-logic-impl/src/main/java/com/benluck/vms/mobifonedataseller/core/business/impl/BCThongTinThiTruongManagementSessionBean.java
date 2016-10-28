package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.core.business.BaoCaoThongTinThiTruongManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BaoCaoTTThiTruongDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BranchDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.DistrictDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.RetailDealerDTO;
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
 * Date: 10/22/14
 * Time: 3:44 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "BCThongTinThiTruongManagementSessionEJB")
public class BCThongTinThiTruongManagementSessionBean implements BaoCaoThongTinThiTruongManagementLocalBean{
    @EJB
    private CTTichDiemLocalBean ctTichDiemLocalBean;

    public BCThongTinThiTruongManagementSessionBean() {
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
    public List<RetailDealerDTO> findRetailDealerList(Long branchId, Long districtId) {
        List resultSet = this.ctTichDiemLocalBean.findRetailDealerList(branchId, districtId);
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
    public Object[] baoCaoThongTinThiTruongTheoDaiLy(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) {
        Object[] resultObject = this.ctTichDiemLocalBean.baoCaoThongTinThiTruongTheoDaiLy(properties, firstItem, maxPageItems, sortExpression, sortDirection);
        List<BaoCaoTTThiTruongDTO> dtoList = new ArrayList<BaoCaoTTThiTruongDTO>();
        for(Object object : (List)resultObject[1]){
            Object[] tmpArr = (Object[])object;
            BaoCaoTTThiTruongDTO dto = new BaoCaoTTThiTruongDTO();
            dto.setBranch_Name(tmpArr[0] != null ? tmpArr[0].toString() : null);
            dto.setDistrict_Name(tmpArr[1] != null ? tmpArr[1].toString() : null);
            dto.setDealer_code(tmpArr[2] != null ? tmpArr[2].toString() : null);
            dto.setDealer_Name(tmpArr[3] != null ? tmpArr[3].toString() : null);
            dto.setAddress(tmpArr[4] != null ? tmpArr[4].toString() : null);
            dto.setSoLuongBanSimViettel(tmpArr[5] != null ? Integer.valueOf(tmpArr[5].toString()) : null);
            dto.setGiaSimViettel(tmpArr[6] != null ? Double.valueOf(tmpArr[6].toString()) : null);
            dto.setSoLuongBanTheViettel(tmpArr[7] != null ? Integer.valueOf(tmpArr[7].toString()) : null);
            dto.setSoLuongBanSimMobi(tmpArr[8] != null ? Integer.valueOf(tmpArr[8].toString()) : null);
            dto.setGiaSimMobi(tmpArr[9] != null ? Double.valueOf(tmpArr[9].toString()) : null);
            dto.setSoLuongBanTheMobi(tmpArr[10] != null ? Integer.valueOf(tmpArr[10].toString()) : null);
            dto.setSoLuongBanSimVina(tmpArr[11] != null ? Integer.valueOf(tmpArr[11].toString()) : null);
            dto.setGiaSimVina(tmpArr[12] != null ? Double.valueOf(tmpArr[12].toString()) : null);
            dto.setSoLuongBanTheVina(tmpArr[13] != null ? Integer.valueOf(tmpArr[13].toString()) : null);
            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return resultObject;
    }

    @Override
    public Object[] baoCaoThongTinThiTruongTheoQuanHuyen(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) {
        Object[] resultObject = this.ctTichDiemLocalBean.baoCaoThongTinThiTruongTheoQuanHuyen(properties, firstItem, maxPageItems, sortExpression, sortDirection);
        List<BaoCaoTTThiTruongDTO> dtoList = new ArrayList<BaoCaoTTThiTruongDTO>();
        for(Object object : (List)resultObject[1]){
            Object[] tmpArr = (Object[])object;
            BaoCaoTTThiTruongDTO dto = new BaoCaoTTThiTruongDTO();
            dto.setBranch_Name(tmpArr[0] != null ? tmpArr[0].toString() : null);
            dto.setDistrict_Name(tmpArr[1] != null ? tmpArr[1].toString() : null);
            dto.setSoLuongBanSimViettel(tmpArr[2] != null ? Integer.valueOf(tmpArr[2].toString()) : null);
            dto.setGiaSimViettel(tmpArr[3] != null ? Double.valueOf(tmpArr[3].toString()) : null);
            dto.setSoLuongBanTheViettel(tmpArr[4] != null ? Integer.valueOf(tmpArr[4].toString()) : null);
            dto.setSoLuongBanSimMobi(tmpArr[5] != null ? Integer.valueOf(tmpArr[5].toString()) : null);
            dto.setGiaSimMobi(tmpArr[6] != null ? Double.valueOf(tmpArr[6].toString()) : null);
            dto.setSoLuongBanTheMobi(tmpArr[7] != null ? Integer.valueOf(tmpArr[7].toString()) : null);
            dto.setSoLuongBanSimVina(tmpArr[8] != null ? Integer.valueOf(tmpArr[8].toString()) : null);
            dto.setGiaSimVina(tmpArr[9] != null ? Double.valueOf(tmpArr[9].toString()) : null);
            dto.setSoLuongBanTheVina(tmpArr[10] != null ? Integer.valueOf(tmpArr[10].toString()) : null);
            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return resultObject;
    }

    @Override
    public Object[] baoCaoThongTinThiTruongTheoChiNhanh(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) {
        Object[] resultObject = this.ctTichDiemLocalBean.baoCaoThongTinThiTruongTheoChiNhanh(properties, firstItem, maxPageItems, sortExpression, sortDirection);
        List<BaoCaoTTThiTruongDTO> dtoList = new ArrayList<BaoCaoTTThiTruongDTO>();
        for(Object object : (List)resultObject[1]){
            Object[] tmpArr = (Object[])object;
            BaoCaoTTThiTruongDTO dto = new BaoCaoTTThiTruongDTO();
            dto.setBranch_Name(tmpArr[0] != null ? tmpArr[0].toString() : null);
            dto.setSoLuongBanSimViettel(tmpArr[1] != null ? Integer.valueOf(tmpArr[1].toString()) : null);
            dto.setGiaSimViettel(tmpArr[2] != null ? Double.valueOf(tmpArr[2].toString()) : null);
            dto.setSoLuongBanTheViettel(tmpArr[3] != null ? Integer.valueOf(tmpArr[3].toString()) : null);
            dto.setSoLuongBanSimMobi(tmpArr[4] != null ? Integer.valueOf(tmpArr[4].toString()) : null);
            dto.setGiaSimMobi(tmpArr[5] != null ? Double.valueOf(tmpArr[5].toString()) : null);
            dto.setSoLuongBanTheMobi(tmpArr[6] != null ? Integer.valueOf(tmpArr[6].toString()) : null);
            dto.setSoLuongBanSimVina(tmpArr[7] != null ? Integer.valueOf(tmpArr[7].toString()) : null);
            dto.setGiaSimVina(tmpArr[8] != null ? Double.valueOf(tmpArr[8].toString()) : null);
            dto.setSoLuongBanTheVina(tmpArr[9] != null ? Integer.valueOf(tmpArr[9].toString()) : null);
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
