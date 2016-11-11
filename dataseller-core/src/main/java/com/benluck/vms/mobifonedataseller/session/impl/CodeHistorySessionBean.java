package com.benluck.vms.mobifonedataseller.session.impl;

import com.benluck.vms.mobifonedataseller.domain.MBDCodeHistoryEntity;
import com.benluck.vms.mobifonedataseller.session.CodeHistoryLocalBean;

import javax.ejb.Stateless;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 11/11/16
 * Time: 19:46
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "CodeHistorySessionEJB")
public class CodeHistorySessionBean extends AbstractSessionBean<MBDCodeHistoryEntity, Long> implements CodeHistoryLocalBean{

    public CodeHistorySessionBean() {
    }

}
