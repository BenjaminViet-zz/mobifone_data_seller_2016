package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.core.business.BaoCaoChiTietHangMucPSC_kpp_ban_truc_tiep_btg_va_psc_theoDBH_managementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BaoCaoChiTietHangMucPSCKPP_ban_truc_tiep_btg_va_psc_TheoDBHDTO;
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
 * Time: 1:46 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "BaoCaoChiTietHangMucPSC_kpp_ban_truc_tiep_btg_va_psc_theoDBH_managementSessionEJB")
public class BaoCaoChiTietHangMucPSC_kpp_ban_truc_tiep_btg_va_psc_theoDBH_managementSessionBean implements BaoCaoChiTietHangMucPSC_kpp_ban_truc_tiep_btg_va_psc_theoDBH_managementLocalBean{
    @EJB
    private CTTichDiemLocalBean ctTichDiemLocalBean;

    public BaoCaoChiTietHangMucPSC_kpp_ban_truc_tiep_btg_va_psc_theoDBH_managementSessionBean() {
    }

    @Override
    public Object[] search(Map<String, Object> properties, Integer firstItem, Integer reportMaxPageItems, String sortExpression, String sortDirection) {
        Object[] resultObject = this.ctTichDiemLocalBean.baoCaoChiTietHangMucPSC_kpp_ban_truc_tiep_btg_va_psc_theoDBH(properties, firstItem, reportMaxPageItems, sortExpression, sortDirection);
        List<BaoCaoChiTietHangMucPSCKPP_ban_truc_tiep_btg_va_psc_TheoDBHDTO> dtoList = new ArrayList<BaoCaoChiTietHangMucPSCKPP_ban_truc_tiep_btg_va_psc_TheoDBHDTO>();
        for(Object object : (List)resultObject[1]){
            Object[] tmpArr = (Object[])object;
            BaoCaoChiTietHangMucPSCKPP_ban_truc_tiep_btg_va_psc_TheoDBHDTO dto = new BaoCaoChiTietHangMucPSCKPP_ban_truc_tiep_btg_va_psc_TheoDBHDTO();
            dto.setBranch_Name(tmpArr[0] != null ? tmpArr[0].toString() : null);
            dto.setDistrict_Name(tmpArr[1] != null ? tmpArr[1].toString() : null);
            dto.setDealer_Code(tmpArr[2] != null ? tmpArr[2].toString() : null);
            dto.setDealer_Name(tmpArr[3] != null ? tmpArr[3].toString() : null);
            dto.setSoEZ(tmpArr[4] != null ? tmpArr[4].toString() : null);
            dto.setNgay_nhan_tin(tmpArr[5] != null ? Timestamp.valueOf(tmpArr[5].toString()) : null);
            dto.setSoThueBaoKH(tmpArr[6] != null ? tmpArr[6].toString() : null);
            dto.setNgay_kich(tmpArr[7] != null ? Integer.valueOf(tmpArr[7].toString()) : null);
            dto.setThang_kich(tmpArr[8] != null ? Integer.valueOf(tmpArr[8].toString()) : null);
            dto.setNam_kich(tmpArr[9] != null ? Integer.valueOf(tmpArr[9].toString()) : null);
            dto.setCuocPS_thoai(tmpArr[10] != null ? Double.valueOf(tmpArr[10].toString()) : new Double(0));
            dto.setCuocPS_sms(tmpArr[11] != null ? Double.valueOf(tmpArr[11].toString()) : new Double(0));
            dto.setCuocPS_data(tmpArr[12] != null ? Double.valueOf(tmpArr[12].toString()) : new Double(0));
            dto.setCuocPS_khac(tmpArr[13] != null ? Double.valueOf(tmpArr[13].toString()) : new Double(0));
            dto.setCuocPS_total(dto.getCuocPS_thoai() + dto.getCuocPS_sms() + dto.getCuocPS_data() + dto.getCuocPS_khac());
            dto.setKhuyen_mai(tmpArr[14] != null ? Double.valueOf(tmpArr[14].toString()) : new Double(0));
            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return resultObject;
    }
}
