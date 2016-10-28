package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.core.business.WarningReport_KPP_ByBranchManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BaoCaoCanhBao_KPP_TheoChiNhanhDTO;
import com.benluck.vms.mobifonedataseller.session.CTTichDiemLocalBean;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 11/20/14
 * Time: 5:57 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "BaoCaoCanhBao_kpp_theoChiNhanhManagementSessionEJB")
public class BaoCaoCanhBao_kpp_theoChiNhanhManagementSessionBean implements WarningReport_KPP_ByBranchManagementLocalBean {
    @EJB
    private CTTichDiemLocalBean ctTichDiemLocalBean;

    public BaoCaoCanhBao_kpp_theoChiNhanhManagementSessionBean() {
    }

    @Override
    public Object[] search(Map<String, Object> properties, Integer firstItem, Integer reportMaxPageItems, String sortExpression, String sortDirection) {
        Object[] resultObject = this.ctTichDiemLocalBean.baoCaoCanhBao_kpp_theoChiNhanh(properties, firstItem, reportMaxPageItems, sortExpression, sortDirection);
        List<BaoCaoCanhBao_KPP_TheoChiNhanhDTO> dtoList = new ArrayList<BaoCaoCanhBao_KPP_TheoChiNhanhDTO>();
        for(Object object : (List)resultObject[1]){
            Object[] tmpArr = (Object[])object;
            BaoCaoCanhBao_KPP_TheoChiNhanhDTO dto = new BaoCaoCanhBao_KPP_TheoChiNhanhDTO();
            dto.setBranch_Name(tmpArr[0] != null ? tmpArr[0].toString() : null);
            dto.setChuKy(tmpArr[1] != null ? Integer.valueOf(tmpArr[1].toString()) : null);
            dto.setItem_Name(tmpArr[2] != null ? tmpArr[2].toString() : null);
            dto.setSoPhatSinh(tmpArr[3] != null ? Double.valueOf(tmpArr[3].toString()) : null);
            dto.setSoDiemDaDat(tmpArr[4] != null ? Double.valueOf(tmpArr[4].toString()) : null);
            dto.setSoPhaiDat(tmpArr[5] != null ? Double.valueOf(tmpArr[5].toString()) : null);
            dto.setThoiGianConLai(tmpArr[6] != null ? Integer.valueOf(tmpArr[6].toString()) : null);
            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return resultObject;
    }
}
