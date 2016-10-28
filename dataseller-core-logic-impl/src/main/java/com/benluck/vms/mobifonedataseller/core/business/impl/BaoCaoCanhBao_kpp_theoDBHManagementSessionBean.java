package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.core.business.BaoCaoCanhBao_kpp_theoDBHManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BaoCaoCanhBao_KPP_TheoDBHDTO;
import com.benluck.vms.mobifonedataseller.session.CTTichDiemLocalBean;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 11/20/14
 * Time: 5:35 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "BaoCaoCanhBao_kpp_theoDBHManagementSessionEJB")
public class BaoCaoCanhBao_kpp_theoDBHManagementSessionBean implements BaoCaoCanhBao_kpp_theoDBHManagementLocalBean{
    @EJB
    private CTTichDiemLocalBean ctTichDiemLocalBean;

    public BaoCaoCanhBao_kpp_theoDBHManagementSessionBean() {
    }

    @Override
    public Object[] search(Map<String, Object> properties, Integer firstItem, Integer reportMaxPageItems, String sortExpression, String sortDirection) {
        Object[] resultObject = this.ctTichDiemLocalBean.baoCaoCanhBao_kpp_theoDBH(properties, firstItem, reportMaxPageItems, sortExpression, sortDirection);
        List<BaoCaoCanhBao_KPP_TheoDBHDTO> dtoList = new ArrayList<BaoCaoCanhBao_KPP_TheoDBHDTO>();
        for(Object object : (List)resultObject[1]){
            Object[] tmpArr = (Object[])object;
            BaoCaoCanhBao_KPP_TheoDBHDTO dto = new BaoCaoCanhBao_KPP_TheoDBHDTO();
            dto.setBranch_Name(tmpArr[0] != null ? tmpArr[0].toString() : null);
            dto.setDistrict_Name(tmpArr[1] != null ? tmpArr[1].toString() : null);
            dto.setDealer_Code(tmpArr[2] != null ? tmpArr[2].toString() : null);
            dto.setDealer_Name(tmpArr[3] != null ? tmpArr[3].toString() : null);
            dto.setChuKy(tmpArr[4] != null ? Integer.valueOf(tmpArr[4].toString()) : null);
            dto.setItem_Name(tmpArr[5] != null ? tmpArr[5].toString() : null);
            dto.setSoPhatSinh(tmpArr[6] != null ? Double.valueOf(tmpArr[6].toString()) : null);
            dto.setSoDiemDaDat(tmpArr[7] != null ? Double.valueOf(tmpArr[7].toString()) : null);
            dto.setSoPhaiDat(tmpArr[8] != null ? Double.valueOf(tmpArr[8].toString()) : null);
            dto.setNgayTongHop(tmpArr[9] != null ? Timestamp.valueOf(tmpArr[9].toString()) : null);
            dto.setThoiGianConLai(tmpArr[10] != null ? Integer.valueOf(tmpArr[10].toString()) : null);
            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return resultObject;
    }
}
