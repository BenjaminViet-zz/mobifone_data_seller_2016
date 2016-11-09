package com.benluck.vms.mobifonedataseller.session.impl;

import com.benluck.vms.mobifonedataseller.domain.MBDCodeHistoryEntity;
import com.benluck.vms.mobifonedataseller.session.MBDCodeHistoryLocalBean;

import javax.ejb.Stateless;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/9/16
 * Time: 21:34
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "MBDCodeHisotrySessionEJB")
public class MBDCodeHisotrySessionBean extends AbstractSessionBean<MBDCodeHistoryEntity, Long> implements MBDCodeHistoryLocalBean{
    public MBDCodeHisotrySessionBean() {
    }
}
