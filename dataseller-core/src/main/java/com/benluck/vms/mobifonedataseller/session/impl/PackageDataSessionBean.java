package com.benluck.vms.mobifonedataseller.session.impl;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.domain.PackageDataEntity;
import com.benluck.vms.mobifonedataseller.session.PackageDataLocalBean;
import org.apache.commons.lang3.StringUtils;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.text.DecimalFormat;
import java.util.Calendar;
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

    @Override
    public List<Long> findPackageDataIdListHasGeneratedCardCode(Integer year) {
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append(" SELECT pdcg.packageData.packageDataId FROM PackageDataCodeGenEntity pdcg WHERE pdcg.year = :year ");
        Query query = entityManager.createQuery(sqlQuery.toString()).setParameter("year", year);
        return (List<Long>)query.getResultList();
    }

    @Override
    public Integer findUsageBeforeDelete(Long packageDataId) {
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append(" SELECT (SELECT COUNT(o.orderId) FROM MOBI_DATA_ORDER o WHERE o.packageDataId = p.packageDataId) + ")
                .append("           (SELECT COUNT(pdcg.packageDataCodeGenId) FROM MOBI_DATA_PACKAGEDATA_CODE_GEN pdcg WHERE pdcg.packageDataId = p.packageDataId ) ")
                .append(" FROM MOBI_DATA_PACKAGE_DATA p WHERE p.packageDataId = :packageDataId ");
        Query query = entityManager.createNativeQuery(sqlQuery.toString()).setParameter("packageDataId", packageDataId);
        return Integer.valueOf(query.getSingleResult().toString());
    }

    @Override
    public PackageDataEntity checkDuplicateValueOrPrefixCardCode(Long packageDataId, Double value, String customPrefixCardCode) {
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append(" FROM PackageDataEntity p WHERE 1 = 1 ");
        if(StringUtils.isNotBlank(customPrefixCardCode)){
            sqlQuery.append(" AND (p.customPrefixUnitPrice = :customPrefixUnitPrice OR (p.customPrefixUnitPrice IS NULL AND p.value = :value)) ");
        }else{
            sqlQuery.append(" AND p.customPrefixUnitPrice = :customPrefixUnitPrice  AND p.value = :value ");
        }
        if(packageDataId != null){
            sqlQuery.append(" AND p.packageDataId != :packageDataId ");
        }
        Query query = entityManager.createQuery(sqlQuery.toString());
        if(StringUtils.isNotBlank(customPrefixCardCode)){
            query.setParameter("value", value);
            query.setParameter("customPrefixUnitPrice", customPrefixCardCode);
        }else{
            query.setParameter("value", value);
            query.setParameter("customPrefixUnitPrice", new DecimalFormat("#").format(value/1000));
        }

        if(packageDataId != null){
            query.setParameter("packageDataId", packageDataId);
        }
        List<PackageDataEntity> entityList = (List<PackageDataEntity>) query.getResultList();
        if(entityList.size() > 0){
            return entityList.get(0);
        }
        return null;
    }
}
