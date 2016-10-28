package com.benluck.vms.mobifonedataseller.webapp.command.command2014;


import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.UserGroupDTO;
import com.benluck.vms.mobifonedataseller.webapp.command.AbstractCommand;

/**
 * Created with IntelliJ IDEA.
 * User: benluck
 * Date: 11/17/13
 * Time: 3:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserGroupCommand extends AbstractCommand<UserGroupDTO> {
    public UserGroupCommand() {
        this.pojo = new UserGroupDTO();
    }
}
