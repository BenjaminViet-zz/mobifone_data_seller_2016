package com.benluck.vms.mobifonedataseller.session;

import com.benluck.vms.mobifonedataseller.domain.MoneyExchangeBranchHistoryEntity;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/26/14
 * Time: 3:43 PM
 * To change this template use File | Settings | File Templates.
 */
public interface MoneyExchangeBranchHistoryLocalBean extends GenericSessionBean<MoneyExchangeBranchHistoryEntity, Long>{

    /**
     * Get history of payment at Agency.
     * @param properties
     * @param firstItem
     * @param maxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] searchPaymentHistoryAtAgency(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection);
}
