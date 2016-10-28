package com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/31/14
 * Time: 1:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class KppGiftDTO implements Serializable{
    private static final long serialVersionUID = -4057296531054689614L;

    private Long gift_Id;
    private String gift_Name;
    private String gift_level;
    private Integer gift_Quantity;
    private Double gift_Value;

    public Long getGift_Id() {
        return gift_Id;
    }

    public void setGift_Id(Long gift_Id) {
        this.gift_Id = gift_Id;
    }

    public String getGift_Name() {
        return gift_Name;
    }

    public void setGift_Name(String gift_Name) {
        this.gift_Name = gift_Name;
    }

    public String getGift_level() {
        return gift_level;
    }

    public void setGift_level(String gift_level) {
        this.gift_level = gift_level;
    }

    public Integer getGift_Quantity() {
        return gift_Quantity;
    }

    public void setGift_Quantity(Integer gift_Quantity) {
        this.gift_Quantity = gift_Quantity;
    }

    public Double getGift_Value() {
        return gift_Value;
    }

    public void setGift_Value(Double gift_Value) {
        this.gift_Value = gift_Value;
    }
}
