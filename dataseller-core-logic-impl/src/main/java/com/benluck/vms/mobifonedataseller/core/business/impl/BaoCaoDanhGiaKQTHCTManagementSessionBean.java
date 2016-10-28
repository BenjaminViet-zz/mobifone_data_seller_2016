package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.core.business.BaoCaoDanhGiaKQTHCTManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BaoCaoDanhGiaKQTHCTChiTietTheoNgayDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BaoCaoDanhGiaKQTHCTSummaryDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015.BaoCaoDanhGiaKQTHCTChiTietTheoNgay_QStudent2015DTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015.BaoCaoDanhGiaKQTHCTSummary_QStudent2015DTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015.BaoCaoDanhGiaKetQuaThucHienCTTheoNgay_QStudent2015DTO;
import com.benluck.vms.mobifonedataseller.session.CTTichDiemLocalBean;
import com.benluck.vms.mobifonedataseller.session.ThueBaoPhatTrienMoiLocalBean;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/30/14
 * Time: 4:41 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "BaoCaoDanhGiaKQTHCTManagementSessionEJB")
public class BaoCaoDanhGiaKQTHCTManagementSessionBean implements BaoCaoDanhGiaKQTHCTManagementLocalBean{
    @EJB
    private CTTichDiemLocalBean ctTichDiemLocalBean;
    @EJB
    private ThueBaoPhatTrienMoiLocalBean thueBaoPhatTrienMoiLocalBean;

    public BaoCaoDanhGiaKQTHCTManagementSessionBean() {
    }

    @Override
    public BaoCaoDanhGiaKQTHCTSummaryDTO baoCaoDanhGiaKQTHCT_summary_tdcg(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) {
        Timestamp fromDateTimeThamGia = null;
        if(properties.get("fromDateTimeThamGia") != null){
            fromDateTimeThamGia = Timestamp.valueOf(properties.get("fromDateTimeThamGia").toString());
        }else{
            Calendar cal = Calendar.getInstance();
            cal.set(2014, 0, 1);
            fromDateTimeThamGia = new Timestamp(cal.getTimeInMillis());
        }
        Timestamp toDateTimeThamGia = null;
        if(properties.get("toDateTimeThamGia") != null){
            toDateTimeThamGia = Timestamp.valueOf(properties.get("toDateTimeThamGia").toString());
        }else{
            Calendar cal = Calendar.getInstance();
            toDateTimeThamGia = new Timestamp(cal.getTimeInMillis());
        }
        Timestamp fromDateTimeReportFilter = null;
        if(properties.get("fromDateTimeReportFilter") != null){
            fromDateTimeReportFilter = Timestamp.valueOf(properties.get("fromDateTimeReportFilter").toString());
        }else{
            Calendar cal = Calendar.getInstance();
            cal.set(2014, 0, 1);
            fromDateTimeReportFilter = new Timestamp(cal.getTimeInMillis());
        }
        Timestamp toDateTimeReportFilter = null;
        if(properties.get("toDateTimeReportFilter") != null){
            toDateTimeReportFilter = Timestamp.valueOf(properties.get("toDateTimeReportFilter").toString());
        }else{
            Calendar cal = Calendar.getInstance();
            toDateTimeReportFilter = new Timestamp(cal.getTimeInMillis());
        }

        Long branchId = null;
        Long districtId = null;
        if(properties.get("branchId") != null){
            branchId = Long.valueOf(properties.get("branchId").toString());
        }
        if(properties.get("districtId") != null){
            districtId = Long.valueOf(properties.get("districtId").toString());
        }

        BaoCaoDanhGiaKQTHCTSummaryDTO dto = new BaoCaoDanhGiaKQTHCTSummaryDTO();
        baoCaoDanhGiaKetQuaTH_tieuChi1_summary(dto, fromDateTimeThamGia, toDateTimeThamGia);
        baoCaoDanhGiaKetQuaTH_tieuChi2_summary(dto, fromDateTimeThamGia, toDateTimeThamGia);
        baoCaoDanhGiaKetQuaTH_tieuChi3And4_summary(dto, fromDateTimeThamGia, toDateTimeThamGia);
        baoCaoDanhGiaKetQuaTH_tieuChi5And6_summary(dto, fromDateTimeThamGia, toDateTimeThamGia, fromDateTimeReportFilter, toDateTimeReportFilter);
        baoCaoDanhGiaKetQuaTH_tieuChi7_summary(dto, fromDateTimeThamGia, toDateTimeThamGia);
        baoCaoDanhGiaKetQuaTH_tieuChi8_summary(dto, fromDateTimeThamGia, toDateTimeThamGia);
        baoCaoDanhGiaKetQuaTH_tieuChi9_summary(dto, fromDateTimeThamGia, toDateTimeThamGia);
        baoCaoDanhGiaKetQuaTH_tieuChi10_summary(dto, fromDateTimeThamGia, toDateTimeThamGia);
        return dto;
    }

    @Override
    public BaoCaoDanhGiaKQTHCTSummary_QStudent2015DTO baoCaoDanhGiaKQTHCT_summary_qStudent(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) {
        Timestamp fromDateTimeThamGia = null;
        if(properties.get("fromDateTimeThamGia") != null){
            fromDateTimeThamGia = Timestamp.valueOf(properties.get("fromDateTimeThamGia").toString());
        }else{
            Calendar cal = Calendar.getInstance();
            cal.set(2015, 0, 1);
            fromDateTimeThamGia = new Timestamp(cal.getTimeInMillis());
        }
        Timestamp toDateTimeThamGia = null;
        if(properties.get("toDateTimeThamGia") != null){
            toDateTimeThamGia = Timestamp.valueOf(properties.get("toDateTimeThamGia").toString());
        }else{
            Calendar cal = Calendar.getInstance();
            toDateTimeThamGia = new Timestamp(cal.getTimeInMillis());
        }
        Timestamp fromDateTimeReportFilter = null;
        if(properties.get("fromDateTimeReportFilter") != null){
            fromDateTimeReportFilter = Timestamp.valueOf(properties.get("fromDateTimeReportFilter").toString());
        }else{
            Calendar cal = Calendar.getInstance();
            cal.set(2015, 0, 1);
            fromDateTimeReportFilter = new Timestamp(cal.getTimeInMillis());
        }
        Timestamp toDateTimeReportFilter = null;
        if(properties.get("toDateTimeReportFilter") != null){
            toDateTimeReportFilter = Timestamp.valueOf(properties.get("toDateTimeReportFilter").toString());
        }else{
            Calendar cal = Calendar.getInstance();
            toDateTimeReportFilter = new Timestamp(cal.getTimeInMillis());
        }

        Long branchId = null;
        Long districtId = null;
        if(properties.get("branchId") != null){
            branchId = Long.valueOf(properties.get("branchId").toString());
        }
        if(properties.get("districtId") != null){
            districtId = Long.valueOf(properties.get("districtId").toString());
        }

        BaoCaoDanhGiaKQTHCTSummary_QStudent2015DTO dto = new BaoCaoDanhGiaKQTHCTSummary_QStudent2015DTO();
        baoCaoDanhGiaKetQuaTH_tieuChi1_summary_QStudent2015(dto, fromDateTimeThamGia, toDateTimeThamGia);
        baoCaoDanhGiaKetQuaTH_tieuChi2_summary_QStudent2015(dto, fromDateTimeThamGia, toDateTimeThamGia);
        baoCaoDanhGiaKetQuaTH_tieuChi3And4_summary_QStudent2015(dto, fromDateTimeThamGia, toDateTimeThamGia);
        baoCaoDanhGiaKetQuaTH_tieuChi5And6_summary_QStudent2015(dto, fromDateTimeThamGia, toDateTimeThamGia, fromDateTimeReportFilter, toDateTimeReportFilter);
        baoCaoDanhGiaKetQuaTH_tieuChi7_summary_QStudent2015(dto, fromDateTimeThamGia, toDateTimeThamGia);
        baoCaoDanhGiaKetQuaTH_tieuChi8_summary_QStudent2015(dto, fromDateTimeThamGia, toDateTimeThamGia);
        baoCaoDanhGiaKetQuaTH_tieuChi9_summary_QStudent2015(dto, fromDateTimeThamGia, toDateTimeThamGia);
        baoCaoDanhGiaKetQuaTH_tieuChi10_summary_QStudent2015(dto, fromDateTimeThamGia, toDateTimeThamGia);
        return dto;
    }

    @Override
    public BaoCaoDanhGiaKQTHCTChiTietTheoNgayDTO baoCaoDanhGiaKQTHCT_theoNgay_tdcg(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) {
        Timestamp fromDateTimeThamGia = null;
        if(properties.get("fromDateTimeThamGia") != null){
            fromDateTimeThamGia = Timestamp.valueOf(properties.get("fromDateTimeThamGia").toString());
        }else{
            Calendar cal = Calendar.getInstance();
            cal.set(2014, 0, 1);
            fromDateTimeThamGia = new Timestamp(cal.getTimeInMillis());
        }
        Timestamp toDateTimeThamGia = null;
        if(properties.get("toDateTimeThamGia") != null){
            toDateTimeThamGia = Timestamp.valueOf(properties.get("toDateTimeThamGia").toString());
        }else{
            Calendar cal = Calendar.getInstance();
            toDateTimeThamGia = new Timestamp(cal.getTimeInMillis());
        }
        Timestamp fromDateTimeReportFilter = null;
        if(properties.get("fromDateTimeReportFilter") != null){
            fromDateTimeReportFilter = Timestamp.valueOf(properties.get("fromDateTimeReportFilter").toString());
        }else{
            Calendar cal = Calendar.getInstance();
            cal.set(2014, 0, 1);
            fromDateTimeReportFilter = new Timestamp(cal.getTimeInMillis());
        }
        Timestamp toDateTimeReportFilter = null;
        if(properties.get("toDateTimeReportFilter") != null){
            toDateTimeReportFilter = Timestamp.valueOf(properties.get("toDateTimeReportFilter").toString());
        }else{
            Calendar cal = Calendar.getInstance();
            toDateTimeReportFilter = new Timestamp(cal.getTimeInMillis());
        }

        Long branchId = null;
        Long districtId = null;
        if(properties.get("branchId") != null){
            branchId = Long.valueOf(properties.get("branchId").toString());
        }
        if(properties.get("districtId") != null){
            districtId = Long.valueOf(properties.get("districtId").toString());
        }

        BaoCaoDanhGiaKQTHCTChiTietTheoNgayDTO dto = new BaoCaoDanhGiaKQTHCTChiTietTheoNgayDTO();
        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> danhSachNgayRender_tieuChi3And4 = buildDanhSachBaoCaoTheoNgay4Thang9();
        dto.setDanhSachNgayRender_tieuChi3And4(danhSachNgayRender_tieuChi3And4);
        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> danhSachNgayRender_tieuChi5And6 = buildDanhSachBaoCaoTheoNgay(fromDateTimeReportFilter, toDateTimeReportFilter);
        dto.setDanhSachNgayRender_tieuChi5And6(danhSachNgayRender_tieuChi5And6);

        baoCaoDanhGiaKetQuaTH_tieuChi1_theoNgay(dto, fromDateTimeThamGia, toDateTimeThamGia);
        baoCaoDanhGiaKetQuaTH_tieuChi2_theoNgay(dto, fromDateTimeThamGia, toDateTimeThamGia);
        baoCaoDanhGiaKetQuaTH_tieuChi3And4_theoNgay(dto, branchId, districtId, fromDateTimeThamGia, toDateTimeThamGia, fromDateTimeReportFilter, toDateTimeReportFilter);
        baoCaoDanhGiaKetQuaTH_tieuChi5And6_theoNgay(dto, branchId, districtId, fromDateTimeThamGia, toDateTimeThamGia, fromDateTimeReportFilter, toDateTimeReportFilter);
        baoCaoDanhGiaKetQuaTH_tieuChi7_theoNgay(dto, fromDateTimeThamGia, toDateTimeThamGia);
        baoCaoDanhGiaKetQuaTH_tieuChi8_theoNgay(dto, fromDateTimeThamGia, toDateTimeThamGia);
        baoCaoDanhGiaKetQuaTH_tieuChi9_theoNgay(dto, fromDateTimeThamGia, toDateTimeThamGia);
        baoCaoDanhGiaKetQuaTH_tieuChi10_theoNgay(dto, fromDateTimeThamGia, toDateTimeThamGia);
        return dto;
    }

    @Override
    public BaoCaoDanhGiaKQTHCTChiTietTheoNgay_QStudent2015DTO baoCaoDanhGiaKQTHCT_theoNgay_qStudent(Map<String, Object> properties, Integer firstItem, Integer maxPageItems, String sortExpression, String sortDirection) {
        Timestamp fromDateTimeThamGia = null;
        if(properties.get("fromDateTimeThamGia") != null){
            fromDateTimeThamGia = Timestamp.valueOf(properties.get("fromDateTimeThamGia").toString());
        }else{
            Calendar cal = Calendar.getInstance();
            cal.set(2015, 0, 1);
            fromDateTimeThamGia = new Timestamp(cal.getTimeInMillis());
        }
        Timestamp toDateTimeThamGia = null;
        if(properties.get("toDateTimeThamGia") != null){
            toDateTimeThamGia = Timestamp.valueOf(properties.get("toDateTimeThamGia").toString());
        }else{
            Calendar cal = Calendar.getInstance();
            toDateTimeThamGia = new Timestamp(cal.getTimeInMillis());
        }
        Timestamp fromDateTimeReportFilter = null;
        if(properties.get("fromDateTimeReportFilter") != null){
            fromDateTimeReportFilter = Timestamp.valueOf(properties.get("fromDateTimeReportFilter").toString());
        }else{
            Calendar cal = Calendar.getInstance();
            cal.set(2015, 0, 1);
            fromDateTimeReportFilter = new Timestamp(cal.getTimeInMillis());
        }
        Timestamp toDateTimeReportFilter = null;
        if(properties.get("toDateTimeReportFilter") != null){
            toDateTimeReportFilter = Timestamp.valueOf(properties.get("toDateTimeReportFilter").toString());
        }else{
            Calendar cal = Calendar.getInstance();
            toDateTimeReportFilter = new Timestamp(cal.getTimeInMillis());
        }

        Long branchId = null;
        Long districtId = null;
        if(properties.get("branchId") != null){
            branchId = Long.valueOf(properties.get("branchId").toString());
        }
        if(properties.get("districtId") != null){
            districtId = Long.valueOf(properties.get("districtId").toString());
        }

        BaoCaoDanhGiaKQTHCTChiTietTheoNgay_QStudent2015DTO dto = new BaoCaoDanhGiaKQTHCTChiTietTheoNgay_QStudent2015DTO();
        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgay_QStudent2015DTO> danhSachNgayRender_tieuChi3And4 = buildDanhSachBaoCaoTheoNgay1Thang3nam2015_QStudent();
        dto.setDanhSachNgayRender_tieuChi3And4(danhSachNgayRender_tieuChi3And4);
        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgay_QStudent2015DTO> danhSachNgayRender_tieuChi5And6 = buildDanhSachBaoCaoTheoNgay_QStudent(fromDateTimeReportFilter, toDateTimeReportFilter);
        dto.setDanhSachNgayRender_tieuChi5And6(danhSachNgayRender_tieuChi5And6);

        baoCaoDanhGiaKetQuaTH_tieuChi1_theoNgay_QStudent(dto, fromDateTimeThamGia, toDateTimeThamGia);
        baoCaoDanhGiaKetQuaTH_tieuChi2_theoNgay_QStudent(dto, fromDateTimeThamGia, toDateTimeThamGia);
        baoCaoDanhGiaKetQuaTH_tieuChi3And4_theoNgay_QStudent(dto, branchId, districtId, fromDateTimeThamGia, toDateTimeThamGia, fromDateTimeReportFilter, toDateTimeReportFilter);
        baoCaoDanhGiaKetQuaTH_tieuChi5And6_theoNgay_QStudent(dto, branchId, districtId, fromDateTimeThamGia, toDateTimeThamGia, fromDateTimeReportFilter, toDateTimeReportFilter);
        baoCaoDanhGiaKetQuaTH_tieuChi7_theoNgay_QStudent(dto, fromDateTimeThamGia, toDateTimeThamGia);
        baoCaoDanhGiaKetQuaTH_tieuChi8_theoNgay_QStudent(dto, fromDateTimeThamGia, toDateTimeThamGia);
        baoCaoDanhGiaKetQuaTH_tieuChi9_theoNgay_QStudent(dto, fromDateTimeThamGia, toDateTimeThamGia);
        baoCaoDanhGiaKetQuaTH_tieuChi10_theoNgay_QStudent(dto, fromDateTimeThamGia, toDateTimeThamGia);
        return dto;
    }

