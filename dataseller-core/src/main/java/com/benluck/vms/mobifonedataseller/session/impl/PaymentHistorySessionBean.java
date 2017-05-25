package com.benluck.vms.mobifonedataseller.session.impl;

import com.benluck.vms.mobifonedataseller.domain.PaymentHistoryEntity;
import com.benluck.vms.mobifonedataseller.session.PaymentHistoryLocalBean;

import javax.ejb.Stateless;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 5/25/17
 * Time: 23:12
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "PaymentHistorySessionEJB")
public class PaymentHistorySessionBean extends AbstractSessionBean<PaymentHistoryEntity, Long> implements PaymentHistoryLocalBean{
    public PaymentHistorySessionBean() {
    }

    @Override
    public void deleteByPaymentId(Long paymentId) {
        entityManager.createQuery("DELETE FROM PaymentHistoryEntity WHERE payment.paymentId = :paymentId").setParameter("paymentId", paymentId).executeUpdate();
    }
}
