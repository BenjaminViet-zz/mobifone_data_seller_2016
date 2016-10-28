package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.core.business.BaoCaoChiTietHangMucPSC_kpp_ban_vas_theoQuanHuyen_managementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BaoCaoChiTietHangMucPSCKPP_ban_vas_TheoQuanHuyenDTO;
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
 * Time: 7:24 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "BaoCaoChiTietHangMucPSC_kpp_ban_vas_theoQuanHuyen_managementSessionEJB")
public class BaoCaoChiTietHangMucPSC_kpp_ban_vas_theoQuanHuyen_managementSessionBean implements BaoCaoChiTietHangMucPSC_kpp_ban_vas_theoQuanHuyen_managementLocalBean{
    @EJB
    private CTTichDiemLocalBean ctTichDiemLocalBean;

    public BaoCaoChiTietHangMucPSC_kpp_ban_vas_theoQuanHuyen_managementSessionBean() {
    }

    @Override
    public Object[] search(Map<String, Object> properties, Integer firstItem, Integer reportMaxPageItems, String sortExpression, String sortDirection) {
        Object[] resultObject = this.ctTichDiemLocalBean.baoCaoChiTietHangMucPSC_kpp_ban_vas_theoQuanHuyen(properties, firstItem, reportMaxPageItems, sortExpression, sortDirection);
        List<BaoCaoChiTietHangMucPSCKPP_ban_vas_TheoQuanHuyenDTO> dtoList = new ArrayList<BaoCaoChiTietHangMucPSCKPP_ban_vas_TheoQuanHuyenDTO>();
        for(Object object : (List)resultObject[1]){
            Object[] tmpArr = (Object[])object;
            BaoCaoChiTietHangMucPSCKPP_ban_vas_TheoQuanHuyenDTO dto = new BaoCaoChiTietHangMucPSCKPP_ban_vas_TheoQuanHuyenDTO();
            dto.setBranch_Name(tmpArr[0] != null ? tmpArr[0].toString() : null);
            dto.setDistrict_Name(tmpArr[1] != null ? tmpArr[1].toString() : null);
            dto.setLoaiVAS(tmpArr[2] != null ? tmpArr[2].toString() : null);
            dto.setSoLuong(tmpArr[3] != null ? Double.valueOf(tmpArr[3].toString()) : null);
            dto.setDoanhThu(tmpArr[4] != null ? Double.valueOf(tmpArr[4].toString()) : null);
            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return resultObject;
    }
}
