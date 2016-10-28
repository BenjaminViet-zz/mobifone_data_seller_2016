package com.benluck.vms.mobifonedataseller.session;

import com.benluck.vms.mobifonedataseller.domain.MoneyExchangeBranchEntity;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/26/14
 * Time: 3:42 PM
 * To change this template use File | Settings | File Templates.
 */
public interface MoneyExchangeBranchLocalBean extends GenericSessionBean<MoneyExchangeBranchEntity, Long>{

    /**
     * Find MoneyExchangeBranch by dealerId.
     * @param dealer_id
     * @return MoneyExchangeBranchEntity
     */
    MoneyExchangeBranchEntity findByDealerId(Long dealer_id);
}
