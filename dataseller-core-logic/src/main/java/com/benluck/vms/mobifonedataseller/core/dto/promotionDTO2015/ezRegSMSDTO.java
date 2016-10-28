package com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 3/17/15
 * Time: 3:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class ezRegSMSDTO implements Serializable{
    private static final long serialVersionUID = 8798233521305140451L;

    private String ez_Isdn;
    private String dealer_Code;
    private String district_Code;
    private Timestamp reg_Date;
    private String reg_Status;
    private String reg_Log;
    private Long id;
    private Long sms_Log_Id;
    private String serial;

    public String getEz_Isdn() {
        return ez_Isdn;
    }

    public void setEz_Isdn(String ez_Isdn) {
        this.ez_Isdn = ez_Isdn;
    }

    public String getDealer_Code() {
        return dealer_Code;
    }

    public void setDealer_Code(String dealer_Code) {
        this.dealer_Code = dealer_Code;
    }

    public String getDistrict_Code() {
        return district_Code;
    }

    public void setDistrict_Code(String district_Code) {
        this.district_Code = district_Code;
    }

    public Timestamp getReg_Date() {
        return reg_Date;
    }

    public void setReg_Date(Timestamp reg_Date) {
        this.reg_Date = reg_Date;
    }

    public String getReg_Status() {
        return reg_Status;
    }

    public void setReg_Status(String reg_Status) {
        this.reg_Status = reg_Status;
    }

    public String getReg_Log() {
        return reg_Log;
    }

    public void setReg_Log(String reg_Log) {
        this.reg_Log = reg_Log;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSms_Log_Id() {
        return sms_Log_Id;
    }

    public void setSms_Log_Id(Long sms_Log_Id) {
        this.sms_Log_Id = sms_Log_Id;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }
}
