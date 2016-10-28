package com.benluck.vms.mobifonedataseller.webapp.command.command2014;

import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.CTTichDiemMaDuThuongDTO;
import com.benluck.vms.mobifonedataseller.webapp.command.AbstractCommand;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/29/14
 * Time: 5:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class TraCuuDanhSachMaThuongTrungThuongCommand extends AbstractCommand<CTTichDiemMaDuThuongDTO> {
    public TraCuuDanhSachMaThuongTrungThuongCommand(){
        this.pojo = new CTTichDiemMaDuThuongDTO();
    }

    private Date fromDate;
    private Date toDate;
    private Timestamp fromDateTime;
    private Timestamp toDateTime;
    private String maDuThuong;
    private String dealer_Code;
    private String soEZ;
    private Long kpp_gift_Id;

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

    public String getMaDuThuong() {
        return maDuThuong;
    }

    public void setMaDuThuong(String maDuThuong) {
        this.maDuThuong = maDuThuong;
    }

    public String getDealer_Code() {
        return dealer_Code;
    }

    public void setDealer_Code(String dealer_Code) {
        this.dealer_Code = dealer_Code;
    }

    public String getSoEZ() {
        return soEZ;
    }

    public void setSoEZ(String soEZ) {
        this.soEZ = soEZ;
    }

    public Long getKpp_gift_Id() {
        return kpp_gift_Id;
    }

    public void setKpp_gift_Id(Long kpp_gift_Id) {
        this.kpp_gift_Id = kpp_gift_Id;
    }
}
