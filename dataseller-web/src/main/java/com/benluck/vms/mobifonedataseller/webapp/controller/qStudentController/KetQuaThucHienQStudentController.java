package com.benluck.vms.mobifonedataseller.webapp.controller.qStudentController;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.utils.DateUtil;
import com.benluck.vms.mobifonedataseller.core.business.*;
import com.benluck.vms.mobifonedataseller.core.dto.*;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.*;
import com.benluck.vms.mobifonedataseller.editor.CustomDateEditor;
import com.benluck.vms.mobifonedataseller.security.util.SecurityUtils;
import com.benluck.vms.mobifonedataseller.common.utils.PromotionEnum;
import com.benluck.vms.mobifonedataseller.util.ExcelUtil;
import com.benluck.vms.mobifonedataseller.util.RequestUtil;
import com.benluck.vms.mobifonedataseller.util.WebCommonUtil;
import com.benluck.vms.mobifonedataseller.webapp.command.command2014.*;
import com.benluck.vms.mobifonedataseller.webapp.dto.CellDataType;
import com.benluck.vms.mobifonedataseller.webapp.dto.CellValue;
import com.benluck.vms.mobifonedataseller.webapp.validator.GiftDeliveryAgentHistoryValidator;
import com.benluck.vms.mobifonedataseller.webapp.validator.GiftDeliveryThueBaoHistoryValidator;
import com.benluck.vms.mobifonedataseller.webapp.validator.validator2015.MaPhieuExchangeQStudent2015HuyDoiQuaValidator;
import com.benluck.vms.mobifonedataseller.webapp.validator.validator2015.MaPhieuExchangeQStudent2015Validator;
import com.benluck.vms.mobifonedataseller.webapp.validator.validator2015.MaPhieuThueBaoQStudent2015Validator;
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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.ejb.ObjectNotFoundException;
import javax.ejb.RemoveException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: HP
 * Date: 4/2/15
 * Time: 9:28 AM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class KetQuaThucHienQStudentController extends ApplicationObjectSupport {
    private static final Integer TOTAL_COLUMN_EXPORT = 4;
    private static final Integer TOTAL_COLUMN_EXPORT_TRA_CUU_MA_PHIEU = 8;
    private static final Integer TOTAL_COLUMN_EXPORT_TRAC_CUU_KHO_CUA_HANG_KHAC = 6;
    private static final Integer TOTAL_COLUMN_EXPORT_REPORT_KHCN = 9;

    @Autowired
    private MaPhieuCTTichDiemManagementLocalBean maPhieuCTTichDiemManagementLocalBean;
    @Autowired
    private GiftDeliveryAgentHistoryManagementLocalBean giftDeliveryAgentHistoryManagementLocalBean;
    @Autowired
    private GiftDeliveryAgentHistoryValidator giftDeliveryAgentHistoryValidator;
    @Autowired
    private MaPhieuExchangeQStudent2015Validator maPhieuExchangeQStudent2015Validator;
    @Autowired
    private StockAgentManagementLocalBean stockAgentManagementLocalBean;
    @Autowired
    private MaPhieuThueBaoQStudent2015Validator maPhieuThueBaoQStudent2015Validator;
    @Autowired
    private GiftDeliveryThueBaoHistoryValidator giftDeliveryThueBaoHistoryValidator;
    @Autowired
    private GiftDeliveryThueBaoHistoryManagementLocalBean giftDeliveryThueBaoHistoryManagementLocalBean;
    @Autowired
    private DepartmentManagementLocalBean departmentManagementLocalBean;
    @Autowired
    private ChiNhanhManagementLocalBean chiNhanhManagementLocalBean;
    @Autowired
    private BranchManagementLocalBean branchManagementLocalBean;
    @Autowired
    private MaPhieuExchangeQStudent2015HuyDoiQuaValidator maPhieuExchangeQStudent2015HuyDoiQuaValidator;

    private Log log = LogFactory.getLog(KetQuaThucHienQStudentController.class);

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor("dd/MM/yyyy"));
    }

    @RequestMapping(value={"/cuahangmobifone/qstudent/tracuutheomaphieu.html"
                            ,"/tongdai/qstudent/tracuutheomaphieu.html"
                            ,"/admin/qstudent/tracuutheomaphieu.html"
                            ,"/chinhanh/qstudent/tracuutheomaphieu.html"})
    public ModelAndView list(@ModelAttribute(value = Constants.FORM_MODEL_KEY)TicketCommand command,
                             HttpServletRequest request,
                             HttpServletResponse response,
                             BindingResult bindingResult) throws RemoveException {
        ModelAndView mav = new ModelAndView("/2015/q_student_and_q_teen/exchangeGift");
        String crudaction = command.getCrudaction();
        if(StringUtils.isNotBlank(crudaction)){
            if(crudaction.equals("search")){
                RequestUtil.initSearchBean(request, command);
                Object[] resultObject = this.maPhieuCTTichDiemManagementLocalBean.searchByMaPhieu_qStudent(command.getPojo().getMa_phieu(), command.getPojo().getDa_doi_qua(), command.getFirstItem(), command.getMaxPageItems(), command.getSortExpression(), command.getSortDirection());
                command.setTotalItems(Integer.valueOf(resultObject[0].toString()));
                command.setListResult((List<CTTichDiemMaPhieuDTO>)resultObject[1]);
                mav.addObject("page", command.getPage() - 1);
                mav.addObject(Constants.LIST_MODEL_KEY, command);

                if(command.getTotalItems() == 0){
                    mav.addObject(Constants.ALERT_TYPE, "info");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("cuahanggiaodich.tracuumaphieu.msg.no_record_found").replaceAll("\\{0}", command.getPojo().getMa_phieu()));
                }
            }else if(crudaction.equals("exchange")){
                mav = exchangeGift("/2015/q_student_and_q_teen/exchangeGift", command, bindingResult);
            }else if (crudaction.equals("cancel-exchange")){
                mav = cancelGiftExchange("/2015/q_student_and_q_teen/exchangeGift", command, bindingResult);
            }else if(crudaction.equals("export")){
                try{
                    export2ExcelTraCuuTheoMaPhieu(command, request, response);
                }catch (Exception e){
                    mav.addObject(Constants.ALERT_TYPE, "danger");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("label.export_failed"));
                }
            }
        }
        List<GiftDTO> giftList = this.giftDeliveryThueBaoHistoryManagementLocalBean.findGiftList();
        mav.addObject("giftList",giftList);
        return mav;
    }

    /**
     * This method is used to export data of this page to Excel.
     * @param command to bind model data and crudAction for processing.
     * @param request
     * @param response
     * @throws Exception
     */
    private void export2ExcelTraCuuTheoMaPhieu(TicketCommand command, HttpServletRequest request, HttpServletResponse response)throws Exception{
        try{
            Object[] resultObject = this.maPhieuCTTichDiemManagementLocalBean.searchByMaPhieu_qStudent(command.getPojo().getMa_phieu(), command.getPojo().getDa_doi_qua(), 0, Integer.MAX_VALUE, null, null);
            List<CTTichDiemMaPhieuDTO> dtoList = (List<CTTichDiemMaPhieuDTO>)resultObject[1];
            String outputFileName = "/files/temp/tracuutheomaphieu_" + System.currentTimeMillis() + ".xls";
            String reportTemplate = request.getSession().getServletContext().getRealPath("/files/temp/export/tracuutheomaphieu.xls");
            String export2FileName = request.getSession().getServletContext().getRealPath(outputFileName);
            WorkbookSettings ws = new WorkbookSettings();
            ExcelUtil.setEncoding4Workbook(ws);
            Workbook templateWorkbook = Workbook.getWorkbook(new File(reportTemplate), ws);
            WritableWorkbook workbook = Workbook.createWorkbook(new File(export2FileName), templateWorkbook);
            WritableSheet sheet = workbook.getSheet(0);
            int startRow = 4;

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
                for(CTTichDiemMaPhieuDTO dto : dtoList){
                    CellValue[] chiNhanhResValue = addToCellMaPhieuDTO(dto, indexRow);
                    ExcelUtil.addRow(sheet, startRow++, chiNhanhResValue, normalStringCellFormat, integerCellFormat, doubleCellFormat, null);
                    indexRow++;
                }
                workbook.write();
                workbook.close();
                response.sendRedirect(request.getSession().getServletContext().getContextPath() + outputFileName);
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            throw new Exception("Error when export data to excel.\n + details: " + e.getMessage());
        }
    }

    /**
     * This method is used to put DTOs data to each cells of rows before exporting Excel.
     * @param dto
     * @param indexRow
     * @return
     */
    private CellValue[] addToCellMaPhieuDTO(CTTichDiemMaPhieuDTO dto, Integer indexRow){
        SimpleDateFormat df = new SimpleDateFormat("dd/M/yyyy");
        CellValue[] resValue = new CellValue[TOTAL_COLUMN_EXPORT_TRA_CUU_MA_PHIEU];
        int columnIndex = 0;
        resValue[columnIndex++] = new CellValue(CellDataType.INT, indexRow);
        if(StringUtils.isNotBlank(dto.getMa_phieu())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getMa_phieu());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(StringUtils.isNotBlank(dto.getThue_bao())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getThue_bao());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getNgay_ps() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, df.format(dto.getNgay_ps()));
        }else{
            resValue[columnIndex++] = new CellValue();
        }

        StringBuilder da_doi_qua_status = new StringBuilder(this.getMessageSourceAccessor().getMessage("label.chua_doi_qua"));
        if(dto.getDa_doi_qua() != null && dto.getDa_doi_qua().equals(2)){
            da_doi_qua_status = new StringBuilder(this.getMessageSourceAccessor().getMessage("label.da_giao_qua"));
        }
        resValue[columnIndex++] = new CellValue(CellDataType.STRING, da_doi_qua_status.toString());
        if(dto.getNgay_doi_qua() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, df.format(dto.getNgay_doi_qua()));
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(StringUtils.isNotBlank(dto.getTenCuaHang())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getTenCuaHang());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getDiemTichLuy() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getDiemTichLuy());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        return resValue;
    }

    /**
     * This method is used to handle progress of gift exchanging.
     * @param view
     * @param command
     * @param bindingResult
     * @return
     */
    private ModelAndView exchangeGift(String view, TicketCommand command, BindingResult bindingResult){
        ModelAndView mav = new ModelAndView(view);
        if(command.getCheckList() == null || command.getCheckList().length == 0){
            mav.addObject(Constants.ALERT_TYPE, "warning");
            mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("cuahanggiaodich.label.alert_no_ma_phieu_chosen"));
        }else{
            // Validate validity of request before exchanging.
            maPhieuExchangeQStudent2015Validator.validate(command, bindingResult);
            if(!bindingResult.hasErrors() && StringUtils.isBlank(command.getMessage())){
                try{
                    // Get inventory total in stock.
                    Integer inventoryTotal = this.stockAgentManagementLocalBean.countInventoryTotalByAgentId_qStudent(SecurityUtils.getPrincipal().getDepartmentId(),command.getPojo().getGift().getGiftId());
                    if(inventoryTotal.compareTo(0) > 0){
                        if(inventoryTotal.compareTo(command.getCheckList().length) < 0){
                            mav.addObject(Constants.ALERT_TYPE, "info");
                            mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("cuahanggiaodich.not_enough_inventory_in_stock", new Object[]{inventoryTotal}));
                        }else{
                            this.maPhieuCTTichDiemManagementLocalBean.shopUserGiaoQua_qStudent(SecurityUtils.getLoginUserId(), command.getCheckList(), SecurityUtils.getPrincipal().getShopCode(), SecurityUtils.getPrincipal().getUsername(),command.getPojo().getGift().getGiftId());
                            mav.addObject(Constants.ALERT_TYPE, "success");
                            mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("cuahanggiaodich.giaoqua_successfully"));
                        }
                    }else{
                        mav.addObject(Constants.ALERT_TYPE, "info");
                        mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("cuahanggiaodich.out_of_inventory_in_stock"));
                    }
                }catch (Exception e){
                    log.error("An error happened while trying to update Ma Phieu status to Exchanged and write details to Action Log table.");
                    log.error(e.getMessage(), e);
                    mav.addObject(Constants.ALERT_TYPE, "danger");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("cuahanggiaodich.giaoqua_exception"));
                }
            }else{
                mav.addObject(Constants.ALERT_TYPE, "info");
                mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, command.getMessage());
            }
        }
        return mav;
    }

    /**
     * This method is used to handling progress of canceling.
     * @param view
     * @param command
     * @param bindingResult
     * @return
     */
    private ModelAndView cancelGiftExchange(String view, TicketCommand command, BindingResult bindingResult){
        ModelAndView mav = new ModelAndView(view);
        if(command.getCheckListHuy() == null || command.getCheckListHuy().length == 0){
            mav.addObject(Constants.ALERT_TYPE, "warning");
            mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("cuahanggiaodich.label.alert_no_ma_phieu_chosen"));
        }else{
            // Validate validity of request before canceling.
            maPhieuExchangeQStudent2015HuyDoiQuaValidator.validate(command, bindingResult);
            if(!bindingResult.hasErrors() && StringUtils.isBlank(command.getMessage())){
                try{
                    this.maPhieuCTTichDiemManagementLocalBean.cancelGiftExchange(SecurityUtils.getLoginUserId(), command.getCheckListHuy(), SecurityUtils.getPrincipal().getShopCode(), SecurityUtils.getPrincipal().getUsername(), command.getPojo().getGift().getGiftId());
                    mav.addObject(Constants.ALERT_TYPE, "success");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("cuahanggiaodich.huygiaoqua_successfully"));
                }catch (Exception e){
                    log.error("An error happened while trying to cancelling and write details to Action Log table.");
                    log.error(e.getMessage(), e);
                    mav.addObject(Constants.ALERT_TYPE, "danger");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("cuahanggiaodich.huygiaoqua_exception"));
                }
            }else{
                mav.addObject(Constants.ALERT_TYPE, "info");
                mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, command.getMessage());
            }
        }
        return mav;
    }

    @RequestMapping(value = {"/cuahangmobifone/qstudent/tracuutheosothuebao.html","/tongdai/qstudent/tracuutheosothuebao.html"
            ,"/admin/qstudent/tracuutheosothuebao.html","/chinhanh/qstudent/tracuutheosothuebao.html"})
    public ModelAndView findByThueBao(@ModelAttribute(value = Constants.FORM_MODEL_KEY)TicketCommand command,
                                      HttpServletRequest request,
                                      HttpServletResponse response,
                                      BindingResult bindingResult){
        ModelAndView mav = new ModelAndView("/2015/q_student_and_q_teen/exchangeGift");
        String crudaction = command.getCrudaction();
        if(StringUtils.isNotBlank(crudaction)){
            if(crudaction.equals("search")){
                maPhieuThueBaoQStudent2015Validator.validate(command, bindingResult);
                if(!bindingResult.hasErrors() && StringUtils.isBlank(command.getMessage())){
                    RequestUtil.initSearchBean(request, command);
                    Object[] resultObject = this.maPhieuCTTichDiemManagementLocalBean.searchByThueBao_qStudent(command.getPojo().getThue_bao(), command.getPojo().getDa_doi_qua(), command.getFirstItem(), command.getMaxPageItems(), command.getSortExpression(), command.getSortDirection());
                    command.setTotalItems(Integer.valueOf(resultObject[0].toString()));
                    command.setListResult((List<CTTichDiemMaPhieuDTO>)resultObject[1]);
                    mav.addObject("page", command.getPage() - 1);
                    mav.addObject(Constants.LIST_MODEL_KEY, command);

                    if(StringUtils.isNotBlank(command.getWarning())){
                        mav.addObject(Constants.ALERT_TYPE, "info");
                        mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, command.getWarning());
                    }else{
                        if(command.getTotalItems() == 0){
                            mav.addObject(Constants.ALERT_TYPE, "info");
                            mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("cuahanggiaodich.tracuutheothuebao.msg.no_record_found").replaceAll("\\{0}", command.getPojo().getThue_bao()));
                        }
                    }
                }else if(StringUtils.isNotBlank(command.getMessage())){
                    mav.addObject(Constants.ALERT_TYPE, "info");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, command.getMessage());
                }
            }else if(crudaction.equals("exchange")){
                mav = exchangeGift("/2015/q_student_and_q_teen/exchangeGift", command, bindingResult);
            }else if (crudaction.equals("cancel-exchange")){
                mav = cancelGiftExchange("/2015/q_student_and_q_teen/exchangeGift", command, bindingResult);
            }else if (crudaction.equals("export")){
                try{
                    export2ExcelTraCuuTheoThueBao(command, request, response);
                }catch (Exception e){
                    log.error(e.getMessage());
                    mav.addObject(Constants.ALERT_TYPE, "danger");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("label.export_failed"));
                }
            }
        }
        List<GiftDTO> giftList = this.giftDeliveryThueBaoHistoryManagementLocalBean.findGiftList();
        mav.addObject("giftList",giftList);
        mav.addObject(Constants.LIST_MODEL_KEY, command);
        return mav;
    }

    /**
     * This method is used to export report data to Excel based on Thue Bao filter.
     * @param command
     * @param request
     * @param response
     * @throws Exception
     */
    private void export2ExcelTraCuuTheoThueBao(TicketCommand command, HttpServletRequest request, HttpServletResponse response)throws Exception{
        try{
            Object[] resultObject = this.maPhieuCTTichDiemManagementLocalBean.searchByThueBao_qStudent(command.getPojo().getMa_phieu(), command.getPojo().getDa_doi_qua(), 0, Integer.MAX_VALUE, null, null);
            List<CTTichDiemMaPhieuDTO> dtoList = (List<CTTichDiemMaPhieuDTO>)resultObject[1];
            String outputFileName = "/files/temp/tracuutheothuebao_" + System.currentTimeMillis() + ".xls";
            String reportTemplate = request.getSession().getServletContext().getRealPath("/files/temp/export/tracuutheothuebao.xls");
            String export2FileName = request.getSession().getServletContext().getRealPath(outputFileName);
            WorkbookSettings ws = new WorkbookSettings();
            ExcelUtil.setEncoding4Workbook(ws);
            Workbook templateWorkbook = Workbook.getWorkbook(new File(reportTemplate), ws);
            WritableWorkbook workbook = Workbook.createWorkbook(new File(export2FileName), templateWorkbook);
            WritableSheet sheet = workbook.getSheet(0);
            int startRow = 4;

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
                for(CTTichDiemMaPhieuDTO dto : dtoList){
                    CellValue[] resValue = addToCellMaPhieuDTO(dto, indexRow);
                    ExcelUtil.addRow(sheet, startRow++, resValue, normalStringCellFormat, integerCellFormat, doubleCellFormat, null);
                    indexRow++;
                }
                workbook.write();
                workbook.close();
                response.sendRedirect(request.getSession().getServletContext().getContextPath() + outputFileName);
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            throw new Exception("Error when export data to excel.\n + details: " + e.getMessage());
        }
    }

    @RequestMapping("/cuahangmobifone/qstudent/nhaphang.html")
    public ModelAndView nhanHang(@ModelAttribute(value = Constants.FORM_MODEL_KEY)GiftDeliveryAgentHistoryCommand command,
                                 BindingResult bindingResult){
        ModelAndView mav = new ModelAndView("/cuahangmobifone/nhapkho");
        String crudaction = command.getCrudaction();
        if(StringUtils.isNotBlank(crudaction) && crudaction.equals("insert")){
            giftDeliveryAgentHistoryValidator.validate(command,bindingResult);
            if(!bindingResult.hasErrors()){
                try{
                    UserDTO nguoiNhap = new UserDTO();
                    nguoiNhap.setUserId(SecurityUtils.getLoginUserId());
                    nguoiNhap.setUserName(SecurityUtils.getPrincipal().getUserName());
                    command.getPojo().setUser(nguoiNhap);
                    this.giftDeliveryAgentHistoryManagementLocalBean.inputStock(command.getPojo());
                    mav.addObject(Constants.ALERT_TYPE, "success");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("stockagent.msg.nhap_kho_successfully"));
                }catch (Exception e){
                    log.error("Error when save info for delivery stock. \n Details: " + e.getMessage());
                    mav.addObject(Constants.ALERT_TYPE, "danger");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("stockagent.msg.nhap_kho_exception"));
                }
            }
        }
        mav.addObject("ngayGiaoNhanHangTuVMS", new Timestamp(System.currentTimeMillis()));

        Integer inventoryTotal = this.stockAgentManagementLocalBean.countInventoryTotalByAgentId_tdcg(SecurityUtils.getPrincipal().getDepartmentId());
        mav.addObject("inventoryTotal", inventoryTotal);
        return mav;
    }

    @RequestMapping("/cuahangmobifone/qstudent/xuatkho.html")
    public ModelAndView xuatKho(@ModelAttribute(value = Constants.FORM_MODEL_KEY)GiftDeliveryThueBaoHistoryCommand command){
        ModelAndView mav = new ModelAndView("/cuahangmobifone/xuatkho");
        String crudaction = command.getCrudaction();
        if(StringUtils.isNotBlank(crudaction)){
            if(crudaction.equals("insert-update")){
                try{
                    this.giftDeliveryAgentHistoryManagementLocalBean.getFromStock(SecurityUtils.getLoginUserId(), SecurityUtils.getPrincipal().getDepartmentId(), command.getPojo().getQuantity());
                    mav.addObject(Constants.ALERT_TYPE, "info");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("cuahanggiaodich.xuat_kho.msg.successfully"));
                }catch (Exception e){
                    mav.addObject(Constants.ALERT_TYPE, "danger");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("cuahanggiaodich.xuat_kho.msg.exception"));
                }
            }
        }
        try{
            Integer totalRealInStock = this.stockAgentManagementLocalBean.countInventoryTotalByAgentId_tdcg(SecurityUtils.getPrincipal().getDepartmentId());
            mav.addObject("inventoryTotal", totalRealInStock);
        }catch (Exception e){
            mav.addObject("inventoryTotal", 0);
        }
        return mav;
    }

    @RequestMapping(value = {"/cuahangmobifone/qstudent/lichsunhapkho.html","/admin/qstudent/lichsunhapkho.html","/chinhanh/qstudent/lichsunhapkho.html"})
    public ModelAndView lichsunhapkho(@ModelAttribute(value = Constants.FORM_MODEL_KEY)GiftDeliveryAgentHistoryCommand command,
                                      HttpServletRequest request){
        ModelAndView mav = new ModelAndView("/cuahangmobifone/lichsunhapkho");
        convert2Timestamp(command);
        RequestUtil.initSearchBean(request, command);
        Object[] resultObject = null;
        if(SecurityUtils.userHasAuthority(Constants.ADMIN_ROLE)
                || SecurityUtils.userHasAuthority(Constants.CHINHANH_ROLE)){
            resultObject = this.giftDeliveryAgentHistoryManagementLocalBean.search_qStudent(command.getDepartmentId(), command.getPojo(), command.getFirstItem(), command.getMaxPageItems(), command.getSortExpression(), command.getSortDirection());
            if(command.getDepartmentId() != null && Integer.valueOf(resultObject[0].toString()).equals(0)){
                mav.addObject(Constants.ALERT_TYPE, "info");
                mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("lichsugiaonhanhang.label.no_history"));
            }
        }else{
            resultObject = this.giftDeliveryAgentHistoryManagementLocalBean.search_qStudent(SecurityUtils.getPrincipal().getDepartmentId(), command.getPojo(), command.getFirstItem(), command.getMaxPageItems(), command.getSortExpression(), command.getSortDirection());
            if(Integer.valueOf(resultObject[0].toString()).equals(0)){
                mav.addObject(Constants.ALERT_TYPE, "info");
                mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("lichsugiaonhanhang.label.no_history"));
            }
        }
        command.setTotalItems(Integer.valueOf(resultObject[0].toString()));
        command.setListResult((List<GiftDeliveryAgentHistoryDTO>)resultObject[1]);
        mav.addObject("page", command.getPage() - 1);
        mav.addObject(Constants.LIST_MODEL_KEY,command);
        return mav;
    }

    /**
     * Retrieve Date value and format to Timestamp.
     * @param command comprise model data.
     */
    private void convert2Timestamp(GiftDeliveryAgentHistoryCommand command){
        if(command.getFromDate() != null){
            command.getPojo().setFromDate(DateUtil.dateToTimestamp(command.getFromDate(), Constants.VI_DATE_FORMAT));
        }
        if(command.getToDate() != null){
            command.getPojo().setToDate(DateUtil.dateToTimestamp(command.getToDate(), Constants.VI_DATE_FORMAT));
        }
    }

    @RequestMapping(value = {"/cuahangmobifone/qstudent/baocaotheokhcn.html","/tongdai/qstudent/baocaotheokhcn.html"
            ,"/admin/qstudent/baocaotheokhcn.html","/chinhanh/qstudent/baocaotheokhcn.html",
            "/baocao/qstudent/baocaotheokhcn.html"})
    public ModelAndView reportByKHCN(@ModelAttribute(value = Constants.FORM_MODEL_KEY)GiftDeliveryThueBaoHistoryCommand command,
                                     BindingResult bindingResult,
                                     HttpServletRequest request,
                                     HttpServletResponse response){
        ModelAndView mav = new ModelAndView("/2015/q_student_and_q_teen/reportykhcn");
        String crudaction = command.getCrudaction();
        if(StringUtils.isNotBlank(crudaction)){
            if(crudaction.equals("search")){
                giftDeliveryThueBaoHistoryValidator.validate(command, bindingResult);
                if(!bindingResult.hasErrors()){
                    if(command.getFromDate() != null){
                        command.getPojo().setFromDate(DateUtil.dateToTimestamp(command.getFromDate(), Constants.VI_DATE_FORMAT));
                    }
                    if(command.getToDate() != null){
                        command.getPojo().setToDate(DateUtil.dateToTimestamp(command.getToDate(), Constants.VI_DATE_FORMAT));
                    }

                    RequestUtil.initSearchBean(request, command);
                    Object[] resultObject = this.giftDeliveryThueBaoHistoryManagementLocalBean.reportByKHCN_qStudent(command.getPojo(), command.getFirstItem(), command.getMaxPageItems(), command.getSortExpression(), command.getSortDirection());
                    command.setTotalItems(Integer.valueOf(resultObject[0].toString()));
                    command.setListResult((List<GiftDeliveryThueBaoHistoryDTO>)resultObject[1]);
                    mav.addObject("page", command.getPage() - 1);
                    mav.addObject(Constants.LIST_MODEL_KEY, command);
                    if(command.getTotalItems() == 0){
                        mav.addObject(Constants.ALERT_TYPE, "info");
                        mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("chinhanh.doi_tien_dbh.no_record_found"));
                    }
                }
            }else if(crudaction.equals("export")){
                try{
                    export2ExcelGiftExchangeByKHCN(command, request, response);
                }catch (Exception e){
                    mav.addObject(Constants.ALERT_TYPE, "danger");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("label.export_failed"));
                }

            }
        }
        return mav;
    }

    /**
     * This method is used to export report data for Gift Exchanging by KHCN.
     * @param command
     * @param request
     * @param response
     * @throws Exception
     */
    private void export2ExcelGiftExchangeByKHCN(GiftDeliveryThueBaoHistoryCommand command, HttpServletRequest request, HttpServletResponse response) throws Exception{
        try{
            SimpleDateFormat df = new SimpleDateFormat("dd-M-yyyy");
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            String ngayXuatBaoCao = df.format(currentTimestamp);

            if(command.getFromDate() != null){
                command.getPojo().setFromDate(DateUtil.dateToTimestamp(command.getFromDate(), Constants.VI_DATE_FORMAT));
            }
            if(command.getToDate() != null){
                command.getPojo().setToDate(DateUtil.dateToTimestamp(command.getToDate(), Constants.VI_DATE_FORMAT));
            }
            Object[] resultObject = this.giftDeliveryThueBaoHistoryManagementLocalBean.reportByKHCN_qStudent(command.getPojo(), 0, Integer.MAX_VALUE, command.getSortExpression(), command.getSortDirection());
            List<GiftDeliveryThueBaoHistoryDTO> dtoList = (List<GiftDeliveryThueBaoHistoryDTO>)resultObject[1];
            String outputFileName = "/files/temp/baocaogiaoquatheokhcn_" + ngayXuatBaoCao + ".xls";
            String reportTemplate = request.getSession().getServletContext().getRealPath("/files/temp/export/baocaogiaoquatheokhcn.xls");
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

            WritableCellFormat stringNgayBaoCaoCellFormat = new WritableCellFormat(normalFont);
            stringNgayBaoCaoCellFormat.setBorder(jxl.format.Border.ALL, BorderLineStyle.NONE);
            stringNgayBaoCaoCellFormat.setAlignment(Alignment.CENTRE);

            WritableCellFormat cellFormat = new WritableCellFormat(normalFont);
            cellFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.MEDIUM);

            WritableCellFormat writableCellFormat = new WritableCellFormat(normalFont);
            writableCellFormat.setBorder(jxl.format.Border.ALL, BorderLineStyle.MEDIUM);
            writableCellFormat.setAlignment(Alignment.RIGHT);

            if(command.getPojo().getFromDate() != null){
                CellValue[] resValueNgayXuatBaoCaoTu = WebCommonUtil.addNgayXuatBaoCao(TOTAL_COLUMN_EXPORT_REPORT_KHCN);
                resValueNgayXuatBaoCaoTu[0] = new CellValue(CellDataType.STRING, "Ngày xuất báo cáo từ: " + df.format(command.getPojo().getFromDate()));
                ExcelUtil.addRow(sheet, 3, resValueNgayXuatBaoCaoTu, stringNgayBaoCaoCellFormat, null, null, null);
            }
            if(command.getPojo().getToDate() != null){
                CellValue[] resValueNgayXuatBaoCaoTu = WebCommonUtil.addNgayXuatBaoCao(TOTAL_COLUMN_EXPORT_REPORT_KHCN);
                resValueNgayXuatBaoCaoTu[0] = new CellValue(CellDataType.STRING, "Ngày xuất báo cáo đến: " + df.format(command.getPojo().getToDate()));
                ExcelUtil.addRow(sheet, 4, resValueNgayXuatBaoCaoTu, stringNgayBaoCaoCellFormat, null, null, null);
            }

            if(dtoList.size() > 0){
                int indexRow = 1;
                for(GiftDeliveryThueBaoHistoryDTO dto : dtoList){
                    CellValue[] chiNhanhResValue = addCellValues(dto, indexRow);
                    ExcelUtil.addRow(sheet, startRow++, chiNhanhResValue, cellFormat, writableCellFormat, null, null);
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
     * Write DTO to cells in sheet of Excel.
     * @param dataRow DTO which comprise data for cells data
     * @param indexRow row index in Sheet of Excel file.
     * @return
     */
    private CellValue[] addCellValues(GiftDeliveryThueBaoHistoryDTO dataRow, int indexRow){
        SimpleDateFormat df = new SimpleDateFormat("dd/M/yyyy");
        CellValue[] resValue = new CellValue[TOTAL_COLUMN_EXPORT_REPORT_KHCN];
        int columnIndex = 0;
        resValue[columnIndex++] = new CellValue(CellDataType.INT, indexRow);
        resValue[columnIndex++] = new CellValue(CellDataType.STRING, dataRow.getGiftDeliveryThueBao().getThueBao());
        resValue[columnIndex++] = new CellValue(CellDataType.STRING, dataRow.getMa_phieu());
        resValue[columnIndex++] = new CellValue(CellDataType.STRING, dataRow.getGift().getName());
        resValue[columnIndex++] = new CellValue(CellDataType.INT, dataRow.getQuantity());
        resValue[columnIndex++] = new CellValue(CellDataType.STRING, df.format(dataRow.getGiftDeliveryThueBao().getDeliveryTime()));
        if(dataRow.getGiftDeliveryThueBao().getNvGiao().getDisplayName() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dataRow.getGiftDeliveryThueBao().getNvGiao().getDisplayName());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        resValue[columnIndex++] = new CellValue(CellDataType.STRING, dataRow.getTenCuaHangGiaoQua());
        resValue[columnIndex++] = new CellValue(CellDataType.STRING, this.getMessageSourceAccessor().getMessage("label.da_giao_qua"));
        return resValue;
    }

    @RequestMapping(value = {"/cuahangmobifone/qstudent/khohangcuahangkhac.html","/tongdai/qstudent/khohangcuahangkhac.html"
            ,"/admin/qstudent/khohangcuahangkhac.html","/chinhanh/qstudent/khohangcuahangkhac.html"})
    public ModelAndView tracuukhohangcuahangmobifonekhac(@ModelAttribute(value = Constants.FORM_MODEL_KEY)TraCuuKhoHangCuaHangMobifoneKhacCommand command,
                                                         HttpServletRequest request,
                                                         HttpServletResponse response) throws ObjectNotFoundException {
        ModelAndView mav = new ModelAndView("/2015/q_student_and_q_teen/tracuukhohangcuahangkhac");
        String crudaction = command.getCrudaction();
        if(StringUtils.isNotBlank(crudaction)){
            if(crudaction.equals("export")){
                try{
                    exportTraCuuKhoCuaHangKhac2Excel(command, request, response);
                }catch (Exception e){
                    log.error(e.getMessage());
                    mav.addObject(Constants.ALERT_TYPE, "danger");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("label.export_failed"));
                }
            }
        }

        RequestUtil.initSearchBean(request, command);
        Object[] resultObject = this.stockAgentManagementLocalBean.searchOtherShopStockByProperties_qStudent(PromotionEnum.Q_STUDENT_Q_TEEN_2015.getCode(), command.getPojo(), command.getFirstItem(), command.getMaxPageItems(), command.getSortExpression(), command.getSortDirection());
        command.setTotalItems(Integer.valueOf(resultObject[0].toString()));
        command.setListResult((List<StockAgentDTO>)resultObject[1]);
        mav.addObject("page", command.getPage() - 1);
        mav.addObject(Constants.LIST_MODEL_KEY, command);
        if(command.getTotalItems() == 0){
            mav.addObject(Constants.ALERT_TYPE, "info");
            mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("chinhanh.doi_tien_dbh.no_record_found"));
        }
        List<BranchDTO> branchList = this.branchManagementLocalBean.findByCTCode(PromotionEnum.Q_STUDENT_Q_TEEN_2015.getCode());
        mav.addObject("branchList", branchList);

         if(command.getPojo().getBranch() != null && command.getPojo().getBranch().getBranch_Id() != null){
             List<DepartmentDTO> departmentList = this.departmentManagementLocalBean.findDepartmentListByBranchIdAndCtCode(command.getPojo().getBranch().getBranch_Id(), PromotionEnum.Q_STUDENT_Q_TEEN_2015.getCode());
             mav.addObject("departmentList", departmentList);
        } else {
             List<DepartmentDTO> departmentList = this.departmentManagementLocalBean.findAll();
             mav.addObject("departmentList", departmentList);
         }
        return mav;
    }

    /**
     * Export data to Excel for report Tra Cuu Kho Cua Hang Khac
     * @param command
     * @param request
     * @param response
     * @throws Exception
     */
    private void exportTraCuuKhoCuaHangKhac2Excel(TraCuuKhoHangCuaHangMobifoneKhacCommand command, HttpServletRequest request, HttpServletResponse response) throws Exception{
        Object[] resultObject = this.stockAgentManagementLocalBean.searchOtherShopStockByProperties_qStudent(PromotionEnum.Q_STUDENT_Q_TEEN_2015.getCode(), command.getPojo(), command.getFirstItem(), command.getMaxPageItems(), command.getSortExpression(), command.getSortDirection());
        List<StockAgentDTO> dtoList = (List<StockAgentDTO>)resultObject[1];
        try{
            SimpleDateFormat df = new SimpleDateFormat("dd-M-yyyy");
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            String ngayXuatBaoCao = df.format(currentTimestamp);
            String outputFileName = "/files/temp/tracuukhocuahangkhac_" + ngayXuatBaoCao + ".xls";
            String reportTemplate = request.getSession().getServletContext().getRealPath("/files/temp/export/tracuukhocuahangkhac.xls");
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

            int indexRow = 1;
            if(dtoList.size() > 0){
                for(StockAgentDTO dto : dtoList){
                    CellValue[] resValue = addCellReportTraCuuKhoCuaHangKhac(dto, indexRow);
                    ExcelUtil.addRow(sheet, startRow++, resValue, normalStringCellFormat, integerCellFormat, null, null);
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

    /**
     * Write report data to cells for Excel exporting.
     * @param dataRow DTO
     * @param indexRow row index in Ex
     * @return
     */
    private CellValue[] addCellReportTraCuuKhoCuaHangKhac(StockAgentDTO dataRow, Integer indexRow){
        CellValue[] resValue = new CellValue[TOTAL_COLUMN_EXPORT_TRAC_CUU_KHO_CUA_HANG_KHAC];
        int columnIndex = 0;
        resValue[columnIndex++] = new CellValue(CellDataType.INT, indexRow);
        if (dataRow.getBranch() != null && StringUtils.isNotBlank(dataRow.getBranch().getBranch_name())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dataRow.getBranch().getBranch_name());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if (dataRow.getDepartment() != null && StringUtils.isNotBlank(dataRow.getDepartment().getName())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dataRow.getDepartment().getName());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if (dataRow.getGift() != null && StringUtils.isNotBlank(dataRow.getGift().getName())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dataRow.getGift().getName());
        }else{
            resValue[columnIndex++] = new CellValue();
        }

        StringBuilder trang_thai_kho = new StringBuilder(this.getMessageSourceAccessor().getMessage("label.con_qua"));
        if(dataRow.getTotal().compareTo(0) <= 0){
            trang_thai_kho = new StringBuilder(this.getMessageSourceAccessor().getMessage("label.het_qua"));
        }
        resValue[columnIndex++] = new CellValue(CellDataType.STRING, trang_thai_kho.toString());
        resValue[columnIndex++] = new CellValue(CellDataType.INT, dataRow.getTotal());
        return resValue;
    }

    @RequestMapping("/ajax/cuahangmobifone/qstudent/getDeprtmentListByBranchIdAndCtCode.html")
    public @ResponseBody
    Map ajaxGetDepartmentListByChiNhanh(@RequestParam(value = "branch")Long branchId){
        Map<String, Object> map = new HashMap<String, Object>();
        List<DepartmentDTO> departmentList;
        if (branchId !=null){
            departmentList = this.departmentManagementLocalBean.findDepartmentListByBranchIdAndCtCode(branchId, PromotionEnum.Q_STUDENT_Q_TEEN_2015.getCode());
        }else {
            departmentList = this.departmentManagementLocalBean.findAll();

        }
        map.put("departmentList", departmentList);
        return  map;
    }

    @RequestMapping(value = {"/cuahangmobifone/qstudent/quanlyphieukhuyenmai.html"
                            ,"/tongdai/qstudent/quanlyphieukhuyenmai.html"
                            ,"/admin/qstudent/quanlyphieukhuyenmai.html"
                            ,"/chinhanh/qstudent/quanlyphieukhuyenmai.html"
                            ,"/baocao/qstudent/quanlyphieukhuyenmai.html"})
    public ModelAndView baocaoquanlyphieukhuyenmai(@ModelAttribute(value = Constants.FORM_MODEL_KEY)ChiNhanhCommand command,
                                                   HttpServletRequest request,
                                                   HttpServletResponse response) throws ObjectNotFoundException {
        ModelAndView mav = new ModelAndView("/2015/q_student_and_q_teen/baocaoquanlyphieukhuyenmai");
        String crudaction = command.getCrudaction();
        if(StringUtils.isNotBlank(crudaction)){
            if(crudaction.equals("search")){
                List<BranchDTO> dtoList = this.chiNhanhManagementLocalBean.baoCaoQuanLyPhieuKhuyenMai_qStudent(PromotionEnum.Q_STUDENT_Q_TEEN_2015.getCode(), command.getPojo());
                mav.addObject("reportDataList", dtoList);
            }else if(crudaction.equals("export")){
                try{
                    export2ExcelPromotionTicketData(command, request, response);
                }catch (Exception e){
                    mav.addObject(Constants.ALERT_TYPE, "danger");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("export_data_exception"));
                }
            }
        }
        if(SecurityUtils.userHasAuthority(Constants.ADMIN_ROLE)
                || SecurityUtils.userHasAuthority(Constants.TONGDAI_ROLE) || SecurityUtils.userHasAuthority(Constants.BAOCAO_ROLE)){
            List<BranchDTO> branchList = this.branchManagementLocalBean.findByCTCode(PromotionEnum.Q_STUDENT_Q_TEEN_2015.getCode());
            mav.addObject("branchList", branchList);
        }else if(SecurityUtils.userHasAuthority(Constants.CHINHANH_ROLE)){
            try{
                BranchDTO branchDTO = this.branchManagementLocalBean.findByChiNhanhIdAndCTCode(SecurityUtils.getPrincipal().getMappingDBLinkBranchId(), PromotionEnum.Q_STUDENT_Q_TEEN_2015.getCode());
                List<BranchDTO> branchList = new ArrayList<>();
                branchList.add(branchDTO);
                mav.addObject("branchList", branchList);
                command.getPojo().setBranch(branchDTO);
            }catch (ObjectNotFoundException oe){}


        }else if(SecurityUtils.userHasAuthority(Constants.NHANVIEN_ROLE)){
            try{
                BranchDTO branchDTO = this.branchManagementLocalBean.findByDepartmentIdAndCTCode(SecurityUtils.getPrincipal().getDepartmentId(), PromotionEnum.Q_STUDENT_Q_TEEN_2015.getCode());
                List<BranchDTO> branchList = new ArrayList<BranchDTO>();
                branchList.add(branchDTO);
                mav.addObject("branchList", branchList);
            }catch (Exception e){}

            try{
                DepartmentDTO departmentDTO = this.departmentManagementLocalBean.findById(SecurityUtils.getPrincipal().getDepartmentId());
                List<DepartmentDTO> departmentList = new ArrayList<DepartmentDTO>();
                departmentList.add(departmentDTO);
                mav.addObject("departmentList", departmentList);
            }catch (Exception e){}
        }

        if(!SecurityUtils.userHasAuthority(Constants.NHANVIEN_ROLE)){
            if(command.getPojo().getBranch() != null && command.getPojo().getBranch().getBranch_Id() != null){
                List<DepartmentDTO> departmentList = this.departmentManagementLocalBean.findDepartmentListByBranchIdAndCtCode(command.getPojo().getBranch().getBranch_Id(), PromotionEnum.Q_STUDENT_Q_TEEN_2015.getCode());
                mav.addObject("departmentList", departmentList);
            } else {
                List<DepartmentDTO> departmentList = this.departmentManagementLocalBean.findAll();
                mav.addObject("departmentList", departmentList);
            }
        }
        return mav;
    }

    /**
     * This method is used to export promotion ticket data to Excel.
     * @param command
     * @param request
     * @param response
     * @throws Exception
     */
    private void export2ExcelPromotionTicketData(ChiNhanhCommand command, HttpServletRequest request, HttpServletResponse response) throws Exception{
        List<BranchDTO> dtoList = this.chiNhanhManagementLocalBean.baoCaoQuanLyPhieuKhuyenMai_qStudent(PromotionEnum.Q_STUDENT_Q_TEEN_2015.getCode(), command.getPojo());
        try{
            SimpleDateFormat df = new SimpleDateFormat("dd-M-yyyy");
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            String ngayXuatBaoCao = df.format(currentTimestamp);
            String outputFileName = "/files/temp/quanlyphieukhuyenmai_" + ngayXuatBaoCao + ".xls";
            String reportTemplate = request.getSession().getServletContext().getRealPath("/files/temp/export/quanlyphieukhuyenmai.xls");
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

            WritableCellFormat chiNhanhNormalStringCellFormat = new WritableCellFormat(boldFont);
            chiNhanhNormalStringCellFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.MEDIUM);

            WritableCellFormat cuaHangStringCellFormat = new WritableCellFormat(normalFont);
            cuaHangStringCellFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.MEDIUM);

            WritableCellFormat integerCellFormat = new WritableCellFormat(normalFont);
            integerCellFormat.setBorder(jxl.format.Border.ALL, BorderLineStyle.MEDIUM);
            integerCellFormat.setAlignment(Alignment.RIGHT);

            WritableCellFormat titleCellFormat = new WritableCellFormat(normalFont);
            titleCellFormat.setAlignment(Alignment.LEFT);

            WritableCellFormat summaryIntegerCellFormat = new WritableCellFormat(boldFont);
            summaryIntegerCellFormat.setBorder(jxl.format.Border.ALL, BorderLineStyle.MEDIUM);
            summaryIntegerCellFormat.setAlignment(Alignment.RIGHT);

            WritableCellFormat summaryStringCellFormat = new WritableCellFormat(boldFont);
            summaryStringCellFormat.setBorder(jxl.format.Border.ALL, BorderLineStyle.MEDIUM);

            Integer totalSoLuongNhap = 0;
            Integer totalSoPhieuDaDoi = 0;
            Integer totalTon = 0;

            // for title
            CellValue[] title1ResValue = new CellValue[TOTAL_COLUMN_EXPORT];
            String chiNhanhName = "";
            if(command.getPojo().getBranch() != null && command.getPojo().getBranch().getBranch_Id() != null){
                BranchDTO branchDTO = this.branchManagementLocalBean.findByChiNhanhIdAndCTCode(command.getPojo().getBranch().getBranch_Id(), PromotionEnum.Q_STUDENT_Q_TEEN_2015.getCode());
                chiNhanhName = branchDTO.getBranch_name();
            }
            String cuaHangName = "";
            if(command.getPojo().getDepartmentId() != null){
                DepartmentDTO departmentDTO = this.departmentManagementLocalBean.findById(command.getPojo().getDepartmentId());
                cuaHangName = departmentDTO.getName();
            }
            title1ResValue[0] = new CellValue(CellDataType.STRING, this.getMessageSourceAccessor().getMessage("label.chi_nhanh") + ": " + chiNhanhName);
            title1ResValue[1] = new CellValue();
            title1ResValue[2] = new CellValue(CellDataType.STRING, this.getMessageSourceAccessor().getMessage("label.ten_cua_hang") + ": " + cuaHangName);
            ExcelUtil.addRow(sheet, 4, title1ResValue, titleCellFormat, integerCellFormat, null, null);

            CellValue[] title2ResValue = new CellValue[TOTAL_COLUMN_EXPORT];
            title2ResValue[0] = new CellValue(CellDataType.STRING, this.getMessageSourceAccessor().getMessage("label.ngay_xuat_bao_cao") + ": " + df.format(new Date()));
            title2ResValue[1] = new CellValue();
            title2ResValue[2] = new CellValue();
            title2ResValue[3] = new CellValue();
            ExcelUtil.addRow(sheet, 5, title2ResValue, titleCellFormat, null, null, null);

            if(dtoList.size() > 0){
                for(BranchDTO dto : dtoList){
                    CellValue[] chiNhanhResValue = addCellValues( dto);
                    ExcelUtil.addRow(sheet, startRow++, chiNhanhResValue, chiNhanhNormalStringCellFormat, integerCellFormat, null, null);
                    if(dto.getDepartment() != null && dto.getDepartment().size() > 0){
                        for(DepartmentDTO departmentDTO : dto.getDepartment()){
                            CellValue[] cuaHangResValue = addCellValues(departmentDTO);
                            totalSoPhieuDaDoi += Integer.valueOf(cuaHangResValue[2].getValue().toString());
                            totalTon += Integer.valueOf(cuaHangResValue[3].getValue().toString());
                            ExcelUtil.addRow(sheet, startRow++, cuaHangResValue, cuaHangStringCellFormat, integerCellFormat, null, null);
                        }
                    }
                }

                // for summary row
                CellValue[] summaryResValue = new CellValue[TOTAL_COLUMN_EXPORT];
                summaryResValue[0] = new CellValue(CellDataType.STRING, this.getMessageSourceAccessor().getMessage("label.tong"));
                summaryResValue[1] = new CellValue();
                summaryResValue[2] = new CellValue(CellDataType.INT, totalSoPhieuDaDoi);
                summaryResValue[3] = new CellValue(CellDataType.INT, totalTon);
                ExcelUtil.addRow(sheet, startRow++, summaryResValue, summaryStringCellFormat, summaryIntegerCellFormat, null, null);

                workbook.write();
                workbook.close();
                response.sendRedirect(request.getSession().getServletContext().getContextPath() + outputFileName);
            }
        }catch (Exception e){
            throw new Exception("Error when export data to excel.\n + details: " + e.getMessage());
        }
    }

    /**
     * Write branch name to report cells.
     * @param dataRow
     * @return
     */
    private CellValue[] addCellValues(BranchDTO dataRow){
        CellValue[] resValue = new CellValue[TOTAL_COLUMN_EXPORT];
        int columnIndex = 0;
        resValue[columnIndex++] = new CellValue(CellDataType.STRING, dataRow.getBranch_name());
        resValue[columnIndex++] = new CellValue();
        resValue[columnIndex++] = new CellValue();
        resValue[columnIndex++] = new CellValue();
        return resValue;
    }

    /**
     * Write department info to report cells.
     * @param dataRow
     * @return
     */
    private CellValue[] addCellValues(DepartmentDTO dataRow){
        CellValue[] resValue = new CellValue[TOTAL_COLUMN_EXPORT];
        int columnIndex = 0;
        resValue[columnIndex++] = new CellValue(CellDataType.STRING, "     " + dataRow.getName());
        resValue[columnIndex++] = new CellValue(CellDataType.STRING, dataRow.getGift().getName());
        resValue[columnIndex++] = new CellValue(CellDataType.INT, dataRow.getSoLuongPhieuDaDoi());
        resValue[columnIndex++] = new CellValue(CellDataType.INT, dataRow.getTon());
        return resValue;
    }
}
