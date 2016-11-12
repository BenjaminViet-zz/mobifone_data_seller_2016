package com.benluck.vms.mobifonedataseller.domain;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/12/16
 * Time: 15:41
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "MOBI_DATA_NOTIFICATION")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Entity
public class NotificationEntity {
    private Long notificationId;
    private UserEntity user;
    private String messageType;
    private String message;
    private Timestamp createdDate;
    private Integer isRead;

    @Column(name = "NotificationID")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MOBI_DATA_NOTIFY_SEQ")
    @SequenceGenerator(name="MOBI_DATA_NOTIFY_SEQ", sequenceName="MOBI_DATA_NOTIFY_SEQ", allocationSize=1)
    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USERID", referencedColumnName = "USERID")
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @Column(name = "MESSAGETYPE")
    @Basic
    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    @Column(name = "MESSAGE")
    @Basic
    @Lob
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Column(name = "CREATEDDATE")
    @Basic
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "ISREAD")
    @Basic
    public Integer getRead() {
        return isRead;
    }

    public void setRead(Integer read) {
        this.isRead = read;
    }

    @Override
    public int hashCode() {
        int result = notificationId != null ? notificationId.hashCode() : 0;
        result = 31 * result + (messageType != null ? messageType.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (isRead != null ? isRead.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NotificationEntity that = (NotificationEntity) o;

        if (notificationId != null ? !notificationId.equals(that.notificationId) : that.notificationId != null) return false;
        if (messageType != null ? !messageType.equals(that.messageType) : that.messageType != null) return false;
        if (message != null ? !message.equals(that.message) : that.message != null) return false;
        if (isRead != null ? !isRead.equals(that.isRead) : that.isRead != null) return false;
        if (createdDate != null ? !createdDate.equals(that.createdDate) : that.createdDate != null) return false;

        return true;
    }
}
