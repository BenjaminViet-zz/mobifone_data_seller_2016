package com.benluck.vms.mobifonedataseller.session;

import com.benluck.vms.mobifonedataseller.core.dto.UserDTO;
import com.benluck.vms.mobifonedataseller.domain.UserEntity;

import javax.ejb.ObjectNotFoundException;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: benluck
 * Date: 10/4/14
 * Time: 3:07 PM
 * To change this template use File | Settings | File Templates.
 */
public interface UserLocalBean extends GenericSessionBean<UserEntity, Long> {
    UserEntity findByUserName(String userName) throws ObjectNotFoundException;
    List<UserEntity> findListByProperties(Map<String, Object> properties, String sortExpression, String sortDirection, Integer offset, Integer limitItems);

    UserEntity loadUserByUserNameAndPassword(String username, String password) throws ObjectNotFoundException;
}
