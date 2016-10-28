package com.benluck.vms.mobifonedataseller.webapp.controller;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.utils.DateUtil;
import com.benluck.vms.mobifonedataseller.core.business.*;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BaoCaoTongHopKetQuaChuongTrinhKPPTheoDaiLyDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BranchDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.DistrictDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.RetailDealerDTO;
import com.benluck.vms.mobifonedataseller.editor.CustomDateEditor;
import com.benluck.vms.mobifonedataseller.security.util.SecurityUtils;
import com.benluck.vms.mobifonedataseller.util.ExcelUtil;
import com.benluck.vms.mobifonedataseller.util.RequestUtil;
import com.benluck.vms.mobifonedataseller.util.WebCommonUtil;
import com.benluck.vms.mobifonedataseller.webapp.command.command2014.BaoCaoTongHopKetQuaChuongTrinhKPPTheoDaiLyCommand;
import com.benluck.vms.mobifonedataseller.webapp.dto.CellDataType;
import com.benluck.vms.mobifonedataseller.webapp.dto.CellValue;
import com.benluck.vms.mobifonedataseller.webapp.validator.BaoCaoTongHopKetQuaChuongTrinhKPPTheoDaiLyValidator;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.lang.Boolean;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 11/16/14
 * Time: 9:48 AM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class BaoCaoTongHopKetQuaChuongTrinhKPPTheoDBHController extends ApplicationObjectSupport{
    private Log log = LogFactory.getLog(BaoCaoTongHopKetQuaChuongTrinhKPPTheoDBHController.class);

    @Autowired
    private BaoCaoTongHopKetQuaChuongTrinh_kpp_theoDBHManagementLocalBean reportManagementLocalBean;
    @Autowired
    private BranchManagementLocalBean branchManagementLocalBean;
    @Autowired
    private DistrictManagementLocalBean districtManagementLocalBean;
    @Autowired
    private RetailDealerManagementLocalBean retailDealerManagementLocalBean;
    @Autowired
    private BaoCaoTongHopKetQuaChuongTrinhKPPTheoDaiLyValidator validator;

    private static final Integer TOTAL_COLUMN_EXPORT_FILTER_DATE = 19;
    private static final Integer TOTAL_COLUMN_EXPORT_NO_FILTER_DATE = 18;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor("dd/MM/yyyy"));
    }

    @RequestMapping(value = {"/admin/baocaotonghopketquachuongtrinh_kpp_theodaily.html","/chinhanh/baocaotonghopketquachuongtrinh_kpp_theodaily.html"})
    public ModelAndView baoCaoTheoQuanHuyen(@ModelAttribute(value = Constants.FORM_MODEL_KEY)BaoCaoTongHopKetQuaChuongTrinhKPPTheoDaiLyCommand command,
                                            HttpServletRequest request,
                                            HttpServletResponse response,
                                            BindingResult bindingResult){
        ModelAndView mav = new ModelAndView("/admin/baocaotonghopketquachuongtrinh_kpp/theodaily");
        String crudaction = command.getCrudaction();
        if(StringUtils.isNotBlank(crudaction)){
            if(crudaction.equals("search")){
                validator.validate(command, bindingResult);
                if(!bindingResult.hasErrors()){
                    RequestUtil.initSearchBean4report(request, command);
                    convertDate2Timestamp(command);
                    Map<String, Object> properties = buildProperties(command);
                    Object[] resultObject = this.reportManagementLocalBean.search(properties, command.getFirstItem(), command.getReportMaxPageItems(), command.getSortExpression(), command.getSortDirection());
                    command.setTotalItems(Integer.valueOf(resultObject[0].toString()));
                    command.setListResult((List<BaoCaoTongHopKetQuaChuongTrinhKPPTheoDaiLyDTO>)resultObject[1]);
                    mav.addObject(Constants.LIST_MODEL_KEY, command);
                    mav.addObject("page", command.getPage() - 1);

                    if(command.getTotalItems() == 0){
                        mav.addObject(Constants.ALERT_TYPE, "info");
                        mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("admin.baocaotonghopchuongtrinhkpp.msg.no_record_found"));
                    }
                }
            }else if(crudaction.equals("export")){
                validator.validate(command, bindingResult);
                if(!bindingResult.hasErrors()){
                    try{
                        export2Excel(command, request, response);
                    }catch (Exception e){
                        mav.addObject(Constants.ALERT_TYPE, "danger");
                        mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("export_data_exception"));
                    }
                }
            }
        }
        referenceData(command, mav);
        return mav;
    }

    /**
     * Fetch more related data base on user role and put them into the model.
     * @param command
     * @param mav
     */
    private void referenceData(BaoCaoTongHopKetQuaChuongTrinhKPPTheoDaiLyCommand command, ModelAndView mav){
        if(SecurityUtils.userHasAuthority(Constants.ADMIN_ROLE) || SecurityUtils.userHasAuthority(Constants.BAOCAO_ROLE)){
            List<BranchDTO> branchList = this.branchManagementLocalBean.findAll_tdcg();
            mav.addObject("branchList", branchList);

            if(command.getBranch_Id() != null){
                List<DistrictDTO> districtList = this.districtManagementLocalBean.findByBranchId_tdcg(command.getBranch_Id());
                mav.addObject("districtList", districtList);
            }else{
                List<DistrictDTO> districtList = this.districtManagementLocalBean.findAll_tdcg();
                mav.addObject("districtList", districtList);
            }
            if(command.getDistrict_Id() != null){
                List<RetailDealerDTO> retailDealerList = this.retailDealerManagementLocalBean.findByDistrictId_tdcg(command.getDistrict_Id());
                mav.addObject("retailDealerList", retailDealerList);
            }else if(command.getBranch_Id() != null){
                List<RetailDealerDTO> retailDealerList = this.retailDealerManagementLocalBean.findByBranchId_tdcg(command.getBranch_Id());
                mav.addObject("retailDealerList", retailDealerList);
            }else{
                List<RetailDealerDTO> retailDealerList = this.retailDealerManagementLocalBean.findAll_tdcg();
                mav.addObject("retailDealerList", retailDealerList);
            }
        }else if(SecurityUtils.userHasAuthority(Constants.CHINHANH_ROLE)){
            try{
                BranchDTO branchDTO = this.branchManagementLocalBean.findBranchById_tdcg(SecurityUtils.getPrincipal().getBranchId());
                List<BranchDTO> branchList = new ArrayList<BranchDTO>();
                branchList.add(branchDTO);
                mav.addObject("branchList", branchList);
            }catch (Exception e){}

            List<DistrictDTO> districtList = this.districtManagementLocalBean.findByBranchId_tdcg(SecurityUtils.getPrincipal().getBranchId());
            mav.addObject("districtList", districtList);

            if(command.getDistrict_Id() != null){
                List<RetailDealerDTO> retailDealerList = this.retailDealerManagementLocalBean.findByDistrictId_tdcg(command.getDistrict_Id());
                mav.addObject("retailDealerList", retailDealerList);
            }else{
                List<RetailDealerDTO> retailDealerList = this.retailDealerManagementLocalBean.findByBranchId_tdcg(SecurityUtils.getPrincipal().getBranchId());
                mav.addObject("retailDealerList", retailDealerList);
            }
        }

        List<Integer> giaiDoanList = new ArrayList<Integer>();
        giaiDoanList.add(10);
        giaiDoanList.add(11);
        giaiDoanList.add(12);
        mav.addObject("giaiDoanList", giaiDoanList);
    }

    /**
     * Build pairs of key-value properties for querying.
     * @param command
     * @return
     */
    private Map<String, Object> buildProperties(BaoCaoTongHopKetQuaChuongTrinhKPPTheoDaiLyCommand command){
        Map<String, Object> properties = new HashMap<String, Object>();
        if(command.getBranch_Id() != null){
            properties.put("branch_Id", command.getBranch_Id());
        }
        if(command.getDistrict_Id() != null){
            properties.put("district_Id", command.getDistrict_Id());
        }
        if(command.getDealer_Id() != null){
            properties.put("dealer_Id", command.getDealer_Id());
        }
        if(command.getGiaiDoan() != null){
            properties.put("cycle", command.getGiaiDoan());
        }
        if(command.getFromDateTime() != null){
            properties.put("fromDateTime", command.getFromDateTime());
        }
        if(command.getToDateTime() != null){
            properties.put("toDateTime", command.getToDateTime());
        }
        if(command.getTrang_thai_dat() != null && !command.getTrang_thai_dat().equals(-1)){
            properties.put("condition_status", command.getTrang_thai_dat());
        }
        return properties;
    }

    /**
     * Get Date value and format to Timestamp.
     * @param command
     */
    private void convertDate2Timestamp(BaoCaoTongHopKetQuaChuongTrinhKPPTheoDaiLyCommand command){
        if(command.getFromDate() != null){
            command.setFromDateTime(DateUtil.dateToTimestamp(command.getFromDate(), Constants.VI_DATE_FORMAT));
        }
        if(command.getToDate() != null){
            command.setToDateTime(DateUtil.dateToTimestamp(command.getToDate(), Constants.VI_DATE_FORMAT));
        }
    }

    /**
     * Export the report data to Excel file.
     * @param command
     * @param request
     * @param response
     * @throws Exception
     */
    private void export2Excel(BaoCaoTongHopKetQuaChuongTrinhKPPTheoDaiLyCommand command, HttpServletRequest request,HttpServletResponse response)throws Exception{
        try{
            SimpleDateFormat df = new SimpleDateFormat("dd-M-yyyy");
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            String ngayXuatBaoCao = df.format(currentTimestamp);
            convertDate2Timestamp(command);
            Map<String, Object> properties = buildProperties(command);
            Object[] resultObject = this.reportManagementLocalBean.search(properties, 0, Integer.MAX_VALUE, command.getSortExpression(), command.getSortDirection());
            List<BaoCaoTongHopKetQuaChuongTrinhKPPTheoDaiLyDTO> dtoList = (List<BaoCaoTongHopKetQuaChuongTrinhKPPTheoDaiLyDTO>)resultObject[1];
            String outputFileName = "/files/temp/baocaoketquachuongtrinh_kpp_theodaily_" + ngayXuatBaoCao + ".xls";
            String exportTemplatePath = "/files/temp/export/baocaoketquachuongtrinh_kpp_theodaily_nofilterdate.xls";

            boolean hasFilterDate = true;
            if(command.getFromDateTime() == null && command.getToDateTime() == null){
                hasFilterDate = false;
            }

            if(hasFilterDate){
                exportTemplatePath = "/files/temp/export/baocaoketquachuongtrinh_kpp_theodaily_filterdate.xls";
            }
            String reportTemplate = request.getSession().getServletContext().getRealPath(exportTemplatePath);
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

            Integer totalExportCol = TOTAL_COLUMN_EXPORT_FILTER_DATE;

            if(command.getFromDateTime() != null){
                CellValue[] resValueNgayXuatBaoCaoTu = WebCommonUtil.addNgayXuatBaoCao(totalExportCol);
                resValueNgayXuatBaoCaoTu[0] = new CellValue(CellDataType.STRING, "Ngày xuất báo cáo từ: " + df.format(command.getFromDateTime()));
                ExcelUtil.addRow(sheet, 3, resValueNgayXuatBaoCaoTu, stringNgayBaoCaoCellFormat, null, null, null);
            }
            if(command.getToDateTime() != null){
                CellValue[] resValueNgayXuatBaoCaoTu = WebCommonUtil.addNgayXuatBaoCao(totalExportCol);
                resValueNgayXuatBaoCaoTu[0] = new CellValue(CellDataType.STRING, "Ngày xuất báo cáo đến: " + df.format(command.getToDateTime()));
                ExcelUtil.addRow(sheet, 4, resValueNgayXuatBaoCaoTu, stringNgayBaoCaoCellFormat, null, null, null);
            }

            if(dtoList.size() > 0){
                int indexRow = 1;
                for(BaoCaoTongHopKetQuaChuongTrinhKPPTheoDaiLyDTO dto : dtoList){
                    CellValue[] resValue = addCellValues(dto, indexRow, hasFilterDate);
                    ExcelUtil.addRow(sheet, startRow++, resValue, stringCellFormat, integerCellFormat, doubleCellFormat, null);
                    indexRow++;
                }
                workbook.write();
                workbook.close();
                response.sendRedirect(request.getSession().getServletContext().getContextPath() + outputFileName);
            }
        }catch (Exception e){
            throw new Exception("Failed to export to excel BaoCaoKetQuaChuongTrinhKPPTheoDaiLy. \n" + e.getMessage());
        }
    }

    /**
     * Write DTO's properties to Excel cells.
     * @param dto
     * @param indexRow
     * @param hasFilterDate
     * @return
     */
    private CellValue[] addCellValues(BaoCaoTongHopKetQuaChuongTrinhKPPTheoDaiLyDTO dto, int indexRow, Boolean hasFilterDate){
        SimpleDateFormat df = new SimpleDateFormat("dd-M-yyyy");
        CellValue[] resValue = new CellValue[TOTAL_COLUMN_EXPORT_NO_FILTER_DATE];
        if(hasFilterDate != null && hasFilterDate.booleanValue()){
            resValue = new CellValue[TOTAL_COLUMN_EXPORT_FILTER_DATE];
        }
        int columnIndex = 0;
        resValue[columnIndex++] = new CellValue(CellDataType.INT, indexRow);
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
        if(dto.getGiaiDoan() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.INT, dto.getGiaiDoan());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(hasFilterDate != null && hasFilterDate.booleanValue()){
            if(dto.getNgay() != null){
                resValue[columnIndex++] = new CellValue(CellDataType.STRING, df.format(dto.getNgay()));
            }else{
                resValue[columnIndex++] = new CellValue();
            }
        }
        if(StringUtils.isNotBlank(dto.getDealer_Code())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getDealer_Code());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(StringUtils.isNotBlank(dto.getDealer_Name())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getDealer_Name());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(StringUtils.isNotBlank(dto.getTrang_thai_dat()) && dto.getTrang_thai_dat().equals("1")){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, this.getMessageSourceAccessor().getMessage("label.dat"));
        }else{
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, this.getMessageSourceAccessor().getMessage("label.khong_dat"));
        }
        if(dto.getSoLuongBTG_MuaTuMobifone() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getSoLuongBTG_MuaTuMobifone());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getDoanhThuMuaThe() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getDoanhThuMuaThe());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getSoLuongVAS() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getSoLuongVAS());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getDoanhThu_VAS() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getDoanhThu_VAS());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getSoLuongTon_BanTrucTiepBTG() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getSoLuongTon_BanTrucTiepBTG());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getSoLuongMoi_BanTrucTiepBTG() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getSoLuongMoi_BanTrucTiepBTG());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getCuocPSBoTon_BanTrucTiepBTG() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getCuocPSBoTon_BanTrucTiepBTG());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getCuocPSBoMoi_BanTrucTiepBTG() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getCuocPSBoMoi_BanTrucTiepBTG());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getSoLuong_GioiThieuKH() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getSoLuong_GioiThieuKH());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getSoLuongPSCuoc_GioiThieuKH() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getSoLuongPSCuoc_GioiThieuKH());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getCuocPS_GioiThieuKH() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getCuocPS_GioiThieuKH());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        return resValue;
    }
}
