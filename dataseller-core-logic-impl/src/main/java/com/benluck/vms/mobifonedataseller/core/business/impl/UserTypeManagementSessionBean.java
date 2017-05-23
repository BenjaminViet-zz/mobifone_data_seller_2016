package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.beanUtil.UserTypeBeanUtil;
import com.benluck.vms.mobifonedataseller.core.business.UserTypeManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.UserTypeDTO;
import com.benluck.vms.mobifonedataseller.domain.UserTypeEntity;
import com.benluck.vms.mobifonedataseller.session.GenericSessionBean;
import com.benluck.vms.mobifonedataseller.session.UserTypeLocalBean;

import javax.ejb.EJB;
import javax.ejb.ObjectNotFoundException;
import javax.ejb.Stateless;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 5/22/17
 * Time: 22:07
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "UserTypeManagementSessionEJB")
public class UserTypeManagementSessionBean implements UserTypeManagementLocalBean{

    @EJB
    private UserTypeLocalBean userTypeService;

    public UserTypeManagementSessionBean() {
    }

    @Override
    public List<UserTypeDTO> findAll() {
        return UserTypeBeanUtil.entityList2DTOList(this.userTypeService.findAll());
    }

    @Override
    public UserTypeDTO findByCode(String code) throws ObjectNotFoundException {
        return UserTypeBeanUtil.entity2DTO(this.userTypeService.findEqualUnique("code", code));
    }
}
