package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.core.business.PromItemsManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.PromItemsDTO;
import com.benluck.vms.mobifonedataseller.session.CTTichDiemLocalBean;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 11/7/14
 * Time: 12:14 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "PromItemsManagementSessionEJB")
public class PromItemsManagementSessionBean implements PromItemsManagementLocalBean{
    @EJB
    private CTTichDiemLocalBean ctTichDiemLocalBean;

    public PromItemsManagementSessionBean() {
    }

    @Override
    public List<PromItemsDTO> findAll() {
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
