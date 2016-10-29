package com.benluck.vms.mobifonedataseller.webapp.command;


import com.benluck.vms.mobifonedataseller.core.dto.UserDTO;
import com.benluck.vms.mobifonedataseller.webapp.command.AbstractCommand;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2/19/14
 * Time: 1:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserCommand extends AbstractCommand<UserDTO> {
    public UserCommand(){
        this.pojo = new UserDTO();
    }
}
