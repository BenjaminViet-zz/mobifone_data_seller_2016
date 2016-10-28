package com.benluck.vms.mobifonedataseller.webapp.command.command2014;


import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.UserDTO;
import com.benluck.vms.mobifonedataseller.webapp.command.AbstractCommand;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2/19/14
 * Time: 1:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserCommand extends AbstractCommand<UserDTO> {
        public UserCommand(){
            this.pojo = new UserDTO();
        }
    private String confirmPassword;
    private String statistic;
    private String message;
    private Integer soDiemHienTai;
    private Integer currentView = 1;
    private Integer thangTichDiem = Calendar.getInstance().get(Calendar.MONTH) + 1;
    private Long itemId;
    private String maDuThuong;
    private Integer cycle = Calendar.getInstance().get(Calendar.MONTH) + 1;
    private Integer fromStart = 0;

    // for xem chi tiet theo hang muc KPP
    private Long d_Id;
    private Long item_Id;
    private Date ngay_ps_date;
    private Timestamp ngay_ps;

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    private Date fromDate;
    private Date toDate;
    private Timestamp fromDateTime;
    private Timestamp toDateTime;

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

    public String getStatistic() {
        return statistic;
    }

    public void setStatistic(String statistic) {
        this.statistic = statistic;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getSoDiemHienTai() {
        return soDiemHienTai;
    }

    public void setSoDiemHienTai(Integer soDiemHienTai) {
        this.soDiemHienTai = soDiemHienTai;
    }

    public Integer getCurrentView() {
        return currentView;
    }

    public void setCurrentView(Integer currentView) {
        this.currentView = currentView;
    }

    public Integer getThangTichDiem() {
        return thangTichDiem;
    }

    public void setThangTichDiem(Integer thangTichDiem) {
        this.thangTichDiem = thangTichDiem;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getMaDuThuong() {
        return maDuThuong;
    }

    public void setMaDuThuong(String maDuThuong) {
        this.maDuThuong = maDuThuong;
    }

    public Long getD_Id() {
        return d_Id;
    }

    public void setD_Id(Long d_Id) {
        this.d_Id = d_Id;
    }

    public Long getItem_Id() {
        return item_Id;
    }

    public void setItem_Id(Long item_Id) {
        this.item_Id = item_Id;
    }

    public Date getNgay_ps_date() {
        return ngay_ps_date;
    }

    public void setNgay_ps_date(Date ngay_ps_date) {
        this.ngay_ps_date = ngay_ps_date;
    }

    public Timestamp getNgay_ps() {
        return ngay_ps;
    }

    public void setNgay_ps(Timestamp ngay_ps) {
        this.ngay_ps = ngay_ps;
    }

    public Integer getCycle() {
        return cycle;
    }

    public void setCycle(Integer cycle) {
        this.cycle = cycle;
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

    public Integer getFromStart() {
        return fromStart;
    }

    public void setFromStart(Integer fromStart) {
        this.fromStart = fromStart;
    }
}
