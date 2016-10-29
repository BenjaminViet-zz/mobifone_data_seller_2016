package com.benluck.vms.mobifonedataseller.webapp.controller;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.core.business.RoleManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.UserManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.UserRoleManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.RoleDTO;
import com.benluck.vms.mobifonedataseller.core.dto.UserRoleDTO;
import com.benluck.vms.mobifonedataseller.editor.CustomDateEditor;
import com.benluck.vms.mobifonedataseller.webapp.command.RoleCommand;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.ejb.DuplicateKeyException;
import javax.ejb.ObjectNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Controller
public class UserRoleController extends ApplicationObjectSupport {
    private transient final Log log = LogFactory.getLog(getClass());

    @Autowired
    private UserRoleManagementLocalBean userRoleManagementLocalBean;

    @Autowired
    private UserManagementLocalBean usersManagementLocalBean;

    @Autowired
    private RoleManagementLocalBean roleManagementLocalBean;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor());
    }

    @RequestMapping(value={"/userRole/list.html"})
    public ModelAndView list(RoleCommand command, HttpServletRequest request) throws ObjectNotFoundException, DuplicateKeyException {
        ModelAndView mav = new ModelAndView("/userRole/list");
        List<RoleDTO> listRole = null;
        if(command.getSelectedUserId() != null){
             listRole = userRoleManagementLocalBean.findRoleOfUser(command.getSelectedUserId());
        }
        mav.addObject("listChecked", listRole);
        referenceData(mav, command);
        mav.addObject(Constants.LIST_MODEL_KEY, command);
        return mav;
    }

    private void referenceData(ModelAndView mav,RoleCommand command) {
        List<RoleDTO> listRole = this.roleManagementLocalBean.findAll();
        command.setListResult(listRole);
        command.setTotalItems(listRole.size());
    }
}
