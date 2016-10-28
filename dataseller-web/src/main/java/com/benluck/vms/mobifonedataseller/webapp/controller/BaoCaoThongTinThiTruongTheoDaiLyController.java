
package com.benluck.vms.mobifonedataseller.webapp.controller;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.utils.DateUtil;
import com.benluck.vms.mobifonedataseller.core.business.BaoCaoThongTinThiTruongManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.DistrictManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.RetailDealerManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BaoCaoTTThiTruongDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BranchDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.DistrictDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.RetailDealerDTO;
import com.benluck.vms.mobifonedataseller.editor.CustomDateEditor;
import com.benluck.vms.mobifonedataseller.security.util.SecurityUtils;
import com.benluck.vms.mobifonedataseller.util.ExcelUtil;
import com.benluck.vms.mobifonedataseller.util.RequestUtil;
import com.benluck.vms.mobifonedataseller.util.WebCommonUtil;
import com.benluck.vms.mobifonedataseller.webapp.command.command2014.BaoCaoThongTinThiTruongCommand;
import com.benluck.vms.mobifonedataseller.webapp.dto.CellDataType;
import com.benluck.vms.mobifonedataseller.webapp.dto.CellValue;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class BaoCaoThongTinThiTruongTheoDaiLyController extends ApplicationObjectSupport {

    @Autowired
    private BaoCaoThongTinThiTruongManagementLocalBean baoCaoThongTinThiTruongManagementLocalBean;
    @Autowired
    private DistrictManagementLocalBean districtManagementLocalBean;
    @Autowired
    private RetailDealerManagementLocalBean retailDealerManagementLocalBean;

    private Log log = LogFactory.getLog(BaoCaoThongTinThiTruongTheoDaiLyController.class);

    private static final Integer TOTAL_COLUMN_EXPORT = 15;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor("dd/MM/yyyy"));
    }

    @RequestMapping(value = {"/admin/baocaothongtinthitruongtheodaily.html","/chinhanh/baocaothongtinthitruongtheodaily.html"})
    public ModelAndView baocaothongtinthitruong(@ModelAttribute(value = Constants.FORM_MODEL_KEY)BaoCaoThongTinThiTruongCommand command,
                                                   HttpServletRequest request,
                                                   HttpServletResponse response){
        ModelAndView mav = new ModelAndView("/admin/baocaothongtinthitruongtheodaily");
        String crudaction = command.getCrudaction();
        if(StringUtils.isNotBlank(crudaction)){
            if(crudaction.equals("search")){
                RequestUtil.initSearchBean4report(request, command);
                convertDate2Timestamp(command);
                Map<String, Object> properties = buildProperties(command);
                Object[] resultObject = this.baoCaoThongTinThiTruongManagementLocalBean.baoCaoThongTinThiTruongTheoDaiLy(properties, command.getFirstItem(), command.getReportMaxPageItems(), command.getSortExpression(), command.getSortDirection());
                command.setTotalItems(Integer.valueOf(resultObject[0].toString()));
                command.setListResult((List<BaoCaoTTThiTruongDTO>)resultObject[1]);
                mav.addObject(Constants.LIST_MODEL_KEY, command);
                mav.addObject("page", command.getPage() - 1);

                if(command.getTotalItems() == 0){
                    mav.addObject(Constants.ALERT_TYPE, "info");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("admin.baocaothongtinthitruong.no_record_found"));
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
        if(SecurityUtils.userHasAuthority(Constants.ADMIN_ROLE) || SecurityUtils.userHasAuthority(Constants.BAOCAO_ROLE)){
            List<BranchDTO> branchList = this.baoCaoThongTinThiTruongManagementLocalBean.findAllBranches();
            mav.addObject("branchList", branchList);

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
            try{
                BranchDTO branchDTO = this.baoCaoThongTinThiTruongManagementLocalBean.findBranchById(SecurityUtils.getPrincipal().getBranchId());
                List<BranchDTO> branchList = new ArrayList<BranchDTO>();
                branchList.add(branchDTO);
                mav.addObject("branchList", branchList);
            }catch (Exception e){}

            List<DistrictDTO> districtList = this.baoCaoThongTinThiTruongManagementLocalBean.findByBranchId(SecurityUtils.getPrincipal().getBranchId());
            mav.addObject("districtList", districtList);

            if(command.getDistrictId() != null){
                List<RetailDealerDTO> retailDealerList = this.retailDealerManagementLocalBean.findByBranchId_tdcg(command.getBranchId());
                mav.addObject("retailDealerList", retailDealerList);
            }else{
                List<RetailDealerDTO> retailDealerList = this.retailDealerManagementLocalBean.findByBranchId_tdcg(SecurityUtils.getPrincipal().getBranchId());
                mav.addObject("retailDealerList", retailDealerList);
            }
        }

        return  mav;
    }

    private void convertDate2Timestamp(BaoCaoThongTinThiTruongCommand command){
        if(command.getFromDate() != null){
            command.setFromDateTime(DateUtil.dateToTimestamp(command.getFromDate(), Constants.VI_DATE_FORMAT));
        }
        if(command.getToDate() != null){
            command.setToDateTime(DateUtil.dateToTimestamp(command.getToDate(), Constants.VI_DATE_FORMAT));
        }
    }

    private Map<String, Object> buildProperties(BaoCaoThongTinThiTruongCommand command){
        Map<String, Object> properties = new HashMap<String, Object>();
        if(command.getBranchId() != null){
            properties.put("branchId", command.getBranchId());
        }else{
            if(SecurityUtils.userHasAuthority(Constants.CHINHANH_ROLE)){
                properties.put("branchId", SecurityUtils.getPrincipal().getBranchId());
            }
        }
        if(command.getDistrictId() != null){
            properties.put("districtId", command.getDistrictId());
        }
        if(command.getRetailDealer() != null && command.getRetailDealer().getDealer_Id() != null){
            properties.put("dealer_Id", command.getRetailDealer().getDealer_Id());
        }
        if(command.getFromDateTime() != null){
            properties.put("fromDateTime", command.getFromDateTime());
        }
        if(command.getToDateTime() != null){
            properties.put("toDateTime", command.getToDateTime());
        }
        return properties;
    }

    @RequestMapping("/ajax/admin/getRetailDealerList.html")
    public @ResponseBody Map ajaxGetDepartmentListByChiNhanh(@RequestParam(value = "branchId", required = false)Long branchId,
                                                             @RequestParam(value = "districtId", required = false)Long districtId){
        Map<String, Object> map = new HashMap<String, Object>();
        List<RetailDealerDTO> dealerDTOs = this.baoCaoThongTinThiTruongManagementLocalBean.findRetailDealerList(branchId, districtId);
        map.put("retailDealerList", dealerDTOs);
        return map;
    }


    private void export2Excel(BaoCaoThongTinThiTruongCommand command, HttpServletRequest request, HttpServletResponse response) throws Exception{
        try{
            SimpleDateFormat df = new SimpleDateFormat("dd-M-yyyy");
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            String ngayXuatBaoCao = df.format(currentTimestamp);
            convertDate2Timestamp(command);
            Map<String, Object> properties = buildProperties(command);
            Object[] resultObject = this.baoCaoThongTinThiTruongManagementLocalBean.baoCaoThongTinThiTruongTheoDaiLy(properties, 0, Integer.MAX_VALUE, command.getSortExpression(), command.getSortDirection());
            List<BaoCaoTTThiTruongDTO> dtoList = (List<BaoCaoTTThiTruongDTO>)resultObject[1];
            String outputFileName = "/files/temp/baocaothongtinthitruongtheodaily_" + ngayXuatBaoCao + ".xls";
            String reportTemplate = request.getSession().getServletContext().getRealPath("/files/temp/export/baocaothongtinthitruongtheodaily.xls");
            String export2FileName = request.getSession().getServletContext().getRealPath(outputFileName);
            WorkbookSettings ws = new WorkbookSettings();
            ExcelUtil.setEncoding4Workbook(ws);
            Workbook templateWorkbook = Workbook.getWorkbook(new File(reportTemplate), ws);
            WritableWorkbook workbook = Workbook.createWorkbook(new File(export2FileName), templateWorkbook);
            WritableSheet sheet = workbook.getSheet(0);
            int startRow = 8;

            WritableFont normalFont = new WritableFont(WritableFont.TIMES, 10, WritableFont.NO_BOLD);
            normalFont.setColour(Colour.BLACK);

            WritableFont boldFont = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD);
            normalFont.setColour(Colour.BLACK);

            WritableCellFormat stringCellFormat = new WritableCellFormat(normalFont);
            stringCellFormat.setBorder(Border.ALL, BorderLineStyle.MEDIUM);

            WritableCellFormat stringNgayBaoCaoCellFormat = new WritableCellFormat(normalFont);
            stringNgayBaoCaoCellFormat.setBorder(Border.ALL, BorderLineStyle.NONE);
            stringNgayBaoCaoCellFormat.setAlignment(Alignment.CENTRE);

            WritableCellFormat integerCellFormat = new WritableCellFormat(normalFont);
            integerCellFormat.setBorder(Border.ALL, BorderLineStyle.MEDIUM);
            integerCellFormat.setAlignment(Alignment.CENTRE);

            NumberFormat nf = new NumberFormat("#,###");
            WritableCellFormat doubleCellFormat = new WritableCellFormat(nf);
            doubleCellFormat.setFont(normalFont);
            doubleCellFormat.setBorder(Border.ALL, BorderLineStyle.MEDIUM);
            doubleCellFormat.setAlignment(Alignment.RIGHT);

            if(command.getFromDateTime() != null){
                CellValue[] resValueNgayXuatBaoCaoTu = WebCommonUtil.addNgayXuatBaoCao(TOTAL_COLUMN_EXPORT);
                resValueNgayXuatBaoCaoTu[0] = new CellValue(CellDataType.STRING, "Ngày xuất báo cáo từ: " + df.format(command.getFromDateTime()));
                ExcelUtil.addRow(sheet, 3, resValueNgayXuatBaoCaoTu, stringNgayBaoCaoCellFormat, null, null, null);
            }
            if(command.getToDateTime() != null){
                CellValue[] resValueNgayXuatBaoCaoTu = WebCommonUtil.addNgayXuatBaoCao(TOTAL_COLUMN_EXPORT);
                resValueNgayXuatBaoCaoTu[0] = new CellValue(CellDataType.STRING, "Ngày xuất báo cáo đến: " + df.format(command.getToDateTime()));
                ExcelUtil.addRow(sheet, 4, resValueNgayXuatBaoCaoTu, stringNgayBaoCaoCellFormat, null, null, null);
            }

            if(dtoList.size() > 0){
                int indexRow = 1;
                for(BaoCaoTTThiTruongDTO dto : dtoList){
                    CellValue[] chiNhanhResValue = addCellValues(dto, indexRow);
                    ExcelUtil.addRow(sheet, startRow++, chiNhanhResValue, stringCellFormat, integerCellFormat, doubleCellFormat, null);
                    indexRow++;
                }
                workbook.write();
                workbook.close();
                response.sendRedirect(request.getSession().getServletContext().getContextPath() + outputFileName);
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            throw new Exception("Error when export data to excel.\n + details: " + e.getMessage());
        }
    }

    private CellValue[] addCellValues(BaoCaoTTThiTruongDTO dto, int STT){
        CellValue[] resValue = new CellValue[TOTAL_COLUMN_EXPORT];
        int columnIndex = 0;
        resValue[columnIndex++] = new CellValue(CellDataType.INT, STT);
        if(StringUtils.isNotBlank(dto.getBranch_Name())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getBranch_Name());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(StringUtils.isNotBlank(dto.getDistrict_Name())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getDistrict_Name());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(StringUtils.isNotBlank(dto.getDealer_code())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getDealer_code());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(StringUtils.isNotBlank(dto.getDealer_Name())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getDealer_Name());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(StringUtils.isNotBlank(dto.getAddress())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getAddress());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getSoLuongBanSimViettel() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.INT, dto.getSoLuongBanSimViettel());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getGiaSimViettel() != null){
            BigDecimal giaSimViettel = new BigDecimal(dto.getGiaSimViettel().doubleValue());
            giaSimViettel = giaSimViettel.setScale(2, BigDecimal.ROUND_HALF_UP);
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, giaSimViettel.doubleValue());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getSoLuongBanTheViettel() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.INT, dto.getSoLuongBanTheViettel());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getSoLuongBanSimMobi() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.INT, dto.getSoLuongBanSimMobi());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getGiaSimMobi() != null){
            BigDecimal giaSimMobi = new BigDecimal(dto.getGiaSimMobi().doubleValue());
            giaSimMobi = giaSimMobi.setScale(2, BigDecimal.ROUND_HALF_UP);
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, giaSimMobi.doubleValue());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getSoLuongBanTheMobi() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.INT, dto.getSoLuongBanTheMobi());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getSoLuongBanSimVina() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.INT, dto.getSoLuongBanSimVina());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getGiaSimVina() != null){
            BigDecimal giaSimViNa = new BigDecimal(dto.getGiaSimVina().doubleValue());
            giaSimViNa = giaSimViNa.setScale(2, BigDecimal.ROUND_HALF_UP);
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, giaSimViNa.doubleValue());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getSoLuongBanTheVina() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.INT, dto.getSoLuongBanTheVina());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        return resValue;
    }

}

