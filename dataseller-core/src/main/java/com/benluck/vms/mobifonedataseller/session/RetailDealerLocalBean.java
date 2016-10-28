package com.benluck.vms.mobifonedataseller.session;

import javax.ejb.Local;
import javax.ejb.ObjectNotFoundException;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 3/5/15
 * Time: 10:55 AM
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface RetailDealerLocalBean {
    /**
     * Find District list by DBLink.
     * @param dbLink
     * @return
     * @throws ObjectNotFoundException
     */
    List findAllByDBLink(String dbLink) throws ObjectNotFoundException;

    /**
     * Find District list by chiNhanhId and Chuong Trinh Code
     * @param branchId
     * @param dbLink
     * @return
     */
    List findByBranchIdAndDBLink(Long branchId, String dbLink) throws ObjectNotFoundException;

    /**
     * Find Retail Dealer list by districtId and DBLink.
     * @param district_id
     * @param dbLink
     * @return
     */
    List findByDistrictIdAndDBLink(Long district_id, String dbLink)throws ObjectNotFoundException;

    /**
     * Get information of dealer by properties.
     * @param properties
     * @param dbLinkName
     * @return An array of Objects for Dealer information.
     * @throws ObjectNotFoundException
     */
    Object[] findByEzAndDBLink(Map<String, Object> properties, String dbLinkName) throws ObjectNotFoundException;

    /**
     * Get information of dealer by properties.
     * @param soEZ
     * @param dbLinkName
     * @return An array of Objects for Dealer information.
     */
    Object[] findBySoEZAndDBLinkName(String soEZ, String dbLinkName);

    /**
     * Get information of dealer by properties.
     * @param dealer_id
     * @param dbLinkName
     * @return An array of Objects for Dealer information.
     */
    Object[] findByIdAndDBLinkName(Long dealer_id, String dbLinkName);
}
