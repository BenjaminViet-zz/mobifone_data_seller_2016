package com.benluck.vms.mobifonedataseller.core.business;

import javax.ejb.ObjectNotFoundException;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 4/7/15
 * Time: 10:21 PM
 * To change this template use File | Settings | File Templates.
 */
public interface QTeenQStudent2015ManagementLocalBean {

    /**
     * Check if the phone number has registered to the promotion.
     * @param phoneNumber
     * @return
     * @throws ObjectNotFoundException
     */
    Boolean checkRegister(String phoneNumber) throws ObjectNotFoundException;
}
