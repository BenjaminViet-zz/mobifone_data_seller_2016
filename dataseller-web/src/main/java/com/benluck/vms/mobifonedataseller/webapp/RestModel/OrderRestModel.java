package com.benluck.vms.mobifonedataseller.webapp.RestModel;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 12/4/16
 * Time: 19:16
 * To change this template use File | Settings | File Templates.
 */
public class OrderRestModel {
    private String packageDataName;
    private Integer quantity;
    private Double unitPrice;
    private Timestamp issuedDate;
    private List<String> cardCodeList;
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

    public List<String> getCardCodeList() {
        return cardCodeList;
    }

    public void setCardCodeList(List<String> cardCodeList) {
        this.cardCodeList = cardCodeList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
