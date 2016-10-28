package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.core.business.BaoCaoChiTietHangMucPSC_kpp_mua_btg_tu_mobifone_theoDBH_managementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BaoCaoChiTietHangMucPSCKPP_MuaBTGTuMobiFone_TheoDBHDTO;
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
 * Time: 2:54 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "BaoCaoChiTietHangMucPSC_kpp_mua_btg_tu_mobifone_theoDBH_managementSessionEJB")
public class BaoCaoChiTietHangMucPSC_kpp_mua_btg_tu_mobifone_theoDBH_managementSessionBean implements BaoCaoChiTietHangMucPSC_kpp_mua_btg_tu_mobifone_theoDBH_managementLocalBean{
    @EJB
    private CTTichDiemLocalBean ctTichDiemLocalBean;

    public BaoCaoChiTietHangMucPSC_kpp_mua_btg_tu_mobifone_theoDBH_managementSessionBean() {
    }

    @Override
    public Object[] search(Map<String, Object> properties, Integer firstItem, Integer reportMaxPageItems, String sortExpression, String sortDirection) {
        Object[] resultObject = this.ctTichDiemLocalBean.baoCaoChiTietHangMucPSC_kpp_mua_btg_tu_mobiFone_theoDaiLy(properties, firstItem, reportMaxPageItems, sortExpression, sortDirection);
        List<BaoCaoChiTietHangMucPSCKPP_MuaBTGTuMobiFone_TheoDBHDTO> dtoList = new ArrayList<BaoCaoChiTietHangMucPSCKPP_MuaBTGTuMobiFone_TheoDBHDTO>();
        for(Object object : (List)resultObject[1]){
            Object[] tmpArr = (Object[])object;
            BaoCaoChiTietHangMucPSCKPP_MuaBTGTuMobiFone_TheoDBHDTO dto = new BaoCaoChiTietHangMucPSCKPP_MuaBTGTuMobiFone_TheoDBHDTO();
            dto.setBranch_Name(tmpArr[0] != null ? tmpArr[0].toString() : null);
            dto.setDistrict_Name(tmpArr[1] != null ? tmpArr[1].toString() : null);
            dto.setDealer_Code(tmpArr[2] != null ? tmpArr[2].toString() : null);
            dto.setDealer_Name(tmpArr[3] != null ? tmpArr[3].toString() : null);
            dto.setNgay_don_hang(tmpArr[4] != null ? Timestamp.valueOf(tmpArr[4].toString()) : null);
            dto.setDonHang(tmpArr[5] != null ? tmpArr[5].toString() : null);
//            dto.setTuSerial(tmpArr[6] != null ? tmpArr[6].toString() : null);
//            dto.setDenSerial(tmpArr[7] != null ? tmpArr[7].toString() : null);
            dto.setSoLuongBTG(tmpArr[6] != null ? Double.valueOf(tmpArr[6].toString()) : null);
            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return resultObject;
    }
}
