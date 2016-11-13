package com.benluck.vms.mobifonedataseller.webapp.controller.report;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.utils.DateUtil;
import com.benluck.vms.mobifonedataseller.core.business.MBDCostManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.MBDReportDetailExpenseDTO;
import com.benluck.vms.mobifonedataseller.editor.CustomDateEditor;
import com.benluck.vms.mobifonedataseller.security.util.SecurityUtils;
import com.benluck.vms.mobifonedataseller.util.ExcelUtil;
import com.benluck.vms.mobifonedataseller.util.RequestUtil;
import com.benluck.vms.mobifonedataseller.webapp.command.ReportDetailExpenseCommand;
import com.benluck.vms.mobifonedataseller.webapp.dto.CellDataType;
import com.benluck.vms.mobifonedataseller.webapp.dto.CellValue;
import com.benluck.vms.mobifonedataseller.webapp.exception.ForBiddenException;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by thaihoang on 11/1/2016.
 */
@Controller
public class ReportDetailExpenseController extends ApplicationObjectSupport{
    private Logger logger = Logger.getLogger(ReportDetailExpenseController.class);
    private final Integer TOTAL_COLUMN_EXPORT = 18;

    @Autowired
    private MBDCostManagementLocalBean costService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor("dd/MM/yyyy"));
    }

    @RequestMapping( value = {"/admin/reportDetailExpense/list.html", "/user/reportDetailExpense/list.html"})
    public ModelAndView list(@ModelAttribute(Constants.FORM_MODEL_KEY)ReportDetailExpenseCommand command,
                             HttpServletRequest request,
                             HttpServletResponse response){

        if(!SecurityUtils.userHasAuthority(Constants.USERGROUP_ADMIN) && !SecurityUtils.userHasAuthority(Constants.USERGROUP_VMS_USER) && !SecurityUtils.userHasAuthority(Constants.REPORT_MANAGER)){
            logger.warn("User: " + SecurityUtils.getPrincipal().getDisplayName() + ", userId: " + SecurityUtils.getLoginUserId() + " is trying to access non-authorized page: " + " report page. ACCESS DENIED FOR BIDDEN!");
            throw new ForBiddenException();
        }

        ModelAndView mav = new ModelAndView("/admin/report/expense/detailList");
        String action = command.getCrudaction();

        if(StringUtils.isNotBlank(action)){
            if(action.equals(Constants.ACTION_EXPORT)){
                try{
                    export2Excel(command, request, response);
                }catch (Exception e){
                    e.printStackTrace();
                    mav.addObject(Constants.ALERT_TYPE, "danger");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("order.export_failed"));
                    logger.error(e.getMessage());
                }
            }else if(action.equals(Constants.ACTION_SEARCH)){
                executeSearch(command, request);
                mav.addObject(Constants.LIST_MODEL_KEY, command);
            }
        }
        mav.addObject(Constants.LIST_MODEL_KEY, command);
        return mav;
    }

    private void executeSearch(ReportDetailExpenseCommand command, HttpServletRequest request){
        RequestUtil.initSearchBean(request, command);

        convertDate2Timestamp(command);
        Map<String, Object> properties = buildProperties(command);

        Object[] resultObject = this.costService.searchDetailReportDataByProperties(properties, command.getSortExpression(), command.getSortDirection(), command.getFirstItem(), command.getReportMaxPageItems());
        command.setTotalItems(Integer.valueOf(resultObject[0].toString()));
        command.setListResult((List<MBDReportDetailExpenseDTO>)resultObject[1]);
        command.setMaxPageItems(command.getReportMaxPageItems());
    }

    private void convertDate2Timestamp(ReportDetailExpenseCommand command){
        if(command.getIssuedDateFrom() != null){
            command.getPojo().setIssuedDateTimeFrom(DateUtil.dateToTimestamp(command.getIssuedDateFrom(), Constants.VI_DATE_FORMAT));
        }
        if(command.getIssuedDateTo() != null){
            command.getPojo().setIssuedDateTimeTo(DateUtil.dateToTimestamp(command.getIssuedDateTo(), Constants.VI_DATE_FORMAT));
        }
    }

    private Map<String, Object> buildProperties(ReportDetailExpenseCommand command){
        MBDReportDetailExpenseDTO pojo = command.getPojo();
        Map<String, Object> properties = new HashMap<String, Object>();

        if(StringUtils.isNotBlank(pojo.getShopCode())){
            properties.put("shopCode", pojo.getShopCode());
        }
        if(StringUtils.isNotBlank(pojo.getEmpCode())){
            properties.put("empCode", pojo.getEmpCode());
        }
        if(StringUtils.isNotBlank(pojo.getIsdn())){
            properties.put("isdn", pojo.getIsdn());
        }
        if(pojo.getIssuedDateTimeFrom() != null){
            properties.put("issuedDateTimeFrom", pojo.getIssuedDateTimeFrom());
        }
        if(pojo.getIssuedDateTimeTo() != null){
            properties.put("issuedDateTimeTo", pojo.getIssuedDateTimeTo());
        }
        return properties;

    }

    private void export2Excel(ReportDetailExpenseCommand command, HttpServletRequest request, HttpServletResponse response) throws Exception{
        SimpleDateFormat df = new SimpleDateFormat("dd-M-yyyy");
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        String exportDate = df.format(currentTimestamp);

        convertDate2Timestamp(command);
        Map<String, Object> properties = buildProperties(command);

        Object[] resultObject = this.costService.searchDetailReportDataByProperties(properties, command.getSortExpression(), command.getSortDirection(), command.getFirstItem(), command.getReportMaxPageItems());
        List<MBDReportDetailExpenseDTO> dtoList = (List<MBDReportDetailExpenseDTO>)resultObject[1];

        if(dtoList.size() == 0){
            logger.error("Error happened when fetching report detail expense");
            throw new Exception("Error happened when fetching report detail expense");
        }

        String reportTemplate = request.getSession().getServletContext().getRealPath("/files/temp/export/bao_cao_chi_tiet_chi_phi.xls");
        String outputFileName = "/files/temp/export/bao_cao_chi_tiet_chi_phi_" + exportDate + ".xls";
        String export2FileName = request.getSession().getServletContext().getRealPath(outputFileName);
        WorkbookSettings ws = new WorkbookSettings();
        ExcelUtil.setEncoding4Workbook(ws);
        Workbook templateWorkbook = Workbook.getWorkbook(new File(reportTemplate), ws);
        WritableWorkbook workbook = Workbook.createWorkbook(new File(export2FileName), templateWorkbook);
        WritableSheet sheet = workbook.getSheet(0);
        int startRow = 6;

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

        if(dtoList.size() > 0){
            int indexRow = 1;
            for(MBDReportDetailExpenseDTO dto : dtoList){
                CellValue[] resValue = addCellValues(dto, indexRow);
                ExcelUtil.addRow(sheet, startRow++, resValue, stringCellFormat, integerCellFormat, doubleCellFormat, null);
                indexRow++;
            }
            workbook.write();
            workbook.close();
            response.sendRedirect(request.getSession().getServletContext().getContextPath() + outputFileName);
        }
    }
    private CellValue[] addCellValues(MBDReportDetailExpenseDTO dto, int indexRow){
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        CellValue[] resValue = new CellValue[TOTAL_COLUMN_EXPORT];
        int columnIndex = 0;
        resValue[columnIndex++] = new CellValue(CellDataType.INT, indexRow);
        resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getEmpCode().toString());
        resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getIsdn().toString());
        resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getName().toString());
        resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getEmpCode().toString());
        resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getBusType().toString());
        resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getLoaiTB().toString());
        resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getCustType().toString());
        resValue[columnIndex++] = new CellValue(CellDataType.STRING, df.format(dto.getStaDateTime()));
        resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getActStatus().toString());
        resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getStatus().toString());
        resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getCuocThucThu());
        resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, Double.valueOf(dto.getDevelopmentAmount1()));
        resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, Double.valueOf(dto.getDevelopmentAmount2()));
        resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, Double.valueOf(dto.getDevelopmentAmount3()));
        resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, Double.valueOf(dto.getMaintainAmount1()));
        resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, Double.valueOf(dto.getMaintainAmount2()));
        resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, Double.valueOf(dto.getMaintainAmount3()));
        return resValue;
    }
}
