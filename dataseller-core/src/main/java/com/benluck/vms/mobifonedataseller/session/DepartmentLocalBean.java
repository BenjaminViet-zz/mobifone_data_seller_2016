package com.benluck.vms.mobifonedataseller.session;

import com.benluck.vms.mobifonedataseller.domain.DepartmentEntity;
import com.benluck.vms.mobifonedataseller.domain.ShopCodeChiNhanhEntity;
import com.benluck.vms.mobifonedataseller.domain.UserEntity;

import javax.ejb.ObjectNotFoundException;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: benluck
 * Date: 10/4/14
 * Time: 3:11 PM
 * To change this template use File | Settings | File Templates.
 */
public interface DepartmentLocalBean extends GenericSessionBean<DepartmentEntity, Long> {

    /**
     * Get department information by code.
     * @param code
     * @return DepartmentEntity
     */
    DepartmentEntity findByCode(String code);

    /**
     * Get all Department by branchId.
     * @param branchId
     * @return A list of matched DepartmentEntity.
     */
    List findDepartmentListByBranchId(Long branchId);

    /**
     *
     * @param departmentId
     * @param shopCodeId
     * @return
     * @throws ObjectNotFoundException
     */
    List findByIdAndShopCodeId(Long departmentId, Long shopCodeId) throws ObjectNotFoundException;

    /**
     * Search list of department be properties.
     * @param properties
     * @param offset
     * @param limit
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] search(Map<String, Object> properties, Integer offset, Integer limit, String sortExpression, String sortDirection);

    /**
     * Find department list by branchId and promotionId
     * @param branchId
     * @param promotionId
     * @return
     */
    List findByBranchIdAndpromotionId(Long branchId, Long promotionId);
}
