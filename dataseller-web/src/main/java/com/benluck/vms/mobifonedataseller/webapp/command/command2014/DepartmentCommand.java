package com.benluck.vms.mobifonedataseller.webapp.command.command2014;


import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.DepartmentDTO;
import com.benluck.vms.mobifonedataseller.webapp.command.AbstractCommand;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2/19/14
 * Time: 1:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class DepartmentCommand extends AbstractCommand<DepartmentDTO> {
    public DepartmentCommand(){
        this.pojo = new DepartmentDTO();
    }
}
