package com.benluck.vms.mobifonedataseller.session.impl;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.domain.PackageDataCodeGenEntity;
import com.benluck.vms.mobifonedataseller.session.PackageDataCodeGenLocalBean;

import javax.ejb.ObjectNotFoundException;
import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/6/16
 * Time: 20:35
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "PackageDataCodeGenSessionEJB")
public class PackageDataCodeGenSessionBean extends AbstractSessionBean<PackageDataCodeGenEntity, Long> implements PackageDataCodeGenLocalBean{

    public PackageDataCodeGenSessionBean() {
    }

    @Override
    public PackageDataCodeGenEntity findByUniqueCompositeKey(Long packageDataId, Integer year) throws ObjectNotFoundException{
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append(" FROM PackageDataCodeGenEntity pdcg WHERE pdcg.packageData.packageDataId = :packageDataId AND pdcg.year = :year ");
        Query query = entityManager.createQuery(sqlQuery.toString())
                                    .setParameter("packageDataId", packageDataId)
                                    .setParameter("year", year);
        List<PackageDataCodeGenEntity> entityList = (List<PackageDataCodeGenEntity>)query.getResultList();
        if(entityList == null || entityList.size() == 0){
            throw new ObjectNotFoundException("Can not find PackageDataCodeGenEntity with packageId " + packageDataId + " and year " + year);
        }else{
            return entityList.get(0);
        }
    }
}
