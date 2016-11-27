package com.benluck.vms.mobifonedataseller.webapp.controller;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.utils.DateUtil;
import com.benluck.vms.mobifonedataseller.core.business.*;
import com.benluck.vms.mobifonedataseller.core.dto.OrderDTO;
import com.benluck.vms.mobifonedataseller.core.dto.OrderDataCodeDTO;
import com.benluck.vms.mobifonedataseller.core.dto.PackageDataDTO;
import com.benluck.vms.mobifonedataseller.core.dto.UserDTO;
import com.benluck.vms.mobifonedataseller.editor.CustomCurrencyFormatEditor;
import com.benluck.vms.mobifonedataseller.editor.CustomDateEditor;
import com.benluck.vms.mobifonedataseller.security.util.SHA256Util;
import com.benluck.vms.mobifonedataseller.security.util.SecurityUtils;
import com.benluck.vms.mobifonedataseller.util.ExcelUtil;
import com.benluck.vms.mobifonedataseller.util.RedisUtil;
import com.benluck.vms.mobifonedataseller.util.RequestUtil;
import com.benluck.vms.mobifonedataseller.utils.MobiFoneSecurityBase64Util;
import com.benluck.vms.mobifonedataseller.webapp.command.OrderCommand;
import com.benluck.vms.mobifonedataseller.webapp.dto.CellDataType;
import com.benluck.vms.mobifonedataseller.webapp.dto.CellValue;
import com.benluck.vms.mobifonedataseller.webapp.exception.ForBiddenException;
import com.benluck.vms.mobifonedataseller.webapp.task.TaskTakeCardCode;
import com.benluck.vms.mobifonedataseller.webapp.validator.OrderValidator;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.lang.Boolean;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by thaihoang on 10/31/2016.
 */

@Controller
public class OrderController extends ApplicationObjectSupport{

    private Logger logger = Logger.getLogger(OrderController.class);

    private static final Integer TOTAL_COLUMN_EXPORT = 9;

