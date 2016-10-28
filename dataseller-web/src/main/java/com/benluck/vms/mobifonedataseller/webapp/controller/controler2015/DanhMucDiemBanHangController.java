package com.benluck.vms.mobifonedataseller.webapp.controller.controler2015;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.core.business.*;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BranchDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.DistrictDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.RetailDealerDTO;
import com.benluck.vms.mobifonedataseller.editor.CustomDateEditor;
import com.benluck.vms.mobifonedataseller.security.util.SecurityUtils;
import com.benluck.vms.mobifonedataseller.common.utils.PromotionEnum;
import com.benluck.vms.mobifonedataseller.util.ExcelUtil;
import com.benluck.vms.mobifonedataseller.util.RequestUtil;
import com.benluck.vms.mobifonedataseller.webapp.command.command2015.DanhMucBanHangCommand;
import com.benluck.vms.mobifonedataseller.webapp.dto.CellDataType;
import com.benluck.vms.mobifonedataseller.webapp.dto.CellValue;
import com.benluck.vms.mobifonedataseller.webapp.validator.DanhMucDiemBanHangValidator;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.ejb.ObjectNotFoundException;
import javax.ejb.RemoveException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: vovanphuc0810
 * Date: 2/12/15
 * Time: 9:41 AM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class DanhMucDiemBanHangController extends ApplicationObjectSupport{
    private transient final Log log = LogFactory.getLog(getClass());
    private static final Integer TOTAL_COLUMN_EXPORT = 9;

    @Autowired
    private RetailDealerManagementLocalBean retailDealerManagementLocalBean;
    @Autowired
    private BranchManagementLocalBean branchManagementLocalBean;
    @Autowired
    private DistrictManagementLocalBean districtManagementLocalBean;
    @Autowired
    private DanhMucDiemBanHangValidator danhMucDiemBanHangValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor());
    }

    @RequestMapping(value = {"/admin/thuebaophattrienmoi/danhmucdiembanhang.html","/chinhanh/thuebaophattrienmoi/danhmucdiembanhang.html"})
    public ModelAndView traCuuThongTinThueBao(@ModelAttribute(value = Constants.FORM_MODEL_KEY)DanhMucBanHangCommand bean,
                                              HttpServletRequest request,
                                              HttpServletResponse response,
                                              BindingResult bindingResult)throws RemoveException {
        ModelAndView mav = new ModelAndView("/2015/thuebaophattrienmoi/admin/danhmucdiembanhang/list");

        String crudaction = bean.getCrudaction();
        danhMucDiemBanHangValidator.validate(bean, bindingResult);
        if(!bindingResult.hasErrors()){
            if (StringUtils.isNotBlank(crudaction)){
                if(crudaction.equals("export")){
                    try{
                        export2Excel(bean, request, response);
                    }catch (Exception e){
                        mav.addObject(Constants.ALERT_TYPE, "danger");
                        mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("export_data_exception"));
                    }
                }
            }
            referenceData(bean,mav);
            executeSearch(bean, request);
            mav.addObject(Constants.LIST_MODEL_KEY, bean);
            mav.addObject("page", bean.getPage() - 1);
        }
        return mav;
    }

    private void referenceData(DanhMucBanHangCommand command, ModelAndView mav) {
        List<BranchDTO> branchList = new ArrayList<BranchDTO>();
        List<DistrictDTO> districtList = new ArrayList<DistrictDTO>();
        List<RetailDealerDTO> retailDealerList = new ArrayList<RetailDealerDTO>();
        if(SecurityUtils.userHasAuthority(Constants.ADMIN_ROLE)){
            branchList = this.branchManagementLocalBean.findAll_tbptm();
            if (command.getPojo().getBranch() != null && command.getPojo().getBranch().getBranch_Id() != null) {
                districtList = this.districtManagementLocalBean.findByBranchId_tbptm(command.getPojo().getBranch().getBranch_Id());
            } else {
                districtList = this.districtManagementLocalBean.findAll_tbptm();
            }

            if (command.getPojo().getDistrict() != null && command.getPojo().getDistrict().getDistrict_Id() != null) {
                retailDealerList = this.retailDealerManagementLocalBean.findByDistrictId_tbptm(command.getPojo().getDistrict().getDistrict_Id());
            } else if (command.getPojo().getBranch() != null && command.getPojo().getBranch().getBranch_Id() != null) {
                retailDealerList = this.retailDealerManagementLocalBean.findByBranchId_tbptm(command.getPojo().getBranch().getBranch_Id());
            } else {
                retailDealerList = this.retailDealerManagementLocalBean.findAll_tbptm();
            }
        }else if (SecurityUtils.userHasAuthority(Constants.CHINHANH_ROLE)){
            try{
                BranchDTO branchDTO = this.branchManagementLocalBean.findByChiNhanhIdAndCTCode(SecurityUtils.getPrincipal().getMappingDBLinkBranchId(), PromotionEnum.THUE_BAO_PTM_2015.getCode());
                branchList.add(branchDTO);
                command.getPojo().setBranch(branchDTO);

                if(branchDTO != null){
                    districtList = this.districtManagementLocalBean.findByBranchIdAndCTCode(branchDTO.getBranch_Id(), PromotionEnum.THUE_BAO_PTM_2015.getCode());
                }

                if(command.getPojo().getDistrict() != null && command.getPojo().getDistrict().getDistrict_Id() != null){
                    retailDealerList = this.retailDealerManagementLocalBean.findByDistrictIdAndCTCode(command.getPojo().getDistrict().getDistrict_Id(), PromotionEnum.THUE_BAO_PTM_2015.getCode());
                }else{
                    if(branchDTO != null){
                        retailDealerList = this.retailDealerManagementLocalBean.findByBranchIdAndCTCode(branchDTO.getBranch_Id(), PromotionEnum.THUE_BAO_PTM_2015.getCode());
                    }
                }
            }catch (ObjectNotFoundException oe){}
        }

        mav.addObject("branchList", branchList);
        mav.addObject("districtList", districtList);
        mav.addObject("retailDealerList", retailDealerList);
    }



    private void executeSearch(DanhMucBanHangCommand bean, HttpServletRequest request) {
        RequestUtil.initSearchBean(request, bean);
        if (SecurityUtils.userHasAuthority(Constants.CHINHANH_ROLE)){
            if (bean.getPojo().getBranch() != null && bean.getPojo().getBranch().getBranch_Id() != null){
                try{
                    BranchDTO branchDTO = this.branchManagementLocalBean.findByChiNhanhIdAndCTCode(SecurityUtils.getPrincipal().getMappingDBLinkBranchId(), PromotionEnum.THUE_BAO_PTM_2015.getCode());
                    bean.getPojo().setBranch(branchDTO);
                }catch (ObjectNotFoundException oe){}
            }
        }

        Object[] results = this.retailDealerManagementLocalBean.search_tbptm(bean.getPojo(), bean.getSortExpression(), bean.getSortDirection(), bean.getFirstItem(), bean.getMaxPageItems());
        bean.setListResult((List<RetailDealerDTO>)results[0]);
        bean.setTotalItems(Integer.valueOf(results[1].toString()));
    }

    @RequestMapping(value = {"/ajax/thuebaotm/getRetailDealerListByBranchId.html"})
    public @ResponseBody Map ajaxLoadRetailDealerByBranch(@RequestParam(value = "branchId", required = false)Long branchId){
        Map<String, Object> map = new HashMap<String, Object>();
        List<RetailDealerDTO> retailDealerList;
        if(branchId != null){
            retailDealerList = this.retailDealerManagementLocalBean.findByBranchId_tbptm(branchId);
        }else{
            retailDealerList = this.retailDealerManagementLocalBean.findAll_tbptm();
        }
        map.put("retailDealerList", retailDealerList);
        return map;
    }

    @RequestMapping("/ajax/thuebaotm/getRetailDealerList.html")
    public @ResponseBody Map ajaxGetDepartmentListByChiNhanh(@RequestParam(value = "branchId", required = false)Long branchId,
                                                             @RequestParam(value = "districtId", required = false)Long districtId){
        Map<String, Object> map = new HashMap<String, Object>();
        List<RetailDealerDTO> dealerDTOs = this.retailDealerManagementLocalBean.findByDistrictIdAndByBranchId_tbptm(branchId, districtId);
        map.put("retailDealerList", dealerDTOs);
        return map;
    }

    private void export2Excel(DanhMucBanHangCommand command, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try{
            SimpleDateFormat df = new SimpleDateFormat("dd-M-yyyy");
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            String ngayXuatBaoCao = df.format(currentTimestamp);
            Object[] resultObject = this.retailDealerManagementLocalBean.search_tbptm(command.getPojo(), command.getSortExpression(), command.getSortDirection(), 0, Integer.MAX_VALUE);
            List<RetailDealerDTO> dtoList = (List<RetailDealerDTO>)resultObject[0];

            String outputFileName = "/files/temp/danhmucdiembanhang" + ngayXuatBaoCao + ".xls";
            String reportTemplate = request.getSession().getServletContext().getRealPath("/files/temp/export/danhmucdiembanhang.xls");
            String export2FileName = request.getSession().getServletContext().getRealPath(outputFileName);
            WorkbookSettings ws = new WorkbookSettings();
            ExcelUtil.setEncoding4Workbook(ws);
            Workbook templateWorkbook = Workbook.getWorkbook(new File(reportTemplate), ws);
            WritableWorkbook workbook = Workbook.createWorkbook(new File(export2FileName), templateWorkbook);
            WritableSheet sheet = workbook.getSheet(0);
            int startRow = 6;

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
                for(RetailDealerDTO dto : dtoList){
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

    private CellValue[] addCellValues(RetailDealerDTO dto, int indexRow) {
        CellValue[] resValue = new CellValue[TOTAL_COLUMN_EXPORT];
        SimpleDateFormat df = new SimpleDateFormat("dd/M/yyyy");
        int columnIndex = 0;
        resValue[columnIndex++] = new CellValue(CellDataType.INT, indexRow);
        if(StringUtils.isNotBlank(dto.getDealer_name())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getDealer_name());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(StringUtils.isNotBlank(dto.getDealer_code())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getDealer_code());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(StringUtils.isNotBlank(dto.getEz_isdn())){
            if (StringUtils.isNotBlank(dto.getPrimary())){
                if (dto.getPrimary().equals("0")){
                    resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getEz_isdn() + "@");
                }else {
                    resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getEz_isdn());
                }
            }
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(StringUtils.isNotBlank(dto.getBranch_name())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getBranch_name());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(StringUtils.isNotBlank(dto.getDistrict_name())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getDistrict_name());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(StringUtils.isNotBlank(dto.getAddress())){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, dto.getAddress());
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(dto.getCreatedDate() !=  null){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, df.format(dto.getCreatedDate()));
        }else{
            resValue[columnIndex++] = new CellValue();
        }
        if(StringUtils.isNotBlank(dto.getPropertiesCode()) && dto.getPropertiesCode().equals(Constants.DEALER_HAVE_DOC)){
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, this.getMessageSourceAccessor().getMessage("label.co"));
        }else{
            resValue[columnIndex++] = new CellValue(CellDataType.STRING, this.getMessageSourceAccessor().getMessage("label.khong"));
        }
        return resValue;
    }
}
