package com.benluck.vms.mobifonedataseller.webapp.controller;

import com.benluck.vms.mobifonedataseller.core.business.DistrictManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.DistrictDTO;
import com.benluck.vms.mobifonedataseller.common.utils.PromotionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.ejb.ObjectNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: HP
 * Date: 2/13/15
 * Time: 4:53 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class DistrictController extends ApplicationObjectSupport {
    @Autowired
    private DistrictManagementLocalBean districtManagementLocalBean;

    @RequestMapping("/ajax/thuebaotm/getByBranchId.html")
    public @ResponseBody
    Map ajaxGetByBranchId(@RequestParam(value = "chiNhanh") Long branchId) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (branchId != null) {
            List<DistrictDTO> districtList = this.districtManagementLocalBean.findByBranchId_tbptm(branchId);
            map.put("districtList", districtList);
        } else {
            List<DistrictDTO> districtList = this.districtManagementLocalBean.findAll_tbptm();
            map.put("districtList", districtList);
        }
        return map;
    }

    @RequestMapping("/ajax/qStudent/getByBranchId.html")
    public @ResponseBody
    Map ajaxGetByBranchId_qStudent(@RequestParam(value = "chiNhanh") Long branchId) throws ObjectNotFoundException {
        Map<String, Object> map = new HashMap<String, Object>();
        if (branchId != null) {
            List<DistrictDTO> districtList = this.districtManagementLocalBean.findByBranchIdAndCTCode(branchId, PromotionEnum.Q_STUDENT_Q_TEEN_2015.getCode());
            map.put("districtList", districtList);
        } else {
            List<DistrictDTO> districtList = this.districtManagementLocalBean.findAllByChuongTrinhCode(PromotionEnum.Q_STUDENT_Q_TEEN_2015.getCode());
            map.put("districtList", districtList);
        }
        return map;
    }
}
