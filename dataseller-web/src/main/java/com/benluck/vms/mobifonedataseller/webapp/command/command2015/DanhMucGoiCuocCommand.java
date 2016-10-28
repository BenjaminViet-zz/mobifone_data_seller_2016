package com.benluck.vms.mobifonedataseller.webapp.command.command2015;

import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015.PromPackageDTO;
import com.benluck.vms.mobifonedataseller.webapp.command.AbstractCommand;

/**
 * Created with IntelliJ IDEA.
 * User: HP
 * Date: 2/25/15
 * Time: 10:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class DanhMucGoiCuocCommand extends AbstractCommand<PromPackageDTO> {
    public DanhMucGoiCuocCommand(){
        this.pojo = new PromPackageDTO();
    }
}
