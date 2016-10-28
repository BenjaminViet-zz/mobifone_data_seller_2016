package com.benluck.vms.mobifonedataseller.webapp.command.command2014;

import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.GiftDeliveryAgentHistoryDTO;
import com.benluck.vms.mobifonedataseller.webapp.command.AbstractCommand;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/15/14
 * Time: 8:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class GiftDeliveryAgentHistoryCommand extends AbstractCommand<GiftDeliveryAgentHistoryDTO> {
    public GiftDeliveryAgentHistoryCommand(){
        this.pojo = new GiftDeliveryAgentHistoryDTO();
    }

    private Date fromDate;
    private Date toDate;
    private Long chiNhanhId;
    private Long departmentId;
    private String message;

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

    public Long getChiNhanhId() {
        return chiNhanhId;
    }

    public void setChiNhanhId(Long chiNhanhId) {
        this.chiNhanhId = chiNhanhId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
