package com.benluck.vms.mobifonedataseller.webapp.controller;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.utils.Config;
import com.benluck.vms.mobifonedataseller.core.business.PackageDataManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.PackageDataDTO;
import com.benluck.vms.mobifonedataseller.security.util.SecurityUtils;
import com.benluck.vms.mobifonedataseller.util.RequestUtil;
import com.benluck.vms.mobifonedataseller.webapp.command.PackageDataCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
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

    @Autowired
    private PackageDataManagementLocalBean noiDungChuontrTrinhService;

    @RequestMapping(value={"/", "/index*", "index.html", "/home.html"})
    public ModelAndView home(){
        return new ModelAndView("/mobifonedata/trangchu");
    }

    @RequestMapping(value = "/noi-dung-chuong-trinh.html")
    public ModelAndView list(@ModelAttribute(Constants.FORM_MODEL_KEY)PackageDataCommand command, HttpServletRequest request){
        ModelAndView mav = new ModelAndView("/mobifonedata/thelechuongtrinh");
        executeSearch(command, request);
        mav.addObject(Constants.LIST_MODEL_KEY, command);
        return mav;
    }

    @RequestMapping(value = "/quy-tac-su-dung.html")
    public ModelAndView cachThucDoiQua_Q_Student(){
        return new ModelAndView("/mobifonedata/cachthucdoi");
    }

    private void executeSearch(PackageDataCommand command, HttpServletRequest request){
        RequestUtil.initSearchBean(request, command);
        Object[] resultObject = noiDungChuontrTrinhService.findByProperties(new HashMap<String, Object>(), command.getSortExpression(), command.getSortDirection(), command.getFirstItem(), command.getMaxPageItems());
        command.setTotalItems(Integer.valueOf(resultObject[0].toString()));
        command.setListResult((List<PackageDataDTO>)resultObject[1]);
        command.setMaxPageItems(command.getMaxPageItems());
    }
}