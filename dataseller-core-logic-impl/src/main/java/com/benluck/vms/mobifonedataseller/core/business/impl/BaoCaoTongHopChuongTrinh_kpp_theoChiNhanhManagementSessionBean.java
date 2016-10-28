package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.core.business.BaoCaoTongHopChuongTrinh_kpp_theoChiNhanhManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BaoCaoTongHopKetQuaChuongTrinhKPPTheoChiNhanhOrQuanHuyenDTO;
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
 * Date: 11/16/14
 * Time: 7:45 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "BaoCaoTongHopChuongTrinh_kpp_theoChiNhanhManagementSessionEJB")
public class BaoCaoTongHopChuongTrinh_kpp_theoChiNhanhManagementSessionBean implements BaoCaoTongHopChuongTrinh_kpp_theoChiNhanhManagementLocalBean{
    @EJB
    private CTTichDiemLocalBean ctTichDiemLocalBean;
    public BaoCaoTongHopChuongTrinh_kpp_theoChiNhanhManagementSessionBean() {
    }

    @Override
    public Object[] search(Map<String, Object> properties, Integer firstItem, Integer reportMaxPageItems, String sortExpression, String sortDirection) {
        boolean hasFilterDate = false;
        if(properties.get("fromDateTime") != null || properties.get("toDateTime") != null){
            hasFilterDate = true;
        }
        Object[] resultObject = this.ctTichDiemLocalBean.baoCaoTongHopChuongTrinhKPP_theoChiNhanh(properties, hasFilterDate, firstItem, reportMaxPageItems, sortExpression, sortDirection);
        List<BaoCaoTongHopKetQuaChuongTrinhKPPTheoChiNhanhOrQuanHuyenDTO> dtoList = new ArrayList<BaoCaoTongHopKetQuaChuongTrinhKPPTheoChiNhanhOrQuanHuyenDTO>();
        for(Object object : (List)resultObject[1]){
            Object[] tmpArr = (Object[])object;
            int colIndex = 0;
            BaoCaoTongHopKetQuaChuongTrinhKPPTheoChiNhanhOrQuanHuyenDTO dto = new BaoCaoTongHopKetQuaChuongTrinhKPPTheoChiNhanhOrQuanHuyenDTO();
            dto.setBranch_Name(tmpArr[colIndex] != null ? tmpArr[colIndex].toString() : null);
            dto.setGiaiDoan(tmpArr[colIndex + 1] != null ? Integer.valueOf(tmpArr[colIndex + 1].toString()) : null);
            if(hasFilterDate){
                dto.setNgay(tmpArr[colIndex + 2] != null ? Timestamp.valueOf(tmpArr[colIndex + 2].toString()) : null);
            }else{
                colIndex -= 1;
            }
            dto.setSoLuongDBHThamGia(tmpArr[colIndex + 3] != null ? Integer.valueOf(tmpArr[colIndex + 3].toString()) : 0);
            dto.setSoLuongDBHDat(tmpArr[colIndex + 4] != null ? Integer.valueOf(tmpArr[colIndex + 4].toString()) : 0);
            dto.setSoLuongBTG_MuaTuMobifone(tmpArr[colIndex + 5] != null ? Double.valueOf(tmpArr[colIndex + 5].toString()) : new Double(0));
            dto.setDoanhThuMuaThe(tmpArr[colIndex + 6] != null ? Double.valueOf(tmpArr[colIndex + 6].toString()) : new Double(0));
            dto.setSoLuong_VAS(tmpArr[colIndex + 7] != null ? Double.valueOf(tmpArr[colIndex + 7].toString()) : new Double(0));
            dto.setDoanhThu_VAS(tmpArr[colIndex + 8] != null ? Double.valueOf(tmpArr[colIndex + 8].toString()) : new Double(0));
            dto.setSoLuongTon_BanTrucTiepBTG(tmpArr[colIndex + 9] != null ? Double.valueOf(tmpArr[colIndex + 9].toString()) : new Double(0));
            dto.setSoLuongMoi_BanTrucTiepBTG(tmpArr[colIndex + 10] != null ? Double.valueOf(tmpArr[colIndex + 10].toString()) : new Double(0));
            dto.setCuocPSBoTon_BanTrucTiepBTG(tmpArr[colIndex + 11] != null ? Double.valueOf(tmpArr[colIndex + 11].toString()) : new Double(0));
            dto.setCuocPSBoMoi_BanTrucTiepBTG(tmpArr[colIndex + 12] != null ? Double.valueOf(tmpArr[colIndex + 12].toString()) : new Double(0));
            dto.setSoLuong_GioiThieuKH(tmpArr[colIndex + 13] != null ? Double.valueOf(tmpArr[colIndex + 13].toString()) : new Double(0));
            dto.setSoLuongCuocPS_GioiThieuKH(tmpArr[colIndex + 14] != null ? Double.valueOf(tmpArr[colIndex + 14].toString()) : new Double(0));
            dto.setCuocPS_GioiThieuKH(tmpArr[colIndex + 15] != null ? Double.valueOf(tmpArr[colIndex + 15].toString()) : new Double(0));
            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return resultObject;
    }
}
