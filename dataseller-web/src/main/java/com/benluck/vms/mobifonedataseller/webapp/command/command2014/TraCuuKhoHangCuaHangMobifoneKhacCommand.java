package com.benluck.vms.mobifonedataseller.webapp.command.command2014;

import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.StockAgentDTO;
import com.benluck.vms.mobifonedataseller.webapp.command.AbstractCommand;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/18/14
 * Time: 11:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class TraCuuKhoHangCuaHangMobifoneKhacCommand extends AbstractCommand<StockAgentDTO> {
    public TraCuuKhoHangCuaHangMobifoneKhacCommand(){
        this.pojo = new StockAgentDTO();
    }
}
