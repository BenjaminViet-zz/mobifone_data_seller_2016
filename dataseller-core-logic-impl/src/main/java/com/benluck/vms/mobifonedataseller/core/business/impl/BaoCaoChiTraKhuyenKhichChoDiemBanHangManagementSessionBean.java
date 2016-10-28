package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.core.business.BaoCaoChiTraKhuyenKhichChoDiemBanHangManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BranchDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.DistrictDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.RetailDealerDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015.BaoCaoChiTraKhuyenKhichChoDBHDTO;
import com.benluck.vms.mobifonedataseller.session.ThueBaoPhatTrienMoiLocalBean;

import javax.ejb.EJB;
import javax.ejb.ObjectNotFoundException;
import javax.ejb.Stateless;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 3/5/15
 * Time: 11:39 PM
 * To change this template use File | Settings | File Templates.
 */

@Stateless(name = "BaoCaoChiTraKhuyenKhichChoDBHManagementSessionEJB")
public class BaoCaoChiTraKhuyenKhichChoDiemBanHangManagementSessionBean implements BaoCaoChiTraKhuyenKhichChoDiemBanHangManagementLocalBean {

    @EJB
    private ThueBaoPhatTrienMoiLocalBean thueBaoPhatTrienMoiLocalBean;

    @Override
    public Object[] baoCaoChiTraKhuyenKhichChoDiemBanHang_TheoDiemBanHang(Map<String, Object> properties, String sortExpression, String sortDirection, int firstItem, int maxPageItems) throws ObjectNotFoundException {
         Object[] resultObject = this.thueBaoPhatTrienMoiLocalBean.baoCaoChiTraKhuyenKhichChoDiemBanHang_TheoDiemBanHang(properties, firstItem, maxPageItems, sortExpression, sortDirection);
        List<BaoCaoChiTraKhuyenKhichChoDBHDTO> dtoList = new ArrayList<BaoCaoChiTraKhuyenKhichChoDBHDTO>();
        for (Object object : (List)resultObject[1]) {
            Object[] tmpArr = (Object[])object;
            BaoCaoChiTraKhuyenKhichChoDBHDTO dto = new BaoCaoChiTraKhuyenKhichChoDBHDTO();

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
            if(tmpArr[2] != null && tmpArr[8] != null){
                RetailDealerDTO diemBanHang = new RetailDealerDTO();
                diemBanHang.setDealer_code(tmpArr[2] != null ? tmpArr[2].toString() : null);
                diemBanHang.setDealer_name(tmpArr[8] != null ? tmpArr[8].toString() : null);

                dto.setRetailDealer(diemBanHang);
            }
            if (tmpArr[3] != null){
                dto.setSoEz(tmpArr[3] != null ? tmpArr[3].toString() : null);
            }
            if(tmpArr[4] != null){
                dto.setSoTienDaChi(tmpArr[4] != null ? Double.valueOf(tmpArr[4].toString()) : null);
            }
            if(tmpArr[9] != null){
                dto.setTax_Code(tmpArr[9] != null ? tmpArr[9].toString() : null);
            }
            if(tmpArr[10] != null){
                dto.setAddress(tmpArr[10] != null ? tmpArr[10].toString() : null);
            }
            if(tmpArr[11] != null){
                dto.setContact_name(tmpArr[11] != null ? tmpArr[11].toString() : null);
            }

            if (properties.get("loaiBaoCao") != null && properties.get("loaiBaoCao").toString() == "0"){
                if(tmpArr[12] != null){
                    dto.setThoiGianPhaiChi(tmpArr[12] != null ? Timestamp.valueOf(tmpArr[12].toString()) : null);
                }
                if(tmpArr[13] != null){
                    dto.setThoiGianThucChi(tmpArr[13] != null ? Timestamp.valueOf(tmpArr[13].toString()):null);
                }
                if (tmpArr[14] != null){
                    dto.setSumDate(tmpArr[14] != null ? Timestamp.valueOf(tmpArr[14].toString()) : null);
                }
                if (tmpArr[15] != null){
                    dto.setSerial(tmpArr[15] != null ? tmpArr[15].toString():null);
                }
                if(tmpArr[16] != null){
                    dto.setSoTienKhuyenKhich(tmpArr[16] != null ? Double.valueOf(tmpArr[16].toString()):null);
                }
            }

            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return resultObject;

    }

    @Override
    public Object[] baoCaoChiTraKhuyenKhichChoDiemBanHang_TheoQuanHuyen(Map<String, Object> properties, String sortExpression, String sortDirection, int firstItem, int maxPageItems) throws ObjectNotFoundException {
        Object[] resultObject = this.thueBaoPhatTrienMoiLocalBean.baoCaoChiTraKhuyenKhichChoDiemBanHang_TheoQuanHuyen(properties, firstItem, maxPageItems, sortExpression, sortDirection);
        List<BaoCaoChiTraKhuyenKhichChoDBHDTO> dtoList = new ArrayList<BaoCaoChiTraKhuyenKhichChoDBHDTO>();
        for (Object object : (List)resultObject[1]) {
            Object[] tmpArr = (Object[])object;
            BaoCaoChiTraKhuyenKhichChoDBHDTO dto = new BaoCaoChiTraKhuyenKhichChoDBHDTO();

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
            if(tmpArr[2] != null){
                dto.setSoTienKhuyenKhich(tmpArr[2] != null ? Double.valueOf(tmpArr[2].toString()):null);
            }
            if(tmpArr[3] != null){
                dto.setSoTienDaChi(tmpArr[3] != null ? Double.valueOf(tmpArr[3].toString()) : null);
            }
            if(tmpArr[4] != null){
                dto.setThoiGianPhaiChi(tmpArr[4] != null ? Timestamp.valueOf(tmpArr[4].toString()) : null);
            }
            if(tmpArr[5] != null){
                dto.setThoiGianThucChi(tmpArr[5] != null ? Timestamp.valueOf(tmpArr[5].toString()):null);
            }


            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return resultObject;

    }

    @Override
    public Object[] baoCaoChiTraKhuyenKhichChoDiemBanHang_theoChiNhanh(Map<String, Object> properties, String sortExpression, String sortDirection, int firstItem, int maxPageItems) throws ObjectNotFoundException {
        Object[] resultObject = this.thueBaoPhatTrienMoiLocalBean.baoCaoChiTraKhuyenKhichChoDiemBanHang_TheoChiNhanh(properties, firstItem, maxPageItems, sortExpression, sortDirection);
        List<BaoCaoChiTraKhuyenKhichChoDBHDTO> dtoList = new ArrayList<BaoCaoChiTraKhuyenKhichChoDBHDTO>();
        for (Object object : (List)resultObject[1]) {
            Object[] tmpArr = (Object[])object;
            BaoCaoChiTraKhuyenKhichChoDBHDTO dto = new BaoCaoChiTraKhuyenKhichChoDBHDTO();

            if(tmpArr[0] != null){
                BranchDTO chiNhanh = new BranchDTO();
                chiNhanh.setBranch_name(tmpArr[0] != null ? tmpArr[0].toString() : null);
                dto.setBranch(chiNhanh);
            }
            if(tmpArr[1] != null){
                dto.setSoTienKhuyenKhich(tmpArr[1] != null ? Double.valueOf(tmpArr[1].toString()):null);
            }
            if(tmpArr[2] != null){
                dto.setSoTienDaChi(tmpArr[2] != null ? Double.valueOf(tmpArr[2].toString()) : null);
            }
            if(tmpArr[3] != null){
                dto.setThoiGianPhaiChi(tmpArr[3] != null ? Timestamp.valueOf(tmpArr[3].toString()) : null);
            }
            if(tmpArr[4] != null){
                dto.setThoiGianThucChi(tmpArr[4] != null ? Timestamp.valueOf(tmpArr[4].toString()):null);
            }


            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return resultObject;
    }
}
