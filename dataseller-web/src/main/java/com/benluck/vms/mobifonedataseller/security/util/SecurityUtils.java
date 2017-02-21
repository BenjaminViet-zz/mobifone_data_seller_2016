package com.benluck.vms.mobifonedataseller.security.util;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.security.MD5Utils;
import com.benluck.vms.mobifonedataseller.context.AppContext;
import com.benluck.vms.mobifonedataseller.core.business.UserManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

public class SecurityUtils {
    private static ApplicationContext ctx = AppContext.getApplicationContext();
    private static UserManagementLocalBean userService = ctx.getBean(UserManagementLocalBean.class);
	/**
	 * This method to retrieve the current online User Detail 
	 * @return the current online MyUserDetail object
	 */
	public static MyUserDetail getPrincipal() {
		return (MyUserDetail) (SecurityContextHolder
				.getContext()).getAuthentication().getPrincipal();
	}
	/**
	 * This getLoginUserId() is only used for doing jUnit test case only
	 * @return the current login name for online user
	 */
	public static Long getLoginUserId() {
		return getPrincipal().getUserId();
	}


    /**
     * Check a role from authorized roles of the authenticated user.
     * @param roleCode
     * @return true if the roleCode existed in user's roles, else false.
     */
	public static boolean userHasAuthority(String roleCode) {
		List<GrantedAuthority> list = (List<GrantedAuthority>)(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
		List<GrantedAuthority> authorities = list;
    	for (GrantedAuthority authority : authorities) {
    		if (authority.getAuthority().equals(roleCode)) {
    			return true;
    		}
    	}
    	return false;
	}

    /**
     * Get list of authorized roles or permissions of the authenticated user.
     * @return a list of authorized roles of the user.
     */
    public static List<String> getAuthorities(){
        List<String> results = new ArrayList<String>();
        List<GrantedAuthority> authorities = (List<GrantedAuthority>)(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        for (GrantedAuthority authority : authorities) {
            results.add(authority.getAuthority());
        }
        return results;
    }

    public static boolean validateUser4RestAPIAccess(String userName, String password) throws Exception{
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        List<UserDTO> allUserList = userService.fetchAllUserIsNotLDAP();
        if (allUserList != null && allUserList.size() > 0){
            StringBuffer tmpEncryptedPasswordSHA256 = null;
            for (UserDTO userDTO : allUserList){
                if (userDTO.getUserName().equals(userName)){

                    tmpEncryptedPasswordSHA256 = new StringBuffer(DatatypeConverter.printHexBinary(md.digest(userDTO.getPassword().getBytes("UTF-8"))));
                    System.out.println(tmpEncryptedPasswordSHA256.toString());
                    if (password.equals(tmpEncryptedPasswordSHA256.toString())){
                        return true;
                    }
                }
            }
        }

        return false;
    }
}