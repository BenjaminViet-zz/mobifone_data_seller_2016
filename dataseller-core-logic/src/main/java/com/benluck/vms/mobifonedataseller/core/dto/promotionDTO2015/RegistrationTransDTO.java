package com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015;

import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.RetailDealerDTO;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 2/10/15
 * Time: 6:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class RegistrationTransDTO implements Serializable{
    private static final long serialVersionUID = -5643986666935542205L;
    private RetailDealerDTO retailDealer;
    private String ez_Isdn; // so EZ đang ky
    private Long transId;
    private PromPackageDTO goiCuoc;
    private String package_Code;
    private String customer_Isdn;   // so thue bao khach hang
    private Date trans_Date;        // ngay dang ky
    private String import_Date;
    private String prom_Condition_Status;   // tinh trang khuyen khich
    private String prom_Condition_Error;
    private String reg_Position;
    private Double prom_Amount;
    private Integer trans_Status;           // tinh trang cua giao dich : 0: khong thanh cong; 1: thanh cong
    private Integer payment_Status = -1;           // tinh trang cua giao dich : 0: khong thanh cong; 1: thanh cong
    private String trans_Error;
    private Timestamp payment_Date;
    private Timestamp sum_Date;
    private Double calling_amount;
    private Double sms_amount;
    private Double data_amount;
    private Double others_amount;
    private Timestamp active_datetime;
    private String event_code;
    private String event_pos_code;
    private String event_pos_name;
    private String sales_shop_code;

    private Timestamp fromDate;
    private Timestamp toDate;
    private Timestamp ngayChiTraDateTime;
    private Double tongTienQuyDoi;
    private Double soTienDuDK;
    private Integer totalTrans_ThoaDKCT;
    private Integer totalTransaction;
    private Integer haveDoc_Status;
    private String primary;

    public Integer getHaveDoc_Status() {
        return haveDoc_Status;
    }

    public void setHaveDoc_Status(Integer haveDoc_Status) {
        this.haveDoc_Status = haveDoc_Status;
    }

    public Timestamp getFromDate() {
        return fromDate;
    }

    public void setFromDate(Timestamp fromDate) {
        this.fromDate = fromDate;
    }

    public Timestamp getToDate() {
        return toDate;
    }

    public void setToDate(Timestamp toDate) {
        this.toDate = toDate;
    }

    public Timestamp getSum_Date() {
        return sum_Date;
    }

    public void setSum_Date(Timestamp sum_Date) {
        this.sum_Date = sum_Date;
    }

    public RetailDealerDTO getRetailDealer() {
        return retailDealer;
    }

    public void setRetailDealer(RetailDealerDTO retailDealer) {
        this.retailDealer = retailDealer;
    }

    public String getEz_Isdn() {
        return ez_Isdn;
    }

    public void setEz_Isdn(String ez_Isdn) {
        this.ez_Isdn = ez_Isdn;
    }

    public PromPackageDTO getGoiCuoc() {
        return goiCuoc;
    }

    public void setGoiCuoc(PromPackageDTO goiCuoc) {
        this.goiCuoc = goiCuoc;
    }

    public String getPackage_Code() {
        return package_Code;
    }

    public void setPackage_Code(String package_Code) {
        this.package_Code = package_Code;
    }

    public String getCustomer_Isdn() {
        return customer_Isdn;
    }

    public void setCustomer_Isdn(String customer_Isdn) {
        this.customer_Isdn = customer_Isdn;
    }

    public Date getTrans_Date() {
        return trans_Date;
    }

    public void setTrans_Date(Date trans_Date) {
        this.trans_Date = trans_Date;
    }

    public String getImport_Date() {
        return import_Date;
    }

    public void setImport_Date(String import_Date) {
        this.import_Date = import_Date;
    }

    public String getProm_Condition_Status() {
        return prom_Condition_Status;
    }

    public void setProm_Condition_Status(String prom_Condition_Status) {
        this.prom_Condition_Status = prom_Condition_Status;
    }

    public String getProm_Condition_Error() {
        return prom_Condition_Error;
    }

    public void setProm_Condition_Error(String prom_Condition_Error) {
        this.prom_Condition_Error = prom_Condition_Error;
    }

    public String getReg_Position() {
        return reg_Position;
    }

    public void setReg_Position(String reg_Position) {
        this.reg_Position = reg_Position;
    }

    public Double getProm_Amount() {
        return prom_Amount;
    }

    public void setProm_Amount(Double prom_Amount) {
        this.prom_Amount = prom_Amount;
    }

    public Integer getTrans_Status() {
        return trans_Status;
    }

    public void setTrans_Status(Integer trans_Status) {
        this.trans_Status = trans_Status;
    }

    public Integer getPayment_Status() {
        return payment_Status;
    }

    public void setPayment_Status(Integer payment_Status) {
        this.payment_Status = payment_Status;
    }

    public Long getTransId() {
        return transId;
    }

    public void setTransId(Long transId) {
        this.transId = transId;
    }

    public String getTrans_Error() {
        return trans_Error;
    }

    public void setTrans_Error(String trans_Error) {
        this.trans_Error = trans_Error;
    }

    public Timestamp getPayment_Date() {
        return payment_Date;
    }

    public void setPayment_Date(Timestamp payment_Date) {
        this.payment_Date = payment_Date;
    }

    public Timestamp getNgayChiTraDateTime() {
        return ngayChiTraDateTime;
    }

    public void setNgayChiTraDateTime(Timestamp ngayChiTraDateTime) {
        this.ngayChiTraDateTime = ngayChiTraDateTime;
    }

    public Double getTongTienQuyDoi() {
        return tongTienQuyDoi;
    }

    public void setTongTienQuyDoi(Double tongTienQuyDoi) {
        this.tongTienQuyDoi = tongTienQuyDoi;
    }

    public Double getSoTienDuDK() {
        return soTienDuDK;
    }

    public void setSoTienDuDK(Double soTienDuDK) {
        this.soTienDuDK = soTienDuDK;
    }

    public Integer getTotalTrans_ThoaDKCT() {
        return totalTrans_ThoaDKCT;
    }

    public void setTotalTrans_ThoaDKCT(Integer totalTrans_ThoaDKCT) {
        this.totalTrans_ThoaDKCT = totalTrans_ThoaDKCT;
    }

    public String getPrimary() {
        return primary;
    }

    public void setPrimary(String primary) {
        this.primary = primary;
    }

    public Double getCalling_amount() {
        return calling_amount;
    }

    public void setCalling_amount(Double calling_amount) {
        this.calling_amount = calling_amount;
    }

    public Double getSms_amount() {
        return sms_amount;
    }

    public void setSms_amount(Double sms_amount) {
        this.sms_amount = sms_amount;
    }

    public Double getData_amount() {
        return data_amount;
    }

    public void setData_amount(Double data_amount) {
        this.data_amount = data_amount;
    }

    public Double getOthers_amount() {
        return others_amount;
    }

    public void setOthers_amount(Double others_amount) {
        this.others_amount = others_amount;
    }

    public String getEvent_code() {
        return event_code;
    }

    public void setEvent_code(String event_code) {
        this.event_code = event_code;
    }

    public String getEvent_pos_code() {
        return event_pos_code;
    }

    public void setEvent_pos_code(String event_pos_code) {
        this.event_pos_code = event_pos_code;
    }

    public String getEvent_pos_name() {
        return event_pos_name;
    }

    public void setEvent_pos_name(String event_pos_name) {
        this.event_pos_name = event_pos_name;
    }

    public String getSales_shop_code() {
        return sales_shop_code;
    }

    public void setSales_shop_code(String sales_shop_code) {
        this.sales_shop_code = sales_shop_code;
    }

    public Timestamp getActive_datetime() {
        return active_datetime;
    }

    public void setActive_datetime(Timestamp active_datetime) {
        this.active_datetime = active_datetime;
    }
}
