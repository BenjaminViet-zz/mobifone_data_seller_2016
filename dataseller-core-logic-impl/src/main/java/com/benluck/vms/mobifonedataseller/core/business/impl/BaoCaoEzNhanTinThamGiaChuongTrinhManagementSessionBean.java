package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.core.business.BaoCaoEzNhanTinThamGiaChuongTrinhManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BranchDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.DistrictDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.RetailDealerDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015.BaoCaoEzNhanTinThamGiaChuongTrinhDTO;
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
 * Date: 3/18/15
 * Time: 9:42 AM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "BaoCaoEzNhanTinThamGiaChuongTrinhManagementSessionEJB")
public class BaoCaoEzNhanTinThamGiaChuongTrinhManagementSessionBean implements BaoCaoEzNhanTinThamGiaChuongTrinhManagementLocalBean {
    @EJB
    private ThueBaoPhatTrienMoiLocalBean thueBaoPhatTrienMoiLocalBean;
    public BaoCaoEzNhanTinThamGiaChuongTrinhManagementSessionBean() {
    }

    @Override
    public Object[] baoCaoEzNhanTinThamGiaChuongTrinh(Map<String, Object> properties, String sortExpression, String sortDirection, int firstItem, int maxPageItems) {
        Object[] resultObject = this.thueBaoPhatTrienMoiLocalBean.baoCaoEzNhanTinThamGiaChuongTrinh(properties, firstItem, maxPageItems, sortExpression, sortDirection);
        List<BaoCaoEzNhanTinThamGiaChuongTrinhDTO> dtoList = new ArrayList<BaoCaoEzNhanTinThamGiaChuongTrinhDTO>();
        for (Object object : (List)resultObject[1]) {
            Object[] tmpArr = (Object[])object;
            BaoCaoEzNhanTinThamGiaChuongTrinhDTO dto = new BaoCaoEzNhanTinThamGiaChuongTrinhDTO();

            dto.setSoEz(tmpArr[0] != null ? tmpArr[0].toString() : null);
            if(tmpArr[1] != null){
                BranchDTO chiNhanh = new BranchDTO();
                chiNhanh.setBranch_name(tmpArr[1] != null ? tmpArr[1].toString() : null);
                dto.setBranch(chiNhanh);
            }
            if(tmpArr[2] != null){
                DistrictDTO quanHuyen = new DistrictDTO();
                quanHuyen.setDistrict_name(tmpArr[2] != null ? tmpArr[2].toString() : null);
                dto.setDistrict(quanHuyen);
            }
            if(tmpArr[3] != null && tmpArr [4] != null){
                RetailDealerDTO diemBanHang = new RetailDealerDTO();
                diemBanHang.setDealer_code(tmpArr[3] != null ? tmpArr[3].toString() : null);
                diemBanHang.setDealer_name(tmpArr[4] != null ? tmpArr[4].toString() : null);
                dto.setRetailDealer(diemBanHang);
            }
            dto.setNgayNhanTin(tmpArr[5] != null ? Timestamp.valueOf(tmpArr[5].toString()) : null);
            dto.setGhiChu(tmpArr[6] != null ? tmpArr[6].toString() : null);
            dto.setNgayKichHoat(tmpArr[7] != null ? Timestamp.valueOf(tmpArr[7].toString()) : null);

            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return resultObject;
    }
}
