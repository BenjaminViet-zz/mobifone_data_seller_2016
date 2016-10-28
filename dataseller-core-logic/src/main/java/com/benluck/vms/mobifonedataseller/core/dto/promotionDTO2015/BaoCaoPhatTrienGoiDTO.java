package com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: vovanphuc0810
 * Date: 7/15/15
 * Time: 10:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class BaoCaoPhatTrienGoiDTO implements Serializable {
    private static final long serialVersionUID = 5805374617943857095L;

    private PromPackageDTO promPackageDTO;
    private Integer accumulated_quantity;
    private Integer new_reg_quantity;
    private Integer finished_quantity ;
    private Integer ThucTangGiam;
    private Integer renewal_quantity;
    private Integer tongSoGoi;
    private Double package_revenue;
    private Timestamp sumDate;
    private Timestamp fromDate;
    private Timestamp toDate;

    public PromPackageDTO getPromPackageDTO() {
        return promPackageDTO;
    }

    public void setPromPackageDTO(PromPackageDTO promPackageDTO) {
        this.promPackageDTO = promPackageDTO;
    }

    public Integer getAccumulated_quantity() {
        return accumulated_quantity;
    }

    public void setAccumulated_quantity(Integer accumulated_quantity) {
        this.accumulated_quantity = accumulated_quantity;
    }

    public Integer getNew_reg_quantity() {
        return new_reg_quantity;
    }

    public void setNew_reg_quantity(Integer new_reg_quantity) {
        this.new_reg_quantity = new_reg_quantity;
    }

    public Integer getFinished_quantity() {
        return finished_quantity;
    }

    public void setFinished_quantity(Integer finished_quantity) {
        this.finished_quantity = finished_quantity;
    }

    public Integer getThucTangGiam() {
        return ThucTangGiam;
    }

    public void setThucTangGiam(Integer thucTangGiam) {
        ThucTangGiam = thucTangGiam;
    }

    public Integer getRenewal_quantity() {
        return renewal_quantity;
    }

    public void setRenewal_quantity(Integer renewal_quantity) {
        this.renewal_quantity = renewal_quantity;
    }

    public Integer getTongSoGoi() {
        return tongSoGoi;
    }

    public void setTongSoGoi(Integer tongSoGoi) {
        this.tongSoGoi = tongSoGoi;
    }

    public Double getPackage_revenue() {
        return package_revenue;
    }

    public void setPackage_revenue(Double package_revenue) {
        this.package_revenue = package_revenue;
    }

    public Timestamp getSumDate() {
        return sumDate;
    }

    public void setSumDate(Timestamp sumDate) {
        this.sumDate = sumDate;
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
}
