package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.common.utils.DozerSingletonMapper;
import com.benluck.vms.mobifonedataseller.core.business.UserGroupManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.UserGroupDTO;
import com.benluck.vms.mobifonedataseller.domain.VmsUserGroupEntity;
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
        for (VmsUserGroupEntity entity : (List<VmsUserGroupEntity>)res[1]) {
            dtos.add(DozerSingletonMapper.getInstance().map(entity, UserGroupDTO.class));
        }
        res[1] = dtos;
        return res;
    }

    @Override
    public UserGroupDTO updateItem(UserGroupDTO userGroupDTO) throws ObjectNotFoundException, DuplicateKeyException {
        VmsUserGroupEntity dbItem = this.userGroupLocalBean.findById(userGroupDTO.getUserGroupId());
        if (dbItem == null) throw new ObjectNotFoundException("Not found user group " + userGroupDTO.getUserGroupId());
        VmsUserGroupEntity pojo = DozerSingletonMapper.getInstance().map(userGroupDTO, VmsUserGroupEntity.class);
        pojo.setModifiedDate(new Timestamp(System.currentTimeMillis()));
        pojo.setCreatedDate(dbItem.getCreatedDate());
        return DozerSingletonMapper.getInstance().map(this.userGroupLocalBean.update(pojo), UserGroupDTO.class);
    }

    @Override
    public UserGroupDTO addItem(UserGroupDTO userGroupDTO) throws DuplicateKeyException {
        VmsUserGroupEntity pojo = DozerSingletonMapper.getInstance().map(userGroupDTO, VmsUserGroupEntity.class);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        pojo.setCreatedDate(now);
        pojo.setModifiedDate(now);
        return  DozerSingletonMapper.getInstance().map(this.userGroupLocalBean.save(pojo), UserGroupDTO.class);
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
        List<UserGroupDTO> listResult = new ArrayList<UserGroupDTO>();
        List<VmsUserGroupEntity> entities = this.userGroupLocalBean.findAll();
        for (VmsUserGroupEntity entity : entities){
            UserGroupDTO userGroupDTO = DozerSingletonMapper.getInstance().map(entity, UserGroupDTO.class);
            listResult.add(userGroupDTO);
        }
        return listResult;
    }

    @Override
    public List<UserGroupDTO> findAll4Access() {
        List<VmsUserGroupEntity> entityList = this.userGroupLocalBean.findAll4Access();
        List<UserGroupDTO> dtoList = new ArrayList<UserGroupDTO>();
        for (VmsUserGroupEntity entity : entityList){
            dtoList.add(DozerSingletonMapper.getInstance().map(entity, UserGroupDTO.class));
        }
        return dtoList;
    }

    @Override
    public UserGroupDTO findByCode(String code) throws ObjectNotFoundException {
        VmsUserGroupEntity entity = this.userGroupLocalBean.findEqualUnique("code", code);
        if (entity == null) throw new ObjectNotFoundException("Not found User Group has Code "+code);
        return DozerSingletonMapper.getInstance().map(entity, UserGroupDTO.class);
    }
}
