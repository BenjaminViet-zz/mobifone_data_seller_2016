package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.beanUtil.RoleBeanUtil;
import com.benluck.vms.mobifonedataseller.beanUtil.UserBeanUtil;
import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.security.DesEncrypterUtils;
import com.benluck.vms.mobifonedataseller.common.utils.DozerSingletonMapper;
import com.benluck.vms.mobifonedataseller.core.business.UserManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.RoleDTO;
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
    private UserRoleLocalBean userRoleService;

    public UserManagementSessionBean() {
    }

    @Override
    public UserDTO findByUsername(String username) throws ObjectNotFoundException {
        UserEntity entity = userService.findByUserName(username);
        return DozerSingletonMapper.getInstance().map(entity, UserDTO.class);
    }

    @Override
    public UserDTO findById(Long userId) throws ObjectNotFoundException {
        UserEntity entity = userService.findById(userId);
        if (entity == null){
            throw new ObjectNotFoundException("Not found user  "+ userId);
        }
        return  DozerSingletonMapper.getInstance().map(entity, UserDTO.class);
    }

    @Override
    public Object[] searchByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer offset, Integer limitItems, String whereClause) {
        Object[] resultObject = this.userService.searchByProperties(properties, sortExpression, sortDirection, offset, limitItems, whereClause);
        List<UserDTO> dtoList = new ArrayList<UserDTO>();

        List<UserEntity> entityList = (List<UserEntity>) resultObject[1];
        if(entityList.size() > 0){
            for (UserEntity entity : entityList){
                dtoList.add(UserBeanUtil.entity2DTO(entity));
            }
            resultObject[1] = dtoList;
        }
        return resultObject;
    }

    @Override
    public UserDTO findEqualUnique(String key, String value) throws ObjectNotFoundException{
        UserEntity entity = this.userService.findEqualUnique(key, value);
        return UserBeanUtil.entity2DTO(entity);
    }

    @Override
    public List<RoleDTO> loadRolesByUserId(Long userId) {
        List<UserRoleEntity> entityList = this.userRoleService.findProperty("user.userId", userId);
        if(entityList.size() > 0){
            List<RoleDTO> roleDTOList = new ArrayList<RoleDTO>();
            for (UserRoleEntity entity : entityList){
                roleDTOList.add(RoleBeanUtil.entity2DTO(entity.getRole()));
            }
            return roleDTOList;
        }
        return new ArrayList<RoleDTO>();
    }

    @Override
    public void updateItem(UserDTO pojo) throws DuplicateKeyException, ObjectNotFoundException {
        UserEntity userEntity = this.userService.findById(pojo.getUserId());
        userEntity.setUserName(pojo.getUserName());
        userEntity.setDisplayName(pojo.getDisplayName());
        userEntity.setPassword(DesEncrypterUtils.getInstance().encrypt(pojo.getPassword()));
        userEntity.setLastModified(new Timestamp(System.currentTimeMillis()));
        this.userService.update(userEntity);
    }

    @Override
    public void deleteItemById(Long userId) throws RemoveException {
        this.userService.delete(userId);
    }

    @Override
    public void addItem(UserDTO dto) throws DuplicateKeyException{
        UserEntity entity = new UserEntity();
        entity.setUserName(dto.getUserName());
        entity.setPassword(DesEncrypterUtils.getInstance().encrypt(dto.getPassword()));
        entity.setDisplayName(dto.getDisplayName());
        entity.setStatus(Constants.USER_ACTIVE);
        entity.setCreatedDate(new Timestamp(System.currentTimeMillis()));

        UserGroupEntity userGroupEntity = new UserGroupEntity();
        userGroupEntity.setUserGroupId(dto.getUserGroup().getUserGroupId());

        entity.setUserGroup(userGroupEntity);
        this.userService.save(entity);
    }
}
