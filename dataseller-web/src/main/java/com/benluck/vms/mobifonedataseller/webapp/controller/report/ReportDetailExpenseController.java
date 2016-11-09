package com.benluck.vms.mobifonedataseller.webapp.controller.report;

import org.apache.log4j.Logger;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by thaihoang on 11/1/2016.
 */
@Controller
public class ReportDetailExpenseController extends ApplicationObjectSupport{
    private Logger logger = Logger.getLogger(ReportDetailExpenseController.class);

    @RequestMapping( value = {"/admin/reportDetailExpense/list.html", "/user/reportDetailExpense/list.html"})
    public ModelAndView list(){
        return new ModelAndView("/admin/report/expense/detailList");
    }
}
