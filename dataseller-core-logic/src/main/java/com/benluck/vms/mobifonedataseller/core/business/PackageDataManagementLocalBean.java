package com.benluck.vms.mobifonedataseller.core.business;

import com.benluck.vms.mobifonedataseller.core.dto.PackageDataDTO;

import javax.ejb.DuplicateKeyException;
import javax.ejb.Local;
import javax.ejb.ObjectNotFoundException;
import javax.ejb.RemoveException;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/1/16
 * Time: 22:14
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface PackageDataManagementLocalBean {

    List<PackageDataDTO> findAll();

    Object[] findByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer offset, Integer limitItems);

    Object[] findListNotYetGenerateCardCode(Integer year, String sortExpression, String sortDirection, Integer offset, Integer limitItems);

    PackageDataDTO findById(Long packageDataId) throws ObjectNotFoundException;

    List<Long> findPackageDataIdListHasGeneratedCardCode(Integer year);

    PackageDataDTO findEqualUnique(String propertyName, Object propertyValue) throws ObjectNotFoundException;

    PackageDataDTO addItem(PackageDataDTO pojo) throws DuplicateKeyException;

    void updateItem(PackageDataDTO pojo) throws ObjectNotFoundException, DuplicateKeyException;

    Integer findUsageBeforeDelete(Long packageDataId);

    void deleteItem(Long packageDataId) throws RemoveException;

    PackageDataDTO checkDuplicateValueOrPrefixCardCode(Long packageDataId, Double value, String customPrefixCardCode);
}
