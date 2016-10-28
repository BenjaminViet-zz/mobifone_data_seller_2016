package com.benluck.vms.mobifonedataseller.core.business;

import javax.ejb.Local;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: vovanphuc0810
 * Date: 5/4/15
 * Time: 10:00 AM
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface BaoCaoSoDiemChoThueBaoManagementLocalBean {

    /**
     * Get data for report ScoreSubscriberReport.
     * @param properties
     * @param firstItem
     * @param maxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return  An array of Objects with 2 elements.
     *          The first one is total of records in DB, and the last one is a list of Object that comprise data for the report.
     */
    Object[] search(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection);
}
