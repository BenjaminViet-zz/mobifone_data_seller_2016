package com.benluck.vms.mobifonedataseller.session;

import com.benluck.vms.mobifonedataseller.domain.BranchMappingEntity;
import com.benluck.vms.mobifonedataseller.session.GenericSessionBean;

import javax.ejb.ObjectNotFoundException;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 3/2/15
 * Time: 11:14 PM
 * To change this template use File | Settings | File Templates.
 */
public interface BranchMappingLocalBean extends GenericSessionBean<BranchMappingEntity, Long>{
    /**
     * Find mapped branch by Chuong Trinh Id and Chi Nhanh Id
     * @param chuongTrinhId
     * @param chiNhanhId
     * @return BranchMappingEntity
     */
    BranchMappingEntity findByUniqueProperties(Long chuongTrinhId, Long chiNhanhId)throws ObjectNotFoundException;

    /**
     * Find mapped branch by Chuong Trinh Code and Chi Nhanh Id
     * @param chiNhanhId
     * @param ctCode
     * @return BranchMappingEntity
     */
    BranchMappingEntity findByUniqueProperties(Long chiNhanhId, String ctCode)throws ObjectNotFoundException;

    /**
     * Find mapped branch by Chuong Trinh Code and departmentId
     * @param departmentId
     * @param ctCode
     * @return BranchMappingEntity
     * @throws ObjectNotFoundException
     */
    BranchMappingEntity findByDepartmentIdAndCtCode(Long departmentId, String ctCode) throws ObjectNotFoundException;
}
