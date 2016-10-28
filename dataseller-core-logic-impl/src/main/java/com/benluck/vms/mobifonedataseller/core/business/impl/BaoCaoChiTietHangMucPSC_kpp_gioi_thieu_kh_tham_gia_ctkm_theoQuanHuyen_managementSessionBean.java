package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.core.business.BaoCaoChiTietHangMucPSC_kpp_gioi_thieu_kh_tham_gia_ctkm_theoQuanHuyen_managementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BaoCaoChiTietHangMucPSCKPP_gioi_thieu_kh_tham_gia_ctkm_TheoQuanHuyenDTO;
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
 * Time: 4:19 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "BaoCaoChiTietHangMucPSC_kpp_gioi_thieu_kh_tham_gia_ctkm_theoQuanHuyen_managementSessionEJB")
public class BaoCaoChiTietHangMucPSC_kpp_gioi_thieu_kh_tham_gia_ctkm_theoQuanHuyen_managementSessionBean implements BaoCaoChiTietHangMucPSC_kpp_gioi_thieu_kh_tham_gia_ctkm_theoQuanHuyen_managementLocalBean{
    @EJB
    private CTTichDiemLocalBean ctTichDiemLocalBean;

    public BaoCaoChiTietHangMucPSC_kpp_gioi_thieu_kh_tham_gia_ctkm_theoQuanHuyen_managementSessionBean() {
    }

    @Override
    public Object[] search(Map<String, Object> properties, Integer firstItem, Integer reportMaxPageItems, String sortExpression, String sortDirection) {
        Object[] resultObject = this.ctTichDiemLocalBean.baoCaoChiTietHangMucPSC_kpp_gioi_thieu_kh_tham_gia_ctkm_theoQuanHuyen(properties, firstItem, reportMaxPageItems, sortExpression, sortDirection);
        List<BaoCaoChiTietHangMucPSCKPP_gioi_thieu_kh_tham_gia_ctkm_TheoQuanHuyenDTO> dtoList = new ArrayList<BaoCaoChiTietHangMucPSCKPP_gioi_thieu_kh_tham_gia_ctkm_TheoQuanHuyenDTO>();
        for(Object object : (List)resultObject[1]){
            Object[] tmpArr = (Object[])object;
            BaoCaoChiTietHangMucPSCKPP_gioi_thieu_kh_tham_gia_ctkm_TheoQuanHuyenDTO dto = new BaoCaoChiTietHangMucPSCKPP_gioi_thieu_kh_tham_gia_ctkm_TheoQuanHuyenDTO();
            dto.setBranch_Name(tmpArr[0] != null ? tmpArr[0].toString() : null);
            dto.setDistrict_Name(tmpArr[1] != null ? tmpArr[1].toString() : null);
            dto.setCuocPS_thoai(tmpArr[2] != null ? Double.valueOf(tmpArr[2].toString()) : null);
            dto.setCuocPS_sms(tmpArr[3] != null ? Double.valueOf(tmpArr[3].toString()) : null);
            dto.setCuocPS_data(tmpArr[4] != null ? Double.valueOf(tmpArr[4].toString()) : null);
            dto.setCuocPS_khac(tmpArr[5] != null ? Double.valueOf(tmpArr[5].toString()) : null);
            dto.setCuocPS_total(dto.getCuocPS_thoai() + dto.getCuocPS_sms() + dto.getCuocPS_data() + dto.getCuocPS_khac());
            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return resultObject;
    }
}
