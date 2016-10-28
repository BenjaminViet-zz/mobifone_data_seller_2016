package com.benluck.vms.mobifonedataseller.webapp.controller.controler2015;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.utils.DateUtil;
import com.benluck.vms.mobifonedataseller.core.business.BranchManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.BranchMappingManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.DistrictManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.RetailDealerManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BranchDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BranchMappingDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.DistrictDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.RetailDealerDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015.RegistrationTransDTO;
import com.benluck.vms.mobifonedataseller.editor.CustomDateEditor;
import com.benluck.vms.mobifonedataseller.security.util.SecurityUtils;
import com.benluck.vms.mobifonedataseller.common.utils.PromotionEnum;
import com.benluck.vms.mobifonedataseller.util.ExcelUtil;
import com.benluck.vms.mobifonedataseller.util.RequestUtil;
import com.benluck.vms.mobifonedataseller.util.WebCommonUtil;
import com.benluck.vms.mobifonedataseller.webapp.command.command2014.RegistrationTransCommand;
import com.benluck.vms.mobifonedataseller.webapp.controller.UserController;
import com.benluck.vms.mobifonedataseller.webapp.dto.CellDataType;
import com.benluck.vms.mobifonedataseller.webapp.dto.CellValue;
import com.benluck.vms.mobifonedataseller.webapp.validator.ChiTraDiemBanHangValidator;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.*;
import jxl.write.NumberFormat;
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
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.sql.Timestamp;
import java.text.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: HP
 * Date: 2/6/15
 * Time: 5:56 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class RegistrationTransController extends ApplicationObjectSupport {

    private Log log = LogFactory.getLog(UserController.class);
    private static final Integer TOTAL_COLUMN_EXPORT = 15;

    @Autowired
    private BranchManagementLocalBean branchManagementLocalBean;
    @Autowired
    private DistrictManagementLocalBean districtManagementLocalBean;
    @Autowired
    private RetailDealerManagementLocalBean retailDealerManagementLocalBean;
    @Autowired
    private BranchMappingManagementLocalBean branchMappingManagementLocalBean;
    @Autowired
    private ChiTraDiemBanHangValidator chiTraDiemBanHangValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor("dd/MM/yyyy"));
    }

    @RequestMapping(value = {"/admin/chitradiembanhang.html","/chinhanh/chitradiembanhang.html"})
    public ModelAndView xacNhanChiTra(@ModelAttribute(value = Constants.FORM_MODEL_KEY)RegistrationTransCommand command,
                                       BindingResult bindingResult,
                                       HttpServletRequest request,
                                       HttpServletResponse response){
        ModelAndView mav = new ModelAndView("2015/thuebaophattrienmoi/admin/chitrachodiembanhang/list");
        String crudaction = command.getCrudaction();
        if(StringUtils.isNotBlank(crudaction)){
            if(crudaction.equals("search")){
                RequestUtil.initSearchBean4report(request, command);
                convertDate2Timestamp(command);
                Map<String, Object> properties = buildProperties(command);
                if(properties.size() > 0){
                    mav.addObject("hasFilter", true);
                }
                Object[] resultObject = this.branchManagementLocalBean.searchPaymentAgency(properties, properties.size() > 0 ? 0 : command.getFirstItem(), properties.size() > 0 ? Integer.MAX_VALUE : command.getReportMaxPageItems(), command.getSortExpression(), command.getSortDirection());
                command.setTotalItems(Integer.valueOf(resultObject[0].toString()));
                command.setListResult((List<RegistrationTransDTO>)resultObject[1]);
                mav.addObject(Constants.LIST_MODEL_KEY, command);
                mav.addObject("page", command.getPage() - 1);

                if(command.getTotalItems() == 0){
                    mav.addObject(Constants.ALERT_TYPE, "info");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("chinhanh.doi_tien_dbh.no_record_found"));
                }
            }else if(crudaction.equals("chi-tra")){
                convertDate2Timestamp(command);
                generalList(command);
                chiTraDiemBanHangValidator.validate(command, bindingResult);
                if(!bindingResult.hasErrors() && StringUtils.isBlank(command.getMessage())){
                    try{
                        this.branchManagementLocalBean.paymentAgency(SecurityUtils.getLoginUserId(), command.getCheckList(), command.getPojo().getNgayChiTraDateTime());
                        mav.addObject(Constants.ALERT_TYPE, "info");
                        mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("chitradbh.msg.payment_successfully"));
                    }catch (Exception e){
                        mav.addObject(Constants.ALERT_TYPE, "danger");
                        mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("chitradbh.msg.payment_exception"));
                    }
                }else{
                    mav.addObject(Constants.ALERT_TYPE, "danger");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, command.getMessage());
                }
            }else if(crudaction.equals("export")){
                try{
                    convertDate2Timestamp(command);
                    export2Excel(command, request, response);
                }catch (Exception e){
                    mav.addObject(Constants.ALERT_TYPE, "danger");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("export_data_exception"));
                }
            }
        }
        referenceData(command, mav);
        return mav;
    }

    private void generalList(RegistrationTransCommand command){
        List<String> checkListGeneral = new ArrayList<>();
        List<String> complexKeyList = new ArrayList<String>();

        for (String s :command.getCheckList()){
            Long dealer_Id = Long.valueOf(s.split("\\_")[0]);
            java.text.DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String sum_Date = sum_Date = s.split("\\_")[3].toString();
            String soEZ = s.split("\\_")[1];
            Integer totalTrans = Integer.valueOf(s.split("\\_")[2]);

            if (!complexKeyList.contains(dealer_Id + "_" + soEZ)){
                complexKeyList.add(dealer_Id + "_" + soEZ);
                checkListGeneral.add(s);
            }else {
                String listSumDate = checkListGeneral.get(complexKeyList.indexOf(dealer_Id + "_" + soEZ)).split("\\_")[3].toString();
                totalTrans += Integer.valueOf(checkListGeneral.get(complexKeyList.indexOf(dealer_Id + "_" + soEZ)).split("\\_")[2]);
                listSumDate += "#" + sum_Date;
                checkListGeneral.set(complexKeyList.indexOf(dealer_Id + "_" + soEZ), dealer_Id + "_" + soEZ + "_" + totalTrans + "_" + listSumDate);
            }
        }
        command.setCheckList(checkListGeneral.toArray(new String[checkListGeneral.size()]));
    }

    private void convertDate2Timestamp(RegistrationTransCommand command){
        if(command.getFromDate() != null){
            command.getPojo().setFromDate(DateUtil.dateToTimestamp(command.getFromDate(), Constants.VI_DATE_FORMAT));
        }
        if(command.getToDate() != null){
            command.getPojo().setToDate(DateUtil.dateToTimestamp(command.getToDate(), Constants.VI_DATE_FORMAT));
        }
        if(command.getNgayChiTra() != null){
            command.getPojo().setNgayChiTraDateTime(DateUtil.dateToTimestamp(command.getNgayChiTra(), Constants.VI_DATE_FORMAT));
        }
    }


    private Map<String, Object> buildProperties(RegistrationTransCommand command){
        Map<String, Object> properties = new HashMap<String, Object>();
        if(SecurityUtils.userHasAuthority(Constants.ADMIN_ROLE)){
            if(command.getBranchId() != null){
                properties.put("branch_Id", command.getBranchId());
            }
        }else if(SecurityUtils.userHasAuthority(Constants.CHINHANH_ROLE)){
            try{
                BranchMappingDTO branchMappingDTO = this.branchMappingManagementLocalBean.findByBranchIdAndProgramCode(SecurityUtils.getPrincipal().getMappingDBLinkBranchId(), PromotionEnum.THUE_BAO_PTM_2015.getCode());
                properties.put("branch_Id", branchMappingDTO.getBranchId());
            }catch (ObjectNotFoundException oe){
                log.error(oe.getMessage(), oe);
            }
        }
        if(command.getDistrict() != null && command.getDistrict().getDistrict_Id() != null){
            properties.put("district_Id", command.getDistrict().getDistrict_Id());
        }
        if(command.getDealer_Id() != null){
            properties.put("dealer_Id", command.getDealer_Id());
        }
        if(command.getPojo().getPayment_Status().intValue() != -1){
            properties.put("payment_Status", command.getPojo().getPayment_Status());
        }
        if(command.getPojo().getFromDate() != null){
            properties.put("fromDateTime", command.getPojo().getFromDate());
        }
        if(command.getPojo().getToDate() != null){
            properties.put("toDateTime", command.getPojo().getToDate());
        }
        if (StringUtils.isNotBlank(command.getPojo().getEz_Isdn())){
            properties.put("ez", command.getPojo().getEz_Isdn());
        }

        return properties;
    }

    private void referenceData(RegistrationTransCommand command, ModelAndView mav){
        if(SecurityUtils.userHasAuthority(Constants.ADMIN_ROLE) || SecurityUtils.userHasAuthority(Constants.BAOCAO_ROLE)){
            List<BranchDTO> branchList = this.branchManagementLocalBean.findAll_tbptm();
            mav.addObject("branchList", branchList);
            if(command.getBranchId() != null){
                List<DistrictDTO> districtList = this.districtManagementLocalBean.findByBranchId_tbptm(command.getBranchId());
                mav.addObject("districtList", districtList);
            }else{
                List<DistrictDTO> districtList = this.districtManagementLocalBean.findAll_tbptm();
                mav.addObject("districtList", districtList);
            }

            if(command.getDistrict() != null && command.getDistrict().getDistrict_Id() != null){
                List<RetailDealerDTO> retailDealerList = this.retailDealerManagementLocalBean.findByDistrictId_tbptm(command.getDistrict().getDistrict_Id());
                mav.addObject("retailDealerList", retailDealerList);
            }else if(command.getBranchId() != null){
                List<RetailDealerDTO> retailDealerList = this.retailDealerManagementLocalBean.findByBranchId_tbptm(command.getBranchId());
                mav.addObject("retailDealerList", retailDealerList);
            }else{
                List<RetailDealerDTO> retailDealerList = this.retailDealerManagementLocalBean.findAll_tbptm();
                mav.addObject("retailDealerList", retailDealerList);
            }
        }else if (SecurityUtils.userHasAuthority(Constants.CHINHANH_ROLE)){
            if(SecurityUtils.getPrincipal().getBranchId() != null){
                List<DistrictDTO> districtList = this.branchManagementLocalBean.findByBranchId(SecurityUtils.getPrincipal().getBranchId());
                mav.addObject("districtList", districtList);
            }

            if(command.getDistrict() != null && command.getDistrict().getDistrict_Id() != null){
                List<RetailDealerDTO> retailDealerList = this.retailDealerManagementLocalBean.findByDistrictId_tbptm(command.getDistrict().getDistrict_Id());
                mav.addObject("retailDealerList", retailDealerList);
            }else{
                List<RetailDealerDTO> retailDealerList = this.retailDealerManagementLocalBean.findByBranchId_tbptm(SecurityUtils.getPrincipal().getBranchId());
                mav.addObject("retailDealerList", retailDealerList);
            }
        }
        List<Integer> monthList = new ArrayList<Integer>();
        monthList.add(10);
        monthList.add(11);
        monthList.add(12);
        mav.addObject("monthList", monthList);
    }

    private void export2Excel(RegistrationTransCommand command, HttpServletRequest request,HttpServletResponse response)throws Exception{
        try{
            SimpleDateFormat df = new SimpleDateFormat("dd-M-yyyy");
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            String ngayXuatBaoCao = df.format(currentTimestamp);
            Map<String, Object> properties = buildProperties(command);
            Object[] resultObject = this.branchManagementLocalBean.searchPaymentAgency(properties, 0, Integer.MAX_VALUE, command.getSortExpression(), command.getSortDirection());
            List<RegistrationTransDTO> dtoList = (List<RegistrationTransDTO>)resultObject[1];
            String outputFileName = "/files/temp/Baocao_ChiTra_" + ngayXuatBaoCao + ".xls";
            String reportTemplate = request.getSession().getServletContext().getRealPath("/files/temp/export/thuebaoptm_2015/Baocao_ChiTra.xls");
            String export2FileName = request.getSession().getServletContext().getRealPath(outputFileName);
            WorkbookSettings ws = new WorkbookSettings();
            ExcelUtil.setEncoding4Workbook(ws);
            Workbook templateWorkbook = Workbook.getWorkbook(new File(reportTemplate), ws);
            WritableWorkbook workbook = Workbook.createWorkbook(new File(export2FileName), templateWorkbook);
            WritableSheet sheet = workbook.getSheet(0);
            int startRow = 6;

            WritableFont normalFont = new WritableFont(WritableFont.TIMES, 10, WritableFont.NO_BOLD);
            normalFont.setColour(Colour.BLACK);

            WritableFont boldFont = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD);
            normalFont.setColour(Colour.BLACK);

            WritableCellFormat stringCellFormat = new WritableCellFormat(normalFont);
                stringCellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

            WritableCellFormat stringNgayBaoCaoCellFormat = new WritableCellFormat(normalFont);
            stringNgayBaoCaoCellFormat.setBorder(Border.ALL, BorderLineStyle.NONE);
            stringNgayBaoCaoCellFormat.setAlignment(Alignment.CENTRE);

            WritableCellFormat integerCellFormat = new WritableCellFormat(normalFont);
            integerCellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
            integerCellFormat.setAlignment(Alignment.CENTRE);

            NumberFormat nf = new NumberFormat("#,###");
            WritableCellFormat doubleCellFormat = new WritableCellFormat(nf);
            doubleCellFormat.setFont(normalFont);
            doubleCellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
            doubleCellFormat.setAlignment(Alignment.RIGHT);

            if(command.getFromDate() != null || command.getToDate() != null){
                CellValue[] resValueNgayXuatBaoCaoTu = WebCommonUtil.addNgayXuatBaoCao(TOTAL_COLUMN_EXPORT);
                String tuNgay = "";
                String denNgay = "";
                if (command.getFromDate() != null){
                    tuNgay = "Từ ngày: " + df.format(command.getFromDate());
                }
                if ( command.getToDate() != null){
                    denNgay = " Đến ngày: " + df.format(command.getToDate());
                }
                resValueNgayXuatBaoCaoTu[0] = new CellValue(CellDataType.STRING, "Ngày tổng hợp: " + tuNgay + denNgay);
                ExcelUtil.addRow(sheet, 3, resValueNgayXuatBaoCaoTu, stringNgayBaoCaoCellFormat, null, null, null);
            }

            if(dtoList.size() > 0){
                int indexRow = 1;
                for(RegistrationTransDTO dto : dtoList){
                    CellValue[] resValue = addCellValues(dto, indexRow);
                    ExcelUtil.addRow(sheet, startRow++, resValue, stringCellFormat, integerCellFormat, doubleCellFormat, null);
                    indexRow++;
                }
                workbook.write();
                workbook.close();
                response.sendRedirect(request.getSession().getServletContext().getContextPath() + outputFileName);
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            throw new Exception("Failed to export to excel BaoCaoChiTraDBH. \n" + e.getMessage());
        }
    }

    private CellValue[] addCellValues(RegistrationTransDTO dto, int indexRow){
        SimpleDateFormat df = new SimpleDateFormat("dd-M-yyyy");
        CellValue[] resValue = new CellValue[TOTAL_COLUMN_EXPORT];
        int columnIndex = 0;
        resValue[columnIndex++] = new CellValue(CellDataType.INT, indexRow);
        if(StringUtils.isNotBlank(dto.getRetailDealer().getBranch_name())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getRetailDealer().getBranch_name());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(StringUtils.isNotBlank(dto.getRetailDealer().getDistrict_name())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getRetailDealer().getDistrict_name());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getRetailDealer() != null && StringUtils.isNotBlank(dto.getRetailDealer().getDealer_code())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getRetailDealer().getDealer_code());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getRetailDealer() != null && StringUtils.isNotBlank(dto.getRetailDealer().getDealer_name())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getRetailDealer().getDealer_name());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(StringUtils.isNotBlank(dto.getEz_Isdn())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getEz_Isdn());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getRetailDealer() != null && StringUtils.isNotBlank(dto.getRetailDealer().getTax_Code())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getRetailDealer().getTax_Code());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getRetailDealer() != null && StringUtils.isNotBlank(dto.getRetailDealer().getContact_name())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getRetailDealer().getContact_name());
        }else{
            resValue[columnIndex++] = new CellValue();
        }

        if(dto.getRetailDealer() != null && StringUtils.isNotBlank(dto.getRetailDealer().getAddress())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getRetailDealer().getAddress());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getTongTienQuyDoi() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getTongTienQuyDoi());
        } else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getSoTienDuDK() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getSoTienDuDK());
        } else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getSum_Date() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, df.format(dto.getSum_Date()));
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getSum_Date() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, this.getMessageSourceAccessor().getMessage("label.da_chot_ky"));
        }else{
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, this.getMessageSourceAccessor().getMessage("label.chua_chot_ky"));
        }
        if(dto.getPayment_Status() != null){
            if(dto.getPayment_Status() == 1){
                resValue[columnIndex++] = new CellValue(CellDataType.STRING, this.getMessageSourceAccessor().getMessage("label.chitra.da_chi_tra"));
            }else{
                resValue[columnIndex++] = new CellValue(CellDataType.STRING, this.getMessageSourceAccessor().getMessage("label.chitra.chua_chi_tra"));
            }
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getPayment_Date() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, df.format(dto.getPayment_Date()));
        }else{
            resValue[columnIndex++] = new CellValue();
        }

        return resValue;
    }
}
