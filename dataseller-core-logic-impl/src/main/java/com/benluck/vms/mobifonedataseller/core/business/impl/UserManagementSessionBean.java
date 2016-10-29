package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.security.DesEncrypterUtils;
import com.benluck.vms.mobifonedataseller.common.utils.Config;
import com.benluck.vms.mobifonedataseller.common.utils.DozerSingletonMapper;
import com.benluck.vms.mobifonedataseller.core.business.UserManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.RoleDTO;
import com.benluck.vms.mobifonedataseller.core.dto.UserDTO;
import com.benluck.vms.mobifonedataseller.core.dto.UserGroupDTO;
import com.benluck.vms.mobifonedataseller.domain.*;
import com.benluck.vms.mobifonedataseller.session.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import javax.ejb.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;


@javax.ejb.Stateless(name = "UserManagementSessionEJB")
public class UserManagementSessionBean implements UserManagementLocalBean{
    @EJB
    private UserLocalBean usersLocalBean;
    @EJB
    private UserGroupLocalBean userGroupLocalBean;

    public UserManagementSessionBean() {
    }

    @Override
    public UserDTO findByUsername(String username) throws ObjectNotFoundException {
        UserEntity entity = usersLocalBean.findByUserName(username);
        return DozerSingletonMapper.getInstance().map(entity, UserDTO.class);
    }

    @Override
    public UserDTO findById(Long userId) throws ObjectNotFoundException {
        UserEntity entity = usersLocalBean.findById(userId);
        if (entity == null){
            throw new ObjectNotFoundException("Not found user  "+ userId);
        }
        return  DozerSingletonMapper.getInstance().map(entity, UserDTO.class);
    }

    @Override
    public UserDTO addItem(UserDTO userDTO) throws DuplicateKeyException, ObjectNotFoundException {
        try {
            UserEntity entity = new UserEntity();
            entity.setUserName(userDTO.getUserName());
            entity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
            entity.setDisplayName(userDTO.getDisplayName());
            entity.setPassword(DesEncrypterUtils.getInstance().encrypt(userDTO.getPassword()));
            entity.setStatus(userDTO.getStatus());

            UserGroupEntity VmsUserGroupEntity = new UserGroupEntity();
            VmsUserGroupEntity.setUserGroupId(userDTO.getUserGroup().getUserGroupId());
            entity.setUserGroup(VmsUserGroupEntity);

            entity = this.usersLocalBean.save(entity);
            return DozerSingletonMapper.getInstance().map(entity, UserDTO.class);
        }catch ( DuplicateKeyException de){
            throw new DuplicateKeyException("Can not insert User");
        }
    }

    @Override
    public UserDTO updateItem(UserDTO dto) throws ObjectNotFoundException, DuplicateKeyException {
        UserEntity entity = this.usersLocalBean.findById(dto.getUserId());
        if (entity == null){
            throw new ObjectNotFoundException("Not found user "+ dto.getUserId());
        }

        UserGroupEntity VmsUserGroupEntity = new UserGroupEntity();
        VmsUserGroupEntity.setUserGroupId(dto.getUserGroup().getUserGroupId());
        entity.setUserGroup(VmsUserGroupEntity);

        entity.setPassword(DesEncrypterUtils.getInstance().encrypt(dto.getPassword()));
        entity.setDisplayName(dto.getDisplayName());
        entity.setStatus(dto.getStatus());
        return DozerSingletonMapper.getInstance().map(usersLocalBean.update(entity), UserDTO.class);
    }

    @Override
    public Integer deleted(String checkList[]) throws RemoveException {
        Integer res = 0;
        if (checkList!= null && checkList.length > 0){
            for (String id : checkList){
                try{
                    this.usersLocalBean.delete(Long.parseLong(id));
                    res ++;
                }catch (RemoveException re){
                    throw new RemoveException("Has errors");
                }

            }
        }
        return res;
    }

