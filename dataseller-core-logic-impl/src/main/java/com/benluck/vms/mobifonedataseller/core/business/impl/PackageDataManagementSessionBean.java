package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.beanUtil.PackageDataBeanUtil;
import com.benluck.vms.mobifonedataseller.core.business.PackageDataManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.PackageDataDTO;
import com.benluck.vms.mobifonedataseller.domain.PackageDataEntity;
import com.benluck.vms.mobifonedataseller.session.PackageDataLocalBean;

import javax.ejb.*;
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

    @Override
    public List<Long> findPackageDataIdListHasGeneratedCardCode(Integer year) {
        return this.packageDataService.findPackageDataIdListHasGeneratedCardCode(year);
    }

    @Override
    public PackageDataDTO findEqualUnique(String propertyName, String propertyValue) throws ObjectNotFoundException {
        return PackageDataBeanUtil.entity2DTO(this.packageDataService.findEqualUnique(propertyName, propertyValue));
    }

    @Override
    public void updateItem(PackageDataDTO pojo) throws ObjectNotFoundException, DuplicateKeyException {
        PackageDataEntity dbItem = this.packageDataService.findById(pojo.getPackageDataId());
        dbItem.setName(pojo.getName());
        dbItem.setValue(pojo.getValue());
        dbItem.setVolume(pojo.getVolume());
        dbItem.setDuration(pojo.getDuration());
        dbItem.setDurationText(pojo.getDuration() + " Ngày");
        dbItem.setNumberOfExtend(pojo.getNumberOfExtend());
        dbItem.setTk(pojo.getTk());
        this.packageDataService.update(dbItem);
    }

    @Override
    public PackageDataDTO addItem(PackageDataDTO pojo) throws DuplicateKeyException {
        PackageDataEntity entity = new PackageDataEntity();
        entity.setName(pojo.getName());
        entity.setValue(pojo.getValue());
        entity.setVolume(pojo.getVolume());
        entity.setDuration(pojo.getDuration());
        entity.setDurationText(pojo.getDuration() + " Ngày");
        entity.setNumberOfExtend(pojo.getNumberOfExtend());
        entity.setTk(pojo.getTk());
        return PackageDataBeanUtil.entity2DTO(this.packageDataService.save(entity));
    }

    @Override
    public Integer findUsageBeforeDelete(Long packageDataId) {
        return this.packageDataService.findUsageBeforeDelete(packageDataId);
    }

    @Override
    public void deleteItem(Long packageDataId) throws RemoveException {
        this.packageDataService.delete(packageDataId);
    }
}
