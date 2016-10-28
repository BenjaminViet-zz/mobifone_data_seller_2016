package com.benluck.vms.mobifonedataseller.webapp.validator;

import com.benluck.vms.mobifonedataseller.core.business.StockAgentManagementLocalBean;
import com.benluck.vms.mobifonedataseller.security.util.SecurityUtils;
import com.benluck.vms.mobifonedataseller.webapp.command.command2014.GiftDeliveryAgentHistoryCommand;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 11/5/14
 * Time: 4:11 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class XuatKhoValidator extends ApplicationObjectSupport implements Validator{
    private transient final Log log = LogFactory.getLog(getClass());

    @Autowired
    private StockAgentManagementLocalBean stockAgentManagementLocalBean;

    @Override
    public boolean supports(Class<?> aClass) {
        return GiftDeliveryAgentHistoryCommand.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        GiftDeliveryAgentHistoryCommand command = (GiftDeliveryAgentHistoryCommand)o;
        checkValidStock2Release(command, errors);
    }

    /**
     * Check items in stock to make sure that it is always enough of items to delivery.
     * @param command
     * @param errors
     */
    private void checkValidStock2Release(GiftDeliveryAgentHistoryCommand command, Errors errors){
        if(command.getPojo().getQuantity() != null && command.getPojo().getQuantity().compareTo(0) > 0){
            try{
                Integer totalInventoryInStock = this.stockAgentManagementLocalBean.countInventoryTotalByAgentId_tdcg(SecurityUtils.getPrincipal().getDepartmentId());
                if(totalInventoryInStock.compareTo(command.getPojo().getQuantity()) < 0){
                    errors.rejectValue("pojo.quantity", ".cuahanggiaodich.xuat_kho.msg.not_enough_stock_inventory");
                }
            }catch (Exception e){
                command.setMessage(this.getMessageSourceAccessor().getMessage("cuahanggiaodich.xuat_kho.msg.no_inventory_in_stock"));
            }
        }else{
            errors.rejectValue("pojo.quantity", "cuahanggiaodich.xuat_kho.msg.not_empty_so_luong_xuat");
        }
    }
}