    @Override
    public Object[] search(Map<String, Object> properties, String sortExpression, String sortDirection, Integer firstItem, Integer maxPageItems) {
        Object[] result = this.usersLocalBean.findListUser(properties, sortExpression, sortDirection, firstItem, maxPageItems);
        List resultSet = (List)result[1];
        List<UserDTO> listUserDTO = new ArrayList<>();
        for (Object object : resultSet){
            Object[] tmpArr = (Object[])object;
            UserDTO dto = new UserDTO();
            dto.setUserId(Long.valueOf(tmpArr[0].toString()));
            dto.setUserName(tmpArr[1].toString());
            dto.setDisplayName(tmpArr[2] != null ? tmpArr[2].toString() : null);

            dto.setStatus(Integer.valueOf(tmpArr[6].toString()));

            UserGroupDTO userGroupDTO = new UserGroupDTO();
            userGroupDTO.setUserGroupId(Long.valueOf(tmpArr[7].toString()));
            userGroupDTO.setName(tmpArr[8].toString());
            dto.setUserGroup(userGroupDTO);

            listUserDTO.add(dto);
        }
        result[1] =   listUserDTO;
        return result;
    }

    @Override
    public UserDTO findByCode(String code) throws ObjectNotFoundException {
        UserEntity dbItem = this.usersLocalBean.findByUserName(code);
        if (dbItem == null ) throw new ObjectNotFoundException("Not found User "+ code);
        return DozerSingletonMapper.getInstance().map(dbItem, UserDTO.class);
    }

    @Override
    public void updateProfile(UserDTO dto) throws ObjectNotFoundException, DuplicateKeyException {
        UserEntity entity = this.usersLocalBean.findById(dto.getUserId());
        if (entity == null){
            throw new ObjectNotFoundException("Not found user "+ dto.getUserId());
        }

        entity.setUserName(dto.getUserName());
        entity.setPassword(DesEncrypterUtils.getInstance().encrypt(dto.getPassword()));
        entity.setDisplayName(dto.getDisplayName());
        entity.setStatus(dto.getStatus());
        entity = usersLocalBean.update(entity);
    }

    @Override
    public List<UserDTO> searchByName(String name) throws ObjectNotFoundException {
        List<UserDTO> resultList = new ArrayList<UserDTO>();
        List<UserEntity> entities = this.usersLocalBean.findByName("fullname",name);
        for (UserEntity entity : entities){
            UserDTO UserDTO = DozerSingletonMapper.getInstance().map(entity, UserDTO.class);
            resultList.add(UserDTO);
        }
        return resultList;
    }

    @Override
    public List<RoleDTO> findRoleOfUser(Long userId) throws ObjectNotFoundException {
        UserEntity userEntity = usersLocalBean.findById(userId);
        List<RoleDTO> listResult = new ArrayList<RoleDTO>();
        if(userEntity.getRoles() != null)
        {
            if(userEntity.getRoles().size() > 0){
                for(RoleEntity roleEntity: userEntity.getRoles())
                {
                    RoleDTO roleDTO = DozerSingletonMapper.getInstance().map(roleEntity, RoleDTO.class);
                    listResult.add(roleDTO);
                }
            }
        }
        return listResult;
    }

    @Override
    public Object[] saveImport(List<UserDTO> listUsers) {
        int createCount = 0;
        int updateCount = 0;
        int errorCount = 0 ;
        for(UserDTO userDTO : listUsers){
            boolean flagUpdate = true;
            UserEntity userEntity = new UserEntity();
            try{
                userEntity = this.usersLocalBean.findByUserName(userDTO.getUserName());
                userEntity.setLastModified(new Timestamp(System.currentTimeMillis()));
            }catch (Exception exception){
                userEntity = new UserEntity();
                flagUpdate = false;
                userEntity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
            }
            userEntity.setUserName(userDTO.getUserName());
            userEntity.setDisplayName(userDTO.getDisplayName());
            userEntity.setPassword(DesEncrypterUtils.getInstance().encrypt("123456"));
            userEntity.setStatus(1);
            try{
                if(flagUpdate){
                    updateCount++;
                    this.usersLocalBean.update(userEntity);
                }else{
                    userEntity.setUserGroup(this.userGroupLocalBean.findEqualUnique("code", "NHANVIEN"));
                    this.usersLocalBean.save(userEntity);
                    createCount++;
                }
            }catch (Exception e){
                errorCount ++;
            }
        }
        return new Object[]{createCount, updateCount, errorCount};
    }

    @Override
    public void deleteById(Long userId) throws RemoveException{
        this.usersLocalBean.delete(userId);
    }
}
