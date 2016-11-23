package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.beanUtil.MBDCodeHistoryBeanUtil;
import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.core.business.CodeHistoryManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.MBDCodeHistoryDTO;
import com.benluck.vms.mobifonedataseller.domain.MBDCodeHistoryEntity;
import com.benluck.vms.mobifonedataseller.session.CodeHistoryLocalBean;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/11/16
 * Time: 19:44
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "CodeHistoryManagementSessionEJB")
public class CodeHistoryManagementSessionBean implements CodeHistoryManagementLocalBean{

    @EJB
    private CodeHistoryLocalBean codeHistoryService;

    public CodeHistoryManagementSessionBean() {
    }

    @Override
    public Double calculateTotalPaidPackageValue(String isdn) {
        return this.codeHistoryService.calculateTotalPaidPackageValue(isdn);
    }

    @Override
    public Object[] searchPaymentHistoryByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer firstItem, Integer reportMaxPageItems) {
        Object[] resultObject = this.codeHistoryService.searchPaymentHistoryByProperties(properties, sortExpression, sortDirection, firstItem, reportMaxPageItems);
        List listObject = (List)resultObject[1];
        Object[] tmpObjectArr = null;
        List<MBDCodeHistoryDTO> dtoList = new ArrayList<MBDCodeHistoryDTO>();
        for (Object tmpObject : listObject){
            tmpObjectArr = (Object[]) tmpObject;
            MBDCodeHistoryDTO dto = new MBDCodeHistoryDTO();
            dto.setIsdn(tmpObjectArr[0] != null ? tmpObjectArr[0].toString() : "");
            dto.setName(tmpObjectArr[1] != null ? tmpObjectArr[1].toString() : "");
            dto.setTin(tmpObjectArr[2] != null ? tmpObjectArr[2].toString() : "");
            dto.setRegDateTime(tmpObjectArr[3] != null ? Timestamp.valueOf(tmpObjectArr[3].toString()) : null);
            dto.setStaDateTime(tmpObjectArr[4] != null ? Timestamp.valueOf(tmpObjectArr[4].toString()) : null);
            dtoList.add(dto);
        }
        resultObject[1] = dtoList;
        return resultObject;
    }
}
