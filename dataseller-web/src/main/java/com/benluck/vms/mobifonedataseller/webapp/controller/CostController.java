package com.benluck.vms.mobifonedataseller.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by thaihoang on 11/1/2016.
 */
@Controller
public class CostController {
    @RequestMapping( value = {"/admin/cost/list.html", "/cost/list.html"})
    public ModelAndView list(){
        return new ModelAndView("/admin/cost/list");
    }
}
