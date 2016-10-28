package com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/23/14
 * Time: 7:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class PromItemsDTO implements Serializable {
    private static final long serialVersionUID = -1262751018578392415L;

    private Long item_Id;
    private String item_code;
    private String item_name;
    private Integer from_value;
    private String status;
    private String item_unit;

    public Long getItem_Id() {
        return item_Id;
    }

    public void setItem_Id(Long item_Id) {
        this.item_Id = item_Id;
    }

    public String getItem_code() {
        return item_code;
    }

    public void setItem_code(String item_code) {
        this.item_code = item_code;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public Integer getFrom_value() {
        return from_value;
    }

    public void setFrom_value(Integer from_value) {
        this.from_value = from_value;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getItem_unit() {
        return item_unit;
    }

    public void setItem_unit(String item_unit) {
        this.item_unit = item_unit;
    }
}
