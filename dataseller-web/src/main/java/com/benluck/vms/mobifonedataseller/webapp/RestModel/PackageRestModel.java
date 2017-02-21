package com.benluck.vms.mobifonedataseller.webapp.RestModel;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 2/21/17
 * Time: 12:57
 * To change this template use File | Settings | File Templates.
 */
public class PackageRestModel {
    private String packageName;
    private Double price;
    private String volumn;
    private Integer duration;
    private Integer extend;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getVolumn() {
        return volumn;
    }

    public void setVolumn(String volumn) {
        this.volumn = volumn;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getExtend() {
        return extend;
    }

    public void setExtend(Integer extend) {
        this.extend = extend;
    }
}
