package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.core.business.BaoCaoCanhBao_kpp_theoQuanHuyenManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BaoCaoCanhBao_KPP_TheoQuanHuyenDTO;
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
 * Time: 5:56 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "BaoCaoCanhBao_kpp_theoQuanHuyenManagementSessionEJB")
public class BaoCaoCanhBao_kpp_theoQuanHuyenManagementSessionBean implements BaoCaoCanhBao_kpp_theoQuanHuyenManagementLocalBean{
    @EJB
    private CTTichDiemLocalBean ctTichDiemLocalBean;

    public BaoCaoCanhBao_kpp_theoQuanHuyenManagementSessionBean() {
    }

    @Override
    public Object[] search(Map<String, Object> properties, Integer firstItem, Integer reportMaxPageItems, String sortExpression, String sortDirection) {
        Object[] resultObject = this.ctTichDiemLocalBean.baoCaoCanhBao_kpp_theoQuanHuyen(properties, firstItem, reportMaxPageItems, sortExpression, sortDirection);
        List<BaoCaoCanhBao_KPP_TheoQuanHuyenDTO> dtoList = new ArrayList<BaoCaoCanhBao_KPP_TheoQuanHuyenDTO>();
        for(Object object : (List)resultObject[1]){
            Object[] tmpArr = (Object[])object;
            BaoCaoCanhBao_KPP_TheoQuanHuyenDTO dto = new BaoCaoCanhBao_KPP_TheoQuanHuyenDTO();
            dto.setBranch_Name(tmpArr[0] != null ? tmpArr[0].toString() : null);
            dto.setDistrict_Name(tmpArr[1] != null ? tmpArr[1].toString() : null);
            dto.setChuKy(tmpArr[2] != null ? Integer.valueOf(tmpArr[2].toString()) : null);
            dto.setItem_Name(tmpArr[3] != null ? tmpArr[3].toString() : null);
            dto.setSoPhatSinh(tmpArr[4] != null ? Double.valueOf(tmpArr[4].toString()) : null);
            dto.setSoDiemDaDat(tmpArr[5] != null ? Double.valueOf(tmpArr[5].toString()) : null);
            dto.setSoPhaiDat(tmpArr[6] != null ? Double.valueOf(tmpArr[6].toString()) : null);
            dto.setThoiGianConLai(tmpArr[7] != null ? Integer.valueOf(tmpArr[7].toString()) : null);
            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return resultObject;
    }
}
