package com.benluck.vms.mobifonedataseller.webapp.controller;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.core.business.OrderHistoryManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.OrderHistoryDTO;
import com.benluck.vms.mobifonedataseller.util.RequestUtil;
import com.benluck.vms.mobifonedataseller.webapp.command.OrderHistoryCommand;
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
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/3/16
 * Time: 07:13
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class OrderHistoryController extends ApplicationObjectSupport{
    private Logger logger = Logger.getLogger(OrderHistoryController.class);

    @Autowired
    private OrderHistoryManagementLocalBean orderHistoryService;

    @RequestMapping(value = {"/admin/orderhistory/list.html", "/user/orderhistory/list.html"})
    public ModelAndView history(@ModelAttribute(Constants.FORM_MODEL_KEY)OrderHistoryCommand command,
                                BindingResult bindingResult,
                                HttpServletRequest request){
        ModelAndView mav = new ModelAndView("/admin/orderhistory/list");

        executeSearch(command, request);
        mav.addObject(Constants.LIST_MODEL_KEY, command);
        return mav;
    }

    private void executeSearch(OrderHistoryCommand command, HttpServletRequest request){
        RequestUtil.initSearchBean(request, command);

        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("order.orderId", command.getPojo().getOrder().getOrderId());

        Object[] objectResult = this.orderHistoryService.searchByProperties(properties, command.getSortExpression(), command.getSortDirection(), command.getFirstItem(), command.getMaxPageItems());
        command.setTotalItems(Integer.valueOf(objectResult[0].toString()));
        command.setListResult((List<OrderHistoryDTO>)objectResult[1]);
    }
}
