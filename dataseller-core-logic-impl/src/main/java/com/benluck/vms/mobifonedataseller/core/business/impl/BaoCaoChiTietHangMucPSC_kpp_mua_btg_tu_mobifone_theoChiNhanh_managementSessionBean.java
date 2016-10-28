package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.core.business.BaoCaoChiTietHangMucPSC_kpp_mua_btg_tu_mobifone_theoChiNhanh_managementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BaoCaoChiTietHangMucPSCKPP_MuaBTGTuMobiFone_TheoChiNhanhDTO;
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
 * Date: 11/18/14
 * Time: 1:12 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "BaoCaoChiTietHangMucPSC_kpp_mua_btg_tu_mobifone_theoChiNhanh_managementSessionEJB")
public class BaoCaoChiTietHangMucPSC_kpp_mua_btg_tu_mobifone_theoChiNhanh_managementSessionBean implements BaoCaoChiTietHangMucPSC_kpp_mua_btg_tu_mobifone_theoChiNhanh_managementLocalBean{
    @EJB
    private CTTichDiemLocalBean ctTichDiemLocalBean;

    public BaoCaoChiTietHangMucPSC_kpp_mua_btg_tu_mobifone_theoChiNhanh_managementSessionBean() {
    }

    @Override
    public Object[] search(Map<String, Object> properties, Integer firstItem, Integer reportMaxPageItems, String sortExpression, String sortDirection) {
        Object[] resultObject = this.ctTichDiemLocalBean.baoCaoChiTietHangMucPSC_kpp_mua_btg_tu_mobiFone_theoChiNhanh(properties, firstItem, reportMaxPageItems, sortExpression, sortDirection);
        List<BaoCaoChiTietHangMucPSCKPP_MuaBTGTuMobiFone_TheoChiNhanhDTO> dtoList = new ArrayList<BaoCaoChiTietHangMucPSCKPP_MuaBTGTuMobiFone_TheoChiNhanhDTO>();
        for(Object object : (List)resultObject[1]){
            Object[] tmpArr = (Object[])object;
            BaoCaoChiTietHangMucPSCKPP_MuaBTGTuMobiFone_TheoChiNhanhDTO dto = new BaoCaoChiTietHangMucPSCKPP_MuaBTGTuMobiFone_TheoChiNhanhDTO();
            dto.setBranch_Name(tmpArr[0] != null ? tmpArr[0].toString() : null);
            dto.setNgay_don_hang(tmpArr[1] != null ? Timestamp.valueOf(tmpArr[1].toString()) : null);
            dto.setDonHang(tmpArr[2] != null ? tmpArr[2].toString() : null);
            dto.setTuSerial(tmpArr[3] != null ? tmpArr[3].toString() : null);
            dto.setDenSerial(tmpArr[4] != null ? tmpArr[4].toString() : null);
            dto.setSoLuongBTG(tmpArr[5] != null ? Double.valueOf(tmpArr[5].toString()) : null);
            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return resultObject;
    }
}
