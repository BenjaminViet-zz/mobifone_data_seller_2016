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
        StringBuilder whereClause = new StringBuilder();
        whereClause.append("A.code != '" + Constants.ADMIN_ROLE + "'");

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
    public void updateItem(UserGroupDTO pojo, String[] permissionIdArr) throws ObjectNotFoundException, DuplicateKeyException {
        UserGroupEntity dbItem = this.userGroupService.findById(pojo.getUserGroupId());
        dbItem.setCode(pojo.getCode());
        dbItem.setDescription(pojo.getDescription());
        dbItem = this.userGroupService.update(dbItem);

        if(permissionIdArr != null && permissionIdArr.length > 0){
            Long[] updatingPermissionIds = new Long[permissionIdArr.length];
            for (int i = 0; i < permissionIdArr.length; i++){
                updatingPermissionIds[i] = Long.valueOf(permissionIdArr[i]);
            }

            Long[] dbPermissionIds = this.userGroupPermissionService.findPermissionIsListById(pojo.getUserGroupId());
            List<Long> addingPermissionIds = new ArrayList<Long>();

            for(Long updatingPermissionId : updatingPermissionIds){
                boolean isExisting = false;
                for(Long dbPermissionId : dbPermissionIds){
                    if(dbPermissionId.equals(updatingPermissionId)){
                        isExisting = true;
                        break;
                    }
                }

                if(!isExisting){
                    addingPermissionIds.add(updatingPermissionId);
                }

                // add new permissions to this UserGroup
                for(Long newPermissionId : addingPermissionIds){
                    UserGroupPermissionEntity userGroupPermissionEntity = new UserGroupPermissionEntity();
                    userGroupPermissionEntity.setUserGroup(dbItem);

                    PermissionEntity permissionEntity = new PermissionEntity();
                    permissionEntity.setPermissionId(newPermissionId);
                    userGroupPermissionEntity.setPermission(permissionEntity);
                    this.userGroupPermissionService.save(userGroupPermissionEntity);
                }

                // delete permissions which is no longer in use
                this.userGroupPermissionService.deletePermissionNotInList(pojo.getUserGroupId(), updatingPermissionIds);
            }
        }else{
            this.userGroupPermissionService.deleteByUserGroupId(pojo.getUserGroupId());
        }
    }

    @Override
    public void addItem(UserGroupDTO pojo, String[] permissionIdArr) throws DuplicateKeyException {
        UserGroupEntity userGroupEntity = new UserGroupEntity();
        userGroupEntity.setCode(pojo.getCode());
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
        UserGroupEntity entity = this.userGroupService.findEqualUnique("code", code);
        if (entity == null) throw new ObjectNotFoundException("Not found User Group has Code "+code);
        return DozerSingletonMapper.getInstance().map(entity, UserGroupDTO.class);
    }
}
