package com.benluck.vms.mobifonedataseller.webapp.command.command2014;

import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.PromotionDTO;
import com.benluck.vms.mobifonedataseller.webapp.command.AbstractCommand;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 3/2/15
 * Time: 11:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class DMChuongTrinhCommand extends AbstractCommand<PromotionDTO> {
    public DMChuongTrinhCommand(){
        this.pojo = new PromotionDTO();
    }
}