    private void baoCaoDanhGiaKetQuaTH_tieuChi1_summary(BaoCaoDanhGiaKQTHCTSummaryDTO dto, Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia){
        List resultSet = this.ctTichDiemLocalBean.baoCaoDanhGiaKetQuaTHCT_tieuChi1(fromDateTimeThamGia, toDateTimeThamGia);
        if(resultSet != null && resultSet.size() > 0){
            for(Object object : resultSet){
                Object[] tmpArr = (Object[])object;
                if(tmpArr[0] != null){
                    if(tmpArr[0].toString().equalsIgnoreCase("truoc")){
                        dto.setTieuChi1_tb_cu(tmpArr[1] != null ? Double.valueOf(tmpArr[1].toString()) : new Double(0));
                    }else if(tmpArr[0].toString().equalsIgnoreCase("sau")){
                        dto.setTieuChi1_tb_moi(tmpArr[1] != null ? Double.valueOf(tmpArr[1].toString()) : new Double(0));
                    }
                }
            }
        }
    }

    private void baoCaoDanhGiaKetQuaTH_tieuChi1_summary_QStudent2015(BaoCaoDanhGiaKQTHCTSummary_QStudent2015DTO dto, Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia){
        Long total = this.thueBaoPhatTrienMoiLocalBean.baoCaoDanhGiaKetQuaTHCT_tieuChi1(fromDateTimeThamGia, toDateTimeThamGia);
        if(total != null){
            dto.setTieuChi1_tb(total != null ? Double.valueOf(total.toString()) : new Double(0));
        }
    }

    private void baoCaoDanhGiaKetQuaTH_tieuChi1_theoNgay(BaoCaoDanhGiaKQTHCTChiTietTheoNgayDTO dto, Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia){
        List resultSet = this.ctTichDiemLocalBean.baoCaoDanhGiaKetQuaTHCT_tieuChi1(fromDateTimeThamGia, toDateTimeThamGia);
        if(resultSet != null && resultSet.size() > 0){
            for(Object object : resultSet){
                Object[] tmpArr = (Object[])object;
                if(tmpArr[0] != null){
                    if(tmpArr[0].toString().equalsIgnoreCase("truoc")){
                        dto.setTieuChi1_tb_cu(tmpArr[1] != null ? Double.valueOf(tmpArr[1].toString()) : new Double(0));
                    }else if(tmpArr[0].toString().equalsIgnoreCase("sau")){
                        dto.setTieuChi1_tb_moi(tmpArr[1] != null ? Double.valueOf(tmpArr[1].toString()) : new Double(0));
                    }
                }
            }
        }
    }
    private void baoCaoDanhGiaKetQuaTH_tieuChi1_theoNgay_QStudent(BaoCaoDanhGiaKQTHCTChiTietTheoNgay_QStudent2015DTO dto, Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia){
        Long total = this.thueBaoPhatTrienMoiLocalBean.baoCaoDanhGiaKetQuaTHCT_tieuChi1(fromDateTimeThamGia, toDateTimeThamGia);
        if(total != null){
            dto.setTieuChi1_tb(total != null ? Double.valueOf(total.toString()) : new Double(0));
        }
    }

    private void baoCaoDanhGiaKetQuaTH_tieuChi2_summary(BaoCaoDanhGiaKQTHCTSummaryDTO dto, Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia){
        List resultSet = this.ctTichDiemLocalBean.baoCaoDanhGiaKetQuaTHCT_tieuChi2(fromDateTimeThamGia, toDateTimeThamGia);
        if(resultSet != null && resultSet.size() > 0){
            if(resultSet != null && resultSet.size() > 0){
                for(Object object : resultSet){
                    Object[] tmpArr = (Object[])object;
                    if(tmpArr[0] != null){
                        if(tmpArr[0].toString().equalsIgnoreCase("truoc")){
                            dto.setTieuChi2_tb_cu(tmpArr[1] != null ? Double.valueOf(tmpArr[1].toString()) : new Double(0));
                        }else if(tmpArr[0].toString().equalsIgnoreCase("sau")){
                            dto.setTieuChi2_tb_moi(tmpArr[1] != null ? Double.valueOf(tmpArr[1].toString()) : new Double(0));
                        }
                    }
                }
            }
        }
    }
    private void baoCaoDanhGiaKetQuaTH_tieuChi2_summary_QStudent2015(BaoCaoDanhGiaKQTHCTSummary_QStudent2015DTO dto, Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia){
        Long total = this.thueBaoPhatTrienMoiLocalBean.baoCaoDanhGiaKetQuaTHCT_tieuChi2(fromDateTimeThamGia, toDateTimeThamGia);
        if(total != null){
            dto.setTieuChi2_tb(total != null ? Double.valueOf(total.toString()) : new Double(0));
        }
    }

    private void baoCaoDanhGiaKetQuaTH_tieuChi2_theoNgay(BaoCaoDanhGiaKQTHCTChiTietTheoNgayDTO dto, Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia){
        List resultSet = this.ctTichDiemLocalBean.baoCaoDanhGiaKetQuaTHCT_tieuChi2(fromDateTimeThamGia, toDateTimeThamGia);
        if(resultSet != null && resultSet.size() > 0){
            if(resultSet != null && resultSet.size() > 0){
                for(Object object : resultSet){
                    Object[] tmpArr = (Object[])object;
                    if(tmpArr[0] != null){
                        if(tmpArr[0].toString().equalsIgnoreCase("truoc")){
                            dto.setTieuChi2_tb_cu(tmpArr[1] != null ? Double.valueOf(tmpArr[1].toString()) : new Double(0));
                        }else if(tmpArr[0].toString().equalsIgnoreCase("sau")){
                            dto.setTieuChi2_tb_moi(tmpArr[1] != null ? Double.valueOf(tmpArr[1].toString()) : new Double(0));
                        }
                    }
                }
            }
        }
    }
    private void baoCaoDanhGiaKetQuaTH_tieuChi2_theoNgay_QStudent(BaoCaoDanhGiaKQTHCTChiTietTheoNgay_QStudent2015DTO dto, Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia){
        Long total = this.thueBaoPhatTrienMoiLocalBean.baoCaoDanhGiaKetQuaTHCT_tieuChi2(fromDateTimeThamGia, toDateTimeThamGia);
        if(total != null ){
            dto.setTieuChi2_tb(total != null ? Double.valueOf(total.toString()) : new Double(0));

        }
    }

    private void baoCaoDanhGiaKetQuaTH_tieuChi3And4_summary(BaoCaoDanhGiaKQTHCTSummaryDTO dto, Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia){
        List resultSet = this.ctTichDiemLocalBean.baoCaoDanhGiaKetQuaTHCT_tieuChi3And4Summary(fromDateTimeThamGia, toDateTimeThamGia);
        if(resultSet != null && resultSet.size() > 0){
            Double total_dt_tieuChi3_tkc_t9_truoc = new Double(0);
            Double total_dt_tieuChi3_tkc_t9_sau = new Double(0);

            Double total_dt_tieuChi4_tkt_t9_truoc = new Double(0);
            Double total_dt_tieuChi4_tkt_t9_sau = new Double(0);
            for(Object object : resultSet){
                Object[] tmpArr = (Object[])object;
                if(tmpArr[2] != null){
                    if(tmpArr[1] != null){
                        if(tmpArr[1].toString().equals("truoc")){
                            if(tmpArr[0] != null){
                                if(tmpArr[0].equals("t")){
                                    dto.setTieuChi3_t_cu(Double.valueOf(tmpArr[2].toString()));
                                    total_dt_tieuChi3_tkc_t9_truoc += dto.getTieuChi3_t_cu();
                                }else if(tmpArr[0].equals("s")){
                                    dto.setTieuChi3_s_cu(Double.valueOf(tmpArr[2].toString()));
                                    total_dt_tieuChi3_tkc_t9_truoc += dto.getTieuChi3_s_cu();
                                }else if(tmpArr[0].equals("d")){
                                    dto.setTieuChi3_d_cu(Double.valueOf(tmpArr[2].toString()));
                                    total_dt_tieuChi3_tkc_t9_truoc += dto.getTieuChi3_d_cu();
                                }else if(tmpArr[0].equals("v")){
                                    dto.setTieuChi3_v_cu(Double.valueOf(tmpArr[2].toString()));
                                    total_dt_tieuChi3_tkc_t9_truoc += dto.getTieuChi3_v_cu();
                                }else if(tmpArr[0].equals("r")){
                                    dto.setTieuChi3_r_cu(Double.valueOf(tmpArr[2].toString()));
                                    total_dt_tieuChi3_tkc_t9_truoc += dto.getTieuChi3_r_cu();
                                }else if(tmpArr[0].equals("k")){
                                    dto.setTieuChi3_k_cu(Double.valueOf(tmpArr[2].toString()));
                                    total_dt_tieuChi3_tkc_t9_truoc += dto.getTieuChi3_k_cu();
                                }
                            }
                        }else if(tmpArr[1].toString().equals("sau")){
                            if(tmpArr[0] != null){
                                if(tmpArr[0].equals("t")){
                                    dto.setTieuChi3_t_moi(Double.valueOf(tmpArr[2].toString()));
                                    total_dt_tieuChi3_tkc_t9_sau += dto.getTieuChi3_t_moi();
                                }else if(tmpArr[0].equals("s")){
                                    dto.setTieuChi3_s_moi(Double.valueOf(tmpArr[2].toString()));
                                    total_dt_tieuChi3_tkc_t9_sau += dto.getTieuChi3_s_moi();
                                }else if(tmpArr[0].equals("d")){
                                    dto.setTieuChi3_d_moi(Double.valueOf(tmpArr[2].toString()));
                                    total_dt_tieuChi3_tkc_t9_sau += dto.getTieuChi3_d_moi();
                                }else if(tmpArr[0].equals("v")){
                                    dto.setTieuChi3_v_moi(Double.valueOf(tmpArr[2].toString()));
                                    total_dt_tieuChi3_tkc_t9_sau += dto.getTieuChi3_v_moi();
                                }else if(tmpArr[0].equals("r")){
                                    dto.setTieuChi3_r_moi(Double.valueOf(tmpArr[2].toString()));
                                    total_dt_tieuChi3_tkc_t9_sau += dto.getTieuChi3_r_moi();
                                }else if(tmpArr[0].equals("k")){
                                    dto.setTieuChi3_k_moi(Double.valueOf(tmpArr[2].toString()));
                                    total_dt_tieuChi3_tkc_t9_sau += dto.getTieuChi3_k_moi();
                                }
                            }
                        }
                    }
                }
                if(tmpArr[3] != null){
                    if(tmpArr[1] != null){
                        if(tmpArr[0].toString().equals("truoc")){
                            if(tmpArr[0] != null){
                                if(tmpArr[0].equals("t")){
                                    dto.setTieuChi4_t_cu(Double.valueOf(tmpArr[3].toString()));
                                    total_dt_tieuChi4_tkt_t9_truoc += dto.getTieuChi4_t_cu();
                                }else if(tmpArr[0].equals("s")){
                                    dto.setTieuChi4_s_cu(Double.valueOf(tmpArr[3].toString()));
                                    total_dt_tieuChi4_tkt_t9_truoc += dto.getTieuChi4_s_cu();
                                }else if(tmpArr[0].equals("d")){
                                    dto.setTieuChi4_d_cu(Double.valueOf(tmpArr[3].toString()));
                                    total_dt_tieuChi4_tkt_t9_truoc += dto.getTieuChi4_d_cu();
                                }else if(tmpArr[0].equals("v")){
                                    dto.setTieuChi4_v_cu(Double.valueOf(tmpArr[3].toString()));
                                    total_dt_tieuChi4_tkt_t9_truoc += dto.getTieuChi4_v_cu();
                                }else if(tmpArr[0].equals("r")){
                                    dto.setTieuChi4_r_cu(Double.valueOf(tmpArr[3].toString()));
                                    total_dt_tieuChi4_tkt_t9_truoc += dto.getTieuChi4_r_cu();
                                }else if(tmpArr[0].equals("k")){
                                    dto.setTieuChi4_k_cu(Double.valueOf(tmpArr[3].toString()));
                                    total_dt_tieuChi4_tkt_t9_truoc += dto.getTieuChi4_k_cu();
                                }
                            }
                        }else if(tmpArr[1].toString().equals("sau")){
                            if(tmpArr[0] != null){
                                if(tmpArr[0].equals("t")){
                                    dto.setTieuChi4_t_moi(Double.valueOf(tmpArr[3].toString()));
                                    total_dt_tieuChi4_tkt_t9_sau += dto.getTieuChi4_t_moi();
                                }else if(tmpArr[0].equals("s")){
                                    dto.setTieuChi4_s_moi(Double.valueOf(tmpArr[3].toString()));
                                    total_dt_tieuChi4_tkt_t9_sau += dto.getTieuChi4_s_moi();
                                }else if(tmpArr[0].equals("d")){
                                    dto.setTieuChi4_d_moi(Double.valueOf(tmpArr[3].toString()));
                                    total_dt_tieuChi4_tkt_t9_sau += dto.getTieuChi4_d_moi();
                                }else if(tmpArr[0].equals("v")){
                                    dto.setTieuChi4_v_moi(Double.valueOf(tmpArr[3].toString()));
                                    total_dt_tieuChi4_tkt_t9_sau += dto.getTieuChi4_v_moi();
                                }else if(tmpArr[0].equals("r")){
                                    dto.setTieuChi4_r_moi(Double.valueOf(tmpArr[3].toString()));
                                    total_dt_tieuChi4_tkt_t9_sau += dto.getTieuChi4_r_moi();
                                }else if(tmpArr[0].equals("k")){
                                    dto.setTieuChi4_k_moi(Double.valueOf(tmpArr[3].toString()));
                                    total_dt_tieuChi4_tkt_t9_sau += dto.getTieuChi4_k_moi();
                                }
                            }
                        }
                    }
                }
            }

            dto.setTieuChi3_td_tkc_cu(total_dt_tieuChi3_tkc_t9_truoc);
            dto.setTieuChi3_td_tkc_moi(total_dt_tieuChi3_tkc_t9_sau);
            dto.setTieuChi4_td_tkt_cu(total_dt_tieuChi4_tkt_t9_truoc);
            dto.setTieuChi4_td_tkt_moi(total_dt_tieuChi4_tkt_t9_sau);
        }
    }
    private void baoCaoDanhGiaKetQuaTH_tieuChi3And4_summary_QStudent2015(BaoCaoDanhGiaKQTHCTSummary_QStudent2015DTO dto, Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia){
        List resultSet = this.thueBaoPhatTrienMoiLocalBean.baoCaoDanhGiaKetQuaTHCT_tieuChi3And4Summary(fromDateTimeThamGia, toDateTimeThamGia);
        if(resultSet != null && resultSet.size() > 0){
            Double total_dt_tieuChi3_tkc_t9 = new Double(0);

            Double total_dt_tieuChi4_tkt_t9 = new Double(0);
            for(Object object : resultSet){
                Object[] tmpArr = (Object[])object;
                if(tmpArr[1] != null){
                    if(tmpArr[0] != null){
                        if(tmpArr[0].equals("t")){
                            dto.setTieuChi3_t(Double.valueOf(tmpArr[1].toString()));
                            total_dt_tieuChi3_tkc_t9 += dto.getTieuChi3_t();
                        }else if(tmpArr[0].equals("s")){
                            dto.setTieuChi3_s(Double.valueOf(tmpArr[1].toString()));
                            total_dt_tieuChi3_tkc_t9 += dto.getTieuChi3_s();
                        }else if(tmpArr[0].equals("d")){
                            dto.setTieuChi3_d(Double.valueOf(tmpArr[1].toString()));
                            total_dt_tieuChi3_tkc_t9 += dto.getTieuChi3_d();
                        }else if(tmpArr[0].equals("v")){
                            dto.setTieuChi3_v(Double.valueOf(tmpArr[1].toString()));
                            total_dt_tieuChi3_tkc_t9 += dto.getTieuChi3_v();
                        }else if(tmpArr[0].equals("r")){
                            dto.setTieuChi3_r(Double.valueOf(tmpArr[1].toString()));
                            total_dt_tieuChi3_tkc_t9 += dto.getTieuChi3_r();
                        }else if(tmpArr[0].equals("k")){
                            dto.setTieuChi3_k(Double.valueOf(tmpArr[1].toString()));
                            total_dt_tieuChi3_tkc_t9 += dto.getTieuChi3_k();
                        }
                    }
                }
                if(tmpArr[2] != null){
                    if(tmpArr[0] != null){
                        if(tmpArr[0].equals("t")){
                            dto.setTieuChi4_t(Double.valueOf(tmpArr[2].toString()));
                            total_dt_tieuChi4_tkt_t9 += dto.getTieuChi4_t();
                        }else if(tmpArr[0].equals("s")){
                            dto.setTieuChi4_s(Double.valueOf(tmpArr[2].toString()));
                            total_dt_tieuChi4_tkt_t9 += dto.getTieuChi4_s();
                        }else if(tmpArr[0].equals("d")){
                            dto.setTieuChi4_d(Double.valueOf(tmpArr[2].toString()));
                            total_dt_tieuChi4_tkt_t9 += dto.getTieuChi4_d();
                        }else if(tmpArr[0].equals("v")){
                            dto.setTieuChi4_v(Double.valueOf(tmpArr[2].toString()));
                            total_dt_tieuChi4_tkt_t9 += dto.getTieuChi4_v();
                        }else if(tmpArr[0].equals("r")){
                            dto.setTieuChi4_r(Double.valueOf(tmpArr[2].toString()));
                            total_dt_tieuChi4_tkt_t9 += dto.getTieuChi4_r();
                        }else if(tmpArr[0].equals("k")){
                            dto.setTieuChi4_k(Double.valueOf(tmpArr[2].toString()));
                            total_dt_tieuChi4_tkt_t9 += dto.getTieuChi4_k();
                        }
                    }
                }
            }

            dto.setTieuChi3_td_tkc(total_dt_tieuChi3_tkc_t9);
            dto.setTieuChi4_td_tkt(total_dt_tieuChi4_tkt_t9);
        }
    }

