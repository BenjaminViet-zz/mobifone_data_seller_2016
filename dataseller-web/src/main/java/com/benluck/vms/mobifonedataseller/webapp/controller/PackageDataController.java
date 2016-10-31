package com.benluck.vms.mobifonedataseller.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 10/31/16
 * Time: 07:32
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class PackageDataController {

    @RequestMapping(value = {"/admin/package_data/list.html", "/package_data/list.html"})
    public ModelAndView list(){
        return new ModelAndView("/admin/packagedata/list");
    }

    /*@RequestMapping( value = {"/admin/package_data/add.html", "/package_data/add.html"} )
    public ModelAndView add() {
        return new ModelAndView("/admin/packagedata/add");
    }*/
}
