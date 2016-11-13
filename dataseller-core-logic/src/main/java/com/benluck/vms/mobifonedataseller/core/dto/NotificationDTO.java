package com.benluck.vms.mobifonedataseller.core.dto;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/12/16
 * Time: 15:53
 * To change this template use File | Settings | File Templates.
 */
public class NotificationDTO implements Serializable{
    private static final long serialVersionUID = -7872092705158472726L;

    private Long notificationId;
    private UserDTO user;
    private String messageType;
    private String message;
    private Timestamp createdDate;
    private Integer isRead = 0;     // 0 : Not yet read; 1: read already

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getRead() {
        return isRead;
    }

    public void setRead(Integer read) {
        isRead = read;
    }
}
