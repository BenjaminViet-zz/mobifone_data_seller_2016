package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.common.utils.DozerSingletonMapper;
import com.benluck.vms.mobifonedataseller.core.business.ChiNhanhManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BranchDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.ChiNhanhDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.DepartmentDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.GiftDTO;
import com.benluck.vms.mobifonedataseller.domain.ChiNhanhEntity;
import com.benluck.vms.mobifonedataseller.domain.DMChuongTrinhEntity;
import com.benluck.vms.mobifonedataseller.session.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.ejb.DuplicateKeyException;
import javax.ejb.EJB;
import javax.ejb.ObjectNotFoundException;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/19/14
 * Time: 1:43 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "ChiNhanhManagementSessionEJB")
public class ChiNhanhManagementSessionBean implements ChiNhanhManagementLocalBean{
    private transient final Log log = LogFactory.getLog(getClass());

    public ChiNhanhManagementSessionBean() {
    }

    @EJB
    private ChiNhanhLocalBean chiNhanhLocalBean;
    @EJB
    private StockAgentLocalBean stockAgentLocalBean;
    @EJB
    private BranchMappingLocalBean branchMappingLocalBean;
    @EJB
    private DMChuongTrinhLocalBean dmChuongTrinhLocalBean;
    @EJB
    private BranchLocalBean branchLocalBean;

    @Override
    public List<ChiNhanhDTO> baoCaoQuanLyPhieuKhuyenMai_tdcg(Long chiNhanhId, Long departmentId) {
        List<ChiNhanhEntity> entityList = null;
        if(chiNhanhId != null){
            try{
                ChiNhanhEntity chiNhanhEntity = this.chiNhanhLocalBean.findById(chiNhanhId);
                entityList = new ArrayList<ChiNhanhEntity>();
                entityList.add(chiNhanhEntity);
            }catch (ObjectNotFoundException oe){
                log.error("Can not find object for chiNhanhId: " + chiNhanhId);
                entityList = this.chiNhanhLocalBean.findAllAndSort();
            }
        }else{
            entityList = this.chiNhanhLocalBean.findAllAndSort();
        }
        List<ChiNhanhDTO> dtoList = new ArrayList<ChiNhanhDTO>();
        for(ChiNhanhEntity entity : entityList){
            ChiNhanhDTO dto = DozerSingletonMapper.getInstance().map(entity, ChiNhanhDTO.class);
            List resultSet = this.stockAgentLocalBean.baoCaoQuanLyPhieuKhuyenMai_tdcg(entity.getChiNhanhId(), departmentId);
            List<DepartmentDTO> cuaHangList = new ArrayList<DepartmentDTO>();
            if(resultSet != null){
                for (Object object : resultSet){
                    Object[] tmpArr = (Object[])object;
                    DepartmentDTO cuaHangDTo = new DepartmentDTO();
                    cuaHangDTo.setDepartmentId(Long.valueOf(tmpArr[0].toString()));
                    cuaHangDTo.setCode(tmpArr[1].toString());
                    cuaHangDTo.setName(tmpArr[2].toString());
                    cuaHangDTo.setSoLuongNhap(Integer.valueOf(tmpArr[4].toString()));
                    cuaHangDTo.setSoLuongPhieuDaDoi(Integer.valueOf(tmpArr[5].toString()));
                    cuaHangDTo.setTon(Integer.valueOf(tmpArr[6].toString()));
                    cuaHangList.add(cuaHangDTo);
                }
            }
            dto.setCuaHangList(cuaHangList);
            dtoList.add(dto);
        }
        return  dtoList;
    }

    @Override
    public List<BranchDTO> baoCaoQuanLyPhieuKhuyenMai_qStudent(String code, ChiNhanhDTO pojo) throws ObjectNotFoundException {
        List<BranchDTO> branchList = new ArrayList<BranchDTO>();;
        DMChuongTrinhEntity dmChuongTrinhEntity = this.dmChuongTrinhLocalBean.findEqualUnique("code", code);
        if(pojo.getBranch() != null && pojo.getBranch().getBranch_Id() != null){
            try{
                Object[] resultObject = this.branchLocalBean.findByBranchIdAndDBLink(pojo.getBranch().getBranch_Id(),dmChuongTrinhEntity.getDbLinkName());
                BranchDTO dto = new BranchDTO();
                dto.setBranch_Id(resultObject[0] != null ? Long.valueOf(resultObject[0].toString()) : null);
                dto.setBranch_code(resultObject[1] != null ? resultObject[1].toString() : null);
                dto.setBranch_name(resultObject[2] != null ? resultObject[2].toString() : null);
                branchList.add(dto);
            }catch (ObjectNotFoundException oe){
                log.error("Can not find object for branchId: " + pojo.getBranch().getBranch_Id());
            }
        }else{
            List resultSet = this.branchLocalBean.findAllBranchesByDBLink(dmChuongTrinhEntity.getDbLinkName());
            for (Object object : resultSet){
                Object[] tmpArr = (Object[])object;
                BranchDTO dto = new BranchDTO();
                dto.setBranch_Id(tmpArr[0] != null ? Long.valueOf(tmpArr[0].toString()) : null);
                dto.setBranch_code(tmpArr[1] != null ? tmpArr[1].toString() : null);
                dto.setBranch_name(tmpArr[2] != null ? tmpArr[2].toString() : null);
                branchList.add(dto);
            }
        }
        List<BranchDTO> dtoList = new ArrayList<BranchDTO>();
        for(BranchDTO branchDTO : branchList){
            BranchDTO dto = branchDTO;
            List resultSet = this.stockAgentLocalBean.baoCaoQuanLyPhieuKhuyenMai_qStudent(branchDTO.getBranch_Id(), dmChuongTrinhEntity.getChuongTrinhId(), pojo.getDepartmentId());
            List<DepartmentDTO> cuaHangList = new ArrayList<DepartmentDTO>();
            if(resultSet != null){
                for (Object object : resultSet){
                    Object[] tmpArr = (Object[])object;
                    DepartmentDTO cuaHangDTO = new DepartmentDTO();
                    cuaHangDTO.setCode(tmpArr[0] != null ? tmpArr[0].toString() : null);
                    cuaHangDTO.setName(tmpArr[1] != null ? tmpArr[1].toString() : null);
                    GiftDTO giftDTO = new GiftDTO();
                    giftDTO.setName(tmpArr[2] != null ? tmpArr[2].toString() : null);
                    cuaHangDTO.setGift(giftDTO);
                    cuaHangDTO.setTon(tmpArr[3] != null ? Integer.valueOf(tmpArr[3].toString()) : null);
                    cuaHangDTO.setSoLuongPhieuDaDoi(tmpArr[4] != null ? Integer.valueOf(tmpArr[4].toString()) : null);
                    cuaHangList.add(cuaHangDTO);
                }
            }
            dto.setDepartment(cuaHangList);
            dtoList.add(dto);
        }
        return  dtoList;
    }

