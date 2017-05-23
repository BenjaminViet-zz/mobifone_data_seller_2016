package com.benluck.vms.mobifonedataseller.webapp.controller.report;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.utils.DateUtil;
import com.benluck.vms.mobifonedataseller.core.business.KHDNManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.MBDCostManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.MBDReportGeneralExpenseDTO;
import com.benluck.vms.mobifonedataseller.editor.CustomDateEditor;
import com.benluck.vms.mobifonedataseller.security.util.SecurityUtils;
import com.benluck.vms.mobifonedataseller.util.ExcelExtensionUtil;
import com.benluck.vms.mobifonedataseller.util.ExcelUtil;
import com.benluck.vms.mobifonedataseller.util.RequestUtil;
import com.benluck.vms.mobifonedataseller.webapp.command.ReportGeneralExpenseCommand;
import com.benluck.vms.mobifonedataseller.webapp.dto.CellDataType;
import com.benluck.vms.mobifonedataseller.webapp.dto.CellValue;
import com.benluck.vms.mobifonedataseller.webapp.exception.ForBiddenException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.*;
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
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/9/16
 * Time: 22:18
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class ReportGeneralExpenseController extends ApplicationObjectSupport{
    private Logger logger = Logger.getLogger(ReportGeneralExpenseController.class);
    private final Integer TOTAL_COLUMN_EXPORT = 10;

    @Autowired
    private MBDCostManagementLocalBean costService;
    @Autowired
    private KHDNManagementLocalBean khdnService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor("dd/MM/yyyy"));
    }

    @RequestMapping(value = {"/admin/reportGeneralExpense/list.html", "/user/reportGeneralExpense/list.html", "/khdn/reportGeneralExpense/list.html"})
    public ModelAndView list(@ModelAttribute(Constants.FORM_MODEL_KEY)ReportGeneralExpenseCommand command,
                             HttpServletRequest request,
                             HttpServletResponse response){

        if(!SecurityUtils.userHasAuthority(Constants.GENERAL_EXPENSE_REPORT_MANAGER)){
            logger.warn("User: " + SecurityUtils.getPrincipal().getDisplayName() + ", userId: " + SecurityUtils.getLoginUserId() + " is trying to access non-authorized page: reportGeneralExpense/list.html report page. ACCESS DENIED FOR BIDDEN!");
            throw new ForBiddenException();
        }

        ModelAndView mav = new ModelAndView("/admin/report/expense/generalList");
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

        mav.addObject("KHDNList", this.khdnService.findAll());
        mav.addObject(Constants.LIST_MODEL_KEY, command);
        return mav;
    }

    private void convertDate2Timestamp(ReportGeneralExpenseCommand command){
        if(command.getIssuedDateFrom() != null){
            command.getPojo().setIssuedDateTimeFrom(DateUtil.dateToTimestamp(command.getIssuedDateFrom(), Constants.VI_DATE_FORMAT));
        }
        if(command.getIssuedDateTo() != null){
            command.getPojo().setIssuedDateTimeTo(DateUtil.dateToTimestamp(command.getIssuedDateTo(), Constants.VI_DATE_FORMAT));
        }
    }

    private void executeSearch(ReportGeneralExpenseCommand command, HttpServletRequest request){
        RequestUtil.initSearchBean(request, command);
        convertDate2Timestamp(command);
        Map<String, Object> properties = buildProperties(command);

        Object[] resultObject = this.costService.searchGeneralReportDataByProperties(properties, command.getSortExpression(), command.getSortDirection(), command.getFirstItem(), command.getReportMaxPageItems());
        command.setTotalItems(Integer.valueOf(resultObject[0].toString()));
        command.setListResult((List<MBDReportGeneralExpenseDTO>)resultObject[1]);
        command.setMaxPageItems(command.getReportMaxPageItems());
    }

    private Map<String, Object> buildProperties(ReportGeneralExpenseCommand command){
        MBDReportGeneralExpenseDTO pojo = command.getPojo();
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

    private void export2Excel(ReportGeneralExpenseCommand command, HttpServletRequest request, HttpServletResponse response) throws Exception{
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        String exportDate = df.format(currentTimestamp);

        convertDate2Timestamp(command);
        Map<String, Object> properties = buildProperties(command);

        Object[] resultObject = this.costService.searchGeneralReportDataByProperties(properties, command.getSortExpression(), command.getSortDirection(), command.getFirstItem(), command.getReportMaxPageItems());
        List<MBDReportGeneralExpenseDTO> dtoList = (List<MBDReportGeneralExpenseDTO>)resultObject[1];

        if(dtoList.size() == 0){
            logger.error("Error happened when fetching report general expense");
            throw new Exception("Error happened when fetching report general expense");
        }

        String outputFileName = "/files/temp/export/bao_cao_tong_hop_chi_phi_" + exportDate + ".xlsx";
        FileOutputStream fileOut = new FileOutputStream(request.getSession().getServletContext().getRealPath(outputFileName));
        XSSFWorkbook workbook = new XSSFWorkbook(OPCPackage.open(request.getSession().getServletContext().getRealPath("/files/temp/export/bao_cao_tong_hop_chi_phi.xlsx")));
        XSSFCreationHelper createHelper = workbook.getCreationHelper();
        XSSFSheet sheet = workbook.getSheetAt(0);
        int startRow = 6;

        XSSFFont normalFont = workbook.createFont();

        XSSFCellStyle stringCellFormat = workbook.createCellStyle();
        stringCellFormat.setFont(normalFont);

        XSSFCellStyle integerCellFormat = workbook.createCellStyle();
        integerCellFormat.setFont(normalFont);
        integerCellFormat.setAlignment(HorizontalAlignment.CENTER);

        XSSFCellStyle doubleCellFormat = workbook.createCellStyle();
        doubleCellFormat.setFont(normalFont);
        doubleCellFormat.setDataFormat(createHelper.createDataFormat().getFormat("#,###"));
        doubleCellFormat.setAlignment(HorizontalAlignment.CENTER);

        if(dtoList.size() > 0){
            int indexRow = 0;
            for(MBDReportGeneralExpenseDTO dto : dtoList){
                XSSFRow row = sheet.createRow(startRow + indexRow);
                CellValue[] resValue = addCellValues(dto, indexRow);
                ExcelExtensionUtil.addRow(row, resValue, stringCellFormat, integerCellFormat, doubleCellFormat);
                indexRow++;
            }
            workbook.write(fileOut);
            fileOut.close();
            response.sendRedirect(request.getSession().getServletContext().getContextPath() + outputFileName);
        }
    }
    private CellValue[] addCellValues(MBDReportGeneralExpenseDTO dto, int indexRow){
        CellValue[] resValue = new CellValue[TOTAL_COLUMN_EXPORT];
        int columnIndex = 0;
        resValue[columnIndex++] = new CellValue(CellDataType.INT, indexRow + 1);
        resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getShopCode().toString());
        resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getShopName().toString());
        resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getDevelopmentAmount1());
        resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getDevelopmentAmount2());
        resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getDevelopmentAmount3());
        resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getMaintainAmount1());
        resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getMaintainAmount2());
        resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getMaintainAmount3());
        resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, (dto.getDevelopmentAmount1() + dto.getDevelopmentAmount2() + dto.getDevelopmentAmount3() + dto.getMaintainAmount1() + dto.getMaintainAmount2() + dto.getMaintainAmount3()));
        return resValue;
    }
}
