package com.benluck.vms.mobifonedataseller.webapp.controller.controler2015;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.utils.Config;
import com.benluck.vms.mobifonedataseller.common.utils.DateUtil;
import com.benluck.vms.mobifonedataseller.core.business.MaPhieuCTTichDiemManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.SoDiemCTTichDiemManagementlocalBean;
import com.benluck.vms.mobifonedataseller.core.business.UserManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.UserPasswordManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.CTTichDiemSoDiemDTO;
import com.benluck.vms.mobifonedataseller.core.dto.CTTichDiemSoDiemStatisticByMonthDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.UserDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.UserPasswordDTO;
import com.benluck.vms.mobifonedataseller.editor.CustomDateEditor;
import com.benluck.vms.mobifonedataseller.util.RequestUtil;
import com.benluck.vms.mobifonedataseller.webapp.command.command2014.UserCommand;
import com.benluck.vms.mobifonedataseller.webapp.validator.validator2015.XemDiemDoiQuaValidator_qStudent;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.ejb.ObjectNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Công Thành
 * Date: 01/04/2015
 * Time: 14:33
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class QStudentXemDiemDoiQua extends ApplicationObjectSupport {
    private final String USER_VERIFIDED = "USER_VERIFIED_QSTUDENT";
    private final String REQUEST_OTP_KHCN = "REQUEST_OTP_KHCN_QSTUDENT";

    private transient final Log log = LogFactory.getLog(getClass());

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.registerCustomEditor(Date.class, new CustomDateEditor("dd/MM/yyyy"));
    }

    @Autowired
    private XemDiemDoiQuaValidator_qStudent xemDiemDoiQuaValidator_qStudent;
    @Autowired
    private UserManagementLocalBean userManagementLocalBean;
    @Autowired
    private UserPasswordManagementLocalBean userPasswordManagementLocalBean;
    @Autowired
    private MaPhieuCTTichDiemManagementLocalBean maPhieuCTTichDiemManagementLocalBean;
    @Autowired
    private SoDiemCTTichDiemManagementlocalBean soDiemCTTichDiemManagementlocalBean;

    @RequestMapping(value="/q-teen-q-student/xem-diem-doi-qua.html")
    public ModelAndView xemDoiQua(@ModelAttribute(value = Constants.FORM_MODEL_KEY)UserCommand command,
                                  BindingResult bindingResult,
                                  HttpServletRequest request){
        ModelAndView mav = new ModelAndView("/2015/q_student_and_q_teen/xemdoiqua");
        boolean enableLoginFunction = false;
        String enableLoginFunctionFlag = Config.getInstance().getProperty("khachhangcanhan_login_function.enable");
        if(StringUtils.isNotBlank(enableLoginFunctionFlag) && enableLoginFunctionFlag.equalsIgnoreCase("true")){
            enableLoginFunction = true;
            mav.addObject("enableLoginFunction", enableLoginFunction);
            String crudaction = command.getCrudaction();
            if(StringUtils.isNotBlank(crudaction)){
                if(request.getSession().getAttribute(USER_VERIFIDED + RequestUtil.getClusterSessionId(request)) != null){
                    if(crudaction.equalsIgnoreCase("doi-qua")){
                        mav = doi_qua(command, request);
                    }else if (crudaction.equalsIgnoreCase("xem-diem")){
                        command.setStatistic("byDateAndMonth");
                        mav = view_report(command, request);
                    }
                }
            }else{
                if(request.getSession().getAttribute(USER_VERIFIDED + RequestUtil.getClusterSessionId(request)) != null){
                    command.setStatistic("byDateAndMonth");
                    mav = view_report(command, request);
                }else{
                    if(StringUtils.isNotBlank(command.getPojo().getUserName())){
                        xemDiemDoiQuaValidator_qStudent.validate(command, bindingResult);
                        if(!bindingResult.hasErrors() && StringUtils.isBlank(command.getMessage())){
                            try{
                                this.userManagementLocalBean.saveUserInfoAndGenerateVerifyLoginCode(command.getPojo(), RequestUtil.getServerIP(request), Constants.USERGROUP_TB);
                                mav = login(command, request);
                                mav.addObject(Constants.ALERT_TYPE, "info");
                                mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("khcn.msg.ma_dang_nhap_da_gui"));
                                request.getSession().setAttribute(REQUEST_OTP_KHCN + RequestUtil.getClusterSessionId(request), true);
                            }catch (Exception e){
                                logger.error("Error when check and generate verify code for Mobifone Number: " + command.getPojo().getUserName());
                                mav.addObject(Constants.ALERT_TYPE, "danger");
                                mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("khcn.msg.error_when_generate_verify_code"));
                            }
                        }else{
                            if(StringUtils.isNotBlank(command.getMessage())){
                                mav.addObject(Constants.ALERT_TYPE, "info");
                                mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, command.getMessage());
                            }
                            mav.addObject("hasErrors", true);
                        }
                    }else if(StringUtils.isNotBlank(command.getPojo().getPassword())){
                        mav = login(command, request);
                    }else{
                        if(request.getSession().getAttribute(REQUEST_OTP_KHCN + RequestUtil.getClusterSessionId(request)) != null){
                            mav = login(command, request);
                        }
                    }
                }
            }
        }
        return mav;
    }

    private ModelAndView login(UserCommand command, HttpServletRequest request){
        ModelAndView mav = new ModelAndView("/2015/q_student_and_q_teen/xemdoiqua_login");
        if(StringUtils.isNotBlank(command.getPojo().getPassword())){
            try{
                if(request.getSession().getAttribute(REQUEST_OTP_KHCN + RequestUtil.getClusterSessionId(request)) != null){
                    request.getSession().removeAttribute(REQUEST_OTP_KHCN + RequestUtil.getClusterSessionId(request));
                }
                UserPasswordDTO userPasswordDTO = this.userPasswordManagementLocalBean.validateVerifyingCode(command.getPojo().getPassword().trim(), Constants.USERGROUP_TB);
                if(userPasswordDTO.isExpired()) {
                    mav.addObject(Constants.ALERT_TYPE, "info");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("khcn.msg.expired_verify_code"));
                }else{
                    request.getSession().setAttribute(USER_VERIFIDED + RequestUtil.getClusterSessionId(request), userPasswordDTO.getUser());
                    command.setStatistic("byDateAndMonth");
                    mav = view_report(command, request);
                    Object[] resultObject = statisticCumulativeScoresByDate(command);
                    mav.addObject("soDiemList", (List<CTTichDiemSoDiemDTO>)resultObject[1]);
                }
            }catch (ObjectNotFoundException oe){
                logger.error(oe.getMessage());
                mav.addObject(Constants.ALERT_TYPE, "info");
                mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("khcn.msg.not_exists_verify_code"));
            }
        }
        return  mav;
    }

    private ModelAndView view_report(UserCommand command, HttpServletRequest request){
        ModelAndView mav = new ModelAndView("/2015/q_student_and_q_teen/xemdoiqua_report");
        if(request.getSession().getAttribute(USER_VERIFIDED + RequestUtil.getClusterSessionId(request)) != null){
            UserDTO dto = (UserDTO)request.getSession().getAttribute(USER_VERIFIDED + RequestUtil.getClusterSessionId(request));
            command.setPojo(dto);
            String statisticBy = command.getStatistic();
            if(StringUtils.isNotBlank(statisticBy)){
                if(statisticBy.equals("byDateAndMonth")){
                    Object[] resultObject  = statisticCumulativeScoresByDate(command);
                    mav.addObject("statisticByDateList", (List<CTTichDiemSoDiemDTO>)resultObject[1]);

                    Object[] resultObject1 = statisticCumulativeScoresByMonth(command);
                    mav.addObject("statisticByMonthList", (List<CTTichDiemSoDiemStatisticByMonthDTO>)resultObject1[1]);
                }else if(statisticBy.equalsIgnoreCase("byDate")){
                    Object[] resultObject  = statisticCumulativeScoresByDate(command);
                    mav.addObject("statisticByDateList", (List<CTTichDiemSoDiemDTO>)resultObject[1]);
                }else if(statisticBy.equalsIgnoreCase("byMonth")){
                    Object[] resultObject = statisticCumulativeScoresByMonth(command);
                    mav.addObject("statisticByMonthList", (List<CTTichDiemSoDiemStatisticByMonthDTO>)resultObject[1]);
                    mav.addObject("showStatisticByMonth",true);
                }
            }
        }
        mav.addObject("xemDiemFlag", true);
        return  mav;
    }

    private ModelAndView doi_qua(UserCommand command, HttpServletRequest request){
        ModelAndView mav = new ModelAndView("/2015/q_student_and_q_teen/xemdoiqua_report");
        if(request.getSession().getAttribute(USER_VERIFIDED + RequestUtil.getClusterSessionId(request)) != null){
            UserDTO dto = (UserDTO)request.getSession().getAttribute(USER_VERIFIDED + RequestUtil.getClusterSessionId(request));
            command.setPojo(dto);
            Object[] resultObject  = searchDanhSachMaPhieu2DoiQua(command);
            mav.addObject("danhSachPhieuDoiQuaList", (List<CTTichDiemSoDiemDTO>)resultObject[1]);

            Integer soDiemHienTai = this.soDiemCTTichDiemManagementlocalBean.getCurrentScoreTotal_qStudent(command.getPojo().getUserName());
            mav.addObject("totalCurrentPoint", soDiemHienTai);
        }
        mav.addObject("doiQuaFlag", true);
        return mav;
    }

    private Object[] searchDanhSachMaPhieu2DoiQua(UserCommand command){
        return this.maPhieuCTTichDiemManagementLocalBean.search_qStudent(command.getPojo().getUserName(), 0, Integer.MAX_VALUE, null, null);
    }

    private Object[] statisticCumulativeScoresByDate(UserCommand command){
        Map<String, Object> properties = new HashMap<String, Object>();
        if(command.getFromDate() != null){
            command.getPojo().setFromDate(DateUtil.dateToTimestamp(command.getFromDate(), Constants.VI_DATE_FORMAT));
            if(command.getFromDate() != null){
                properties.put("fromDate", command.getPojo().getFromDate());
            }
        }
        if(command.getToDate() != null){
            command.getPojo().setToDate(DateUtil.dateToTimestamp(command.getToDate(), Constants.VI_DATE_FORMAT));
            if(command.getToDate() != null){
                properties.put("toDate", command.getPojo().getToDate());
            }
        }
        return this.soDiemCTTichDiemManagementlocalBean.statisticCumulativeScoresByDate_qStudent(properties, command.getPojo().getUserName(), 0, Integer.MAX_VALUE, null, null);
    }

    private Object[] statisticCumulativeScoresByMonth(UserCommand command){
        return this.soDiemCTTichDiemManagementlocalBean.statisticCumulativeScoresByMonth_qStudent(command.getPojo().getUserName());
    }

    @RequestMapping("/q-teen-q-student/doi-qua.html")
    public ModelAndView doiQua(@ModelAttribute(value = Constants.FORM_MODEL_KEY)UserCommand command,
                               RedirectAttributes redirectAttributes,
                               HttpServletRequest request){
        if(request.getSession().getAttribute(USER_VERIFIDED + RequestUtil.getClusterSessionId(request)) != null){
            try{
                UserDTO dto = (UserDTO)request.getSession().getAttribute(USER_VERIFIDED + RequestUtil.getClusterSessionId(request));
                command.setPojo(dto);
                Integer soLuongQuaDaDoi = this.maPhieuCTTichDiemManagementLocalBean.getTotalOgExchangedMaPhieu_qStudent(dto.getMobileNumber());
                if(soLuongQuaDaDoi.compareTo(Constants.TOTAL_SO_LUONG_QUA_CT) < 0){
                    this.soDiemCTTichDiemManagementlocalBean.exchangeGift_qStudent(command.getPojo().getUserName());
                    redirectAttributes.addFlashAttribute(Constants.ALERT_TYPE, "success");
                    redirectAttributes.addFlashAttribute(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("doiqua.msg.success"));
                }else{
                    command.setStatistic("byDateAndMonth");
                    ModelAndView mav = view_report(command, request);
                    mav.addObject(Constants.ALERT_TYPE, "danger");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("doiqua.msg.so_luong_ct_qua_da_het"));
                    return  mav;
                }
            }catch (ObjectNotFoundException oe){
                redirectAttributes.addFlashAttribute(Constants.ALERT_TYPE, "info");
                redirectAttributes.addFlashAttribute(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("doiqua.msg.no_ma_phieu_chua_doi_qua_found"));
            }catch (Exception e){
                redirectAttributes.addFlashAttribute(Constants.ALERT_TYPE, "success");
                redirectAttributes.addFlashAttribute(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("doiqua.msg.failed"));
            }
        }else{
            redirectAttributes.addFlashAttribute(Constants.ALERT_TYPE, "info");
            redirectAttributes.addFlashAttribute(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("doiqua.msg.alert_login"));
        }
        return new ModelAndView("redirect:/2015/q_student_and_q_teen/xem-diem-doi-qua.html?crudaction=doi-qua");
    }

    @RequestMapping("/q-teen-q-student/logout.html")
    public ModelAndView KHCNLogout(HttpServletRequest request){
        if(request.getSession().getAttribute(USER_VERIFIDED + RequestUtil.getClusterSessionId(request)) != null){
            request.getSession().removeAttribute(USER_VERIFIDED + RequestUtil.getClusterSessionId(request));
    }
        return new ModelAndView("redirect:/q-teen-q-student/xem-diem-doi-qua.html");
    }
}
