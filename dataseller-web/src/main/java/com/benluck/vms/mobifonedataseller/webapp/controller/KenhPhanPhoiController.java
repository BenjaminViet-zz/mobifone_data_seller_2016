package com.benluck.vms.mobifonedataseller.webapp.controller;

import com.benluck.vms.mobifonedataseller.common.utils.Config;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created with IntelliJ IDEA.
 * User: HauKute
 * Date: 10/14/14
 * Time: 4:48 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class KenhPhanPhoiController extends ApplicationObjectSupport {

    @InitBinder
    public void initBinder(WebDataBinder binder) {

    }

    @RequestMapping(value={"/kenhphanphoi/cach-thuc-du-thuong.html"})
    public ModelAndView cachthucdoithuong(){
        ModelAndView modelAndView = new ModelAndView("/kenhphanphoi/kpp_cachthucdoithuong");
        return modelAndView;
    }

    @RequestMapping(value="/kenhphanphoi/dia-diem-doi-qua.html")
    public ModelAndView diadiemdoiqua(){
        ModelAndView modelAndView = new ModelAndView("/kenhphanphoi/kpp_diadiemdoiqua");
        return modelAndView;
    }

    @RequestMapping(value="/kenhphanphoi/the-le-chuong-trinh.html")
    public ModelAndView thelechuongtrinh(){
        ModelAndView modelAndView = new ModelAndView("/kenhphanphoi/kpp_thelechuongtrinh");
        return modelAndView;
    }

    @RequestMapping(value="/kenhphanphoi/trang-chu.html")
    public ModelAndView trangchu(){
        ModelAndView modelAndView = new ModelAndView("/kenhphanphoi/kpp_trangchu");
        return modelAndView;
    }
}
