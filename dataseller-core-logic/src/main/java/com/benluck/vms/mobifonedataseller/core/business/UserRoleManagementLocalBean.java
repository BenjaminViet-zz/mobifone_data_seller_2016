package com.benluck.vms.mobifonedataseller.core.business;


import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.RoleDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.UserRoleDTO;

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
public interface UserRoleManagementLocalBean {

    /**
     * Search list of User Role by properties.
     * @param properties
     * @param sortExpression
     * @param sortDirection
     * @param firstItem
     * @param maxPageItems
     * @return An array of Objects with 2 elements. The first one is total of matched records for this querying, the last one is a list of UserRoleDTOs.
     */
    Object[] search(Map<String, Object> properties, String sortExpression, String sortDirection, int firstItem, int maxPageItems);

    /**
     * Update UserRole information into DB by userGroupDTO.
     * @param userGroupDTO
     * @throws ObjectNotFoundException
     * @throws DuplicateKeyException
     */
    void updateItem(UserRoleDTO userGroupDTO) throws ObjectNotFoundException, DuplicateKeyException;

    /**
     * Insert new UserRole data into DB by userRoleDTO
     * @param userRoleDTO
     * @return UserRoleDTO
     * @throws DuplicateKeyException
     */
    UserRoleDTO addItem(UserRoleDTO userRoleDTO) throws DuplicateKeyException;

    /**
     * Retreive UserRole data by userRoleId.
     * @param userGroupId
     * @return UserRoleDTO
     * @throws ObjectNotFoundException
     */
    UserRoleDTO findById(Long userGroupId) throws ObjectNotFoundException;

    /**
     * Delete a list of UserRole that matched to userRoleIds in String.
     * @param checkList
     * @return
     */
    Integer deleteItems(String[] checkList);

    /**
     * Retrieve UserRole data by userRole string.
     * @param userRole
     * @return UserRoleDTO
     * @throws ObjectNotFoundException
     */
    UserRoleDTO findByUserRole(String userRole) throws ObjectNotFoundException;

    /**
     * Fetch all of UserRole in DB to a list of UserRoleDTO.
     * @return List<UserRoleDTO>
     */
    List<UserRoleDTO> findAll();

    /**
     * Find list of Role of users that has this role.
     * @param userId
     * @return List<DTO>
     * @throws ObjectNotFoundException
     */
    List<RoleDTO> findRoleOfUser(Long userId) throws ObjectNotFoundException;
}
