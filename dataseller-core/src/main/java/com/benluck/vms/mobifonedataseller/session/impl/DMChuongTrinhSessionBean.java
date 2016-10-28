package com.benluck.vms.mobifonedataseller.session.impl;

import com.benluck.vms.mobifonedataseller.domain.DMChuongTrinhEntity;
import com.benluck.vms.mobifonedataseller.session.DMChuongTrinhLocalBean;

import javax.ejb.Stateless;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 3/2/15
 * Time: 11:15 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "DMChuongTrinhSessionEJB")
public class DMChuongTrinhSessionBean extends AbstractSessionBean<DMChuongTrinhEntity, Long> implements DMChuongTrinhLocalBean{
    public DMChuongTrinhSessionBean() {
    }
}
