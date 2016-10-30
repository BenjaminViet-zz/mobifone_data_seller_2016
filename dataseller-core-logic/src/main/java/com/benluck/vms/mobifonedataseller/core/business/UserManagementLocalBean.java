package com.benluck.vms.mobifonedataseller.core.business;

import com.benluck.vms.mobifonedataseller.core.dto.PermissionDTO;
import com.benluck.vms.mobifonedataseller.core.dto.UserDTO;

import javax.ejb.DuplicateKeyException;
import javax.ejb.ObjectNotFoundException;
import javax.ejb.RemoveException;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: benluck
 * Date: 10/4/14
 * Time: 3:54 PM
 * To change this template use File | Settings | File Templates.
 */
public interface UserManagementLocalBean {
    /**
     * Find with lower userName format
     * @param username
     * @return
     * @throws ObjectNotFoundException
     */
    UserDTO findByUsername(String username) throws ObjectNotFoundException;

    UserDTO findById(Long userId) throws ObjectNotFoundException;

    UserDTO findEqualUnique(String key, String value) throws ObjectNotFoundException;

    Object[] searchByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer offset, Integer limitItems, String whereClause);

    List<PermissionDTO> loadPermissionsByUserId(Long userId);

    UserDTO updateItem(UserDTO pojo) throws DuplicateKeyException, ObjectNotFoundException;

    void deleteItemById(Long userId) throws RemoveException;

    UserDTO addItem(UserDTO dto) throws DuplicateKeyException;

    void updatePasswordUserLDAP(String userName, String rawPassword) throws ObjectNotFoundException, DuplicateKeyException;
}
