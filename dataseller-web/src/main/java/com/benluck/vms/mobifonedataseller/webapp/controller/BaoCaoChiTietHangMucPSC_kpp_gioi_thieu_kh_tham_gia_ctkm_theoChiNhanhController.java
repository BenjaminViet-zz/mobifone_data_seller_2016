package com.benluck.vms.mobifonedataseller.webapp.controller;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.utils.DateUtil;
import com.benluck.vms.mobifonedataseller.core.business.BaoCaoChiTietHangMucPSC_kpp_gioi_thieu_kh_tham_gia_ctkm_theoChiNhanh_managementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.BranchManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.DistrictManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BaoCaoChiTietHangMucPSCKPP_gioi_thieu_kh_tham_gia_ctkm_TheoChiNhanhDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BranchDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.DistrictDTO;
import com.benluck.vms.mobifonedataseller.editor.CustomDateEditor;
import com.benluck.vms.mobifonedataseller.security.util.SecurityUtils;
import com.benluck.vms.mobifonedataseller.util.ExcelUtil;
import com.benluck.vms.mobifonedataseller.util.RequestUtil;
import com.benluck.vms.mobifonedataseller.util.WebCommonUtil;
import com.benluck.vms.mobifonedataseller.webapp.command.command2014.BaoCaoChiTietHangMucPSC_kpp_gioi_thieu_kh_tham_gia_ctkm_theoChiNhanhCommand;
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
 * Date: 11/18/14
 * Time: 11:30 AM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class BaoCaoChiTietHangMucPSC_kpp_gioi_thieu_kh_tham_gia_ctkm_theoChiNhanhController extends ApplicationObjectSupport {
    private Log log = LogFactory.getLog(BaoCaoChiTietHangMucPSC_kpp_gioi_thieu_kh_tham_gia_ctkm_theoChiNhanhController.class);
    private static final Integer TOTAL_COLUMN_EXPORT = 7;

    @Autowired
    private BaoCaoChiTietHangMucPSC_kpp_gioi_thieu_kh_tham_gia_ctkm_theoChiNhanh_managementLocalBean reportManagementLocalBean;
    @Autowired
    private BranchManagementLocalBean branchManagementLocalBean;
    @Autowired
    private DistrictManagementLocalBean districtManagementLocalBean;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor("dd/MM/yyyy"));
    }

    @RequestMapping(value = {"/admin/baocaochitiethangmucpsc_gioi_thieu_kh_tham_gia_ctkm_theochinhanh.html","/chinhanh/baocaochitiethangmucpsc_gioi_thieu_kh_tham_gia_ctkm_theochinhanh.html"})
    public ModelAndView report(@ModelAttribute(value = Constants.FORM_MODEL_KEY)BaoCaoChiTietHangMucPSC_kpp_gioi_thieu_kh_tham_gia_ctkm_theoChiNhanhCommand command,
                               HttpServletRequest request,
                               HttpServletResponse response){
        ModelAndView mav = new ModelAndView("/admin/baocaochitiethangmucpsc/gioithieukhthamgiactkm/theochinhanh");
        String crudaction = command.getCrudaction();
        if(StringUtils.isNotBlank(crudaction)){
            if(crudaction.equals("search")){
                RequestUtil.initSearchBean4report(request, command);
                convertDate2Timestamp(command);
                Map<String, Object> properties = buildProperties(command);
                Object[] resultObject = this.reportManagementLocalBean.search(properties, command.getFirstItem(), command.getReportMaxPageItems(), command.getSortExpression(), command.getSortDirection());
                command.setTotalItems(Integer.valueOf(resultObject[0].toString()));
                command.setListResult((List<BaoCaoChiTietHangMucPSCKPP_gioi_thieu_kh_tham_gia_ctkm_TheoChiNhanhDTO>)resultObject[1]);
                mav.addObject(Constants.LIST_MODEL_KEY, command);
                mav.addObject("page", command.getPage() - 1);

                if(command.getTotalItems() == 0){
                    mav.addObject(Constants.ALERT_TYPE, "info");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("admin.baocaochitiethangmucpsc_kpp_gioi_thieu_kh_tham_gia_ctkm.no_record_found"));
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

    private void referenceData(BaoCaoChiTietHangMucPSC_kpp_gioi_thieu_kh_tham_gia_ctkm_theoChiNhanhCommand command, ModelAndView mav){
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
        }else if(SecurityUtils.userHasAuthority(Constants.CHINHANH_ROLE)){
            try{
                BranchDTO branchDTO = this.branchManagementLocalBean.findBranchById_tdcg(SecurityUtils.getPrincipal().getBranchId());
                List<BranchDTO> branchList = new ArrayList<BranchDTO>();
                branchList.add(branchDTO);
                mav.addObject("branchList", branchList);
            }catch (Exception e){}

            List<DistrictDTO> districtList = this.districtManagementLocalBean.findByBranchId_tdcg(SecurityUtils.getPrincipal().getBranchId());
            mav.addObject("districtList", districtList);
        }
    }

    private Map<String, Object> buildProperties(BaoCaoChiTietHangMucPSC_kpp_gioi_thieu_kh_tham_gia_ctkm_theoChiNhanhCommand command){
        Map<String, Object> properties = new HashMap<String, Object>();
        if(command.getBranch_Id() != null){
            properties.put("branch_Id", command.getBranch_Id());
        }
        if(command.getFromDateTime() != null){
            properties.put("fromDateTime", command.getFromDateTime());
        }
        if(command.getToDateTime() != null){
            properties.put("toDateTime", command.getToDateTime());
        }
        return properties;
    }

    private void convertDate2Timestamp(BaoCaoChiTietHangMucPSC_kpp_gioi_thieu_kh_tham_gia_ctkm_theoChiNhanhCommand command){
        if(command.getFromDate() != null){
            command.setFromDateTime(DateUtil.dateToTimestamp(command.getFromDate(), Constants.VI_DATE_FORMAT));
        }
        if(command.getToDate() != null){
            command.setToDateTime(DateUtil.dateToTimestamp(command.getToDate(), Constants.VI_DATE_FORMAT));
        }
    }

    private void export2Excel(BaoCaoChiTietHangMucPSC_kpp_gioi_thieu_kh_tham_gia_ctkm_theoChiNhanhCommand command, HttpServletRequest request,HttpServletResponse response)throws Exception{
        try{
            SimpleDateFormat df = new SimpleDateFormat("dd-M-yyyy");
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            String ngayXuatBaoCao = df.format(currentTimestamp);
            convertDate2Timestamp(command);
            Map<String, Object> properties = buildProperties(command);
            Object[] resultObject = this.reportManagementLocalBean.search(properties, 0, Integer.MAX_VALUE, command.getSortExpression(), command.getSortDirection());
            List<BaoCaoChiTietHangMucPSCKPP_gioi_thieu_kh_tham_gia_ctkm_TheoChiNhanhDTO> dtoList = (List<BaoCaoChiTietHangMucPSCKPP_gioi_thieu_kh_tham_gia_ctkm_TheoChiNhanhDTO>)resultObject[1];
            String reportTemplate = request.getSession().getServletContext().getRealPath("/files/temp/export/baocaochitiethangmuc_psc_kpp_gioi_thieu_kh_tham_gia_ctkm_theochinhanh.xls");
            String outputFileName = "/files/temp/baocaochitiethangmuc_psc_kpp_gioi_thieu_kh_tham_gia_ctkm_theochinhanh_" + ngayXuatBaoCao + ".xls";
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
                for(BaoCaoChiTietHangMucPSCKPP_gioi_thieu_kh_tham_gia_ctkm_TheoChiNhanhDTO dto : dtoList){
                    CellValue[] resValue = addCellValues(dto, indexRow);
                    ExcelUtil.addRow(sheet, startRow++, resValue, stringCellFormat, integerCellFormat, doubleCellFormat, null);
                    indexRow++;
                }
                workbook.write();
                workbook.close();
                response.sendRedirect(request.getSession().getServletContext().getContextPath() + outputFileName);
            }
        }catch (Exception e){
            throw new Exception("Failed to export to excel BaoCaoKetQuaChuongTrinhKPPTheoChiNhanh. \n" + e.getMessage());
        }
    }

    private CellValue[] addCellValues(BaoCaoChiTietHangMucPSCKPP_gioi_thieu_kh_tham_gia_ctkm_TheoChiNhanhDTO dto, int indexRow){
        SimpleDateFormat df = new SimpleDateFormat("dd-M-yyyy");
        CellValue[] resValue = new CellValue[TOTAL_COLUMN_EXPORT];
        int columnIndex = 0;
        resValue[columnIndex++] = new CellValue(CellDataType.INT, indexRow);
        if(StringUtils.isNotBlank(dto.getBranch_Name())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getBranch_Name());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getCuocPS_thoai() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getCuocPS_thoai());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getCuocPS_sms() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getCuocPS_sms());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getCuocPS_data() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getCuocPS_data());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getCuocPS_khac() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getCuocPS_khac());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getCuocPS_total() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getCuocPS_total());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        return resValue;
    }
}
