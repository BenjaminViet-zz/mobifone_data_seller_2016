package com.benluck.vms.mobifonedataseller.webapp.command;


import com.benluck.vms.mobifonedataseller.core.dto.UserGroupDTO;

import java.util.ArrayList;
import java.util.List;

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
