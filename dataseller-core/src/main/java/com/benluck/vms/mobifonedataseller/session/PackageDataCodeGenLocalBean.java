package com.benluck.vms.mobifonedataseller.session;

import com.benluck.vms.mobifonedataseller.domain.PackageDataCodeGenEntity;

import javax.ejb.ObjectNotFoundException;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/6/16
 * Time: 20:34
 * To change this template use File | Settings | File Templates.
 */
public interface PackageDataCodeGenLocalBean extends GenericSessionBean<PackageDataCodeGenEntity, Long>{

    PackageDataCodeGenEntity findByUniqueCompositeKey(Long packageDataId, Integer year) throws ObjectNotFoundException;

    Boolean checkBeforeGeneratingCardCode(Integer year, String[] packageDataIds);
}