    private List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> buildDanhSachBaoCaoTheoNgay4Thang9(){
        Calendar calThang9 = Calendar.getInstance();
        calThang9.set(2014,8,1);
        calThang9.set(Calendar.HOUR_OF_DAY, 0);
        calThang9.set(Calendar.MINUTE, 0);
        calThang9.set(Calendar.SECOND, 0);
        calThang9.set(Calendar.MILLISECOND, 0);
        Calendar firstDateInThang9 = calThang9;
        long startDateInMiliseconds = firstDateInThang9.getTimeInMillis();

        Calendar endDateInThang9 = calThang9;
        endDateInThang9.set(Calendar.DAY_OF_MONTH, calThang9.getActualMaximum(Calendar.DAY_OF_MONTH));
        long endDateInMiliseconds = endDateInThang9.getTimeInMillis();
        int oneDayInMiliseconds = 24 * 60 * 60 * 1000;
        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> danhSachNgayDTOList = new ArrayList<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO>();
        for(long time = startDateInMiliseconds; time <= endDateInMiliseconds; time += oneDayInMiliseconds){
            BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO dto = new BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO();
            dto.setDate(new Timestamp(time));
            danhSachNgayDTOList.add(dto);
        }
        return danhSachNgayDTOList;
    }
    private List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgay_QStudent2015DTO> buildDanhSachBaoCaoTheoNgay1Thang3nam2015_QStudent(){
        Calendar calThang3 = Calendar.getInstance();
        calThang3.set(2015,2,1);
        calThang3.set(Calendar.HOUR_OF_DAY, 0);
        calThang3.set(Calendar.MINUTE, 0);
        calThang3.set(Calendar.SECOND, 0);
        calThang3.set(Calendar.MILLISECOND, 0);
        Calendar firstDateInThang3 = calThang3;
        long startDateInMiliseconds = firstDateInThang3.getTimeInMillis();

        Calendar endDateInThang3 = calThang3;
        endDateInThang3.set(Calendar.DAY_OF_MONTH, calThang3.getActualMaximum(Calendar.DAY_OF_MONTH));
        long endDateInMiliseconds = endDateInThang3.getTimeInMillis();
        int oneDayInMiliseconds = 24 * 60 * 60 * 1000;
        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgay_QStudent2015DTO> danhSachNgayDTOList = new ArrayList<BaoCaoDanhGiaKetQuaThucHienCTTheoNgay_QStudent2015DTO>();
        for(long time = startDateInMiliseconds; time <= endDateInMiliseconds; time += oneDayInMiliseconds){
            BaoCaoDanhGiaKetQuaThucHienCTTheoNgay_QStudent2015DTO dto = new BaoCaoDanhGiaKetQuaThucHienCTTheoNgay_QStudent2015DTO();
            dto.setDate(new Timestamp(time));
            danhSachNgayDTOList.add(dto);
        }
        return danhSachNgayDTOList;
    }

    private List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> buildDanhSachBaoCaoTheoNgay(Timestamp fromDateTimeReportFilter, Timestamp toDateTimeReportFilter){
        long startDateInMiliseconds = fromDateTimeReportFilter.getTime();
        long endDateInMiliseconds = toDateTimeReportFilter.getTime();
        int oneDayInMiliseconds = 24 * 60 * 60 * 1000;
        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> danhSachNgayDTOList = new ArrayList<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO>();
        for(long time = startDateInMiliseconds; time <= endDateInMiliseconds; time += oneDayInMiliseconds){
            BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO dto = new BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO();
            dto.setDate(new Timestamp(time));
            danhSachNgayDTOList.add(dto);
        }
        return danhSachNgayDTOList;
    }
    private List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgay_QStudent2015DTO> buildDanhSachBaoCaoTheoNgay_QStudent(Timestamp fromDateTimeReportFilter, Timestamp toDateTimeReportFilter){
        long startDateInMiliseconds = fromDateTimeReportFilter.getTime();
        long endDateInMiliseconds = toDateTimeReportFilter.getTime();
        int oneDayInMiliseconds = 24 * 60 * 60 * 1000;
        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgay_QStudent2015DTO> danhSachNgayDTOList = new ArrayList<BaoCaoDanhGiaKetQuaThucHienCTTheoNgay_QStudent2015DTO>();
        for(long time = startDateInMiliseconds; time <= endDateInMiliseconds; time += oneDayInMiliseconds){
            BaoCaoDanhGiaKetQuaThucHienCTTheoNgay_QStudent2015DTO dto = new BaoCaoDanhGiaKetQuaThucHienCTTheoNgay_QStudent2015DTO();
            dto.setDate(new Timestamp(time));
            danhSachNgayDTOList.add(dto);
        }
        return danhSachNgayDTOList;
    }

    private void setDoanhThu2ChiTietNgay(Timestamp ngay_ps, List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> danhSachChiTietNgayDTOList, String input_type, Double doanhThu){
        for(BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO dto : danhSachChiTietNgayDTOList){
            if(dto.getDate().equals(ngay_ps)){
                if(input_type.equals("truoc")){
                    dto.setDoanhThu_truoc(dto.getDoanhThu_truoc() + doanhThu);
                }else if(input_type.equals("sau")){
                    dto.setDoanhThu_sau(dto.getDoanhThu_sau() + doanhThu);
                }
                break;
            }
        }
    }
    private void setDoanhThu2ChiTietNgay_QStudent(Timestamp ngay_ps, List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgay_QStudent2015DTO> danhSachChiTietNgayDTOList, Double doanhThu){
        for(BaoCaoDanhGiaKetQuaThucHienCTTheoNgay_QStudent2015DTO dto : danhSachChiTietNgayDTOList){
            if(dto.getDate().equals(ngay_ps)){
                dto.setDoanhThu(dto.getDoanhThu() + doanhThu);
                break;
            }
        }
    }

