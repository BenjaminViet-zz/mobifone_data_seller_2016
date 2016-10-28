package com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/25/14
 * Time: 10:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class BranchDTO implements Serializable{
    private static final long serialVersionUID = -3528809274729364291L;

    private Long branch_Id;
    private String branch_code;
    private String branch_name;
    private String address;
    private List<DepartmentDTO> department;

    public Long getBranch_Id() {
        return branch_Id;
    }

    public void setBranch_Id(Long branch_Id) {
        this.branch_Id = branch_Id;
    }

    public String getBranch_code() {
        return branch_code;
    }

    public void setBranch_code(String branch_code) {
        this.branch_code = branch_code;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<DepartmentDTO> getDepartment() {
        return department;
    }

    public void setDepartment(List<DepartmentDTO> department) {
        this.department = department;
    }
}
