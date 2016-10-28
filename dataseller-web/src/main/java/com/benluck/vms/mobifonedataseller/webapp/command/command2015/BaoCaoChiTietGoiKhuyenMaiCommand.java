package com.benluck.vms.mobifonedataseller.webapp.command.command2015;

import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015.BaoCaoChiTietGoiKhuyenMaiDTO;
import com.benluck.vms.mobifonedataseller.webapp.command.AbstractCommand;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: HP
 * Date: 3/2/15
 * Time: 2:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class BaoCaoChiTietGoiKhuyenMaiCommand extends AbstractCommand<BaoCaoChiTietGoiKhuyenMaiDTO> {
    public BaoCaoChiTietGoiKhuyenMaiCommand(){
        this.pojo = new BaoCaoChiTietGoiKhuyenMaiDTO();
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
