package com.benluck.vms.mobifonedataseller.core.business.impl;


import com.benluck.vms.mobifonedataseller.common.utils.DozerSingletonMapper;
import com.benluck.vms.mobifonedataseller.core.business.RoleManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.RoleDTO;
import com.benluck.vms.mobifonedataseller.domain.RoleEntity;
import com.benluck.vms.mobifonedataseller.session.RoleLocalBean;

import javax.ejb.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;




@Stateless(name = "RoleManagementSessionEJB")
public class RoleManagementSessionBean implements RoleManagementLocalBean {
    public RoleManagementSessionBean() {
    }

    @EJB
    private RoleLocalBean roleLocalBean;

    @Override
    public Object[] search(Map<String, Object> properties, String sortExpression, String sortDirection, int firstItem, int maxPageItems) {
        Object[] res = roleLocalBean.searchByProperties(properties, sortExpression, sortDirection , firstItem , maxPageItems);
        List<RoleDTO> dtos = new ArrayList<RoleDTO>();
        for (RoleEntity entity : (List<RoleEntity>)res[1]) {
            dtos.add(DozerSingletonMapper.getInstance().map(entity, RoleDTO.class));
        }
        res[1] = dtos;
        return res;
    }

    @Override
    public RoleDTO updateItem(RoleDTO roleDTO) throws ObjectNotFoundException, DuplicateKeyException {
        RoleEntity dbItem = this.roleLocalBean.findById(roleDTO.getRoleId());
        if (dbItem == null) throw new ObjectNotFoundException("Not found role " + roleDTO.getRoleId());
        RoleEntity pojo = DozerSingletonMapper.getInstance().map(roleDTO, RoleEntity.class);
        return DozerSingletonMapper.getInstance().map(this.roleLocalBean.update(pojo), RoleDTO.class);
    }

    @Override
    public RoleDTO addItem(RoleDTO roleDTO) throws DuplicateKeyException {
        RoleEntity pojo = DozerSingletonMapper.getInstance().map(roleDTO, RoleEntity.class);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        return  DozerSingletonMapper.getInstance().map(this.roleLocalBean.save(pojo), RoleDTO.class);
    }

    @Override
    public RoleDTO findById(Long roleId) throws ObjectNotFoundException {
        return  DozerSingletonMapper.getInstance().map(this.roleLocalBean.findById(roleId), RoleDTO.class);
    }

    @Override
    public Integer deleteItems(String[] checkList) {
        Integer res = 0;
        if (checkList != null && checkList.length > 0) {
            res = checkList.length;
            for (String id : checkList) {
                try {
                    roleLocalBean.delete(Long.parseLong(id));
                    res++;
                } catch (RemoveException e) {
                }
            }
        }
        return res;
    }

    @Override
    public List<RoleDTO> findAll() {
        List<RoleEntity> res =  roleLocalBean.findAll();
        List<RoleDTO> dtos = new ArrayList<RoleDTO>();
        for (RoleEntity entity : res) {
            dtos.add(DozerSingletonMapper.getInstance().map(entity, RoleDTO.class));
        }
        return  dtos;
    }



}
