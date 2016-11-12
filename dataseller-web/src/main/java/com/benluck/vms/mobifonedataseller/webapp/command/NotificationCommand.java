package com.benluck.vms.mobifonedataseller.webapp.command;

import com.benluck.vms.mobifonedataseller.core.dto.NotificationDTO;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/12/16
 * Time: 16:01
 * To change this template use File | Settings | File Templates.
 */
public class NotificationCommand extends AbstractCommand<NotificationDTO>{
    public NotificationCommand(){
        this.pojo = new NotificationDTO();
    }
}
