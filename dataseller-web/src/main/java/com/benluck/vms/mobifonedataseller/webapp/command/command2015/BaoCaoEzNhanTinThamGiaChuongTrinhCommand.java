package com.benluck.vms.mobifonedataseller.webapp.command.command2015;

import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015.BaoCaoEzNhanTinThamGiaChuongTrinhDTO;
import com.benluck.vms.mobifonedataseller.webapp.command.AbstractCommand;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: HP
 * Date: 3/17/15
 * Time: 6:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class BaoCaoEzNhanTinThamGiaChuongTrinhCommand extends AbstractCommand<BaoCaoEzNhanTinThamGiaChuongTrinhDTO> {
    public BaoCaoEzNhanTinThamGiaChuongTrinhCommand(){
        this.pojo = new BaoCaoEzNhanTinThamGiaChuongTrinhDTO();
    }

    private Date fromDate;
    private Date toDate;
    private Date fromNgayKichHoat;
    private Date toNgayKichHoat;

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

    public Date getFromNgayKichHoat() {
        return fromNgayKichHoat;
    }

    public void setFromNgayKichHoat(Date fromNgayKichHoat) {
        this.fromNgayKichHoat = fromNgayKichHoat;
    }

    public Date getToNgayKichHoat() {
        return toNgayKichHoat;
    }

    public void setToNgayKichHoat(Date toNgayKichHoat) {
        this.toNgayKichHoat = toNgayKichHoat;
    }
}
