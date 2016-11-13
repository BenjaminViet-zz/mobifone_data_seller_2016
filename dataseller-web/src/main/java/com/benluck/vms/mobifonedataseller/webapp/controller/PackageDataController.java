package com.benluck.vms.mobifonedataseller.webapp.controller;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.core.business.PackageDataManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.PackageDataDTO;
import com.benluck.vms.mobifonedataseller.security.util.SecurityUtils;
import com.benluck.vms.mobifonedataseller.util.RequestUtil;
import com.benluck.vms.mobifonedataseller.webapp.command.PackageDataCommand;
import com.benluck.vms.mobifonedataseller.webapp.exception.ForBiddenException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 10/31/16
 * Time: 07:32
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class PackageDataController {
    private Logger logger = Logger.getLogger(PackageDataController.class);

    @Autowired
    private PackageDataManagementLocalBean packageDataService;

    @RequestMapping(value = {"/admin/package_data/list.html", "/user/package_data/list.html"})
    public ModelAndView list(@ModelAttribute(Constants.FORM_MODEL_KEY)PackageDataCommand command, HttpServletRequest request){

        if(!SecurityUtils.userHasAuthority(Constants.USERGROUP_ADMIN) && !SecurityUtils.userHasAuthority(Constants.USERGROUP_VMS_USER) && !SecurityUtils.userHasAuthority(Constants.PACKAGE_DATA_MANAGER)){
            logger.warn("User: " + SecurityUtils.getPrincipal().getDisplayName() + ", userId: " + SecurityUtils.getLoginUserId() + " is trying to access non-authorized page: " + "/packagedate/list.html page. ACCESS DENIED FOR BIDDEN!");
            throw new ForBiddenException();
        }

        ModelAndView mav = new ModelAndView("/admin/packagedata/list");
        executeSearch(command, request);
        mav.addObject(Constants.LIST_MODEL_KEY, command);
        return mav;
    }

    private void executeSearch(PackageDataCommand command, HttpServletRequest request){
        RequestUtil.initSearchBean(request, command);
        Object[] resultObject = packageDataService.findByProperties(new HashMap<String, Object>(), command.getSortExpression(), command.getSortDirection(), command.getFirstItem(), command.getMaxPageItems());
        command.setTotalItems(Integer.valueOf(resultObject[0].toString()));
        command.setListResult((List<PackageDataDTO>)resultObject[1]);
        command.setMaxPageItems(command.getMaxPageItems());
    }
}
