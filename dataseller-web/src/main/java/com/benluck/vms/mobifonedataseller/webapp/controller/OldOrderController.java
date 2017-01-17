package com.benluck.vms.mobifonedataseller.webapp.controller;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.utils.DateUtil;
import com.benluck.vms.mobifonedataseller.core.business.*;
import com.benluck.vms.mobifonedataseller.core.dto.OrderDTO;
import com.benluck.vms.mobifonedataseller.core.dto.PackageDataDTO;
import com.benluck.vms.mobifonedataseller.core.dto.UsedCardCodeDTO;
import com.benluck.vms.mobifonedataseller.core.dto.UserDTO;
import com.benluck.vms.mobifonedataseller.editor.CustomCurrencyFormatEditor;
import com.benluck.vms.mobifonedataseller.editor.CustomDateEditor;
import com.benluck.vms.mobifonedataseller.security.util.SecurityUtils;
import com.benluck.vms.mobifonedataseller.util.RedisUtil;
import com.benluck.vms.mobifonedataseller.webapp.command.OrderCommand;
import com.benluck.vms.mobifonedataseller.webapp.exception.ForBiddenException;
import com.benluck.vms.mobifonedataseller.webapp.task.TaskImportOldOrder;
import com.benluck.vms.mobifonedataseller.webapp.validator.OldOrderValidator;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/24/16
 * Time: 21:11
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class OldOrderController extends ApplicationObjectSupport{
    private Logger logger = Logger.getLogger(OldOrderController.class);

    @Autowired
    private OrderManagementLocalBean orderService;
    @Autowired
    private PackageDataManagementLocalBean packageDataService;
    @Autowired
    private KHDNManagementLocalBean KHDNService;
    @Autowired
    private CodeHistoryManagementLocalBean codeHistoryService;
    @Autowired
    private OldOrderValidator validator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor("dd/MM/yyyy"));
        binder.registerCustomEditor(Double.class, new CustomCurrencyFormatEditor());
        binder.registerCustomEditor(Integer.class, new CustomCurrencyFormatEditor());
    }

    @RequestMapping(value = {"/admin/order/oldorder/add.html", "/user/order/oldorder/add.html"})
    public ModelAndView oldOrder(@ModelAttribute(Constants.FORM_MODEL_KEY)OrderCommand command,
                                 HttpServletRequest request,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes){
        ModelAndView mav = new ModelAndView("/admin/order/oldorder");

        if(!SecurityUtils.userHasAuthority(Constants.USERGROUP_ADMIN) && !SecurityUtils.userHasAuthority(Constants.USERGROUP_VMS_USER)){
            logger.warn("User: " + SecurityUtils.getPrincipal().getDisplayName() + ", userId: " + SecurityUtils.getLoginUserId() + " is trying to access non-authorized page: " + "/order/add.html or /order/edit.html page. ACCESS DENIED FOR BIDDEN!");
            throw new ForBiddenException();
        }

        if (!RedisUtil.pingRedisServer()){
            redirectAttributes.addFlashAttribute(Constants.ALERT_TYPE, "danger");
            redirectAttributes.addFlashAttribute(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("redis.msg.server_dead"));
            if(SecurityUtils.userHasAuthority(Constants.USERGROUP_ADMIN)){
                return new ModelAndView("redirect:/admin/order/list.html");
            }else{
                return new ModelAndView("redirect:/user/order/list.html");
            }
        }

        Boolean hasImportedUsedCardCode = (Boolean) RedisUtil.getRedisValueByKey(Constants.IMPORTED_CARD_CODE_REDIS_KEY_AND_HASKEY, Constants.IMPORTED_CARD_CODE_REDIS_KEY_AND_HASKEY);
        if(hasImportedUsedCardCode == null || !hasImportedUsedCardCode.booleanValue()){
            logger.warn("Please import Used Card Code list before using this feature.");
            if(SecurityUtils.userHasAuthority(Constants.USERGROUP_ADMIN)){
                return new ModelAndView("redirect:/admin/order/list.html");
            }else{
                return new ModelAndView("redirect:/user/order/list.html");
            }
        }

        String crudaction = command.getCrudaction();
        if(StringUtils.isNotBlank(crudaction)){
            try{
                if(crudaction.equals("insert-update")){
                    MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
                    Map<String, MultipartFile> map = mRequest.getFileMap();
                    MultipartFile fileUpload = (MultipartFile) map.get("file");
                    command.setFileUpload(fileUpload);

                    validator.validate(command, bindingResult);
                    if(!command.getHasError() && StringUtils.isBlank(command.getErrorMessage()) && (command.getErrorUsedCardCodeImportList() == null || command.getErrorUsedCardCodeImportList().size() == 0)){
                        convertDate2Timestamp(command);
                        OrderDTO pojo = command.getPojo();

                        pojo.setOrderStatus(Constants.ORDER_STATUS_PROCESSING);

                        UserDTO updatedBy = new UserDTO();
                        updatedBy.setUserId(SecurityUtils.getLoginUserId());
                        pojo.setCreatedBy(updatedBy);

                        PackageDataDTO packageDataDTO = this.packageDataService.findById(pojo.getPackageData().getPackageDataId());
                        if(packageDataDTO.getUnitPrice4CardCode().length() > 2){
                            mav.addObject(Constants.ALERT_TYPE, "info");
                            mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("order.only_support_unit_price_2_digit"));
                        }else{
                            pojo.setUnitPrice(packageDataDTO.getValue());
                            if (pojo.getOrderId() == null ){
                                pojo = this.orderService.createOldOrder(pojo);

                                startJobOldOrderProcessing(pojo.getOrderId(), command.getUsedCardCodeImportList());

                                redirectAttributes.addFlashAttribute(Constants.ALERT_TYPE, "success");
                                redirectAttributes.addFlashAttribute("messageResponse", this.getMessageSourceAccessor().getMessage("old_order.procseeing_import_used_card_code"));

                                if(SecurityUtils.userHasAuthority(Constants.USERGROUP_ADMIN)){
                                    return new ModelAndView("redirect:/admin/order/list.html");
                                }else if(SecurityUtils.userHasAuthority(Constants.USERGROUP_VMS_USER)){
                                    return new ModelAndView("redirect:/user/order/list.html");
                                }else if(SecurityUtils.userHasAuthority(Constants.USERGROUP_KHDN)){
                                    return new ModelAndView("redirect:/khdn/order/list.html");
                                }else{
                                    return new ModelAndView("redirect:/custom_user/order/list.html");
                                }

                            } else {
//                                OrderDTO originOrderDTO = this.orderService.findById(command.getPojo().getOrderId());
//
//                                if(originOrderDTO.getOrderStatus().equals(Constants.ORDER_STATUS_PROCESSING)
//                                        && pojo.getOrderStatus().equals(Constants.ORDER_STATUS_FINISH)){
//                                    pojo.setCardCodeProcessStatus(Constants.ORDER_CARD_CODE_PROCESSING_STATUS);
//                                }
//
//                                this.orderService.updateItem(pojo);
//
//                                // Update Card Code size in DB nd Cache
//                                if(originOrderDTO.getOrderStatus().equals(Constants.ORDER_STATUS_PROCESSING)
//                                        && pojo.getOrderStatus().equals(Constants.ORDER_STATUS_FINISH)){
//                                    startTaskTakingCardCode(pojo.getOrderId(), packageDataDTO.getUnitPrice());
//                                }
//                                redirectAttributes.addFlashAttribute(Constants.ALERT_TYPE, "success");
//                                redirectAttributes.addFlashAttribute("messageResponse", this.getMessageSourceAccessor().getMessage("database.update.successful"));
                            }

                            if(SecurityUtils.userHasAuthority(Constants.USERGROUP_ADMIN)){
                                return new ModelAndView("redirect:/admin/order/list.html");
                            }else{
                                return new ModelAndView("redirect:/user/order/list.html");
                            }
                        }
                    }else{
                        command.setTotalItems(command.getErrorUsedCardCodeImportList().size());
                        command.setMaxPageItems(command.getTotalItems());
                        mav.addObject(Constants.LIST_MODEL_KEY, command);

                        mav.addObject(Constants.ALERT_TYPE, "danger");
                        mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, command.getErrorMessage());
                    }
                }
            }catch (Exception e){
                logger.error("Error when create Old Order. \nDetails: " + e.getMessage());
                mav.addObject(Constants.ALERT_TYPE, "danger");
                mav.addObject("messageResponse", this.getMessageSourceAccessor().getMessage("old_order.create_exception_duplicated_card_code"));
            }
        }

        preferenceData(mav, command);
        return mav;
    }

    private void startJobOldOrderProcessing(Long orderId, List<UsedCardCodeDTO> usedCardCodeDTOList){
        TaskImportOldOrder taskImportOldOrder = new TaskImportOldOrder(usedCardCodeDTOList, SecurityUtils.getLoginUserId(), orderId);
        Timer timer = new Timer(true);
        timer.schedule(taskImportOldOrder, 0);
    }

    private void preferenceData(ModelAndView mav, OrderCommand command){
        mav.addObject("packageDataList", packageDataService.findAll());
        mav.addObject("KHDNList", KHDNService.findAll());
        mav.addObject("packageDataIdListHasGeneratedCardCode", this.packageDataService.findPackageDataIdListHasGeneratedCardCode(Calendar.getInstance().get(Calendar.YEAR)));
        mav.addObject("hasImportedUsedCardCode", RedisUtil.getRedisValueByKey(Constants.IMPORTED_CARD_CODE_REDIS_KEY_AND_HASKEY, Constants.IMPORTED_CARD_CODE_REDIS_KEY_AND_HASKEY));

        if(command.getPojo() != null && command.getPojo().getOrderId() != null ){
            mav.addObject("totalRemainingPaidPackageValue", this.codeHistoryService.calculateTotalPaidPackageValue(command.getPojo().getKhdn().getStb_vas(), command.getPojo().getOrderId()));
        }else{
            mav.addObject("totalRemainingPaidPackageValue", 0D);
        }
    }

    /**
     * Copy Date value and format to Timestamp.
     * @param command
     */
    private void convertDate2Timestamp(OrderCommand command){
        if(command.getIssuedDate() != null){
            command.getPojo().setIssuedDate(DateUtil.dateToTimestamp(command.getIssuedDate(), Constants.VI_DATE_FORMAT));
        }
        if(command.getShippingDate() != null){
            command.getPojo().setShippingDate(DateUtil.dateToTimestamp(command.getShippingDate(), Constants.VI_DATE_FORMAT));
        }
    }
}
