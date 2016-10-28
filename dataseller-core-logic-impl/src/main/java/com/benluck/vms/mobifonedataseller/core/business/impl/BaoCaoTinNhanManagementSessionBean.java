package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.core.business.BaoCaoTinNhanManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BranchDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.DistrictDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.RetailDealerDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015.BaoCaoTinNhanDTO;
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
 * Time: 11:54 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "BaoCaoTinNhanManagementSessionEJB")
public class BaoCaoTinNhanManagementSessionBean implements BaoCaoTinNhanManagementLocalBean {
    @EJB
    private ThueBaoPhatTrienMoiLocalBean thueBaoPhatTrienMoiLocalBean;
    public BaoCaoTinNhanManagementSessionBean() {
    }

    @Override
    public Object[] baoCaoTinNhan_TheoDiemBanHang(Map<String, Object> properties, String sortExpression, String sortDirection, int firstItem, int maxPageItems) {
        Object[] resultObject = this.thueBaoPhatTrienMoiLocalBean.baoCaoTinNhan_TheoDiemBanHang(properties, firstItem, maxPageItems, sortExpression, sortDirection);
        List<BaoCaoTinNhanDTO> dtoList = new ArrayList<BaoCaoTinNhanDTO>();
        for (Object object : (List)resultObject[1]) {
            Object[] tmpArr = (Object[])object;
            BaoCaoTinNhanDTO dto = new BaoCaoTinNhanDTO();

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
            dto.setSoLuongThanhCong(tmpArr[5] != null ? Integer.valueOf(tmpArr[5].toString()) : null);
            dto.setSoLuongDuocChiTra(tmpArr[6] != null ? Integer.valueOf(tmpArr[6].toString()) : null);
            dto.setSoLuongKhongThanhCong(tmpArr[7] != null ? Integer.valueOf(tmpArr[7].toString()) : null);

            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return resultObject;
    }

    @Override
    public Object[] baoCaoTinNhan_TheoQuanHuyen(Map<String, Object> properties, String sortExpression, String sortDirection, int firstItem, int maxPageItems) {
        Object[] resultObject = this.thueBaoPhatTrienMoiLocalBean.baoCaoTinNhan_TheoQuanHuyen(properties, firstItem, maxPageItems, sortExpression, sortDirection);
        List<BaoCaoTinNhanDTO> dtoList = new ArrayList<BaoCaoTinNhanDTO>();
        for (Object object : (List)resultObject[1]) {
            Object[] tmpArr = (Object[])object;
            BaoCaoTinNhanDTO dto = new BaoCaoTinNhanDTO();

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
            dto.setSoLuongThanhCong(tmpArr[2] != null ? Integer.valueOf(tmpArr[2].toString()) : null);
            dto.setSoLuongDuocChiTra(tmpArr[3] != null ? Integer.valueOf(tmpArr[3].toString()) : null);
            dto.setSoLuongKhongThanhCong(tmpArr[4] != null ? Integer.valueOf(tmpArr[4].toString()) : null);

            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return resultObject;
    }

    @Override
    public Object[] baoCaoTinNhan_TheoChiNhanh(Map<String, Object> properties, String sortExpression, String sortDirection, int firstItem, int maxPageItems) {
        Object[] resultObject = this.thueBaoPhatTrienMoiLocalBean.baoCaoTinNhan_TheoChiNhanh(properties, firstItem, maxPageItems, sortExpression, sortDirection);
        List<BaoCaoTinNhanDTO> dtoList = new ArrayList<BaoCaoTinNhanDTO>();
        for (Object object : (List)resultObject[1]) {
            Object[] tmpArr = (Object[])object;
            BaoCaoTinNhanDTO dto = new BaoCaoTinNhanDTO();

            if(tmpArr[0] != null){
                BranchDTO chiNhanh = new BranchDTO();
                chiNhanh.setBranch_name(tmpArr[0] != null ? tmpArr[0].toString() : null);
                dto.setBranch(chiNhanh);
            }
            dto.setSoLuongThanhCong(tmpArr[1] != null ? Integer.valueOf(tmpArr[1].toString()) : null);
            dto.setSoLuongDuocChiTra(tmpArr[2] != null ? Integer.valueOf(tmpArr[2].toString()) : null);
            dto.setSoLuongKhongThanhCong(tmpArr[3] != null ? Integer.valueOf(tmpArr[3].toString()) : null);

            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return resultObject;
    }
}
