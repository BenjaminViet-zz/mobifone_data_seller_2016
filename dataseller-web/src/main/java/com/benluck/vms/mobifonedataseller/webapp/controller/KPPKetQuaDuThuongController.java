package com.benluck.vms.mobifonedataseller.webapp.controller;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.utils.CommonUtil;
import com.benluck.vms.mobifonedataseller.common.utils.Config;
import com.benluck.vms.mobifonedataseller.common.utils.DateUtil;
import com.benluck.vms.mobifonedataseller.core.business.KPPManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.CTTichDiemMaDuThuongDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.KppGiftDTO;
import com.benluck.vms.mobifonedataseller.editor.CustomDateEditor;
import com.benluck.vms.mobifonedataseller.util.RequestUtil;
import com.benluck.vms.mobifonedataseller.webapp.command.command2014.TraCuuDanhSachMaThuongTrungThuongCommand;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/29/14
 * Time: 5:06 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class KPPKetQuaDuThuongController extends ApplicationObjectSupport{
    private Log log = LogFactory.getLog(KPPKetQuaDuThuongController.class);
    @Autowired
    private KPPManagementLocalBean kppManagementLocalBean;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor("dd/MM/yyyy"));
    }

    @RequestMapping(value="/kenhphanphoi/ket-qua-du-thuong.html")
    public ModelAndView ketquaduthuong(@ModelAttribute(value = Constants.FORM_MODEL_KEY)TraCuuDanhSachMaThuongTrungThuongCommand command,
                                       HttpServletRequest request){
        ModelAndView mav = new ModelAndView("/kenhphanphoi/kpp_ketquaduthuong");
        String enableViewKetQuaDuThuong = Config.getInstance().getProperty("ket_qua_du_thuong.enable");
        if(StringUtils.isNotBlank(enableViewKetQuaDuThuong) && enableViewKetQuaDuThuong.equalsIgnoreCase("true")){
            mav.addObject("enableViewKQDTFlag", true);
            if(StringUtils.isNotBlank(command.getSoEZ())){
                if(StringUtils.isNotBlank(command.getSoEZ())){
                    if(!command.getSoEZ().matches("^0{1}\\d{9,10}$")){
                        mav.addObject(Constants.ALERT_TYPE, "info");
                        mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("kpp.tra_cuu_ma_trung_thuong.msg.invalid_soEZ"));
                        return mav;
                    }
                    try{
                        boolean hasRegister = this.kppManagementLocalBean.checkIfAlreadyRegistered(command.getSoEZ());
                    }catch (Exception e){
                        mav.addObject(Constants.ALERT_TYPE, "danger");
                        mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("kpp.tra_cuu_ma_trung_thuong.msg.not_yet_register"));
                        return mav;
                    }
                }
            }
            RequestUtil.initSearchBean(request, command);
            convertDate2Timestamp(command);
            Map<String, Object> properties = buildProperties(command);
            Object[] resultObject = this.kppManagementLocalBean.traCuuDanhSachTrungThuongKPP(properties, command.getFirstItem(), command.getMaxPageItems(), command.getSortExpression(), command.getSortDirection());
            command.setTotalItems(Integer.valueOf(resultObject[0].toString()));
            command.setListResult((List<CTTichDiemMaDuThuongDTO>)resultObject[1]);
            mav.addObject("page", command.getPage() - 1);
            mav.addObject(Constants.LIST_MODEL_KEY, command);

            if(command.getTotalItems() == 0){
                mav.addObject(Constants.ALERT_TYPE, "info");
                mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("kpp.ketquaduthuong.msg.no_record_found"));
            }

            List<KppGiftDTO> kppGiftList = this.kppManagementLocalBean.findAllKppGifts();
            mav.addObject("kppGiftList", kppGiftList);
        }
        return mav;
    }

    /**
     * Build pairs of key-value properties for querying.
     * @param command
     * @return
     */
    private Map<String, Object> buildProperties(TraCuuDanhSachMaThuongTrungThuongCommand command){
        Map<String, Object> properties = new HashMap<String, Object>();
        if(StringUtils.isNotBlank(command.getDealer_Code())){
            properties.put("dealer_Code", command.getDealer_Code().trim());
        }
        if(StringUtils.isNotBlank(command.getSoEZ())){
            String soEZ = command.getSoEZ().trim();
            soEZ = CommonUtil.removeCountryCode(soEZ);
            properties.put("soEZ", soEZ);
        }
        if(command.getKpp_gift_Id() != null){
            properties.put("kppGift_Id", command.getKpp_gift_Id());
        }
        return properties;
    }

    /**
     * Get Date value and format to Timestamp.
     * @param command
     */
    private void convertDate2Timestamp(TraCuuDanhSachMaThuongTrungThuongCommand command){
        if(command.getFromDate() != null){
            command.setFromDateTime(DateUtil.dateToTimestamp(command.getFromDate(), Constants.VI_DATE_FORMAT));
        }
        if(command.getToDate() != null){
            command.setToDateTime(DateUtil.dateToTimestamp(command.getToDate(), Constants.VI_DATE_FORMAT));
        }
    }
}
