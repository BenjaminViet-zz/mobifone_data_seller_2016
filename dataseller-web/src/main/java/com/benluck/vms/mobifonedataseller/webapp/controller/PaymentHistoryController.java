package com.benluck.vms.mobifonedataseller.webapp.controller;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.core.business.KHDNManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.OrderManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.PaymentHistoryManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.PaymentHistoryDTO;
import com.benluck.vms.mobifonedataseller.core.dto.UserDTO;
import com.benluck.vms.mobifonedataseller.editor.CustomCurrencyFormatEditor;
import com.benluck.vms.mobifonedataseller.editor.CustomDateEditor;
import com.benluck.vms.mobifonedataseller.security.util.SecurityUtils;
import com.benluck.vms.mobifonedataseller.util.ExcelExtensionUtil;
import com.benluck.vms.mobifonedataseller.util.RequestUtil;
import com.benluck.vms.mobifonedataseller.util.WebCommonUtil;
import com.benluck.vms.mobifonedataseller.webapp.command.PaymentHistoryCommand;
import com.benluck.vms.mobifonedataseller.webapp.dto.CellValue;
import com.benluck.vms.mobifonedataseller.webapp.exception.ForBiddenException;
import com.benluck.vms.mobifonedataseller.webapp.validator.PaymentHistoryValidator;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
 * Date: 11/15/16
 * Time: 21:14
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class PaymentHistoryController extends ApplicationObjectSupport{
    private Logger logger = Logger.getLogger(PaymentHistoryController.class);
    private final Integer TOTAL_COLUMN_EXPORT = 6;

    @Autowired
    private PaymentHistoryManagementLocalBean paymentHistoryService;
    @Autowired
    private KHDNManagementLocalBean khdnService;
    @Autowired
    private OrderManagementLocalBean orderService;
    @Autowired
    private PaymentHistoryValidator validator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor("dd/MM/yyyy"));
        binder.registerCustomEditor(Double.class, new CustomCurrencyFormatEditor());
        binder.registerCustomEditor(Integer.class, new CustomCurrencyFormatEditor());
    }

    @RequestMapping(value = {"/admin/payment-history/list.html", "/user/payment-history/list.html", "/khdn/payment-history/list.html"})
    public ModelAndView list(@ModelAttribute(value = Constants.FORM_MODEL_KEY) PaymentHistoryCommand command,
                                       HttpServletRequest request,
                                       HttpServletResponse response,
                                       BindingResult bindingResult,
                                       RedirectAttributes redirectAttributes){

        if(!SecurityUtils.userHasAuthority(Constants.PAYMENT_MANAGER)){
            logger.warn("User: " + SecurityUtils.getPrincipal().getDisplayName() + ", userId: " + SecurityUtils.getLoginUserId() + " is trying to access non-authorized page: " + "/payment-history/list.html page. ACCESS DENIED FOR BIDDEN!");
            throw new ForBiddenException();
        }

        ModelAndView mav = new ModelAndView("/admin/paymenthistory/list");
        String action = command.getCrudaction();
        if(StringUtils.isNotBlank(action)){
            if(action.equals(Constants.ACTION_DELETE)){
                validator.validate(command, bindingResult);
                if (!bindingResult.hasErrors() && StringUtils.isBlank(command.getErrorMessage())){
                    try{
                        this.paymentHistoryService.deleteItem(command.getPojo().getPaymentHistoryId());
                        redirectAttributes.addFlashAttribute(Constants.ALERT_TYPE, "success");
                        redirectAttributes.addFlashAttribute(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("database.delete_item_successfully", new Object[]{this.getMessageSourceAccessor().getMessage("payment_history.management.label.payment_history_item")}));
                    }catch (Exception e){
                        logger.error(e.getMessage());
                        redirectAttributes.addFlashAttribute(Constants.ALERT_TYPE, "danger");
                        redirectAttributes.addFlashAttribute(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("import.error_unknown"));
                    }
                    return new ModelAndView(new StringBuilder("redirect:").append(WebCommonUtil.getPrefixUrl()).append("/payment-history/list.html").toString());
                }else if (StringUtils.isNotBlank(command.getErrorMessage())){
                    mav.addObject(Constants.ALERT_TYPE, "danger");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, command.getErrorMessage());
                }
            }
        }

        executeSearch(command, request, mav);
        referenceData(mav, command);
        return mav;
    }

    private void referenceData(ModelAndView mav, PaymentHistoryCommand command){
        mav.addObject("KHDNList", this.khdnService.findAll());

        if (command.getKhdnId() != null){
            mav.addObject("orderList", this.orderService.findListByKHDNIdHasPayment(command.getKhdnId()));
        }else{
            mav.addObject("orderList", this.orderService.findAllHasCreatedPayment());
        }
    }

    private void executeSearch(PaymentHistoryCommand command, HttpServletRequest request, ModelAndView mav){
        RequestUtil.initSearchBean(request, command);

        Map<String, Object> properties = buildProperties(command);

        Object[] resultObject = this.paymentHistoryService.searchByProperties(properties, command.getSortExpression(), command.getSortDirection(), command.getFirstItem(), command.getReportMaxPageItems());
        command.setTotalItems(Integer.valueOf(resultObject[0].toString()));
        command.setListResult((List<PaymentHistoryDTO>)resultObject[1]);
        command.setMaxPageItems(command.getReportMaxPageItems());
        mav.addObject(Constants.LIST_MODEL_KEY, command);
    }

    private Map<String, Object> buildProperties(PaymentHistoryCommand command){
        PaymentHistoryDTO pojo = command.getPojo();
        Map<String, Object> properties = new HashMap<String, Object>();

        if (pojo.getPayment() != null && pojo.getPayment().getPaymentId() != null){
            properties.put("paymentId", pojo.getPayment().getPaymentId());
        }else{
            if (command.getKhdnId() != null){
                properties.put("khdnId", command.getKhdnId());
            }

            if (command.getOrderId() != null){
                properties.put("orderId", command.getOrderId());
            }
        }

        return properties;
    }

    @RequestMapping(value = {"/admin/payment-history/edit.html", "/user/payment-history/edit.html", "/khdn/payment-history/edit.html"})
    public ModelAndView edit(@ModelAttribute(value = Constants.FORM_MODEL_KEY) PaymentHistoryCommand command,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes){

        if(!SecurityUtils.userHasAuthority(Constants.PAYMENT_STATUS_MANAGER)){
            logger.warn("User: " + SecurityUtils.getPrincipal().getDisplayName() + ", userId: " + SecurityUtils.getLoginUserId() + " is trying to access non-authorized page: " + "/payment-history/edit.html page. ACCESS DENIED FOR BIDDEN!");
            throw new ForBiddenException();
        }

        PaymentHistoryDTO pojo = command.getPojo();
        if (pojo.getPaymentHistoryId() == null){
            redirectAttributes.addFlashAttribute(Constants.ALERT_TYPE, "info");
            redirectAttributes.addFlashAttribute(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("payment_history.management.edit_page.msg.not_empty_payment_history_id"));
            return new ModelAndView(new StringBuilder("redirect:").append(WebCommonUtil.getPrefixUrl()).append("/payment-hidtory/list.html").toString());
        }

        ModelAndView mav = new ModelAndView("/admin/paymenthistory/edit");
        String action = command.getCrudaction();
        if(StringUtils.isNotBlank(action)){
            if (action.equals(Constants.ACTION_UPDATE)){
                convertDateUtil2DateSQL(command);
                validator.validate(command, bindingResult);
                if (!bindingResult.hasErrors() && StringUtils.isBlank(command.getErrorMessage())){
                    try{
                        UserDTO modifiedBy = new UserDTO();
                        modifiedBy.setUserId(SecurityUtils.getLoginUserId());
                        command.getPojo().setModifiedBy(modifiedBy);

                        pojo = this.paymentHistoryService.updateItem(command.getPojo());
                        redirectAttributes.addFlashAttribute(Constants.ALERT_TYPE, "success");
                        redirectAttributes.addFlashAttribute(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("database.update_item_successfully", new Object[]{this.getMessageSourceAccessor().getMessage("payment_history.management.label.payment_history_item")}));
                        return new ModelAndView(new StringBuilder("redirect:").append(WebCommonUtil.getPrefixUrl()).append("/payment-history/list.html").append("?pojo.payment.paymentId=").append(pojo.getPayment().getPaymentId()).toString());
                    }catch (Exception e){
                        logger.error(e.getMessage());
                        mav.addObject(Constants.ALERT_TYPE, "danger");
                        mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("import.error_unknown"));
                    }
                }else if (StringUtils.isNotBlank(command.getErrorMessage())){
                    mav.addObject(Constants.ALERT_TYPE, "danger");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, command.getErrorMessage());
                }
            }
        }else{
            try{
                validator.validate(command, bindingResult);
                if (!bindingResult.hasErrors() && StringUtils.isBlank(command.getErrorMessage())){
                    command.setPojo(this.paymentHistoryService.findById(pojo.getPaymentHistoryId()));
                }else if (StringUtils.isNotBlank(command.getErrorMessage())){
                    redirectAttributes.addFlashAttribute(Constants.ALERT_TYPE, "danger");
                    redirectAttributes.addFlashAttribute(Constants.MESSAGE_RESPONSE_MODEL_KEY, command.getErrorMessage());
                    return new ModelAndView(new StringBuilder("redirect:").append(WebCommonUtil.getPrefixUrl()).append("/payment-history/list.html").toString());
                }
            }catch (Exception e){
                logger.error(e.getMessage());
                redirectAttributes.addFlashAttribute(Constants.ALERT_TYPE, "info");
                redirectAttributes.addFlashAttribute(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("import.unknown_error"));
                return new ModelAndView(new StringBuilder("redirect:").append(WebCommonUtil.getPrefixUrl()).append("/payment-hidtory/list.html").toString());
            }
        }

        return mav;
    }

    private void convertDateUtil2DateSQL(PaymentHistoryCommand command){
        if (command.getPaymentDate() != null){
            command.getPojo().setPaymentDate(new java.sql.Date(command.getPaymentDate().getTime()));
        }
    }
}
