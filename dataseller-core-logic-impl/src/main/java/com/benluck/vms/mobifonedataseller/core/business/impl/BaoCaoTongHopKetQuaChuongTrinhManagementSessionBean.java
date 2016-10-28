package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.core.business.BaoCaoTongHopKetQuaChuongTrinhManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BranchDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.DistrictDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.RetailDealerDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015.BaoCaoTongHopKetQuaChuongTrinhDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015.PromPackageDTO;
import com.benluck.vms.mobifonedataseller.session.ThueBaoPhatTrienMoiLocalBean;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: HP
 * Date: 3/5/15
 * Time: 4:17 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "BaoCaoTongHopKQCTManagementSessionEJB")
public class BaoCaoTongHopKetQuaChuongTrinhManagementSessionBean implements BaoCaoTongHopKetQuaChuongTrinhManagementLocalBean {

    @EJB
    private ThueBaoPhatTrienMoiLocalBean thueBaoPhatTrienMoiLocalBean;

    public BaoCaoTongHopKetQuaChuongTrinhManagementSessionBean() {
    }

    @Override
    public Object[] baoCaoTongHopKetQuaChuongTrinh_TheoDiemBanHang(Map<String, Object> properties, String sortExpression, String sortDirection, int firstItem, int maxPageItems) {
        Object[] resultObject = this.thueBaoPhatTrienMoiLocalBean.baoCaoTongHopKetQuaChuongTrinh_TheoDiemBanHang(properties, firstItem, maxPageItems, sortExpression, sortDirection);
        List<BaoCaoTongHopKetQuaChuongTrinhDTO> dtoList = new ArrayList<BaoCaoTongHopKetQuaChuongTrinhDTO>();
        for (Object object : (List)resultObject[1]) {
            Object[] tmpArr = (Object[])object;
            BaoCaoTongHopKetQuaChuongTrinhDTO dto = new BaoCaoTongHopKetQuaChuongTrinhDTO();

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
            dto.setSoEZ(tmpArr[4] != null ? tmpArr[4].toString() : null);
            dto.setSoLuongBTGMoi(tmpArr[5] != null ? Integer.valueOf(tmpArr[5].toString()) : null);
            dto.setSoLuongBTGTon(tmpArr[6] != null ? Integer.valueOf(tmpArr[6].toString()) : null);
            dto.setSoLuongGoiT50_s(tmpArr[7] != null ? Integer.valueOf(tmpArr[7].toString()) : null);
            dto.setSoLuongGoi3T50_s(tmpArr[8] != null ? Integer.valueOf(tmpArr[8].toString()) : null);
            dto.setSoLuongGoiT100_s(tmpArr[9] != null ? Integer.valueOf(tmpArr[9].toString()) : null);
            dto.setSoLuongGoi3T100_s(tmpArr[10] != null ? Integer.valueOf(tmpArr[10].toString()) : null);
            dto.setSoLuongGoiT50_CT(tmpArr[11] != null ? Integer.valueOf(tmpArr[11].toString()) : null);
            dto.setSoLuongGoi3T50_CT(tmpArr[12] != null ? Integer.valueOf(tmpArr[12].toString()) : null);
            dto.setSoLuongGoiT100_CT(tmpArr[13] != null ? Integer.valueOf(tmpArr[13].toString()) : null);
            dto.setSoLuongGoi3T100_CT(tmpArr[14] != null ? Integer.valueOf(tmpArr[14].toString()) : null);
            dto.setDoanhThuBTGMoi(tmpArr[18] != null ? Double.valueOf(tmpArr[18].toString()) : null);
            dto.setDoanhThuBTGTon(tmpArr[19] != null ? Double.valueOf(tmpArr[19].toString()) : null);
            if (tmpArr[20] != null){
                PromPackageDTO promPackageDTO = new PromPackageDTO();
                promPackageDTO.setPackage_Name(tmpArr[20] != null ? tmpArr[20].toString() : null);
                dto.setPromPackage(promPackageDTO);
            }
            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return resultObject;
    }

    @Override
    public Object[] baoCaoTongHopKetQuaChuongTrinh_TheoQuanHuyen(Map<String, Object> properties, String sortExpression, String sortDirection, int firstItem, int maxPageItems) {
        Object[] resultObject = this.thueBaoPhatTrienMoiLocalBean.baoCaoTongHopKetQuaChuongTrinh_TheoQuanHuyen(properties, firstItem, maxPageItems, sortExpression, sortDirection);
        List<BaoCaoTongHopKetQuaChuongTrinhDTO> dtoList = new ArrayList<BaoCaoTongHopKetQuaChuongTrinhDTO>();
        for (Object object : (List)resultObject[1]) {
            Object[] tmpArr = (Object[])object;
            BaoCaoTongHopKetQuaChuongTrinhDTO dto = new BaoCaoTongHopKetQuaChuongTrinhDTO();

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
            dto.setSoLuongBTGMoi(tmpArr[2] != null ? Integer.valueOf(tmpArr[2].toString()) : null);
            dto.setSoLuongBTGTon(tmpArr[3] != null ? Integer.valueOf(tmpArr[3].toString()) : null);
            dto.setSoLuongGoiT50_s(tmpArr[4] != null ? Integer.valueOf(tmpArr[4].toString()) : null);
            dto.setSoLuongGoi3T50_s(tmpArr[5] != null ? Integer.valueOf(tmpArr[5].toString()) : null);
            dto.setSoLuongGoiT100_s(tmpArr[6] != null ? Integer.valueOf(tmpArr[6].toString()) : null);
            dto.setSoLuongGoi3T100_s(tmpArr[7] != null ? Integer.valueOf(tmpArr[7].toString()) : null);
            dto.setSoLuongGoiT50_CT(tmpArr[8] != null ? Integer.valueOf(tmpArr[8].toString()) : null);
            dto.setSoLuongGoi3T50_CT(tmpArr[9] != null ? Integer.valueOf(tmpArr[9].toString()) : null);
            dto.setSoLuongGoiT100_CT(tmpArr[10] != null ? Integer.valueOf(tmpArr[10].toString()) : null);
            dto.setSoLuongGoi3T100_CT(tmpArr[11] != null ? Integer.valueOf(tmpArr[11].toString()) : null);
            dto.setSLDBHThamGia(tmpArr[12] != null ? Integer.valueOf(tmpArr[12].toString()) : null);
            dto.setDoanhThuBTGMoi(tmpArr[15] != null ? Double.valueOf(tmpArr[15].toString()) : null);
            dto.setDoanhThuBTGTon(tmpArr[16] != null ? Double.valueOf(tmpArr[16].toString()) : null);
            if (tmpArr[17] != null){
                PromPackageDTO promPackageDTO = new PromPackageDTO();
                promPackageDTO.setPackage_Name(tmpArr[17] != null ? tmpArr[17].toString() : null);
                dto.setPromPackage(promPackageDTO);
            }
            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return resultObject;
    }

    @Override
    public Object[] baoCaoTongHopKetQuaChuongTrinh_TheoChiNhanh(Map<String, Object> properties, String sortExpression, String sortDirection, int firstItem, int maxPageItems) {
        Object[] resultObject = this.thueBaoPhatTrienMoiLocalBean.baoCaoTongHopKetQuaChuongTrinh_TheoChiNhanh(properties, firstItem, maxPageItems, sortExpression, sortDirection);
        List<BaoCaoTongHopKetQuaChuongTrinhDTO> dtoList = new ArrayList<BaoCaoTongHopKetQuaChuongTrinhDTO>();
        for (Object object : (List)resultObject[1]) {
            Object[] tmpArr = (Object[])object;
            BaoCaoTongHopKetQuaChuongTrinhDTO dto = new BaoCaoTongHopKetQuaChuongTrinhDTO();

            if(tmpArr[0] != null){
                BranchDTO chiNhanh = new BranchDTO();
                chiNhanh.setBranch_name(tmpArr[0] != null ? tmpArr[0].toString() : null);
                dto.setBranch(chiNhanh);
            }
            dto.setSoLuongBTGMoi(tmpArr[1] != null ? Integer.valueOf(tmpArr[1].toString()) : null);
            dto.setSoLuongBTGTon(tmpArr[2] != null ? Integer.valueOf(tmpArr[2].toString()) : null);
            dto.setSoLuongGoiT50_s(tmpArr[3] != null ? Integer.valueOf(tmpArr[3].toString()) : null);
            dto.setSoLuongGoi3T50_s(tmpArr[4] != null ? Integer.valueOf(tmpArr[4].toString()) : null);
            dto.setSoLuongGoiT100_s(tmpArr[5] != null ? Integer.valueOf(tmpArr[5].toString()) : null);
            dto.setSoLuongGoi3T100_s(tmpArr[6] != null ? Integer.valueOf(tmpArr[6].toString()) : null);
            dto.setSoLuongGoiT50_CT(tmpArr[7] != null ? Integer.valueOf(tmpArr[7].toString()) : null);
            dto.setSoLuongGoi3T50_CT(tmpArr[8] != null ? Integer.valueOf(tmpArr[8].toString()) : null);
            dto.setSoLuongGoiT100_CT(tmpArr[9] != null ? Integer.valueOf(tmpArr[9].toString()) : null);
            dto.setSoLuongGoi3T100_CT(tmpArr[10] != null ? Integer.valueOf(tmpArr[10].toString()) : null);
            dto.setSLDBHThamGia(tmpArr[11] != null ? Integer.valueOf(tmpArr[11].toString()) : null);
            dto.setDoanhThuBTGMoi(tmpArr[13] != null ? Double.valueOf(tmpArr[13].toString()) : null);
            dto.setDoanhThuBTGTon(tmpArr[14] != null ? Double.valueOf(tmpArr[14].toString()) : null);
            if (tmpArr[15] != null){
                PromPackageDTO promPackageDTO = new PromPackageDTO();
                promPackageDTO.setPackage_Name(tmpArr[15] != null ? tmpArr[15].toString() : null);
                dto.setPromPackage(promPackageDTO);
            }

            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return resultObject;
    }
}
