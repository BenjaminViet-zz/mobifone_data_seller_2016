package com.benluck.vms.mobifonedataseller.session;

import com.benluck.vms.mobifonedataseller.domain.ChiNhanhEntity;

import javax.ejb.ObjectNotFoundException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/19/14
 * Time: 1:39 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ChiNhanhLocalBean extends GenericSessionBean<ChiNhanhEntity, Long>{

    /**
     * Search all and sort branches in the promotion by ascending.
     * @return A list of all branches in the promotion.
     */
    List<ChiNhanhEntity> findAllAndSort();

    /**
     * Get branch information by departmentId.
     * @param departmentId
     * @return An array of Objects that comprises properties of the Branch.
     * @throws ObjectNotFoundException
     */
    Object[] findByDepartmentId(Long departmentId) throws ObjectNotFoundException;

}
