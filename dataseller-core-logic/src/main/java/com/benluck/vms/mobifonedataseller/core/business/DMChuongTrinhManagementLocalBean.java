package com.benluck.vms.mobifonedataseller.core.business;

import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.PromotionDTO;

import javax.ejb.DuplicateKeyException;
import javax.ejb.Local;
import javax.ejb.ObjectNotFoundException;
import javax.ejb.RemoveException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 3/2/15
 * Time: 11:09 PM
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface DMChuongTrinhManagementLocalBean {

    /**
     * Fetch list of promotions in the database.
     * @param firstItem
     * @param maxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] search(Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection);

    /**
     * Fetch all promotions in DB.
     * @return A list of PromotionDTO.
     */
    List <PromotionDTO> findAll();

    /**
     * Perform inserting new promotion into DB.
     * @param dto
     * @throws DuplicateKeyException
     */
    void addItem(PromotionDTO dto) throws DuplicateKeyException;

    /**
     * Perform updating promotion information to the DB.
     * @param dto
     * @throws ObjectNotFoundException
     * @throws DuplicateKeyException
     */
    void updateItem(PromotionDTO dto) throws ObjectNotFoundException, DuplicateKeyException;

    /**
     * Retrieve promotion by unique code.
     * @param code
     * @return PromotionDTO
     * @throws ObjectNotFoundException
     */
    PromotionDTO findByCode(String code)throws ObjectNotFoundException;

    /**
     * Reyrieve promotion by chuongTrinhId.
     * @param chuongTrinhId
     * @return
     * @throws ObjectNotFoundException
     */
    PromotionDTO findById(Long chuongTrinhId) throws ObjectNotFoundException;

    /**
     * Perform deleting promotion by promotionId
     * @param promotionId
     * @throws RemoveException
     */
    void deleteItem(Long promotionId) throws RemoveException;
}
