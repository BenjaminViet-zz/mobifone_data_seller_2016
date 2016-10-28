package com.benluck.vms.mobifonedataseller.domain;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 3/16/15
 * Time: 11:03 AM
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "ACTION_LOG")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Entity
public class ActionLogEntity {
    private Long logId;
    private DMChuongTrinhEntity chuongTrinh;
    private String tableLog;
    private String columnIdLogName;
    private UserEntity nguoiThaoTac;
    private Timestamp createdTime;
    private String description;
    private Long columnIdLogValue;
    private String supportData;

    @Column(name = "LOGID")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACTION_LOG_SEQ")
    @SequenceGenerator(name="ACTION_LOG_SEQ", sequenceName="ACTION_LOG_SEQ", allocationSize=1)
    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHUONGTRINHID", referencedColumnName = "CHUONGTRINHID")
    public DMChuongTrinhEntity getChuongTrinh() {
        return chuongTrinh;
    }

    public void setChuongTrinh(DMChuongTrinhEntity chuongTrinh) {
        this.chuongTrinh = chuongTrinh;
    }

    @Column(name = "TABLE_LOG")
    @Basic
    public String getTableLog() {
        return tableLog;
    }

    public void setTableLog(String tableLog) {
        this.tableLog = tableLog;
    }

    @Column(name = "COLUMN_ID_LOG_NAME")
    @Basic
    public String getColumnIdLogName() {
        return columnIdLogName;
    }

    public void setColumnIdLogName(String columnIdLogName) {
        this.columnIdLogName = columnIdLogName;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USERID", referencedColumnName = "USERID")
    public UserEntity getNguoiThaoTac() {
        return nguoiThaoTac;
    }

    public void setNguoiThaoTac(UserEntity nguoiThaoTac) {
        this.nguoiThaoTac = nguoiThaoTac;
    }

    @Column(name = "CREATEDTIME")
    @Basic
    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    @Column(name = "DESCRIPTION")
    @Basic
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "COLUMN_ID_LOG_VALUE")
    @Basic
    public Long getColumnIdLogValue() {
        return columnIdLogValue;
    }

    public void setColumnIdLogValue(Long columnIdLogValue) {
        this.columnIdLogValue = columnIdLogValue;
    }

    @Column(name = "SUPPORT_DATA")
    @Basic
    public String getSupportData() {
        return supportData;
    }

    public void setSupportData(String supportData) {
        this.supportData = supportData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActionLogEntity that = (ActionLogEntity) o;

        if (logId != null ? !logId.equals(that.logId) : that.logId != null) return false;
        if (tableLog != null ? !tableLog.equals(that.tableLog) : that.tableLog != null) return false;
        if (columnIdLogName != null ? !columnIdLogName.equals(that.columnIdLogName) : that.columnIdLogName != null) return false;
        if (createdTime != null ? !createdTime.equals(that.createdTime) : that.createdTime != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (columnIdLogValue != null ? !columnIdLogValue.equals(that.columnIdLogValue) : that.columnIdLogValue != null) return false;
        if (supportData!= null ? !supportData.equals(that.supportData) : that.supportData != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = logId != null ? logId.hashCode() : 0;
        result = 31 * result + (tableLog != null ? tableLog.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (columnIdLogName != null ? columnIdLogName.hashCode() : 0);
        result = 31 * result + (createdTime != null ? createdTime.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (columnIdLogValue != null ? columnIdLogValue.hashCode() : 0);
        result = 31 * result + (supportData != null ? supportData.hashCode() : 0);
        return result;
    }
}
