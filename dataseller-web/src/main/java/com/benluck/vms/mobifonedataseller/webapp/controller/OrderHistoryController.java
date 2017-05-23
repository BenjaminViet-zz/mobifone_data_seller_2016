package com.benluck.vms.mobifonedataseller.webapp.controller;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.core.business.KHDNManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.OrderDataCodeManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.OrderHistoryManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.PackageDataManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.OrderDataCodeDTO;
import com.benluck.vms.mobifonedataseller.core.dto.OrderHistoryDTO;
import com.benluck.vms.mobifonedataseller.security.util.SecurityUtils;
import com.benluck.vms.mobifonedataseller.util.RequestUtil;
import com.benluck.vms.mobifonedataseller.webapp.command.OrderHistoryCommand;
import com.benluck.vms.mobifonedataseller.webapp.exception.ForBiddenException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
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
    @Autowired
    private PackageDataManagementLocalBean packageDataService;
    @Autowired
    private OrderDataCodeManagementLocalBean orderDataCodeService;
    @Autowired
    private KHDNManagementLocalBean KHDNService;

    @RequestMapping(value = {"/admin/orderhistory/list.html", "/user/orderhistory/list.html", "/khdn/orderhistory/list.html"})
    public ModelAndView history(@ModelAttribute(Constants.FORM_MODEL_KEY)OrderHistoryCommand command,
                                BindingResult bindingResult,
                                HttpServletRequest request){
        if(!SecurityUtils.userHasAuthority(Constants.ORDER_MANAGER)){
            logger.warn("User: " + SecurityUtils.getPrincipal().getDisplayName() + ", userId: " + SecurityUtils.getLoginUserId() + " is trying to access non-authorized page: " + "/orderhistory/list.html page. ACCESS DENIED FOR BIDDEN!");
            throw new ForBiddenException();
        }

        ModelAndView mav = new ModelAndView("/admin/orderhistory/list");

        executeSearch(command, request);
        preferenceData(mav);
        fetchCardCodeList(mav, command, request);
        mav.addObject(Constants.LIST_MODEL_KEY, command);
        return mav;
    }

    private void preferenceData(ModelAndView mav){
        mav.addObject("packageDataList", packageDataService.findAll());
        mav.addObject("KHDNList", KHDNService.findAll());
    }

    private void fetchCardCodeList(ModelAndView mav, OrderHistoryCommand command, HttpServletRequest request){
        initSearchCardCodeList(request, command);
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("order.orderId", command.getPojo().getOrder().getOrderId());

        Object[] resultObject = this.orderDataCodeService.findByProperties(properties, command.getSortExpressionCardCode(), command.getSortDirectionCardCode(), command.getFirstCardCodePageItem(), command.getCardCodeMaxPageItems());
        command.setTotalCardCodeItems(Integer.valueOf(resultObject[0].toString()));
        command.setCardCodeList((List<OrderDataCodeDTO>)resultObject[1]);

        mav.addObject("cardCodeItem", command);
    }

    private void initSearchCardCodeList(HttpServletRequest request, OrderHistoryCommand command){
        if (command != null){
            String sortExpression = request.getParameter(new ParamEncoder("tableList2").encodeParameterName(TableTagParameters.PARAMETER_SORT));
            String sortDirection = request.getParameter(new ParamEncoder("tableList2").encodeParameterName(TableTagParameters.PARAMETER_ORDER));
            String sPage = request.getParameter(new ParamEncoder("tableList2").encodeParameterName(TableTagParameters.PARAMETER_PAGE));
            Integer page = 1;
            if (StringUtils.isNotBlank(sPage)) {
                try {
                    page = Integer.valueOf(sPage);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
            command.setCardCodePageIndex(page);
            command.setFirstCardCodePageItem((command.getPage() - 1) * command.getReportMaxPageItems());
            command.setSortExpressionCardCode(sortExpression);
            command.setSortDirectionCardCode(sortDirection);
        }
    }

    private void executeSearch(OrderHistoryCommand command, HttpServletRequest request){
        RequestUtil.initSearchBean(request, command);
        OrderHistoryDTO pojo = command.getPojo();

        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("order.orderId", command.getPojo().getOrder().getOrderId());

        command.setSortExpression("createdDate");
        command.setSortDirection(Constants.SORT_DESC);  // sort DESC

        if(pojo.getKhdn() != null && pojo.getKhdn().getKHDNId() != null){
            properties.put("khdn.KHDNId", pojo.getKhdn().getKHDNId());
        }
        if(pojo.getPackageData() != null && pojo.getPackageData().getPackageDataId() != null){
            properties.put("packageData.packageDataId", pojo.getPackageData().getPackageDataId());
        }

        Object[] objectResult = this.orderHistoryService.searchByProperties(properties, command.getSortExpression(), command.getSortDirection(), command.getFirstItem(), command.getMaxPageItems());
        command.setTotalItems(Integer.valueOf(objectResult[0].toString()));
        command.setListResult((List<OrderHistoryDTO>) objectResult[1]);
    }
}
