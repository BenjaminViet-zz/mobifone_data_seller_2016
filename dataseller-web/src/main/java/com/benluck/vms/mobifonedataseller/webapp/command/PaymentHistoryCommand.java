package com.benluck.vms.mobifonedataseller.webapp.command;

import com.benluck.vms.mobifonedataseller.core.dto.PaymentHistoryDTO;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 5/27/17
 * Time: 18:31
 * To change this template use File | Settings | File Templates.
 */
public class PaymentHistoryCommand extends AbstractCommand<PaymentHistoryDTO>{
    public PaymentHistoryCommand(){
        this.pojo = new PaymentHistoryDTO();
    }

    private Long khdnId;
    private Long orderId;
    private Date paymentDate;

    public Long getKhdnId() {
        return khdnId;
    }

    public void setKhdnId(Long khdnId) {
        this.khdnId = khdnId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }
}
