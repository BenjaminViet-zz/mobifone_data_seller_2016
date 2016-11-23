package com.benluck.vms.mobifonedataseller.security.util;

import com.benluck.vms.mobifonedataseller.core.dto.NotificationDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyUserDetail extends User {

	public MyUserDetail(String username, String password, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked, Collection<GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired,
				accountNonLocked, authorities);
	}
	
	private Long userId;
    private String userName;
	private String displayName;
    private String isdn;
    private List<NotificationDTO> notificationList = new ArrayList<NotificationDTO>();

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public List<NotificationDTO> getNotificationList() {
        return notificationList;
    }

    public void setNotificationList(List<NotificationDTO> notificationList) {
        this.notificationList = notificationList;
    }

    public String getIsdn() {
        return isdn;
    }

    public void setIsdn(String isdn) {
        this.isdn = isdn;
    }
}
