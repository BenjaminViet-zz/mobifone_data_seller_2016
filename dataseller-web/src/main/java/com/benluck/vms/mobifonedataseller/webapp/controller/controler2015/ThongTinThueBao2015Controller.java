package com.benluck.vms.mobifonedataseller.webapp.controller.controler2015;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.core.business.ThongTinThueBaoManagementLocalBean;
import com.benluck.vms.mobifonedataseller.editor.CustomDateEditor;
import com.benluck.vms.mobifonedataseller.util.RequestUtil;
import com.benluck.vms.mobifonedataseller.webapp.command.command2014.ThongTinThueBaoCommand;
import com.benluck.vms.mobifonedataseller.webapp.controller.DiemGiaoDichController;
import com.benluck.vms.mobifonedataseller.webapp.validator.ThongTinThueBaoValidator;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: HP
 * Date: 2/6/15
 * Time: 9:32 AM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class ThongTinThueBao2015Controller extends ApplicationObjectSupport {
    private Log log = LogFactory.getLog(DiemGiaoDichController.class);

    @Autowired
    private ThongTinThueBaoValidator validator;
    @Autowired
    private ThongTinThueBaoManagementLocalBean thongTinThueBaoManagementLocalBean;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor("dd/MM/yyyy"));
    }

    @RequestMapping(value = {"/tongdai/thuebaophattrienmoi/tracuuthongtinthuebao.html", "/admin/thuebaophattrienmoi/tracuuthongtinthuebao.html",
            "/chinhanh/thuebaophattrienmoi/tracuuthongtinthuebao.html"})
    public ModelAndView traCuuThongTinThueBao(@ModelAttribute(value = Constants.FORM_MODEL_KEY) ThongTinThueBaoCommand command,
                                              BindingResult bindingResult,
                                              HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/2015/thuebaophattrienmoi/admin/tracuuthongtinthuebao");
        String crudaction = command.getCrudaction();
        if (StringUtils.isNotBlank(crudaction)) {
            if (crudaction.equals("search")) {
                executeSearch(command, request);
                if (command.getTotalItems() != 0) {
                    mav.addObject(Constants.ALERT_TYPE, "success");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("thongtinthuebao.msg.thue_bao_hop_le"));
                } else if (command.getTotalItems() == 0) {
                    mav.addObject(Constants.ALERT_TYPE, "danger");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("thongtinthuebao.msg.thue_bao_khong_hop_le"));
                }
            }
        }

        mav.addObject(Constants.LIST_MODEL_KEY, command);
        mav.addObject("page", command.getPage() - 1);

        return mav;
    }


    private void executeSearch(ThongTinThueBaoCommand bean, HttpServletRequest request) {
        RequestUtil.initSearchBean(request, bean);

        Object[] results = this.thongTinThueBaoManagementLocalBean.search_tbptm(bean.getThue_bao(), bean.getSortExpression(), bean.getSortDirection(), bean.getFirstItem(), bean.getMaxPageItems());
//        bean.setListResult((List<ThongTinThueBaoMaPhieuDTO>)results[0]);
        bean.setTotalItems(Integer.valueOf(results[0].toString()));
    }
}
