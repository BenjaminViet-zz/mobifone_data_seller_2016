package com.benluck.vms.mobifonedataseller.webapp.command.command2015;

import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015.BaoCaoSoEZNhanTinThamGiaChuongTrinhDTO;
import com.benluck.vms.mobifonedataseller.webapp.command.AbstractCommand;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: HP
 * Date: 2/26/15
 * Time: 10:57 AM
 * To change this template use File | Settings | File Templates.
 */
public class BaoCaoSoEZNhanTinThamGiaChuongTrinhCommand extends AbstractCommand<BaoCaoSoEZNhanTinThamGiaChuongTrinhDTO> {
    public BaoCaoSoEZNhanTinThamGiaChuongTrinhCommand(){
        this.pojo = new BaoCaoSoEZNhanTinThamGiaChuongTrinhDTO();
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
