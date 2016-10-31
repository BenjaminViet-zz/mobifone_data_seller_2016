package com.benluck.vms.mobifonedataseller.webapp.controller;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.core.business.UserGroupManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.UserManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.UserDTO;
import com.benluck.vms.mobifonedataseller.core.dto.UserGroupDTO;
import com.benluck.vms.mobifonedataseller.security.util.SecurityUtils;
import com.benluck.vms.mobifonedataseller.webapp.command.UserCommand;
import com.benluck.vms.mobifonedataseller.webapp.validator.ProfileValidator;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.ejb.ObjectNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 10/30/16
 * Time: 17:16
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class ProfileController extends ApplicationObjectSupport{
    private Logger logger = Logger.getLogger(ProfileController.class);

    @Autowired
    private UserManagementLocalBean userService;
    @Autowired
    private UserGroupManagementLocalBean userGroupService;
    @Autowired
    private ProfileValidator validator;


    @RequestMapping(value ={"/admin/profile.html","/user/profile.html"})
    public ModelAndView editProfile(@ModelAttribute(Constants.FORM_MODEL_KEY)UserCommand command, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) throws ObjectNotFoundException {

        ModelAndView mav = new ModelAndView("/user/profile");
        String crudaction = command.getCrudaction();

        if (StringUtils.isNotBlank(crudaction) && crudaction.equals("insert-update")){
            try{
                validator.validate(command, bindingResult);
                if(StringUtils.isNotBlank(command.getErrorMessage())){
                    mav.addObject(Constants.ALERT_TYPE, "danger");
                    mav.addObject("messageResponse", command.getErrorMessage());
                }else if (!bindingResult.hasErrors()){
                    this.userService.updateItem(command.getPojo());
                    SecurityUtils.getPrincipal().setDisplayName(command.getPojo().getDisplayName());
                    mav.addObject(Constants.ALERT_TYPE, "success");
                    mav.addObject("messageResponse", this.getMessageSourceAccessor().getMessage("database.update.successful"));
                }
            } catch (Exception e) {
                mav.addObject(Constants.ALERT_TYPE, "danger");
                mav.addObject("messageResponse", this.getMessageSourceAccessor().getMessage("database.update.exception"));
            }
        }

        command.setPojo(this.userService.findById(SecurityUtils.getLoginUserId()));

        List<UserGroupDTO> userGroups = this.userGroupService.findAll();
        mav.addObject("userGroups", userGroups);

        mav.addObject(Constants.FORM_MODEL_KEY, command);

        return mav;
    }
}
