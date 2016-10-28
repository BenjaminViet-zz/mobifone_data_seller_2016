package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.core.business.DistrictManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.DistrictDTO;
import com.benluck.vms.mobifonedataseller.domain.DMChuongTrinhEntity;
import com.benluck.vms.mobifonedataseller.session.*;

import javax.ejb.EJB;
import javax.ejb.ObjectNotFoundException;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/26/14
 * Time: 10:48 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "DistrictManagementSessionEJB")
public class DistrictManagementSessionBean implements DistrictManagementLocalBean{
    @EJB
    private CTTichDiemLocalBean ctTichDiemLocalBean;
    @EJB
    private ThueBaoPhatTrienMoiLocalBean thueBaoPhatTrienMoiLocalBean;
    @EJB
    private DistrictLocalBean districtLocalBean;
    @EJB
    private DMChuongTrinhLocalBean dmChuongTrinhLocalBean;
    @EJB
    private BranchMappingLocalBean branchMappingLocalBean;

    public DistrictManagementSessionBean() {
    }

    @Override
    public List<DistrictDTO> findByBranchId_tdcg(Long branchId) {
        List<DistrictDTO> dtoList = new ArrayList<DistrictDTO>();
        List resultSet = this.ctTichDiemLocalBean.findDistrictListByBranchId(branchId);
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
    public List<DistrictDTO> findAll_tdcg() {
        List<DistrictDTO> dtoList = new ArrayList<DistrictDTO>();
        List resultSet = this.ctTichDiemLocalBean.findAllDistricts();
        for (Object object : resultSet){
            Object[] tmpArr = (Object[])object;
            DistrictDTO dto = new DistrictDTO();
            dto.setDistrict_Id(tmpArr[0] != null ? Long.valueOf(tmpArr[0].toString()) : null);
            dto.setDistrict_code(tmpArr[1] != null ? tmpArr[1].toString() : null);
            dto.setDistrict_name(tmpArr[2] != null ? tmpArr[2].toString() : null);
            dtoList.add(dto);
        }

        return dtoList;
    }

    @Override
    public List<DistrictDTO> findByBranchId_tbptm(Long branchId) {
        List<DistrictDTO> dtoList = new ArrayList<DistrictDTO>();
        List resultSet = this.thueBaoPhatTrienMoiLocalBean.findDistrictListByBranchId(branchId);
        for (Object object : resultSet){
            Object[] tmpArr = (Object[])object;
            DistrictDTO dto = new DistrictDTO();
            dto.setDistrict_Id(tmpArr[0] != null ? Long.valueOf(tmpArr[0].toString()) : null);
            dto.setDistrict_code(tmpArr[1] != null ? tmpArr[1].toString() : null);
            dto.setDistrict_name(tmpArr[2] != null ? tmpArr[2].toString() : null);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public List<DistrictDTO> findAll_tbptm() {
        List<DistrictDTO> dtoList = new ArrayList<DistrictDTO>();
        List resultSet = this.thueBaoPhatTrienMoiLocalBean.findAllDistricts();
        for (Object object : resultSet){
            Object[] tmpArr = (Object[])object;
            DistrictDTO dto = new DistrictDTO();
            dto.setDistrict_Id(tmpArr[0] != null ? Long.valueOf(tmpArr[0].toString()) : null);
            dto.setDistrict_code(tmpArr[1] != null ? tmpArr[1].toString() : null);
            dto.setDistrict_name(tmpArr[2] != null ? tmpArr[2].toString() : null);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public List<DistrictDTO> findAllByChuongTrinhCode(String ctCode) throws ObjectNotFoundException{
        DMChuongTrinhEntity dmChuongTrinhEntity = this.dmChuongTrinhLocalBean.findEqualUnique("code", ctCode);
        List<DistrictDTO> dtoList = new ArrayList<DistrictDTO>();
        List resultSet = this.districtLocalBean.findAllByDBLink(dmChuongTrinhEntity.getDbLinkName());
        for (Object object : resultSet){
            Object[] tmpArr = (Object[])object;
            DistrictDTO dto = new DistrictDTO();
            dto.setDistrict_Id(tmpArr[0] != null ? Long.valueOf(tmpArr[0].toString()) : null);
            dto.setDistrict_code(tmpArr[1] != null ? tmpArr[1].toString() : null);
            dto.setDistrict_name(tmpArr[2] != null ? tmpArr[2].toString() : null);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public List<DistrictDTO> findByBranchIdAndCTCode(Long branchId, String ctCode) throws ObjectNotFoundException {
        DMChuongTrinhEntity dmChuongTrinhEntity = this.dmChuongTrinhLocalBean.findEqualUnique("code", ctCode);
        List<DistrictDTO> dtoList = new ArrayList<DistrictDTO>();
        List resultSet = this.districtLocalBean.findByBranchIdAndDBLink(branchId, dmChuongTrinhEntity.getDbLinkName());
        for (Object object : resultSet){
            Object[] tmpArr = (Object[])object;
            DistrictDTO dto = new DistrictDTO();
            dto.setDistrict_Id(tmpArr[0] != null ? Long.valueOf(tmpArr[0].toString()) : null);
            dto.setDistrict_code(tmpArr[1] != null ? tmpArr[1].toString() : null);
            dto.setDistrict_name(tmpArr[2] != null ? tmpArr[2].toString() : null);
            dtoList.add(dto);
        }
        return dtoList;
    }


    @Override
    public DistrictDTO findByDistrictId(Long districtId) throws ObjectNotFoundException {
        Object[] resultObject = this.ctTichDiemLocalBean.findDistrictById(districtId);
        DistrictDTO districtDTO = new DistrictDTO();
        Object[] tmpArr = (Object[])resultObject;
        districtDTO.setDistrict_Id(tmpArr[0] != null ? Long.valueOf(tmpArr[0].toString()) : null);
        districtDTO.setDistrict_code(tmpArr[1] != null ? tmpArr[1].toString() : null);
        districtDTO.setDistrict_name(tmpArr[2] != null ? tmpArr[2].toString() : null);
        return districtDTO;
    }
}
