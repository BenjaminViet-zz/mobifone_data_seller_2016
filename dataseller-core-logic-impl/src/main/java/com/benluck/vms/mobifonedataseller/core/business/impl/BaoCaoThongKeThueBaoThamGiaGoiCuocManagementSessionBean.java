package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.core.business.BaoCaoThongKeThueBaoThamGiaGoiCuocManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BranchDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.DistrictDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.RetailDealerDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015.BaoCaoThongKeThueBaoThamGiaGoiCuocDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015.PromPackageDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015.RegistrationTransDTO;
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
 * Date: 3/4/15
 * Time: 10:25 AM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "BaoCaoThongKeThueBaoTGGCManagementSessionEJB")
public class BaoCaoThongKeThueBaoThamGiaGoiCuocManagementSessionBean implements BaoCaoThongKeThueBaoThamGiaGoiCuocManagementLocalBean {
    @EJB
    private ThueBaoPhatTrienMoiLocalBean thueBaoPhatTrienMoiLocalBean;

    public BaoCaoThongKeThueBaoThamGiaGoiCuocManagementSessionBean() {
    }
    @Override
    public Object[] baoCaoThongKeThueBaoThamGiaGoiCuoc_TheoDiemBanHang(Map<String, Object> properties, String sortExpression, String sortDirection, Integer firstItems, Integer maxPageItems){
        Object[] resultObject = this.thueBaoPhatTrienMoiLocalBean.baoCaoThongKeThueBaoThamGiaGoiCuoc_TheoDiemBanHang(properties, firstItems, maxPageItems, sortExpression, sortDirection);
        List<BaoCaoThongKeThueBaoThamGiaGoiCuocDTO> dtoList = new ArrayList<BaoCaoThongKeThueBaoThamGiaGoiCuocDTO>();
        for (Object object : (List)resultObject[1]) {
            Object[] tmpArr = (Object[])object;
            BaoCaoThongKeThueBaoThamGiaGoiCuocDTO dto = new BaoCaoThongKeThueBaoThamGiaGoiCuocDTO();

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
                RetailDealerDTO diemBanHang = new RetailDealerDTO();
                diemBanHang.setDealer_name(tmpArr[2] != null ? tmpArr[2].toString() : null);
                diemBanHang.setDealer_code(tmpArr[12] != null ? tmpArr[12].toString() : null);
                dto.setRetailDealer(diemBanHang);
            }
            dto.setSoEZ(tmpArr[3] != null ? tmpArr[3].toString() : null);
            dto.setSoThueBao(tmpArr[4] != null ? tmpArr[4].toString() : null);
            if(tmpArr[5] != null){
                PromPackageDTO goiCuoc = new PromPackageDTO();
                goiCuoc.setPackage_Name(tmpArr[5] != null ? tmpArr[5].toString() : null);
                dto.setPromPackage(goiCuoc);
            }
            dto.setThoiGianDK(tmpArr[6] != null ? Timestamp.valueOf(tmpArr[6].toString()) : null);
            dto.setTinhTrang(tmpArr[7] != null ? tmpArr[7].toString() : null);
            dto.setPromConditionStatus(tmpArr[8] != null ? tmpArr[8].toString() : null);
            dto.setTransError(tmpArr[9] != null ? tmpArr[9].toString() : null);
            dto.setPromConditionError(tmpArr[10] != null ? tmpArr[10].toString() : null);
            dto.setSerial(tmpArr[11] != null ? tmpArr[11].toString() : null);
            if(tmpArr[13] != null){
                RegistrationTransDTO registrationTransDTO = new RegistrationTransDTO();
                registrationTransDTO.setReg_Position(tmpArr[13] != null ? tmpArr[13].toString() : null);
                registrationTransDTO.setCalling_amount(tmpArr[15] != null ? Double.valueOf(tmpArr[15].toString()) : null);
                registrationTransDTO.setData_amount(tmpArr[16] != null ? Double.valueOf(tmpArr[16].toString()) : null);
                registrationTransDTO.setSms_amount(tmpArr[17] != null ? Double.valueOf(tmpArr[17].toString()) : null);
                registrationTransDTO.setOthers_amount(tmpArr[18] != null ? Double.valueOf(tmpArr[18].toString()) : null);
                registrationTransDTO.setActive_datetime(tmpArr[19] != null ? Timestamp.valueOf(tmpArr[19].toString()) : null);
                registrationTransDTO.setEvent_code(tmpArr[20] != null ? tmpArr[20].toString() : null);
                registrationTransDTO.setEvent_pos_code(tmpArr[21] != null ? tmpArr[21].toString() : null);
                registrationTransDTO.setEvent_pos_name(tmpArr[22] != null ? tmpArr[22].toString() : null);
                registrationTransDTO.setSales_shop_code(tmpArr[23] != null ? tmpArr[23].toString() : null);
                dto.setRegistrationTrans(registrationTransDTO);
            }
            dto.setNgayTongHop(tmpArr[14] != null ? Timestamp.valueOf(tmpArr[14].toString()) : null);
            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return resultObject;
    }

