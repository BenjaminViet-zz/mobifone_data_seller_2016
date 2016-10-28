package com.benluck.vms.mobifonedataseller.webapp.command.command2014;


import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.RoleDTO;
import com.benluck.vms.mobifonedataseller.webapp.command.AbstractCommand;

/**
 * Created with IntelliJ IDEA.
 * User: benluck
 * Date: 11/17/13
 * Time: 3:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class RoleCommand extends AbstractCommand<RoleDTO> {
    public RoleCommand() {
        this.pojo = new RoleDTO();
    }

    private String[] deleteList;

    private Long userID;

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String[] getDeleteList() {
        return deleteList;
    }

    public void setDeleteList(String[] deleteList) {
        this.deleteList = deleteList;
    }

}
