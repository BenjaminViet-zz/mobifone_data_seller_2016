package com.benluck.vms.mobifonedataseller.webapp.command.command2014;

import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.MoneyExchangeBranchHistoryDTO;
import com.benluck.vms.mobifonedataseller.webapp.command.AbstractCommand;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/26/14
 * Time: 9:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class MoneyExchangeBranchHistoryCommand extends AbstractCommand<MoneyExchangeBranchHistoryDTO> {
    public MoneyExchangeBranchHistoryCommand(){
        this.pojo = new MoneyExchangeBranchHistoryDTO();
    }

    private Long branchId;
    private Long districtId;
    private String dealer_code;
    private Long dealer_Id;

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public String getDealer_code() {
        return dealer_code;
    }

    public void setDealer_code(String dealer_code) {
        this.dealer_code = dealer_code;
    }

    public Long getDealer_Id() {
        return dealer_Id;
    }

    public void setDealer_Id(Long dealer_Id) {
        this.dealer_Id = dealer_Id;
    }
}
