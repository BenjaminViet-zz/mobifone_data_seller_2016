package com.benluck.vms.mobifonedataseller.session;

import com.benluck.vms.mobifonedataseller.domain.UserEntity;
import sun.misc.VMSupport;

import javax.ejb.ObjectNotFoundException;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: benluck
 * Date: 10/4/14
 * Time: 3:07 PM
 * To change this template use File | Settings | File Templates.
 */
public interface UserLocalBean extends GenericSessionBean<UserEntity, Long> {
    List<UserEntity> findByName(String fullname, String name);

    /**
     * Retrieve user data by username.
     * @param userName
     * @return
     * @throws ObjectNotFoundException
     */
    UserEntity findByUserName(String userName) throws ObjectNotFoundException;

    /**
     * Get list of UserEntity that matched to the filter.
     * @param properties
     * @param sortExpression
     * @param sortDirection
     * @param firstItem
     * @param maxPageItems
     * @return A list of UserEntity.
     */
    Object[] findListUser(Map<String, Object> properties, String sortExpression, String sortDirection, Integer firstItem, Integer maxPageItems);

    /**
     * Retrieve UserEntity by phone number.
     * @param mobileNumber
     * @return UserEntity
     * @throws ObjectNotFoundException
     */
    UserEntity findByMobileNumber(String mobileNumber) throws ObjectNotFoundException;
}
