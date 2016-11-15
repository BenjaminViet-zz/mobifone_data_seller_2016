package com.benluck.vms.mobifonedataseller.session;

import com.benluck.vms.mobifonedataseller.domain.MBDCodeHistoryEntity;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/11/16
 * Time: 19:46
 * To change this template use File | Settings | File Templates.
 */
public interface CodeHistoryLocalBean extends GenericSessionBean<MBDCodeHistoryEntity, Long>{

    Double calculateTotalPaidPackageValue(String isdn);

}
