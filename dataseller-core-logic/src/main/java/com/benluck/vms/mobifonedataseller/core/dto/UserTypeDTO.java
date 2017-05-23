package com.benluck.vms.mobifonedataseller.core.dto;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 5/22/17
 * Time: 22:05
 * To change this template use File | Settings | File Templates.
 */
public class UserTypeDTO {
    private Long userTypeId;
    private String code;
    private String description;

    public Long getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(Long userTypeId) {
        this.userTypeId = userTypeId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
