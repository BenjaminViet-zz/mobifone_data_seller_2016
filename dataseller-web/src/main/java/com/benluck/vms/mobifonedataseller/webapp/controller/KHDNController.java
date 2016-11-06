package com.benluck.vms.mobifonedataseller.webapp.controller;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.core.business.KHDNManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.KHDNDTO;
import com.benluck.vms.mobifonedataseller.core.dto.UserGroupDTO;
import com.benluck.vms.mobifonedataseller.util.RequestUtil;
import com.benluck.vms.mobifonedataseller.webapp.command.KHDNCommand;
import com.benluck.vms.mobifonedataseller.webapp.command.UserGroupCommand;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * Created by thaihoang on 11/1/2016.
 */
@Controller
public class KHDNController {

    @Autowired
    private KHDNManagementLocalBean khdnService;

    @RequestMapping( value = {"/admin/vendor/list.html"})
    public ModelAndView list(@ModelAttribute(Constants.FORM_MODEL_KEY) KHDNCommand command, HttpServletRequest request){
        ModelAndView mav = new ModelAndView("/admin/khdn/list");
        executeSearch(command, request);

        preferenceData(mav);
        mav.addObject(Constants.LIST_MODEL_KEY, command);
        return mav;
    }

    /*@RequestMapping( value={"/admin/vendor/add.html"} )
    public ModelAndView add(){
        return new ModelAndView("/admin/khdn/add");
    }

    @RequestMapping( value={"/admin/vendor/edit.html"} )
    public ModelAndView edit(){
        return new ModelAndView("/admin/khdn/add");
    }*/
    private void executeSearch(KHDNCommand command, HttpServletRequest request){
        RequestUtil.initSearchBean(request, command);
        Object[] resultObject = khdnService.findByProperties(new HashMap<String, Object>(), command.getSortExpression(), command.getSortDirection(), command.getFirstItem(), command.getMaxPageItems());
        command.setTotalItems(Integer.valueOf(resultObject[0].toString()));
        command.setListResult((List<KHDNDTO>)resultObject[1]);
        command.setMaxPageItems(command.getMaxPageItems());
    }

    private void preferenceData(ModelAndView mav){
        mav.addObject("KHDNList", khdnService.findAll());
    }

}
