package com.benluck.vms.mobifonedataseller.webapp.controller.qStudentController;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.utils.DateUtil;
import com.benluck.vms.mobifonedataseller.core.business.ReportCtTichDiemManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BaoCaoQuanLyTinNhanDTO;
import com.benluck.vms.mobifonedataseller.editor.CustomDateEditor;
import com.benluck.vms.mobifonedataseller.util.ExcelUtil;
import com.benluck.vms.mobifonedataseller.util.WebCommonUtil;
import com.benluck.vms.mobifonedataseller.webapp.command.command2014.BaoCaoQuanLyTinNhanCommand;
import com.benluck.vms.mobifonedataseller.webapp.dto.CellDataType;
import com.benluck.vms.mobifonedataseller.webapp.dto.CellValue;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/22/14
 * Time: 3:27 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class BaoCaoQuanLyTinNhanQStudentController extends ApplicationObjectSupport{
    private static final Integer TOTAL_COLUMN_EXPORT = 7;
    private transient final Log log = LogFactory.getLog(getClass());

    @Autowired
    private ReportCtTichDiemManagementLocalBean reportCtTichDiemManagementLocalBean;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor("dd/MM/yyyy"));
    }

    @RequestMapping(value = {"/admin/qstudent/bcquanlytinnhan.html"
                            ,"/chinhanh/qstudent/bcquanlytinnhan.html"})
    public ModelAndView bcQuanLyTinNhan(@ModelAttribute(value = Constants.FORM_MODEL_KEY)BaoCaoQuanLyTinNhanCommand command,
                                        HttpServletRequest request,
                                        HttpServletResponse response){
        ModelAndView mav = new ModelAndView("/2015/q_student_and_q_teen/baocaoquanlytinnhan");
        String crudaction = command.getCrudaction();
        if(command.getFromDate() != null){
            command.setFromDateTime(DateUtil.dateToTimestamp(command.getFromDate(), Constants.VI_DATE_FORMAT));
        }
        if(command.getToDate() != null){
            command.setToDateTime(DateUtil.dateToTimestamp(command.getToDate(), Constants.VI_DATE_FORMAT));
        }
        List<BaoCaoQuanLyTinNhanDTO> dtoList = this.reportCtTichDiemManagementLocalBean.baoCaoQuanLyTinNhan_qStudent(command.getLoaiTinNhanTuKHCN(), command.getLoaiTinNhanTuVMS(), command.getFromDateTime(), command.getToDateTime());
        if(StringUtils.isNotBlank(crudaction) && crudaction.equals("export")){
            try{
                export2Excel(dtoList, command.getFromDateTime(), command.getToDateTime(), request, response);
            }catch (Exception e){
                log.error(e.getMessage());
                mav.addObject(Constants.ALERT_TYPE, "danger");
                mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("label.export_failed"));
            }
        }
        mav.addObject("reportData", dtoList);
        if(dtoList.size() == 0){
            mav.addObject(Constants.ALERT_TYPE, "danger");
            mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("admin.baocaoquanlytinnhan.msg.no_record_found"));
        }
        return mav;
    }

    /**
     * Export the report data to Excel.
     * @param dtoList
     * @param fromDate
     * @param toDate
     * @param request
     * @param response
     * @throws Exception
     */
    private void export2Excel(List<BaoCaoQuanLyTinNhanDTO> dtoList, Timestamp fromDate, Timestamp toDate, HttpServletRequest request, HttpServletResponse response)throws Exception{
        try{
            SimpleDateFormat df = new SimpleDateFormat("dd-M-yyyy");
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            String ngayXuatBaoCao = df.format(currentTimestamp);
            String outputFileName = "/files/temp/baocaoquanlytinnhan_" + ngayXuatBaoCao + ".xls";
            String reportTemplate = request.getSession().getServletContext().getRealPath("/files/temp/export/thuebaoptm_2015/baocaoquanlytinnhan.xls");
            String export2FileName = request.getSession().getServletContext().getRealPath(outputFileName);
            WorkbookSettings ws = new WorkbookSettings();
            ExcelUtil.setEncoding4Workbook(ws);
            Workbook templateWorkbook = Workbook.getWorkbook(new File(reportTemplate), ws);
            WritableWorkbook workbook = Workbook.createWorkbook(new File(export2FileName), templateWorkbook);
            WritableSheet sheet = workbook.getSheet(0);
            int startRow = 21;

            WritableFont normalFont = new WritableFont(WritableFont.TIMES, 10, WritableFont.NO_BOLD);
            normalFont.setColour(Colour.BLACK);

            WritableCellFormat stringCellFormat = new WritableCellFormat(normalFont);
            stringCellFormat.setBorder(Border.ALL, BorderLineStyle.MEDIUM);
            stringCellFormat.setAlignment(Alignment.CENTRE);

            WritableCellFormat stringNgayBaoCaoCellFormat = new WritableCellFormat(normalFont);
            stringNgayBaoCaoCellFormat.setBorder(Border.ALL, BorderLineStyle.NONE);
            stringNgayBaoCaoCellFormat.setAlignment(Alignment.CENTRE);

            WritableCellFormat integerCellFormat = new WritableCellFormat(normalFont);
            integerCellFormat.setBorder(Border.ALL, BorderLineStyle.MEDIUM);
            integerCellFormat.setAlignment(Alignment.CENTRE);

            WritableCellFormat titleCellFormat = new WritableCellFormat(normalFont);
            titleCellFormat.setAlignment(Alignment.LEFT);

            if(fromDate != null){
                CellValue[] resValueNgayXuatBaoCaoTu = WebCommonUtil.addNgayXuatBaoCao(TOTAL_COLUMN_EXPORT);
                resValueNgayXuatBaoCaoTu[0] = new CellValue(CellDataType.STRING, "Ngày xuất báo cáo từ: " + df.format(fromDate));
                ExcelUtil.addRow(sheet, 3, resValueNgayXuatBaoCaoTu, stringNgayBaoCaoCellFormat, null, null, null);
            }
            if(toDate != null){
                CellValue[] resValueNgayXuatBaoCaoTu = WebCommonUtil.addNgayXuatBaoCao(TOTAL_COLUMN_EXPORT);
                resValueNgayXuatBaoCaoTu[0] = new CellValue(CellDataType.STRING, "Ngày xuất báo cáo đến: " + df.format(toDate));
                ExcelUtil.addRow(sheet, 4, resValueNgayXuatBaoCaoTu, stringNgayBaoCaoCellFormat, null, null, null);
            }

            if(dtoList.size() > 0){
                for(BaoCaoQuanLyTinNhanDTO dto : dtoList){
                    CellValue[] chiNhanhResValue = addCellValues( dto);
                    ExcelUtil.addRow(sheet, startRow++, chiNhanhResValue, stringCellFormat, integerCellFormat, null, null);
                }

                workbook.write();
                workbook.close();
                response.sendRedirect(request.getSession().getServletContext().getContextPath() + outputFileName);
            }
        }catch (Exception e){
            throw new Exception("Error when export data to excel.\n + details: " + e.getMessage());
        }
    }

    /**
     * Write DTO's properties to report cells.
     * @param dto
     * @return
     */
    private CellValue[] addCellValues(BaoCaoQuanLyTinNhanDTO dto){
        CellValue[] resValue = new CellValue[TOTAL_COLUMN_EXPORT];
        int columnIndex = 0;
        resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getDoiTuong());
        SimpleDateFormat df = new SimpleDateFormat("dd/M/yyyy");
        resValue[columnIndex++] = new CellValue(CellDataType.STRING, df.format(dto.getThoiGianNhanTin()));
        resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getNoiDungTinNhan());
        resValue[columnIndex++] = new CellValue(CellDataType.INT, dto.getSoLuongTB());
        resValue[columnIndex++] = new CellValue(CellDataType.INT, dto.getSoLuongTinNhan());
        resValue[columnIndex++] = new CellValue(CellDataType.INT, dto.getSoLuongTinNhanThanhCong());
        resValue[columnIndex++] = new CellValue(CellDataType.INT, dto.getSoLuongTinNhanKhongThanhCong());
        return resValue;
    }
}
