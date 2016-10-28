package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.core.business.BaoCaoTinNhan_kpp_theoQuanHuyenManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BaoCaoTinNhan_KPP_TheoQuanHuyenDTO;
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
 * Time: 10:48 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "BaoCaoTinNhan_kpp_theoQuanHuyenManagementSessionEJB")
public class BaoCaoTinNhan_kpp_theoQuanHuyenManagementSessionBean implements BaoCaoTinNhan_kpp_theoQuanHuyenManagementLocalBean{
    @EJB
    CTTichDiemLocalBean ctTichDiemLocalBean;

    public BaoCaoTinNhan_kpp_theoQuanHuyenManagementSessionBean() {
    }

    @Override
    public Object[] search(Map<String, Object> properties, Integer firstItem, Integer reportMaxPageItems, String sortExpression, String sortDirection) {
        Object[] resultObject = this.ctTichDiemLocalBean.baoCaoTinNhan_kpp_theoQuanHuyen(properties, firstItem, reportMaxPageItems, sortExpression, sortDirection);
        List<BaoCaoTinNhan_KPP_TheoQuanHuyenDTO> dtoList = new ArrayList<BaoCaoTinNhan_KPP_TheoQuanHuyenDTO>();
        for(Object object : (List)resultObject[1]){
            Object[] tmpArr = (Object[])object;
            BaoCaoTinNhan_KPP_TheoQuanHuyenDTO dto = new BaoCaoTinNhan_KPP_TheoQuanHuyenDTO();
            dto.setBranch_Name(tmpArr[0] != null ? tmpArr[0].toString() : null);
            dto.setDistrict_Name(tmpArr[1] != null ? tmpArr[1].toString() : null);
            dto.setItem_Name(tmpArr[2] != null ? tmpArr[2].toString() : null);
            dto.setSoLuongThanhCong(tmpArr[3] != null ? Double.valueOf(tmpArr[3].toString()) : null);
            dto.setSoEZChuaDangKy(tmpArr[4] != null ? Double.valueOf(tmpArr[4].toString()) : null);
            dto.setSoThueBaoKhongThuocDoiTuongThamGia(tmpArr[5] != null ? Double.valueOf(tmpArr[5].toString()) : null);
            dto.setSoThueBaoDaDuocDangKy(tmpArr[6] != null ? Double.valueOf(tmpArr[6].toString()) : null);
            dto.setSoThueBaoDangChoXacNhan(tmpArr[7] != null ? Double.valueOf(tmpArr[7].toString()) : null);
            dto.setSoCuPhapKhongDung(tmpArr[8] != null ? Double.valueOf(tmpArr[8].toString()) : null);
            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return resultObject;
    }
}
