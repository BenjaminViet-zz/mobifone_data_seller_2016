
package com.benluck.vms.mobifonedataseller.webapp.controller;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.security.DesEncrypterUtils;
import com.benluck.vms.mobifonedataseller.common.utils.CacheUtil;
import com.benluck.vms.mobifonedataseller.common.utils.CommonUtil;
import com.benluck.vms.mobifonedataseller.core.business.ChiNhanhManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.DepartmentManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.UserGroupManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.UserManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.ChiNhanhDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.DepartmentDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.UserDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.UserGroupDTO;
import com.benluck.vms.mobifonedataseller.editor.PojoEditor;
import com.benluck.vms.mobifonedataseller.security.util.SecurityUtils;
import com.benluck.vms.mobifonedataseller.util.ExcelUtil;
import com.benluck.vms.mobifonedataseller.util.FileUtils;
import com.benluck.vms.mobifonedataseller.util.RequestUtil;
import com.benluck.vms.mobifonedataseller.webapp.command.command2014.UserCommand;
import com.benluck.vms.mobifonedataseller.webapp.validator.UserValidator;
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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.ejb.DuplicateKeyException;
import javax.ejb.ObjectNotFoundException;
import javax.ejb.RemoveException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController extends ApplicationObjectSupport {

    @Autowired
    private UserManagementLocalBean usersManagementLocalBean;
    @Autowired
    private UserGroupManagementLocalBean userGroupManagementLocalBean;
    @Autowired
    private DepartmentManagementLocalBean departmentManagementLocalBean;
    @Autowired
    private ChiNhanhManagementLocalBean chiNhanhManagementLocalBean;

    @Autowired
    private UserValidator validator;

    private String CACHE_KEY = "USERS_CACHE";
    private Log log = LogFactory.getLog(UserController.class);

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(UserDTO.class, new PojoEditor(UserDTO.class, "userId", Long.class));
        binder.registerCustomEditor(UserGroupDTO.class, new PojoEditor(UserGroupDTO.class, "userGroupId", Long.class));

    }

    @RequestMapping(value = "/admin/nhapdanhmucnguoidung.html")
    public ModelAndView importUser(UserCommand command, HttpServletRequest request, RedirectAttributes redirectAttributes){
        ModelAndView modelAndView = new ModelAndView("/user/import");
        String crudaction = command.getCrudaction();
        if (RequestMethod.POST.toString().equals(request.getMethod())){
            if(StringUtils.isNotBlank(crudaction) && crudaction.equals("import-save")){
                List<UserDTO> listUsers = (List<UserDTO>)CacheUtil.getInstance().getValue(CACHE_KEY);
                Object[] result = null;
                try{
                    result = this.usersManagementLocalBean.saveImport(listUsers);
                    CacheUtil.getInstance().remove(CACHE_KEY);
                }catch (Exception e){
                    log.error(e.getMessage());
                }
                modelAndView = new ModelAndView("redirect:/admin/userList.html");
                redirectAttributes.addFlashAttribute("messageResponse", this.getMessageSourceAccessor().getMessage("label.importuser.success", new Object[]{Integer.valueOf(result[0].toString()), Integer.valueOf(result[1].toString()), Integer.valueOf(result[2].toString())}));
                return modelAndView;
            }else{
                List<UserDTO> listDto = file2DTO(request);
                if(listDto != null){
                    modelAndView.addObject("listResult", listDto);
                    CacheUtil.getInstance().putValue(CACHE_KEY, listDto);
                }
            }
        }
        return modelAndView;
    }

    /**
     * Read the uploaded file from the request and convert data to DTOs for storing.
     * @param request
     * @return
     */
    private List<UserDTO> file2DTO(HttpServletRequest request) {
        List<UserDTO> listResult = new ArrayList<UserDTO>();
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
            int columnCount = s.getColumns();
            Map<String, DepartmentDTO> mapDepartment = new HashMap<String, DepartmentDTO>();
            for (int i = 1; i < rowCount; i++) {
                rowData = s.getRow(i);
                String shop_code = rowData[0].getContents().trim();
                String user_name = rowData[1].getContents().trim();
                String full_name = rowData[2].getContents().trim();
                String password = user_name;
                try{
                    password = rowData[3].getContents().trim();
                }catch (Exception ex){

                }
                if(shop_code != null && shop_code != ""){
                    if(mapDepartment.get(shop_code) == null){
                        try{
                            DepartmentDTO department = this.departmentManagementLocalBean.findByCode(shop_code);
                            mapDepartment.put(shop_code, department);
                        }catch (Exception ex){
                            log.error(ex.getMessage());
                        }
                    }
                }
                UserDTO userImport = new UserDTO(user_name, full_name, password);
                userImport.setDepartment(new DepartmentDTO());
                userImport.getDepartment().setCode(shop_code);
                if(mapDepartment.get(shop_code) != null){
                    userImport.setDepartment(mapDepartment.get(shop_code));
                }
                listResult.add(userImport);
            }
        }catch (Exception exception){
            log.error(exception.getMessage());
            return null;
        }
        return listResult;

    }

    @RequestMapping("/admin/userList.html")
	public ModelAndView list(@ModelAttribute(value = Constants.FORM_MODEL_KEY)UserCommand command, HttpServletRequest request) throws RemoveException {
        ModelAndView mav = new ModelAndView("/user/list");
        String crudaction = command.getCrudaction();
        if (StringUtils.isNotBlank(crudaction)){
            if(crudaction.equals("delete")){
                if(command.getPojo().getUserId() != null){
                    boolean hasRelatedToData = this.usersManagementLocalBean.checkIfHasRelatedToData(command.getPojo().getUserId());
                    if(!hasRelatedToData){
                        try{
                            this.usersManagementLocalBean.deleteById(command.getPojo().getUserId());
                            mav.addObject(Constants.ALERT_TYPE, "success");
                            mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("admin.user.delete_successfully"));
                        }catch (Exception e){
                            mav.addObject(Constants.ALERT_TYPE, "info");
                            mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("admin.user.can_not_delete_user"));
                        }
                    }else{
                        mav.addObject(Constants.ALERT_TYPE, "info");
                        mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("admin.user.msg.no_user_id"));
                    }
                }
            }else if (crudaction.equals("find")){
                executeSearch(command, request);
            }
        }
        executeSearch(command, request);

        List<ChiNhanhDTO> chiNhanhList = this.chiNhanhManagementLocalBean.findAll();
        mav.addObject("chiNhanhList", chiNhanhList);

        if(command.getPojo().getChiNhanhId() != null){
            List<DepartmentDTO> departmentList = this.departmentManagementLocalBean.findDepartmentListByChiNhanhId(command.getPojo().getChiNhanhId());
            mav.addObject("departmentList", departmentList);
        }
        List<UserGroupDTO> userGroupList = this.userGroupManagementLocalBean.findAll4Access();
        mav.addObject("userGroupList", userGroupList);
        mav.addObject("page", command.getPage() - 1);
        mav.addObject(Constants.LIST_MODEL_KEY, command);
        return mav;
	}

    @RequestMapping(value = {"/admin/editUserInfo.html"})
    public ModelAndView edit(@ModelAttribute(Constants.FORM_MODEL_KEY)UserCommand command,
                             BindingResult bindingResult) throws DuplicateKeyException {
        ModelAndView mav = new ModelAndView("user/edit");
        String crudaction = command.getCrudaction();
        UserDTO pojo = command.getPojo();
        if (StringUtils.isNotBlank(crudaction)){
            if(crudaction.equals("insert-update")){
                try{
                    validator.validate(command, bindingResult);
                    if (!bindingResult.hasErrors()){
                        if (pojo.getUserId() == null ){
                            pojo = this.usersManagementLocalBean.addItem(command.getPojo());
                            mav.addObject(Constants.ALERT_TYPE, "success");
                            mav.addObject("messageResponse", this.getMessageSourceAccessor().getMessage("database.add.successful"));
                        } else {
                            pojo = this.usersManagementLocalBean.updateItem(command.getPojo());
                            mav.addObject(Constants.ALERT_TYPE, "success");
                            mav.addObject("messageResponse", this.getMessageSourceAccessor().getMessage("database.update.successful"));
                        }
                        command.setPojo(pojo);
                    }else{
                        pojo.setPassword(DesEncrypterUtils.getInstance().decrypt(pojo.getPassword()));
                    }

                } catch (Exception e){
                    log.error(e.getMessage(), e);
                    mav.addObject(Constants.ALERT_TYPE, "danger");
                    mav.addObject("messageResponse", this.getMessageSourceAccessor().getMessage("database.exception.keynotfound"));
                }
            }

        }
        if (pojo.getUserId()!= null){
            try{
                pojo = this.usersManagementLocalBean.findById(command.getPojo().getUserId());
                pojo.setPassword(DesEncrypterUtils.getInstance().decrypt(pojo.getPassword()));
                command.setPojo(pojo);
            } catch (Exception e){
                log.error(e.getMessage(), e);
            }
        }
        List<UserGroupDTO> userGroups = this.userGroupManagementLocalBean.findAll4Access();
        mav.addObject("userGroups", userGroups);
        List<DepartmentDTO> listDepartments = this.departmentManagementLocalBean.findAll();
        mav.addObject("listDepartments", listDepartments);
        List<ChiNhanhDTO> chiNhanhList = this.chiNhanhManagementLocalBean.findAll();
        mav.addObject("chiNhanhList", chiNhanhList);
        mav.addObject(Constants.FORM_MODEL_KEY, command);
        return mav;
    }

    /**
     * Perform searching related data for this view and put them to the model.
     * @param bean
     * @param request
     */
    private void executeSearch(UserCommand bean, HttpServletRequest request){
        RequestUtil.initSearchBean(request, bean);
        Map<String, Object> properties = new HashMap<String, Object>();
        UserDTO pojo = bean.getPojo();
        if (StringUtils.isNotBlank(pojo.getUserName())){
            properties.put("userName", pojo.getUserName());
        }
        if (StringUtils.isNotBlank(pojo.getDisplayName())){
            properties.put("displayName", pojo.getDisplayName());
        }
        if (pojo.getChiNhanhId() != null){
            properties.put("chiNhanhId", pojo.getChiNhanhId());
        }
        if (pojo.getUserGroup() != null && pojo.getUserGroup().getUserGroupId() != null){
            properties.put("userGroupId", pojo.getUserGroup().getUserGroupId());
        }
        if (pojo.getDepartment() != null && pojo.getDepartment().getDepartmentId() != null){
            properties.put("departmentId", pojo.getDepartment().getDepartmentId());
        }
        Object [] result = this.usersManagementLocalBean.search(properties, bean.getSortExpression(), bean.getSortDirection(), bean.getFirstItem(), bean.getMaxPageItems());
        bean.setTotalItems(Integer.valueOf(result[0].toString()));
        bean.setListResult((List<UserDTO>)result[1]);
        bean.setMaxPageItems(bean.getMaxPageItems());
    }

    @RequestMapping(value ={"/admin/thongtincanhan.html","/cuahangmobifone/thongtincanhan.html","/chinhanh/thongtincanhan.html","/tongdai/thongtincanhan.html"})
    public ModelAndView editProfile(@ModelAttribute(Constants.FORM_MODEL_KEY)UserCommand command, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) throws ObjectNotFoundException {
        ModelAndView mav = new ModelAndView("/user/editprofile");
        String crudaction = command.getCrudaction();
        UserDTO pojo = command.getPojo();
        Long userId = SecurityUtils.getLoginUserId();
        if (StringUtils.isNotBlank(crudaction) && crudaction.equals("insert-update")){
            try{
                validator.validate(command, bindingResult);
                if (!bindingResult.hasErrors()){
                    this.usersManagementLocalBean.updateProfile(command.getPojo());
                    mav.addObject(Constants.ALERT_TYPE, "success");
                    mav.addObject("messageResponse", this.getMessageSourceAccessor().getMessage("database.update.successful"));
                }
            } catch (Exception e) {
                mav.addObject(Constants.ALERT_TYPE, "danger");
                mav.addObject("messageResponse", this.getMessageSourceAccessor().getMessage("database.update.exception"));
            }
        }
        if (userId != null && userId > 0){
            try{
                pojo = this.usersManagementLocalBean.findById(userId);
                pojo.setPassword(DesEncrypterUtils.getInstance().decrypt(pojo.getPassword()));
                command.setPojo(pojo);
            }catch (Exception e){
                log.error(e.getMessage(), e);
            }
        }
        command.setPojo(pojo);
        mav.addObject(Constants.FORM_MODEL_KEY, command);

        return mav;
    }
}

