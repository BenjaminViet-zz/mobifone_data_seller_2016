package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.beanUtil.KHDNBeanUtil;
import com.benluck.vms.mobifonedataseller.core.business.KHDNManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.KHDNDTO;
import com.benluck.vms.mobifonedataseller.domain.KHDNEntity;
import com.benluck.vms.mobifonedataseller.session.KHDNLocalBean;

import javax.ejb.*;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/2/16
 * Time: 07:37
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "KHDNManagementSessionEJB")
public class KHDNManagementSessionBean implements KHDNManagementLocalBean{

    @EJB
    private KHDNLocalBean khdnService;

    public KHDNManagementSessionBean() {
    }

    @Override
    public List<KHDNDTO> findAll() {
        return KHDNBeanUtil.entityList2DTOList(this.khdnService.findAll());
    }

    @Override
    public KHDNDTO findById(Long khdnId) throws ObjectNotFoundException {
        return KHDNBeanUtil.entity2DTO(khdnService.findById(khdnId));
    }

    @Override
    public Object[] findByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer offset, Integer limitItems) {
        Object[] resultObject = this.khdnService.searchByProperties(properties, sortExpression, sortDirection, offset, limitItems);
        List<KHDNDTO> dtoList = KHDNBeanUtil.entityList2DTOList((List<KHDNEntity>) resultObject[1]);
        resultObject[1] = dtoList;
        return resultObject;
    }

    @Override
    public void deleteItemById(Long khdnId) throws RemoveException {
        this.khdnService.delete(khdnId);
    }

    @Override
    public void addItem(KHDNDTO pojo) throws DuplicateKeyException {
        KHDNEntity entity = new KHDNEntity();
        entity.setName(pojo.getName());
        entity.setMst(pojo.getMst());
        entity.setGpkd(pojo.getGpkd());
        entity.setIssuedContractDate(pojo.getIssuedContractDate());
        entity.setStb_vas(pojo.getStb_vas());
        entity.setCustId(pojo.getCustId());
        this.khdnService.save(entity);
    }

    @Override
    public void updateItem(KHDNDTO pojo) throws ObjectNotFoundException, DuplicateKeyException {
        KHDNEntity dbItem = this.khdnService.findById(pojo.getKHDNId());
        dbItem.setName(pojo.getName());
        dbItem.setGpkd(pojo.getGpkd());
        dbItem.setMst(pojo.getMst());
        dbItem.setIssuedContractDate(pojo.getIssuedContractDate());
        dbItem.setStb_vas(pojo.getStb_vas());
        dbItem.setCustId(pojo.getCustId());
        this.khdnService.update(dbItem);
    }

    @Override
    public KHDNDTO findEqualUnique(String key, String value) throws ObjectNotFoundException {
        return KHDNBeanUtil.entity2DTO(this.khdnService.findEqualUnique(key, value));
    }

    @Override
    public Boolean checkExistsBeforeDelete(Long khdnId) {
        return this.khdnService.checkExistsBeforeDelete(khdnId);
    }
}
