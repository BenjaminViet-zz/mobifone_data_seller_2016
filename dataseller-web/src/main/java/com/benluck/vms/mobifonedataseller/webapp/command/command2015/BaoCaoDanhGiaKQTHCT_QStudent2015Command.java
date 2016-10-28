package com.benluck.vms.mobifonedataseller.webapp.command.command2015;

import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015.BaoCaoDanhGiaKQTHCTSummary_QStudent2015DTO;
import com.benluck.vms.mobifonedataseller.webapp.command.AbstractCommand;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: vovanphuc0810
 * Date: 4/8/15
 * Time: 11:49 AM
 * To change this template use File | Settings | File Templates.
 */
public class BaoCaoDanhGiaKQTHCT_QStudent2015Command extends AbstractCommand<BaoCaoDanhGiaKQTHCTSummary_QStudent2015DTO> {
    public BaoCaoDanhGiaKQTHCT_QStudent2015Command(){
        this.pojo = new BaoCaoDanhGiaKQTHCTSummary_QStudent2015DTO();
    }

    private Date fromDateThamGia;
    private Date toDateThamGia;
    private Timestamp fromDateTimeThamGia;
    private Timestamp toDateTimeThamGia;
    private Long branchId;
    private Long districtId;
    private Integer reportSummary = 0;  // 1: report summary    0: report theo ngay.

    private Date fromDateReportFilter;
    private Date toDateReportFilter;
    private Timestamp fromDateTimeReportFilter;
    private Timestamp toDateTimeReportFilter;

    public Date getFromDateThamGia() {
        return fromDateThamGia;
    }

    public void setFromDateThamGia(Date fromDateThamGia) {
        this.fromDateThamGia = fromDateThamGia;
    }

    public Date getToDateThamGia() {
        return toDateThamGia;
    }

    public void setToDateThamGia(Date toDateThamGia) {
        this.toDateThamGia = toDateThamGia;
    }

    public Timestamp getFromDateTimeThamGia() {
        return fromDateTimeThamGia;
    }

    public void setFromDateTimeThamGia(Timestamp fromDateTimeThamGia) {
        this.fromDateTimeThamGia = fromDateTimeThamGia;
    }

    public Timestamp getToDateTimeThamGia() {
        return toDateTimeThamGia;
    }

    public void setToDateTimeThamGia(Timestamp toDateTimeThamGia) {
        this.toDateTimeThamGia = toDateTimeThamGia;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public Integer getReportSummary() {
        return reportSummary;
    }

    public void setReportSummary(Integer reportSummary) {
        this.reportSummary = reportSummary;
    }

    public Date getFromDateReportFilter() {
        return fromDateReportFilter;
    }

    public void setFromDateReportFilter(Date fromDateReportFilter) {
        this.fromDateReportFilter = fromDateReportFilter;
    }

    public Date getToDateReportFilter() {
        return toDateReportFilter;
    }

    public void setToDateReportFilter(Date toDateReportFilter) {
        this.toDateReportFilter = toDateReportFilter;
    }

    public Timestamp getFromDateTimeReportFilter() {
        return fromDateTimeReportFilter;
    }

    public void setFromDateTimeReportFilter(Timestamp fromDateTimeReportFilter) {
        this.fromDateTimeReportFilter = fromDateTimeReportFilter;
    }

    public Timestamp getToDateTimeReportFilter() {
        return toDateTimeReportFilter;
    }

    public void setToDateTimeReportFilter(Timestamp toDateTimeReportFilter) {
        this.toDateTimeReportFilter = toDateTimeReportFilter;
    }
}
