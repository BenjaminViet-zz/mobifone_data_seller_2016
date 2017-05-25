package com.benluck.vms.mobifonedataseller.session.impl;

import com.benluck.vms.mobifonedataseller.domain.PaymentEntity;
import com.benluck.vms.mobifonedataseller.session.PaymentLocalBean;

import javax.ejb.Stateless;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 5/25/17
 * Time: 23:12
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "PaymentSessionEJB")
public class PaymentSessionBean extends AbstractSessionBean<PaymentEntity, Long> implements PaymentLocalBean{
    public PaymentSessionBean() {
    }
}
