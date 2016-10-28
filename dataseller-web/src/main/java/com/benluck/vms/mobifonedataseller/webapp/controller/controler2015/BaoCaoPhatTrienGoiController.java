package com.benluck.vms.mobifonedataseller.webapp.controller.controler2015;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.utils.DateUtil;
import com.benluck.vms.mobifonedataseller.core.business.BaoCaoPhatTrienGoiManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.PromPackageManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015.*;
import com.benluck.vms.mobifonedataseller.editor.CustomDateEditor;
import com.benluck.vms.mobifonedataseller.util.ExcelUtil;
import com.benluck.vms.mobifonedataseller.util.RequestUtil;
import com.benluck.vms.mobifonedataseller.util.WebCommonUtil;
import com.benluck.vms.mobifonedataseller.webapp.command.command2015.BaoCaoPhatTrienGoiCommand;
import com.benluck.vms.mobifonedataseller.webapp.dto.CellDataType;
import com.benluck.vms.mobifonedataseller.webapp.dto.CellValue;
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
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: vovanphuc0810
 * Date: 7/15/15
 * Time: 9:59 AM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class BaoCaoPhatTrienGoiController extends ApplicationObjectSupport {
    private transient final Log log = LogFactory.getLog(getClass());
    private static final Integer TOTAL_COLUMN_EXPORT = 10;
    private static final Integer number_of_templte_export_rows = 11;         // number of rows in export template.

    @Autowired
    private PromPackageManagementLocalBean promPackageService;
    @Autowired
    private BaoCaoPhatTrienGoiManagementLocalBean baoCaoPhatTrienGoiService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor("dd/MM/yyyy"));
    }

    @RequestMapping(value = {"/admin/thuebaophattrienmoi/BaoCaoPhatTrienGoi.html",
            "/chinhanh/thuebaophattrienmoi/BaoCaoPhatTrienGoi.html",
            "/baocao/thuebaophattrienmoi/BaoCaoPhatTrienGoi.html"})
    public ModelAndView report(@ModelAttribute(value = Constants.FORM_MODEL_KEY)BaoCaoPhatTrienGoiCommand command,
                               HttpServletRequest request,
                               HttpServletResponse response)throws ObjectNotFoundException {
        ModelAndView mav = new ModelAndView("2015/thuebaophattrienmoi/admin/baocaophattriengoi");
        String crudaction = command.getCrudaction();
        if (StringUtils.isNotBlank(crudaction) && command.getToDate() != null && command.getFromDate() != null){
            if (crudaction.equals("search")){
                RequestUtil.initSearchBean4report(request, command);
                convertDate2Timestamp(command);
                Map<String, Object> properties = buildProperties(command);
                Object[] results = this.baoCaoPhatTrienGoiService.baoCaoPhatTrienGoi(properties, command.getSortExpression(), command.getSortDirection(), command.getFirstItem(), command.getReportMaxPageItems());
                command.setListResult((List<BaoCaoPhatTrienGoiDTO>)results[1]);
                command.setTotalItems(Integer.valueOf(results[0].toString()));
                mav.addObject(Constants.LIST_MODEL_KEY, command);
                mav.addObject("page", command.getPage() - 1);
                if(command.getTotalItems() == 0){
                    mav.addObject(Constants.ALERT_TYPE, "info");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("admin.baocaotheohangmucphatsinh.no_record_found"));
                }
            } else if(crudaction.equals("export")){
                try{
                    convertDate2Timestamp(command);
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

    private Map<String, Object> buildProperties(BaoCaoPhatTrienGoiCommand command){
        Map<String, Object> properties = new HashMap<String, Object>();
        if(command.getPojo().getPromPackageDTO() != null && command.getPojo().getPromPackageDTO().getPackage_Id() != null){
            properties.put("packageId", command.getPojo().getPromPackageDTO().getPackage_Id());
        }
        if(command.getPojo().getFromDate() != null){
            properties.put("fromDate", command.getPojo().getFromDate());
        }
        if(command.getPojo().getToDate() != null){
            properties.put("toDate", command.getPojo().getToDate());
        }
        return properties;
    }

    private void convertDate2Timestamp(BaoCaoPhatTrienGoiCommand command){
        if(command.getFromDate() != null){
            command.getPojo().setFromDate(DateUtil.dateToTimestamp(command.getFromDate(), Constants.VI_DATE_FORMAT));
        }
        if(command.getToDate() != null){
            command.getPojo().setToDate(DateUtil.dateToTimestamp(command.getToDate(), Constants.VI_DATE_FORMAT));
        }
    }

    private void referenceData(BaoCaoPhatTrienGoiCommand command, ModelAndView mav) throws ObjectNotFoundException {
        List<PromPackageDTO> promPackage = promPackageService.findAll();
        mav.addObject("promPackage", promPackage);
    }

    private void export2Excel(BaoCaoPhatTrienGoiCommand command, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try{
            SimpleDateFormat df = new SimpleDateFormat("dd-M-yyyy");
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            String ngayXuatBaoCao = df.format(currentTimestamp);
            Map<String, Object> properties = buildProperties(command);
            Object[] resultObject = this.baoCaoPhatTrienGoiService.baoCaoPhatTrienGoi(properties, command.getSortExpression(), command.getSortDirection(), 0, Integer.MAX_VALUE);
            List<BaoCaoPhatTrienGoiDTO> dtoList = (List<BaoCaoPhatTrienGoiDTO>)resultObject[1];

            String outputFileName = "/files/temp/baocaophattriengoi" + ngayXuatBaoCao + ".xls";
            String reportTemplate = request.getSession().getServletContext().getRealPath("/files/temp/export/thuebaoptm_2015/baocaophattriengoi.xls");
            String export2FileName = request.getSession().getServletContext().getRealPath(outputFileName);
            WorkbookSettings ws = new WorkbookSettings();
            ExcelUtil.setEncoding4Workbook(ws);
            Workbook templateWorkbook = Workbook.getWorkbook(new File(reportTemplate), ws);
            WritableWorkbook workbook = Workbook.createWorkbook(new File(export2FileName), templateWorkbook);
            WritableSheet sheet = workbook.getSheet(0);
            int startRow = 11;

            WritableFont normalFont = new WritableFont(WritableFont.TIMES, 10, WritableFont.NO_BOLD);
            normalFont.setColour(Colour.BLACK);

            WritableCellFormat stringCellFormat = new WritableCellFormat(normalFont);
            stringCellFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);

            WritableCellFormat stringNgayBaoCaoCellFormat = new WritableCellFormat(normalFont);
            stringNgayBaoCaoCellFormat.setBorder(Border.ALL, BorderLineStyle.NONE);
            stringNgayBaoCaoCellFormat.setAlignment(Alignment.CENTRE);

            WritableCellFormat integerCellFormat = new WritableCellFormat(normalFont);
            integerCellFormat.setBorder(jxl.format.Border.ALL, BorderLineStyle.THIN);
            integerCellFormat.setAlignment(Alignment.RIGHT);

            NumberFormat nf = new NumberFormat("#,###");
            WritableCellFormat doubleCellFormat = new WritableCellFormat(nf);
            doubleCellFormat.setFont(normalFont);
            doubleCellFormat.setBorder(jxl.format.Border.ALL, BorderLineStyle.THIN);
            doubleCellFormat.setAlignment(Alignment.RIGHT);

            WritableCellFormat stringFillterCellFormat = new WritableCellFormat(normalFont);
            stringFillterCellFormat.setBorder(Border.ALL, BorderLineStyle.NONE);
            stringFillterCellFormat.setAlignment(Alignment.LEFT);

            if(command.getFromDate() != null || command.getToDate() != null){
                CellValue[] resValueNgayXuatBaoCaoTu = WebCommonUtil.addNgayXuatBaoCao(TOTAL_COLUMN_EXPORT);
                resValueNgayXuatBaoCaoTu[2] = new CellValue(CellDataType.STRING, "Ngày giao dịch: ");
                String tuNgay = "";
                String denNgay = "";
                if (command.getFromDate() != null){
                    tuNgay = "Từ ngày: " + df.format(command.getFromDate());
                }
                if ( command.getToDate() != null){
                    denNgay = " Đến ngày: " + df.format(command.getToDate());
                }
                resValueNgayXuatBaoCaoTu[3] = new CellValue(CellDataType.STRING, tuNgay +" "+ denNgay);
                ExcelUtil.addRow(sheet, 4, resValueNgayXuatBaoCaoTu, stringFillterCellFormat, null, null, null);
            }

            if (command.getPojo().getPromPackageDTO() != null && command.getPojo().getPromPackageDTO().getPackage_Id() != null){
                CellValue[] resValueNgayXuatBaoCaoTu = WebCommonUtil.addNgayXuatBaoCao(TOTAL_COLUMN_EXPORT);
                resValueNgayXuatBaoCaoTu[2] = new CellValue(CellDataType.STRING, " Gói Cước: ");
                resValueNgayXuatBaoCaoTu[3] = new CellValue(CellDataType.STRING, dtoList.get(0).getPromPackageDTO().getPackage_Name());
                ExcelUtil.addRow(sheet, 5, resValueNgayXuatBaoCaoTu, stringFillterCellFormat, null, null, null);
            } else {
                CellValue[] resValueNgayXuatBaoCaoTu = WebCommonUtil.addNgayXuatBaoCao(TOTAL_COLUMN_EXPORT);
                resValueNgayXuatBaoCaoTu[2] = new CellValue(CellDataType.STRING, "Gói Cước: ");
                resValueNgayXuatBaoCaoTu[3] = new CellValue(CellDataType.STRING, this.getMessageSourceAccessor().getMessage("label.all"));
                ExcelUtil.addRow(sheet, 5, resValueNgayXuatBaoCaoTu, stringFillterCellFormat, null, null, null);
            }

            Integer maxRemainingRowsInSheet = Constants.MAX_EXPORT_RECORDS - number_of_templte_export_rows;

            if(dtoList.size() > 0){
                int indexRow = 1;
                for(BaoCaoPhatTrienGoiDTO dto : dtoList){
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
               /* for(BaoCaoPhatTrienGoiDTO dto : dtoList){
                    CellValue[] values = addCellValues(dto, indexRow);
                    ExcelUtil.addRow(sheet, startRow++, values, stringCellFormat, integerCellFormat, doubleCellFormat, null);
                    indexRow++;
                }*/
                workbook.write();
                workbook.close();
                response.sendRedirect(request.getSession().getServletContext().getContextPath() + outputFileName);
            }
        }catch (Exception e){
            throw new Exception("Error when export data to excel.\n + details: " + e.getMessage());
        }
    }

    private CellValue[] addCellValues(BaoCaoPhatTrienGoiDTO dto, int indexRow) {
        CellValue[] resValue = new CellValue[TOTAL_COLUMN_EXPORT];
        SimpleDateFormat df = new SimpleDateFormat("dd/M/yyyy");
        int columnIndex = 0;
        resValue[columnIndex++] = new CellValue(CellDataType.INT, indexRow);
        if(dto.getPromPackageDTO() != null && StringUtils.isNotBlank(dto.getPromPackageDTO().getPackage_Name())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getPromPackageDTO().getPackage_Name());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getPromPackageDTO() != null && dto.getPromPackageDTO().getPrice() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getPromPackageDTO().getPrice());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getAccumulated_quantity() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.INT, dto.getAccumulated_quantity());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getNew_reg_quantity() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.INT, dto.getNew_reg_quantity());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getFinished_quantity() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.INT, dto.getFinished_quantity());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getAccumulated_quantity() != null || dto.getFinished_quantity() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.INT, dto.getAccumulated_quantity() - dto.getFinished_quantity());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getRenewal_quantity() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.INT, dto.getRenewal_quantity());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getAccumulated_quantity() != null || dto.getRenewal_quantity() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.INT, dto.getAccumulated_quantity() + dto.getRenewal_quantity());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getPackage_revenue() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getPackage_revenue());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        return resValue;
    }
}
