package com.benluck.vms.mobifonedataseller.webapp.controller;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.core.business.BranchManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.ChiNhanhManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.DMChuongTrinhManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.PromotionDTO;
import com.benluck.vms.mobifonedataseller.util.RequestUtil;
import com.benluck.vms.mobifonedataseller.webapp.command.command2014.DMChuongTrinhCommand;
import com.benluck.vms.mobifonedataseller.webapp.validator.DMChuongTrinhValidator;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.ejb.DuplicateKeyException;
import javax.ejb.ObjectNotFoundException;
import javax.ejb.RemoveException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 3/2/15
 * Time: 11:21 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class DMChuongTrinhController extends ApplicationObjectSupport{
    @Autowired
    private BranchManagementLocalBean branchManagementLocalBean;
    @Autowired
    private ChiNhanhManagementLocalBean chiNhanhManagementLocalBean;
    @Autowired
    private DMChuongTrinhManagementLocalBean dmChuongTrinhService;
    @Autowired
    private DMChuongTrinhValidator validator;

    @RequestMapping(value = {"/admin/danhmucchuongtrinh.html"})
    public ModelAndView dmChuongTrinh(@ModelAttribute(value = Constants.FORM_MODEL_KEY)DMChuongTrinhCommand command,
                                      HttpServletRequest request){
        ModelAndView mav = new ModelAndView("/admin/danhmuc/chuongtrinh/list");
        executeSearch(command, request, mav);
        return mav;
    }

    @RequestMapping(value = {"/admin/danhmucchuongtrinh/themmoi.html","/admin/danhmucchuongtrinh/chinhsua.html"})
    public ModelAndView edit(@ModelAttribute(value = Constants.FORM_MODEL_KEY)DMChuongTrinhCommand command,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes){
        ModelAndView mav = new ModelAndView("/admin/danhmuc/chuongtrinh/edit");
        String crudaction = command.getCrudaction();
        if(StringUtils.isNotBlank(crudaction)){
            if(crudaction.equals("insert-update")){
                validator.validate(command, bindingResult);
                if(!bindingResult.hasErrors()){
                    if(command.getPojo().getChuongTrinhId() != null){
                        try{
                            this.dmChuongTrinhService.updateItem(command.getPojo());
                            redirectAttributes.addFlashAttribute(Constants.ALERT_TYPE, "success");
                            redirectAttributes.addFlashAttribute(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("dmchuongtrinh.msg.update_successfully"));
                        }catch (Exception e){
                            redirectAttributes.addFlashAttribute(Constants.ALERT_TYPE, "danger");
                            redirectAttributes.addFlashAttribute(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("dmchuongtrinh.msg.update_exception"));
                        }
                    }else{
                        try{
                            this.dmChuongTrinhService.addItem(command.getPojo());
                            redirectAttributes.addFlashAttribute(Constants.ALERT_TYPE, "success");
                            redirectAttributes.addFlashAttribute(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("dmchuongtrinh.msg.add_successfully"));
                        }catch (DuplicateKeyException de){
                            redirectAttributes.addFlashAttribute(Constants.ALERT_TYPE, "danger");
                            redirectAttributes.addFlashAttribute(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("dmchuongtrinh.msg.add_exception"));
                        }
                    }
                    return new ModelAndView("redirect:/admin/danhmucchuongtrinh.html");
                }
            }else if(crudaction.equals("delete")){
                try{
                    this.dmChuongTrinhService.deleteItem(command.getPojo().getChuongTrinhId());
                    redirectAttributes.addFlashAttribute(Constants.ALERT_TYPE, "success");
                    redirectAttributes.addFlashAttribute(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("dmchuongtrinh.msg.delete_successfully"));
                }catch (RemoveException re){
                    logger.error(re.getMessage(), re);
                    redirectAttributes.addFlashAttribute(Constants.ALERT_TYPE, "danger");
                    redirectAttributes.addFlashAttribute(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("dmchuongtrinh.msg.delete_exception"));
                }
                return new ModelAndView("redirect:/admin/danhmucchuongtrinh.html");
            }
        }else if(command.getPojo().getChuongTrinhId() != null){
            try{
                PromotionDTO chuongTrinhDTO = this.dmChuongTrinhService.findById(command.getPojo().getChuongTrinhId());
                command.setPojo(chuongTrinhDTO);
            }catch (ObjectNotFoundException oe){
                logger.error(oe.getMessage(), oe);
                mav.addObject(Constants.ALERT_TYPE, "danger");
                mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("dmchuongtrinh.msg.not_found_chuong_trinh"));
            }
        }
        return mav;
    }

    /**
     * Perform querying to fetch data for web.
     * @param command
     * @param request
     * @param mav
     */
    private void executeSearch(DMChuongTrinhCommand command, HttpServletRequest request, ModelAndView mav){
        RequestUtil.initSearchBean(request, command);
        Object[] resultObject = this.dmChuongTrinhService.search(command.getFirstItem(), command.getMaxPageItems(), command.getSortExpression(), command.getSortDirection());
        command.setListResult((List<PromotionDTO>)resultObject[1]);
        command.setTotalItems(Integer.valueOf(resultObject[0].toString()));
        mav.addObject("page", command.getPage() - 1);
        mav.addObject(Constants.LIST_MODEL_KEY, command);
    }
}
