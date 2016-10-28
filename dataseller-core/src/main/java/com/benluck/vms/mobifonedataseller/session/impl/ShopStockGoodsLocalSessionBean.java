package com.benluck.vms.mobifonedataseller.session.impl;

import com.benluck.vms.mobifonedataseller.session.ShopStockGoodsLocalBean;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vovanphuc0810
 * Date: 4/7/15
 * Time: 1:46 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "ShopStockGoodsLocalSessionEJB")
public class ShopStockGoodsLocalSessionBean implements ShopStockGoodsLocalBean {
    public ShopStockGoodsLocalSessionBean() {
    }
    @PersistenceContext(unitName = "core-data")
    protected EntityManager entityManager;

    @Override
    public List findShopStockByShopCode(String shopCode, Long giftId) {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select sg.STOCK_ID, sg.GIFT_ID, sg.QUANTITY from QSV_2015_SHOP_STOCK_GOODS@MKITDW_KM_QSV_DBL sg where sg.SHOP_CODE = :shopCode and sg.GIFT_ID = :giftId order by sg.SHOP_CODE  ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("shopCode", shopCode);
        query.setParameter("giftId", giftId);
        return  query.getResultList();
    }

    @Override
    public List findShopStockByIdAndStockId(Long stockId, Long giftId) {
        StringBuilder sqlQueryClause = new StringBuilder();
        sqlQueryClause.append(" select sg.STOCK_ID, sg.GIFT_ID, sg.QUANTITY from QSV_2015_SHOP_STOCK_GOODS@MKITDW_KM_QSV_DBL sg where sg.stock_Id = :stockId and sg.GIFT_ID = :giftId  ");
        Query query = entityManager.createNativeQuery(sqlQueryClause.toString());
        query.setParameter("stockId", stockId);
        query.setParameter("giftId", giftId);
        return  query.getResultList();
    }
}
