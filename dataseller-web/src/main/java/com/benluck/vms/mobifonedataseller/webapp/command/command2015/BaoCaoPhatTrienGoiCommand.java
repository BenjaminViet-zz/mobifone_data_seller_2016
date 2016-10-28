package com.benluck.vms.mobifonedataseller.webapp.command.command2015;

import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015.BaoCaoPhatTrienGoiDTO;
import com.benluck.vms.mobifonedataseller.webapp.command.AbstractCommand;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: vovanphuc0810
 * Date: 7/15/15
 * Time: 10:05 AM
 * To change this template use File | Settings | File Templates.
 */
public class BaoCaoPhatTrienGoiCommand extends AbstractCommand<BaoCaoPhatTrienGoiDTO> {
    public BaoCaoPhatTrienGoiCommand(){
        this.pojo = new BaoCaoPhatTrienGoiDTO();
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
