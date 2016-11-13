package com.benluck.vms.mobifonedataseller.webapp.command;

import com.benluck.vms.mobifonedataseller.core.dto.MBDCostInfoDTO;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/13/16
 * Time: 00:08
 * To change this template use File | Settings | File Templates.
 */
public class MBDCostCommand extends AbstractCommand<MBDCostInfoDTO>{
    public MBDCostCommand(){
        this.pojo = new MBDCostInfoDTO();
    }

    private Date paymentDate;
    private Date staDateFrom;
    private Date staDateTo;

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Date getStaDateFrom() {
        return staDateFrom;
    }

    public void setStaDateFrom(Date staDateFrom) {
        this.staDateFrom = staDateFrom;
    }

    public Date getStaDateTo() {
        return staDateTo;
    }

    public void setStaDateTo(Date staDateTo) {
        this.staDateTo = staDateTo;
    }
}
