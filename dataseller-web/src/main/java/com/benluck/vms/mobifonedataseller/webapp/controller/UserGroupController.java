package com.benluck.vms.mobifonedataseller.webapp.controller;


import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.core.business.PermissionManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.UserGroupManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.PermissionDTO;
import com.benluck.vms.mobifonedataseller.core.dto.UserGroupDTO;
import com.benluck.vms.mobifonedataseller.editor.CustomDateEditor;
import com.benluck.vms.mobifonedataseller.security.util.SecurityUtils;
import com.benluck.vms.mobifonedataseller.util.RequestUtil;
import com.benluck.vms.mobifonedataseller.util.WebCommonUtil;
import com.benluck.vms.mobifonedataseller.webapp.command.UserGroupCommand;
import com.benluck.vms.mobifonedataseller.webapp.exception.ForBiddenException;
import com.benluck.vms.mobifonedataseller.webapp.validator.UserGroupValidator;
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

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
public class UserGroupController extends ApplicationObjectSupport {
    private transient final Logger logger = Logger.getLogger(UserGroupController.class);

    @Autowired
    private UserGroupManagementLocalBean userGroupService;
    @Autowired
    private
    PermissionManagementLocalBean permissionService;

    @Autowired
    private UserGroupValidator validator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor());
    }

    @RequestMapping(value = {"/admin/usergroup/edit.html", "/admin/usergroup/add.html",
                            "/user/usergroup/edit.html", "/user/usergroup/add.html",
                            "/khdn/usergroup/edit.html", "/khdn/usergroup/add.html"})
    public ModelAndView edit(@ModelAttribute(Constants.FORM_MODEL_KEY) UserGroupCommand command,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {

        if(!SecurityUtils.userHasAuthority(Constants.USER_GROUP_MANAGER)){
            logger.warn("User: " + SecurityUtils.getPrincipal().getDisplayName() + ", userId: " + SecurityUtils.getLoginUserId() + " is trying to access non-authorized page: " + "/usergroup/add.html or /usergroup/edit.html page. ACCESS DENIED FOR BIDDEN!");
            throw new ForBiddenException();
        }

        ModelAndView mav = new ModelAndView("/admin/usergroup/edit");
        String crudaction = command.getCrudaction();
        UserGroupDTO pojo = command.getPojo();

        List<PermissionDTO> permissionDTOList = this.permissionService.findAll();

        try {
            if(StringUtils.isNotBlank(crudaction) && crudaction.equals("insert-update")) {
                validator.validate(command, bindingResult);
                if(!bindingResult.hasErrors()) {
                    if(pojo.getUserGroupId() != null && pojo.getUserGroupId() > 0) {
                        this.userGroupService.updateItem(command.getPojo(), command.getCheckList());
                        redirectAttributes.addFlashAttribute(Constants.ALERT_TYPE, "success");
                        redirectAttributes.addFlashAttribute(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("database.update_item_successfully", new Object[]{this.getMessageSourceAccessor().getMessage("usergroup.label.group")}));
                    } else {
                        this.userGroupService.addItem(command.getPojo(), command.getCheckList());
                        redirectAttributes.addFlashAttribute(Constants.ALERT_TYPE, "success");
                        redirectAttributes.addFlashAttribute(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("database.add_item_successfully", new Object[]{this.getMessageSourceAccessor().getMessage("usergroup.label.group")}));
                    }
                    return new ModelAndView(new StringBuilder("redirect:").append(WebCommonUtil.getPrefixUrl()).append("/usergroup/list.html").toString());
                }
            }else if (command.getPojo().getUserGroupId() != null){
                command.setPojo(this.userGroupService.findAndFetchPermissionListById(command.getPojo().getUserGroupId()));

                if(command.getPojo().getPermissionList() != null && command.getPojo().getPermissionList().size() > 0){
                    for (PermissionDTO permissionDTO : permissionDTOList){
                        for (PermissionDTO dbPermissionDTO : command.getPojo().getPermissionList()){
                            if(permissionDTO.getPermissionId().equals(dbPermissionDTO.getPermissionId())){
                                permissionDTO.setChecked(true);
                                break;
                            }
                        }
                    }
                }
            }
        }catch(Exception e) {
            logger.error(e.getMessage(), e);
            mav.addObject(Constants.ALERT_TYPE, "danger");
            mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("general.exception.msg"));
        }

        mav.addObject("permissionDTOList", permissionDTOList);
        mav.addObject(Constants.FORM_MODEL_KEY, command);
        return mav;
    }

    @RequestMapping(value = {"/admin/usergroup/list.html", "/user/usergroup/list.html", "/khdn/usergroup/list.html"})
    public ModelAndView list(@ModelAttribute(Constants.FORM_MODEL_KEY) UserGroupCommand command,
                             HttpServletRequest request,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes){
        ModelAndView mav = new ModelAndView("/admin/usergroup/list");

        if(!SecurityUtils.userHasAuthority(Constants.USERGROUP_ADMIN) && !SecurityUtils.userHasAuthority(Constants.USER_GROUP_MANAGER)){
            logger.warn("User: " + SecurityUtils.getPrincipal().getDisplayName() + ", userId: " + SecurityUtils.getLoginUserId() + " is trying to access non-authorized page: " + "/usergroup/list.html page. ACCESS DENIED FOR BIDDEN!");
            throw new ForBiddenException();
        }

        String action = command.getCrudaction();
        if (StringUtils.isNotBlank(action)){
            if(action.equals("delete")){
                if(command.getPojo().getUserGroupId() != null){
                    validator.validate(command, bindingResult);
                    if(StringUtils.isBlank(command.getErrorMessage())){
                        try{
                            this.userGroupService.deleteItem(command.getPojo().getUserGroupId());
                            redirectAttributes.addFlashAttribute(Constants.ALERT_TYPE, "success");
                            redirectAttributes.addFlashAttribute(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("admin.user_group.delete_successfully"));

                            return new ModelAndView(new StringBuilder("redirect:").append(WebCommonUtil.getPrefixUrl()).append("/usergroup/list.html").toString());
                        }catch (Exception e){
                            mav.addObject(Constants.ALERT_TYPE, "info");
                            mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("admin.user_group.can_not_delete_user_group"));
                        }
                    }else{
                        mav.addObject(Constants.ALERT_TYPE, "danger");
                        mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, command.getErrorMessage());
                    }
                }
            }
        }

        executeSearch(command, request);
        mav.addObject("page", command.getPage() - 1);
        mav.addObject(Constants.LIST_MODEL_KEY, command);
        return mav;
    }

    /**
     * Fetch UserGroup list by properties
     * @param command
     * @param command
     */
    private void executeSearch(UserGroupCommand command,HttpServletRequest request){
        RequestUtil.initSearchBean(request, command);

        Object [] result = this.userGroupService.searchByProperties(new HashMap<String, Object>(), command.getSortExpression(), command.getSortDirection(), command.getFirstItem(), command.getMaxPageItems());
        command.setTotalItems(Integer.valueOf(result[0].toString()));
        command.setListResult((List<UserGroupDTO>)result[1]);
        command.setMaxPageItems(command.getMaxPageItems());
    }
}
