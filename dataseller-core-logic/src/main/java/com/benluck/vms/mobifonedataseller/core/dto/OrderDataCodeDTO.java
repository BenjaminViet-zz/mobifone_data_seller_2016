package com.benluck.vms.mobifonedataseller.core.dto;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/6/16
 * Time: 11:12
 * To change this template use File | Settings | File Templates.
 */
public class OrderDataCodeDTO implements Serializable{
    private static final long serialVersionUID = 3918146555836267495L;
    private Long orderDataCodeId;
    private OrderDTO order;
    private Long serial;
    private String dataCode;
    private Timestamp expiredDate;

    public Long getOrderDataCodeId() {
        return orderDataCodeId;
    }

    public void setOrderDataCodeId(Long orderDataCodeId) {
        this.orderDataCodeId = orderDataCodeId;
    }

    public OrderDTO getOrder() {
        return order;
    }

    public void setOrder(OrderDTO order) {
        this.order = order;
    }

    public Long getSerial() {
        return serial;
    }

    public void setSerial(Long serial) {
        this.serial = serial;
    }

    public String getDataCode() {
        return dataCode;
    }

    public void setDataCode(String dataCode) {
        this.dataCode = dataCode;
    }

    public Timestamp getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Timestamp expiredDate) {
        this.expiredDate = expiredDate;
    }
}
