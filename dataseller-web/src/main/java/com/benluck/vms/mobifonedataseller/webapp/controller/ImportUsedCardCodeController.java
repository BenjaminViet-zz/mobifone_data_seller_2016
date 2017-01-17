package com.benluck.vms.mobifonedataseller.webapp.controller;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.utils.CommonUtil;
import com.benluck.vms.mobifonedataseller.context.AppContext;
import com.benluck.vms.mobifonedataseller.core.business.UsedCardCodeManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.UsedCardCodeDTO;
import com.benluck.vms.mobifonedataseller.security.util.SecurityUtils;
import com.benluck.vms.mobifonedataseller.util.FileUtils;
import com.benluck.vms.mobifonedataseller.util.RedisUtil;
import com.benluck.vms.mobifonedataseller.util.RequestUtil;
import com.benluck.vms.mobifonedataseller.webapp.command.ImportUsedCardCodeCommand;
import com.benluck.vms.mobifonedataseller.webapp.task.TaskImportUsedCardCode;
import com.benluck.vms.mobifonedataseller.webapp.validator.ImportUsedCardCodeValidator;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Timer;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/26/16
 * Time: 21:09
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class ImportUsedCardCodeController extends ApplicationObjectSupport{
    private Logger logger = Logger.getLogger(ImportKHDNController.class);

    @Autowired
    private UsedCardCodeManagementLocalBean usedCardCodeService;
    @Autowired
    private ImportUsedCardCodeValidator validator;

    @RequestMapping(value = {"/admin/import_used_card_code.html"})
    public ModelAndView importUsedCardCode(@ModelAttribute(Constants.FORM_MODEL_KEY)ImportUsedCardCodeCommand command,
                                           BindingResult bindingResult,
                                           HttpServletRequest request,
                                           RedirectAttributes redirectAttributes) throws IOException{
        ModelAndView mav = new ModelAndView("/admin/usedCardCode/import");
        String action = command.getCrudaction();

        if (!RedisUtil.pingRedisServer()){
            redirectAttributes.addFlashAttribute(Constants.ALERT_TYPE, "danger");
            redirectAttributes.addFlashAttribute(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("redis.msg.server_dead"));
            return new ModelAndView("redirect:/admin/order/list.html");
        }

        if(StringUtils.isNotBlank(action)){
            if(command.getStepImportIndex().equals(Constants.IMPORT_CARD_CODE_STEP_1_CHOOSE_FILE) && action.equals(Constants.ACTION_UPLOAD)){
                MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
                Map<String, MultipartFile> map = mRequest.getFileMap();
                MultipartFile csvfile = (MultipartFile) map.get("file");
                command.setFileUpload(csvfile);

                try{
                    String destFolder = CommonUtil.getBaseFolder() + CommonUtil.getTempFolderName();
                    String fileName = FileUtils.upload(mRequest, destFolder, csvfile);

                    command.setCsvFileUploadPath(request.getSession().getServletContext().getRealPath(destFolder + "/" + fileName));

                    validator.validate(command, bindingResult);
                    if(!command.getHasError().booleanValue() && StringUtils.isBlank(command.getErrorMessage()) && (command.getErrorImportUsedCardCodeList() == null || command.getErrorImportUsedCardCodeList().size() == 0)){
                        command.setStepImportIndex(Constants.IMPORT_CARD_CODE_STEP_2_UPLOAD);

                        if(StringUtils.isNotBlank(command.getErrorMessage())){
                            mav.addObject(Constants.ALERT_TYPE, "danger");
                            mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, command.getErrorMessage());
                        }else{
                            request.getSession().setAttribute(Constants.IMPORT_USED_CARD_CODE_SESSION_CACHE_KEY + RequestUtil.getClusterSessionId(request), command.getImportUsedCardCodeList());

                            mav.addObject(Constants.ALERT_TYPE, "success");
                            mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("import.correct_data"));
                        }

                        command.setImportUsedCardCode(this.usedCardCodeService.checkImportUsedCardCode());
                    }else{
                        mav.addObject(Constants.ALERT_TYPE, "danger");
                        mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, command.getErrorMessage());
                    }
                }catch (ServletException se){
                    logger.error(se.getMessage());
                    mav.addObject(Constants.ALERT_TYPE, "danger");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("import.error.file_import_unknown_error"));
                }
            }else if(action.equals(Constants.ACTION_IMPORT)){
                // start import Card Code.
                List<UsedCardCodeDTO> importUsedCardCodeList = (List<UsedCardCodeDTO>)request.getSession().getAttribute(Constants.IMPORT_USED_CARD_CODE_SESSION_CACHE_KEY + RequestUtil.getClusterSessionId(request));
                if(importUsedCardCodeList == null || importUsedCardCodeList.size() == 0){
                    request.getSession().removeAttribute(Constants.IMPORT_USED_CARD_CODE_SESSION_CACHE_KEY + RequestUtil.getClusterSessionId(request));
                    mav.addObject(Constants.ALERT_TYPE, "danger");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("import_used_card_code.data_card_code_is_expired"));
                }else{
                    try{
                        TaskImportUsedCardCode taskImportUsedCardCode = new TaskImportUsedCardCode(importUsedCardCodeList, SecurityUtils.getLoginUserId());
                        Timer timer = new Timer(true);
                        timer.schedule(taskImportUsedCardCode, 0);

                        redirectAttributes.addFlashAttribute(Constants.ALERT_TYPE, "success");
                        redirectAttributes.addFlashAttribute(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("import_used_card_code.import_in_progress"));
                        return new ModelAndView("redirect:/admin/import_used_card_code.html");
                    }catch (Exception e){
                        logger.error(e.getMessage());
                        mav.addObject(Constants.ALERT_TYPE, "danger");
                        mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("import_used_card_code.import_exception"));
                    }
                    request.getSession().removeAttribute(Constants.IMPORT_USED_CARD_CODE_SESSION_CACHE_KEY + RequestUtil.getClusterSessionId(request));
                }
            }
        }else{
            command.setStepImportIndex(0);
        }

        return mav;
    }
}
