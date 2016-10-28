package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.core.business.BaoCaoChiTietHangMucPSC_kpp_mua_the_tu_mobifone_theoQuanHuyen_managementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BaoCaoChiTietHangMucPSCKPP_MuaTheTuMobiFone_TheoQuanHuyenDTO;
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
 * Time: 1:20 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "BaoCaoChiTietHangMucPSC_kpp_mua_the_tu_mobifone_theoQuanHuyen_managementSessionEJB")
public class BaoCaoChiTietHangMucPSC_kpp_mua_the_tu_mobifone_theoQuanHuyen_managementSessionBean implements BaoCaoChiTietHangMucPSC_kpp_mua_the_tu_mobifone_theoQuanHuyen_managementLocalBean{
    @EJB
    private CTTichDiemLocalBean ctTichDiemLocalBean;

    public BaoCaoChiTietHangMucPSC_kpp_mua_the_tu_mobifone_theoQuanHuyen_managementSessionBean() {
    }

    @Override
    public Object[] search(Map<String, Object> properties, Integer firstItem, Integer reportMaxPageItems, String sortExpression, String sortDirection) {
        //        Object[] resultObject = this.ctTichDiemLocalBean.baoCaoChiTietHangMucPSC_kpp_mua_the_tu_mobiFone_theoQuanHuyen(properties, firstItem, reportMaxPageItems, sortExpression, sortDirection);
        Object[] resultObject = new Object[]{0, new ArrayList<>()};
        List<BaoCaoChiTietHangMucPSCKPP_MuaTheTuMobiFone_TheoQuanHuyenDTO> dtoList = new ArrayList<BaoCaoChiTietHangMucPSCKPP_MuaTheTuMobiFone_TheoQuanHuyenDTO>();
        for(Object object : (List)resultObject[1]){
            Object[] tmpArr = (Object[])object;
            BaoCaoChiTietHangMucPSCKPP_MuaTheTuMobiFone_TheoQuanHuyenDTO dto = new BaoCaoChiTietHangMucPSCKPP_MuaTheTuMobiFone_TheoQuanHuyenDTO();
            dto.setBranch_Name(tmpArr[0] != null ? tmpArr[0].toString() : null);
            dto.setDistrict_Name(tmpArr[1] != null ? tmpArr[1].toString() : null);
            dto.setNgay_don_hang(tmpArr[2] != null ? Timestamp.valueOf(tmpArr[2].toString()) : null);
            dto.setDonHang(tmpArr[3] != null ? tmpArr[3].toString() : null);
            dto.setTuSerial(tmpArr[4] != null ? tmpArr[4].toString() : null);
            dto.setDenSerial(tmpArr[5] != null ? tmpArr[5].toString() : null);
            dto.setDoanhSoTheTrenHoaDon(tmpArr[6] != null ? Double.valueOf(tmpArr[6].toString()) : null);
            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return resultObject;
    }
}
