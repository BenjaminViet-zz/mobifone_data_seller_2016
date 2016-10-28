package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.core.business.BaoCaoTheoHangMucPhatSinhManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BaoCaoTheoHangMucPhatSinhDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BranchDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.DistrictDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.PromItemsDTO;
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
 * Time: 1:33 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "BaoCaoTheoHangMucPhatSinhManagementSessionEJB")
public class BaoCaoTheoHangMucPhatSinhManagementSessionBean implements BaoCaoTheoHangMucPhatSinhManagementLocalBean{
    @EJB
    private CTTichDiemLocalBean ctTichDiemLocalBean;

    public BaoCaoTheoHangMucPhatSinhManagementSessionBean() {
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
    public Object[] baoCaoTheoHangMucPhatSinh(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) {
        Object[] resultObject = this.ctTichDiemLocalBean.baoCaoTheoHangMucPhatSinh(properties, firstItem, maxPageItems, sortExpression, sortDirection);
        List<BaoCaoTheoHangMucPhatSinhDTO> dtoList = new ArrayList<BaoCaoTheoHangMucPhatSinhDTO>();
        for (Object object : (List)resultObject[1]){
            Object[] tmpArr = (Object[])object;
            BaoCaoTheoHangMucPhatSinhDTO dto = new BaoCaoTheoHangMucPhatSinhDTO();
            dto.setDealer_Code(tmpArr[1] != null ? tmpArr[1].toString() : null);
            dto.setDealer_Name(tmpArr[2] != null ? tmpArr[2].toString() : null);
            dto.setBranch_Name(tmpArr[4] != null ? tmpArr[4].toString() : null);
            dto.setDistrict_Name(tmpArr[6] != null ? tmpArr[6].toString() : null);
            dto.setItem_Name(tmpArr[8] != null ? tmpArr[8].toString() : null);
            dto.setDonViTinh(tmpArr[9] != null ? tmpArr[9].toString() : null);
            dto.setSoPS(tmpArr[10] != null ? Double.valueOf(tmpArr[10].toString()) : null);
            dto.setCycle(tmpArr[11] != null ? Integer.valueOf(tmpArr[11].toString()) : null);
            dto.setQuyDiem(tmpArr[12] != null ? Double.valueOf(tmpArr[12].toString()) : null);
            dto.setSoTienTuongUng(tmpArr[13] != null ? Double.valueOf(tmpArr[13].toString()) : null);
            dto.setSoMaDuThuong(tmpArr[14] != null ? Integer.valueOf(tmpArr[14].toString()) : null);
            if(tmpArr[15] != null && tmpArr[15].toString().equalsIgnoreCase("1")
                    && tmpArr[16] != null && tmpArr[16].toString().equalsIgnoreCase("1")
                    && tmpArr[17] != null && tmpArr[17].toString().equalsIgnoreCase("1")
                    && tmpArr[18] != null && tmpArr[18].toString().equalsIgnoreCase("1")
                    && tmpArr[19] != null && tmpArr[19].toString().equalsIgnoreCase("1")
                    && tmpArr[20] != null && tmpArr[20].toString().equalsIgnoreCase("1")){
                dto.setDu_dieu_kien_status(Constants.DU_DIEU_KIEN);
            }else{
                dto.setDu_dieu_kien_status(Constants.KHONG_DIEU_KIEN);
            }
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
