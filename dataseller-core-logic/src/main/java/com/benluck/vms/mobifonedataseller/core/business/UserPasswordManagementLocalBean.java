package com.benluck.vms.mobifonedataseller.core.business;

import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.UserPasswordDTO;

import javax.ejb.ObjectNotFoundException;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/13/14
 * Time: 12:10 PM
 * To change this template use File | Settings | File Templates.
 */
public interface UserPasswordManagementLocalBean {

    /**
     * Validate validity of the code
     * @param verifyCode
     * @param userGroupCode
     * @return
     * @throws ObjectNotFoundException
     */
    UserPasswordDTO validateVerifyingCode(String verifyCode, String userGroupCode) throws ObjectNotFoundException;
}
