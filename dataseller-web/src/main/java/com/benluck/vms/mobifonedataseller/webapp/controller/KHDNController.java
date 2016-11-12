package com.benluck.vms.mobifonedataseller.webapp.controller;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.utils.DateUtil;
import com.benluck.vms.mobifonedataseller.core.business.KHDNManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.KHDNDTO;
import com.benluck.vms.mobifonedataseller.editor.CustomDateEditor;
import com.benluck.vms.mobifonedataseller.util.RequestUtil;
import com.benluck.vms.mobifonedataseller.webapp.command.KHDNCommand;
import com.benluck.vms.mobifonedataseller.webapp.validator.KHDNValidator;
import org.apache.commons.lang.StringUtils;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.ejb.DuplicateKeyException;
import javax.ejb.ObjectNotFoundException;
import javax.ejb.RemoveException;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor("dd/MM/yyyy"));
    }

    @RequestMapping( value = {"/admin/khdn/list.html"})
    public ModelAndView list(@ModelAttribute(value = Constants.FORM_MODEL_KEY) KHDNCommand command,
                             HttpServletRequest request,
                             BindingResult bindingResult) throws RemoveException {
        ModelAndView mav = new ModelAndView("/admin/khdn/list");
        String action = command.getCrudaction();
        if (StringUtils.isNotBlank(action)){
            if(action.equals("delete")){
                if(command.getPojo().getKHDNId() != null){
                    try{
                        validator.validate(command, bindingResult);
                        if(StringUtils.isBlank(command.getErrorMessage())){
                            this.khdnService.deleteItemById(command.getPojo().getKHDNId());
                            mav.addObject(Constants.ALERT_TYPE, "success");
                            mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("admin.khdn.delete_successfully"));
                        }else{
                            mav.addObject(Constants.ALERT_TYPE, "danger");
                            mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage(command.getErrorMessage()));
                            executeSearch(mav, command, request);
                        }
                    }catch (Exception e){
                        mav.addObject(Constants.ALERT_TYPE, "info");
                        mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("admin.khdn.delete_khdn_exception"));
                    }
                }
            }else if (action.equals(Constants.ACTION_SEARCH)){
                executeSearch(mav, command, request);
            }
        }else{
            executeSearch(mav, command, request);
        }

        mav.addObject("page", command.getPage() - 1);
        mav.addObject(Constants.LIST_MODEL_KEY, command);
        return mav;
    }

    @RequestMapping(value = {"/admin/khdn/add.html", "/admin/khdn/edit.html"})
    public ModelAndView edit(@ModelAttribute(Constants.FORM_MODEL_KEY) KHDNCommand command,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView("/admin/khdn/edit");
        String crudaction = command.getCrudaction();
        KHDNDTO pojo = command.getPojo();

        try{
            if (StringUtils.isNotBlank(crudaction)){
                if(crudaction.equals("insert-update")){
                    convertDate2Timestamp(command);
                    validator.validate(command, bindingResult);
                    if (!bindingResult.hasErrors()){
                        if (pojo.getKHDNId() == null ){
                            this.khdnService.addItem(command.getPojo());
                            redirectAttributes.addFlashAttribute(Constants.ALERT_TYPE, "success");
                            redirectAttributes.addFlashAttribute("messageResponse", this.getMessageSourceAccessor().getMessage("database.add.successful"));
                        } else {
                            this.khdnService.updateItem(command.getPojo());
                            redirectAttributes.addFlashAttribute(Constants.ALERT_TYPE, "success");
                            redirectAttributes.addFlashAttribute("messageResponse", this.getMessageSourceAccessor().getMessage("database.update.successful"));
                        }
                        return new ModelAndView("redirect:/admin/khdn/list.html");
                    }
                }
            } else if( pojo.getKHDNId() != null ) {
                command.setPojo(this.khdnService.findById(command.getPojo().getKHDNId()));
            }
        }catch (ObjectNotFoundException one){
            logger.error("Can not get data of khdnId: " + pojo.getKHDNId());
            mav.addObject(Constants.ALERT_TYPE, "danger");
            mav.addObject("messageResponse", this.getMessageSourceAccessor().getMessage("database.exception.keynotfound"));
        }catch (DuplicateKeyException dle){
            logger.error("Duplicated khdnId: " + pojo.getKHDNId());
            mav.addObject(Constants.ALERT_TYPE, "danger");
            mav.addObject("messageResponse", this.getMessageSourceAccessor().getMessage("database.exception.duplicated_id"));
        }
        preferenceData(mav);
        mav.addObject("page", command.getPage() - 1);
        mav.addObject(Constants.LIST_MODEL_KEY, command);
        return mav;
    }

    private void executeSearch(ModelAndView mav, KHDNCommand command, HttpServletRequest request){
        RequestUtil.initSearchBean(request, command);
        KHDNDTO pojo = command.getPojo();

        Map<String, Object> properties = new HashMap<String, Object>();

        if(StringUtils.isNotBlank(pojo.getMst())){
            properties.put("mst", pojo.getMst());
        }
        if(StringUtils.isNotBlank(pojo.getGpkd())){
            properties.put("gpkd", pojo.getGpkd());
        }
        if(StringUtils.isNotBlank(pojo.getName())){
            properties.put("name", pojo.getName());
        }
        if(StringUtils.isNotBlank(pojo.getStb_vas())){
            properties.put("stb_vas", pojo.getStb_vas());
        }

        if(properties.size() == 0 && StringUtils.isBlank(command.getSortExpression())){
            command.setSortExpression("name");
            command.setSortDirection(Constants.SORT_DESC);
        }

        Object[] resultObject = khdnService.findByProperties(properties, command.getSortExpression(), command.getSortDirection(), command.getFirstItem(), command.getMaxPageItems());
        command.setTotalItems(Integer.valueOf(resultObject[0].toString()));
        command.setListResult((List<KHDNDTO>)resultObject[1]);
        command.setMaxPageItems(command.getMaxPageItems());
    }

    private void preferenceData(ModelAndView mav){
        mav.addObject("KHDNList", khdnService.findAll());
    }
    /**
     * Copy Date value and format to Timestamp.
     * @param command
     */
    private void convertDate2Timestamp(KHDNCommand command){
        if(command.getIssuedContractDate() != null){
            command.getPojo().setIssuedContractDate(DateUtil.dateToTimestamp(command.getIssuedContractDate(), Constants.VI_DATE_FORMAT));
        }
    }
}
