/**
 * 
 */
package com.benluck.vms.mobifonedataseller.security;

import com.benluck.vms.mobifonedataseller.common.security.DesEncrypterUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.encoding.PasswordEncoder;

/**
 * @author benluck
 *
 */
public class MyPasswordEncoder implements PasswordEncoder {


	public String encodePassword(String password, Object salt)
			throws DataAccessException {
		return DesEncrypterUtils.getInstance().encrypt(password);
	}
    public boolean isPasswordValid(String encPass, String rawPass, Object salt)
            throws DataAccessException {
        if(StringUtils.isEmpty(rawPass)) {
            return false;
        }
        String encryptedPass = DesEncrypterUtils.getInstance().encrypt(rawPass);
        return encryptedPass.equals(encPass);
    }

}
