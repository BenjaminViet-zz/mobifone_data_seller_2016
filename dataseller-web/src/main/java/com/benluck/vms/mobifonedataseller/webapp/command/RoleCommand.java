package com.benluck.vms.mobifonedataseller.webapp.command;


import com.benluck.vms.mobifonedataseller.core.dto.RoleDTO;

/**
 * Created with IntelliJ IDEA.
 * User: viennh
 * Date: 11/17/13
 * Time: 3:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class RoleCommand extends AbstractCommand<RoleDTO> {
    public RoleCommand() {
        this.pojo = new RoleDTO();
    }
    private Long selectedUserId;

    public Long getSelectedUserId() {
        return selectedUserId;
    }

    public void setSelectedUserId(Long selectedUserId) {
        this.selectedUserId = selectedUserId;
    }
}
