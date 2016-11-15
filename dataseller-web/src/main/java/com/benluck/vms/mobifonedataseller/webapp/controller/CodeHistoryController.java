package com.benluck.vms.mobifonedataseller.webapp.controller;

import com.benluck.vms.mobifonedataseller.core.business.CodeHistoryManagementLocalBean;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/15/16
 * Time: 21:14
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class CodeHistoryController extends ApplicationObjectSupport{
    private Logger logger = Logger.getLogger(CodeHistoryController.class);

    @Autowired
    private CodeHistoryManagementLocalBean codeHistoryService;

    @RequestMapping(value = {"/ajax/calculateTotalPaidPackageValue.html"})
    public @ResponseBody Map calculateTotalPaidPackageValue(@RequestParam(value = "isdn", required = true) String isdn){
        Map<String, Object> mapRes = new HashMap<>();

        mapRes.put("value", this.codeHistoryService.calculateTotalPaidPackageValue(isdn));

        return mapRes;
    }

}