    @Override
    public List<ChiNhanhDTO> findAll() {
        List<ChiNhanhEntity> entityList = this.chiNhanhLocalBean.findAllAndSort();
        List<ChiNhanhDTO> dtoList = new ArrayList<ChiNhanhDTO>();
        for (ChiNhanhEntity entity : entityList){
            dtoList.add(DozerSingletonMapper.getInstance().map(entity, ChiNhanhDTO.class));
        }
        return dtoList;
    }

    @Override
    public ChiNhanhDTO findById(Long chiNhanhId) throws ObjectNotFoundException{
        ChiNhanhEntity entity = this.chiNhanhLocalBean.findById(chiNhanhId);
        return DozerSingletonMapper.getInstance().map(entity, ChiNhanhDTO.class);
    }

    @Override
    public Object[] search(Integer offset, Integer limit, String sortExpression, String sortDirection) {
        Map<String, Object> properties = new HashMap<String, Object>();
        if(StringUtils.isBlank(sortExpression)){
            sortExpression = "chiNhanh";
        }
        if(StringUtils.isBlank(sortDirection)){
            sortDirection = "2";
        }
        Object[] resultObject = this.chiNhanhLocalBean.searchByProperties(properties, sortExpression, sortDirection, offset, limit, null);
        List<ChiNhanhDTO> dtoList = new ArrayList<ChiNhanhDTO>();
        for (ChiNhanhEntity entity : (List<ChiNhanhEntity>)resultObject[1]){
            dtoList.add(DozerSingletonMapper.getInstance().map(entity, ChiNhanhDTO.class));
        }
        resultObject[1] = dtoList;
        return resultObject;
    }

    @Override
    public ChiNhanhDTO findByChiNhanhUnique(Integer chiNhanh) {
        try{
            ChiNhanhEntity entity = this.chiNhanhLocalBean.findEqualUnique("chiNhanh", chiNhanh);
            return  DozerSingletonMapper.getInstance().map(entity, ChiNhanhDTO.class);
        }catch (ObjectNotFoundException oe){
            return  null;
        }
    }

    @Override
    public void updateItem(ChiNhanhDTO dto) throws ObjectNotFoundException, DuplicateKeyException {
        try{
            ChiNhanhEntity entity = this.chiNhanhLocalBean.findById(dto.getChiNhanhId());
            entity.setChiNhanh(dto.getChiNhanh());
            entity.setName(dto.getName());
            entity = this.chiNhanhLocalBean.update(entity);
        }catch (ObjectNotFoundException oe){
            throw  new ObjectNotFoundException("Object not foudn for chiNhanhId: " + dto.getChiNhanhId());
        }
    }

    @Override
    public void anhXaChiNhanh(List<ChiNhanhDTO> chiNhanhList) throws ObjectNotFoundException, DuplicateKeyException {
        for (ChiNhanhDTO dto : chiNhanhList){
            try{
                ChiNhanhEntity entity = this.chiNhanhLocalBean.findById(dto.getChiNhanhId());
                entity.setBranchId(dto.getBranch().getBranch_Id());
                this.chiNhanhLocalBean.update(entity);
            }catch (ObjectNotFoundException oe){
                throw new ObjectNotFoundException("Object not found for chiNhanh " + dto.getChiNhanh() + ". \nDetails: " + oe.getMessage());
            }catch (DuplicateKeyException de){
                throw new ObjectNotFoundException("Duplicate key for chiNhanh " + dto.getChiNhanh() + ". \nDetails: " + de.getMessage());
            }
        }
    }

    @Override
    public ChiNhanhDTO findChiNhanhByDepartmentId(Long departmentId) throws ObjectNotFoundException{
        Object[] resultSet = this.chiNhanhLocalBean.findByDepartmentId(departmentId);
        ChiNhanhDTO dto = new ChiNhanhDTO();
        dto.setChiNhanhId(resultSet[0] != null ? Long.valueOf(resultSet[0].toString()) : null);
        dto.setChiNhanh(resultSet[1] != null ? Integer.valueOf(resultSet[1].toString()) : null);
        dto.setName(resultSet[2] != null ? resultSet[2].toString(): null);
        dto.setBranchId(resultSet[3] != null ? Long.valueOf(resultSet[3].toString()) : null);
        return dto;
    }
}
