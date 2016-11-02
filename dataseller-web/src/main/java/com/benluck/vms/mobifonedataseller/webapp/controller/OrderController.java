package com.benluck.vms.mobifonedataseller.webapp.controller;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.core.business.KHDNManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.OrderManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.PackageDataManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.OrderDTO;
import com.benluck.vms.mobifonedataseller.util.RequestUtil;
import com.benluck.vms.mobifonedataseller.webapp.command.OrderCommand;
import com.benluck.vms.mobifonedataseller.webapp.validator.OrderValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
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

    @RequestMapping(value = {"/admin/order/list.html", "/user/order/list.html"} )
    public ModelAndView list(@ModelAttribute(Constants.FORM_MODEL_KEY)OrderCommand command,
                             HttpServletRequest request,
                             BindingResult bindingResult){
        ModelAndView mav = new ModelAndView("/admin/order/list");
        executeSearch(command, request);
        preferenceData(mav);
        mav.addObject(Constants.FORM_MODEL_KEY, command);
        return mav;
    }

    private void executeSearch(OrderCommand command, HttpServletRequest request){
        RequestUtil.initSearchBean(request, command);

        OrderDTO pojo = command.getPojo();
        Map<String, Object> properties = new HashMap<String, Object>();
        if(pojo.getKhdn() != null && pojo.getKhdn().getGpkd() != null){
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

    @RequestMapping(value = {"/admin/order/add.html", "/user/order/add.html"} )
    public ModelAndView add(){
        return new ModelAndView("/admin/order/add");
    }

    private void preferenceData(ModelAndView mav){
        mav.addObject("packageDataList", packageDataService.findAll());
        mav.addObject("KHDNList", KHDNService.findAll());
    }
}
