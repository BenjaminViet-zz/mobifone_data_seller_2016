package com.benluck.vms.mobifonedataseller.webapp.controller;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.core.business.PackageDataCodeGenManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.PackageDataManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.PackageDataDTO;
import com.benluck.vms.mobifonedataseller.editor.CustomCurrencyFormatEditor;
import com.benluck.vms.mobifonedataseller.security.util.SecurityUtils;
import com.benluck.vms.mobifonedataseller.util.RequestUtil;
import com.benluck.vms.mobifonedataseller.webapp.command.PackageDataCommand;
import com.benluck.vms.mobifonedataseller.webapp.exception.ForBiddenException;
import com.benluck.vms.mobifonedataseller.webapp.task.TaskGenerateCardCode;
import com.benluck.vms.mobifonedataseller.webapp.validator.PackageDataValidator;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.ejb.DuplicateKeyException;
import javax.ejb.ObjectNotFoundException;
import javax.ejb.RemoveException;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 10/31/16
 * Time: 07:32
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class PackageDataController extends ApplicationObjectSupport{
    private Logger logger = Logger.getLogger(PackageDataController.class);

    @Autowired
    private PackageDataManagementLocalBean packageDataService;
    @Autowired
    private PackageDataCodeGenManagementLocalBean packageDataCodeGenService;
    @Autowired
    private PackageDataValidator validator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Double.class, new CustomCurrencyFormatEditor());
        binder.registerCustomEditor(Integer.class, new CustomCurrencyFormatEditor());
    }

    @RequestMapping(value = {"/admin/package_data/list.html", "/user/package_data/list.html"})
    public ModelAndView list(@ModelAttribute(Constants.FORM_MODEL_KEY)PackageDataCommand command,
                             HttpServletRequest request,
                             RedirectAttributes redirectAttributes,
                             BindingResult bindingResult){

        if(!SecurityUtils.userHasAuthority(Constants.USERGROUP_ADMIN) && !SecurityUtils.userHasAuthority(Constants.USERGROUP_VMS_USER) && !SecurityUtils.userHasAuthority(Constants.PACKAGE_DATA_MANAGER)){
            logger.warn("User: " + SecurityUtils.getPrincipal().getDisplayName() + ", userId: " + SecurityUtils.getLoginUserId() + " is trying to access non-authorized page: " + "/packagedate/list.html page. ACCESS DENIED FOR BIDDEN!");
            throw new ForBiddenException();
        }

        ModelAndView mav = new ModelAndView("/admin/packagedata/list");
        String crudaction = command.getCrudaction();
        if(StringUtils.isNotBlank(crudaction)){
            if(crudaction.equals(Constants.ACTION_DELETE)){
                try{
                    validator.validate(command, bindingResult);
                    if(StringUtils.isBlank(command.getErrorMessage())){
                        packageDataService.deleteItem(command.getPojo().getPackageDataId());

                        redirectAttributes.addFlashAttribute(Constants.ALERT_TYPE, "success");
                        redirectAttributes.addFlashAttribute(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("admin.packagedata.remove_successfully"));

                        if(SecurityUtils.userHasAuthority(Constants.USERGROUP_ADMIN)){
                            return new ModelAndView("redirect:/admin/package_data/list.html");
                        }else{
                            return new ModelAndView("redirect:/user/package_data/list.html");
                        }
                    }else{
                        mav.addObject(Constants.ALERT_TYPE, "danger");
                        mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, command.getErrorMessage());
                    }
                }catch (RemoveException re){
                    logger.error(re.getMessage());
                    mav.addObject(Constants.ALERT_TYPE, "danger");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("admin.packagedata.remove_exception"));
                }
            }
        }
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

    @RequestMapping(value = {"/admin/package_data/add.html", "/admin/package_data/edit.html", "/user/package_data/add.html", "/user/package_data/edit.html"})
    public ModelAndView edit(@ModelAttribute(Constants.FORM_MODEL_KEY) PackageDataCommand command,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {

        if(!SecurityUtils.userHasAuthority(Constants.USERGROUP_ADMIN) && !SecurityUtils.userHasAuthority(Constants.USERGROUP_VMS_USER) && !SecurityUtils.userHasAuthority(Constants.PACKAGE_DATA_MANAGER)){
            logger.warn("User: " + SecurityUtils.getPrincipal().getDisplayName() + ", userId: " + SecurityUtils.getLoginUserId() + " is trying to access non-authorized page: " + "/package_data/add.html or /package_data/edit.html page. ACCESS DENIED FOR BIDDEN!");
            throw new ForBiddenException();
        }

        ModelAndView mav = new ModelAndView("/admin/packagedata/edit");
        String crudaction = command.getCrudaction();
        PackageDataDTO pojo = command.getPojo();

        try{
            if (StringUtils.isNotBlank(crudaction)){
                validator.validate(command, bindingResult);
                if(crudaction.equals("insert-update")){
                    if (!bindingResult.hasErrors() && StringUtils.isBlank(command.getErrorMessage())){
                        if (pojo.getPackageDataId() == null ){
                            command.setPojo(this.packageDataService.addItem(command.getPojo()));
                            command.getPojo().setGeneratedCardCode(pojo.getGeneratedCardCode());
                            check2GenerateCardCode(command);
                            redirectAttributes.addFlashAttribute(Constants.ALERT_TYPE, "success");
                            redirectAttributes.addFlashAttribute("messageResponse", this.getMessageSourceAccessor().getMessage("database.add.successful"));
                        } else {
                            this.packageDataService.updateItem(command.getPojo());
                            command.getPojo().setGeneratedCardCode(pojo.getGeneratedCardCode());
                            check2GenerateCardCode(command);
                            redirectAttributes.addFlashAttribute(Constants.ALERT_TYPE, "success");
                            redirectAttributes.addFlashAttribute("messageResponse", this.getMessageSourceAccessor().getMessage("database.update.successful"));
                        }
                        return new ModelAndView("redirect:/admin/package_data/list.html");
                    }else{
                        if(StringUtils.isNotBlank(command.getErrorMessage())){
                            mav.addObject(Constants.ALERT_TYPE, "danger");
                            mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, command.getErrorMessage());
                        }
                    }
                }
            } else if( pojo.getPackageDataId() != null ) {
                command.setPojo(this.packageDataService.findById(command.getPojo().getPackageDataId()));
            }
        }catch (ObjectNotFoundException one){
            logger.error("Can not get data of packageDataId: " + pojo.getPackageDataId());
            mav.addObject(Constants.ALERT_TYPE, "danger");
            mav.addObject("messageResponse", this.getMessageSourceAccessor().getMessage("database.exception.keynotfound"));
        }catch (DuplicateKeyException dle){
            logger.error("Duplicated packageDataId: " + pojo.getPackageDataId());
            mav.addObject(Constants.ALERT_TYPE, "danger");
            mav.addObject("messageResponse", this.getMessageSourceAccessor().getMessage("database.exception.duplicated_id"));
        }
        return mav;
    }

    private void check2GenerateCardCode(PackageDataCommand command){
        if(command.getPojo().getGeneratedCardCode() != null && command.getPojo().getGeneratedCardCode()){
            try{
                PackageDataDTO pojo = this.packageDataService.findById(command.getPojo().getPackageDataId());
                if(!pojo.getGeneratedCardCode()){
                    Integer year = Calendar.getInstance().get(Calendar.YEAR);

                    String[] packageDataIdsStr = new String[1];
                    packageDataIdsStr[0] = command.getPojo().getPackageDataId().toString();

                    this.packageDataCodeGenService.AddOrUpdateProcessing(year, packageDataIdsStr, Constants.PACKAGE_DATA_CODE_GEN_STATUS_PROCESSING);

                    startJobGenerateCardCode4PackageStorage(packageDataIdsStr, year);
                }
            }catch (Exception e){
                logger.error(e.getMessage());
            }
        }
    }

    private void startJobGenerateCardCode4PackageStorage(String[] packageDataIdsStr, Integer year){
        TaskGenerateCardCode generateCardCodeTask = new TaskGenerateCardCode(SecurityUtils.getLoginUserId(), year, packageDataIdsStr);
        Timer timer = new Timer(true);
        timer.schedule(generateCardCodeTask, 0);
    }
}
