package com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/22/14
 * Time: 4:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class MaPhieuDTO implements Serializable{
    private static final long serialVersionUID = 2228708968985850676L;

    private String thue_bao;
    private String ma_phieu;
    private Timestamp ngay_ps;
    private Integer da_doi_qua = -1;
    private Timestamp ngay_doi_qua;
    private String user_name;
    private String shop_code;
    private String ngay_ps_date;

    public String getThue_bao() {
        return thue_bao;
    }

    public void setThue_bao(String thue_bao) {
        this.thue_bao = thue_bao;
    }

    public String getMa_phieu() {
        return ma_phieu;
    }

    public void setMa_phieu(String ma_phieu) {
        this.ma_phieu = ma_phieu;
    }

    public Timestamp getNgay_ps() {
        return ngay_ps;
    }

    public void setNgay_ps(Timestamp ngay_ps) {
        this.ngay_ps = ngay_ps;
    }

    public Integer getDa_doi_qua() {
        return da_doi_qua;
    }

    public void setDa_doi_qua(Integer da_doi_qua) {
        this.da_doi_qua = da_doi_qua;
    }

    public Timestamp getNgay_doi_qua() {
        return ngay_doi_qua;
    }

    public void setNgay_doi_qua(Timestamp ngay_doi_qua) {
        this.ngay_doi_qua = ngay_doi_qua;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getShop_code() {
        return shop_code;
    }

    public void setShop_code(String shop_code) {
        this.shop_code = shop_code;
    }

    public String getNgay_ps_date() {
        return ngay_ps_date;
    }

    public void setNgay_ps_date(String ngay_ps_date) {
        this.ngay_ps_date = ngay_ps_date;
    }
}
