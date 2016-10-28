package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.utils.DozerSingletonMapper;
import com.benluck.vms.mobifonedataseller.core.business.GiftDeliveryAgentHistoryManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.GiftDeliveryAgentHistoryDTO;
import com.benluck.vms.mobifonedataseller.domain.*;
import com.benluck.vms.mobifonedataseller.session.*;
import org.apache.commons.lang3.StringUtils;

import javax.ejb.DuplicateKeyException;
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
 * Date: 10/15/14
 * Time: 11:16 AM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "GiftDeliveryAgentHistoryManagementSessionEJB")
public class GiftDeliveryAgentHistoryManagementSessionBean implements GiftDeliveryAgentHistoryManagementLocalBean{
    @EJB
    private GiftDeliveryAgentHistoryLocalBean giftDeliveryAgentHistoryLocalBean;
    @EJB
    private StockAgentLocalBean stockAgentLocalBean;
    @EJB
    private UserLocalBean userLocalBean;
    @EJB
    private GiftLocalBean giftLocalBean;
    @EJB
    private GiftAgentTransferHistoryLocalBean giftAgentTransferHistoryLocalBean;

    public GiftDeliveryAgentHistoryManagementSessionBean() {
    }

    @Override
    public void inputStock(GiftDeliveryAgentHistoryDTO dto) throws ObjectNotFoundException, DuplicateKeyException {
        try{
            UserEntity warehouser = this.userLocalBean.findByUserName(dto.getUser().getUserName());
            Integer inventoryTotal = this.stockAgentLocalBean.countInventoryTotalByAgentId_tdcg(warehouser.getDepartment().getDepartmentId());

            StockAgentEntity stockAgentEntity = newOrUpdateStock(warehouser.getDepartment(), dto.getQuantity());
            GiftDeliveryAgentHistoryEntity giftDeliveryAgentHistoryEntity = new GiftDeliveryAgentHistoryEntity();
            giftDeliveryAgentHistoryEntity.setStockAgent(stockAgentEntity);
            giftDeliveryAgentHistoryEntity.setUser(warehouser);
            giftDeliveryAgentHistoryEntity.setInventoryTotal(inventoryTotal);
            GiftEntity giftEntity = this.giftLocalBean.findByCode(Constants.GIFTCODE_PMH);
            if(giftEntity == null){
                throw new ObjectNotFoundException("Object not found for Gift with code: " + Constants.GIFTCODE_PMH);
            }

            giftDeliveryAgentHistoryEntity.setGift(giftEntity);
            giftDeliveryAgentHistoryEntity.setQuantity(dto.getQuantity());
            giftDeliveryAgentHistoryEntity.setDeliveryTime(new Timestamp(System.currentTimeMillis()));
            giftDeliveryAgentHistoryEntity.setStatus(Constants.DELIVERIED_STOCK);
            giftDeliveryAgentHistoryEntity.setTenNguoiGiaoHang(dto.getTenNguoiGiaoHang());
            giftDeliveryAgentHistoryEntity = this.giftDeliveryAgentHistoryLocalBean.save(giftDeliveryAgentHistoryEntity);
        }catch (ObjectNotFoundException oe){
            throw new ObjectNotFoundException("Object not found for soThueBao: " + dto.getUser().getUserName());
        }
    }

    @Override
    public Object[] search_tdcg(Long agentId, GiftDeliveryAgentHistoryDTO dto, Integer offset, Integer limit, String sortExpression, String sortDirection) {
        Map<String, Object> properties = buildProperties(agentId);
        String whereClause = buildWhereClause(dto);
        if (StringUtils.isBlank(sortDirection)){
            sortDirection = Constants.SORT_ASC;
        }
        if (StringUtils.isBlank(sortExpression)){
            sortExpression = "deliveryTime";
        }
        Object[] resultObject = this.giftDeliveryAgentHistoryLocalBean.searchByProperties(properties, sortExpression, sortDirection, offset, limit, whereClause);
        resultObject = convert2DTO(resultObject);
        return resultObject;
    }

