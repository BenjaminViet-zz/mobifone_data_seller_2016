package com.benluck.vms.mobifonedataseller.webapp.command.command2014;

import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BaoCaoQuanLyTinNhanDTO;
import com.benluck.vms.mobifonedataseller.webapp.command.AbstractCommand;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/22/14
 * Time: 3:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class BaoCaoQuanLyTinNhanCommand extends AbstractCommand<BaoCaoQuanLyTinNhanDTO> {
    public BaoCaoQuanLyTinNhanCommand(){
        this.pojo = new BaoCaoQuanLyTinNhanDTO();
    }

    private String loaiTinNhanTuKHCN;
    private String loaiTinNhanTuVMS;
    private Date fromDate;
    private Date toDate;
    private Timestamp fromDateTime;
    private Timestamp toDateTime;

    public String getLoaiTinNhanTuKHCN() {
        return loaiTinNhanTuKHCN;
    }

    public void setLoaiTinNhanTuKHCN(String loaiTinNhanTuKHCN) {
        this.loaiTinNhanTuKHCN = loaiTinNhanTuKHCN;
    }

    public String getLoaiTinNhanTuVMS() {
        return loaiTinNhanTuVMS;
    }

    public void setLoaiTinNhanTuVMS(String loaiTinNhanTuVMS) {
        this.loaiTinNhanTuVMS = loaiTinNhanTuVMS;
    }

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

    public Timestamp getFromDateTime() {
        return fromDateTime;
    }

    public void setFromDateTime(Timestamp fromDateTime) {
        this.fromDateTime = fromDateTime;
    }

    public Timestamp getToDateTime() {
        return toDateTime;
    }

    public void setToDateTime(Timestamp toDateTime) {
        this.toDateTime = toDateTime;
    }
}
