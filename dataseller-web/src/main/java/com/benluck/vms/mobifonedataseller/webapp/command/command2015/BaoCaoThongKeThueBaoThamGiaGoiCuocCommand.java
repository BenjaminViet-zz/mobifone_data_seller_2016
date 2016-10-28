package com.benluck.vms.mobifonedataseller.webapp.command.command2015;

import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015.BaoCaoThongKeThueBaoThamGiaGoiCuocDTO;
import com.benluck.vms.mobifonedataseller.webapp.command.AbstractCommand;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: HP
 * Date: 2/26/15
 * Time: 2:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class BaoCaoThongKeThueBaoThamGiaGoiCuocCommand extends AbstractCommand<BaoCaoThongKeThueBaoThamGiaGoiCuocDTO> {
    public BaoCaoThongKeThueBaoThamGiaGoiCuocCommand(){
        this.pojo = new BaoCaoThongKeThueBaoThamGiaGoiCuocDTO();
    }

    private Date fromDate;
    private Date toDate;
    private Date fromTransDate;
    private Date toTransDate;



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

    public Date getFromTransDate() {
        return fromTransDate;
    }

    public void setFromTransDate(Date fromTransDate) {
        this.fromTransDate = fromTransDate;
    }

    public Date getToTransDate() {
        return toTransDate;
    }

    public void setToTransDate(Date toTransDate) {
        this.toTransDate = toTransDate;
    }
}
