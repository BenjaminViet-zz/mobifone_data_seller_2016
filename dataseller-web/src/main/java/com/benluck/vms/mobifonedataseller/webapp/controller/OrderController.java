package com.benluck.vms.mobifonedataseller.webapp.controller;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.utils.DateUtil;
import com.benluck.vms.mobifonedataseller.core.business.*;
import com.benluck.vms.mobifonedataseller.core.dto.OrderDTO;
import com.benluck.vms.mobifonedataseller.core.dto.OrderDataCodeDTO;
import com.benluck.vms.mobifonedataseller.core.dto.PackageDataCodeGenDTO;
import com.benluck.vms.mobifonedataseller.core.dto.UserDTO;
import com.benluck.vms.mobifonedataseller.dataCodeGenerator.DataCodeUtil;
import com.benluck.vms.mobifonedataseller.editor.CustomCurrencyFormatEditor;
import com.benluck.vms.mobifonedataseller.editor.CustomDateEditor;
import com.benluck.vms.mobifonedataseller.redis.domain.DataCode;
import com.benluck.vms.mobifonedataseller.redis.service.DataCodeService;
import com.benluck.vms.mobifonedataseller.security.util.SecurityUtils;
import com.benluck.vms.mobifonedataseller.util.ExcelUtil;
import com.benluck.vms.mobifonedataseller.util.RequestUtil;
import com.benluck.vms.mobifonedataseller.webapp.command.OrderCommand;
import com.benluck.vms.mobifonedataseller.webapp.dto.CellDataType;
import com.benluck.vms.mobifonedataseller.webapp.dto.CellValue;
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

