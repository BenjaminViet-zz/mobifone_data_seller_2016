package com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/29/14
 * Time: 11:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class BaoCaoHangHoaDTO implements Serializable{
    private static final long serialVersionUID = 3471776451341787391L;

    private String branch_Name;
    private String district_Name;
    private Long dealer_Id;
    private String dealer_Code;
    private String dealer_Name;
    private Double card_quantity;
    private Double kit_quantity;
    private Double bhtt_quantity;
    private Double bhtt_psc_25;
    private Double psc_amount;
    private Double gtkh_quantity;
    private Double gtkh_psc_25k;
    private Double vas_quantity;

    public String getBranch_Name() {
        return branch_Name;
    }

    public void setBranch_Name(String branch_Name) {
        this.branch_Name = branch_Name;
    }

    public String getDistrict_Name() {
        return district_Name;
    }

    public void setDistrict_Name(String district_Name) {
        this.district_Name = district_Name;
    }

    public String getDealer_Code() {
        return dealer_Code;
    }

    public void setDealer_Code(String dealer_Code) {
        this.dealer_Code = dealer_Code;
    }

    public String getDealer_Name() {
        return dealer_Name;
    }

    public void setDealer_Name(String dealer_Name) {
        this.dealer_Name = dealer_Name;
    }

    public Long getDealer_Id() {
        return dealer_Id;
    }

    public void setDealer_Id(Long dealer_Id) {
        this.dealer_Id = dealer_Id;
    }

    public Double getBhtt_psc_25() {
        return bhtt_psc_25;
    }

    public void setBhtt_psc_25(Double bhtt_psc_25) {
        this.bhtt_psc_25 = bhtt_psc_25;
    }

    public Double getPsc_amount() {
        return psc_amount;
    }

    public void setPsc_amount(Double psc_amount) {
        this.psc_amount = psc_amount;
    }

    public Double getGtkh_psc_25k() {
        return gtkh_psc_25k;
    }

    public void setGtkh_psc_25k(Double gtkh_psc_25k) {
        this.gtkh_psc_25k = gtkh_psc_25k;
    }

    public Double getCard_quantity() {
        return card_quantity;
    }

    public void setCard_quantity(Double card_quantity) {
        this.card_quantity = card_quantity;
    }

    public Double getKit_quantity() {
        return kit_quantity;
    }

    public void setKit_quantity(Double kit_quantity) {
        this.kit_quantity = kit_quantity;
    }

    public Double getBhtt_quantity() {
        return bhtt_quantity;
    }

    public void setBhtt_quantity(Double bhtt_quantity) {
        this.bhtt_quantity = bhtt_quantity;
    }

    public Double getGtkh_quantity() {
        return gtkh_quantity;
    }

    public void setGtkh_quantity(Double gtkh_quantity) {
        this.gtkh_quantity = gtkh_quantity;
    }

    public Double getVas_quantity() {
        return vas_quantity;
    }

    public void setVas_quantity(Double vas_quantity) {
        this.vas_quantity = vas_quantity;
    }
}
