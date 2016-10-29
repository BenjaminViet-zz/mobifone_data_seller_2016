package com.benluck.vms.mobifonedataseller.webapp.controller;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.security.util.SecurityUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 10/29/16
 * Time: 15:06
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class DashboardPageController {
    private Logger logger = Logger.getLogger(DashboardPageController.class);

    @RequestMapping(value = {"/admin/dashboard.html", "/dashboard.html"})
    public ModelAndView checkLogin(){
        if(SecurityUtils.userHasAuthority(Constants.ADMIN_ROLE)){
            return new ModelAndView("/admin/dashboard");
        }
        return new ModelAndView("/dashboard");
    }
}
