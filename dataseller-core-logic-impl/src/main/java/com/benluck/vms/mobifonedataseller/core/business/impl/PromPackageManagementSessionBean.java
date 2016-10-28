package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.core.business.PromPackageManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015.PromPackageDTO;
import com.benluck.vms.mobifonedataseller.session.ThueBaoPhatTrienMoiLocalBean;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 2/12/15
 * Time: 12:11 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "PromPackageManagementSessionEJB")
public class PromPackageManagementSessionBean implements PromPackageManagementLocalBean{
    public PromPackageManagementSessionBean() {
    }

    @EJB
    private ThueBaoPhatTrienMoiLocalBean thueBaoPhatTrienMoiLocalBean;

    @Override
    public List<PromPackageDTO> findAll() {
        List resultSet = this.thueBaoPhatTrienMoiLocalBean.findAllPromPackage();
        List<PromPackageDTO> dtoList = new ArrayList<PromPackageDTO>();
        for (Object object : resultSet){
            Object[] tmpArr = (Object[])object;
            PromPackageDTO dto = new PromPackageDTO();
            dto.setPackage_Id(tmpArr[0] != null ? Long.valueOf(tmpArr[0].toString()) : null);
            dto.setPackage_Code(tmpArr[1] != null ? tmpArr[1].toString() : null);
            dto.setPackage_Name(tmpArr[2] != null ? tmpArr[2].toString() : null);
            dto.setPackage_Type(tmpArr[3] != null ? tmpArr[3].toString() : null);
            dto.setPrice(tmpArr[4] != null ? Double.valueOf(tmpArr[4].toString()) : null);
            dto.setCreated_DateTime(tmpArr[5] != null ? Timestamp.valueOf(tmpArr[5].toString()) : null);
            dto.setCreate_User(tmpArr[6] != null ? Long.valueOf(tmpArr[6].toString()) : null);
            dto.setDescription(tmpArr[7] != null ? tmpArr[7].toString() : null);
            dto.setProm_Amount(tmpArr[8] != null ? Double.valueOf(tmpArr[8].toString()) : null);
            dtoList.add(dto);
        }
        return dtoList;
    }
}
