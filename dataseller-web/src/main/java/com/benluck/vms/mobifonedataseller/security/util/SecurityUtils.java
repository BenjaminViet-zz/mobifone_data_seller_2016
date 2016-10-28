package com.benluck.vms.mobifonedataseller.security.util;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.security.MD5Utils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

public class SecurityUtils {
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

}