    @Override
    public Object[] baoCaoThongKeThueBaoThamGiaGoiCuoc_TheoQuanHuyen(Map<String, Object> properties, String sortExpression, String sortDirection, Integer firstItems, Integer maxPageItems) {
        Object[] resultObject = this.thueBaoPhatTrienMoiLocalBean.baoCaoThongKeThueBaoThamGiaGoiCuoc_TheoQuanHuyen(properties, firstItems, maxPageItems, sortExpression, sortDirection);
        List<BaoCaoThongKeThueBaoThamGiaGoiCuocDTO> dtoList = new ArrayList<BaoCaoThongKeThueBaoThamGiaGoiCuocDTO>();
        for (Object object : (List)resultObject[1]) {
            Object[] tmpArr = (Object[])object;
            BaoCaoThongKeThueBaoThamGiaGoiCuocDTO dto = new BaoCaoThongKeThueBaoThamGiaGoiCuocDTO();

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
                PromPackageDTO goiCuoc = new PromPackageDTO();
                goiCuoc.setPackage_Name(tmpArr[2] != null ? tmpArr[2].toString() : null);
                dto.setPromPackage(goiCuoc);
            }
            dto.setThoiGianDK(tmpArr[3] != null ? Timestamp.valueOf(tmpArr[3].toString()) : null);
            dto.setTinhTrang(tmpArr[4] != null ? tmpArr[4].toString() : null);
            dto.setPromConditionStatus(tmpArr[5] != null ? tmpArr[5].toString() : null);
            dto.setTransError(tmpArr[6] != null ? tmpArr[6].toString() : null);
            dto.setPromConditionError(tmpArr[7] != null ? tmpArr[7].toString() : null);

            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return resultObject;
    }

    @Override
    public Object[] baoCaoThongKeThueBaoThamGiaGoiCuoc_TheoChiNhanh(Map<String, Object> properties, String sortExpression, String sortDirection, Integer firstItems, Integer maxPageItems) {
        Object[] resultObject = this.thueBaoPhatTrienMoiLocalBean.baoCaoThongKeThueBaoThamGiaGoiCuoc_TheoChiNhanh(properties, firstItems, maxPageItems, sortExpression, sortDirection);
        List<BaoCaoThongKeThueBaoThamGiaGoiCuocDTO> dtoList = new ArrayList<BaoCaoThongKeThueBaoThamGiaGoiCuocDTO>();
        for (Object object : (List)resultObject[1]) {
            Object[] tmpArr = (Object[])object;
            BaoCaoThongKeThueBaoThamGiaGoiCuocDTO dto = new BaoCaoThongKeThueBaoThamGiaGoiCuocDTO();

            if(tmpArr[0] != null){
                BranchDTO chiNhanh = new BranchDTO();
                chiNhanh.setBranch_name(tmpArr[0] != null ? tmpArr[0].toString() : null);
                dto.setBranch(chiNhanh);
            }
            if(tmpArr[1] != null){
                PromPackageDTO goiCuoc = new PromPackageDTO();
                goiCuoc.setPackage_Name(tmpArr[1] != null ? tmpArr[1].toString() : null);
                dto.setPromPackage(goiCuoc);
            }
            dto.setThoiGianDK(tmpArr[2] != null ? Timestamp.valueOf(tmpArr[2].toString()) : null);
            dto.setTinhTrang(tmpArr[3] != null ? tmpArr[3].toString() : null);
            dto.setPromConditionStatus(tmpArr[4] != null ? tmpArr[4].toString() : null);
            dto.setTransError(tmpArr[5] != null ? tmpArr[5].toString() : null);
            dto.setPromConditionError(tmpArr[6] != null ? tmpArr[6].toString() : null);

            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return resultObject;
    }

}
