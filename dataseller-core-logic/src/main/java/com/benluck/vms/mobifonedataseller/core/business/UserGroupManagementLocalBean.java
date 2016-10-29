package com.benluck.vms.mobifonedataseller.core.business;

import com.benluck.vms.mobifonedataseller.core.dto.UserGroupDTO;

import javax.ejb.DuplicateKeyException;
import javax.ejb.Local;
import javax.ejb.ObjectNotFoundException;
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


//    ============

    /**
     * Get list of UserGroup by properties.
     * @param properties
     * @param sortExpression
     * @param sortDirection
     * @param firstItem
     * @param maxPageItems
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] search(Map<String, Object> properties, String sortExpression, String sortDirection, int firstItem, int maxPageItems);

    /**
     * Perform updating details of user group.
     * @param userGroupDTO
     * @return UserGroupDTO
     * @throws ObjectNotFoundException
     * @throws DuplicateKeyException
     */
    UserGroupDTO updateItem(UserGroupDTO userGroupDTO) throws ObjectNotFoundException, DuplicateKeyException;

    /**
     * Perform inserting new UserGroup into DB.
     * @param userGroupDTO
     * @return UserGroupDTO
     * @throws DuplicateKeyException
     */
    UserGroupDTO addItem(UserGroupDTO userGroupDTO) throws DuplicateKeyException;

    /**
     * Retrieve UserGroup by Id.
     * @param userGroupId
     * @return UserGroupDTO
     * @throws ObjectNotFoundException
     */
    UserGroupDTO findById(Long userGroupId) throws ObjectNotFoundException;

    /**
     * Delete list of UserGroup by list of UserGroupId in string.
     * @param checkList
     * @return
     */
    Integer deleteItems(String[] checkList);

    /**
     * Retrive UserGroupDTO by unique code.
     * @param code
     * @return UserGroupDTO.
     * @throws ObjectNotFoundException
     */
    UserGroupDTO findByCode(String code) throws ObjectNotFoundException;
}
