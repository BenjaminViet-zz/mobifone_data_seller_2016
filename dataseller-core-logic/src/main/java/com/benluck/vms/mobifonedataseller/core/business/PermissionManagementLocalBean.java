package com.benluck.vms.mobifonedataseller.core.business;

import com.benluck.vms.mobifonedataseller.core.dto.PermissionDTO;
import com.benluck.vms.mobifonedataseller.core.dto.UserGroupPermissionDTO;

import javax.ejb.Local;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 10/31/16
 * Time: 22:21
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface PermissionManagementLocalBean {
    List<PermissionDTO> findAllAndSort();
}
