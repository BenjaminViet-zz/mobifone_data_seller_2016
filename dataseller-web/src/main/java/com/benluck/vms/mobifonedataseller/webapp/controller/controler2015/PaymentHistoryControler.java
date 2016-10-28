package com.benluck.vms.mobifonedataseller.webapp.controller.controler2015;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.utils.DateUtil;
import com.benluck.vms.mobifonedataseller.core.business.*;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.ActionLogDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BranchDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.DistrictDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.RetailDealerDTO;
import com.benluck.vms.mobifonedataseller.editor.CustomDateEditor;
import com.benluck.vms.mobifonedataseller.security.util.SecurityUtils;
import com.benluck.vms.mobifonedataseller.common.utils.PromotionEnum;
import com.benluck.vms.mobifonedataseller.util.ExcelUtil;
import com.benluck.vms.mobifonedataseller.util.RequestUtil;
import com.benluck.vms.mobifonedataseller.webapp.command.command2015.BaoCaoEzNhanTinThamGiaChuongTrinhCommand;
import com.benluck.vms.mobifonedataseller.webapp.command.command2015.LichSuChiTraCommand;
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
 * Date: 3/18/15
 * Time: 5:42 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class PaymentHistoryControler extends ApplicationObjectSupport {
    private Log log = LogFactory.getLog(PaymentHistoryControler.class);

    private static final Integer TOTAL_COLUMN_EXPORT = 6;
    @Autowired
    private RetailDealerManagementLocalBean retailDealerManagementLocalBean;
    @Autowired
    private BranchManagementLocalBean branchManagementLocalBean;
    @Autowired
    private DistrictManagementLocalBean districtManagementLocalBean;
    @Autowired
    private ActionLogManagementLocalBean actionLogService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor("dd/MM/yyyy"));
    }

    @RequestMapping(value = {"/admin/thuebaophattrienmoi/lichsuchitra.html",
            "/chinhanh/thuebaophattrienmoi/lichsuchitra.html"})
    public ModelAndView report(@ModelAttribute(value = Constants.FORM_MODEL_KEY)LichSuChiTraCommand command,
                               HttpServletRequest request,
                               HttpServletResponse response)throws ObjectNotFoundException {
        ModelAndView mav = new ModelAndView("2015/thuebaophattrienmoi/admin/lichsuchitra");
        String crudaction = command.getCrudaction();
        if (StringUtils.isNotBlank(crudaction)){
            if (crudaction.equals("search")){
                RequestUtil.initSearchBean4report(request, command);
                Map<String, Object> properties = buildProperties(command);
                Object[] results = this.actionLogService.search(properties, command.getSortExpression(), command.getSortDirection(), command.getFirstItem(), command.getMaxPageItems());
                command.setListResult((List<ActionLogDTO>)results[1]);
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

    private Map<String, Object> buildProperties(LichSuChiTraCommand command){
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

    private void referenceData(LichSuChiTraCommand command, ModelAndView mav) throws ObjectNotFoundException {
        if(SecurityUtils.userHasAuthority(Constants.ADMIN_ROLE)){
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

    private void export2Excel(LichSuChiTraCommand command, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try{
            SimpleDateFormat df = new SimpleDateFormat("dd-M-yyyy");
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            String ngayXuatBaoCao = df.format(currentTimestamp);
            Map<String, Object> properties = buildProperties(command);
            Object[] resultObject = this.actionLogService.search(properties, command.getSortExpression(), command.getSortDirection(), 0, Integer.MAX_VALUE);
            List<ActionLogDTO> dtoList = (List<ActionLogDTO>)resultObject[1];

            String outputFileName = "/files/temp/lichSuChiTraDBH_" + ngayXuatBaoCao + ".xls";
            String reportTemplate = request.getSession().getServletContext().getRealPath("/files/temp/export/thuebaoptm_2015/lichSuChiTraDBH.xls");
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

            if(dtoList.size() > 0){
                int indexRow = 1;
                for(ActionLogDTO dto : dtoList){
                    CellValue[] values = addCellValues(dto, indexRow);
                    ExcelUtil.addRow(sheet, startRow++, values, stringCellFormat, integerCellFormat, doubleCellFormat, null);
                    indexRow++;
                }
                workbook.write();
                workbook.close();
                response.sendRedirect(request.getSession().getServletContext().getContextPath() + outputFileName);
            }
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new Exception("Error when export data to excel.\n + details: " + e.getMessage());
        }
    }

    private CellValue[] addCellValues(ActionLogDTO dto, int indexRow) {
        CellValue[] resValue = new CellValue[TOTAL_COLUMN_EXPORT];
        SimpleDateFormat df = new SimpleDateFormat("dd/M/yyyy");
        int columnIndex = 0;
        resValue[columnIndex++] = new CellValue(CellDataType.INT, indexRow);
        if(dto.getUser() != null && StringUtils.isNotBlank(dto.getUser().getDisplayName())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getUser().getDisplayName());
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
        if(dto.getRegistrationTrans() != null && dto.getRegistrationTrans().getProm_Amount() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.DOUBLE, dto.getRegistrationTrans().getProm_Amount());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getCreatedTime() != null){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, df.format(dto.getCreatedTime()));
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        return resValue;
    }
}
