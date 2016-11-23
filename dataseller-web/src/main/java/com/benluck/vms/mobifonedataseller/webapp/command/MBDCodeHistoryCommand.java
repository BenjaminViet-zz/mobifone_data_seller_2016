package com.benluck.vms.mobifonedataseller.webapp.command;

import com.benluck.vms.mobifonedataseller.core.dto.MBDCodeHistoryDTO;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/11/16
 * Time: 19:49
 * To change this template use File | Settings | File Templates.
 */
public class MBDCodeHistoryCommand extends AbstractCommand<MBDCodeHistoryDTO>{
    public MBDCodeHistoryCommand(){
        this.pojo = new MBDCodeHistoryDTO();
    }

    private Date regDateFrom;
    private Date regDateTo;
    private Date staDateFrom;
    private Date staDateTo;

    public Date getRegDateFrom() {
        return regDateFrom;
    }

    public void setRegDateFrom(Date regDateFrom) {
        this.regDateFrom = regDateFrom;
    }

    public Date getRegDateTo() {
        return regDateTo;
    }

    public void setRegDateTo(Date regDateTo) {
        this.regDateTo = regDateTo;
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
