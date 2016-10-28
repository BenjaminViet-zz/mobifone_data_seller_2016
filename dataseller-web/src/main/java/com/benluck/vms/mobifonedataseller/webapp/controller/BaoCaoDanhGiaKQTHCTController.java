package com.benluck.vms.mobifonedataseller.webapp.controller;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.utils.DateUtil;
import com.benluck.vms.mobifonedataseller.core.business.BaoCaoDanhGiaKQTHCTManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.BranchManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.DistrictManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.*;
import com.benluck.vms.mobifonedataseller.editor.CustomDateEditor;
import com.benluck.vms.mobifonedataseller.security.util.SecurityUtils;
import com.benluck.vms.mobifonedataseller.util.ExcelUtil;
import com.benluck.vms.mobifonedataseller.util.WebCommonUtil;
import com.benluck.vms.mobifonedataseller.webapp.command.command2014.BaoCaoDanhGiaKQTHCTCommand;
import com.benluck.vms.mobifonedataseller.webapp.dto.CellDataType;
import com.benluck.vms.mobifonedataseller.webapp.dto.CellValue;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/30/14
 * Time: 4:26 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class BaoCaoDanhGiaKQTHCTController extends ApplicationObjectSupport{
    private Log log = LogFactory.getLog(BaoCaoDanhGiaKQTHCTController.class);
    private static final Integer TOTAL_COLUMN_EXPORT = 4;

    @Autowired
    private BaoCaoDanhGiaKQTHCTManagementLocalBean baoCaoDanhGiaKQTHCTManagementLocalBean;
    @Autowired
    private BranchManagementLocalBean branchManagementLocalBean;
    @Autowired
    private DistrictManagementLocalBean districtManagementLocalBean;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor("dd/MM/yyyy"));
    }

    @RequestMapping(value = {"/admin/baocaodanhgiaketquathuchienct.html","/chinhanh/baocaodanhgiaketquathuchienct.html"})
    public ModelAndView baoCaoDanhGiaKetQuaThucHienCT(@ModelAttribute(value = Constants.FORM_MODEL_KEY)BaoCaoDanhGiaKQTHCTCommand command,
                                                      HttpServletRequest request,
                                                      HttpServletResponse response){
        ModelAndView mav = new ModelAndView("/admin/baocaodanhgiaketquathuchienct_summary");
        String crudaction = command.getCrudaction();
        if(StringUtils.isNotBlank(crudaction)){
            if (crudaction.equals("search")){
                convertDate2Timestamp(command);
                Map<String, Object> properties = buildProperties(command);
                if(command.getReportSummary().equals(Constants.REPORT_DANHGIAKETQUATHUCHIENCT_THEONGAY)){
                    BaoCaoDanhGiaKQTHCTChiTietTheoNgayDTO dto = this.baoCaoDanhGiaKQTHCTManagementLocalBean.baoCaoDanhGiaKQTHCT_theoNgay_tdcg(properties, 0, Integer.MAX_VALUE, command.getSortExpression(), command.getSortDirection());
                    mav.addObject("reportData", dto);
                    mav.setViewName("/admin/baocaodanhgiaketquathuchienct_theoNgay");
                }else{
                    BaoCaoDanhGiaKQTHCTSummaryDTO dto = this.baoCaoDanhGiaKQTHCTManagementLocalBean.baoCaoDanhGiaKQTHCT_summary_tdcg(properties, 0, Integer.MAX_VALUE, command.getSortExpression(), command.getSortDirection());
                    mav.addObject("reportData", dto);
                }
            }else if (crudaction.equals("export")){
                try{
                    if(command.getReportSummary().equals(Constants.REPORT_DANHGIAKETQUATHUCHIENCT_THEONGAY)){
                        export2Excel_theoNgay(command, request, response);
                    }else{
                        export2Excel_summary(command, request, response);
                    }
                }catch (Exception e){
                    mav.addObject(Constants.ALERT_TYPE, "danger");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("export_data_exception"));
                }
            }
        }
        referenceData(command, mav);
        return mav;
    }

    private void referenceData(BaoCaoDanhGiaKQTHCTCommand command, ModelAndView mav){
        if(SecurityUtils.userHasAuthority(Constants.ADMIN_ROLE) || SecurityUtils.userHasAuthority(Constants.BAOCAO_ROLE)){
            List<BranchDTO> branchList = this.branchManagementLocalBean.findAll_tdcg();
            mav.addObject("branchList", branchList);

            if(command.getBranchId() != null){
                List<DistrictDTO> districtList = this.districtManagementLocalBean.findByBranchId_tdcg(command.getBranchId());
                mav.addObject("districtList", districtList);
            }else{
                List<DistrictDTO> districtList = this.districtManagementLocalBean.findAll_tdcg();
                mav.addObject("districtList", districtList);
            }
        }else if(SecurityUtils.userHasAuthority(Constants.CHINHANH_ROLE)){
            try{
                List<BranchDTO> branchList = new ArrayList<BranchDTO>();
                BranchDTO branchDTO = this.branchManagementLocalBean.findBranchById_tdcg(SecurityUtils.getPrincipal().getBranchId());
                branchList.add(branchDTO);
                mav.addObject("branchList", branchList);
            }catch (Exception e){}

            List<DistrictDTO> districtList = this.districtManagementLocalBean.findByBranchId_tdcg(SecurityUtils.getPrincipal().getBranchId());
            mav.addObject("districtList", districtList);
        }
    }

    private Map<String, Object> buildProperties(BaoCaoDanhGiaKQTHCTCommand command){
        Map<String, Object> properties = new HashMap<String, Object>();
        if(command.getFromDateTimeThamGia() != null){
            properties.put("fromDateTimeThamGia", command.getFromDateTimeThamGia());
        }
        if(command.getToDateTimeThamGia() != null){
            properties.put("toDateTimeThamGia", command.getToDateTimeThamGia());
        }
        if(command.getFromDateTimeReportFilter() != null){
            properties.put("fromDateTimeReportFilter", command.getFromDateTimeReportFilter());
        }
        if(command.getToDateTimeReportFilter() != null){
            properties.put("toDateTimeReportFilter", command.getToDateTimeReportFilter());
        }
        if(SecurityUtils.userHasAuthority(Constants.CHINHANH_ROLE)){
            properties.put("branchId", SecurityUtils.getPrincipal().getBranchId());
        }else if(SecurityUtils.userHasAuthority(Constants.ADMIN_ROLE)){
            if(command.getBranchId() != null){
                properties.put("branchId", command.getBranchId());
            }
        }
        if(command.getDistrictId() != null){
            properties.put("districtId", command.getDistrictId());
        }
        return properties;
    }

    private void convertDate2Timestamp(BaoCaoDanhGiaKQTHCTCommand command){
        if(command.getFromDateThamGia() != null){
            command.setFromDateTimeThamGia(DateUtil.dateToTimestamp(command.getFromDateThamGia(), Constants.VI_DATE_FORMAT));
        }
        if(command.getToDateThamGia() != null){
            command.setToDateTimeThamGia(DateUtil.dateToTimestamp(command.getToDateThamGia(), Constants.VI_DATE_FORMAT));
        }
        if(command.getFromDateReportFilter() != null){
            command.setFromDateTimeReportFilter(DateUtil.dateToTimestamp(command.getFromDateReportFilter(), Constants.VI_DATE_FORMAT));
        }
        if(command.getToDateReportFilter() != null){
            command.setToDateTimeReportFilter(DateUtil.dateToTimestamp(command.getToDateReportFilter(), Constants.VI_DATE_FORMAT));
        }
    }

    private void export2Excel_summary(BaoCaoDanhGiaKQTHCTCommand command, HttpServletRequest request, HttpServletResponse response)throws Exception{
        try{
            SimpleDateFormat df = new SimpleDateFormat("dd-M-yyyy");
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            String ngayXuatBaoCao = df.format(currentTimestamp);
            convertDate2Timestamp(command);
            Map<String, Object> properties = buildProperties(command);
            BaoCaoDanhGiaKQTHCTSummaryDTO dto = this.baoCaoDanhGiaKQTHCTManagementLocalBean.baoCaoDanhGiaKQTHCT_summary_tdcg(properties, 0, Integer.MAX_VALUE, command.getSortExpression(), command.getSortDirection());
            Object[] resultObject = new Object[]{0, new ArrayList<BaoCaoDanhGiaKQTHCTSummaryDTO>()};
            List<BaoCaoDanhGiaKQTHCTSummaryDTO> dtoList = (List<BaoCaoDanhGiaKQTHCTSummaryDTO>)resultObject[1];
            String outputFileName = "/files/temp/baocaodanhgiakqthct_" + ngayXuatBaoCao + ".xls";
            String reportTemplate = request.getSession().getServletContext().getRealPath("/files/temp/export/baocaodanhgiakqthct_summary.xls");
            String export2FileName = request.getSession().getServletContext().getRealPath(outputFileName);
            WorkbookSettings ws = new WorkbookSettings();
            ExcelUtil.setEncoding4Workbook(ws);
            Workbook templateWorkbook = Workbook.getWorkbook(new File(reportTemplate), ws);
            WritableWorkbook workbook = Workbook.createWorkbook(new File(export2FileName), templateWorkbook);
            WritableSheet sheet = workbook.getSheet(0);
            int startRow = 12;

            WritableFont normalFont = new WritableFont(WritableFont.TIMES, 10, WritableFont.NO_BOLD);
            normalFont.setColour(Colour.BLACK);

            WritableFont boldFont = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD);
            normalFont.setColour(Colour.BLACK);

            WritableCellFormat stringCellFormat = new WritableCellFormat(normalFont);
            stringCellFormat.setBorder(Border.ALL, BorderLineStyle.MEDIUM);

            WritableCellFormat stringNgayBaoCaoCellFormat = new WritableCellFormat(normalFont);
            stringNgayBaoCaoCellFormat.setBorder(Border.ALL, BorderLineStyle.NONE);
            stringNgayBaoCaoCellFormat.setAlignment(Alignment.CENTRE);

            WritableCellFormat integerCellFormat = new WritableCellFormat(normalFont);
            integerCellFormat.setBorder(Border.ALL, BorderLineStyle.MEDIUM);
            integerCellFormat.setAlignment(Alignment.CENTRE);

            NumberFormat nf = new NumberFormat("#,###");
            WritableCellFormat doubleCellFormat = new WritableCellFormat(nf);
            doubleCellFormat.setFont(normalFont);
            doubleCellFormat.setBorder(Border.ALL, BorderLineStyle.MEDIUM);
            doubleCellFormat.setAlignment(Alignment.RIGHT);

            if(command.getBranchId() != null){
                BranchDTO branchDTO = this.branchManagementLocalBean.findBranchById_tdcg(command.getBranchId());
                CellValue[] resValueNgayThamGiaCTTu = WebCommonUtil.addNgayXuatBaoCao(TOTAL_COLUMN_EXPORT);
                resValueNgayThamGiaCTTu[0] = new CellValue(CellDataType.STRING, "Chi nhánh: " + branchDTO.getBranch_name());
                ExcelUtil.addRow(sheet, 4, resValueNgayThamGiaCTTu, stringNgayBaoCaoCellFormat, null, null, null);
            }
            if(command.getDistrictId() != null){
                DistrictDTO districtDTO = this.districtManagementLocalBean.findByDistrictId(command.getDistrictId());
                CellValue[] resValueNgayThamGiaCTTu = WebCommonUtil.addNgayXuatBaoCao(TOTAL_COLUMN_EXPORT);
                resValueNgayThamGiaCTTu[0] = new CellValue(CellDataType.STRING, "Quận huyện: " + districtDTO.getDistrict_name());
                ExcelUtil.addRow(sheet, 5, resValueNgayThamGiaCTTu, stringNgayBaoCaoCellFormat, null, null, null);
            }
            if(command.getFromDateTimeThamGia() != null){
                CellValue[] resValueNgayThamGiaCTTu = WebCommonUtil.addNgayXuatBaoCao(TOTAL_COLUMN_EXPORT);
                resValueNgayThamGiaCTTu[0] = new CellValue(CellDataType.STRING, "Ngày tham gia chương trình từ: " + df.format(command.getFromDateTimeThamGia()));
                ExcelUtil.addRow(sheet, 6, resValueNgayThamGiaCTTu, stringNgayBaoCaoCellFormat, null, null, null);
            }
            if(command.getToDateTimeThamGia() != null){
                CellValue[] resValueNgayThamGiaCTDen = WebCommonUtil.addNgayXuatBaoCao(TOTAL_COLUMN_EXPORT);
                resValueNgayThamGiaCTDen[0] = new CellValue(CellDataType.STRING, "Ngày tham gia chương trình đến: " + df.format(command.getToDateTimeThamGia()));
                ExcelUtil.addRow(sheet, 7, resValueNgayThamGiaCTDen, stringNgayBaoCaoCellFormat, null, null, null);
            }

            if(command.getFromDateTimeReportFilter() != null){
                CellValue[] resValueNgayPhatSinhTu = WebCommonUtil.addNgayXuatBaoCao(TOTAL_COLUMN_EXPORT);
                resValueNgayPhatSinhTu[0] = new CellValue(CellDataType.STRING, "Ngày phát sinh từ: " + df.format(command.getFromDateReportFilter()));
                ExcelUtil.addRow(sheet, 8, resValueNgayPhatSinhTu, stringNgayBaoCaoCellFormat, null, null, null);
            }
            if(command.getToDateTimeReportFilter() != null){
                CellValue[] resValueNgayPahtSinhDen = WebCommonUtil.addNgayXuatBaoCao(TOTAL_COLUMN_EXPORT);
                resValueNgayPahtSinhDen[0] = new CellValue(CellDataType.STRING, "Ngày phát sinh đến: " + df.format(command.getToDateReportFilter()));
                ExcelUtil.addRow(sheet, 9, resValueNgayPahtSinhDen, stringNgayBaoCaoCellFormat, null, null, null);
            }

            for(int i = 1; i <= 41; i++){
                CellValue[] resValue = addCellValues_summary(dto, i);
                ExcelUtil.addRow(sheet, startRow++, resValue, stringCellFormat, integerCellFormat, doubleCellFormat, null);
            }
            workbook.write();
            workbook.close();
            response.sendRedirect(request.getSession().getServletContext().getContextPath() + outputFileName);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            throw new Exception("Failed to export to excel BaoCaoDanhGiaKetQuaThucHienCT. \n" + e.getMessage());
        }
    }

    private void export2Excel_theoNgay(BaoCaoDanhGiaKQTHCTCommand command, HttpServletRequest request, HttpServletResponse response)throws Exception{
        try{
            SimpleDateFormat df = new SimpleDateFormat("dd-M-yyyy");
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            String ngayXuatBaoCao = df.format(currentTimestamp);
            convertDate2Timestamp(command);
            Map<String, Object> properties = buildProperties(command);
            BaoCaoDanhGiaKQTHCTChiTietTheoNgayDTO dto = this.baoCaoDanhGiaKQTHCTManagementLocalBean.baoCaoDanhGiaKQTHCT_theoNgay_tdcg(properties, 0, Integer.MAX_VALUE, command.getSortExpression(), command.getSortDirection());
            Object[] resultObject = new Object[]{0, new ArrayList<BaoCaoDanhGiaKQTHCTSummaryDTO>()};
            List<BaoCaoDanhGiaKQTHCTSummaryDTO> dtoList = (List<BaoCaoDanhGiaKQTHCTSummaryDTO>)resultObject[1];
            String outputFileName = "/files/temp/baocaodanhgiakqthct_" + ngayXuatBaoCao + ".xls";
            String reportTemplate = request.getSession().getServletContext().getRealPath("/files/temp/export/baocaodanhgiakqthct_theoNgay.xls");
            String export2FileName = request.getSession().getServletContext().getRealPath(outputFileName);
            WorkbookSettings ws = new WorkbookSettings();
            ExcelUtil.setEncoding4Workbook(ws);
            Workbook templateWorkbook = Workbook.getWorkbook(new File(reportTemplate), ws);
            WritableWorkbook workbook = Workbook.createWorkbook(new File(export2FileName), templateWorkbook);
            WritableSheet sheet = workbook.getSheet(0);
            int startRow = 12;

            WritableFont normalFont = new WritableFont(WritableFont.TIMES, 10, WritableFont.NO_BOLD);
            normalFont.setColour(Colour.BLACK);

            WritableFont boldFont = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD);
            normalFont.setColour(Colour.BLACK);

            WritableCellFormat stringCellFormat = new WritableCellFormat(normalFont);
            stringCellFormat.setBorder(Border.ALL, BorderLineStyle.MEDIUM);

            WritableCellFormat stringNgayBaoCaoCellFormat = new WritableCellFormat(normalFont);
            stringNgayBaoCaoCellFormat.setBorder(Border.ALL, BorderLineStyle.NONE);
            stringNgayBaoCaoCellFormat.setAlignment(Alignment.CENTRE);

            WritableCellFormat integerCellFormat = new WritableCellFormat(normalFont);
            integerCellFormat.setBorder(Border.ALL, BorderLineStyle.MEDIUM);
            integerCellFormat.setAlignment(Alignment.CENTRE);

            NumberFormat nf = new NumberFormat("#,###");
            WritableCellFormat doubleCellFormat = new WritableCellFormat(nf);
            doubleCellFormat.setFont(normalFont);
            doubleCellFormat.setBorder(Border.ALL, BorderLineStyle.MEDIUM);
            doubleCellFormat.setAlignment(Alignment.RIGHT);

            if(command.getBranchId() != null){
                BranchDTO branchDTO = this.branchManagementLocalBean.findBranchById_tdcg(command.getBranchId());
                CellValue[] resValueNgayThamGiaCTTu = WebCommonUtil.addNgayXuatBaoCao(TOTAL_COLUMN_EXPORT);
                resValueNgayThamGiaCTTu[0] = new CellValue(CellDataType.STRING, "Chi nhánh: " + branchDTO.getBranch_name());
                ExcelUtil.addRow(sheet, 4, resValueNgayThamGiaCTTu, stringNgayBaoCaoCellFormat, null, null, null);
            }
            if(command.getDistrictId() != null){
                DistrictDTO districtDTO = this.districtManagementLocalBean.findByDistrictId(command.getDistrictId());
                CellValue[] resValueNgayThamGiaCTTu = WebCommonUtil.addNgayXuatBaoCao(TOTAL_COLUMN_EXPORT);
                resValueNgayThamGiaCTTu[0] = new CellValue(CellDataType.STRING, "Quận huyện: " + districtDTO.getDistrict_name());
                ExcelUtil.addRow(sheet, 5, resValueNgayThamGiaCTTu, stringNgayBaoCaoCellFormat, null, null, null);
            }

            if(command.getFromDateTimeThamGia() != null){
                CellValue[] resValueNgayThamGiaCTTu = WebCommonUtil.addNgayXuatBaoCao(TOTAL_COLUMN_EXPORT);
                resValueNgayThamGiaCTTu[0] = new CellValue(CellDataType.STRING, "Ngày tham gia chương trình từ: " + df.format(command.getFromDateTimeThamGia()));
                ExcelUtil.addRow(sheet, 6, resValueNgayThamGiaCTTu, stringNgayBaoCaoCellFormat, null, null, null);
            }
            if(command.getToDateTimeThamGia() != null){
                CellValue[] resValueNgayThamGiaCTDen = WebCommonUtil.addNgayXuatBaoCao(TOTAL_COLUMN_EXPORT);
                resValueNgayThamGiaCTDen[0] = new CellValue(CellDataType.STRING, "Ngày tham gia chương trình đến: " + df.format(command.getToDateTimeThamGia()));
                ExcelUtil.addRow(sheet, 7, resValueNgayThamGiaCTDen, stringNgayBaoCaoCellFormat, null, null, null);
            }

            if(command.getFromDateTimeReportFilter() != null){
                CellValue[] resValueNgayPhatSinhTu = WebCommonUtil.addNgayXuatBaoCao(TOTAL_COLUMN_EXPORT);
                resValueNgayPhatSinhTu[0] = new CellValue(CellDataType.STRING, "Ngày phát sinh từ: " + df.format(command.getFromDateReportFilter()));
                ExcelUtil.addRow(sheet, 8, resValueNgayPhatSinhTu, stringNgayBaoCaoCellFormat, null, null, null);
            }

            if(command.getToDateTimeReportFilter() != null){
                CellValue[] resValueNgayPhatSinhDen = WebCommonUtil.addNgayXuatBaoCao(TOTAL_COLUMN_EXPORT);
                resValueNgayPhatSinhDen[0] = new CellValue(CellDataType.STRING, "Ngày phát sinh đến: " + df.format(command.getToDateReportFilter()));
                ExcelUtil.addRow(sheet, 9, resValueNgayPhatSinhDen, stringNgayBaoCaoCellFormat, null, null, null);
            }


            // table 1
            for(int i = 1; i <= 14; i++){
                CellValue[] resValue = addCellValues_theoNgay_table1(dto, i);
                ExcelUtil.addRow(sheet, startRow++, resValue, stringCellFormat, integerCellFormat, doubleCellFormat, null);
            }

            boldFont.setPointSize(11);
            WritableCellFormat stringTitleCellFormat = new WritableCellFormat(normalFont);
            stringTitleCellFormat.setBorder(Border.ALL, BorderLineStyle.MEDIUM);
            stringTitleCellFormat.setAlignment(Alignment.CENTRE);
            stringTitleCellFormat.setFont(boldFont);

            // table 2: tiru chi 3 and 4
            startRow++;
            startRow++;
            startRow++;
            startRow++;
            CellValue[] titleTable2_row1 = new CellValue[2 + (dto.getDanhSachNgayRender_tieuChi3And4().size() * 2)];
            int fromCell_row1 = 0;
            titleTable2_row1[fromCell_row1++] = new CellValue(CellDataType.STRING, "STT");
            titleTable2_row1[fromCell_row1++] = new CellValue(CellDataType.STRING, "Tiêu Chí");
            for(BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO ngayDTO : dto.getDanhSachNgayRender_tieuChi3And4()){
                titleTable2_row1[fromCell_row1++] = new CellValue(CellDataType.STRING, df.format(ngayDTO.getDate()));
                fromCell_row1++;
            }

            CellValue[] titleTable2_row2 = new CellValue[2 + (dto.getDanhSachNgayRender_tieuChi3And4().size() * 2)];
            int fromCell_row2 = 0;
            titleTable2_row2[fromCell_row2++] = new CellValue();
            titleTable2_row2[fromCell_row2++] = new CellValue();
            for(BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO ngayDTO : dto.getDanhSachNgayRender_tieuChi3And4()){
                titleTable2_row2[fromCell_row2++] = new CellValue(CellDataType.STRING, "Thuê bao mới");
                titleTable2_row2[fromCell_row2++] = new CellValue(CellDataType.STRING, "Thuê bao cũ");
            }
            ExcelUtil.addRow(sheet, 28, titleTable2_row1, stringTitleCellFormat, integerCellFormat, doubleCellFormat, null);
            ExcelUtil.addRow(sheet, 29, titleTable2_row2, stringTitleCellFormat, integerCellFormat, doubleCellFormat, null);

            for(int i = 1; i <= 14; i++){
                CellValue[] resValue = addCellValues_theoNgay_table2(dto, i);
                ExcelUtil.addRow(sheet, startRow++, resValue, stringCellFormat, integerCellFormat, doubleCellFormat, null);
            }

            //table 3 : tieu chi 5 and 6
            startRow++;
            startRow++;
            startRow++;
            startRow++;
            CellValue[] titleTable3_row1 = new CellValue[2 + (dto.getDanhSachNgayRender_tieuChi5And6().size() * 2)];
            int fromCell_row3 = 0;
            titleTable3_row1[fromCell_row3++] = new CellValue(CellDataType.STRING, "STT");
            titleTable3_row1[fromCell_row3++] = new CellValue(CellDataType.STRING, "Tiêu Chí");
            for(BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO ngayDTO : dto.getDanhSachNgayRender_tieuChi5And6()){
                titleTable3_row1[fromCell_row3++] = new CellValue(CellDataType.STRING, df.format(ngayDTO.getDate()));
                fromCell_row3++;
            }

            CellValue[] titleTable3_row2 = new CellValue[2 + (dto.getDanhSachNgayRender_tieuChi5And6().size() * 2)];
            int fromCell_row4 = 0;
            titleTable3_row2[fromCell_row4++] = new CellValue();
            titleTable3_row2[fromCell_row4++] = new CellValue();
            for(BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO ngayDTO : dto.getDanhSachNgayRender_tieuChi5And6()){
                titleTable3_row2[fromCell_row4++] = new CellValue(CellDataType.STRING, "Thuê bao mới");
                titleTable3_row2[fromCell_row4++] = new CellValue(CellDataType.STRING, "Thuê bao cũ");
            }
            ExcelUtil.addRow(sheet, 46, titleTable3_row1, stringTitleCellFormat, integerCellFormat, doubleCellFormat, null);
            ExcelUtil.addRow(sheet, 47, titleTable3_row2, stringTitleCellFormat, integerCellFormat, doubleCellFormat, null);

            for(int i = 1; i <= 14; i++){
                CellValue[] resValue = addCellValues_theoNgay_table3(dto, i);
                ExcelUtil.addRow(sheet, startRow++, resValue, stringCellFormat, integerCellFormat, doubleCellFormat, null);
            }

            workbook.write();
            workbook.close();
            response.sendRedirect(request.getSession().getServletContext().getContextPath() + outputFileName);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            throw new Exception("Failed to export to excel BaoCaoDanhSachMaThuong. \n" + e.getMessage());
        }
    }

    private CellValue[] addCellValues_summary(BaoCaoDanhGiaKQTHCTSummaryDTO dto, int indexRow){
        SimpleDateFormat df = new SimpleDateFormat("dd/M/yyyy");
        CellValue[] resValue = new CellValue[TOTAL_COLUMN_EXPORT];
        int columnIndex = 0;
        if(indexRow == 1){
            resValue[columnIndex++] = new CellValue(CellDataType.INT, 1);
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "Số lượng thuê bao tham gia chương trình");
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi1_tb_moi());
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi1_tb_cu());
        }else if(indexRow == 2){
            resValue[columnIndex++] = new CellValue(CellDataType.INT, 2);
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "Số lượng thuê bao tham gia chương trình trên VLR");
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi2_tb_moi());
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi2_tb_cu());
        }else if(indexRow == 3){
            resValue[columnIndex++] = new CellValue(CellDataType.INT, 3);
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "Số tiền tiêu dùng tài khoản chính tháng 9/2014");
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi3_td_tkc_moi());
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi3_td_tkc_cu());
        }else if(indexRow == 4){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "Thoại");
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi3_t_moi());
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi3_t_cu());
        }else if(indexRow == 5){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "SMS");
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi3_s_moi());
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi3_s_cu());
        }else if(indexRow == 6){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "Data");
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi3_d_moi());
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi3_d_cu());
        }else if(indexRow == 7){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "GTGT");
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi3_v_moi());
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi3_v_cu());
        }else if(indexRow == 8){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "RMQT outbound");
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi3_r_moi());
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi3_r_cu());
        }else if(indexRow == 9){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "Khác");
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi3_k_moi());
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi3_k_cu());
        }else if(indexRow == 10){
            resValue[columnIndex++] = new CellValue(CellDataType.INT, 4);
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "Số tiền tiêu dùng tài khoản thưởng tháng 9/2014");
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi4_s_moi());
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi4_td_tkt_cu());
        }else if(indexRow == 11){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "Thoại");
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi4_t_moi());
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi4_t_cu());
        }else if(indexRow == 12){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "SMS");
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi4_s_moi());
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi4_s_cu());
        }else if(indexRow == 13){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "Data");
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi4_d_moi());
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi4_d_cu());
        }else if(indexRow == 14){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "GTGT");
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi4_v_moi());
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi4_v_cu());
        }else if(indexRow == 15){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "RMQT outbound");
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi4_r_moi());
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi4_r_cu());
        }else if(indexRow == 16){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "Khác");
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi4_k_moi());
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi4_k_cu());
        }else if(indexRow == 17){
            resValue[columnIndex++] = new CellValue(CellDataType.INT, 5);
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "Số tiền tiêu dùng tài khoản chính trong thời gian triển khai");
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi5_td_tkc_moi());
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi5_td_tkc_cu());
        }else if(indexRow == 18){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "Thoại");
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi5_t_moi());
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi5_t_cu());
        }else if(indexRow == 19){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "SMS");
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi5_s_moi());
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi5_s_cu());
        }else if(indexRow == 20){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "Data");
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi5_d_moi());
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi5_d_cu());
        }else if(indexRow == 21){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "GTGT");
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi5_v_moi());
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi5_v_cu());
        }else if(indexRow == 22){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "RMQT outbound");
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi5_r_moi());
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi5_r_cu());
        }else if(indexRow == 23){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "Khác");
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi5_k_moi());
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi5_k_cu());
        }else if(indexRow == 24){
            resValue[columnIndex++] = new CellValue(CellDataType.INT, 6);
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "Số tiền tiêu dùng tài khoản thưởng trong thời gian triển khai");
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi6_td_tkt_moi());
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi6_td_tkt_cu());
        }else if(indexRow == 25){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "Thoại");
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi6_t_moi());
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi6_t_cu());
        }else if(indexRow == 26){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "SMS");
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi6_s_moi());
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi6_s_cu());
        }else if(indexRow == 27){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "Data");
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi6_d_moi());
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi6_d_cu());
        }else if(indexRow == 28){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "GTGT");
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi6_v_moi());
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi6_v_cu());
        }else if(indexRow == 29){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "RMQT outbound");
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi6_r_moi());
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi6_r_cu());
        }else if(indexRow == 30){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "Khác");
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi6_k_moi());
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi6_k_cu());
        }else if(indexRow == 31){
            resValue[columnIndex++] = new CellValue(CellDataType.INT, 7);
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "Số lượng thuê bao đủ điều kiện cấp mã đổi thưởng");
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi7_sl_tb_ddk_cap_ma_moi());
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi7_sl_tb_ddk_cap_ma_cu());
        }else if(indexRow == 32){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "1 mã");
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi7_1_ma_moi());
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi7_1_ma_cu());
        }else if(indexRow == 33){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "2 mã");
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi7_2_ma_moi());
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi7_2_ma_cu());
        }else if(indexRow == 34){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "3 mã");
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi7_3_ma_moi());
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi7_3_ma_cu());
        }else if(indexRow == 35){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "Trên 3 mã");
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi7_tren_3_ma_moi());
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi7_tren_3_ma_cu());
        }else if(indexRow == 36){
            resValue[columnIndex++] = new CellValue(CellDataType.INT, 8);
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "Số lượng thuê bao đã đổi thưởng");
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi8_sl_tb_da_doi_thuong_moi());
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi8_sl_tb_da_doi_thuong_cu());
        }else if(indexRow == 37){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "1 mã");
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi8_1_phieu_moi());
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi8_1_phieu_cu());
        }else if(indexRow == 38){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "2 mã");
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi8_2_phieu_moi());
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi8_2_phieu_cu());
        }else if(indexRow == 38){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "3 mã");
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi8_3_phieu_moi());
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi8_3_phieu_cu());
        }else if(indexRow == 39){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "trên 3 mã");
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi8_tren_3_phieu_moi());
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi8_tren_3_phieu_cu());
        }else if(indexRow == 40){
            resValue[columnIndex++] = new CellValue(CellDataType.INT, 9);
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "Số lượng mã đổi thưởng đã phát sinh");
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi9_sl_ma_doi_da_ps_moi());
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi9_sl_ma_doi_da_ps_cu());
        }else if(indexRow == 41){
            resValue[columnIndex++] = new CellValue(CellDataType.INT, 10);
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "Số lượng phiếu quà tặng đã đổi");
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi10_sl_phieu_qua_tang_da_doi_moi());
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi10_sl_phieu_qua_tang_da_doi_cu());
        }
        return resValue;
    }

    private CellValue[] addCellValues_theoNgay_table1(BaoCaoDanhGiaKQTHCTChiTietTheoNgayDTO dto, int indexRow){
        SimpleDateFormat df = new SimpleDateFormat("dd/M/yyyy");
        CellValue[] resValue = new CellValue[TOTAL_COLUMN_EXPORT];
        int columnIndex = 0;
        if(indexRow == 1){
            resValue[columnIndex++] = new CellValue(CellDataType.INT, 1);
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "Số lượng thuê bao tham gia chương trình");
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi1_tb_moi());
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi1_tb_cu());
        }else if(indexRow == 2){
            resValue[columnIndex++] = new CellValue(CellDataType.INT, 2);
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "Số lượng thuê bao tham gia chương trình trên VLR");
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi2_tb_moi());
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi2_tb_cu());
        }else if(indexRow == 3){
            resValue[columnIndex++] = new CellValue(CellDataType.INT, 3);
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "Số lượng thuê bao đủ điều kiện cấp mã đổi thưởng");
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi7_sl_tb_ddk_cap_ma_moi());
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi7_sl_tb_ddk_cap_ma_cu());
        }else if(indexRow == 4){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "1 mã");
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi7_1_ma_moi());
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi7_1_ma_cu());
        }else if(indexRow == 5){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "2 mã");
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi7_2_ma_moi());
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi7_2_ma_cu());
        }else if(indexRow == 6){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "3 mã");
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi7_3_ma_moi());
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi7_3_ma_cu());
        }else if(indexRow == 7){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "Trên 3 mã");
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi7_tren_3_ma_moi());
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi7_tren_3_ma_cu());
        }else if(indexRow == 8){
            resValue[columnIndex++] = new CellValue(CellDataType.INT, 4);
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "Số lượng thuê bao đã đổi thưởng");
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi8_sl_tb_da_doi_thuong_moi());
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi8_sl_tb_da_doi_thuong_cu());
        }else if(indexRow == 9){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "1 mã");
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi8_1_phieu_moi());
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi8_1_phieu_cu());
        }else if(indexRow == 10){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "2 mã");
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi8_2_phieu_moi());
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi8_2_phieu_cu());
        }else if(indexRow == 11){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "3 mã");
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi8_3_phieu_moi());
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi8_3_phieu_cu());
        }else if(indexRow == 12){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "trên 3 mã");
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi8_tren_3_phieu_moi());
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi8_tren_3_phieu_cu());
        }else if(indexRow == 13){
            resValue[columnIndex++] = new CellValue(CellDataType.INT, 5);
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "Số lượng mã đổi thưởng đã phát sinh");
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi9_sl_ma_doi_da_ps_moi());
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi9_sl_ma_doi_da_ps_cu());
        }else if(indexRow == 14){
            resValue[columnIndex++] = new CellValue(CellDataType.INT, 6);
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "Số lượng phiếu quà tặng đã đổi");
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi10_sl_phieu_qua_tang_da_doi_moi());
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTieuChi10_sl_phieu_qua_tang_da_doi_cu());
        }
        return resValue;
    }

    private CellValue[] addCellValues_theoNgay_table2(BaoCaoDanhGiaKQTHCTChiTietTheoNgayDTO dto, int indexRow){
        SimpleDateFormat df = new SimpleDateFormat("dd/M/yyyy");
        Integer total_column = dto.getDanhSachNgayRender_tieuChi3And4().size() * 2 + 2;
        CellValue[] resValue = new CellValue[total_column];
        int columnIndex = 0;
        if(indexRow == 1){
            resValue[columnIndex++] = new CellValue(CellDataType.INT, 1);
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "Số tiền tiêu dùng tài khoản chính tháng 9/2014");
            for(BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO dateDTO : dto.getTieuChi3_summary()){
                resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dateDTO.getDoanhThu_sau());
                resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dateDTO.getDoanhThu_truoc());
            }
        }else if(indexRow == 2){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "Thoại");
            for(BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO dateDTO : dto.getTieuChi3_loai_t()){
                resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dateDTO.getDoanhThu_sau());
                resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dateDTO.getDoanhThu_truoc());
            }
        }else if(indexRow == 3){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "SMS");
            for(BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO dateDTO : dto.getTieuChi3_loai_s()){
                resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dateDTO.getDoanhThu_sau());
                resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dateDTO.getDoanhThu_truoc());
            }
        }else if(indexRow == 4){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "Data");
            for(BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO dateDTO : dto.getTieuChi3_loai_d()){
                resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dateDTO.getDoanhThu_sau());
                resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dateDTO.getDoanhThu_truoc());
            }
        }else if(indexRow == 5){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "GTGT");
            for(BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO dateDTO : dto.getTieuChi3_loai_v()){
                resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dateDTO.getDoanhThu_sau());
                resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dateDTO.getDoanhThu_truoc());
            }
        }else if(indexRow == 6){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "RMQT outbound");
            for(BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO dateDTO : dto.getTieuChi3_loai_r()){
                resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dateDTO.getDoanhThu_sau());
                resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dateDTO.getDoanhThu_truoc());
            }
        }else if(indexRow == 7){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "Khác");
            for(BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO dateDTO : dto.getTieuChi3_loai_k()){
                resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dateDTO.getDoanhThu_sau());
                resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dateDTO.getDoanhThu_truoc());
            }
        }else if(indexRow == 8){
            resValue[columnIndex++] = new CellValue(CellDataType.INT, 2);
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "Số tiền tiêu dùng tài khoản thưởng tháng 9/2014");
            for(BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO dateDTO : dto.getTieuChi4_summary()){
                resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dateDTO.getDoanhThu_sau());
                resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dateDTO.getDoanhThu_truoc());
            }
        }else if(indexRow == 9){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "Thoại");
            for(BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO dateDTO : dto.getTieuChi4_loai_t()){
                resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dateDTO.getDoanhThu_sau());
                resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dateDTO.getDoanhThu_truoc());
            }
        }else if(indexRow == 10){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "SMS");
            for(BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO dateDTO : dto.getTieuChi4_loai_s()){
                resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dateDTO.getDoanhThu_sau());
                resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dateDTO.getDoanhThu_truoc());
            }
        }else if(indexRow == 11){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "Data");
            for(BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO dateDTO : dto.getTieuChi4_loai_d()){
                resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dateDTO.getDoanhThu_sau());
                resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dateDTO.getDoanhThu_truoc());
            }
        }else if(indexRow == 12){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "GTGT");
            for(BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO dateDTO : dto.getTieuChi4_loai_v()){
                resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dateDTO.getDoanhThu_sau());
                resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dateDTO.getDoanhThu_truoc());
            }
        }else if(indexRow == 13){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "RMQT outbound");
            for(BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO dateDTO : dto.getTieuChi4_loai_r()){
                resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dateDTO.getDoanhThu_sau());
                resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dateDTO.getDoanhThu_truoc());
            }
        }else if(indexRow == 14){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "Khác");
            for(BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO dateDTO : dto.getTieuChi4_loai_k()){
                resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dateDTO.getDoanhThu_sau());
                resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dateDTO.getDoanhThu_truoc());
            }
        }
        return resValue;
    }

    private CellValue[] addCellValues_theoNgay_table3(BaoCaoDanhGiaKQTHCTChiTietTheoNgayDTO dto, int indexRow){
        SimpleDateFormat df = new SimpleDateFormat("dd/M/yyyy");
        Integer total_column = dto.getDanhSachNgayRender_tieuChi5And6().size() * 2 + 2;
        CellValue[] resValue = new CellValue[total_column];
        int columnIndex = 0;
        if(indexRow == 1){
            resValue[columnIndex++] = new CellValue(CellDataType.INT, 1);
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "Số tiền tiêu dùng tài khoản chính trong thời gian triển khai");
            for(BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO dateDTO : dto.getTieuChi5_summary()){
                resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dateDTO.getDoanhThu_sau());
                resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dateDTO.getDoanhThu_truoc());
            }
        }else if(indexRow == 2){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "Thoại");
            for(BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO dateDTO : dto.getTieuChi5_loai_t()){
                resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dateDTO.getDoanhThu_sau());
                resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dateDTO.getDoanhThu_truoc());
            }
        }else if(indexRow == 3){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "SMS");
            for(BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO dateDTO : dto.getTieuChi5_loai_s()){
                resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dateDTO.getDoanhThu_sau());
                resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dateDTO.getDoanhThu_truoc());
            }
        }else if(indexRow == 4){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "Data");
            for(BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO dateDTO : dto.getTieuChi5_loai_d()){
                resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dateDTO.getDoanhThu_sau());
                resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dateDTO.getDoanhThu_truoc());
            }
        }else if(indexRow == 5){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "GTGT");
            for(BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO dateDTO : dto.getTieuChi5_loai_v()){
                resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dateDTO.getDoanhThu_sau());
                resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dateDTO.getDoanhThu_truoc());
            }
        }else if(indexRow == 6){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "RMQT outbound");
            for(BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO dateDTO : dto.getTieuChi5_loai_r()){
                resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dateDTO.getDoanhThu_sau());
                resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dateDTO.getDoanhThu_truoc());
            }
        }else if(indexRow == 7){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "Khác");
            for(BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO dateDTO : dto.getTieuChi5_loai_k()){
                resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dateDTO.getDoanhThu_sau());
                resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dateDTO.getDoanhThu_truoc());
            }
        }else if(indexRow == 8){
            resValue[columnIndex++] = new CellValue(CellDataType.INT,2);
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "Số tiền tiêu dùng tài khoản thưởng trong thời gian triển khai");
            for(BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO dateDTO : dto.getTieuChi6_summary()){
                resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dateDTO.getDoanhThu_sau());
                resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dateDTO.getDoanhThu_truoc());
            }
        }else if(indexRow == 9){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "Thoại");
            for(BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO dateDTO : dto.getTieuChi6_loai_t()){
                resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dateDTO.getDoanhThu_sau());
                resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dateDTO.getDoanhThu_truoc());
            }
        }else if(indexRow == 10){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "SMS");
            for(BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO dateDTO : dto.getTieuChi6_loai_s()){
                resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dateDTO.getDoanhThu_sau());
                resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dateDTO.getDoanhThu_truoc());
            }
        }else if(indexRow == 11){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "Data");
            for(BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO dateDTO : dto.getTieuChi6_loai_d()){
                resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dateDTO.getDoanhThu_sau());
                resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dateDTO.getDoanhThu_truoc());
            }
        }else if(indexRow == 12){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "GTGT");
            for(BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO dateDTO : dto.getTieuChi6_loai_v()){
                resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dateDTO.getDoanhThu_sau());
                resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dateDTO.getDoanhThu_truoc());
            }
        }else if(indexRow == 13){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "RMQT outbound");
            for(BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO dateDTO : dto.getTieuChi6_loai_r()){
                resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dateDTO.getDoanhThu_sau());
                resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dateDTO.getDoanhThu_truoc());
            }
        }else if(indexRow == 14){
            resValue[columnIndex++] = new CellValue();
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, "Khác");
            for(BaoCaoDanhGiaKetQuaThucHienCTTheoNgayDTO dateDTO : dto.getTieuChi6_loai_k()){
                resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dateDTO.getDoanhThu_sau());
                resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dateDTO.getDoanhThu_truoc());
            }
        }
        return resValue;
    }
}
