package com.benluck.vms.mobifonedataseller.session;

import com.benluck.vms.mobifonedataseller.domain.PackageDataEntity;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/1/16
 * Time: 22:17
 * To change this template use File | Settings | File Templates.
 */
public interface PackageDataLocalBean extends GenericSessionBean<PackageDataEntity, Long>{

    Object[] findListNotYetGenerateCardCode(Integer year, String sortExpression, String sortDirection, Integer offset, Integer limitItems);

    List<Long> findPackageDataIdListHasGeneratedCardCode(Integer year);

    Integer findUsageBeforeDelete(Long packageDataId);

    PackageDataEntity checkDuplicateValueOrPrefixCardCode(Long packageDataId, Double value, String customPrefixCardCode);
}
