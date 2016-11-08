package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.beanUtil.PackageDataCodeGenBeanUtil;
import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.core.business.PackageDataCodeGenManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.PackageDataCodeGenDTO;
import com.benluck.vms.mobifonedataseller.domain.PackageDataCodeGenEntity;
import com.benluck.vms.mobifonedataseller.domain.PackageDataEntity;
import com.benluck.vms.mobifonedataseller.session.PackageDataCodeGenLocalBean;

import javax.ejb.DuplicateKeyException;
import javax.ejb.EJB;
import javax.ejb.ObjectNotFoundException;
import javax.ejb.Stateless;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/6/16
 * Time: 20:33
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "PackageDataCodeGenManagementSessionEJB")
public class PackageDataCodeGenManagementSessionBean implements PackageDataCodeGenManagementLocalBean{

    @EJB
    private PackageDataCodeGenLocalBean packageDataCodeGenService;

    public PackageDataCodeGenManagementSessionBean() {
    }

    @Override
    public Object[] searchByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer offset, Integer limitItems) {
        Object[] resultObject = this.packageDataCodeGenService.searchByProperties(properties, sortExpression, sortDirection, offset, limitItems);
        List<PackageDataCodeGenDTO> orderDTOList = PackageDataCodeGenBeanUtil.entityList2DTOList((List<PackageDataCodeGenEntity>) resultObject[1]);
        resultObject[1] = orderDTOList;
        return resultObject;
    }

    @Override
    public void insertUpdatePackageDataCodeGenAndBatch(Long packageDataId, Integer year, Integer batchIndex, Integer numberOfCardCodesGeneratedOrRemain, Boolean isLastBatchGenerated) throws ObjectNotFoundException, DuplicateKeyException, Exception{
        PackageDataCodeGenEntity dbItem = this.packageDataCodeGenService.findByUniqueCompositeKey(packageDataId, year);
        if(dbItem.getStatus().equals(Constants.PACKAGE_DATA_CODE_GEN_STATUS_SUCCESS)){
            if(batchIndex == 1){
                if(numberOfCardCodesGeneratedOrRemain > dbItem.getBatch1_Remaining()){
                    throw new Exception("The remaining of Card Code for batch " + batchIndex + " must less than Database storage. Updating value: " + numberOfCardCodesGeneratedOrRemain + " | DB Storage Value : " + dbItem.getBatch1_Remaining());
                }
                dbItem.setBatch1_Remaining(numberOfCardCodesGeneratedOrRemain);
            }else if(batchIndex == 2){
                if(numberOfCardCodesGeneratedOrRemain > dbItem.getBatch2_Remaining()){
                    throw new Exception("The remaining of Card Code for batch " + batchIndex + " must less than Database storage. Updating value: " + numberOfCardCodesGeneratedOrRemain + " | DB Storage Value : " + dbItem.getBatch2_Remaining());
                }
                dbItem.setBatch2_Remaining(numberOfCardCodesGeneratedOrRemain);
            }else if(batchIndex == 3){
                if(numberOfCardCodesGeneratedOrRemain > dbItem.getBatch3_Remaining()){
                    throw new Exception("The remaining of Card Code for batch " + batchIndex + " must less than Database storage. Updating value: " + numberOfCardCodesGeneratedOrRemain + " | DB Storage Value : " + dbItem.getBatch3_Remaining());
                }
                dbItem.setBatch3_Remaining(numberOfCardCodesGeneratedOrRemain);
            }else if(batchIndex == 4){
                if(numberOfCardCodesGeneratedOrRemain > dbItem.getBatch4_Remaining()){
                    throw new Exception("The remaining of Card Code for batch " + batchIndex + " must less than Database storage. Updating value: " + numberOfCardCodesGeneratedOrRemain + " | DB Storage Value : " + dbItem.getBatch4_Remaining());
                }
                dbItem.setBatch4_Remaining(numberOfCardCodesGeneratedOrRemain);
            }else if(batchIndex == 5){
                if(numberOfCardCodesGeneratedOrRemain > dbItem.getBatch5_Remaining()){
                    throw new Exception("The remaining of Card Code for batch " + batchIndex + " must less than Database storage. Updating value: " + numberOfCardCodesGeneratedOrRemain + " | DB Storage Value : " + dbItem.getBatch5_Remaining());
                }
                dbItem.setBatch5_Remaining(numberOfCardCodesGeneratedOrRemain);
            }else if(batchIndex == 6){
                if(numberOfCardCodesGeneratedOrRemain > dbItem.getBatch6_Remaining()){
                    throw new Exception("The remaining of Card Code for batch " + batchIndex + " must less than Database storage. Updating value: " + numberOfCardCodesGeneratedOrRemain + " | DB Storage Value : " + dbItem.getBatch6_Remaining());
                }
                dbItem.setBatch6_Remaining(numberOfCardCodesGeneratedOrRemain);
            }else if(batchIndex == 7){
                if(numberOfCardCodesGeneratedOrRemain > dbItem.getBatch7_Remaining()){
                    throw new Exception("The remaining of Card Code for batch " + batchIndex + " must less than Database storage. Updating value: " + numberOfCardCodesGeneratedOrRemain + " | DB Storage Value : " + dbItem.getBatch7_Remaining());
                }
                dbItem.setBatch7_Remaining(numberOfCardCodesGeneratedOrRemain);
            }else if(batchIndex == 8){
                if(numberOfCardCodesGeneratedOrRemain > dbItem.getBatch8_Remaining()){
                    throw new Exception("The remaining of Card Code for batch " + batchIndex + " must less than Database storage. Updating value: " + numberOfCardCodesGeneratedOrRemain + " | DB Storage Value : " + dbItem.getBatch8_Remaining());
                }
                dbItem.setBatch8_Remaining(numberOfCardCodesGeneratedOrRemain);
            }else if(batchIndex == 9){
                if(numberOfCardCodesGeneratedOrRemain > dbItem.getBatch9_Remaining()){
                    throw new Exception("The remaining of Card Code for batch " + batchIndex + " must less than Database storage. Updating value: " + numberOfCardCodesGeneratedOrRemain + " | DB Storage Value : " + dbItem.getBatch9_Remaining());
                }
                dbItem.setBatch9_Remaining(numberOfCardCodesGeneratedOrRemain);
            }

            this.packageDataCodeGenService.update(dbItem);
        }else if (dbItem.getStatus().equals(Constants.PACKAGE_DATA_CODE_GEN_STATUS_PROCESSING)){
            if(batchIndex == 1){
                dbItem.setBatch1_Remaining(numberOfCardCodesGeneratedOrRemain);
            }else if(batchIndex == 2){
                dbItem.setBatch2_Remaining(numberOfCardCodesGeneratedOrRemain);
            }else if(batchIndex == 3){
                dbItem.setBatch3_Remaining(numberOfCardCodesGeneratedOrRemain);
            }else if(batchIndex == 4){
                dbItem.setBatch4_Remaining(numberOfCardCodesGeneratedOrRemain);
            }else if(batchIndex == 5){
                dbItem.setBatch5_Remaining(numberOfCardCodesGeneratedOrRemain);
            }else if(batchIndex == 6){
                dbItem.setBatch6_Remaining(numberOfCardCodesGeneratedOrRemain);
            }else if(batchIndex == 7){
                dbItem.setBatch7_Remaining(numberOfCardCodesGeneratedOrRemain);
            }else if(batchIndex == 8){
                dbItem.setBatch8_Remaining(numberOfCardCodesGeneratedOrRemain);
            }else if(batchIndex == 9){
                dbItem.setBatch9_Remaining(numberOfCardCodesGeneratedOrRemain);
            }

            if(isLastBatchGenerated.booleanValue()){
                dbItem.setStatus(Constants.PACKAGE_DATA_CODE_GEN_STATUS_SUCCESS);
            }

            this.packageDataCodeGenService.update(dbItem);
        }
    }

    @Override
    public void updateProcessing(Integer year, String[] packageDataIds, Integer statusCode) throws ObjectNotFoundException, DuplicateKeyException {
        Long packageDataId = null;
        PackageDataCodeGenEntity dbItem = null;
        for (String packageDataIdStr : packageDataIds){
            packageDataId = Long.valueOf(packageDataIdStr);

            try{
                dbItem = this.packageDataCodeGenService.findByUniqueCompositeKey(packageDataId, year);
            }catch (ObjectNotFoundException one){}

            if(dbItem == null){
                PackageDataEntity packageDataEntity = new PackageDataEntity();
                packageDataEntity.setPackageDataId(Long.valueOf(packageDataIdStr));

                PackageDataCodeGenEntity entity = new PackageDataCodeGenEntity();
                entity.setPackageData(packageDataEntity);
                entity.setYear(year);
                entity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
                entity.setBatch1_Remaining(0);
                entity.setBatch2_Remaining(0);
                entity.setBatch3_Remaining(0);
                entity.setBatch4_Remaining(0);
                entity.setBatch5_Remaining(0);
                entity.setBatch6_Remaining(0);
                entity.setBatch7_Remaining(0);
                entity.setBatch8_Remaining(0);
                entity.setBatch9_Remaining(0);
                entity.setStatus(statusCode);
                this.packageDataCodeGenService.save(entity);
            }else if(!dbItem.getStatus().equals(statusCode)){
                dbItem.setStatus(statusCode);
                this.packageDataCodeGenService.update(dbItem);
            }
        }
    }

    @Override
    public Boolean checkBeforeGeneratingCardCode(Integer year, String[] packageDataIds) {
        return this.packageDataCodeGenService.checkBeforeGeneratingCardCode(year, packageDataIds);
    }

    @Override
    public PackageDataCodeGenDTO findByUniqueCompositeKey(Long packageDataId, Integer year) throws ObjectNotFoundException{
        return PackageDataCodeGenBeanUtil.entity2DTO(this.packageDataCodeGenService.findByUniqueCompositeKey(packageDataId, year));
    }

    @Override
    public void updateBatchRemainingCardCodeSize(Long packageDataCodeGenId, int batchIndex, int remainingCardCodeSize) throws ObjectNotFoundException, DuplicateKeyException{
        PackageDataCodeGenEntity dbItem = this.packageDataCodeGenService.findById(packageDataCodeGenId);
        if(batchIndex == 1){
            dbItem.setBatch1_Remaining(remainingCardCodeSize);
        }else if(batchIndex == 2){
            dbItem.setBatch2_Remaining(remainingCardCodeSize);
        }else if(batchIndex == 3){
            dbItem.setBatch3_Remaining(remainingCardCodeSize);
        }else if(batchIndex == 4){
            dbItem.setBatch4_Remaining(remainingCardCodeSize);
        }else if(batchIndex == 5){
            dbItem.setBatch5_Remaining(remainingCardCodeSize);
        }else if(batchIndex == 6){
            dbItem.setBatch6_Remaining(remainingCardCodeSize);
        }else if(batchIndex == 7){
            dbItem.setBatch7_Remaining(remainingCardCodeSize);
        }else if(batchIndex == 8){
            dbItem.setBatch8_Remaining(remainingCardCodeSize);
        }else if(batchIndex == 9){
            dbItem.setBatch9_Remaining(remainingCardCodeSize);
        }
        this.packageDataCodeGenService.update(dbItem);
    }
}
