package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.core.business.GiftAgentTransferHistoryManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.GiftAgentTransferHistoryDTO;
import com.benluck.vms.mobifonedataseller.session.GiftAgentTransferHistoryLocalBean;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 11/5/14
 * Time: 5:37 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "GiftAgentTransferHistoryManagementSessionEJB")
public class GiftAgentTransferHistoryManagementSessionBean implements GiftAgentTransferHistoryManagementLocalBean{
    @EJB
    private GiftAgentTransferHistoryLocalBean giftAgentTransferHistoryLocalBean;

    public GiftAgentTransferHistoryManagementSessionBean() {
    }

    @Override
    public Object[] search_tdcg(Long departmentId, Timestamp fromDateTime, Timestamp toDateTime, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) {
        Object[] resultObject = this.giftAgentTransferHistoryLocalBean.search(departmentId, fromDateTime, toDateTime, firstItem, maxPageItems, sortExpression, sortDirection);
        List<GiftAgentTransferHistoryDTO> dtoList = new ArrayList<GiftAgentTransferHistoryDTO>();
        for(Object object : (List)resultObject[1]){
            Object[] tmpArr = (Object[])object;
            GiftAgentTransferHistoryDTO dto = new GiftAgentTransferHistoryDTO();
            dto.setGiftAgentTransferHistoryId(tmpArr[0] != null ? Long.valueOf(tmpArr[0].toString()) : null);
            dto.setCreatedDate(tmpArr[1] != null ? Timestamp.valueOf(tmpArr[1].toString()) : null);
            dto.setQuantity(tmpArr[2] != null ? Integer.valueOf(tmpArr[2].toString()) : null);
            dto.setNguoi_xuat(tmpArr[3] != null ? tmpArr[3].toString() : null);
            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return resultObject;
    }

    @Override
    public Object[] search_qStudent(Long departmentId, Timestamp fromDateTime, Timestamp toDateTime, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) {
        Object[] resultObject = this.giftAgentTransferHistoryLocalBean.search(departmentId, fromDateTime, toDateTime, firstItem, maxPageItems, sortExpression, sortDirection);
        List<GiftAgentTransferHistoryDTO> dtoList = new ArrayList<GiftAgentTransferHistoryDTO>();
        for(Object object : (List)resultObject[1]){
            Object[] tmpArr = (Object[])object;
            GiftAgentTransferHistoryDTO dto = new GiftAgentTransferHistoryDTO();
            dto.setGiftAgentTransferHistoryId(tmpArr[0] != null ? Long.valueOf(tmpArr[0].toString()) : null);
            dto.setCreatedDate(tmpArr[1] != null ? Timestamp.valueOf(tmpArr[1].toString()) : null);
            dto.setQuantity(tmpArr[2] != null ? Integer.valueOf(tmpArr[2].toString()) : null);
            dto.setNguoi_xuat(tmpArr[3] != null ? tmpArr[3].toString() : null);
            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return resultObject;
    }
}
