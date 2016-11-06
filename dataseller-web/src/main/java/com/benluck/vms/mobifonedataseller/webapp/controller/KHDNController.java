package com.benluck.vms.mobifonedataseller.webapp.controller;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.exception.DuplicateKeyException;
import com.benluck.vms.mobifonedataseller.core.business.KHDNManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.KHDNDTO;
import com.benluck.vms.mobifonedataseller.util.RequestUtil;
import com.benluck.vms.mobifonedataseller.webapp.command.KHDNCommand;
import com.benluck.vms.mobifonedataseller.webapp.validator.KHDNValidator;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.ejb.ObjectNotFoundException;
import javax.ejb.RemoveException;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * Created by thaihoang on 11/1/2016.
 */
@Controller
public class KHDNController extends ApplicationObjectSupport {
    private Logger logger = Logger.getLogger(OrderController.class);
    @Autowired
    private KHDNManagementLocalBean khdnService;

    @Autowired
    private KHDNValidator validator;


    /*@RequestMapping( value = {"/admin/vendor/list.html"})
    public ModelAndView list(@ModelAttribute(value = Constants.FORM_MODEL_KEY) KHDNCommand command, HttpServletRequest request) throws RemoveException {
        ModelAndView mav = new ModelAndView("/admin/khdn/list");
        String action = command.getCrudaction();
        if (StringUtils.isNotBlank(action)){
            if(action.equals("delete")){
                if(command.getPojo().getKHDNId() != null){
                    try{
                        this.khdnService.deleteItemById(command.getPojo().getKHDNId());
                        mav.addObject(Constants.ALERT_TYPE, "success");
                        mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("admin.user.delete_successfully"));
                    }catch (Exception e){
                        mav.addObject(Constants.ALERT_TYPE, "info");
                        mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("admin.user.can_not_delete_user"));
                    }
                }
            }else if (action.equals(Constants.ACTION_SEARCH)){
                executeSearch(command, request);
            }
        }
        mav.addObject("page", command.getPage() - 1);
        mav.addObject(Constants.LIST_MODEL_KEY, command);
        return mav;*//**//*
        ModelAndView mav = new ModelAndView("/admin/khdn/list");
        executeSearch(command, request);
        mav.addObject(Constants.LIST_MODEL_KEY, command);
        return mav;
    }*/

    @RequestMapping( value = {"/admin/vendor/list.html"})
    /*public ModelAndView list(@ModelAttribute(value = Constants.FORM_MODEL_KEY) KHDNCommand command, HttpServletRequest request) throws RemoveException {*/
    public ModelAndView list(@ModelAttribute(Constants.FORM_MODEL_KEY) KHDNCommand command, HttpServletRequest request){
        ModelAndView mav = new ModelAndView("/admin/khdn/list");
        executeSearch(command, request);

        preferenceData(mav);
        mav.addObject(Constants.LIST_MODEL_KEY, command);
        return mav;
    }


    @RequestMapping(value = {"/admin/vendor/add.html", "/admin/vendor/edit.html"})
    public ModelAndView edit(@ModelAttribute(Constants.FORM_MODEL_KEY) KHDNCommand command,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView("/admin/khdn/edit");
        String crudaction = command.getCrudaction();
        KHDNDTO pojo = command.getPojo();

        try{
            if (StringUtils.isNotBlank(crudaction)){
                if(crudaction.equals("insert-update")){
                    validator.validate(command, bindingResult);
                    if (!bindingResult.hasErrors()){
                        if (pojo.getKHDNId() == null ){
                            /*this.khdnService.addItem(command.getPojo());
                            redirectAttributes.addFlashAttribute(Constants.ALERT_TYPE, "success");
                            redirectAttributes.addFlashAttribute("messageResponse", this.getMessageSourceAccessor().getMessage("database.add.successful"));*/
                        } else {
                            /*this.khdnService.updateItem(command.getPojo());
                            redirectAttributes.addFlashAttribute(Constants.ALERT_TYPE, "success");
                            redirectAttributes.addFlashAttribute("messageResponse", this.getMessageSourceAccessor().getMessage("database.update.successful"));*/
                        }
                        return new ModelAndView("redirect:/admin/user/list.html");
                    }
                }
            } else if( pojo.getKHDNId() != null ) {
                command.setPojo(this.khdnService.findById(command.getPojo().getKHDNId()));
            }
        }catch (ObjectNotFoundException one){
            logger.error("Can not get data of khdnId: " + pojo.getKHDNId());
            mav.addObject(Constants.ALERT_TYPE, "danger");
            mav.addObject("messageResponse", this.getMessageSourceAccessor().getMessage("database.exception.keynotfound"));
        }/*catch (DuplicateKeyException dle){
            logger.error("Duplicated khdnId: " + pojo.getKHDNId());
            mav.addObject(Constants.ALERT_TYPE, "danger");
            mav.addObject("messageResponse", this.getMessageSourceAccessor().getMessage("database.exception.duplicated_id"));
        }*/
        preferenceData(mav);
        mav.addObject("page", command.getPage() - 1);
        mav.addObject(Constants.LIST_MODEL_KEY, command);
        return mav;
    }





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
