package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.core.business.BaoCaoChiTietGoiKhuyenMai_TheoMaGoiTenGoiManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BranchDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.DistrictDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.RetailDealerDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015.BaoCaoChiTietGoiKhuyenMaiDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015.PromPackageDTO;
import com.benluck.vms.mobifonedataseller.session.ThueBaoPhatTrienMoiLocalBean;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: HP
 * Date: 3/6/15
 * Time: 5:49 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "BaoCaoChiTietGoiKM_theoMaGoiTenGoiManagementSessionEJB")
public class BaoCaoChiTietGoiKhuyenMai_TheoMaGoiTenGoiManagementSessionBean implements BaoCaoChiTietGoiKhuyenMai_TheoMaGoiTenGoiManagementLocalBean {
    @EJB
    private ThueBaoPhatTrienMoiLocalBean thueBaoPhatTrienMoiLocalBean;
    public BaoCaoChiTietGoiKhuyenMai_TheoMaGoiTenGoiManagementSessionBean() {
    }

    @Override
    public Object[] baoCaoChiTietGoiKhuyenMai_TheoMaGoi_TenGoi_TheoDiemBanHang(Map<String, Object> properties, String sortExpression, String sortDirection, int firstItem, int maxPageItems) {
        Object[] resultObject = this.thueBaoPhatTrienMoiLocalBean.baoCaoChiTietGoiKhuyenMai_TheoMaGoi_TenGoi_TheoDiemBanHang(properties, firstItem, maxPageItems, sortExpression, sortDirection);
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
            dto.setThoiGianDK(tmpArr[4] != null ? Timestamp.valueOf(tmpArr[4].toString()) : null);
            dto.setSoThueBao(tmpArr[5] != null ? tmpArr[5].toString() : null);
            dto.setSoEZ(tmpArr[6] != null ? tmpArr[6].toString() : null);
            dto.setLoaiThueBao(tmpArr[7] != null ? tmpArr[7].toString() : null);
            if(tmpArr[8] != null){
                PromPackageDTO goiCuoc = new PromPackageDTO();
                goiCuoc.setPackage_Name(tmpArr[8] != null ? tmpArr[8].toString() : null);
                dto.setPromPackage(goiCuoc);
            }
            dto.setTinhTrang(tmpArr[9] != null ? tmpArr[9].toString() : null);
            dto.setTransError(tmpArr[10] != null ? tmpArr[10].toString() : null);
            dto.setPromConditionStatus(tmpArr[11] != null ? tmpArr[11].toString() : null);
            dto.setPromConditionError(tmpArr[12] != null ? tmpArr[12].toString() : null);
            dto.setSerial(tmpArr[13] != null ? tmpArr[13].toString() : null);

            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return resultObject;
    }

    @Override
    public Object[] baoCaoChiTietGoiKhuyenMai_TheoMaGoi_TenGoi_TheoQuanHuyen(Map<String, Object> properties, String sortExpression, String sortDirection, int firstItem, int maxPageItems) {
        Object[] resultObject = this.thueBaoPhatTrienMoiLocalBean.baoCaoChiTietGoiKhuyenMai_TheoMaGoi_TenGoi_TheoQuanHuyen(properties, firstItem, maxPageItems, sortExpression, sortDirection);
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
            dto.setThoiGianDK(tmpArr[2] != null ? Timestamp.valueOf(tmpArr[2].toString()) : null);
            if(tmpArr[3] != null){
                PromPackageDTO goiCuoc = new PromPackageDTO();
                goiCuoc.setPackage_Name(tmpArr[3] != null ? tmpArr[3].toString() : null);
                dto.setPromPackage(goiCuoc);
            }
            dto.setTinhTrang(tmpArr[4] != null ? tmpArr[4].toString() : null);
            dto.setTransError(tmpArr[5] != null ? tmpArr[5].toString() : null);
            dto.setPromConditionStatus(tmpArr[6] != null ? tmpArr[6].toString() : null);
            dto.setPromConditionError(tmpArr[7] != null ? tmpArr[7].toString() : null);

            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return resultObject;
    }

    @Override
    public Object[] baoCaoChiTietGoiKhuyenMai_TheoMaGoi_TenGoi_TheoChiNhanh(Map<String, Object> properties, String sortExpression, String sortDirection, int firstItem, int maxPageItems) {
        Object[] resultObject = this.thueBaoPhatTrienMoiLocalBean.baoCaoChiTietGoiKhuyenMai_TheoMaGoi_TenGoi_TheoChiNhanh(properties, firstItem, maxPageItems, sortExpression, sortDirection);
        List<BaoCaoChiTietGoiKhuyenMaiDTO> dtoList = new ArrayList<BaoCaoChiTietGoiKhuyenMaiDTO>();
        for (Object object : (List)resultObject[1]) {
            Object[] tmpArr = (Object[])object;
            BaoCaoChiTietGoiKhuyenMaiDTO dto = new BaoCaoChiTietGoiKhuyenMaiDTO();

            if(tmpArr[0] != null){
                BranchDTO chiNhanh = new BranchDTO();
                chiNhanh.setBranch_name(tmpArr[0] != null ? tmpArr[0].toString() : null);
                dto.setBranch(chiNhanh);
            }
            dto.setThoiGianDK(tmpArr[1] != null ? Timestamp.valueOf(tmpArr[1].toString()) : null);
            if(tmpArr[2] != null){
                PromPackageDTO goiCuoc = new PromPackageDTO();
                goiCuoc.setPackage_Name(tmpArr[2] != null ? tmpArr[2].toString() : null);
                dto.setPromPackage(goiCuoc);
            }
            dto.setTinhTrang(tmpArr[3] != null ? tmpArr[3].toString() : null);
            dto.setTransError(tmpArr[4] != null ? tmpArr[4].toString() : null);
            dto.setPromConditionStatus(tmpArr[5] != null ? tmpArr[5].toString() : null);
            dto.setPromConditionError(tmpArr[6] != null ? tmpArr[6].toString() : null);

            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return resultObject;
    }
}
