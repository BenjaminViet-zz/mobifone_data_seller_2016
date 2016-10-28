package com.benluck.vms.mobifonedataseller.webapp.controller;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.utils.CacheUtil;
import com.benluck.vms.mobifonedataseller.common.utils.CommonUtil;
import com.benluck.vms.mobifonedataseller.core.business.ChiNhanhManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.DepartmentManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.ChiNhanhDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.DepartmentDTO;
import com.benluck.vms.mobifonedataseller.editor.CustomDateEditor;
import com.benluck.vms.mobifonedataseller.util.ExcelUtil;
import com.benluck.vms.mobifonedataseller.util.FileUtils;
import com.benluck.vms.mobifonedataseller.util.RequestUtil;
import com.benluck.vms.mobifonedataseller.webapp.command.command2014.DepartmentCommand;
import com.benluck.vms.mobifonedataseller.webapp.validator.DepartmentValidator;
import jxl.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.ejb.ObjectNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class DepartmentController extends ApplicationObjectSupport {
    private transient final Log log = LogFactory.getLog(getClass());

    @Autowired
    DepartmentManagementLocalBean departmentManagementLocalBean;
    @Autowired
    private ChiNhanhManagementLocalBean chiNhanhManagementLocalBean;
    @Autowired
    DepartmentValidator departmentValidator;

    public static String CACHE_DEPARTMENT_SHOP = "CACHE_DEPARTMENT_SHOP";

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor());
    }

    @RequestMapping(value={"/admin/danhmuccuahang.html"})
    public ModelAndView list(DepartmentCommand bean, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/department/list");
        if(StringUtils.isNotBlank(bean.getCrudaction()) && bean.getCrudaction().equals(Constants.ACTION_DELETE)) {
            Integer totalDeleted = 0;
            try {
                totalDeleted = departmentManagementLocalBean.deleteItems(bean.getCheckList());
                mav.addObject("totalDeleted", totalDeleted);
            }catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        executeSearch(bean, request);
        mav.addObject(Constants.LIST_MODEL_KEY, bean);
        mav.addObject("page", bean.getPage() - 1);

        List<ChiNhanhDTO> chiNhanhList = this.chiNhanhManagementLocalBean.findAll();
        mav.addObject("chiNhanhList", chiNhanhList);
        return mav;
    }

    @RequestMapping("/admin/department/view.html")
    public ModelAndView view(@ModelAttribute(Constants.FORM_MODEL_KEY) DepartmentCommand departmentCommand, BindingResult bindingResult, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/department/view");
        DepartmentDTO pojo = departmentCommand.getPojo();
        if(!bindingResult.hasErrors()&& departmentCommand.getPojo().getDepartmentId() != null && departmentCommand.getPojo().getDepartmentId() > 0) {
            try {
                DepartmentDTO itemObj = this.departmentManagementLocalBean.findById(pojo.getDepartmentId());
                departmentCommand.setPojo(itemObj);
            }
            catch (Exception e) {
                logger.error("Could not found news " + departmentCommand.getPojo().getDepartmentId(), e);
            }
        }
        return mav;
    }

    @RequestMapping("/admin/thongtincuahang.html")
    public ModelAndView edit(@ModelAttribute(Constants.FORM_MODEL_KEY) DepartmentCommand departmentCommand, BindingResult bindingResult, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/department/edit");
        String crudaction = departmentCommand.getCrudaction();
        DepartmentDTO pojo = departmentCommand.getPojo();
        if(StringUtils.isNotBlank(crudaction) && crudaction.equals("insert-update")) {
            try {
                //validate
                departmentValidator.validate(departmentCommand, bindingResult);

                if(!bindingResult.hasErrors()) {
                    if(pojo.getDepartmentId() != null && pojo.getDepartmentId() > 0) {
                        pojo.setDepartmentType(Constants.DEPARTMENT_TYPE_SHOP);
                        this.departmentManagementLocalBean.updateItem(departmentCommand.getPojo());
                        mav.addObject(Constants.ALERT_TYPE, "success");
                        mav.addObject("messageResponse", this.getMessageSourceAccessor().getMessage("database.update.successful"));
                    } else {
                        pojo.setDepartmentType(Constants.DEPARTMENT_TYPE_SHOP);
                        pojo = this.departmentManagementLocalBean.addItem(departmentCommand.getPojo());
                        mav.addObject(Constants.ALERT_TYPE, "success");
                        mav.addObject("messageResponse", this.getMessageSourceAccessor().getMessage("database.add.successful"));
                    }
                }
            }catch (ObjectNotFoundException oe) {
                logger.error(oe.getMessage());
                mav.addObject(Constants.ALERT_TYPE, "danger");
                mav.addObject("messageResponse", this.getMessageSourceAccessor().getMessage("database.exception.keynotfound"));
            }catch(Exception e) {
                logger.error(e.getMessage());
                mav.addObject(Constants.ALERT_TYPE, "danger");
                mav.addObject("messageResponse", this.getMessageSourceAccessor().getMessage("general.exception.msg"));
            }
        }
        if(!bindingResult.hasErrors()&& departmentCommand.getPojo().getDepartmentId() != null && departmentCommand.getPojo().getDepartmentId() > 0) {
            if (departmentCommand.getPojo().getShopCode() != null && departmentCommand.getPojo().getShopCode().getShopCodeChiNhanhId() != null
                    && departmentCommand.getPojo().getShopCode().getShopCodeChiNhanhId() > 0){
                try {
                    DepartmentDTO itemObj = this.departmentManagementLocalBean.findByIdAndShopCodeId(pojo.getShopCode().getShopCodeChiNhanhId(),pojo.getDepartmentId());
                    departmentCommand.setPojo(itemObj);
                }
                catch (Exception e) {
                    logger.error("Could not found news " + departmentCommand.getPojo().getDepartmentId(), e);
                }
            }
        }
        mav.addObject(Constants.FORM_MODEL_KEY, departmentCommand);
        referenceData(departmentCommand,mav);
        return mav;
    }

    /**
     * Perform searching data for report.
     * @param bean
     * @param request
     */
    private void executeSearch(DepartmentCommand bean, HttpServletRequest request) {
        RequestUtil.initSearchBean(request, bean);

        Object[] results = this.departmentManagementLocalBean.search(bean.getPojo(), bean.getSortExpression(), bean.getSortDirection(), bean.getFirstItem(), bean.getMaxPageItems());
        bean.setListResult((List<DepartmentDTO>)results[1]);
        bean.setTotalItems(Integer.valueOf(results[0].toString()));
    }

    @RequestMapping(value = "/admin/department/import.html")
    public ModelAndView importShop(HttpServletRequest request) throws ObjectNotFoundException {
        ModelAndView mav = new ModelAndView("/department/import");
        String crudaction = request.getParameter("crudaction");
        if (RequestMethod.POST.toString().equals(request.getMethod())){
            if(StringUtils.isNotBlank(crudaction) && "import-save".equals(crudaction))
            {
                List<DepartmentDTO> listDto = (List<DepartmentDTO>)CacheUtil.getInstance().getValue(RequestUtil.getClusterSessionId(request) + CACHE_DEPARTMENT_SHOP);
                this.departmentManagementLocalBean.importAndUpdate(listDto);
                CacheUtil.getInstance().remove(RequestUtil.getClusterSessionId(request) + CACHE_DEPARTMENT_SHOP);
            }
            else
            {
                List<DepartmentDTO> listDto = file2DTO(request, mav) ;
                CacheUtil.getInstance().putValue(RequestUtil.getClusterSessionId(request) + CACHE_DEPARTMENT_SHOP, listDto);
                mav.addObject("listResult", listDto);
            }
        }
        return mav;
    }

    /**
     * Fetch related data for this view.
     * @param command
     * @param mav
     */
    private void referenceData(DepartmentCommand command, ModelAndView mav){
        List<ChiNhanhDTO> chiNhanhList = this.chiNhanhManagementLocalBean.findAll();
        mav.addObject("chiNhanhList", chiNhanhList);
    }

    /**
     * Read the uploaded file and store to DB.
     * @param request
     * @param mav
     * @return
     */
    private List<DepartmentDTO> file2DTO(HttpServletRequest request, ModelAndView mav) {
        List<DepartmentDTO> results = new ArrayList<DepartmentDTO>();
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request ;
        Map<String, MultipartFile> map = multipartHttpServletRequest.getFileMap();
        MultipartFile csvFile = (MultipartFile) map.get("csvfile");
        int k = 0;
        try{
            String destFolder = CommonUtil.getBaseFolder() + CommonUtil.getTempFolderName();
            String fileName = FileUtils.upload(request, destFolder, csvFile);
            String destFileName = request.getSession().getServletContext().getRealPath(destFolder + "/" + fileName);

            WorkbookSettings ws = null;
            Workbook workbook = null;
            Sheet s = null;
            Cell rowData[] = new Cell[0];
            DateCell dc = null;
            int totalSheet = 0;
            FileInputStream fs = new FileInputStream(new File(destFileName));
            ws = new WorkbookSettings();
            ExcelUtil.setEncoding4Workbook(ws);
            workbook = Workbook.getWorkbook(fs,ws);
            s = workbook.getSheet(0);
            int rowCount = s.getRows();
            int columnCount = s.getColumns();

            for (int i = 1; i < rowCount; i++) {
                k ++;
                rowData = s.getRow(i);
                DepartmentDTO departmentDTO = new DepartmentDTO();
                departmentDTO.setCode(rowData[0].getContents());
                departmentDTO.setName(rowData[1].getContents());
                departmentDTO.setAddress(rowData[2].getContents());
                departmentDTO.setTel(rowData[3].getContents());
                departmentDTO.setContactName(rowData[4].getContents());
                departmentDTO.setDepartmentType(Constants.DEPARTMENT_TYPE_SHOP);

                results.add(departmentDTO);
            }
        }
        catch (Exception e)
        {
            return results;
        }
        return results;
    }
}
