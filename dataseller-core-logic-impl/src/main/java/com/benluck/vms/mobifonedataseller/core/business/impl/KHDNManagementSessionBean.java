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
    private KHDNLocalBean KHDNService;

    public KHDNManagementSessionBean() {
    }

    @Override
    public List<KHDNDTO> findAll() {
        return KHDNBeanUtil.entityList2DTOList(this.KHDNService.findAll());
    }

    @Override
    public KHDNDTO findById(Long userId) throws ObjectNotFoundException {
        return KHDNBeanUtil.entity2DTO(KHDNService.findById(userId));
    }

    @Override
    public Object[] findByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer offset, Integer limitItems) {
        Object[] resultObject = this.KHDNService.searchByProperties(properties, sortExpression, sortDirection, offset, limitItems);
        List<KHDNDTO> dtoList = KHDNBeanUtil.entityList2DTOList((List<KHDNEntity>) resultObject[1]);
        resultObject[1] = dtoList;
        return resultObject;
    }

    @Override
    public void deleteItemById(Long KHDNId) throws RemoveException {
        this.KHDNService.delete(KHDNId);
    }

    @Override
    public KHDNDTO addItem(KHDNDTO pojo) throws DuplicateKeyException {
        KHDNEntity entity = new KHDNEntity();
        entity.setName(pojo.getName());
        entity.setMst(pojo.getMst());
        entity.setGpkd(pojo.getGpkd());
        entity.setIssuedContractDate(pojo.getIssuedContractDate());
        entity.setStb_vas(pojo.getStb_vas());
        //entity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        /*entity.setActiveStatus(Constants.ORDER_ACTIVE_STATUS_ALIVE);*/
        //entity = this.orderService.save(entity);
        return KHDNBeanUtil.entity2DTO(this.KHDNService.save(entity));
        //createdOrderHistory(pojo, Constants.ORDER_HISTORY_OPERATOR_CREATED, entity);
    }
}
