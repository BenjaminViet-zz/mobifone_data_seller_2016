package com.benluck.vms.mobifonedataseller.session;

import com.benluck.vms.mobifonedataseller.domain.UserGroupPermissionEntity;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: benluck
 * Date: 10/4/14
 * Time: 3:08 PM
 * To change this template use File | Settings | File Templates.
 */
public interface UserGroupPermissionLocalBean extends GenericSessionBean<UserGroupPermissionEntity, Long> {

    void deleteByUserGroupId(Long userGroupId);

    List<Long> findPermissionIsListById(Long userGroupId);

    void deleteOutUpdatePermissionIds(Long userGroupId, List<Long> permissionIds);
}
