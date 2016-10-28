package com.benluck.vms.mobifonedataseller.core.business.impl;

import com.benluck.vms.mobifonedataseller.common.utils.DozerSingletonMapper;
import com.benluck.vms.mobifonedataseller.core.business.UserPasswordManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.promotionDTO2014.UserPasswordDTO;
import com.benluck.vms.mobifonedataseller.domain.UserPasswordEntity;
import com.benluck.vms.mobifonedataseller.session.UserPasswordLocalBean;

import javax.ejb.EJB;
import javax.ejb.ObjectNotFoundException;
import javax.ejb.Stateless;
import java.sql.Time;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Luck
 * Date: 10/13/14
 * Time: 12:11 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "UserPasswordManagementSessionEJB")
public class UserPasswordManagementSessionBean implements UserPasswordManagementLocalBean{
    public UserPasswordManagementSessionBean() {
    }

    @EJB
    private UserPasswordLocalBean userPasswordLocalBean;

    @Override
    public UserPasswordDTO validateVerifyingCode(String verifyCode, String userGroupCode) throws ObjectNotFoundException {
        UserPasswordEntity entity = this.userPasswordLocalBean.getInfoByVerifyingCode(verifyCode, userGroupCode);
        UserPasswordDTO dto = DozerSingletonMapper.getInstance().map(entity, UserPasswordDTO.class);
        if(entity.getExpiredTime().compareTo(new Time(System.currentTimeMillis())) < 0){
            dto.setExpired(true);
        }
        return dto;
    }
}
