package com.benluck.vms.mobifonedataseller.webapp.controller;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.webapp.command.command2014.TicketCommand;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/18/14
 * Time: 10:48 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class TongDaiController extends ApplicationObjectSupport{

    @RequestMapping("/tongdai/tracuu.html")
    public ModelAndView tracuu(@ModelAttribute(value = Constants.FORM_MODEL_KEY)TicketCommand command,
                               HttpServletRequest request){
        return new ModelAndView("/cuahangmobifone/tracuumaphieu");
    }
}
