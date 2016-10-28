package com.benluck.vms.mobifonedataseller.session;

import javax.ejb.Local;
import javax.ejb.ObjectNotFoundException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 3/5/15
 * Time: 10:41 AM
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface DistrictLocalBean {

    /**
     * Find list of District by DB Link name.
     * @param dbLinkName
     * @return A list of matched district.
     * @throws ObjectNotFoundException
     */
    List findAllByDBLink(String dbLinkName)throws ObjectNotFoundException;

    /**
     * Find list of District by branchId and DBLink name.
     * @param branchId
     * @param dbLinkName
     * @return
     * @throws ObjectNotFoundException
     */
    List findByBranchIdAndDBLink(Long branchId, String dbLinkName)throws ObjectNotFoundException;
}
