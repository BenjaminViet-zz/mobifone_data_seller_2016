package com.benluck.vms.mobifonedataseller.core.business;

import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.DepartmentDTO;


import javax.ejb.DuplicateKeyException;
import javax.ejb.Local;
import javax.ejb.ObjectNotFoundException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: HauKute
 * Date: 10/9/14
 * Time: 6:29 PM
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface DepartmentManagementLocalBean {

    /**
     * Get list of department on paging by properties.
     * @param departmentDTO
     * @param sortExpression
     * @param sortDirection
     * @param firstItems
     * @param maxPageItems
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] search(DepartmentDTO departmentDTO, String sortExpression, String sortDirection, Integer firstItems ,Integer maxPageItems);

    /**
     * Perform updating department information into DB.
     * @param departmentDTO
     * @return DepartmentDTO
     * @throws ObjectNotFoundException
     * @throws DuplicateKeyException
     */
    DepartmentDTO updateItem(DepartmentDTO departmentDTO) throws ObjectNotFoundException, DuplicateKeyException;

    /**
     * Perform inserting new department
     * @param departmentDTO
     * @return
     * @throws DuplicateKeyException
     */
    DepartmentDTO addItem(DepartmentDTO departmentDTO) throws DuplicateKeyException;

    /**
     * Retrieve department by departmentId.
     * @param departmentId
     * @return DepartmentDTO
     * @throws ObjectNotFoundException
     */
    DepartmentDTO findById(Long departmentId) throws ObjectNotFoundException;

    /**
     * Fetch all departments in the promotion.
     * @return List of department.
     */
    List<DepartmentDTO> findAll();

    /**
     * Perform deleting departments by checkList.
     * @param checkList
     * @return
     */
    Integer deleteItems(String[] checkList);

    /**
     * Retrieve department by unique code.
     * @param code
     * @return DepartmentDTO
     * @throws ObjectNotFoundException
     */
    DepartmentDTO findByCode(String code) throws ObjectNotFoundException;

    /**
     * Fetch all departments that has this type.
     * @param type
     * @return A list of DepartmentDTO.
     */
    List<DepartmentDTO> findAllByType(String type);

    /**
     * Perform inserting list of department information into DB.
     * @param listDto
     */
    void importAndUpdate(List<DepartmentDTO> listDto);

    /**
     * Fetch all department which belong to the chiNhanhId
     * @param chiNhanhId
     * @return A list of DepartmentDTO
     */
    List<DepartmentDTO> findDepartmentListByChiNhanhId(Long chiNhanhId);

    /**
     * Find department information by properties.
     * @param shopCodeChiNhanhId
     * @param departmentId
     * @return DepartmentDTO
     */
    DepartmentDTO findByIdAndShopCodeId(Long shopCodeChiNhanhId, Long departmentId);

    /**
     * Search department list by properties.
     * @param branchId
     * @param code
     * @return A list of matched departments.
     */
    List<DepartmentDTO> findDepartmentListByBranchIdAndCtCode(Long branchId, String code);
}
