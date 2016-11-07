package com.benluck.vms.mobifonedataseller.core.dto;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/6/16
 * Time: 20:44
 * To change this template use File | Settings | File Templates.
 */
public class PackageDataCodeGenDTO implements Serializable{
    private static final long serialVersionUID = 917900004544664360L;

    private Long packageDataCodeGenId;
    private PackageDataDTO packageData;
    private Integer year;
    private Integer batch1_Remaining;
    private Integer batch2_Remaining;
    private Integer batch3_Remaining;
    private Integer batch4_Remaining;
    private Integer batch5_Remaining;
    private Integer batch6_Remaining;
    private Integer batch7_Remaining;
    private Integer batch8_Remaining;
    private Integer batch9_Remaining;
    private Timestamp createdDate;
    private Integer isGeneratedCardCode = 0;    // 0: Chua generate Card Code; 1: generated
    private Integer status;                     // 0: Generating in progress; 1: failed on generating Card Code; 2: Generate Card Code successfully

    public Long getPackageDataCodeGenId() {
        return packageDataCodeGenId;
    }

    public void setPackageDataCodeGenId(Long packageDataCodeGenId) {
        this.packageDataCodeGenId = packageDataCodeGenId;
    }

    public PackageDataDTO getPackageData() {
        return packageData;
    }

    public void setPackageData(PackageDataDTO packageData) {
        this.packageData = packageData;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getGeneratedCardCode() {
        return isGeneratedCardCode;
    }

    public void setGeneratedCardCode(Integer generatedCardCode) {
        isGeneratedCardCode = generatedCardCode;
    }

    public Integer getBatch1_Remaining() {
        return batch1_Remaining;
    }

    public void setBatch1_Remaining(Integer batch1_Remaining) {
        this.batch1_Remaining = batch1_Remaining;
    }

    public Integer getBatch2_Remaining() {
        return batch2_Remaining;
    }

    public void setBatch2_Remaining(Integer batch2_Remaining) {
        this.batch2_Remaining = batch2_Remaining;
    }

    public Integer getBatch3_Remaining() {
        return batch3_Remaining;
    }

    public void setBatch3_Remaining(Integer batch3_Remaining) {
        this.batch3_Remaining = batch3_Remaining;
    }

    public Integer getBatch4_Remaining() {
        return batch4_Remaining;
    }

    public void setBatch4_Remaining(Integer batch4_Remaining) {
        this.batch4_Remaining = batch4_Remaining;
    }

    public Integer getBatch5_Remaining() {
        return batch5_Remaining;
    }

    public void setBatch5_Remaining(Integer batch5_Remaining) {
        this.batch5_Remaining = batch5_Remaining;
    }

    public Integer getBatch6_Remaining() {
        return batch6_Remaining;
    }

    public void setBatch6_Remaining(Integer batch6_Remaining) {
        this.batch6_Remaining = batch6_Remaining;
    }

    public Integer getBatch7_Remaining() {
        return batch7_Remaining;
    }

    public void setBatch7_Remaining(Integer batch7_Remaining) {
        this.batch7_Remaining = batch7_Remaining;
    }

    public Integer getBatch8_Remaining() {
        return batch8_Remaining;
    }

    public void setBatch8_Remaining(Integer batch8_Remaining) {
        this.batch8_Remaining = batch8_Remaining;
    }

    public Integer getBatch9_Remaining() {
        return batch9_Remaining;
    }

    public void setBatch9_Remaining(Integer batch9_Remaining) {
        this.batch9_Remaining = batch9_Remaining;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
