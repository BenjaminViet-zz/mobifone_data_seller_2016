package com.benluck.vms.mobifonedataseller.webapp.controller;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.utils.DateUtil;
import com.benluck.vms.mobifonedataseller.core.business.KPPManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.RetailDealerManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.ChiTietHangMucPhatSinhKPPDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.RetailDealerDTO;
import com.benluck.vms.mobifonedataseller.editor.CustomDateEditor;
import com.benluck.vms.mobifonedataseller.security.util.SecurityUtils;
import com.benluck.vms.mobifonedataseller.util.ExcelUtil;
import com.benluck.vms.mobifonedataseller.util.RequestUtil;
import com.benluck.vms.mobifonedataseller.util.WebCommonUtil;
import com.benluck.vms.mobifonedataseller.webapp.command.command2014.ChiTietHangMucPhatSinhDiemKPPCommand;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 11/7/14
 * Time: 12:12 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class BaoBaoChiTietHangMucPhatSinhDiemKPPController extends ApplicationObjectSupport{
    private Log log = LogFactory.getLog(TraCuuLichSuXuatKhoController.class);

    @Autowired
    private KPPManagementLocalBean kppManagementLocalBean;
    @Autowired
    private RetailDealerManagementLocalBean retailDealerManagementLocalBean;

    private static final Integer TOTAL_COLUMN_EXPORT_ITEM_1 = 7;
    private static final Integer TOTAL_COLUMN_EXPORT_ITEM_2 = 7;
    private static final Integer TOTAL_COLUMN_EXPORT_ITEM_3 = 7;
    private static final Integer TOTAL_COLUMN_EXPORT_ITEM_4 = 7;
    private static final Integer TOTAL_COLUMN_EXPORT_ITEM_5 = 5;
    private static final Integer TOTAL_COLUMN_EXPORT_ITEM_6 = 6;
    private static final Integer TOTAL_COLUMN_EXPORT_ITEM_7 = 5;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor("dd/MM/yyyy"));
    }

    @RequestMapping(value = {"/admin/baocaochitiethangmucphatsinhdiemkpp.html"
                            ,"/chinhanh/baocaochitiethangmucphatsinhdiemkpp.html"
                            ,"/tongdai/baocaochitiethangmucphatsinhdiemkpp.html"
                            ,"/tongdai/baocaochitiethangmucphatsinhdiemkpp.html"})
    public ModelAndView baoCaoChiTietHangMucPhatSinhDiemKPP(@ModelAttribute(value = Constants.FORM_MODEL_KEY)ChiTietHangMucPhatSinhDiemKPPCommand command,
                                                         HttpServletRequest request,
                                                         HttpServletResponse response){
        ModelAndView mav = new ModelAndView("/admin/baocaochitietphatsinhdiemkpp");
        String crudaction= command.getCrudaction();
        if(StringUtils.isNotBlank(crudaction)){
            if(crudaction.equals("go-back") || crudaction.indexOf("go-back") != -1){
                mav = checkGoBack(request);
                return mav;
            }else if(crudaction.equals("export")){
                try{
                    convertDate2Timestamp(command);
                    export2Excel(command, request, response);
                }catch (Exception e){
                    mav.addObject(Constants.ALERT_TYPE, "danger");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("export_data_exception"));
                }
            }
        }else{
            if(command.getDealer_Id() == null || command.getItem_Id() == null
                    || command.getCycle() == null){
                mav.addObject(Constants.ALERT_TYPE, "danger");
                mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("admin.chitiethangmucphatsinhdiemkpp.msg.not_enough_parameter"));
            }else{
                try{
                    RetailDealerDTO retailDealer = this.retailDealerManagementLocalBean.findById_tdcg(command.getDealer_Id());
                    mav.addObject("retailDealer", retailDealer);
                    RequestUtil.initSearchBean(request, command);
                    convertDate2Timestamp(command);
                    Object[] resultObject = this.kppManagementLocalBean.xemChiTietHangMucPhatSinh(true, command.getDealer_Id(), command.getItem_Id(), command.getFromDateTime(), command.getToDateTime(), command.getFirstItem(), command.getMaxPageItems(), command.getSortExpression(), command.getSortDirection());
                    command.setTotalItems(Integer.valueOf(resultObject[0].toString()));
                    command.setListResult((List<ChiTietHangMucPhatSinhKPPDTO>)resultObject[1]);
                    mav.addObject(Constants.LIST_MODEL_KEY, command);
                    mav.addObject("page", command.getPage() -1);
                    mav.addObject("item_Id",command.getItem_Id());

                    if(command.getTotalItems() == 0){
                        mav.addObject(Constants.ALERT_TYPE, "info");
                        mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("admin.chitiethangmucphatsinhdiemkpp.msg.no_record_found"));
                    }
                }catch (Exception e){
                    log.error("Error: " + e.getMessage());
                    mav.addObject(Constants.ALERT_TYPE, "danger");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("admin.chitiethangmucphatsinhdiemkpp.msg.error_find_info_dealer"));
                }
            }
        }
        return mav;
    }

    /**
     * Copy Date value and format to Timestamp.
     * @param command
     */
    private void convertDate2Timestamp(ChiTietHangMucPhatSinhDiemKPPCommand command){
        if(command.getFromDate() != null){
            command.setFromDateTime(DateUtil.dateToTimestamp(command.getFromDate(), Constants.VI_DATE_FORMAT));
        }
        if(command.getToDate() != null){
            command.setToDateTime(DateUtil.dateToTimestamp(command.getToDate(), Constants.VI_DATE_FORMAT));
        }
    }

    /**
     *
     * @param request
     * @return
     */
    private ModelAndView checkGoBack(HttpServletRequest request){
        String storeParmas = request.getSession().getAttribute(Constants.STORE_REQUEST_PARAMS_STR + RequestUtil.getClusterSessionId(request)).toString();
        String[] params = storeParmas.split("\\&");
        StringBuilder paramUrl = new StringBuilder();
        DateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        paramUrl.append("?crudaction=go-back");
        for(String param : params){
            if(param.indexOf(Constants.PARAM_ITEM_ID) != -1){
                paramUrl.append("&").append(Constants.PARAM_ITEM_ID).append("=").append(param.split("\\=")[1].toString());
            }
            if(param.indexOf(Constants.PARAM_SO_EZ) != -1){
                paramUrl.append("&").append(Constants.PARAM_SO_EZ).append("=").append(param.split("\\=")[1].toString());
            }
            if(param.indexOf(Constants.PARAM_DEALER_ID) != -1){
                paramUrl.append("&").append(Constants.PARAM_DEALER_ID).append("=").append(param.split("\\=")[1].toString());
            }
            if(param.indexOf(Constants.PARAM_FROM_DATE) != -1){
                try{
                    String fromDateInStr = param.split("\\=")[1].toString();
                    Date fromDateF = df.parse(fromDateInStr);
                    paramUrl.append("&").append(Constants.PARAM_FROM_DATE).append("=").append(sdf.format(fromDateF));
                }catch (Exception e){}
            }
            if(param.indexOf(Constants.PARAM_TO_DATE) != -1){
                try{
                    String toDateInStr = param.split("\\=")[1].toString();
                    Date toDateF = df.parse(toDateInStr);
                    paramUrl.append("&").append(Constants.PARAM_TO_DATE).append("=").append(sdf.format(toDateF));
                }catch (Exception e){}
            }
        }
        String role = "";
        if(SecurityUtils.userHasAuthority(Constants.ADMIN_ROLE)){
            role = "/admin";
        }else if(SecurityUtils.userHasAuthority(Constants.CHINHANH_ROLE)){
            role = "/chinhanh";
        }else if(SecurityUtils.userHasAuthority(Constants.TONGDAI_ROLE)){
            role = "/tongdai";
        }
        return new ModelAndView("redirect:" + role + "/baocaochitietphatsinh.html" + paramUrl.toString());
    }

    /**
     * Export the report data to Excel.
     * @param command
     * @param request
     * @param response
     * @throws Exception
     */
    private void export2Excel(ChiTietHangMucPhatSinhDiemKPPCommand command, HttpServletRequest request,HttpServletResponse response)throws Exception{
        try{
            SimpleDateFormat df = new SimpleDateFormat("dd-M-yyyy");
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            String ngayXuatBaoCao = df.format(currentTimestamp);


            if(command.getFromDate() != null){
                command.setFromDateTime(DateUtil.dateToTimestamp(command.getFromDate(), Constants.VI_DATE_FORMAT));
            }
            if(command.getToDate() != null){
                command.setToDateTime(DateUtil.dateToTimestamp(command.getToDate(), Constants.VI_DATE_FORMAT));
            }
            Object[] resultObject = this.kppManagementLocalBean.xemChiTietHangMucPhatSinh(true, command.getDealer_Id(), command.getItem_Id(), command.getFromDateTime(), command.getToDateTime(), 0, Integer.MAX_VALUE, command.getSortExpression(), command.getSortDirection());
            List<ChiTietHangMucPhatSinhKPPDTO> dtoList = (List<ChiTietHangMucPhatSinhKPPDTO>)resultObject[1];

            StringBuilder templateFileExport = new StringBuilder("baocaochitiethangmucphatsinhdiem_khuyenkhichmuabanhangBTG");
            if(command.getItem_Id().equals(Constants.ITEM_ID_2)){
//                templateFileExport = new StringBuilder("baocaochitiethangmucphatsinhdiem_khuyenkhichbanhangtructiepBTG");
                templateFileExport = new StringBuilder("baocaochitiethangmucphatsinhdiem_khuyenkhichmuabanhangBTGNew");
            }else if(command.getItem_Id().equals(Constants.ITEM_ID_3)){
                templateFileExport = new StringBuilder("baocaochitiethangmucphatsinhdiem_khuyenkhichthuebaoPSC");
            }else if(command.getItem_Id().equals(Constants.ITEM_ID_4)){
                templateFileExport = new StringBuilder("baocaochitiethangmucphatsinhdiem_khuyenkhichmuathetaikhoan");
            }else if(command.getItem_Id().equals(Constants.ITEM_ID_5)){
                templateFileExport = new StringBuilder("baocaochitiethangmucphatsinhdiem_khuyenkhichmoiKHthamgiaCTKM");
            }else if(command.getItem_Id().equals(Constants.ITEM_ID_6)){
                templateFileExport = new StringBuilder("baocaochitiethangmucphatsinhdiem_khuyenkhichbanVAS");
            }else if(command.getItem_Id().equals(Constants.ITEM_ID_7)){
                templateFileExport = new StringBuilder("baocaochitiethangmucphatsinhdiem_khuyenkhichcungcapthongtinthitruong");
            }

            String outputFileName = "/files/temp/" + templateFileExport.toString() + "_" + ngayXuatBaoCao + ".xls";
            String reportTemplate = request.getSession().getServletContext().getRealPath("/files/temp/export/" +templateFileExport.toString()+ ".xls");
            String export2FileName = request.getSession().getServletContext().getRealPath(outputFileName);
            WorkbookSettings ws = new WorkbookSettings();
            ExcelUtil.setEncoding4Workbook(ws);
            Workbook templateWorkbook = Workbook.getWorkbook(new File(reportTemplate), ws);
            WritableWorkbook workbook = Workbook.createWorkbook(new File(export2FileName), templateWorkbook);
            WritableSheet sheet = workbook.getSheet(0);
            int startRow = 8;

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


            int indexColCellFromAndToDate = 0;
            int indexRowCellFromAndToDate = 4;

            if(command.getFromDateTime() != null){
                CellValue[] resValueNgayXuatBaoCaoTu = WebCommonUtil.addNgayXuatBaoCao(TOTAL_COLUMN_EXPORT_ITEM_1);
                resValueNgayXuatBaoCaoTu[indexColCellFromAndToDate] = new CellValue(CellDataType.STRING, "Ngày xuất báo cáo từ: " + df.format(command.getFromDateTime()));
                ExcelUtil.addRow(sheet, indexRowCellFromAndToDate, resValueNgayXuatBaoCaoTu, stringNgayBaoCaoCellFormat, null, null, null);
            }
            if(command.getToDateTime() != null){
                CellValue[] resValueNgayXuatBaoCaoTu = WebCommonUtil.addNgayXuatBaoCao(TOTAL_COLUMN_EXPORT_ITEM_1);
                resValueNgayXuatBaoCaoTu[indexColCellFromAndToDate] = new CellValue(CellDataType.STRING, "Ngày xuất báo cáo đến: " + df.format(command.getToDateTime()));
                ExcelUtil.addRow(sheet, indexRowCellFromAndToDate + 1, resValueNgayXuatBaoCaoTu, stringNgayBaoCaoCellFormat, null, null, null);
            }

            if(dtoList.size() > 0){
                int indexRow = 1;
                for(ChiTietHangMucPhatSinhKPPDTO dto : dtoList){
                    if(command.getItem_Id().equals(Constants.ITEM_ID_1)){
                        CellValue[] resValue = addCellValues4Item1(dto, indexRow);
                        ExcelUtil.addRow(sheet, startRow++, resValue, stringCellFormat, integerCellFormat, doubleCellFormat, null);
                        indexRow++;
                    }else if(command.getItem_Id().equals(Constants.ITEM_ID_2)){
//                        CellValue[] resValue = addCellValues4Item2(dto, indexRow);
                        CellValue[] resValue = addCellValues4Item2New(dto, indexRow);
                        ExcelUtil.addRow(sheet, startRow++, resValue, stringCellFormat, integerCellFormat, doubleCellFormat, null);
                        indexRow++;
                    }else if(command.getItem_Id().equals(Constants.ITEM_ID_3)){
                        CellValue[] resValue = addCellValues4Item3(dto, indexRow);
                        ExcelUtil.addRow(sheet, startRow++, resValue, stringCellFormat, integerCellFormat, doubleCellFormat, null);
                        indexRow++;
                    }else if(command.getItem_Id().equals(Constants.ITEM_ID_4)){
                        CellValue[] resValue = addCellValues4Item4(dto, indexRow);
                        ExcelUtil.addRow(sheet, startRow++, resValue, stringCellFormat, integerCellFormat, doubleCellFormat, null);
                        indexRow++;
                    }else if(command.getItem_Id().equals(Constants.ITEM_ID_5)){
                        CellValue[] resValue = addCellValues4Item5(dto, indexRow);
                        ExcelUtil.addRow(sheet, startRow++, resValue, stringCellFormat, integerCellFormat, doubleCellFormat, null);
                        indexRow++;
                    }else if(command.getItem_Id().equals(Constants.ITEM_ID_6)){
                        CellValue[] resValue = addCellValues4Item6(dto, indexRow);
                        ExcelUtil.addRow(sheet, startRow++, resValue, stringCellFormat, integerCellFormat, doubleCellFormat, null);
                        indexRow++;
                    }else if(command.getItem_Id().equals(Constants.ITEM_ID_7)){
                        CellValue[] resValue = addCellValues4Item7(dto, indexRow);
                        ExcelUtil.addRow(sheet, startRow++, resValue, stringCellFormat, integerCellFormat, doubleCellFormat, null);
                        indexRow++;
                    }
                }
                workbook.write();
                workbook.close();
                response.sendRedirect(request.getSession().getServletContext().getContextPath() + outputFileName);
            }
        }catch (Exception e){
            throw new Exception("Failed to export to excel BaoCaoChiTietPhatSinhDiem. \n" + e.getMessage());
        }
    }

    /**
     * Write DTO's properties to Cells for exporting in case of item 1.
     * @param dto
     * @param indexRow
     * @return
     */
    private CellValue[] addCellValues4Item1(ChiTietHangMucPhatSinhKPPDTO dto, int indexRow){
        SimpleDateFormat df = new SimpleDateFormat("dd-M-yyyy");
        CellValue[] resValue = new CellValue[TOTAL_COLUMN_EXPORT_ITEM_1];
        int columnIndex = 0;
        resValue[columnIndex++] = new CellValue(CellDataType.INT, indexRow);
        if(StringUtils.isNotBlank(dto.getSoHoaDon())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getSoHoaDon());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getNgayHoaDon() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, df.format(dto.getNgayHoaDon()));
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(StringUtils.isNotBlank(dto.getMaHangHoa())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getMaHangHoa());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(StringUtils.isNotBlank(dto.getTenHangHoa())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getTenHangHoa());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getSoLuong() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.INT, dto.getSoLuong());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getGiaTri() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getGiaTri());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        return resValue;
    }

    /**
     * Write DTO's properties to Cells for exporting in case of item 2.
     * @param dto
     * @param indexRow
     * @return
     */
    private CellValue[] addCellValues4Item2(ChiTietHangMucPhatSinhKPPDTO dto, int indexRow){
        SimpleDateFormat df = new SimpleDateFormat("dd-M-yyyy");
        CellValue[] resValue = new CellValue[TOTAL_COLUMN_EXPORT_ITEM_2];
        int columnIndex = 0;
        resValue[columnIndex++] = new CellValue(CellDataType.INT, indexRow);
        if(StringUtils.isNotBlank(dto.getSoEZ())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getSoEZ());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getNgay_ps() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, df.format(dto.getNgay_ps()));
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(StringUtils.isNotBlank(dto.getNoiDungTinNhan())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getNoiDungTinNhan());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(StringUtils.isNotBlank(dto.getKetQua())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getKetQua());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        return resValue;
    }

    /**
     * Write DTO's properties to Cells for exporting in case of item 2 new.
     * @param dto
     * @param indexRow
     * @return
     */
    private CellValue[] addCellValues4Item2New(ChiTietHangMucPhatSinhKPPDTO dto, int indexRow){
        SimpleDateFormat df = new SimpleDateFormat("dd-M-yyyy");
        CellValue[] resValue = new CellValue[TOTAL_COLUMN_EXPORT_ITEM_2];
        int columnIndex = 0;
        resValue[columnIndex++] = new CellValue(CellDataType.INT, indexRow);
        if(StringUtils.isNotBlank(dto.getDealer_Code())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getDealer_Code());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(StringUtils.isNotBlank(dto.getDealer_Name())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getDealer_Name());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(StringUtils.isNotBlank(dto.getSoEZ())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getSoEZ());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getNgay_ps() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, df.format(dto.getNgay_ps()));
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(StringUtils.isNotBlank(dto.getSoKhachHang())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getSoKhachHang());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getNgayKichHoat() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, df.format(dto.getNgayKichHoat()));
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        return resValue;
    }

    /**
     * Write DTO's properties to Cells for exporting in case of item 3.
     * @param dto
     * @param indexRow
     * @return
     */
    private CellValue[] addCellValues4Item3(ChiTietHangMucPhatSinhKPPDTO dto, int indexRow){
        SimpleDateFormat df = new SimpleDateFormat("dd-M-yyyy");
        CellValue[] resValue = new CellValue[TOTAL_COLUMN_EXPORT_ITEM_3];
        int columnIndex = 0;
        resValue[columnIndex++] = new CellValue(CellDataType.INT, indexRow);
        if(StringUtils.isNotBlank(dto.getSo_thue_bao())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getSo_thue_bao());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getNgayNhanTin() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, df.format(dto.getNgayNhanTin()));
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getNgayKichHoat() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, df.format(dto.getNgayKichHoat()));
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getNgay_ps() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, df.format(dto.getNgay_ps()));
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getCuoc_ps() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getCuoc_ps());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getSoDiemQuiDoi() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getSoDiemQuiDoi());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        return resValue;
    }

    /**
     * Write DTO's properties to Cells for exporting in case of item 4.
     * @param dto
     * @param indexRow
     * @return
     */
    private CellValue[] addCellValues4Item4(ChiTietHangMucPhatSinhKPPDTO dto, int indexRow){
        SimpleDateFormat df = new SimpleDateFormat("dd-M-yyyy");
        CellValue[] resValue = new CellValue[TOTAL_COLUMN_EXPORT_ITEM_4];
        int columnIndex = 0;
        resValue[columnIndex++] = new CellValue(CellDataType.INT, indexRow);
        if(StringUtils.isNotBlank(dto.getSoHoaDon())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getSoHoaDon());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getNgayHoaDon() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, df.format(dto.getNgayHoaDon()));
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(StringUtils.isNotBlank(dto.getMaHangHoa())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getMaHangHoa());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(StringUtils.isNotBlank(dto.getTenHangHoa())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getTenHangHoa());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getSoLuong() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.INT, dto.getSoLuong());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getGiaTri() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getGiaTri());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        return resValue;
    }

    /**
     * Write DTO's properties to Cells for exporting in case of item 5.
     * @param dto
     * @param indexRow
     * @return
     */
    private CellValue[] addCellValues4Item5(ChiTietHangMucPhatSinhKPPDTO dto, int indexRow){
        SimpleDateFormat df = new SimpleDateFormat("dd-M-yyyy");
        CellValue[] resValue = new CellValue[TOTAL_COLUMN_EXPORT_ITEM_5];
        int columnIndex = 0;
        resValue[columnIndex++] = new CellValue(CellDataType.INT, indexRow);
        if(StringUtils.isNotBlank(dto.getSoEZ())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getSoEZ());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getNgay_ps() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, df.format(dto.getNgay_ps()));
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(StringUtils.isNotBlank(dto.getNoiDungTinNhan())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getNoiDungTinNhan());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(StringUtils.isNotBlank(dto.getKetQua())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getKetQua());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        return resValue;
    }

    /**
     * Write DTO's properties to Cells for exporting in case of item 6.
     * @param dto
     * @param indexRow
     * @return
     */
    private CellValue[] addCellValues4Item6(ChiTietHangMucPhatSinhKPPDTO dto, int indexRow){
        SimpleDateFormat df = new SimpleDateFormat("dd-M-yyyy");
        CellValue[] resValue = new CellValue[TOTAL_COLUMN_EXPORT_ITEM_6];
        int columnIndex = 0;
        resValue[columnIndex++] = new CellValue(CellDataType.INT, indexRow);
        if(StringUtils.isNotBlank(dto.getMaGiaoDich())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getMaGiaoDich());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(StringUtils.isNotBlank(dto.getSoEZ())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getSoEZ());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(StringUtils.isNotBlank(dto.getMaGoi())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getMaGoi());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(StringUtils.isNotBlank(dto.getTenGoi())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getTenGoi());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getNgay_ps() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, df.format(dto.getNgay_ps()));
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        return resValue;
    }

    /**
     * Write DTO's properties to Cells for exporting in case of item 7.
     * @param dto
     * @param indexRow
     * @return
     */
    private CellValue[] addCellValues4Item7(ChiTietHangMucPhatSinhKPPDTO dto, int indexRow){
        SimpleDateFormat df = new SimpleDateFormat("dd-M-yyyy");
        CellValue[] resValue = new CellValue[TOTAL_COLUMN_EXPORT_ITEM_7];
        int columnIndex = 0;
        resValue[columnIndex++] = new CellValue(CellDataType.INT, indexRow);
        if(StringUtils.isNotBlank(dto.getSoEZ())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getSoEZ());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getNgay_ps() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, df.format(dto.getNgay_ps()));
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(StringUtils.isNotBlank(dto.getNoiDungTinNhan())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getNoiDungTinNhan());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(StringUtils.isNotBlank(dto.getKetQua())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getKetQua());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        return resValue;
    }
}
