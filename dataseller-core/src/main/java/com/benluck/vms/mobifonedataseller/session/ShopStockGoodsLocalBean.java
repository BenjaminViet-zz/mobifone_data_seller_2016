package com.benluck.vms.mobifonedataseller.session;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vovanphuc0810
 * Date: 4/7/15
 * Time: 1:45 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ShopStockGoodsLocalBean {

    /**
     * Find quantity of each items in the shop stock by shop code and giftId
     * @param shopCode
     * @param giftId
     * @return A list of items with more information.
     */
    List findShopStockByShopCode(String shopCode, Long giftId);

    /**
     * Find quantity of each items in the shop stock by stockId and giftId
     * @param stockId
     * @param giftId
     * @return A list of items with more information.
     */
    List findShopStockByIdAndStockId(Long stockId, Long giftId);
}
