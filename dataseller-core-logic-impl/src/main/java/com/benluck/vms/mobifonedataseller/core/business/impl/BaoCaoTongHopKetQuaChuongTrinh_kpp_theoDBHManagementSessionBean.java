package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.core.business.BaoCaoTongHopKetQuaChuongTrinh_kpp_theoDBHManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BaoCaoTongHopKetQuaChuongTrinhKPPTheoDaiLyDTO;
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
 * Time: 8:07 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "BaoCaoTongHopKetQuaChuongTrinh_kpp_theoDBHManagementSessionEJB")
public class BaoCaoTongHopKetQuaChuongTrinh_kpp_theoDBHManagementSessionBean implements BaoCaoTongHopKetQuaChuongTrinh_kpp_theoDBHManagementLocalBean{
    @EJB
    private CTTichDiemLocalBean ctTichDiemLocalBean;
    public BaoCaoTongHopKetQuaChuongTrinh_kpp_theoDBHManagementSessionBean() {
    }

    @Override
    public Object[] search(Map<String, Object> properties, Integer firstItem, Integer reportMaxPageItems, String sortExpression, String sortDirection) {
        boolean hasFilterDate = false;
        if(properties.get("fromDateTime") != null || properties.get("toDateTime") != null){
            hasFilterDate = true;
        }
        Object[] resultObject = this.ctTichDiemLocalBean.baoCaoTongHopChuongTrinhKPP_theoDaiLy(properties, hasFilterDate, firstItem, reportMaxPageItems, sortExpression, sortDirection);
        List<BaoCaoTongHopKetQuaChuongTrinhKPPTheoDaiLyDTO> dtoList = new ArrayList<BaoCaoTongHopKetQuaChuongTrinhKPPTheoDaiLyDTO>();
        for(Object object : (List)resultObject[1]){
            int colIndex = 0;
            Object[] tmpArr = (Object[])object;
            BaoCaoTongHopKetQuaChuongTrinhKPPTheoDaiLyDTO dto = new BaoCaoTongHopKetQuaChuongTrinhKPPTheoDaiLyDTO();
            dto.setBranch_Name(tmpArr[colIndex] != null ? tmpArr[colIndex].toString() : null);
            dto.setDistrict_Name(tmpArr[colIndex + 1] != null ? tmpArr[colIndex + 1].toString() : null);
            dto.setGiaiDoan(tmpArr[colIndex + 2] != null ? Integer.valueOf(tmpArr[colIndex + 2].toString()) : null);

            if(hasFilterDate){
                dto.setNgay(tmpArr[colIndex + 3] != null ? Timestamp.valueOf(tmpArr[colIndex + 3].toString()) : null);
            }else{
                colIndex -= 1;
            }
            dto.setDealer_Code(tmpArr[colIndex + 4] != null ? tmpArr[colIndex + 4].toString() : null);
            dto.setDealer_Name(tmpArr[colIndex + 5] != null ? tmpArr[colIndex + 5].toString() : null);
            dto.setTrang_thai_dat(tmpArr[colIndex + 6] != null ? tmpArr[colIndex + 6].toString() : null);
            dto.setSoLuongBTG_MuaTuMobifone(tmpArr[colIndex + 7] != null ? Double.valueOf(tmpArr[colIndex + 7].toString()) : null);
            dto.setDoanhThuMuaThe(tmpArr[colIndex + 8] != null ? Double.valueOf(tmpArr[colIndex + 8].toString()) : null);
            dto.setSoLuongVAS(tmpArr[colIndex + 9] != null ? Double.valueOf(tmpArr[colIndex + 9].toString()) : null);
            dto.setDoanhThu_VAS(tmpArr[colIndex + 10] != null ? Double.valueOf(tmpArr[colIndex + 10].toString()) : null);
            dto.setSoLuongTon_BanTrucTiepBTG(tmpArr[colIndex + 11] != null ? Double.valueOf(tmpArr[colIndex + 11].toString()) : null);
            dto.setSoLuongMoi_BanTrucTiepBTG(tmpArr[colIndex + 12] != null ? Double.valueOf(tmpArr[colIndex + 12].toString()) : null);
            dto.setCuocPSBoTon_BanTrucTiepBTG(tmpArr[colIndex + 13] != null ? Double.valueOf(tmpArr[colIndex + 13].toString()) : null);
            dto.setCuocPSBoMoi_BanTrucTiepBTG(tmpArr[colIndex + 14] != null ? Double.valueOf(tmpArr[colIndex + 14].toString()) : null);
            dto.setSoLuong_GioiThieuKH(tmpArr[colIndex + 15] != null ? Double.valueOf(tmpArr[colIndex + 15].toString()) : null);
            dto.setSoLuongPSCuoc_GioiThieuKH(tmpArr[colIndex + 16] != null ? Double.valueOf(tmpArr[colIndex + 16].toString()) : null);
            dto.setCuocPS_GioiThieuKH(tmpArr[colIndex + 17] != null ? Double.valueOf(tmpArr[colIndex + 17].toString()) : null);
            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return resultObject;
    }
}
