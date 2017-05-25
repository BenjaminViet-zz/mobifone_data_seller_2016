package com.benluck.vms.mobifonedataseller.webapp.command;

import com.benluck.vms.mobifonedataseller.core.dto.PaymentDTO;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 5/25/17
 * Time: 23:36
 * To change this template use File | Settings | File Templates.
 */
public class PaymentCommand extends AbstractCommand<PaymentDTO>{
    public PaymentCommand(){
        this.pojo = new PaymentDTO();
    }

    private Date paymentDate;

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }
}
