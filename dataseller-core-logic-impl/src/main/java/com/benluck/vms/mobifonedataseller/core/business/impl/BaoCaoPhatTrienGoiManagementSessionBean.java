package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.core.business.BaoCaoPhatTrienGoiManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015.BaoCaoPhatTrienGoiDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015.PromPackageDTO;
import com.benluck.vms.mobifonedataseller.session.ThueBaoPhatTrienMoiLocalBean;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: vovanphuc0810
 * Date: 7/15/15
 * Time: 10:32 AM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "BaoCaoPhatTrienGoiManagementSessionEJB")
public class BaoCaoPhatTrienGoiManagementSessionBean implements BaoCaoPhatTrienGoiManagementLocalBean{
    public BaoCaoPhatTrienGoiManagementSessionBean() {
    }
    @EJB
    private ThueBaoPhatTrienMoiLocalBean thueBaoPhatTrienMoiLocalBean;

    @Override
    public Object[] baoCaoPhatTrienGoi(Map<String, Object> properties, String sortExpression, String sortDirection, Integer firstItem, Integer reportMaxPageItems) {
        Object[] resultObject = this.thueBaoPhatTrienMoiLocalBean.baoCaoPhatTrienGoi(properties, firstItem, reportMaxPageItems, sortExpression, sortDirection);
        List<BaoCaoPhatTrienGoiDTO> dtoList = new ArrayList<BaoCaoPhatTrienGoiDTO>();
        for (Object object : (List)resultObject[1]){
            Object[] tmpArr = (Object[])object;
            BaoCaoPhatTrienGoiDTO dto = new BaoCaoPhatTrienGoiDTO();
            if (tmpArr[0] != null || tmpArr[1] != null){
                PromPackageDTO promPackageDTO = new PromPackageDTO();
                promPackageDTO.setPackage_Name(tmpArr[0] != null ? tmpArr[0].toString() : null);
                promPackageDTO.setPrice(tmpArr[1] != null ? Double.valueOf(tmpArr[1].toString()) : null);
                dto.setPromPackageDTO(promPackageDTO);
            }
            dto.setAccumulated_quantity(tmpArr[2] != null ? Integer.valueOf(tmpArr[2].toString()) : null);
            dto.setNew_reg_quantity(tmpArr[3] != null ? Integer.valueOf(tmpArr[3].toString()) : null);
            dto.setFinished_quantity(tmpArr[4] != null ? Integer.valueOf(tmpArr[4].toString()) : null);
            dto.setRenewal_quantity(tmpArr[5] != null ? Integer.valueOf(tmpArr[5].toString()) : null);
            dto.setPackage_revenue(tmpArr[6] != null ? Double.valueOf(tmpArr[6].toString()) : null);
            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return resultObject;
    }
}
