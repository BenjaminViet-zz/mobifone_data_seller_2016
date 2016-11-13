package com.benluck.vms.mobifonedataseller.core.business;

import com.benluck.vms.mobifonedataseller.core.dto.MBDCostInfoDTO;

import javax.ejb.DuplicateKeyException;
import javax.ejb.Local;
import javax.ejb.ObjectNotFoundException;
import java.sql.Timestamp;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/9/16
 * Time: 22:01
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface MBDCostManagementLocalBean {

    /**
     * Search genral report data
     * @param properties
     * @param sortExpression
     * @param sortDirection
     * @param firstItem
     * @param reportMaxPageItems
     * @return
     */
    Object[] searchGeneralReportDataByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer firstItem, Integer reportMaxPageItems);

    /**
     * Search detail report data
     * @param properties
     * @param sortExpression
     * @param sortDirection
     * @param firstItem
     * @param reportMaxPageItems
     * @return
     */
    Object[] searchDetailReportDataByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer firstItem, Integer reportMaxPageItems);

    void updatePayment(String[] checkList, Timestamp paymentDate) throws ObjectNotFoundException, DuplicateKeyException;

    MBDCostInfoDTO findById(Long paymentId) throws ObjectNotFoundException;

    Object[] findPaymentListByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer firstItem, Integer reportMaxPageItems);
}