    private void baoCaoDanhGiaKetQuaTH_tieuChi3And4_theoNgay(BaoCaoDanhGiaKQTHCTChiTietTheoNgayDTO dto, Long branchId, Long districtId, Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia, Timestamp fromDateTimeReportFilter, Timestamp toDateTimeReportFilter){
        List resultSet = this.ctTichDiemLocalBean.baoCaoDanhGiaKetQuaTHCT_tieuChi3And4TheoNgay(branchId, districtId, fromDateTimeThamGia, toDateTimeThamGia);
        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> danhSachChiTietNgay_tieuChi3_summary = buildDanhSachBaoCaoTheoNgay4Thang9();
        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> danhSachChiTietNgay_tieuChi3_loai_t = buildDanhSachBaoCaoTheoNgay4Thang9();
        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> danhSachChiTietNgay_tieuChi3_loai_s = buildDanhSachBaoCaoTheoNgay4Thang9();
        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> danhSachChiTietNgay_tieuChi3_loai_d = buildDanhSachBaoCaoTheoNgay4Thang9();
        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> danhSachChiTietNgay_tieuChi3_loai_v = buildDanhSachBaoCaoTheoNgay4Thang9();
        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> danhSachChiTietNgay_tieuChi3_loai_r = buildDanhSachBaoCaoTheoNgay4Thang9();
        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> danhSachChiTietNgay_tieuChi3_loai_k = buildDanhSachBaoCaoTheoNgay4Thang9();
        dto.setTieuChi3_summary(danhSachChiTietNgay_tieuChi3_summary);
        dto.setTieuChi3_loai_t(danhSachChiTietNgay_tieuChi3_loai_t);
        dto.setTieuChi3_loai_s(danhSachChiTietNgay_tieuChi3_loai_s);
        dto.setTieuChi3_loai_d(danhSachChiTietNgay_tieuChi3_loai_d);
        dto.setTieuChi3_loai_v(danhSachChiTietNgay_tieuChi3_loai_v);
        dto.setTieuChi3_loai_r(danhSachChiTietNgay_tieuChi3_loai_r);
        dto.setTieuChi3_loai_k(danhSachChiTietNgay_tieuChi3_loai_k);

        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> danhSachChiTietNgay_tieuChi4_summary = buildDanhSachBaoCaoTheoNgay4Thang9();
        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> danhSachChiTietNgay_tieuChi4_loai_t = buildDanhSachBaoCaoTheoNgay4Thang9();
        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> danhSachChiTietNgay_tieuChi4_loai_s = buildDanhSachBaoCaoTheoNgay4Thang9();
        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> danhSachChiTietNgay_tieuChi4_loai_d = buildDanhSachBaoCaoTheoNgay4Thang9();
        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> danhSachChiTietNgay_tieuChi4_loai_v = buildDanhSachBaoCaoTheoNgay4Thang9();
        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> danhSachChiTietNgay_tieuChi4_loai_r = buildDanhSachBaoCaoTheoNgay4Thang9();
        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> danhSachChiTietNgay_tieuChi4_loai_k = buildDanhSachBaoCaoTheoNgay4Thang9();
        dto.setTieuChi4_summary(danhSachChiTietNgay_tieuChi4_summary);
        dto.setTieuChi4_loai_t(danhSachChiTietNgay_tieuChi4_loai_t);
        dto.setTieuChi4_loai_s(danhSachChiTietNgay_tieuChi4_loai_s);
        dto.setTieuChi4_loai_d(danhSachChiTietNgay_tieuChi4_loai_d);
        dto.setTieuChi4_loai_v(danhSachChiTietNgay_tieuChi4_loai_v);
        dto.setTieuChi4_loai_r(danhSachChiTietNgay_tieuChi4_loai_r);
        dto.setTieuChi4_loai_k(danhSachChiTietNgay_tieuChi4_loai_k);
        if(resultSet != null && resultSet.size() > 0){
            Double total_dt_tieuChi3_tkc_t9_truoc_loai_t = new Double(0);
            Double total_dt_tieuChi3_tkc_t9_truoc_loai_s = new Double(0);
            Double total_dt_tieuChi3_tkc_t9_truoc_loai_d = new Double(0);
            Double total_dt_tieuChi3_tkc_t9_truoc_loai_v = new Double(0);
            Double total_dt_tieuChi3_tkc_t9_truoc_loai_r = new Double(0);
            Double total_dt_tieuChi3_tkc_t9_truoc_loai_k = new Double(0);

            Double total_dt_tieuChi3_tkc_t9_sau_loai_t = new Double(0);
            Double total_dt_tieuChi3_tkc_t9_sau_loai_s = new Double(0);
            Double total_dt_tieuChi3_tkc_t9_sau_loai_d = new Double(0);
            Double total_dt_tieuChi3_tkc_t9_sau_loai_v = new Double(0);
            Double total_dt_tieuChi3_tkc_t9_sau_loai_r = new Double(0);
            Double total_dt_tieuChi3_tkc_t9_sau_loai_k = new Double(0);

            Double total_dt_tieuChi4_tkt_t9_truoc_loai_t = new Double(0);
            Double total_dt_tieuChi4_tkt_t9_truoc_loai_s = new Double(0);
            Double total_dt_tieuChi4_tkt_t9_truoc_loai_d = new Double(0);
            Double total_dt_tieuChi4_tkt_t9_truoc_loai_v = new Double(0);
            Double total_dt_tieuChi4_tkt_t9_truoc_loai_r = new Double(0);
            Double total_dt_tieuChi4_tkt_t9_truoc_loai_k = new Double(0);

            Double total_dt_tieuChi4_tkt_t9_sau_loai_t = new Double(0);
            Double total_dt_tieuChi4_tkt_t9_sau_loai_s = new Double(0);
            Double total_dt_tieuChi4_tkt_t9_sau_loai_d = new Double(0);
            Double total_dt_tieuChi4_tkt_t9_sau_loai_v = new Double(0);
            Double total_dt_tieuChi4_tkt_t9_sau_loai_r = new Double(0);
            Double total_dt_tieuChi4_tkt_t9_sau_loai_k = new Double(0);

            for(Object object : resultSet){
                Object[] tmpArr = (Object[])object;

                if(tmpArr[3] != null){
                    if(tmpArr[2] != null){
                        String input_type = tmpArr[2].toString().trim();
                        if(input_type.equals("truoc")){
                            if(tmpArr[1] != null){
                                Timestamp ngay_ps = Timestamp.valueOf(tmpArr[0].toString());
                                Double doanhThu = Double.valueOf(tmpArr[3].toString());
                                if(tmpArr[1].equals("t")){
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi3_loai_t(), input_type, doanhThu);
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi3_summary(), input_type, doanhThu);
                                    total_dt_tieuChi3_tkc_t9_truoc_loai_t += doanhThu;
                                }else if(tmpArr[1].equals("s")){
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi3_loai_s(), input_type, doanhThu);
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi3_summary(), input_type, doanhThu);
                                    total_dt_tieuChi3_tkc_t9_truoc_loai_s += doanhThu;
                                }else if(tmpArr[1].equals("d")){
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi3_loai_d(), input_type, doanhThu);
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi3_summary(), input_type, doanhThu);
                                    total_dt_tieuChi3_tkc_t9_truoc_loai_d += doanhThu;
                                }else if(tmpArr[1].equals("v")){
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi3_loai_v(), input_type, doanhThu);
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi3_summary(), input_type, doanhThu);
                                    total_dt_tieuChi3_tkc_t9_truoc_loai_v += doanhThu;
                                }else if(tmpArr[1].equals("r")){
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi3_loai_r(), input_type, doanhThu);
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi3_summary(), input_type, doanhThu);
                                    total_dt_tieuChi3_tkc_t9_truoc_loai_r += doanhThu;
                                }else if(tmpArr[1].equals("k")){
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi3_loai_k(), input_type, doanhThu);
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi3_summary(), input_type, doanhThu);
                                    total_dt_tieuChi3_tkc_t9_truoc_loai_k += doanhThu;
                                }
                            }
                        }else if(input_type.equals("sau")){
                            if(tmpArr[1] != null){
                                Timestamp ngay_ps = Timestamp.valueOf(tmpArr[0].toString());
                                Double doanhThu = Double.valueOf(tmpArr[3].toString());
                                if(tmpArr[1].equals("t")){
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi3_loai_t(), input_type, doanhThu);
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi3_summary(), input_type, doanhThu);
                                    total_dt_tieuChi3_tkc_t9_sau_loai_t += doanhThu;
                                }else if(tmpArr[1].equals("s")){
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi3_loai_s(), input_type, doanhThu);
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi3_summary(), input_type, doanhThu);
                                    total_dt_tieuChi3_tkc_t9_sau_loai_s += doanhThu;
                                }else if(tmpArr[1].equals("d")){
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi3_loai_d(), input_type, doanhThu);
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi3_summary(), input_type, doanhThu);
                                    total_dt_tieuChi3_tkc_t9_sau_loai_d += doanhThu;
                                }else if(tmpArr[1].equals("v")){
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi3_loai_v(), input_type, doanhThu);
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi3_summary(), input_type, doanhThu);
                                    total_dt_tieuChi3_tkc_t9_sau_loai_v += doanhThu;
                                }else if(tmpArr[1].equals("r")){
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi3_loai_r(), input_type, doanhThu);
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi3_summary(), input_type, doanhThu);
                                    total_dt_tieuChi3_tkc_t9_sau_loai_r += doanhThu;
                                }else if(tmpArr[1].equals("k")){
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi3_loai_k(), input_type, doanhThu);
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi3_summary(), input_type, doanhThu);
                                    total_dt_tieuChi3_tkc_t9_sau_loai_k += doanhThu;
                                }
                            }
                        }
                    }
                }
                if(tmpArr[4] != null){
                    if(tmpArr[2] != null){
                        String input_type = tmpArr[2].toString().trim();
                        if(input_type.equals("truoc")){
                            if(tmpArr[1] != null){
                                Timestamp ngay_ps = Timestamp.valueOf(tmpArr[0].toString());
                                Double doanhThu = Double.valueOf(tmpArr[4].toString());
                                if(tmpArr[1].equals("t")){
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi4_loai_t(), input_type, doanhThu);
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi4_summary(), input_type, doanhThu);
                                    total_dt_tieuChi4_tkt_t9_truoc_loai_t += doanhThu;
                                }else if(tmpArr[1].equals("s")){
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi4_loai_s(), input_type, doanhThu);
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi4_summary(), input_type, doanhThu);
                                    total_dt_tieuChi4_tkt_t9_truoc_loai_s += doanhThu;
                                }else if(tmpArr[1].equals("d")){
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi4_loai_d(), input_type, doanhThu);
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi4_summary(), input_type, doanhThu);
                                    total_dt_tieuChi4_tkt_t9_truoc_loai_d += doanhThu;
                                }else if(tmpArr[1].equals("v")){
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi4_loai_v(), input_type, doanhThu);
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi4_summary(), input_type, doanhThu);
                                    total_dt_tieuChi4_tkt_t9_truoc_loai_v += doanhThu;
                                }else if(tmpArr[1].equals("r")){
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi4_loai_r(), input_type, doanhThu);
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi4_summary(), input_type, doanhThu);
                                    total_dt_tieuChi4_tkt_t9_truoc_loai_r += doanhThu;
                                }else if(tmpArr[1].equals("k")){
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi4_loai_k(), input_type, doanhThu);
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi4_summary(), input_type, doanhThu);
                                    total_dt_tieuChi4_tkt_t9_truoc_loai_k += doanhThu;
                                }
                            }
                        }else if(tmpArr[2].toString().equals("sau")){
                            if(tmpArr[1] != null){
                                Timestamp ngay_ps = Timestamp.valueOf(tmpArr[0].toString());
                                Double doanhThu = Double.valueOf(tmpArr[4].toString());
                                if(tmpArr[1].equals("t")){
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi4_loai_t(), input_type, doanhThu);
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi4_summary(), input_type, doanhThu);
                                    total_dt_tieuChi4_tkt_t9_sau_loai_t += doanhThu;
                                }else if(tmpArr[1].equals("s")){
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi4_loai_t(), input_type, doanhThu);
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi4_summary(), input_type, doanhThu);
                                    total_dt_tieuChi4_tkt_t9_sau_loai_s += doanhThu;
                                }else if(tmpArr[1].equals("d")){
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi4_loai_t(), input_type, doanhThu);
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi4_summary(), input_type, doanhThu);
                                    total_dt_tieuChi4_tkt_t9_sau_loai_d += doanhThu;
                                }else if(tmpArr[1].equals("v")){
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi4_loai_t(), input_type, doanhThu);
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi4_summary(), input_type, doanhThu);
                                    total_dt_tieuChi4_tkt_t9_sau_loai_v += doanhThu;
                                }else if(tmpArr[1].equals("r")){
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi4_loai_t(), input_type, doanhThu);
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi4_summary(), input_type, doanhThu);
                                    total_dt_tieuChi4_tkt_t9_sau_loai_r += doanhThu;
                                }else if(tmpArr[1].equals("k")){
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi4_loai_t(), input_type, doanhThu);
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi4_summary(), input_type, doanhThu);
                                    total_dt_tieuChi4_tkt_t9_sau_loai_k += doanhThu;
                                }
                            }
                        }
                    }
                }
            }

            dto.setTotal_tieuChi3_truoc(total_dt_tieuChi3_tkc_t9_truoc_loai_t + total_dt_tieuChi3_tkc_t9_truoc_loai_s + total_dt_tieuChi3_tkc_t9_truoc_loai_d + total_dt_tieuChi3_tkc_t9_truoc_loai_v + total_dt_tieuChi3_tkc_t9_truoc_loai_r + total_dt_tieuChi3_tkc_t9_truoc_loai_k);
            dto.setTotal_tieuChi3_sau(total_dt_tieuChi3_tkc_t9_sau_loai_t + total_dt_tieuChi3_tkc_t9_sau_loai_s + total_dt_tieuChi3_tkc_t9_sau_loai_d + total_dt_tieuChi3_tkc_t9_sau_loai_v + total_dt_tieuChi3_tkc_t9_sau_loai_r + total_dt_tieuChi3_tkc_t9_sau_loai_k);
            dto.setTotal_tieuChi4_truoc(total_dt_tieuChi4_tkt_t9_truoc_loai_t + total_dt_tieuChi4_tkt_t9_truoc_loai_s + total_dt_tieuChi4_tkt_t9_truoc_loai_d + total_dt_tieuChi4_tkt_t9_truoc_loai_v + total_dt_tieuChi4_tkt_t9_truoc_loai_r + total_dt_tieuChi4_tkt_t9_truoc_loai_k);
            dto.setTotal_tieuChi4_sau(total_dt_tieuChi4_tkt_t9_sau_loai_t + total_dt_tieuChi4_tkt_t9_sau_loai_s + total_dt_tieuChi4_tkt_t9_sau_loai_d + total_dt_tieuChi4_tkt_t9_sau_loai_v + total_dt_tieuChi4_tkt_t9_sau_loai_r + total_dt_tieuChi4_tkt_t9_sau_loai_k);
        }
    }
    private void baoCaoDanhGiaKetQuaTH_tieuChi3And4_theoNgay_QStudent(BaoCaoDanhGiaKQTHCTChiTietTheoNgay_QStudent2015DTO dto, Long branchId, Long districtId, Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia, Timestamp fromDateTimeReportFilter, Timestamp toDateTimeReportFilter){
        List resultSet = this.thueBaoPhatTrienMoiLocalBean.baoCaoDanhGiaKetQuaTHCT_tieuChi3And4TheoNgay(branchId, districtId, fromDateTimeThamGia, toDateTimeThamGia);
        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgay_QStudent2015DTO> danhSachChiTietNgay_tieuChi3_summary = buildDanhSachBaoCaoTheoNgay1Thang3nam2015_QStudent();
        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgay_QStudent2015DTO> danhSachChiTietNgay_tieuChi3_loai_t = buildDanhSachBaoCaoTheoNgay1Thang3nam2015_QStudent();
        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgay_QStudent2015DTO> danhSachChiTietNgay_tieuChi3_loai_s = buildDanhSachBaoCaoTheoNgay1Thang3nam2015_QStudent();
        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgay_QStudent2015DTO> danhSachChiTietNgay_tieuChi3_loai_d = buildDanhSachBaoCaoTheoNgay1Thang3nam2015_QStudent();
        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgay_QStudent2015DTO> danhSachChiTietNgay_tieuChi3_loai_v = buildDanhSachBaoCaoTheoNgay1Thang3nam2015_QStudent();
        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgay_QStudent2015DTO> danhSachChiTietNgay_tieuChi3_loai_r = buildDanhSachBaoCaoTheoNgay1Thang3nam2015_QStudent();
        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgay_QStudent2015DTO> danhSachChiTietNgay_tieuChi3_loai_k = buildDanhSachBaoCaoTheoNgay1Thang3nam2015_QStudent();
        dto.setTieuChi3_summary(danhSachChiTietNgay_tieuChi3_summary);
        dto.setTieuChi3_loai_t(danhSachChiTietNgay_tieuChi3_loai_t);
        dto.setTieuChi3_loai_s(danhSachChiTietNgay_tieuChi3_loai_s);
        dto.setTieuChi3_loai_d(danhSachChiTietNgay_tieuChi3_loai_d);
        dto.setTieuChi3_loai_v(danhSachChiTietNgay_tieuChi3_loai_v);
        dto.setTieuChi3_loai_r(danhSachChiTietNgay_tieuChi3_loai_r);
        dto.setTieuChi3_loai_k(danhSachChiTietNgay_tieuChi3_loai_k);

        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgay_QStudent2015DTO> danhSachChiTietNgay_tieuChi4_summary = buildDanhSachBaoCaoTheoNgay1Thang3nam2015_QStudent();
        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgay_QStudent2015DTO> danhSachChiTietNgay_tieuChi4_loai_t = buildDanhSachBaoCaoTheoNgay1Thang3nam2015_QStudent();
        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgay_QStudent2015DTO> danhSachChiTietNgay_tieuChi4_loai_s = buildDanhSachBaoCaoTheoNgay1Thang3nam2015_QStudent();
        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgay_QStudent2015DTO> danhSachChiTietNgay_tieuChi4_loai_d = buildDanhSachBaoCaoTheoNgay1Thang3nam2015_QStudent();
        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgay_QStudent2015DTO> danhSachChiTietNgay_tieuChi4_loai_v = buildDanhSachBaoCaoTheoNgay1Thang3nam2015_QStudent();
        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgay_QStudent2015DTO> danhSachChiTietNgay_tieuChi4_loai_r = buildDanhSachBaoCaoTheoNgay1Thang3nam2015_QStudent();
        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgay_QStudent2015DTO> danhSachChiTietNgay_tieuChi4_loai_k = buildDanhSachBaoCaoTheoNgay1Thang3nam2015_QStudent();
        dto.setTieuChi4_summary(danhSachChiTietNgay_tieuChi4_summary);
        dto.setTieuChi4_loai_t(danhSachChiTietNgay_tieuChi4_loai_t);
        dto.setTieuChi4_loai_s(danhSachChiTietNgay_tieuChi4_loai_s);
        dto.setTieuChi4_loai_d(danhSachChiTietNgay_tieuChi4_loai_d);
        dto.setTieuChi4_loai_v(danhSachChiTietNgay_tieuChi4_loai_v);
        dto.setTieuChi4_loai_r(danhSachChiTietNgay_tieuChi4_loai_r);
        dto.setTieuChi4_loai_k(danhSachChiTietNgay_tieuChi4_loai_k);
        if(resultSet != null && resultSet.size() > 0){
            Double total_dt_tieuChi3_tkc_t3_loai_t = new Double(0);
            Double total_dt_tieuChi3_tkc_t3_loai_s = new Double(0);
            Double total_dt_tieuChi3_tkc_t3_loai_d = new Double(0);
            Double total_dt_tieuChi3_tkc_t3_loai_v = new Double(0);
            Double total_dt_tieuChi3_tkc_t3_loai_r = new Double(0);
            Double total_dt_tieuChi3_tkc_t3_loai_k = new Double(0);

            Double total_dt_tieuChi4_tkt_t3_loai_t = new Double(0);
            Double total_dt_tieuChi4_tkt_t3_loai_s = new Double(0);
            Double total_dt_tieuChi4_tkt_t3_loai_d = new Double(0);
            Double total_dt_tieuChi4_tkt_t3_loai_v = new Double(0);
            Double total_dt_tieuChi4_tkt_t3_loai_r = new Double(0);
            Double total_dt_tieuChi4_tkt_t3_loai_k = new Double(0);


            for(Object object : resultSet){
                Object[] tmpArr = (Object[])object;

                if(tmpArr[2] != null){
                    Timestamp ngay_ps = Timestamp.valueOf(tmpArr[0].toString());
                    Double doanhThu = Double.valueOf(tmpArr[2].toString());
                    if(tmpArr[1].equals("t")){
                        setDoanhThu2ChiTietNgay_QStudent(ngay_ps, dto.getTieuChi3_loai_t(), doanhThu);
                        setDoanhThu2ChiTietNgay_QStudent(ngay_ps, dto.getTieuChi3_summary(), doanhThu);
                        total_dt_tieuChi3_tkc_t3_loai_t += doanhThu;
                    }else if(tmpArr[1].equals("s")){
                        setDoanhThu2ChiTietNgay_QStudent(ngay_ps, dto.getTieuChi3_loai_s(), doanhThu);
                        setDoanhThu2ChiTietNgay_QStudent(ngay_ps, dto.getTieuChi3_summary(), doanhThu);
                        total_dt_tieuChi3_tkc_t3_loai_s += doanhThu;
                    }else if(tmpArr[1].equals("d")){
                        setDoanhThu2ChiTietNgay_QStudent(ngay_ps, dto.getTieuChi3_loai_d(), doanhThu);
                        setDoanhThu2ChiTietNgay_QStudent(ngay_ps, dto.getTieuChi3_summary(), doanhThu);
                        total_dt_tieuChi3_tkc_t3_loai_d += doanhThu;
                    }else if(tmpArr[1].equals("v")){
                        setDoanhThu2ChiTietNgay_QStudent(ngay_ps, dto.getTieuChi3_loai_v(), doanhThu);
                        setDoanhThu2ChiTietNgay_QStudent(ngay_ps, dto.getTieuChi3_summary(), doanhThu);
                        total_dt_tieuChi3_tkc_t3_loai_v += doanhThu;
                    }else if(tmpArr[1].equals("r")){
                        setDoanhThu2ChiTietNgay_QStudent(ngay_ps, dto.getTieuChi3_loai_r(), doanhThu);
                        setDoanhThu2ChiTietNgay_QStudent(ngay_ps, dto.getTieuChi3_summary(), doanhThu);
                        total_dt_tieuChi3_tkc_t3_loai_r += doanhThu;
                    }else if(tmpArr[1].equals("k")){
                        setDoanhThu2ChiTietNgay_QStudent(ngay_ps, dto.getTieuChi3_loai_k(), doanhThu);
                        setDoanhThu2ChiTietNgay_QStudent(ngay_ps, dto.getTieuChi3_summary(), doanhThu);
                        total_dt_tieuChi3_tkc_t3_loai_k += doanhThu;
                    }
                }
                if(tmpArr[3] != null){
                    if(tmpArr[1] != null){
                        Timestamp ngay_ps = Timestamp.valueOf(tmpArr[0].toString());
                        Double doanhThu = Double.valueOf(tmpArr[3].toString());
                        if(tmpArr[1].equals("t")){
                            setDoanhThu2ChiTietNgay_QStudent(ngay_ps, dto.getTieuChi4_loai_t(), doanhThu);
                            setDoanhThu2ChiTietNgay_QStudent(ngay_ps, dto.getTieuChi4_summary(), doanhThu);
                            total_dt_tieuChi4_tkt_t3_loai_t += doanhThu;
                        }else if(tmpArr[1].equals("s")){
                            setDoanhThu2ChiTietNgay_QStudent(ngay_ps, dto.getTieuChi4_loai_s(), doanhThu);
                            setDoanhThu2ChiTietNgay_QStudent(ngay_ps, dto.getTieuChi4_summary(), doanhThu);
                            total_dt_tieuChi4_tkt_t3_loai_s += doanhThu;
                        }else if(tmpArr[1].equals("d")){
                            setDoanhThu2ChiTietNgay_QStudent(ngay_ps, dto.getTieuChi4_loai_d(), doanhThu);
                            setDoanhThu2ChiTietNgay_QStudent(ngay_ps, dto.getTieuChi4_summary(), doanhThu);
                            total_dt_tieuChi4_tkt_t3_loai_d += doanhThu;
                        }else if(tmpArr[1].equals("v")){
                            setDoanhThu2ChiTietNgay_QStudent(ngay_ps, dto.getTieuChi4_loai_v(), doanhThu);
                            setDoanhThu2ChiTietNgay_QStudent(ngay_ps, dto.getTieuChi4_summary(), doanhThu);
                            total_dt_tieuChi4_tkt_t3_loai_v += doanhThu;
                        }else if(tmpArr[1].equals("r")){
                            setDoanhThu2ChiTietNgay_QStudent(ngay_ps, dto.getTieuChi4_loai_r(), doanhThu);
                            setDoanhThu2ChiTietNgay_QStudent(ngay_ps, dto.getTieuChi4_summary(), doanhThu);
                            total_dt_tieuChi4_tkt_t3_loai_r += doanhThu;
                        }else if(tmpArr[1].equals("k")){
                            setDoanhThu2ChiTietNgay_QStudent(ngay_ps, dto.getTieuChi4_loai_k(), doanhThu);
                            setDoanhThu2ChiTietNgay_QStudent(ngay_ps, dto.getTieuChi4_summary(), doanhThu);
                            total_dt_tieuChi4_tkt_t3_loai_k += doanhThu;
                        }
                    }
                }
            }

            dto.setTotal_tieuChi3(total_dt_tieuChi3_tkc_t3_loai_t + total_dt_tieuChi3_tkc_t3_loai_s + total_dt_tieuChi3_tkc_t3_loai_d + total_dt_tieuChi3_tkc_t3_loai_v + total_dt_tieuChi3_tkc_t3_loai_r + total_dt_tieuChi3_tkc_t3_loai_k);
            dto.setTotal_tieuChi4(total_dt_tieuChi4_tkt_t3_loai_t + total_dt_tieuChi4_tkt_t3_loai_s + total_dt_tieuChi4_tkt_t3_loai_d + total_dt_tieuChi4_tkt_t3_loai_v + total_dt_tieuChi4_tkt_t3_loai_r + total_dt_tieuChi4_tkt_t3_loai_k);
        }
    }

    private void baoCaoDanhGiaKetQuaTH_tieuChi5And6_theoNgay(BaoCaoDanhGiaKQTHCTChiTietTheoNgayDTO dto, Long branchId, Long districtId, Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia, Timestamp fromDateTimeReportFilter, Timestamp toDateTimeReportFilter){
        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> danhSachChiTietNgay_tieuChi5_summary = buildDanhSachBaoCaoTheoNgay(fromDateTimeReportFilter, toDateTimeReportFilter);
        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> danhSachChiTietNgay_tieuChi5_loai_t = buildDanhSachBaoCaoTheoNgay(fromDateTimeReportFilter, toDateTimeReportFilter);
        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> danhSachChiTietNgay_tieuChi5_loai_s = buildDanhSachBaoCaoTheoNgay(fromDateTimeReportFilter, toDateTimeReportFilter);
        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> danhSachChiTietNgay_tieuChi5_loai_d = buildDanhSachBaoCaoTheoNgay(fromDateTimeReportFilter, toDateTimeReportFilter);
        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> danhSachChiTietNgay_tieuChi5_loai_v = buildDanhSachBaoCaoTheoNgay(fromDateTimeReportFilter, toDateTimeReportFilter);
        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> danhSachChiTietNgay_tieuChi5_loai_r = buildDanhSachBaoCaoTheoNgay(fromDateTimeReportFilter, toDateTimeReportFilter);
        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> danhSachChiTietNgay_tieuChi5_loai_k = buildDanhSachBaoCaoTheoNgay(fromDateTimeReportFilter, toDateTimeReportFilter);
        dto.setTieuChi5_summary(danhSachChiTietNgay_tieuChi5_summary);
        dto.setTieuChi5_loai_t(danhSachChiTietNgay_tieuChi5_loai_t);
        dto.setTieuChi5_loai_s(danhSachChiTietNgay_tieuChi5_loai_s);
        dto.setTieuChi5_loai_d(danhSachChiTietNgay_tieuChi5_loai_d);
        dto.setTieuChi5_loai_v(danhSachChiTietNgay_tieuChi5_loai_v);
        dto.setTieuChi5_loai_r(danhSachChiTietNgay_tieuChi5_loai_r);
        dto.setTieuChi5_loai_k(danhSachChiTietNgay_tieuChi5_loai_k);


        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> danhSachChiTietNgay_tieuChi6_summary = buildDanhSachBaoCaoTheoNgay(fromDateTimeReportFilter, toDateTimeReportFilter);
        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> danhSachChiTietNgay_tieuChi6_loai_t = buildDanhSachBaoCaoTheoNgay(fromDateTimeReportFilter, toDateTimeReportFilter);
        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> danhSachChiTietNgay_tieuChi6_loai_s = buildDanhSachBaoCaoTheoNgay(fromDateTimeReportFilter, toDateTimeReportFilter);
        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> danhSachChiTietNgay_tieuChi6_loai_d = buildDanhSachBaoCaoTheoNgay(fromDateTimeReportFilter, toDateTimeReportFilter);
        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> danhSachChiTietNgay_tieuChi6_loai_v = buildDanhSachBaoCaoTheoNgay(fromDateTimeReportFilter, toDateTimeReportFilter);
        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> danhSachChiTietNgay_tieuChi6_loai_r = buildDanhSachBaoCaoTheoNgay(fromDateTimeReportFilter, toDateTimeReportFilter);
        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO> danhSachChiTietNgay_tieuChi6_loai_k = buildDanhSachBaoCaoTheoNgay(fromDateTimeReportFilter, toDateTimeReportFilter);
        dto.setTieuChi6_summary(danhSachChiTietNgay_tieuChi6_summary);
        dto.setTieuChi6_loai_t(danhSachChiTietNgay_tieuChi6_loai_t);
        dto.setTieuChi6_loai_s(danhSachChiTietNgay_tieuChi6_loai_s);
        dto.setTieuChi6_loai_d(danhSachChiTietNgay_tieuChi6_loai_d);
        dto.setTieuChi6_loai_v(danhSachChiTietNgay_tieuChi6_loai_v);
        dto.setTieuChi6_loai_r(danhSachChiTietNgay_tieuChi6_loai_r);
        dto.setTieuChi6_loai_k(danhSachChiTietNgay_tieuChi6_loai_k);
        List resultSet = this.ctTichDiemLocalBean.baoCaoDanhGiaKetQuaTHCT_tieuChi5And6TheoNgay(branchId, districtId, fromDateTimeThamGia, toDateTimeThamGia, fromDateTimeReportFilter, toDateTimeReportFilter);
        if(resultSet != null && resultSet.size() > 0){
            Double total_dt_tieuChi5_truoc_loai_t = new Double(0);
            Double total_dt_tieuChi5_truoc_loai_s = new Double(0);
            Double total_dt_tieuChi5_truoc_loai_d = new Double(0);
            Double total_dt_tieuChi5_truoc_loai_v = new Double(0);
            Double total_dt_tieuChi5_truoc_loai_r = new Double(0);
            Double total_dt_tieuChi5_truoc_loai_k = new Double(0);

            Double total_dt_tieuChi5_sau_loai_t = new Double(0);
            Double total_dt_tieuChi5_sau_loai_s = new Double(0);
            Double total_dt_tieuChi5_sau_loai_d = new Double(0);
            Double total_dt_tieuChi5_sau_loai_v = new Double(0);
            Double total_dt_tieuChi5_sau_loai_r = new Double(0);
            Double total_dt_tieuChi5_sau_loai_k = new Double(0);

            Double total_dt_tieuChi6_tkt_truoc_loai_t = new Double(0);
            Double total_dt_tieuChi6_tkt_truoc_loai_s = new Double(0);
            Double total_dt_tieuChi6_tkt_truoc_loai_d = new Double(0);
            Double total_dt_tieuChi6_tkt_truoc_loai_v = new Double(0);
            Double total_dt_tieuChi6_tkt_truoc_loai_r = new Double(0);
            Double total_dt_tieuChi6_tkt_truoc_loai_k = new Double(0);

            Double total_dt_tieuChi6_tkt_sau_loai_t = new Double(0);
            Double total_dt_tieuChi6_tkt_sau_loai_s = new Double(0);
            Double total_dt_tieuChi6_tkt_sau_loai_d = new Double(0);
            Double total_dt_tieuChi6_tkt_sau_loai_v = new Double(0);
            Double total_dt_tieuChi6_tkt_sau_loai_r = new Double(0);
            Double total_dt_tieuChi6_tkt_sau_loai_k = new Double(0);

            for(Object object : resultSet){
                Object[] tmpArr = (Object[])object;

                if(tmpArr[3] != null){
                    if(tmpArr[2] != null){
                        String input_type = tmpArr[2].toString().trim();
                        if(input_type.equals("truoc")){
                            if(tmpArr[1] != null){
                                Timestamp ngay_ps = Timestamp.valueOf(tmpArr[0].toString());
                                Double doanhThu = Double.valueOf(tmpArr[3].toString());
                                if(tmpArr[1].equals("t")){
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi5_loai_t(), input_type, doanhThu);
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi5_summary(), input_type, doanhThu);
                                    total_dt_tieuChi5_truoc_loai_t += doanhThu;
                                }else if(tmpArr[1].equals("s")){
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi5_loai_s(), input_type, doanhThu);
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi5_summary(), input_type, doanhThu);
                                    total_dt_tieuChi5_truoc_loai_s += doanhThu;
                                }else if(tmpArr[1].equals("d")){
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi5_loai_d(), input_type, doanhThu);
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi5_summary(), input_type, doanhThu);
                                    total_dt_tieuChi5_truoc_loai_d += doanhThu;
                                }else if(tmpArr[1].equals("v")){
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi5_loai_v(), input_type, doanhThu);
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi5_summary(), input_type, doanhThu);
                                    total_dt_tieuChi5_truoc_loai_v += doanhThu;
                                }else if(tmpArr[1].equals("r")){
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi5_loai_r(), input_type, doanhThu);
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi5_summary(), input_type, doanhThu);
                                    total_dt_tieuChi5_truoc_loai_r += doanhThu;
                                }else if(tmpArr[1].equals("k")){
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi5_loai_k(), input_type, doanhThu);
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi5_summary(), input_type, doanhThu);
                                    total_dt_tieuChi5_truoc_loai_k += doanhThu;
                                }
                            }
                        }else if(input_type.equals("sau")){
                            if(tmpArr[1] != null){
                                Timestamp ngay_ps = Timestamp.valueOf(tmpArr[0].toString());
                                Double doanhThu = Double.valueOf(tmpArr[3].toString());
                                if(tmpArr[1].equals("t")){
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi5_loai_t(), input_type, doanhThu);
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi5_summary(), input_type, doanhThu);
                                    total_dt_tieuChi5_sau_loai_t += doanhThu;
                                }else if(tmpArr[1].equals("s")){
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi5_loai_s(), input_type, doanhThu);
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi5_summary(), input_type, doanhThu);
                                    total_dt_tieuChi5_sau_loai_s += doanhThu;
                                }else if(tmpArr[1].equals("d")){
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi5_loai_d(), input_type, doanhThu);
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi5_summary(), input_type, doanhThu);
                                    total_dt_tieuChi5_sau_loai_d += doanhThu;
                                }else if(tmpArr[1].equals("v")){
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi5_loai_v(), input_type, doanhThu);
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi5_summary(), input_type, doanhThu);
                                    total_dt_tieuChi5_sau_loai_v += doanhThu;
                                }else if(tmpArr[1].equals("r")){
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi5_loai_r(), input_type, doanhThu);
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi5_summary(), input_type, doanhThu);
                                    total_dt_tieuChi5_sau_loai_r += doanhThu;
                                }else if(tmpArr[1].equals("k")){
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi5_loai_k(), input_type, doanhThu);
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi5_summary(), input_type, doanhThu);
                                    total_dt_tieuChi5_sau_loai_k += doanhThu;
                                }
                            }
                        }
                    }
                }
                if(tmpArr[4] != null){
                    if(tmpArr[2] != null){
                        String input_type = tmpArr[2].toString().trim();
                        if(input_type.equals("truoc")){
                            if(tmpArr[1] != null){
                                Timestamp ngay_ps = Timestamp.valueOf(tmpArr[0].toString());
                                Double doanhThu = Double.valueOf(tmpArr[4].toString());
                                if(tmpArr[1].equals("t")){
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi6_loai_t(), input_type, doanhThu);
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi6_summary(), input_type, doanhThu);
                                    total_dt_tieuChi6_tkt_truoc_loai_t += doanhThu;
                                }else if(tmpArr[1].equals("s")){
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi6_loai_s(), input_type, doanhThu);
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi6_summary(), input_type, doanhThu);
                                    total_dt_tieuChi6_tkt_truoc_loai_s += doanhThu;
                                }else if(tmpArr[1].equals("d")){
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi6_loai_d(), input_type, doanhThu);
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi6_summary(), input_type, doanhThu);
                                    total_dt_tieuChi6_tkt_truoc_loai_d += doanhThu;
                                }else if(tmpArr[1].equals("v")){
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi6_loai_v(), input_type, doanhThu);
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi6_summary(), input_type, doanhThu);
                                    total_dt_tieuChi6_tkt_truoc_loai_v += doanhThu;
                                }else if(tmpArr[1].equals("r")){
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi6_loai_r(), input_type, doanhThu);
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi6_summary(), input_type, doanhThu);
                                    total_dt_tieuChi6_tkt_truoc_loai_r += doanhThu;
                                }else if(tmpArr[1].equals("k")){
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi6_loai_k(), input_type, doanhThu);
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi6_summary(), input_type, doanhThu);
                                    total_dt_tieuChi6_tkt_truoc_loai_k += doanhThu;
                                }
                            }
                        }else if(tmpArr[2].toString().equals("sau")){
                            if(tmpArr[1] != null){
                                Timestamp ngay_ps = Timestamp.valueOf(tmpArr[0].toString());
                                Double doanhThu = Double.valueOf(tmpArr[4].toString());
                                if(tmpArr[1].equals("t")){
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi6_loai_t(), input_type, doanhThu);
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi6_summary(), input_type, doanhThu);
                                    total_dt_tieuChi6_tkt_sau_loai_t += doanhThu;
                                }else if(tmpArr[1].equals("s")){
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi6_loai_t(), input_type, doanhThu);
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi6_summary(), input_type, doanhThu);
                                    total_dt_tieuChi6_tkt_sau_loai_s += doanhThu;
                                }else if(tmpArr[1].equals("d")){
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi6_loai_t(), input_type, doanhThu);
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi6_summary(), input_type, doanhThu);
                                    total_dt_tieuChi6_tkt_sau_loai_d += doanhThu;
                                }else if(tmpArr[1].equals("v")){
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi6_loai_t(), input_type, doanhThu);
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi6_summary(), input_type, doanhThu);
                                    total_dt_tieuChi6_tkt_sau_loai_v += doanhThu;
                                }else if(tmpArr[1].equals("r")){
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi6_loai_t(), input_type, doanhThu);
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi6_summary(), input_type, doanhThu);
                                    total_dt_tieuChi6_tkt_sau_loai_r += doanhThu;
                                }else if(tmpArr[1].equals("k")){
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi6_loai_t(), input_type, doanhThu);
                                    setDoanhThu2ChiTietNgay(ngay_ps, dto.getTieuChi6_summary(), input_type, doanhThu);
                                    total_dt_tieuChi6_tkt_sau_loai_k += doanhThu;
                                }
                            }
                        }
                    }
                }
            }

            dto.setTotal_tieuChi5_truoc(total_dt_tieuChi5_truoc_loai_t + total_dt_tieuChi5_truoc_loai_s + total_dt_tieuChi5_truoc_loai_d + total_dt_tieuChi5_truoc_loai_v + total_dt_tieuChi5_truoc_loai_r + total_dt_tieuChi5_truoc_loai_k);
            dto.setTotal_tieuChi5_sau(total_dt_tieuChi5_sau_loai_t + total_dt_tieuChi5_sau_loai_s + total_dt_tieuChi5_sau_loai_d + total_dt_tieuChi5_sau_loai_v + total_dt_tieuChi5_sau_loai_r + total_dt_tieuChi5_sau_loai_k);
            dto.setTotal_tieuChi6_truoc(total_dt_tieuChi6_tkt_truoc_loai_t + total_dt_tieuChi6_tkt_truoc_loai_s + total_dt_tieuChi6_tkt_truoc_loai_d + total_dt_tieuChi6_tkt_truoc_loai_v + total_dt_tieuChi6_tkt_truoc_loai_r + total_dt_tieuChi6_tkt_truoc_loai_k);
            dto.setTotal_tieuChi6_sau(total_dt_tieuChi6_tkt_sau_loai_t + total_dt_tieuChi6_tkt_sau_loai_s + total_dt_tieuChi6_tkt_sau_loai_d + total_dt_tieuChi6_tkt_sau_loai_v + total_dt_tieuChi6_tkt_sau_loai_r + total_dt_tieuChi6_tkt_sau_loai_k);
        }
    }
    private void baoCaoDanhGiaKetQuaTH_tieuChi5And6_theoNgay_QStudent(BaoCaoDanhGiaKQTHCTChiTietTheoNgay_QStudent2015DTO dto, Long branchId, Long districtId, Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia, Timestamp fromDateTimeReportFilter, Timestamp toDateTimeReportFilter){
        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgay_QStudent2015DTO> danhSachChiTietNgay_tieuChi5_summary = buildDanhSachBaoCaoTheoNgay_QStudent(fromDateTimeReportFilter, toDateTimeReportFilter);
        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgay_QStudent2015DTO> danhSachChiTietNgay_tieuChi5_loai_t = buildDanhSachBaoCaoTheoNgay_QStudent(fromDateTimeReportFilter, toDateTimeReportFilter);
        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgay_QStudent2015DTO> danhSachChiTietNgay_tieuChi5_loai_s = buildDanhSachBaoCaoTheoNgay_QStudent(fromDateTimeReportFilter, toDateTimeReportFilter);
        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgay_QStudent2015DTO> danhSachChiTietNgay_tieuChi5_loai_d = buildDanhSachBaoCaoTheoNgay_QStudent(fromDateTimeReportFilter, toDateTimeReportFilter);
        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgay_QStudent2015DTO> danhSachChiTietNgay_tieuChi5_loai_v = buildDanhSachBaoCaoTheoNgay_QStudent(fromDateTimeReportFilter, toDateTimeReportFilter);
        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgay_QStudent2015DTO> danhSachChiTietNgay_tieuChi5_loai_r = buildDanhSachBaoCaoTheoNgay_QStudent(fromDateTimeReportFilter, toDateTimeReportFilter);
        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgay_QStudent2015DTO> danhSachChiTietNgay_tieuChi5_loai_k = buildDanhSachBaoCaoTheoNgay_QStudent(fromDateTimeReportFilter, toDateTimeReportFilter);
        dto.setTieuChi5_summary(danhSachChiTietNgay_tieuChi5_summary);
        dto.setTieuChi5_loai_t(danhSachChiTietNgay_tieuChi5_loai_t);
        dto.setTieuChi5_loai_s(danhSachChiTietNgay_tieuChi5_loai_s);
        dto.setTieuChi5_loai_d(danhSachChiTietNgay_tieuChi5_loai_d);
        dto.setTieuChi5_loai_v(danhSachChiTietNgay_tieuChi5_loai_v);
        dto.setTieuChi5_loai_r(danhSachChiTietNgay_tieuChi5_loai_r);
        dto.setTieuChi5_loai_k(danhSachChiTietNgay_tieuChi5_loai_k);


        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgay_QStudent2015DTO> danhSachChiTietNgay_tieuChi6_summary = buildDanhSachBaoCaoTheoNgay_QStudent(fromDateTimeReportFilter, toDateTimeReportFilter);
        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgay_QStudent2015DTO> danhSachChiTietNgay_tieuChi6_loai_t = buildDanhSachBaoCaoTheoNgay_QStudent(fromDateTimeReportFilter, toDateTimeReportFilter);
        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgay_QStudent2015DTO> danhSachChiTietNgay_tieuChi6_loai_s = buildDanhSachBaoCaoTheoNgay_QStudent(fromDateTimeReportFilter, toDateTimeReportFilter);
        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgay_QStudent2015DTO> danhSachChiTietNgay_tieuChi6_loai_d = buildDanhSachBaoCaoTheoNgay_QStudent(fromDateTimeReportFilter, toDateTimeReportFilter);
        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgay_QStudent2015DTO> danhSachChiTietNgay_tieuChi6_loai_v = buildDanhSachBaoCaoTheoNgay_QStudent(fromDateTimeReportFilter, toDateTimeReportFilter);
        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgay_QStudent2015DTO> danhSachChiTietNgay_tieuChi6_loai_r = buildDanhSachBaoCaoTheoNgay_QStudent(fromDateTimeReportFilter, toDateTimeReportFilter);
        List<BaoCaoDanhGiaKetQuaThucHienCTTheoNgay_QStudent2015DTO> danhSachChiTietNgay_tieuChi6_loai_k = buildDanhSachBaoCaoTheoNgay_QStudent(fromDateTimeReportFilter, toDateTimeReportFilter);
        dto.setTieuChi6_summary(danhSachChiTietNgay_tieuChi6_summary);
        dto.setTieuChi6_loai_t(danhSachChiTietNgay_tieuChi6_loai_t);
        dto.setTieuChi6_loai_s(danhSachChiTietNgay_tieuChi6_loai_s);
        dto.setTieuChi6_loai_d(danhSachChiTietNgay_tieuChi6_loai_d);
        dto.setTieuChi6_loai_v(danhSachChiTietNgay_tieuChi6_loai_v);
        dto.setTieuChi6_loai_r(danhSachChiTietNgay_tieuChi6_loai_r);
        dto.setTieuChi6_loai_k(danhSachChiTietNgay_tieuChi6_loai_k);
        List resultSet = this.thueBaoPhatTrienMoiLocalBean.baoCaoDanhGiaKetQuaTHCT_tieuChi5And6TheoNgay(branchId, districtId, fromDateTimeThamGia, toDateTimeThamGia, fromDateTimeReportFilter, toDateTimeReportFilter);
        if(resultSet != null && resultSet.size() > 0){
            Double total_dt_tieuChi5_loai_t = new Double(0);
            Double total_dt_tieuChi5_loai_s = new Double(0);
            Double total_dt_tieuChi5_loai_d = new Double(0);
            Double total_dt_tieuChi5_loai_v = new Double(0);
            Double total_dt_tieuChi5_loai_r = new Double(0);
            Double total_dt_tieuChi5_loai_k = new Double(0);

            Double total_dt_tieuChi6_tkt_loai_t = new Double(0);
            Double total_dt_tieuChi6_tkt_loai_s = new Double(0);
            Double total_dt_tieuChi6_tkt_loai_d = new Double(0);
            Double total_dt_tieuChi6_tkt_loai_v = new Double(0);
            Double total_dt_tieuChi6_tkt_loai_r = new Double(0);
            Double total_dt_tieuChi6_tkt_loai_k = new Double(0);


            for(Object object : resultSet){
                Object[] tmpArr = (Object[])object;

                if(tmpArr[2] != null){
                    if(tmpArr[1] != null){
                        Timestamp ngay_ps = Timestamp.valueOf(tmpArr[0].toString());
                        Double doanhThu = Double.valueOf(tmpArr[2].toString());
                        if(tmpArr[1].equals("t")){
                            setDoanhThu2ChiTietNgay_QStudent(ngay_ps, dto.getTieuChi5_loai_t(),  doanhThu);
                            setDoanhThu2ChiTietNgay_QStudent(ngay_ps, dto.getTieuChi5_summary(),  doanhThu);
                            total_dt_tieuChi5_loai_t += doanhThu;
                        }else if(tmpArr[1].equals("s")){
                            setDoanhThu2ChiTietNgay_QStudent(ngay_ps, dto.getTieuChi5_loai_s(),  doanhThu);
                            setDoanhThu2ChiTietNgay_QStudent(ngay_ps, dto.getTieuChi5_summary(),  doanhThu);
                            total_dt_tieuChi5_loai_s += doanhThu;
                        }else if(tmpArr[1].equals("d")){
                            setDoanhThu2ChiTietNgay_QStudent(ngay_ps, dto.getTieuChi5_loai_d(),  doanhThu);
                            setDoanhThu2ChiTietNgay_QStudent(ngay_ps, dto.getTieuChi5_summary(),  doanhThu);
                            total_dt_tieuChi5_loai_d += doanhThu;
                        }else if(tmpArr[1].equals("v")){
                            setDoanhThu2ChiTietNgay_QStudent(ngay_ps, dto.getTieuChi5_loai_v(),  doanhThu);
                            setDoanhThu2ChiTietNgay_QStudent(ngay_ps, dto.getTieuChi5_summary(),  doanhThu);
                            total_dt_tieuChi5_loai_v += doanhThu;
                        }else if(tmpArr[1].equals("r")){
                            setDoanhThu2ChiTietNgay_QStudent(ngay_ps, dto.getTieuChi5_loai_r(),  doanhThu);
                            setDoanhThu2ChiTietNgay_QStudent(ngay_ps, dto.getTieuChi5_summary(),  doanhThu);
                            total_dt_tieuChi5_loai_r += doanhThu;
                        }else if(tmpArr[1].equals("k")){
                            setDoanhThu2ChiTietNgay_QStudent(ngay_ps, dto.getTieuChi5_loai_k(),  doanhThu);
                            setDoanhThu2ChiTietNgay_QStudent(ngay_ps, dto.getTieuChi5_summary(),  doanhThu);
                            total_dt_tieuChi5_loai_k += doanhThu;
                        }
                    }
                }
                if(tmpArr[3] != null){
                    if(tmpArr[1] != null){
                        Timestamp ngay_ps = Timestamp.valueOf(tmpArr[0].toString());
                        Double doanhThu = Double.valueOf(tmpArr[3].toString());
                        if(tmpArr[1].equals("t")){
                            setDoanhThu2ChiTietNgay_QStudent(ngay_ps, dto.getTieuChi6_loai_t(),  doanhThu);
                            setDoanhThu2ChiTietNgay_QStudent(ngay_ps, dto.getTieuChi6_summary(),  doanhThu);
                            total_dt_tieuChi6_tkt_loai_t += doanhThu;
                        }else if(tmpArr[1].equals("s")){
                            setDoanhThu2ChiTietNgay_QStudent(ngay_ps, dto.getTieuChi6_loai_s(),  doanhThu);
                            setDoanhThu2ChiTietNgay_QStudent(ngay_ps, dto.getTieuChi6_summary(),  doanhThu);
                            total_dt_tieuChi6_tkt_loai_s += doanhThu;
                        }else if(tmpArr[1].equals("d")){
                            setDoanhThu2ChiTietNgay_QStudent(ngay_ps, dto.getTieuChi6_loai_d(),  doanhThu);
                            setDoanhThu2ChiTietNgay_QStudent(ngay_ps, dto.getTieuChi6_summary(),  doanhThu);
                            total_dt_tieuChi6_tkt_loai_d += doanhThu;
                        }else if(tmpArr[1].equals("v")){
                            setDoanhThu2ChiTietNgay_QStudent(ngay_ps, dto.getTieuChi6_loai_v(),  doanhThu);
                            setDoanhThu2ChiTietNgay_QStudent(ngay_ps, dto.getTieuChi6_summary(),  doanhThu);
                            total_dt_tieuChi6_tkt_loai_v += doanhThu;
                        }else if(tmpArr[1].equals("r")){
                            setDoanhThu2ChiTietNgay_QStudent(ngay_ps, dto.getTieuChi6_loai_r(),  doanhThu);
                            setDoanhThu2ChiTietNgay_QStudent(ngay_ps, dto.getTieuChi6_summary(),  doanhThu);
                            total_dt_tieuChi6_tkt_loai_r += doanhThu;
                        }else if(tmpArr[1].equals("k")){
                            setDoanhThu2ChiTietNgay_QStudent(ngay_ps, dto.getTieuChi6_loai_k(),  doanhThu);
                            setDoanhThu2ChiTietNgay_QStudent(ngay_ps, dto.getTieuChi6_summary(), doanhThu);
                            total_dt_tieuChi6_tkt_loai_k += doanhThu;
                        }
                    }
                }
            }

            dto.setTotal_tieuChi5(total_dt_tieuChi5_loai_t + total_dt_tieuChi5_loai_s + total_dt_tieuChi5_loai_d + total_dt_tieuChi5_loai_v + total_dt_tieuChi5_loai_r + total_dt_tieuChi5_loai_k);
            dto.setTotal_tieuChi6(total_dt_tieuChi6_tkt_loai_t + total_dt_tieuChi6_tkt_loai_s + total_dt_tieuChi6_tkt_loai_d + total_dt_tieuChi6_tkt_loai_v + total_dt_tieuChi6_tkt_loai_r + total_dt_tieuChi6_tkt_loai_k);
        }
    }

    private void baoCaoDanhGiaKetQuaTH_tieuChi5And6_summary(BaoCaoDanhGiaKQTHCTSummaryDTO dto, Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia,
                                                Timestamp fromDateTimeReportFilter, Timestamp toDateTimeReportFilter){
        List resultSet = this.ctTichDiemLocalBean.baoCaoDanhGiaKetQuaTHCT_tieuChi5And6Summary(fromDateTimeThamGia, toDateTimeThamGia, fromDateTimeReportFilter, toDateTimeReportFilter);
        if(resultSet != null && resultSet.size() > 0){
            Double total_dt_tieuChi5_tkc_truoc = new Double(0);
            Double total_dt_tieuChi5_tkc_sau = new Double(0);

            Double total_dt_tieuChi6_tkt_truoc = new Double(0);
            Double total_dt_tieuChi6_tkt_sau = new Double(0);
            for(Object object : resultSet){
                Object[] tmpArr = (Object[])object;
                if(tmpArr[2] != null){
                    if(tmpArr[1] != null){
                        if(tmpArr[1].toString().equals("truoc")){
                            if(tmpArr[0] != null){
                                if(tmpArr[0].equals("t")){
                                    dto.setTieuChi5_t_cu(Double.valueOf(tmpArr[2].toString()));
                                    total_dt_tieuChi5_tkc_truoc += dto.getTieuChi5_t_cu();
                                }else if(tmpArr[0].equals("s")){
                                    dto.setTieuChi5_s_cu(Double.valueOf(tmpArr[2].toString()));
                                    total_dt_tieuChi5_tkc_truoc += dto.getTieuChi5_s_cu();
                                }else if(tmpArr[0].equals("d")){
                                    dto.setTieuChi5_d_cu(Double.valueOf(tmpArr[2].toString()));
                                    total_dt_tieuChi5_tkc_truoc += dto.getTieuChi5_d_cu();
                                }else if(tmpArr[0].equals("v")){
                                    dto.setTieuChi5_v_cu(Double.valueOf(tmpArr[2].toString()));
                                    total_dt_tieuChi5_tkc_truoc += dto.getTieuChi5_v_cu();
                                }else if(tmpArr[0].equals("r")){
                                    dto.setTieuChi5_r_cu(Double.valueOf(tmpArr[2].toString()));
                                    total_dt_tieuChi5_tkc_truoc += dto.getTieuChi5_r_cu();
                                }else if(tmpArr[0].equals("k")){
                                    dto.setTieuChi5_k_cu(Double.valueOf(tmpArr[2].toString()));
                                    total_dt_tieuChi5_tkc_truoc += dto.getTieuChi5_k_cu();
                                }
                            }
                        }else if(tmpArr[1].toString().equals("sau")){
                            if(tmpArr[0] != null){
                                if(tmpArr[0].equals("t")){
                                    dto.setTieuChi5_t_moi(Double.valueOf(tmpArr[2].toString()));
                                    total_dt_tieuChi5_tkc_sau += dto.getTieuChi5_t_moi();
                                }else if(tmpArr[0].equals("s")){
                                    dto.setTieuChi5_s_moi(Double.valueOf(tmpArr[2].toString()));
                                    total_dt_tieuChi5_tkc_sau += dto.getTieuChi5_s_moi();
                                }else if(tmpArr[0].equals("d")){
                                    dto.setTieuChi5_d_moi(Double.valueOf(tmpArr[2].toString()));
                                    total_dt_tieuChi5_tkc_sau += dto.getTieuChi5_d_moi();
                                }else if(tmpArr[0].equals("v")){
                                    dto.setTieuChi5_v_moi(Double.valueOf(tmpArr[2].toString()));
                                    total_dt_tieuChi5_tkc_sau += dto.getTieuChi5_v_moi();
                                }else if(tmpArr[0].equals("r")){
                                    dto.setTieuChi5_r_moi(Double.valueOf(tmpArr[2].toString()));
                                    total_dt_tieuChi5_tkc_sau += dto.getTieuChi5_r_moi();
                                }else if(tmpArr[0].equals("k")){
                                    dto.setTieuChi5_k_moi(Double.valueOf(tmpArr[2].toString()));
                                    total_dt_tieuChi5_tkc_sau += dto.getTieuChi5_k_moi();
                                }
                            }
                        }
                    }
                }
                if(tmpArr[3] != null){
                    if(tmpArr[1] != null){
                        if(tmpArr[1].toString().equals("truoc")){
                            if(tmpArr[0] != null){
                                if(tmpArr[0].equals("t")){
                                    dto.setTieuChi6_t_cu(Double.valueOf(tmpArr[3].toString()));
                                    total_dt_tieuChi6_tkt_truoc += dto.getTieuChi6_t_cu();
                                }else if(tmpArr[0].equals("s")){
                                    dto.setTieuChi6_s_cu(Double.valueOf(tmpArr[3].toString()));
                                    total_dt_tieuChi6_tkt_truoc += dto.getTieuChi6_s_cu();
                                }else if(tmpArr[0].equals("d")){
                                    dto.setTieuChi6_d_cu(Double.valueOf(tmpArr[3].toString()));
                                    total_dt_tieuChi6_tkt_truoc += dto.getTieuChi6_d_cu();
                                }else if(tmpArr[0].equals("v")){
                                    dto.setTieuChi6_v_cu(Double.valueOf(tmpArr[3].toString()));
                                    total_dt_tieuChi6_tkt_truoc += dto.getTieuChi6_v_cu();
                                }else if(tmpArr[0].equals("r")){
                                    dto.setTieuChi6_r_cu(Double.valueOf(tmpArr[3].toString()));
                                    total_dt_tieuChi6_tkt_truoc += dto.getTieuChi6_r_cu();
                                }else if(tmpArr[0].equals("k")){
                                    dto.setTieuChi6_k_cu(Double.valueOf(tmpArr[3].toString()));
                                    total_dt_tieuChi6_tkt_truoc += dto.getTieuChi6_k_cu();
                                }
                            }
                        }else if(tmpArr[1].toString().equals("sau")){
                            if(tmpArr[0] != null){
                                if(tmpArr[0].equals("t")){
                                    dto.setTieuChi6_t_moi(Double.valueOf(tmpArr[3].toString()));
                                    total_dt_tieuChi6_tkt_sau += dto.getTieuChi6_t_moi();
                                }else if(tmpArr[0].equals("s")){
                                    dto.setTieuChi6_s_moi(Double.valueOf(tmpArr[3].toString()));
                                    total_dt_tieuChi6_tkt_sau += dto.getTieuChi6_s_moi();
                                }else if(tmpArr[0].equals("d")){
                                    dto.setTieuChi6_d_moi(Double.valueOf(tmpArr[3].toString()));
                                    total_dt_tieuChi6_tkt_sau += dto.getTieuChi6_d_moi();
                                }else if(tmpArr[0].equals("v")){
                                    dto.setTieuChi6_v_moi(Double.valueOf(tmpArr[3].toString()));
                                    total_dt_tieuChi6_tkt_sau += dto.getTieuChi6_v_moi();
                                }else if(tmpArr[0].equals("r")){
                                    dto.setTieuChi6_r_moi(Double.valueOf(tmpArr[3].toString()));
                                    total_dt_tieuChi6_tkt_sau += dto.getTieuChi6_r_moi();
                                }else if(tmpArr[0].equals("k")){
                                    dto.setTieuChi6_k_moi(Double.valueOf(tmpArr[3].toString()));
                                    total_dt_tieuChi6_tkt_sau += dto.getTieuChi6_k_moi();
                                }
                            }
                        }
                    }
                }
            }

            dto.setTieuChi5_td_tkc_cu(total_dt_tieuChi5_tkc_truoc);
            dto.setTieuChi5_td_tkc_moi(total_dt_tieuChi5_tkc_sau);
            dto.setTieuChi6_td_tkt_cu(total_dt_tieuChi6_tkt_truoc);
            dto.setTieuChi6_td_tkt_moi(total_dt_tieuChi6_tkt_sau);
        }
    }
    private void baoCaoDanhGiaKetQuaTH_tieuChi5And6_summary_QStudent2015(BaoCaoDanhGiaKQTHCTSummary_QStudent2015DTO dto, Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia,
                                                Timestamp fromDateTimeReportFilter, Timestamp toDateTimeReportFilter){
        List resultSet = this.thueBaoPhatTrienMoiLocalBean.baoCaoDanhGiaKetQuaTHCT_tieuChi5And6Summary(fromDateTimeThamGia, toDateTimeThamGia, fromDateTimeReportFilter, toDateTimeReportFilter);
        if(resultSet != null && resultSet.size() > 0){
            Double total_dt_tieuChi5_tkc = new Double(0);

            Double total_dt_tieuChi6_tkt = new Double(0);
            for(Object object : resultSet){
                Object[] tmpArr = (Object[])object;
                if(tmpArr[1] != null){
                    if(tmpArr[0] != null){
                        if(tmpArr[0].equals("t")){
                            dto.setTieuChi5_t(Double.valueOf(tmpArr[1].toString()));
                            total_dt_tieuChi5_tkc += dto.getTieuChi5_t();
                        }else if(tmpArr[0].equals("s")){
                            dto.setTieuChi5_s(Double.valueOf(tmpArr[1].toString()));
                            total_dt_tieuChi5_tkc += dto.getTieuChi5_s();
                        }else if(tmpArr[0].equals("d")){
                            dto.setTieuChi5_d(Double.valueOf(tmpArr[1].toString()));
                            total_dt_tieuChi5_tkc += dto.getTieuChi5_d();
                        }else if(tmpArr[0].equals("v")){
                            dto.setTieuChi5_v(Double.valueOf(tmpArr[1].toString()));
                            total_dt_tieuChi5_tkc += dto.getTieuChi5_v();
                        }else if(tmpArr[0].equals("r")){
                            dto.setTieuChi5_r(Double.valueOf(tmpArr[1].toString()));
                            total_dt_tieuChi5_tkc += dto.getTieuChi5_r();
                        }else if(tmpArr[0].equals("k")){
                            dto.setTieuChi5_k(Double.valueOf(tmpArr[1].toString()));
                            total_dt_tieuChi5_tkc += dto.getTieuChi5_k();
                        }
                    }
                }
                if(tmpArr[2] != null){
                    if(tmpArr[0] != null){
                        if(tmpArr[0].equals("t")){
                            dto.setTieuChi6_t(Double.valueOf(tmpArr[2].toString()));
                            total_dt_tieuChi6_tkt += dto.getTieuChi6_t();
                        }else if(tmpArr[0].equals("s")){
                            dto.setTieuChi6_s(Double.valueOf(tmpArr[2].toString()));
                            total_dt_tieuChi6_tkt += dto.getTieuChi6_s();
                        }else if(tmpArr[0].equals("d")){
                            dto.setTieuChi6_d(Double.valueOf(tmpArr[2].toString()));
                            total_dt_tieuChi6_tkt += dto.getTieuChi6_d();
                        }else if(tmpArr[0].equals("v")){
                            dto.setTieuChi6_v(Double.valueOf(tmpArr[2].toString()));
                            total_dt_tieuChi6_tkt += dto.getTieuChi6_v();
                        }else if(tmpArr[0].equals("r")){
                            dto.setTieuChi6_r(Double.valueOf(tmpArr[2].toString()));
                            total_dt_tieuChi6_tkt += dto.getTieuChi6_r();
                        }else if(tmpArr[0].equals("k")){
                            dto.setTieuChi6_k(Double.valueOf(tmpArr[2].toString()));
                            total_dt_tieuChi6_tkt += dto.getTieuChi6_k();
                        }
                    }
                }
            }

            dto.setTieuChi5_td_tkc(total_dt_tieuChi5_tkc);
            dto.setTieuChi6_td_tkt(total_dt_tieuChi6_tkt);
        }
    }

    private void baoCaoDanhGiaKetQuaTH_tieuChi7_summary(BaoCaoDanhGiaKQTHCTSummaryDTO dto, Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia) {
        List resultSet = this.ctTichDiemLocalBean.baoCaoDanhGiaKetQuaTHCT_tieuChi7(fromDateTimeThamGia, toDateTimeThamGia);
        if(resultSet != null && resultSet.size() > 0){
            Double total_truoc = new Double(0);
            Double total_sau = new Double(0);
            for(Object object : resultSet){
                Object[] tmpArr = (Object[])object;
                if(tmpArr[0] != null){
                    if(tmpArr[0].toString().equalsIgnoreCase("1")){
                        // 1 ma~
                        if(tmpArr[1] != null){
                            if(tmpArr[1].toString().equalsIgnoreCase("truoc")){
                                if(tmpArr[2] != null){
                                    dto.setTieuChi7_1_ma_cu(Double.valueOf(tmpArr[2].toString()));
                                    total_truoc += dto.getTieuChi7_1_ma_cu();
                                }
                            }else if(tmpArr[1].toString().equalsIgnoreCase("sau")){
                                if(tmpArr[2] != null){
                                    dto.setTieuChi7_1_ma_moi(Double.valueOf(tmpArr[2].toString()));
                                    total_sau += dto.getTieuChi7_1_ma_moi();
                                }
                            }
                        }
                    }else if(tmpArr[0].toString().equalsIgnoreCase("2")){
                        // 2 ma~
                        if(tmpArr[1] != null){
                            if(tmpArr[1].toString().equalsIgnoreCase("truoc")){
                                if(tmpArr[2] != null){
                                    dto.setTieuChi7_2_ma_cu(Double.valueOf(tmpArr[2].toString()));
                                    total_truoc += dto.getTieuChi7_2_ma_cu();
                                }
                            }else if(tmpArr[1].toString().equalsIgnoreCase("sau")){
                                if(tmpArr[2] != null){
                                    dto.setTieuChi7_2_ma_moi(Double.valueOf(tmpArr[2].toString()));
                                    total_sau += dto.getTieuChi7_2_ma_moi();
                                }
                            }
                        }
                    }else if(tmpArr[0].toString().equalsIgnoreCase("3")){
                        // 3 ma~
                        if(tmpArr[1] != null){
                            if(tmpArr[1].toString().equalsIgnoreCase("truoc")){
                                if(tmpArr[2] != null){
                                    dto.setTieuChi7_3_ma_cu(Double.valueOf(tmpArr[2].toString()));
                                    total_truoc += dto.getTieuChi7_3_ma_cu();
                                }
                            }else if(tmpArr[1].toString().equalsIgnoreCase("sau")){
                                if(tmpArr[2] != null){
                                    dto.setTieuChi7_3_ma_moi(Double.valueOf(tmpArr[2].toString()));
                                    total_sau += dto.getTieuChi7_3_ma_moi();
                                }
                            }
                        }
                    }else if(tmpArr[0].toString().equalsIgnoreCase("4")){
                        // > 3 ma~
                        if(tmpArr[1] != null){
                            if(tmpArr[1].toString().equalsIgnoreCase("truoc")){
                                if(tmpArr[2] != null){
                                    dto.setTieuChi7_tren_3_ma_cu(Double.valueOf(tmpArr[2].toString()));
                                    total_truoc += dto.getTieuChi7_tren_3_ma_cu();
                                }
                            }else if(tmpArr[1].toString().equalsIgnoreCase("sau")){
                                if(tmpArr[2] != null){
                                    dto.setTieuChi7_tren_3_ma_moi(Double.valueOf(tmpArr[2].toString()));
                                    total_sau += dto.getTieuChi7_tren_3_ma_moi();
                                }
                            }
                        }
                    }
                }
            }
            dto.setTieuChi7_sl_tb_ddk_cap_ma_cu(total_truoc);
            dto.setTieuChi7_sl_tb_ddk_cap_ma_moi(total_sau);
        }
    }
    private void baoCaoDanhGiaKetQuaTH_tieuChi7_summary_QStudent2015(BaoCaoDanhGiaKQTHCTSummary_QStudent2015DTO dto, Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia) {
        List resultSet = this.thueBaoPhatTrienMoiLocalBean.baoCaoDanhGiaKetQuaTHCT_tieuChi7(fromDateTimeThamGia, toDateTimeThamGia);
        if(resultSet != null && resultSet.size() > 0){
            Double total = new Double(0);
            for(Object object : resultSet){
                Object[] tmpArr = (Object[])object;
                if(tmpArr[0] != null){
                    if(tmpArr[0].toString().equalsIgnoreCase("1")){
                        // 1 ma~
                        if(tmpArr[1] != null){
                            dto.setTieuChi7_1_ma(Double.valueOf(tmpArr[1].toString()));
                            total += dto.getTieuChi7_1_ma();
                        }
                    }else if(tmpArr[0].toString().equalsIgnoreCase("2")){
                        // 2 ma~
                        if(tmpArr[1] != null){
                            dto.setTieuChi7_2_ma(Double.valueOf(tmpArr[1].toString()));
                            total += dto.getTieuChi7_2_ma();
                        }
                    }else if(tmpArr[0].toString().equalsIgnoreCase("3")){
                        // 3 ma~
                        if(tmpArr[1] != null){
                            dto.setTieuChi7_3_ma(Double.valueOf(tmpArr[1].toString()));
                            total += dto.getTieuChi7_3_ma();
                        }
                    }else if(tmpArr[0].toString().equalsIgnoreCase("4")){
                        // > 3 ma~
                        if(tmpArr[1] != null){
                            dto.setTieuChi7_tren_3_ma(Double.valueOf(tmpArr[1].toString()));
                            total += dto.getTieuChi7_tren_3_ma();
                        }
                    }
                }
            }
            dto.setTieuChi7_sl_tb_ddk_cap_ma(total);
        }
    }

    private void baoCaoDanhGiaKetQuaTH_tieuChi7_theoNgay(BaoCaoDanhGiaKQTHCTChiTietTheoNgayDTO dto, Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia) {
        List resultSet = this.ctTichDiemLocalBean.baoCaoDanhGiaKetQuaTHCT_tieuChi7(fromDateTimeThamGia, toDateTimeThamGia);
        if(resultSet != null && resultSet.size() > 0){
            Double total_truoc = new Double(0);
            Double total_sau = new Double(0);
            for(Object object : resultSet){
                Object[] tmpArr = (Object[])object;
                if(tmpArr[0] != null){
                    if(tmpArr[0].toString().equalsIgnoreCase("1")){
                        // 1 ma~
                        if(tmpArr[1] != null){
                            if(tmpArr[1].toString().equalsIgnoreCase("truoc")){
                                if(tmpArr[2] != null){
                                    dto.setTieuChi7_1_ma_cu(Double.valueOf(tmpArr[2].toString()));
                                    total_truoc += dto.getTieuChi7_1_ma_cu();
                                }
                            }else if(tmpArr[1].toString().equalsIgnoreCase("sau")){
                                if(tmpArr[2] != null){
                                    dto.setTieuChi7_1_ma_moi(Double.valueOf(tmpArr[2].toString()));
                                    total_sau += dto.getTieuChi7_1_ma_moi();
                                }
                            }
                        }
                    }else if(tmpArr[0].toString().equalsIgnoreCase("2")){
                        // 2 ma~
                        if(tmpArr[1] != null){
                            if(tmpArr[1].toString().equalsIgnoreCase("truoc")){
                                if(tmpArr[2] != null){
                                    dto.setTieuChi7_2_ma_cu(Double.valueOf(tmpArr[2].toString()));
                                    total_truoc += dto.getTieuChi7_2_ma_cu();
                                }
                            }else if(tmpArr[1].toString().equalsIgnoreCase("sau")){
                                if(tmpArr[2] != null){
                                    dto.setTieuChi7_2_ma_moi(Double.valueOf(tmpArr[2].toString()));
                                    total_sau += dto.getTieuChi7_2_ma_moi();
                                }
                            }
                        }
                    }else if(tmpArr[0].toString().equalsIgnoreCase("3")){
                        // 3 ma~
                        if(tmpArr[1] != null){
                            if(tmpArr[1].toString().equalsIgnoreCase("truoc")){
                                if(tmpArr[2] != null){
                                    dto.setTieuChi7_3_ma_cu(Double.valueOf(tmpArr[2].toString()));
                                    total_truoc += dto.getTieuChi7_3_ma_cu();
                                }
                            }else if(tmpArr[1].toString().equalsIgnoreCase("sau")){
                                if(tmpArr[2] != null){
                                    dto.setTieuChi7_3_ma_moi(Double.valueOf(tmpArr[2].toString()));
                                    total_sau += dto.getTieuChi7_3_ma_moi();
                                }
                            }
                        }
                    }else if(tmpArr[0].toString().equalsIgnoreCase("4")){
                        // > 3 ma~
                        if(tmpArr[1] != null){
                            if(tmpArr[1].toString().equalsIgnoreCase("truoc")){
                                if(tmpArr[2] != null){
                                    dto.setTieuChi7_tren_3_ma_cu(Double.valueOf(tmpArr[2].toString()));
                                    total_truoc += dto.getTieuChi7_tren_3_ma_cu();
                                }
                            }else if(tmpArr[1].toString().equalsIgnoreCase("sau")){
                                if(tmpArr[2] != null){
                                    dto.setTieuChi7_tren_3_ma_moi(Double.valueOf(tmpArr[2].toString()));
                                    total_sau += dto.getTieuChi7_tren_3_ma_moi();
                                }
                            }
                        }
                    }
                }
            }
            dto.setTieuChi7_sl_tb_ddk_cap_ma_cu(total_truoc);
            dto.setTieuChi7_sl_tb_ddk_cap_ma_moi(total_sau);
        }
    }
    private void baoCaoDanhGiaKetQuaTH_tieuChi7_theoNgay_QStudent(BaoCaoDanhGiaKQTHCTChiTietTheoNgay_QStudent2015DTO dto, Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia) {
        List resultSet = this.thueBaoPhatTrienMoiLocalBean.baoCaoDanhGiaKetQuaTHCT_tieuChi7(fromDateTimeThamGia, toDateTimeThamGia);
        if(resultSet != null && resultSet.size() > 0){
            Double total = new Double(0);
            for(Object object : resultSet){
                Object[] tmpArr = (Object[])object;
                if(tmpArr[0] != null){
                    if(tmpArr[0].toString().equalsIgnoreCase("1")){
                        // 1 ma~
                        if(tmpArr[1] != null){
                            dto.setTieuChi7_1_ma(Double.valueOf(tmpArr[1].toString()));
                            total += dto.getTieuChi7_1_ma();
                        }
                    }else if(tmpArr[0].toString().equalsIgnoreCase("2")){
                        // 2 ma~
                        if(tmpArr[1] != null){
                            dto.setTieuChi7_2_ma(Double.valueOf(tmpArr[1].toString()));
                            total += dto.getTieuChi7_2_ma();
                        }
                    }else if(tmpArr[0].toString().equalsIgnoreCase("3")){
                        // 3 ma~
                        if(tmpArr[1] != null){
                            dto.setTieuChi7_3_ma(Double.valueOf(tmpArr[1].toString()));
                            total += dto.getTieuChi7_3_ma();
                        }
                    }else if(tmpArr[0].toString().equalsIgnoreCase("4")){
                        // > 3 ma~
                        if(tmpArr[1] != null){
                            dto.setTieuChi7_tren_3_ma(Double.valueOf(tmpArr[1].toString()));
                            total += dto.getTieuChi7_tren_3_ma();
                        }
                    }
                }
            }
            dto.setTieuChi7_sl_tb_ddk_cap_ma(total);
        }
    }

    private void baoCaoDanhGiaKetQuaTH_tieuChi8_summary(BaoCaoDanhGiaKQTHCTSummaryDTO dto, Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia) {
        List resultSet = this.ctTichDiemLocalBean.baoCaoDanhGiaKetQuaTHCT_tieuChi8(fromDateTimeThamGia, toDateTimeThamGia);
        if(resultSet != null && resultSet.size() > 0){
            Double total_truoc = new Double(0);
            Double total_sau = new Double(0);
            for(Object object : resultSet){
                Object[] tmpArr = (Object[])object;
                if(tmpArr[0] != null){
                    if(tmpArr[0].toString().equalsIgnoreCase("1")){
                        // 1 ma~
                        if(tmpArr[1] != null){
                            if(tmpArr[1].toString().equalsIgnoreCase("truoc")){
                                if(tmpArr[2] != null){
                                    dto.setTieuChi8_1_phieu_cu(Double.valueOf(tmpArr[2].toString()));
                                    total_truoc += dto.getTieuChi8_1_phieu_cu();
                                }
                            }else if(tmpArr[1].toString().equalsIgnoreCase("sau")){
                                if(tmpArr[2] != null){
                                    dto.setTieuChi8_1_phieu_moi(Double.valueOf(tmpArr[2].toString()));
                                    total_sau += dto.getTieuChi8_1_phieu_moi();
                                }
                            }
                        }
                    }else if(tmpArr[0].toString().equalsIgnoreCase("2")){
                        // 2 ma~
                        if(tmpArr[1] != null){
                            if(tmpArr[1].toString().equalsIgnoreCase("truoc")){
                                if(tmpArr[2] != null){
                                    dto.setTieuChi8_2_phieu_cu(Double.valueOf(tmpArr[2].toString()));
                                    total_truoc += dto.getTieuChi8_2_phieu_cu();
                                }
                            }else if(tmpArr[1].toString().equalsIgnoreCase("sau")){
                                if(tmpArr[2] != null){
                                    dto.setTieuChi8_2_phieu_moi(Double.valueOf(tmpArr[2].toString()));
                                    total_sau += dto.getTieuChi8_2_phieu_moi();
                                }
                            }
                        }
                    }else if(tmpArr[0].toString().equalsIgnoreCase("3")){
                        // 3 ma~
                        if(tmpArr[1] != null){
                            if(tmpArr[1].toString().equalsIgnoreCase("truoc")){
                                if(tmpArr[2] != null){
                                    dto.setTieuChi8_3_phieu_cu(Double.valueOf(tmpArr[2].toString()));
                                    total_truoc += dto.getTieuChi8_3_phieu_cu();
                                }
                            }else if(tmpArr[1].toString().equalsIgnoreCase("sau")){
                                if(tmpArr[2] != null){
                                    dto.setTieuChi8_3_phieu_moi(Double.valueOf(tmpArr[2].toString()));
                                    total_sau += dto.getTieuChi8_3_phieu_moi();
                                }
                            }
                        }
                    }else if(tmpArr[0].toString().equalsIgnoreCase("4")){
                        // > 3 ma~
                        if(tmpArr[1] != null){
                            if(tmpArr[1].toString().equalsIgnoreCase("truoc")){
                                if(tmpArr[2] != null){
                                    dto.setTieuChi8_tren_3_phieu_cu(Double.valueOf(tmpArr[2].toString()));
                                    total_truoc += dto.getTieuChi8_tren_3_phieu_cu();
                                }
                            }else if(tmpArr[1].toString().equalsIgnoreCase("sau")){
                                if(tmpArr[2] != null){
                                    dto.setTieuChi8_tren_3_phieu_moi(Double.valueOf(tmpArr[2].toString()));
                                    total_sau += dto.getTieuChi8_tren_3_phieu_moi();
                                }
                            }
                        }
                    }
                }
            }
            dto.setTieuChi8_sl_tb_da_doi_thuong_cu(total_truoc);
            dto.setTieuChi8_sl_tb_da_doi_thuong_moi(total_sau);
        }
    }
    private void baoCaoDanhGiaKetQuaTH_tieuChi8_summary_QStudent2015(BaoCaoDanhGiaKQTHCTSummary_QStudent2015DTO dto, Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia) {
        List resultSet = this.thueBaoPhatTrienMoiLocalBean.baoCaoDanhGiaKetQuaTHCT_tieuChi8(fromDateTimeThamGia, toDateTimeThamGia);
        if(resultSet != null && resultSet.size() > 0){
            Double total = new Double(0);
            for(Object object : resultSet){
                Object[] tmpArr = (Object[])object;
                if(tmpArr[0] != null){
                    if(tmpArr[0].toString().equalsIgnoreCase("1")){
                        // 1 ma~
                        if(tmpArr[1] != null){
                            dto.setTieuChi8_1_phieu(Double.valueOf(tmpArr[1].toString()));
                            total += dto.getTieuChi8_1_phieu();
                        }
                    }else if(tmpArr[0].toString().equalsIgnoreCase("2")){
                        // 2 ma~
                        if(tmpArr[1] != null){
                            dto.setTieuChi8_2_phieu(Double.valueOf(tmpArr[1].toString()));
                            total += dto.getTieuChi8_2_phieu();
                        }
                    }else if(tmpArr[0].toString().equalsIgnoreCase("3")){
                        // 3 ma~
                        if(tmpArr[1] != null){
                            dto.setTieuChi8_3_phieu(Double.valueOf(tmpArr[1].toString()));
                            total += dto.getTieuChi8_3_phieu();
                        }
                    }else if(tmpArr[0].toString().equalsIgnoreCase("4")){
                        // > 3 ma~
                        if(tmpArr[1] != null){
                            dto.setTieuChi8_tren_3_phieu(Double.valueOf(tmpArr[1].toString()));
                            total += dto.getTieuChi8_tren_3_phieu();
                        }
                    }
                }
            }
            dto.setTieuChi8_sl_tb_da_doi_thuong(total);
        }
    }

    private void baoCaoDanhGiaKetQuaTH_tieuChi8_theoNgay(BaoCaoDanhGiaKQTHCTChiTietTheoNgayDTO dto, Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia) {
        List resultSet = this.ctTichDiemLocalBean.baoCaoDanhGiaKetQuaTHCT_tieuChi8(fromDateTimeThamGia, toDateTimeThamGia);
        if(resultSet != null && resultSet.size() > 0){
            Double total_truoc = new Double(0);
            Double total_sau = new Double(0);
            for(Object object : resultSet){
                Object[] tmpArr = (Object[])object;
                if(tmpArr[0] != null){
                    if(tmpArr[0].toString().equalsIgnoreCase("1")){
                        // 1 ma~
                        if(tmpArr[1] != null){
                            if(tmpArr[1].toString().equalsIgnoreCase("truoc")){
                                if(tmpArr[2] != null){
                                    dto.setTieuChi8_1_phieu_cu(Double.valueOf(tmpArr[2].toString()));
                                    total_truoc += dto.getTieuChi8_1_phieu_cu();
                                }
                            }else if(tmpArr[1].toString().equalsIgnoreCase("sau")){
                                if(tmpArr[2] != null){
                                    dto.setTieuChi8_1_phieu_moi(Double.valueOf(tmpArr[2].toString()));
                                    total_sau += dto.getTieuChi8_1_phieu_moi();
                                }
                            }
                        }
                    }else if(tmpArr[0].toString().equalsIgnoreCase("2")){
                        // 2 ma~
                        if(tmpArr[1] != null){
                            if(tmpArr[1].toString().equalsIgnoreCase("truoc")){
                                if(tmpArr[2] != null){
                                    dto.setTieuChi8_2_phieu_cu(Double.valueOf(tmpArr[2].toString()));
                                    total_truoc += dto.getTieuChi8_2_phieu_cu();
                                }
                            }else if(tmpArr[1].toString().equalsIgnoreCase("sau")){
                                if(tmpArr[2] != null){
                                    dto.setTieuChi8_2_phieu_moi(Double.valueOf(tmpArr[2].toString()));
                                    total_sau += dto.getTieuChi8_2_phieu_moi();
                                }
                            }
                        }
                    }else if(tmpArr[0].toString().equalsIgnoreCase("3")){
                        // 3 ma~
                        if(tmpArr[1] != null){
                            if(tmpArr[1].toString().equalsIgnoreCase("truoc")){
                                if(tmpArr[2] != null){
                                    dto.setTieuChi8_3_phieu_cu(Double.valueOf(tmpArr[2].toString()));
                                    total_truoc += dto.getTieuChi8_3_phieu_cu();
                                }
                            }else if(tmpArr[1].toString().equalsIgnoreCase("sau")){
                                if(tmpArr[2] != null){
                                    dto.setTieuChi8_3_phieu_moi(Double.valueOf(tmpArr[2].toString()));
                                    total_sau += dto.getTieuChi8_3_phieu_moi();
                                }
                            }
                        }
                    }else if(tmpArr[0].toString().equalsIgnoreCase("4")){
                        // > 3 ma~
                        if(tmpArr[1] != null){
                            if(tmpArr[1].toString().equalsIgnoreCase("truoc")){
                                if(tmpArr[2] != null){
                                    dto.setTieuChi8_tren_3_phieu_cu(Double.valueOf(tmpArr[2].toString()));
                                    total_truoc += dto.getTieuChi8_tren_3_phieu_cu();
                                }
                            }else if(tmpArr[1].toString().equalsIgnoreCase("sau")){
                                if(tmpArr[2] != null){
                                    dto.setTieuChi8_tren_3_phieu_moi(Double.valueOf(tmpArr[2].toString()));
                                    total_sau += dto.getTieuChi8_tren_3_phieu_moi();
                                }
                            }
                        }
                    }
                }
            }
            dto.setTieuChi8_sl_tb_da_doi_thuong_cu(total_truoc);
            dto.setTieuChi8_sl_tb_da_doi_thuong_moi(total_sau);
        }
    }
    private void baoCaoDanhGiaKetQuaTH_tieuChi8_theoNgay_QStudent(BaoCaoDanhGiaKQTHCTChiTietTheoNgay_QStudent2015DTO dto, Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia) {
        List resultSet = this.thueBaoPhatTrienMoiLocalBean.baoCaoDanhGiaKetQuaTHCT_tieuChi8(fromDateTimeThamGia, toDateTimeThamGia);
        if(resultSet != null && resultSet.size() > 0){
            Double total = new Double(0);
            for(Object object : resultSet){
                Object[] tmpArr = (Object[])object;
                if(tmpArr[0] != null){
                    if(tmpArr[0].toString().equalsIgnoreCase("1")){
                        // 1 ma~
                        if(tmpArr[1] != null){
                            dto.setTieuChi8_1_phieu(Double.valueOf(tmpArr[1].toString()));
                            total += dto.getTieuChi8_1_phieu();
                        }
                    }else if(tmpArr[0].toString().equalsIgnoreCase("2")){
                        // 2 ma~
                        if(tmpArr[1] != null){
                            dto.setTieuChi8_2_phieu(Double.valueOf(tmpArr[1].toString()));
                            total += dto.getTieuChi8_2_phieu();
                        }
                    }else if(tmpArr[0].toString().equalsIgnoreCase("3")){
                        // 3 ma~
                        if(tmpArr[1] != null){
                            dto.setTieuChi8_3_phieu(Double.valueOf(tmpArr[1].toString()));
                            total += dto.getTieuChi8_3_phieu();
                        }
                    }else if(tmpArr[0].toString().equalsIgnoreCase("4")){
                        // > 3 ma~
                        if(tmpArr[2] != null){
                            dto.setTieuChi8_tren_3_phieu(Double.valueOf(tmpArr[2].toString()));
                            total += dto.getTieuChi8_tren_3_phieu();
                        }
                    }
                }
            }
            dto.setTieuChi8_sl_tb_da_doi_thuong(total);
        }
    }

    private void baoCaoDanhGiaKetQuaTH_tieuChi9_summary(BaoCaoDanhGiaKQTHCTSummaryDTO dto, Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia) {
        List resultSet = this.ctTichDiemLocalBean.baoCaoDanhGiaKetQuaTHCT_tieuChi9(fromDateTimeThamGia, toDateTimeThamGia);
        if(resultSet != null && resultSet.size() > 0){
            for(Object object : resultSet){
                Object[] tmpArr = (Object[])object;
                if(tmpArr[0] != null){
                    if(tmpArr[0].toString().equalsIgnoreCase("truoc")){
                        dto.setTieuChi9_sl_ma_doi_da_ps_cu(tmpArr[1] != null ? Double.valueOf(tmpArr[1].toString()) : 0);
                    }else if(tmpArr[0].toString().equalsIgnoreCase("sau")){
                        dto.setTieuChi9_sl_ma_doi_da_ps_moi(tmpArr[1] != null ? Double.valueOf(tmpArr[1].toString()) : 0);
                    }
                }
            }
        }
    }
    private void baoCaoDanhGiaKetQuaTH_tieuChi9_summary_QStudent2015(BaoCaoDanhGiaKQTHCTSummary_QStudent2015DTO dto, Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia) {
        Long total = this.thueBaoPhatTrienMoiLocalBean.baoCaoDanhGiaKetQuaTHCT_tieuChi9(fromDateTimeThamGia, toDateTimeThamGia);
        if(total != null ){
            dto.setTieuChi9_sl_ma_doi_da_ps(total != null ? Double.valueOf(total.toString()) : 0);
        }
    }

    private void baoCaoDanhGiaKetQuaTH_tieuChi9_theoNgay(BaoCaoDanhGiaKQTHCTChiTietTheoNgayDTO dto, Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia) {
        List resultSet = this.ctTichDiemLocalBean.baoCaoDanhGiaKetQuaTHCT_tieuChi9(fromDateTimeThamGia, toDateTimeThamGia);
        if(resultSet != null && resultSet.size() > 0){
            for(Object object : resultSet){
                Object[] tmpArr = (Object[])object;
                if(tmpArr[0] != null){
                    if(tmpArr[0].toString().equalsIgnoreCase("truoc")){
                        dto.setTieuChi9_sl_ma_doi_da_ps_cu(tmpArr[1] != null ? Double.valueOf(tmpArr[1].toString()) : 0);
                    }else if(tmpArr[0].toString().equalsIgnoreCase("sau")){
                        dto.setTieuChi9_sl_ma_doi_da_ps_moi(tmpArr[1] != null ? Double.valueOf(tmpArr[1].toString()) : 0);
                    }
                }
            }
        }
    }
    private void baoCaoDanhGiaKetQuaTH_tieuChi9_theoNgay_QStudent(BaoCaoDanhGiaKQTHCTChiTietTheoNgay_QStudent2015DTO dto, Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia) {
        Long total = this.thueBaoPhatTrienMoiLocalBean.baoCaoDanhGiaKetQuaTHCT_tieuChi9(fromDateTimeThamGia, toDateTimeThamGia);
        if(total != null){
            dto.setTieuChi9_sl_ma_doi_da_ps(total != null ? Double.valueOf(total.toString()) : 0);
        }
    }

    private void baoCaoDanhGiaKetQuaTH_tieuChi10_summary(BaoCaoDanhGiaKQTHCTSummaryDTO dto, Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia) {
        List resultSet = this.ctTichDiemLocalBean.baoCaoDanhGiaKetQuaTHCT_tieuChi10(fromDateTimeThamGia, toDateTimeThamGia);
        if(resultSet != null && resultSet.size() > 0){
            for(Object object : resultSet){
                Object[] tmpArr = (Object[])object;
                if(tmpArr[0] != null){
                    if(tmpArr[0].toString().equalsIgnoreCase("truoc")){
                        dto.setTieuChi10_sl_phieu_qua_tang_da_doi_cu(tmpArr[1] != null ? Double.valueOf(tmpArr[1].toString()) : 0);
                    }else if(tmpArr[0].toString().equalsIgnoreCase("sau")){
                        dto.setTieuChi10_sl_phieu_qua_tang_da_doi_moi(tmpArr[1] != null ? Double.valueOf(tmpArr[1].toString()) : 0);
                    }
                }
            }
        }
    }
    private void baoCaoDanhGiaKetQuaTH_tieuChi10_summary_QStudent2015(BaoCaoDanhGiaKQTHCTSummary_QStudent2015DTO dto, Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia) {
        Long total = this.thueBaoPhatTrienMoiLocalBean.baoCaoDanhGiaKetQuaTHCT_tieuChi10(fromDateTimeThamGia, toDateTimeThamGia);
        if(total != null){
            dto.setTieuChi10_sl_phieu_qua_tang_da_doi(total != null ? Double.valueOf(total.toString()) : 0);
        }
    }

    private void baoCaoDanhGiaKetQuaTH_tieuChi10_theoNgay(BaoCaoDanhGiaKQTHCTChiTietTheoNgayDTO dto, Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia) {
        List resultSet = this.ctTichDiemLocalBean.baoCaoDanhGiaKetQuaTHCT_tieuChi10(fromDateTimeThamGia, toDateTimeThamGia);
        if(resultSet != null && resultSet.size() > 0){
            for(Object object : resultSet){
                Object[] tmpArr = (Object[])object;
                if(tmpArr[0] != null){
                    if(tmpArr[0].toString().equalsIgnoreCase("truoc")){
                        dto.setTieuChi10_sl_phieu_qua_tang_da_doi_cu(tmpArr[1] != null ? Double.valueOf(tmpArr[1].toString()) : 0);
                    }else if(tmpArr[0].toString().equalsIgnoreCase("sau")){
                        dto.setTieuChi10_sl_phieu_qua_tang_da_doi_moi(tmpArr[1] != null ? Double.valueOf(tmpArr[1].toString()) : 0);
                    }
                }
            }
        }
    }
    private void baoCaoDanhGiaKetQuaTH_tieuChi10_theoNgay_QStudent(BaoCaoDanhGiaKQTHCTChiTietTheoNgay_QStudent2015DTO dto, Timestamp fromDateTimeThamGia, Timestamp toDateTimeThamGia) {
        Long total = this.thueBaoPhatTrienMoiLocalBean.baoCaoDanhGiaKetQuaTHCT_tieuChi10(fromDateTimeThamGia, toDateTimeThamGia);
        if(total != null ){
            dto.setTieuChi10_sl_phieu_qua_tang_da_doi(total != null ? Double.valueOf(total.toString()) : 0);
        }
    }
}
