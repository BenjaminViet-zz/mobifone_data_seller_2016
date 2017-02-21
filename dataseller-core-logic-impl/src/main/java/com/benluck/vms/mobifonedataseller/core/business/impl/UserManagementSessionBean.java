package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.beanUtil.PermissionBeanUtil;
import com.benluck.vms.mobifonedataseller.beanUtil.UserBeanUtil;
import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.security.DesEncrypterUtils;
import com.benluck.vms.mobifonedataseller.common.utils.DozerSingletonMapper;
import com.benluck.vms.mobifonedataseller.core.business.UserManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.PermissionDTO;
import com.benluck.vms.mobifonedataseller.core.dto.UserDTO;
import com.benluck.vms.mobifonedataseller.domain.*;
import com.benluck.vms.mobifonedataseller.session.*;

import javax.ejb.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@javax.ejb.Stateless(name = "UserManagementSessionEJB")
public class UserManagementSessionBean implements UserManagementLocalBean{
    @EJB
    private UserLocalBean userService;
    @EJB
    private PermissionLocalBean permissionService;

    public UserManagementSessionBean() {
    }

    @Override
    public UserDTO findByUsername(String username) throws ObjectNotFoundException {
        return UserBeanUtil.entity2DTO(userService.findByUserName(username));
    }

    @Override
    public UserDTO findById(Long userId) throws ObjectNotFoundException {
        return UserBeanUtil.entity2DTO(userService.findById(userId));
    }

    @Override
    public Object[] searchByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer offset, Integer limitItems, String whereClause) {
        Object[] resultObject = this.userService.searchByProperties(properties, sortExpression, sortDirection, offset, limitItems, whereClause);
        List<UserDTO> dtoList = UserBeanUtil.entityList2DTOList((List<UserEntity>) resultObject[1]);
        resultObject[1] = dtoList;
        return resultObject;
    }

    @Override
    public UserDTO findEqualUnique(String key, String value) throws ObjectNotFoundException{
        return UserBeanUtil.entity2DTO(this.userService.findEqualUnique(key, value));
    }

    @Override
    public List<PermissionDTO> loadPermissionsByUserId(Long userId) {
        return PermissionBeanUtil.entityList2DTOList(this.permissionService.findPermissionByUserId(userId));
    }

    @Override
    public UserDTO updateItem(UserDTO pojo, Boolean flagUpdateUserGroup) throws DuplicateKeyException, ObjectNotFoundException {
        UserEntity dbItem = this.userService.findById(pojo.getUserId());
        dbItem.setUserName(pojo.getUserName());
        dbItem.setDisplayName(pojo.getDisplayName());
        dbItem.setIsdn(pojo.getIsdn());
        dbItem.setPassword(DesEncrypterUtils.getInstance().encrypt(pojo.getPassword()));
        dbItem.setLastModified(new Timestamp(System.currentTimeMillis()));

        if(flagUpdateUserGroup != null && flagUpdateUserGroup.booleanValue() && pojo.getUserGroup() != null){
            dbItem.setStatus(pojo.getStatus());
            UserGroupEntity userGroupEntity = new UserGroupEntity();
            userGroupEntity.setUserGroupId(pojo.getUserGroup().getUserGroupId());
            dbItem.setUserGroup(userGroupEntity);
        }
        if(pojo.getStatus() != null){
            dbItem.setStatus(pojo.getStatus());
        }
        return UserBeanUtil.entity2DTO(this.userService.update(dbItem));
    }

    @Override
    public void deleteItemById(Long userId) throws RemoveException {
        this.userService.delete(userId);
    }

    @Override
    public UserDTO addItem(UserDTO dto) throws DuplicateKeyException{
        UserEntity entity = new UserEntity();
        entity.setUserName(dto.getUserName());
        entity.setPassword(DesEncrypterUtils.getInstance().encrypt(dto.getPassword()));
        entity.setDisplayName(dto.getDisplayName());
        entity.setIsdn(dto.getIsdn());
        entity.setStatus(Constants.USER_ACTIVE);
        if(dto.getLDAP() != null){
            entity.setLDAP(dto.getLDAP());
        }else{
            entity.setLDAP(Constants.USER_NOT_LDAP);
        }
        entity.setCreatedDate(new Timestamp(System.currentTimeMillis()));

        UserGroupEntity userGroupEntity = new UserGroupEntity();
        userGroupEntity.setUserGroupId(dto.getUserGroup().getUserGroupId());
        userGroupEntity.setCode(dto.getUserGroup().getCode());

        entity.setUserGroup(userGroupEntity);
        return UserBeanUtil.entity2DTO(this.userService.save(entity));
    }

    @Override
    public void updatePasswordUserLDAP(String userName, String rawPassword) throws ObjectNotFoundException, DuplicateKeyException {
        UserEntity dbItem = this.userService.findEqualUnique("userName", userName);
        dbItem.setPassword(DesEncrypterUtils.getInstance().encrypt(rawPassword));
        userService.update(dbItem);
    }

    @Override
    public UserDTO loadUserByUserNameAndPassword(String username, String password) throws ObjectNotFoundException {
        return UserBeanUtil.entity2DTO(this.userService.loadUserByUserNameAndPassword(username, password));
    }

    @Override
    public List<UserDTO> fetchAllUserIsNotLDAP() {
        return UserBeanUtil.entityList2DTOList(this.userService.fetchAllUserIsNotLDAP());
    }
}
