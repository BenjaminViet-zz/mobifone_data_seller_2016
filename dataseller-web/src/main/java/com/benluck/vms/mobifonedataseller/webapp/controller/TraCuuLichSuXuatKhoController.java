package com.benluck.vms.mobifonedataseller.webapp.controller;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.utils.DateUtil;
import com.benluck.vms.mobifonedataseller.core.business.ChiNhanhManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.DepartmentManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.GiftAgentTransferHistoryManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.ChiNhanhDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.DepartmentDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.GiftAgentTransferHistoryDTO;
import com.benluck.vms.mobifonedataseller.editor.CustomDateEditor;
import com.benluck.vms.mobifonedataseller.security.util.SecurityUtils;
import com.benluck.vms.mobifonedataseller.util.RequestUtil;
import com.benluck.vms.mobifonedataseller.webapp.command.command2014.GiftAgentTransferHistoryCommand;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 11/5/14
 * Time: 8:23 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class TraCuuLichSuXuatKhoController extends ApplicationObjectSupport{
    private Log log = LogFactory.getLog(TraCuuLichSuXuatKhoController.class);

    @Autowired
    private GiftAgentTransferHistoryManagementLocalBean giftAgentTransferHistoryManagementLocalBean;
    @Autowired
    private ChiNhanhManagementLocalBean chiNhanhManagementLocalBean;
    @Autowired
    private DepartmentManagementLocalBean departmentManagementLocalBean;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor("dd/MM/yyyy"));
    }

    @RequestMapping(value = {"/cuahangmobifone/lichsuxuatkho.html", "/admin/lichsuxuatkho.html", "/chinhanh/lichsuxuatkho.html"})
    public ModelAndView lichSuXuatKho(@ModelAttribute(value = Constants.FORM_MODEL_KEY)GiftAgentTransferHistoryCommand command,
                                      HttpServletRequest request){
        ModelAndView mav = new ModelAndView("/cuahangmobifone/lichsuxuatkho");
        String crudaction = command.getCrudaction();
        if(StringUtils.isNotBlank(crudaction)){
            if(crudaction.equals("search")){
                RequestUtil.initSearchBean(request, command);
                convert2Timestamp(command);
                Long departmentId = SecurityUtils.getPrincipal().getDepartmentId();
                if(!SecurityUtils.userHasAuthority(Constants.NHANVIEN_ROLE)){
                    departmentId = command.getDepartmentId();
                }
                Object[] resultObject = this.giftAgentTransferHistoryManagementLocalBean.search_tdcg(departmentId, command.getFromDateTime(), command.getToDateTime(), command.getFirstItem(), command.getMaxPageItems(), command.getSortExpression(), command.getSortDirection());
                command.setTotalItems(Integer.valueOf(resultObject[0].toString()));
                command.setListResult((List<GiftAgentTransferHistoryDTO>)resultObject[1]);
                mav.addObject(Constants.LIST_MODEL_KEY, command);
                if(command.getTotalItems() == 0){
                    mav.addObject(Constants.ALERT_TYPE, "info");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("cuahanggiaodich.xuat_kho.msg.no_record_found"));
                }
            }
        }
        referenceData(command, mav);
        return mav;
    }

    /**
     * Fetch more related data base on user role for this view and put them to the model.
     * @param command
     * @param mav
     */
    private void referenceData(GiftAgentTransferHistoryCommand command, ModelAndView mav){
        if(SecurityUtils.userHasAuthority(Constants.ADMIN_ROLE)){
            List<ChiNhanhDTO> chiNhanhList = this.chiNhanhManagementLocalBean.findAll();
            mav.addObject("chiNhanhList", chiNhanhList);

            if(command.getChiNhanhId() != null){
                List<DepartmentDTO> departmentList = this.departmentManagementLocalBean.findDepartmentListByChiNhanhId(command.getChiNhanhId());
                mav.addObject("departmentList", departmentList);
            }
        }else if(SecurityUtils.userHasAuthority(Constants.CHINHANH_ROLE)){
            try{
                ChiNhanhDTO chiNhanhDTO = this.chiNhanhManagementLocalBean.findById(SecurityUtils.getPrincipal().getMappingDBLinkBranchId());
                List<ChiNhanhDTO> chiNhanhList = new ArrayList<ChiNhanhDTO>();
                chiNhanhList.add(chiNhanhDTO);
                mav.addObject("chiNhanhList", chiNhanhList);
            }catch (Exception e){}
            List<DepartmentDTO> departmentList = this.departmentManagementLocalBean.findDepartmentListByChiNhanhId(SecurityUtils.getPrincipal().getMappingDBLinkBranchId());
            mav.addObject("departmentList", departmentList);
        }
    }

    /**
     * Get Date value and format to Timestamp.
     * @param command
     */
    private void convert2Timestamp(GiftAgentTransferHistoryCommand command){
        if(command.getFromDate() != null){
            command.setFromDateTime(DateUtil.dateToTimestamp(command.getFromDate(), Constants.VI_DATE_FORMAT));
        }
        if(command.getToDate() != null){
            command.setToDateTime(DateUtil.dateToTimestamp(command.getToDate(), Constants.VI_DATE_FORMAT));
        }
    }
}