package com.benluck.vms.mobifonedataseller.session;

import com.benluck.vms.mobifonedataseller.domain.GiftDeliveryThueBaoHistoryEntity;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/17/14
 * Time: 12:53 AM
 * To change this template use File | Settings | File Templates.
 */
public interface GiftDeliveryThueBaoHistoryLocalBean extends GenericSessionBean<GiftDeliveryThueBaoHistoryEntity, Long>{
    /**
     * Fetch for CT tich diem cuoc goi
     * @param thue_bao
     * @param fromDate
     * @param toDate
     * @param offset
     * @param limit
     * @param sortExpression
     * @param sortDirection
     * @return
     */
    Object[] report4KHCN_tdcg(String thue_bao, Timestamp fromDate, Timestamp toDate, Integer offset, Integer limit, String sortExpression, String sortDirection);

    /**
     * Fetch all Gift list in the promotion.
     * @return A list of Objects.
     */
    List findGiftList();
}
