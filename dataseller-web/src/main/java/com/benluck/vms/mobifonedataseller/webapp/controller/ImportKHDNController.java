package com.benluck.vms.mobifonedataseller.webapp.controller;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.core.dto.ImportKHDNDTO;
import com.benluck.vms.mobifonedataseller.security.util.SecurityUtils;
import com.benluck.vms.mobifonedataseller.util.RequestUtil;
import com.benluck.vms.mobifonedataseller.webapp.command.ImportKHDNCommand;
import com.benluck.vms.mobifonedataseller.webapp.exception.ForBiddenException;
import com.benluck.vms.mobifonedataseller.webapp.task.TaskImportKHDN;
import com.benluck.vms.mobifonedataseller.webapp.validator.ImportKHDNValidator;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/11/16
 * Time: 21:32
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class ImportKHDNController extends ApplicationObjectSupport{
    private Logger logger = Logger.getLogger(ImportKHDNController.class);

    @Autowired
    private ImportKHDNValidator validator;

    @RequestMapping(value = {"/admin/khdn/import.html", "/user/khdn/import.html"})
    public ModelAndView importFile(@ModelAttribute(Constants.FORM_MODEL_KEY)ImportKHDNCommand command,
                                   HttpServletRequest request,
                                   BindingResult bindingResult){

        if(!SecurityUtils.userHasAuthority(Constants.USERGROUP_ADMIN) && !SecurityUtils.userHasAuthority(Constants.USERGROUP_VMS_USER) && !SecurityUtils.userHasAuthority(Constants.KHDN_MANAGER)){
            logger.warn("User: " + SecurityUtils.getPrincipal().getDisplayName() + ", userId: " + SecurityUtils.getLoginUserId() + " is trying to access non-authorized page: " + "/khdn/import.html user page. ACCESS DENIED FOR BIDDEN!");
            throw new ForBiddenException();
        }

        ModelAndView mav = new ModelAndView("/admin/khdn/import");
        String action = command.getCrudaction();

        if(StringUtils.isNotBlank(action)){
            if(action.equals(Constants.ACTION_UPLOAD)){
                MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
                Map<String, MultipartFile> map = mRequest.getFileMap();
                MultipartFile fileUpload = (MultipartFile) map.get("file");
                command.setFileUpload(fileUpload);

                validator.validate(command, bindingResult);
                if(!command.getHasError().booleanValue()){
                    command.setStepImportIndex(Constants.IMPORT_ORDER_STEP_2_UPLOAD);

                    if(StringUtils.isNotBlank(command.getErrorMessage())){
                        mav.addObject(Constants.ALERT_TYPE, "danger");
                        mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, command.getErrorMessage());
                    }else{
                        request.getSession().setAttribute(Constants.ORDER_IMPORT_FILE_CACHE_KEY + RequestUtil.getClusterSessionId(request), command.getImportKHDNDTOList());

                        mav.addObject(Constants.ALERT_TYPE, "success");
                        mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("import.correct_data"));
                    }
                }else{
                    mav.addObject(Constants.ALERT_TYPE, "danger");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, command.getErrorMessage());
                }
            }
        }

        return mav;
    }

    private void startJobImport(List<ImportKHDNDTO> importDTOList){
        TaskImportKHDN importTask = new TaskImportKHDN(SecurityUtils.getLoginUserId(), importDTOList);
        Timer timer = new Timer(true);
        timer.schedule(importTask, 0);
    }

    @RequestMapping(value = "/ajax/khdn/import.html")
    public @ResponseBody Map processImport(HttpServletRequest request){
        Map<String, Object> mapRes = new HashMap<String, Object>();

        try{
            List<ImportKHDNDTO> importDTOList = (List<ImportKHDNDTO>)request.getSession().getAttribute(Constants.ORDER_IMPORT_FILE_CACHE_KEY + RequestUtil.getClusterSessionId(request));

            if(importDTOList == null || importDTOList.size() == 0){
                mapRes.put("r", false);
                mapRes.put("msg", this.getMessageSourceAccessor().getMessage("import.exceed_min_row_to_read"));
            }else{
                if(importDTOList != null && importDTOList.size() > 0){
                    startJobImport(importDTOList);

                    request.getSession().removeAttribute(Constants.ORDER_IMPORT_FILE_CACHE_KEY + RequestUtil.getClusterSessionId(request));

                    // TO DO CODE update Notification
                }

                mapRes.put("r", true);
            }
        }catch (Exception e){
            logger.error("Error happen when process Import KHDN file. Details: \n" + e.getMessage());
            request.getSession().removeAttribute(Constants.ORDER_IMPORT_FILE_CACHE_KEY + RequestUtil.getClusterSessionId(request));
        }

        return mapRes;
    }
}
