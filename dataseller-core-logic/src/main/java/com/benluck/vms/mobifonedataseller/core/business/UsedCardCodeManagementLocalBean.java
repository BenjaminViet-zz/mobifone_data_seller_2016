package com.benluck.vms.mobifonedataseller.core.business;

import com.benluck.vms.mobifonedataseller.core.dto.UsedCardCodeDTO;

import javax.ejb.DuplicateKeyException;
import javax.ejb.Local;
import java.util.HashSet;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/26/16
 * Time: 11:01
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface UsedCardCodeManagementLocalBean {

    HashSet<String> findAllListCardCode();

    void importCardCodeList(List<UsedCardCodeDTO> importUsedCardCodeList) throws Exception;

    Boolean checkImportUsedCardCode();
}
