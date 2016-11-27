package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.core.business.UsedCardCodeManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.UsedCardCodeDTO;
import com.benluck.vms.mobifonedataseller.domain.UsedCardCodeEntity;
import com.benluck.vms.mobifonedataseller.session.UsedCardCodeLocalBean;
import com.benluck.vms.mobifonedataseller.utils.MobiFoneSecurityBase64Util;

import javax.ejb.DuplicateKeyException;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.HashSet;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/26/16
 * Time: 11:02
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "UsedCardCodeManagementSessionEJB")
public class UsedCardCodeManagementSessionBean implements UsedCardCodeManagementLocalBean{

    @EJB
    private UsedCardCodeLocalBean usedCardCodeService;

    public UsedCardCodeManagementSessionBean() {
    }

    @Override
    public HashSet<String> findAllListCardCode() {
        return this.usedCardCodeService.findAllListCardCode();
    }

    @Override
    public void importCardCodeList(List<UsedCardCodeDTO> importUsedCardCodeList) throws Exception {
        this.usedCardCodeService.deleteAll();

        for (UsedCardCodeDTO dto : importUsedCardCodeList){
            UsedCardCodeEntity entity = new UsedCardCodeEntity();
            entity.setCardCode(MobiFoneSecurityBase64Util.encode(dto.getCardCode()));
            this.usedCardCodeService.save(entity);
        }
    }

    @Override
    public Boolean checkImportUsedCardCode() {
        return this.usedCardCodeService.checkImportUsedCardCode();
    }
}
