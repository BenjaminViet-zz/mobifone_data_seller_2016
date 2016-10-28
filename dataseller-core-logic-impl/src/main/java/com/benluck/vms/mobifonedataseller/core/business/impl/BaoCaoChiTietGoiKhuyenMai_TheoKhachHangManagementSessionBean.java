package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.core.business.BaoCaoChiTietGoiKhuyenMai_TheoKhachHangManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BranchDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.DistrictDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.RetailDealerDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015.BaoCaoChiTietGoiKhuyenMaiDTO;
import com.benluck.vms.mobifonedataseller.session.ThueBaoPhatTrienMoiLocalBean;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: HP
 * Date: 3/6/15
 * Time: 9:17 AM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "BaoCaoChiTietGoiKM_TheoKhachHangManagementSessionEJB")
public class BaoCaoChiTietGoiKhuyenMai_TheoKhachHangManagementSessionBean implements BaoCaoChiTietGoiKhuyenMai_TheoKhachHangManagementLocalBean {
    @EJB
    private ThueBaoPhatTrienMoiLocalBean thueBaoPhatTrienMoiLocalBean;

    public BaoCaoChiTietGoiKhuyenMai_TheoKhachHangManagementSessionBean() {
    }

    @Override
    public Object[] baoCaoChiTietGoiKhuyenMai_TheoKhachHang_TheoDiemBanHang(Map<String, Object> properties, String sortExpression, String sortDirection, int firstItem, int maxPageItems) {
        Object[] resultObject = this.thueBaoPhatTrienMoiLocalBean.baoCaoChiTietGoiKhuyenMai_TheoKhachHang_TheoDiemBanHang(properties, firstItem, maxPageItems, sortExpression, sortDirection);
        List<BaoCaoChiTietGoiKhuyenMaiDTO> dtoList = new ArrayList<BaoCaoChiTietGoiKhuyenMaiDTO>();
        for (Object object : (List)resultObject[1]) {
            Object[] tmpArr = (Object[])object;
            BaoCaoChiTietGoiKhuyenMaiDTO dto = new BaoCaoChiTietGoiKhuyenMaiDTO();

            if(tmpArr[0] != null){
                BranchDTO chiNhanh = new BranchDTO();
                chiNhanh.setBranch_name(tmpArr[0] != null ? tmpArr[0].toString() : null);
                dto.setBranch(chiNhanh);
            }
            if(tmpArr[1] != null){
                DistrictDTO quanHuyen = new DistrictDTO();
                quanHuyen.setDistrict_name(tmpArr[1] != null ? tmpArr[1].toString() : null);
                dto.setDistrict(quanHuyen);
            }
            if(tmpArr[2] != null && tmpArr [3] != null){
                RetailDealerDTO diemBanHang = new RetailDealerDTO();
                diemBanHang.setDealer_code(tmpArr[2] != null ? tmpArr[2].toString() : null);
                diemBanHang.setDealer_name(tmpArr[3] != null ? tmpArr[3].toString() : null);
                dto.setRetailDealer(diemBanHang);
            }
            dto.setSoThueBao(tmpArr[4] != null ? tmpArr[4].toString() : null);
            dto.setLoaiThueBao(tmpArr[5] != null ? tmpArr[5].toString() : null);
            dto.setSoLuongGoiT50_s(tmpArr[6] != null ? Integer.valueOf(tmpArr[6].toString()) : null);
            dto.setSoLuongGoi3T50_s(tmpArr[7] != null ? Integer.valueOf(tmpArr[7].toString()) : null);
            dto.setSoLuongGoiT100_s(tmpArr[8] != null ? Integer.valueOf(tmpArr[8].toString()) : null);
            dto.setSoLuongGoi3T100_s(tmpArr[9] != null ? Integer.valueOf(tmpArr[9].toString()) : null);
            dto.setSoLuongGoiT50_CT(tmpArr[10] != null ? Integer.valueOf(tmpArr[10].toString()) : null);
            dto.setSoLuongGoi3T50_CT(tmpArr[11] != null ? Integer.valueOf(tmpArr[11].toString()) : null);
            dto.setSoLuongGoiT100_CT(tmpArr[12] != null ? Integer.valueOf(tmpArr[12].toString()) : null);
            dto.setSoLuongGoi3T100_CT(tmpArr[13] != null ? Integer.valueOf(tmpArr[13].toString()) : null);
            dto.setSoLuongGoiT50_KCT(tmpArr[14] != null ? Integer.valueOf(tmpArr[14].toString()) : null);
            dto.setSoLuongGoi3T50_KCT(tmpArr[15] != null ? Integer.valueOf(tmpArr[15].toString()) : null);
            dto.setSoLuongGoiT100_KCT(tmpArr[16] != null ? Integer.valueOf(tmpArr[16].toString()) : null);
            dto.setSoLuongGoi3T100_KCT(tmpArr[17] != null ? Integer.valueOf(tmpArr[17].toString()) : null);

            dto.setViTriDangKy(tmpArr[18] != null ? tmpArr[18].toString() : null);

            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return resultObject;
    }

    @Override
    public Object[] baoCaoChiTietGoiKhuyenMai_TheoKhachHang_TheoQuanHuyen(Map<String, Object> properties, String sortExpression, String sortDirection, int firstItem, int maxPageItems) {
        Object[] resultObject = this.thueBaoPhatTrienMoiLocalBean.baoCaoChiTietGoiKhuyenMai_TheoKhachHang_TheoQuanHuyen(properties, firstItem, maxPageItems, sortExpression, sortDirection);
        List<BaoCaoChiTietGoiKhuyenMaiDTO> dtoList = new ArrayList<BaoCaoChiTietGoiKhuyenMaiDTO>();
        for (Object object : (List)resultObject[1]) {
            Object[] tmpArr = (Object[])object;
            BaoCaoChiTietGoiKhuyenMaiDTO dto = new BaoCaoChiTietGoiKhuyenMaiDTO();

            if(tmpArr[0] != null){
                BranchDTO chiNhanh = new BranchDTO();
                chiNhanh.setBranch_name(tmpArr[0] != null ? tmpArr[0].toString() : null);
                dto.setBranch(chiNhanh);
            }
            if(tmpArr[1] != null){
                DistrictDTO quanHuyen = new DistrictDTO();
                quanHuyen.setDistrict_name(tmpArr[1] != null ? tmpArr[1].toString() : null);
                dto.setDistrict(quanHuyen);
            }
            dto.setSoLuongGoiT50_s(tmpArr[2] != null ? Integer.valueOf(tmpArr[2].toString()) : null);
            dto.setSoLuongGoi3T50_s(tmpArr[3] != null ? Integer.valueOf(tmpArr[3].toString()) : null);
            dto.setSoLuongGoiT100_s(tmpArr[4] != null ? Integer.valueOf(tmpArr[4].toString()) : null);
            dto.setSoLuongGoi3T100_s(tmpArr[5] != null ? Integer.valueOf(tmpArr[5].toString()) : null);
            dto.setSoLuongGoiT50_CT(tmpArr[6] != null ? Integer.valueOf(tmpArr[6].toString()) : null);
            dto.setSoLuongGoi3T50_CT(tmpArr[7] != null ? Integer.valueOf(tmpArr[7].toString()) : null);
            dto.setSoLuongGoiT100_CT(tmpArr[8] != null ? Integer.valueOf(tmpArr[8].toString()) : null);
            dto.setSoLuongGoi3T100_CT(tmpArr[9] != null ? Integer.valueOf(tmpArr[9].toString()) : null);
            dto.setSoLuongGoiT50_KCT(tmpArr[10] != null ? Integer.valueOf(tmpArr[10].toString()) : null);
            dto.setSoLuongGoi3T50_KCT(tmpArr[11] != null ? Integer.valueOf(tmpArr[11].toString()) : null);
            dto.setSoLuongGoiT100_KCT(tmpArr[12] != null ? Integer.valueOf(tmpArr[12].toString()) : null);
            dto.setSoLuongGoi3T100_KCT(tmpArr[13] != null ? Integer.valueOf(tmpArr[13].toString()) : null);

            dto.setViTriDangKy(tmpArr[14] != null ? tmpArr[14].toString() : null);

            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return resultObject;
    }

    @Override
    public Object[] baoCaoChiTietGoiKhuyenMai_TheoKhachHang_TheoChiNhanh(Map<String, Object> properties, String sortExpression, String sortDirection, int firstItem, int maxPageItems) {
        Object[] resultObject = this.thueBaoPhatTrienMoiLocalBean.baoCaoChiTietGoiKhuyenMai_TheoKhachHang_TheoChiNhanh(properties, firstItem, maxPageItems, sortExpression, sortDirection);
        List<BaoCaoChiTietGoiKhuyenMaiDTO> dtoList = new ArrayList<BaoCaoChiTietGoiKhuyenMaiDTO>();
        for (Object object : (List)resultObject[1]) {
            Object[] tmpArr = (Object[])object;
            BaoCaoChiTietGoiKhuyenMaiDTO dto = new BaoCaoChiTietGoiKhuyenMaiDTO();

            if(tmpArr[0] != null){
                BranchDTO chiNhanh = new BranchDTO();
                chiNhanh.setBranch_name(tmpArr[0] != null ? tmpArr[0].toString() : null);
                dto.setBranch(chiNhanh);
            }
            dto.setSoLuongGoiT50_s(tmpArr[1] != null ? Integer.valueOf(tmpArr[1].toString()) : null);
            dto.setSoLuongGoi3T50_s(tmpArr[2] != null ? Integer.valueOf(tmpArr[2].toString()) : null);
            dto.setSoLuongGoiT100_s(tmpArr[3] != null ? Integer.valueOf(tmpArr[3].toString()) : null);
            dto.setSoLuongGoi3T100_s(tmpArr[4] != null ? Integer.valueOf(tmpArr[4].toString()) : null);
            dto.setSoLuongGoiT50_CT(tmpArr[5] != null ? Integer.valueOf(tmpArr[5].toString()) : null);
            dto.setSoLuongGoi3T50_CT(tmpArr[6] != null ? Integer.valueOf(tmpArr[6].toString()) : null);
            dto.setSoLuongGoiT100_CT(tmpArr[7] != null ? Integer.valueOf(tmpArr[7].toString()) : null);
            dto.setSoLuongGoi3T100_CT(tmpArr[8] != null ? Integer.valueOf(tmpArr[8].toString()) : null);
            dto.setSoLuongGoiT50_KCT(tmpArr[9] != null ? Integer.valueOf(tmpArr[9].toString()) : null);
            dto.setSoLuongGoi3T50_KCT(tmpArr[10] != null ? Integer.valueOf(tmpArr[10].toString()) : null);
            dto.setSoLuongGoiT100_KCT(tmpArr[11] != null ? Integer.valueOf(tmpArr[11].toString()) : null);
            dto.setSoLuongGoi3T100_KCT(tmpArr[12] != null ? Integer.valueOf(tmpArr[12].toString()) : null);

            dto.setViTriDangKy(tmpArr[13] != null ? tmpArr[13].toString() : null);

            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return resultObject;
    }
}
