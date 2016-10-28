package com.benluck.vms.mobifonedataseller.webapp.controller.controler2015;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.utils.DateUtil;
import com.benluck.vms.mobifonedataseller.core.business.*;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BranchDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.DistrictDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.RetailDealerDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015.BaoCaoTongHopKetQuaChuongTrinhDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015.PromPackageDTO;
import com.benluck.vms.mobifonedataseller.editor.CustomDateEditor;
import com.benluck.vms.mobifonedataseller.security.util.SecurityUtils;
import com.benluck.vms.mobifonedataseller.common.utils.PromotionEnum;
import com.benluck.vms.mobifonedataseller.util.ExcelUtil;
import com.benluck.vms.mobifonedataseller.util.RequestUtil;
import com.benluck.vms.mobifonedataseller.util.WebCommonUtil;
import com.benluck.vms.mobifonedataseller.webapp.command.command2015.BaoCaoTongHopKetQuaChuongTrinhCommand;
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

import javax.ejb.ObjectNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: HP
 * Date: 3/2/15
 * Time: 10:14 AM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class BaoCaoTongHopKetQuaChuongTrinh_TheoDailyController extends ApplicationObjectSupport {
    private Log log = LogFactory.getLog(BaoCaoTinNhanDangKyGoiDBHController.class);
    private static final Integer TOTAL_COLUMN_EXPORT = 20;

    @Autowired
    private PromPackageManagementLocalBean promPackageService;
    @Autowired
    private RetailDealerManagementLocalBean retailDealerManagementLocalBean;
    @Autowired
    private BranchManagementLocalBean branchManagementLocalBean;
    @Autowired
    private DistrictManagementLocalBean districtManagementLocalBean;
    @Autowired
    private BaoCaoTongHopKetQuaChuongTrinhManagementLocalBean baoCaoTongHopKetQuaChuongTrinhService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor("dd/MM/yyyy"));
    }

    @RequestMapping(value = {"/admin/thuebaophattrienmoi/baocaotonghop_ketquachuongtrinh_theodaily.html",
            "/chinhanh/thuebaophattrienmoi/baocaotonghop_ketquachuongtrinh_theodaily.html",
            "/baocao/thuebaophattrienmoi/baocaotonghop_ketquachuongtrinh_theodaily.html"})
    public ModelAndView report(@ModelAttribute(value = Constants.FORM_MODEL_KEY)BaoCaoTongHopKetQuaChuongTrinhCommand command,
                               HttpServletRequest request,
                               HttpServletResponse response)throws ObjectNotFoundException{
        ModelAndView mav = new ModelAndView("2015/thuebaophattrienmoi/admin/baocaotonghopketquachuongtrinh/theodaily");
        String crudaction = command.getCrudaction();
        if (StringUtils.isNotBlank(crudaction)){
            if (crudaction.equals("search")){
                RequestUtil.initSearchBean4report(request, command);
                convertDate2Timestamp(command);
                Map<String, Object> properties = buildProperties(command);
                Object[] results = this.baoCaoTongHopKetQuaChuongTrinhService.baoCaoTongHopKetQuaChuongTrinh_TheoDiemBanHang(properties, command.getSortExpression(), command.getSortDirection(), command.getFirstItem(), command.getReportMaxPageItems());
                command.setListResult((List<BaoCaoTongHopKetQuaChuongTrinhDTO>)results[1]);
                command.setTotalItems(Integer.valueOf(results[0].toString()));
                mav.addObject(Constants.LIST_MODEL_KEY, command);
                mav.addObject("page", command.getPage() - 1);
                if(command.getTotalItems() == 0){
                    mav.addObject(Constants.ALERT_TYPE, "info");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("admin.baocaotheohangmucphatsinh.no_record_found"));
                }
            } else if(crudaction.equals("export")){
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

    private void referenceData(BaoCaoTongHopKetQuaChuongTrinhCommand command, ModelAndView mav) throws ObjectNotFoundException {
        List<PromPackageDTO> promPackage = promPackageService.findAll();
        mav.addObject("promPackage", promPackage);
        if(SecurityUtils.userHasAuthority(Constants.ADMIN_ROLE) || SecurityUtils.userHasAuthority(Constants.BAOCAO_ROLE)){
            List<BranchDTO> branchList = this.branchManagementLocalBean.findByCTCode(PromotionEnum.THUE_BAO_PTM_2015.getCode());
            mav.addObject("branchList", branchList);
            if(command.getPojo().getBranch() != null && command.getPojo().getBranch().getBranch_Id() != null){
                List<DistrictDTO> districtList = this.districtManagementLocalBean.findByBranchIdAndCTCode(command.getPojo().getBranch().getBranch_Id(), PromotionEnum.THUE_BAO_PTM_2015.getCode());
                mav.addObject("districtList", districtList);
            }else{
                List<DistrictDTO> districtList = this.districtManagementLocalBean.findAllByChuongTrinhCode(PromotionEnum.THUE_BAO_PTM_2015.getCode());
                mav.addObject("districtList", districtList);
            }
            if(command.getPojo().getDistrict() != null && command.getPojo().getDistrict().getDistrict_Id() != null){
                List<RetailDealerDTO> retailDealerList = this.retailDealerManagementLocalBean.findByDistrictIdAndCTCode(command.getPojo().getDistrict().getDistrict_Id(), PromotionEnum.THUE_BAO_PTM_2015.getCode());
                mav.addObject("retailDealerList", retailDealerList);
            }else if(command.getPojo().getBranch() != null && command.getPojo().getBranch().getBranch_Id() != null){
                List<RetailDealerDTO> retailDealerList = this.retailDealerManagementLocalBean.findByBranchIdAndCTCode(command.getPojo().getBranch().getBranch_Id(), PromotionEnum.THUE_BAO_PTM_2015.getCode());
                mav.addObject("retailDealerList", retailDealerList);
            }else{
                List<RetailDealerDTO> retailDealerList = this.retailDealerManagementLocalBean.findAllByCTCode(PromotionEnum.THUE_BAO_PTM_2015.getCode());
                mav.addObject("retailDealerList", retailDealerList);
            }
        }else if(SecurityUtils.userHasAuthority(Constants.CHINHANH_ROLE)){
            try{
                BranchDTO branchDTO = this.branchManagementLocalBean.findByChiNhanhIdAndCTCode(SecurityUtils.getPrincipal().getMappingDBLinkBranchId(), PromotionEnum.THUE_BAO_PTM_2015.getCode());
                List<BranchDTO> branchList = new ArrayList<BranchDTO>();
                branchList.add(branchDTO);
                mav.addObject("branchList", branchList);


                List<DistrictDTO> districtList = this.districtManagementLocalBean.findByBranchIdAndCTCode(branchDTO.getBranch_Id(), PromotionEnum.THUE_BAO_PTM_2015.getCode());
                mav.addObject("districtList", districtList);

                if(command.getPojo().getDistrict() != null && command.getPojo().getDistrict().getDistrict_Id() != null){
                    List<RetailDealerDTO> retailDealerList = this.retailDealerManagementLocalBean.findByDistrictIdAndCTCode(command.getPojo().getDistrict().getDistrict_Id(), PromotionEnum.THUE_BAO_PTM_2015.getCode());
                    mav.addObject("retailDealerList", retailDealerList);
                }else{
                    List<RetailDealerDTO> retailDealerList = this.retailDealerManagementLocalBean.findByBranchIdAndCTCode(branchDTO.getBranch_Id(), PromotionEnum.THUE_BAO_PTM_2015.getCode());
                    mav.addObject("retailDealerList", retailDealerList);
                }
            }catch (Exception e){}
        }
    }
    private Map<String, Object> buildProperties(BaoCaoTongHopKetQuaChuongTrinhCommand command){
        Map<String, Object> properties = new HashMap<String, Object>();
        if(command.getPojo().getPromPackage() != null && command.getPojo().getPromPackage().getPackage_Id() != null){
            properties.put("packageId", command.getPojo().getPromPackage().getPackage_Id());
        }
        if(command.getPojo().getBranch() != null && command.getPojo().getBranch().getBranch_Id() != null){
            properties.put("branchId", command.getPojo().getBranch().getBranch_Id());
        }
        if(command.getPojo().getDistrict() != null && command.getPojo().getDistrict().getDistrict_Id() != null){
            properties.put("districtId", command.getPojo().getDistrict().getDistrict_Id());
        }
        if(command.getPojo().getRetailDealer() != null && command.getPojo().getRetailDealer().getDealer_Id() != null){
            properties.put("retailDealerId", command.getPojo().getRetailDealer().getDealer_Id());
        }
        if(command.getPojo().getFromDate() != null){
            properties.put("fromDateTime", command.getPojo().getFromDate());
        }
        if(command.getPojo().getToDate() != null){
            properties.put("toDateTime", command.getPojo().getToDate());
        }
        return properties;
    }
    private void convertDate2Timestamp(BaoCaoTongHopKetQuaChuongTrinhCommand command){
        if(command.getFromDate() != null){
            command.getPojo().setFromDate(DateUtil.dateToTimestamp(command.getFromDate(), Constants.VI_DATE_FORMAT));
        }
        if(command.getToDate() != null){
            command.getPojo().setToDate(DateUtil.dateToTimestamp(command.getToDate(), Constants.VI_DATE_FORMAT));
        }
    }
    private void export2Excel(BaoCaoTongHopKetQuaChuongTrinhCommand command, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try{
            SimpleDateFormat df = new SimpleDateFormat("dd-M-yyyy");
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            String ngayXuatBaoCao = df.format(currentTimestamp);
            Map<String, Object> properties = buildProperties(command);
            Object[] resultObject = this.baoCaoTongHopKetQuaChuongTrinhService.baoCaoTongHopKetQuaChuongTrinh_TheoDiemBanHang(properties, command.getSortExpression(), command.getSortDirection(), 0, Integer.MAX_VALUE);
            List<BaoCaoTongHopKetQuaChuongTrinhDTO> dtoList = (List<BaoCaoTongHopKetQuaChuongTrinhDTO>)resultObject[1];

            String outputFileName = "/files/temp/baocaotonghopketquachuongtrinh_theodaily" + ngayXuatBaoCao + ".xls";
            String reportTemplate = request.getSession().getServletContext().getRealPath("/files/temp/export/thuebaoptm_2015/baocaotonghopketquachuongtrinh_theodaily.xls");
            String export2FileName = request.getSession().getServletContext().getRealPath(outputFileName);
            WorkbookSettings ws = new WorkbookSettings();
            ExcelUtil.setEncoding4Workbook(ws);
            Workbook templateWorkbook = Workbook.getWorkbook(new File(reportTemplate), ws);
            WritableWorkbook workbook = Workbook.createWorkbook(new File(export2FileName), templateWorkbook);
            WritableSheet sheet = workbook.getSheet(0);
            int startRow = 10;

            WritableFont normalFont = new WritableFont(WritableFont.TIMES, 10, WritableFont.NO_BOLD);
            normalFont.setColour(Colour.BLACK);

            WritableCellFormat stringCellFormat = new WritableCellFormat(normalFont);
            stringCellFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);

            WritableCellFormat stringNgayBaoCaoCellFormat = new WritableCellFormat(normalFont);
            stringNgayBaoCaoCellFormat.setBorder(Border.ALL, BorderLineStyle.NONE);
            stringNgayBaoCaoCellFormat.setAlignment(Alignment.CENTRE);

            WritableCellFormat integerCellFormat = new WritableCellFormat(normalFont);
            integerCellFormat.setBorder(jxl.format.Border.ALL, BorderLineStyle.THIN);
            integerCellFormat.setAlignment(Alignment.CENTRE);

            NumberFormat nf = new NumberFormat("#,###");
            WritableCellFormat doubleCellFormat = new WritableCellFormat(nf);
            doubleCellFormat.setFont(normalFont);
            doubleCellFormat.setBorder(jxl.format.Border.ALL, BorderLineStyle.THIN);
            doubleCellFormat.setAlignment(Alignment.RIGHT);

            WritableCellFormat stringFillterCellFormat = new WritableCellFormat(normalFont);
            stringFillterCellFormat.setBorder(Border.ALL, BorderLineStyle.NONE);
            stringFillterCellFormat.setAlignment(Alignment.LEFT);

            if (command.getPojo().getPromPackage() != null && command.getPojo().getPromPackage().getPackage_Id() != null
                    || command.getFromDate() != null || command.getToDate() != null){
                CellValue[] resValueNgayXuatBaoCaoTu = WebCommonUtil.addNgayXuatBaoCao(TOTAL_COLUMN_EXPORT);
                resValueNgayXuatBaoCaoTu[2] = new CellValue(CellDataType.STRING, "Ngày giao dịch: ");
                resValueNgayXuatBaoCaoTu[5] = new CellValue(CellDataType.STRING, " Gói Cước: ");

                if(command.getFromDate() != null || command.getToDate() != null){
                    String tuNgay = "";
                    String denNgay = "";
                    if (command.getFromDate() != null){
                        tuNgay = "Từ ngày: " + df.format(command.getFromDate());
                    }
                    if ( command.getToDate() != null){
                        denNgay = " Đến ngày: " + df.format(command.getToDate());
                    }
                    resValueNgayXuatBaoCaoTu[3] = new CellValue(CellDataType.STRING, tuNgay +" "+ denNgay);
                }

                if (command.getPojo().getPromPackage() != null && command.getPojo().getPromPackage().getPackage_Id() != null){
                    resValueNgayXuatBaoCaoTu[6] = new CellValue(CellDataType.STRING, dtoList.get(0).getPromPackage().getPackage_Name());
                }else {
                    resValueNgayXuatBaoCaoTu[6] = new CellValue(CellDataType.STRING, this.getMessageSourceAccessor().getMessage("label.all"));
                }
                ExcelUtil.addRow(sheet, 4, resValueNgayXuatBaoCaoTu, stringFillterCellFormat, null, null, null);
            } else {
                CellValue[] resValueNgayXuatBaoCaoTu = WebCommonUtil.addNgayXuatBaoCao(TOTAL_COLUMN_EXPORT);
                resValueNgayXuatBaoCaoTu[2] = new CellValue(CellDataType.STRING, "Ngày giao dịch: ");
                resValueNgayXuatBaoCaoTu[5] = new CellValue(CellDataType.STRING, "Gói Cước: ");
                resValueNgayXuatBaoCaoTu[6] = new CellValue(CellDataType.STRING, this.getMessageSourceAccessor().getMessage("label.all"));
                ExcelUtil.addRow(sheet, 4, resValueNgayXuatBaoCaoTu, stringFillterCellFormat, null, null, null);
            }


            if (command.getPojo().getBranch() != null && command.getPojo().getBranch().getBranch_Id() != null
                    || command.getPojo().getDistrict() != null && command.getPojo().getDistrict().getDistrict_Id() != null){
                CellValue[] resValueNgayXuatBaoCaoTu = WebCommonUtil.addNgayXuatBaoCao(TOTAL_COLUMN_EXPORT);
                resValueNgayXuatBaoCaoTu[2] = new CellValue(CellDataType.STRING, "Chi nhánh: ");
                resValueNgayXuatBaoCaoTu[5] = new CellValue(CellDataType.STRING, "Quận huyện: ");

                if (command.getPojo().getBranch() != null && command.getPojo().getBranch().getBranch_Id() != null){
                    resValueNgayXuatBaoCaoTu[3] = new CellValue(CellDataType.STRING, dtoList.get(0).getBranch().getBranch_name());
                } else {
                    resValueNgayXuatBaoCaoTu[3] = new CellValue(CellDataType.STRING, this.getMessageSourceAccessor().getMessage("label.all"));

                }
                if (command.getPojo().getDistrict() != null && command.getPojo().getDistrict().getDistrict_Id() != null){
                    resValueNgayXuatBaoCaoTu[6] = new CellValue(CellDataType.STRING, dtoList.get(0).getDistrict().getDistrict_name());
                }else {
                    resValueNgayXuatBaoCaoTu[6] = new CellValue(CellDataType.STRING, this.getMessageSourceAccessor().getMessage("label.all"));
                }
                ExcelUtil.addRow(sheet, 5, resValueNgayXuatBaoCaoTu, stringFillterCellFormat, null, null, null);
            }else {
                CellValue[] resValueNgayXuatBaoCaoTu = WebCommonUtil.addNgayXuatBaoCao(TOTAL_COLUMN_EXPORT);
                resValueNgayXuatBaoCaoTu[2] = new CellValue(CellDataType.STRING, "Chi nhánh: ");
                resValueNgayXuatBaoCaoTu[5] = new CellValue(CellDataType.STRING, "Quận huyện: ");
                resValueNgayXuatBaoCaoTu[3] = new CellValue(CellDataType.STRING, this.getMessageSourceAccessor().getMessage("label.all"));
                resValueNgayXuatBaoCaoTu[6] = new CellValue(CellDataType.STRING, this.getMessageSourceAccessor().getMessage("label.all"));
                ExcelUtil.addRow(sheet, 5, resValueNgayXuatBaoCaoTu, stringFillterCellFormat, null, null, null);
            }

            if (command.getPojo().getRetailDealer() != null && command.getPojo().getRetailDealer().getDealer_Id() != null){
                CellValue[] resValueNgayXuatBaoCaoTu = WebCommonUtil.addNgayXuatBaoCao(TOTAL_COLUMN_EXPORT);
                resValueNgayXuatBaoCaoTu[2] = new CellValue(CellDataType.STRING, "Điểm bán hàng: ");

                if (command.getPojo().getRetailDealer() != null && command.getPojo().getRetailDealer().getDealer_Id() != null){
                    resValueNgayXuatBaoCaoTu[3] = new CellValue(CellDataType.STRING, dtoList.get(0).getRetailDealer().getDealer_name() + "-" + dtoList.get(0).getRetailDealer().getDealer_code());
                }else {
                    resValueNgayXuatBaoCaoTu[3] = new CellValue(CellDataType.STRING, this.getMessageSourceAccessor().getMessage("label.all"));
                }
                ExcelUtil.addRow(sheet, 6, resValueNgayXuatBaoCaoTu, stringFillterCellFormat, null, null, null);
            }else {
                CellValue[] resValueNgayXuatBaoCaoTu = WebCommonUtil.addNgayXuatBaoCao(TOTAL_COLUMN_EXPORT);
                resValueNgayXuatBaoCaoTu[2] = new CellValue(CellDataType.STRING, "Điểm bán hàng: ");
                resValueNgayXuatBaoCaoTu[3] = new CellValue(CellDataType.STRING,  this.getMessageSourceAccessor().getMessage("label.all"));
                ExcelUtil.addRow(sheet, 6, resValueNgayXuatBaoCaoTu, stringFillterCellFormat, null, null, null);
            }


            if(dtoList.size() > 0){
                int indexRow = 1;
                for(BaoCaoTongHopKetQuaChuongTrinhDTO dto : dtoList){
                    CellValue[] values = addCellValues(dto, indexRow);
                    ExcelUtil.addRow(sheet, startRow++, values, stringCellFormat, integerCellFormat, doubleCellFormat, null);
                    indexRow++;
                }
                workbook.write();
                workbook.close();
                response.sendRedirect(request.getSession().getServletContext().getContextPath() + outputFileName);
            }
        }catch (Exception e){
            throw new Exception("Error when export data to excel.\n + details: " + e.getMessage());
        }
    }

    private CellValue[] addCellValues(BaoCaoTongHopKetQuaChuongTrinhDTO dto, int indexRow) {
        CellValue[] resValue = new CellValue[TOTAL_COLUMN_EXPORT];
        SimpleDateFormat df = new SimpleDateFormat("dd/M/yyyy");
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
        if(StringUtils.isNotBlank(dto.getSoEZ())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getSoEZ());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getSoLuongBTGMoi() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.INT, dto.getSoLuongBTGMoi());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getSoLuongBTGTon() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.INT, dto.getSoLuongBTGTon());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getSoLuongBTGTon() != null || dto.getSoLuongBTGMoi() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.INT, dto.getSoLuongBTGTon() + dto.getSoLuongBTGMoi());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getSoLuongGoiT50_s() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.INT, dto.getSoLuongGoiT50_s());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getSoLuongGoi3T50_s() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.INT, dto.getSoLuongGoi3T50_s());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getSoLuongGoiT100_s() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.INT, dto.getSoLuongGoiT100_s());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getSoLuongGoi3T100_s() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.INT, dto.getSoLuongGoi3T100_s());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getSoLuongGoiT50_CT() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.INT, dto.getSoLuongGoiT50_CT());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getSoLuongGoi3T50_CT() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.INT, dto.getSoLuongGoi3T50_CT());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getSoLuongGoiT100_CT() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.INT, dto.getSoLuongGoiT100_CT());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getSoLuongGoi3T100_CT() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.INT, dto.getSoLuongGoi3T100_CT());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getDoanhThuBTGMoi() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getDoanhThuBTGMoi());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getDoanhThuBTGTon() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getDoanhThuBTGTon());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getDoanhThuBTGMoi() != null || dto.getDoanhThuBTGMoi() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getDoanhThuBTGMoi() + dto.getDoanhThuBTGTon());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        return resValue;
    }
}
