package com.benluck.vms.mobifonedataseller.core.business;

import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.RoleDTO;

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
public interface RoleManagementLocalBean {

    /**
     * Get list of Role in DB bt properties.
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
     * Perform updating Role information to the DB.
     * @param roleDTO
     * @return RoleDTO
     * @throws ObjectNotFoundException
     * @throws DuplicateKeyException
     */
    RoleDTO updateItem(RoleDTO roleDTO) throws ObjectNotFoundException, DuplicateKeyException;

    /**
     * Perform inserting new Role to the DB.
     * @param roleDTO
     * @return RoleDTO
     * @throws DuplicateKeyException
     */
    RoleDTO addItem(RoleDTO roleDTO) throws DuplicateKeyException;

    /**
     * Retrive Role by roleId
     * @param roleId
     * @return RoleDTO
     * @throws ObjectNotFoundException
     */
    RoleDTO findById(Long roleId) throws ObjectNotFoundException;

    /**
     * Delete list of Role by an array of roleId in string.
     * @param checkList
     * @return Number of role that has deleted from DB.
     */
    Integer deleteItems(String[] checkList);

    /**
     * Fetch all Roles in DB.
     * @return A list of RoleDTO.
     */
    List<RoleDTO> findAll();
}
