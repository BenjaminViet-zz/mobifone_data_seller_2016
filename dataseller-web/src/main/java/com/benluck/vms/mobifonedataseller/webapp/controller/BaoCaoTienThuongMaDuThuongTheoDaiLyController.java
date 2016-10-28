package com.benluck.vms.mobifonedataseller.webapp.controller;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.utils.DateUtil;
import com.benluck.vms.mobifonedataseller.core.business.BaoCaoTienThuongMaDuThuongManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.DistrictManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.RetailDealerManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.*;
import com.benluck.vms.mobifonedataseller.editor.CustomDateEditor;
import com.benluck.vms.mobifonedataseller.security.util.SecurityUtils;
import com.benluck.vms.mobifonedataseller.util.ExcelUtil;
import com.benluck.vms.mobifonedataseller.util.RequestUtil;
import com.benluck.vms.mobifonedataseller.util.WebCommonUtil;
import com.benluck.vms.mobifonedataseller.webapp.command.command2014.BaoCaoTienThuongMaDuThuongCommand;
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
 * User: Benjamin Luck
 * Date: 10/28/14
 * Time: 9:21 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class BaoCaoTienThuongMaDuThuongTheoDaiLyController extends ApplicationObjectSupport{
    private Log log = LogFactory.getLog(BaoCaoThongTinThiTruongTheoDaiLyController.class);

    private static final Integer TOTAL_COLUMN_EXPORT = 17;

    @Autowired
    private BaoCaoTienThuongMaDuThuongManagementLocalBean baoCaoTienThuongMaDuThuongManagementLocalBean;
    @Autowired
    private DistrictManagementLocalBean districtManagementLocalBean;
    @Autowired
    private RetailDealerManagementLocalBean retailDealerManagementLocalBean;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor("dd/MM/yyyy"));
    }

    @RequestMapping(value = {"/admin/baocaotienthuongmaduthuongtheodaily.html","/chinhanh/baocaotienthuongmaduthuongtheodaily.html"})
    public ModelAndView baoCaoTienThuongMaDuThuong(@ModelAttribute(value = Constants.FORM_MODEL_KEY)BaoCaoTienThuongMaDuThuongCommand command,
                                                   HttpServletRequest request,
                                                   HttpServletResponse response){
        ModelAndView mav = new ModelAndView("/admin/baocaotienthuongmaduthuongtheodaily");
        String crudaction = command.getCrudaction();
        if(StringUtils.isNotBlank(crudaction)){
            if(crudaction.equals("search")){
                RequestUtil.initSearchBean4report(request, command);
                convertDate2Timestamp(command);
                Map<String, Object> properties = buildProperties(command);
                Object[] resultObject = this.baoCaoTienThuongMaDuThuongManagementLocalBean.baoCaoTienThuongMaDuThuongTheoDaiLy(properties, command.getFirstItem(), command.getReportMaxPageItems(), command.getSortExpression(), command.getSortDirection());
                command.setTotalItems(Integer.valueOf(resultObject[0].toString()));
                command.setListResult((List<BaoCaoTienThuongMaDuThuongDTO>) resultObject[1]);
                mav.addObject("page", command.getPage() - 1);
                mav.addObject(Constants.LIST_MODEL_KEY, command);

                if(command.getTotalItems() == 0){
                    mav.addObject(Constants.ALERT_TYPE, "info");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("admin.baocaodanhsachmathuong.msg.no_record_found"));
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
            List<BranchDTO> branchList = this.baoCaoTienThuongMaDuThuongManagementLocalBean.findAllBranches();
            mav.addObject("branchList", branchList);

            List<DistrictDTO> districtList = this.districtManagementLocalBean.findAll_tdcg();
            mav.addObject("districtList",districtList);

            List<RetailDealerDTO> retailDealerList = this.retailDealerManagementLocalBean.findAll_tdcg();
            mav.addObject("retailDealerList", retailDealerList);
        }else if(SecurityUtils.userHasAuthority(Constants.CHINHANH_ROLE)){
            try{
                BranchDTO branchDTO = this.baoCaoTienThuongMaDuThuongManagementLocalBean.findBranchById(SecurityUtils.getPrincipal().getBranchId());
                List<BranchDTO> branchList = new ArrayList<BranchDTO>();
                branchList.add(branchDTO);
                mav.addObject("branchList", branchList);
            }catch (Exception e){}

            List<DistrictDTO> districtList = this.baoCaoTienThuongMaDuThuongManagementLocalBean.findByBranchId(SecurityUtils.getPrincipal().getBranchId());
            mav.addObject("districtList", districtList);

            if(command.getDistrict_Id() != null){
                List<RetailDealerDTO> retailDealerList = this.retailDealerManagementLocalBean.findByDistrictId_tdcg(command.getDistrict_Id());
                mav.addObject("retailDealerList", retailDealerList);
            }else{
                List<RetailDealerDTO> retailDealerList = this.retailDealerManagementLocalBean.findByBranchId_tdcg(SecurityUtils.getPrincipal().getBranchId());
                mav.addObject("retailDealerList", retailDealerList);
            }
        }
        List<PromItemsDTO> itemList = this.baoCaoTienThuongMaDuThuongManagementLocalBean.findAllItems();
        mav.addObject("itemList", itemList);
        return mav;
    }

    private Map<String, Object> buildProperties(BaoCaoTienThuongMaDuThuongCommand command){
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
        if(command.getItem_Id() != null){
            properties.put("item_Id", command.getItem_Id());
        }
        if(command.getFromDateTime() != null){
            properties.put("fromDateTime", command.getFromDateTime());
        }
        if(command.getToDateTime() != null){
            properties.put("toDateTime", command.getToDateTime());
        }
        if(StringUtils.isNotBlank(command.getSoEZ())){
            properties.put("soEZ", command.getSoEZ().trim());
        }
        if(StringUtils.isNotBlank(command.getTrang_thai_tra_thuong())){
            properties.put("trangThaiTraThuong", command.getTrang_thai_tra_thuong().trim());
        }
        return properties;
    }

    private void convertDate2Timestamp(BaoCaoTienThuongMaDuThuongCommand command){
        if(command.getFromDate() != null){
            command.setFromDateTime(DateUtil.dateToTimestamp(command.getFromDate(), Constants.VI_DATE_FORMAT));
        }
        if(command.getToDate() != null){
            command.setToDateTime(DateUtil.dateToTimestamp(command.getToDate(), Constants.VI_DATE_FORMAT));
        }
    }

    private void export2Excel(BaoCaoTienThuongMaDuThuongCommand command, HttpServletRequest request,HttpServletResponse response)throws Exception{
        try{
            SimpleDateFormat df = new SimpleDateFormat("dd-M-yyyy");
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            String ngayXuatBaoCao = df.format(currentTimestamp);
            convertDate2Timestamp(command);
            Map<String, Object> properties = buildProperties(command);
            Object[] resultObject = this.baoCaoTienThuongMaDuThuongManagementLocalBean.baoCaoTienThuongMaDuThuongTheoDaiLy(properties, 0, Integer.MAX_VALUE, command.getSortExpression(), command.getSortDirection());
            List<BaoCaoTienThuongMaDuThuongDTO> dtoList = (List<BaoCaoTienThuongMaDuThuongDTO>)resultObject[1];
            String outputFileName = "/files/temp/baocaotienthuongmaduthuongtheodaily_" + ngayXuatBaoCao + ".xls";
            String reportTemplate = request.getSession().getServletContext().getRealPath("/files/temp/export/baocaotienthuongmaduthuongtheodaily.xls");
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
                for(BaoCaoTienThuongMaDuThuongDTO dto : dtoList){
                    CellValue[] resValue = addCellValues(dto, indexRow);
                    ExcelUtil.addRow(sheet, startRow++, resValue, stringCellFormat, integerCellFormat, doubleCellFormat, null);
                    indexRow++;
                }
                workbook.write();
                workbook.close();
                response.sendRedirect(request.getSession().getServletContext().getContextPath() + outputFileName);
            }
        }catch (Exception e){
            throw new Exception("Failed to export to excel BaoCaoTienThuongMaDuThuongTheoDaiLy. \n" + e.getMessage());
        }
    }

    private CellValue[] addCellValues(BaoCaoTienThuongMaDuThuongDTO dto, int indexRow){
        CellValue[] resValue = new CellValue[TOTAL_COLUMN_EXPORT];
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
        if(StringUtils.isNotBlank(dto.getAddress())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getAddress());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(StringUtils.isNotBlank(dto.getItem_Name())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getItem_Name());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getNgay() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.INT, dto.getNgay());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getThang() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.INT, dto.getThang());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getNam() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.INT, dto.getNam());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getSoThucTe() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getSoThucTe());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getSoDuocTinhDiem() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getSoDuocTinhDiem());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getSoDuocQuiDoi() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getSoDuocQuiDoi());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getSoDiemQuiDoi() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getSoDiemQuiDoi());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getSoTienQuiDoi() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getSoTienQuiDoi());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getSoMaDuThuong() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.INT, dto.getSoMaDuThuong());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        StringBuilder trangThaiTraThuong = new StringBuilder("");
        if(StringUtils.isNotBlank(dto.getTrang_thai_tra_thuong())){
            if(dto.getTrang_thai_tra_thuong().equals(Constants.TRANG_THAI_TRA_THUONG_CHUA_CHI_TRA)){
                trangThaiTraThuong = new StringBuilder(this.getMessageSourceAccessor().getMessage("label.chua_chi_tra"));
            }else if(dto.getTrang_thai_tra_thuong().equals(Constants.TRANG_THAI_TRA_THUONG_DA_CHI_TRA)){
                trangThaiTraThuong = new StringBuilder(this.getMessageSourceAccessor().getMessage("label.da_chi_tra"));
            }else if(dto.getTrang_thai_tra_thuong().equals(Constants.TRANG_THAI_TRA_THUONG_KHONG_DAT)){
                trangThaiTraThuong = new StringBuilder(this.getMessageSourceAccessor().getMessage("label.khong_dat"));
            }
        }
        resValue[columnIndex++] = new CellValue(CellDataType.STRING, trangThaiTraThuong.toString());
        return resValue;
    }
}
