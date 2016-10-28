package com.benluck.vms.mobifonedataseller.webapp.controller.controler2015;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.utils.CacheUtil;
import com.benluck.vms.mobifonedataseller.common.utils.CommonUtil;
import com.benluck.vms.mobifonedataseller.core.business.*;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.BranchDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.DistrictDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.RetailDealerDTO;
import com.benluck.vms.mobifonedataseller.common.utils.PromotionEnum;
import com.benluck.vms.mobifonedataseller.util.ExcelUtil;
import com.benluck.vms.mobifonedataseller.util.FileUtils;
import com.benluck.vms.mobifonedataseller.webapp.command.command2015.DanhMucBanHangCommand;
import com.benluck.vms.mobifonedataseller.webapp.validator.validator2015.ImportDangKyDSThoaThuanValidator;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: HP
 * Date: 3/16/15
 * Time: 10:25 AM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class ImportDangKyDSThoaThuanController extends ApplicationObjectSupport {
    private Log log = LogFactory.getLog(ImportDangKyDSThoaThuanController.class);

    @Autowired
    private RetailDealerManagementLocalBean retailDealerManagementLocalBean;
    @Autowired
    private ImportDangKyDSThoaThuanValidator importValidator;

    private final String CACHE_KEY = "EZ_CACHE";

    @RequestMapping(value = {"/admin/thuebaophattrienmoi/importDSDangKyThoaThuanDBH.html",
            "/chinhanh/thuebaophattrienmoi/importDSDangKyThoaThuanDBH.html"})
    public ModelAndView importUser(DanhMucBanHangCommand command,
                                   HttpServletRequest request,
                                   BindingResult bindingResult){
        ModelAndView mav = new ModelAndView("/2015/thuebaophattrienmoi/chinhanh/importDSDangKyThoaThuan");
        String crudaction = command.getCrudaction();
        if (RequestMethod.POST.toString().equals(request.getMethod())){
            if(StringUtils.isNotBlank(crudaction) && crudaction.equals("import-save")){
                List<RetailDealerDTO> listUsers = (List<RetailDealerDTO>) CacheUtil.getInstance().getValue(CACHE_KEY);
                try{
                    this.retailDealerManagementLocalBean.saveImport(listUsers);
                    CacheUtil.getInstance().remove(CACHE_KEY);
                    mav.addObject(Constants.ALERT_TYPE, "success");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("import_thoa_thuan.msg.add_successfully"));
                }catch (Exception e){
                    log.error(e.getMessage(),e);
                    mav.addObject(Constants.ALERT_TYPE, "danger");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("import_thoa_thuan.msg.add_exception"));
                }
            }else{
                List<RetailDealerDTO> listDto = file2DTO(request ,mav);
                command.setListResult(listDto);
                importValidator.validate(command, bindingResult);
                if(StringUtils.isNotBlank(command.getError()) || bindingResult.hasErrors()){
                    mav.addObject("hasError",true);
                    mav.addObject(Constants.ALERT_TYPE, "danger");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("import_thoa_thuan.error"));
                }
                if(listDto != null){
                    mav.addObject(Constants.LIST_MODEL_KEY, command);
                    CacheUtil.getInstance().putValue(CACHE_KEY, listDto);
                }
            }
        }
        return mav;
    }

    private List<RetailDealerDTO> file2DTO(HttpServletRequest request, ModelAndView mav) {
        List<RetailDealerDTO> listResult = new ArrayList<RetailDealerDTO>();
        MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> map = mRequest.getFileMap();
        MultipartFile csvFile = (MultipartFile) map.get("csvfile");
        try{
            String destFolder = CommonUtil.getBaseFolder() + CommonUtil.getTempFolderName();
            String fileName = FileUtils.upload(request, destFolder, csvFile);
            String destFileName = request.getSession().getServletContext().getRealPath(destFolder + "/" + fileName);

            WorkbookSettings ws;
            Workbook workbook ;
            Sheet s ;
            Cell rowData[] ;
            FileInputStream fs = new FileInputStream(new File(destFileName));
            ws = new WorkbookSettings();
            ExcelUtil.setEncoding4Workbook(ws);
            workbook = Workbook.getWorkbook(fs,ws);
            s = workbook.getSheet(0);
            int rowCount = s.getRows();
            boolean checkEmpty = false;
            if (s.getColumns() != 4){
                mav.addObject(Constants.ALERT_TYPE, "info");
                mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("import_thoa_thuan.khong_dung_temple"));
                return null;
            }
            for (int i = 1; i < rowCount; i++) {
                rowData = s.getRow(i);
                String ez_isdn = rowData[0].getContents().trim();
                String retail_Dealer = rowData[1].getContents().trim();
                String district = rowData[2].getContents().trim();
                String branch = rowData[3].getContents().trim();

                RetailDealerDTO haveDocImport = new RetailDealerDTO();
                haveDocImport.setEz_isdn(ez_isdn);
                haveDocImport.setDealer_code(retail_Dealer);
                if (StringUtils.isNotBlank(district)){
                    DistrictDTO districtDTO = new DistrictDTO();
                    districtDTO.setDistrict_code(district);
                    haveDocImport.setDistrict(districtDTO);
                }
                if (StringUtils.isNotBlank(branch)){
                    BranchDTO branchDTO = new BranchDTO();
                    branchDTO.setBranch_code(branch);
                    haveDocImport.setBranch(branchDTO);
                }

                if (StringUtils.isBlank(retail_Dealer) || StringUtils.isBlank(ez_isdn)){
                    checkEmpty = true;
                    haveDocImport.setError("Dữ liệu import không được trống!");
                    haveDocImport.setCheckEmpty(true);
                }else if (StringUtils.isNotBlank(haveDocImport.getEz_isdn())){
                    try {
                        RetailDealerDTO retailDealerList= this.retailDealerManagementLocalBean.findByEzAndCtCode_tbptm(haveDocImport, PromotionEnum.THUE_BAO_PTM_2015.getCode());
                        haveDocImport.setDealer_Id(retailDealerList.getDealer_Id());
                    }catch (Exception e){}
                }
                listResult.add(haveDocImport);
            }
            if (checkEmpty){
                mav.addObject("hasError",true);
                mav.addObject(Constants.ALERT_TYPE, "danger");
                mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("import_thoa_thuan.error.empty"));
            }
        }catch (Exception exception){
            log.error(exception.getMessage());
        }
        return listResult;
    }
}
