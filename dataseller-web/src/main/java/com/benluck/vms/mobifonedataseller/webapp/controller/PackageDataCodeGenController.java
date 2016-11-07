package com.benluck.vms.mobifonedataseller.webapp.controller;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.utils.Config;
import com.benluck.vms.mobifonedataseller.core.business.PackageDataCodeGenManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.PackageDataManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.PackageDataCodeGenDTO;
import com.benluck.vms.mobifonedataseller.core.dto.PackageDataDTO;
import com.benluck.vms.mobifonedataseller.util.RequestUtil;
import com.benluck.vms.mobifonedataseller.webapp.command.PackageDataCodeGenCommand;
import com.benluck.vms.mobifonedataseller.webapp.task.TaskGenerateCardCode;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.ejb.ObjectNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/6/16
 * Time: 20:50
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class PackageDataCodeGenController extends ApplicationObjectSupport{
    private Logger logger = Logger.getLogger(PackageDataController.class);

    @Autowired
    private PackageDataCodeGenManagementLocalBean packageDataCodeGenService;
    @Autowired
    private PackageDataManagementLocalBean packageDataService;

    @RequestMapping(value = {"/admin/packagedatacodegen/list.html"})
    public ModelAndView list(@ModelAttribute(Constants.FORM_MODEL_KEY)PackageDataCodeGenCommand command,
                             HttpServletRequest request){
        ModelAndView mav = new ModelAndView("/admin/packagedatacodegen/list");
        executeSearch(command, request);
        mav.addObject(Constants.LIST_MODEL_KEY, command);
        preferenceData(mav);
        return mav;
    }

    private void executeSearch(PackageDataCodeGenCommand command, HttpServletRequest request){
        RequestUtil.initSearchBean(request, command);
        Map<String, Object> properties = new HashMap<String, Object>();

        PackageDataCodeGenDTO pojo = command.getPojo();
        properties.put("year", pojo.getYear());

        if(pojo.getPackageData() != null && pojo.getPackageData().getPackageDataId() != null){
            properties.put("packageData.packageDataId", pojo.getPackageData().getPackageDataId());
        }

        Object[] resultObject = this.packageDataCodeGenService.searchByProperties(properties, command.getSortExpression(), command.getSortDirection(), command.getFirstItem(), command.getReportMaxPageItems());
        command.setTotalItems(Integer.valueOf(resultObject[0].toString()));
        command.setListResult((List<PackageDataCodeGenDTO>)resultObject[1]);
        command.setMaxPageItems(command.getReportMaxPageItems());

        // Danh sach Package Data chua sinh Card Code.
        Object[] resultObject1 = this.packageDataService.findListNotYetGenerateCardCode(pojo.getYear(), null, null, 0, Integer.MAX_VALUE);
        command.setTotalPackageDataNotGenerateCardCode(Integer.valueOf(resultObject1[0].toString()));
        command.setPackageDataNotGenerateCardCodeListResult((List<PackageDataDTO>)resultObject1[1]);
    }

    private void preferenceData(ModelAndView mav){
        List<PackageDataDTO> packageDataList = this.packageDataService.findAll();
        mav.addObject("packageDataList", packageDataList);

        List<Integer> yearList = new ArrayList<Integer>();
        Calendar current = Calendar.getInstance();
        Integer currentYear = current.get(Calendar.YEAR);
        yearList.add(currentYear - 1);
        for (int year = currentYear; year < currentYear + 5; year++){
            yearList.add(year);
        }
        mav.addObject("yearList", yearList);
    }

    @RequestMapping(value = {"/ajax/admin/packagedatacodegen/generateCardCode.html"})
    public @ResponseBody Map generateCardCode(@RequestParam(value = "year", required = true)Integer year,
                                              @RequestParam(value = "packageDataIds", required = true)String[] packageDataIds){
        Map resultMap = new HashMap();

        try{
            Boolean allow2GenerateCardCode = this.packageDataCodeGenService.checkBeforeGeneratingCardCode(year, packageDataIds);
            if(allow2GenerateCardCode){
                this.packageDataCodeGenService.updateProcessing(year, packageDataIds, Constants.PACKAGE_DATA_CODE_GEN_STATUS_PROCESSING);

                TaskGenerateCardCode generateCardCodeTask = new TaskGenerateCardCode(year, packageDataIds);
                Timer timer = new Timer(true);
                timer.schedule(generateCardCodeTask, 0);

                resultMap.put("r", true);
            }else{
                resultMap.put("r", false);
                resultMap.put("msg", this.getMessageSourceAccessor().getMessage("packagedatacodegen.generation.at_least_one_package_data_has_generated_card_code_success"));
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            resultMap.put("r", false);
        }
        return resultMap;
    }
}
