package com.benluck.vms.mobifonedataseller.webapp.controller;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.core.business.*;
import com.benluck.vms.mobifonedataseller.editor.CustomCurrencyFormatEditor;
import com.benluck.vms.mobifonedataseller.editor.CustomDateEditor;
import com.benluck.vms.mobifonedataseller.security.util.SecurityUtils;
import com.benluck.vms.mobifonedataseller.util.RedisUtil;
import com.benluck.vms.mobifonedataseller.webapp.command.OrderCommand;
import com.benluck.vms.mobifonedataseller.webapp.exception.ForBiddenException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/24/16
 * Time: 21:11
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class OldOrderController extends ApplicationObjectSupport{
    private Logger logger = Logger.getLogger(OldOrderController.class);

    @Autowired
    private OrderManagementLocalBean orderService;
    @Autowired
    private PackageDataManagementLocalBean packageDataService;
    @Autowired
    private KHDNManagementLocalBean KHDNService;
    @Autowired
    private CodeHistoryManagementLocalBean codeHistoryService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor("dd/MM/yyyy"));
        binder.registerCustomEditor(Double.class, new CustomCurrencyFormatEditor());
        binder.registerCustomEditor(Integer.class, new CustomCurrencyFormatEditor());
    }

    @RequestMapping(value = {"/admin/order/oldorder/add.html", "/user/order/oldorder/add.html"})
    public ModelAndView oldOrder(@ModelAttribute(Constants.FORM_MODEL_KEY)OrderCommand command,
                                 HttpServletRequest request,
                                 BindingResult bindingResult){
        ModelAndView mav = new ModelAndView("/admin/order/oldorder");

        if(!SecurityUtils.userHasAuthority(Constants.USERGROUP_ADMIN) && !SecurityUtils.userHasAuthority(Constants.USERGROUP_VMS_USER)){
            logger.warn("User: " + SecurityUtils.getPrincipal().getDisplayName() + ", userId: " + SecurityUtils.getLoginUserId() + " is trying to access non-authorized page: " + "/order/add.html or /order/edit.html page. ACCESS DENIED FOR BIDDEN!");
            throw new ForBiddenException();
        }
        Boolean hasImportedUsedCardCode = (Boolean) RedisUtil.getRedisValueByKey(Constants.IMPORTED_CARD_CODE_REDIS_KEY_AND_HASKEY, Constants.IMPORTED_CARD_CODE_REDIS_KEY_AND_HASKEY);
        if(hasImportedUsedCardCode == null || !hasImportedUsedCardCode.booleanValue()){
            logger.warn("Please import Used Card Code list before using this feature.");
            if(SecurityUtils.userHasAuthority(Constants.USERGROUP_ADMIN)){
                return new ModelAndView("redirect:/admin/order/list.html");
            }else{
                return new ModelAndView("redirect:/user/order/list.html");
            }
        }

        preferenceData(mav, command);
        return mav;
    }

    private void preferenceData(ModelAndView mav, OrderCommand command){
        mav.addObject("packageDataList", packageDataService.findAll());
        mav.addObject("KHDNList", KHDNService.findAll());
        mav.addObject("packageDataIdListHasGeneratedCardCode", this.packageDataService.findPackageDataIdListHasGeneratedCardCode(Calendar.getInstance().get(Calendar.YEAR)));
        mav.addObject("hasImportedUsedCardCode", RedisUtil.getRedisValueByKey(Constants.IMPORTED_CARD_CODE_REDIS_KEY_AND_HASKEY, Constants.IMPORTED_CARD_CODE_REDIS_KEY_AND_HASKEY));

        if(command.getPojo() != null && command.getPojo().getOrderId() != null ){
            mav.addObject("totalRemainingPaidPackageValue", this.codeHistoryService.calculateTotalPaidPackageValue(command.getPojo().getKhdn().getStb_vas()));
        }else{
            mav.addObject("totalRemainingPaidPackageValue", 0D);
        }
    }
}
