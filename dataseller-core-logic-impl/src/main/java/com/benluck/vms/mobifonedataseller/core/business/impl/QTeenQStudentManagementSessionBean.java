package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.core.business.QTeenQStudent2015ManagementLocalBean;
import com.benluck.vms.mobifonedataseller.session.ThueBaoPhatTrienMoiLocalBean;

import javax.ejb.EJB;
import javax.ejb.ObjectNotFoundException;
import javax.ejb.Stateless;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 4/7/15
 * Time: 10:22 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "QTeenQStudentManagementSessionEJB")
public class QTeenQStudentManagementSessionBean implements QTeenQStudent2015ManagementLocalBean{
    public QTeenQStudentManagementSessionBean() {
    }

    @EJB
    private ThueBaoPhatTrienMoiLocalBean thueBaoPhatTrienMoiLocalBean;

    @Override
    public Boolean checkRegister(String phoneNumber) throws ObjectNotFoundException {
        return this.thueBaoPhatTrienMoiLocalBean.checkRegisterThueBao4QTeen(phoneNumber);
    }
}
