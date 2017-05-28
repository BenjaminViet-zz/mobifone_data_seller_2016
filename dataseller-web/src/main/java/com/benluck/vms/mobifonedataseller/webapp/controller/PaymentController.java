package com.benluck.vms.mobifonedataseller.webapp.controller;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.core.business.KHDNManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.OrderManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.PaymentManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.PaymentDTO;
import com.benluck.vms.mobifonedataseller.core.dto.UserDTO;
import com.benluck.vms.mobifonedataseller.editor.CustomCurrencyFormatEditor;
import com.benluck.vms.mobifonedataseller.editor.CustomDateEditor;
import com.benluck.vms.mobifonedataseller.security.util.SecurityUtils;
import com.benluck.vms.mobifonedataseller.util.RequestUtil;
import com.benluck.vms.mobifonedataseller.util.WebCommonUtil;
import com.benluck.vms.mobifonedataseller.webapp.command.PaymentCommand;
import com.benluck.vms.mobifonedataseller.webapp.exception.ForBiddenException;
import com.benluck.vms.mobifonedataseller.webapp.validator.PaymentValidator;
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
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 5/25/17
 * Time: 23:39
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class PaymentController extends ApplicationObjectSupport{
    private Logger logger = Logger.getLogger(PaymentController.class);

    @Autowired
    private PaymentManagementLocalBean paymentService;
    @Autowired
    private KHDNManagementLocalBean khdnService;
    @Autowired
    private OrderManagementLocalBean orderService;
    @Autowired
    private PaymentValidator validator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor("dd/MM/yyyy"));
        binder.registerCustomEditor(Double.class, new CustomCurrencyFormatEditor());
        binder.registerCustomEditor(Integer.class, new CustomCurrencyFormatEditor());
    }

    @RequestMapping(value = {"/admin/payment/list.html", "/user/payment/list.html", "/khdn/payment/list.html"})
    public ModelAndView list(@ModelAttribute(Constants.FORM_MODEL_KEY)PaymentCommand command,
                             BindingResult bindingResult,
                             HttpServletRequest request,
                             RedirectAttributes redirectAttributes){
        if(!SecurityUtils.userHasAuthority(Constants.PAYMENT_MANAGER)){
            logger.warn("User: " + SecurityUtils.getPrincipal().getDisplayName() + ", userId: " + SecurityUtils.getLoginUserId() + " is trying to access non-authorized page: " + "/payment/list.html page. ACCESS DENIED FOR BIDDEN!");
            throw new ForBiddenException();
        }

        ModelAndView mav = new ModelAndView("/admin/payment/list");
        String crudaction = command.getCrudaction();

        if (StringUtils.isNotBlank(crudaction)){
            if (crudaction.equals(Constants.ACTION_DELETE)){
                validator.validate(command, bindingResult);
                if (!bindingResult.hasErrors() && StringUtils.isBlank(command.getErrorMessage())){
                    try{
                        paymentService.deleteItem(command.getPojo().getPaymentId());

                        redirectAttributes.addFlashAttribute(Constants.ALERT_TYPE, "success");
                        redirectAttributes.addFlashAttribute(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("database.delete_item_successfully", new Object[]{this.getMessageSourceAccessor().getMessage("payment.management.label.payment")}));
                    }catch (Exception e){
                        redirectAttributes.addFlashAttribute(Constants.ALERT_TYPE, "danger");
                        redirectAttributes.addFlashAttribute(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("database.delete_item_exception", new Object[]{this.getMessageSourceAccessor().getMessage("payment.management.label.payment")}));
                    }
                    return new ModelAndView(new StringBuilder("redirect:").append(WebCommonUtil.getPrefixUrl()).append("/payment/list.html").toString());
                }else if (StringUtils.isNotBlank(command.getErrorMessage())){
                    redirectAttributes.addFlashAttribute(Constants.ALERT_TYPE, "danger");
                    redirectAttributes.addFlashAttribute(Constants.MESSAGE_RESPONSE_MODEL_KEY, command.getErrorMessage());
                    return new ModelAndView(new StringBuilder("redirect:").append(WebCommonUtil.getPrefixUrl()).append("/payment/list.html").toString());
                }
            }
        }

        executeSearch(mav, request, command);
        referenceData(mav, command);
        return mav;
    }

    private void executeSearch(ModelAndView mav, HttpServletRequest request, PaymentCommand command){
        RequestUtil.initSearchBean(request, command);
        Map<String, Object> properties = buildProperties(command);
        command.setSortExpression("createdDate");
        command.setSortDirection(Constants.SORT_DESC);

        Object[] resultObject = paymentService.searchByProperties(properties, command.getSortExpression(), command.getSortDirection(), command.getFirstItem(), command.getMaxPageItems());
        command.setTotalItems(Integer.valueOf(resultObject[0].toString()));
        command.setListResult((List<PaymentDTO>) resultObject[1]);
        mav.addObject(Constants.LIST_MODEL_KEY, command);
    }

    private Map<String, Object> buildProperties(PaymentCommand command){
        PaymentDTO pojo = command.getPojo();
        Map<String, Object> properties = new HashMap<String, Object>();

        if (pojo.getOrder() != null && pojo.getOrder().getKhdn() != null && pojo.getOrder().getKhdn().getKHDNId() != null){
            properties.put("order.khdn.KHDNId", pojo.getOrder().getKhdn().getKHDNId());
        }

        if (pojo.getStatus() != null && pojo.getStatus().intValue() != -1){
            properties.put("status", pojo.getStatus());
        }

        return properties;
    }

    private void referenceData(ModelAndView mav, PaymentCommand command){
        mav.addObject("KHDNList", this.khdnService.findAll());
    }

    private void convertDate2DateSQL(PaymentCommand command){
        if (command.getPaymentDate() != null){
            command.getPojo().setPaymentDate(new java.sql.Date(command.getPaymentDate().getTime()));
        }
    }

    @RequestMapping(value = {"/admin/payment/add.html", "/admin/payment/edit.html",
                            "/user/payment/add.html", "/user/payment/edit.html",
                            "/khdn/payment/add.html", "/khdn/payment/edit.html"})
    public ModelAndView addOrEdit(@ModelAttribute(Constants.FORM_MODEL_KEY)PaymentCommand command,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes){
        if(!SecurityUtils.userHasAuthority(Constants.PAYMENT_MANAGER)){
            logger.warn("User: " + SecurityUtils.getPrincipal().getDisplayName() + ", userId: " + SecurityUtils.getLoginUserId() + " is trying to access non-authorized page: " + "/payment/add.html or /payment/edit.html page. ACCESS DENIED FOR BIDDEN!");
            throw new ForBiddenException();
        }

        ModelAndView mav = new ModelAndView("/admin/payment/edit");
        String crudaction = command.getCrudaction();
        PaymentDTO pojo = command.getPojo();

        if (pojo.getPaymentId() != null && !SecurityUtils.userHasAuthority(Constants.PAYMENT_STATUS_MANAGER)){
            logger.warn("User: " + SecurityUtils.getPrincipal().getDisplayName() + ", userId: " + SecurityUtils.getLoginUserId() + " is trying to access non-authorized page: " + "/payment/edit.html page. ACCESS DENIED FOR BIDDEN!");
            throw new ForBiddenException();
        }

        try{
            if (StringUtils.isNotBlank(crudaction)){
                if (crudaction.equals("insert-update")){
                    convertDate2DateSQL(command);
                    validator.validate(command, bindingResult);
                    if (!bindingResult.hasErrors() && StringUtils.isBlank(command.getErrorMessage())){
                        UserDTO createdBy = new UserDTO();
                        createdBy.setUserId(SecurityUtils.getLoginUserId());
                        pojo.setCreatedBy(createdBy);

                        if (pojo.getPaymentId() != null){
                            this.paymentService.updateItem(pojo);

                            redirectAttributes.addFlashAttribute(Constants.ALERT_TYPE, "success");
                            redirectAttributes.addFlashAttribute(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("database.update_item_successfully", new Object[]{this.getMessageSourceAccessor().getMessage("payment.management.label.payment")}));
                        }else{
                            this.paymentService.addItem(pojo);
                            redirectAttributes.addFlashAttribute(Constants.ALERT_TYPE, "success");
                            redirectAttributes.addFlashAttribute(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("database.add_item_successfully", new Object[]{this.getMessageSourceAccessor().getMessage("payment.management.label.payment")}));
                        }

                        return new ModelAndView(new StringBuilder("redirect:").append(WebCommonUtil.getPrefixUrl()).append("/payment/list.html").toString());
                    }else if (StringUtils.isNotBlank(command.getErrorMessage())){
                        mav.addObject(Constants.ALERT_TYPE, "danger");
                        mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, command.getErrorMessage());
                    }
                }
            }else{
                if (command.getPojo().getPaymentId() != null){
                    validator.validate(command, bindingResult);
                    if (!bindingResult.hasErrors() && StringUtils.isBlank(command.getErrorMessage())){
                        command.setPojo(this.paymentService.findById(command.getPojo().getPaymentId()));
                    }else if (StringUtils.isNotBlank(command.getErrorMessage())){
                        redirectAttributes.addFlashAttribute(Constants.ALERT_TYPE, "danger");
                        redirectAttributes.addFlashAttribute(Constants.MESSAGE_RESPONSE_MODEL_KEY, command.getErrorMessage());
                        return new ModelAndView(new StringBuilder("redirect:").append(WebCommonUtil.getPrefixUrl()).append("/payment/list.html").toString());
                    }
                }
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            redirectAttributes.addFlashAttribute(Constants.ALERT_TYPE, "danger");
            redirectAttributes.addFlashAttribute(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("import.unknown_error"));

            return new ModelAndView(new StringBuilder("redirect:/").append(WebCommonUtil.getPrefixUrl()).append("/payment/list.html").toString());
        }

        referenceData(mav, command);
        return mav;
    }
}
