package com.benluck.vms.mobifonedataseller.webapp.controller.controler2015;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.utils.DateUtil;
import com.benluck.vms.mobifonedataseller.core.business.*;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BranchDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.DistrictDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.RetailDealerDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015.BaoCaoEzNhanTinThamGiaChuongTrinhDTO;
import com.benluck.vms.mobifonedataseller.editor.CustomDateEditor;
import com.benluck.vms.mobifonedataseller.security.util.SecurityUtils;
import com.benluck.vms.mobifonedataseller.common.utils.PromotionEnum;
import com.benluck.vms.mobifonedataseller.util.ExcelUtil;
import com.benluck.vms.mobifonedataseller.util.RequestUtil;
import com.benluck.vms.mobifonedataseller.util.WebCommonUtil;
import com.benluck.vms.mobifonedataseller.webapp.command.command2015.BaoCaoEzNhanTinThamGiaChuongTrinhCommand;
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
 * Date: 3/17/15
 * Time: 5:52 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class BaoCaoEzTinNhanThamGiaChuongTrinhController extends ApplicationObjectSupport {
    private transient final Log log = LogFactory.getLog(getClass());

    private static final Integer TOTAL_COLUMN_EXPORT = 9;
    @Autowired
    private RetailDealerManagementLocalBean retailDealerManagementLocalBean;
    @Autowired
    private BranchManagementLocalBean branchManagementLocalBean;
    @Autowired
    private DistrictManagementLocalBean districtManagementLocalBean;
    @Autowired
    private BaoCaoEzNhanTinThamGiaChuongTrinhManagementLocalBean baoCaoEzNhanTinThamGiaChuongTrinhService;


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor("dd/MM/yyyy"));
    }

    @RequestMapping(value = {"/admin/thuebaophattrienmoi/baocaoeznhantinthamgiachuongtrinh.html",
            "/chinhanh/thuebaophattrienmoi/baocaoeznhantinthamgiachuongtrinh.html",
            "/baocao/thuebaophattrienmoi/baocaoeznhantinthamgiachuongtrinh.html"})
    public ModelAndView report(@ModelAttribute(value = Constants.FORM_MODEL_KEY)BaoCaoEzNhanTinThamGiaChuongTrinhCommand command,
                               HttpServletRequest request,
                               HttpServletResponse response)throws ObjectNotFoundException {
        ModelAndView mav = new ModelAndView("2015/thuebaophattrienmoi/admin/baocaoeznhantinthamgiachuongtrinh");
        String crudaction = command.getCrudaction();
        if (StringUtils.isNotBlank(crudaction)){
            if (crudaction.equals("search")){
                RequestUtil.initSearchBean4report(request, command);
                convertDate2Timestamp(command);
                Map<String, Object> properties = buildProperties(command);
                Object[] results = this.baoCaoEzNhanTinThamGiaChuongTrinhService.baoCaoEzNhanTinThamGiaChuongTrinh(properties, command.getSortExpression(), command.getSortDirection(), command.getFirstItem(), command.getReportMaxPageItems());
                command.setListResult((List<BaoCaoEzNhanTinThamGiaChuongTrinhDTO>)results[1]);
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

    private Map<String, Object> buildProperties(BaoCaoEzNhanTinThamGiaChuongTrinhCommand command){
        Map<String, Object> properties = new HashMap<String, Object>();
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
        if(command.getPojo().getFromNgayKichHoat() != null){
            properties.put("fromNgayKichHoat", command.getPojo().getFromNgayKichHoat());
        }
        if(command.getPojo().getToNgayKichHoat() != null){
            properties.put("toNgayKichHoat", command.getPojo().getFromNgayKichHoat());
        }
        if (StringUtils.isNotBlank(command.getPojo().getSoEz())){
            properties.put("ez", command.getPojo().getSoEz());
        }
        return properties;
    }

    private void convertDate2Timestamp(BaoCaoEzNhanTinThamGiaChuongTrinhCommand command){
        if(command.getFromDate() != null){
            command.getPojo().setFromDate(DateUtil.dateToTimestamp(command.getFromDate(), Constants.VI_DATE_FORMAT));
        }
        if(command.getToDate() != null){
            command.getPojo().setToDate(DateUtil.dateToTimestamp(command.getToDate(), Constants.VI_DATE_FORMAT));
        }
        if(command.getFromNgayKichHoat() != null){
            command.getPojo().setFromNgayKichHoat(DateUtil.dateToTimestamp(command.getFromNgayKichHoat(), Constants.VI_DATE_FORMAT));
        }
        if(command.getToNgayKichHoat() != null){
            command.getPojo().setToNgayKichHoat(DateUtil.dateToTimestamp(command.getToNgayKichHoat(), Constants.VI_DATE_FORMAT));
        }

    }

    private void referenceData(BaoCaoEzNhanTinThamGiaChuongTrinhCommand command, ModelAndView mav) throws ObjectNotFoundException {
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

    private void export2Excel(BaoCaoEzNhanTinThamGiaChuongTrinhCommand command, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try{
            SimpleDateFormat df = new SimpleDateFormat("dd-M-yyyy");
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            String ngayXuatBaoCao = df.format(currentTimestamp);
            Map<String, Object> properties = buildProperties(command);
            Object[] resultObject = this.baoCaoEzNhanTinThamGiaChuongTrinhService.baoCaoEzNhanTinThamGiaChuongTrinh(properties, command.getSortExpression(), command.getSortDirection(), 0, Integer.MAX_VALUE);
            List<BaoCaoEzNhanTinThamGiaChuongTrinhDTO> dtoList = (List<BaoCaoEzNhanTinThamGiaChuongTrinhDTO>)resultObject[1];

            String outputFileName = "/files/temp/baocaoeznhantinthamgiachuongtrinh" + ngayXuatBaoCao + ".xls";
            String reportTemplate = request.getSession().getServletContext().getRealPath("/files/temp/export/thuebaoptm_2015/baocaoeznhantinthamgiachuongtrinh.xls");
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

            if(command.getFromDate() != null || command.getFromNgayKichHoat() != null){
                CellValue[] resValueNgayXuatBaoCao = WebCommonUtil.addNgayXuatBaoCao(TOTAL_COLUMN_EXPORT);
                if(command.getFromDate() != null){
                    resValueNgayXuatBaoCao[7] = new CellValue(CellDataType.STRING, "Ngày nhắn tin từ: " + df.format(command.getFromDate()));
                }
                if(command.getFromNgayKichHoat() != null){
                    resValueNgayXuatBaoCao[1] = new CellValue(CellDataType.STRING, "Ngày kích hoạt từ: " + df.format(command.getFromNgayKichHoat()));
                }
                ExcelUtil.addRow(sheet, 4, resValueNgayXuatBaoCao, stringNgayBaoCaoCellFormat, null, null, null);
            }
            if(command.getToDate() != null || command.getToNgayKichHoat() != null){
                CellValue[] resValueNgayXuatBaoCao = WebCommonUtil.addNgayXuatBaoCao(TOTAL_COLUMN_EXPORT);
                if(command.getFromDate() != null){
                    resValueNgayXuatBaoCao[7] = new CellValue(CellDataType.STRING, "Ngày nhắn tin đến: " + df.format(command.getToDate()));
                }
                if(command.getFromNgayKichHoat() != null){
                    resValueNgayXuatBaoCao[1] = new CellValue(CellDataType.STRING, "Ngày kích hoạt đến: " + df.format(command.getToNgayKichHoat()));
                }
                ExcelUtil.addRow(sheet, 5, resValueNgayXuatBaoCao, stringNgayBaoCaoCellFormat, null, null, null);
            }

            if(dtoList.size() > 0){
                int indexRow = 1;
                for(BaoCaoEzNhanTinThamGiaChuongTrinhDTO dto : dtoList){
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

    private CellValue[] addCellValues(BaoCaoEzNhanTinThamGiaChuongTrinhDTO dto, int indexRow) {
        CellValue[] resValue = new CellValue[TOTAL_COLUMN_EXPORT];
        SimpleDateFormat df = new SimpleDateFormat("dd/M/yyyy hh:mm:ss");
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
        if(StringUtils.isNotBlank(dto.getSoEz())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getSoEz());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getNgayNhanTin() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, df.format(dto.getNgayNhanTin()));
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getNgayKichHoat() !=  null){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, df.format(dto.getNgayKichHoat()));
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(StringUtils.isNotBlank(dto.getGhiChu())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getGhiChu());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        return resValue;
    }
}
