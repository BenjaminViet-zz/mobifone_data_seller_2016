package com.benluck.vms.mobifonedataseller.session;

import com.benluck.vms.mobifonedataseller.domain.GiftDeliveryAgentHistoryEntity;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/15/14
 * Time: 11:13 AM
 * To change this template use File | Settings | File Templates.
 */
public interface GiftDeliveryAgentHistoryLocalBean extends GenericSessionBean<GiftDeliveryAgentHistoryEntity, Long>{

    /**
     * Find all stock history that related to the subscriberId.
     * @param userId
     * @return A list of history that related to the subscriberId.
     */
    List<GiftDeliveryAgentHistoryEntity> findBySubscriberId(Long userId);
}
