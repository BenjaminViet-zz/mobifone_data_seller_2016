package com.benluck.vms.mobifonedataseller.core.dto;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/15/14
 * Time: 2:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class CTTichDiemSoDiemStatisticByMonthDTO implements Serializable{
    private static final long serialVersionUID = -4746221302014972093L;

    private Integer month;
    private Double total;

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
