package com.benluck.vms.mobifonedataseller.webapp.controller;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.core.business.BranchManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.BranchMappingManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.ChiNhanhManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.DMChuongTrinhManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BranchDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BranchMappingDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.ChiNhanhDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.PromotionDTO;
import com.benluck.vms.mobifonedataseller.security.util.SecurityUtils;
import com.benluck.vms.mobifonedataseller.webapp.command.command2014.BranchMappingCommand;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.ejb.DuplicateKeyException;
import javax.ejb.ObjectNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 3/4/15
 * Time: 9:55 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class BranchMappingController extends ApplicationObjectSupport{
    private Log log = LogFactory.getLog(UserController.class);

    @Autowired
    private ChiNhanhManagementLocalBean chiNhanhService;
    @Autowired
    private DMChuongTrinhManagementLocalBean dmChuongTrinhService;
    @Autowired
    private BranchManagementLocalBean branchService;
    @Autowired
    BranchMappingManagementLocalBean branchMappingService;

    @RequestMapping("/admin/anhxachinhanh.html")
    public ModelAndView anhxachinhanh(@ModelAttribute(value = Constants.FORM_MODEL_KEY)BranchMappingCommand command,
                                      HttpServletRequest request) throws ObjectNotFoundException {
        ModelAndView mav = new ModelAndView("/admin/anhxachinhanh");
        String crudaction = command.getCrudaction();
        if(StringUtils.isNotBlank(crudaction) && crudaction.equals("insert-update")){
            PromotionDTO chuongTrinhDTO = new PromotionDTO();
            chuongTrinhDTO.setChuongTrinhId(command.getPojo().getChuongTrinh().getChuongTrinhId());
            for(BranchMappingDTO branchMappingDTO : command.getBranchMappingList()){
                branchMappingDTO.setChuongTrinh(chuongTrinhDTO);
            }
            try{
                this.branchMappingService.anhXaChiNhanh(SecurityUtils.getLoginUserId(), command.getBranchMappingList());
                mav.addObject(Constants.ALERT_TYPE, "info");
                mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("admin.anhxachinhanh.update_successfully"));
            }catch (DuplicateKeyException e){
                logger.error(e.getMessage(), e);
                mav.addObject(Constants.ALERT_TYPE, "danger");
                mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("admin.anhxachinhanh.update_exception"));
            }
        }
        referenceData(command, mav);
        return mav;
    }

    /**
     * Fetch more related data for this view and put them to model.
     * @param command
     * @param mav
     */
    private void referenceData(BranchMappingCommand command, ModelAndView mav){
        List<PromotionDTO> dmChuongTrinhList = dmChuongTrinhService.findAll();
        List<ChiNhanhDTO> chiNhanhList = this.chiNhanhService.findAll();
        List<BranchDTO> branchList = new ArrayList<BranchDTO>();
        List<BranchMappingDTO> branchMappingList = new ArrayList<BranchMappingDTO>();
        if (dmChuongTrinhList.size() > 0){
            try{
                if (command.getPojo().getChuongTrinh() != null && command.getPojo().getChuongTrinh().getChuongTrinhId() != null){
                    branchList = this.branchService.findByChuongTrinhId(command.getPojo().getChuongTrinh().getChuongTrinhId());
                    branchMappingList = this.branchMappingService.findByChuongTrinhId(command.getPojo().getChuongTrinh().getChuongTrinhId());
                }else{
                    branchList = this.branchService.findByChuongTrinhId(dmChuongTrinhList.get(0).getChuongTrinhId());
                    branchMappingList = this.branchMappingService.findByChuongTrinhId(dmChuongTrinhList.get(0).getChuongTrinhId());
                }
            }catch (ObjectNotFoundException oe){
                mav.addObject(Constants.ALERT_TYPE, "danger");
                mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("branchmapping.msg.no_branch_found"));
            }
        }

        mav.addObject("branchMappingList",branchMappingList);
        mav.addObject("dmChuongTrinhList",dmChuongTrinhList);
        mav.addObject("chiNhanhList", chiNhanhList);
        mav.addObject("branchList", branchList);
    }
}
