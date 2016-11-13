package com.benluck.vms.mobifonedataseller.webapp.controller;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.utils.DateUtil;
import com.benluck.vms.mobifonedataseller.core.business.MBDCostManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.MBDCostInfoDTO;
import com.benluck.vms.mobifonedataseller.editor.CustomDateEditor;
import com.benluck.vms.mobifonedataseller.security.util.SecurityUtils;
import com.benluck.vms.mobifonedataseller.util.RequestUtil;
import com.benluck.vms.mobifonedataseller.webapp.command.MBDCostCommand;
import com.benluck.vms.mobifonedataseller.webapp.exception.ForBiddenException;
import com.benluck.vms.mobifonedataseller.webapp.validator.PaymentManagementValidator;
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

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/12/16
 * Time: 23:49
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class PaymentManagementController extends ApplicationObjectSupport{
    private Logger logger = Logger.getLogger(PaymentManagementController.class);

    @Autowired
    private MBDCostManagementLocalBean costService;
    @Autowired
    private PaymentManagementValidator validator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor("dd/MM/yyyy"));
    }

    @RequestMapping(value = {"/admin/payment/management.html", "/user/payment/management.html"})
    public ModelAndView management(@ModelAttribute(Constants.FORM_MODEL_KEY)MBDCostCommand command,
                                   HttpServletRequest request,
                                   BindingResult bindingResult){

        if(!SecurityUtils.userHasAuthority(Constants.USERGROUP_ADMIN) && !SecurityUtils.userHasAuthority(Constants.USERGROUP_VMS_USER) && !SecurityUtils.userHasAuthority(Constants.EXPENSE_MANAGER)){
            logger.warn("User: " + SecurityUtils.getPrincipal().getDisplayName() + ", userId: " + SecurityUtils.getLoginUserId() + " is trying to access non-authorized page: " + "/payment/management.html page. ACCESS DENIED FOR BIDDEN!");
            throw new ForBiddenException();
        }

        ModelAndView mav = new ModelAndView("/admin/payment/management");
        String action = command.getCrudaction();

        if(StringUtils.isNotBlank(action)  ){
            if(action.equals(Constants.ACTION_UPDATE)){
                try{
                    convertDate2Timestamp(command);
                    validator.validate(command, bindingResult);
                    if(StringUtils.isBlank(command.getErrorMessage())){
                        this.costService.updatePayment(command.getCheckList(), command.getPojo().getPaymentDate());
                        mav.addObject(Constants.ALERT_TYPE, "success");
                        mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("code_history.management.successfully_update_payment"));
                    }else{
                        mav.addObject(Constants.ALERT_TYPE, "danger");
                        mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, command.getErrorMessage());
                    }
                }catch (Exception e){
                    logger.error(e.getMessage());
                    mav.addObject(Constants.ALERT_TYPE, "danger");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("code_history.management.error_not_found_code_history_to_update"));
                }
            }else if (action.equals(Constants.ACTION_SEARCH)){
                executeSearch(command, request);
            }
        }

        mav.addObject(Constants.LIST_MODEL_KEY, command);
        return mav;
    }

    private void convertDate2Timestamp(MBDCostCommand command){
        if(command.getPaymentDate() != null){
            command.getPojo().setPaymentDate(DateUtil.dateToTimestamp(command.getPaymentDate(), Constants.VI_DATE_FORMAT));
        }
        if(command.getStaDateFrom() != null){
            command.getPojo().setStaDateFrom(DateUtil.dateToTimestamp(command.getStaDateFrom(), Constants.VI_DATE_FORMAT));
        }
        if(command.getStaDateTo() != null){
            command.getPojo().setStaDateTo(DateUtil.dateToTimestamp(command.getStaDateTo(), Constants.VI_DATE_FORMAT));
        }
    }

    private Map<String, Object> buildProperties(MBDCostCommand command){
        MBDCostInfoDTO pojo = command.getPojo();
        Map<String, Object> properties = new HashMap<String, Object>();

        if(pojo.getStaDateFrom() != null){
            properties.put("staDateTimeFrom", pojo.getStaDateFrom());
        }
        if(pojo.getStaDateFrom() != null){
            properties.put("staDateTimeTo", pojo.getStaDateTo());
        }
        if(StringUtils.isNotBlank(pojo.getIsdn())){
            properties.put("isdn", pojo.getIsdn().trim());
        }
        if(StringUtils.isNotBlank(pojo.getName())){
            properties.put("name", pojo.getName().trim());
        }
        if(StringUtils.isNotBlank(pojo.getShopCode())){
            properties.put("shopCode", pojo.getShopCode().trim());
        }
        properties.put("paymentStatus", pojo.getPaymentStatus().trim());
        return properties;
    }

    private void executeSearch(MBDCostCommand command, HttpServletRequest request){
        RequestUtil.initSearchBean(request,command);
        Map<String,Object> properties = buildProperties(command);

        command.setSortExpression("issue_month");
        command.setSortDirection(Constants.SORT_DESC);

        Object[] resultObject = this.costService.searchPaymentListByProperties(properties, command.getSortExpression(), command.getSortDirection(), command.getFirstItem(), command.getReportMaxPageItems());
        command.setTotalItems(Integer.valueOf(resultObject[0].toString()));
        command.setListResult((List<MBDCostInfoDTO>)resultObject[1]);
        command.setMaxPageItems(command.getReportMaxPageItems());
    }
}
