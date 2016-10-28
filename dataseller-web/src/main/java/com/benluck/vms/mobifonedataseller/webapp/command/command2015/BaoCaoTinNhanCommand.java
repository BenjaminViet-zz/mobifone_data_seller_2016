package com.benluck.vms.mobifonedataseller.webapp.command.command2015;

import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015.BaoCaoTinNhanDTO;
import com.benluck.vms.mobifonedataseller.webapp.command.AbstractCommand;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: HP
 * Date: 3/2/15
 * Time: 4:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class BaoCaoTinNhanCommand extends AbstractCommand<BaoCaoTinNhanDTO> {
    public BaoCaoTinNhanCommand(){
        this.pojo = new BaoCaoTinNhanDTO();
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
