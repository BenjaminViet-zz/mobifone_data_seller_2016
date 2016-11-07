package com.benluck.vms.mobifonedataseller.session.impl;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.domain.PackageDataEntity;
import com.benluck.vms.mobifonedataseller.session.PackageDataLocalBean;
import org.apache.commons.lang3.StringUtils;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/1/16
 * Time: 22:17
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "PackageDataSessionEJB")
public class PackageDataSessionBean extends AbstractSessionBean<PackageDataEntity, Long> implements PackageDataLocalBean{

    public PackageDataSessionBean() {
    }

    @Override
    public Object[] findListNotYetGenerateCardCode(Integer year, String sortExpression, String sortDirection, Integer offset, Integer limitItems) {
        StringBuilder sqlMainQuery = new StringBuilder();
        sqlMainQuery.append(" FROM PackageDataEntity pd ")
                    .append(" WHERE NOT EXISTS (SELECT 1 FROM PackageDataCodeGenEntity pdcg WHERE pdcg.year = :year AND pd.packageDataId = pdcg.packageData.packageDataId) ");

        StringBuilder sqlQuery = new StringBuilder(sqlMainQuery.toString());
        if(StringUtils.isNotBlank(sortExpression) && StringUtils.isNotBlank(sortDirection)){
            sqlQuery.append(" ORDER BY pd.").append(sortExpression);
            sqlQuery.append(sortDirection.equals(Constants.SORT_ASC) ? " ASC " : " DESC ");
        }

        Query query = entityManager.createQuery(sqlQuery.toString()).setParameter("year", year);
        query.setFirstResult(offset);
        query.setMaxResults(limitItems);
        List<PackageDataEntity> packageDataEntityList = (List<PackageDataEntity>)query.getResultList();

        StringBuilder sqlCount = new StringBuilder();
        sqlCount.append(" SELECT COUNT(pd.packageDataId) ").append(sqlMainQuery.toString());
        Query countQuery = entityManager.createQuery(sqlCount.toString()).setParameter("year", year);
        Integer count = Integer.valueOf(countQuery.getSingleResult().toString());
        return new Object[]{count, packageDataEntityList};
    }
}
