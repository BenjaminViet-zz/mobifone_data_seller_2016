package com.benluck.vms.mobifonedataseller.webapp.controller.report;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.core.business.CodeHistoryManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.KHDNManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.MBDCodeHistoryDTO;
import com.benluck.vms.mobifonedataseller.util.ExcelUtil;
import com.benluck.vms.mobifonedataseller.util.RequestUtil;
import com.benluck.vms.mobifonedataseller.webapp.command.MBDCodeHistoryCommand;
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
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/11/16
 * Time: 19:41
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class MBDCodeHistoryController extends ApplicationObjectSupport{
    private Logger logger = Logger.getLogger(MBDCodeHistoryController.class);
    private final Integer TOTAL_COLUMN_EXPORT = 6;

    @Autowired
    private CodeHistoryManagementLocalBean codeHistoryService;
    @Autowired
    private KHDNManagementLocalBean khdnService;

    @RequestMapping(value = {"/admin/codeHistory/list.html", "/vms/codeHistory/list.html"})
    public ModelAndView list(@ModelAttribute(Constants.FORM_MODEL_KEY)MBDCodeHistoryCommand command,
                             HttpServletRequest request,
                             HttpServletResponse response){
        ModelAndView mav = new ModelAndView("/admin/codeHistory/list");
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
        return mav;
    }

    private void executeSearch(MBDCodeHistoryCommand command, HttpServletRequest request){
        RequestUtil.initSearchBean(request, command);

        Map<String, Object> properties = buildProperties(command);

        Object[] resultObject = this.codeHistoryService.searchByProperties(properties, command.getSortExpression(), command.getSortDirection(), command.getFirstItem(), command.getReportMaxPageItems());
        command.setTotalItems(Integer.valueOf(resultObject[0].toString()));
        command.setListResult((List<MBDCodeHistoryDTO>)resultObject[1]);
        command.setMaxPageItems(command.getReportMaxPageItems());
    }

    private Map<String, Object> buildProperties(MBDCodeHistoryCommand command){
        MBDCodeHistoryDTO pojo = command.getPojo();
        Map<String, Object> properties = new HashMap<String, Object>();

        if(StringUtils.isNotBlank(pojo.getName())){
            properties.put("", pojo.getName());
        }
        if(StringUtils.isNotBlank(pojo.getIsdn())){
            properties.put("isdn", pojo.getIsdn());
        }
        if(StringUtils.isNotBlank(pojo.getTin())){
            properties.put("tin", pojo.getTin());
        }
        // ngay dang ky
        return properties;
    }

    private void export2Excel(MBDCodeHistoryCommand command, HttpServletRequest request, HttpServletResponse response) throws Exception{
        SimpleDateFormat df = new SimpleDateFormat("dd-M-yyyy");
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        String exportDate = df.format(currentTimestamp);

        Map<String, Object> properties = buildProperties(command);

        Object[] resultObject = this.codeHistoryService.searchByProperties(properties, command.getSortExpression(), command.getSortDirection(), command.getFirstItem(), command.getReportMaxPageItems());
        List<MBDCodeHistoryDTO> dtoList = (List<MBDCodeHistoryDTO>)resultObject[1];

        if(dtoList.size() == 0){
            logger.error("Error happened when fetching report general expense for CustID: " + command.getPojo().getCustId());
            throw new Exception("Error happened when fetching report general expense for CustID: " + command.getPojo().getCustId());
        }

        String reportTemplate = request.getSession().getServletContext().getRealPath("/files/temp/export/lich_su_chi_tra.xls");
        String outputFileName = "/files/temp/export/lich_su_chi_tra_" + exportDate + ".xls";
        String export2FileName = request.getSession().getServletContext().getRealPath(outputFileName);
        WorkbookSettings ws = new WorkbookSettings();
        ExcelUtil.setEncoding4Workbook(ws);
        Workbook templateWorkbook = Workbook.getWorkbook(new File(reportTemplate), ws);
        WritableWorkbook workbook = Workbook.createWorkbook(new File(export2FileName), templateWorkbook);
        WritableSheet sheet = workbook.getSheet(0);
        int startRow = 5;

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
            for(MBDCodeHistoryDTO dto : dtoList){
                CellValue[] resValue = addCellValues(dto, indexRow);
                ExcelUtil.addRow(sheet, startRow++, resValue, stringCellFormat, integerCellFormat, doubleCellFormat, null);
                indexRow++;
            }
            workbook.write();
            workbook.close();
            response.sendRedirect(request.getSession().getServletContext().getContextPath() + outputFileName);
        }
    }
    private CellValue[] addCellValues(MBDCodeHistoryDTO dto, int indexRow){
        SimpleDateFormat df = new SimpleDateFormat("dd-M-yyyy");
        CellValue[] resValue = new CellValue[TOTAL_COLUMN_EXPORT];
        int columnIndex = 0;
        resValue[columnIndex++] = new CellValue(CellDataType.INT, indexRow);
        resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getName().toString());
        resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getTin().toString());
        resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getIsdn().toString());
        resValue[columnIndex++] = new CellValue(CellDataType.STRING, df.format(dto.getRegDateTime()));
        resValue[columnIndex++] = new CellValue(CellDataType.STRING, df.format(dto.getStaDateTime()));
        return resValue;
    }
}
