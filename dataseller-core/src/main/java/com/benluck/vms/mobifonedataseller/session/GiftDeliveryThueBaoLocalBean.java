package com.benluck.vms.mobifonedataseller.session;

import com.benluck.vms.mobifonedataseller.domain.GiftDeliveryThueBaoEntity;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/17/14
 * Time: 12:53 AM
 * To change this template use File | Settings | File Templates.
 */
public interface GiftDeliveryThueBaoLocalBean extends GenericSessionBean<GiftDeliveryThueBaoEntity, Long>{

    /**
     * 
     * @param userId
     * @return A list of history about delivery that related to the deliverer.
     */
    List<GiftDeliveryThueBaoEntity> findByDelivererId(Long userId);
}
