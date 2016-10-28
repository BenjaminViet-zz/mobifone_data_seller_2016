package com.benluck.vms.mobifonedataseller.webapp.command.command2015;

import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.ActionLogDTO;
import com.benluck.vms.mobifonedataseller.webapp.command.AbstractCommand;

/**
 * Created with IntelliJ IDEA.
 * User: HP
 * Date: 3/18/15
 * Time: 5:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class LichSuChiTraCommand extends AbstractCommand<ActionLogDTO> {
    public LichSuChiTraCommand(){
        this.pojo = new ActionLogDTO();
    }
}
