package com.benluck.vms.mobifonedataseller.session;

import com.benluck.vms.mobifonedataseller.domain.ActionLogEntity;
import com.benluck.vms.mobifonedataseller.domain.MoneyExchangeBranchEntity;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 3/16/15
 * Time: 11:16 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ActionLogLocalBean extends GenericSessionBean<ActionLogEntity, Long>{

    /**
     * Perform querying for action logs using native SQL.
     * @param properties pairs of key-value which mapping to related column name and its value under the table.
     * @param sortExpression expression base on relational mapping entities for sorting.
     * @param sortDirection direction of the above expression.
     * @param firstItem offset of the row index in result set from the query.
     * @param maxPageItems number of rows that will be counted for result from query.
     * @return  An array of Objects with 2 elements.
     *          The first one will be total of records for querying with these properties and the last one will be an array of Objects from Database.
     */
    Object[] search(Map<String, Object> properties, String sortExpression, String sortDirection, Integer firstItem, Integer maxPageItems);

    /**
     * Remove history of gift exchange by Ma Phieu (ticket).
     * @param ticketCode
     */
    void removeMaPhieuGiftHistory(String ticketCode);

    /**
     * Verify if the Ma Phieu (ticket) existed in the Action Log.
     * @param maPhieu
     * @return
     */
    Boolean checkLastMaPhieu(String maPhieu);

    /**
     * Verify if the Ma Phieu (ticket) existed in the Action Log.
     * @param maPhieu
     */
    void removeGiftHistory(String maPhieu);

    /**
     * Get all action logs that related to the Ma Phieu.
     * @param maPhieu
     * @return
     */
    List findByMaPhieu(String maPhieu);

    /**
     * Get data for report KHCN of the promotion "Q-Teen and Q-Student".
     * @param properties
     * @param offset
     * @param limit
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one will be total of records for querying with these properties and the last one will be an array of Objects from Database.
     */
    Object[] report4KHCN_qStudent(Map<String, Object> properties, Integer offset, Integer limit, String sortExpression, String sortDirection);
}
