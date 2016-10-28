package com.benluck.vms.mobifonedataseller.webapp.controller.controler2015;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.utils.DateUtil;
import com.benluck.vms.mobifonedataseller.core.business.*;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.RetailDealerDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.UserDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.UserPasswordDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015.PromPackageDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015.RegistrationTransDTO;
import com.benluck.vms.mobifonedataseller.editor.CustomDateEditor;
import com.benluck.vms.mobifonedataseller.common.utils.PromotionEnum;
import com.benluck.vms.mobifonedataseller.util.RequestUtil;
import com.benluck.vms.mobifonedataseller.webapp.command.command2015.ThueBaoPTM_KetQuaThucHien_Command;
import com.benluck.vms.mobifonedataseller.webapp.validator.KetQuaThucHienValidator;
import org.apache.commons.lang3.StringUtils;
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

import javax.ejb.ObjectNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 2/10/15
 * Time: 6:11 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class ThueBaoPhatTrienMoi_KetQuaThucHien_2015_Controller extends ApplicationObjectSupport{
    private Log log = LogFactory.getLog(ThueBaoPhatTrienMoi_KetQuaThucHien_2015_Controller.class);
    private static final String USER_LOGON = "USER_LOGON_";
    private static final String REQUEST_OTP = "REQUEST_OTP_";
    private String USERNAME_KPP_BY_SESSION = "USERNAME_KPP_BY_SESSION";

    @Autowired
    private KetQuaThucHienValidator validator;
    @Autowired
    private UserManagementLocalBean userService;
    @Autowired
    private UserPasswordManagementLocalBean userPasswordService;
    @Autowired
    private ThueBaoPTM_KetQuaThucHienManagementLocalBean ketQuaThucHienService;
    @Autowired
    private PromPackageManagementLocalBean promPackageService;
    @Autowired
    private RetailDealerManagementLocalBean retailDealerService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor("dd/MM/yyyy"));
    }

    @RequestMapping("/thuebaophattrienmoi/kenhphanphoi/tra-cuu-ket-qua-thuc-hien.html")
    public ModelAndView traCuuKetQuaThucHien(@ModelAttribute(value = Constants.FORM_MODEL_KEY)ThueBaoPTM_KetQuaThucHien_Command command,
                                             BindingResult bindingResult,
                                             HttpServletRequest request){
        ModelAndView mav = new ModelAndView("/2015/thuebaophattrienmoi/kenhphanphoi/ketquathuchien");
        String crudaction = command.getCrudaction();
        if(StringUtils.isNotBlank(crudaction)){
            if(crudaction.equals("dang-xuat")){
                if(request.getSession().getAttribute(REQUEST_OTP + RequestUtil.getClusterSessionId(request)) != null){
                    request.getSession().removeAttribute(REQUEST_OTP + RequestUtil.getClusterSessionId(request));
                }
                if(request.getSession().getAttribute(USER_LOGON + RequestUtil.getClusterSessionId(request)) != null){
                    request.getSession().removeAttribute(USER_LOGON + RequestUtil.getClusterSessionId(request));
                    return mav;
                }
            }
            if(crudaction.equals("change-ez")){
                command.getUser().setPassword("");
            }
        }

        // login or view report
        if(request.getSession().getAttribute(USER_LOGON + RequestUtil.getClusterSessionId(request)) != null){
            UserDTO dto = (UserDTO)request.getSession().getAttribute(USER_LOGON + RequestUtil.getClusterSessionId(request));
            dto.setUserName(dto.getUserName().replace(Constants.PREFIX_KPP_USERNAME, ""));
            dto.setFromDate(null);
            dto.setToDate(null);
            command.setUser(dto);
            mav = view_report(command, request);
        }else{
            if(StringUtils.isNotBlank(command.getUser().getUserName())){
                validator.validate(command, bindingResult);
                if(!bindingResult.hasErrors() && StringUtils.isBlank(command.getMessage())){
                    try{
                        // add prefix for KPP username
                        command.getUser().setUserName(Constants.PREFIX_KPP_USERNAME + command.getUser().getUserName());
                        this.userService.saveUserInfoAndGenerateVerifyLoginCode(command.getUser(), RequestUtil.getServerIP(request), Constants.USERGROUP_KPP);
                        mav = checkLogin(command, request, mav);
                        mav.addObject(Constants.ALERT_TYPE, "info");
                        mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("kpp.msg.ma_dang_nhap_da_gui"));
                        mav.addObject("enterPasswordOTP", true);
                        request.getSession().setAttribute(REQUEST_OTP + RequestUtil.getClusterSessionId(request), true);
                    }catch (Exception e){
                        mav.addObject(Constants.ALERT_TYPE, "info");
                        mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("kpp.msg.error_when_generate_verify_code"));
                    }
                }else{
                    mav.addObject(Constants.ALERT_TYPE, "info");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, command.getMessage());
                }
            }else if(StringUtils.isNotBlank(command.getUser().getPassword())){
                mav = checkLogin(command, request, mav);
            }else if(request.getSession().getAttribute(REQUEST_OTP + RequestUtil.getClusterSessionId(request)) != null){
                mav.addObject("enterPasswordOTP", true);
            }
        }
        return mav;
    }

    private void referenceData(ThueBaoPTM_KetQuaThucHien_Command command, ModelAndView mav){
        List<PromPackageDTO> goiCuocList = this.promPackageService.findAll();
        mav.addObject("goiCuocList", goiCuocList);
    }

    private ModelAndView view_report(ThueBaoPTM_KetQuaThucHien_Command command, HttpServletRequest request){
        ModelAndView mav = new ModelAndView("/2015/thuebaophattrienmoi/kenhphanphoi/ketquathuchien_report");
        convertDate2Timestamp(command);
        RequestUtil.initSearchBean(request, command);
        Map<String, Object> properties = buildProperties(command, request);
        Object[] resultObject =  this.ketQuaThucHienService.search(properties, command.getFirstItem(), command.getMaxPageItems(), command.getSortExpression(), command.getSortDirection());
        command.setListResult((List<RegistrationTransDTO>)resultObject[0]);
        command.setTotalItems(Integer.valueOf(resultObject[1].toString()));
        mav.addObject(Constants.LIST_MODEL_KEY, command);
        mav.addObject("page", command.getPage() - 1);

        String soEZ = request.getSession().getAttribute(USERNAME_KPP_BY_SESSION).toString();
        RetailDealerDTO dealerDTO = this.retailDealerService.findBySoEZAndCTCode(soEZ, PromotionEnum.THUE_BAO_PTM_2015.getCode());
        mav.addObject("retailDealerInfo", dealerDTO);

        referenceData(command, mav);
        return mav;
    }

    private Map<String, Object> buildProperties(ThueBaoPTM_KetQuaThucHien_Command command, HttpServletRequest request){
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("soEZ", request.getSession().getAttribute(USERNAME_KPP_BY_SESSION));
        if(StringUtils.isNotBlank(command.getPojo().getCustomer_Isdn())){
            properties.put("soThueBaoKH", command.getPojo().getCustomer_Isdn());
        }
        if(command.getPojo().getGoiCuoc() != null && command.getPojo().getGoiCuoc().getPackage_Id() != null){
            properties.put("goiCuocId", command.getPojo().getGoiCuoc().getPackage_Id());
        }
        if(command.getThoiGianDangKyFromTime() != null){
            properties.put("fromDate", command.getThoiGianDangKyFromTime());
        }
        if(command.getThoiGianDangKyToTime() != null){
            properties.put("toDate", command.getThoiGianDangKyToTime());
        }
        if(StringUtils.isNotBlank(command.getPojo().getProm_Condition_Status())){
            properties.put("tinhTrangKhuyenKhich", command.getPojo().getProm_Condition_Status());
        }
        if(command.getPojo().getTrans_Status() != null){
            properties.put("tinhTrangGiaoDich", command.getPojo().getTrans_Status());
        }
        return properties;
    }

    private ModelAndView checkLogin(ThueBaoPTM_KetQuaThucHien_Command command, HttpServletRequest request, ModelAndView mav){
        if(StringUtils.isNotBlank(command.getUser().getPassword())){
            try{
                if(request.getSession().getAttribute(REQUEST_OTP + RequestUtil.getClusterSessionId(request)) != null){
                    request.getSession().removeAttribute(REQUEST_OTP + RequestUtil.getClusterSessionId(request));
                }
                UserPasswordDTO userPasswordDTO = this.userPasswordService.validateVerifyingCode(command.getUser().getPassword().trim(), Constants.USERGROUP_KPP);
                if(userPasswordDTO.isExpired()) {
                    mav.addObject(Constants.ALERT_TYPE, "info");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("kpp.msg.expired_verify_code"));
                }else{
                    request.getSession().setAttribute(USER_LOGON + RequestUtil.getClusterSessionId(request), userPasswordDTO.getUser());
                    UserDTO dto = userPasswordDTO.getUser();
                    dto.setUserName(dto.getUserName().replace(Constants.PREFIX_KPP_USERNAME, ""));
                    request.getSession().setAttribute(USERNAME_KPP_BY_SESSION, dto.getUserName());
                    command.setUser(dto);
                    mav = view_report(command, request);
                }
            }catch (ObjectNotFoundException oe){
                mav.setViewName("/2015/thuebaophattrienmoi/kenhphanphoi/ketquathuchien");
                mav.addObject("enterPasswordOTP", true);
                mav.addObject("allowChangeEZ", true);
                logger.error(oe.getMessage(), oe);
                mav.addObject(Constants.ALERT_TYPE, "info");
                mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("khcn.msg.not_exists_verify_code"));
            }
        }
        return  mav;
    }

    private void convertDate2Timestamp(ThueBaoPTM_KetQuaThucHien_Command command){
        if(command.getThoiGianDangKyFrom() != null){
            command.setThoiGianDangKyFromTime(DateUtil.dateToTimestamp(command.getThoiGianDangKyFrom(), Constants.VI_DATE_FORMAT));
        }
        if(command.getThoiGianDangKyTo() != null){
            command.setThoiGianDangKyToTime(DateUtil.dateToTimestamp(command.getThoiGianDangKyTo(), Constants.VI_DATE_FORMAT));
        }
    }
}
