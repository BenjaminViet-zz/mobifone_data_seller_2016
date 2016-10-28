package com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/25/14
 * Time: 10:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class RetailDealerDTO implements Serializable{
    private static final long serialVersionUID = -4832132589659195723L;

    private Long dealer_Id;
    private String dealer_code;
    private String dealer_name;
    private String dealer_type;
    private String ez_isdn;
    private BranchDTO branch;
    private String mobile;
    private String agent_code;
    private String branch_name;
    private String home_number;
    private String street;
    private String precint;
    private String district_name;
    private String contact_name;
    private String tax_Code;
    private String email;
    private DistrictDTO district;
    private String address;
    private String error;
    private Boolean checkEmpty;
    private Timestamp createdDate;
    private String propertiesCode;
    private String primary;

    public Boolean getCheckEmpty() {
        return checkEmpty;
    }

    public void setCheckEmpty(Boolean checkEmpty) {
        this.checkEmpty = checkEmpty;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getDealer_Id() {
        return dealer_Id;
    }

    public void setDealer_Id(Long dealer_Id) {
        this.dealer_Id = dealer_Id;
    }

    public String getDealer_code() {
        return dealer_code;
    }

    public void setDealer_code(String dealer_code) {
        this.dealer_code = dealer_code;
    }

    public String getDealer_name() {
        return dealer_name;
    }

    public void setDealer_name(String dealer_name) {
        this.dealer_name = dealer_name;
    }

    public String getDealer_type() {
        return dealer_type;
    }

    public void setDealer_type(String dealer_type) {
        this.dealer_type = dealer_type;
    }

    public String getEz_isdn() {
        return ez_isdn;
    }

    public void setEz_isdn(String ez_isdn) {
        this.ez_isdn = ez_isdn;
    }

    public BranchDTO getBranch() {
        return branch;
    }

    public void setBranch(BranchDTO branch) {
        this.branch = branch;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAgent_code() {
        return agent_code;
    }

    public void setAgent_code(String agent_code) {
        this.agent_code = agent_code;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public String getHome_number() {
        return home_number;
    }

    public void setHome_number(String home_number) {
        this.home_number = home_number;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPrecint() {
        return precint;
    }

    public void setPrecint(String precint) {
        this.precint = precint;
    }

    public String getDistrict_name() {
        return district_name;
    }

    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public DistrictDTO getDistrict() {
        return district;
    }

    public void setDistrict(DistrictDTO district) {
        this.district = district;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public String getPropertiesCode() {
        return propertiesCode;
    }

    public void setPropertiesCode(String propertiesCode) {
        this.propertiesCode = propertiesCode;
    }

    public String getPrimary() {
        return primary;
    }

    public void setPrimary(String primary) {
        this.primary = primary;
    }

    public String getTax_Code() {
        return tax_Code;
    }

    public void setTax_Code(String tax_Code) {
        this.tax_Code = tax_Code;
    }
}
