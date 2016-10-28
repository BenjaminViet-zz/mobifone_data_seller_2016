package com.benluck.vms.mobifonedataseller.webapp.command.command2014;

import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.DistrictDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015.RegistrationTransDTO;
import com.benluck.vms.mobifonedataseller.webapp.command.AbstractCommand;

import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: HP
 * Date: 2/6/15
 * Time: 6:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class RegistrationTransCommand extends AbstractCommand<RegistrationTransDTO> {
    public RegistrationTransCommand(){
        this.pojo = new RegistrationTransDTO();
    }

    private Long branchId;
    private DistrictDTO district;
    private Long dealer_Id;
    private String message;
    private String trang_thai_chot_ky = "-1";
    private String trang_thai_doi_tien = "-1";
    private String trang_thai_du_dk_doi_tien = "-1";
    private Integer cycle = Calendar.getInstance().get(Calendar.MONTH) + 1;
    private String kit_sales_condition_status;
    private String sms_sales_condition_status;
    private String sub_charge_condition_status;
    private String sub_intro_condition_status;
    private String vas_sales_condition_status;
    private String market_info_condition_status;
    private Date fromDate;
    private Date toDate;
    private Date ngayChiTra;

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public DistrictDTO getDistrict() {
        return district;
    }

    public void setDistrict(DistrictDTO district) {
        this.district = district;
    }

    public Long getDealer_Id() {
        return dealer_Id;
    }

    public void setDealer_Id(Long dealer_Id) {
        this.dealer_Id = dealer_Id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTrang_thai_chot_ky() {
        return trang_thai_chot_ky;
    }

    public void setTrang_thai_chot_ky(String trang_thai_chot_ky) {
        this.trang_thai_chot_ky = trang_thai_chot_ky;
    }

    public String getTrang_thai_doi_tien() {
        return trang_thai_doi_tien;
    }

    public void setTrang_thai_doi_tien(String trang_thai_doi_tien) {
        this.trang_thai_doi_tien = trang_thai_doi_tien;
    }

    public String getTrang_thai_du_dk_doi_tien() {
        return trang_thai_du_dk_doi_tien;
    }

    public void setTrang_thai_du_dk_doi_tien(String trang_thai_du_dk_doi_tien) {
        this.trang_thai_du_dk_doi_tien = trang_thai_du_dk_doi_tien;
    }

    public Integer getCycle() {
        return cycle;
    }

    public void setCycle(Integer cycle) {
        this.cycle = cycle;
    }

    public String getKit_sales_condition_status() {
        return kit_sales_condition_status;
    }

    public void setKit_sales_condition_status(String kit_sales_condition_status) {
        this.kit_sales_condition_status = kit_sales_condition_status;
    }

    public String getSms_sales_condition_status() {
        return sms_sales_condition_status;
    }

    public void setSms_sales_condition_status(String sms_sales_condition_status) {
        this.sms_sales_condition_status = sms_sales_condition_status;
    }

    public String getSub_charge_condition_status() {
        return sub_charge_condition_status;
    }

    public void setSub_charge_condition_status(String sub_charge_condition_status) {
        this.sub_charge_condition_status = sub_charge_condition_status;
    }

    public String getSub_intro_condition_status() {
        return sub_intro_condition_status;
    }

    public void setSub_intro_condition_status(String sub_intro_condition_status) {
        this.sub_intro_condition_status = sub_intro_condition_status;
    }

    public String getVas_sales_condition_status() {
        return vas_sales_condition_status;
    }

    public void setVas_sales_condition_status(String vas_sales_condition_status) {
        this.vas_sales_condition_status = vas_sales_condition_status;
    }

    public String getMarket_info_condition_status() {
        return market_info_condition_status;
    }

    public void setMarket_info_condition_status(String market_info_condition_status) {
        this.market_info_condition_status = market_info_condition_status;
    }

    public Date getNgayChiTra() {
        return ngayChiTra;
    }

    public void setNgayChiTra(Date ngayChiTra) {
        this.ngayChiTra = ngayChiTra;
    }
}
