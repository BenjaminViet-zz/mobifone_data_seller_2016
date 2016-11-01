package com.benluck.vms.mobifonedataseller.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by thaihoang on 10/31/2016.
 */

@Controller
public class DonHangController {

    @RequestMapping(value = {"/admin/order/list.html", "/order/list.html"} )
    public ModelAndView list(){
        return new ModelAndView("/admin/donhang/list");
    }
}