    @Autowired
    private OrderManagementLocalBean orderService;
    @Autowired
    private PackageDataManagementLocalBean packageDataService;
    @Autowired
    private KHDNManagementLocalBean KHDNService;
    @Autowired
    private OrderDataCodeManagementLocalBean orderDataCodeService;
    @Autowired
    private CodeHistoryManagementLocalBean codeHistoryService;
    @Autowired
    private OrderValidator validator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor("dd/MM/yyyy"));
        binder.registerCustomEditor(Double.class, new CustomCurrencyFormatEditor());
        binder.registerCustomEditor(Integer.class, new CustomCurrencyFormatEditor());
    }

    @RequestMapping(value = {"/admin/order/list.html", "/user/order/list.html", "/khdn/order/list.html", "/custom_user/order/list.html"} )
    public ModelAndView list(@ModelAttribute(Constants.FORM_MODEL_KEY)OrderCommand command,
                             HttpServletRequest request,
                             HttpServletResponse response,
                             RedirectAttributes redirectAttributes){

        if(!SecurityUtils.userHasAuthority(Constants.USERGROUP_ADMIN) && !SecurityUtils.userHasAuthority(Constants.USERGROUP_VMS_USER) && !SecurityUtils.userHasAuthority(Constants.USERGROUP_KHDN) && !SecurityUtils.userHasAuthority(Constants.ORDER_MANAGER)){
            logger.warn("User: " + SecurityUtils.getPrincipal().getDisplayName() + ", userId: " + SecurityUtils.getLoginUserId() + " is trying to access non-authorized page: " + "/order/list.html page. ACCESS DENIED FOR BIDDEN!");
            throw new ForBiddenException();
        }

        ModelAndView mav = new ModelAndView("/admin/order/list");
        String action = command.getCrudaction();

        if (StringUtils.isNotBlank(action)){
            if(action.equals("delete")){
                if(command.getPojo().getOrderId() != null){
                    try{
                        if(!SecurityUtils.userHasAuthority(Constants.USERGROUP_ADMIN) && !SecurityUtils.userHasAuthority(Constants.USERGROUP_VMS_USER)){
                            redirectAttributes.addFlashAttribute(Constants.ALERT_TYPE, "danger");
                            redirectAttributes.addFlashAttribute(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("order.only_vms_user_can_delete_order"));
                        }else{
                            this.orderService.deleteItem(command.getPojo().getOrderId(), SecurityUtils.getLoginUserId());
                            redirectAttributes.addFlashAttribute(Constants.ALERT_TYPE, "success");
                            redirectAttributes.addFlashAttribute(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("order.delete_successfully"));
                        }

                        if(SecurityUtils.userHasAuthority(Constants.USERGROUP_ADMIN)){
                            return new ModelAndView("redirect:/admin/order/list.html");
                        }else if(SecurityUtils.userHasAuthority(Constants.USERGROUP_VMS_USER)){
                            return new ModelAndView("redirect:/user/order/list.html");
                        }else if(SecurityUtils.userHasAuthority(Constants.USERGROUP_VMS_USER)){
                            return new ModelAndView("redirect:/khdn/order/list.html");
                        }if(SecurityUtils.userHasAuthority(Constants.USERGROUP_VMS_USER)){
                            return new ModelAndView("redirect:/custom_user/order/list.html");
                        }
                    }catch (Exception e){
                        mav.addObject(Constants.ALERT_TYPE, "info");
                        mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("order.can_not_delete"));
                    }
                }
            }else if (action.equals(Constants.ACTION_SEARCH)){
                executeSearch(command, request);
            }else if(action.equals(Constants.ACTION_EXPORT)){
                try{
                    exportOrder2Excel(command, request, response);
                }catch (Exception e){
                    e.printStackTrace();
                    mav.addObject(Constants.ALERT_TYPE, "danger");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("order.export_failed"));
                    logger.error(e.getMessage());
                }
            }
        }

        preferenceData(mav, command);

        mav.addObject(Constants.LIST_MODEL_KEY, command);
        return mav;
    }

    private void exportOrder2Excel(OrderCommand command, HttpServletRequest request, HttpServletResponse response) throws Exception{
        SimpleDateFormat df = new SimpleDateFormat("dd-M-yyyy");
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        String exportDate = df.format(currentTimestamp);

        List<OrderDataCodeDTO> dtoList = this.orderDataCodeService.fetchByOrderId(command.getPojo().getOrderId());

        if(dtoList.size() == 0){
            logger.error("Error happened when fetching Data Code by OrderId: " + command.getPojo().getOrderId());
            throw new Exception("Error happened when fetching Data Code by OrderId: " + command.getPojo().getOrderId());
        }

        String reportTemplate = request.getSession().getServletContext().getRealPath("/files/temp/export/don_hang.xls");
        String outputFileName = "/files/temp/export/don_hang_" + exportDate + ".xls";
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

        boolean adminExport4MOBIFONE = false;

        if((SecurityUtils.userHasAuthority(Constants.USERGROUP_ADMIN) || SecurityUtils.userHasAuthority(Constants.USERGROUP_VMS_USER)) && command.getExportOptionType().equals(Constants.ADMIN_EXPORT_4_MOBIFONE)){
            adminExport4MOBIFONE = true;
        }

        if(dtoList.size() > 0){
            int indexRow = 1;
            for(OrderDataCodeDTO dto : dtoList){
                CellValue[] resValue = addCellValues(dto, indexRow, adminExport4MOBIFONE);
                ExcelUtil.addRow(sheet, startRow++, resValue, stringCellFormat, integerCellFormat, doubleCellFormat, null);
                indexRow++;
            }
            workbook.write();
            workbook.close();
            response.sendRedirect(request.getSession().getServletContext().getContextPath() + outputFileName);
        }
    }
    private CellValue[] addCellValues(OrderDataCodeDTO dto, int indexRow, boolean adminExport4MOBIFONE){
        SimpleDateFormat df = new SimpleDateFormat("dd-M-yyyy");
        CellValue[] resValue = new CellValue[TOTAL_COLUMN_EXPORT];
        int columnIndex = 0;
        resValue[columnIndex++] = new CellValue(CellDataType.INT, indexRow);
        resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getSerial().toString());

        if(adminExport4MOBIFONE){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, SHA256Util.hash(MobiFoneSecurityBase64Util.decode(dto.getDataCode().toString())));
        }else{
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getDataCode().toString());
        }
        resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, Double.valueOf(dto.getOrder().getPackageData().getValue()));
        resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getOrder().getPackageData().getVolume());
        resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getOrder().getPackageData().getDuration());
        resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getOrder().getPackageData().getName());
        resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getOrder().getKhdn().getMst());
        resValue[columnIndex++] = new CellValue(CellDataType.STRING, df.format(dto.getExpiredDate()));
        return resValue;
    }

    private void executeSearch(OrderCommand command, HttpServletRequest request){
        OrderDTO pojo = command.getPojo();

        RequestUtil.initSearchBean(request, command);
        Map<String, Object> properties = new HashMap<String, Object>();

        if(pojo.getKhdn() != null && pojo.getKhdn().getKHDNId() != null){
            properties.put("khdn.KHDNId", pojo.getKhdn().getKHDNId());
        }
        if(pojo.getPackageData() != null && pojo.getPackageData().getPackageDataId() != null){
            properties.put("packageData.packageDataId", pojo.getPackageData().getPackageDataId());
        }

        if(!SecurityUtils.userHasAuthority(Constants.USERGROUP_ADMIN) && !SecurityUtils.userHasAuthority(Constants.USERGROUP_VMS_USER)){
            properties.put("khdn.stb_vas", SecurityUtils.getPrincipal().getIsdn() != null ? SecurityUtils.getPrincipal().getIsdn() : "-1");
        }

        StringBuilder whereClause = new StringBuilder("A.activeStatus = " + Constants.ORDER_ACTIVE_STATUS_ALIVE);

        Object[] resultObject = this.orderService.searchByProperties(properties, command.getSortExpression(), command.getSortDirection(), command.getFirstItem(), command.getReportMaxPageItems(), whereClause.toString());
        command.setTotalItems(Integer.valueOf(resultObject[0].toString()));
        command.setListResult((List<OrderDTO>)resultObject[1]);
        command.setMaxPageItems(command.getReportMaxPageItems());
    }

    private void preferenceData(ModelAndView mav, OrderCommand command){
        mav.addObject("packageDataList", packageDataService.findAll());
        mav.addObject("KHDNList", KHDNService.findAll());
        mav.addObject("packageDataIdListHasGeneratedCardCode", this.packageDataService.findPackageDataIdListHasGeneratedCardCode(Calendar.getInstance().get(Calendar.YEAR)));
        mav.addObject("hasImportedUsedCardCode", RedisUtil.getRedisValueByKey(Constants.IMPORTED_CARD_CODE_REDIS_KEY_AND_HASKEY, Constants.IMPORTED_CARD_CODE_REDIS_KEY_AND_HASKEY));

        if(command.getPojo() != null && command.getPojo().getOrderId() != null ){
            mav.addObject("totalRemainingPaidPackageValue", this.codeHistoryService.calculateTotalPaidPackageValue(command.getPojo().getKhdn().getStb_vas()));
        }else{
            mav.addObject("totalRemainingPaidPackageValue", 0D);
        }
    }

    @RequestMapping(value = {"/admin/order/add.html", "/user/order/add.html",
                            "/admin/order/edit.html", "/user/order/edit.html"})
    public ModelAndView updateOrCreateOrder(@ModelAttribute(Constants.FORM_MODEL_KEY)OrderCommand command,
                                            BindingResult bindingResult,
                                            RedirectAttributes redirectAttributes){

        if(!SecurityUtils.userHasAuthority(Constants.USERGROUP_ADMIN) && !SecurityUtils.userHasAuthority(Constants.USERGROUP_VMS_USER)){
            logger.warn("User: " + SecurityUtils.getPrincipal().getDisplayName() + ", userId: " + SecurityUtils.getLoginUserId() + " is trying to access non-authorized page: " + "/order/add.html or /order/edit.html page. ACCESS DENIED FOR BIDDEN!");
            throw new ForBiddenException();
        }

        Boolean hasImportedUsedCardCode = (Boolean)RedisUtil.getRedisValueByKey(Constants.IMPORTED_CARD_CODE_REDIS_KEY_AND_HASKEY, Constants.IMPORTED_CARD_CODE_REDIS_KEY_AND_HASKEY);
        if(hasImportedUsedCardCode == null || !hasImportedUsedCardCode.booleanValue()){
            logger.warn("Please import Used Card Code list before using this feature.");
            if(SecurityUtils.userHasAuthority(Constants.USERGROUP_ADMIN)){
                return new ModelAndView("redirect:/admin/order/list.html");
            }else{
                return new ModelAndView("redirect:/user/order/list.html");
            }
        }

        ModelAndView mav = new ModelAndView("/admin/order/edit");
        String crudaction = command.getCrudaction();

        OrderDTO pojo = command.getPojo();
        try{
            if (StringUtils.isNotBlank(crudaction)){
                if(crudaction.equals("insert-update")){
                    validator.validate(command, bindingResult);
                    convertDate2Timestamp(command);
                    if (!bindingResult.hasErrors()){
                        UserDTO updatedBy = new UserDTO();
                        updatedBy.setUserId(SecurityUtils.getLoginUserId());
                        pojo.setCreatedBy(updatedBy);

                        PackageDataDTO packageDataDTO = this.packageDataService.findById(pojo.getPackageData().getPackageDataId());
                        if(packageDataDTO.getUnitPrice4CardCode().length() > 2){
                            mav.addObject(Constants.ALERT_TYPE, "info");
                            mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("order.only_support_unit_price_2_digit"));
                        }else{

                            if (pojo.getOrderId() == null ){
                                pojo = this.orderService.addItem(pojo);

                                startTaskTakingCardCode(pojo.getOrderId(), packageDataDTO.getUnitPrice4CardCode());

                                redirectAttributes.addFlashAttribute(Constants.ALERT_TYPE, "success");
                                redirectAttributes.addFlashAttribute("messageResponse", this.getMessageSourceAccessor().getMessage("database.add.successful"));
                            } else {
                                OrderDTO originOrderDTO = this.orderService.findById(command.getPojo().getOrderId());

                                if(originOrderDTO.getOrderStatus().equals(Constants.ORDER_STATUS_PROCESSING)
                                        && pojo.getOrderStatus().equals(Constants.ORDER_STATUS_FINISH)){
                                    pojo.setCardCodeProcessStatus(Constants.ORDER_CARD_CODE_PROCESSING_STATUS);
                                }

                                this.orderService.updateItem(pojo);

                                // Update Card Code size in DB nd Cache
                                if(originOrderDTO.getOrderStatus().equals(Constants.ORDER_STATUS_PROCESSING)
                                        && pojo.getOrderStatus().equals(Constants.ORDER_STATUS_FINISH)){
                                    startTaskTakingCardCode(pojo.getOrderId(), packageDataDTO.getUnitPrice4CardCode());
                                }
                                redirectAttributes.addFlashAttribute(Constants.ALERT_TYPE, "success");
                                redirectAttributes.addFlashAttribute("messageResponse", this.getMessageSourceAccessor().getMessage("database.update.successful"));
                            }

                            if(SecurityUtils.userHasAuthority(Constants.USERGROUP_ADMIN)){
                                return new ModelAndView("redirect:/admin/order/list.html");
                            }else{
                                return new ModelAndView("redirect:/user/order/list.html");
                            }
                        }
                    }
                }
            }else if(pojo.getOrderId() != null){
                OrderDTO originOrderDTO = this.orderService.findById(command.getPojo().getOrderId());

                if(originOrderDTO.getOrderStatus().equals(Constants.ORDER_STATUS_FINISH)){
                    redirectAttributes.addFlashAttribute(Constants.ALERT_TYPE, "danger");
                    redirectAttributes.addFlashAttribute(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("order.not_allow_update_finish_order"));
                    if(SecurityUtils.userHasAuthority(Constants.USERGROUP_ADMIN)){
                        return new ModelAndView("redirect:/admin/order/list.html");
                    }else{
                        return new ModelAndView("redirect:/user/order/list.html");
                    }
                }
                command.setPojo(originOrderDTO);
            }
        }catch (Exception e){
            logger.error("Error when add or update Order. \nDetails: " + e.getMessage());
            mav.addObject(Constants.ALERT_TYPE, "danger");
            mav.addObject("messageResponse", this.getMessageSourceAccessor().getMessage("database.exception.duplicated_id"));
        }

        preferenceData(mav, command);
        return mav;
    }

    private void startTaskTakingCardCode(Long orderId, String unitPriceCode){
        TaskTakeCardCode taskTakeCardCode = new TaskTakeCardCode(SecurityUtils.getLoginUserId(), orderId, unitPriceCode);
        Timer timer = new Timer(true);
        timer.schedule(taskTakeCardCode, 0);
    }

    /**
     * Copy Date value and format to Timestamp.
     * @param command
     */
    private void convertDate2Timestamp(OrderCommand command){
        if(command.getIssuedDate() != null){
            command.getPojo().setIssuedDate(DateUtil.dateToTimestamp(command.getIssuedDate(), Constants.VI_DATE_FORMAT));
        }
        if(command.getShippingDate() != null){
            command.getPojo().setShippingDate(DateUtil.dateToTimestamp(command.getShippingDate(), Constants.VI_DATE_FORMAT));
        }
    }
}
