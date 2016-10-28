package com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/25/14
 * Time: 10:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class DistrictDTO implements Serializable{
    private static final long serialVersionUID = -4503008777136121446L;

    private Long district_Id;
    private String district_code;
    private Long city_Id;
    private BranchDTO branch;
    private String district_name;

    public Long getDistrict_Id() {
        return district_Id;
    }

    public void setDistrict_Id(Long district_Id) {
        this.district_Id = district_Id;
    }

    public String getDistrict_code() {
        return district_code;
    }

    public void setDistrict_code(String district_code) {
        this.district_code = district_code;
    }

    public Long getCity_Id() {
        return city_Id;
    }

    public void setCity_Id(Long city_Id) {
        this.city_Id = city_Id;
    }

    public BranchDTO getBranch() {
        return branch;
    }

    public void setBranch(BranchDTO branch) {
        this.branch = branch;
    }

    public String getDistrict_name() {
        return district_name;
    }

    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
    }
}