import javax.ejb.DuplicateKeyException;
import javax.ejb.ObjectNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
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
    private PackageDataCodeGenManagementLocalBean packageDataCodeGenService;
    @Autowired
    private OrderValidator validator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor("dd/MM/yyyy"));
        binder.registerCustomEditor(Double.class, new CustomCurrencyFormatEditor());
        binder.registerCustomEditor(Integer.class, new CustomCurrencyFormatEditor());
    }

    @RequestMapping(value = {"/admin/order/list.html", "/user/order/list.html"} )
    public ModelAndView list(@ModelAttribute(Constants.FORM_MODEL_KEY)OrderCommand command,
                             HttpServletRequest request,
                             HttpServletResponse response){
        ModelAndView mav = new ModelAndView("/admin/order/list");
        String action = command.getCrudaction();

        if (StringUtils.isNotBlank(action)){
            if(action.equals("delete")){
                if(command.getPojo().getOrderId() != null){
                    try{
                        this.orderService.deleteItem(command.getPojo().getOrderId(), SecurityUtils.getLoginUserId());
                        mav.addObject(Constants.ALERT_TYPE, "success");
                        mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("order.can_not_delete"));
                    }catch (Exception e){
                        mav.addObject(Constants.ALERT_TYPE, "info");
                        mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("order.delete_successfully"));
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

        preferenceData(mav);

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


        if(dtoList.size() > 0){
            int indexRow = 1;
            for(OrderDataCodeDTO dto : dtoList){
                CellValue[] resValue = addCellValues(dto, indexRow);
                ExcelUtil.addRow(sheet, startRow++, resValue, stringCellFormat, integerCellFormat, doubleCellFormat, null);
                indexRow++;
            }
            workbook.write();
            workbook.close();
            response.sendRedirect(request.getSession().getServletContext().getContextPath() + outputFileName);
        }
    }
    private CellValue[] addCellValues(OrderDataCodeDTO dto, int indexRow){
        SimpleDateFormat df = new SimpleDateFormat("dd-M-yyyy");
        CellValue[] resValue = new CellValue[TOTAL_COLUMN_EXPORT];
        int columnIndex = 0;
        resValue[columnIndex++] = new CellValue(CellDataType.INT, indexRow);
        resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getSerial().toString());
        resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getDataCode().toString());
        resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getOrder().getPackageData().getValue());
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

        StringBuilder whereClause = new StringBuilder("A.activeStatus = " + Constants.ORDER_ACTIVE_STATUS_ALIVE);

        Object[] resultObject = this.orderService.searchByProperties(properties, command.getSortExpression(), command.getSortDirection(), command.getFirstItem(), command.getReportMaxPageItems(), whereClause.toString());
        command.setTotalItems(Integer.valueOf(resultObject[0].toString()));
        command.setListResult((List<OrderDTO>)resultObject[1]);
        command.setMaxPageItems(command.getReportMaxPageItems());
    }

    private void preferenceData(ModelAndView mav){
        mav.addObject("packageDataList", packageDataService.findAll());
        mav.addObject("KHDNList", KHDNService.findAll());
    }

    @RequestMapping(value = {"/admin/order/add.html", "/user/order/add.html",
                            "/admin/order/edit.html", "/user/order/edit.html"})
    public ModelAndView updateOrCreateOrder(@ModelAttribute(Constants.FORM_MODEL_KEY)OrderCommand command,
                                            BindingResult bindingResult,
                                            RedirectAttributes redirectAttributes){
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

                        Calendar current = Calendar.getInstance();
                        String yearCode = String.valueOf(current.get(Calendar.YEAR)).replace("0","");
                        String unitPriceCode = String.valueOf(pojo.getUnitPrice()/1000).replaceAll("\\.\\d*", "");

                        if(unitPriceCode.length() > 2){
                            mav.addObject(Constants.ALERT_TYPE, "info");
                            mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("order.only_support_unit_price_2_digit"));
                        }else{

                            PackageDataCodeGenDTO packageDataCodeGenDTO = this.packageDataCodeGenService.findByUniqueCompositeKey(pojo.getPackageData().getPackageDataId(), Calendar.getInstance().get(Calendar.YEAR));
                            Object[] cardCodeHSGenerationObject = null;

                            if (pojo.getOrderId() == null ){

                                // Take Card Code from Cache
                                if(pojo.getOrderStatus().equals(Constants.ORDER_STATUS_FINISH)){
                                    cardCodeHSGenerationObject = DataCodeUtil.generateDataCodes(packageDataCodeGenDTO, Calendar.getInstance().get(Calendar.YEAR), yearCode, unitPriceCode, pojo.getQuantity());
                                }

                                if(Integer.valueOf(cardCodeHSGenerationObject[0].toString()) != pojo.getQuantity()){
                                    throw new Exception("Error when taking Card Code List from Cache. Details: Not matching request size and generated size. ");
                                }

                                pojo.setMapBatchIndexAndCardCodeHSRemaining((Map<String, HashSet<String>>)cardCodeHSGenerationObject[1]);

                                this.orderService.addItem(pojo);

                                // Update Card Code size in DB nd Cachce
                                if(pojo.getOrderStatus().equals(Constants.ORDER_STATUS_FINISH)){
                                    DataCodeUtil.updateRemainingCardCodeSize(packageDataCodeGenDTO.getPackageDataCodeGenId(), yearCode, pojo.getMapBatchIndexAndCardCodeHSRemaining());
                                }

                                redirectAttributes.addFlashAttribute(Constants.ALERT_TYPE, "success");
                                redirectAttributes.addFlashAttribute("messageResponse", this.getMessageSourceAccessor().getMessage("database.add.successful"));
                            } else {
                                OrderDTO originOrderDTO = this.orderService.findById(command.getPojo().getOrderId());

                                // Take Card Code from Cache
                                if(originOrderDTO.getOrderStatus().equals(Constants.ORDER_STATUS_PROCESSING)
                                        && pojo.getOrderStatus().equals(Constants.ORDER_STATUS_FINISH)){
                                    cardCodeHSGenerationObject = DataCodeUtil.generateDataCodes(packageDataCodeGenDTO, Calendar.getInstance().get(Calendar.YEAR), yearCode, unitPriceCode, pojo.getQuantity());
                                }

                                if(Integer.valueOf(cardCodeHSGenerationObject[0].toString()) != pojo.getQuantity()){
                                    throw new Exception("Error when taking Card Code List from Cache. Details: Not matching request size and generated size. ");
                                }

                                pojo.setMapBatchIndexAndCardCodeHSRemaining((Map<String, HashSet<String>>)cardCodeHSGenerationObject[1]);

                                this.orderService.updateItem(pojo);

                                // Update Card Code size in DB nd Cachce
                                if(originOrderDTO.getOrderStatus().equals(Constants.ORDER_STATUS_PROCESSING)
                                        && pojo.getOrderStatus().equals(Constants.ORDER_STATUS_FINISH)){
                                    DataCodeUtil.updateRemainingCardCodeSize(packageDataCodeGenDTO.getPackageDataCodeGenId(), yearCode, pojo.getMapBatchIndexAndCardCodeHSRemaining());
                                }
                                redirectAttributes.addFlashAttribute(Constants.ALERT_TYPE, "success");
                                redirectAttributes.addFlashAttribute("messageResponse", this.getMessageSourceAccessor().getMessage("database.update.successful"));
                            }
                            return new ModelAndView("redirect:/admin/order/list.html");
                        }
                    }
                }
            }else if(pojo.getOrderId() != null){
                command.setPojo(this.orderService.findById(command.getPojo().getOrderId()));
            }
        }catch (Exception e){
            logger.error("Error when add or update OrderId: " + e.getMessage());
            mav.addObject(Constants.ALERT_TYPE, "danger");
            mav.addObject("messageResponse", this.getMessageSourceAccessor().getMessage("database.exception.duplicated_id"));
        }

        preferenceData(mav);
        mav.addObject("remainingBalance", calculateRemainingBalance());
        return mav;
    }

    private Double calculateRemainingBalance(){
        return new Double(100);
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
