package com.benluck.vms.mobifonedataseller.webapp.RestModel;

import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 2/21/17
 * Time: 13:19
 * To change this template use File | Settings | File Templates.
 */
public class OrderNoCardCodeListRestModel {
    private String packageDataName;
    private Integer quantity;
    private Double unitPrice;
    private Timestamp issuedDate;
    private String status;

    public String getPackageDataName() {
        return packageDataName;
    }

    public void setPackageDataName(String packageDataName) {
        this.packageDataName = packageDataName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Timestamp getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(Timestamp issuedDate) {
        this.issuedDate = issuedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
