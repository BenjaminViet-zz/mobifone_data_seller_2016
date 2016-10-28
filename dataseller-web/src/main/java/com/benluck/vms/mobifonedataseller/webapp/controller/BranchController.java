package com.benluck.vms.mobifonedataseller.webapp.controller;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.core.business.BranchManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.ChiNhanhManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.DistrictManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.RetailDealerManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BranchDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.DealerAccountDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.DistrictDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.RetailDealerDTO;
import com.benluck.vms.mobifonedataseller.security.util.SecurityUtils;
import com.benluck.vms.mobifonedataseller.util.ExcelUtil;
import com.benluck.vms.mobifonedataseller.util.RequestUtil;
import com.benluck.vms.mobifonedataseller.util.WebCommonUtil;
import com.benluck.vms.mobifonedataseller.webapp.command.command2014.DealerAccountCommand;
import com.benluck.vms.mobifonedataseller.webapp.dto.CellDataType;
import com.benluck.vms.mobifonedataseller.webapp.dto.CellValue;
import com.benluck.vms.mobifonedataseller.webapp.validator.DoiTienDBHValidator;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/25/14
 * Time: 10:07 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class BranchController extends ApplicationObjectSupport {
    private Log log = LogFactory.getLog(UserController.class);
    private static final Integer TOTAL_COLUMN_EXPORT = 10;

    @Autowired
    private BranchManagementLocalBean branchManagementLocalBean;
    @Autowired
    private ChiNhanhManagementLocalBean chiNhanhManagementLocalBean;
    @Autowired
    private DistrictManagementLocalBean districtManagementLocalBean;
    @Autowired
    private RetailDealerManagementLocalBean retailDealerManagementLocalBean;
    @Autowired
    private DoiTienDBHValidator doiTienDBHValidator;

    @RequestMapping(value = {"/chinhanh/chikhuyenkhichdbh.html","/admin/chikhuyenkhichdbh.html"})
    public ModelAndView xacNhanDoiTien(@ModelAttribute(value = Constants.FORM_MODEL_KEY)DealerAccountCommand command,
                                       BindingResult bindingResult,
                                       HttpServletRequest request,
                                       HttpServletResponse response){
        ModelAndView mav = new ModelAndView("/chinhanh/xacnhandoitien_dbh");
        String crudaction = command.getCrudaction();
        if(StringUtils.isNotBlank(crudaction)){
            if(crudaction.equals("search")){
                RequestUtil.initSearchBean(request, command);
                Map<String, Object> properties = buildProperties(command);
                Object[] resultObject = this.branchManagementLocalBean.search(properties, command.getFirstItem(),command.getMaxPageItems(), command.getSortExpression(), command.getSortDirection());
                command.setTotalItems(Integer.valueOf(resultObject[0].toString()));
                command.setListResult((List<DealerAccountDTO>)resultObject[1]);
                mav.addObject(Constants.LIST_MODEL_KEY, command);
                mav.addObject("page", command.getPage() - 1);

                if(command.getTotalItems() == 0){
                    mav.addObject(Constants.ALERT_TYPE, "info");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("chinhanh.doi_tien_dbh.no_record_found"));
                }
            }else if(crudaction.equals("doi-tien")){
                doiTienDBHValidator.validate(command, bindingResult);
                if(!bindingResult.hasErrors() && StringUtils.isBlank(command.getMessage())){
                    try{
                        this.branchManagementLocalBean.moneyExchangeAgency(SecurityUtils.getLoginUserId(), command.getCheckList());
                        mav.addObject(Constants.ALERT_TYPE, "info");
                        mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("chinhanh.doi_tien_dbh.money_exchange_successfully"));
                    }catch (Exception e){
                        mav.addObject(Constants.ALERT_TYPE, "danger");
                        mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("chinhanh.doi_tien_dbh.money_exchange_exception"));
                    }
                }else{
                    mav.addObject(Constants.ALERT_TYPE, "danger");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, command.getMessage());
                }
            }else if(crudaction.equals("export")){
                try{
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

    /**
     * Build pairs of key-value for querying.
     * @param command
     * @return a map instance of key-value pairs for querying.
     */
    private Map<String, Object> buildProperties(DealerAccountCommand command){
        Map<String, Object> properties = new HashMap<String, Object>();
        if(command.getDistrict() != null){
            properties.put("district_Id", command.getDistrict().getDistrict_Id());
        }
        if(command.getDealer_Id() != null){
            properties.put("dealer_Id", command.getDealer_Id());
        }
        if(command.getPojo().getDealer() != null && StringUtils.isNotBlank(command.getPojo().getDealer().getDealer_code())){
            properties.put("dealer_code", command.getPojo().getDealer().getDealer_code());
        }
        if(StringUtils.isNotBlank(command.getTrang_thai_chot_ky()) && !command.getTrang_thai_chot_ky().equals("-1")){
            properties.put("trangThaiChotKy", command.getTrang_thai_chot_ky());
        }
        if(StringUtils.isNotBlank(command.getTrang_thai_doi_tien()) && !command.getTrang_thai_doi_tien().equals("-1")){
            properties.put("trangThaiDoiTien", command.getTrang_thai_doi_tien());
        }
        if(StringUtils.isNotBlank(command.getTrang_thai_du_dk_doi_tien()) && !command.getTrang_thai_du_dk_doi_tien().equals("-1")){
            properties.put("trangThaiDuDieuKienDoiTien", command.getTrang_thai_du_dk_doi_tien());
        }
        if(command.getCycle() != null){
            properties.put("cycle", command.getCycle());
        }
        if(SecurityUtils.userHasAuthority(Constants.ADMIN_ROLE)){
            if(command.getBranchId() != null){
                properties.put("branchId", command.getBranchId());
            }
        }else if(SecurityUtils.userHasAuthority(Constants.CHINHANH_ROLE)){
            properties.put("branchId", SecurityUtils.getPrincipal().getBranchId());
        }
        return properties;
    }

    /**
     * Fetch more related data for this view and put them to the model.
     * @param command
     * @param mav
     */
    private void referenceData(DealerAccountCommand command, ModelAndView mav){
        if(SecurityUtils.userHasAuthority(Constants.ADMIN_ROLE)){
            List<BranchDTO> branchList = this.branchManagementLocalBean.findAll_tdcg();
            mav.addObject("branchList", branchList);
            if(command.getBranchId() != null){
                List<DistrictDTO> districtList = this.districtManagementLocalBean.findByBranchId_tdcg(command.getBranchId());
                mav.addObject("districtList", districtList);
            }else{
                List<DistrictDTO> districtList = this.districtManagementLocalBean.findAll_tdcg();
                mav.addObject("districtList", districtList);
            }

            if(command.getDistrict() != null && command.getDistrict().getDistrict_Id() != null){
                List<RetailDealerDTO> retailDealerList = this.retailDealerManagementLocalBean.findByDistrictId_tdcg(command.getDealer_Id());
                mav.addObject("retailDealerList", retailDealerList);
            }else if(command.getBranchId() != null){
                List<RetailDealerDTO> retailDealerList = this.retailDealerManagementLocalBean.findByBranchId_tdcg(command.getBranchId());
                mav.addObject("retailDealerList", retailDealerList);
            }else{
                List<RetailDealerDTO> retailDealerList = this.retailDealerManagementLocalBean.findAll_tdcg();
                mav.addObject("retailDealerList", retailDealerList);
            }
        }else if (SecurityUtils.userHasAuthority(Constants.CHINHANH_ROLE)){
            if(SecurityUtils.getPrincipal().getBranchId() != null){
                List<DistrictDTO> districtList = this.branchManagementLocalBean.findByBranchId(SecurityUtils.getPrincipal().getBranchId());
                mav.addObject("districtList", districtList);
            }

            if(command.getDistrict() != null && command.getDistrict().getDistrict_Id() != null){
                List<RetailDealerDTO> retailDealerList = this.retailDealerManagementLocalBean.findByDistrictId_tdcg(command.getDistrict().getDistrict_Id());
                mav.addObject("retailDealerList", retailDealerList);
            }else{
                List<RetailDealerDTO> retailDealerList = this.retailDealerManagementLocalBean.findByBranchId_tdcg(SecurityUtils.getPrincipal().getBranchId());
                mav.addObject("retailDealerList", retailDealerList);
            }
        }
        List<Integer> monthList = new ArrayList<Integer>();
        monthList.add(10);
        monthList.add(11);
        monthList.add(12);
        mav.addObject("monthList", monthList);
    }

    /**
     * Export the report data to Excel.
     * @param command
     * @param request
     * @param response
     * @throws Exception
     */
    private void export2Excel(DealerAccountCommand command, HttpServletRequest request,HttpServletResponse response)throws Exception{
        try{
            SimpleDateFormat df = new SimpleDateFormat("dd-M-yyyy");
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            String ngayXuatBaoCao = df.format(currentTimestamp);
            Map<String, Object> properties = buildProperties(command);
            Object[] resultObject = this.branchManagementLocalBean.search(properties, 0, Integer.MAX_VALUE, command.getSortExpression(), command.getSortDirection());
            List<DealerAccountDTO> dtoList = (List<DealerAccountDTO>)resultObject[1];
            String outputFileName = "/files/temp/tracuuthongtindoitiendbh_" + ngayXuatBaoCao + ".xls";
            String reportTemplate = request.getSession().getServletContext().getRealPath("/files/temp/export/tracuuthongtindoitiendbh.xls");
            String export2FileName = request.getSession().getServletContext().getRealPath(outputFileName);
            WorkbookSettings ws = new WorkbookSettings();
            ExcelUtil.setEncoding4Workbook(ws);
            Workbook templateWorkbook = Workbook.getWorkbook(new File(reportTemplate), ws);
            WritableWorkbook workbook = Workbook.createWorkbook(new File(export2FileName), templateWorkbook);
            WritableSheet sheet = workbook.getSheet(0);
            int startRow = 7;

            WritableFont normalFont = new WritableFont(WritableFont.TIMES, 10, WritableFont.NO_BOLD);
            normalFont.setColour(Colour.BLACK);

            WritableFont boldFont = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD);
            normalFont.setColour(Colour.BLACK);

            WritableCellFormat stringCellFormat = new WritableCellFormat(normalFont);
            stringCellFormat.setBorder(Border.ALL, BorderLineStyle.MEDIUM);

            WritableCellFormat stringNgayBaoCaoCellFormat = new WritableCellFormat(normalFont);
            stringNgayBaoCaoCellFormat.setBorder(Border.ALL, BorderLineStyle.NONE);

            WritableCellFormat integerCellFormat = new WritableCellFormat(normalFont);
            integerCellFormat.setBorder(Border.ALL, BorderLineStyle.MEDIUM);
            integerCellFormat.setAlignment(Alignment.CENTRE);

            NumberFormat nf = new NumberFormat("#,###");
            WritableCellFormat doubleCellFormat = new WritableCellFormat(nf);
            doubleCellFormat.setFont(normalFont);
            doubleCellFormat.setBorder(Border.ALL, BorderLineStyle.MEDIUM);
            doubleCellFormat.setAlignment(Alignment.RIGHT);

            if(command.getCycle() != null){
                CellValue[] resValueNgayXuatBaoCaoTu = WebCommonUtil.addNgayXuatBaoCao(TOTAL_COLUMN_EXPORT);
                resValueNgayXuatBaoCaoTu[8] = new CellValue(CellDataType.STRING, "Kỳ báo cáo: ");
                resValueNgayXuatBaoCaoTu[9] = new CellValue(CellDataType.STRING, command.getCycle() + "/" + "2014");
                ExcelUtil.addRow(sheet, 4, resValueNgayXuatBaoCaoTu, stringNgayBaoCaoCellFormat, null, null, null);
            }

            if(dtoList.size() > 0){
                int indexRow = 1;
                for(DealerAccountDTO dto : dtoList){
                    CellValue[] resValue = addCellValues(dto, indexRow);
                    ExcelUtil.addRow(sheet, startRow++, resValue, stringCellFormat, integerCellFormat, doubleCellFormat, null);
                    indexRow++;
                }
                workbook.write();
                workbook.close();
                response.sendRedirect(request.getSession().getServletContext().getContextPath() + outputFileName);
            }
        }catch (Exception e){
            throw new Exception("Failed to export to excel BaoCaoDanhSachMaThuong. \n" + e.getMessage());
        }
    }

    /**
     * Write DTO' properties to Excel cells.
     * @param dto
     * @param indexRow
     * @return
     */
    private CellValue[] addCellValues(DealerAccountDTO dto, int indexRow){
        SimpleDateFormat df = new SimpleDateFormat("dd/M/yyyy");
        CellValue[] resValue = new CellValue[TOTAL_COLUMN_EXPORT];
        int columnIndex = 0;
        resValue[columnIndex++] = new CellValue(CellDataType.INT, indexRow);
        if(StringUtils.isNotBlank(dto.getBranch_Name())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getBranch_Name());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(StringUtils.isNotBlank(dto.getTenQuanHuyen())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getTenQuanHuyen());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getDealer() != null && StringUtils.isNotBlank(dto.getDealer().getDealer_code())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getDealer().getDealer_name());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getDealer() != null && StringUtils.isNotBlank(dto.getDealer().getDealer_name())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getDealer().getDealer_name());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getCycle() != null){
            String ky = df.format(dto.getCycle());
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, ky.substring(ky.indexOf("/") + 1, ky.length()));
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getExchange_amount() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getExchange_amount());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(StringUtils.isNotBlank(dto.getCycle_lock_status())){
            if(dto.getCycle_lock_status().equals(Constants.DEALER_ACCONT_ACTION_DA_CHOT_KY)){
                resValue[columnIndex++] = new CellValue(CellDataType.STRING, this.getMessageSourceAccessor().getMessage("label.da_chot_ky"));
            }else{
                resValue[columnIndex++] = new CellValue(CellDataType.STRING, this.getMessageSourceAccessor().getMessage("label.chua_chot_ky"));
            }
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(StringUtils.isNotBlank(dto.getCondition_status())){
            if(dto.getCycle_lock_status().equals("1")){
                resValue[columnIndex++] = new CellValue(CellDataType.STRING, this.getMessageSourceAccessor().getMessage("label.dat"));
            }else{
                resValue[columnIndex++] = new CellValue(CellDataType.STRING, this.getMessageSourceAccessor().getMessage("label.khong_dat"));
            }
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(StringUtils.isNotBlank(dto.getExchange_money_status())){
            if(dto.getCycle_lock_status().equals(Constants.DEALER_ACCOUNT_ACTION_DA_DOI_TIEN)){
                resValue[columnIndex++] = new CellValue(CellDataType.STRING, this.getMessageSourceAccessor().getMessage("label.da_doi_tien"));
            }else{
                resValue[columnIndex++] = new CellValue(CellDataType.STRING, this.getMessageSourceAccessor().getMessage("label.chua_chot_ky"));
            }
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        return resValue;
    }
}
