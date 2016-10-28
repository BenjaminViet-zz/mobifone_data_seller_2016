package com.benluck.vms.mobifonedataseller.session;

import com.benluck.vms.mobifonedataseller.domain.VmsUserGroupEntity;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: benluck
 * Date: 10/4/14
 * Time: 3:07 PM
 * To change this template use File | Settings | File Templates.
 */
public interface UserGroupLocalBean extends GenericSessionBean<VmsUserGroupEntity, Long> {

    /**
     * Retrieve UserGroupEntity by unique code.
     * @param code
     * @return UserGroupEntity
     */
    VmsUserGroupEntity findUserGroupByCode(String code);

    /**
     * Get list of UserGroupEntity for accessing.
     * @return A list of UserGroupEntity.
     */
    List<VmsUserGroupEntity> findAll4Access();
}
