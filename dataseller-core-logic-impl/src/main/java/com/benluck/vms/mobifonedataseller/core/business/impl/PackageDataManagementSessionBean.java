package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.beanUtil.PackageDataBeanUtil;
import com.benluck.vms.mobifonedataseller.core.business.PackageDataManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.PackageDataDTO;
import com.benluck.vms.mobifonedataseller.domain.PackageDataEntity;
import com.benluck.vms.mobifonedataseller.session.PackageDataLocalBean;

import javax.ejb.EJB;
import javax.ejb.ObjectNotFoundException;
import javax.ejb.Stateless;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/1/16
 * Time: 22:15
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "PackageDataManagementSessionEJB")
public class PackageDataManagementSessionBean implements PackageDataManagementLocalBean{

    @EJB
    private PackageDataLocalBean packageDataService;

    public PackageDataManagementSessionBean() {
    }

    @Override
    public List<PackageDataDTO> findAll() {
        return PackageDataBeanUtil.entityList2DTOList(this.packageDataService.findAll());
    }

    @Override
    public Object[] findByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer offset, Integer limitItems) {
        Object[] resultObject = this.packageDataService.searchByProperties(properties, sortExpression, sortDirection, offset, limitItems);
        List<PackageDataDTO> dtoList = PackageDataBeanUtil.entityList2DTOList((List<PackageDataEntity>) resultObject[1]);
        resultObject[1] = dtoList;
        return resultObject;
    }

    @Override
    public Object[] findListNotYetGenerateCardCode(Integer year, String sortExpression, String sortDirection, Integer offset, Integer limitItems) {
        Object[] resultObject = this.packageDataService.findListNotYetGenerateCardCode(year, sortExpression, sortDirection, offset, limitItems);
        List<PackageDataEntity> packageDataEntityList = (List<PackageDataEntity>)resultObject[1];
        if(packageDataEntityList.size() > 0){
            resultObject[1] = PackageDataBeanUtil.entityList2DTOList(packageDataEntityList);
        }
        return resultObject;
    }

    @Override
    public PackageDataDTO findById(Long packageDataId) throws ObjectNotFoundException {
        return PackageDataBeanUtil.entity2DTO(packageDataService.findById(packageDataId));
    }
}
