package com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/22/14
 * Time: 4:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class ThongTinThueBaoMaPhieuDTO implements Serializable {
    private static final long serialVersionUID = 5310361788746880139L;

    private String ma_phieu;
    private Timestamp ngay_ps;
    private Integer da_doi_qua;
    private Timestamp ngay_doi_qua;
    private String cua_hang_doi_qua_name;

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

    public String getCua_hang_doi_qua_name() {
        return cua_hang_doi_qua_name;
    }

    public void setCua_hang_doi_qua_name(String cua_hang_doi_qua_name) {
        this.cua_hang_doi_qua_name = cua_hang_doi_qua_name;
    }
}
