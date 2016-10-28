package com.benluck.vms.mobifonedataseller.session;

import com.benluck.vms.mobifonedataseller.domain.UserPasswordEntity;

import javax.ejb.ObjectNotFoundException;

/**
 * Created with IntelliJ IDEA.
 * User: benluck
 * Date: 10/4/14
 * Time: 3:12 PM
 * To change this template use File | Settings | File Templates.
 */
public interface UserPasswordLocalBean extends GenericSessionBean<UserPasswordEntity, Long> {
    /**
     * Get the UserPassword that generated for the user to login.
     * @param verifyCode
     * @param userGroupType
     * @return
     * @throws ObjectNotFoundException
     */
    UserPasswordEntity getInfoByVerifyingCode(String verifyCode, String userGroupType) throws ObjectNotFoundException;

    /**
     * Get lastest verifying code of the user for login.
     * @param phoneNumber
     * @return
     */
    UserPasswordEntity findLastestBySoPhoneNumber(String phoneNumber);
}
