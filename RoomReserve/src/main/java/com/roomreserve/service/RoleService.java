package com.roomreserve.service;

import java.util.List;

import com.roomreserve.entities.Role;
import com.roomreserve.entities.User;

public interface RoleService {

	List<Role> getRoles();
	
	Role createRole(Role role);
	
	void deleteRole(long roleId);
	
	Role findByName(String name); 
	
	User removeUserFromRole(long userId , long roleId);
	
	User assignRoleToUser(long userId , long roleId);
	
	Role removeAllUsersFromRole(long roleId); 
}
