
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.ejb.DuplicateKeyException;
import javax.ejb.ObjectNotFoundException;
import javax.ejb.RemoveException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController extends ApplicationObjectSupport {

    @Autowired
    private UserManagementLocalBean usersManagementLocalBean;
    @Autowired
    private UserGroupManagementLocalBean userGroupManagementLocalBean;
    @Autowired
    private UserValidator validator;

    private Log log = LogFactory.getLog(UserController.class);

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(UserDTO.class, new PojoEditor(UserDTO.class, "userId", Long.class));
        binder.registerCustomEditor(UserGroupDTO.class, new PojoEditor(UserGroupDTO.class, "userGroupId", Long.class));

    }

    @RequestMapping("/admin/userList.html")
	public ModelAndView list(@ModelAttribute(value = Constants.FORM_MODEL_KEY)UserCommand command, HttpServletRequest request) throws RemoveException {
        ModelAndView mav = new ModelAndView("/user/list");
        String crudaction = command.getCrudaction();
        if (StringUtils.isNotBlank(crudaction)){
            if(crudaction.equals("delete")){
                if(command.getPojo().getUserId() != null){
                    boolean hasRelatedToData = false;
                    if(!hasRelatedToData){
                        try{
                            this.usersManagementLocalBean.deleteById(command.getPojo().getUserId());
                            mav.addObject(Constants.ALERT_TYPE, "success");
                            mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("admin.user.delete_successfully"));
                        }catch (Exception e){
                            mav.addObject(Constants.ALERT_TYPE, "info");
                            mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("admin.user.can_not_delete_user"));
                        }
                    }else{
                        mav.addObject(Constants.ALERT_TYPE, "info");
                        mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("admin.user.msg.no_user_id"));
                    }
                }
            }else if (crudaction.equals("find")){
                executeSearch(command, request);
            }
        }
        executeSearch(command, request);

        List<UserGroupDTO> userGroupList = this.userGroupManagementLocalBean.findAll4Access();
        mav.addObject("userGroupList", userGroupList);
        mav.addObject("page", command.getPage() - 1);
        mav.addObject(Constants.LIST_MODEL_KEY, command);
        return mav;
	}

    @RequestMapping(value = {"/admin/editUserInfo.html"})
    public ModelAndView edit(@ModelAttribute(Constants.FORM_MODEL_KEY)UserCommand command,
                             BindingResult bindingResult) throws DuplicateKeyException {
        ModelAndView mav = new ModelAndView("user/edit");
        String crudaction = command.getCrudaction();
        UserDTO pojo = command.getPojo();
        if (StringUtils.isNotBlank(crudaction)){
            if(crudaction.equals("insert-update")){
                try{
                    validator.validate(command, bindingResult);
                    if (!bindingResult.hasErrors()){
                        if (pojo.getUserId() == null ){
                            pojo = this.usersManagementLocalBean.addItem(command.getPojo());
                            mav.addObject(Constants.ALERT_TYPE, "success");
                            mav.addObject("messageResponse", this.getMessageSourceAccessor().getMessage("database.add.successful"));
                        } else {
                            pojo = this.usersManagementLocalBean.updateItem(command.getPojo());
                            mav.addObject(Constants.ALERT_TYPE, "success");
                            mav.addObject("messageResponse", this.getMessageSourceAccessor().getMessage("database.update.successful"));
                        }
                        command.setPojo(pojo);
                    }else{
                        pojo.setPassword(DesEncrypterUtils.getInstance().decrypt(pojo.getPassword()));
                    }

                } catch (Exception e){
                    log.error(e.getMessage(), e);
                    mav.addObject(Constants.ALERT_TYPE, "danger");
                    mav.addObject("messageResponse", this.getMessageSourceAccessor().getMessage("database.exception.keynotfound"));
                }
            }

        }
        if (pojo.getUserId()!= null){
            try{
                pojo = this.usersManagementLocalBean.findById(command.getPojo().getUserId());
                pojo.setPassword(DesEncrypterUtils.getInstance().decrypt(pojo.getPassword()));
                command.setPojo(pojo);
            } catch (Exception e){
                log.error(e.getMessage(), e);
            }
        }
        List<UserGroupDTO> userGroups = this.userGroupManagementLocalBean.findAll4Access();
        mav.addObject("userGroups", userGroups);
        mav.addObject(Constants.FORM_MODEL_KEY, command);
        return mav;
    }

    /**
     * Perform searching related data for this view and put them to the model.
     * @param bean
     * @param request
     */
    private void executeSearch(UserCommand bean, HttpServletRequest request){
        RequestUtil.initSearchBean(request, bean);
        Map<String, Object> properties = new HashMap<String, Object>();
        UserDTO pojo = bean.getPojo();
        if (StringUtils.isNotBlank(pojo.getUserName())){
            properties.put("userName", pojo.getUserName());
        }
        if (StringUtils.isNotBlank(pojo.getDisplayName())){
            properties.put("displayName", pojo.getDisplayName());
        }
        Object [] result = this.usersManagementLocalBean.search(properties, bean.getSortExpression(), bean.getSortDirection(), bean.getFirstItem(), bean.getMaxPageItems());
        bean.setTotalItems(Integer.valueOf(result[0].toString()));
        bean.setListResult((List<UserDTO>)result[1]);
        bean.setMaxPageItems(bean.getMaxPageItems());
    }

    @RequestMapping(value ={"/admin/thongtincanhan.html","/cuahangmobifone/thongtincanhan.html","/chinhanh/thongtincanhan.html","/tongdai/thongtincanhan.html"})
    public ModelAndView editProfile(@ModelAttribute(Constants.FORM_MODEL_KEY)UserCommand command, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) throws ObjectNotFoundException {
        ModelAndView mav = new ModelAndView("/user/editprofile");
        String crudaction = command.getCrudaction();
        UserDTO pojo = command.getPojo();
        Long userId = SecurityUtils.getLoginUserId();
        if (StringUtils.isNotBlank(crudaction) && crudaction.equals("insert-update")){
            try{
                validator.validate(command, bindingResult);
                if (!bindingResult.hasErrors()){
                    this.usersManagementLocalBean.updateProfile(command.getPojo());
                    mav.addObject(Constants.ALERT_TYPE, "success");
                    mav.addObject("messageResponse", this.getMessageSourceAccessor().getMessage("database.update.successful"));
                }
            } catch (Exception e) {
                mav.addObject(Constants.ALERT_TYPE, "danger");
                mav.addObject("messageResponse", this.getMessageSourceAccessor().getMessage("database.update.exception"));
            }
        }
        if (userId != null && userId > 0){
            try{
                pojo = this.usersManagementLocalBean.findById(userId);
                pojo.setPassword(DesEncrypterUtils.getInstance().decrypt(pojo.getPassword()));
                command.setPojo(pojo);
            }catch (Exception e){
                log.error(e.getMessage(), e);
            }
        }
        command.setPojo(pojo);
        mav.addObject(Constants.FORM_MODEL_KEY, command);

        return mav;
    }
}

