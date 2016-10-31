package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.beanUtil.UserGroupBeanUtil;
import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.utils.DozerSingletonMapper;
import com.benluck.vms.mobifonedataseller.core.business.UserGroupManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.UserGroupDTO;
import com.benluck.vms.mobifonedataseller.domain.UserGroupEntity;
import com.benluck.vms.mobifonedataseller.session.UserGroupLocalBean;
import com.benluck.vms.mobifonedataseller.session.UserGroupPermissionLocalBean;

import javax.ejb.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Stateless(name = "UserGroupManagementSessionEJB")
public class UserGroupManagementSessionBean implements UserGroupManagementLocalBean {
    public UserGroupManagementSessionBean() {
    }

    @EJB
    private UserGroupLocalBean userGroupService;
    @EJB
    private UserGroupPermissionLocalBean userGroupPermissionService;

    @Override
    public List<UserGroupDTO> findAll() {
        return UserGroupBeanUtil.entityList2DTOList(this.userGroupService.findAll4Access());
    }

    @Override
    public Object[] searchByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer offset, Integer limitItems) {
        StringBuilder whereClause = new StringBuilder();
        whereClause.append("A.code != " + Constants.ADMIN_ROLE);

        Object[] resultObject = this.userGroupService.searchByProperties(properties, sortExpression, sortDirection, offset, limitItems, whereClause.toString());
        List<UserGroupDTO> dtoList = UserGroupBeanUtil.entityList2DTOList((List<UserGroupEntity>) resultObject[1]);
        resultObject[1] = dtoList;
        return resultObject;
    }

    @Override
    public void deleteItem(Long userGroupId) throws RemoveException {
        if(!this.userGroupService.checkInUse(userGroupId)){
            this.userGroupPermissionService.deleteByUserGroupId(userGroupId);
            this.userGroupService.delete(userGroupId);
        }else {
            throw new RemoveException("The UserGroupId: "  + userGroupId + " is in use!. Can not remove it.");
        }
    }

    @Override
    public void updateItem(UserGroupDTO pojo) throws ObjectNotFoundException, DuplicateKeyException {
        UserGroupEntity dbItem = this.userGroupService.findById(pojo.getUserGroupId());
        dbItem.setCode(pojo.getCode());
        dbItem.setDescription(pojo.getDescription());
        this.userGroupService.update(dbItem);
    }

    @Override
    public void addItem(UserGroupDTO pojo) throws DuplicateKeyException {
        UserGroupEntity entity = new UserGroupEntity();
        entity.setCode(pojo.getCode());
        entity.setDescription(pojo.getDescription());
        this.userGroupService.save(entity);
    }

    @Override
    public UserGroupDTO findById(Long userGroupId) throws ObjectNotFoundException {
        return UserGroupBeanUtil.entity2DTO(this.userGroupService.findById(userGroupId));
    }

    @Override
    public UserGroupDTO findByCode(String code) throws ObjectNotFoundException {
        UserGroupEntity entity = this.userGroupService.findEqualUnique("code", code);
        if (entity == null) throw new ObjectNotFoundException("Not found User Group has Code "+code);
        return DozerSingletonMapper.getInstance().map(entity, UserGroupDTO.class);
    }
}
