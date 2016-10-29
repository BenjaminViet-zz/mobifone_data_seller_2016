package com.benluck.vms.mobifonedataseller.security;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.core.business.UserManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.RoleDTO;
import com.benluck.vms.mobifonedataseller.core.dto.UserDTO;
import com.benluck.vms.mobifonedataseller.security.util.MyUserDetail;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;


/**
 * @author benluck
 *
 */

public class MyUserDetailService implements UserDetailsService {
    private transient final Logger log = Logger.getLogger(getClass());

    protected UserCache userCache = null;

    @Autowired
    private UserManagementLocalBean userManagementLocalBean;

    public void setUserManagementLocalBean(UserManagementLocalBean userManagementLocalBean) {
        this.userManagementLocalBean = userManagementLocalBean;
    }

    /**
     * Creates new instance of MyUserDetailService
     */
    public MyUserDetailService() {

    }

    /**
     * Set UserCache
     *
     * @param userCache
     *            user cache to set
     */
    public void setUserCache(UserCache userCache) {
        this.userCache = userCache;
    }

    /**
     * Locates the user based on the username. In the actual implementation, the
     * search may possibly be case insensitive, or case insensitive depending on
     * how the implementaion instance is configured. In this case, the
     * <code>UserDetails</code> object that comes back may have a username
     * that is of a different case than what was actually requested..
     *
     * @param username
     *            the username presented to the {@link
     *            org.springframework.security.authentication.dao.DaoAuthenticationProvider}
     * @return a fully populated user record (never <code>null</code>)
     * @throws org.springframework.security.core.userdetails.UsernameNotFoundException
     *             if the user could not be found or the user has no
     *             GrantedAuthority
     * @throws org.springframework.dao.DataAccessException
     *             if user could not be found for a repository-specific reason
     */
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException, DataAccessException {
        UserDTO account = null;
        List<RoleDTO> roleDTOs = null;
        try {
            account = userManagementLocalBean.findByUsername(username);
            roleDTOs = userManagementLocalBean.findRoleOfUser(account.getUserId());
        } catch (Exception e) {
            log.error("Could not load user info for login with username:" + username + ". Exception: " + e.getMessage(), e);
        }

        if (account == null) {
            throw new UsernameNotFoundException("UserProcessingFilter.usernameNotFound:" + username);

        }
        if (account.getStatus() == null) {
            throw new UsernameNotFoundException("UserProcessingFilter.usernameNotFound:" + username + ".Status is NULL");
        }else if (!account.getStatus().equals(Constants.USER_ACTIVE)) {
            if (account.getStatus().equals(Constants.USER_INACTIVE)) {
                throw new LockedException("User is in-active:" + username);
            }else {
                throw new UsernameNotFoundException("UserProcessingFilter.usernameNotFound:" + username + ".Status not available.");
            }
        }

        //this line of code is used to check whether the user has login or not
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for(RoleDTO roleDTO : roleDTOs) {
            authorities.add(new SimpleGrantedAuthority(roleDTO.getCode()));
        }
        if(account.getUserGroup() != null) {
            authorities.add(new SimpleGrantedAuthority(account.getUserGroup().getCode()));
        }

        authorities.add(new SimpleGrantedAuthority(Constants.LOGON_ROLE));

        MyUserDetail loginUser = new MyUserDetail(username, account.getPassword(), true, true, true, true, authorities);
        BeanUtils.copyProperties(account, loginUser);
        return loginUser;
    }

}