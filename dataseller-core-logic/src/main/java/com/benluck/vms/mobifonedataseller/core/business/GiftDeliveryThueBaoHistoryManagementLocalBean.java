package com.benluck.vms.mobifonedataseller.core.business;

import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.GiftDTO;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.GiftDeliveryThueBaoHistoryDTO;

import javax.ejb.Local;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/17/14
 * Time: 12:56 AM
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface GiftDeliveryThueBaoHistoryManagementLocalBean {
    /**
     * Fetch for CT tich diem cuoc goi
     * @param dto
     * @param offset
     * @param limit
     * @param sortExpression
     * @param sortDirection
     * @return
     */
    Object[] reportByKHCN_tdcg(GiftDeliveryThueBaoHistoryDTO dto, Integer offset, Integer limit, String sortExpression, String sortDirection);

    /**
     * Fetch for CT Q-Student Q-Teen
     * @param dto
     * @param offset
     * @param limit
     * @param sortExpression
     * @param sortDirection
     * @return
     */
    Object[] reportByKHCN_qStudent(GiftDeliveryThueBaoHistoryDTO dto, Integer offset, Integer limit, String sortExpression, String sortDirection);

    /**
     * Fetch all Gift list in the promotion.
     * @return A list of GiftDTO
     */
    List<GiftDTO> findGiftList();
}
