package com.benluck.vms.mobifonedataseller.webapp.validator.validator2015;

import com.benluck.vms.mobifonedataseller.common.utils.CommonUtil;
import com.benluck.vms.mobifonedataseller.core.business.RetailDealerManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.RetailDealerDTO;
import com.benluck.vms.mobifonedataseller.webapp.command.command2015.DanhMucBanHangCommand;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 3/23/15
 * Time: 12:30 AM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class ImportDangKyDSThoaThuanValidator extends ApplicationObjectSupport implements Validator {
    @Autowired
    RetailDealerManagementLocalBean retailDealerManagementLocalBean;
    @Override
    public boolean supports(Class<?> aClass) {
        return ImportDangKyDSThoaThuanValidator.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        DanhMucBanHangCommand command = (DanhMucBanHangCommand)o;
        trimmingFields(command, errors);
        checkDuplicatedItems(command, errors);

    }

    /**
     * Remove spaces at the start and end of string variables.
     * @param command
     * @param errors
     */
    private void trimmingFields(DanhMucBanHangCommand command, Errors errors){
        if(command.getListResult() != null && command.getListResult().size() > 0){
            for (RetailDealerDTO dto : command.getListResult()){
                dto.setDealer_code(dto.getDealer_code().trim());
                dto.setEz_isdn(CommonUtil.removeCountryCode(dto.getEz_isdn().trim()));
                if(dto.getBranch() != null && StringUtils.isNotBlank(dto.getBranch().getBranch_code())){
                    dto.getBranch().setBranch_code(dto.getBranch().getBranch_code().trim());
                }
                if(dto.getDistrict() != null && StringUtils.isNotBlank(dto.getDistrict().getDistrict_code())){
                    dto.getDistrict().setDistrict_code(dto.getDistrict().getDistrict_code().trim());
                }
            }
        }
    }

    /**
     * Check if duplicated values.
     * @param command
     * @param errors
     */
    private void checkDuplicatedItems(DanhMucBanHangCommand command, Errors errors){
        if(command.getListResult() != null && command.getListResult().size() > 0){
            List<String> complexKeyList = new ArrayList<String>();
            for (RetailDealerDTO dto : command.getListResult()){
                String complexKey = dto.getEz_isdn() + "_" + dto.getDealer_code();
                if(!this.retailDealerManagementLocalBean.checkIfDealerHaveDOC(dto.getDealer_code(), dto.getEz_isdn())){
                    if(!complexKeyList.contains(complexKey)){
                        complexKeyList.add(complexKey);
                    }else{
                        dto.setError(this.getMessageSourceAccessor().getMessage("import_thoa_thuan.msg.duplicated_import_item"));
                    }
                }else{
                    dto.setError(this.getMessageSourceAccessor().getMessage("import_thoa_thuan.msg.duplicated_import"));
                }
            }
        }
    }
}
