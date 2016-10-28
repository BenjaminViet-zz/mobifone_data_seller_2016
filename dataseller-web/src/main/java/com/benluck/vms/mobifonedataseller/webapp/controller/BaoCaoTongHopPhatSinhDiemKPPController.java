package com.benluck.vms.mobifonedataseller.webapp.controller;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.utils.DateUtil;
import com.benluck.vms.mobifonedataseller.core.business.PromItemsManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.RetailDealerManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.TraCuuThongTinKPPManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.DealerAccountActionDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.PromItemsDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.RetailDealerDTO;
import com.benluck.vms.mobifonedataseller.editor.CustomDateEditor;
import com.benluck.vms.mobifonedataseller.security.util.SecurityUtils;
import com.benluck.vms.mobifonedataseller.util.ExcelUtil;
import com.benluck.vms.mobifonedataseller.util.RequestUtil;
import com.benluck.vms.mobifonedataseller.util.WebCommonUtil;
import com.benluck.vms.mobifonedataseller.webapp.command.command2014.TraCuuThongTinKPPCommand;
import com.benluck.vms.mobifonedataseller.webapp.dto.CellDataType;
import com.benluck.vms.mobifonedataseller.webapp.dto.CellValue;
import com.benluck.vms.mobifonedataseller.webapp.validator.TraCuuThongTinKPPValidator;
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
import org.springframework.validation.BindingResult;
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
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 11/7/14
 * Time: 11:25 AM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class BaoCaoTongHopPhatSinhDiemKPPController extends ApplicationObjectSupport{
    private Log log = LogFactory.getLog(TraCuuLichSuXuatKhoController.class);
    private static final Integer TOTAL_COLUMN_EXPORT = 8;

    @Autowired
    private TraCuuThongTinKPPManagementLocalBean traCuuThongTinKPPManagaementLocalBean;
    @Autowired
    private RetailDealerManagementLocalBean retailDealerManagementLocalBean;
    @Autowired
    private PromItemsManagementLocalBean promItemsManagementLocalBean;
    @Autowired
    private TraCuuThongTinKPPValidator traCuuThongTinKPPValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor("dd/MM/yyyy"));
    }

    @RequestMapping(value = {"/admin/baocaochitietphatsinh.html","/chinhanh/baocaochitietphatsinh.html","/tongdai/baocaochitietphatsinh.html"})
    public ModelAndView baoCaoChiTietPhatSinh(@ModelAttribute(value = Constants.FORM_MODEL_KEY)TraCuuThongTinKPPCommand command,
                             HttpServletRequest request,
                             HttpServletResponse response,
                             BindingResult bindingResult){
        ModelAndView mav = new ModelAndView("/admin/baocaotonghopphatsinhdiemkpp");
        String crudaction = command.getCrudaction();
        if(StringUtils.isNotBlank(crudaction)){
            if(crudaction.equals("search") || crudaction.equals("tro-lai")){
                if(command.getDealer_Id() == null && StringUtils.isBlank(command.getSoEZ())){
                    mav.addObject(Constants.ALERT_TYPE, "info");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("admin.tracuuthongtinkpp.msg.can_chon_dbh_hoac_nhap_so_ez_de_tra_cuu"));
                }else{
                    traCuuThongTinKPPValidator.validate(command, bindingResult);
                    if(!bindingResult.hasErrors() && StringUtils.isBlank(command.getMessage())){
                        RequestUtil.initSearchBean(request, command);
                        convertDate2Timestamp(command);
                        storeQueryInSession(command, request);
                        Map<String, Object> properties = buildProperties(command);
                        Object[] resultObject =  this.traCuuThongTinKPPManagaementLocalBean.search(properties, command.getFirstItem(), command.getMaxPageItems(), command.getSortExpression(), command.getSortDirection());
                        command.setTotalItems(Integer.valueOf(resultObject[0].toString()));
                        command.setListResult((List<DealerAccountActionDTO>)resultObject[1]);
                        mav.addObject(Constants.LIST_MODEL_KEY, command);
                        mav.addObject("page", command.getPage() - 1);

                        if(command.getTotalItems() == 0){
                            mav.addObject(Constants.ALERT_TYPE, "info");
                            mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("admin.tracuuthongtinkpp.msg.no_record_found"));
                        }
                    }else{
                        mav.addObject(Constants.ALERT_TYPE, "info");
                        mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, command.getMessage());
                    }
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
        referenceData(command, mav);
        return mav;
    }

    /**
     * Store searching params to the Session and get back to handle go to previous page.
     * @param command
     * @param request
     */
    private void storeQueryInSession(TraCuuThongTinKPPCommand command, HttpServletRequest request){
        StringBuilder storePara = new StringBuilder();
        if(command.getItem_Id() != null){
            if(StringUtils.isNotBlank(storePara.toString())){
                storePara.append("&");
            }
            storePara.append(Constants.PARAM_ITEM_ID).append("=").append(command.getItem_Id().toString());
        }
        if(command.getDealer_Id() != null){
            if(StringUtils.isNotBlank(storePara.toString())){
                storePara.append("&");
            }
            storePara.append(Constants.PARAM_DEALER_ID).append("=").append(command.getDealer_Id().toString());
        }
        if(StringUtils.isNotBlank(command.getSoEZ())){
            if(StringUtils.isNotBlank(storePara.toString())){
                storePara.append("&");
            }
            storePara.append(Constants.PARAM_SO_EZ).append("=").append(command.getSoEZ().toString());
        }
        if(command.getFromDate() != null){
            if(StringUtils.isNotBlank(storePara.toString())){
                storePara.append("&");
            }
            storePara.append(Constants.PARAM_FROM_DATE).append("=" + command.getFromDate().toString());
        }
        if(command.getToDate() != null){
            if(StringUtils.isNotBlank(storePara.toString())){
                storePara.append("&");
            }
            storePara.append(Constants.PARAM_TO_DATE).append("=").append(command.getToDate().toString());
        }
        request.getSession().setAttribute(Constants.STORE_REQUEST_PARAMS_STR + RequestUtil.getClusterSessionId(request), storePara.toString());
    }

    /**
     * Build pairs of key-value for querying.
     * @param command
     * @return
     */
    private Map<String, Object> buildProperties(TraCuuThongTinKPPCommand command){
        Map<String, Object> properties = new HashMap<String, Object>();
        if(command.getDealer_Id() != null){
            properties.put("dealer_Id", command.getDealer_Id());
        }
        if(StringUtils.isNotBlank(command.getSoEZ())){
            properties.put("soEZ", command.getSoEZ().trim());
        }
        if(command.getFromDateTime() != null){
            properties.put("fromDate", command.getFromDateTime());
        }
        if(command.getToDateTime() != null){
            properties.put("toDate", command.getToDateTime());
        }
        if(command.getItem_Id() != null){
            properties.put("item_Id", command.getItem_Id());
        }
        return properties;
    }

    /**
     * Get the Date value and format them to Timestamp.
     * @param command
     */
    private void convertDate2Timestamp(TraCuuThongTinKPPCommand command){
        if(command.getFromDate() != null){
            command.setFromDateTime(DateUtil.dateToTimestamp(command.getFromDate(), Constants.VI_DATE_FORMAT));
        }
        if(command.getToDate() != null){
            command.setToDateTime(DateUtil.dateToTimestamp(command.getToDate(), Constants.VI_DATE_FORMAT));
        }
    }

    /**
     * Fetch more related data base on role of user and put them to the model.
     * @param command
     * @param mav
     */
    private void referenceData(TraCuuThongTinKPPCommand command, ModelAndView mav){
        if(SecurityUtils.userHasAuthority(Constants.ADMIN_ROLE) || SecurityUtils.userHasAuthority(Constants.BAOCAO_ROLE)){
            List<RetailDealerDTO> retailDealerList = this.retailDealerManagementLocalBean.findAll_tdcg();
            mav.addObject("retailDealerList", retailDealerList);
        }else if(SecurityUtils.userHasAuthority(Constants.CHINHANH_ROLE)){
            List<RetailDealerDTO> retailDealerList = this.retailDealerManagementLocalBean.findByBranchId_tdcg(SecurityUtils.getPrincipal().getBranchId());
            mav.addObject("retailDealerList", retailDealerList);
        }

        List<PromItemsDTO> itemList = this.promItemsManagementLocalBean.findAll();
        mav.addObject("itemList", itemList);
    }

    /**
     * Export the report data to the Excel.
     * @param command
     * @param request
     * @param response
     * @throws Exception
     */
    private void export2Excel(TraCuuThongTinKPPCommand command, HttpServletRequest request,HttpServletResponse response)throws Exception{
        try{
            SimpleDateFormat df = new SimpleDateFormat("dd-M-yyyy");
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            String ngayXuatBaoCao = df.format(currentTimestamp);
            convertDate2Timestamp(command);
            Map<String, Object> properties = buildProperties(command);
            Object[] resultObject = this.traCuuThongTinKPPManagaementLocalBean.search(properties, 0, Integer.MAX_VALUE, command.getSortExpression(), command.getSortDirection());
            List<DealerAccountActionDTO> dtoList = (List<DealerAccountActionDTO>)resultObject[1];
            String outputFileName = "/files/temp/tracuuthongtinhangmucphatsinhkpp_" + ngayXuatBaoCao + ".xls";
            String reportTemplate = request.getSession().getServletContext().getRealPath("/files/temp/export/tracuuthongtinhangmucphatsinhkpp.xls");
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

            if(dtoList.size() > 0){
                int indexRow = 1;
                for(DealerAccountActionDTO dto : dtoList){
                    CellValue[] resValue = addCellValues(dto, indexRow);
                    ExcelUtil.addRow(sheet, startRow++, resValue, stringCellFormat, integerCellFormat, doubleCellFormat, null);
                    indexRow++;
                }
                workbook.write();
                workbook.close();
                response.sendRedirect(request.getSession().getServletContext().getContextPath() + outputFileName);
            }
        }catch (Exception e){
            throw new Exception("Failed to export to excel BaoCaoTongHopPhatSinhDiem. \n" + e.getMessage());
        }
    }

    /**
     * Write DTO's properties to Excel cells.
     * @param dto
     * @param indexRow
     * @return
     */
    private CellValue[] addCellValues(DealerAccountActionDTO dto, int indexRow){
        CellValue[] resValue = new CellValue[TOTAL_COLUMN_EXPORT];
        int columnIndex = 0;
        resValue[columnIndex++] = new CellValue(CellDataType.INT, indexRow);
        if(StringUtils.isNotBlank(dto.getBranch_Name())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getBranch_Name());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(StringUtils.isNotBlank(dto.getDistrict_Name())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getDistrict_Name());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(StringUtils.isNotBlank(dto.getTenLoaiPS())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getTenLoaiPS());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getCycle() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.INT, dto.getCycle());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getProm_point() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.INT, dto.getProm_point());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getSoTienTuongUng() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getSoTienTuongUng());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getSoMaDuThuong() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.INT, dto.getSoMaDuThuong());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        return resValue;
    }
}
