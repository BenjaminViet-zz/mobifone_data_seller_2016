package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.beanUtil.MBDCodeHistoryBeanUtil;
import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.core.business.CodeHistoryManagementLocalBean;
import com.benluck.vms.mobifonedataseller.domain.MBDCodeHistoryEntity;
import com.benluck.vms.mobifonedataseller.session.CodeHistoryLocalBean;

import javax.ejb.EJB;
import javax.ejb.Stateless;
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
}
