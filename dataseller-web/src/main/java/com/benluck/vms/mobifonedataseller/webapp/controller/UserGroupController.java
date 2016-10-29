package com.benluck.vms.mobifonedataseller.webapp.controller;


import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.core.business.UserGroupManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.UserGroupDTO;
import com.benluck.vms.mobifonedataseller.editor.CustomDateEditor;
import com.benluck.vms.mobifonedataseller.util.RequestUtil;
import com.benluck.vms.mobifonedataseller.webapp.command.UserGroupCommand;
import com.benluck.vms.mobifonedataseller.webapp.validator.UserGroupValidator;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserGroupController extends ApplicationObjectSupport {
    private transient final Log log = LogFactory.getLog(getClass());

    @Autowired
    private UserGroupManagementLocalBean userGroupManagementLocalBean;

    @Autowired
    private UserGroupValidator userGroupValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor());
    }

    @RequestMapping("/admin/usergroup/edit.html")
    public ModelAndView edit(@ModelAttribute(Constants.FORM_MODEL_KEY) UserGroupCommand usergroupCommand, BindingResult bindingResult, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/usergroup/edit");
        String crudaction = usergroupCommand.getCrudaction();
        UserGroupDTO pojo = usergroupCommand.getPojo();
        if(StringUtils.isNotBlank(crudaction) && crudaction.equals("insert-update")) {
            try {
                userGroupValidator.validate(usergroupCommand, bindingResult);
                if(!bindingResult.hasErrors()) {
                    if(pojo.getUserGroupId() != null && pojo.getUserGroupId() > 0) {
                        pojo = this.userGroupManagementLocalBean.updateItem(usergroupCommand.getPojo());
                        mav.addObject("messageResponse", this.getMessageSourceAccessor().getMessage("database.update.successful"));
                    } else {
                        pojo = this.userGroupManagementLocalBean.addItem(usergroupCommand.getPojo());
                        mav.addObject("messageResponse", this.getMessageSourceAccessor().getMessage("database.add.successful"));
                    }
                    mav.addObject("success", true);
                    usergroupCommand.setPojo(pojo);

                }
            }catch(Exception e) {
                logger.error(e.getMessage(), e);
                mav.addObject("messageResponse", this.getMessageSourceAccessor().getMessage("general.exception.msg"));
            }
        }
        if(!bindingResult.hasErrors()&& usergroupCommand.getPojo().getUserGroupId() != null && usergroupCommand.getPojo().getUserGroupId() > 0) {
            try {
                UserGroupDTO itemObj = this.userGroupManagementLocalBean.findById(pojo.getUserGroupId());
                usergroupCommand.setPojo(itemObj);
            }
            catch (Exception e) {
                logger.error("Could not found user group " + usergroupCommand.getPojo().getUserGroupId(), e);
            }
        }
        mav.addObject(Constants.FORM_MODEL_KEY, usergroupCommand);
        return mav;
    }
}