    @Override
    public Object[] search_qStudent(Long agentId, GiftDeliveryAgentHistoryDTO dto, int firstItem, int maxPageItems, String sortExpression, String sortDirection) {
        Map<String, Object> properties = buildProperties(agentId);
        String whereClause = buildWhereClause(dto);
        if (StringUtils.isBlank(sortDirection)){
            sortDirection = Constants.SORT_ASC;
        }
        if (StringUtils.isBlank(sortExpression)){
            sortExpression = "deliveryTime";
        }
        Object[] resultObject = this.giftDeliveryAgentHistoryLocalBean.searchByProperties(properties, sortExpression, sortDirection, firstItem, maxPageItems, whereClause);
        resultObject = convert2DTO(resultObject);
        return resultObject;
    }

    @Override
    public void getFromStock(Long nguoiXuatKhoId, Long departmentId, Integer quantity) throws Exception{
        try{
            StockAgentEntity stockAgentEntity = this.stockAgentLocalBean.findByDepartmentId(departmentId);

            Integer inventoryTotal = stockAgentEntity.getTotal();

            GiftAgentTransferHistoryEntity historyEntity = new GiftAgentTransferHistoryEntity();
            historyEntity.setStockAgent(stockAgentEntity);
            historyEntity.setQuantity(quantity);
            historyEntity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
            historyEntity.setInventoryTotal(inventoryTotal);

            UserEntity nguoiChuyenEntity = new UserEntity();
            nguoiChuyenEntity.setUserId(nguoiXuatKhoId);
            historyEntity.setNguoiChuyen(nguoiChuyenEntity);
            historyEntity = this.giftAgentTransferHistoryLocalBean.save(historyEntity);

            stockAgentEntity.setTotal(stockAgentEntity.getTotal() - quantity);
            stockAgentEntity = this.stockAgentLocalBean.update(stockAgentEntity);
        }catch (Exception e){
            throw new Exception("Xuat kho that bai. \n Details: " + e.getMessage());
        }
    }

    private Object[] convert2DTO(Object[] resultObject){
        List<GiftDeliveryAgentHistoryDTO> dtoList = new ArrayList<GiftDeliveryAgentHistoryDTO>();
        List<GiftDeliveryAgentHistoryEntity> entityList = (List<GiftDeliveryAgentHistoryEntity>)resultObject[1];
        if(entityList != null && entityList.size() > 0){
            for (GiftDeliveryAgentHistoryEntity entity : entityList){
                dtoList.add(DozerSingletonMapper.getInstance().map(entity, GiftDeliveryAgentHistoryDTO.class));
            }
        }
        resultObject[1] = dtoList;
        return  resultObject;
    }

    private Map<String, Object> buildProperties(Long agentId){
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("stockAgent.department.departmentId", agentId);
        return  properties;
    }

    private String buildWhereClause(GiftDeliveryAgentHistoryDTO dto){
        StringBuilder whereClause = new StringBuilder(" 1 = 1");
        if(dto.getFromDate() != null){
            whereClause.append(" and A.deliveryTime >= to_timestamp(SUBSTR('").append(dto.getFromDate().toString()).append("',1,19)")
                    .append(", 'yyyy-MM-dd HH24:MI:SS,FF')");
        }
        if(dto.getToDate() != null){
            whereClause.append(" and A.deliveryTime < (to_timestamp(SUBSTR('").append(dto.getToDate().toString()).append("',1,19) ")
                    .append(", 'yyyy-MM-dd HH24:MI:SS,FF') + 1) ");
        }
        return  whereClause.toString();
    }

    /**
     * Perform updating or inserting new items to the stock of agency
     * @param agentEntity
     * @param soLuongNhap
     * @return StockAgentEntity
     * @throws DuplicateKeyException
     */
    private StockAgentEntity newOrUpdateStock(DepartmentEntity agentEntity, Integer soLuongNhap)throws DuplicateKeyException{
        try{
            StockAgentEntity stockAgentEntity = this.stockAgentLocalBean.findByDepartmentId(agentEntity.getDepartmentId());
            if(stockAgentEntity == null){
                stockAgentEntity = new StockAgentEntity();
                stockAgentEntity.setDepartment(agentEntity);
                stockAgentEntity.setTotal(soLuongNhap);
                stockAgentEntity = this.stockAgentLocalBean.save(stockAgentEntity);
            }else{
                stockAgentEntity.setTotal(stockAgentEntity.getTotal() + soLuongNhap);
                stockAgentEntity = this.stockAgentLocalBean.update(stockAgentEntity);
            }
            return stockAgentEntity;
        }catch (DuplicateKeyException de){
            throw new DuplicateKeyException("Duplicate key for StockAgent with agentId: " + agentEntity.getDepartmentId());
        }
    }
}
