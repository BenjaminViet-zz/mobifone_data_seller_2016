package com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2015;

import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.DepartmentDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.GiftDTO;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: vovanphuc0810
 * Date: 4/7/15
 * Time: 1:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class ShopStockGoodsDTO implements Serializable {
    private static final long serialVersionUID = -3753518079169218183L;

    private DepartmentDTO department;
    private Long stockId;
    private GiftDTO gift;
    private Integer quantity;

    public DepartmentDTO getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentDTO department) {
        this.department = department;
    }

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    public GiftDTO getGift() {
        return gift;
    }

    public void setGift(GiftDTO gift) {
        this.gift = gift;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
