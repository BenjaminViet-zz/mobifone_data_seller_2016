package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.beanUtil.MBDReportGeneralExpenseDTOBeanUtil;
import com.benluck.vms.mobifonedataseller.core.business.MBDCostManagementLocalBean;
import com.benluck.vms.mobifonedataseller.domain.MBDCostEntity;
import com.benluck.vms.mobifonedataseller.session.MBDCostLocalBean;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/9/16
 * Time: 22:01
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "MBDCostManagementSessionEJB")
public class MBDCostManagementSessionBean implements MBDCostManagementLocalBean{

    @EJB
    private MBDCostLocalBean costService;

    public MBDCostManagementSessionBean() {
    }

    @Override
    public Object[] searchByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer firstItem, Integer reportMaxPageItems) {
        Object[] resultObject = this.costService.search4GeneralExpenseReport(properties, sortExpression, sortDirection, firstItem, reportMaxPageItems);
        List<MBDCostEntity> entityList = (List<MBDCostEntity>)resultObject[1];
        if(entityList.size() > 0){
            resultObject[1] = MBDReportGeneralExpenseDTOBeanUtil.entityList2DTOList(entityList);
        }
        return resultObject;
    }
}
