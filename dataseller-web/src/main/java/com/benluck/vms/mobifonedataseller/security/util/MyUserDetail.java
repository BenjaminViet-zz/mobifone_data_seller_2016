package com.benluck.vms.mobifonedataseller.security.util;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

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
	
	private String email;

    private String role;

    private String mobileNumber;

    private String shopCode;

    private Long departmentId;

    private Long branchId;

    private Long mappingDBLinkBranchId;

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Retrieve the email from the user
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
     * Set email to the user
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

    /**
     * Retrieve the userId from the user
     * @return userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Set userId to the user
     * @param userId the userId to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Retrieve the role from the user.
     * @return role of the authorized user in the system
     */
    public String getRole() {
        return role;
    }

    /**
     * Set role to the user
     * @param role the role to set
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Retrieve the Shop Code from the user.
     * @return Shop Code of the shop that user is working for.
     */
    public String getShopCode() {
        return shopCode;
    }

    /**
     * Set Shop Code to the user
     * @param shopCode the shop code of the shop that user is working for.
     */
    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    /**
     * Retrieve departmentId which the user belong to.
     * @return departmentId of the department that user is belong to.
     */
    public Long getDepartmentId() {
        return departmentId;
    }

    /**
     * Set the departmentId to the user
     * @param departmentId
     */
    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * Retrieve user name of the user
     * @return userName of the user which is used to login to the system.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Set user name for the user. This is used for login.
     * @param userName user name of the user which is used to login.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Retrieve branchId of the branch that the user is working on.
     * @return branchId of the branch that the user is working on.
     */
    public Long getBranchId() {
        return branchId;
    }

    /**
     * Set branchId of the branch that the user is working for.
     * @param branchId the branchId of the branch that the user is working for.
     */
    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    /**
     * Retrieve the mapping of branchId in Database Link from the user
     * @return
     */
    public Long getMappingDBLinkBranchId() {
        return mappingDBLinkBranchId;
    }

    /**
     * Set the mapping of branchId through Database Link for the user
     * @param mappingDBLinkBranchId
     */
    public void setMappingDBLinkBranchId(Long mappingDBLinkBranchId) {
        this.mappingDBLinkBranchId = mappingDBLinkBranchId;
    }
}
