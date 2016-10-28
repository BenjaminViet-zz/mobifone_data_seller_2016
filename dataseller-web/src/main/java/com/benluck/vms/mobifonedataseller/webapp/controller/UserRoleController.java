package com.benluck.vms.mobifonedataseller.webapp.controller;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.core.business.RoleManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.UserManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.UserRoleManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.RoleDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.UserRoleDTO;
import com.benluck.vms.mobifonedataseller.editor.CustomDateEditor;
import com.benluck.vms.mobifonedataseller.webapp.command.command2014.RoleCommand;
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
        String crudaction = command.getCrudaction();
        if(StringUtils.isNotBlank(crudaction) && "save-update".equals(crudaction))
        {
            if((command.getCheckList()!=null && command.getCheckList().length> 0)|| (command.getDeleteList() != null && command.getDeleteList().length> 0))
            {
                UserRoleDTO userRoleDTO = new UserRoleDTO();
                userRoleDTO.setUserID(command.getUserID());
                userRoleDTO.setRoleID(command.getCheckList());
                userRoleDTO.setDeleteRole(command.getDeleteList());
                userRoleManagementLocalBean.updateItem(userRoleDTO);
                mav.addObject("messageResponse", this.getMessageSourceAccessor().getMessage("database.update.successful"));
            }
            else
            {
                mav.addObject("messageResponse", this.getMessageSourceAccessor().getMessage("userProfile.nothingtoupdate"));
            }
        }
        List<RoleDTO> listRole = null;
        if(command.getUserID() != null){
             listRole = userRoleManagementLocalBean.findRoleOfUser(command.getUserID());
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
