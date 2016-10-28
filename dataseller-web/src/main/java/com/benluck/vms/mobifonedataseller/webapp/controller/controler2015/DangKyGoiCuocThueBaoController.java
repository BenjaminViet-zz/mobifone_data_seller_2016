package com.benluck.vms.mobifonedataseller.webapp.controller.controler2015;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.DangKyGoiCuoc2015DTO;
import com.benluck.vms.mobifonedataseller.webapp.command.command2014.DangKyGoiCuoc2015Command;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: HP
 * Date: 2/6/15
 * Time: 2:51 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class DangKyGoiCuocThueBaoController extends ApplicationObjectSupport{
    @RequestMapping(value = "/admin/dangkygoicuoc2015.html")
    public ModelAndView traCuuThongTinThueBao(@ModelAttribute(value = Constants.FORM_MODEL_KEY)DangKyGoiCuoc2015Command command,
                                              HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/2015/thuebaophattrienmoi/admin/dangkygoicuoc/edit");
//        List<ChiNhanhDTO> chiNhanhList = this.chiNhanhManagementLocalBean.findAll_tdcg();
//        mav.addObject("chiNhanhList", chiNhanhList);

//        if(command.getPojo().getChiNhanhId() != null){
//            List<DepartmentDTO> departmentList = this.departmentManagementLocalBean.findDepartmentListByChiNhanhId(command.getPojo().getChiNhanhId());
//            mav.addObject("departmentList", departmentList);
//        }
//        List<UserGroupDTO> userGroupList = this.userGroupManagementLocalBean.findAll4Access();
//        mav.addObject("userGroupList", userGroupList);

        List<DangKyGoiCuoc2015DTO> dangKyGoiCuoc2015List = new ArrayList<DangKyGoiCuoc2015DTO>();

        DangKyGoiCuoc2015DTO dto = new DangKyGoiCuoc2015DTO();
        dto.setGoiCuoc("Goi Cuoc1");

        DangKyGoiCuoc2015DTO dto1 = new DangKyGoiCuoc2015DTO();
        dto1.setGoiCuoc("Goi Cuoc2");

        DangKyGoiCuoc2015DTO dto2 = new DangKyGoiCuoc2015DTO();
        dto2.setGoiCuoc("Goi Cuoc3");
        dangKyGoiCuoc2015List.add(dto);
        dangKyGoiCuoc2015List.add(dto1);
        dangKyGoiCuoc2015List.add(dto2);
        mav.addObject("dangKyGoiCuoc2015List", dangKyGoiCuoc2015List);
        mav.addObject("page", command.getPage() - 1);
        mav.addObject(Constants.LIST_MODEL_KEY, command);
        return mav;
    }
}
