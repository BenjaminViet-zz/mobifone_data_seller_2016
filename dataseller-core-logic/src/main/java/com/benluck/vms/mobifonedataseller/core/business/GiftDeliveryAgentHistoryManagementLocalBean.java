package com.benluck.vms.mobifonedataseller.core.business;

import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.GiftDeliveryAgentHistoryDTO;

import javax.ejb.DuplicateKeyException;
import javax.ejb.Local;
import javax.ejb.ObjectNotFoundException;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/15/14
 * Time: 11:15 AM
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface GiftDeliveryAgentHistoryManagementLocalBean {

    /**
     * Perform inputting items to the stock and write to Action Log for tracking.
     * @param dto
     * @throws ObjectNotFoundException
     * @throws DuplicateKeyException
     */
    void inputStock(GiftDeliveryAgentHistoryDTO dto) throws ObjectNotFoundException, DuplicateKeyException;

    /**
     * Perform updating quantity of items in stock of the agency.
     * @param nguoiXuatKhoId
     * @param departmentId
     * @param quantity
     * @throws Exception
     */
    void getFromStock(Long nguoiXuatKhoId, Long departmentId, Integer quantity) throws Exception;

    /**
     * Search items in stock of the agency in the promotion "Tich Diem Cuoc Goi Nhan Voucher".
     * @param agentId
     * @param dto
     * @param offset
     * @param limit
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] search_tdcg(Long agentId, GiftDeliveryAgentHistoryDTO dto, Integer offset, Integer limit, String sortExpression, String sortDirection);

    /**
     * Search items in stock of the agency in the promotion "Q-Teen and Q-Student".
     * @param departmentId
     * @param pojo
     * @param firstItem
     * @param maxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] search_qStudent(Long departmentId, GiftDeliveryAgentHistoryDTO pojo, int firstItem, int maxPageItems, String sortExpression, String sortDirection);
}
