package com.benluck.vms.mobifonedataseller.core.business;

import com.benluck.vms.mobifonedataseller.core.dto.UserGroupDTO;

import javax.ejb.DuplicateKeyException;
import javax.ejb.Local;
import javax.ejb.ObjectNotFoundException;
import javax.ejb.RemoveException;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: benluck
 * Date: 2/19/14
 * Time: 9:06 AM
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface UserGroupManagementLocalBean {

    List<UserGroupDTO> findAll();

    Object[] searchByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer offset, Integer limitItems);

    void deleteItem(Long userGroupId) throws RemoveException;

    void updateItem(UserGroupDTO pojo, String[] permissionIdArr) throws ObjectNotFoundException, DuplicateKeyException;

    void addItem(UserGroupDTO pojo, String[] permissionIdArr) throws DuplicateKeyException;

    UserGroupDTO findAndFetchPermissionListById(Long userGroupId) throws ObjectNotFoundException;

    UserGroupDTO findByCode(String code) throws ObjectNotFoundException;
}
