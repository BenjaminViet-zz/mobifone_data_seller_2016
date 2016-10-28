package com.benluck.vms.mobifonedataseller.session;

import com.benluck.vms.mobifonedataseller.domain.BranchMappingEntity;

import javax.ejb.Local;
import javax.ejb.ObjectNotFoundException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 3/5/15
 * Time: 10:07 AM
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface BranchLocalBean {

    /**
     * Get list of branches for a promotion which use particular DB Link name.
     * @param dbLinkName
     * @return A list of branch through DB Link name.
     * @throws ObjectNotFoundException
     */
    List findAllBranchesByDBLink(String dbLinkName) throws ObjectNotFoundException;

    /**
     * Get branch information through DB Link name.
     * @param branchId
     * @param dbLinkName
     * @return An array of Objects.
     * @throws ObjectNotFoundException
     */
    Object[] findByBranchIdAndDBLink(Long branchId, String dbLinkName)throws ObjectNotFoundException;

}
