package com.benluck.vms.mobifonedataseller.webapp.controller;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.utils.Config;
import com.benluck.vms.mobifonedataseller.security.util.SecurityUtils;
import com.benluck.vms.mobifonedataseller.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: benluck
 * Date: 10/9/14
 * Time: 4:49 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class HomeController extends ApplicationObjectSupport {

    @RequestMapping(value={"/", "/index*", "index.html", "/home.html"})
    public ModelAndView home(){
        return new ModelAndView("/mobifonedata/trangchu");
    }

    @RequestMapping(value = "/noi-dung-chuong-trinh.html")
    public ModelAndView theLeChuongTrinh_Q_Student(){
        return new ModelAndView("/mobifonedata/thelechuongtrinh");
    }

    @RequestMapping(value = "/quy-tac-su-dung.html")
    public ModelAndView cachThucDoiQua_Q_Student(){
        return new ModelAndView("/mobifonedata/cachthucdoi");
    }
}