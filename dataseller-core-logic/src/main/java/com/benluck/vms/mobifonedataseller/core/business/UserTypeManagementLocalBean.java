package com.benluck.vms.mobifonedataseller.core.business;

import com.benluck.vms.mobifonedataseller.core.dto.UserTypeDTO;

import javax.ejb.Local;
import javax.ejb.ObjectNotFoundException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vietquocpham
 * Date: 5/22/17
 * Time: 22:07
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface UserTypeManagementLocalBean {
    List<UserTypeDTO> findAll();

    UserTypeDTO findByCode(String code) throws ObjectNotFoundException;
}
