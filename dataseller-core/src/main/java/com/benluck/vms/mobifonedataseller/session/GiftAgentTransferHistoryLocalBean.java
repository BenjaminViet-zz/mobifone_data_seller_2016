package com.benluck.vms.mobifonedataseller.session;

import com.benluck.vms.mobifonedataseller.domain.GiftAgentTransferHistoryEntity;

import java.sql.Timestamp;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 11/5/14
 * Time: 5:39 PM
 * To change this template use File | Settings | File Templates.
 */
public interface GiftAgentTransferHistoryLocalBean extends GenericSessionBean<GiftAgentTransferHistoryEntity, Long>{

    /**
     * Search history of input or get items from stock of agency.
     * @param departmentId
     * @param fromDateTime
     * @param toDateTime
     * @param firstItem
     * @param maxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] search(Long departmentId, Timestamp fromDateTime, Timestamp toDateTime, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection);
}
