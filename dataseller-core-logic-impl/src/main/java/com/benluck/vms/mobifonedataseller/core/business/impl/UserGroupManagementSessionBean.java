package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.beanUtil.PermissionBeanUtil;
import com.benluck.vms.mobifonedataseller.beanUtil.UserGroupBeanUtil;
import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.utils.DozerSingletonMapper;
import com.benluck.vms.mobifonedataseller.core.business.UserGroupManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.PermissionDTO;
import com.benluck.vms.mobifonedataseller.core.dto.UserGroupDTO;
import com.benluck.vms.mobifonedataseller.domain.PermissionEntity;
import com.benluck.vms.mobifonedataseller.domain.UserGroupEntity;
import com.benluck.vms.mobifonedataseller.domain.UserGroupPermissionEntity;
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
        return UserGroupBeanUtil.entityList2DTOList(this.userGroupService.findAll());
    }

    @Override
    public List<UserGroupDTO> findAll4Access() {
        return UserGroupBeanUtil.entityList2DTOList(this.userGroupService.findAll4Access());
    }

    @Override
    public Object[] searchByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer offset, Integer limitItems) {
        Object[] resultObject = this.userGroupService.searchByProperties(properties, sortExpression, sortDirection, offset, limitItems);
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
    public void updateItem(UserGroupDTO pojo, String[] permissionIdArr) throws ObjectNotFoundException, DuplicateKeyException {
        UserGroupEntity dbItem = this.userGroupService.findById(pojo.getUserGroupId());
        dbItem.setDescription(pojo.getDescription());
        dbItem = this.userGroupService.update(dbItem);

        if(!pojo.getCode().equals(Constants.USERGROUP_ADMIN)
                && !pojo.getCode().equals(Constants.USERGROUP_KHDN)
                && !pojo.getCode().equals(Constants.USERGROUP_VMS_USER)){
            dbItem.setCode(pojo.getCode().toUpperCase());
            if(permissionIdArr != null && permissionIdArr.length > 0){
                List<Long> updatingPermissionIds = new ArrayList<Long>();
                List<Long> dbPermissionIds = userGroupPermissionService.findPermissionIsListById(pojo.getUserGroupId());
                List<Long> deletePermissionIds = new ArrayList<Long>();
                List<Long> newPermissionIds = new ArrayList<Long>();

                for (String permissionIdStr : permissionIdArr){
                    updatingPermissionIds.add(Long.valueOf(permissionIdStr));
                }

                for(Long updatingPermissionId : updatingPermissionIds){
                    if(!dbPermissionIds.contains(updatingPermissionId)){
                        newPermissionIds.add(updatingPermissionId);
                    }
                }

                for (Long dbPermissionId : dbPermissionIds){
                    if(!updatingPermissionIds.contains(dbPermissionId)){
                        deletePermissionIds.add(dbPermissionId);
                    }
                }

                // add new permissions to this UserGroup
                if(newPermissionIds.size() > 0){
                    for(Long newPermissionId : newPermissionIds){
                        UserGroupPermissionEntity userGroupPermissionEntity = new UserGroupPermissionEntity();
                        userGroupPermissionEntity.setUserGroup(dbItem);

                        PermissionEntity permissionEntity = new PermissionEntity();
                        permissionEntity.setPermissionId(newPermissionId);
                        userGroupPermissionEntity.setPermission(permissionEntity);
                        this.userGroupPermissionService.save(userGroupPermissionEntity);
                    }
                }

                // delete permissions which is no longer in use
                if(deletePermissionIds.size() > 0){
                    this.userGroupPermissionService.deleteOutUpdatePermissionIds(pojo.getUserGroupId(), deletePermissionIds);
                }
            }else{
                this.userGroupPermissionService.deleteByUserGroupId(pojo.getUserGroupId());
            }
        }
    }

    @Override
    public void addItem(UserGroupDTO pojo, String[] permissionIdArr) throws DuplicateKeyException {
        UserGroupEntity userGroupEntity = new UserGroupEntity();
        userGroupEntity.setCode(pojo.getCode().toUpperCase());
        userGroupEntity.setDescription(pojo.getDescription());
        userGroupEntity = this.userGroupService.save(userGroupEntity);

        if(permissionIdArr != null && permissionIdArr.length > 0){
            for (String permissionIdStr : permissionIdArr){
                UserGroupPermissionEntity userGroupPermissionEntity = new UserGroupPermissionEntity();
                PermissionEntity permissionEntity = new PermissionEntity();
                permissionEntity.setPermissionId(Long.valueOf(permissionIdStr));
                userGroupPermissionEntity.setPermission(permissionEntity);
                userGroupPermissionEntity.setUserGroup(userGroupEntity);
                this.userGroupPermissionService.save(userGroupPermissionEntity);
            }
        }
    }

    @Override
    public UserGroupDTO findAndFetchPermissionListById(Long userGroupId) throws ObjectNotFoundException {
        UserGroupDTO dto = UserGroupBeanUtil.entity2DTO(this.userGroupService.findById(userGroupId));
        List<UserGroupPermissionEntity> userGroupPermissionEntityList = this.userGroupPermissionService.findProperty("userGroup.userGroupId", userGroupId);
        if(userGroupPermissionEntityList.size() > 0){
            List<PermissionDTO> permissionDTOList = new ArrayList<PermissionDTO>();
            for (UserGroupPermissionEntity entity : userGroupPermissionEntityList){
                permissionDTOList.add(PermissionBeanUtil.entity2DTO(entity.getPermission()));
            }
            dto.setPermissionList(permissionDTOList);
        }
        return dto;
    }

    @Override
    public UserGroupDTO findByCode(String code) throws ObjectNotFoundException {
        UserGroupEntity entity = this.userGroupService.findEqualUnique("code", code.toUpperCase());
        if (entity == null) throw new ObjectNotFoundException("Not found User Group has Code "+code);
        return DozerSingletonMapper.getInstance().map(entity, UserGroupDTO.class);
    }

    @Override
    public UserGroupDTO findById(Long userGroupId) throws ObjectNotFoundException {
        return UserGroupBeanUtil.entity2DTO(this.userGroupService.findById(userGroupId));
    }
}
