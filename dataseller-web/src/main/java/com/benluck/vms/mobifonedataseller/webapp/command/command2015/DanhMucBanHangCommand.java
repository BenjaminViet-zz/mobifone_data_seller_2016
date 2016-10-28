package com.benluck.vms.mobifonedataseller.webapp.command.command2015;

import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.RetailDealerDTO;
import com.benluck.vms.mobifonedataseller.webapp.command.AbstractCommand;

/**
 * Created with IntelliJ IDEA.
 * User: HP
 * Date: 2/12/15
 * Time: 12:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class DanhMucBanHangCommand extends AbstractCommand<RetailDealerDTO> {
    public DanhMucBanHangCommand(){
        this.pojo = new RetailDealerDTO();
    }
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}

