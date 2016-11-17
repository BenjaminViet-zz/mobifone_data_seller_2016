package com.benluck.vms.mobifonedataseller.webapp.controller;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.core.business.NotificationManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.NotificationDTO;
import com.benluck.vms.mobifonedataseller.security.util.SecurityUtils;
import com.benluck.vms.mobifonedataseller.util.RequestUtil;
import com.benluck.vms.mobifonedataseller.webapp.command.NotificationCommand;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 10/29/16
 * Time: 15:06
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class DashboardPageController extends ApplicationObjectSupport{
    private Logger logger = Logger.getLogger(DashboardPageController.class);

    @Autowired
    private NotificationManagementLocalBean notificationService;

    @RequestMapping(value = {"/admin/notification.html", "/user/notification.html", "/khdn/notification.html"})
    public ModelAndView checkLogin(@ModelAttribute(Constants.FORM_MODEL_KEY)NotificationCommand command,
                                   HttpServletRequest request){
        ModelAndView mav = new ModelAndView("/admin/notification/dashboard");
        String action = command.getCrudaction();

        if(StringUtils.isNotBlank(action) && action.equals(Constants.ACTION_UPDATE)){
            if(command.getCheckList() != null && command.getCheckList().length > 0){
                try{
                    List<Long> notificationIds = new ArrayList<Long>();
                    for (String notificationIdStr : command.getCheckList()){
                        notificationIds.add(Long.valueOf(notificationIdStr));
                    }

                    notificationService.updateIsRead(notificationIds);

                    mav.addObject(Constants.ALERT_TYPE, "success");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("dashboard.notification.update_notification_success"));
                }catch (Exception e){
                    logger.error(e.getMessage());
                    mav.addObject(Constants.ALERT_TYPE, "danger");
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("dashboard.notification.error_on_update_notification_list"));
                }
            }else{
                mav.addObject(Constants.ALERT_TYPE, "danger");
                mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY, this.getMessageSourceAccessor().getMessage("dashboard.notification.no_items_chosen_to_update"));
            }
        }

        executeSearch(command, request);
        mav.addObject(Constants.LIST_MODEL_KEY, command);
        return mav;
    }

    private void executeSearch(NotificationCommand command, HttpServletRequest request){
        RequestUtil.initSearchBean(request, command);

        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("read", command.getPojo().getRead());
        properties.put("user.userId", SecurityUtils.getLoginUserId());

        command.setSortExpression("createdDate");
        command.setSortDirection(Constants.SORT_DESC);

        Object[] resultObject = this.notificationService.searchByProperties(properties, command.getSortExpression(), command.getSortDirection(), command.getFirstItem(), command.getMaxPageItems());
        command.setTotalItems(Integer.valueOf(resultObject[0].toString()));
        command.setListResult((List<NotificationDTO>)resultObject[1]);
    }
}
