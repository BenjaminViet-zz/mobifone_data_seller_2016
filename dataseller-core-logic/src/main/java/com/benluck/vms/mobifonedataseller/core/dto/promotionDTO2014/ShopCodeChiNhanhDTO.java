package com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/18/14
 * Time: 5:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class ShopCodeChiNhanhDTO implements Serializable {
    private static final long serialVersionUID = 6354682135389499890L;

    private Long shopCodeChiNhanhId;
    private String shopCode;
    private ChiNhanhDTO chiNhanh;

    public Long getShopCodeChiNhanhId() {
        return shopCodeChiNhanhId;
    }

    public void setShopCodeChiNhanhId(Long shopCodeChiNhanhId) {
        this.shopCodeChiNhanhId = shopCodeChiNhanhId;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public ChiNhanhDTO getChiNhanh() {
        return chiNhanh;
    }

    public void setChiNhanh(ChiNhanhDTO chiNhanh) {
        this.chiNhanh = chiNhanh;
    }
}
