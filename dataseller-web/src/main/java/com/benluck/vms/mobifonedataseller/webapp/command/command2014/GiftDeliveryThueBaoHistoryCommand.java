package com.benluck.vms.mobifonedataseller.webapp.command.command2014;

import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.GiftDeliveryThueBaoHistoryDTO;
import com.benluck.vms.mobifonedataseller.webapp.command.AbstractCommand;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/17/14
 * Time: 10:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class GiftDeliveryThueBaoHistoryCommand extends AbstractCommand<GiftDeliveryThueBaoHistoryDTO> {
    public GiftDeliveryThueBaoHistoryCommand(){
        this.pojo = new GiftDeliveryThueBaoHistoryDTO();
    }

    private Date fromDate;
    private Date toDate;

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }
}
