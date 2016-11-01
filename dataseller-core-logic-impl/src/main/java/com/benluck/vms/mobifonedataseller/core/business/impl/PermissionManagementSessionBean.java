package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.beanUtil.PermissionBeanUtil;
import com.benluck.vms.mobifonedataseller.core.business.PermissionManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.PermissionDTO;
import com.benluck.vms.mobifonedataseller.session.PermissionLocalBean;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 10/31/16
 * Time: 22:53
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "PermissionManagementSessionEJB")
public class PermissionManagementSessionBean implements PermissionManagementLocalBean{
    @EJB
    private PermissionLocalBean permissionService;

    public PermissionManagementSessionBean() {
    }

    @Override
    public List<PermissionDTO> findAll() {
        return PermissionBeanUtil.entityList2DTOList(this.permissionService.findAll());
    }
}
