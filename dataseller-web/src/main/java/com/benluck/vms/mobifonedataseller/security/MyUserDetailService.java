package com.benluck.vms.mobifonedataseller.security;

import com.benluck.vms.mobifonedataseller.common.Constants;
import com.benluck.vms.mobifonedataseller.common.utils.Config;
import com.benluck.vms.mobifonedataseller.core.business.NotificationManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.UserGroupManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.business.UserManagementLocalBean;
import com.benluck.vms.mobifonedataseller.core.dto.*;
import com.benluck.vms.mobifonedataseller.security.util.MyUserDetail;
import com.benluck.vms.mobifonedataseller.util.WebCommonUtil;
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
import org.springframework.stereotype.Service;

import javax.ejb.ObjectNotFoundException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


/**
 * @author benluck
 *
 */

public class MyUserDetailService implements UserDetailsService {
    private transient final Logger log = Logger.getLogger(MyUserDetailService.class);

    protected UserCache userCache = null;
    private LdapUserLookup ldapUserLookup;
    private UserGroupManagementLocalBean userGroupService;
    private NotificationManagementLocalBean notificationService;
    private UserManagementLocalBean userService;

    public void setUserService(UserManagementLocalBean userService) {
        this.userService = userService;
    }

    public void setLdapUserLookup(LdapUserLookup ldapUserLookup) {
        this.ldapUserLookup = ldapUserLookup;
    }

    public void setUserGroupService(UserGroupManagementLocalBean userGroupService) {
        this.userGroupService = userGroupService;
    }

    public void setNotificationService(NotificationManagementLocalBean notificationService) {
        this.notificationService = notificationService;
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
     * @param passUserName
     *            the username presented to the {@link
     *            org.springframework.security.authentication.dao.DaoAuthenticationProvider}
     * @return a fully populated user record (never <code>null</code>)
     * @throws org.springframework.security.core.userdetails.UsernameNotFoundException
     *             if the user could not be found or the user has no
     *             GrantedAuthority
     * @throws org.springframework.dao.DataAccessException
     *             if user could not be found for a repository-specific reason
     */
    public UserDetails loadUserByUsername(String passUserName)
            throws UsernameNotFoundException, DataAccessException {
        UserDTO account = null;
        List<PermissionDTO> permissionDTOList = null;

        //Check from LDAP
        LDAPUserDTO userDTO = null;

        String[] userPassArr = WebCommonUtil.splitUsernameAndPassword(passUserName);
        String username = userPassArr[0];
        String password = userPassArr[1];

        boolean responseFromLDAP = false;
        try{
            if(Config.getInstance().getProperty("LDAP_login").equals("true")){
                responseFromLDAP = ldapUserLookup.authenticate(username, password);
            }

            if(responseFromLDAP){
                userDTO = ldapUserLookup.getUser(username);
                try{
                    account = this.userService.findByUsername(username);
                }catch (ObjectNotFoundException oe){}
                if (userDTO != null) {
                    if(account != null){
                        account.setUserName(username);
                        account.setPassword(password);
                        account.setDisplayName(userDTO.getDisplayName());
                        account.setStatus(Constants.USER_ACTIVE);
                        account.setLDAP(Constants.USER_LDAP);
                        account.setDisplayName(userDTO.getDisplayName());
                        account = userService.updateItem(account);
                    }else{
                        account = new UserDTO();
                        account.setUserName(username);
                        account.setPassword(password);
                        account.setDisplayName(userDTO.getDisplayName());
                        account.setStatus(Constants.USER_ACTIVE);
                        account.setLDAP(Constants.USER_LDAP);
                        account.setDisplayName(userDTO.getDisplayName());
                        UserGroupDTO userGroupDTO = this.userGroupService.findByCode(Constants.USERGROUP_VMS_USER);
                        account.setUserGroup(userGroupDTO);
                        account = userService.addItem(account);
                    }
                    // cheat for check login with DAP
                    account.setPassword(password);
                }
            }else{
                account = this.userService.findByUsername(username);
            }
        }catch (Exception e) {
            // If LDAP Server is not available to connect, switch authentication to DB.
            log.error(e.getMessage(), e);
            try{
                account = this.userService.findByUsername(username);
            }catch (ObjectNotFoundException one){
                log.error("User name: " + username  + " did not exists in the system.");
            }

        }

        if (account == null) {
            log.error("Could not load user info for login with username:" + username);
            throw new UsernameNotFoundException("UserProcessingFilter.usernameNotFound:" + username);
        }else{
            if(account.getUserGroup().getCode() != null){
                if(!account.getUserGroup().getCode().equals(Constants.ADMIN_ROLE)){
                    permissionDTOList = userService.loadPermissionsByUserId(account.getUserId());

                    if(permissionDTOList.size() == 0){
                        log.error("User " + account.getUserName() + " did not grant to any permission");
                    }

                }else {
                    permissionDTOList = new ArrayList<PermissionDTO>();
                }
            }
        }

        if (account == null) {
            throw new UsernameNotFoundException("UserProcessingFilter.usernameNotFound:" + username);
        }

        if(account.getStatus() == Constants.USER_INACTIVE){
            throw new LockedException("locked_account");
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
        for(PermissionDTO roleDTO : permissionDTOList) {
            authorities.add(new SimpleGrantedAuthority(roleDTO.getCode()));
        }
        if(account.getUserGroup() != null) {
            authorities.add(new SimpleGrantedAuthority(account.getUserGroup().getCode()));
        }

        authorities.add(new SimpleGrantedAuthority(Constants.LOGON_ROLE));

        MyUserDetail loginUser = new MyUserDetail(username, username + Constants.SECURITY_CREDENTIAL_DELIMITER + account.getPassword(), true, true, true, true, authorities);
        populateUserInfo(account.getUserId(), loginUser);
        BeanUtils.copyProperties(account, loginUser);
        return loginUser;
    }


    private void populateUserInfo(Long userId, MyUserDetail loginUser){
        List<NotificationDTO> notificationList = this.notificationService.fetchNotificationNewestList(userId);
        loginUser.setNotificationList(notificationList);
    }
}