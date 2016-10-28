package com.benluck.vms.mobifonedataseller.webapp.controller.qStudentController;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.utils.DateUtil;
import com.benluck.vms.mobifonedataseller.core.business.SoDiemCTTichDiemManagementlocalBean;
import com.benluck.vms.mobifonedataseller.core.business.ThongTinThueBaoManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.CTTichDiemSoDiemDTO;
import com.benluck.vms.mobifonedataseller.core.dto.CTTichDiemSoDiemStatisticByMonthDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.ThongTinThueBaoMaPhieuDTO;
import com.benluck.vms.mobifonedataseller.editor.CustomDateEditor;
import com.benluck.vms.mobifonedataseller.util.RequestUtil;
import com.benluck.vms.mobifonedataseller.webapp.command.command2014.ThongTinThueBaoCommand;
import com.benluck.vms.mobifonedataseller.webapp.controller.DiemGiaoDichController;
import com.benluck.vms.mobifonedataseller.webapp.validator.validator2015.ThongTinThueBaoQStudentQTeenValidator;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: HP
 * Date: 4/1/15
 * Time: 5:36 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class TraCuuThongTinThueBaoQStudentController extends ApplicationObjectSupport {
    private Log log = LogFactory.getLog(DiemGiaoDichController.class);

    @Autowired
    private ThongTinThueBaoQStudentQTeenValidator validator;
    @Autowired
    private ThongTinThueBaoManagementLocalBean thongTinThueBaoManagementLocalBean;
    @Autowired
    private SoDiemCTTichDiemManagementlocalBean soDiemCTTichDiemManagementlocalBean;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor("dd/MM/yyyy"));
    }

    @RequestMapping(value = {"/tongdai/qstudent/tracuuthongtinthuebao.html"
                            ,"/cuahangmobifone/qstudent/tracuuthongtinthuebao.html"
                            ,"/admin/qstudent/tracuuthongtinthuebao.html"
                            ,"/chinhanh/qstudent/tracuuthongtinthuebao.html"})
    public ModelAndView tracuuthongtinthuebao(@ModelAttribute(value = Constants.FORM_MODEL_KEY)ThongTinThueBaoCommand command,
                                              BindingResult bindingResult,
                                              HttpServletRequest request){
        ModelAndView mav = new ModelAndView("/2015/q_student_and_q_teen/tracuuthongtinthuebao");
        String crudaction = command.getCrudaction();
        if(StringUtils.isNotBlank(crudaction) && crudaction.equals("search")){
            validator.validate(command, bindingResult);
            if(!bindingResult.hasErrors() && StringUtils.isBlank(command.getMessage())){
                try{
                    RequestUtil.initSearchBean(request, command);
                    Integer soDiemHientai = this.thongTinThueBaoManagementLocalBean.getSoDiemHienTai_qStudent(command.getThue_bao());
                    Integer soPhieuDaDoi = this.thongTinThueBaoManagementLocalBean.getSoPhieuDaDoi_qStudent(command.getThue_bao());
                    Object[] resultObject = this.thongTinThueBaoManagementLocalBean.traCuuThongTinThueBao_qStudent(command.getThue_bao(), command.getFirstItem(), command.getMaxPageItems(), command.getSortExpression(), command.getSortDirection());
                    command.setSoDiemHienTai(soDiemHientai);
                    command.setSoPhieuDaDoi(soPhieuDaDoi);
                    command.setTotalItems(Integer.valueOf(resultObject[0].toString()));
                    command.setListResult((List<ThongTinThueBaoMaPhieuDTO>)resultObject[1]);
                    mav.addObject(Constants.LIST_MODEL_KEY, command);

                    if(command.getTotalItems() == 0){
                        mav.addObject(Constants.ALERT_TYPE, "info");
                        mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("cuahanggiaodich.thongtinthuebao.no_ma_phieu_found"));
                    }else{
                        if(StringUtils.isNotBlank(command.getWarning())){
                            mav.addObject(Constants.ALERT_TYPE, "info");
                            mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, command.getWarning());
                        }
                    }

                    Object[] resultObject1 = this.soDiemCTTichDiemManagementlocalBean.statisticCumulativeScoresByMonth_qStudent(command.getThue_bao());
                    mav.addObject("lichSuDiemTheoThangTableList", (List<CTTichDiemSoDiemStatisticByMonthDTO>)resultObject1[1]);
                }catch (Exception e){
                    mav.addObject(Constants.ALERT_TYPE, "danger");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("cuahanggiaodich.thongtinthuebao.not_found"));
                }
            }else if(StringUtils.isNotBlank(command.getMessage())){
                mav.addObject(Constants.ALERT_TYPE, "info");
                mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, command.getMessage());
            }
        }
        return mav;
    }

    @RequestMapping(value = {"/ajax/cuahangmobifone/qstudent/loadajaxmaphieulist.html","/ajax/tongdai/qstudent/loadajaxmaphieulist.html","/ajax/admin/qstudent/loadajaxmaphieulist.html"}, method = RequestMethod.POST)
    public ModelAndView loadDanhSachMaPhieu(@ModelAttribute(value = Constants.FORM_MODEL_KEY)ThongTinThueBaoCommand command,
                                            @RequestParam(value = "soThueBao")String soThueBao,
                                            HttpServletRequest request){
        ModelAndView mav = new ModelAndView("/cuahangmobifone/thongtinthuebao_dsmaphieutablelist");
        initSearchTableList(command, request);
        command.setThue_bao(soThueBao);
        Object[] resultObject = this.thongTinThueBaoManagementLocalBean.traCuuThongTinThueBao_qStudent(command.getThue_bao(), command.getFirstItem(), command.getMaxPageItems(), command.getSortExpression(), command.getSortDirection());
        command.setListResult((List<ThongTinThueBaoMaPhieuDTO>)resultObject[1]);
        command.setTotalItems(Integer.valueOf(resultObject[0].toString()));
        mav.addObject("page", command.getPage() - 1);
        mav.addObject(Constants.LIST_MODEL_KEY, command);
        return mav;
    }

    /**
     * Retrieve parameters from table submitting for paging, sort with table id 'tableList'.
     * @param command
     * @param request
     */
    private void initSearchTableList(ThongTinThueBaoCommand command, HttpServletRequest request){
        if (command != null){
            String sortExpression = request.getParameter(new ParamEncoder("tableList").encodeParameterName(TableTagParameters.PARAMETER_SORT));
            String sortDirection = request.getParameter(new ParamEncoder("tableList").encodeParameterName(TableTagParameters.PARAMETER_ORDER));
            String sPage = request.getParameter(new ParamEncoder("tableList").encodeParameterName(TableTagParameters.PARAMETER_PAGE));
            Integer page = 1;
            if (StringUtils.isNotBlank(sPage)) {
                try {
                    page = Integer.valueOf(sPage);
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
            command.setPage(page);
            command.setFirstItem((command.getPage() - 1) * command.getMaxPageItems());
            command.setSortExpression(sortExpression);
            command.setSortDirection(sortDirection);
        }
    }

    @RequestMapping(value = {"/ajax/cuahangmobifone/qstudent/loadlichsudiemtheongay.html"
            ,"/ajax/tongdai/qstudent/loadlichsudiemtheongay.html"
            ,"/ajax/admin/qstudent/loadlichsudiemtheongay.html"
            ,"/ajax/chinhanh/qstudent/loadlichsudiemtheongay.html"})
    public ModelAndView loadLichSuDiemTheoNgay(@ModelAttribute(value = Constants.FORM_MODEL_KEY)ThongTinThueBaoCommand command,
                                               @RequestParam(value = "fromDate", required = false)Date fromDate,
                                               @RequestParam(value = "toDate", required = false)Date toDate,
                                               @RequestParam(value = "thue_bao")String thue_bao,
                                               HttpServletRequest request){
        ModelAndView mav = new ModelAndView("/2015/q_student_and_q_teen/thongtinthuebao_lichsudiemtheongay");
        initSearchBean4LichSuDiemTheoNgayTableList(command, request);
        Timestamp fromDateTime = null;
        Timestamp toDateTime = null;
        if(fromDate == null){
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.getInstance().get(Calendar.YEAR), 0, 1);
            fromDate =  new Date(cal.getTimeInMillis());
        }
        if(toDate == null){
            toDate = new Date(System.currentTimeMillis());
        }
        Map<String, Object> properties = new HashMap<String, Object>();
        if(fromDate != null){
            fromDateTime = DateUtil.dateToTimestamp(fromDate, Constants.VI_DATE_FORMAT);
            properties.put("fromDate", fromDateTime);
        }
        if(toDate != null){
            toDateTime = DateUtil.dateToTimestamp(toDate, Constants.VI_DATE_FORMAT);
            properties.put("toDate", toDateTime);
        }
        Object[] resultObject = this.soDiemCTTichDiemManagementlocalBean.statisticCumulativeScoresByDate_qStudent(properties, thue_bao, 0, Integer.MAX_VALUE, null, null);
        mav.addObject("lichSuDiemTableList", (List<CTTichDiemSoDiemDTO>)resultObject[1]);
        mav.addObject("totalItems", Integer.valueOf(resultObject[0].toString()));
        return mav;
    }

    /**
     * Retrieve parameters from table submitting for paging, sort with table id 'lichSuDiemTableList'.
     * @param command
     * @param request
     */
    private void initSearchBean4LichSuDiemTheoNgayTableList(ThongTinThueBaoCommand command, HttpServletRequest request){
        if (command != null){
            String sortExpression = request.getParameter(new ParamEncoder("lichSuDiemTableList").encodeParameterName(TableTagParameters.PARAMETER_SORT));
            String sortDirection = request.getParameter(new ParamEncoder("lichSuDiemTableList").encodeParameterName(TableTagParameters.PARAMETER_ORDER));
            String sPage = request.getParameter(new ParamEncoder("lichSuDiemTableList").encodeParameterName(TableTagParameters.PARAMETER_PAGE));
            Integer page = 1;
            if (StringUtils.isNotBlank(sPage)) {
                try {
                    page = Integer.valueOf(sPage);
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
            command.setPage(page);
            command.setFirstItem((command.getPage() - 1) * command.getMaxPageItems());
            command.setSortExpression(sortExpression);
            command.setSortDirection(sortDirection);
        }
    }
}
