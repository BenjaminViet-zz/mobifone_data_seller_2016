package com.benluck.vms.mobifonedataseller.webapp.controller.controler2015;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.utils.DateUtil;
import com.benluck.vms.mobifonedataseller.core.business.BaoCaoThongKeThueBaoThamGiaGoiCuocManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.BranchManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.DistrictManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.PromPackageManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BranchDTO;
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
public class BaoCaoThongKeThueBaoThamGiaGoiCuoc_TheoChiNhanhController extends ApplicationObjectSupport {
    private Log log = LogFactory.getLog(BaoCaoTinNhanDangKyGoiDBHController.class);
    private static final Integer TOTAL_COLUMN_EXPORT = 6;

    @Autowired
    private PromPackageManagementLocalBean promPackageService;
    @Autowired
    private BranchManagementLocalBean branchManagementLocalBean;
    @Autowired
    private DistrictManagementLocalBean districtManagementLocalBean;
    @Autowired
    private BaoCaoThongKeThueBaoThamGiaGoiCuocManagementLocalBean baoCaoThongKeThueBaoThamGiaGoiCuocService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor("dd/MM/yyyy"));
    }

    @RequestMapping(value = {"/admin/thuebaophattrienmoi/baocaothongkethuebao_thamgiagoicuoc_theochinhanh.html",
            "/chinhanh/thuebaophattrienmoi/baocaothongkethuebao_thamgiagoicuoc_theochinhanh.html",
            "/baocao/thuebaophattrienmoi/baocaothongkethuebao_thamgiagoicuoc_theochinhanh.html"})
    public ModelAndView report(@ModelAttribute(value = Constants.FORM_MODEL_KEY)BaoCaoThongKeThueBaoThamGiaGoiCuocCommand command,
                               HttpServletRequest request,
                               HttpServletResponse response)throws ObjectNotFoundException{
        ModelAndView mav = new ModelAndView("2015/thuebaophattrienmoi/admin/baocaothongkethuebaothamgiagoicuoc/theochinhanh");
        String crudaction = command.getCrudaction();
        if (StringUtils.isNotBlank(crudaction)){
            if (crudaction.equals("search")){
                RequestUtil.initSearchBean4report(request, command);
                convertDate2Timestamp(command);
                Map<String, Object> properties = buildProperties(command);
                Object[] results = this.baoCaoThongKeThueBaoThamGiaGoiCuocService.baoCaoThongKeThueBaoThamGiaGoiCuoc_TheoChiNhanh(properties, command.getSortExpression(), command.getSortDirection(), command.getFirstItem(), command.getMaxPageItems());
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
        }else if(SecurityUtils.userHasAuthority(Constants.CHINHANH_ROLE)){
            try{
                BranchDTO branchDTO = this.branchManagementLocalBean.findByChiNhanhIdAndCTCode(SecurityUtils.getPrincipal().getMappingDBLinkBranchId(), PromotionEnum.THUE_BAO_PTM_2015.getCode());
                List<BranchDTO> branchList = new ArrayList<BranchDTO>();
                branchList.add(branchDTO);
                mav.addObject("branchList", branchList);
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
        if(command.getPojo().getFromDate() != null){
            properties.put("fromDateTime", command.getPojo().getFromDate());
        }
        if(command.getPojo().getToDate() != null){
            properties.put("toDateTime", command.getPojo().getToDate());
        }
        if(command.getPojo().getTinhTrang() != null && !command.getPojo().getTinhTrang().equals("-1")){
            if (command.getPojo().getTinhTrang().equals(Constants.TINH_TRANG_KHONG_THANH_CONG) ||
                    command.getPojo().getTinhTrang().equals(Constants.TINH_TRANG_THANH_CONG)){
                properties.put("conditionStatus", command.getPojo().getTinhTrang());

            }else if (command.getPojo().getTinhTrang().equals(Constants.TINH_TRANG_DUOC_CHI_TRA)){
                properties.put("promConditionStatus", Constants.THANH_CONG_DUOC_CHI_TRA);
            }
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
    }
    private void export2Excel(BaoCaoThongKeThueBaoThamGiaGoiCuocCommand command, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try{
            SimpleDateFormat df = new SimpleDateFormat("dd-M-yyyy");
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            String ngayXuatBaoCao = df.format(currentTimestamp);
            Map<String, Object> properties = buildProperties(command);
            Object[] resultObject = this.baoCaoThongKeThueBaoThamGiaGoiCuocService.baoCaoThongKeThueBaoThamGiaGoiCuoc_TheoDiemBanHang(properties, command.getSortExpression(), command.getSortDirection(), 0, Integer.MAX_VALUE);
            List<BaoCaoThongKeThueBaoThamGiaGoiCuocDTO> dtoList = (List<BaoCaoThongKeThueBaoThamGiaGoiCuocDTO>)resultObject[1];

            String outputFileName = "/files/temp/baocaothongkethuebao_thamgiachuongtrinh_theochinhanh" + ngayXuatBaoCao + ".xls";
            String reportTemplate = request.getSession().getServletContext().getRealPath("/files/temp/export/thuebaoptm_2015/baocaothongkethuebao_thamgiachuongtrinh_theochinhanh.xls");
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

            if(command.getFromDate() != null){
                CellValue[] resValueNgayXuatBaoCaoTu = WebCommonUtil.addNgayXuatBaoCao(TOTAL_COLUMN_EXPORT);
                resValueNgayXuatBaoCaoTu[4] = new CellValue(CellDataType.STRING, "Ngày xuất báo cáo từ: " + df.format(command.getFromDate()));
                ExcelUtil.addRow(sheet, 4, resValueNgayXuatBaoCaoTu, stringNgayBaoCaoCellFormat, null, null, null);
            }
            if(command.getToDate() != null){
                CellValue[] resValueNgayXuatBaoCaoTu = WebCommonUtil.addNgayXuatBaoCao(TOTAL_COLUMN_EXPORT);
                resValueNgayXuatBaoCaoTu[4] = new CellValue(CellDataType.STRING, "Ngày xuất báo cáo đến: " + df.format(command.getToDate()));
                ExcelUtil.addRow(sheet, 5, resValueNgayXuatBaoCaoTu, stringNgayBaoCaoCellFormat, null, null, null);
            }


            if(dtoList.size() > 0){
                int indexRow = 1;
                for(BaoCaoThongKeThueBaoThamGiaGoiCuocDTO dto : dtoList){
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

    private CellValue[] addCellValues(BaoCaoThongKeThueBaoThamGiaGoiCuocDTO dto, int indexRow) {
        CellValue[] resValue = new CellValue[TOTAL_COLUMN_EXPORT];
        SimpleDateFormat df = new SimpleDateFormat("dd/M/yyyy");
        int columnIndex = 0;
        resValue[columnIndex++] = new CellValue(CellDataType.INT, indexRow);
        if(dto.getBranch() != null && StringUtils.isNotBlank(dto.getBranch().getBranch_name())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getBranch().getBranch_name());
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
        StringBuilder tinh_trang = null;
        if(dto.getTinhTrang() != null){
            if(dto.getTinhTrang().equals(Constants.TINH_TRANG_KHONG_THANH_CONG)){
                tinh_trang = new StringBuilder(this.getMessageSourceAccessor().getMessage("label.khong_thanh_cong"));
            }else if(dto.getTinhTrang().equals(Constants.TINH_TRANG_THANH_CONG)){
                if (dto.getPromConditionStatus()!=null){
                    if (dto.getPromConditionStatus().equals(Constants.THANH_CONG_KHONG_DUOC_CHI_TRA)){
                        tinh_trang = new StringBuilder(this.getMessageSourceAccessor().getMessage("label.thanh_cong_nhung_khong_duoc_chi_tra"));
                    }else if (dto.getPromConditionStatus().equals(Constants.THANH_CONG_DUOC_CHI_TRA)){
                        tinh_trang = new StringBuilder(this.getMessageSourceAccessor().getMessage("label.duoc_chi_tra"));
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
        if(dto.getTinhTrang() != null){
            if(dto.getTinhTrang().equals(Constants.TINH_TRANG_KHONG_THANH_CONG)){
                if (dto.getTransError() != null){
                    ly_do = new StringBuilder(dto.getTransError());
                }
            }else if(dto.getTinhTrang().equals(Constants.TINH_TRANG_THANH_CONG)){
                if (dto.getPromConditionError() != null){
                    ly_do = new StringBuilder(dto.getPromConditionError());
                }
            }
        }
        if (ly_do != null){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, ly_do.toString());
        }else {
            resValue[columnIndex++] = new CellValue();
        }
        return resValue;
    }
}
