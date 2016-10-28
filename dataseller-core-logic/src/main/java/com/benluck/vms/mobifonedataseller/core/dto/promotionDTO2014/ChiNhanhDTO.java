package com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/19/14
 * Time: 11:32 AM
 * To change this template use File | Settings | File Templates.
 */
public class ChiNhanhDTO implements Serializable {
    private static final long serialVersionUID = 4268665591935898709L;
    private Long chiNhanhId;
    private Integer chiNhanh;
    private String name;
    private Long branchId;
    private BranchDTO branch;   // dung de mapping voi BranchId cua VMS.
    private List<DepartmentDTO> cuaHangList;

    private Long departmentId;
    private String tenCuaHang;

    public Long getChiNhanhId() {
        return chiNhanhId;
    }

    public void setChiNhanhId(Long chiNhanhId) {
        this.chiNhanhId = chiNhanhId;
    }

    public Integer getChiNhanh() {
        return chiNhanh;
    }

    public void setChiNhanh(Integer chiNhanh) {
        this.chiNhanh = chiNhanh;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DepartmentDTO> getCuaHangList() {
        return cuaHangList;
    }

    public void setCuaHangList(List<DepartmentDTO> cuaHangList) {
        this.cuaHangList = cuaHangList;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getTenCuaHang() {
        return tenCuaHang;
    }

    public void setTenCuaHang(String tenCuaHang) {
        this.tenCuaHang = tenCuaHang;
    }

    public BranchDTO getBranch() {
        return branch;
    }

    public void setBranch(BranchDTO branch) {
        this.branch = branch;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }
}
