package com.benluck.vms.mobifonedataseller.core.business;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: HP
 * Date: 2/11/15
 * Time: 5:14 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ThueBaoPTM_KetQuaThucHienManagementLocalBean {

    /**
     * Search data for registration details by properties.
     * @param properties
     * @param firstItem
     * @param maxPageItems
     * @param sortExpression
     * @param sortDirection
     * @return
     */
    Object[] search(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection);
}
