package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.core.business.ActionLogManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.ActionLogDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.RetailDealerDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.UserDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015.RegistrationTransDTO;
import com.benluck.vms.mobifonedataseller.session.ActionLogLocalBean;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 3/16/15
 * Time: 11:15 AM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "ActionLogManagementSessionEJB")
public class ActionLogManagementSessionBean implements ActionLogManagementLocalBean {
    public ActionLogManagementSessionBean() {
    }

    @EJB
    private ActionLogLocalBean actionLogLocalBean;

    @Override
    public Object[] search(Map<String, Object> properties, String sortExpression, String sortDirection, Integer firstItem, Integer maxPageItems) {
        Object[] resultObject = this.actionLogLocalBean.search(properties, sortExpression, sortDirection, firstItem, maxPageItems);
        List<ActionLogDTO> dtoList = new ArrayList<ActionLogDTO>();
        for (Object object : (List)resultObject[1]) {
            Object[] tmpArr = (Object[])object;

            ActionLogDTO dto = new ActionLogDTO();

            if(tmpArr[0] != null){
                UserDTO userDTO = new UserDTO();
                userDTO.setDisplayName(tmpArr[0] != null ? tmpArr[0].toString() : null);
                dto.setUser(userDTO);
            }
            if(tmpArr[1] != null && tmpArr[2] != null){
                RegistrationTransDTO registrationTransDTO = new RegistrationTransDTO();
                registrationTransDTO.setProm_Amount(tmpArr[1] != null ? Double.valueOf(tmpArr[1].toString()) : null);
                dto.setRegistrationTrans(registrationTransDTO);
            }
            dto.setCreatedTime(tmpArr[2] != null ? Timestamp.valueOf(tmpArr[2].toString()) : null);
            if(tmpArr[3] != null && tmpArr [4] != null){
                RetailDealerDTO diemBanHang = new RetailDealerDTO();
                diemBanHang.setDealer_code(tmpArr[3] != null ? tmpArr[3].toString() : null);
                diemBanHang.setDealer_name(tmpArr[4] != null ? tmpArr[4].toString() : null);
                dto.setRetailDealer(diemBanHang);
            }

            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return resultObject;
    }
}
