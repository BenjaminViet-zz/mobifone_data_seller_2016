package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.core.business.PaymentHistoryManagementLocalBean;
import com.benluck.vms.mobifonedataseller.session.PaymentHistoryLocalBean;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 5/25/17
 * Time: 23:28
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "PaymentHistoryManagementSessionEJB")
public class PaymentHistoryManagementSessionBean implements PaymentHistoryManagementLocalBean{

    @EJB
    private PaymentHistoryLocalBean paymentHistoryService;

    public PaymentHistoryManagementSessionBean() {
    }
}
