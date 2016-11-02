package com.benluck.vms.mobifonedataseller.webapp.controller;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.utils.DateUtil;
import com.benluck.vms.mobifonedataseller.core.business.KHDNManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.OrderManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.PackageDataManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.OrderDTO;
import com.benluck.vms.mobifonedataseller.core.dto.UserDTO;
import com.benluck.vms.mobifonedataseller.editor.CustomDateEditor;
import com.benluck.vms.mobifonedataseller.security.util.SecurityUtils;
import com.benluck.vms.mobifonedataseller.util.RequestUtil;
import com.benluck.vms.mobifonedataseller.webapp.command.OrderCommand;
import com.benluck.vms.mobifonedataseller.webapp.validator.OrderValidator;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by thaihoang on 10/31/2016.
 */

@Controller
public class OrderController extends ApplicationObjectSupport{
    private Logger logger = Logger.getLogger(OrderController.class);

    @Autowired
    private OrderManagementLocalBean orderService;
    @Autowired
    private PackageDataManagementLocalBean packageDataService;
    @Autowired
    private KHDNManagementLocalBean KHDNService;
    @Autowired
    private OrderValidator validator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor("dd/MM/yyyy"));
    }

    @RequestMapping(value = {"/admin/order/list.html", "/user/order/list.html"} )
    public ModelAndView list(@ModelAttribute(Constants.FORM_MODEL_KEY)OrderCommand command,
                             HttpServletRequest request,
                             BindingResult bindingResult){
        ModelAndView mav = new ModelAndView("/admin/order/list");
        executeSearch(command, request);
        preferenceData(mav);
        mav.addObject(Constants.LIST_MODEL_KEY, command);
        return mav;
    }

    private void executeSearch(OrderCommand command, HttpServletRequest request){
        RequestUtil.initSearchBean(request, command);

        OrderDTO pojo = command.getPojo();
        Map<String, Object> properties = new HashMap<String, Object>();
        if(pojo.getKhdn() != null && pojo.getKhdn().getKHDNId() != null){
            properties.put("khdn.KHDNId", pojo.getKhdn().getKHDNId());
        }
        if(pojo.getPackageData() != null && pojo.getPackageData().getPackageDataId() != null){
            properties.put("packageData.packageDataId", pojo.getPackageData().getPackageDataId());
        }

        Object[] resultObject = this.orderService.searchByProperties(properties, command.getSortExpression(), command.getSortDirection(), command.getFirstItem(), command.getReportMaxPageItems());
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

                        if (pojo.getOrderId() == null ){
                            this.orderService.addItem(command.getPojo());
                            redirectAttributes.addFlashAttribute(Constants.ALERT_TYPE, "success");
                            redirectAttributes.addFlashAttribute("messageResponse", this.getMessageSourceAccessor().getMessage("database.add.successful"));
                        } else {
                            this.orderService.updateItem(command.getPojo());
                            redirectAttributes.addFlashAttribute(Constants.ALERT_TYPE, "success");
                            redirectAttributes.addFlashAttribute("messageResponse", this.getMessageSourceAccessor().getMessage("database.update.successful"));
                        }
                        return new ModelAndView("redirect:/admin/order/list.html");
                    }
                }
            }else if(pojo.getOrderId() != null){
                command.setPojo(this.orderService.findById(command.getPojo().getOrderId()));
            }
        }catch (ObjectNotFoundException one){
            logger.error("Can not get data of OrderId: " + pojo.getOrderId());
            mav.addObject(Constants.ALERT_TYPE, "danger");
            mav.addObject("messageResponse", this.getMessageSourceAccessor().getMessage("database.exception.keynotfound"));
        }catch (DuplicateKeyException dle){
            logger.error("Duplicated OrderId: " + pojo.getOrderId());
            mav.addObject(Constants.ALERT_TYPE, "danger");
            mav.addObject("messageResponse", this.getMessageSourceAccessor().getMessage("database.exception.duplicated_id"));
        }

        preferenceData(mav);
        return mav;
    }

    /**
     * Copy Date value and format to Timestamp.
     * @param command
     */
    private void convertDate2Timestamp(OrderCommand command){
        if(command.getIssuedDate() != null){
            command.getPojo().setIssueDate(DateUtil.dateToTimestamp(command.getIssuedDate(), Constants.VI_DATE_FORMAT));
        }
        if(command.getShippingDate() != null){
            command.getPojo().setShippingDate(DateUtil.dateToTimestamp(command.getShippingDate(), Constants.VI_DATE_FORMAT));
        }
    }
}
