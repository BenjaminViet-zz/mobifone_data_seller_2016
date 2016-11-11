package com.benluck.vms.mobifonedataseller.webapp.command;

import com.benluck.vms.mobifonedataseller.core.dto.MBDCodeHistoryDTO;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/11/16
 * Time: 19:49
 * To change this template use File | Settings | File Templates.
 */
public class MBDCodeHistoryCommand extends AbstractCommand<MBDCodeHistoryDTO>{
    public MBDCodeHistoryCommand(){
        this.pojo = new MBDCodeHistoryDTO();
    }
}
