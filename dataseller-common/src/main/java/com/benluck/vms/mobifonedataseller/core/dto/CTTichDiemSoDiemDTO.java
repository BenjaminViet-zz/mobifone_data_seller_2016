package com.benluck.vms.mobifonedataseller.core.dto;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by hieu
 * Date: 10/11/14
 * Time: 12:51 PM
 */
public class CTTichDiemSoDiemDTO implements Serializable{
    private Long id;
    private String thue_bao;
    private Timestamp ngay_ps;
    private Double so_diem;
    private Double so_tien_psc;
    private Integer da_cap_ma = -1;
    private Timestamp insert_date;
    private String type_input;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getThue_bao() {
        return thue_bao;
    }

    public void setThue_bao(String thue_bao) {
        this.thue_bao = thue_bao;
    }

    public Timestamp getNgay_ps() {
        return ngay_ps;
    }

    public void setNgay_ps(Timestamp ngay_ps) {
        this.ngay_ps = ngay_ps;
    }

    public Double getSo_diem() {
        return so_diem;
    }

    public void setSo_diem(Double so_diem) {
        this.so_diem = so_diem;
    }

    public void setSo_tien_psc(Double so_tien_psc) {
        this.so_tien_psc = so_tien_psc;
    }

    public Integer getDa_cap_ma() {
        return da_cap_ma;
    }

    public void setDa_cap_ma(Integer da_cap_ma) {
        this.da_cap_ma = da_cap_ma;
    }

    public Timestamp getInsert_date() {
        return insert_date;
    }

    public void setInsert_date(Timestamp insert_date) {
        this.insert_date = insert_date;
    }

    public String getType_input() {
        return type_input;
    }

    public void setType_input(String type_input) {
        this.type_input = type_input;
    }

    public Double getSo_tien_psc() {
        return so_tien_psc;
    }
}
