/**
 * 
 */
package com.benluck.vms.mobifonedataseller.security;

import com.benluck.vms.mobifonedataseller.common.security.DesEncrypterUtils;
import com.benluck.vms.mobifonedataseller.common.utils.Config;
import com.benluck.vms.mobifonedataseller.core.business.UserManagementLocalBean;
import com.benluck.vms.mobifonedataseller.util.WebCommonUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.encoding.PasswordEncoder;

/**
 * @author benluck
 *
 */
public class MyPasswordEncoder implements PasswordEncoder {

    private LdapUserLookup ldapUserLookup;
    private UserManagementLocalBean userService;

    public void setLdapUserLookup(LdapUserLookup ldapUserLookup) {
        this.ldapUserLookup = ldapUserLookup;
    }

    public void setUserService(UserManagementLocalBean userService) {
        this.userService = userService;
    }

    public String encodePassword(String password, Object salt)
			throws DataAccessException {
		return DesEncrypterUtils.getInstance().encrypt(password);
	}
    public boolean isPasswordValid(String encPass, String rawPass, Object salt)
            throws DataAccessException {
        boolean res = false;
        if(StringUtils.isNotEmpty(rawPass)) {
            String[] userPassArr = WebCommonUtil.splitUsernameAndPassword(encPass);
            String userName = userPassArr[0];
            String encryptPass = userPassArr[1];

            String encPass2 = DesEncrypterUtils.getInstance().encrypt(rawPass);
            res = encryptPass.equals(encPass2);

            if(Config.getInstance().getProperty("LDAP_login").equals("true")){
                if(!res){
                    try{
                        res = ldapUserLookup.authenticate(userName, rawPass);
                        if(res){
                            userService.updatePasswordUserLDAP(userName, rawPass);
                        }
                    }catch (Exception e){}
                }
            }
        }
        return res;
    }

}
