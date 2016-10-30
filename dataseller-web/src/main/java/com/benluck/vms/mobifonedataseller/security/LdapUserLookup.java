package com.benluck.vms.mobifonedataseller.security;

import com.benluck.vms.mobifonedataseller.core.dto.LDAPUserDTO;

import java.util.List;

public interface LdapUserLookup {

	public boolean authenticate(String username, String password);
	public LDAPUserDTO getUser(String userName);
    public List<LDAPUserDTO> searchByDisplayName(String displayName);

    public List<LDAPUserDTO> searchByDepartment(String department);

}
