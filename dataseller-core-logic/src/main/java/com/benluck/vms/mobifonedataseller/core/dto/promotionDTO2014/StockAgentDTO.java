package com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/15/14
 * Time: 11:03 AM
 * To change this template use File | Settings | File Templates.
 */
public class StockAgentDTO implements Serializable {
    private static final long serialVersionUID = 1992949003013941308L;

    private Long stockAgentId;
    private DepartmentDTO department;
    private Integer total;
    private Integer chiNhanh;
    private Long chiNhanhId;
    private Integer trang_thai_kho = -1;    // -1: all, 0: het hang, 1: con hang.
    private BranchDTO branch;
    private GiftDTO gift;

    public Integer getTrang_thai_kho() {
        return trang_thai_kho;
    }

    public void setTrang_thai_kho(Integer trang_thai_kho) {
        this.trang_thai_kho = trang_thai_kho;
    }

    public Long getStockAgentId() {
        return stockAgentId;
    }

    public void setStockAgentId(Long stockAgentId) {
        this.stockAgentId = stockAgentId;
    }

    public DepartmentDTO getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentDTO department) {
        this.department = department;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getChiNhanh() {
        return chiNhanh;
    }

    public void setChiNhanh(Integer chiNhanh) {
        this.chiNhanh = chiNhanh;
    }

    public Long getChiNhanhId() {
        return chiNhanhId;
    }

    public void setChiNhanhId(Long chiNhanhId) {
        this.chiNhanhId = chiNhanhId;
    }

    public BranchDTO getBranch() {
        return branch;
    }

    public void setBranch(BranchDTO branch) {
        this.branch = branch;
    }

    public GiftDTO getGift() {
        return gift;
    }

    public void setGift(GiftDTO gift) {
        this.gift = gift;
    }
}
