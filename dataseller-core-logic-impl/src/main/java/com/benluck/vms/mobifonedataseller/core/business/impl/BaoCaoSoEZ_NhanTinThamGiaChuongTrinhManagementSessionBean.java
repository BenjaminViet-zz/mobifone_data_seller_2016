package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.core.business.BaoCaoSoEZ_NhanTinThamGiaChuongTrinhManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015.BaoCaoSoEZNhanTinThamGiaChuongTrinhDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015.PromPackageDTO;
import com.benluck.vms.mobifonedataseller.session.ThueBaoPhatTrienMoiLocalBean;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: HP
 * Date: 3/3/15
 * Time: 2:31 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "BaoCaoSoEZ_NhanTinTGCTManagementSessionEJB")
public class BaoCaoSoEZ_NhanTinThamGiaChuongTrinhManagementSessionBean implements BaoCaoSoEZ_NhanTinThamGiaChuongTrinhManagementLocalBean {
    @EJB
    private ThueBaoPhatTrienMoiLocalBean thueBaoPhatTrienMoiLocalBean;

    public BaoCaoSoEZ_NhanTinThamGiaChuongTrinhManagementSessionBean() {
    }

    @Override
    public Object[] baoCaoSoEZ_NhanTinThamGiaChuongTrinh(BaoCaoSoEZNhanTinThamGiaChuongTrinhDTO baoCaoSoEZNhanTinThamGiaChuongTrinhDTO, String sortExpression, String sortDirection, Integer firstItems, Integer maxPageItems) {
        Map<String, Object> properties = new HashMap<String, Object>();

        if(baoCaoSoEZNhanTinThamGiaChuongTrinhDTO.getGoiCuoc() != null && baoCaoSoEZNhanTinThamGiaChuongTrinhDTO.getGoiCuoc().getPackage_Id() != null){
            properties.put("packageId", baoCaoSoEZNhanTinThamGiaChuongTrinhDTO.getGoiCuoc().getPackage_Id());
        }

        if(baoCaoSoEZNhanTinThamGiaChuongTrinhDTO.getFromDate() != null){
            properties.put("fromDateTime", baoCaoSoEZNhanTinThamGiaChuongTrinhDTO.getFromDate());
        }
        if(baoCaoSoEZNhanTinThamGiaChuongTrinhDTO.getToDate() != null){
            properties.put("toDateTime", baoCaoSoEZNhanTinThamGiaChuongTrinhDTO.getToDate());
        }
        if(baoCaoSoEZNhanTinThamGiaChuongTrinhDTO.getBranchId() != null){
            properties.put("branch_Id", baoCaoSoEZNhanTinThamGiaChuongTrinhDTO.getBranchId());
        }

        Object[] resultObject = this.thueBaoPhatTrienMoiLocalBean.BaoCaoSoEZ_NhanTinThamGiaChuongTrinh(properties, firstItems, maxPageItems, sortExpression, sortDirection);
        List<BaoCaoSoEZNhanTinThamGiaChuongTrinhDTO> dtoList = new ArrayList<BaoCaoSoEZNhanTinThamGiaChuongTrinhDTO>();
        for (Object object : (List)resultObject[1]) {
            Object[] tmpArr = (Object[])object;
            BaoCaoSoEZNhanTinThamGiaChuongTrinhDTO dto = new BaoCaoSoEZNhanTinThamGiaChuongTrinhDTO();

            dto.setSoEZDangKy(tmpArr[0] != null ? tmpArr[0].toString() : null);
            dto.setThueBaoKH(tmpArr[1] != null ? tmpArr[1].toString() : null);
            if(tmpArr[2] != null){
                PromPackageDTO goiCuoc = new PromPackageDTO();
                goiCuoc.setPackage_Name(tmpArr[2] != null ? tmpArr[2].toString() : null);
                dto.setGoiCuoc(goiCuoc);
            }
            dto.setThoiGianDK(tmpArr[3] != null ? Timestamp.valueOf(tmpArr[3].toString()) : null);
            dto.setMucKhuyenKhich(tmpArr[4] != null ? Double.valueOf(tmpArr[4].toString()) : null);
            dto.setSerial(tmpArr[5] != null ? tmpArr[5].toString() : null);
            dto.setTransStatus(tmpArr[6] != null ? tmpArr[6].toString() : null);
            dto.setTransError(tmpArr[7] != null ? tmpArr[7].toString() : null);

            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return resultObject;
    }
}
