package com.benluck.vms.mobifonedataseller.webapp.controller.qStudentController;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.core.business.BaoCaoSoDiemChoThueBaoManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015.BaoCaoSoDiemChoThueBaoDTO;
import com.benluck.vms.mobifonedataseller.util.ExcelUtil;
import com.benluck.vms.mobifonedataseller.util.RequestUtil;
import com.benluck.vms.mobifonedataseller.webapp.command.command2015.BaoCaoSoDiemChoThueBaoCommand;
import com.benluck.vms.mobifonedataseller.webapp.dto.CellDataType;
import com.benluck.vms.mobifonedataseller.webapp.dto.CellValue;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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
 * User: vovanphuc0810
 * Date: 5/4/15
 * Time: 9:28 AM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class BaoCaoSoDiemChoThueBaoController extends ApplicationObjectSupport {
    private static final Integer TOTAL_COLUMN_EXPORT = 3;
    private transient final Log log = LogFactory.getLog(getClass());

    @Autowired
    private BaoCaoSoDiemChoThueBaoManagementLocalBean baoCaoSoDiemChoThueBaoManagementLocalBean;

    @RequestMapping(value = {"/cuahangmobifone/qstudent/baocaosodiemchochuongtrinh.html"
                            ,"/tongdai/qstudent/baocaosodiemchochuongtrinh.html"
                            ,"/admin/qstudent/baocaosodiemchochuongtrinh.html"
                            ,"/chinhanh/qstudent/baocaosodiemchochuongtrinh.html"
                            ,"/baocao/qstudent/baocaosodiemchochuongtrinh.html"})
    public ModelAndView reportByKHCN(@ModelAttribute(value = Constants.FORM_MODEL_KEY)BaoCaoSoDiemChoThueBaoCommand command,
                                     BindingResult bindingResult,
                                     HttpServletRequest request,
                                     HttpServletResponse response){
        ModelAndView mav = new ModelAndView("/2015/q_student_and_q_teen/baocaosodiemchothuebao");
        String crudaction = command.getCrudaction();
        if(StringUtils.isNotBlank(crudaction)){
            if(crudaction.equals("search")){
                RequestUtil.initSearchBean4report(request, command);
                Map<String, Object> properties = buildProperties(command);
                Object[] resultObject = this.baoCaoSoDiemChoThueBaoManagementLocalBean.search(properties, command.getFirstItem(), command.getReportMaxPageItems(), command.getSortExpression(), command.getSortDirection());
                command.setTotalItems(Integer.valueOf(resultObject[0].toString()));
                command.setListResult((List<BaoCaoSoDiemChoThueBaoDTO>)resultObject[1]);
                mav.addObject("page", command.getPage() - 1);
                mav.addObject(Constants.LIST_MODEL_KEY, command);
                if(command.getTotalItems() == 0){
                    mav.addObject(Constants.ALERT_TYPE, "info");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("chinhanh.doi_tien_dbh.no_record_found"));
                }
            }else if(crudaction.equals("export")){
                try{
                    export2Excel(command, request, response);
                }catch (Exception e){
                    mav.addObject(Constants.ALERT_TYPE, "danger");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("label.export_failed"));
                }

            }
        }
        return mav;
    }

    /**
     * Build properties for querying.
     * @param command
     * @return
     */
    private Map<String, Object> buildProperties(BaoCaoSoDiemChoThueBaoCommand command){
        Map<String, Object> properties = new HashMap<String, Object>();
        if(StringUtils.isNotBlank(command.getPojo().getSoEz())){
            properties.put("soEz", command.getPojo().getSoEz());
        }
        if(command.getSoDiemTo() != null){
            properties.put("soDiemTo", command.getSoDiemTo());
        }
        if(command.getSoDiemFrom() != null){
            properties.put("soDiemFrom", command.getSoDiemFrom());
        }
        return properties;
    }

    /**
     * Export the report data to Excel.
     * @param command
     * @param request
     * @param response
     * @throws Exception
     */
    private void export2Excel(BaoCaoSoDiemChoThueBaoCommand command, HttpServletRequest request, HttpServletResponse response) throws Exception{
        try{
            SimpleDateFormat df = new SimpleDateFormat("dd-M-yyyy");
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            String ngayXuatBaoCao = df.format(currentTimestamp);

            Map<String, Object> properties = buildProperties(command);
            Object[] resultObject = this.baoCaoSoDiemChoThueBaoManagementLocalBean.search(properties, 0, Integer.MAX_VALUE, command.getSortExpression(), command.getSortDirection());
            List<BaoCaoSoDiemChoThueBaoDTO> dtoList = (List<BaoCaoSoDiemChoThueBaoDTO>)resultObject[1];
            String outputFileName = "/files/temp/baocaosodiemchochuongtrinh" + ngayXuatBaoCao + ".xls";
            String reportTemplate = request.getSession().getServletContext().getRealPath("/files/temp/export/thuebaoptm_2015/baocaosodiemchochuongtrinh.xls");
            String export2FileName = request.getSession().getServletContext().getRealPath(outputFileName);
            WorkbookSettings ws = new WorkbookSettings();
            ExcelUtil.setEncoding4Workbook(ws);
            Workbook templateWorkbook = Workbook.getWorkbook(new File(reportTemplate), ws);
            WritableWorkbook workbook = Workbook.createWorkbook(new File(export2FileName), templateWorkbook);
            WritableSheet sheet = workbook.getSheet(0);
            int startRow = 5;

            WritableFont normalFont = new WritableFont(WritableFont.TIMES, 10, WritableFont.NO_BOLD);
            normalFont.setColour(Colour.BLACK);

            WritableCellFormat normalStringCellFormat = new WritableCellFormat(normalFont);
            normalStringCellFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.MEDIUM);
            normalStringCellFormat.setAlignment(Alignment.CENTRE);

            WritableCellFormat integerCellFormat = new WritableCellFormat(normalFont);
            integerCellFormat.setBorder(jxl.format.Border.ALL, BorderLineStyle.MEDIUM);
            integerCellFormat.setAlignment(Alignment.CENTRE);

            NumberFormat nf = new NumberFormat("#,###");
            WritableCellFormat doubleCellFormat = new WritableCellFormat(nf);
            doubleCellFormat.setFont(normalFont);
            doubleCellFormat.setBorder(jxl.format.Border.ALL, BorderLineStyle.MEDIUM);
            doubleCellFormat.setAlignment(Alignment.RIGHT);

            int indexRow = 1;
            if(dtoList.size() > 0){
                for(BaoCaoSoDiemChoThueBaoDTO dto : dtoList){
                    CellValue[] chiNhanhResValue = addCellValues(dto, indexRow);
                    ExcelUtil.addRow(sheet, startRow++, chiNhanhResValue, normalStringCellFormat, integerCellFormat, doubleCellFormat, null);
                    indexRow++;
                }
                workbook.write();
                workbook.close();
                response.sendRedirect(request.getSession().getServletContext().getContextPath() + outputFileName);
            }
        }catch (Exception e){
            throw new Exception("Export failed for ReportForKHCN." + e.getMessage());
        }
    }

    /**
     * Write DTO's properties to Excel cells for exporting.
     * @param dto DTO comprise report row data.
     * @param indexRow row index in Sheet of Excel to write data.
     * @return
     */
    private CellValue[] addCellValues(BaoCaoSoDiemChoThueBaoDTO dto, int indexRow) {
        CellValue[] resValue = new CellValue[TOTAL_COLUMN_EXPORT];
        int columnIndex = 0;
        resValue[columnIndex++] = new CellValue(CellDataType.INT, indexRow);
        if(StringUtils.isNotBlank(dto.getSoEz())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getSoEz());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getSoDiem() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.INT, dto.getSoDiem());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        return resValue;
    }
}
