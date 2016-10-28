package com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/22/14
 * Time: 11:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class DealerAccountActionDTO implements Serializable{
    private static final long serialVersionUID = 4777736263084292659L;

    private String tenLoaiPS;
    private Timestamp issue_date;
    private Integer soDiem;
    private Long item_Id;
    private Long dealer_Id;
    private Integer soLuong;
    private String item_unit;
    private Integer cycle;
    private Double soTienTuongUng;
    private Integer soMaDuThuong;

    private Integer total_point;
    private Integer prom_point;
    private Integer ticket_point;
    private String cycle_lock_status;
    private String trang_thai_dat;
    private String branch_Name;
    private String district_Name;
    private String dealer_Code;
    private String dealer_Name;
    private String condition_status;

    public String getTenLoaiPS() {
        return tenLoaiPS;
    }

    public void setTenLoaiPS(String tenLoaiPS) {
        this.tenLoaiPS = tenLoaiPS;
    }

    public Timestamp getIssue_date() {
        return issue_date;
    }

    public void setIssue_date(Timestamp issue_date) {
        this.issue_date = issue_date;
    }

    public Integer getSoDiem() {
        return soDiem;
    }

    public void setSoDiem(Integer soDiem) {
        this.soDiem = soDiem;
    }

    public Integer getTotal_point() {
        return total_point;
    }

    public void setTotal_point(Integer total_point) {
        this.total_point = total_point;
    }

    public Integer getProm_point() {
        return prom_point;
    }

    public void setProm_point(Integer prom_point) {
        this.prom_point = prom_point;
    }

    public Integer getTicket_point() {
        return ticket_point;
    }

    public void setTicket_point(Integer ticket_point) {
        this.ticket_point = ticket_point;
    }

    public Long getItem_Id() {
        return item_Id;
    }

    public void setItem_Id(Long item_Id) {
        this.item_Id = item_Id;
    }

    public Long getDealer_Id() {
        return dealer_Id;
    }

    public void setDealer_Id(Long dealer_Id) {
        this.dealer_Id = dealer_Id;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    public String getItem_unit() {
        return item_unit;
    }

    public void setItem_unit(String item_unit) {
        this.item_unit = item_unit;
    }

    public Integer getCycle() {
        return cycle;
    }

    public void setCycle(Integer cycle) {
        this.cycle = cycle;
    }

    public Double getSoTienTuongUng() {
        return soTienTuongUng;
    }

    public void setSoTienTuongUng(Double soTienTuongUng) {
        this.soTienTuongUng = soTienTuongUng;
    }

    public Integer getSoMaDuThuong() {
        return soMaDuThuong;
    }

    public void setSoMaDuThuong(Integer soMaDuThuong) {
        this.soMaDuThuong = soMaDuThuong;
    }

    public String getCycle_lock_status() {
        return cycle_lock_status;
    }

    public void setCycle_lock_status(String cycle_lock_status) {
        this.cycle_lock_status = cycle_lock_status;
    }

    public String getTrang_thai_dat() {
        return trang_thai_dat;
    }

    public void setTrang_thai_dat(String trang_thai_dat) {
        this.trang_thai_dat = trang_thai_dat;
    }

    public String getBranch_Name() {
        return branch_Name;
    }

    public void setBranch_Name(String branch_Name) {
        this.branch_Name = branch_Name;
    }

    public String getDistrict_Name() {
        return district_Name;
    }

    public void setDistrict_Name(String district_Name) {
        this.district_Name = district_Name;
    }

    public String getDealer_Code() {
        return dealer_Code;
    }

    public void setDealer_Code(String dealer_Code) {
        this.dealer_Code = dealer_Code;
    }

    public String getDealer_Name() {
        return dealer_Name;
    }

    public void setDealer_Name(String dealer_Name) {
        this.dealer_Name = dealer_Name;
    }

    public String getCondition_status() {
        return condition_status;
    }

    public void setCondition_status(String condition_status) {
        this.condition_status = condition_status;
    }
}
