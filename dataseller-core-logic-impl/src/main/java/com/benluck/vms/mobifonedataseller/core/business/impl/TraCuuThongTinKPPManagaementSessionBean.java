package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.core.business.TraCuuThongTinKPPManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.DealerAccountActionDTO;
import com.benluck.vms.mobifonedataseller.session.CTTichDiemLocalBean;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 11/7/14
 * Time: 12:51 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "TraCuuThongTinKPPManagaementSessionEJB")
public class TraCuuThongTinKPPManagaementSessionBean implements TraCuuThongTinKPPManagementLocalBean {
    @EJB
    private CTTichDiemLocalBean ctTichDiemLocalBean;

    public TraCuuThongTinKPPManagaementSessionBean() {
    }

    @Override
    public Object[] search(Map<String, Object> properties, Integer firstItems, Integer maxPageItems, String sortExpression, String sortDirection) {
        Object[] resultObject = this.ctTichDiemLocalBean.searchKPPInformationByProperties(properties, firstItems, maxPageItems, sortExpression, sortDirection);
        List<DealerAccountActionDTO> dtoList = new ArrayList<DealerAccountActionDTO>();

        for(Object object : (List)resultObject[1]){
            Object[] tmpArr = (Object[])object;
            DealerAccountActionDTO dto = new DealerAccountActionDTO();
            dto.setItem_Id(tmpArr[0] != null ? Long.valueOf(tmpArr[0].toString()) : null);
            dto.setTenLoaiPS(tmpArr[1] != null ? tmpArr[1].toString() : null);
            dto.setBranch_Name(tmpArr[2] != null ? tmpArr[2].toString() : null);
            dto.setDistrict_Name(tmpArr[3] != null ? tmpArr[3].toString() : null);
            dto.setDealer_Id(tmpArr[4] != null ? Long.valueOf(tmpArr[4].toString()) : null);
            dto.setDealer_Code(tmpArr[5] != null ? tmpArr[5].toString() : null);
            dto.setDealer_Name(tmpArr[6] != null ? tmpArr[6].toString() : null);
            dto.setCycle(tmpArr[7] != null ? Integer.valueOf(tmpArr[7].toString()) : null);
            dto.setProm_point(tmpArr[8] != null ? Integer.valueOf(tmpArr[8].toString()) : null);
            dto.setSoTienTuongUng(tmpArr[9] != null ? Double.valueOf(tmpArr[9].toString()) : null);
            dto.setSoMaDuThuong(tmpArr[10] != null ? Integer.valueOf(tmpArr[10].toString()) : null);
            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return resultObject;
    }
}
