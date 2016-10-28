package com.benluck.vms.mobifonedataseller.core.business;

import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.RoleDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.UserDTO;

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
     * Retrieve user data by username.
     * @param username
     * @return UserDTO
     * @throws ObjectNotFoundException
     */
    UserDTO findByUsername(String username) throws ObjectNotFoundException;

    /**
     * Retrieve user data by userId.
     * @param userId
     * @return
     * @throws ObjectNotFoundException
     */
    UserDTO findById(Long userId) throws ObjectNotFoundException;

    /**
     * Insert new user data into DB.
     * @param userDTO
     * @return UserDTO
     * @throws DuplicateKeyException
     * @throws ObjectNotFoundException
     */
    UserDTO addItem(UserDTO userDTO) throws DuplicateKeyException, ObjectNotFoundException;

    /**
     * Update user information.
     * @param UserDTO
     * @return UserDTO
     * @throws ObjectNotFoundException
     * @throws DuplicateKeyException
     */
    UserDTO updateItem(UserDTO UserDTO) throws ObjectNotFoundException, DuplicateKeyException;

    /**
     * Delete list of user by list of userId in string.
     * @param checkList
     * @return UserDTO
     * @throws RemoveException
     */
    Integer deleted(String checkList[]) throws RemoveException;

    /**
     * Get list of user by properties.
     * @param properties
     * @param sortExpression
     * @param sortDirection
     * @param firstItem
     * @param maxPageItems
     * @return Number of user that have been deleted from DB.
     */
    Object [] search(Map<String, Object> properties, String sortExpression, String sortDirection, Integer firstItem, Integer maxPageItems);

    /**
     * Retrieve user data by unique code.
     * @param code
     * @return UserDTO
     * @throws ObjectNotFoundException
     */
    UserDTO findByCode(String code) throws ObjectNotFoundException;

    /**
     * Retrieve user data by phoneNumber
     * @param phoneNumber
     * @return
     * @throws ObjectNotFoundException
     */
    UserDTO findByMobileNumber(String phoneNumber) throws ObjectNotFoundException;

    /**
     * Retrieve user data by email.
     * @param email
     * @return
     * @throws ObjectNotFoundException
     */
    UserDTO findByEmail(String email) throws ObjectNotFoundException;

    /**
     * Update user profile.
     * @param UserDTO
     * @throws ObjectNotFoundException
     * @throws DuplicateKeyException
     */
    void updateProfile(UserDTO UserDTO) throws ObjectNotFoundException, DuplicateKeyException;

    /**
     * Get a list of matched user that have name matched with name filter.
     * @param name
     * @return A list of userDTO for matched user records.
     * @throws ObjectNotFoundException
     */
    List<UserDTO> searchByName(String name) throws ObjectNotFoundException;

    /**
     * Get a list of user by properties.
     * @param userId
     * @return A list of RoleDTO that matched to filter.
     * @throws ObjectNotFoundException
     */
    List<RoleDTO> findRoleOfUser(Long userId) throws ObjectNotFoundException;

    /**
     * Perform save data from importing file.
     * @param listUsers
     * @return An array of Object with 2 elements. The first one is total records and the last one is a list of UserDTO inserted into DB.
     */
    Object[] saveImport(List<UserDTO> listUsers);

    /**
     *
     * @param dto
     * @param serverIP
     * @param userGroupCode
     * @throws ObjectNotFoundException
     * @throws DuplicateKeyException
     */
    void saveUserInfoAndGenerateVerifyLoginCode(UserDTO dto, String serverIP, String userGroupCode) throws ObjectNotFoundException, DuplicateKeyException;

    /**
     * Delete user from DB by userId.
     * @param userId
     * @throws RemoveException
     */
    void deleteById(Long userId) throws RemoveException;

    /**
     * Verify if the user related to other process before deleting.
     * @param userId
     * @return
     */
    Boolean checkIfHasRelatedToData(Long userId);
}
