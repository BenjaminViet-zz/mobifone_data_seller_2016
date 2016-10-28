package com.benluck.vms.mobifonedataseller.webapp.controller;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.core.business.ChiNhanhManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.ChiNhanhDTO;
import com.benluck.vms.mobifonedataseller.util.RequestUtil;
import com.benluck.vms.mobifonedataseller.webapp.command.command2014.ChiNhanhCommand;
import com.benluck.vms.mobifonedataseller.webapp.validator.ChiNhanhValidator;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.ejb.ObjectNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/19/14
 * Time: 9:58 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class ChiNhanhController extends ApplicationObjectSupport {
    private Log log = LogFactory.getLog(UserController.class);

    @Autowired
    private ChiNhanhManagementLocalBean chiNhanhManagementLocalBean;
    @Autowired
    private ChiNhanhValidator validator;

    @RequestMapping("/admin/danhmucchinhanh.html")
    public ModelAndView danhMucChiNhanh(@ModelAttribute(value = Constants.FORM_MODEL_KEY)ChiNhanhCommand command, HttpServletRequest request){
        ModelAndView mav = new ModelAndView("/chinhanh/list");
        RequestUtil.initSearchBean(request, command);
        Object[] resultObject = this.chiNhanhManagementLocalBean.search(command.getFirstItem(), command.getMaxPageItems(),command.getSortExpression(), command.getSortDirection());
        command.setTotalItems(Integer.valueOf(resultObject[0].toString()));
        command.setListResult((List<ChiNhanhDTO>)resultObject[1]);
        mav.addObject(Constants.LIST_MODEL_KEY, command);
        return mav;
    }

    @RequestMapping("/admin/thongtinchinhanh.html")
    public ModelAndView thongtinchinhanh(@ModelAttribute(value = Constants.FORM_MODEL_KEY)ChiNhanhCommand command,
                                         HttpServletRequest request,
                                         BindingResult bindingResult){
        ModelAndView mav = new ModelAndView("/chinhanh/edit");
        String crudaction = command.getCrudaction();
        if(StringUtils.isNotBlank(crudaction) && crudaction.equals("insert-update")){
            validator.validate(command, bindingResult);
            if(!bindingResult.hasErrors()){
                if(command.getPojo().getChiNhanhId() != null){
                    try{
                        this.chiNhanhManagementLocalBean.updateItem(command.getPojo());
                        mav.addObject(Constants.ALERT_TYPE, "success");
                        mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("chinhanh.thong_tin_chi_nhanh.update_successfully"));
                    }catch (Exception e){
                        log.error("Error happen when updating chiNhanh information.\n Details: " + e.getMessage());
                        mav.addObject(Constants.ALERT_TYPE, "danger");
                        mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("chinhanh.thong_tin_chi_nhanh.update_exception"));
                    }
                }else{
                    // TODO code for add new.
                }
            }
        }else{
            if(command.getPojo().getChiNhanhId() != null){
                try{
                    ChiNhanhDTO dto = this.chiNhanhManagementLocalBean.findById(command.getPojo().getChiNhanhId());
                    command.setPojo(dto);
                }catch (ObjectNotFoundException oe){
                    log.error("Object not found for chiNhanhId: " + command.getPojo().getChiNhanhId());
                    mav.addObject(Constants.ALERT_TYPE, "info");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("chinhanh.thong_tin_chi_nhanh.msg.object_not_found"));
                }
            }
        }
        return mav;
    }
}
