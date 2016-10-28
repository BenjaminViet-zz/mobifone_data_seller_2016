package com.benluck.vms.mobifonedataseller.webapp.controller;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.core.business.BranchManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.DistrictManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.RetailDealerManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BranchDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.DistrictDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.MoneyExchangeBranchHistoryDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.RetailDealerDTO;
import com.benluck.vms.mobifonedataseller.security.util.SecurityUtils;
import com.benluck.vms.mobifonedataseller.util.ExcelUtil;
import com.benluck.vms.mobifonedataseller.util.RequestUtil;
import com.benluck.vms.mobifonedataseller.webapp.command.command2014.MoneyExchangeBranchHistoryCommand;
import com.benluck.vms.mobifonedataseller.webapp.dto.CellDataType;
import com.benluck.vms.mobifonedataseller.webapp.dto.CellValue;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/26/14
 * Time: 9:24 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class TraCuuLichSuDoiTienController extends ApplicationObjectSupport {
    private Log log = LogFactory.getLog(TraCuuLichSuDoiTienController.class);
    private static final Integer TOTAL_EXPORT_COLUMN = 8;

    @Autowired
    private BranchManagementLocalBean branchManagementLocalBean;
    @Autowired
    private DistrictManagementLocalBean districtManagementLocalBean;
    @Autowired
    private RetailDealerManagementLocalBean retailDealerManagementLocalBean;

    @RequestMapping(value = {"/chinhanh/lichsuchikhuyenkhichdbh.html","/admin/lichsuchikhuyenkhichdbh.html"})
    public ModelAndView traCuuLichSuDoiTienDBH(@ModelAttribute(value = Constants.FORM_MODEL_KEY)MoneyExchangeBranchHistoryCommand command,
                                               HttpServletRequest request,
                                               HttpServletResponse response){
        ModelAndView mav = new ModelAndView("/chinhanh/lichsudoitien_dbh");
        String crudaction = command.getCrudaction();
        if(StringUtils.isNotBlank(crudaction)){
            if(crudaction.equals("search")){
                Object[] resultObject = traCuuLichSuDoiTien(command, request);
                command.setTotalItems(Integer.valueOf(resultObject[0].toString()));
                command.setListResult((List<MoneyExchangeBranchHistoryDTO>)resultObject[1]);
                mav.addObject(Constants.LIST_MODEL_KEY, command);
                mav.addObject("page", command.getPage() - 1);

                if(command.getTotalItems() == 0){
                    mav.addObject(Constants.ALERT_TYPE, "info");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("chinhanh.tra_cuu_lich_su_doi_tien_dbh.no_record_found"));
                }
            }else if(crudaction.equals("export")){
                try{
                    export2Excel(command, request, response);
                }catch (Exception e){
                    log.error(e.getMessage());
                    mav.addObject(Constants.ALERT_TYPE, "danger");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("label.export_failed"));
                }
            }
        }
        if(SecurityUtils.userHasAuthority(Constants.ADMIN_ROLE)){
            List<BranchDTO> branchList = this.branchManagementLocalBean.findAll_tdcg();
            mav.addObject("branchList", branchList);
            if(command.getBranchId() != null){
                List<DistrictDTO> districtList = this.districtManagementLocalBean.findByBranchId_tdcg(command.getBranchId());
                mav.addObject("districtList", districtList);
            }

            if(command.getBranchId() != null){
                List<DistrictDTO> districtList = this.districtManagementLocalBean.findByBranchId_tdcg(command.getBranchId());
                mav.addObject("districtList", districtList);
            }else{
                List<DistrictDTO> districtList = this.districtManagementLocalBean.findAll_tdcg();
                mav.addObject("districtList", districtList);
            }

            if(command.getDistrictId() != null){
                List<RetailDealerDTO> retailDealerList = this.retailDealerManagementLocalBean.findByDistrictId_tdcg(command.getDistrictId());
                mav.addObject("retailDealerList", retailDealerList);
            }else if(command.getBranchId() != null){
                List<RetailDealerDTO> retailDealerList = this.retailDealerManagementLocalBean.findByBranchId_tdcg(command.getBranchId());
                mav.addObject("retailDealerList", retailDealerList);
            }else{
                List<RetailDealerDTO> retailDealerList = this.retailDealerManagementLocalBean.findAll_tdcg();
                mav.addObject("retailDealerList", retailDealerList);
            }
        }else if(SecurityUtils.userHasAuthority(Constants.CHINHANH_ROLE)){
            List<DistrictDTO> districtList = this.districtManagementLocalBean.findByBranchId_tdcg(SecurityUtils.getPrincipal().getBranchId());
            mav.addObject("districtList", districtList);

            if(command.getDistrictId() != null){
                List<RetailDealerDTO> retailDealerList = this.retailDealerManagementLocalBean.findByDistrictId_tdcg(command.getDistrictId());
                mav.addObject("retailDealerList", retailDealerList);
            }else{
                List<RetailDealerDTO> retailDealerList = this.retailDealerManagementLocalBean.findByBranchId_tdcg(SecurityUtils.getPrincipal().getBranchId());
                mav.addObject("retailDealerList", retailDealerList);
            }
        }
        return mav;
    }

    /**
     * Get history of money exchanging of Dealer at by branch.
     * @param command
     * @param request
     * @return
     */
    private Object[] traCuuLichSuDoiTien(MoneyExchangeBranchHistoryCommand command, HttpServletRequest request){
        RequestUtil.initSearchBean(request, command);
        if(SecurityUtils.userHasAuthority(Constants.USERGROUP_CN)){
            command.setBranchId(SecurityUtils.getPrincipal().getBranchId());
        }
        Map<String, Object> properties = buildProperties(command);
        return this.branchManagementLocalBean.searchPaymentHistoryAtAgency(properties, command.getFirstItem(), command.getMaxPageItems(), command.getSortExpression(), command.getSortDirection());
    }

    /**
     * Build pairs of key-value properties for querying.
     * @param command
     * @return
     */
    private Map<String, Object> buildProperties(MoneyExchangeBranchHistoryCommand command){
        Map<String, Object> properties = new HashMap<String, Object>();
        if(command.getBranchId() != null){
            properties.put("branchId", command.getBranchId());
        }
        if(command.getDistrictId() != null){
            properties.put("districtId", command.getDistrictId());
        }
        if(StringUtils.isNotBlank(command.getDealer_code())){
            properties.put("dealer_code", command.getDealer_code());
        }
        if(command.getDealer_Id() != null){
            properties.put("dealer_Id", command.getDealer_Id());
        }
        return properties;
    }

    /**
     * Export the report data to Excel.
     * @param command
     * @param request
     * @param response
     * @throws Exception
     */
    private void export2Excel(MoneyExchangeBranchHistoryCommand command, HttpServletRequest request, HttpServletResponse response)throws Exception{
        try{
            Map<String, Object> properties = buildProperties(command);
            Object[] resultObject = this.branchManagementLocalBean.searchPaymentHistoryAtAgency(properties, 0, Integer.MAX_VALUE, command.getSortExpression(), command.getSortDirection());
            List<MoneyExchangeBranchHistoryDTO> dtoList = (List<MoneyExchangeBranchHistoryDTO>)resultObject[1];
            String outputFileName = "/files/temp/tracuulichsudoitien_dbh_" + System.currentTimeMillis() + ".xls";
            String reportTemplate = request.getSession().getServletContext().getRealPath("/files/temp/export/tracuulichsudoitien_dbh.xls");
            String export2FileName = request.getSession().getServletContext().getRealPath(outputFileName);
            WorkbookSettings ws = new WorkbookSettings();
            ExcelUtil.setEncoding4Workbook(ws);
            Workbook templateWorkbook = Workbook.getWorkbook(new File(reportTemplate), ws);
            WritableWorkbook workbook = Workbook.createWorkbook(new File(export2FileName), templateWorkbook);
            WritableSheet sheet = workbook.getSheet(0);
            int startRow = 4;

            WritableFont normalFont = new WritableFont(WritableFont.TIMES, 10, WritableFont.NO_BOLD);
            normalFont.setColour(Colour.BLACK);

            WritableCellFormat normalStringCellFormat = new WritableCellFormat(normalFont);
            normalStringCellFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.MEDIUM);

            WritableCellFormat integerCellFormat = new WritableCellFormat(normalFont);
            integerCellFormat.setBorder(jxl.format.Border.ALL, BorderLineStyle.MEDIUM);
            integerCellFormat.setAlignment(Alignment.CENTRE);

            NumberFormat nf = new NumberFormat("#,###");
            WritableCellFormat doubleCellFormat = new WritableCellFormat(nf);
            doubleCellFormat.setFont(normalFont);
            doubleCellFormat.setBorder(jxl.format.Border.ALL, BorderLineStyle.MEDIUM);
            doubleCellFormat.setAlignment(Alignment.RIGHT);

            int indexRow = 1;
            if(dtoList.size() > 0){
                for(MoneyExchangeBranchHistoryDTO dto : dtoList){
                    CellValue[] chiNhanhResValue = addToCellValues(dto, indexRow);
                    ExcelUtil.addRow(sheet, startRow++, chiNhanhResValue, normalStringCellFormat, integerCellFormat, doubleCellFormat, null);
                    indexRow++;
                }
                workbook.write();
                workbook.close();
                response.sendRedirect(request.getSession().getServletContext().getContextPath() + outputFileName);
            }
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new Exception("Loi xay ra khi export ra file Excel du lieu lich su doi tien cho DBH.\n Details: " + e.getMessage());
        }
    }

    /**
     * Write DTO's properties to Excel cells for exporting.
     * @param dto
     * @param indexRow
     * @return
     */
    private CellValue[] addToCellValues(MoneyExchangeBranchHistoryDTO dto, Integer indexRow){
        SimpleDateFormat df = new SimpleDateFormat("dd/M/yyyy");
        CellValue[] resValue = new CellValue[TOTAL_EXPORT_COLUMN];
        int columnIndex = 0;
        resValue[columnIndex++] = new CellValue(CellDataType.INT, indexRow);
        if(dto.getBranch() != null && StringUtils.isNotBlank(dto.getBranch().getBranch_name())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getBranch().getBranch_name());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getDistrict() != null && StringUtils.isNotBlank(dto.getDistrict().getDistrict_name())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getDistrict().getDistrict_name());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(StringUtils.isNotBlank(dto.getRetailDealer().getDealer_code())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getRetailDealer().getDealer_code());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(StringUtils.isNotBlank(dto.getRetailDealer().getDealer_name())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getRetailDealer().getDealer_name());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(StringUtils.isNotBlank(dto.getItem_name())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getItem_name());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getExchangeAmount() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getExchangeAmount());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getCreatedDate() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, df.format(dto.getCreatedDate()));
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        return resValue;
    }

    @RequestMapping(value = {"/ajax/chinhanh/getDistrictListByBranch.html","/ajax/admin/getDistrictListByBranch.html"})
    public @ResponseBody Map ajaxLoadDistrictByBranch(@RequestParam(value = "branchId", required = false)Long branchId){
        Map<String, Object> map = new HashMap<String, Object>();
        if(branchId != null){
            List<DistrictDTO> districtList = this.districtManagementLocalBean.findByBranchId_tdcg(branchId);
            map.put("districtList", districtList);
        }else{
            if(SecurityUtils.userHasAuthority(Constants.ADMIN_ROLE)){
                List<DistrictDTO> districtList = this.districtManagementLocalBean.findAll_tdcg();
                map.put("districtList", districtList);
            }else{
                List<DistrictDTO> districtList = this.districtManagementLocalBean.findByBranchId_tdcg(SecurityUtils.getPrincipal().getBranchId());
                map.put("districtList", districtList);
            }
        }
        return map;
    }

    @RequestMapping(value = {"/ajax/chinhanh/getRetailDealerListByBranchId.html","/ajax/admin/getRetailDealerListByBranchId.html"})
    public @ResponseBody Map ajaxLoadRetailDealerByBranch(@RequestParam(value = "branchId", required = false)Long branchId){
        Map<String, Object> map = new HashMap<String, Object>();
        List<RetailDealerDTO> retailDealerList;
        if(branchId != null){
            retailDealerList = this.retailDealerManagementLocalBean.findByBranchId_tdcg(branchId);
        }else{
            retailDealerList = this.retailDealerManagementLocalBean.findAll_tdcg();
        }
        map.put("retailDealerList", retailDealerList);
        return map;
    }
}
