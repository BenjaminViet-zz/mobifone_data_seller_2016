package com.benluck.vms.mobifonedataseller.session;

import com.benluck.vms.mobifonedataseller.domain.GiftEntity;

/**
 * Created with IntelliJ IDEA.
 * User: benluck
 * Date: 10/4/14
 * Time: 3:11 PM
 * To change this template use File | Settings | File Templates.
 */
public interface GiftLocalBean extends GenericSessionBean<GiftEntity, Long> {

    /**
     * Find Gift by unique code.
     * @param code
     * @return GiftEntity.
     */
    GiftEntity findByCode(String code);
}
