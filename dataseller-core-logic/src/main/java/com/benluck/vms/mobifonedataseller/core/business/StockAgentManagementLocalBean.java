package com.benluck.vms.mobifonedataseller.core.business;

import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.StockAgentDTO;

import javax.ejb.Local;
import javax.ejb.ObjectNotFoundException;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/15/14
 * Time: 11:10 AM
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface StockAgentManagementLocalBean {
    /**
     * Fetch for promotion CT tich diem cuoc goi. Get total of gifts in stock by departmentId (shopId).
     * @param departmentId
     * @return
     */
    Integer countInventoryTotalByAgentId_tdcg(Long departmentId);

    /**
     * Fetch for promotion CT tich diem Q-Student Q-Teen. get total of gifts in stock by departmentId (shopId) and giftId.
     *
     * @param departmentId
     * @param giftId
     * @return
     */
    Integer countInventoryTotalByAgentId_qStudent(Long departmentId, Long giftId);

    /**
     * Fetch for promotion CT Tich Diem Cuoc Goi Nhan Voucher. Find other shop stock which has gift for exchange.
     * @param chiNhanh
     * @param agentId
     * @param trang_thai_kho
     * @param offset
     * @param limit
     * @param sortExpression
     * @param sortDirection
     * @return
     */
    Object[] searchOtherShopStockByProperties_tdcg(Integer chiNhanh, Long agentId, Integer trang_thai_kho, Integer offset, Integer limit, String sortExpression, String sortDirection);

    /**
     * Fetch for promotion CT Q-Student Q-Teen.
     *
     * @param pojo
     * @param offset
     * @param limit
     * @param sortExpression
     * @param sortDirection
     * @return
     */
    Object[] searchOtherShopStockByProperties_qStudent(String ctCode, StockAgentDTO pojo, Integer offset, Integer limit, String sortExpression, String sortDirection) throws ObjectNotFoundException;
}
