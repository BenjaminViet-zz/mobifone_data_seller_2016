package com.benluck.vms.mobifonedataseller.webapp.controller.controler2015;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.core.business.PromPackageManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015.PromPackageDTO;
import com.benluck.vms.mobifonedataseller.editor.CustomDateEditor;
import com.benluck.vms.mobifonedataseller.webapp.command.command2015.DanhMucGoiCuocCommand;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.ejb.RemoveException;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: HP
 * Date: 2/25/15
 * Time: 9:31 AM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class DanhMucGoiCuocContronller extends ApplicationObjectSupport {
    private transient final Log log = LogFactory.getLog(getClass());
    @Autowired
    private PromPackageManagementLocalBean promPackageService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor());
    }

    @RequestMapping(value = {"/admin/thuebaophattrienmoi/danhmucgoicuoc.html","/chinhanh/thuebaophattrienmoi/danhmucgoicuoc.html"})
    public ModelAndView danhMucGoiCuoc(@ModelAttribute(value = Constants.FORM_MODEL_KEY)DanhMucGoiCuocCommand bean, HttpServletRequest request)throws RemoveException {
        ModelAndView mav = new ModelAndView("/2015/thuebaophattrienmoi/admin/danhmucgoicuoc");

        List<PromPackageDTO> goiCuocList = this.promPackageService.findAll();

        bean.setListResult(goiCuocList);
        bean.setTotalItems(5);

        mav.addObject(Constants.LIST_MODEL_KEY, bean);
        mav.addObject("page", bean.getPage() - 1);

        return mav;
    }
}
