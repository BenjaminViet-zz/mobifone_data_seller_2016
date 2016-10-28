package com.benluck.vms.mobifonedataseller.webapp.controller;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.utils.Config;
import com.benluck.vms.mobifonedataseller.common.utils.DateUtil;
import com.benluck.vms.mobifonedataseller.core.business.KPPManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.UserManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.UserPasswordManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.*;
import com.benluck.vms.mobifonedataseller.editor.CustomDateEditor;
import com.benluck.vms.mobifonedataseller.util.RequestUtil;
import com.benluck.vms.mobifonedataseller.webapp.command.command2014.DealerAccountCommand;
import com.benluck.vms.mobifonedataseller.webapp.command.command2014.UserCommand;
import com.benluck.vms.mobifonedataseller.webapp.validator.XemDiemDoiThuongKPPValidator;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/21/14
 * Time: 9:51 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class KPPXemDiemDoiThuongController extends ApplicationObjectSupport{
    private transient final Log log = LogFactory.getLog(getClass());

    private static final String USER_LOGON = "USER_LOGON_";
    private static final String REQUEST_OTP = "REQUEST_OTP_";
    private static final String STORE_REQUEST_PARAMS_STR = "STORE_REQUEST_PARAMS_STR_";

    private static final String PARAM_THANG_TICH_DIEM = "thangTichDiem";
    private static final String PARAM_ITEM_ID = "itemId";
    private static final String PARAM_FROM_DATE = "fromDate";
    private static final String PARAM_TO_DATE = "toDate";

    @Autowired
    private KPPManagementLocalBean kppManagementLocalBean;
    @Autowired
    private XemDiemDoiThuongKPPValidator xemDiemDoiThuongKPPValidator;
    @Autowired
    private UserManagementLocalBean userManagementLocalBean;
    @Autowired
    private UserPasswordManagementLocalBean userPasswordManagementLocalBean;

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.registerCustomEditor(Date.class, new CustomDateEditor("dd/MM/yyyy"));
    }


    @RequestMapping(value="/kenhphanphoi/xem-diem-doi-thuong.html")
    public ModelAndView xemdiemdoiqua(@ModelAttribute(value = Constants.FORM_MODEL_KEY)UserCommand command,
                                      HttpServletRequest request,
                                      BindingResult bindingResult){
        ModelAndView mav = new ModelAndView("/kenhphanphoi/kpp_xemdiemdoithuong");
        String flagEnableLogin = Config.getInstance().getProperty("kpp_login.enable");
        String crudaction = command.getCrudaction();
        if(StringUtils.isNotBlank(crudaction)){
            if(crudaction.equals("dang-xuat")){
                if(request.getSession().getAttribute(REQUEST_OTP + RequestUtil.getClusterSessionId(request)) != null){
                    request.getSession().removeAttribute(REQUEST_OTP + RequestUtil.getClusterSessionId(request));
                }
                if(request.getSession().getAttribute(USER_LOGON + RequestUtil.getClusterSessionId(request)) != null){
                    request.getSession().removeAttribute(USER_LOGON + RequestUtil.getClusterSessionId(request));
                    mav.addObject("enableLogin", true);
                    return mav;
                }
            }
        }
        if(StringUtils.isNotBlank(flagEnableLogin) && flagEnableLogin.equalsIgnoreCase("true")){
            if(request.getSession().getAttribute(USER_LOGON + RequestUtil.getClusterSessionId(request)) != null){
                UserDTO dto = (UserDTO)request.getSession().getAttribute(USER_LOGON + RequestUtil.getClusterSessionId(request));
                dto.setUserName(dto.getUserName().replace(Constants.PREFIX_KPP_USERNAME, ""));
                dto.setFromDate(null);
                dto.setToDate(null);
                command.setPojo(dto);
                mav = report(command, request);
            }else{
                if(StringUtils.isNotBlank(command.getPojo().getUserName())){
                    xemDiemDoiThuongKPPValidator.validate(command, bindingResult);
                    if(!bindingResult.hasErrors() && StringUtils.isBlank(command.getMessage())){
                        try{
                            // ad prefix for KPP username
                            command.getPojo().setUserName(Constants.PREFIX_KPP_USERNAME + command.getPojo().getUserName());
                            this.userManagementLocalBean.saveUserInfoAndGenerateVerifyLoginCode(command.getPojo(), RequestUtil.getServerIP(request), Constants.USERGROUP_KPP);
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
                        mav.addObject("enableLogin", true);
                    }
                }else if(StringUtils.isNotBlank(command.getPojo().getPassword())){
                    mav = checkLogin(command, request, mav);
                }else{
                    if(request.getSession().getAttribute(REQUEST_OTP + RequestUtil.getClusterSessionId(request)) != null){
                        mav.addObject("enterPasswordOTP", true);
                    }else{
                        mav.addObject("enableLogin", true);
                    }
                }
            }
        }
        return mav;
    }

    /**
     * Verify if the user has login already. Else redirect to login page.
     * @param command
     * @param request
     * @param mav
     * @return
     */
    private ModelAndView checkLogin(UserCommand command, HttpServletRequest request, ModelAndView mav){
        if(StringUtils.isNotBlank(command.getPojo().getPassword())){
            try{
                if(request.getSession().getAttribute(REQUEST_OTP + RequestUtil.getClusterSessionId(request)) != null){
                    request.getSession().removeAttribute(REQUEST_OTP + RequestUtil.getClusterSessionId(request));
                }
                UserPasswordDTO userPasswordDTO = this.userPasswordManagementLocalBean.validateVerifyingCode(command.getPojo().getPassword().trim(), Constants.USERGROUP_KPP);
                if(userPasswordDTO.isExpired()) {
                    mav.addObject(Constants.ALERT_TYPE, "info");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("kpp.msg.expired_verify_code"));
                    mav.addObject("enableLogin", true);
                }else{
                    request.getSession().setAttribute(USER_LOGON + RequestUtil.getClusterSessionId(request), userPasswordDTO.getUser());
                    UserDTO dto = userPasswordDTO.getUser();
                    dto.setUserName(dto.getUserName().replace(Constants.PREFIX_KPP_USERNAME, ""));
                    command.setPojo(dto);
                    mav = report(command, request);
                }
            }catch (ObjectNotFoundException oe){
                mav.setViewName("/kenhphanphoi/kpp_xemdiemdoithuong");
                mav.addObject("enterPasswordOTP", true);
                logger.error(oe.getMessage());
                mav.addObject(Constants.ALERT_TYPE, "info");
                mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("khcn.msg.not_exists_verify_code"));
            }
        }
        return  mav;
    }

    /**
     * Build report data for KPP.
     * @param command
     * @param request
     * @return
     */
    private ModelAndView report(UserCommand command, HttpServletRequest request){
        ModelAndView mav = new ModelAndView("/kenhphanphoi/kpp_xemdiemdoithuong_report");

        StringBuilder storePara = new StringBuilder();
        storePara.append(PARAM_THANG_TICH_DIEM).append("=").append(command.getThangTichDiem().toString());

        if(command.getItemId() != null){
            storePara.append("&").append(PARAM_ITEM_ID).append("=").append(command.getItemId().toString());
        }
        if(command.getFromDate() != null){
            storePara.append("&").append(PARAM_FROM_DATE).append("=" + command.getFromDate().toString());
        }
        if(command.getToDate() != null){
            storePara.append("&").append(PARAM_TO_DATE).append("=").append(command.getToDate().toString());
        }
        request.getSession().setAttribute(STORE_REQUEST_PARAMS_STR + RequestUtil.getClusterSessionId(request), storePara.toString());

        try{
            String thongTinDBH = this.kppManagementLocalBean.generateInfo4Agency(command.getPojo().getUserName());
            mav.addObject("thongTinDBH", thongTinDBH);
        }catch (ObjectNotFoundException oe){
            log.error(oe.getMessage());
        }

        String crudaction = command.getCrudaction();
        if(StringUtils.isNotBlank(crudaction) && crudaction.equals("danh-sach-ma-du-thuong")){
            mav.setViewName("/kenhphanphoi/kpp_danhsachmaduthuong_new");
            Integer tongSoMaDuThuongHienTai = this.kppManagementLocalBean.getCurrentTotalScoreWinningTicketKPP(command.getPojo().getUserName());
            mav.addObject("tongSoMaDuThuongHienTai", tongSoMaDuThuongHienTai);

            Object[] resultObject1 = traCuuDanhSachMaDuThuong(command, request);
            mav.addObject("danhSachMaDuThuongTableListTotalItems", Integer.valueOf(resultObject1[0].toString()));
            mav.addObject("danhSachMaDuThuongTableList", (List<CTTichDiemMaDuThuongDTO>)resultObject1[1]);
        }else{
            // Danh sach diem theo ngay / thang
            Object[] resultObject = traCuuPhatSinhDiemTheoNgay(command, request);
            mav.addObject("lichSuDiemTheoNgayTotalItems", Integer.valueOf(resultObject[0].toString()));
            mav.addObject("lichSuDiemTheoNgayTableList", (List<DealerAccountActionDTO>)resultObject[1]);

            try{
                RetailDealerDTO retailDealerDTO = this.kppManagementLocalBean.findRetailDealerByThueBao(command.getPojo().getUserName());
                mav.addObject("retailDealerDTO", retailDealerDTO);
            }catch (ObjectNotFoundException oe){}

            List<DealerAccountActionDTO> reportByMonth = traCuuPhatSinhDiemTheoThang(command, request);
            mav.addObject("lichSuDiemTheoThangTableList", reportByMonth);

            Integer tongSoMaDuThuongHienTai = this.kppManagementLocalBean.getCurrentTotalScoreWinningTicketKPP(command.getPojo().getUserName());
            mav.addObject("tongSoMaDuThuongHienTai", tongSoMaDuThuongHienTai);

            Object[] resultObject1 = traCuuDanhSachMaDuThuong(command, request);
            mav.addObject("danhSachMaDuThuongTableListTotalItems", Integer.valueOf(resultObject1[0].toString()));
            mav.addObject("danhSachMaDuThuongTableList", (List<CTTichDiemMaDuThuongDTO>)resultObject1[1]);

            Object[] resultObject2 = getWarningReportData(command, request);
            mav.addObject("canhBaoTableListTotalItems", Integer.valueOf(resultObject2[0].toString()));
            mav.addObject("canhBaoTableList", (List<CanhBaoKPPDTO>)resultObject2[1]);

            List<PromItemsDTO> itemList = this.kppManagementLocalBean.findAllItems();
            mav.addObject("itemList", itemList);
        }
        return mav;
    }

    /**
     * Fetch warning data for this view.
     * @param command
     * @param request
     * @return
     */
    private Object[] getWarningReportData(UserCommand command, HttpServletRequest request){
        initSearchBean4CanhBaoTableList(command, request);
        return this.kppManagementLocalBean.getWarningReportData(command.getPojo().getUserName(), command.getFirstItem(), command.getMaxPageItems(), command.getSortExpression(), command.getSortDirection());
    }

    /**
     * Search score of transactions by month
     * @param command
     * @param request
     * @return
     */
    private List<DealerAccountActionDTO> traCuuPhatSinhDiemTheoThang(UserCommand command, HttpServletRequest request) {
        return this.kppManagementLocalBean.traCuuPhatSinhTheoThangNew(command.getPojo().getUserName(), command.getThangTichDiem());
    }

    /**
     * Search score of transactions by date
     * @param command
     * @param request
     * @return
     */
    private Object[] traCuuPhatSinhDiemTheoNgay(UserCommand command, HttpServletRequest request){
        initSearchBean4LichSuDiemTheoNgayTableList(command, request);
        if(command.getFromDate() != null){
            command.getPojo().setFromDate(DateUtil.dateToTimestamp(command.getFromDate(), Constants.VI_DATE_FORMAT));
        }
        if(command.getToDate() != null){
            command.getPojo().setToDate(DateUtil.dateToTimestamp(command.getToDate(), Constants.VI_DATE_FORMAT));
        }
        return this.kppManagementLocalBean.getScoreByDateNew(command.getThangTichDiem(), command.getItemId(), command.getPojo().getUserName(), command.getPojo().getFromDate(), command.getPojo().getToDate(), command.getFirstItem(), command.getMaxPageItems(), command.getSortExpression(), command.getSortDirection());
    }

    /**
     * Retrieve params of paging, sort expression from submitted table.
     * @param command
     * @param request
     */
    private void initSearchBean4LichSuDiemTheoNgayTableList(UserCommand command, HttpServletRequest request){
        if (command != null){
            String sortExpression = request.getParameter(new ParamEncoder("lichSuDiemTableList").encodeParameterName(TableTagParameters.PARAMETER_SORT));
            String sortDirection = request.getParameter(new ParamEncoder("lichSuDiemTableList").encodeParameterName(TableTagParameters.PARAMETER_ORDER));
            String sPage = request.getParameter(new ParamEncoder("lichSuDiemTableList").encodeParameterName(TableTagParameters.PARAMETER_PAGE));
            Integer page = 1;
            if (org.apache.commons.lang.StringUtils.isNotBlank(sPage)) {
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

    /**
     * Get ticket list for lucky circle promotion.
     * @param command
     * @param request
     * @return
     */
    private Object[] traCuuDanhSachMaDuThuong(UserCommand command, HttpServletRequest request){
        initSearchBean4DanhSachMaDuThuongTableList(command, request);
        return this.kppManagementLocalBean.searchWinningTicketList(command.getPojo().getUserName(), command.getFirstItem(), command.getMaxPageItems(), command.getSortExpression(), command.getSortDirection());
    }

    private void initSearchBean4DanhSachMaDuThuongTableList(UserCommand command, HttpServletRequest request){
        if (command != null){
            String sortExpression = request.getParameter(new ParamEncoder("danhSachMaDuThuongTableList").encodeParameterName(TableTagParameters.PARAMETER_SORT));
            String sortDirection = request.getParameter(new ParamEncoder("danhSachMaDuThuongTableList").encodeParameterName(TableTagParameters.PARAMETER_ORDER));
            String sPage = request.getParameter(new ParamEncoder("danhSachMaDuThuongTableList").encodeParameterName(TableTagParameters.PARAMETER_PAGE));
            Integer page = 1;
            if (org.apache.commons.lang.StringUtils.isNotBlank(sPage)) {
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

    private void initSearchBean4CanhBaoTableList(UserCommand command, HttpServletRequest request){
        if (command != null){
            String sortExpression = request.getParameter(new ParamEncoder("canhBaoTableList").encodeParameterName(TableTagParameters.PARAMETER_SORT));
            String sortDirection = request.getParameter(new ParamEncoder("canhBaoTableList").encodeParameterName(TableTagParameters.PARAMETER_ORDER));
            String sPage = request.getParameter(new ParamEncoder("canhBaoTableList").encodeParameterName(TableTagParameters.PARAMETER_PAGE));
            Integer page = 1;
            if (org.apache.commons.lang.StringUtils.isNotBlank(sPage)) {
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

    @RequestMapping("/kenhphanphoi/xem-diem-doi-thuong-chi-tiet-hang-muc-theo-ngay.html")
    public ModelAndView xemChiTietTheoHangMuc(@ModelAttribute(value = Constants.FORM_MODEL_KEY)UserCommand command,
                                              HttpServletRequest request){
        ModelAndView mav = new ModelAndView("/kenhphanphoi/kpp_xemdiemdoithuong_xemchitiet");
        String crudaction = command.getCrudaction();
        if(StringUtils.isNotBlank(crudaction) && crudaction.equals("tro-lai")){
            mav = checkGoBack(request);
            return mav;
        }else{
            if(request.getSession().getAttribute(USER_LOGON + RequestUtil.getClusterSessionId(request)) != null){
                if(command.getD_Id() != null && command.getItem_Id() != null){
                    if(request.getSession().getAttribute(USER_LOGON + RequestUtil.getClusterSessionId(request)) != null){
                        UserDTO dto = (UserDTO)request.getSession().getAttribute(USER_LOGON + RequestUtil.getClusterSessionId(request));
                        command.setPojo(dto);
                        initSearchBean4DetailTableList(command, request);
                        convert2Timestamp(command);
                        Object[] resultObject = this.kppManagementLocalBean.xemChiTietHangMucPhatSinh(false, command.getD_Id(), command.getItem_Id(), command.getFromDateTime(), command.getToDateTime(), command.getFirstItem(), command.getMaxPageItems(), command.getSortExpression(), command.getSortDirection());

                        Integer detailTableListTotalItems = Integer.valueOf(resultObject[0].toString());
                        mav.addObject("item_Id", command.getItem_Id());
                        mav.addObject("detailTableListTotalItems", detailTableListTotalItems);
                        mav.addObject("detailTableList", (List<ChiTietHangMucPhatSinhKPPDTO>)resultObject[1]);
                        mav.addObject("page", command.getPage() -1);

                        if(detailTableListTotalItems.intValue() == 0){
                            mav.addObject(Constants.ALERT_TYPE, "info");
                            mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("kpp.xemchitiettheohangmuc.msg.no_detail_to_view"));
                        }

                        if(command.getCycle() != null){
                            List<Integer> monthList = new ArrayList<Integer>();
                            Calendar currentDate = Calendar.getInstance();
                            if(currentDate.get(Calendar.YEAR) > 2014){
                                monthList.add(10);
                                monthList.add(11);
                                monthList.add(12);
                            }else{
                                if(command.getCycle().compareTo(10) >= 0){
                                    monthList.add(10);
                                }
                                if(command.getCycle().compareTo(11) >= 0){
                                    monthList.add(11);
                                }
                                if(command.getCycle().compareTo(12) >= 0){
                                    monthList.add(12);
                                }
                            }
                            mav.addObject("monthList", monthList);
                        }
                    }else{
                        mav.addObject(Constants.ALERT_TYPE, "info");
                        mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("label.session_expired"));
                    }
                }else{
                    mav.addObject(Constants.ALERT_TYPE, "danger");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("kpp.xemchitiettheohangmuc.msg.invalud_data"));
                }
            }else{
                return new ModelAndView("redirect:/kenhphanphoi/xem-diem-doi-thuong.html");
            }
        }
        return mav;
    }

    /**
     * get Date value and format to Timstamp.
     * @param command
     */
    private void convert2Timestamp(UserCommand command) {
        if(command.getFromStart().equals(1)){
            command.setThangTichDiem(command.getCycle());
        }
        if(command.getFromDate() == null && command.getToDate() == null){
            Calendar currentMonthYear2014 = Calendar.getInstance();
            currentMonthYear2014.set(Calendar.YEAR, 2014);
            currentMonthYear2014.set(Calendar.MONTH, command.getThangTichDiem() - 1);

            Calendar firstDayOfMonth = currentMonthYear2014;
            firstDayOfMonth.set(Calendar.DAY_OF_MONTH, 1);
            command.setFromDate(firstDayOfMonth.getTime());

            Calendar lastDayOfMonth = currentMonthYear2014;
            lastDayOfMonth.set(Calendar.DAY_OF_MONTH, currentMonthYear2014.getActualMaximum(Calendar.DAY_OF_MONTH));
            command.setToDate(lastDayOfMonth.getTime());
        }
        if(command.getFromDate() != null){
            command.setFromDateTime(DateUtil.dateToTimestamp(command.getFromDate(), Constants.VI_DATE_FORMAT));
        }
        if(command.getToDate() != null){
            command.setToDateTime(DateUtil.dateToTimestamp(command.getToDate(), Constants.VI_DATE_FORMAT));
        }
    }

    /**
     * Get stored value in Session to know what previous page come. Go to previous page with right filter.
     * @param request
     * @return
     */
    private ModelAndView checkGoBack(HttpServletRequest request){
        String storeParmas = request.getSession().getAttribute(STORE_REQUEST_PARAMS_STR + RequestUtil.getClusterSessionId(request)).toString();
        String[] params = storeParmas.split("\\&");
        StringBuilder paramUrl = new StringBuilder();
        DateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for(String param : params){
            if(param.indexOf(PARAM_THANG_TICH_DIEM) != -1){
                paramUrl.append("?").append(PARAM_THANG_TICH_DIEM).append("=").append(param.split("\\=")[1].toString());
            }
            if(param.indexOf(PARAM_ITEM_ID) != -1){
                paramUrl.append("&").append(PARAM_ITEM_ID).append("=").append(param.split("\\=")[1].toString());
            }
            if(param.indexOf(PARAM_FROM_DATE) != -1){
                try{
                    String fromDateInStr = param.split("\\=")[1].toString();
                    Date fromDateF = df.parse(fromDateInStr);
                    paramUrl.append("&").append(PARAM_FROM_DATE).append("=").append(sdf.format(fromDateF));
                }catch (Exception e){}
            }
            if(param.indexOf(PARAM_TO_DATE) != -1){
                try{
                    String toDateInStr = param.split("\\=")[1].toString();
                    Date toDateF = df.parse(toDateInStr);
                    paramUrl.append("&").append(PARAM_TO_DATE).append("=").append(sdf.format(toDateF));
                }catch (Exception e){}
            }
        }
        return new ModelAndView("redirect:/kenhphanphoi/xem-diem-doi-thuong.html" + paramUrl.toString());
    }

    /**
     * Get page index, sort expression from submitted table for paging.
     * @param command
     * @param request
     */
    private void initSearchBean4DetailTableList(UserCommand command, HttpServletRequest request){
        if (command != null){
            String sortExpression = request.getParameter(new ParamEncoder("detailTableList").encodeParameterName(TableTagParameters.PARAMETER_SORT));
            String sortDirection = request.getParameter(new ParamEncoder("detailTableList").encodeParameterName(TableTagParameters.PARAMETER_ORDER));
            String sPage = request.getParameter(new ParamEncoder("detailTableList").encodeParameterName(TableTagParameters.PARAMETER_PAGE));
            Integer page = 1;
            if (org.apache.commons.lang.StringUtils.isNotBlank(sPage)) {
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

    @RequestMapping(value = "/kenhphanphoi/xem-chi-tiet-trang-thai-ky.html")
    public ModelAndView xemChiTietTrangThaiKy(@ModelAttribute(value = Constants.FORM_MODEL_KEY)DealerAccountCommand command,
                        HttpServletRequest request){
        ModelAndView mav = new ModelAndView("/kenhphanphoi/xemchitiettrangthaiky");
        if(request.getSession().getAttribute(USER_LOGON + RequestUtil.getClusterSessionId(request)) != null){
            String crudaction = command.getCrudaction();
            if(StringUtils.isNotBlank(crudaction) && crudaction.equals("tro-lai")){
                mav = checkGoBack(request);
                return mav;
            }else{
                if(command.getDealer_Id() != null && command.getCycle() != null){
                    Object[] resultSet = this.kppManagementLocalBean.traCuuChiTietTrangThaiKy(command.getDealer_Id(), command.getCycle());
                    command.setTotalItems(Integer.valueOf(resultSet[0].toString()));
                    command.setListResult((List<DealerAccountDTO>)resultSet[1]);

                    if(command.getTotalItems() == 0){
                        mav.addObject(Constants.ALERT_TYPE, "info");
                        mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("kpp.xemchitiettrangthaichuky.msg.no_detail_to_view"));
                    }else{
                        DealerAccountDTO dto = command.getListResult().get(0);
                        mav.addObject("detailData", dto);
                    }
                }else{
                    mav.addObject(Constants.ALERT_TYPE, "danger");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("label.msg.invalid_data"));
                }
            }
        }else{
            return new ModelAndView("redirect:/kenhphanphoi/xem-diem-doi-thuong.html");
        }
        return mav;
    }
}
