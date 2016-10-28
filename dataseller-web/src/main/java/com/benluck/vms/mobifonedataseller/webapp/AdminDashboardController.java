package com.benluck.vms.mobifonedataseller.webapp;

import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: benluck
 * Date: 10/9/14
 * Time: 2:33 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class AdminDashboardController extends ApplicationObjectSupport {
    @RequestMapping(value={"/admin/dashboard.html"})
    public ModelAndView dashboard() {
        return new ModelAndView("admin/dashboard");
    }
}
