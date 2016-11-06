package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.beanUtil.KHDNBeanUtil;
import com.benluck.vms.mobifonedataseller.core.business.KHDNManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.KHDNDTO;
import com.benluck.vms.mobifonedataseller.domain.KHDNEntity;
import com.benluck.vms.mobifonedataseller.session.KHDNLocalBean;

import javax.ejb.EJB;
import javax.ejb.Stateless;
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
    public Object[] findByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer offset, Integer limitItems) {
        Object[] resultObject = this.KHDNService.searchByProperties(properties, sortExpression, sortDirection, offset, limitItems);
        List<KHDNDTO> dtoList = KHDNBeanUtil.entityList2DTOList((List<KHDNEntity>) resultObject[1]);
        resultObject[1] = dtoList;
        return resultObject;
    }
}
