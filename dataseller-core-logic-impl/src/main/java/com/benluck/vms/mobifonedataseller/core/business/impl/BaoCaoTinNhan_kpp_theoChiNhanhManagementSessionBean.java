package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.core.business.BaoCaoTinNhan_kpp_theoChiNhanhManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BaoCaoTinNhan_KPP_TheoChiNhanhDTO;
import com.benluck.vms.mobifonedataseller.session.CTTichDiemLocalBean;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 11/18/14
 * Time: 10:50 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "BaoCaoTinNhan_kpp_theoChiNhanhManagementSessionEJB")
public class BaoCaoTinNhan_kpp_theoChiNhanhManagementSessionBean implements BaoCaoTinNhan_kpp_theoChiNhanhManagementLocalBean{
    @EJB
    private CTTichDiemLocalBean ctTichDiemLocalBean;
    public BaoCaoTinNhan_kpp_theoChiNhanhManagementSessionBean() {
    }

    @Override
    public Object[] search(Map<String, Object> properties, Integer firstItem, Integer reportMaxPageItems, String sortExpression, String sortDirection) {
        Object[] resultObject = this.ctTichDiemLocalBean.baoCaoTinNhan_kpp_theoChiNhanh(properties, firstItem, reportMaxPageItems, sortExpression, sortDirection);
        List<BaoCaoTinNhan_KPP_TheoChiNhanhDTO> dtoList = new ArrayList<BaoCaoTinNhan_KPP_TheoChiNhanhDTO>();
        for(Object object : (List)resultObject[1]){
            Object[] tmpArr = (Object[])object;
            BaoCaoTinNhan_KPP_TheoChiNhanhDTO dto = new BaoCaoTinNhan_KPP_TheoChiNhanhDTO();
            dto.setBranch_Name(tmpArr[0] != null ? tmpArr[0].toString() : null);
            dto.setItem_Name(tmpArr[1] != null ? tmpArr[1].toString() : null);
            dto.setSoLuongThanhCong(tmpArr[2] != null ? Double.valueOf(tmpArr[2].toString()) : null);
            dto.setSoEZChuaDangKy(tmpArr[3] != null ? Double.valueOf(tmpArr[3].toString()) : null);
            dto.setSoThueBaoKhongThuocDoiTuongThamGia(tmpArr[4] != null ? Double.valueOf(tmpArr[4].toString()) : null);
            dto.setSoThueBaoDaDuocDangKy(tmpArr[5] != null ? Double.valueOf(tmpArr[5].toString()) : null);
            dto.setSoThueBaoDangChoXacNhan(tmpArr[6] != null ? Double.valueOf(tmpArr[6].toString()) : null);
            dto.setSoCuPhapKhongDung(tmpArr[7] != null ? Double.valueOf(tmpArr[7].toString()) : null);
            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return resultObject;
    }
}
