package com.benluck.vms.mobifonedataseller.session;

import com.benluck.vms.mobifonedataseller.domain.UsedCardCodeEntity;

import java.util.HashSet;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/26/16
 * Time: 11:04
 * To change this template use File | Settings | File Templates.
 */
public interface UsedCardCodeLocalBean extends GenericSessionBean<UsedCardCodeEntity, Long>{

    HashSet<String> findAllListCardCode();

    Boolean checkImportUsedCardCode();

    void deleteAll();
}
