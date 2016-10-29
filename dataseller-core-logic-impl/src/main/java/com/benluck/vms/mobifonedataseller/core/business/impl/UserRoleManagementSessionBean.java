package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.common.utils.DozerSingletonMapper;
import com.benluck.vms.mobifonedataseller.core.business.UserRoleManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.RoleDTO;
import com.benluck.vms.mobifonedataseller.core.dto.UserRoleDTO;
import com.benluck.vms.mobifonedataseller.domain.RoleEntity;
import com.benluck.vms.mobifonedataseller.domain.UserEntity;
import com.benluck.vms.mobifonedataseller.domain.UserRoleEntity;
import com.benluck.vms.mobifonedataseller.session.RoleLocalBean;
import com.benluck.vms.mobifonedataseller.session.UserLocalBean;
import com.benluck.vms.mobifonedataseller.session.UserRoleLocalBean;

import javax.ejb.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



@Stateless(name = "UserRoleManagementSessionEJB")
public class UserRoleManagementSessionBean implements UserRoleManagementLocalBean {
    public UserRoleManagementSessionBean() {
    }

    @EJB
    private UserRoleLocalBean userRoleLocalBean;

    @EJB
    private UserLocalBean usersLocalBean;

    @EJB
    private RoleLocalBean roleLocalBean;

    @Override
    public Object[] search(Map<String, Object> properties, String sortExpression, String sortDirection, int firstItem, int maxPageItems) {
        Object[] res = userRoleLocalBean.searchByProperties(properties, sortExpression, sortDirection , firstItem , maxPageItems);
        List<UserRoleDTO> dtos = new ArrayList<UserRoleDTO>();
        for (UserRoleEntity entity : (List<UserRoleEntity>)res[1]) {
            dtos.add(DozerSingletonMapper.getInstance().map(entity, UserRoleDTO.class));
        }
        res[1] = dtos;
        return res;
    }

    @Override
    public void updateItem(UserRoleDTO userRoleDTO) throws ObjectNotFoundException, DuplicateKeyException {
       UserEntity userEntity = usersLocalBean.findById(userRoleDTO.getUserRoleId());
        usersLocalBean.update(userEntity);
    }

    @Override
    public UserRoleDTO addItem(UserRoleDTO userRoleDTO) throws DuplicateKeyException {
        UserRoleEntity pojo = DozerSingletonMapper.getInstance().map(userRoleDTO, UserRoleEntity.class);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        return  DozerSingletonMapper.getInstance().map(this.userRoleLocalBean.save(pojo), UserRoleDTO.class);
    }

    @Override
    public UserRoleDTO findById(Long userRoleId) throws ObjectNotFoundException {
        return  DozerSingletonMapper.getInstance().map(this.userRoleLocalBean.findById(userRoleId), UserRoleDTO.class);
    }

    @Override
    public Integer deleteItems(String[] checkList) {
        Integer res = 0;
        if (checkList != null && checkList.length > 0) {
            res = checkList.length;
            for (String id : checkList) {
                try {
                    userRoleLocalBean.delete(Long.parseLong(id));
                    res++;
                } catch (RemoveException e) {
                }
            }
        }
        return res;
    }

    @Override
    public UserRoleDTO findByUserRole(String userRole) throws ObjectNotFoundException {
        UserRoleEntity entity = userRoleLocalBean.findEqualUnique("userRole", userRole);
        return DozerSingletonMapper.getInstance().map(entity, UserRoleDTO.class);
    }

    @Override
    public List<UserRoleDTO> findAll() {
        List<UserRoleEntity> res =  userRoleLocalBean.findAll();
        List<UserRoleDTO> dtos = new ArrayList<UserRoleDTO>();
        for (UserRoleEntity entity : res) {
            dtos.add(DozerSingletonMapper.getInstance().map(entity, UserRoleDTO.class));
        }
        return  dtos;
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
}
