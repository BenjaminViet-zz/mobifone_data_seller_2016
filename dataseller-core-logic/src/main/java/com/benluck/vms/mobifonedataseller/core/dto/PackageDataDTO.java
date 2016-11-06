package com.benluck.vms.mobifonedataseller.core.dto;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 10/30/16
 * Time: 22:35
 * To change this template use File | Settings | File Templates.
 */
public class PackageDataDTO implements Serializable {
    private static final long serialVersionUID = 4354551771178255164L;

    private Long packageDataId;
    private String name;
    private Double value;
    private String volume;
    private Integer duration;
    private String durationText;
    private Integer numberOfExtend;
    private String tk;

    public PackageDataDTO() {
    }

    public PackageDataDTO(Long packageDataId, String name, Double value, String volume, Integer duration, String durationText) {
        this.packageDataId = packageDataId;
        this.name = name;
        this.value = value;
        this.volume = volume;
        this.duration = duration;
        this.durationText = durationText;
    }

    public Long getPackageDataId() {
        return packageDataId;
    }

    public void setPackageDataId(Long packageDataId) {
        this.packageDataId = packageDataId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getDurationText() {
        return durationText;
    }

    public void setDurationText(String durationText) {
        this.durationText = durationText;
    }

    public Integer getNumberOfExtend() {
        return numberOfExtend;
    }

    public void setNumberOfExtend(Integer numberOfExtend) {
        this.numberOfExtend = numberOfExtend;
    }

    public String getTk() {
        return tk;
    }

    public void setTk(String tk) {
        this.tk = tk;
    }
}
