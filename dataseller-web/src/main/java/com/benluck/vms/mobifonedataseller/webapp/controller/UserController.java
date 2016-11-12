
package com.benluck.vms.mobifonedataseller.webapp.controller;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.security.DesEncrypterUtils;
import com.benluck.vms.mobifonedataseller.core.business.UserGroupManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.UserManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.UserDTO;
import com.benluck.vms.mobifonedataseller.core.dto.UserGroupDTO;
import com.benluck.vms.mobifonedataseller.editor.PojoEditor;
import com.benluck.vms.mobifonedataseller.security.util.SecurityUtils;
import com.benluck.vms.mobifonedataseller.util.RequestUtil;
import com.benluck.vms.mobifonedataseller.webapp.command.UserCommand;
import com.benluck.vms.mobifonedataseller.webapp.validator.UserValidator;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.ejb.DuplicateKeyException;
import javax.ejb.ObjectNotFoundException;
import javax.ejb.RemoveException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController extends ApplicationObjectSupport {

    @Autowired
    private UserManagementLocalBean userService;
    @Autowired
    private UserGroupManagementLocalBean userGroupService;
    @Autowired
    private UserValidator validator;

    private Log log = LogFactory.getLog(UserController.class);

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(UserDTO.class, new PojoEditor(UserDTO.class, "userId", Long.class));
        binder.registerCustomEditor(UserGroupDTO.class, new PojoEditor(UserGroupDTO.class, "userGroupId", Long.class));

    }

    @RequestMapping("/admin/user/list.html")
	public ModelAndView list(@ModelAttribute(value = Constants.FORM_MODEL_KEY)UserCommand command,
                             HttpServletRequest request,
                             BindingResult bindingResult) throws RemoveException {
        ModelAndView mav = new ModelAndView("/admin/user/list");
        String action = command.getCrudaction();
        if (StringUtils.isNotBlank(action)){
            if(action.equals("delete")){
                if(command.getPojo().getUserId() != null){
                    validator.validate(command, bindingResult);
                    if(StringUtils.isBlank(command.getErrorMessage())){
                        try{
                            this.userService.deleteItemById(command.getPojo().getUserId());
                            mav.addObject(Constants.ALERT_TYPE, "success");
                            mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("admin.user.delete_successfully"));
                        }catch (Exception e){
                            mav.addObject(Constants.ALERT_TYPE, "info");
                            mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("admin.user.can_not_delete_user"));
                        }
                    }else{
                        mav.addObject(Constants.ALERT_TYPE, "danger");
                        mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, command.getErrorMessage());
                    }
                }
            }else if (action.equals(Constants.ACTION_SEARCH)){
                executeSearch(mav, command, request);
            }
        }

        preferenceData(mav);

        mav.addObject("page", command.getPage() - 1);
        mav.addObject(Constants.LIST_MODEL_KEY, command);
        return mav;
	}

    private void preferenceData(ModelAndView mav){
        List<UserGroupDTO> userGroups = this.userGroupService.findAll();
        mav.addObject("userGroups", userGroups);
    }

    @RequestMapping(value = {"/admin/user/add.html", "/admin/user/edit.html"})
    public ModelAndView edit(@ModelAttribute(Constants.FORM_MODEL_KEY)UserCommand command,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView("/admin/user/edit");
        String crudaction = command.getCrudaction();
        UserDTO pojo = command.getPojo();
        boolean notSystemUser = true;

        try{
            if (StringUtils.isNotBlank(crudaction)){
                if(crudaction.equals("insert-update")){
                    validator.validate(command, bindingResult);
                    if (!bindingResult.hasErrors()){
                        if (pojo.getUserId() == null ){
                            this.userService.addItem(command.getPojo());
                            redirectAttributes.addFlashAttribute(Constants.ALERT_TYPE, "success");
                            redirectAttributes.addFlashAttribute("messageResponse", this.getMessageSourceAccessor().getMessage("database.add.successful"));
                        } else {
                            this.userService.updateItem(command.getPojo());
                            redirectAttributes.addFlashAttribute(Constants.ALERT_TYPE, "success");
                            redirectAttributes.addFlashAttribute("messageResponse", this.getMessageSourceAccessor().getMessage("database.update.successful"));
                        }
                        return new ModelAndView("redirect:/admin/user/list.html");
                    }
                }
            }else if(pojo.getUserId() != null){
                command.setPojo(this.userService.findById(command.getPojo().getUserId()));

                if(command.getPojo().getUserGroup().getCode().equals(Constants.USERGROUP_ADMIN)
                        && command.getPojo().getUserName().equalsIgnoreCase("admin")){
                    notSystemUser = false;
                }
            }
        }catch (ObjectNotFoundException one){
            logger.error("Can not get profile of UserId: " + pojo.getUserId());
            mav.addObject(Constants.ALERT_TYPE, "danger");
            mav.addObject("messageResponse", this.getMessageSourceAccessor().getMessage("database.exception.keynotfound"));
        }catch (DuplicateKeyException dle){
            logger.error("Duplicated UserId: " + pojo.getUserId());
            mav.addObject(Constants.ALERT_TYPE, "danger");
            mav.addObject("messageResponse", this.getMessageSourceAccessor().getMessage("database.exception.duplicated_id"));
        }

        mav.addObject("notSystemUser", notSystemUser);
        preferenceData(mav);
        return mav;
    }


    /**
     * Fetch uset list by properties
     * @param command
     * @param request
     */
    private void executeSearch(ModelAndView mav, UserCommand command, HttpServletRequest request){
        RequestUtil.initSearchBean(request, command);
        Map<String, Object> properties = new HashMap<String, Object>();
        UserDTO pojo = command.getPojo();
        if (StringUtils.isNotBlank(pojo.getUserName())){
            properties.put("userName", pojo.getUserName());
        }
        if (StringUtils.isNotBlank(pojo.getDisplayName())){
            properties.put("displayName", pojo.getDisplayName());
        }
        if (pojo.getUserGroup() != null && pojo.getUserGroup().getUserGroupId() != null){
            properties.put("userGroup.userGroupId", pojo.getUserGroup().getUserGroupId());
        }

        StringBuilder whereClause = new StringBuilder();
        whereClause.append("A.userId != " + SecurityUtils.getLoginUserId());

        Object [] result = this.userService.searchByProperties(properties, command.getSortExpression(), command.getSortDirection(), command.getFirstItem(), command.getMaxPageItems(), whereClause.toString());
        command.setTotalItems(Integer.valueOf(result[0].toString()));
        command.setListResult((List<UserDTO>)result[1]);
        command.setMaxPageItems(command.getMaxPageItems());
    }
}

