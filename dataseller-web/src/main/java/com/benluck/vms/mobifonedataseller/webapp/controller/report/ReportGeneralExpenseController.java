package com.benluck.vms.mobifonedataseller.webapp.controller.report;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.core.business.KHDNManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.MBDCostManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.MBDReportGeneralExpenseDTO;
import com.benluck.vms.mobifonedataseller.util.RequestUtil;
import com.benluck.vms.mobifonedataseller.webapp.command.ReportGeneralExpenseCommand;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/9/16
 * Time: 22:18
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class ReportGeneralExpenseController extends ApplicationObjectSupport{
    private Logger logger = Logger.getLogger(ReportGeneralExpenseController.class);

    @Autowired
    private MBDCostManagementLocalBean costService;
    @Autowired
    private KHDNManagementLocalBean khdnService;

    @RequestMapping(value = {"/admin/reportGeneralExpense/list.html", "/user/reportGeneralExpense/list.html"})
    public ModelAndView list(@ModelAttribute(Constants.FORM_MODEL_KEY)ReportGeneralExpenseCommand command,
                             HttpServletRequest request,
                             HttpServletResponse response){
        ModelAndView mav = new ModelAndView("/admin/report/expense/generalList");
        String action = command.getCrudaction();

        if(StringUtils.isNotBlank(action)){
            if(action.equals(Constants.ACTION_EXPORT)){
                export2Excel(command, request, response);
            }else if(action.equals(Constants.ACTION_SEARCH)){
                executeSearch(command, request);
                mav.addObject(Constants.LIST_MODEL_KEY, command);
            }
        }
        mav.addObject("KHDNList", this.khdnService.findAll());
        return mav;
    }

    private void executeSearch(ReportGeneralExpenseCommand command, HttpServletRequest request){
        RequestUtil.initSearchBean(request, command);

        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("custID", command.getPojo().getCustId());

        Object[] resultObject = this.costService.searchByProperties(properties, command.getSortExpression(), command.getSortDirection(), command.getFirstItem(),command.getReportMaxPageItems());
        command.setTotalItems(Integer.valueOf(resultObject[0].toString()));
        command.setListResult((List<MBDReportGeneralExpenseDTO>)resultObject[1]);
        command.setMaxPageItems(command.getReportMaxPageItems());
    }

    private void export2Excel(ReportGeneralExpenseCommand command, HttpServletRequest request, HttpServletResponse response){

    }
}
