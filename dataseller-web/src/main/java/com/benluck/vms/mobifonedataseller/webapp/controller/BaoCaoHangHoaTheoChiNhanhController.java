package com.benluck.vms.mobifonedataseller.webapp.controller;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.utils.DateUtil;
import com.benluck.vms.mobifonedataseller.core.business.BaoCaoHangHoaManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BaoCaoHangHoaDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BranchDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.PromItemsDTO;
import com.benluck.vms.mobifonedataseller.editor.CustomDateEditor;
import com.benluck.vms.mobifonedataseller.security.util.SecurityUtils;
import com.benluck.vms.mobifonedataseller.util.ExcelUtil;
import com.benluck.vms.mobifonedataseller.util.RequestUtil;
import com.benluck.vms.mobifonedataseller.util.WebCommonUtil;
import com.benluck.vms.mobifonedataseller.webapp.command.command2014.BaoCaoHangHoaCommand;
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
 * Date: 10/29/14
 * Time: 11:18 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class BaoCaoHangHoaTheoChiNhanhController extends ApplicationObjectSupport{
    private Log log = LogFactory.getLog(BaoCaoHangHoaTheoChiNhanhController.class);
    private static final Integer TOTAL_COLUMN_EXPORT = 10;

    @Autowired
    private BaoCaoHangHoaManagementLocalBean baoCaoHangHoaManagementLocalBean;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor("dd/MM/yyyy"));
    }

    @RequestMapping(value = {"/admin/baocaohanghoatheochinhanh.html","/chinhanh/baocaohanghoatheochinhanh.html"})
    public ModelAndView baocaohanghoatheodaily(@ModelAttribute(value = Constants.FORM_MODEL_KEY)BaoCaoHangHoaCommand command,
                                      HttpServletRequest request,
                                      HttpServletResponse response){
        ModelAndView mav = new ModelAndView("/admin/baocaohanghoatheochinhanh");
        String crudaction = command.getCrudaction();
        if(StringUtils.isNotBlank(crudaction)){
            if(crudaction.equals("search")){
                RequestUtil.initSearchBean4report(request, command);
                convertDate2Timestamp(command);
                Map<String, Object> properties = buildProperties(command);
                Object[] resultSet = this.baoCaoHangHoaManagementLocalBean.baoCaoHangHoaTheoChiNhanh(properties, 0, Integer.MAX_VALUE, command.getSortExpression(), command.getSortDirection());
                command.setTotalItems(Integer.valueOf(resultSet[0].toString()));
                command.setListResult((List<BaoCaoHangHoaDTO>) resultSet[1]);
                mav.addObject(Constants.LIST_MODEL_KEY, command);
                mav.addObject("page", command.getPage() - 1);

                if(command.getTotalItems() == 0){
                    mav.addObject(Constants.ALERT_TYPE, "info");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("admin.baocaohanghoa.msg.no_record_found"));
                }
            }else if(crudaction.equals("export")){
                try{
                    export2Excel(command, request, response);
                }catch (Exception e){
                    mav.addObject(Constants.ALERT_TYPE, "danger");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("export_data_exception"));
                }
            }
        }
        if(SecurityUtils.userHasAuthority(Constants.ADMIN_ROLE) || SecurityUtils.userHasAuthority(Constants.BAOCAO_ROLE)){
            List<BranchDTO> branchList = this.baoCaoHangHoaManagementLocalBean.findAllBranches();
            mav.addObject("branchList", branchList);
        }else if(SecurityUtils.userHasAuthority(Constants.CHINHANH_ROLE)){
            try{
                BranchDTO branchDTO = this.baoCaoHangHoaManagementLocalBean.findBranchById(SecurityUtils.getPrincipal().getBranchId());
                List<BranchDTO> branchList = new ArrayList<BranchDTO>();
                branchList.add(branchDTO);
                mav.addObject("branchList", branchList);
            }catch (Exception e){}
        }

        List<PromItemsDTO> itemList = this.baoCaoHangHoaManagementLocalBean.findAllItems();
        mav.addObject("itemList", itemList);
        return mav;
    }

    private Map<String, Object> buildProperties(BaoCaoHangHoaCommand command){
        Map<String, Object> properties = new HashMap<String, Object>();
        if(command.getBranch_Id() != null){
            properties.put("branch_Id", command.getBranch_Id());
        }else{
            if(SecurityUtils.userHasAuthority(Constants.CHINHANH_ROLE)){
                properties.put("branch_Id", SecurityUtils.getPrincipal().getBranchId());
            }
        }
        if(command.getItem_Id() != null){
            properties.put("item_Id", command.getItem_Id());
        }
        if(command.getFromDateTime() != null){
            properties.put("fromDateTime", command.getFromDateTime());
        }
        if(command.getToDateTime() != null){
            properties.put("toDateTime", command.getToDateTime());
        }
        return properties;
    }

    private void convertDate2Timestamp(BaoCaoHangHoaCommand command){
        if(command.getFromDate() != null){
            command.setFromDateTime(DateUtil.dateToTimestamp(command.getFromDate(), Constants.VI_DATE_FORMAT));
        }
        if(command.getToDate() != null){
            command.setToDateTime(DateUtil.dateToTimestamp(command.getToDate(), Constants.VI_DATE_FORMAT));
        }
    }

    private void export2Excel(BaoCaoHangHoaCommand command, HttpServletRequest request,HttpServletResponse response)throws Exception{
        try{
            SimpleDateFormat df = new SimpleDateFormat("dd-M-yyyy");
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            String ngayXuatBaoCao = df.format(currentTimestamp);
            convertDate2Timestamp(command);
            Map<String, Object> properties = buildProperties(command);
            Object[] resultSet = this.baoCaoHangHoaManagementLocalBean.baoCaoHangHoaTheoChiNhanh(properties, 0, Integer.MAX_VALUE, command.getSortExpression(), command.getSortDirection());
            String outputFileName = "/files/temp/baocaohanghoatheochinhanh_" + ngayXuatBaoCao + ".xls";
            String reportTemplate = request.getSession().getServletContext().getRealPath("/files/temp/export/baocaohanghoatheochinhanh.xls");
            String export2FileName = request.getSession().getServletContext().getRealPath(outputFileName);
            WorkbookSettings ws = new WorkbookSettings();
            ExcelUtil.setEncoding4Workbook(ws);
            Workbook templateWorkbook = Workbook.getWorkbook(new File(reportTemplate), ws);
            WritableWorkbook workbook = Workbook.createWorkbook(new File(export2FileName), templateWorkbook);
            WritableSheet sheet = workbook.getSheet(0);
            int startRow = 7;

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

            if(command.getFromDateTime() != null){
                CellValue[] resValueNgayXuatBaoCaoTu = WebCommonUtil.addNgayXuatBaoCao(TOTAL_COLUMN_EXPORT);
                resValueNgayXuatBaoCaoTu[0] = new CellValue(CellDataType.STRING, "Ngày xuất báo cáo từ: " + df.format(command.getFromDateTime()));
                ExcelUtil.addRow(sheet, 3, resValueNgayXuatBaoCaoTu, stringNgayBaoCaoCellFormat, null, null, null);
            }
            if(command.getToDateTime() != null){
                CellValue[] resValueNgayXuatBaoCaoTu = WebCommonUtil.addNgayXuatBaoCao(TOTAL_COLUMN_EXPORT);
                resValueNgayXuatBaoCaoTu[0] = new CellValue(CellDataType.STRING, "Ngày xuất báo cáo đến: " + df.format(command.getToDateTime()));
                ExcelUtil.addRow(sheet, 4, resValueNgayXuatBaoCaoTu, stringNgayBaoCaoCellFormat, null, null, null);
            }

            List<BaoCaoHangHoaDTO> dtoList = (List<BaoCaoHangHoaDTO>)resultSet[1];
            if(dtoList.size() > 0){
                int indexRow = 1;
                for(BaoCaoHangHoaDTO dto : dtoList){
                    CellValue[] resValue = addCellValues(dto, indexRow);
                    ExcelUtil.addRow(sheet, startRow++, resValue, stringCellFormat, integerCellFormat, doubleCellFormat, null);
                    indexRow++;
                }
                workbook.write();
                workbook.close();
                response.sendRedirect(request.getSession().getServletContext().getContextPath() + outputFileName);
            }
        }catch (Exception e){
            throw new Exception("Failed to export to excel BaoCaoHangHoaTheoChiNhanh. \n" + e.getMessage());
        }
    }

    private CellValue[] addCellValues(BaoCaoHangHoaDTO dto, int indexRow){
        SimpleDateFormat df = new SimpleDateFormat("dd/M/yyyy");
        CellValue[] resValue = new CellValue[TOTAL_COLUMN_EXPORT];
        int columnIndex = 0;
        resValue[columnIndex++] = new CellValue(CellDataType.INT, indexRow);
        if(StringUtils.isNotBlank(dto.getBranch_Name())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getBranch_Name());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getCard_quantity() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getCard_quantity());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getKit_quantity() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getKit_quantity());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getBhtt_quantity() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getBhtt_quantity());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getBhtt_psc_25() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getBhtt_psc_25());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getPsc_amount() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getPsc_amount());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getGtkh_quantity() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getGtkh_quantity());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getGtkh_psc_25k() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getGtkh_psc_25k());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getVas_quantity() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getVas_quantity());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        return resValue;
    }
}
