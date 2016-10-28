package com.benluck.vms.mobifonedataseller.webapp.controller;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.utils.DateUtil;
import com.benluck.vms.mobifonedataseller.core.business.BaoCaoTheoHangMucPhatSinhManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.DistrictManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BaoCaoTheoHangMucPhatSinhDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BranchDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.DistrictDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.PromItemsDTO;
import com.benluck.vms.mobifonedataseller.editor.CustomDateEditor;
import com.benluck.vms.mobifonedataseller.security.util.SecurityUtils;
import com.benluck.vms.mobifonedataseller.util.ExcelUtil;
import com.benluck.vms.mobifonedataseller.util.RequestUtil;
import com.benluck.vms.mobifonedataseller.util.WebCommonUtil;
import com.benluck.vms.mobifonedataseller.webapp.command.command2014.BaoCaoTheoHangMucPhatSinhCommand;
import com.benluck.vms.mobifonedataseller.webapp.dto.CellDataType;
import com.benluck.vms.mobifonedataseller.webapp.dto.CellValue;
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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: HauKute
 * Date: 10/25/14
 * Time: 2:59 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class BaoCaoTheoHangMucPhatSinhController extends ApplicationObjectSupport {
    private transient final Log log = LogFactory.getLog(getClass());
    private static final Integer TOTAL_COLUMN_EXPORT = 12;

    @Autowired
    private BaoCaoTheoHangMucPhatSinhManagementLocalBean baoCaoTheoHangMucPhatSinhManagementLocalBean;
    @Autowired
    private DistrictManagementLocalBean districtManagementLocalBean;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor("dd/MM/yyyy"));
    }

    @RequestMapping(value = {"/admin/baocaotheohangmucphatsinh.html","/chinhanh/baocaotheohangmucphatsinh.html"})
    public ModelAndView bcchokenhphanphoi(@ModelAttribute(value = Constants.FORM_MODEL_KEY)BaoCaoTheoHangMucPhatSinhCommand command,
                                        HttpServletRequest request,
                                        HttpServletResponse response){
        ModelAndView mav = new ModelAndView("/admin/baocaotheohangmucphatsinh");
        String crudaction = command.getCrudaction();
        if(StringUtils.isNotBlank(crudaction)){
            if(crudaction.equals("search")){
                RequestUtil.initSearchBean4report(request, command);
                convertDate2Timestamp(command);
                Map<String, Object> properties = buildProperties(command);
                Object[] resultObject = this.baoCaoTheoHangMucPhatSinhManagementLocalBean.baoCaoTheoHangMucPhatSinh(properties, command.getFirstItem(), command.getReportMaxPageItems(), command.getSortExpression(), command.getSortDirection());
                command.setTotalItems(Integer.valueOf(resultObject[0].toString()));
                command.setListResult((List<BaoCaoTheoHangMucPhatSinhDTO>)resultObject[1]);
                mav.addObject(Constants.LIST_MODEL_KEY, command);
                mav.addObject("page", command.getPage() - 1);

                if(command.getTotalItems() == 0){
                    mav.addObject(Constants.ALERT_TYPE, "info");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("admin.baocaotheohangmucphatsinh.no_record_found"));
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
            List<BranchDTO> branchList = this.baoCaoTheoHangMucPhatSinhManagementLocalBean.findAllBranches();
            mav.addObject("branchList", branchList);

            if(command.getBranch_Id() != null){
                List<DistrictDTO> districtList = this.districtManagementLocalBean.findByBranchId_tdcg(command.getBranch_Id());
            }else{
                List<DistrictDTO> districtList = this.districtManagementLocalBean.findAll_tdcg();
                mav.addObject("districtList", districtList);
            }
        }else if(SecurityUtils.userHasAuthority(Constants.CHINHANH_ROLE)){
            try{
                BranchDTO branchDTO = this.baoCaoTheoHangMucPhatSinhManagementLocalBean.findBranchById(SecurityUtils.getPrincipal().getBranchId());
                List<BranchDTO> branchList = new ArrayList<BranchDTO>();
                branchList.add(branchDTO);
                mav.addObject("branchList", branchList);
            }catch (Exception e){}

            List<DistrictDTO> districtList = this.baoCaoTheoHangMucPhatSinhManagementLocalBean.findByBranchId(SecurityUtils.getPrincipal().getBranchId());
            mav.addObject("districtList", districtList);
        }
        List<PromItemsDTO> itemList = this.baoCaoTheoHangMucPhatSinhManagementLocalBean.findAllItems();
        mav.addObject("itemList", itemList);
        return mav;

    }

    private Map<String, Object> buildProperties(BaoCaoTheoHangMucPhatSinhCommand command){
        Map<String, Object> properties = new HashMap<String, Object>();
        if(command.getBranch_Id() != null){
            properties.put("branch_Id", command.getBranch_Id());
        }
        if(command.getDistrict_Id() != null){
            properties.put("district_Id", command.getDistrict_Id());
        }
        if(command.getItem_Id() != null){
            properties.put("item_Id", command.getItem_Id());
        }
        if(command.getFromDateTime() != null){
            properties.put("fromDateTime", command.getFromDateTime());
        }
        if(command.getToDateTime() != null){
            properties.put("toDateTime", command.getToDateTime());
        }
        return properties;
    }

    private void convertDate2Timestamp(BaoCaoTheoHangMucPhatSinhCommand command){
        if(command.getFromDate() != null){
            command.setFromDateTime(DateUtil.dateToTimestamp(command.getFromDate(), Constants.VI_DATE_FORMAT));
        }
        if(command.getToDate() != null){
            command.setToDateTime(DateUtil.dateToTimestamp(command.getToDate(), Constants.VI_DATE_FORMAT));
        }
    }

    private void export2Excel(BaoCaoTheoHangMucPhatSinhCommand command, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try{
            SimpleDateFormat df = new SimpleDateFormat("dd-M-yyyy");
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            String ngayXuatBaoCao = df.format(currentTimestamp);
            convertDate2Timestamp(command);
            Map<String, Object> properties = buildProperties(command);
            Object[] resultObject = this.baoCaoTheoHangMucPhatSinhManagementLocalBean.baoCaoTheoHangMucPhatSinh(properties, 0, Integer.MAX_VALUE, command.getSortExpression(), command.getSortDirection());
            List<BaoCaoTheoHangMucPhatSinhDTO> dtoList = (List<BaoCaoTheoHangMucPhatSinhDTO>)resultObject[1];
            String outputFileName = "/files/temp/baocaotheohangmucphatsinh_" + ngayXuatBaoCao + ".xls";
            String reportTemplate = request.getSession().getServletContext().getRealPath("/files/temp/export/baocaotheohangmucphatsinh.xls");
            String export2FileName = request.getSession().getServletContext().getRealPath(outputFileName);
            WorkbookSettings ws = new WorkbookSettings();
            ExcelUtil.setEncoding4Workbook(ws);
            Workbook templateWorkbook = Workbook.getWorkbook(new File(reportTemplate), ws);
            WritableWorkbook workbook = Workbook.createWorkbook(new File(export2FileName), templateWorkbook);
            WritableSheet sheet = workbook.getSheet(0);
            int startRow = 7;

            WritableFont normalFont = new WritableFont(WritableFont.TIMES, 10, WritableFont.NO_BOLD);
            normalFont.setColour(Colour.BLACK);

            WritableCellFormat stringCellFormat = new WritableCellFormat(normalFont);
            stringCellFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.MEDIUM);

            WritableCellFormat stringNgayBaoCaoCellFormat = new WritableCellFormat(normalFont);
            stringNgayBaoCaoCellFormat.setBorder(Border.ALL, BorderLineStyle.NONE);
            stringNgayBaoCaoCellFormat.setAlignment(Alignment.CENTRE);

            WritableCellFormat integerCellFormat = new WritableCellFormat(normalFont);
            integerCellFormat.setBorder(jxl.format.Border.ALL, BorderLineStyle.MEDIUM);
            integerCellFormat.setAlignment(Alignment.CENTRE);

            NumberFormat nf = new NumberFormat("#,###");
            WritableCellFormat doubleCellFormat = new WritableCellFormat(nf);
            doubleCellFormat.setFont(normalFont);
            doubleCellFormat.setBorder(jxl.format.Border.ALL, BorderLineStyle.MEDIUM);
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

            Double totalSoPS = new Double(0);
            Double totalQuyDiem = new Double(0);
            Double totalSoTienTuongUng = new Double(0);
            Integer totalSoMaDuThuong = 0;
            if(dtoList.size() > 0){
                int indexRow = 1;
                for(BaoCaoTheoHangMucPhatSinhDTO dto : dtoList){
                    CellValue[] values = addCellValues(dto, indexRow);
                    totalSoPS += dto.getSoPS() != null ? dto.getSoPS() : 0;
                    totalQuyDiem += dto.getQuyDiem() != null ? dto.getQuyDiem() : 0;
                    totalSoMaDuThuong += dto.getSoMaDuThuong() != null ? dto.getSoMaDuThuong() : 0;
                    totalSoTienTuongUng += dto.getSoTienTuongUng() != null ? dto.getSoTienTuongUng() : 0;
                    ExcelUtil.addRow(sheet, startRow++, values, stringCellFormat, integerCellFormat, doubleCellFormat, null);
                    indexRow++;
                }

                // for summary
                int columnIndex = 0;
                CellValue[] summaryRow = new CellValue[TOTAL_COLUMN_EXPORT];
                summaryRow[columnIndex++] = new CellValue(CellDataType.STRING, this.getMessageSourceAccessor().getMessage("label.tong_cong"));
                summaryRow[columnIndex++] = new CellValue();
                summaryRow[columnIndex++] = new CellValue();
                summaryRow[columnIndex++] = new CellValue();
                summaryRow[columnIndex++] = new CellValue(CellDataType.DOUBLE, totalSoPS);
                summaryRow[columnIndex++] = new CellValue();
                summaryRow[columnIndex++] = new CellValue();
                summaryRow[columnIndex++] = new CellValue();
                summaryRow[columnIndex++] = new CellValue(CellDataType.DOUBLE, totalQuyDiem);
                summaryRow[columnIndex++] = new CellValue(CellDataType.DOUBLE, totalSoTienTuongUng);
                summaryRow[columnIndex++] = new CellValue(CellDataType.INT, totalSoMaDuThuong);
                summaryRow[columnIndex++] = new CellValue();
                ExcelUtil.addRow(sheet, startRow++, summaryRow, stringCellFormat, integerCellFormat, doubleCellFormat, null);

                workbook.write();
                workbook.close();
                response.sendRedirect(request.getSession().getServletContext().getContextPath() + outputFileName);
            }
        }catch (Exception e){
            throw new Exception("Error when export data to excel.\n + details: " + e.getMessage());
        }
    }

    private CellValue[] addCellValues(BaoCaoTheoHangMucPhatSinhDTO dto, int indexRow) {
        CellValue[] resValue = new CellValue[TOTAL_COLUMN_EXPORT];
        SimpleDateFormat df = new SimpleDateFormat("dd/M/yyyy");
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
        if(StringUtils.isNotBlank(dto.getItem_Name())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getItem_Name());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getSoPS() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getSoPS());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(StringUtils.isNotBlank(dto.getDonViTinh())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getDonViTinh());
        }else{
            resValue[columnIndex++] = new CellValue();
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
        if(dto.getQuyDiem() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getQuyDiem());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getSoTienTuongUng() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getSoTienTuongUng());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getSoMaDuThuong() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.INT, dto.getSoMaDuThuong());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        StringBuilder du_dieu_kien_status = null;
        if(dto.getDu_dieu_kien_status() != null){
            if(dto.getDu_dieu_kien_status().equals(Constants.DU_DIEU_KIEN)){
                du_dieu_kien_status = new StringBuilder(this.getMessageSourceAccessor().getMessage("admin.baocaotheohangmucphatsinh.label.du_dieu_kien"));
            }else{
                du_dieu_kien_status = new StringBuilder(this.getMessageSourceAccessor().getMessage("admin.baocaotheohangmucphatsinh.khong_du_dieu_kien"));
            }
        }else{
            du_dieu_kien_status = new StringBuilder(this.getMessageSourceAccessor().getMessage("admin.baocaotheohangmucphatsinh.khong_du_dieu_kien"));
        }
        resValue[columnIndex++] = new CellValue(CellDataType.STRING, du_dieu_kien_status.toString());
        return resValue;
    }

}
