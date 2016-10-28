package com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 11/19/14
 * Time: 10:00 AM
 * To change this template use File | Settings | File Templates.
 */
public class ServiceTypeDTO implements Serializable{
    private static final long serialVersionUID = 2167871623639491715L;

    private String name;
    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
