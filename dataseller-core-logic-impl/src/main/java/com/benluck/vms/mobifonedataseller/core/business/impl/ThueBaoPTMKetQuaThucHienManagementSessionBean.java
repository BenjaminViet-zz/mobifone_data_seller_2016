package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.core.business.ThueBaoPTM_KetQuaThucHienManagementLocalBean;
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
 * Date: 2/11/15
 * Time: 5:15 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "ThueBaoPTMKetQuaThucHienManagementSessionEJB")
public class ThueBaoPTMKetQuaThucHienManagementSessionBean implements ThueBaoPTM_KetQuaThucHienManagementLocalBean {
    @EJB
    private ThueBaoPhatTrienMoiLocalBean thueBaoPhatTrienMoiLocalBean;

    public ThueBaoPTMKetQuaThucHienManagementSessionBean() {
    }

    @Override
    public Object[] search(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) {
        Object[] resultObject = this.thueBaoPhatTrienMoiLocalBean.searchKetQuaThueBaoThamGiaGoiCuoc(properties, firstItem, maxPageItems, sortExpression, sortDirection);
        List<RegistrationTransDTO> dtoList = new ArrayList<RegistrationTransDTO>();
        for (Object object : (List)resultObject[0]){
            Object[] tmpArr = (Object[])object;
            RegistrationTransDTO dto = new RegistrationTransDTO();
            dto.setEz_Isdn(tmpArr[0] != null ? tmpArr[0].toString() : null);
            dto.setCustomer_Isdn(tmpArr[1] != null ? tmpArr[1].toString() : null);
            if(tmpArr[2] != null){
                PromPackageDTO goiCuoc = new PromPackageDTO();
                goiCuoc.setPackage_Name(tmpArr[2].toString());
                goiCuoc.setProm_Amount(tmpArr[3] != null ? Double.valueOf(tmpArr[3].toString()) : null);
                dto.setGoiCuoc(goiCuoc);
            }
            dto.setTrans_Date(tmpArr[4] != null ? Timestamp.valueOf(tmpArr[4].toString()) : null);
            dto.setReg_Position(tmpArr[5] != null ? tmpArr[5].toString() : null);
            dto.setProm_Condition_Status(tmpArr[6] != null ? tmpArr[6].toString() : null);
            dto.setTrans_Status(tmpArr[7] != null ? Integer.valueOf(tmpArr[7].toString()) : null);
            dtoList.add(dto);
        }
        resultObject[0] = dtoList;
        return resultObject;
    }
}
