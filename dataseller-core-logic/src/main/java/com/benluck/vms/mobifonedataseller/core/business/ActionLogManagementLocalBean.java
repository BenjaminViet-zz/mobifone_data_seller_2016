package com.benluck.vms.mobifonedataseller.core.business;

import javax.ejb.Local;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 3/16/15
 * Time: 11:14 AM
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface ActionLogManagementLocalBean {
    /**
     * Provide API searching to get action logs on some particular table name.
     * @param properties pairs of key-value which mapping to related column name and its value under the table.
     * @param sortExpression expression base on relational mapping entities for sorting.
     * @param sortDirection direction of the above expression.
     * @param firstItem offset of the row index in result set from the query.
     * @param maxPageItems number of rows that will be counted for result from query.
     * @return array of Objects with 2 elements. The first one will be total of records for querying with these properties and the last one will be a list of POJOs from Database.
     */
    Object[] search(Map<String, Object> properties, String sortExpression, String sortDirection, Integer firstItem, Integer maxPageItems);
}
