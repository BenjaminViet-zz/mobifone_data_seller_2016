package com.benluck.vms.mobifonedataseller.core.business;

import javax.ejb.Local;
import java.sql.Timestamp;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 11/5/14
 * Time: 5:36 PM
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface GiftAgentTransferHistoryManagementLocalBean {
    /**
     * Fetch for CT tich dien cuoc goi
     * @param departmentId
     * @param fromDateTime
     * @param toDateTime
     * @param firstItem
     * @param maxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return
     */
    Object[] search_tdcg(Long departmentId, Timestamp fromDateTime, Timestamp toDateTime, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection);

    /**
     * Fetch for CT tich diem Q-Student Q-Teen
     * @param departmentId
     * @param fromDateTime
     * @param toDateTime
     * @param firstItem
     * @param maxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return
     */
    Object[] search_qStudent(Long departmentId, Timestamp fromDateTime, Timestamp toDateTime, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection);
}
