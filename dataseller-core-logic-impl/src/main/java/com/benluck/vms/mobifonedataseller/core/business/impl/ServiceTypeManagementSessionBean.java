package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.core.business.ServiceTypeManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.ServiceTypeDTO;
import com.benluck.vms.mobifonedataseller.session.CTTichDiemLocalBean;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 11/19/14
 * Time: 10:07 AM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "ServiceTypeManagementSessionEJB")
public class ServiceTypeManagementSessionBean implements ServiceTypeManagementLocalBean{
    @EJB
    private CTTichDiemLocalBean ctTichDiemLocalBean;

    public ServiceTypeManagementSessionBean() {
    }

    @Override
    public List<ServiceTypeDTO> findServiceTypeList() {
        List resultSet = this.ctTichDiemLocalBean.findServiceTypeList();
        List<ServiceTypeDTO> dtoList = new ArrayList<ServiceTypeDTO>();
        for(Object object : resultSet){
            Object[] tmpArr = (Object[])object;
            ServiceTypeDTO dto = new ServiceTypeDTO();
            dto.setCode(tmpArr[0] != null ? tmpArr[0].toString() : null);
            dto.setName(tmpArr[1] != null ? tmpArr[1].toString() : null);
            dtoList.add(dto);
        }
        return dtoList;
    }
}
