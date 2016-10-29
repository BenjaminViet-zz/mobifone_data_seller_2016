package com.benluck.vms.mobifonedataseller.session;

import com.benluck.vms.mobifonedataseller.domain.PermissionEntity;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 10/29/16
 * Time: 21:42
 * To change this template use File | Settings | File Templates.
 */
public interface PermissionLocalBean extends GenericSessionBean<PermissionEntity,Long> {
    List<PermissionEntity> findPermissionByUserId(Long userId);
}
