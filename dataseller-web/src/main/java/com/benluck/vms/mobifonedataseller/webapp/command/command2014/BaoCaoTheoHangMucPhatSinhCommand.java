package com.benluck.vms.mobifonedataseller.webapp.command.command2014;

import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BaoCaoTheoHangMucPhatSinhDTO;
import com.benluck.vms.mobifonedataseller.webapp.command.AbstractCommand;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: HauKute
 * Date: 10/25/14
 * Time: 3:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class BaoCaoTheoHangMucPhatSinhCommand extends AbstractCommand<BaoCaoTheoHangMucPhatSinhDTO> {
    public BaoCaoTheoHangMucPhatSinhCommand(){
        this.pojo = new BaoCaoTheoHangMucPhatSinhDTO();
    }
    private Integer cycle = Calendar.getInstance().get(Calendar.MONTH) + 1;
    private Date fromDate;
    private Date toDate;
    private Timestamp fromDateTime;
    private Timestamp toDateTime;
    private Long branch_Id;
    private Long district_Id;
    private Long item_Id;

    public Integer getCycle() {
        return cycle;
    }

    public void setCycle(Integer cycle) {
        this.cycle = cycle;
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

    public Long getItem_Id() {
        return item_Id;
    }

    public void setItem_Id(Long item_Id) {
        this.item_Id = item_Id;
    }

    public Long getBranch_Id() {
        return branch_Id;
    }

    public void setBranch_Id(Long branch_Id) {
        this.branch_Id = branch_Id;
    }

    public Long getDistrict_Id() {
        return district_Id;
    }

    public void setDistrict_Id(Long district_Id) {
        this.district_Id = district_Id;
    }
}
