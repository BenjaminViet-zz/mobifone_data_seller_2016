package com.benluck.vms.mobifonedataseller.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by thaihoang on 11/1/2016.
 */
@Controller
public class KHDNController {

    @RequestMapping( value = {"/admin/vendor/list.html", "/vendor/list.html"})
    public ModelAndView list(){
        return new ModelAndView("/admin/khdn/list");
    }

    @RequestMapping( value={"/admin/vendor/add.html"} )
    public ModelAndView add(){
        return new ModelAndView("/admin/donhang/add");
    }
}
