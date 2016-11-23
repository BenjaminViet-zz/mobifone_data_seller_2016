package com.benluck.vms.mobifonedataseller.core.dto;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/9/16
 * Time: 21:27
 * To change this template use File | Settings | File Templates.
 */
public class MBDCodeHistoryDTO implements Serializable{
    private static final long serialVersionUID = -469713212908993808L;

    private Long transId;
    private Long subId;
    private String isdn;
    private Timestamp regDateTime;
    private Long custId;
    private String name;
    private Timestamp staDateTime;
    private String tin;
    private Timestamp insertDateTime;
    private Timestamp regDateTimeFrom;
    private Timestamp regDateTimeTo;
    private Timestamp staDateTimeTo;
    private Timestamp staDateTimeFrom;

    public Long getTransId() {
        return transId;
    }

    public void setTransId(Long transId) {
        this.transId = transId;
    }

    public Long getSubId() {
        return subId;
    }

    public void setSubId(Long subId) {
        this.subId = subId;
    }

    public String getIsdn() {
        return isdn;
    }

    public void setIsdn(String isdn) {
        this.isdn = isdn;
    }

    public Timestamp getRegDateTime() {
        return regDateTime;
    }

    public void setRegDateTime(Timestamp regDateTime) {
        this.regDateTime = regDateTime;
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getStaDateTime() {
        return staDateTime;
    }

    public void setStaDateTime(Timestamp staDateTime) {
        this.staDateTime = staDateTime;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public Timestamp getInsertDateTime() {
        return insertDateTime;
    }

    public void setInsertDateTime(Timestamp insertDateTime) {
        this.insertDateTime = insertDateTime;
    }

    public Timestamp getRegDateTimeFrom() {
        return regDateTimeFrom;
    }

    public void setRegDateTimeFrom(Timestamp regDateTimeFrom) {
        this.regDateTimeFrom = regDateTimeFrom;
    }

    public Timestamp getRegDateTimeTo() {
        return regDateTimeTo;
    }

    public void setRegDateTimeTo(Timestamp regDateTimeTo) {
        this.regDateTimeTo = regDateTimeTo;
    }

    public Timestamp getStaDateTimeTo() {
        return staDateTimeTo;
    }

    public void setStaDateTimeTo(Timestamp staDateTimeTo) {
        this.staDateTimeTo = staDateTimeTo;
    }

    public Timestamp getStaDateTimeFrom() {
        return staDateTimeFrom;
    }

    public void setStaDateTimeFrom(Timestamp staDateTimeFrom) {
        this.staDateTimeFrom = staDateTimeFrom;
    }
}
