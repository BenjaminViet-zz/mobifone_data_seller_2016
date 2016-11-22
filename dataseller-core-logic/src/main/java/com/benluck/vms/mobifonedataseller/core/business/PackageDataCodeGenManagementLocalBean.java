package com.benluck.vms.mobifonedataseller.core.business;

import com.benluck.vms.mobifonedataseller.core.dto.PackageDataCodeGenDTO;

import javax.ejb.DuplicateKeyException;
import javax.ejb.Local;
import javax.ejb.ObjectNotFoundException;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/6/16
 * Time: 20:31
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface PackageDataCodeGenManagementLocalBean {

    Object[] searchByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer offset, Integer limitItems);

    void insertUpdatePackageDataCodeGenAndBatch(Long packageDataId, Integer year, Integer batchIndex, Integer numberOfCardCodesGeneratedOrRemain, Boolean isLastBatchGenerated) throws ObjectNotFoundException, DuplicateKeyException, Exception;

    void AddOrUpdateProcessing(Integer year, String[] packageDataIds, Integer statusCode) throws ObjectNotFoundException, DuplicateKeyException;

    PackageDataCodeGenDTO findByUniqueCompositeKey(Long packageDataId, Integer year) throws ObjectNotFoundException;

    void updateBatchRemainingCardCodeSize(Long packageDataCodeGenId, int batchIndex, int remainingCardCodeSize) throws ObjectNotFoundException, DuplicateKeyException;
}
