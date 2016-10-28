package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.core.business.StockAgentManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BranchDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.DepartmentDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.GiftDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.StockAgentDTO;
import com.benluck.vms.mobifonedataseller.domain.DMChuongTrinhEntity;
import com.benluck.vms.mobifonedataseller.domain.DepartmentEntity;
import com.benluck.vms.mobifonedataseller.session.DMChuongTrinhLocalBean;
import com.benluck.vms.mobifonedataseller.session.DepartmentLocalBean;
import com.benluck.vms.mobifonedataseller.session.StockAgentLocalBean;

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
 * Date: 10/15/14
 * Time: 11:10 AM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "StockAgentManagementSessionEJB")
public class StockAgentManagementSessionBean implements StockAgentManagementLocalBean{
    @EJB
    private StockAgentLocalBean stockAgentLocalBean;
    @EJB
    private DMChuongTrinhLocalBean dmChuongTrinhLocalBean;
    @EJB
    private DepartmentLocalBean departmentLocalBean;

    public StockAgentManagementSessionBean() {
    }

    @Override
    public Integer countInventoryTotalByAgentId_tdcg(Long departmentId) {
        return this.stockAgentLocalBean.countInventoryTotalByAgentId_tdcg(departmentId);
    }

    @Override
    public Integer countInventoryTotalByAgentId_qStudent(Long departmentId, Long giftId) {
        try {
            DepartmentEntity departmentEntity = this.departmentLocalBean.findById(departmentId);
            return this.stockAgentLocalBean.countInventoryTotalByAgentId_qStudent(departmentEntity.getCode(),giftId);
        } catch (ObjectNotFoundException e) {
            return null;
        }
    }

    @Override
    public Object[] searchOtherShopStockByProperties_tdcg(Integer chiNhanh, Long agentId, Integer trang_thai_kho, Integer offset, Integer limit, String sortExpression, String sortDirection) {
        Object[] resultObject = this.stockAgentLocalBean.traCuuKhoHangCuaHangMobifoneKhac_tdcg(chiNhanh, agentId, trang_thai_kho, offset, limit, sortExpression, sortDirection);
        List resultSet = (List)resultObject[1];
        List<StockAgentDTO> dtoList = new ArrayList<StockAgentDTO>();
        for (Object object : resultSet){
            Object[] tmpArr = (Object[])object;
            StockAgentDTO dto = new StockAgentDTO();
            dto.setStockAgentId(tmpArr[0] != null ? Long.valueOf(tmpArr[0].toString()) : null);

            DepartmentDTO agentDTO = new DepartmentDTO();
            agentDTO.setDepartmentId(Long.valueOf(tmpArr[1].toString()));
            agentDTO.setCode(tmpArr[2].toString());
            agentDTO.setName(tmpArr[3].toString());
            dto.setDepartment(agentDTO);

            dto.setChiNhanh(tmpArr[4] != null ? Integer.valueOf(tmpArr[4].toString())  : null);
            dto.setTotal(Integer.valueOf(tmpArr[5].toString()));
            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return  resultObject;
    }

    @Override
    public Object[] searchOtherShopStockByProperties_qStudent(String ctCode, StockAgentDTO pojo, Integer offset, Integer limit, String sortExpression, String sortDirection) throws ObjectNotFoundException {
        Map<String, Object> properties = new HashMap<String, Object>();

        if(pojo.getBranch() != null && pojo.getBranch().getBranch_Id() != null){
            properties.put("branchId", pojo.getBranch().getBranch_Id());
        }
        if(pojo.getDepartment() != null && pojo.getDepartment().getDepartmentId() != null){
            properties.put("departmentId", pojo.getDepartment().getDepartmentId());
        }
        if (pojo.getTrang_thai_kho() != null && !pojo.getTrang_thai_kho().equals(-1)){
            properties.put("trang_thai_kho", pojo.getTrang_thai_kho());
        }

        DMChuongTrinhEntity dmChuongTrinhEntity = this.dmChuongTrinhLocalBean.findEqualUnique("code", ctCode);

        Object[] resultObject = this.stockAgentLocalBean.traCuuKhoHangCuaHangMobifoneKhac_qStudent(properties, dmChuongTrinhEntity.getChuongTrinhId(), offset, limit, sortExpression, sortDirection);
        List resultSet = (List)resultObject[1];
        List<StockAgentDTO> dtoList = new ArrayList<StockAgentDTO>();
        for (Object object : resultSet){
            Object[] tmpArr = (Object[])object;
            StockAgentDTO dto = new StockAgentDTO();

            DepartmentDTO agentDTO = new DepartmentDTO();
            agentDTO.setCode(tmpArr[0] != null ? tmpArr[0].toString()  : null);
            agentDTO.setName(tmpArr[1] != null ? tmpArr[1].toString()  : null);
            dto.setDepartment(agentDTO);

            BranchDTO branchDTO = new BranchDTO();
            branchDTO.setBranch_name(tmpArr[2] != null ? tmpArr[2].toString() : null);
            dto.setBranch(branchDTO);

            GiftDTO giftDTO = new GiftDTO();
            giftDTO.setName(tmpArr[3] != null ? tmpArr[3].toString()  : null);
            dto.setGift(giftDTO);
            dto.setTotal(tmpArr[4] != null ? Integer.valueOf(tmpArr[4].toString()) : null);
            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return  resultObject;
    }
}
