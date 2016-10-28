package com.benluck.vms.mobifonedataseller.webapp.controller.controler2015;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.utils.DateUtil;
import com.benluck.vms.mobifonedataseller.core.business.*;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BranchDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.DistrictDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.RetailDealerDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015.BaoCaoThongKeThueBaoThamGiaGoiCuocDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015.PromPackageDTO;
import com.benluck.vms.mobifonedataseller.editor.CustomDateEditor;
import com.benluck.vms.mobifonedataseller.security.util.SecurityUtils;
import com.benluck.vms.mobifonedataseller.common.utils.PromotionEnum;
import com.benluck.vms.mobifonedataseller.util.ExcelUtil;
import com.benluck.vms.mobifonedataseller.util.RequestUtil;
import com.benluck.vms.mobifonedataseller.util.WebCommonUtil;
import com.benluck.vms.mobifonedataseller.webapp.command.command2015.BaoCaoThongKeThueBaoThamGiaGoiCuocCommand;
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
 * Date: 2/26/15
 * Time: 2:19 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class BaoCaoThongKeThueBaoThamGiaGoiCuoc_TheoDailyController extends ApplicationObjectSupport {
    private Log log = LogFactory.getLog(BaoCaoThongKeThueBaoThamGiaGoiCuoc_TheoDailyController.class);
    private static final Integer TOTAL_COLUMN_EXPORT = 23;
    private static final Integer number_of_templte_export_rows = 12;         // number of rows in export template.

    @Autowired
    private PromPackageManagementLocalBean promPackageService;
    @Autowired
    private BranchManagementLocalBean branchManagementLocalBean;
    @Autowired
    private DistrictManagementLocalBean districtManagementLocalBean;
    @Autowired
    private RetailDealerManagementLocalBean retailDealerManagementLocalBean;
    @Autowired
    private BaoCaoThongKeThueBaoThamGiaGoiCuocManagementLocalBean baoCaoThongKeThueBaoThamGiaGoiCuocService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor("dd/MM/yyyy"));
    }

    @RequestMapping(value = {"/admin/thuebaophattrienmoi/baocaothongkethuebao_thamgiagoicuoc_theodaily.html",
            "/chinhanh/thuebaophattrienmoi/baocaothongkethuebao_thamgiagoicuoc_theodaily.html",
            "/baocao/thuebaophattrienmoi/baocaothongkethuebao_thamgiagoicuoc_theodaily.html"})
    public ModelAndView report(@ModelAttribute(value = Constants.FORM_MODEL_KEY)BaoCaoThongKeThueBaoThamGiaGoiCuocCommand command,
                                                            HttpServletRequest request,
                                                            HttpServletResponse response) throws ObjectNotFoundException {
        ModelAndView mav = new ModelAndView("2015/thuebaophattrienmoi/admin/baocaothongkethuebaothamgiagoicuoc/theodaily");
        String crudaction = command.getCrudaction();
        if (StringUtils.isNotBlank(crudaction)){
            if (crudaction.equals("search")){
                RequestUtil.initSearchBean4report(request, command);
                convertDate2Timestamp(command);
                Map<String, Object> properties = buildProperties(command);
                Object[] results = this.baoCaoThongKeThueBaoThamGiaGoiCuocService.baoCaoThongKeThueBaoThamGiaGoiCuoc_TheoDiemBanHang(properties, command.getSortExpression(), command.getSortDirection(), command.getFirstItem(), command.getReportMaxPageItems());
                command.setListResult((List<BaoCaoThongKeThueBaoThamGiaGoiCuocDTO>)results[1]);
                command.setTotalItems(Integer.valueOf(results[0].toString()));
                mav.addObject(Constants.LIST_MODEL_KEY, command);
                mav.addObject("page", command.getPage() - 1);
                if(command.getTotalItems() == 0){
                    mav.addObject(Constants.ALERT_TYPE, "info");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("admin.baocaotheohangmucphatsinh.no_record_found"));
                }
            } else if(crudaction.equals("export")){
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
    private void referenceData(BaoCaoThongKeThueBaoThamGiaGoiCuocCommand command, ModelAndView mav) throws ObjectNotFoundException {
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
    private Map<String, Object> buildProperties(BaoCaoThongKeThueBaoThamGiaGoiCuocCommand command){
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
        if(command.getPojo().getFromTransDate() != null){
            properties.put("fromTransDate", command.getPojo().getFromTransDate());
        }
        if(command.getPojo().getToTransDate() != null){
            properties.put("toTransDate", command.getPojo().getToTransDate());
        }
        if(command.getPojo().getTinhTrang() != null && !command.getPojo().getTinhTrang().equals("-1")){
                properties.put("conditionStatus", command.getPojo().getTinhTrang());
        }
        if (command.getPojo().getTinhTrangChiTra() != null && !command.getPojo().getTinhTrangChiTra().equals("-1")){
                properties.put("promConditionStatus", command.getPojo().getTinhTrangChiTra());
        }
        if(StringUtils.isNotBlank(command.getPojo().getSoEZ())){
            properties.put("soEZ", command.getPojo().getSoEZ().trim());
        }
        return properties;
    }
    private void convertDate2Timestamp(BaoCaoThongKeThueBaoThamGiaGoiCuocCommand command){
        if(command.getFromDate() != null){
            command.getPojo().setFromDate(DateUtil.dateToTimestamp(command.getFromDate(), Constants.VI_DATE_FORMAT));
        }
        if(command.getToDate() != null){
            command.getPojo().setToDate(DateUtil.dateToTimestamp(command.getToDate(), Constants.VI_DATE_FORMAT));
        }
        if(command.getFromTransDate() != null){
            command.getPojo().setFromTransDate(DateUtil.dateToTimestamp(command.getFromTransDate(), Constants.VI_DATE_FORMAT));
        }
        if(command.getToTransDate() != null){
            command.getPojo().setToTransDate(DateUtil.dateToTimestamp(command.getToTransDate(), Constants.VI_DATE_FORMAT));
        }
    }
    private void export2Excel(BaoCaoThongKeThueBaoThamGiaGoiCuocCommand command, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try{
            SimpleDateFormat df = new SimpleDateFormat("dd-M-yyyy");
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            String ngayXuatBaoCao = df.format(currentTimestamp);
            Map<String, Object> properties = buildProperties(command);
            Object[] resultObject = this.baoCaoThongKeThueBaoThamGiaGoiCuocService.baoCaoThongKeThueBaoThamGiaGoiCuoc_TheoDiemBanHang(properties, command.getSortExpression(), command.getSortDirection(), 0, Integer.MAX_VALUE);
            List<BaoCaoThongKeThueBaoThamGiaGoiCuocDTO> dtoList = (List<BaoCaoThongKeThueBaoThamGiaGoiCuocDTO>)resultObject[1];

            String outputFileName = "/files/temp/baocaothongkethuebao_thamgiachuongtrinh_theodaily" + ngayXuatBaoCao + ".xls";
            String reportTemplate = request.getSession().getServletContext().getRealPath("/files/temp/export/thuebaoptm_2015/baocaothongkethuebao_thamgiachuongtrinh_theodaily.xls");
            String export2FileName = request.getSession().getServletContext().getRealPath(outputFileName);
            WorkbookSettings ws = new WorkbookSettings();
            ExcelUtil.setEncoding4Workbook(ws);
            Workbook templateWorkbook = Workbook.getWorkbook(new File(reportTemplate), ws);
            WritableWorkbook workbook = Workbook.createWorkbook(new File(export2FileName), templateWorkbook);
            WritableSheet sheet = workbook.getSheet(0);
            int startRow = 12;

            WritableFont normalFont = new WritableFont(WritableFont.TIMES, 10, WritableFont.NO_BOLD);
            normalFont.setColour(Colour.BLACK);

            WritableCellFormat stringCellFormat = new WritableCellFormat(normalFont);
            stringCellFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);

            WritableCellFormat stringNgayBaoCaoCellFormat = new WritableCellFormat(normalFont);
            stringNgayBaoCaoCellFormat.setBorder(Border.ALL, BorderLineStyle.NONE);
            stringNgayBaoCaoCellFormat.setAlignment(Alignment.CENTRE);

            WritableCellFormat stringFillterCellFormat = new WritableCellFormat(normalFont);
            stringFillterCellFormat.setBorder(Border.ALL, BorderLineStyle.NONE);
            stringFillterCellFormat.setAlignment(Alignment.LEFT);

            WritableCellFormat integerCellFormat = new WritableCellFormat(normalFont);
            integerCellFormat.setBorder(jxl.format.Border.ALL, BorderLineStyle.THIN);
            integerCellFormat.setAlignment(Alignment.CENTRE);

            NumberFormat nf = new NumberFormat("#,###");
            WritableCellFormat doubleCellFormat = new WritableCellFormat(nf);
            doubleCellFormat.setFont(normalFont);
            doubleCellFormat.setBorder(jxl.format.Border.ALL, BorderLineStyle.THIN);
            doubleCellFormat.setAlignment(Alignment.RIGHT);

            if (command.getFromDate() != null || command.getToDate() != null || command.getFromTransDate() != null || command.getToTransDate() != null){
                CellValue[] resValueNgayXuatBaoCaoTu = WebCommonUtil.addNgayXuatBaoCao(TOTAL_COLUMN_EXPORT);
                resValueNgayXuatBaoCaoTu[3] = new CellValue(CellDataType.STRING, "Ngày tổng hợp: ");
                resValueNgayXuatBaoCaoTu[6] = new CellValue(CellDataType.STRING, "Ngày giao dịch: ");

                if(command.getFromDate() != null || command.getToDate() != null){
                    String tuNgay = "";
                    String denNgay = "";
                    if (command.getFromDate() != null){
                        tuNgay = "Từ ngày: " + df.format(command.getFromDate());
                    }
                    if ( command.getToDate() != null){
                        denNgay = " Đến ngày: " + df.format(command.getToDate());
                    }
                    resValueNgayXuatBaoCaoTu[4] = new CellValue(CellDataType.STRING, tuNgay +" "+ denNgay);
                }

                if(command.getFromTransDate() != null || command.getToTransDate() != null){
                    String tuNgay = "";
                    String denNgay = "";
                    if (command.getFromTransDate() != null){
                        tuNgay = "Từ ngày: " + df.format(command.getFromTransDate());
                    }
                    if ( command.getToTransDate() != null){
                        denNgay = " Đến ngày: " + df.format(command.getToTransDate());
                    }
                    resValueNgayXuatBaoCaoTu[7] = new CellValue(CellDataType.STRING, tuNgay +" "+ denNgay);
                }
                ExcelUtil.addRow(sheet, 4, resValueNgayXuatBaoCaoTu, stringFillterCellFormat, null, null, null);
            }

            if (command.getPojo().getPromPackage() != null && command.getPojo().getPromPackage().getPackage_Id() != null || StringUtils.isNotBlank(command.getPojo().getSoEZ())){
                CellValue[] resValueNgayXuatBaoCaoTu = WebCommonUtil.addNgayXuatBaoCao(TOTAL_COLUMN_EXPORT);
                resValueNgayXuatBaoCaoTu[6] = new CellValue(CellDataType.STRING, "Số Ez: ");
                resValueNgayXuatBaoCaoTu[3] = new CellValue(CellDataType.STRING, " Gói Cước: ");


                if (StringUtils.isNotBlank(command.getPojo().getSoEZ())){
                    resValueNgayXuatBaoCaoTu[7] = new CellValue(CellDataType.STRING, command.getPojo().getSoEZ());
                }

                if (command.getPojo().getPromPackage() != null && command.getPojo().getPromPackage().getPackage_Id() != null){
                    resValueNgayXuatBaoCaoTu[4] = new CellValue(CellDataType.STRING, dtoList.get(0).getPromPackage().getPackage_Name());
                }else {
                    resValueNgayXuatBaoCaoTu[4] = new CellValue(CellDataType.STRING, this.getMessageSourceAccessor().getMessage("label.all"));
                }
                ExcelUtil.addRow(sheet, 5, resValueNgayXuatBaoCaoTu, stringFillterCellFormat, null, null, null);
            } else {
                CellValue[] resValueNgayXuatBaoCaoTu = WebCommonUtil.addNgayXuatBaoCao(TOTAL_COLUMN_EXPORT);
                resValueNgayXuatBaoCaoTu[6] = new CellValue(CellDataType.STRING, "Số Ez: ");
                resValueNgayXuatBaoCaoTu[3] = new CellValue(CellDataType.STRING, "Gói Cước: ");
                resValueNgayXuatBaoCaoTu[4] = new CellValue(CellDataType.STRING, this.getMessageSourceAccessor().getMessage("label.all"));
                ExcelUtil.addRow(sheet, 5, resValueNgayXuatBaoCaoTu, stringFillterCellFormat, null, null, null);
            }


            if (command.getPojo().getBranch() != null && command.getPojo().getBranch().getBranch_Id() != null
                    || StringUtils.isNotBlank(command.getPojo().getTinhTrangChiTra()) && !command.getPojo().getTinhTrangChiTra().equals("-1")){
                CellValue[] resValueNgayXuatBaoCaoTu = WebCommonUtil.addNgayXuatBaoCao(TOTAL_COLUMN_EXPORT);
                resValueNgayXuatBaoCaoTu[6] = new CellValue(CellDataType.STRING, "Tình trạng giao dịch: ");
                resValueNgayXuatBaoCaoTu[3] = new CellValue(CellDataType.STRING, "Chi nhánh: ");

                if (StringUtils.isNotBlank(command.getPojo().getTinhTrangChiTra()) && !command.getPojo().getTinhTrangChiTra().equals("-1")){
                    String tinhTrangChiTra = "";
                    if(command.getPojo().getTinhTrangChiTra().equals(Constants.TINH_TRANG_CHUA_XET)){
                        tinhTrangChiTra = this.getMessageSourceAccessor().getMessage("label.chua_xet");
                    }
                    if(command.getPojo().getTinhTrangChiTra().equals(Constants.TINH_TRANG_DUOC_CHI_TRA)){
                        tinhTrangChiTra = this.getMessageSourceAccessor().getMessage("label.duoc_chi_tra");
                    }
                    if(command.getPojo().getTinhTrangChiTra().equals(Constants.TINH_TRANG_KHONG_DUOC_CHI_TRA)){
                        tinhTrangChiTra = this.getMessageSourceAccessor().getMessage("label.khong_duoc_chi_tra");
                    }
                    resValueNgayXuatBaoCaoTu[7] = new CellValue(CellDataType.STRING, tinhTrangChiTra);
                }else if (command.getPojo().getTinhTrangChiTra().equals("-1")){
                    resValueNgayXuatBaoCaoTu[7] = new CellValue(CellDataType.STRING, this.getMessageSourceAccessor().getMessage("label.all"));
                }
                if (command.getPojo().getBranch() != null && command.getPojo().getBranch().getBranch_Id() != null){
                    resValueNgayXuatBaoCaoTu[4] = new CellValue(CellDataType.STRING, dtoList.get(0).getBranch().getBranch_name());
                } else {
                    resValueNgayXuatBaoCaoTu[4] = new CellValue(CellDataType.STRING, this.getMessageSourceAccessor().getMessage("label.all"));

                }
                ExcelUtil.addRow(sheet, 6, resValueNgayXuatBaoCaoTu, stringFillterCellFormat, null, null, null);
            }else {
                CellValue[] resValueNgayXuatBaoCaoTu = WebCommonUtil.addNgayXuatBaoCao(TOTAL_COLUMN_EXPORT);
                resValueNgayXuatBaoCaoTu[6] = new CellValue(CellDataType.STRING, "Tình trạng giao dịch: ");
                resValueNgayXuatBaoCaoTu[3] = new CellValue(CellDataType.STRING, "Chi nhánh: ");
                resValueNgayXuatBaoCaoTu[7] = new CellValue(CellDataType.STRING, this.getMessageSourceAccessor().getMessage("label.all"));
                resValueNgayXuatBaoCaoTu[4] = new CellValue(CellDataType.STRING, this.getMessageSourceAccessor().getMessage("label.all"));
                ExcelUtil.addRow(sheet, 6, resValueNgayXuatBaoCaoTu, stringFillterCellFormat, null, null, null);
            }

            if (command.getPojo().getDistrict() != null && command.getPojo().getDistrict().getDistrict_Id() != null
                    || StringUtils.isNotBlank(command.getPojo().getTinhTrang()) && !command.getPojo().getTinhTrang().equals("-1")){
                CellValue[] resValueNgayXuatBaoCaoTu = WebCommonUtil.addNgayXuatBaoCao(TOTAL_COLUMN_EXPORT);
                resValueNgayXuatBaoCaoTu[6] = new CellValue(CellDataType.STRING, "Tình trạng chi trả: ");
                resValueNgayXuatBaoCaoTu[3] = new CellValue(CellDataType.STRING, "Quận huyện: ");

                if (StringUtils.isNotBlank(command.getPojo().getTinhTrang()) && !command.getPojo().getTinhTrang().equals("-1")){
                    String tinhTrangGiaoDich = "";
                    if(command.getPojo().getTinhTrang().equals(Constants.TINH_TRANG_THANH_CONG)){
                        tinhTrangGiaoDich = this.getMessageSourceAccessor().getMessage("label.thanh_cong");
                    }
                    if(command.getPojo().getTinhTrang().equals(Constants.TINH_TRANG_KHONG_THANH_CONG)){
                        tinhTrangGiaoDich = this.getMessageSourceAccessor().getMessage("label.khong_thanh_cong");
                    }
                    resValueNgayXuatBaoCaoTu[7] = new CellValue(CellDataType.STRING, tinhTrangGiaoDich);
                }else if (command.getPojo().getTinhTrang().equals("-1")){
                    resValueNgayXuatBaoCaoTu[7] = new CellValue(CellDataType.STRING, this.getMessageSourceAccessor().getMessage("label.all"));
                }
                if (command.getPojo().getDistrict() != null && command.getPojo().getDistrict().getDistrict_Id() != null){
                    resValueNgayXuatBaoCaoTu[4] = new CellValue(CellDataType.STRING, dtoList.get(0).getDistrict().getDistrict_name());
                }else {
                    resValueNgayXuatBaoCaoTu[4] = new CellValue(CellDataType.STRING, this.getMessageSourceAccessor().getMessage("label.all"));
                }
                ExcelUtil.addRow(sheet, 7, resValueNgayXuatBaoCaoTu, stringFillterCellFormat, null, null, null);
            }else {
                CellValue[] resValueNgayXuatBaoCaoTu = WebCommonUtil.addNgayXuatBaoCao(TOTAL_COLUMN_EXPORT);
                resValueNgayXuatBaoCaoTu[6] = new CellValue(CellDataType.STRING, "Tình trạng chi trả: ");
                resValueNgayXuatBaoCaoTu[3] = new CellValue(CellDataType.STRING, "Quận huyện: ");
                resValueNgayXuatBaoCaoTu[7] = new CellValue(CellDataType.STRING, this.getMessageSourceAccessor().getMessage("label.all"));
                resValueNgayXuatBaoCaoTu[4] = new CellValue(CellDataType.STRING,  this.getMessageSourceAccessor().getMessage("label.all"));
                ExcelUtil.addRow(sheet, 7, resValueNgayXuatBaoCaoTu, stringFillterCellFormat, null, null, null);
            }

            if (command.getPojo().getRetailDealer() != null && command.getPojo().getRetailDealer().getDealer_Id() != null){
                CellValue[] resValueNgayXuatBaoCaoTu = WebCommonUtil.addNgayXuatBaoCao(TOTAL_COLUMN_EXPORT);
                resValueNgayXuatBaoCaoTu[3] = new CellValue(CellDataType.STRING, "Điểm bán hàng: ");

                if (command.getPojo().getRetailDealer() != null && command.getPojo().getRetailDealer().getDealer_Id() != null){
                    resValueNgayXuatBaoCaoTu[4] = new CellValue(CellDataType.STRING, dtoList.get(0).getRetailDealer().getDealer_name() + "-" + dtoList.get(0).getRetailDealer().getDealer_code());
                }else {
                    resValueNgayXuatBaoCaoTu[4] = new CellValue(CellDataType.STRING, this.getMessageSourceAccessor().getMessage("label.all"));
                }
                ExcelUtil.addRow(sheet, 8, resValueNgayXuatBaoCaoTu, stringFillterCellFormat, null, null, null);
            }else {
                CellValue[] resValueNgayXuatBaoCaoTu = WebCommonUtil.addNgayXuatBaoCao(TOTAL_COLUMN_EXPORT);
                resValueNgayXuatBaoCaoTu[3] = new CellValue(CellDataType.STRING, "Điểm bán hàng: ");
                resValueNgayXuatBaoCaoTu[4] = new CellValue(CellDataType.STRING,  this.getMessageSourceAccessor().getMessage("label.all"));
                ExcelUtil.addRow(sheet, 8, resValueNgayXuatBaoCaoTu, stringFillterCellFormat, null, null, null);
            }

            Integer maxRemainingRowsInSheet = Constants.MAX_EXPORT_RECORDS - number_of_templte_export_rows;

            if(dtoList.size() > 0){
                int indexRow = 1;
                for(BaoCaoThongKeThueBaoThamGiaGoiCuocDTO dto : dtoList){
                    if(indexRow > 1 && (indexRow % maxRemainingRowsInSheet) == 0){
                        int sheetNum = indexRow / maxRemainingRowsInSheet;
                        if(sheetNum > 2){
                            sheet = workbook.createSheet("Sheet " + (sheetNum + 1), sheetNum);
                        }else{
                            sheet = workbook.getSheet(sheetNum);
                        }
                        startRow = 0;
                    }
                    CellValue[] values = addCellValues(dto, indexRow, command);
                    ExcelUtil.addRow(sheet, startRow++, values, stringCellFormat, integerCellFormat, doubleCellFormat, null);
                    indexRow++;
                /*for(BaoCaoThongKeThueBaoThamGiaGoiCuocDTO dto : dtoList){
                    CellValue[] values = addCellValues(dto, indexRow, command);
                    ExcelUtil.addRow(sheet, startRow++, values, stringCellFormat, integerCellFormat, doubleCellFormat, null);
                    indexRow++;  */
                }
                workbook.write();
                workbook.close();
                response.sendRedirect(request.getSession().getServletContext().getContextPath() + outputFileName);
            }
        }catch (Exception e){
            throw new Exception("Error when export data to excel.\n + details: " + e.getMessage());
        }
    }

    private CellValue[] addCellValues(BaoCaoThongKeThueBaoThamGiaGoiCuocDTO dto, int indexRow, BaoCaoThongKeThueBaoThamGiaGoiCuocCommand command) {
        CellValue[] resValue = new CellValue[TOTAL_COLUMN_EXPORT];
        SimpleDateFormat df = new SimpleDateFormat("dd/M/yyyy hh:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
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
        if(StringUtils.isNotBlank(dto.getSoThueBao())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getSoThueBao());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getRegistrationTrans() != null && dto.getRegistrationTrans().getActive_datetime() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, sdf.format(dto.getRegistrationTrans().getActive_datetime()));
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getPromPackage() != null && StringUtils.isNotBlank(dto.getPromPackage().getPackage_Name())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getPromPackage().getPackage_Name());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getThoiGianDK() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, df.format(dto.getThoiGianDK()));
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getNgayTongHop() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, sdf.format(dto.getNgayTongHop()));
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getRegistrationTrans() != null && StringUtils.isNotBlank(dto.getRegistrationTrans().getReg_Position())){
            if (dto.getRegistrationTrans().getReg_Position().equals("1")) {
                resValue[columnIndex++] = new CellValue(CellDataType.STRING, this.getMessageSourceAccessor().getMessage("label.hop_le"));
            }else{
                resValue[columnIndex++] = new CellValue(CellDataType.STRING, this.getMessageSourceAccessor().getMessage("label.khong_hop_le"));
            }
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        StringBuilder tinh_trang = null;
        if(StringUtils.isNotBlank(command.getPojo().getTinhTrang()) && !command.getPojo().getTinhTrang().equals("-1")){
            if(command.getPojo().getTinhTrang().equals(Constants.TINH_TRANG_THANH_CONG)){
                tinh_trang = new StringBuilder(this.getMessageSourceAccessor().getMessage("label.thanh_cong"));
            }else if(command.getPojo().getTinhTrang().equals(Constants.TINH_TRANG_KHONG_THANH_CONG)){
                tinh_trang = new StringBuilder(this.getMessageSourceAccessor().getMessage("label.khong_thanh_cong"));
            }else{
                tinh_trang = new StringBuilder(this.getMessageSourceAccessor().getMessage("label.duoc_chi_tra"));
            }
        }else{
            if(StringUtils.isNotBlank(dto.getTinhTrang())){
                if(!dto.getTinhTrang().equals(Constants.TINH_TRANG_THANH_CONG)){
                    tinh_trang = new StringBuilder(this.getMessageSourceAccessor().getMessage("label.khong_thanh_cong"));
                }else{
                    if (StringUtils.isNotBlank(dto.getPromConditionStatus())){
                        if (dto.getPromConditionStatus().equals(Constants.PROM_CONDITION_STATUS_0) || dto.getPromConditionStatus().equals(Constants.PROM_CONDITION_STATUS_2)){
                            tinh_trang = new StringBuilder(this.getMessageSourceAccessor().getMessage("label.thanh_cong_nhung_khong_duoc_chi_tra"));
                        }else if (StringUtils.isNotBlank(dto.getPromConditionStatus()) && dto.getPromConditionStatus().equals(Constants.PROM_CONDITION_STATUS_1)){
                            tinh_trang = new StringBuilder(this.getMessageSourceAccessor().getMessage("label.duoc_chi_tra"));
                        }else{
                            tinh_trang = new StringBuilder(StringUtils.isNotBlank(dto.getPromConditionError()) ? dto.getPromConditionError() : "");
                        }
                    }
                }
            }
        }

        if (tinh_trang != null){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, tinh_trang.toString());
        }else {
            resValue[columnIndex++] = new CellValue();
        }

        StringBuilder ly_do = null;
        if(StringUtils.isNotBlank(dto.getTinhTrang())){
            if(StringUtils.isNotBlank(dto.getTinhTrang()) && dto.getTinhTrang().equals(Constants.TINH_TRANG_KHONG_THANH_CONG)){
                if (StringUtils.isNotBlank(dto.getTransError())){
                    ly_do = new StringBuilder(dto.getTransError());
                }
            }else{
                if (StringUtils.isNotBlank(dto.getPromConditionError())){
                    ly_do = new StringBuilder(dto.getPromConditionError());
                }
            }
        }
        if (ly_do != null){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, ly_do.toString());
        }else {
            resValue[columnIndex++] = new CellValue();
        }
        if(StringUtils.isNotBlank(dto.getSerial())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getSerial());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getRegistrationTrans() != null && StringUtils.isNotBlank(dto.getRegistrationTrans().getSales_shop_code())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getRegistrationTrans().getSales_shop_code());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getRegistrationTrans() != null && dto.getRegistrationTrans().getCalling_amount() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getRegistrationTrans().getCalling_amount());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getRegistrationTrans() != null && dto.getRegistrationTrans().getSms_amount() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getRegistrationTrans().getSms_amount());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getRegistrationTrans() != null && dto.getRegistrationTrans().getData_amount() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getRegistrationTrans().getData_amount());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getRegistrationTrans() != null && dto.getRegistrationTrans().getOthers_amount() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getRegistrationTrans().getOthers_amount());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getRegistrationTrans() != null && StringUtils.isNotBlank(dto.getRegistrationTrans().getEvent_code())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getRegistrationTrans().getEvent_code());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getRegistrationTrans() != null && StringUtils.isNotBlank(dto.getRegistrationTrans().getEvent_pos_code())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getRegistrationTrans().getEvent_pos_code());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getRegistrationTrans() != null && StringUtils.isNotBlank(dto.getRegistrationTrans().getEvent_pos_name())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getRegistrationTrans().getEvent_pos_name());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        return resValue;
    }
}
