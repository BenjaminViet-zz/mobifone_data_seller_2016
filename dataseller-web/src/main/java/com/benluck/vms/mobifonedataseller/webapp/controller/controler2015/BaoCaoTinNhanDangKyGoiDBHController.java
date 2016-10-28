package com.benluck.vms.mobifonedataseller.webapp.controller.controler2015;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.utils.DateUtil;
import com.benluck.vms.mobifonedataseller.core.business.BaoCaoSoEZ_NhanTinThamGiaChuongTrinhManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.BranchMappingManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.PromPackageManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BranchMappingDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015.BaoCaoSoEZNhanTinThamGiaChuongTrinhDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015.PromPackageDTO;
import com.benluck.vms.mobifonedataseller.editor.CustomDateEditor;
import com.benluck.vms.mobifonedataseller.security.util.SecurityUtils;
import com.benluck.vms.mobifonedataseller.common.utils.PromotionEnum;
import com.benluck.vms.mobifonedataseller.util.ExcelUtil;
import com.benluck.vms.mobifonedataseller.util.RequestUtil;
import com.benluck.vms.mobifonedataseller.util.WebCommonUtil;
import com.benluck.vms.mobifonedataseller.webapp.command.command2015.BaoCaoSoEZNhanTinThamGiaChuongTrinhCommand;
import com.benluck.vms.mobifonedataseller.webapp.dto.CellDataType;
import com.benluck.vms.mobifonedataseller.webapp.dto.CellValue;
import com.benluck.vms.mobifonedataseller.webapp.validator.ThongTinThueBaoPTMValidator;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.ejb.ObjectNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: HP
 * Date: 2/26/15
 * Time: 10:49 AM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class BaoCaoTinNhanDangKyGoiDBHController extends ApplicationObjectSupport {
    private Log log = LogFactory.getLog(BaoCaoTinNhanDangKyGoiDBHController.class);
    private static final Integer TOTAL_COLUMN_EXPORT = 8;
    private static final Integer number_of_templte_export_rows = 7;         // number of rows in export template.

    @Autowired
    private ThongTinThueBaoPTMValidator validator;
    @Autowired
    private PromPackageManagementLocalBean promPackageService;
    @Autowired
    private BranchMappingManagementLocalBean branchMappingManagementLocalBean;
    @Autowired
    private BaoCaoSoEZ_NhanTinThamGiaChuongTrinhManagementLocalBean baoCaoSoEZ_nhanTinThamGiaChuongTrinhService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor("dd/MM/yyyy"));
    }

    @RequestMapping(value = {"/admin/thuebaophattrienmoi/baocaotinnhandangkygoidbh.html",
            "/chinhanh/thuebaophattrienmoi/baocaotinnhandangkygoidbh.html",
            "/baocao/thuebaophattrienmoi/baocaotinnhandangkygoidbh.html"})
    public ModelAndView baoCaoSoEZNhanTinThamGiaChuongTrinh(@ModelAttribute(value = Constants.FORM_MODEL_KEY)BaoCaoSoEZNhanTinThamGiaChuongTrinhCommand command,
                                                            BindingResult bindingResult,
                                                            HttpServletRequest request,
                                                            HttpServletResponse response){
        ModelAndView mav = new ModelAndView("2015/thuebaophattrienmoi/admin/baoCaoTinNhanDangKyGoiDBH");
        String crudaction = command.getCrudaction();
        if (StringUtils.isNotBlank(crudaction)){
            if (crudaction.equals("search")){
                RequestUtil.initSearchBean4report(request, command);
                if(command.getFromDate() != null){
                    command.getPojo().setFromDate(DateUtil.dateToTimestamp(command.getFromDate(), Constants.VI_DATE_FORMAT));
                }
                if(command.getToDate() != null){
                    command.getPojo().setToDate(DateUtil.dateToTimestamp(command.getToDate(), Constants.VI_DATE_FORMAT));
                }
                executeSearch(command, request, mav);
            }else if(crudaction.equals("export")){
                try{
                    if(command.getFromDate() != null){
                        command.getPojo().setFromDate(DateUtil.dateToTimestamp(command.getFromDate(), Constants.VI_DATE_FORMAT));
                    }
                    if(command.getToDate() != null){
                        command.getPojo().setToDate(DateUtil.dateToTimestamp(command.getToDate(), Constants.VI_DATE_FORMAT));
                    }
                    export2Excel(command, request, response);
                }catch (Exception e){
                    mav.addObject(Constants.ALERT_TYPE, "danger");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("export_data_exception"));
                }
            }
        }
        referenceData(command, mav);
        return mav;
    }

    private void referenceData(BaoCaoSoEZNhanTinThamGiaChuongTrinhCommand command, ModelAndView mav) {
        List<PromPackageDTO> promPackage = promPackageService.findAll();
        mav.addObject("promPackage", promPackage);
    }

    private void executeSearch(BaoCaoSoEZNhanTinThamGiaChuongTrinhCommand command, HttpServletRequest request, ModelAndView mav) {
        RequestUtil.initSearchBean(request, command);
        if(SecurityUtils.userHasAuthority(Constants.CHINHANH_ROLE)){
            try{
                BranchMappingDTO branchMappingDTO = this.branchMappingManagementLocalBean.findByBranchIdAndProgramCode(SecurityUtils.getPrincipal().getMappingDBLinkBranchId(), PromotionEnum.THUE_BAO_PTM_2015.getCode());
                command.getPojo().setBranchId(branchMappingDTO.getBranchId());
            }catch (ObjectNotFoundException oe){
                log.error(oe.getMessage(), oe);
                mav.addObject(Constants.ALERT_TYPE, "danger");
                mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("baocaotinnhandangkygoi.msg.error"));
                return;
            }
        }
        Object[] results = this.baoCaoSoEZ_nhanTinThamGiaChuongTrinhService.baoCaoSoEZ_NhanTinThamGiaChuongTrinh(command.getPojo(), command.getSortExpression(), command.getSortDirection(), command.getFirstItem(), command.getReportMaxPageItems());
        command.setListResult((List<BaoCaoSoEZNhanTinThamGiaChuongTrinhDTO>)results[1]);
        command.setTotalItems(Integer.valueOf(results[0].toString()));

        mav.addObject(Constants.LIST_MODEL_KEY, command);
        mav.addObject("page", command.getPage() - 1);
        if(command.getTotalItems() == 0){
            mav.addObject(Constants.ALERT_TYPE, "info");
            mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("admin.baocaotheohangmucphatsinh.no_record_found"));
        }
    }

    private void export2Excel(BaoCaoSoEZNhanTinThamGiaChuongTrinhCommand command, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try{
            SimpleDateFormat df = new SimpleDateFormat("dd-M-yyyy");
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            String ngayXuatBaoCao = df.format(currentTimestamp);
            Object[] resultObject = this.baoCaoSoEZ_nhanTinThamGiaChuongTrinhService.baoCaoSoEZ_NhanTinThamGiaChuongTrinh(command.getPojo(), command.getSortExpression(), command.getSortDirection(), 0, Integer.MAX_VALUE);

            List<BaoCaoSoEZNhanTinThamGiaChuongTrinhDTO> dtoList = (List<BaoCaoSoEZNhanTinThamGiaChuongTrinhDTO>)resultObject[1];

            String outputFileName = "/files/temp/baoCaoTinNhanDangKyGoiDBH" + ngayXuatBaoCao + ".xls";
            String reportTemplate = request.getSession().getServletContext().getRealPath("/files/temp/export/thuebaoptm_2015/baoCaoTinNhanDangKyGoiDBH.xls");
            String export2FileName = request.getSession().getServletContext().getRealPath(outputFileName);
            WorkbookSettings ws = new WorkbookSettings();
            ExcelUtil.setEncoding4Workbook(ws);
            Workbook templateWorkbook = Workbook.getWorkbook(new File(reportTemplate), ws);
            WritableWorkbook workbook = Workbook.createWorkbook(new File(export2FileName), templateWorkbook);
            WritableSheet sheet = workbook.getSheet(0);
            int startRow = 7;

            WritableFont normalFont = new WritableFont(WritableFont.TIMES, 10, WritableFont.NO_BOLD);
            normalFont.setColour(Colour.BLACK);

            WritableCellFormat stringCellFormat = new WritableCellFormat(normalFont);
            stringCellFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);

            WritableCellFormat stringNgayBaoCaoCellFormat = new WritableCellFormat(normalFont);
            stringNgayBaoCaoCellFormat.setBorder(Border.ALL, BorderLineStyle.NONE);
            stringNgayBaoCaoCellFormat.setAlignment(Alignment.CENTRE);

            WritableCellFormat integerCellFormat = new WritableCellFormat(normalFont);
            integerCellFormat.setBorder(jxl.format.Border.ALL, BorderLineStyle.THIN);
            integerCellFormat.setAlignment(Alignment.CENTRE);

            NumberFormat nf = new NumberFormat("#,###");
            WritableCellFormat doubleCellFormat = new WritableCellFormat(nf);
            doubleCellFormat.setFont(normalFont);
            doubleCellFormat.setBorder(jxl.format.Border.ALL, BorderLineStyle.THIN);
            doubleCellFormat.setAlignment(Alignment.RIGHT);

            if(command.getFromDate() != null){
                CellValue[] resValueNgayXuatBaoCaoTu = WebCommonUtil.addNgayXuatBaoCao(TOTAL_COLUMN_EXPORT);
                resValueNgayXuatBaoCaoTu[4] = new CellValue(CellDataType.STRING, "Ngày xuất báo cáo từ: " + df.format(command.getFromDate()));
                ExcelUtil.addRow(sheet, 4, resValueNgayXuatBaoCaoTu, stringNgayBaoCaoCellFormat, null, null, null);
            }
            if(command.getToDate() != null){
                CellValue[] resValueNgayXuatBaoCaoTu = WebCommonUtil.addNgayXuatBaoCao(TOTAL_COLUMN_EXPORT);
                resValueNgayXuatBaoCaoTu[4] = new CellValue(CellDataType.STRING, "Ngày xuất báo cáo đến: " + df.format(command.getToDate()));
                ExcelUtil.addRow(sheet, 5, resValueNgayXuatBaoCaoTu, stringNgayBaoCaoCellFormat, null, null, null);
            }

            Integer maxRemainingRowsInSheet = Constants.MAX_EXPORT_RECORDS - number_of_templte_export_rows;

            if(dtoList.size() > 0){
                int indexRow = 1;
                for(BaoCaoSoEZNhanTinThamGiaChuongTrinhDTO dto : dtoList){
                    if(indexRow > 1 && (indexRow % maxRemainingRowsInSheet) == 0){
                        int sheetNum = indexRow / maxRemainingRowsInSheet;
                        if(sheetNum > 2){
                            sheet = workbook.createSheet("Sheet " + (sheetNum + 1), sheetNum);
                        }else{
                            sheet = workbook.getSheet(sheetNum);
                        }
                        startRow = 0;
                    }
                    CellValue[] values = addCellValues(dto, indexRow);
                    ExcelUtil.addRow(sheet, startRow++, values, stringCellFormat, integerCellFormat, doubleCellFormat, null);
                    indexRow++;
                }
                workbook.write();
                workbook.close();
                response.sendRedirect(request.getSession().getServletContext().getContextPath() + outputFileName);
            }
        }catch (Exception e){
            throw new Exception("Error when export data to excel.\n + details: " + e.getMessage());
        }
    }

    private CellValue[] addCellValues(BaoCaoSoEZNhanTinThamGiaChuongTrinhDTO dto, int indexRow) {
        CellValue[] resValue = new CellValue[TOTAL_COLUMN_EXPORT];
        SimpleDateFormat df = new SimpleDateFormat("dd/M/yyyy hh:mm:ss");
        int columnIndex = 0;
        resValue[columnIndex++] = new CellValue(CellDataType.INT, indexRow);
        if(StringUtils.isNotBlank(dto.getSoEZDangKy())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getSoEZDangKy());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(StringUtils.isNotBlank(dto.getThueBaoKH())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getThueBaoKH());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getGoiCuoc() != null && StringUtils.isNotBlank(dto.getGoiCuoc().getPackage_Name())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getGoiCuoc().getPackage_Name());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getThoiGianDK() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, df.format(dto.getThoiGianDK()));
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getMucKhuyenKhich() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getMucKhuyenKhich());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(StringUtils.isNotBlank(dto.getSerial())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getSerial());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        StringBuilder tinh_trang = null;
        if(dto.getTransStatus() != null){
            if(dto.getTransStatus().equals(Constants.TINH_TRANG_KHONG_THANH_CONG)){
                if (dto.getTransError() != null){
                    tinh_trang = new StringBuilder(dto.getTransError());
                }
            }else if(dto.getTransStatus().equals(Constants.TINH_TRANG_THANH_CONG)){
                tinh_trang = new StringBuilder(this.getMessageSourceAccessor().getMessage("label.thanh_cong"));
            }
        }
        if (StringUtils.isNotBlank(tinh_trang)){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, tinh_trang.toString());
        }else {
            resValue[columnIndex++] = new CellValue();
        }
        return resValue;
    }
}
