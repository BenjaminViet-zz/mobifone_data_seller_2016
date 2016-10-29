package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.common.utils.DozerSingletonMapper;
import com.benluck.vms.mobifonedataseller.core.business.UserGroupManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.UserGroupDTO;
import com.benluck.vms.mobifonedataseller.domain.UserGroupEntity;
import com.benluck.vms.mobifonedataseller.session.UserGroupLocalBean;

import javax.ejb.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Stateless(name = "UserGroupManagementSessionEJB")
public class UserGroupManagementSessionBean implements UserGroupManagementLocalBean {
    public UserGroupManagementSessionBean() {
    }

    @EJB
    private UserGroupLocalBean userGroupLocalBean;

    @Override
    public Object[] search(Map<String, Object> properties, String sortExpression, String sortDirection, int firstItem, int maxPageItems) {
        Object[] res = userGroupLocalBean.searchByProperties(properties, sortExpression, sortDirection , firstItem , maxPageItems);
        List<UserGroupDTO> dtos = new ArrayList<UserGroupDTO>();
        for (UserGroupEntity entity : (List<UserGroupEntity>)res[1]) {
            dtos.add(DozerSingletonMapper.getInstance().map(entity, UserGroupDTO.class));
        }
        res[1] = dtos;
        return res;
    }

    @Override
    public UserGroupDTO updateItem(UserGroupDTO userGroupDTO) throws ObjectNotFoundException, DuplicateKeyException {
        UserGroupEntity dbItem = this.userGroupLocalBean.findById(userGroupDTO.getUserGroupId());
        if (dbItem == null) throw new ObjectNotFoundException("Not found user group " + userGroupDTO.getUserGroupId());
        UserGroupEntity pojo = DozerSingletonMapper.getInstance().map(userGroupDTO, UserGroupEntity.class);
        return DozerSingletonMapper.getInstance().map(this.userGroupLocalBean.update(pojo), UserGroupDTO.class);
    }

    @Override
    public UserGroupDTO addItem(UserGroupDTO userGroupDTO) throws DuplicateKeyException {
        UserGroupEntity userGroupEntity = DozerSingletonMapper.getInstance().map(userGroupDTO, UserGroupEntity.class);
        return  DozerSingletonMapper.getInstance().map(this.userGroupLocalBean.save(userGroupEntity), UserGroupDTO.class);
    }

    @Override
    public UserGroupDTO findById(Long userGroupId) throws ObjectNotFoundException {
        return  DozerSingletonMapper.getInstance().map(this.userGroupLocalBean.findById(userGroupId), UserGroupDTO.class);
    }

    @Override
    public Integer deleteItems(String[] checkList) {
        Integer res = 0;
        if (checkList != null && checkList.length > 0) {
            res = checkList.length;
            for (String id : checkList) {
                try {
                    userGroupLocalBean.delete(Long.parseLong(id));
                    res++;
                } catch (RemoveException e) {
                }
            }
        }
        return res;
    }

    @Override
    public List<UserGroupDTO> findAll() {
        List<UserGroupEntity> entityList = this.userGroupLocalBean.findAll4Access();
        List<UserGroupDTO> dtoList = new ArrayList<UserGroupDTO>();
        for (UserGroupEntity entity : entityList){
            dtoList.add(DozerSingletonMapper.getInstance().map(entity, UserGroupDTO.class));
        }
        return dtoList;
    }

    @Override
    public UserGroupDTO findByCode(String code) throws ObjectNotFoundException {
        UserGroupEntity entity = this.userGroupLocalBean.findEqualUnique("code", code);
        if (entity == null) throw new ObjectNotFoundException("Not found User Group has Code "+code);
        return DozerSingletonMapper.getInstance().map(entity, UserGroupDTO.class);
    }
}
