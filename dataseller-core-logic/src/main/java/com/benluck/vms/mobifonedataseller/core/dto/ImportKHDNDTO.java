package com.benluck.vms.mobifonedataseller.core.dto;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/12/16
 * Time: 00:15
 * To change this template use File | Settings | File Templates.
 */
public class ImportKHDNDTO implements Serializable{
    private static final long serialVersionUID = -2086639060452005784L;

    private String name;
    private String mst;
    private String gpkd;
    private Timestamp issuedContractDate;
    private String issuedContractDateStr;
    private String stb_vas;
    private String shopCode;
    private String errorMessage;

    public ImportKHDNDTO() {}

    public ImportKHDNDTO(String shopCode, String name, String mst, String gpkd, String issuedContractDateStr, String stb_vas) {
        this.shopCode = shopCode;
        this.name = name;
        this.mst = mst;
        this.gpkd = gpkd;
        this.issuedContractDateStr = issuedContractDateStr;
        this.stb_vas = stb_vas;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMst() {
        return mst;
    }

    public void setMst(String mst) {
        this.mst = mst;
    }

    public String getGpkd() {
        return gpkd;
    }

    public void setGpkd(String gpkd) {
        this.gpkd = gpkd;
    }

    public Timestamp getIssuedContractDate() {
        return issuedContractDate;
    }

    public void setIssuedContractDate(Timestamp issuedContractDate) {
        this.issuedContractDate = issuedContractDate;
    }

    public String getStb_vas() {
        return stb_vas;
    }

    public void setStb_vas(String stb_vas) {
        this.stb_vas = stb_vas;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getIssuedContractDateStr() {
        return issuedContractDateStr;
    }

    public void setIssuedContractDateStr(String issuedContractDateStr) {
        this.issuedContractDateStr = issuedContractDateStr;
    }
}
