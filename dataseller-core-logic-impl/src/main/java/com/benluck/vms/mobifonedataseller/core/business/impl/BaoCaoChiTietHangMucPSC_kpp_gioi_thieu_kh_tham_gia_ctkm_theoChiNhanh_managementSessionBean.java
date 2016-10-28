package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.core.business.BaoCaoChiTietHangMucPSC_kpp_gioi_thieu_kh_tham_gia_ctkm_theoChiNhanh_managementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BaoCaoChiTietHangMucPSCKPP_gioi_thieu_kh_tham_gia_ctkm_TheoChiNhanhDTO;
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
 * Time: 4:20 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "BaoCaoChiTietHangMucPSC_kpp_gioi_thieu_kh_tham_gia_ctkm_theoChiNhanh_managementSessionEJB")
public class BaoCaoChiTietHangMucPSC_kpp_gioi_thieu_kh_tham_gia_ctkm_theoChiNhanh_managementSessionBean implements BaoCaoChiTietHangMucPSC_kpp_gioi_thieu_kh_tham_gia_ctkm_theoChiNhanh_managementLocalBean{
    @EJB
    private CTTichDiemLocalBean ctTichDiemLocalBean;

    public BaoCaoChiTietHangMucPSC_kpp_gioi_thieu_kh_tham_gia_ctkm_theoChiNhanh_managementSessionBean() {
    }

    @Override
    public Object[] search(Map<String, Object> properties, Integer firstItem, Integer reportMaxPageItems, String sortExpression, String sortDirection) {
        Object[] resultObject = this.ctTichDiemLocalBean.baoCaoChiTietHangMucPSC_kpp_gioi_thieu_kh_tham_gia_ctkm_theoChiNhanh(properties, firstItem, reportMaxPageItems, sortExpression, sortDirection);
        List<BaoCaoChiTietHangMucPSCKPP_gioi_thieu_kh_tham_gia_ctkm_TheoChiNhanhDTO> dtoList = new ArrayList<BaoCaoChiTietHangMucPSCKPP_gioi_thieu_kh_tham_gia_ctkm_TheoChiNhanhDTO>();
        for(Object object : (List)resultObject[1]){
            Object[] tmpArr = (Object[])object;
            BaoCaoChiTietHangMucPSCKPP_gioi_thieu_kh_tham_gia_ctkm_TheoChiNhanhDTO dto = new BaoCaoChiTietHangMucPSCKPP_gioi_thieu_kh_tham_gia_ctkm_TheoChiNhanhDTO();
            dto.setBranch_Name(tmpArr[0] != null ? tmpArr[0].toString() : null);
            dto.setCuocPS_thoai(tmpArr[1] != null ? Double.valueOf(tmpArr[1].toString()) : null);
            dto.setCuocPS_sms(tmpArr[2] != null ? Double.valueOf(tmpArr[2].toString()) : null);
            dto.setCuocPS_data(tmpArr[3] != null ? Double.valueOf(tmpArr[3].toString()) : null);
            dto.setCuocPS_khac(tmpArr[4] != null ? Double.valueOf(tmpArr[4].toString()) : null);
            dto.setCuocPS_total(dto.getCuocPS_thoai() + dto.getCuocPS_sms() + dto.getCuocPS_data() + dto.getCuocPS_khac());
            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return resultObject;
    }
}
