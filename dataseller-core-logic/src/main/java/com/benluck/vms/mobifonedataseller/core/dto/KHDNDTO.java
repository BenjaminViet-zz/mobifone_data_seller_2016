package com.benluck.vms.mobifonedataseller.core.dto;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 10/30/16
 * Time: 22:44
 * To change this template use File | Settings | File Templates.
 */
public class KHDNDTO implements Serializable{
    private static final long serialVersionUID = -1784528134368776822L;

    private Long KHDNId;
    private String name;
    private String mst;
    private String gpkd;
    private Timestamp issuedContractDate;
    private String stb_vas;

    public Long getKHDNId() {
        return KHDNId;
    }

    public void setKHDNId(Long KHDNId) {
        this.KHDNId = KHDNId;
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
}
